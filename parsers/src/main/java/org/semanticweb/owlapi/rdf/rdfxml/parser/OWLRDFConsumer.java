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

import static org.semanticweb.owlapi.model.MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.AnnotationResourceTripleHandler;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.InverseOfHandler;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.ObjectPropertyAssertionHandler;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.TypeHandler;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.getLiteralTripleHandlers;
import static org.semanticweb.owlapi.rdf.rdfxml.parser.AbstractTriplePH.getPredicateHandlers;
import static org.semanticweb.owlapi.util.CollectionFactory.createLinkedSet;
import static org.semanticweb.owlapi.util.CollectionFactory.createList;
import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.vocab.Namespaces.SWRL;
import static org.semanticweb.owlapi.vocab.Namespaces.SWRLB;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.BUILT_IN_AP_IRIS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASSERTION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATATYPE_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISTINCT_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PREDICATE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_CHAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SUBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_VALUE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_WITH_RESTRICTIONS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_OBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_PREDICATE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_SUBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.isEntityTypeIRI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.RDFDocumentFormat;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserParameters;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFOntologyHeaderStatus;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
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
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.providers.AnonymousIndividualByIdProvider;
import org.semanticweb.owlapi.rdf.rdfxml.parser.Translators.TranslatorAccessor;
import org.semanticweb.owlapi.util.AnonymousNodeChecker;
import org.semanticweb.owlapi.util.AnonymousNodeCheckerImpl;
import org.semanticweb.owlapi.util.RemappingIndividualProvider;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A parser/interpreter for an RDF graph which represents an OWL ontology. The consumer interprets
 * triple patterns in the graph to produce the appropriate OWLAPI entities, class expressions and
 * axioms. The parser is based on triple handlers. A given triple handler handles a specific type of
 * triple. Generally speaking this is based on the predicate of a triple, for example, A
 * rdfs:subClassOf B is handled by a subClassOf handler. A handler determines if it can handle a
 * triple in a streaming mode (i.e. while parsing is taking place) or if it can handle a triple
 * after parsing has taken place and the complete graph is in memory. Once a handler handles a
 * triple, that triple is deemed to have been consumed an is discarded. The parser attempts to
 * consume as many triples as possible while streaming parsing is taking place. Whether or not a
 * triple can be consumed during parsing is determined by installed triple handlers.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLRDFConsumer implements RDFConsumer, AnonymousIndividualByIdProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(OWLRDFConsumer.class);
    private static final AtomicInteger ERRORCOUNTER = new AtomicInteger(0);
    protected final AnonymousNodeChecker anon;
    protected final SWRLPieces swrlPieces = new SWRLPieces();
    /**
     * Handler for triples that denote nodes which represent axioms. i.e. owl:AllDisjointClasses
     * owl:AllDisjointProperties owl:AllDifferent owl:NegativePropertyAssertion owl:Axiom These need
     * to be handled separately from other types, because the base triples for annotated axioms
     * should be in the ontology before annotations on the annotated versions of these axioms are
     * parsed.
     */
    protected final Map<IRI, BuiltInTypeHandler> axiomTypes;
    /**
     * Handlers for build in predicates
     */
    protected final Map<IRI, TriplePredicateHandler> predicates;
    /**
     * Handlers for general literal triples (i.e. triples which have predicates that are not part of
     * the built in OWL/RDFS/RDF vocabulary. Such triples either constitute annotationIRIs of
     * relationships between an individual and a data literal (typed or untyped)
     */
    protected final List<LiteralTripleHandler> literals;
    /**
     * Handlers for general resource triples (i.e. triples which have predicates that are not part
     * of the built in OWL/RDFS/RDF vocabulary. Such triples either constitute annotationIRIs or
     * relationships between an individual and another individual.
     */
    protected final List<ResourceTripleHandler> resources;
    final TripleLogger tripleLogger = new TripleLogger();
    final TranslatorAccessor translatorAccessor;
    private final Set<IRI> axioms = createSet();
    /**
     * The shared anonymous nodes.
     */
    private final Map<IRI, Object> sharedAnonymousNodes = createMap();
    /**
     * The pending annotations.
     */
    private final Set<OWLAnnotation> pendingAnnotations = createSet();
    // SWRL Stuff
    /**
     * The annotated anon source2 annotation map.
     */
    private final AnonMap anonWithAnnotations = new AnonMap();
    protected final OWLParserParameters parseParameters;
    /**
     * The data factory.
     */
    private final OWLDataFactory df;
    /**
     * The synonym map.
     */
    private final SynonymMap synonymMap;
    /**
     * Memory optimization: cache annotation axioms to be added at the end
     */
    private final Collection<OWLAnnotationAxiom> parsedAnnotationAxioms = new ArrayList<>();
    /**
     * The axioms to be removed.
     */
    private final Collection<OWLAxiom> axiomsToBeRemoved = new ArrayList<>();
    /**
     * The translated properties.
     */
    private final Map<IRI, OWLObjectPropertyExpression> translatedProperties = createMap();
    private final Map<IRI, IRI> remappedIRIs = createMap();
    private final Map<String, IRI> remappedIRIStrings = createMap();
    // Caching IRIs here helps save memory. This cache is local to a particular
    // consumer, so the cache size itself is much smaller than the total memory
    // footprint
    private final Map<String, IRI> iriMap = createMap();
    /**
     * Handlers for built in types
     */
    private final Map<IRI, BuiltInTypeHandler> builtInTypes;
    protected FoundIRIs iris;
    protected ListTriples lists = new ListTriples();
    // Resource triples
    protected TripleIndex tripleIndex = new TripleIndex(tripleLogger);
    protected boolean axiomParsingMode = false;
    /**
     * The ontology format.
     */
    @Nullable
    private RDFDocumentFormat ontologyFormat;
    /**
     * The last added axiom.
     */
    @Nullable
    private OWLAxiom lastAddedAxiom;
    /**
     * The iri provider.
     */
    @Nullable
    private IRIProvider iriProvider;
    /**
     * The parsed all triples.
     */
    private boolean parsedAllTriples = false;
    private RemappingIndividualProvider anonProvider;

    /**
     * @param p parser parameters
     * @param iriProvider iri provider
     */
    public OWLRDFConsumer(OWLParserParameters p, @Nullable IRIProvider iriProvider) {
        this(p, new AnonymousNodeCheckerImpl(), iriProvider);
    }

    /**
     * @param p parser parameters
     * @param checker anonymous node checker
     * @param iriProvider iri provider
     */
    public OWLRDFConsumer(OWLParserParameters p, AnonymousNodeChecker checker,
        @Nullable IRIProvider iriProvider) {
        parseParameters = p;
        anon = checker;
        OWLOntologyManager m = ont().getOWLOntologyManager();
        df = m.getOWLDataFactory();
        anonProvider = new RemappingIndividualProvider(m.getOntologyConfigurator(), df);
        iris = new FoundIRIs(strict());
        builtInTypes = AbstractBuiltInTypeHandler.handlers(strict());
        axiomTypes = AbstractBuiltInTypeHandler.axioms();
        predicates = getPredicateHandlers();
        literals = getLiteralTripleHandlers();
        // General resource/object triples - i.e. triples which have a
        // predicate that is not a built in IRI. Annotation properties get
        // precedence over object properties, so that if we have
        // a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the
        // statement will be translated as an annotation on a:A
        resources = Arrays.asList(ObjectPropertyAssertionHandler, AnnotationResourceTripleHandler);
        translatorAccessor = new TranslatorAccessor(this);
        synonymMap = new SynonymMap(strict());

        // Cache anything in the existing imports closure
        iris.importsClosureChanged(ont());
        ont().getOntologyID().getOntologyIRI().ifPresent(iris::addOntology);
        this.iriProvider = iriProvider;
    }

    protected OWLOntology ont() {
        return parseParameters.getOntology();
    }

    protected boolean strict() {
        return parseParameters.getConfig().shouldParseWithStrictConfiguration();
    }

    protected static boolean isGeneralPredicate(IRI predicate) {
        return !predicate.isReservedVocabulary() || BUILT_IN_AP_IRIS.contains(predicate)
            || SWRL.inNamespace(predicate) || SWRLB.inNamespace(predicate);
    }

    @Override
    public void addPrefix(String abbreviation, String value) {
        ont().getPrefixManager().setPrefix(abbreviation, value);
    }

    /**
     * Gets the ontology format.
     *
     * @return the ontology format
     */
    public RDFDocumentFormat getOntologyFormat() {
        return verifyNotNull(ontologyFormat, "ontology format has not been set yet");
    }

    /**
     * Sets the ontology format.
     *
     * @param format the new ontology format
     */
    public void setOntologyFormat(RDFDocumentFormat format) {
        ontologyFormat = format;
        tripleLogger.setPrefixManager(ont().getPrefixManager());
    }

    /**
     * Gets the data factory.
     *
     * @return the data factory
     */
    public OWLDataFactory getDataFactory() {
        return df;
    }

    /**
     * Gets any annotations that were translated since the last call of this method (calling this
     * method clears the current pending annotations).
     *
     * @return The set (possibly empty) of pending annotations.
     */
    public Set<OWLAnnotation> pendingAnns() {
        if (pendingAnnotations.isEmpty()) {
            return Collections.emptySet();
        }
        Set<OWLAnnotation> annos = new LinkedHashSet<>(pendingAnnotations);
        pendingAnnotations.clear();
        return annos;
    }

    @Nullable
    private IRI getNullableIRI(@Nullable String s) {
        if (s == null) {
            return null;
        }
        return getIRI(s);
    }

    private IRI getIRI(String s) {
        checkNotNull(s, "s cannot be null");
        IRI iri = null;
        if (iriProvider != null) {
            iri = iriProvider.getIRI(s);
        }
        if (iri != null) {
            return iri;
        }
        return iriMap.computeIfAbsent(s, IRI::create);
    }

    /**
     * Adds the shared anonymous node.
     *
     * @param iri the iri
     * @param translation the translation
     */
    protected void addSharedAnonymousNode(IRI iri, Object translation) {
        sharedAnonymousNodes.put(iri, translation);
    }

    /**
     * Gets the shared anonymous node.
     *
     * @param iri the iri
     * @return the shared anonymous node
     */
    protected Object getSharedAnonymousNode(IRI iri) {
        return sharedAnonymousNodes.get(iri);
    }

    /**
     * Adds the axiom.
     *
     * @param axiom the axiom
     */
    protected void add(OWLAxiom axiom) {
        if (axiom.isAnnotationAxiom()) {
            if (getConfiguration().shouldLoadAnnotations()) {
                parsedAnnotationAxioms.add((OWLAnnotationAxiom) axiom);
            }
        } else {
            ont().add(axiom);
        }
        lastAddedAxiom = axiom;
    }

    /**
     * Marks an axioms for removal at the end of parsing. This is usually used for annotated axioms,
     * since the RDF serialization spec mandates that a "base" triple must be included on
     * serialization.
     *
     * @param axiom The axiom to be removed.
     */
    protected void removeAxiom(OWLAxiom axiom) {
        axiomsToBeRemoved.add(axiom);
    }

    /**
     * Check for and process annotated declaration.
     *
     * @param n the main node
     */
    protected void checkForAndProcessAnnotatedDeclaration(IRI n) {
        IRI property = tripleIndex.resource(n, OWL_ANNOTATED_PROPERTY, false);
        if (!RDF_TYPE.getIRI().equals(property)) {
            return;
        }
        IRI object = tripleIndex.resource(n, OWL_ANNOTATED_TARGET, false);
        if (object == null) {
            return;
        }
        IRI subject = tripleIndex.resource(n, OWL_ANNOTATED_SOURCE, false);
        if (subject == null) {
            return;
        }
        if (isEntityTypeIRI(object)) {
            // This will add and record the declaration for us
            handleNonStream(subject, RDF_TYPE.getIRI(), object);
        }
    }

    /**
     * Sets the ontology id.
     *
     * @param ontologyID the new ontology id
     */
    protected void setOntologyID(OWLOntologyID ontologyID) {
        ont().applyChange(new SetOntologyID(ont(), ontologyID));
    }

    /**
     * Adds the ontology annotation.
     *
     * @param annotation the annotation
     */
    protected void addOntologyAnnotation(OWLAnnotation annotation) {
        ont().applyChange(new AddOntologyAnnotation(ont(), annotation));
    }

    protected void addImport(OWLImportsDeclaration declaration, IRI o) {
        ont().applyChange(new AddImport(ont(), declaration));
        if (!getConfiguration().isImportIgnored(o)) {
            OWLOntologyManager man = ont().getOWLOntologyManager();
            man.makeLoadImportRequest(declaration, getConfiguration());
            handleImportingRDFGraphRatherThanOntology(declaration, man,
                man.getImportedOntology(declaration));
            iris.importsClosureChanged(ont());
        }
    }

    protected void handleImportingRDFGraphRatherThanOntology(OWLImportsDeclaration id,
        OWLOntologyManager man, @Nullable OWLOntology io) {
        if (io == null) {
            return;
        }
        if (io.isAnonymous()
            && getConfiguration().getMissingOntologyHeaderStrategy() == INCLUDE_GRAPH) {
            // We should have just included the triples rather than imported
            // them. So, we remove the imports statement, add the axioms
            // from the imported ontology to out importing ontology and
            // remove the imported ontology.
            // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
            man.applyChange(new RemoveImport(ont(), id));
            io.importsDeclarations().forEach(d -> man.applyChange(new AddImport(ont(), d)));
            io.annotations().forEach(ann -> man.applyChange(new AddOntologyAnnotation(ont(), ann)));
            io.axioms().forEach(this::add);
            man.removeOntology(io);
        }
    }

    @Nullable
    protected OWLAxiom getLastAddedAxiom() {
        return lastAddedAxiom;
    }

    // Helper methods for creating entities
    protected OWLClass owlClass(IRI iri) {
        return df.getOWLClass(iri);
    }

    protected OWLObjectProperty objectProperty(IRI iri) {
        return df.getOWLObjectProperty(iri);
    }

    protected OWLDataProperty dp(IRI iri) {
        return df.getOWLDataProperty(iri);
    }

    protected OWLIndividual individual(IRI iri) {
        if (anon.isAnonymousNode(iri)) {
            return getOWLAnonymousIndividual(iri.toString());
        }
        return df.getOWLNamedIndividual(iri);
    }

    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        return anonProvider.getOWLAnonymousIndividual(nodeId);
    }

    // SWRL Stuff
    // RDFConsumer implementation
    @Override
    public void startModel(IRI physicalURI) {
        // nothing to do here
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
    public void endModel() {
        parsedAllTriples = true;
        // We are now left with triples that could not be consumed during
        // streaming parsing
        iriMap.clear();
        tripleLogger.logNumberOfTriples();
        translatorAccessor.consumeSWRLRules(swrlPieces.swrlRules);
        Set<RDFTriple> remainingTriples = mopUp();
        setParserMetadata(remainingTriples);
        // Do we need to change the ontology IRI?
        chooseAndSetOntologyIRI();
        TripleLogger.logOntologyID(ont().getOntologyID());
        tripleIndex.dumpRemainingTriples();
        cleanup();
        addAnnotationAxioms();
        removeAxiomsScheduledForRemoval();
    }

    protected void setParserMetadata(Set<RDFTriple> triples) {
        if (ontologyFormat != null) {
            RDFParserMetaData loaderMetaData =
                new RDFParserMetaData(RDFOntologyHeaderStatus.PARSED_ONE_HEADER,
                    tripleLogger.count(), triples, iris.guessedDeclarations);
            errors.forEach(loaderMetaData::addError);
            parseParameters.withLoaderMetaData(loaderMetaData);
        }
    }

    private void addAnnotationAxioms() {
        ont().add(parsedAnnotationAxioms);
    }

    private void removeAxiomsScheduledForRemoval() {
        ont().remove(axiomsToBeRemoved);
    }

    /**
     * Selects an IRI that should be used as the IRI of the parsed ontology, or {@code null} if the
     * parsed ontology does not have an IRI
     */
    private void chooseAndSetOntologyIRI() {
        Optional<IRI> ontologyIRIToSet = emptyOptional();
        if (iris.firstOntologyIRI != null) {
            removeUnnecessaryAxioms();
        }
        if (iris.ontologyIRIs.isEmpty()) {
            // No ontology IRIs
            // We used to use the xml:base here. But this is probably incorrect
            // for OWL 2 now.
        } else if (iris.ontologyIRIs.size() == 1) {
            // Exactly one ontologyIRI
            IRI ontologyIRI = iris.ontologyIRIs.iterator().next();
            if (!anon.isAnonymousNode(ontologyIRI)) {
                ontologyIRIToSet = optional(ontologyIRI);
            }
        } else {
            // We have multiple to choose from
            // Choose one that isn't the object of an annotation assertion
            Set<IRI> candidateIRIs = createSet(iris.ontologyIRIs);
            ont().annotations().forEach(a -> a.getValue().asIRI().ifPresent(iri -> {
                if (iris.ontologyIRIs.contains(iri)) {
                    candidateIRIs.remove(iri);
                }
            }));
            // Choose the first one parsed
            if (candidateIRIs.contains(iris.firstOntologyIRI)) {
                ontologyIRIToSet = optional(iris.firstOntologyIRI);
            } else if (!candidateIRIs.isEmpty()) {
                // Just pick any
                ontologyIRIToSet = optional(candidateIRIs.iterator().next());
            }
        }
        if (ontologyIRIToSet.isPresent() && !NodeID.isAnonymousNodeIRI(ontologyIRIToSet.get())) {
            Optional<IRI> versionIRI = ont().getOntologyID().getVersionIRI();
            OWLOntologyID ontologyID = new OWLOntologyID(ontologyIRIToSet, versionIRI);
            ont().applyChange(new SetOntologyID(ont(), ontologyID));
        }
    }

    protected void removeUnnecessaryAxioms() {
        IRI iri = verifyNotNull(iris.firstOntologyIRI);
        asList(ont().annotationAssertionAxioms(iri)).forEach(this::removeUnnecessaryAxiom);
        asList(parsedAnnotationAxioms.stream().filter(this::annotationToRemove))
            .forEach(this::removeUnnecessaryParsedAnnotations);
    }

    protected boolean annotationToRemove(OWLAnnotationAxiom ax) {
        return ax instanceof OWLAnnotationAssertionAxiom
            && ((OWLAnnotationAssertionAxiom) ax).getSubject().equals(iris.firstOntologyIRI);
    }

    protected void removeUnnecessaryParsedAnnotations(OWLAnnotationAxiom ax) {
        addOntologyAnnotation(((OWLAnnotationAssertionAxiom) ax).getAnnotation());
        if (ax.annotations().count() == 0) {
            // axioms with annotations must be preserved, axioms without
            // annotations do not need to be preserved
            // (they exist because of triple ordering in ontology declaration
            // and annotation)
            parsedAnnotationAxioms.remove(ax);
        }
    }

    protected void removeUnnecessaryAxiom(OWLAnnotationAssertionAxiom ax) {
        addOntologyAnnotation(ax.getAnnotation());
        if (ax.annotations().count() == 0) {
            // axioms with annotations must be preserved,
            // axioms without annotations do not need to be preserved
            // (they exist because of triple ordering in ontology
            // declaration and annotation)
            ont().remove(ax);
        }
    }

    private void cleanup() {
        iris.clear();
        lists.clear();
        // XXX clean new members
        translatorAccessor.cleanup();
        tripleIndex.clear();
    }

    @Override
    public void includeModel(@Nullable String logicalURI, @Nullable String physicalURI) {
        // XXX should this do nothing?
    }

    @Override
    public void logicalURI(IRI logicalURI) {
        // XXX what is the purpose of this?
    }

    @Override
    public void statementWithLiteralValue(String subject, String predicate, String object,
        @Nullable String language, @Nullable String datatype) {
        statementWithLiteralValue(getIRI(remapOnlyIfRemapped(subject)), getIRI(predicate), object,
            language, getNullableIRI(datatype));
    }

    @Override
    public void statementWithLiteralValue(IRI subject, IRI predicate, String object,
        @Nullable String language, @Nullable IRI datatype) {
        tripleLogger.logTripleAtDebug(subject, predicate, object, language, datatype);
        OWLLiteral literal = getOWLLiteral(object, datatype, language);
        // Convert all literals to OWLLiterals
        if (!literals.stream().anyMatch(x -> handleStreamConditionally(x, subject,
            synonymMap.getSynonym(predicate), literal))) {
            tripleIndex.addTriple(subject, synonymMap.getSynonym(predicate), literal);
        }
    }

    @Override
    public void statementWithResourceValue(String subject, String predicate, String object) {
        statementWithResourceValue(getIRI(subject), getIRI(predicate), getIRI(object));
    }

    @Override
    public void statementWithResourceValue(IRI subject, IRI predicate, IRI object) {
        tripleLogger.logTripleAtDebug(subject, predicate, object);
        IRI p = synonymMap.getSynonym(predicate);
        IRI o = synonymMap.getSynonym(object);
        boolean consumed = false;
        if (RDF_TYPE.getIRI().equals(p)) {
            BuiltInTypeHandler handler = builtInTypes.get(o);
            if (handler != null) {
                consumed = handleStreamConditionally(handler, subject, p, o);
            } else if (axiomTypes.get(o) == null) {
                // Not a built in type
                iris.addOWLNamedIndividual(subject, false);
                consumed = handleStreamConditionally(TypeHandler, subject, p, o);
            } else {
                addAxiom(subject);
            }
        } else {
            ResourceTripleHandler handler = predicates.get(p);
            if (handler != null) {
                consumed = handleStreamConditionally(handler, subject, p, o);
            } else {
                consumed =
                    resources.stream().anyMatch(x -> handleStreamConditionally(x, subject, p, o));
            }
        }
        if (!consumed) {
            // Not consumed, so add the triple
            tripleIndex.addTriple(subject, p, o);
        }
    }

    /**
     * A convenience method to obtain an {@code OWLLiteral}.
     *
     * @param literal The literal
     * @param datatype The data type
     * @param lang The lang
     * @return The {@code OWLLiteral} (either typed or untyped depending on the params)
     */
    OWLLiteral getOWLLiteral(String literal, @Nullable IRI datatype, @Nullable String lang) {
        if (lang != null && !lang.trim().isEmpty()) {
            return df.getOWLLiteral(literal, lang);
        } else if (datatype != null) {
            return df.getOWLLiteral(literal, df.getOWLDatatype(datatype));
        } else {
            return df.getOWLLiteral(literal);
        }
    }

    /**
     * Given a main node, translated data ranges according to Table 12.
     *
     * @param n The main node
     * @return The translated data range. If the data range could not be translated then an
     *         OWLDatatype with the given IRI is returned.
     */
    public OWLDataRange translateDataRange(IRI n) {
        if (!iris.isDataRange(n) && strict()) {
            // Can't translated ANY according to Table 12
            return generateAndLogParseError(EntityType.DATATYPE, n);
        }
        if (!anon.isAnonymousNode(n) && iris.isDataRange(n)) {
            return df.getOWLDatatype(n);
        }
        IRI and = tripleIndex.resource(n, OWL_INTERSECTION_OF, true);
        if (and != null) {
            return df.getOWLDataIntersectionOf(translatorAccessor.translateToDataRangeSet(and));
        }
        IRI or = tripleIndex.resource(n, OWL_UNION_OF, true);
        if (or != null) {
            return df.getOWLDataUnionOf(translatorAccessor.translateToDataRangeSet(or));
        }
        // The plain complement of triple predicate is in here for legacy
        // reasons
        IRI not = tripleIndex.resource(n, OWL_DATATYPE_COMPLEMENT_OF, true);
        if (!strict() && not == null) {
            not = tripleIndex.resource(n, OWL_COMPLEMENT_OF, true);
        }
        if (not != null) {
            return df.getOWLDataComplementOf(translateDataRange(not));
        }
        IRI oneOfObject = tripleIndex.resource(n, OWL_ONE_OF, true);
        if (oneOfObject != null) {
            return df.getOWLDataOneOf(translatorAccessor.translateToConstantSet(oneOfObject));
        }
        IRI onDt = tripleIndex.resource(n, OWL_ON_DATA_TYPE, true);
        if (onDt != null) {
            if (anon.isAnonymousNode(onDt)) {
                // TODO LOG ERROR
                return df.getOWLDatatype(n);
            }
            OWLDatatype dt = (OWLDatatype) translateDataRange(onDt);
            // Now we have to get the restricted facets - there is some legacy
            // translation code here... the current
            // spec uses a list of triples where the predicate is a facet and
            // the object a literal that is restricted
            // by the facet. Originally, there just used to be multiple
            // facet-"facet value" triples
            IRI facets = tripleIndex.resource(n, OWL_WITH_RESTRICTIONS, true);
            if (facets != null) {
                return df.getOWLDatatypeRestriction(dt,
                    translatorAccessor.translateToFacetRestrictionSet(facets));
            } else if (!strict()) {
                Collection<OWLFacetRestriction> restrictions = createLinkedSet();
                // Try the legacy encoding
                Stream.of(OWLFacet.values())
                    .forEach(f -> consume(() -> tripleIndex.literal(n, f.getIRI(), true),
                        val -> restrictions.add(df.getOWLFacetRestriction(f, val))));
                return df.getOWLDatatypeRestriction(dt, restrictions);
            }
        }
        // Could not translated ANYTHING!
        return generateAndLogParseError(EntityType.DATATYPE, n);
    }

    // Basic node translation - translation of entities

    protected <T> void consume(Supplier<T> next, Consumer<T> use) {
        for (T t = next.get(); t != null; t = next.get()) {
            use.accept(t);
        }
    }

    /**
     * Translate object property expression.
     *
     * @param mainNode the main node
     * @return the oWL object property expression
     */
    public OWLObjectPropertyExpression translateOPE(IRI mainNode) {
        return translatedProperties.computeIfAbsent(mainNode, this::buildOPE);
    }

    protected OWLObjectPropertyExpression buildOPE(IRI mainNode) {
        if (!anon.isAnonymousNode(mainNode)) {
            // Simple object property
            iris.addObjectProperty(mainNode, false);
            return objectProperty(mainNode);
        }
        // Inverse of a property expression
        IRI inverseOfObject = tripleIndex.resource(mainNode, OWL_INVERSE_OF, true);
        if (inverseOfObject != null) {
            OWLObjectPropertyExpression otherProperty = ensureNoNestedAnons(inverseOfObject);
            iris.addObjectProperty(mainNode, false);
            return df.getOWLObjectInverseOf(otherProperty.asOWLObjectProperty());
        }
        iris.addObjectProperty(mainNode, false);

        return df.getOWLObjectInverseOf(objectProperty(mainNode));
    }

    protected OWLObjectPropertyExpression ensureNoNestedAnons(IRI inverseOfObject) {
        OWLObjectPropertyExpression otherProperty = translateOPE(inverseOfObject);
        if (otherProperty.isAnonymous()) {
            throw new OWLRuntimeException(
                "Found nested object property expression but only object property allowed in inverseOf construct");
        }
        return otherProperty;
    }

    /**
     * Translates the annotation on a main node. Triples whose subject is the specified main node
     * and whose subject is typed an an annotation property (or is a built in annotation property)
     * will be translated to annotation on this main node.
     *
     * @param n The main node
     * @return The set of annotations on the main node
     */
    public List<OWLAnnotation> pendingAnns(IRI n) {
        // Are we the subject of an annotation? If so, we need to ensure that
        // the annotations annotate us. This
        // will only happen if we are an annotation!
        MultiMap<IRI, OWLAnnotation> anns = new MultiMap<>(x -> createList());
        anonWithAnnotations.consumeAnnotatedSource(n, x -> anns.put(x, pendingAnns(x)));
        List<OWLAnnotation> nodeAnns = new ArrayList<>();
        anns.allValues(x -> x.getProperty().getIRI())
            .forEach(p -> mapAnnotation(n, anns, nodeAnns, p));
        tripleIndex.getPredicatesBySubject(n).filter(iris::isAP)
            .forEach(p -> mapAnnotation(n, anns, nodeAnns, p));
        return nodeAnns;
    }

    protected void processPredicates(IRI n, Collection<OWLAnnotation> anns, IRI i) {
        if (iris.isAP(i)) {
            consume(() -> tripleIndex.literal(n, i, true),
                l -> anns.add(df.getOWLAnnotation(ap(i), l)));
        }
    }

    protected OWLAnnotationProperty ap(IRI i) {
        return df.getOWLAnnotationProperty(i);
    }

    protected void mapAnnotation(IRI n, MultiMap<IRI, OWLAnnotation> anns,
        Collection<OWLAnnotation> dest, IRI p) {
        OWLAnnotationProperty ap = ap(p);
        consume(() -> tripleIndex.literal(n, p, true), l -> dest.add(df.getOWLAnnotation(ap, l,
            anns.get(getSubjectForAnnotatedPropertyAndObject(n, p, l)))));
        consume(() -> tripleIndex.resource(n, p, true), r -> dest.add(df.getOWLAnnotation(ap,
            getAnnotationValue(r), anns.get(getSubjectForAnnotatedPropertyAndObject(n, p, r)))));
    }

    @Nullable
    private IRI getSubjectForAnnotatedPropertyAndObject(IRI n, IRI p, OWLLiteral v) {
        return anonWithAnnotations.getAnnotatedSource(n,
            i -> annotatedProperty(p, i) && annotatedTarget(v, i));
    }

    @Nullable
    private IRI getSubjectForAnnotatedPropertyAndObject(IRI n, IRI p, IRI v) {
        return anonWithAnnotations.getAnnotatedSource(n,
            i -> annotatedProperty(p, i) && annotatedTarget(v, i));
    }

    protected boolean annotatedTarget(OWLLiteral v, IRI i) {
        return v.equals(tripleIndex.literal(i, OWL_ANNOTATED_TARGET.getIRI(), false));
    }

    protected boolean annotatedTarget(IRI v, IRI i) {
        return v.equals(tripleIndex.resource(i, OWL_ANNOTATED_TARGET.getIRI(), false));
    }

    protected boolean annotatedProperty(IRI p, IRI i) {
        return p.equals(tripleIndex.resource(i, OWL_ANNOTATED_PROPERTY, false));
    }

    private OWLAnnotationValue getAnnotationValue(IRI resVal) {
        return anon.isAnonymousNode(resVal) ? df.getOWLAnonymousIndividual(resVal.toString())
            : resVal;
    }

    private <E extends OWLEntity> E getErrorEntity(EntityType<E> type) {
        IRI iri = IRI.create("http://org.semanticweb.owlapi/error#",
            "Error" + ERRORCOUNTER.incrementAndGet());
        String message = "Entity not properly recognized, missing triples in input? " + iri
            + " for type " + type;
        LOGGER.error(message);
        if (strict()) {
            throw new OWLParserException(message);
        }
        return df.getOWLEntity(type, iri);
    }

    private RDFResource getRDFResource(IRI iri) {
        return anon.isAnonymousNode(iri)
            ? new RDFResourceBlankNode(iri, false, false, tripleIndex.isAxiom(iri))
            : new RDFResourceIRI(iri);
    }

    private RDFTriple getRDFTriple(IRI s, IRI p, IRI o) {
        return new RDFTriple(getRDFResource(s), new RDFResourceIRI(p), getRDFResource(o));
    }

    private RDFTriple getRDFTriple(IRI s, IRI p, OWLLiteral o) {
        return new RDFTriple(getRDFResource(s), new RDFResourceIRI(p), new RDFLiteral(o));
    }

    private Set<RDFTriple> getTriplesForMainNode(IRI n, IRI... augmentingTypes) {
        Set<RDFTriple> triples = createLinkedSet();
        tripleIndex.getPredicatesBySubject(n).forEach(p -> {
            tripleIndex.getResourceObjects(n, p).forEach(o -> triples.add(getRDFTriple(n, p, o)));
            tripleIndex.getLiteralObjects(n, p)
                .forEach(object -> triples.add(getRDFTriple(n, p, object)));
        });
        Stream.of(augmentingTypes).forEach(t -> triples.add(getRDFTriple(n, RDF_TYPE.getIRI(), t)));
        return triples;
    }

    private List<RDFResourceParseError> errors = new ArrayList<>();

    private void logError(RDFResourceParseError error) {
        errors.add(error);
    }

    /**
     * @param entityType entity type
     * @param mainNode main node
     * @param <E> entity type
     * @return error entity
     */
    public <E extends OWLEntity> E generateAndLogParseError(EntityType<E> entityType,
        IRI mainNode) {
        E entity = getErrorEntity(entityType);
        logError(new RDFResourceParseError(entity, getRDFResource(mainNode),
            getTriplesForMainNode(mainNode)));
        return entity;
    }

    protected boolean addFirst(IRI s, IRI p, IRI o) {
        lists.addFirst(s, p, o);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean addFirst(IRI s, IRI p, OWLLiteral o) {
        lists.addFirst(s, p, o);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected void addAxiom(IRI axiomIRI) {
        axioms.add(axiomIRI);
    }

    protected boolean isAxiom(IRI iri) {
        return axioms.contains(iri);
    }

    @Override
    public OntologyConfigurator getConfiguration() {
        return parseParameters.getConfig();
    }

    @Override
    public IRI remapIRI(IRI i) {
        if (anon.isAnonymousNode(i)) {
            // blank nodes do not need to be remapped in this method
            return i;
        }
        IRI computeIfAbsent =
            remappedIRIs.computeIfAbsent(i, x -> IRI.create(NodeID.nextAnonymousIRI()));
        remappedIRIStrings.put(i.toString(), computeIfAbsent);
        return computeIfAbsent;
    }

    @Override
    public String remapOnlyIfRemapped(String i) {
        if (anon.isAnonymousNode(i) || anon.isAnonymousSharedNode(i)) {
            // blank nodes do not need to be remapped in this method
            return i;
        }
        IRI iri = remappedIRIStrings.get(i);
        return iri == null ? i : iri.toString();
    }


    protected OWLAnnotationSubject getSubject(IRI s) {
        return anon.isAnonymousNode(s) ? getOWLAnonymousIndividual(s.toString()) : s;
    }

    protected Collection<OWLDataPropertyExpression> dps(IRI listNode) {
        return translatorAccessor.translateToDataPropertyList(listNode);
    }

    protected Collection<OWLObjectPropertyExpression> ops(IRI listNode) {
        return translatorAccessor.translateToObjectPropertyList(listNode);
    }

    protected boolean isStrict() {
        return getConfiguration().shouldParseWithStrictConfiguration();
    }

    protected OWLClassExpression ce(IRI iri) {
        return translatorAccessor.translateClassExpression(iri);
    }

    protected boolean canHandleEquivalentClass(IRI s) {
        return !anon.isAnonymousNode(s);
    }


    protected boolean isCeLax(IRI mainNode) {
        return iris.isClassExpression(mainNode)
            || isParsedAllTriples() && !iris.isDataRange(mainNode);
    }

    protected boolean isClassExpressionStrict(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isClassExpressionStrict(o);
    }

    protected boolean isClassExpressionLax(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && isCeLax(o);
    }

    protected boolean isObjectPropertyStrict(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isObjectPropertyStrict(o);
    }

    protected boolean isObjectPropertyLax(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isOpLax(o);
    }

    protected boolean isDataPropertyStrict(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isDataPropertyStrict(o);
    }

    protected boolean isDPLax(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isDPLax(o);
    }

    protected boolean isDataRangeStrict(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return iris.isDataRangeStrict(o);
    }

    protected boolean isDataRangeLax(IRI mainNode, OWLRDFVocabulary p) {
        IRI o = tripleIndex.resource(mainNode, p, false);
        return o != null && iris.isDrLax(mainNode);
    }

    protected boolean isClassExpressionListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, iris::isClassExpressionStrict, minSize);
    }

    protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, iris::isDataRangeStrict, minSize);
    }

    protected boolean isIndividualListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, node -> true, minSize);
    }

    protected boolean isResourceListStrict(@Nullable IRI mainNode, TypeMatcher typeMatcher,
        int minSize) {
        if (mainNode == null) {
            return false;
        }
        IRI currentListNode = mainNode;
        Set<IRI> visitedListNodes = new HashSet<>();
        int size = 0;
        while (true) {
            IRI firstObject = tripleIndex.resource(currentListNode, RDF_FIRST, false);
            if (firstObject == null) {
                return false;
            }
            if (!typeMatcher.isTypeStrict(firstObject)) {
                // Something in the list that is not of the required type
                return false;
            } else {
                size++;
            }
            IRI restObject = tripleIndex.resource(currentListNode, RDF_REST, false);
            if (visitedListNodes.contains(restObject)) {
                // Cycle - Non-terminating
                return false;
            }
            if (restObject == null) {
                // Not terminated properly
                return false;
            }
            if (restObject.equals(RDF_NIL.getIRI())) {
                // Terminated properly
                return size >= minSize;
            }
            // Carry on
            visitedListNodes.add(restObject);
            currentListNode = restObject;
        }
    }

    protected boolean eitherAnon(IRI s, IRI o) {
        return anon.isAnonymousNode(s) || anon.isAnonymousNode(o);
    }

    protected SWRLIArgument translateSWRLAtomIObject(IRI mainIRI, IRI argPredicateIRI) {
        IRI argIRI = tripleIndex.resource(mainIRI, argPredicateIRI, true);
        if (argIRI != null) {
            if (swrlPieces.isSWRLVariable(argIRI)) {
                return df.getSWRLVariable(argIRI);
            }
            return df.getSWRLIndividualArgument(individual(argIRI));
        }
        throw new OWLRuntimeException(
            "Cannot translate SWRL Atom I-Object for " + argPredicateIRI + " Triple not found.");
    }

    protected SWRLDArgument translateSWRLAtomDObject(IRI mainIRI, IRI argPredicateIRI) {
        IRI argIRI = tripleIndex.resource(mainIRI, argPredicateIRI, true);
        if (argIRI != null) {
            // Must be a variable -- double check
            if (!swrlPieces.isSWRLVariable(argIRI)) {
                LOGGER.info("Expected SWRL variable for SWRL Data Object: {} (possibly untyped)",
                    argIRI);
            }
            return df.getSWRLVariable(argIRI);
        } else {
            // Must be a literal
            OWLLiteral literal = tripleIndex.literal(mainIRI, argPredicateIRI, true);
            if (literal != null) {
                return df.getSWRLLiteralArgument(literal);
            }
        }
        throw new IllegalStateException("Could not translate SWRL Atom D-Object");
    }

    protected boolean cannotHandleStreaming(IRI s, IRI o) {
        anonWithAnnotations.addAnnotatedSource(o, s);
        checkForAndProcessAnnotatedDeclaration(s);
        return false;
    }

    protected boolean consumeRange(IRI s, IRI o) {
        if (isStrict()) {
            if (iris.isObjectPropertyStrict(s) && iris.isClassExpressionStrict(o)) {
                add(df.getOWLObjectPropertyRangeAxiom(translateOPE(s), ce(o), pendingAnns()));
                return true;
            } else if (iris.isDataPropertyStrict(s) && iris.isDataRangeStrict(o)) {
                add(df.getOWLDataPropertyRangeAxiom(dp(s), translateDataRange(o), pendingAnns()));
                return true;
            } else if (iris.isApLax(s) && !anon.isAnonymousNode(o)) {
                add(df.getOWLAnnotationPropertyRangeAxiom(ap(s), o, pendingAnns()));
                return true;
            }
        } else if (iris.isObjectPropertyStrict(s) && iris.isClassExpression(o)) {
            add(df.getOWLObjectPropertyRangeAxiom(translateOPE(s), ce(o), pendingAnns()));
            return true;
        } else if (iris.isDataPropertyStrict(s) && iris.isDataRange(o)) {
            add(df.getOWLDataPropertyRangeAxiom(dp(s), translateDataRange(o), pendingAnns()));
            return true;
        } else if (iris.isAP(s) && !anon.isAnonymousNode(o)) {
            add(df.getOWLAnnotationPropertyRangeAxiom(ap(s), o, pendingAnns()));
            return true;
        } else if (iris.isAnnotationPropertyOnly(s) && !anon.isAnonymousNode(o)) {
            add(df.getOWLAnnotationPropertyRangeAxiom(ap(s), o, pendingAnns()));
            return true;
        } else if (isCeLax(o)) {
            iris.addObjectProperty(s, false);
            add(df.getOWLObjectPropertyRangeAxiom(translateOPE(s), ce(o), pendingAnns()));
            return true;
        } else if (iris.isDrLax(o)) {
            iris.addDataProperty(s, false);
            add(df.getOWLDataPropertyRangeAxiom(dp(s), translateDataRange(o), pendingAnns()));
            return true;
        } else if (iris.isOpLax(s)) {
            iris.addObjectProperty(s, false);
            add(df.getOWLObjectPropertyRangeAxiom(translateOPE(s), ce(o), pendingAnns()));
            return true;
        } else if (iris.isDPLax(s)) {
            iris.addDataProperty(s, false);
            add(df.getOWLDataPropertyRangeAxiom(dp(s), translateDataRange(o), pendingAnns()));
            return true;
        } else {
            iris.addAnnotationProperty(s, false);
            add(df.getOWLAnnotationPropertyRangeAxiom(ap(s), o, pendingAnns()));
            return true;
        }
        return false;
    }

    protected boolean canHandleAnnotationResourceStreaming(IRI s, IRI p, IRI o) {
        return isStrict() ? false
            : !anon.isAnonymousNode(s) && !anon.isAnonymousNode(o) && iris.isApLax(p);
    }

    protected boolean canHandleAnnotationResource(IRI s, IRI p) {
        return !isAxiom(s) && !iris.isAnnotation(s)
            && (BUILT_IN_AP_IRIS.contains(p) || !p.isReservedVocabulary());
    }

    protected boolean handleAnnotationResourceTriple(IRI s, IRI p, IRI o) {
        OWLAnnotationValue value =
            anon.isAnonymousNode(o) ? getOWLAnonymousIndividual(o.toString()) : o;
        if (iris.isOntology(s)) {
            // Assume we annotation our ontology?
            addOntologyAnnotation(df.getOWLAnnotation(ap(p), value));
        } else {
            add(df.getOWLAnnotationAssertionAxiom(getSubject(s), df.getOWLAnnotation(ap(p), value),
                pendingAnns()));
        }
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleDataAssertion(IRI p) {
        if (isStrict()) {
            return iris.isDataPropertyStrict(p);
        }
        // Handle annotation assertions as annotation assertions only!
        return iris.isDPLax(p) && !iris.isApLax(p);
    }

    protected boolean canHandleLiteralStreaming(IRI p) {
        return isStrict() ? false : iris.isApLax(p);
    }

    protected boolean canHandleObjectAssertion(IRI p) {
        if (isStrict()) {
            return iris.isObjectPropertyStrict(p);
        }
        // Handle annotation assertions as annotation assertions only!
        return iris.isOpLax(p) && !iris.isAnnotationPropertyOnly(p);
    }

    protected boolean handleObjectAssertionTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(p)) {
            add(df.getOWLObjectPropertyAssertionAxiom(translateOPE(p), individual(s), individual(o),
                pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleLiteral(IRI p) {
        return iris.isAnnotationPropertyOnly(p) || iris.isDataPropertyStrict(p);
    }

    protected boolean handleDataAssertionTriple(IRI s, IRI p, OWLLiteral o) {
        add(df.getOWLDataPropertyAssertionAxiom(dp(p), individual(s), o, pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleAnnotationLiteralStreaming(IRI s, IRI p) {
        return !anon.isAnonymousNode(s) && !iris.isAnnotation(s) && iris.isApLax(p);
    }

    protected boolean canHandleAnnotationLiteral(IRI s, IRI p) {
        if (isStrict()) {
            return iris.isAnnotationPropertyOnly(p);
        }
        if (isAxiom(s) || iris.isAnnotation(s)) {
            return false;
        }
        if (iris.isApLax(p) || anon.isAnonymousNode(s)) {
            return true;
        }
        return isCeLax(s) || iris.isDrLax(s) || iris.isOpLax(s) || iris.isDPLax(s);
    }

    protected boolean handleAnnotationLiteralTriple(IRI s, IRI p, OWLLiteral o) {
        if (iris.isOntology(s)) {
            addOntologyAnnotation(df.getOWLAnnotation(ap(p), o, pendingAnns()));
        } else {
            add(df.getOWLAnnotationAssertionAxiom(ap(p), getSubject(s), o, pendingAnns()));
        }
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleAllValuesStreaming(IRI s, IRI p, IRI o) {
        iris.addOWLRestriction(s, false);
        IRI propIRI = tripleIndex.resource(s, OWL_ON_PROPERTY, false);
        if (propIRI != null && (!anon.isAnonymousNode(o)
            || translatorAccessor.getClassExpressionIfTranslated(o) != null)) {
            // The filler is either a datatype or named class
            if (iris.isObjectPropertyStrict(propIRI)) {
                iris.addClassExpression(o, false);
                tripleIndex.addTriple(s, p, o);
                ce(s);
                return true;
            } else if (iris.isDataPropertyStrict(propIRI)) {
            }
        }
        return false;
    }

    protected boolean canHandleAllDifferent(IRI s) {
        return tripleIndex.isResourcePresent(s, OWL_MEMBERS)
            || tripleIndex.isResourcePresent(s, OWL_DISTINCT_MEMBERS);
    }

    protected boolean handleAllDifferentTriple(IRI s, IRI p, IRI o) {
        IRI listNode = object(s, OWL_MEMBERS, OWL_DISTINCT_MEMBERS);
        if (listNode != null) {
            add(df.getOWLDifferentIndividualsAxiom(individuals(listNode), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleVersionTriple(IRI s, IRI p, IRI o) {
        // only setup the versionIRI if it is null before this point
        if (!ont().getOntologyID().getVersionIRI().isPresent()) {
            Optional<IRI> ontologyIRI = ont().getOntologyID().getOntologyIRI();
            Optional<IRI> versionIRI = optional(o);
            // If there was no ontologyIRI before this point and the s
            // of this statement was not anonymous,
            // then use the s IRI as the ontology IRI, else we keep
            // the previous definition for the ontology IRI
            if (!ontologyIRI.isPresent() && !anon.isAnonymousNode(s)) {
                ontologyIRI = optional(s);
            }
            setOntologyID(new OWLOntologyID(ontologyIRI, versionIRI));
        }
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleTypeStreaming(IRI o) {
        // Can handle if o isn't anonymous and either the o
        // IRI is owl:Thing, or it is not part of the build in vocabulary
        iris.addClassExpression(o, false);
        if (anon.isAnonymousNode(o)) {
            return false;
        }
        if (o.isReservedVocabulary()) {
            return o.isThing();
        }
        return true;
    }

    protected boolean handleTypeAssertionTriple(IRI s, IRI p, IRI o) {
        if (BUILT_IN_VOCABULARY_IRIS.contains(o) && !o.isThing()) {
            // Can't have instance of built in vocabulary!
            // Shall we throw an exception here?
            LOGGER.info("Individual of builtin type {}", o);
        }
        add(df.getOWLClassAssertionAxiom(ce(o), individual(s), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleSubPropertyStreaming(IRI s, IRI o) {
        return iris.streamSubproperty(s, o);
    }

    protected boolean handleSubPropertyTriple(IRI s, IRI p, IRI o) {
        // First check for o property chain
        if (!isStrict() && tripleIndex.hasPredicate(s, OWL_PROPERTY_CHAIN.getIRI())) {
            // Property chain
            IRI chainList = tripleIndex.resource(s, OWL_PROPERTY_CHAIN, true);
            List<OWLObjectPropertyExpression> properties =
                translatorAccessor.translateToObjectPropertyList(verifyNotNull(chainList));
            add(df.getOWLSubPropertyChainOfAxiom(properties, translateOPE(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (!isStrict() && tripleIndex.hasPredicate(s, RDF_FIRST.getIRI())) {
            // Legacy o property chain representation
            List<OWLObjectPropertyExpression> properties =
                translatorAccessor.translateToObjectPropertyList(s);
            add(df.getOWLSubPropertyChainOfAxiom(properties, translateOPE(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (iris.isOpLax(s) && iris.isOpLax(o)) {
            add(df.getOWLSubObjectPropertyOfAxiom(translateOPE(s), translateOPE(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (iris.isDPLax(s) && iris.isDPLax(o)) {
            add(df.getOWLSubDataPropertyOfAxiom(dp(s), dp(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (!isStrict()) {
            if (iris.isOP(o)) {
                add(df.getOWLSubObjectPropertyOfAxiom(translateOPE(s), translateOPE(o),
                    pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            } else if (iris.isDP(o)) {
                add(df.getOWLSubDataPropertyOfAxiom(dp(s), dp(o), pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            } else {
                add(df.getOWLSubAnnotationPropertyOfAxiom(ap(s), ap(o), pendingAnns()));
            }
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleSubClassStreaming(IRI s, IRI o) {
        iris.addClassExpression(s, false);
        iris.addClassExpression(o, false);
        return !isStrict() && !eitherAnon(s, o);
    }

    protected boolean handleSubClassTriple(IRI s, IRI p, IRI o) {
        if (isStrict()) {
            if (iris.isClassExpressionStrict(s) && iris.isClassExpressionStrict(o)) {
                add(df.getOWLSubClassOfAxiom(ce(s), ce(o), pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            }
        } else {
            if (isCeLax(s) && isCeLax(o)) {
                add(df.getOWLSubClassOfAxiom(ce(s), ce(o), pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            }
        }
        return false;
    }

    protected boolean handleSameValuesFromTriple(IRI s, IRI p, IRI o) {
        iris.addOWLRestriction(s, false);
        if (iris.isDataRange(o)) {
            IRI property = tripleIndex.resource(s, OWL_ON_PROPERTY, false);
            if (property != null) {
                iris.addDataProperty(property, false);
            }
        }
        // XXX no consumption?
        return false;
    }

    protected boolean handleSameHastriple(IRI s, IRI p, IRI o) {
        add(df.getOWLSameIndividualAxiom(individual(s), individual(o), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleRestTriple(IRI s, IRI p, IRI o) {
        if (!o.equals(RDF_NIL.getIRI())) {
            lists.addRest(s, o);
        }
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandlePropertyRangeStreaming(IRI s, IRI o) {
        iris.inferTypes(s, o);
        return false;
    }

    protected boolean handlePropertyRangeTriple(IRI s, IRI p, IRI o) {
        if (consumeRange(s, o)) {
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handlePropertyDomainTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s) && iris.isClassExpression(o)) {
            add(df.getOWLObjectPropertyDomainAxiom(translateOPE(s), ce(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (iris.isDataPropertyStrict(s) && iris.isClassExpression(o)) {
            add(df.getOWLDataPropertyDomainAxiom(dp(s), ce(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (iris.isApLax(s) && iris.isClassExpression(o) && !anon.isAnonymousNode(o)) {
            add(df.getOWLAnnotationPropertyDomainAxiom(ap(s), o, pendingAnns()));
            // TODO: Handle anonymous domain - error?
            return tripleIndex.consumeTriple(s, p, o);
        } else if (!isStrict()) {
            iris.addAnnotationProperty(s, false);
            add(df.getOWLAnnotationPropertyDomainAxiom(ap(s), o, pendingAnns()));
            // TODO: Handle anonymous domain - error?
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandlePropertyDisjoint(IRI s, IRI o) {
        iris.inferTypes(s, o);
        return iris.isOpLax(s) && iris.isOpLax(o) || iris.isDPLax(s) && iris.isDPLax(o);
    }

    protected boolean handlePropertyDisjointTriple(IRI s, IRI p, IRI o) {
        if (iris.isDPLax(s) && iris.isDPLax(o)) {
            add(df.getOWLDisjointDataPropertiesAxiom(createSet(dp(s), dp(o)), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        if (iris.isOpLax(s) && iris.isOpLax(o)) {
            add(df.getOWLDisjointObjectPropertiesAxiom(createSet(translateOPE(s), translateOPE(o)),
                pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandlePropertyChainStreaming(IRI o) {
        iris.addObjectProperty(o, false);
        return false;
    }

    protected boolean handlePropertyChainTriple(IRI s, IRI p, IRI o) {
        List<OWLObjectPropertyExpression> chain =
            translatorAccessor.translateToObjectPropertyList(o);
        add(df.getOWLSubPropertyChainOfAxiom(chain, translateOPE(s), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleOnPropertyStreaming(IRI s) {
        iris.addOWLRestriction(s, false);
        return false;
    }

    protected boolean canHandleOnDataStreaming(IRI o) {
        iris.addDataRange(o, true);
        return false;
    }

    protected boolean canHandleOnClassStreamng(IRI o) {
        iris.addClassExpression(o, false);
        return false;
    }

    protected boolean canHandleInverseOfStreaming(IRI s, IRI o) {
        iris.addObjectProperty(s, false);
        iris.addObjectProperty(o, false);
        return false;
    }

    protected boolean handleInverseOfTriple(IRI s, IRI p, IRI o) {
        if (axiomParsingMode && iris.isOpLax(s) && iris.isOpLax(o)) {
            add(df.getOWLInverseObjectPropertiesAxiom(translateOPE(s), translateOPE(o),
                pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleIntersectionStreaming(IRI s, IRI o) {
        return iris.canStreamIntersection(s, o);
    }

    protected boolean handleImportsTriple(IRI s, IRI p, IRI o) {
        iris.addOntology(s);
        iris.addOntology(o);
        addImport(df.getOWLImportsDeclaration(o), o);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleHasValueStreaming(IRI s) {
        iris.addOWLRestriction(s, false);
        return false;
    }

    protected boolean canHandleHasKeyStreaming(IRI s) {
        iris.addClassExpression(s, false);
        return false;
    }

    protected boolean handleHasKeyTriple(IRI s, IRI p, IRI o,
        OptimisedListTranslator<OWLPropertyExpression> listTranslator) {
        if (iris.isClassExpression(s)) {
            add(df.getOWLHasKeyAxiom(ce(s), listTranslator.translateList(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleEquivalentClassesStreaming(IRI s, IRI o) {
        iris.inferTypes(s, o);
        return !isStrict() && !eitherAnon(s, o) && iris.bothClassOrDataRange(s, o);
    }

    protected boolean handleEquivalentClassTriple(IRI s, IRI p, IRI o) {
        if (isStrict()) {
            if (iris.isClassExpressionStrict(s) && iris.isClassExpressionStrict(o)) {
                add(df.getOWLEquivalentClassesAxiom(ce(s), ce(o), pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            } else if (iris.isDataRangeStrict(s) && iris.isDataRangeStrict(o)) {
                add(df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype(s), translateDataRange(o),
                    pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            }
        } else {
            if (isCeLax(s) && isCeLax(o)) {
                add(df.getOWLEquivalentClassesAxiom(ce(s), ce(o), pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            } else if (iris.isDrLax(s) || iris.isDrLax(o)) {
                add(df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype(s), translateDataRange(o),
                    pendingAnns()));
                return tripleIndex.consumeTriple(s, p, o);
            }
        }
        return false;
    }

    protected boolean canHandleDisjointWithStreaming(IRI s, IRI o) {
        iris.addClassExpression(s, false);
        iris.addClassExpression(o, false);
        // NB: In strict parsing the above type triples won't get added
        // because they aren't explicit,
        // so we need an extra check to see if there are type triples for
        // the classes
        return !eitherAnon(s, o) && iris.bothCe(s, o);
    }

    protected boolean handleDisjointwithTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLDisjointClassesAxiom(ce(s), ce(o), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleDisjointUnionStreaming(IRI s) {
        iris.addClassExpression(s, false);
        return false;
    }

    protected boolean handleDisjointUnionTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDisjointUnionAxiom((OWLClass) ce(s), classExpressions(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleDifferentFromTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLDifferentIndividualsAxiom(individual(s), individual(o), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleDeclarationTriple(IRI s, IRI p, IRI o) {
        if (o.equals(OWL_CLASS.getIRI())) {
            add(df.getOWLDeclarationAxiom(df.getOWLClass(s), pendingAnns()));
        } else if (o.equals(OWL_OBJECT_PROPERTY.getIRI())) {
            add(df.getOWLDeclarationAxiom(objectProperty(s), pendingAnns()));
        } else if (o.equals(OWL_DATA_PROPERTY.getIRI())) {
            add(df.getOWLDeclarationAxiom(df.getOWLDataProperty(s), pendingAnns()));
        } else if (o.equals(OWL_DATATYPE.getIRI())) {
            add(df.getOWLDeclarationAxiom(df.getOWLDatatype(s), pendingAnns()));
        }
        // XXX no consumption of declaration triple?
        return false;
    }

    protected boolean handleEquivalentPropertyTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s) && iris.isOpLax(o)) {
            add(df.getOWLEquivalentObjectPropertiesAxiom(translateOPE(s), translateOPE(o),
                pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        if (iris.isDPLax(s) && iris.isDPLax(o)) {
            add(df.getOWLEquivalentDataPropertiesAxiom(dp(s), dp(o), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleDatatypeComplementOfStreaming(IRI s, IRI o) {
        iris.addDataRange(s, false);
        iris.addDataRange(o, false);
        return false;
    }

    protected boolean canHandleAnnotatedTargetStreaming(IRI s, IRI o) {
        anonWithAnnotations.addAnnotatedSource(o, s);
        checkForAndProcessAnnotatedDeclaration(s);
        return false;
    }

    protected boolean canHandleannotatedSourceStreaming(IRI s, IRI o) {
        anonWithAnnotations.addAnnotatedSource(o, s);
        checkForAndProcessAnnotatedDeclaration(s);
        return false;
    }

    protected boolean canHandleAnnotatedPropertyStreaming(IRI s, IRI o) {
        anonWithAnnotations.addAnnotatedSource(o, s);
        checkForAndProcessAnnotatedDeclaration(s);
        return false;
    }

    protected boolean canHandleTransitiveStreaming(IRI s, IRI p) {
        handleNonStream(s, p, OWL_OBJECT_PROPERTY.getIRI());
        return !anon.isAnonymousNode(s);
    }

    protected boolean handleTransitiveTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLTransitiveObjectPropertyAxiom(translateOPE(s), pendingAnns()));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleSymmetricStreaming(IRI s, IRI p) {
        boolean isIRI = !anon.isAnonymousNode(s);
        if (isIRI) {
            handleNonStream(s, p, OWL_OBJECT_PROPERTY.getIRI());
        }
        iris.addObjectProperty(s, false);
        return isIRI;
    }

    protected boolean handleSymmetricTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLSymmetricObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleSelfTriple(IRI s, IRI p, IRI o) {
        iris.addOWLRestriction(s, false);
        // Patch to new OWL syntax
        tripleIndex.addTriple(s, OWL_HAS_SELF.getIRI(), df.getOWLLiteral(true));
        return tripleIndex.consumeTriple(s, p, o);
    }

    @SuppressWarnings("unchecked")
    protected <T extends OWLObject> T target(IRI s) {
        OWLObject target = object(s, OWL_TARGET_INDIVIDUAL, RDF_OBJECT);
        if (target == null) {
            target = literal(s, OWL_TARGET_VALUE, OWL_OBJECT, RDF_OBJECT);
        }
        return (T) verifyNotNull(target);
    }

    protected IRI handleSWRLTriple(IRI s) {
        return swrlPieces.addSWRLRule(remapIRI(s));
    }

    protected boolean handleRestrictionTriple(IRI s, IRI p, IRI o) {
        iris.addOWLRestriction(s, true);
        iris.addClassExpression(s, false);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean canHandleReflexiveStream(IRI s) {
        iris.addObjectProperty(s, false);
        return !anon.isAnonymousNode(s);
    }

    protected boolean handleReflexiveTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLReflexiveObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleClassTriple(IRI s, IRI p, IRI o) {
        // TODO: Change to rdfs:Class? (See table 5 in the spec)
        iris.addClassExpression(s, true);
        if (!isStrict()) {
            handleNonStream(s, p, OWL_CLASS.getIRI());
        }
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleClassDeclarationTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(df.getOWLClass(s), pendingAnns()));
        }
        iris.addClassExpression(s, true);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleTypeTriple(IRI s, IRI p, IRI o) {
        LOGGER.info("Usage of rdf vocabulary: {} -> {} -> {}", s, p, o);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleOntologyPropertyTriple(IRI s, IRI p, IRI o) {
        // Add a type triple for an annotation property (Table 6 in Mapping
        // to RDF Graph Spec)
        handleNonStream(s, p, OWL_ANNOTATION_PROPERTY.getIRI());
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleOntologyTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s) && iris.getOntologies().isEmpty()) {
            // Set IRI if it is not null before this point, and make sure to
            // preserve the version IRI if it also existed before this point
            if (!ont().getOntologyID().getOntologyIRI().isPresent()) {
                OWLOntologyID id =
                    new OWLOntologyID(optional(s), ont().getOntologyID().getVersionIRI());
                ont().applyChange(new SetOntologyID(ont(), id));
            }
        }
        iris.addOntology(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleObjectTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(objectProperty(s), pendingAnns()));
        }
        iris.addObjectProperty(s, true);
        // XXX no consume?
        return false;
    }

    protected boolean handleNegAssertionTriple(IRI s, IRI p, IRI o) {
        IRI property = object(s, OWL_ASSERTION_PROPERTY, OWL_PREDICATE, RDF_PREDICATE);
        if (property == null) {
            return false;
        }
        IRI source = object(s, OWL_SOURCE_INDIVIDUAL, OWL_SUBJECT, RDF_SUBJECT);
        if (source == null) {
            return false;
        }
        OWLObject target = target(s);
        if (target instanceof OWLLiteral && (!isStrict() || iris.isDPLax(property))) {
            add(df.getOWLNegativeDataPropertyAssertionAxiom(dp(property), individual(source),
                (OWLLiteral) target, pendingAnns(s)));
            return tripleIndex.consumeTriple(s, p, o);
        } else if (target.isIRI() && (!isStrict() || iris.isOpLax(property))) {
            add(df.getOWLNegativeObjectPropertyAssertionAxiom(translateOPE(property),
                individual(source), individual((IRI) target), pendingAnns(s)));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleNegDataAssertionTriple(IRI s, IRI p, IRI o) {
        IRI property = object(s, OWL_ASSERTION_PROPERTY, OWL_PREDICATE, RDF_PREDICATE);
        if (property == null) {
            return false;
        }
        IRI source = object(s, OWL_SOURCE_INDIVIDUAL, OWL_SUBJECT, RDF_SUBJECT);
        if (source == null) {
            return false;
        }
        add(df.getOWLNegativeDataPropertyAssertionAxiom(dp(property), individual(source), target(s),
            pendingAnns(s)));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleNamedIndividualTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(df.getOWLNamedIndividual(s), pendingAnns()));
        }
        iris.addOWLNamedIndividual(s, true);
        // XXX no consume?
        return false;
    }

    protected boolean canHandleIrreflexiveStreaming(IRI s) {
        iris.addObjectProperty(s, false);
        return !anon.isAnonymousNode(s);
    }

    protected boolean handleIrreflexiveTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLIrreflexiveObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean canHandleInverseFunctionalStreaming(IRI s, IRI p) {
        handleNonStream(s, p, OWL_OBJECT_PROPERTY.getIRI());
        return !anon.isAnonymousNode(s);
    }

    protected boolean handleInverseFunctionalTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLInverseFunctionalObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleFunctionalTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLFunctionalObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        if (iris.isDPLax(s)) {
            add(df.getOWLFunctionalDataPropertyAxiom(dp(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleDeprecatedPropertyTriple(IRI s, IRI p, IRI o) {
        add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleDeprecatedClassTriple(IRI s, IRI p, IRI o) {
        iris.addClassExpression(s, false);
        add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleDatatypeTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(df.getOWLDatatype(s), pendingAnns()));
        }
        iris.addDataRange(s, true);
        // XXX no consume?
        return false;
    }

    protected boolean handleDatarangeTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleDataPropertyTriple(IRI s, IRI p, IRI o) {
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(df.getOWLDataProperty(s), pendingAnns()));
        }
        iris.addDataProperty(s, true);
        // XXX no consuming?
        return false;
    }

    protected boolean canHandleAsymmetricStreaming(IRI s) {
        iris.addObjectProperty(s, false);
        return !anon.isAnonymousNode(s);
    }

    protected boolean handleAsymmetricTriple(IRI s, IRI p, IRI o) {
        if (iris.isOpLax(s)) {
            add(df.getOWLAsymmetricObjectPropertyAxiom(translateOPE(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleAnnotationPropertyTriple(IRI s, IRI p, IRI o) {
        iris.addAnnotationProperty(s, true);
        if (!anon.isAnonymousNode(s)) {
            add(df.getOWLDeclarationAxiom(ap(s), pendingAnns()));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleAllDisjointTriple(IRI s, IRI p, IRI o) {
        IRI listNode = tripleIndex.resource(s, OWL_MEMBERS, true);
        if (listNode != null) {
            if (iris.isOpLax(lists.getFirstResource(listNode, false))) {
                add(df.getOWLDisjointObjectPropertiesAxiom(ops(listNode), pendingAnns(s)));
            } else {
                add(df.getOWLDisjointDataPropertiesAxiom(dps(listNode), pendingAnns(s)));
            }
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected boolean handleAllDisjointClassesTriple(IRI s, IRI p, IRI o) {
        IRI listNode = tripleIndex.resource(s, OWL_MEMBERS, true);
        if (listNode != null) {
            add(df.getOWLDisjointClassesAxiom(classExpressions(listNode), pendingAnns(s)));
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    protected Collection<OWLClassExpression> classExpressions(IRI listNode) {
        return translatorAccessor.translateToClassExpressionSet(listNode);
    }

    protected boolean handletypeaxiomTriple(IRI s, IRI p, IRI o) {
        IRI annotatedSource = getObjectOfSourceTriple(s);
        IRI annotatedProperty = getObjectOfPropertyTriple(s);
        IRI annotatedTarget = getObjectOfTargetTriple(s);
        OWLLiteral annotatedTargetLiteral = null;
        if (annotatedTarget == null) {
            annotatedTargetLiteral = getTargetLiteral(s);
        }
        // check that other conditions are not invalid
        if (annotatedSource != null && annotatedProperty != null) {
            List<OWLAnnotation> annotations = pendingAnns(s);
            pendingAnnotations.addAll(annotations);
            if (annotatedTarget != null) {
                handleNonStream(annotatedSource, annotatedProperty, annotatedTarget);
            } else if (annotatedTargetLiteral != null) {
                handle(annotatedSource, annotatedProperty, annotatedTargetLiteral);
            }
            if (!annotations.isEmpty()) {
                OWLAxiom ax = getLastAddedAxiom();
                removeAxiom(verifyNotNull(ax, "no axiom added yet by the consumer")
                    .getAxiomWithoutAnnotations());
            }
            return tripleIndex.consumeTriple(s, p, o);
        }
        return false;
    }

    @Nullable
    @SuppressWarnings("unused")
    protected OWLAxiom handleAxiomTriples(IRI s, IRI p, IRI o, Set<OWLAnnotation> anns) {
        // Reconstitute the original triple from the reification triples
        return getLastAddedAxiom();
    }

    @Nullable
    protected OWLAxiom handleAxiomTriples(IRI s, IRI p, OWLLiteral o,
        @SuppressWarnings("unused") Set<OWLAnnotation> anns) {
        handle(s, p, o);
        return getLastAddedAxiom();
    }

    @Nullable
    private OWLLiteral getTargetLiteral(IRI s) {
        return literal(s, OWL_ANNOTATED_TARGET, RDF_OBJECT);
    }

    /**
     * Gets the object of the target triple that has the specified main node
     *
     * @param mainNode The main node
     * @return The object of the triple that has the specified mainNode as its s and the IRI
     *         returned by the {@code TypeAxiomHandler#getSourceTriplePredicate()} method. For
     *         backwards compatibility, a search will also be performed for triples whose s is the
     *         specified mainNode and p rdf:object
     */
    @Nullable
    private IRI getObjectOfTargetTriple(IRI mainNode) {
        return object(mainNode, OWL_ANNOTATED_TARGET, RDF_OBJECT, OWL_PROPERTY_CHAIN);
    }

    @Nullable
    private IRI getObjectOfPropertyTriple(IRI s) {
        return object(s, OWL_ANNOTATED_PROPERTY, RDF_PREDICATE);
    }

    /**
     * Gets the source IRI for an annotated or reified axiom
     *
     * @param mainNode The main node of the triple
     * @return The source object
     */
    @Nullable
    private IRI getObjectOfSourceTriple(IRI mainNode) {
        return object(mainNode, OWL_ANNOTATED_SOURCE, RDF_SUBJECT);
    }

    @Nullable
    protected IRI object(IRI s, HasIRI... iri) {
        return Stream.of(iri).map(i -> tripleIndex.resource(s, i, true)).filter(i -> i != null)
            .findAny().orElse(null);
    }

    @Nullable
    protected OWLLiteral literal(IRI s, HasIRI... hasIRIs) {
        return Stream.of(hasIRIs).map(i -> tripleIndex.literal(s, i.getIRI(), true))
            .filter(i -> i != null).findAny().orElse(null);
    }

    protected boolean canHandleDisjointUnion(IRI s) {
        return !anon.isAnonymousNode(s) && iris.isClassExpression(s);
    }

    protected boolean canHandleInverseOf(IRI s, IRI o) {
        return iris.isOpLax(s) && iris.isOpLax(o);
    }

    protected boolean canHandleComplementOfStreaming(IRI s, IRI o) {
        iris.addClassExpression(s, false);
        iris.addClassExpression(o, false);
        return false;
    }

    protected boolean handleNamedEquivalentUnionTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLEquivalentClassesAxiom(ce(s), df.getOWLObjectUnionOf(classExpressions(o))));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleNamedEquivalentOneOfTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLEquivalentClassesAxiom(ce(s), df.getOWLObjectOneOf(individuals(o))));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected Collection<OWLIndividual> individuals(IRI o) {
        return translatorAccessor.translateToIndividualSet(o);
    }

    protected boolean handleNamedEquivalentIntersectionTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLEquivalentClassesAxiom(ce(s),
            df.getOWLObjectIntersectionOf(classExpressions(o))));
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleNamedEquivalentComplementOfTriple(IRI s, IRI p, IRI o) {
        add(df.getOWLEquivalentClassesAxiom(ce(s), df.getOWLObjectComplementOf(ce(o))));
        return tripleIndex.consumeTriple(s, p, o);
    }

    private boolean handleConditionally(@Nullable LiteralTripleHandler h, IRI s, IRI p,
        OWLLiteral o) {
        if (h == null || !h.canHandle(this, s, p, o)) {
            return false;
        }
        h.handleTriple(this, s, p, o);
        return true;
    }

    private boolean handleConditionally(@Nullable ResourceTripleHandler h, IRI s, IRI p, IRI o) {
        if (h == null || !h.canHandle(this, s, p, o)) {
            return false;
        }
        h.handleTriple(this, s, p, o);
        return true;
    }

    private boolean handleStreamConditionally(@Nullable LiteralTripleHandler h, IRI s, IRI p,
        OWLLiteral o) {
        if (h == null || !h.canHandleStreaming(this, s, p, o)) {
            return false;
        }
        h.handleTriple(this, s, p, o);
        return true;
    }

    private boolean handleStreamConditionally(@Nullable ResourceTripleHandler h, IRI s, IRI p,
        IRI o) {
        if (h == null || !h.canHandleStreaming(this, s, p, o)) {
            return false;
        }
        h.handleTriple(this, s, p, o);
        return true;
    }

    private boolean apply(IRI s, IRI p, OWLLiteral o) {
        return isGeneralPredicate(p) && handle(s, p, o);
    }

    private boolean apply(IRI s, IRI p, IRI o) {
        return isGeneralPredicate(p) && handle(s, p, o);
    }

    /**
     * Handles triples in a non-streaming mode. Type triples whose type is an axiom type are NOT
     * handled.
     *
     * @param s The subject of the triple
     * @param p The predicate of the triple
     * @param o The object of the triple
     */
    private void handleNonStream(IRI s, IRI p, IRI o) {
        if (RDF_TYPE.getIRI().equals(p)) {
            if (!handleConditionally(builtInTypes.get(o), s, p, o) && axiomTypes.get(o) == null) {
                // C(a)
                add(df.getOWLClassAssertionAxiom(translatorAccessor.translateClassExpression(o),
                    individual(s), pendingAnns()));
            }
        } else {
            if (!handleConditionally(predicates.get(p), s, p, o)) {
                handle(s, p, o);
            }
        }
    }

    private boolean handle(IRI s, IRI p, OWLLiteral o) {
        return literals.stream().anyMatch(x -> handleConditionally(x, s, p, o));
    }

    private boolean handle(IRI s, IRI p, IRI o) {
        return resources.stream().anyMatch(x -> handleConditionally(x, s, p, o));
    }

    /**
     * We need to mop up all remaining triples. These triples will be in the triples by subject map.
     * Other triples which reside in the triples by predicate (single valued) triple aren't "root"
     * triples for axioms. First we translate all system triples and then go for triples whose
     * predicates are not system/reserved vocabulary IRIs to translate these into ABox assertions or
     * annotationIRIs
     *
     * @return any remaining triples
     */
    private Set<RDFTriple> mopUp() {
        // We need to mop up all remaining triples. These triples will be in
        // the triples by subject map. Other triples which reside in the
        // triples by predicate (single valued) triple aren't "root" triples
        // for axioms. First we translate all system triples, starting with
        // property ranges, then go for triples whose predicates are not
        // system/reserved vocabulary IRIs to translate these into ABox
        // assertions or annotationIRIs
        TriplePredicateHandler propertyRangeHandler = predicates.get(RDFS_RANGE.getIRI());
        tripleIndex.iterate((s, p, o) -> handleConditionally(propertyRangeHandler, s, p, o),
            this::apply, this::apply, (s, p, o) -> handleConditionally(axiomTypes.get(o), s, p, o),
            this::handleNonStream, this::handle);

        axiomParsingMode = true;
        // Inverse property axioms
        tripleIndex.iterate((s, p, o) -> handleConditionally(InverseOfHandler, s, p, o));
        return tripleIndex.getRemainingTriples(anon::isAnonymousNode);
    }


    protected boolean handleSWRLBuiltinTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLBuiltInAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLClassAtomTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLClassAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLDatarangeTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLDataRangeAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLDataPropertyAtomTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLDataPropertyAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLDifferentTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLDifferentFromAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLTriple(IRI s, IRI p, IRI o) {
        IRI remapIRI = handleSWRLTriple(s);
        return tripleIndex.consumeTriple(remapIRI, p, o);
    }

    protected boolean handleSWRLIndividualTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLIndividualPropertyAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLSameAsTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLSameAsAtom(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleSWRLVariableTriple(IRI s, IRI p, IRI o) {
        swrlPieces.addSWRLVariable(s);
        return tripleIndex.consumeTriple(s, p, o);
    }

    protected boolean handleAnnotationTriple(IRI s, IRI p, IRI o) {
        iris.addAnnotationIRI(s);
        // XXX no consumption?
        return false;
    }

    protected boolean canHandleAxiomStreaming(IRI s) {
        // We can't handle this is a streaming fashion, because we can't
        // be sure that the s, p, o triples have been parsed.
        addAxiom(s);
        return false;
    }
}
