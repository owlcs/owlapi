/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.rdfxml.parser;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFOntologyHeaderStatus;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.rdf.syntax.RDFConsumer;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.xml.sax.SAXException;

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
 *         Informatics Group, Date: 07-Dec-2006
 */
public class OWLRDFConsumer implements RDFConsumer {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(OWLRDFConsumer.class
            .getName());
    /** The Constant tripleProcessor. */
    private static final Logger tripleProcessor = Logger
            .getLogger("Triple processor");
    /** The configuration. */
    private OWLOntologyLoaderConfiguration configuration;
    /** The owl ontology manager. */
    private OWLOntologyManager owlOntologyManager;
    // A call back interface, which is used to check whether a node
    // is anonymous or not.
    /** The anonymous node checker. */
    private AnonymousNodeChecker anonymousNodeChecker;
    // The set of IRIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owl:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    /** The class expression ir is. */
    private Set<IRI> classExpressionIRIs;
    // Same as classExpressionIRIs but for object properties
    /** The object property expression ir is. */
    private Set<IRI> objectPropertyExpressionIRIs;
    // Same as classExpressionIRIs but for data properties
    /** The data property expression ir is. */
    private Set<IRI> dataPropertyExpressionIRIs;
    // Same as classExpressionIRIs but for rdf properties
    // things neither typed as a data or object property - bad!
    /** The property ir is. */
    private Set<IRI> propertyIRIs;
    // Set of IRIs that are typed by non-system types and
    // also owl:Thing
    /** The individual ir is. */
    private Set<IRI> individualIRIs;
    // Same as classExpressionIRIs but for annotation properties
    /** The annotation property ir is. */
    private Set<IRI> annotationPropertyIRIs;
    /** The annotation ir is. */
    private Set<IRI> annotationIRIs;
    // IRIs that had a type triple to rdfs:Datatange
    /** The data range ir is. */
    private Set<IRI> dataRangeIRIs;
    // The IRI of the first reource that is typed as an ontology
    /** The first ontology iri. */
    private IRI firstOntologyIRI;
    // IRIs that had a type triple to owl:Ontology
    /** The ontology ir is. */
    private Set<IRI> ontologyIRIs;
    // IRIs that had a type triple to owl:Restriction
    /** The restriction ir is. */
    private Set<IRI> restrictionIRIs;
    // Maps rdf:next triple subjects to objects
    /** The list rest triple map. */
    private Map<IRI, IRI> listRestTripleMap;
    /** The list first resource triple map. */
    private Map<IRI, IRI> listFirstResourceTripleMap;
    /** The list first literal triple map. */
    private Map<IRI, OWLLiteral> listFirstLiteralTripleMap;
    /** The axioms. */
    private Set<IRI> axioms = new HashSet<IRI>();
    /** The shared anonymous nodes. */
    private Map<IRI, Object> sharedAnonymousNodes = new HashMap<IRI, Object>();
    // A translator for lists of class expressions (such lists are used
    // in intersections, unions etc.)
    /** The class expression list translator. */
    private OptimisedListTranslator<OWLClassExpression> classExpressionListTranslator;
    // A translator for individual lists (such lists are used in
    // object oneOf constructs)
    /** The individual list translator. */
    private OptimisedListTranslator<OWLIndividual> individualListTranslator;
    /** The object property list translator. */
    private OptimisedListTranslator<OWLObjectPropertyExpression> objectPropertyListTranslator;
    /** The constant list translator. */
    private OptimisedListTranslator<OWLLiteral> constantListTranslator;
    /** The data property list translator. */
    private OptimisedListTranslator<OWLDataPropertyExpression> dataPropertyListTranslator;
    /** The data range list translator. */
    private OptimisedListTranslator<OWLDataRange> dataRangeListTranslator;
    /** The face restriction list translator. */
    private OptimisedListTranslator<OWLFacetRestriction> faceRestrictionListTranslator;
    // Handlers for built in types
    /** The built in type triple handlers. */
    private Map<IRI, BuiltInTypeHandler> builtInTypeTripleHandlers;
    // Handler for triples that denote nodes which represent axioms.
    // i.e.
    // owl:AllDisjointClasses
    // owl:AllDisjointProperties
    // owl:AllDifferent
    // owl:NegativePropertyAssertion
    // owl:Axiom
    // These need to be handled separately from other types, because the base
    // triples for annotated
    // axioms should be in the ontology before annotations on the annotated
    // versions of these axioms are parsed.
    /** The axiom type triple handlers. */
    protected Map<IRI, BuiltInTypeHandler> axiomTypeTripleHandlers = new HashMap<IRI, BuiltInTypeHandler>();
    // Handlers for build in predicates
    /** The predicate handlers. */
    private Map<IRI, TriplePredicateHandler> predicateHandlers;
    // Handlers for general literal triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary. Such triples either constitute annotationIRIs of
    // relationships between an individual and a data literal (typed or
    // untyped)
    /** The literal triple handlers. */
    protected List<AbstractLiteralTripleHandler> literalTripleHandlers;
    // Handlers for general resource triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary. Such triples either constitute annotationIRIs or
    // relationships between an individual and another individual.
    /** The resource triple handlers. */
    protected List<AbstractResourceTripleHandler> resourceTripleHandlers;
    /** The pending annotations. */
    private Set<OWLAnnotation> pendingAnnotations = new HashSet<OWLAnnotation>();
    /** The annotated anon source2 annotation map. */
    private Map<IRI, Set<IRI>> annotatedAnonSource2AnnotationMap = new HashMap<IRI, Set<IRI>>();
    /** The ontology that the RDF will be parsed into. */
    private OWLOntology ontology;
    /** The expected axioms. */
    private int expectedAxioms = -1;
    /** The parsed axioms. */
    private int parsedAxioms = 0;
    /** The ontology format. */
    private RDFOntologyFormat ontologyFormat;
    /** The data factory. */
    private OWLDataFactory dataFactory;
    /** The class expression translators. */
    private List<ClassExpressionTranslator> classExpressionTranslators = new ArrayList<ClassExpressionTranslator>();
    /** The last added axiom. */
    private OWLAxiom lastAddedAxiom;
    /** The synonym map. */
    private Map<IRI, IRI> synonymMap;
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SWRL Stuff
    /** The swrl rules. */
    private Set<IRI> swrlRules;
    /** The swrl individual property atoms. */
    private Set<IRI> swrlIndividualPropertyAtoms;
    /** The swrl data valued property atoms. */
    private Set<IRI> swrlDataValuedPropertyAtoms;
    /** The swrl class atoms. */
    private Set<IRI> swrlClassAtoms;
    /** The swrl data range atoms. */
    private Set<IRI> swrlDataRangeAtoms;
    /** The swrl built in atoms. */
    private Set<IRI> swrlBuiltInAtoms;
    /** The swrl variables. */
    private Set<IRI> swrlVariables;
    /** The swrl same as atoms. */
    private Set<IRI> swrlSameAsAtoms;
    /** The swrl different from atoms. */
    private Set<IRI> swrlDifferentFromAtoms;
    /** The iri provider. */
    private IRIProvider iriProvider;
    /** The inverse of handler. */
    protected TPInverseOfHandler inverseOfHandler;
    /** The non built in type handler. */
    private TPTypeHandler nonBuiltInTypeHandler;
    /**
     * A cache of annotation axioms to be added at the end - saves some peek
     * memory doing this.
     */
    private Collection<OWLAnnotationAxiom> parsedAnnotationAxioms = new ArrayList<OWLAnnotationAxiom>();
    /** The axioms to be removed. */
    private Collection<OWLAxiom> axiomsToBeRemoved = new ArrayList<OWLAxiom>();
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    /** The parsed all triples. */
    private boolean parsedAllTriples = false;

