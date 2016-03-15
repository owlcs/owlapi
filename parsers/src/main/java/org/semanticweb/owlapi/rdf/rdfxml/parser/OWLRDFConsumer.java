/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.RDFDocumentFormat;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.parser.Translators.TranslatorAccessor;
import org.semanticweb.owlapi.rdf.rdfxml.parser.TripleHandlers.HandlerAccessor;
import org.semanticweb.owlapi.util.AnonymousNodeChecker;
import org.semanticweb.owlapi.util.AnonymousNodeCheckerImpl;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.RemappingIndividualProvider;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;

/**
 * A parser/interpreter for an RDF graph which represents an OWL ontology. The
 * consumer interprets triple patterns in the graph to produce the appropriate
 * OWLAPI entities, class expressions and axioms. The parser is based on triple
 * handlers. A given triple handler handles a specific type of triple. Generally
 * speaking this is based on the predicate of a triple, for example, A
 * rdfs:subClassOf B is handled by a subClassOf handler. A handler determines if
 * it can handle a triple in a streaming mode (i.e. while parsing is taking
 * place) or if it can handle a triple after parsing has taken place and the
 * complete graph is in memory. Once a handler handles a triple, that triple is
 * deemed to have been consumed an is discarded. The parser attempts to consume
 * as many triples as possible while streaming parsing is taking place. Whether
 * or not a triple can be consumed during parsing is determined by installed
 * triple handlers.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLRDFConsumer implements RDFConsumer, AnonymousNodeChecker, OWLAnonymousIndividualByIdProvider {

    /** The Constant DAML_OIL. */
    private static final String DAML_OIL = "http://www.daml.org/2001/03/daml+oil#";
    private static final Logger LOGGER = LoggerFactory.getLogger(OWLRDFConsumer.class);
    @Nonnull final TripleLogger tripleLogger;
    /** The configuration. */
    @Nonnull private final OWLOntologyLoaderConfiguration configuration;
    /** The owl ontology manager. */
    private final OWLOntologyManager owlOntologyManager;
    // The set of IRIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owl:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    /** The class expression iris. */
    private final Set<IRI> classExpressionIRIs;
    /** Same as classExpressionIRIs but for object properties */
    private final Set<IRI> objectPropertyExpressionIRIs;
    /** Same as classExpressionIRIs but for data properties */
    private final Set<IRI> dataPropertyExpressionIRIs;
    /**
     * Same as classExpressionIRIs but for rdf properties things neither typed
     * as a data or object property - bad!
     */
    private final Set<IRI> propertyIRIs;
    /** Set of IRIs that are typed by non-system types and also owl:Thing */
    private final Set<IRI> individualIRIs;
    /** Same as classExpressionIRIs but for annotation properties */
    private final Set<IRI> annotationPropertyIRIs;
    /** The annotation iris. */
    private final Set<IRI> annotationIRIs;
    /** IRIs that had a type triple to rdfs:Datatange */
    private final Set<IRI> dataRangeIRIs;
    /** The IRI of the first reource that is typed as an ontology */
    private IRI firstOntologyIRI;
    /** IRIs that had a type triple to owl:Ontology */
    private final Set<IRI> ontologyIRIs;
    /** IRIs that had a type triple to owl:Restriction */
    private final Set<IRI> restrictionIRIs;
    /** Maps rdf:next triple subjects to objects */
    private final Map<IRI, IRI> listRestTripleMap;
    /** The list first resource triple map. */
    private final Map<IRI, IRI> listFirstResourceTripleMap;
    /** The list first literal triple map. */
    private final Map<IRI, OWLLiteral> listFirstLiteralTripleMap;
    /** The axioms. */
    private final Set<IRI> axioms = new HashSet<>();
    /** The shared anonymous nodes. */
    private final Map<IRI, Object> sharedAnonymousNodes = new HashMap<>();
    /** The pending annotations. */
    private final Set<OWLAnnotation> pendingAnnotations = new HashSet<>();
    /** The annotated anon source2 annotation map. */
    private final Map<IRI, Set<IRI>> annotatedAnonSource2AnnotationMap = new HashMap<>();
    /** The ontology that the RDF will be parsed into. */
    @Nonnull private final OWLOntology ontology;
    /** The expected axioms. */
    private int expectedAxioms = -1;
    /** The parsed axioms. */
    private int parsedAxioms = 0;
    /** The ontology format. */
    private RDFDocumentFormat ontologyFormat;
    /** The data factory. */
    private final OWLDataFactory dataFactory;
    /** The last added axiom. */
    private OWLAxiom lastAddedAxiom;
    /** The synonym map. */
    private Map<IRI, IRI> synonymMap;
    // SWRL Stuff
    /** The swrl rules. */
    private final Set<IRI> swrlRules;
    /** The swrl individual property atoms. */
    private final Set<IRI> swrlIndividualPropertyAtoms;
    /** The swrl data valued property atoms. */
    private final Set<IRI> swrlDataValuedPropertyAtoms;
    /** The swrl class atoms. */
    private final Set<IRI> swrlClassAtoms;
    /** The swrl data range atoms. */
    private final Set<IRI> swrlDataRangeAtoms;
    /** The swrl built in atoms. */
    private final Set<IRI> swrlBuiltInAtoms;
    /** The swrl variables. */
    private final Set<IRI> swrlVariables;
    /** The swrl same as atoms. */
    private final Set<IRI> swrlSameAsAtoms;
    /** The swrl different from atoms. */
    private final Set<IRI> swrlDifferentFromAtoms;
    /** The iri provider. */
    private IRIProvider iriProvider;
    /**
     * A cache of annotation axioms to be added at the end - saves some peek
     * memory doing this.
     */
    private final Collection<OWLAnnotationAxiom> parsedAnnotationAxioms = new ArrayList<>();
    /** The axioms to be removed. */
    private final Collection<OWLAxiom> axiomsToBeRemoved = new ArrayList<>();
    /** The parsed all triples. */
    private boolean parsedAllTriples = false;
    final HandlerAccessor handlerAccessor;
    final TranslatorAccessor translatorAccessor;
    private final AnonymousNodeChecker nodeCheckerDelegate;
    @Nonnull private final ArrayListMultimap<IRI, Class<?>> guessedDeclarations = ArrayListMultimap.create();
    RemappingIndividualProvider anonProvider;

    /**
     * Instantiates a new oWLRDF consumer.
     * 
     * @param ontology
     *        the ontology
     * @param configuration
     *        the configuration
     */
    public OWLRDFConsumer(@Nonnull OWLOntology ontology, @Nonnull OWLOntologyLoaderConfiguration configuration) {
        this(ontology, new AnonymousNodeCheckerImpl(), configuration);
    }

    /**
     * Instantiates a new oWLRDF consumer.
     * 
     * @param ontology
     *        the ontology
     * @param checker
     *        anonymous node checker
     * @param configuration
     *        the configuration
     */
    public OWLRDFConsumer(@Nonnull OWLOntology ontology, @Nonnull AnonymousNodeChecker checker,
        @Nonnull OWLOntologyLoaderConfiguration configuration) {
        nodeCheckerDelegate = checker;
        owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        dataFactory = owlOntologyManager.getOWLDataFactory();
        anonProvider = new RemappingIndividualProvider(dataFactory);
        this.configuration = configuration;
        handlerAccessor = new HandlerAccessor(this);
        translatorAccessor = new TranslatorAccessor(this);
        classExpressionIRIs = CollectionFactory.createSet();
        objectPropertyExpressionIRIs = CollectionFactory.createSet();
        dataPropertyExpressionIRIs = CollectionFactory.createSet();
        individualIRIs = CollectionFactory.createSet();
        annotationPropertyIRIs = CollectionFactory.createSet();
        for (IRI iri : BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            annotationPropertyIRIs.add(iri);
        }
        annotationIRIs = new HashSet<>();
        dataRangeIRIs = CollectionFactory.createSet();
        propertyIRIs = CollectionFactory.createSet();
        restrictionIRIs = CollectionFactory.createSet();
        ontologyIRIs = CollectionFactory.createSet();
        listFirstLiteralTripleMap = CollectionFactory.createMap();
        listFirstResourceTripleMap = CollectionFactory.createMap();
        listRestTripleMap = CollectionFactory.createMap();
        for (OWL2Datatype dt : OWL2Datatype.values()) {
            dataRangeIRIs.add(dt.getIRI());
        }
        dataRangeIRIs.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        if (!configuration.isStrict()) {
            for (XSDVocabulary vocabulary : XSDVocabulary.values()) {
                dataRangeIRIs.add(vocabulary.getIRI());
            }
        }
        swrlRules = new HashSet<>();
        swrlIndividualPropertyAtoms = new HashSet<>();
        swrlDataValuedPropertyAtoms = new HashSet<>();
        swrlClassAtoms = new HashSet<>();
        swrlDataRangeAtoms = new HashSet<>();
        swrlBuiltInAtoms = new HashSet<>();
        swrlVariables = new HashSet<>();
        swrlSameAsAtoms = new HashSet<>();
        swrlDifferentFromAtoms = new HashSet<>();
        classExpressionIRIs.add(OWLRDFVocabulary.OWL_THING.getIRI());
        classExpressionIRIs.add(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        objectPropertyExpressionIRIs.add(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
        objectPropertyExpressionIRIs.add(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
        dataPropertyExpressionIRIs.add(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
        dataPropertyExpressionIRIs.add(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
        setupSynonymMap();
        setupSinglePredicateMaps();
        // Cache anything in the existing imports closure
        importsClosureChanged();
        if (this.ontology.getOntologyID().getOntologyIRI().isPresent()) {
            addOntology(this.ontology.getOntologyID().getOntologyIRI().get());
        }
        tripleLogger = new TripleLogger();
    }

    @Override
    public void addPrefix(String abbreviation, String value) {
        if (ontologyFormat.isPrefixOWLOntologyFormat()) {
            ontologyFormat.asPrefixOWLOntologyFormat().setPrefix(abbreviation, value);
        }
    }

    /**
     * Sets the iRI provider.
     * 
     * @param iriProvider
     *        the new iRI provider
     */
    protected void setIRIProvider(IRIProvider iriProvider) {
        this.iriProvider = iriProvider;
    }

    private void addSingleValuedResPredicate(OWLRDFVocabulary v) {
        Map<IRI, IRI> map = CollectionFactory.createMap();
        singleValuedResTriplesByPredicate.put(v.getIRI(), map);
    }

    private void setupSinglePredicateMaps() {
        addSingleValuedResPredicate(OWL_ON_PROPERTY);
        addSingleValuedResPredicate(OWL_SOME_VALUES_FROM);
        addSingleValuedResPredicate(OWL_ALL_VALUES_FROM);
        addSingleValuedResPredicate(OWL_ON_CLASS);
        addSingleValuedResPredicate(OWL_ON_DATA_RANGE);
    }

    private void setupSynonymMap() {
        // We can load legacy ontologies by providing synonyms for built in
        // vocabulary
        // where the vocabulary has simply changed (e.g. DAML+OIL -> OWL)
        synonymMap = CollectionFactory.createMap();
        // Legacy protege-owlapi representation of QCRs
        synonymMap.put(IRI.create(Namespaces.OWL.getPrefixIRI(), "valuesFrom"), OWL_ON_CLASS.getIRI());
        if (!configuration.isStrict()) {
            addDAMLOILVocabulary();
            addIntermediateOWLSpecVocabulary();
        }
    }

    /** Adds the damloil vocabulary. */
    private void addDAMLOILVocabulary() {
        synonymMap.put(IRI.create(DAML_OIL, "subClassOf"), RDFS_SUBCLASS_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "imports"), OWL_IMPORTS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "range"), RDFS_RANGE.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "hasValue"), OWL_HAS_VALUE.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "type"), RDF_TYPE.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "domain"), RDFS_DOMAIN.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "versionInfo"), OWL_VERSION_INFO.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "comment"), RDFS_COMMENT.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "onProperty"), OWL_ON_PROPERTY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "toClass"), OWL_ALL_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "hasClass"), OWL_SOME_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Restriction"), OWL_RESTRICTION.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Class"), OWL_CLASS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Thing"), OWL_THING.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Nothing"), OWL_NOTHING.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "minCardinality"), OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "cardinality"), OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "maxCardinality"), OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "inverseOf"), OWL_INVERSE_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "samePropertyAs"), OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "hasClassQ"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "cardinalityQ"), OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "maxCardinalityQ"), OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "minCardinalityQ"), OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "complementOf"), OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "unionOf"), OWL_UNION_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "intersectionOf"), OWL_INTERSECTION_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "label"), RDFS_LABEL.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "ObjectProperty"), OWL_OBJECT_PROPERTY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "DatatypeProperty"), OWL_DATA_PROPERTY.getIRI());
    }

    /**
     * There may be some ontologies floating about that use early versions of
     * the OWL 1.1 vocabulary. We can map early versions of the vocabulary to
     * the current OWL 1.1 vocabulary.
     */
    private void addIntermediateOWLSpecVocabulary() {
        for (OWLRDFVocabulary v : values()) {
            addLegacyMapping(v);
        }
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(IRI.create(Namespaces.OWL.toString(), v.getShortForm()), v.getIRI());
            synonymMap.put(IRI.create(Namespaces.OWL11.toString(), v.getShortForm()), v.getIRI());
            synonymMap.put(IRI.create(Namespaces.OWL2.toString(), v.getShortForm()), v.getIRI());
        }
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(IRI.create(Namespaces.OWL2.toString(), v.getShortForm()), v.getIRI());
        }
        synonymMap.put(DeprecatedVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION,
            OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION,
            OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        // Intermediate OWL 2 spec
        synonymMap.put(DeprecatedVocabulary.OWL_SUBJECT, OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_PREDICATE, OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_OBJECT, OWL_ANNOTATED_TARGET.getIRI());
        // Preliminary OWL 1.1 Vocab
        synonymMap.put(IRI.create(Namespaces.OWL.toString(), "cardinalityType"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL.toString(), "dataComplementOf"), OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_ANTI_SYMMETRIC_PROPERTY, OWL_ASYMMETRIC_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_FUNCTIONAL_DATA_PROPERTY, OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_FUNCTIONAL_OBJECT_PROPERTY, OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_SUB_DATA_PROPERTY_OF, RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_SUB_OBJECT_PROPERTY_OF, RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_OBJECT_PROPERTY_RANGE, RDFS_RANGE.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_DATA_PROPERTY_RANGE, RDFS_RANGE.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_OBJECT_PROPERTY_DOMAIN, RDFS_DOMAIN.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_DATA_PROPERTY_DOMAIN, RDFS_DOMAIN.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_DISJOINT_DATA_PROPERTIES, OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_DISJOINT_OBJECT_PROPERTIES, OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_EQUIVALENT_DATA_PROPERTIES, OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_EQUIVALENT_OBJECT_PROPERTIES, OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_OBJECT_RESTRICTION, OWL_RESTRICTION.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_DATA_RESTRICTION, OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RANGE.getIRI(), RDFS_DATATYPE.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_SUBJECT, OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_PREDICATE, OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(DeprecatedVocabulary.OWL_OBJECT, OWL_ANNOTATED_TARGET.getIRI());
    }

    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(IRI.create(Namespaces.OWL2.toString(), v.getShortForm()), v.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL11.toString(), v.getShortForm()), v.getIRI());
    }

    /**
     * Gets the ontology.
     * 
     * @return the ontology
     */
    @Nonnull
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * Gets the ontology format.
     * 
     * @return the ontology format
     */
    @Nonnull
    public RDFDocumentFormat getOntologyFormat() {
        return verifyNotNull(ontologyFormat, "ontology format has not been set yet");
    }

    /**
     * Sets the ontology format.
     * 
     * @param format
     *        the new ontology format
     */
    public void setOntologyFormat(RDFDocumentFormat format) {
        ontologyFormat = format;
        if (ontologyFormat.isPrefixOWLOntologyFormat()) {
            tripleLogger.setPrefixManager(ontologyFormat.asPrefixOWLOntologyFormat());
        }
    }

    /**
     * Sets the expected axioms.
     * 
     * @param expectedAxioms
     *        the new expected axioms
     */
    public void setExpectedAxioms(int expectedAxioms) {
        this.expectedAxioms = expectedAxioms;
    }

    /**
     * Gets the data factory.
     * 
     * @return the data factory
     */
    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }

    // We cache IRIs to save memory!!
    private final Map<String, IRI> IRIMap = CollectionFactory.createMap();

    /**
     * Gets any annotations that were translated since the last call of this
     * method (calling this method clears the current pending annotations).
     * 
     * @return The set (possibly empty) of pending annotations.
     */
    @Nonnull
    public Set<OWLAnnotation> getPendingAnnotations() {
        Set<OWLAnnotation> annos = new LinkedHashSet<>(pendingAnnotations);
        pendingAnnotations.clear();
        return annos;
    }

    /**
     * Sets the pending annotations.
     * 
     * @param annotations
     *        the new pending annotations
     */
    protected void addPendingAnnotations(Set<OWLAnnotation> annotations) {
        pendingAnnotations.addAll(annotations);
    }

    @Nullable
    private IRI getIRINullable(@Nullable String s) {
        if (s == null) {
            return null;
        }
        return getIRI(s);
    }

    @Nonnull
    private IRI getIRI(@Nonnull String s) {
        // if (s == null) {
        // return null;
        // }
        checkNotNull(s, "s cannot be null");
        IRI iri = null;
        if (iriProvider != null) {
            iri = iriProvider.getIRI(s);
        }
        if (iri != null) {
            return iri;
        }
        iri = IRIMap.get(s);
        if (iri == null) {
            iri = IRI.create(s);
            IRIMap.put(s, iri);
        }
        return iri;
    }

    /** Imports closure changed. */
    protected final void importsClosureChanged() {
        // NOTE: This method only gets called when the ontology being parsed
        // adds a direct import. This is enough
        // for resolving the imports closure.
        // We cache IRIs of various entities here.
        // We also mop up any triples that weren't parsed and consumed in the
        // imports closure.
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            for (OWLAnnotationProperty prop : ont.getAnnotationPropertiesInSignature(EXCLUDED)) {
                annotationPropertyIRIs.add(prop.getIRI());
            }
            for (OWLDataProperty prop : ont.getDataPropertiesInSignature()) {
                dataPropertyExpressionIRIs.add(prop.getIRI());
            }
            for (OWLObjectProperty prop : ont.getObjectPropertiesInSignature()) {
                objectPropertyExpressionIRIs.add(prop.getIRI());
            }
            for (OWLClass cls : ont.getClassesInSignature()) {
                classExpressionIRIs.add(cls.getIRI());
            }
            for (OWLDatatype datatype : ont.getDatatypesInSignature()) {
                dataRangeIRIs.add(datatype.getIRI());
            }
            for (OWLNamedIndividual ind : ont.getIndividualsInSignature()) {
                individualIRIs.add(ind.getIRI());
            }
        }
    }

    @Override
    public boolean isAnonymousNode(String iri) {
        return nodeCheckerDelegate.isAnonymousNode(iri);
    }

    @Override
    public boolean isAnonymousSharedNode(String iri) {
        return nodeCheckerDelegate.isAnonymousSharedNode(iri);
    }

    @Override
    public boolean isAnonymousNode(IRI iri) {
        return nodeCheckerDelegate.isAnonymousNode(iri);
    }

    /**
     * Adds the shared anonymous node.
     * 
     * @param iri
     *        the iri
     * @param translation
     *        the translation
     */
    protected void addSharedAnonymousNode(IRI iri, Object translation) {
        sharedAnonymousNodes.put(iri, translation);
    }

    /**
     * Gets the shared anonymous node.
     * 
     * @param iri
     *        the iri
     * @return the shared anonymous node
     */
    protected Object getSharedAnonymousNode(IRI iri) {
        return sharedAnonymousNodes.get(iri);
    }

    private int lastPercentParsed = 0;

    /**
     * Adds the axiom.
     * 
     * @param axiom
     *        the axiom
     */
    protected void addAxiom(OWLAxiom axiom) {
        if (expectedAxioms > 0) {
            parsedAxioms++;
            int percentParsed = (int) (parsedAxioms * 100.0 / expectedAxioms);
            if (lastPercentParsed != percentParsed) {
                lastPercentParsed = percentParsed;
            }
        }
        if (axiom.isAnnotationAxiom()) {
            if (configuration.isLoadAnnotationAxioms()) {
                parsedAnnotationAxioms.add((OWLAnnotationAxiom) axiom);
            }
        } else {
            owlOntologyManager.addAxiom(ontology, axiom);
        }
        lastAddedAxiom = axiom;
    }

    /**
     * Marks an axioms for removal at the end of parsing. This is usually used
     * for annotated axioms, since the RDF serialization spec mandates that a
     * "base" triple must be included on serialization.
     * 
     * @param axiom
     *        The axiom to be removed.
     */
    protected void removeAxiom(OWLAxiom axiom) {
        axiomsToBeRemoved.add(axiom);
    }

    /**
     * Check for and process annotated declaration.
     * 
     * @param mainNode
     *        the main node
     */
    protected void checkForAndProcessAnnotatedDeclaration(IRI mainNode) {
        IRI annotatedPropertyObject = getResourceObject(mainNode, OWL_ANNOTATED_PROPERTY, false);
        if (annotatedPropertyObject == null) {
            return;
        }
        boolean rdfTypePredicate = annotatedPropertyObject.equals(RDF_TYPE.getIRI());
        if (!rdfTypePredicate) {
            return;
        }
        IRI annotatedTargetObject = getResourceObject(mainNode, OWL_ANNOTATED_TARGET, false);
        if (annotatedTargetObject == null) {
            return;
        }
        IRI annotatedSubjectObject = getResourceObject(mainNode, OWL_ANNOTATED_SOURCE, false);
        if (annotatedSubjectObject == null) {
            return;
        }
        boolean isEntityType = isEntityTypeIRI(annotatedTargetObject);
        if (isEntityType) {
            // This will add and record the declaration for us
            handlerAccessor.handle(annotatedSubjectObject, annotatedPropertyObject, annotatedTargetObject);
        }
    }

    /**
     * Determines if the specified IRI is an IRI corresponding to owl:Class,
     * owl:DatatypeProperty, rdfs:Datatype, owl:ObjectProperty,
     * owl:AnnotationProperty, or owl:NamedIndividual.
     * 
     * @param iri
     *        The IRI to check
     * @return {@code true} if the IRI corresponds to a built in OWL entity IRI
     *         otherwise {@code false}.
     */
    private static boolean isEntityTypeIRI(IRI iri) {
        return iri.equals(OWL_CLASS.getIRI()) || iri.equals(OWL_OBJECT_PROPERTY.getIRI()) || iri.equals(
            OWL_DATA_PROPERTY.getIRI()) || iri.equals(OWL_ANNOTATION_PROPERTY.getIRI()) || iri.equals(RDFS_DATATYPE
                .getIRI()) || iri.equals(OWL_NAMED_INDIVIDUAL.getIRI());
    }

    /**
     * Apply change.
     * 
     * @param change
     *        the change
     */
    protected void applyChange(@Nonnull OWLOntologyChange change) {
        owlOntologyManager.applyChange(change);
    }

    /**
     * Sets the ontology id.
     * 
     * @param ontologyID
     *        the new ontology id
     */
    protected void setOntologyID(@Nonnull OWLOntologyID ontologyID) {
        applyChange(new SetOntologyID(ontology, ontologyID));
    }

    /**
     * Adds the ontology annotation.
     * 
     * @param annotation
     *        the annotation
     */
    protected void addOntologyAnnotation(@Nonnull OWLAnnotation annotation) {
        applyChange(new AddOntologyAnnotation(ontology, annotation));
    }

    /**
     * Adds the import.
     * 
     * @param declaration
     *        the declaration
     */
    protected void addImport(@Nonnull OWLImportsDeclaration declaration) {
        applyChange(new AddImport(ontology, declaration));
    }

    /**
     * Gets the last added axiom.
     * 
     * @return the last added axiom
     */
    public OWLAxiom getLastAddedAxiom() {
        return lastAddedAxiom;
    }

    /**
     * Checks if is individual.
     * 
     * @param iri
     *        the iri
     * @return true, if is individual
     */
    protected boolean isIndividual(IRI iri) {
        return individualIRIs.contains(iri);
    }

    /**
     * Adds the rdf property.
     * 
     * @param iri
     *        the iri
     */
    protected void addRDFProperty(IRI iri) {
        propertyIRIs.add(iri);
    }

    /**
     * Checks if is rDF property.
     * 
     * @param iri
     *        the iri
     * @return true, if is rDF property
     */
    protected boolean isRDFProperty(IRI iri) {
        return propertyIRIs.contains(iri);
    }

    /**
     * Adds the class expression.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    public void addClassExpression(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLClass.class, explicitlyTyped);
        addType(iri, classExpressionIRIs, explicitlyTyped);
    }

    private void updateGuesses(IRI iri, Class<?> class1, boolean explicitlyTyped) {
        if (explicitlyTyped && guessedDeclarations.containsKey(iri)) {
            // if an explicitly typed declaration has been added and there was a
            // guess for its type, replace it
            // Do not replace all guesses, as these might be due to punning
            guessedDeclarations.remove(iri, class1);
        }
        if (!explicitlyTyped) {
            guessedDeclarations.put(iri, class1);
        }
    }

    /**
     * Checks if is class expression.
     * 
     * @param iri
     *        the iri
     * @return true, if is class expression
     */
    public boolean isClassExpression(IRI iri) {
        return classExpressionIRIs.contains(iri);
    }

    /**
     * Adds the object property.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    public void addObjectProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLObjectProperty.class, explicitlyTyped);
        addType(iri, objectPropertyExpressionIRIs, explicitlyTyped);
    }

    /**
     * Adds the data property.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    public void addDataProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLDataProperty.class, explicitlyTyped);
        addType(iri, dataPropertyExpressionIRIs, explicitlyTyped);
    }

    /**
     * Adds the annotation property.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    protected void addAnnotationProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLAnnotationProperty.class, explicitlyTyped);
        addType(iri, annotationPropertyIRIs, explicitlyTyped);
    }

    /**
     * Adds the data range.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    public void addDataRange(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLDataRange.class, explicitlyTyped);
        addType(iri, dataRangeIRIs, explicitlyTyped);
    }

    /**
     * Adds the owl named individual.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly type
     */
    protected void addOWLNamedIndividual(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLNamedIndividual.class, explicitlyTyped);
        addType(iri, individualIRIs, explicitlyTyped);
    }

    /**
     * Adds the owl restriction.
     * 
     * @param iri
     *        the iri
     * @param explicitlyTyped
     *        the explicitly typed
     */
    protected void addOWLRestriction(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLClassExpression.class, explicitlyTyped);
        addType(iri, restrictionIRIs, explicitlyTyped);
    }

    private void addType(IRI iri, Set<IRI> types, boolean explicitlyTyped) {
        if (configuration.isStrict() && !explicitlyTyped) {
            LOGGER.warn("STRICT: Not adding implicit type iri={} types={}", iri, types);
            return;
        }
        types.add(iri);
    }

    /**
     * Checks if is restriction.
     * 
     * @param iri
     *        the iri
     * @return true, if is restriction
     */
    public boolean isRestriction(IRI iri) {
        return restrictionIRIs.contains(iri);
    }

    /**
     * Adds the annotation iri.
     * 
     * @param iri
     *        the iri
     */
    protected void addAnnotationIRI(IRI iri) {
        annotationIRIs.add(iri);
    }

    /**
     * Checks if is annotation.
     * 
     * @param iri
     *        the iri
     * @return true, if is annotation
     */
    protected boolean isAnnotation(IRI iri) {
        return annotationIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently an object property IRI and not a
     * data property IRI and not an annotation property IRI. Note that this
     * method is only guaranteed to return the same value once all triples in
     * the imports closure of the RDF graph being parsed have been parsed.
     * 
     * @param iri
     *        The IRI to check.
     * @return {@code true} if the IRI is an object property IRI and not a data
     *         property IRI and not an annotation property IRI. Otherwise,
     *         {@code false}.
     */
    protected boolean isObjectPropertyOnly(IRI iri) {
        return iri != null && !dataPropertyExpressionIRIs.contains(iri) && !annotationPropertyIRIs.contains(iri)
            && objectPropertyExpressionIRIs.contains(iri);
    }

    /**
     * Checks if is object property.
     * 
     * @param iri
     *        the iri
     * @return true, if is object property
     */
    protected boolean isObjectProperty(IRI iri) {
        return objectPropertyExpressionIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently a data property IRI and not an
     * object property IRI and not an annotation property IRI. Note that this
     * method is only guaranteed to return the same value once all triples in
     * the imports closure of the RDF graph being parsed have been parsed.
     * 
     * @param iri
     *        The IRI to check.
     * @return {@code true} if the IRI is a data property IRI and not an object
     *         property IRI and not an annotation property IRI. Otherwise,
     *         {@code false}.
     */
    protected boolean isDataPropertyOnly(IRI iri) {
        return iri != null && !objectPropertyExpressionIRIs.contains(iri) && !annotationPropertyIRIs.contains(iri)
            && dataPropertyExpressionIRIs.contains(iri);
    }

    /**
     * Checks if is data property.
     * 
     * @param iri
     *        the iri
     * @return true, if is data property
     */
    protected boolean isDataProperty(IRI iri) {
        return dataPropertyExpressionIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently an annotation property IRI and not
     * a data property IRI and not an object property IRI. Note that this method
     * is only guaranteed to return the same value once all triples in the
     * imports closure of the RDF graph being parsed have been parsed.
     * 
     * @param iri
     *        The IRI to check.
     * @return {@code true} if the IRI is an annotation property IRI and not a
     *         data property IRI and not an object property IRI. Otherwise,
     *         {@code false}.
     */
    protected boolean isAnnotationPropertyOnly(IRI iri) {
        return iri != null && !objectPropertyExpressionIRIs.contains(iri) && !dataPropertyExpressionIRIs.contains(iri)
            && annotationPropertyIRIs.contains(iri);
    }

    /**
     * Checks if is annotation property.
     * 
     * @param iri
     *        the iri
     * @return true, if is annotation property
     */
    protected boolean isAnnotationProperty(IRI iri) {
        return annotationPropertyIRIs.contains(iri);
    }

    /**
     * Checks if is ontology.
     * 
     * @param iri
     *        the iri
     * @return true, if is ontology
     */
    protected boolean isOntology(IRI iri) {
        return ontologyIRIs.contains(iri);
    }

    /**
     * Gets the oWL ontology manager.
     * 
     * @return the oWL ontology manager
     */
    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }

    /**
     * Records an annotation of an anonymous node (either an annotation of an
     * annotation, or an annotation of an axiom for example).
     * 
     * @param annotatedAnonSource
     *        The source that the annotation annotates
     * @param annotationMainNode
     *        The annotations
     */
    public void addAnnotatedSource(IRI annotatedAnonSource, IRI annotationMainNode) {
        Set<IRI> annotationMainNodes = annotatedAnonSource2AnnotationMap.get(annotatedAnonSource);
        if (annotationMainNodes == null) {
            annotationMainNodes = new HashSet<>();
            annotatedAnonSource2AnnotationMap.put(annotatedAnonSource, annotationMainNodes);
        }
        annotationMainNodes.add(annotationMainNode);
    }

    /**
     * Gets the main nodes of annotations that annotated the specified source.
     * 
     * @param source
     *        The source (axiom or annotation main node)
     * @return The set of main nodes that annotate the specified source
     */
    public Set<IRI> getAnnotatedSourceAnnotationMainNodes(IRI source) {
        Set<IRI> mainNodes = annotatedAnonSource2AnnotationMap.get(source);
        if (mainNodes != null) {
            return mainNodes;
        } else {
            return CollectionFactory.emptySet();
        }
    }

    // Helper methods for creating entities
    /**
     * Gets the oWL class.
     * 
     * @param iri
     *        the iri
     * @return the oWL class
     */
    @Nonnull
    protected OWLClass getOWLClass(@Nonnull IRI iri) {
        return getDataFactory().getOWLClass(iri);
    }

    /**
     * Gets the oWL object property.
     * 
     * @param iri
     *        the iri
     * @return the oWL object property
     */
    protected OWLObjectProperty getOWLObjectProperty(@Nonnull IRI iri) {
        return getDataFactory().getOWLObjectProperty(iri);
    }

    /**
     * Gets the oWL data property.
     * 
     * @param iri
     *        the iri
     * @return the oWL data property
     */
    protected OWLDataProperty getOWLDataProperty(@Nonnull IRI iri) {
        return getDataFactory().getOWLDataProperty(iri);
    }

    /**
     * Gets the oWL individual.
     * 
     * @param iri
     *        the iri
     * @return the oWL individual
     */
    @Nonnull
    protected OWLIndividual getOWLIndividual(@Nonnull IRI iri) {
        if (isAnonymousNode(iri)) {
            return getOWLAnonymousIndividual(iri.toString());
        } else {
            return dataFactory.getOWLNamedIndividual(iri);
        }
    }

    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        return anonProvider.getOWLAnonymousIndividual(nodeId);
    }

    /**
     * Consume triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    protected void consumeTriple(IRI subject, IRI predicate, IRI object) {
        LOGGER.trace("consuming triple");
        tripleLogger.justLog(subject, predicate, object);
        isTriplePresent(subject, predicate, object, true);
    }

    /**
     * Consume triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param con
     *        the con
     */
    protected void consumeTriple(IRI subject, IRI predicate, OWLLiteral con) {
        LOGGER.trace("consuming triple");
        tripleLogger.justLog(subject, predicate, con);
        isTriplePresent(subject, predicate, con, true);
    }

    // SWRL Stuff
    /**
     * Adds the swrl rule.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLRule(IRI iri) {
        swrlRules.add(iri);
    }

    /**
     * Checks if is sWRL rule.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL rule
     */
    protected boolean isSWRLRule(IRI iri) {
        return swrlRules.contains(iri);
    }

    /**
     * Adds the swrl individual property atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLIndividualPropertyAtom(IRI iri) {
        swrlIndividualPropertyAtoms.add(iri);
    }

    /**
     * Checks if is sWRL individual property atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL individual property atom
     */
    protected boolean isSWRLIndividualPropertyAtom(IRI iri) {
        return swrlIndividualPropertyAtoms.contains(iri);
    }

    /**
     * Adds the swrl data property atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLDataPropertyAtom(IRI iri) {
        swrlDataValuedPropertyAtoms.add(iri);
    }

    /**
     * Checks if is sWRL data valued property atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL data valued property atom
     */
    protected boolean isSWRLDataValuedPropertyAtom(IRI iri) {
        return swrlDataValuedPropertyAtoms.contains(iri);
    }

    /**
     * Adds the swrl class atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLClassAtom(IRI iri) {
        swrlClassAtoms.add(iri);
    }

    /**
     * Checks if is sWRL class atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL class atom
     */
    protected boolean isSWRLClassAtom(IRI iri) {
        return swrlClassAtoms.contains(iri);
    }

    /**
     * Adds the swrl same as atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLSameAsAtom(IRI iri) {
        swrlSameAsAtoms.add(iri);
    }

    /**
     * Checks if is sWRL same as atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL same as atom
     */
    protected boolean isSWRLSameAsAtom(IRI iri) {
        return swrlSameAsAtoms.contains(iri);
    }

    /**
     * Adds the swrl different from atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLDifferentFromAtom(IRI iri) {
        swrlDifferentFromAtoms.add(iri);
    }

    /**
     * Checks if is sWRL different from atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL different from atom
     */
    protected boolean isSWRLDifferentFromAtom(IRI iri) {
        return swrlDifferentFromAtoms.contains(iri);
    }

    /**
     * Adds the swrl data range atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLDataRangeAtom(IRI iri) {
        swrlDataRangeAtoms.add(iri);
    }

    /**
     * Checks if is sWRL data range atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL data range atom
     */
    protected boolean isSWRLDataRangeAtom(IRI iri) {
        return swrlDataRangeAtoms.contains(iri);
    }

    /**
     * Adds the swrl built in atom.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLBuiltInAtom(IRI iri) {
        swrlBuiltInAtoms.add(iri);
    }

    /**
     * Checks if is sWRL built in atom.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL built in atom
     */
    protected boolean isSWRLBuiltInAtom(IRI iri) {
        return swrlBuiltInAtoms.contains(iri);
    }

    /**
     * Adds the swrl variable.
     * 
     * @param iri
     *        the iri
     */
    protected void addSWRLVariable(IRI iri) {
        swrlVariables.add(iri);
    }

    /**
     * Checks if is sWRL variable.
     * 
     * @param iri
     *        the iri
     * @return true, if is sWRL variable
     */
    protected boolean isSWRLVariable(IRI iri) {
        return swrlVariables.contains(iri);
    }

    // RDFConsumer implementation
    private static void printTriple(Object subject, Object predicate, Object object) {
        LOGGER.info("Unparsed triple: {} -> {} -> {}", subject, predicate, object);
    }

    /** Dump remaining triples. */
    protected void dumpRemainingTriples() {
        // if info logging is disabled or all collections are empty, do not
        // output anything
        if (LOGGER.isInfoEnabled() && singleValuedResTriplesByPredicate.size() + singleValuedLitTriplesByPredicate
            .size() + resTriplesBySubject.size() + litTriplesBySubject.size() > 0) {
            for (IRI predicate : singleValuedResTriplesByPredicate.keySet()) {
                Map<IRI, IRI> map = singleValuedResTriplesByPredicate.get(predicate);
                for (IRI subject : map.keySet()) {
                    IRI object = map.get(subject);
                    printTriple(subject, predicate, object);
                }
            }
            for (IRI predicate : singleValuedLitTriplesByPredicate.keySet()) {
                Map<IRI, OWLLiteral> map = singleValuedLitTriplesByPredicate.get(predicate);
                for (IRI subject : map.keySet()) {
                    OWLLiteral object = map.get(subject);
                    printTriple(subject, predicate, object);
                }
            }
            for (IRI subject : new ArrayList<>(resTriplesBySubject.keySet())) {
                Map<IRI, Collection<IRI>> map = resTriplesBySubject.get(subject);
                for (IRI predicate : new ArrayList<>(map.keySet())) {
                    Collection<IRI> objects = map.get(predicate);
                    for (IRI object : objects) {
                        printTriple(subject, predicate, object);
                    }
                }
            }
            for (IRI subject : new ArrayList<>(litTriplesBySubject.keySet())) {
                Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
                for (IRI predicate : new ArrayList<>(map.keySet())) {
                    Collection<OWLLiteral> objects = map.get(predicate);
                    for (OWLLiteral object : objects) {
                        printTriple(subject, predicate, object);
                    }
                }
            }
        }
    }

    @Override
    public void startModel(IRI physicalURI) {}

    /**
     * Checks if is parsed all triples.
     * 
     * @return true, if is parsed all triples
     */
    public boolean isParsedAllTriples() {
        return parsedAllTriples;
    }

    @Override
    public void endModel() {
        parsedAllTriples = true;
        // We are now left with triples that could not be consumed during
        // streaming parsing
        IRIMap.clear();
        tripleLogger.logNumberOfTriples();
        translatorAccessor.consumeSWRLRules(swrlRules);
        Set<RDFTriple> remainingTriples = handlerAccessor.mopUp();
        if (ontologyFormat != null) {
            RDFParserMetaData metaData = new RDFParserMetaData(RDFOntologyHeaderStatus.PARSED_ONE_HEADER, tripleLogger
                .count(), remainingTriples, guessedDeclarations);
            ontologyFormat.setOntologyLoaderMetaData(metaData);
        }
        // Do we need to change the ontology IRI?
        chooseAndSetOntologyIRI();
        TripleLogger.logOntologyID(ontology.getOntologyID());
        dumpRemainingTriples();
        cleanup();
        addAnnotationAxioms();
        removeAxiomsScheduledForRemoval();
    }

    private void addAnnotationAxioms() {
        for (OWLAxiom axiom : parsedAnnotationAxioms) {
            assert axiom != null;
            owlOntologyManager.addAxiom(ontology, axiom);
        }
    }

    private void removeAxiomsScheduledForRemoval() {
        for (OWLAxiom axiom : axiomsToBeRemoved) {
            assert axiom != null;
            owlOntologyManager.removeAxiom(ontology, axiom);
        }
    }

    /**
     * Selects an IRI that should be used as the IRI of the parsed ontology, or
     * {@code null} if the parsed ontology does not have an IRI
     */
    private void chooseAndSetOntologyIRI() {
        Optional<IRI> ontologyIRIToSet = Optional.absent();
        if (ontologyIRIs.isEmpty()) {
            // No ontology IRIs
            // We used to use the xml:base here. But this is probably incorrect
            // for OWL 2 now.
        } else if (ontologyIRIs.size() == 1) {
            // Exactly one ontologyIRI
            IRI ontologyIRI = ontologyIRIs.iterator().next();
            assert ontologyIRI != null;
            if (!isAnonymousNode(ontologyIRI)) {
                ontologyIRIToSet = Optional.of(ontologyIRI);
            }
        } else {
            // We have multiple to choose from
            // Choose one that isn't the object of an annotation assertion
            Set<IRI> candidateIRIs = new HashSet<>(ontologyIRIs);
            for (OWLAnnotation anno : ontology.getAnnotations()) {
                if (anno.getValue() instanceof IRI) {
                    IRI iri = (IRI) anno.getValue();
                    if (ontologyIRIs.contains(iri)) {
                        candidateIRIs.remove(iri);
                    }
                }
            }
            // Choose the first one parsed
            if (candidateIRIs.contains(firstOntologyIRI)) {
                ontologyIRIToSet = Optional.of(firstOntologyIRI);
            } else if (!candidateIRIs.isEmpty()) {
                // Just pick any
                ontologyIRIToSet = Optional.of(candidateIRIs.iterator().next());
            }
        }
        if (ontologyIRIToSet.isPresent() && !NodeID.isAnonymousNodeIRI(ontologyIRIToSet.get())) {
            Optional<IRI> versionIRI = ontology.getOntologyID().getVersionIRI();
            OWLOntologyID ontologyID = new OWLOntologyID(ontologyIRIToSet, versionIRI);
            applyChange(new SetOntologyID(ontology, ontologyID));
        }
    }

    private void cleanup() {
        classExpressionIRIs.clear();
        objectPropertyExpressionIRIs.clear();
        dataPropertyExpressionIRIs.clear();
        dataRangeIRIs.clear();
        restrictionIRIs.clear();
        listFirstLiteralTripleMap.clear();
        listFirstResourceTripleMap.clear();
        listRestTripleMap.clear();
        // XXX clean new members
        translatorAccessor.cleanup();
        resTriplesBySubject.clear();
        litTriplesBySubject.clear();
        singleValuedLitTriplesByPredicate.clear();
        singleValuedResTriplesByPredicate.clear();
        guessedDeclarations.clear();
    }

    @Override
    public void includeModel(String logicalURI, String physicalURI) {
        // XXX should this do nothing?
    }

    @Override
    public void logicalURI(IRI logicalURI) {
        // XXX what is the purpose of this?
    }

    /**
     * Gets the synonym.
     * 
     * @param original
     *        the original
     * @return the synonym
     */
    @Nonnull
    protected IRI getSynonym(@Nonnull IRI original) {
        if (!configuration.isStrict()) {
            IRI synonymIRI = synonymMap.get(original);
            if (synonymIRI != null) {
                return synonymIRI;
            }
        }
        return original;
    }

    @Override
    public void statementWithLiteralValue(@Nonnull String subject, @Nonnull String predicate, @Nonnull String object,
        @Nullable String language, @Nullable String datatype) {
        tripleLogger.logTriple(subject, predicate, object, language, datatype);
        IRI subjectIRI = getIRI(remapOnlyIfRemapped(subject));
        IRI predicateIRI = getIRI(predicate);
        predicateIRI = getSynonym(predicateIRI);
        handlerAccessor.handleStreaming(subjectIRI, predicateIRI, object, getIRINullable(datatype), language);
    }

    @Override
    public void statementWithLiteralValue(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull String object,
        String language, IRI datatype) {
        tripleLogger.logTriple(subject, predicate, object, language, datatype);
        handlerAccessor.handleStreaming(subject, getSynonym(predicate), object, datatype, language);
    }

    @Override
    public void statementWithResourceValue(@Nonnull String subject, @Nonnull String predicate, @Nonnull String object) {
        tripleLogger.logTriple(subject, predicate, object);
        IRI subjectIRI = getIRI(subject);
        IRI predicateIRI = getIRI(predicate);
        predicateIRI = getSynonym(predicateIRI);
        IRI objectIRI = getSynonym(getIRI(object));
        handlerAccessor.handleStreaming(subjectIRI, predicateIRI, objectIRI);
    }

    @Override
    public void statementWithResourceValue(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull IRI object) {
        tripleLogger.logTriple(subject, predicate, object);
        handlerAccessor.handleStreaming(subject, getSynonym(predicate), getSynonym(object));
    }

    /**
     * A convenience method to obtain an {@code OWLLiteral}.
     * 
     * @param literal
     *        The literal
     * @param datatype
     *        The data type
     * @param lang
     *        The lang
     * @return The {@code OWLLiteral} (either typed or untyped depending on the
     *         params)
     */
    @Nonnull
        OWLLiteral getOWLLiteral(@Nonnull String literal, @Nullable IRI datatype, @Nullable String lang) {
        if (datatype != null) {
            return dataFactory.getOWLLiteral(literal, dataFactory.getOWLDatatype(datatype));
        } else {
            return dataFactory.getOWLLiteral(literal, lang);
        }
    }

    /**
     * compatibility proxy for TranslatorAccessor#translateClassExpression
     * 
     * @param i
     *        iri fr the class expression
     * @return translated class expression
     */
    @Nonnull
    public OWLClassExpression translateClassExpression(@Nonnull IRI i) {
        return translatorAccessor.translateClassExpression(i);
    }

    /**
     * Given a main node, translated data ranges according to Table 12.
     * 
     * @param mainNode
     *        The main node
     * @return The translated data range. If the data range could not be
     *         translated then an OWLDatatype with the given IRI is returned.
     */
    @Nonnull
    public OWLDataRange translateDataRange(@Nonnull IRI mainNode) {
        if (!isDataRange(mainNode) && configuration.isStrict()) {
            // Can't translated ANY according to Table 12
            return generateAndLogParseError(EntityType.DATATYPE, mainNode);
        }
        if (!isAnonymousNode(mainNode) && isDataRange(mainNode)) {
            return dataFactory.getOWLDatatype(mainNode);
        }
        IRI intersectionOfObject = getResourceObject(mainNode, OWL_INTERSECTION_OF, true);
        if (intersectionOfObject != null) {
            Set<OWLDataRange> dataRanges = translatorAccessor.translateToDataRangeSet(intersectionOfObject);
            return dataFactory.getOWLDataIntersectionOf(dataRanges);
        }
        IRI unionOfObject = getResourceObject(mainNode, OWL_UNION_OF, true);
        if (unionOfObject != null) {
            Set<OWLDataRange> dataRanges = translatorAccessor.translateToDataRangeSet(unionOfObject);
            return dataFactory.getOWLDataUnionOf(dataRanges);
        }
        // The plain complement of triple predicate is in here for legacy
        // reasons
        IRI complementOfObject = getResourceObject(mainNode, OWL_DATATYPE_COMPLEMENT_OF, true);
        if (!configuration.isStrict() && complementOfObject == null) {
            complementOfObject = getResourceObject(mainNode, OWL_COMPLEMENT_OF, true);
        }
        if (complementOfObject != null) {
            OWLDataRange operand = translateDataRange(complementOfObject);
            return dataFactory.getOWLDataComplementOf(operand);
        }
        IRI oneOfObject = getResourceObject(mainNode, OWL_ONE_OF, true);
        if (oneOfObject != null) {
            Set<OWLLiteral> literals = translatorAccessor.translateToConstantSet(oneOfObject);
            return dataFactory.getOWLDataOneOf(literals);
        }
        IRI onDatatypeObject = getResourceObject(mainNode, OWL_ON_DATA_TYPE, true);
        if (onDatatypeObject != null) {
            if (isAnonymousNode(onDatatypeObject)) {
                // TODO LOG ERROR
                return dataFactory.getOWLDatatype(mainNode);
            }
            OWLDatatype restrictedDataRange = (OWLDatatype) translateDataRange(onDatatypeObject);
            // Now we have to get the restricted facets - there is some legacy
            // translation code here... the current
            // spec uses a list of triples where the predicate is a facet and
            // the object a literal that is restricted
            // by the facet. Originally, there just used to be multiple
            // facet-"facet value" triples
            Set<OWLFacetRestriction> restrictions = new HashSet<>();
            IRI facetRestrictionList = getResourceObject(mainNode, OWL_WITH_RESTRICTIONS, true);
            if (facetRestrictionList != null) {
                restrictions = translatorAccessor.translateToFacetRestrictionSet(facetRestrictionList);
            } else if (!configuration.isStrict()) {
                // Try the legacy encoding
                for (IRI facetIRI : OWLFacet.FACET_IRIS) {
                    assert facetIRI != null;
                    OWLLiteral val;
                    while ((val = getLiteralObject(mainNode, facetIRI, true)) != null) {
                        restrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.getFacet(facetIRI), val));
                    }
                }
            }
            return dataFactory.getOWLDatatypeRestriction(restrictedDataRange, restrictions);
        }
        // Could not translated ANYTHING!
        return generateAndLogParseError(EntityType.DATATYPE, mainNode);
    }

    /**
     * Translate data property expression.
     * 
     * @param iri
     *        the iri
     * @return the oWL data property expression
     */
    @Nonnull
    public OWLDataPropertyExpression translateDataPropertyExpression(@Nonnull IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    // Basic node translation - translation of entities
    /** The translated properties. */
    private final Map<IRI, OWLObjectPropertyExpression> translatedProperties = new HashMap<>();

    /**
     * Translate object property expression.
     * 
     * @param mainNode
     *        the main node
     * @return the oWL object property expression
     */
    @Nonnull
    public OWLObjectPropertyExpression translateObjectPropertyExpression(@Nonnull IRI mainNode) {
        OWLObjectPropertyExpression prop = translatedProperties.get(mainNode);
        if (prop != null) {
            return prop;
        }
        if (!isAnonymousNode(mainNode)) {
            // Simple object property
            prop = dataFactory.getOWLObjectProperty(mainNode);
            translatedProperties.put(mainNode, prop);
        } else {
            // Inverse of a property expression
            IRI inverseOfObject = getResourceObject(mainNode, OWL_INVERSE_OF, true);
            if (inverseOfObject != null) {
                OWLObjectPropertyExpression otherProperty = translateObjectPropertyExpression(inverseOfObject);
                prop = dataFactory.getOWLObjectInverseOf(otherProperty);
            } else {
                prop = dataFactory.getOWLObjectInverseOf(dataFactory.getOWLObjectProperty(mainNode));
            }
            objectPropertyExpressionIRIs.add(mainNode);
            translatedProperties.put(mainNode, prop);
        }
        return prop;
    }

    /**
     * Translate individual.
     * 
     * @param node
     *        the node
     * @return the oWL individual
     */
    @Nonnull
    public OWLIndividual translateIndividual(@Nonnull IRI node) {
        return getOWLIndividual(node);
    }

    /**
     * Translates the annotation on a main node. Triples whose subject is the
     * specified main node and whose subject is typed an an annotation property
     * (or is a built in annotation property) will be translated to annotation
     * on this main node.
     * 
     * @param mainNode
     *        The main node
     * @return The set of annotations on the main node
     */
    @Nonnull
    public Set<OWLAnnotation> translateAnnotations(IRI mainNode) {
        // Are we the subject of an annotation? If so, we need to ensure that
        // the annotations annotate us. This
        // will only happen if we are an annotation!
        Map<IRI, Set<OWLAnnotation>> anns = new HashMap<>();
        Set<IRI> annotationMainNodes = getAnnotatedSourceAnnotationMainNodes(mainNode);
        if (!annotationMainNodes.isEmpty()) {
            for (IRI annotationMainNode : annotationMainNodes) {
                anns.put(annotationMainNode, translateAnnotations(annotationMainNode));
            }
        }
        Set<OWLAnnotation> mainNodeAnnotations = new HashSet<>();
        Set<IRI> predicates = getPredicatesBySubject(mainNode);
        for (IRI predicate : predicates) {
            assert predicate != null;
            if (isAnnotationProperty(predicate)) {
                mapAnnotation(mainNode, anns, mainNodeAnnotations, predicate);
            }
        }
        return mainNodeAnnotations;
    }

    protected void mapAnnotation(IRI mainNode, Map<IRI, Set<OWLAnnotation>> anns,
        Set<OWLAnnotation> mainNodeAnnotations, IRI predicate) {
        IRI resVal = getResourceObject(mainNode, predicate, true);
        while (resVal != null) {
            OWLAnnotationProperty prop = dataFactory.getOWLAnnotationProperty(predicate);
            OWLAnnotationValue val;
            if (isAnonymousNode(resVal)) {
                val = dataFactory.getOWLAnonymousIndividual(resVal.toString());
            } else {
                val = resVal;
            }
            IRI annotation = getSubjectForAnnotatedPropertyAndObject(mainNode, predicate, resVal);
            Set<OWLAnnotation> c = anns.get(annotation);
            mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop, val, c != null ? c
                : Collections.<OWLAnnotation> emptySet()));
            resVal = getResourceObject(mainNode, predicate, true);
        }
        OWLLiteral litVal = getLiteralObject(mainNode, predicate, true);
        while (litVal != null) {
            OWLAnnotationProperty prop = dataFactory.getOWLAnnotationProperty(predicate);
            IRI annotation = getSubjectForAnnotatedPropertyAndObject(mainNode, predicate, litVal);
            Set<OWLAnnotation> c = anns.get(annotation);
            mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop, litVal, c != null ? c
                : Collections.<OWLAnnotation> emptySet()));
            litVal = getLiteralObject(mainNode, predicate, true);
        }
    }

    private @Nullable IRI getSubjectForAnnotatedPropertyAndObject(IRI n, IRI p, OWLLiteral v) {
        for (IRI i : getAnnotatedSourceAnnotationMainNodes(n)) {
            if (p.equals(getResourceObject(i, OWL_ANNOTATED_PROPERTY, false)) && v.equals(getLiteralObject(i,
                OWL_ANNOTATED_TARGET, false))) {
                return i;
            }
        }
        return null;
    }

    private @Nullable IRI getSubjectForAnnotatedPropertyAndObject(IRI n, IRI p, IRI v) {
        for (IRI i : getAnnotatedSourceAnnotationMainNodes(n)) {
            if (p.equals(getResourceObject(i, OWL_ANNOTATED_PROPERTY, false)) && v.equals(getResourceObject(i,
                OWL_ANNOTATED_TARGET, false))) {
                return i;
            }
        }
        return null;
    }

    @Nonnull private static final AtomicInteger ERRORCOUNTER = new AtomicInteger(0);

    @Nonnull
    private <E extends OWLEntity> E getErrorEntity(@Nonnull EntityType<E> entityType) {
        IRI iri = IRI.create("http://org.semanticweb.owlapi/error#", "Error" + ERRORCOUNTER.incrementAndGet());
        LOGGER.error("Entity not properly recognized, missing triples in input? {} for type {}", iri, entityType);
        if (configuration.isStrict()) {
            throw new OWLParserException("Entity not properly recognized, missing triples in input? " + iri
                + " for type " + entityType);
        }
        return dataFactory.getOWLEntity(entityType, iri);
    }

    @Nonnull
    private RDFResource getRDFResource(@Nonnull IRI iri) {
        if (isAnonymousNode(iri)) {
            return new RDFResourceBlankNode(iri, false, false);
        } else {
            return new RDFResourceIRI(iri);
        }
    }

    @Nonnull
    private RDFTriple getRDFTriple(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull IRI object) {
        return new RDFTriple(getRDFResource(subject), new RDFResourceIRI(predicate), getRDFResource(object));
    }

    @Nonnull
    private RDFTriple getRDFTriple(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull OWLLiteral object) {
        return new RDFTriple(getRDFResource(subject), new RDFResourceIRI(predicate), new RDFLiteral(object));
    }

    @Nonnull
    private Set<RDFTriple> getTriplesForMainNode(@Nonnull IRI mainNode, IRI... augmentingTypes) {
        Set<RDFTriple> triples = new HashSet<>();
        for (IRI predicate : getPredicatesBySubject(mainNode)) {
            assert predicate != null;
            for (IRI object : getResourceObjects(mainNode, predicate)) {
                assert object != null;
                triples.add(getRDFTriple(mainNode, predicate, object));
            }
            for (OWLLiteral object : getLiteralObjects(mainNode, predicate)) {
                assert object != null;
                triples.add(getRDFTriple(mainNode, predicate, object));
            }
        }
        for (IRI augmentingType : augmentingTypes) {
            assert augmentingType != null;
            triples.add(getRDFTriple(mainNode, OWLRDFVocabulary.RDF_TYPE.getIRI(), augmentingType));
        }
        return triples;
    }

    private void logError(RDFResourceParseError error) {
        ontologyFormat.addError(error);
    }

    /**
     * @param entityType
     *        entity type
     * @param mainNode
     *        main node
     * @param <E>
     *        entity type
     * @return error entity
     */
    @Nonnull
    public <E extends OWLEntity> E generateAndLogParseError(@Nonnull EntityType<E> entityType, @Nonnull IRI mainNode) {
        E entity = getErrorEntity(entityType);
        RDFResource mainNodeResource = getRDFResource(mainNode);
        Set<RDFTriple> mainNodeTriples = getTriplesForMainNode(mainNode);
        RDFResourceParseError error = new RDFResourceParseError(entity, mainNodeResource, mainNodeTriples);
        logError(error);
        return entity;
    }

    /**
     * Gets the predicates by subject.
     * 
     * @param subject
     *        the subject
     * @return the predicates by subject
     */
    protected Set<IRI> getPredicatesBySubject(IRI subject) {
        Set<IRI> iris = new HashSet<>();
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            iris.addAll(predObjMap.keySet());
        }
        Map<IRI, Collection<OWLLiteral>> predObjMapLit = litTriplesBySubject.get(subject);
        if (predObjMapLit != null) {
            iris.addAll(predObjMapLit.keySet());
        }
        return iris;
    }

    /**
     * Gets the resource object.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param consume
     *        the consume
     * @return the resource object
     */
    protected IRI getResourceObject(IRI subject, OWLRDFVocabulary predicate, boolean consume) {
        return getResourceObject(subject, predicate.getIRI(), consume);
    }

    /**
     * Gets the resource object.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param consume
     *        the consume
     * @return the resource object
     */
    protected IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<IRI> objects = predObjMap.get(predicate);
            if (objects != null && !objects.isEmpty()) {
                IRI object = objects.iterator().next();
                if (consume) {
                    objects.remove(object);
                }
                // if (objects.isEmpty()) {
                // predObjMap.remove(predicate);
                // if (predObjMap.isEmpty()) {
                // resTriplesBySubject.remove(subject);
                // }
                // }
                return object;
            }
        }
        return null;
    }

    /**
     * Gets the resource objects.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @return the resource objects
     */
    protected Set<IRI> getResourceObjects(IRI subject, IRI predicate) {
        Set<IRI> result = new HashSet<>();
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (obj != null) {
                result.add(obj);
            }
        }
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<IRI> objects = predObjMap.get(predicate);
            if (objects != null) {
                result.addAll(objects);
            }
        }
        return result;
    }

    /**
     * Gets the literal object.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param consume
     *        the consume
     * @return the literal object
     */
    protected OWLLiteral getLiteralObject(IRI subject, OWLRDFVocabulary predicate, boolean consume) {
        return getLiteralObject(subject, predicate.getIRI(), consume);
    }

    /**
     * Gets the literal object.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param consume
     *        the consume
     * @return the literal object
     */
    protected OWLLiteral getLiteralObject(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<OWLLiteral> objects = predObjMap.get(predicate);
            if (objects != null && !objects.isEmpty()) {
                OWLLiteral object = objects.iterator().next();
                if (consume) {
                    objects.remove(object);
                }
                if (objects.isEmpty()) {
                    predObjMap.remove(predicate);
                }
                return object;
            }
        }
        return null;
    }

    /**
     * Gets the literal objects.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @return the literal objects
     */
    protected Set<OWLLiteral> getLiteralObjects(IRI subject, IRI predicate) {
        Set<OWLLiteral> result = new HashSet<>();
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (obj != null) {
                result.add(obj);
            }
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<OWLLiteral> objects = predObjMap.get(predicate);
            if (objects != null) {
                result.addAll(objects);
            }
        }
        return result;
    }

    /**
     * Checks if is triple present.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     * @param consume
     *        the consume
     * @return true, if is triple present
     */
    protected boolean isTriplePresent(IRI subject, IRI predicate, IRI object, boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<IRI> objects = predObjMap.get(predicate);
            if (objects != null) {
                if (objects.contains(object)) {
                    if (consume) {
                        objects.remove(object);
                        // if (objects.isEmpty()) {
                        // predObjMap.remove(predicate);
                        // if (predObjMap.isEmpty()) {
                        // resTriplesBySubject.remove(subject);
                        // }
                        // }
                    }
                    return true;
                }
                return false;
            }
        }
        return false;// searchGeneralResourceTriples(subject, predicate, object,
                     // consume) != null;
    }

    /**
     * Checks if is general predicate.
     * 
     * @param predicate
     *        the predicate
     * @return true, if is general predicate
     */
    protected static boolean isGeneralPredicate(IRI predicate) {
        return !predicate.isReservedVocabulary() || OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS.contains(
            predicate) || Namespaces.SWRL.inNamespace(predicate) || Namespaces.SWRLB.inNamespace(predicate);
    }

    /**
     * Checks if is triple present.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     * @param consume
     *        the consume
     * @return true, if is triple present
     */
    protected boolean isTriplePresent(IRI subject, IRI predicate, OWLLiteral object, boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Collection<OWLLiteral> objects = predObjMap.get(predicate);
            if (objects != null) {
                if (objects.contains(object)) {
                    if (consume) {
                        objects.remove(object);
                        if (objects.isEmpty()) {
                            predObjMap.remove(predicate);
                            if (predObjMap.isEmpty()) {
                                litTriplesBySubject.remove(subject);
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Checks for predicate.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @return true, if successful
     */
    protected boolean hasPredicate(IRI subject, IRI predicate) {
        Map<IRI, IRI> resPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (resPredMap != null) {
            return resPredMap.containsKey(subject);
        }
        Map<IRI, OWLLiteral> litPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (litPredMap != null) {
            return litPredMap.containsKey(subject);
        }
        Map<IRI, Collection<IRI>> resPredObjMap = resTriplesBySubject.get(subject);
        if (resPredObjMap != null) {
            boolean b = resPredObjMap.containsKey(predicate);
            if (b) {
                return true;
            }
        }
        Map<IRI, Collection<OWLLiteral>> litPredObjMap = litTriplesBySubject.get(subject);
        if (litPredObjMap != null) {
            return litPredObjMap.containsKey(predicate);
        }
        return false;
    }

    /**
     * Adds the rest.
     * 
     * @param subject
     *        the subject
     * @param object
     *        the object
     */
    protected void addRest(IRI subject, IRI object) {
        listRestTripleMap.put(subject, object);
    }

    /**
     * Adds the first.
     * 
     * @param subject
     *        the subject
     * @param object
     *        the object
     */
    protected void addFirst(IRI subject, IRI object) {
        listFirstResourceTripleMap.put(subject, object);
    }

    /**
     * Gets the first resource.
     * 
     * @param subject
     *        the subject
     * @param consume
     *        the consume
     * @return the first resource
     */
    protected IRI getFirstResource(IRI subject, boolean consume) {
        if (consume) {
            return listFirstResourceTripleMap.remove(subject);
        } else {
            return listFirstResourceTripleMap.get(subject);
        }
    }

    /**
     * Gets the first literal.
     * 
     * @param subject
     *        the subject
     * @return the first literal
     */
    protected OWLLiteral getFirstLiteral(IRI subject) {
        return listFirstLiteralTripleMap.get(subject);
    }

    /**
     * Gets the rest.
     * 
     * @param subject
     *        the subject
     * @param consume
     *        the consume
     * @return the rest
     */
    protected IRI getRest(IRI subject, boolean consume) {
        if (consume) {
            return listRestTripleMap.remove(subject);
        } else {
            return listRestTripleMap.get(subject);
        }
    }

    /**
     * Adds the first.
     * 
     * @param subject
     *        the subject
     * @param object
     *        the object
     */
    protected void addFirst(IRI subject, OWLLiteral object) {
        listFirstLiteralTripleMap.put(subject, object);
    }

    /**
     * Adds the ontology.
     * 
     * @param iri
     *        the iri
     */
    protected void addOntology(IRI iri) {
        if (ontologyIRIs.isEmpty()) {
            firstOntologyIRI = iri;
        }
        ontologyIRIs.add(iri);
    }

    /**
     * Gets the ontologies.
     * 
     * @return the ontologies
     */
    protected Set<IRI> getOntologies() {
        return ontologyIRIs;
    }

    /**
     * Adds the axiom.
     * 
     * @param axiomIRI
     *        the axiom iri
     */
    protected void addAxiom(IRI axiomIRI) {
        axioms.add(axiomIRI);
    }

    /**
     * Checks if is axiom.
     * 
     * @param iri
     *        the iri
     * @return true, if is axiom
     */
    protected boolean isAxiom(IRI iri) {
        return axioms.contains(iri);
    }

    /**
     * Checks if is data range.
     * 
     * @param iri
     *        the iri
     * @return true, if is data range
     */
    protected boolean isDataRange(IRI iri) {
        return dataRangeIRIs.contains(iri);
    }

    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    @Nonnull
    protected OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
    }

    // Triple Stuff
    /**
     * Iterate resource triples.
     * 
     * @param iterator
     *        the iterator
     */
    protected void iterateResourceTriples(ResourceTripleIterator iterator) {
        for (IRI subject : resTriplesBySubject.keySet()) {
            Map<IRI, Collection<IRI>> map = resTriplesBySubject.get(subject);
            if (map == null) {
                continue;
            }
            for (IRI predicate : map.keySet()) {
                Collection<IRI> objects = map.get(predicate);
                if (objects == null) {
                    continue;
                }
                for (IRI object : objects.toArray(new IRI[objects.size()])) {
                    assert subject != null;
                    assert predicate != null;
                    assert object != null;
                    iterator.handleResourceTriple(subject, predicate, object);
                }
            }
        }
    }

    /**
     * Iterate literal triples.
     * 
     * @param iterator
     *        the iterator
     */
    protected void iterateLiteralTriples(LiteralTripleIterator iterator) {
        for (IRI subject : new ArrayList<>(litTriplesBySubject.keySet())) {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
            if (map == null) {
                continue;
            }
            for (IRI predicate : new ArrayList<>(map.keySet())) {
                Collection<OWLLiteral> objects = map.get(predicate);
                for (OWLLiteral object : new ArrayList<>(objects)) {
                    assert subject != null;
                    assert predicate != null;
                    assert object != null;
                    iterator.handleLiteralTriple(subject, predicate, object);
                }
            }
        }
    }

    /*
     * Originally we had a special Triple class, which was specialised into
     * ResourceTriple and LiteralTriple - this was used to store triples.
     * However, with very large ontologies this proved to be inefficient in
     * terms of memory usage. Now we just store raw subjects, predicates and
     * object directly in varous maps.
     */
    // Resource triples
    /** Subject, predicate, object */
    private final Map<IRI, Map<IRI, Collection<IRI>>> resTriplesBySubject = CollectionFactory.createMap();
    /** Predicate, subject, object */
    private final Map<IRI, Map<IRI, IRI>> singleValuedResTriplesByPredicate = CollectionFactory.createMap();
    /** Literal triples */
    private final Map<IRI, Map<IRI, Collection<OWLLiteral>>> litTriplesBySubject = CollectionFactory.createMap();
    /** Predicate, subject, object */
    private final Map<IRI, Map<IRI, OWLLiteral>> singleValuedLitTriplesByPredicate = CollectionFactory.createMap();
    private final Map<IRI, IRI> remappedIRIs = CollectionFactory.createMap();
    private final Map<String, IRI> remappedIRIStrings = CollectionFactory.createMap();

    @Override
    @Nonnull
    public IRI remapIRI(@Nonnull IRI i) {
        if (nodeCheckerDelegate.isAnonymousNode(i)) {
            // blank nodes do not need to be remapped in this method
            return i;
        }
        IRI computeIfAbsent = remappedIRIs.get(i);
        if (computeIfAbsent == null) {
            computeIfAbsent = IRI.create(NodeID.nextAnonymousIRI());
            remappedIRIs.put(i, computeIfAbsent);
        }
        remappedIRIStrings.put(i.toString(), computeIfAbsent);
        return computeIfAbsent;
    }

    @Override
    @Nonnull
    public String remapOnlyIfRemapped(@Nonnull String i) {
        if (nodeCheckerDelegate.isAnonymousNode(i) || nodeCheckerDelegate.isAnonymousSharedNode(i)) {
            // blank nodes do not need to be remapped in this method
            return i;
        }
        IRI iri = remappedIRIStrings.get(i);
        return iri == null ? i : iri.toString();
    }

    protected void addTriple(IRI subject, IRI predicate, IRI object) {
        Map<IRI, IRI> subjObjMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, object);
        } else {
            Map<IRI, Collection<IRI>> map = resTriplesBySubject.get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                resTriplesBySubject.put(subject, map);
            }
            Collection<IRI> objects = map.get(predicate);
            if (objects == null) {
                objects = new HashSet<>();
                map.put(predicate, objects);
            }
            objects.add(object);
        }
    }

    protected void addTriple(IRI subject, IRI predicate, OWLLiteral con) {
        Map<IRI, OWLLiteral> subjObjMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, con);
        } else {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                litTriplesBySubject.put(subject, map);
            }
            Collection<OWLLiteral> objects = map.get(predicate);
            if (objects == null) {
                objects = new HashSet<>();
                map.put(predicate, objects);
            }
            objects.add(con);
        }
    }
}