    /**
     * Instantiates a new oWLRDF consumer.
     * 
     * @param ontology
     *        the ontology
     * @param checker
     *        the checker
     * @param configuration
     *        the configuration
     */
    public OWLRDFConsumer(OWLOntology ontology, AnonymousNodeChecker checker,
            OWLOntologyLoaderConfiguration configuration) {
        owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        dataFactory = owlOntologyManager.getOWLDataFactory();
        anonymousNodeChecker = checker;
        this.configuration = configuration;
        classExpressionTranslators.add(new NamedClassTranslator(this));
        classExpressionTranslators
                .add(new ObjectIntersectionOfTranslator(this));
        classExpressionTranslators.add(new ObjectUnionOfTranslator(this));
        classExpressionTranslators.add(new ObjectComplementOfTranslator(this));
        classExpressionTranslators.add(new ObjectOneOfTranslator(this));
        classExpressionTranslators
                .add(new ObjectSomeValuesFromTranslator(this));
        classExpressionTranslators.add(new ObjectAllValuesFromTranslator(this));
        classExpressionTranslators.add(new ObjectHasValueTranslator(this));
        classExpressionTranslators.add(new ObjectHasSelfTranslator(this));
        classExpressionTranslators
                .add(new ObjectMinQualifiedCardinalityTranslator(this));
        classExpressionTranslators
                .add(new ObjectMaxQualifiedCardinalityTranslator(this));
        classExpressionTranslators
                .add(new ObjectQualifiedCardinalityTranslator(this));
        classExpressionTranslators
                .add(new ObjectMinCardinalityTranslator(this));
        classExpressionTranslators
                .add(new ObjectMaxCardinalityTranslator(this));
        classExpressionTranslators.add(new ObjectCardinalityTranslator(this));
        classExpressionTranslators.add(new DataSomeValuesFromTranslator(this));
        classExpressionTranslators.add(new DataAllValuesFromTranslator(this));
        classExpressionTranslators.add(new DataHasValueTranslator(this));
        classExpressionTranslators
                .add(new DataMinQualifiedCardinalityTranslator(this));
        classExpressionTranslators
                .add(new DataMaxQualifiedCardinalityTranslator(this));
        classExpressionTranslators.add(new DataQualifiedCardinalityTranslator(
                this));
        classExpressionTranslators.add(new DataMinCardinalityTranslator(this));
        classExpressionTranslators.add(new DataMaxCardinalityTranslator(this));
        classExpressionTranslators.add(new DataCardinalityTranslator(this));
        classExpressionIRIs = CollectionFactory.createSet();
        objectPropertyExpressionIRIs = CollectionFactory.createSet();
        dataPropertyExpressionIRIs = CollectionFactory.createSet();
        individualIRIs = CollectionFactory.createSet();
        annotationPropertyIRIs = CollectionFactory.createSet();
        for (IRI iri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            annotationPropertyIRIs.add(iri);
        }
        annotationIRIs = new HashSet<IRI>();
        dataRangeIRIs = CollectionFactory.createSet();
        propertyIRIs = CollectionFactory.createSet();
        restrictionIRIs = CollectionFactory.createSet();
        ontologyIRIs = CollectionFactory.createSet();
        listFirstLiteralTripleMap = CollectionFactory.createMap();
        listFirstResourceTripleMap = CollectionFactory.createMap();
        listRestTripleMap = CollectionFactory.createMap();
        classExpressionListTranslator = new OptimisedListTranslator<OWLClassExpression>(
                this, new ClassExpressionListItemTranslator(this));
        individualListTranslator = new OptimisedListTranslator<OWLIndividual>(
                this, new IndividualListItemTranslator(this));
        constantListTranslator = new OptimisedListTranslator<OWLLiteral>(this,
                new TypedConstantListItemTranslator(this));
        objectPropertyListTranslator = new OptimisedListTranslator<OWLObjectPropertyExpression>(
                this, new ObjectPropertyListItemTranslator(this));
        dataPropertyListTranslator = new OptimisedListTranslator<OWLDataPropertyExpression>(
                this, new DataPropertyListItemTranslator(this));
        dataRangeListTranslator = new OptimisedListTranslator<OWLDataRange>(
                this, new DataRangeListItemTranslator(this));
        faceRestrictionListTranslator = new OptimisedListTranslator<OWLFacetRestriction>(
                this, new OWLFacetRestrictionListItemTranslator(this));
        builtInTypeTripleHandlers = CollectionFactory.createMap();
        setupTypeTripleHandlers();
        setupPredicateHandlers();
        // General literal triples - i.e. triples which have a predicate
        // that is not a built in IRI. Annotation properties get precedence
        // over data properties, so that if we have the statement a:A a:foo a:B
        // and a:foo
        // is typed as both an annotation and data property then the statement
        // will be
        // translated as an annotation on a:A
        literalTripleHandlers = new ArrayList<AbstractLiteralTripleHandler>();
        literalTripleHandlers.add(new GTPDataPropertyAssertionHandler(this));
        literalTripleHandlers.add(new TPFirstLiteralHandler(this));
        literalTripleHandlers.add(new GTPAnnotationLiteralHandler(this));
        // General resource/object triples - i.e. triples which have a predicate
        // that is not a built in IRI. Annotation properties get precedence
        // over object properties, so that if we have the statement a:A a:foo
        // a:B and a:foo
        // is typed as both an annotation and data property then the statement
        // will be
        // translated as an annotation on a:A
        resourceTripleHandlers = new ArrayList<AbstractResourceTripleHandler>();
        resourceTripleHandlers.add(new GTPObjectPropertyAssertionHandler(this));
        resourceTripleHandlers
                .add(new GTPAnnotationResourceTripleHandler(this));
        for (OWL2Datatype dt : OWL2Datatype.values()) {
            dataRangeIRIs.add(dt.getIRI());
        }
        dataRangeIRIs.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        if (!configuration.isStrict()) {
            for (XSDVocabulary vocabulary : XSDVocabulary.values()) {
                dataRangeIRIs.add(vocabulary.getIRI());
            }
        }
        swrlRules = new HashSet<IRI>();
        swrlIndividualPropertyAtoms = new HashSet<IRI>();
        swrlDataValuedPropertyAtoms = new HashSet<IRI>();
        swrlClassAtoms = new HashSet<IRI>();
        swrlDataRangeAtoms = new HashSet<IRI>();
        swrlBuiltInAtoms = new HashSet<IRI>();
        swrlVariables = new HashSet<IRI>();
        swrlSameAsAtoms = new HashSet<IRI>();
        swrlDifferentFromAtoms = new HashSet<IRI>();
        classExpressionIRIs.add(OWLRDFVocabulary.OWL_THING.getIRI());
        classExpressionIRIs.add(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        objectPropertyExpressionIRIs
                .add(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
        objectPropertyExpressionIRIs
                .add(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
        dataPropertyExpressionIRIs.add(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY
                .getIRI());
        dataPropertyExpressionIRIs
                .add(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
        setupSynonymMap();
        setupSinglePredicateMaps();
        // Cache anything in the existing imports closure
        importsClosureChanged();
        if (this.ontology.getOntologyID().getOntologyIRI() != null) {
            addOntology(this.ontology.getOntologyID().getOntologyIRI());
        }
    }

    /**
     * Instantiates a new oWLRDF consumer.
     * 
     * @param owlOntologyManager
     *        the owl ontology manager
     * @param ontology
     *        the ontology
     * @param checker
     *        the checker
     * @param configuration
     *        the configuration
     */
    @SuppressWarnings("unused")
    @Deprecated
    public OWLRDFConsumer(OWLOntologyManager owlOntologyManager,
            OWLOntology ontology, AnonymousNodeChecker checker,
            OWLOntologyLoaderConfiguration configuration) {
        this(ontology, checker, configuration);
    }

    /**
     * Sets the iRI provider.
     * 
     * @param iriProvider
     *        the new iRI provider
     */
    public void setIRIProvider(IRIProvider iriProvider) {
        this.iriProvider = iriProvider;
    }

    /**
     * Adds the single valued res predicate.
     * 
     * @param v
     *        the v
     */
    private void addSingleValuedResPredicate(OWLRDFVocabulary v) {
        Map<IRI, IRI> map = CollectionFactory.createMap();
        singleValuedResTriplesByPredicate.put(v.getIRI(), map);
    }

    /** Setup single predicate maps. */
    private void setupSinglePredicateMaps() {
        addSingleValuedResPredicate(OWL_ON_PROPERTY);
        addSingleValuedResPredicate(OWL_SOME_VALUES_FROM);
        addSingleValuedResPredicate(OWL_ALL_VALUES_FROM);
        addSingleValuedResPredicate(OWL_ON_CLASS);
        addSingleValuedResPredicate(OWL_ON_DATA_RANGE);
    }

    /** Setup synonym map. */
    private void setupSynonymMap() {
        // We can load legacy ontologies by providing synonyms for built in
        // vocabulary
        // where the vocabulary has simply changed (e.g. DAML+OIL -> OWL)
        synonymMap = CollectionFactory.createMap();
        // Legacy protege-owlapi representation of QCRs
        synonymMap.put(IRI.create(Namespaces.OWL.toString(), "valuesFrom"),
                OWL_ON_CLASS.getIRI());
        if (!configuration.isStrict()) {
            addDAMLOILVocabulary();
            addIntermediateOWLSpecVocabulary();
        }
    }

    /** The Constant DAML_OIL. */
    private static final String DAML_OIL = "http://www.daml.org/2001/03/daml+oil#";

    /** Adds the damloil vocabulary. */
    private void addDAMLOILVocabulary() {
        synonymMap.put(IRI.create(DAML_OIL, "subClassOf"),
                RDFS_SUBCLASS_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "imports"), OWL_IMPORTS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "range"), RDFS_RANGE.getIRI());
        synonymMap
                .put(IRI.create(DAML_OIL, "hasValue"), OWL_HAS_VALUE.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "type"), RDF_TYPE.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "domain"), RDFS_DOMAIN.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "versionInfo"),
                OWL_VERSION_INFO.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "comment"), RDFS_COMMENT.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "onProperty"),
                OWL_ON_PROPERTY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "toClass"),
                OWL_ALL_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "hasClass"),
                OWL_SOME_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Restriction"),
                OWL_RESTRICTION.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Class"), OWL_CLASS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Thing"), OWL_THING.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "Nothing"), OWL_NOTHING.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "minCardinality"),
                OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "cardinality"),
                OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "maxCardinality"),
                OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "inverseOf"),
                OWL_INVERSE_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "samePropertyAs"),
                OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap
                .put(IRI.create(DAML_OIL, "hasClassQ"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "cardinalityQ"),
                OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "maxCardinalityQ"),
                OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "minCardinalityQ"),
                OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "complementOf"),
                OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "unionOf"), OWL_UNION_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "intersectionOf"),
                OWL_INTERSECTION_OF.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "label"), RDFS_LABEL.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "ObjectProperty"),
                OWL_OBJECT_PROPERTY.getIRI());
        synonymMap.put(IRI.create(DAML_OIL, "DatatypeProperty"),
                OWL_DATA_PROPERTY.getIRI());
    }

    /**
     * There may be some ontologies floating about that use early versions of
     * the OWL 1.1 vocabulary. We can map early versions of the vocabulary to
     * the current OWL 1.1 vocabulary.
     */
    @SuppressWarnings("deprecation")
    private void addIntermediateOWLSpecVocabulary() {
        for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
            addLegacyMapping(v);
        }
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(
                    IRI.create(Namespaces.OWL.toString(), v.getShortName()),
                    v.getIRI());
            synonymMap.put(
                    IRI.create(Namespaces.OWL11.toString(), v.getShortName()),
                    v.getIRI());
            synonymMap.put(
                    IRI.create(Namespaces.OWL2.toString(), v.getShortName()),
                    v.getIRI());
        }
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(
                    IRI.create(Namespaces.OWL2.toString(), v.getShortName()),
                    v.getIRI());
        }
        synonymMap.put(
                OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getIRI(),
                OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        synonymMap.put(OWLRDFVocabulary.OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION
                .getIRI(), OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION
                .getIRI());
        // Intermediate OWL 2 spec
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());
        // Preliminary OWL 1.1 Vocab
        synonymMap.put(
                IRI.create(Namespaces.OWL.toString(), "cardinalityType"),
                OWL_ON_CLASS.getIRI());
        synonymMap.put(
                IRI.create(Namespaces.OWL.toString(), "dataComplementOf"),
                OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(OWL_ANTI_SYMMETRIC_PROPERTY.getIRI(),
                OWL_ASYMMETRIC_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_DATA_PROPERTY.getIRI(),
                OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_OBJECT_PROPERTY.getIRI(),
                OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(OWL_SUB_DATA_PROPERTY_OF.getIRI(),
                RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(OWL_SUB_OBJECT_PROPERTY_OF.getIRI(),
                RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(OWL_OBJECT_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_OBJECT_PROPERTY_DOMAIN.getIRI(),
                RDFS_DOMAIN.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_DOMAIN.getIRI(), RDFS_DOMAIN.getIRI());
        synonymMap.put(OWL_DISJOINT_DATA_PROPERTIES.getIRI(),
                OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(OWL_DISJOINT_OBJECT_PROPERTIES.getIRI(),
                OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(OWL_EQUIVALENT_DATA_PROPERTIES.getIRI(),
                OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(OWL_EQUIVALENT_OBJECT_PROPERTIES.getIRI(),
                OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT_RESTRICTION.getIRI(),
                OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RESTRICTION.getIRI(), OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RANGE.getIRI(), RDFS_DATATYPE.getIRI());
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());
    }

    /**
     * Adds the legacy mapping.
     * 
     * @param v
     *        the v
     */
    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(
                IRI.create(Namespaces.OWL2.toString(), v.getShortName()),
                v.getIRI());
        synonymMap.put(
                IRI.create(Namespaces.OWL11.toString(), v.getShortName()),
                v.getIRI());
    }

    /**
     * Gets the ontology.
     * 
     * @return the ontology
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * Gets the ontology format.
     * 
     * @return the ontology format
     */
    public RDFOntologyFormat getOntologyFormat() {
        return ontologyFormat;
    }

    /**
     * Sets the ontology format.
     * 
     * @param format
     *        the new ontology format
     */
    public void setOntologyFormat(RDFOntologyFormat format) {
        ontologyFormat = format;
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
     * Adds the built in type triple handler.
     * 
     * @param handler
     *        the handler
     */
    private void addBuiltInTypeTripleHandler(BuiltInTypeHandler handler) {
        builtInTypeTripleHandlers.put(handler.getTypeIRI(), handler);
    }

    /**
     * Adds the axiom type triple handler.
     * 
     * @param handler
     *        the handler
     */
    private void addAxiomTypeTripleHandler(BuiltInTypeHandler handler) {
        axiomTypeTripleHandlers.put(handler.getTypeIRI(), handler);
    }

    /** Setup type triple handlers. */
    private void setupTypeTripleHandlers() {
        setupBasicTypeHandlers();
        setupAxiomTypeHandlers();
    }

    /** Setup basic type handlers. */
    private void setupBasicTypeHandlers() {
        addBuiltInTypeTripleHandler(new TypeOntologyPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeAsymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeObjectPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDatatypeHandler(this));
        addBuiltInTypeTripleHandler(new TypeFunctionalPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeInverseFunctionalPropertyHandler(
                this));
        addBuiltInTypeTripleHandler(new TypeIrreflexivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeReflexivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeSymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeTransitivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypeListHandler(this));
        addBuiltInTypeTripleHandler(new TypeAnnotationPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDeprecatedClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeDeprecatedPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataRangeHandler(this));
        addBuiltInTypeTripleHandler(new TypeOntologyHandler(this));
        addBuiltInTypeTripleHandler(new TypeNegativeDataPropertyAssertionHandler(
                this));
        addBuiltInTypeTripleHandler(new TypeRDFSClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeSelfRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeNamedIndividualHandler(this));
        addBuiltInTypeTripleHandler(new TypeAnnotationHandler(this));
        if (!configuration.isStrict()) {
            addBuiltInTypeTripleHandler(new TypeSWRLAtomListHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLBuiltInAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLBuiltInHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLClassAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLDataRangeAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLDataValuedPropertyAtomHandler(
                    this));
            addBuiltInTypeTripleHandler(new TypeSWRLDifferentIndividualsAtomHandler(
                    this));
            addBuiltInTypeTripleHandler(new TypeSWRLImpHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLIndividualPropertyAtomHandler(
                    this));
            addBuiltInTypeTripleHandler(new TypeSWRLSameIndividualAtomHandler(
                    this));
            addBuiltInTypeTripleHandler(new TypeSWRLVariableHandler(this));
        }
    }

    /** Setup axiom type handlers. */
    private void setupAxiomTypeHandlers() {
        addAxiomTypeTripleHandler(new TypeAxiomHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDifferentHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDisjointClassesHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDisjointPropertiesHandler(this));
        addAxiomTypeTripleHandler(new TypeNegativePropertyAssertionHandler(this));
    }

    /**
     * Adds the predicate handler.
     * 
     * @param predicateHandler
     *        the predicate handler
     */
    private void addPredicateHandler(TriplePredicateHandler predicateHandler) {
        predicateHandlers.put(predicateHandler.getPredicateIRI(),
                predicateHandler);
    }

    /** Setup predicate handlers. */
    private void setupPredicateHandlers() {
        predicateHandlers = CollectionFactory.createMap();
        addPredicateHandler(new TPDifferentFromHandler(this));
        addPredicateHandler(new TPDisjointUnionHandler(this));
        addPredicateHandler(new TPDisjointWithHandler(this));
        addPredicateHandler(new TPEquivalentClassHandler(this));
        addPredicateHandler(new TPEquivalentPropertyHandler(this));
        addPredicateHandler(new TPPropertyDomainHandler(this));
        addPredicateHandler(new TPPropertyRangeHandler(this));
        addPredicateHandler(new TPSameAsHandler(this));
        addPredicateHandler(new TPSubClassOfHandler(this));
        addPredicateHandler(new TPSubPropertyOfHandler(this));
        nonBuiltInTypeHandler = new TPTypeHandler(this);
        addPredicateHandler(nonBuiltInTypeHandler);
        addPredicateHandler(new TPDistinctMembersHandler(this));
        addPredicateHandler(new TPImportsHandler(this));
        addPredicateHandler(new TPIntersectionOfHandler(this));
        addPredicateHandler(new TPUnionOfHandler(this));
        addPredicateHandler(new TPComplementOfHandler(this));
        addPredicateHandler(new TPOneOfHandler(this));
        addPredicateHandler(new TPSomeValuesFromHandler(this));
        addPredicateHandler(new TPAllValuesFromHandler(this));
        addPredicateHandler(new TPRestHandler(this));
        addPredicateHandler(new TPFirstResourceHandler(this));
        addPredicateHandler(new TPDeclaredAsHandler(this));
        addPredicateHandler(new TPHasKeyHandler(this));
        addPredicateHandler(new TPVersionIRIHandler(this));
        addPredicateHandler(new TPPropertyChainAxiomHandler(this));
        addPredicateHandler(new TPAnnotatedSourceHandler(this));
        addPredicateHandler(new TPAnnotatedPropertyHandler(this));
        addPredicateHandler(new TPAnnotatedTargetHandler(this));
        addPredicateHandler(new TPPropertyDisjointWithHandler(this));
        inverseOfHandler = new TPInverseOfHandler(this);
        addPredicateHandler(inverseOfHandler);
        addPredicateHandler(new TPOnPropertyHandler(this));
        addPredicateHandler(new TPOnClassHandler(this));
        addPredicateHandler(new TPOnDataRangeHandler(this));
        addPredicateHandler(new TPComplementOfHandler(this));
        addPredicateHandler(new TPDatatypeComplementOfHandler(this));
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
    /** The IRI map. */
    private Map<String, IRI> IRIMap = CollectionFactory.createMap();
    /** The current base count. */
    int currentBaseCount = 0;

    /**
     * Gets any annotations that were translated since the last call of this
     * method (calling this method clears the current pending annotations).
     * 
     * @return The set (possibly empty) of pending annotations.
     */
    public Set<OWLAnnotation> getPendingAnnotations() {
        if (!pendingAnnotations.isEmpty()) {
            Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(
                    pendingAnnotations);
            pendingAnnotations.clear();
            return annos;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Sets the pending annotations.
     * 
     * @param annotations
     *        the new pending annotations
     */
    public void setPendingAnnotations(Set<OWLAnnotation> annotations) {
        if (!pendingAnnotations.isEmpty()) {
            for (OWLAnnotation ann : pendingAnnotations) {
                logger.severe(ann.toString());
            }
            throw new OWLRuntimeException(pendingAnnotations.size()
                    + " pending annotations should have been used by now.");
        }
        pendingAnnotations.addAll(annotations);
    }

    /**
     * Gets the iri.
     * 
     * @param s
     *        the s
     * @return the iri
     */
    private IRI getIRI(String s) {
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
    public void importsClosureChanged() {
        // NOTE: This method only gets called when the ontology being parsed
        // adds a direct import. This is enough
        // for resolving the imports closure.
        // We cache IRIs of various entities here.
        // We also mop up any triples that weren't parsed and consumed in the
        // imports closure.
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            for (OWLAnnotationProperty prop : ont
                    .getAnnotationPropertiesInSignature()) {
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

    /**
     * Determines if a triple has a predicate that corresponds to owl:imports.
     * 
     * @param triple
     *        The triple.
     * @return {@code true} if the triple has a predicate equal to owl:imports
     */
    private boolean isOWLImportsTriple(RDFTriple triple) {
        return triple.getPredicate().getResource()
                .equals(OWLRDFVocabulary.OWL_IMPORTS.getIRI());
    }

    /**
     * Processes an RDFTriple. The triple is deconstructed and the processing is
     * delegated to the
     * 
     * @param triple
     *        The triple to be processed.
     * @throws UnloadableImportException
     *         in the event that the triple was an owl:imports triple and the
     *         import could not be loaded.
     *         {@link #handleStreaming(org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.OWLLiteral)}
     *         and
     *         {@link #handleStreaming(org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI)}
     *         methods.
     */
    private void processRDFTriple(RDFTriple triple)
            throws UnloadableImportException {
        RDFResource subject = triple.getSubject();
        RDFResource predicate = triple.getPredicate();
        RDFNode object = triple.getObject();
        if (object.isLiteral()) {
            RDFLiteral literalObject = (RDFLiteral) object;
            handleStreaming(subject.getResource(), predicate.getResource(),
                    literalObject.getLiteral());
        } else {
            RDFResource resourceObject = (RDFResource) object;
            handleStreaming(subject.getResource(), predicate.getResource(),
                    resourceObject.getResource());
        }
    }

    /**
     * Retrieves the triples from the imports closure that have not been
     * consumed during parsing of ontologies in the imports closure.
     * 
     * @return A set of unconsumed triples.
     */
    private Set<RDFTriple> getUnconsumedTriplesFromImportsClosure() {
        Set<RDFTriple> unparsedTriples = new HashSet<RDFTriple>();
        // Don't get the REFLEXIVE transitive closure - just the transitive
        // closure.
        Set<OWLOntology> imports = ontology.getImports();
        for (OWLOntology ont : imports) {
            unparsedTriples.addAll(getUnconsumedTriples(ont));
        }
        return unparsedTriples;
    }

    /**
     * Gets the set of triples that weren't consumed during parsing of the
     * specified ontology. Note that for ontologies whose ontology documents
     * aren't based on RDF the set will be empty.
     * 
     * @param ont
     *        The ontology.
     * @return The set of triples that weren't consumed when parsing the
     *         ontology document that contained ont.
     */
    private Set<RDFTriple> getUnconsumedTriples(OWLOntology ont) {
        Set<RDFTriple> unparsedTriples = new HashSet<RDFTriple>();
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLOntologyFormat format = man.getOntologyFormat(ont);
        if (format instanceof RDFOntologyFormat) {
            RDFOntologyFormat rdfFormat = (RDFOntologyFormat) format;
            RDFParserMetaData metaData = rdfFormat.getOntologyLoaderMetaData();
            unparsedTriples.addAll(metaData.getUnparsedTriples());
        }
        return unparsedTriples;
    }

    /**
     * Checks whether a node is anonymous.
     * 
     * @param iri
     *        The IRI of the node to be checked.
     * @return {@code true} if the node is anonymous, or {@code false} if the
     *         node is not anonymous.
     */
    protected boolean isAnonymousNode(IRI iri) {
        return anonymousNodeChecker.isAnonymousNode(iri);
    }

    /**
     * Checks if is shared anonymous node.
     * 
     * @param iri
     *        the iri
     * @return true, if is shared anonymous node
     */
    protected boolean isSharedAnonymousNode(IRI iri) {
        // XXX verify: should be doable with nodeid and namespace
        return anonymousNodeChecker.isAnonymousSharedNode(iri.toString());
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

    /** The last percent parsed. */
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
     * @throws UnloadableImportException
     *         the unloadable import exception
     */
    protected void checkForAndProcessAnnotatedDeclaration(IRI mainNode)
            throws UnloadableImportException {
        IRI annotatedPropertyObject = getResourceObject(mainNode,
                OWL_ANNOTATED_PROPERTY, false);
        if (annotatedPropertyObject == null) {
            return;
        }
        boolean rdfTypePredicate = annotatedPropertyObject.equals(RDF_TYPE
                .getIRI());
        if (!rdfTypePredicate) {
            return;
        }
        IRI annotatedTargetObject = getResourceObject(mainNode,
                OWL_ANNOTATED_TARGET, false);
        if (annotatedTargetObject == null) {
            return;
        }
        IRI annotatedSubjectObject = getResourceObject(mainNode,
                OWL_ANNOTATED_SOURCE, false);
        if (annotatedSubjectObject == null) {
            return;
        }
        boolean isEntityType = isEntityTypeIRI(annotatedTargetObject);
        if (isEntityType) {
            // This will add and record the declaration for us
            handle(annotatedSubjectObject, annotatedPropertyObject,
                    annotatedTargetObject);
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
    private boolean isEntityTypeIRI(IRI iri) {
        return iri.equals(OWL_CLASS.getIRI())
                || iri.equals(OWL_OBJECT_PROPERTY.getIRI())
                || iri.equals(OWL_DATA_PROPERTY.getIRI())
                || iri.equals(OWL_ANNOTATION_PROPERTY.getIRI())
                || iri.equals(RDFS_DATATYPE.getIRI())
                || iri.equals(OWL_NAMED_INDIVIDUAL.getIRI());
    }

    /**
     * Apply change.
     * 
     * @param change
     *        the change
     */
    protected void applyChange(OWLOntologyChange change) {
        owlOntologyManager.applyChange(change);
    }

    /**
     * Sets the ontology id.
     * 
     * @param ontologyID
     *        the new ontology id
     */
    protected void setOntologyID(OWLOntologyID ontologyID) {
        applyChange(new SetOntologyID(ontology, ontologyID));
    }

    /**
     * Adds the ontology annotation.
     * 
     * @param annotation
     *        the annotation
     */
    protected void addOntologyAnnotation(OWLAnnotation annotation) {
        applyChange(new AddOntologyAnnotation(ontology, annotation));
    }

    /**
     * Adds the import.
     * 
     * @param declaration
     *        the declaration
     */
    protected void addImport(OWLImportsDeclaration declaration) {
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
        addType(iri, classExpressionIRIs, explicitlyTyped);
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
        addType(iri, dataRangeIRIs, explicitlyTyped);
    }

    /**
     * Adds the owl named individual.
     * 
     * @param iri
     *        the iri
     * @param explicitlyType
     *        the explicitly type
     */
    protected void addOWLNamedIndividual(IRI iri, boolean explicitlyType) {
        addType(iri, individualIRIs, explicitlyType);
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
        addType(iri, restrictionIRIs, explicitlyTyped);
    }

    /**
     * Adds the type.
     * 
     * @param iri
     *        the iri
     * @param types
     *        the types
     * @param explicitlyTyped
     *        the explicitly typed
     */
    private void addType(IRI iri, Set<IRI> types, boolean explicitlyTyped) {
        if (configuration.isStrict()) {
            if (explicitlyTyped) {
                types.add(iri);
            }
        } else {
            types.add(iri);
        }
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
        return iri != null && !dataPropertyExpressionIRIs.contains(iri)
                && !annotationPropertyIRIs.contains(iri)
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
        return iri != null && !objectPropertyExpressionIRIs.contains(iri)
                && !annotationPropertyIRIs.contains(iri)
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
        return iri != null && !objectPropertyExpressionIRIs.contains(iri)
                && !dataPropertyExpressionIRIs.contains(iri)
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
    public void addAnnotatedSource(IRI annotatedAnonSource,
            IRI annotationMainNode) {
        Set<IRI> annotationMainNodes = annotatedAnonSource2AnnotationMap
                .get(annotatedAnonSource);
        if (annotationMainNodes == null) {
            annotationMainNodes = new HashSet<IRI>();
            annotatedAnonSource2AnnotationMap.put(annotatedAnonSource,
                    annotationMainNodes);
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
            return Collections.emptySet();
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Helper methods for creating entities
    // //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the oWL class.
     * 
     * @param iri
     *        the iri
     * @return the oWL class
     */
    protected OWLClass getOWLClass(IRI iri) {
        return getDataFactory().getOWLClass(iri);
    }

    /**
     * Gets the oWL object property.
     * 
     * @param iri
     *        the iri
     * @return the oWL object property
     */
    protected OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return getDataFactory().getOWLObjectProperty(iri);
    }

    /**
     * Gets the oWL data property.
     * 
     * @param iri
     *        the iri
     * @return the oWL data property
     */
    protected OWLDataProperty getOWLDataProperty(IRI iri) {
        return getDataFactory().getOWLDataProperty(iri);
    }

    /**
     * Gets the oWL individual.
     * 
     * @param iri
     *        the iri
     * @return the oWL individual
     */
    protected OWLIndividual getOWLIndividual(IRI iri) {
        if (isAnonymousNode(iri)) {
            return dataFactory.getOWLAnonymousIndividual(iri.toString());
        } else {
            return dataFactory.getOWLNamedIndividual(iri);
        }
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
        isTriplePresent(subject, predicate, con, true);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL Stuff
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // //
    // // RDFConsumer implementation
    // //
    // //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Handles triples in a non-streaming mode. Type triples whose type is an
     * axiom type, are NOT handled.
     * 
     * @param subject
     *        The subject of the triple
     * @param predicate
     *        The predicate of the triple
     * @param object
     *        The object of the triple
     * @throws UnloadableImportException
     *         if such exception is raised by handleTriple()
     */
    public void handle(IRI subject, IRI predicate, IRI object)
            throws UnloadableImportException {
        if (predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
            BuiltInTypeHandler typeHandler = builtInTypeTripleHandlers
                    .get(object);
            if (typeHandler != null) {
                typeHandler.handleTriple(subject, predicate, object);
            } else if (axiomTypeTripleHandlers.get(object) == null) {
                // C(a)
                OWLIndividual ind = translateIndividual(subject);
                OWLClassExpression ce = translateClassExpression(object);
                addAxiom(dataFactory.getOWLClassAssertionAxiom(ce, ind,
                        getPendingAnnotations()));
            }
        } else {
            AbstractResourceTripleHandler handler = predicateHandlers
                    .get(predicate);
            if (handler != null
                    && handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
            } else {
                for (AbstractResourceTripleHandler resHandler : resourceTripleHandlers) {
                    if (resHandler.canHandle(subject, predicate, object)) {
                        resHandler.handleTriple(subject, predicate, object);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Handle.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    public void handle(IRI subject, IRI predicate, OWLLiteral object) {
        for (AbstractLiteralTripleHandler handler : literalTripleHandlers) {
            if (handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
                break;
            }
        }
    }

    /**
     * Append.
     * 
     * @param i
     *        the i
     * @param b
     *        the b
     */
    private void append(IRI i, StringBuilder b) {
        b.append(i.getNamespace());
        if (i.getFragment() != null) {
            b.append(i.getFragment());
        }
    }

    /**
     * Prints the triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    private void printTriple(IRI subject, IRI predicate, IRI object) {
        StringBuilder b = new StringBuilder();
        append(subject, b);
        b.append(" -> ");
        append(predicate, b);
        b.append(" -> ");
        append(object, b);
        logger.fine(b.toString());
    }

    /**
     * Prints the triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    private void printTriple(IRI subject, IRI predicate, Object object) {
        StringBuilder b = new StringBuilder();
        append(subject, b);
        b.append(" -> ");
        append(predicate, b);
        b.append(" -> ");
        b.append(object.toString());
        logger.fine(b.toString());
    }

    /** Dump remaining triples. */
    protected void dumpRemainingTriples() {
        if (logger.isLoggable(Level.FINE)) {
            for (IRI predicate : singleValuedResTriplesByPredicate.keySet()) {
                Map<IRI, IRI> map = singleValuedResTriplesByPredicate
                        .get(predicate);
                for (IRI subject : map.keySet()) {
                    IRI object = map.get(subject);
                    printTriple(subject, predicate, object);
                }
            }
            for (IRI predicate : singleValuedLitTriplesByPredicate.keySet()) {
                Map<IRI, OWLLiteral> map = singleValuedLitTriplesByPredicate
                        .get(predicate);
                for (IRI subject : map.keySet()) {
                    OWLLiteral object = map.get(subject);
                    printTriple(subject, predicate, object);
                }
            }
            for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
                Map<IRI, Collection<IRI>> map = resTriplesBySubject
                        .get(subject);
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Collection<IRI> objects = map.get(predicate);
                    for (IRI object : objects) {
                        printTriple(subject, predicate, object);
                    }
                }
            }
            for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
                Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject
                        .get(subject);
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Collection<OWLLiteral> objects = map.get(predicate);
                    for (OWLLiteral object : objects) {
                        printTriple(subject, predicate, object);
                    }
                }
            }
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Debug stuff
    /** The count. */
    private int count = 0;

    /** Increment triple count. */
    private void incrementTripleCount() {
        count++;
        if (tripleProcessor.isLoggable(Level.FINE) && count % 10000 == 0) {
            tripleProcessor.fine("Parsed: " + count + " triples");
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void startModel(String string) throws SAXException {
        count = 0;
    }

    /**
     * Checks if is parsed all triples.
     * 
     * @return true, if is parsed all triples
     */
    public boolean isParsedAllTriples() {
        return parsedAllTriples;
    }

    @Override
    public void endModel() throws SAXException {
        parsedAllTriples = true;
        try {
            // We are now left with triples that could not be consumed during
            // streaming parsing
            IRIMap.clear();
            tripleProcessor.fine("Total number of triples: " + count);
            RDFOntologyFormat format = ontologyFormat;
            consumeSWRLRules();
            // We need to mop up all remaining triples. These triples will be in
            // the triples by subject map. Other triples which reside in the
            // triples by predicate (single valued) triple aren't "root" triples
            // for axioms. First we translate all system triples, starting with
            // property ranges, then go for triples whose predicates are not
            // system/reserved vocabulary IRIs to translate these into ABox
            // assertions or annotationIRIs
            final TriplePredicateHandler propertyRangeHandler = predicateHandlers
                    .get(RDFS_RANGE.getIRI());
            iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {

                @Override
                public void handleResourceTriple(IRI subject, IRI predicate,
                        IRI object) throws UnloadableImportException {
                    if (propertyRangeHandler.canHandle(subject, predicate,
                            object)) {
                        propertyRangeHandler.handleTriple(subject, predicate,
                                object);
                    }
                }
            });
            iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {

                @Override
                public void handleResourceTriple(IRI subject, IRI predicate,
                        IRI object) throws UnloadableImportException {
                    handle(subject, predicate, object);
                }
            });
            iterateLiteralTriples(new LiteralTripleIterator<UnloadableImportException>() {

                @Override
                public void handleLiteralTriple(IRI subject, IRI predicate,
                        OWLLiteral object) throws UnloadableImportException {
                    handle(subject, predicate, object);
                }
            });
            // Inverse property axioms
            inverseOfHandler.setAxiomParsingMode(true);
            iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {

                @Override
                public void handleResourceTriple(IRI subject, IRI predicate,
                        IRI object) throws UnloadableImportException {
                    if (inverseOfHandler.canHandle(subject, predicate, object)) {
                        inverseOfHandler.handleTriple(subject, predicate,
                                object);
                    }
                }
            });
            // Now handle non-reserved predicate triples
            consumeNonReservedPredicateTriples();
            // Now axiom annotations
            consumeAnnotatedAxioms();
            final Set<RDFTriple> remainingTriples = getRemainingTriples();
            if (format != null) {
                RDFParserMetaData metaData = new RDFParserMetaData(
                        RDFOntologyHeaderStatus.PARSED_ONE_HEADER, count,
                        remainingTriples);
                format.setOntologyLoaderMetaData(metaData);
            }
            // Do we need to change the ontology IRI?
            IRI ontologyIRIToSet = chooseOntologyIRI();
            if (ontologyIRIToSet != null) {
                IRI versionIRI = ontology.getOntologyID().getVersionIRI();
                applyChange(new SetOntologyID(ontology, new OWLOntologyID(
                        ontologyIRIToSet, versionIRI)));
            }
            if (tripleProcessor.isLoggable(Level.FINE)) {
                tripleProcessor.fine("Loaded " + ontology.getOntologyID());
            }
            dumpRemainingTriples();
            cleanup();
            addAnnotationAxioms();
            removeAxiomsScheduledForRemoval();
        } catch (UnloadableImportException e) {
            throw new TranslatedUnloadedImportException(e);
        }
    }

    /** Adds the annotation axioms. */
    private void addAnnotationAxioms() {
        for (OWLAxiom axiom : parsedAnnotationAxioms) {
            owlOntologyManager.addAxiom(ontology, axiom);
        }
    }

    /** Removes the axioms scheduled for removal. */
    private void removeAxiomsScheduledForRemoval() {
        for (OWLAxiom axiom : axiomsToBeRemoved) {
            owlOntologyManager.removeAxiom(ontology, axiom);
        }
    }

    /**
     * Gets the remaining triples.
     * 
     * @return the remaining triples
     */
    private Set<RDFTriple> getRemainingTriples() {
        final Set<RDFTriple> remainingTriples = new HashSet<RDFTriple>();
        iterateResourceTriples(new ResourceTripleIterator<OWLRuntimeException>() {

            @Override
            public void handleResourceTriple(IRI subject, IRI predicate,
                    IRI object) {
                remainingTriples.add(new RDFTriple(subject,
                        isAnonymousNode(subject), predicate,
                        isAnonymousNode(predicate), object,
                        isAnonymousNode(object)));
            }
        });
        iterateLiteralTriples(new LiteralTripleIterator<RuntimeException>() {

            @Override
            public void handleLiteralTriple(IRI subject, IRI predicate,
                    OWLLiteral object) {
                remainingTriples.add(new RDFTriple(subject,
                        isAnonymousNode(subject), predicate,
                        isAnonymousNode(predicate), object));
            }
        });
        return remainingTriples;
    }

    /**
     * Consume non reserved predicate triples.
     * 
     * @throws UnloadableImportException
     *         the unloadable import exception
     */
    private void consumeNonReservedPredicateTriples()
            throws UnloadableImportException {
        iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {

            @Override
            public void handleResourceTriple(IRI subject, IRI predicate,
                    IRI object) throws UnloadableImportException {
                if (isGeneralPredicate(predicate)) {
                    for (AbstractResourceTripleHandler resTripHandler : resourceTripleHandlers) {
                        if (resTripHandler
                                .canHandle(subject, predicate, object)) {
                            resTripHandler.handleTriple(subject, predicate,
                                    object);
                            break;
                        }
                    }
                }
            }
        });
        iterateLiteralTriples(new LiteralTripleIterator<UnloadableImportException>() {

            @Override
            public void handleLiteralTriple(IRI subject, IRI predicate,
                    OWLLiteral object) throws UnloadableImportException {
                if (isGeneralPredicate(predicate)) {
                    for (AbstractLiteralTripleHandler literalTripleHandler : literalTripleHandlers) {
                        if (literalTripleHandler.canHandle(subject, predicate,
                                object)) {
                            literalTripleHandler.handleTriple(subject,
                                    predicate, object);
                            break;
                        }
                    }
                }
            }
        });
    }

    /** Consume swrl rules. */
    private void consumeSWRLRules() {
        SWRLRuleTranslator translator = new SWRLRuleTranslator(this);
        for (IRI ruleIRI : swrlRules) {
            translator.translateRule(ruleIRI);
        }
    }

    /**
     * Consume annotated axioms.
     * 
     * @throws UnloadableImportException
     *         the unloadable import exception
     */
    private void consumeAnnotatedAxioms() throws UnloadableImportException {
        iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {

            @Override
            public void handleResourceTriple(IRI subject, IRI predicate,
                    IRI object) throws UnloadableImportException {
                BuiltInTypeHandler builtInTypeHandler = axiomTypeTripleHandlers
                        .get(object);
                if (builtInTypeHandler != null) {
                    if (builtInTypeHandler
                            .canHandle(subject, predicate, object)) {
                        builtInTypeHandler.handleTriple(subject, predicate,
                                object);
                    }
                }
            }
        });
    }

    /**
     * Selects an IRI to be the ontology IRI.
     * 
     * @return An IRI that should be used as the IRI of the parsed ontology, or
     *         {@code null} if the parsed ontology does not have an IRI
     */
    private IRI chooseOntologyIRI() {
        IRI ontologyIRIToSet = null;
        if (ontologyIRIs.isEmpty()) {
            // No ontology IRIs
            // We used to use the xml:base here. But this is probably incorrect
            // for OWL 2 now.
        } else if (ontologyIRIs.size() == 1) {
            // Exactly one ontologyIRI
            IRI ontologyIRI = ontologyIRIs.iterator().next();
            if (!isAnonymousNode(ontologyIRI)) {
                ontologyIRIToSet = ontologyIRI;
            }
        } else {
            // We have multiple to choose from
            // Choose one that isn't the object of an annotation assertion
            Set<IRI> candidateIRIs = new HashSet<IRI>(ontologyIRIs);
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
                ontologyIRIToSet = firstOntologyIRI;
            } else if (!candidateIRIs.isEmpty()) {
                // Just pick any
                ontologyIRIToSet = candidateIRIs.iterator().next();
            }
        }
        return ontologyIRIToSet;
    }

    /** Cleanup. */
    private void cleanup() {
        classExpressionIRIs.clear();
        objectPropertyExpressionIRIs.clear();
        dataPropertyExpressionIRIs.clear();
        dataRangeIRIs.clear();
        restrictionIRIs.clear();
        listFirstLiteralTripleMap.clear();
        listFirstResourceTripleMap.clear();
        listRestTripleMap.clear();
        translatedClassExpression.clear();
        resTriplesBySubject.clear();
        litTriplesBySubject.clear();
        singleValuedLitTriplesByPredicate.clear();
        singleValuedResTriplesByPredicate.clear();
    }

    @Override
    public void addModelAttribte(String string, String string1)
            throws SAXException {}

    @Override
    public void includeModel(String string, String string1) throws SAXException {}

    @Override
    public void logicalURI(String string) throws SAXException {}

    /**
     * Gets the synonym.
     * 
     * @param original
     *        the original
     * @return the synonym
     */
    public IRI getSynonym(IRI original) {
        if (!configuration.isStrict()) {
            IRI synonymIRI = synonymMap.get(original);
            if (synonymIRI != null) {
                return synonymIRI;
            }
        }
        return original;
    }

    @Override
    public void statementWithLiteralValue(String subject, String predicate,
            String object, String lang, String datatype) throws SAXException {
        incrementTripleCount();
        IRI subjectIRI = getIRI(subject);
        IRI predicateIRI = getIRI(predicate);
        predicateIRI = getSynonym(predicateIRI);
        handleStreaming(subjectIRI, predicateIRI, object, datatype, lang);
    }

    @Override
    public void statementWithResourceValue(String subject, String predicate,
            String object) throws SAXException {
        try {
            incrementTripleCount();
            IRI subjectIRI = getIRI(subject);
            IRI predicateIRI = getIRI(predicate);
            predicateIRI = getSynonym(predicateIRI);
            IRI objectIRI = getSynonym(getIRI(object));
            handleStreaming(subjectIRI, predicateIRI, objectIRI);
        } catch (UnloadableImportException e) {
            throw new TranslatedUnloadedImportException(e);
        }
    }

    /**
     * Called when a resource triple has been parsed.
     * 
     * @param subject
     *        The subject of the triple that has been parsed
     * @param predicate
     *        The predicate of the triple that has been parsed
     * @param object
     *        The object of the triple that has been parsed
     * @throws UnloadableImportException
     *         the unloadable import exception
     */
    private void handleStreaming(IRI subject, IRI predicate, IRI object)
            throws UnloadableImportException {
        boolean consumed = false;
        if (predicate.equals(RDF_TYPE.getIRI())) {
            BuiltInTypeHandler handler = builtInTypeTripleHandlers.get(object);
            if (handler != null) {
                if (handler.canHandleStreaming(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                    consumed = true;
                }
            } else if (axiomTypeTripleHandlers.get(object) == null) {
                // Not a built in type
                addOWLNamedIndividual(subject, false);
                if (nonBuiltInTypeHandler.canHandleStreaming(subject,
                        predicate, object)) {
                    nonBuiltInTypeHandler.handleTriple(subject, predicate,
                            object);
                    consumed = true;
                }
            } else {
                addAxiom(subject);
            }
        } else {
            AbstractResourceTripleHandler handler = predicateHandlers
                    .get(predicate);
            if (handler != null) {
                if (handler.canHandleStreaming(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                    consumed = true;
                }
            } else {
                for (AbstractResourceTripleHandler resHandler : resourceTripleHandlers) {
                    if (resHandler.canHandleStreaming(subject, predicate,
                            object)) {
                        resHandler.handleTriple(subject, predicate, object);
                        consumed = true;
                        break;
                    }
                }
            }
        }
        if (!consumed) {
            // Not consumed, so add the triple
            addTriple(subject, predicate, object);
        }
    }

    /**
     * Handle streaming.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param literal
     *        the literal
     * @param datatype
     *        the datatype
     * @param lang
     *        the lang
     */
    private void handleStreaming(IRI subject, IRI predicate, String literal,
            String datatype, String lang) {
        // Convert all literals to OWLConstants
        OWLLiteral con = getOWLLiteral(literal, datatype, lang);
        handleStreaming(subject, predicate, con);
    }

    /**
     * Handle streaming.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param con
     *        the con
     */
    private void handleStreaming(IRI subject, IRI predicate, OWLLiteral con) {
        for (AbstractLiteralTripleHandler handler : literalTripleHandlers) {
            if (handler.canHandleStreaming(subject, predicate, con)) {
                handler.handleTriple(subject, predicate, con);
                return;
            }
        }
        addTriple(subject, predicate, con);
    }

    /**
     * A convenience method to obtain an {@code OWLConstant}.
     * 
     * @param literal
     *        The literal - must NOT be {@code null}
     * @param datatype
     *        The data type - may be {@code null}
     * @param lang
     *        The lang - may be {@code null}
     * @return The {@code OWLConstant} (either typed or untyped depending on the
     *         params)
     */
    private OWLLiteral getOWLLiteral(String literal, String datatype,
            String lang) {
        if (datatype != null) {
            return dataFactory.getOWLLiteral(literal,
                    dataFactory.getOWLDatatype(getIRI(datatype)));
        } else {
            return dataFactory.getOWLLiteral(literal, lang);
        }
    }

    /**
     * Given a main node, translated data ranges according to Table 12.
     * 
     * @param mainNode
     *        The main node
     * @return The translated data range. If the data range could not be
     *         translated then an OWLDatatype with the given IRI is returned.
     */
    public OWLDataRange translateDataRange(IRI mainNode) {
        if (!isDataRange(mainNode) && configuration.isStrict()) {
            // Can't translated ANY according to Table 12
            return generateAndLogParseError(EntityType.DATATYPE, mainNode);
        }
        if (!isAnonymousNode(mainNode) && isDataRange(mainNode)) {
            return dataFactory.getOWLDatatype(mainNode);
        }
        IRI intersectionOfObject = getResourceObject(mainNode,
                OWL_INTERSECTION_OF, true);
        if (intersectionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(intersectionOfObject);
            return dataFactory.getOWLDataIntersectionOf(dataRanges);
        }
        IRI unionOfObject = getResourceObject(mainNode, OWL_UNION_OF, true);
        if (unionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(unionOfObject);
            return dataFactory.getOWLDataUnionOf(dataRanges);
        }
        // The plain complement of triple predicate is in here for legacy
        // reasons
        IRI complementOfObject = getResourceObject(mainNode,
                OWL_DATATYPE_COMPLEMENT_OF, true);
        if (!configuration.isStrict() && complementOfObject == null) {
            complementOfObject = getResourceObject(mainNode, OWL_COMPLEMENT_OF,
                    true);
        }
        if (complementOfObject != null) {
            OWLDataRange operand = translateDataRange(complementOfObject);
            return dataFactory.getOWLDataComplementOf(operand);
        }
        IRI oneOfObject = getResourceObject(mainNode, OWL_ONE_OF, true);
        if (oneOfObject != null) {
            Set<OWLLiteral> literals = translateToConstantSet(oneOfObject);
            return dataFactory.getOWLDataOneOf(literals);
        }
        IRI onDatatypeObject = getResourceObject(mainNode, OWL_ON_DATA_TYPE,
                true);
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
            Set<OWLFacetRestriction> restrictions = new HashSet<OWLFacetRestriction>();
            IRI facetRestrictionList = getResourceObject(mainNode,
                    OWL_WITH_RESTRICTIONS, true);
            if (facetRestrictionList != null) {
                restrictions = translateToFacetRestrictionSet(facetRestrictionList);
            } else if (!configuration.isStrict()) {
                // Try the legacy encoding
                for (IRI facetIRI : OWLFacet.FACET_IRIS) {
                    OWLLiteral val;
                    while ((val = getLiteralObject(mainNode, facetIRI, true)) != null) {
                        restrictions.add(dataFactory.getOWLFacetRestriction(
                                OWLFacet.getFacet(facetIRI), val));
                    }
                }
            }
            return dataFactory.getOWLDatatypeRestriction(restrictedDataRange,
                    restrictions);
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
    public OWLDataPropertyExpression translateDataPropertyExpression(IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Basic node translation - translation of entities
    // //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** The translated properties. */
    private Map<IRI, OWLObjectPropertyExpression> translatedProperties = new HashMap<IRI, OWLObjectPropertyExpression>();

    /**
     * Translate object property expression.
     * 
     * @param mainNode
     *        the main node
     * @return the oWL object property expression
     */
    public OWLObjectPropertyExpression translateObjectPropertyExpression(
            IRI mainNode) {
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
            IRI inverseOfObject = getResourceObject(mainNode, OWL_INVERSE_OF,
                    true);
            if (inverseOfObject != null) {
                OWLObjectPropertyExpression otherProperty = translateObjectPropertyExpression(inverseOfObject);
                prop = dataFactory.getOWLObjectInverseOf(otherProperty);
            } else {
                prop = dataFactory.getOWLObjectInverseOf(dataFactory
                        .getOWLObjectProperty(mainNode));
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
    public OWLIndividual translateIndividual(IRI node) {
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
    public Set<OWLAnnotation> translateAnnotations(IRI mainNode) {
        // Are we the subject of an annotation? If so, we need to ensure that
        // the annotations annotate us. This
        // will only happen if we are an annotation!
        Set<OWLAnnotation> annosOnMainNodeAnnotations = new HashSet<OWLAnnotation>();
        Set<IRI> annotationMainNodes = getAnnotatedSourceAnnotationMainNodes(mainNode);
        if (!annotationMainNodes.isEmpty()) {
            for (IRI annotationMainNode : annotationMainNodes) {
                annosOnMainNodeAnnotations
                        .addAll(translateAnnotations(annotationMainNode));
            }
        }
        Set<OWLAnnotation> mainNodeAnnotations = new HashSet<OWLAnnotation>();
        Set<IRI> predicates = getPredicatesBySubject(mainNode);
        for (IRI predicate : predicates) {
            if (isAnnotationProperty(predicate)) {
                IRI resVal = getResourceObject(mainNode, predicate, true);
                while (resVal != null) {
                    OWLAnnotationProperty prop = dataFactory
                            .getOWLAnnotationProperty(predicate);
                    OWLAnnotationValue val;
                    if (isAnonymousNode(resVal)) {
                        val = dataFactory.getOWLAnonymousIndividual(resVal
                                .toString());
                    } else {
                        val = resVal;
                    }
                    mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop,
                            val, annosOnMainNodeAnnotations));
                    resVal = getResourceObject(mainNode, predicate, true);
                }
                OWLLiteral litVal = getLiteralObject(mainNode, predicate, true);
                while (litVal != null) {
                    OWLAnnotationProperty prop = dataFactory
                            .getOWLAnnotationProperty(predicate);
                    mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop,
                            litVal, annosOnMainNodeAnnotations));
                    litVal = getLiteralObject(mainNode, predicate, true);
                }
            }
        }
        return mainNodeAnnotations;
    }

    /** The translated class expression. */
    private Map<IRI, OWLClassExpression> translatedClassExpression = new HashMap<IRI, OWLClassExpression>();

    /**
     * Translate class expression.
     * 
     * @param mainNode
     *        the main node
     * @return the oWL class expression
     */
    public OWLClassExpression translateClassExpression(IRI mainNode) {
        OWLClassExpression ce = translatedClassExpression.get(mainNode);
        if (ce == null) {
            ce = translateClassExpressionInternal(mainNode);
            translatedClassExpression.put(mainNode, ce);
        }
        return ce;
    }

    /** The Constant errorCounter. */
    private static final AtomicInteger errorCounter = new AtomicInteger(0);

    /**
     * Gets the error entity.
     * 
     * @param <E>
     *        the element type
     * @param entityType
     *        the entity type
     * @return the error entity
     */
    private <E extends OWLEntity> E getErrorEntity(EntityType<E> entityType) {
        IRI iri = IRI.create("http://org.semanticweb.owlapi/error#", "Error"
                + errorCounter.incrementAndGet());
        return dataFactory.getOWLEntity(entityType, iri);
    }

    /**
     * Translate class expression internal.
     * 
     * @param mainNode
     *        the main node
     * @return the oWL class expression
     */
    private OWLClassExpression translateClassExpressionInternal(IRI mainNode) {
        // Some optimisations...
        // We either have a class or a restriction
        Mode mode = getConfiguration().isStrict() ? Mode.STRICT : Mode.LAX;
        for (ClassExpressionTranslator translator : classExpressionTranslators) {
            if (translator.matches(mainNode, mode)) {
                return translator.translate(mainNode);
            }
        }
        if (!isAnonymousNode(mainNode)) {
            return dataFactory.getOWLClass(mainNode);
        } else {
            return generateAndLogParseError(EntityType.CLASS, mainNode);
        }
    }

    /**
     * Gets the rDF resource.
     * 
     * @param iri
     *        the iri
     * @return the rDF resource
     */
    private RDFResource getRDFResource(IRI iri) {
        return new RDFResource(iri, isAnonymousNode(iri));
    }

    /**
     * Gets the rDF triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     * @return the rDF triple
     */
    private RDFTriple getRDFTriple(IRI subject, IRI predicate, IRI object) {
        return new RDFTriple(getRDFResource(subject),
                getRDFResource(predicate), getRDFResource(object));
    }

    /**
     * Gets the rDF triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     * @return the rDF triple
     */
    private RDFTriple
            getRDFTriple(IRI subject, IRI predicate, OWLLiteral object) {
        return new RDFTriple(getRDFResource(subject),
                getRDFResource(predicate), new RDFLiteral(object));
    }

    /**
     * Gets the triples for main node.
     * 
     * @param mainNode
     *        the main node
     * @param augmentingTypes
     *        the augmenting types
     * @return the triples for main node
     */
    private Set<RDFTriple> getTriplesForMainNode(IRI mainNode,
            IRI... augmentingTypes) {
        Set<RDFTriple> triples = new HashSet<RDFTriple>();
        for (IRI predicate : getPredicatesBySubject(mainNode)) {
            for (IRI object : getResourceObjects(mainNode, predicate)) {
                triples.add(getRDFTriple(mainNode, predicate, object));
            }
            for (OWLLiteral object : getLiteralObjects(mainNode, predicate)) {
                triples.add(getRDFTriple(mainNode, predicate, object));
            }
        }
        for (IRI augmentingType : augmentingTypes) {
            triples.add(getRDFTriple(mainNode,
                    OWLRDFVocabulary.RDF_TYPE.getIRI(), augmentingType));
        }
        return triples;
    }

    /**
     * Log error.
     * 
     * @param error
     *        the error
     */
    private void logError(RDFResourceParseError error) {
        ontologyFormat.addError(error);
    }

    /**
     * Generate and log parse error.
     * 
     * @param <E>
     *        the element type
     * @param entityType
     *        the entity type
     * @param mainNode
     *        the main node
     * @return the e
     */
    private <E extends OWLEntity> E generateAndLogParseError(
            EntityType<E> entityType, IRI mainNode) {
        E entity = getErrorEntity(entityType);
        RDFResource mainNodeResource = getRDFResource(mainNode);
        Set<RDFTriple> mainNodeTriples = getTriplesForMainNode(mainNode);
        RDFResourceParseError error = new RDFResourceParseError(entity,
                mainNodeResource, mainNodeTriples);
        logError(error);
        return entity;
    }

    /**
     * Gets the class expression if translated.
     * 
     * @param mainNode
     *        the main node
     * @return the class expression if translated
     */
    public OWLClassExpression getClassExpressionIfTranslated(IRI mainNode) {
        return translatedClassExpression.get(mainNode);
    }

    /**
     * Translate to object property list.
     * 
     * @param mainNode
     *        the main node
     * @return the list
     */
    public List<OWLObjectPropertyExpression> translateToObjectPropertyList(
            IRI mainNode) {
        return objectPropertyListTranslator.translateList(mainNode);
    }

    /**
     * Translate to data property list.
     * 
     * @param mainNode
     *        the main node
     * @return the list
     */
    public List<OWLDataPropertyExpression> translateToDataPropertyList(
            IRI mainNode) {
        return dataPropertyListTranslator.translateList(mainNode);
    }

    /**
     * Translate to class expression set.
     * 
     * @param mainNode
     *        the main node
     * @return the sets the
     */
    public Set<OWLClassExpression> translateToClassExpressionSet(IRI mainNode) {
        return classExpressionListTranslator.translateToSet(mainNode);
    }

    /**
     * Translate to constant set.
     * 
     * @param mainNode
     *        the main node
     * @return the sets the
     */
    public Set<OWLLiteral> translateToConstantSet(IRI mainNode) {
        return constantListTranslator.translateToSet(mainNode);
    }

    /**
     * Translate to individual set.
     * 
     * @param mainNode
     *        the main node
     * @return the sets the
     */
    public Set<OWLIndividual> translateToIndividualSet(IRI mainNode) {
        return individualListTranslator.translateToSet(mainNode);
    }

    /**
     * Translate to data range set.
     * 
     * @param mainNode
     *        the main node
     * @return the sets the
     */
    public Set<OWLDataRange> translateToDataRangeSet(IRI mainNode) {
        return dataRangeListTranslator.translateToSet(mainNode);
    }

    /**
     * Translate to facet restriction set.
     * 
     * @param mainNode
     *        the main node
     * @return the sets the
     */
    public Set<OWLFacetRestriction>
            translateToFacetRestrictionSet(IRI mainNode) {
        return faceRestrictionListTranslator.translateToSet(mainNode);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the predicates by subject.
     * 
     * @param subject
     *        the subject
     * @return the predicates by subject
     */
    public Set<IRI> getPredicatesBySubject(IRI subject) {
        Set<IRI> IRIs = new HashSet<IRI>();
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            IRIs.addAll(predObjMap.keySet());
        }
        Map<IRI, Collection<OWLLiteral>> predObjMapLit = litTriplesBySubject
                .get(subject);
        if (predObjMapLit != null) {
            IRIs.addAll(predObjMapLit.keySet());
        }
        return IRIs;
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
    public IRI getResourceObject(IRI subject, OWLRDFVocabulary predicate,
            boolean consume) {
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
    public IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate
                .get(predicate);
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
            if (objects != null) {
                if (!objects.isEmpty()) {
                    IRI object = objects.iterator().next();
                    if (consume) {
                        objects.remove(object);
                    }
                    if (objects.isEmpty()) {
                        predObjMap.remove(predicate);
                        if (predObjMap.isEmpty()) {
                            resTriplesBySubject.remove(subject);
                        }
                    }
                    return object;
                }
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
    public Set<IRI> getResourceObjects(IRI subject, IRI predicate) {
        Set<IRI> result = new HashSet<IRI>();
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate
                .get(predicate);
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
    public OWLLiteral getLiteralObject(IRI subject, OWLRDFVocabulary predicate,
            boolean consume) {
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
    public OWLLiteral getLiteralObject(IRI subject, IRI predicate,
            boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate
                .get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject
                .get(subject);
        if (predObjMap != null) {
            Collection<OWLLiteral> objects = predObjMap.get(predicate);
            if (objects != null) {
                if (!objects.isEmpty()) {
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
    public Set<OWLLiteral> getLiteralObjects(IRI subject, IRI predicate) {
        Set<OWLLiteral> result = new HashSet<OWLLiteral>();
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate
                .get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (obj != null) {
                result.add(obj);
            }
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject
                .get(subject);
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
    public boolean isTriplePresent(IRI subject, IRI predicate, IRI object,
            boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate
                .get(predicate);
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
                        if (objects.isEmpty()) {
                            predObjMap.remove(predicate);
                            if (predObjMap.isEmpty()) {
                                resTriplesBySubject.remove(subject);
                            }
                        }
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
    protected boolean isGeneralPredicate(IRI predicate) {
        return !predicate.isReservedVocabulary()
                || OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS
                        .contains(predicate)
                || Namespaces.SWRL.inNamespace(predicate)
                || Namespaces.SWRLB.inNamespace(predicate);
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
    public boolean isTriplePresent(IRI subject, IRI predicate,
            OWLLiteral object, boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate
                .get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject
                .get(subject);
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
    public boolean hasPredicate(IRI subject, IRI predicate) {
        Map<IRI, IRI> resPredMap = singleValuedResTriplesByPredicate
                .get(predicate);
        if (resPredMap != null) {
            return resPredMap.containsKey(subject);
        }
        Map<IRI, OWLLiteral> litPredMap = singleValuedLitTriplesByPredicate
                .get(predicate);
        if (litPredMap != null) {
            return litPredMap.containsKey(subject);
        }
        Map<IRI, Collection<IRI>> resPredObjMap = resTriplesBySubject
                .get(subject);
        if (resPredObjMap != null) {
            boolean b = resPredObjMap.containsKey(predicate);
            if (b) {
                return true;
            }
        }
        Map<IRI, Collection<OWLLiteral>> litPredObjMap = litTriplesBySubject
                .get(subject);
        if (litPredObjMap != null) {
            return litPredObjMap.containsKey(predicate);
        }
        return false;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Adds the rest.
     * 
     * @param subject
     *        the subject
     * @param object
     *        the object
     */
    public void addRest(IRI subject, IRI object) {
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
    public void addFirst(IRI subject, IRI object) {
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
    public IRI getFirstResource(IRI subject, boolean consume) {
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
    public OWLLiteral getFirstLiteral(IRI subject) {
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
    public IRI getRest(IRI subject, boolean consume) {
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
    public void addFirst(IRI subject, OWLLiteral object) {
        listFirstLiteralTripleMap.put(subject, object);
    }

    /**
     * Adds the ontology.
     * 
     * @param iri
     *        the iri
     */
    public void addOntology(IRI iri) {
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
    public Set<IRI> getOntologies() {
        return ontologyIRIs;
    }

    /**
     * Adds the axiom.
     * 
     * @param axiomIRI
     *        the axiom iri
     */
    public void addAxiom(IRI axiomIRI) {
        axioms.add(axiomIRI);
    }

    /**
     * Checks if is axiom.
     * 
     * @param iri
     *        the iri
     * @return true, if is axiom
     */
    public boolean isAxiom(IRI iri) {
        return axioms.contains(iri);
    }

    /**
     * Checks if is data range.
     * 
     * @param iri
     *        the iri
     * @return true, if is data range
     */
    public boolean isDataRange(IRI iri) {
        return dataRangeIRIs.contains(iri);
    }

    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    public OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// Triple Stuff
    // ////
    // ////
    // ////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The Interface ResourceTripleIterator.
     * 
     * @param <E>
     *        the element type
     */
    private interface ResourceTripleIterator<E extends Throwable> {

        /**
         * Handle resource triple.
         * 
         * @param subject
         *        the subject
         * @param predicate
         *        the predicate
         * @param object
         *        the object
         * @throws E
         *         the e
         */
        void handleResourceTriple(IRI subject, IRI predicate, IRI object)
                throws E;
    }

    /**
     * The Interface LiteralTripleIterator.
     * 
     * @param <E>
     *        the element type
     */
    private interface LiteralTripleIterator<E extends Throwable> {

        /**
         * Handle literal triple.
         * 
         * @param subject
         *        the subject
         * @param predicate
         *        the predicate
         * @param object
         *        the object
         * @throws E
         *         the e
         */
        void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object)
                throws E;
    }

    /**
     * Iterate resource triples.
     * 
     * @param <E>
     *        the element type
     * @param iterator
     *        the iterator
     * @throws E
     *         the e
     */
    protected <E extends Throwable> void iterateResourceTriples(
            ResourceTripleIterator<E> iterator) throws E {
        for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
            Map<IRI, Collection<IRI>> map = resTriplesBySubject.get(subject);
            if (map == null) {
                continue;
            }
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Collection<IRI> objects = map.get(predicate);
                if (objects == null) {
                    continue;
                }
                for (IRI object : new ArrayList<IRI>(objects)) {
                    iterator.handleResourceTriple(subject, predicate, object);
                }
            }
        }
    }

    /**
     * Iterate literal triples.
     * 
     * @param <E>
     *        the element type
     * @param iterator
     *        the iterator
     * @throws E
     *         the e
     */
    protected <E extends Throwable> void iterateLiteralTriples(
            LiteralTripleIterator<E> iterator) throws E {
        for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject
                    .get(subject);
            if (map == null) {
                continue;
            }
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Collection<OWLLiteral> objects = map.get(predicate);
                for (OWLLiteral object : new ArrayList<OWLLiteral>(objects)) {
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
    // Subject, predicate, object
    /** The res triples by subject. */
    private Map<IRI, Map<IRI, Collection<IRI>>> resTriplesBySubject = CollectionFactory
            .createMap();
    // Predicate, subject, object
    /** The single valued res triples by predicate. */
    private Map<IRI, Map<IRI, IRI>> singleValuedResTriplesByPredicate = CollectionFactory
            .createMap();
    // Literal triples
    /** The lit triples by subject. */
    private Map<IRI, Map<IRI, Collection<OWLLiteral>>> litTriplesBySubject = CollectionFactory
            .createMap();
    // Predicate, subject, object
    /** The single valued lit triples by predicate. */
    private Map<IRI, Map<IRI, OWLLiteral>> singleValuedLitTriplesByPredicate = CollectionFactory
            .createMap();

    /**
     * Adds the triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    public void addTriple(IRI subject, IRI predicate, IRI object) {
        Map<IRI, IRI> subjObjMap = singleValuedResTriplesByPredicate
                .get(predicate);
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
                objects = new HashSet<IRI>();
                map.put(predicate, objects);
            }
            objects.add(object);
        }
    }

    /**
     * Adds the triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param con
     *        the con
     */
    public void addTriple(IRI subject, IRI predicate, OWLLiteral con) {
        Map<IRI, OWLLiteral> subjObjMap = singleValuedLitTriplesByPredicate
                .get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, con);
        } else {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject
                    .get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                litTriplesBySubject.put(subject, map);
            }
            Collection<OWLLiteral> objects = map.get(predicate);
            if (objects == null) {
                objects = new HashSet<OWLLiteral>();
                map.put(predicate, objects);
            }
            objects.add(con);
        }
    }
}
