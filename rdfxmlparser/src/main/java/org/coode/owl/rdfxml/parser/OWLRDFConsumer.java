package org.coode.owl.rdfxml.parser;

import edu.unika.aifb.rdf.api.syntax.RDFConsumer;
import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.util.OWLDataUtil;
import org.semanticweb.owl.vocab.*;
import static org.semanticweb.owl.vocab.OWLRDFVocabulary.*;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Dec-2006<br><br>
 * <p/>
 * A parser/interpreter for an RDF graph which represents an OWL ontology.  The
 * consumer interprets triple patterns in the graph to produce the appropriate
 * OWLAPI entities, descriptions and axioms.
 * <p/>
 * The parser is based on triple handlers.  A given triple handler handles a specific
 * type of triple.  Generally speaking this is based on the predicate of a triple, for
 * example, A rdfs:subClassOf B is handled by a subClassOf handler.  A handler determines
 * if it can handle a triple in a streaming mode (i.e. while parsing is taking place) or
 * if it can handle a triple after parsing has taken place and the complete graph is in
 * memory.  Once a handler handles a triple, that triple is deemed to have been consumed
 * an is discarded.
 * <p/>
 * The parser attempts to consume as many triples as possible while streaming parsing
 * is taking place. Whether or not a triple can be consumed during parsing is determined
 * by installed triple handlers.
 */
public class OWLRDFConsumer implements RDFConsumer {

    private static final Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    private static final Logger tripleProcessor = Logger.getLogger("Triple processor");

    //private Graph graph;

    private OWLOntologyManager owlOntologyManager;

    private URI xmlBase;

    // A call back interface, which is used to check whether a node
    // is anonymous or not.
    private AnonymousNodeChecker anonymousNodeChecker;

    // The set of URIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owl:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    private Set<URI> owlClassURIs;

    // Same as owlClassURIs but for object properties
    private Set<URI> objectPropertyURIs;

    // Same as owlClassURIs but for data properties
    private Set<URI> dataPropertyURIs;

    // Same as owlClassURIs but for rdf properties
    // things neither typed as a data or object property - bad!
    private Set<URI> propertyURIs;


    // Set of URIs that are typed by non-system types and
    // also owl:Thing
    private Set<URI> individualURIs;


    // Same as owlClassURIs but for annotation properties
    private Set<URI> annotationPropertyURIs;


    private Set<URI> ontologyPropertyURIs;


    // URIs that had a type triple to rdfs:DataRange
    private Set<URI> dataRangeURIs;

    // The URI of the first reource that is typed as an ontology
    private URI firstOntologyURI;

    // URIs that had a type triple to owl:Ontology
    private Set<URI> ontologyURIs;

    // URIs that had a type triple to owl:Restriction
    private Set<URI> restrictionURIs;

    // URIs that had a type triple to owl:SelfRestriction
    private Set<URI> selfRestrictionURIs;

    // URIs that had a type triple to owl:ObjectRestriction
    private Set<URI> objectRestrictionURIs;

    // URIs that had a type triple to owl:DataRestriction
    private Set<URI> dataRestrictionURIs;

    // URIs that had a type triple to rdf:List
    private Set<URI> listURIs;

    // Maps rdf:next triple subjects to objects
    private Map<URI, URI> listRestTripleMap;

    private Map<URI, URI> listFirstResourceTripleMap;

    private Map<URI, OWLLiteral> listFirstLiteralTripleMap;

    private Map<URI, OWLAxiom> reifiedAxiomsMap;

    // A translator for lists of descriptions (such lists are used
    // in intersections, unions etc.)
    private OptimisedListTranslator<OWLDescription> descriptionListTranslator;

    // A translator for individual lists (such lists are used in
    // object oneOf constructs)
    private OptimisedListTranslator<OWLIndividual> individualListTranslator;

    private OptimisedListTranslator<OWLObjectPropertyExpression> objectPropertyListTranslator;

    private OptimisedListTranslator<OWLLiteral> constantListTranslator;

    private OptimisedListTranslator<OWLDataPropertyExpression> dataPropertyListTranslator;

    // Handlers for built in types
    private Map<URI, BuiltInTypeHandler> builtInTypeTripleHandlers;

    // Handlers for build in predicates
    private Map<URI, TriplePredicateHandler> predicateHandlers;

    private Map<URI, AbstractLiteralTripleHandler> skosTripleHandlers;

    // Handlers for general literal triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary.  Such triples either constitute annotations of
    // relationships between an individual and a data literal (typed or
    // untyped)
    private List<AbstractLiteralTripleHandler> literalTripleHandlers;

    // Handlers for general resource triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary.  Such triples either constitute annotations or
    // relationships between an individual and another individual.
    private List<AbstractResourceTripleHandler> resourceTripleHandlers;

    /**
     * The ontology that the RDF will be parsed into
     */
    private OWLOntology ontology;

    private RDFXMLOntologyFormat rdfxmlOntologyFormat;

    private OWLDataFactory dataFactory;

    private DescriptionTranslatorSelector descriptionTranslatorSelector;

    private OWLAxiom lastAddedAxiom;

    private Map<URI, URI> synonymMap;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    // SWRL Stuff

    private Set<URI> swrlRules;

    private Set<URI> swrlIndividualPropertyAtoms;

    private Set<URI> swrlDataValuedPropertyAtoms;

    private Set<URI> swrlClassAtoms;

    private Set<URI> swrlDataRangeAtoms;

    private Set<URI> swrlBuiltInAtoms;

    private Set<URI> swrlVariables;

    private Set<URI> swrlSameAsAtoms;

    private Set<URI> swrlDifferentFromAtoms;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public OWLRDFConsumer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, AnonymousNodeChecker checker) {
        descriptionTranslatorSelector = new DescriptionTranslatorSelector(this);
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        this.dataFactory = owlOntologyManager.getOWLDataFactory();
        this.anonymousNodeChecker = checker;
        owlClassURIs = CollectionFactory.createSet();
        objectPropertyURIs = CollectionFactory.createSet();
        dataPropertyURIs = CollectionFactory.createSet();
        individualURIs = CollectionFactory.createSet();
        annotationPropertyURIs = CollectionFactory.createSet();
        annotationPropertyURIs.addAll(OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTIES);
        ontologyPropertyURIs = CollectionFactory.createSet();
        ontologyPropertyURIs.add(OWLRDFVocabulary.OWL_PRIOR_VERSION.getURI());
        ontologyPropertyURIs.add(OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getURI());
        ontologyPropertyURIs.add(OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getURI());

        addDublinCoreAnnotationURIs();
        dataRangeURIs = CollectionFactory.createSet();
        propertyURIs = CollectionFactory.createSet();
        restrictionURIs = CollectionFactory.createSet();
        selfRestrictionURIs = CollectionFactory.createSet();
        dataRestrictionURIs = CollectionFactory.createSet();
        ontologyURIs = CollectionFactory.createSet();
        objectRestrictionURIs = CollectionFactory.createSet();
        listURIs = CollectionFactory.createSet();
        listFirstLiteralTripleMap = CollectionFactory.createMap();
        listFirstResourceTripleMap = CollectionFactory.createMap();
        listRestTripleMap = CollectionFactory.createMap();
        reifiedAxiomsMap = CollectionFactory.createMap();
        descriptionListTranslator = new OptimisedListTranslator<OWLDescription>(this,
                                                                                new DescriptionListItemTranslator(this));
        individualListTranslator = new OptimisedListTranslator<OWLIndividual>(this,
                                                                              new IndividualListItemTranslator(this));
        constantListTranslator = new OptimisedListTranslator<OWLLiteral>(this,
                                                                          new TypedConstantListItemTranslator(this));
        objectPropertyListTranslator = new OptimisedListTranslator<OWLObjectPropertyExpression>(this,
                                                                                                new ObjectPropertyListItemTranslator(
                                                                                                        this));

        dataPropertyListTranslator = new OptimisedListTranslator<OWLDataPropertyExpression>(this,
                                                                                            new DataPropertyListItemTranslator(this));
        builtInTypeTripleHandlers = CollectionFactory.createMap();
        setupTypeTripleHandlers();
        setupPredicateHandlers();

        // General literal triples - i.e. triples which have a predicate
        // that is not a built in URI.  Annotation properties get precedence
        // over data properties, so that if we have the statement a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the statement will be
        // translated as an annotation on a:A
        literalTripleHandlers = new ArrayList<AbstractLiteralTripleHandler>();
        literalTripleHandlers.add(new GTPAnnotationLiteralHandler(this));
        literalTripleHandlers.add(new GTPDataPropertyAssertionHandler(this));
        literalTripleHandlers.add(new TPFirstLiteralHandler(this));

        // General resource/object triples - i.e. triples which have a predicate
        // that is not a built in URI.  Annotation properties get precedence
        // over object properties, so that if we have the statement a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the statement will be
        // translated as an annotation on a:A
        resourceTripleHandlers = new ArrayList<AbstractResourceTripleHandler>();
        resourceTripleHandlers.add(new GTPAnnotationResourceTripleHandler(this));
        resourceTripleHandlers.add(new GTPObjectPropertyAssertionHandler(this));

        dataRangeURIs.addAll(XSDVocabulary.ALL_DATATYPES);
        dataRangeURIs.add(OWLRDFVocabulary.RDFS_LITERAL.getURI());
        dataRangeURIs.addAll(OWLDatatypeVocabulary.getDatatypeURIs());

        swrlRules = new HashSet<URI>();
        swrlIndividualPropertyAtoms = new HashSet<URI>();
        swrlDataValuedPropertyAtoms = new HashSet<URI>();
        swrlClassAtoms = new HashSet<URI>();
        swrlDataRangeAtoms = new HashSet<URI>();
        swrlBuiltInAtoms = new HashSet<URI>();
        swrlVariables = new HashSet<URI>();
        swrlSameAsAtoms = new HashSet<URI>();
        swrlDifferentFromAtoms = new HashSet<URI>();

        setupSynonymMap();
        setupSinglePredicateMaps();

        setupSKOSTipleHandlers();
    }

    private void addSingleValuedResPredicate(OWLRDFVocabulary v) {
        Map<URI, URI> map = CollectionFactory.createMap();
        singleValuedResTriplesByPredicate.put(v.getURI(), map);
    }


    private void addDublinCoreAnnotationURIs() {
        for (URI uri : DublinCoreVocabulary.ALL_URIS) {
            annotationPropertyURIs.add(uri);
        }
    }


    private void setupSinglePredicateMaps() {
        addSingleValuedResPredicate(OWL_ON_PROPERTY);
        addSingleValuedResPredicate(OWL_SOME_VALUES_FROM);
        addSingleValuedResPredicate(OWL_ALL_VALUES_FROM);
//        addSingleValuedResPredicate(OWL_ONE_OF);
        addSingleValuedResPredicate(OWL_ON_CLASS);
        addSingleValuedResPredicate(OWL_ON_DATA_RANGE);
//        addSingleValuedResPredicate(OWL_INTERSECTION_OF);
//        addSingleValuedResPredicate(OWL_UNION_OF);
//        addSingleValuedResPredicate(OWL_COMPLEMENT_OF);
    }


    private void setupSynonymMap() {
        // We can load legacy ontologies by providing synonyms for built in vocabulary
        // where the vocabulary has simply changed (e.g. DAML+OIL -> OWL)

        synonymMap = CollectionFactory.createMap();
        // Legacy protege-owl representation of QCRs
        synonymMap.put(URI.create(Namespaces.OWL + "valuesFrom"), OWL_ON_CLASS.getURI());

        // Preliminary OWL 1.1 Vocab
        synonymMap.put(URI.create(Namespaces.OWL + "qualifiedMinCardinality"), OWL_MIN_CARDINALITY.getURI());
        synonymMap.put(URI.create(Namespaces.OWL + "qualifiedMaxCardinality"), OWL_MAX_CARDINALITY.getURI());
        synonymMap.put(URI.create(Namespaces.OWL + "qualifiedExactCardinality"), OWL_CARDINALITY.getURI());
        synonymMap.put(URI.create(Namespaces.OWL + "cardinalityType"), OWL_ON_CLASS.getURI());
        synonymMap.put(URI.create(Namespaces.OWL + "dataComplementOf"), OWL_COMPLEMENT_OF.getURI());
        synonymMap.put(OWL_ANTI_SYMMETRIC_PROPERTY.getURI(), OWL_ASYMMETRIC_PROPERTY.getURI());

        // DAML+OIL -> OWL
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#subClassOf"), RDFS_SUBCLASS_OF.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#imports"), OWL_IMPORTS.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#range"), RDFS_RANGE.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#hasValue"), OWL_HAS_VALUE.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#type"), RDF_TYPE.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#domain"), RDFS_DOMAIN.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#versionInfo"), OWL_VERSION_INFO.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#comment"), RDFS_COMMENT.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#onProperty"), OWL_ON_PROPERTY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#toClass"), OWL_ALL_VALUES_FROM.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#hasClass"), OWL_SOME_VALUES_FROM.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#Restriction"), OWL_RESTRICTION.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#Class"), OWL_CLASS.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#Thing"), OWL_THING.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#Nothing"), OWL_NOTHING.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#minCardinality"), OWL_MIN_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#cardinality"), OWL_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#maxCardinality"), OWL_MAX_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#inverseOf"), OWL_INVERSE_OF.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#samePropertyAs"),
                       OWL_EQUIVALENT_PROPERTY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#hasClassQ"), OWL_ON_CLASS.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#cardinalityQ"), OWL_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#maxCardinalityQ"),
                       OWL_MAX_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#minCardinalityQ"),
                       OWL_MIN_CARDINALITY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#complementOf"), OWL_COMPLEMENT_OF.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#unionOf"), OWL_UNION_OF.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#intersectionOf"), OWL_INTERSECTION_OF.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#label"), RDFS_LABEL.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#ObjectProperty"), OWL_OBJECT_PROPERTY.getURI());
        synonymMap.put(URI.create("http://www.daml.org/2001/03/daml+oil#DatatypeProperty"), OWL_DATA_PROPERTY.getURI());
        setupLegacyOWLSpecStuff();
    }


    /**
     * There may be some ontologies floating about that use early versions
     * of the OWL 1.1 vocabulary.  We can map early versions of the vocabulary
     * to the current OWL 1.1 vocabulary.
     */
    private void setupLegacyOWLSpecStuff() {
        for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
            addLegacyMapping(v);
        }
        for (OWLRestrictedDataRangeFacetVocabulary v : OWLRestrictedDataRangeFacetVocabulary.values()) {
            synonymMap.put(URI.create(Namespaces.OWL.toString() + v.getShortName()), v.getURI());
            synonymMap.put(URI.create(Namespaces.OWL11.toString() + v.getShortName()), v.getURI());
            synonymMap.put(URI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getURI());
        }
        for(OWLRestrictedDataRangeFacetVocabulary v : OWLRestrictedDataRangeFacetVocabulary.values()) {
            synonymMap.put(URI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getURI());
        }
    }


    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(URI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getURI());
        synonymMap.put(URI.create(Namespaces.OWL11.toString() + v.getShortName()), v.getURI());
    }

    private void setupSKOSTipleHandlers() {
        skosTripleHandlers = new HashMap<URI, AbstractLiteralTripleHandler>();
        addSKOSTripleHandler(SKOSVocabulary.ALTLABEL);
        addSKOSTripleHandler(SKOSVocabulary.CHANGENOTE);
        addSKOSTripleHandler(SKOSVocabulary.COMMENT);
        addSKOSTripleHandler(SKOSVocabulary.DEFINITION);
        addSKOSTripleHandler(SKOSVocabulary.DOCUMENT);
        addSKOSTripleHandler(SKOSVocabulary.EDITORIALNOTE);
        addSKOSTripleHandler(SKOSVocabulary.HIDDENLABEL);
        addSKOSTripleHandler(SKOSVocabulary.PREFLABEL);
        addSKOSTripleHandler(SKOSVocabulary.SCOPENOTE);   
    }

    private void addSKOSTripleHandler(SKOSVocabulary dataPredicate) {
        skosTripleHandlers.put(dataPredicate.getURI(), new SKOSDataTripleHandler(this, dataPredicate.getURI()));
    }

    public OWLOntology getOntology() {
        return ontology;
    }


    public RDFXMLOntologyFormat getOntologyFormat() {
        return rdfxmlOntologyFormat;
    }


    public void setOntologyFormat(RDFXMLOntologyFormat format) {
        this.rdfxmlOntologyFormat = format;
    }


    private void addBuiltInTypeTripleHandler(BuiltInTypeHandler handler) {
        builtInTypeTripleHandlers.put(handler.getTypeURI(), handler);
    }


    private void setupTypeTripleHandlers() {
        addBuiltInTypeTripleHandler(new TypeAntisymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeAsymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDatatypeHandler(this));
        addBuiltInTypeTripleHandler(new TypeFunctionalDataPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeFunctionalObjectPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeFunctionalPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeInverseFunctionalPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeIrreflexivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeObjectPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeReflexivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeSymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeTransitivePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypeObjectRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypeListHandler(this));
        addBuiltInTypeTripleHandler(new TypeAnnotationPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDeprecatedClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataRangeHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDifferentHandler(this));
        addBuiltInTypeTripleHandler(new TypeOntologyHandler(this));
        addBuiltInTypeTripleHandler(new TypeNegativeObjectPropertyAssertionHandler(this));
        addBuiltInTypeTripleHandler(new TypeNegativeDataPropertyAssertionHandler(this));
        addBuiltInTypeTripleHandler(new TypeAxiomHandler(this));
        addBuiltInTypeTripleHandler(new TypeRDFPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeRDFSClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeSelfRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDisjointClassesHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDisjointPropertiesHandler(this));

        addBuiltInTypeTripleHandler(new TypeSWRLAtomListHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLBuiltInAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLBuiltInHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLClassAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLDataRangeAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLDataValuedPropertyAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLDifferentIndividualsAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLImpHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLIndividualPropertyAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLSameIndividualAtomHandler(this));
        addBuiltInTypeTripleHandler(new TypeSWRLVariableHandler(this));

        addBuiltInTypeTripleHandler(new SKOSConceptTripleHandler(this));
    }


    private void addPredicateHandler(TriplePredicateHandler predicateHandler) {
        predicateHandlers.put(predicateHandler.getPredicateURI(), predicateHandler);
    }


    private void setupPredicateHandlers() {
        predicateHandlers = CollectionFactory.createMap();
        addPredicateHandler(new TPDataPropertDomainHandler(this));
        addPredicateHandler(new TPDataPropertyRangeHandler(this));
        addPredicateHandler(new TPDifferentFromHandler(this));
        addPredicateHandler(new TPDisjointDataPropertiesHandler(this));
        addPredicateHandler(new TPDisjointObjectPropertiesHandler(this));
        addPredicateHandler(new TPDisjointUnionHandler(this));
        addPredicateHandler(new TPDisjointWithHandler(this));
        addPredicateHandler(new TPEquivalentClassHandler(this));
        addPredicateHandler(new TPEquivalentDataPropertyHandler(this));
        addPredicateHandler(new TPEquivalentObjectPropertyHandler(this));
        addPredicateHandler(new TPEquivalentPropertyHandler(this));
        addPredicateHandler(new TPObjectPropertyDomainHandler(this));
        addPredicateHandler(new TPObjectPropertyRangeHandler(this));
        addPredicateHandler(new TPPropertyDomainHandler(this));
        addPredicateHandler(new TPPropertyRangeHandler(this));
        addPredicateHandler(new TPSameAsHandler(this));
        addPredicateHandler(new TPSubClassOfHandler(this));
        addPredicateHandler(new TPSubDataPropertyOfHandler(this));
        addPredicateHandler(new TPSubObjectPropertyOfHandler(this));
        addPredicateHandler(new TPSubPropertyOfHandler(this));
        addPredicateHandler(new TPTypeHandler(this));
        addPredicateHandler(new TPInverseOfHandler(this));
        addPredicateHandler(new TPDistinctMembersHandler(this));
        addPredicateHandler(new TPImportsHandler(this));
        addPredicateHandler(new TPIntersectionOfHandler(this));
        addPredicateHandler(new TPUnionOfHandler(this));
        addPredicateHandler(new TPComplementOfHandler(this));
        addPredicateHandler(new TPOneOfHandler(this));
        addPredicateHandler(new TPOnPropertyHandler(this));
        addPredicateHandler(new TPSomeValuesFromHandler(this));
        addPredicateHandler(new TPAllValuesFromHandler(this));
        addPredicateHandler(new TPRestHandler(this));
        addPredicateHandler(new TPFirstResourceHandler(this));
        addPredicateHandler(new TPDeclaredAsHandler(this));

        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.BROADER));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.NARROWER));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.RELATED));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.HASTOPCONCEPT));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.SEMANTICRELATION));

        
    }


    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }


    // We cache URIs to save memory!!
    private Map<String, URI> uriMap = CollectionFactory.createMap();

    int currentBaseCount = 0;


    private URI getURI(String s) {

        URI uri = uriMap.get(s);
        if (uri == null) {
            uri = URI.create(s);
            uriMap.put(s, uri);
        }
        return uri;
    }

    public void importsClosureChanged() {
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            annotationPropertyURIs.addAll(ont.getAnnotationURIs());
        }
    }


    /**
     * Checks whether a node is anonymous.
     * @param uri The URI of the node to be checked.
     * @return <code>true</code> if the node is anonymous, or
     *         <code>false</code> if the node is not anonymous.
     */
    protected boolean isAnonymousNode(URI uri) {
        return anonymousNodeChecker.isAnonymousNode(uri);
    }


    protected void addAxiom(OWLAxiom axiom) throws OWLException {
        owlOntologyManager.applyChange(new AddAxiom(ontology, axiom));
        lastAddedAxiom = axiom;
        // Consider recording which entity URIs have been used here. This
        // might make translation of "dangling entities" faster (at the expense
        // of memory).
    }


    public OWLAxiom getLastAddedAxiom() {
        return lastAddedAxiom;
    }


    protected void addOWLClass(URI uri) {
        owlClassURIs.add(uri);
    }


    protected void addOWLObjectProperty(URI uri) {
        objectPropertyURIs.add(uri);
    }


    protected void addIndividual(URI uri) {
        individualURIs.add(uri);
    }


    protected boolean isIndividual(URI uri) {
        return individualURIs.contains(uri);
    }


    protected void addRDFProperty(URI uri) {
        propertyURIs.add(uri);
    }


    protected boolean isRDFProperty(URI uri) {
        return propertyURIs.contains(uri);
    }


    protected void addOWLDataProperty(URI uri) {
        dataPropertyURIs.add(uri);
    }


    protected void addOWLDatatype(URI uri) {
        dataRangeURIs.add(uri);
    }


    public void addOWLDataRange(URI uri) {
        dataRangeURIs.add(uri);
    }


    protected void addRestriction(URI uri) {
        restrictionURIs.add(uri);
    }


    protected void addSelfRestriction(URI uri) {
        selfRestrictionURIs.add(uri);
    }


    public boolean isSelfRestriction(URI uri) {
        return selfRestrictionURIs.contains(uri);
    }


    protected void addObjectRestriction(URI uri) {
        objectRestrictionURIs.add(uri);
    }


    protected void addDataRestriction(URI uri) {
        dataRestrictionURIs.add(uri);
    }


    protected void addAnnotationProperty(URI uri) {
        annotationPropertyURIs.add(uri);
        if (rdfxmlOntologyFormat != null) {
            rdfxmlOntologyFormat.addAnnotationURI(uri);
        }
    }


    public boolean isRestriction(URI uri) {
        return restrictionURIs.contains(uri);
    }


    public boolean isObjectRestriction(URI uri) {
        return objectRestrictionURIs.contains(uri);
    }


    public boolean isDataRestriction(URI uri) {
        return dataRestrictionURIs.contains(uri);
    }


    protected boolean isClass(URI uri) {
        if (owlClassURIs.contains(uri)) {
            return true;
        }
        else {
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsClassReference(uri)) {
                    return true;
                }
            }
        }
        return false;
    }


    protected boolean isObjectPropertyOnly(URI uri) {
        if (dataPropertyURIs.contains(uri)) {
            return false;
        }
        if (objectPropertyURIs.contains(uri)) {
            return true;
        }
        else {
            boolean containsObjectPropertyReference = false;
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsDataPropertyReference(uri)) {
                    dataPropertyURIs.add(uri);
                    return false;
                }
                else if (ont.containsObjectPropertyReference(uri)) {
                    containsObjectPropertyReference = true;
                    objectPropertyURIs.add(uri);
                }
            }
            return containsObjectPropertyReference;
        }
    }


    protected boolean isDataPropertyOnly(URI uri) {
        // I don't like the fact that the check to see if something
        // is a data property only includes a check to make sure that
        // it is not an annotation property.  I think the OWL spec should
        // be altered
        if (objectPropertyURIs.contains(uri)) {
            return false;
        }
        if (dataPropertyURIs.contains(uri)) {
            return true;
        }
        else {
            boolean containsDataPropertyReference = false;
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsObjectPropertyReference(uri)) {
                    return false;
                }
                else if (ont.containsDataPropertyReference(uri)) {
                    containsDataPropertyReference = true;
                }
            }
            return containsDataPropertyReference;
        }
    }


    protected boolean isOntologyProperty(URI uri) {
        return ontologyPropertyURIs.contains(uri);
    }


    protected boolean isAnnotationProperty(URI uri) {
        if (annotationPropertyURIs.contains(uri)) {
            return true;
        }
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            if (!ont.equals(ontology)) {
                if (ont.getAnnotationURIs().contains(uri)) {
                    // Cache URI
                    annotationPropertyURIs.addAll(ont.getAnnotationURIs());
                    return annotationPropertyURIs.contains(uri);
                }
                else {
                    OWLOntologyFormat format = owlOntologyManager.getOntologyFormat(ont);
                    if (format instanceof RDFXMLOntologyFormat) {
                        RDFXMLOntologyFormat rdfFormat = (RDFXMLOntologyFormat) format;
                        annotationPropertyURIs.addAll(rdfFormat.getAnnotationURIs());
                        return annotationPropertyURIs.contains(uri);
                    }
                }
            }
        }
        return false;
    }


    protected boolean isOntology(URI uri) {
        return ontologyURIs.contains(uri);
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Helper methods for creating entities
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected OWLClass getOWLClass(URI uri) {
        return getDataFactory().getOWLClass(uri);
    }


    protected OWLObjectProperty getOWLObjectProperty(URI uri) {
        return getDataFactory().getOWLObjectProperty(uri);
    }


    protected OWLDataProperty getOWLDataProperty(URI uri) {
        return getDataFactory().getOWLDataProperty(uri);
    }


    protected OWLIndividual getOWLIndividual(URI uri) {
        if (isAnonymousNode(uri)) {
            return getDataFactory().getOWLAnonymousIndividual(uri);
        }
        else {
            return getDataFactory().getOWLIndividual(uri);
        }
    }


    protected void consumeTriple(URI subject, URI predicate, URI object) {
        isTriplePresent(subject, predicate, object, true);
    }


    protected void consumeTriple(URI subject, URI predicate, OWLLiteral con) {
        isTriplePresent(subject, predicate, con, true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL Stuff
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected void addSWRLRule(URI uri) {
        swrlRules.add(uri);
    }


    protected boolean isSWRLRule(URI uri) {
        return swrlRules.contains(uri);
    }


    protected void addSWRLIndividualPropertyAtom(URI uri) {
        swrlIndividualPropertyAtoms.add(uri);
    }


    protected boolean isSWRLIndividualPropertyAtom(URI uri) {
        return swrlIndividualPropertyAtoms.contains(uri);
    }


    protected void addSWRLDataPropertyAtom(URI uri) {
        swrlDataValuedPropertyAtoms.add(uri);
    }


    protected boolean isSWRLDataValuedPropertyAtom(URI uri) {
        return swrlDataValuedPropertyAtoms.contains(uri);
    }


    protected void addSWRLClassAtom(URI uri) {
        swrlClassAtoms.add(uri);
    }


    protected boolean isSWRLClassAtom(URI uri) {
        return swrlClassAtoms.contains(uri);
    }


    protected void addSWRLSameAsAtom(URI uri) {
        swrlSameAsAtoms.add(uri);
    }


    protected boolean isSWRLSameAsAtom(URI uri) {
        return swrlSameAsAtoms.contains(uri);
    }


    protected void addSWRLDifferentFromAtom(URI uri) {
        swrlDifferentFromAtoms.add(uri);
    }


    protected boolean isSWRLDifferentFromAtom(URI uri) {
        return swrlDifferentFromAtoms.contains(uri);
    }


    protected void addSWRLDataRangeAtom(URI uri) {
        swrlDataRangeAtoms.add(uri);
    }


    protected boolean isSWRLDataRangeAtom(URI uri) {
        return swrlDataRangeAtoms.contains(uri);
    }


    protected void addSWRLBuiltInAtom(URI uri) {
        swrlBuiltInAtoms.add(uri);
    }


    protected boolean isSWRLBuiltInAtom(URI uri) {
        return swrlBuiltInAtoms.contains(uri);
    }


    protected void addSWRLVariable(URI uri) {
        swrlVariables.add(uri);
    }


    protected boolean isSWRLVariable(URI uri) {
        return swrlVariables.contains(uri);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////
    ////  RDFConsumer implementation
    ////
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private long t0;


    public void handle(URI subject, URI predicate, URI object) throws OWLException {
        if (predicate.equals(OWLRDFVocabulary.RDF_TYPE.getURI())) {
            BuiltInTypeHandler typeHandler = builtInTypeTripleHandlers.get(object);
            if (typeHandler != null) {
                typeHandler.handleTriple(subject, predicate, object);
                // Consumed the triple - no further processing
                return;
            }
            else {
                addIndividual(subject);
            }
        }

        AbstractResourceTripleHandler handler = predicateHandlers.get(predicate);
        if (handler != null) {
            if (handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
            }
            return;
        }
    }


    public void handle(URI subject, URI predicate, OWLLiteral object) throws OWLException {
        for (AbstractLiteralTripleHandler handler : literalTripleHandlers) {
            if (handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
                break;
            }
        }
    }


    private static void printTriple(Object subject, Object predicate, Object object, BufferedWriter w) {
        try {
            w.append(subject.toString());
            w.append(" -> ");
            w.append(predicate.toString());
            w.append(" -> ");
            w.append(object.toString());
            w.append("\n");
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    private void dumpRemainingTriples() {
        if (!logger.isLoggable(Level.FINE)) {
            return;
        }
        try {
            StringWriter sw = new StringWriter();
            BufferedWriter w = new BufferedWriter(sw);

            for (URI predicate : singleValuedResTriplesByPredicate.keySet()) {
                Map<URI, URI> map = singleValuedResTriplesByPredicate.get(predicate);
                for (URI subject : map.keySet()) {
                    URI object = map.get(subject);
                    printTriple(subject, predicate, object, w);
                }
            }

            for (URI predicate : singleValuedLitTriplesByPredicate.keySet()) {
                Map<URI, OWLLiteral> map = singleValuedLitTriplesByPredicate.get(predicate);
                for (URI subject : map.keySet()) {
                    OWLLiteral object = map.get(subject);
                    printTriple(subject, predicate, object, w);
                }
            }

            for (URI subject : new ArrayList<URI>(resTriplesBySubject.keySet())) {
                Map<URI, Set<URI>> map = resTriplesBySubject.get(subject);
                for (URI predicate : new ArrayList<URI>(map.keySet())) {
                    Set<URI> objects = map.get(predicate);
                    for (URI object : objects) {
                        printTriple(subject, predicate, object, w);
                    }
                }
            }
            for (URI subject : new ArrayList<URI>(litTriplesBySubject.keySet())) {
                Map<URI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
                for (URI predicate : new ArrayList<URI>(map.keySet())) {
                    Set<OWLLiteral> objects = map.get(predicate);
                    for (OWLLiteral object : objects) {
                        printTriple(subject, predicate, object, w);
                    }
                }
            }
            w.flush();
            logger.fine(sw.getBuffer().toString());
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Debug stuff

    private int count = 0;


    private void incrementTripleCount() {
        count++;
        if (tripleProcessor.isLoggable(Level.FINE) && count % 10000 == 0) {
            tripleProcessor.fine("Parsed: " + count + " triples");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void startModel(String string) throws SAXException {
        count = 0;
        t0 = System.currentTimeMillis();
    }


    /**
     * This is where we do all remaining parsing
     */
    public void endModel() throws SAXException {
        try {
            uriMap.clear();

            tripleProcessor.fine("Total number of triples: " + count);
            RDFXMLOntologyFormat format = getOntologyFormat();
            if (format != null) {
                format.setNumberOfTriplesProcessedDuringLoading(count);
            }

            // Do we need to change the ontology URI?
            if (!ontologyURIs.contains(ontology.getURI())) {
                if (ontologyURIs.size() == 1) {
                    owlOntologyManager.applyChange(new SetOntologyURI(ontology, firstOntologyURI));
                }
                else {
                    if (ontologyURIs.isEmpty()) {
                        if (xmlBase == null) {
                            logger.fine(
                                    "There are no resources which are typed as ontologies.  Cannot determine the URI of the ontology being parsed - using physical URI.");
                        }
                        else {
                            logger.fine(
                                    "There are no resources which are typed as ontologies.  Cannot determine the URI of the ontology being parsed - using xml:base.");
                            owlOntologyManager.applyChange(new SetOntologyURI(ontology, xmlBase));
                        }
                    }
                    else {
                        logger.fine(
                                "There are multiple resources which are typed as ontologies.  Using the first encountered ontology URI.");
                        owlOntologyManager.applyChange(new SetOntologyURI(ontology, firstOntologyURI));
                    }
                }
            }


            if (tripleProcessor.isLoggable(Level.FINE)) {
                tripleProcessor.fine("Loaded " + ontology.getURI());
            }

            // First mop up any rules triples
            SWRLRuleTranslator translator = new SWRLRuleTranslator(this);
            for (URI ruleURI : swrlRules) {
                translator.translateRule(ruleURI);
            }

            // We need to mop up all remaining triples.  These triples will be in the
            // triples by subject map.  Other triples which reside in the triples by
            // predicate (single valued) triple aren't "root" triples for axioms.  First
            // we translate all system triples and then go for triples whose predicates
            // are not system/reserved vocabulary URIs to translate these into ABox assertions
            // or annotations
            for (URI subject : new ArrayList<URI>(resTriplesBySubject.keySet())) {
                Map<URI, Set<URI>> map = resTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (URI predicate : new ArrayList<URI>(map.keySet())) {
                    Set<URI> objects = map.get(predicate);
                    if (objects == null) {
                        continue;
                    }
                    for (URI object : new ArrayList<URI>(objects)) {
                        handle(subject, predicate, object);
                    }
                }
            }

            // TODO: TIDY UP!  This is a copy and paste hack!!
            // Now for the ABox assertions and annotations
            for (URI subject : new ArrayList<URI>(resTriplesBySubject.keySet())) {
                Map<URI, Set<URI>> map = resTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (URI predicate : new ArrayList<URI>(map.keySet())) {
                    Set<URI> objects = map.get(predicate);
                    if (objects == null) {
                        continue;
                    }
                    for (URI object : new ArrayList<URI>(objects)) {
                        for (AbstractResourceTripleHandler resTripHandler : resourceTripleHandlers) {
                            if (resTripHandler.canHandle(subject, predicate, object)) {
                                resTripHandler.handleTriple(subject, predicate, object);
                                break;
                            }
                        }
                    }
                }
            }


            for (URI subject : new ArrayList<URI>(litTriplesBySubject.keySet())) {
                Map<URI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (URI predicate : new ArrayList<URI>(map.keySet())) {
                    Set<OWLLiteral> objects = map.get(predicate);
                    for (OWLLiteral object : new ArrayList<OWLLiteral>(objects)) {
                        handle(subject, predicate, object);
                    }
                }
            }

            translateDanglingEntities();

            dumpRemainingTriples();
        }
        catch (OWLException e) {
            throw new SAXException(e);
        }
        cleanup();
    }


    private void translateDanglingEntities() throws OWLException {
        owlClassURIs.remove(OWLRDFVocabulary.OWL_THING.getURI());
        owlClassURIs.remove(OWLRDFVocabulary.OWL_NOTHING.getURI());
        for (URI clsURI : owlClassURIs) {
            if (!isAnonymousNode(clsURI)) {
                OWLClass cls = getDataFactory().getOWLClass(clsURI);
                addDeclarationIfNecessary(cls);
            }
        }
        for(URI propURI : objectPropertyURIs) {
            if (!isAnonymousNode(propURI)) {
                OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(propURI);
                addDeclarationIfNecessary(prop);
            }
        }
        for(URI propURI : dataPropertyURIs) {
            OWLDataProperty prop = getDataFactory().getOWLDataProperty(propURI);
            addDeclarationIfNecessary(prop);
        }
        // We don't need to do this with individuals, since there is no
        // such things a x rdf:type OWLIndividual
    }


    private void addDeclarationIfNecessary(OWLEntity entity) throws OWLException {
        OWLOntology ont = getOntology();
        if (!ont.containsEntityReference(entity)) {
            boolean ref = false;
            for (OWLOntology o : getOWLOntologyManager().getImportsClosure(ont)) {
                if (!o.equals(ont)) {
                    if (ref = o.containsEntityReference(entity)) {
                        break;
                    }
                }
            }
            if (!ref) {
                addAxiom(getDataFactory().getOWLDeclarationAxiom(entity));
            }
        }
    }


    private void cleanup() {
        owlClassURIs.clear();
        objectPropertyURIs.clear();
        dataPropertyURIs.clear();
        dataRangeURIs.clear();
        restrictionURIs.clear();
        objectRestrictionURIs.clear();
        dataRestrictionURIs.clear();
        listFirstLiteralTripleMap.clear();
        listFirstResourceTripleMap.clear();
        listRestTripleMap.clear();
        translatedDescriptions.clear();
        listURIs.clear();
        resTriplesBySubject.clear();
        litTriplesBySubject.clear();
        singleValuedLitTriplesByPredicate.clear();
        singleValuedResTriplesByPredicate.clear();
    }


    public void addModelAttribte(String string, String string1) throws SAXException {
    }


    public void includeModel(String string, String string1) throws SAXException {

    }


    public void logicalURI(String string) throws SAXException {

    }


    public URI checkForSynonym(URI original) {
        URI synonymURI = synonymMap.get(original);
        if (synonymURI != null) {
            return synonymURI;
        }
        return original;
    }

    public void statementWithLiteralValue(String subject, String predicate, String object, String lang,
                                          String datatype) throws SAXException {
        incrementTripleCount();
        try {
            URI subjectURI = getURI(subject);
            URI predicateURI = getURI(predicate);
            predicateURI = checkForSynonym(predicateURI);
            handleStreaming(subjectURI, predicateURI, object, datatype, lang);
        }
        catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void statementWithResourceValue(String subject, String predicate, String object) throws SAXException {
        try {
            incrementTripleCount();
            URI subjectURI = getURI(subject);
            URI predicateURI = getURI(predicate);
            predicateURI = checkForSynonym(predicateURI);
            URI objectURI = checkForSynonym(getURI(object));
            handleStreaming(subjectURI, predicateURI, objectURI);
        }
        catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }


    private int addCount = 0;


    /**
     * Called when a resource triple has been parsed.
     * @param subject   The subject of the triple that has been parsed
     * @param predicate The predicate of the triple that has been parsed
     * @param object    The object of the triple that has been parsed
     */
    private void handleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        if (predicate.equals(RDF_TYPE.getURI())) {
            BuiltInTypeHandler handler = builtInTypeTripleHandlers.get(object);
            if (handler != null) {
                if (handler.canHandleStreaming(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                    // Consumed the triple - no further processing
                    return;
                }
            }
            else {
                // Individual?
                addIndividual(subject);
            }
        }
        AbstractResourceTripleHandler handler = predicateHandlers.get(predicate);
        if (handler != null) {
            if (handler.canHandleStreaming(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
                return;
            }
        }
//        if(addCount < 10000) {
//            if(!predicate.equals(OWLRDFVocabulary.OWL_ON_PROPERTY.getURI())) {
//                addCount++;
//            }

//        }
        // Not consumed, so add the triple
        addTriple(subject, predicate, object);
    }

    private void handleStreaming(URI subject, URI predicate, String literal, String datatype, String lang) throws
                                                                                                           OWLException {
        // Convert all literals to OWLConstants
        OWLLiteral con = getOWLConstant(literal, datatype, lang);
        AbstractLiteralTripleHandler skosHandler = skosTripleHandlers.get(predicate);
        if(skosHandler != null) {
            skosHandler.handleTriple(subject, predicate, con);
            return;
        }
        for (AbstractLiteralTripleHandler handler : literalTripleHandlers) {
            if (handler.canHandleStreaming(subject, predicate, con)) {
                handler.handleTriple(subject, predicate, con);
                return;
            }
        }
        addTriple(subject, predicate, con);
    }


    /**
     * A convenience method to obtain an <code>OWLConstant</code>
     * @param literal  The literal - must NOT be <code>null</code>
     * @param datatype The data type - may be <code>null</code>
     * @param lang     The lang - may be <code>null</code>
     * @return The <code>OWLConstant</code> (either typed or untyped depending on the params)
     */
    private OWLLiteral getOWLConstant(String literal, String datatype, String lang) {
        if (datatype != null) {
            return dataFactory.getOWLTypedLiteral(literal, dataFactory.getOWLDatatype(getURI(datatype)));
        }
        else {
            if (lang != null) {
                return dataFactory.getOWLUntypedConstant(literal, lang);
            }
            else {
                return dataFactory.getOWLUntypedConstant(literal);
            }
        }
    }


    public OWLDataRange translateDataRange(URI uri) throws OWLException {
        URI oneOfObject = getResourceObject(uri, OWL_ONE_OF.getURI(), true);
        if (oneOfObject != null) {
            Set<OWLLiteral> literals = translateToConstantSet(oneOfObject);
            Set<OWLTypedLiteral> typedConstants = new HashSet<OWLTypedLiteral>(literals.size());
            for (OWLLiteral con : literals) {
                if (con.isTyped()) {
                    typedConstants.add((OWLTypedLiteral) con);
                }
                else {
                    typedConstants.add(getDataFactory().getOWLTypedLiteral(con.getString(),
                                                                            getDataFactory().getOWLDatatype(
                                                                                    XSDVocabulary.STRING.getURI())));
                }
            }
            return getDataFactory().getOWLDataOneOf(typedConstants);
        }
        URI complementOfObject = getResourceObject(uri, OWL_COMPLEMENT_OF.getURI(), true);
        if (complementOfObject != null) {
            OWLDataRange operand = translateDataRange(complementOfObject);
            return getDataFactory().getOWLDataComplementOf(operand);
        }
        URI onDataRangeObject = getResourceObject(uri, OWL_ON_DATA_RANGE.getURI(), true);
        if (onDataRangeObject != null) {
            OWLDataRange restrictedDataRange = translateDataRange(onDataRangeObject);
            // Get facet(s!)
            // Get facet value

            Set<OWLDataRangeFacetRestriction> restrictions = new HashSet<OWLDataRangeFacetRestriction>();

            for (URI facetURI : OWLRestrictedDataRangeFacetVocabulary.FACET_URIS) {
                OWLLiteral val;
                while ((val = getLiteralObject(uri, facetURI, true)) != null) {
                    if (val.isTyped()) {
                        restrictions.add(dataFactory.getOWLDataRangeFacetRestriction(
                                OWLRestrictedDataRangeFacetVocabulary.getFacet(facetURI),
                                (OWLTypedLiteral) val));
                    }
                    else {
                        restrictions.add(dataFactory.getOWLDataRangeFacetRestriction(
                                OWLRestrictedDataRangeFacetVocabulary.getFacet(facetURI),
                                dataFactory.getOWLTypedLiteral(val.getString(),
                                                                OWLDataUtil.getIntDataType(dataFactory))));
                    }
                }
            }

            return dataFactory.getOWLDataRangeRestriction(restrictedDataRange, restrictions);
        }
        return getDataFactory().getOWLDatatype(uri);
    }


    public OWLDataPropertyExpression translateDataPropertyExpression(URI uri) throws OWLException {
        return dataFactory.getOWLDataProperty(uri);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Basic node translation - translation of entities
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<URI, OWLObjectPropertyExpression> translatedProperties = new HashMap<URI, OWLObjectPropertyExpression>();

    public OWLObjectPropertyExpression translateObjectPropertyExpression(URI mainNode) throws OWLException {
        OWLObjectPropertyExpression prop = translatedProperties.get(mainNode);
        if(prop != null) {
            return prop;
        }
        if (isList(mainNode, false)) {
            throw new OWLRDFParserException("Attempting to translate an object property list as a property expression!");
        }
        addOWLObjectProperty(mainNode);
        if (!isAnonymousNode(mainNode)) {
            // Simple object property
            prop = getDataFactory().getOWLObjectProperty(mainNode);
            translatedProperties.put(mainNode, prop);
            return prop;
        }
        else {
            // Inverse of a property expression
            
            URI inverseOfObject = getResourceObject(mainNode, OWL_INVERSE_OF.getURI(), true);
            if (inverseOfObject == null) {
                throw new IllegalStateException(
                        "Attempting to translate inverse property (anon property), but inverseOf triple is missing (" + mainNode + ")");
            }
            OWLObjectPropertyExpression otherProperty = translateObjectPropertyExpression(inverseOfObject);
            prop = getDataFactory().getOWLObjectPropertyInverse(otherProperty);
            translatedProperties.put(mainNode, prop);
            return prop;
        }
    }


    public OWLIndividual translateIndividual(URI node) throws OWLException {
        return getOWLIndividual(node);
    }


    private Map<URI, OWLDescription> translatedDescriptions = new HashMap<URI, OWLDescription>();


    public OWLDescription translateDescription(URI mainNode) throws OWLException {
        if (!isAnonymousNode(mainNode)) {
            return getDataFactory().getOWLClass(mainNode);
        }
        OWLDescription desc = translatedDescriptions.get(mainNode);
        if (desc == null) {
            DescriptionTranslator translator = descriptionTranslatorSelector.getDescriptionTranslator(mainNode);
            if (translator != null) {
                desc = translator.translate(mainNode);
                translatedDescriptions.put(mainNode, desc);
                restrictionURIs.remove(mainNode);
                objectRestrictionURIs.remove(mainNode);
            }
            else {
                logger.fine(
                        "Unable to determine the type of description from the available triples - assuming owl:Class");
                return getDataFactory().getOWLClass(mainNode);
            }
        }
        return desc;
    }


    public OWLDescription getDescriptionIfTranslated(URI mainNode) {
        return translatedDescriptions.get(mainNode);
    }


    public List<OWLObjectPropertyExpression> translateToObjectPropertyList(URI mainNode) throws OWLException {
        return objectPropertyListTranslator.translateList(mainNode);
    }

    public List<OWLDataPropertyExpression> translateToDataPropertyList(URI mainNode) throws OWLException {
        return dataPropertyListTranslator.translateList(mainNode);
    }

    public Set<OWLDescription> translateToDescriptionSet(URI mainNode) throws OWLException {
        return descriptionListTranslator.translateToSet(mainNode);
    }


    public Set<OWLLiteral> translateToConstantSet(URI mainNode) throws OWLException {
        return constantListTranslator.translateToSet(mainNode);
    }


    public Set<OWLIndividual> translateToIndividualSet(URI mainNode) throws OWLException {
        return individualListTranslator.translateToSet(mainNode);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public URI getResourceObject(URI subject, URI predicate, boolean consume) {
        Map<URI, URI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            URI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<URI, Set<URI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<URI> objects = predObjMap.get(predicate);
            if (objects != null) {
                if (!objects.isEmpty()) {
                    URI object = objects.iterator().next();
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


    public OWLLiteral getLiteralObject(URI subject, URI predicate, boolean consume) {
        Map<URI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<URI, Set<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<OWLLiteral> objects = predObjMap.get(predicate);
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


    public boolean isTriplePresent(URI subject, URI predicate, URI object, boolean consume) {
        Map<URI, URI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            URI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<URI, Set<URI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<URI> objects = predObjMap.get(predicate);
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
        return false;
    }


    public boolean isTriplePresent(URI subject, URI predicate, OWLLiteral object, boolean consume) {
        Map<URI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<URI, Set<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<OWLLiteral> objects = predObjMap.get(predicate);
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


    public boolean hasPredicate(URI subject, URI predicate) {
        Map<URI, URI> resPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (resPredMap != null) {
            return resPredMap.containsKey(subject);
        }
        Map<URI, OWLLiteral> litPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (litPredMap != null) {
            return litPredMap.containsKey(subject);
        }
        Map<URI, Set<URI>> resPredObjMap = resTriplesBySubject.get(subject);
        if (resPredObjMap != null) {
            boolean b = resPredObjMap.containsKey(predicate);
            if (b) {
                return true;
            }
        }
        Map<URI, Set<OWLLiteral>> litPredObjMap = litTriplesBySubject.get(subject);
        if (litPredObjMap != null) {
            return litPredObjMap.containsKey(predicate);
        }
        return false;
    }


    public boolean hasPredicateObject(URI subject, URI predicate, URI object) throws OWLException {
        Map<URI, URI> predMap = singleValuedResTriplesByPredicate.get(predicate);
        if (predMap != null) {
            URI objectURI = predMap.get(subject);
            if (objectURI == null) {
                return false;
            }
            return objectURI.equals(object);
        }
        Map<URI, Set<URI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<URI> objects = predObjMap.get(predicate);
            if (objects != null) {
                return objects.contains(object);
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    public void addList(URI uri) {
        listURIs.add(uri);
    }


    public boolean isList(URI uri, boolean consume) {
        if (consume) {
            return listURIs.remove(uri);
        }
        else {
            return listURIs.contains(uri);
        }
    }


    public void addRest(URI subject, URI object) {
        listRestTripleMap.put(subject, object);
    }


    public void addFirst(URI subject, URI object) {
        listFirstResourceTripleMap.put(subject, object);
    }


    public URI getFirstResource(URI subject, boolean consume) {
        if (consume) {
            return listFirstResourceTripleMap.remove(subject);
        }
        else {
            return listFirstResourceTripleMap.get(subject);
        }
    }


    public OWLLiteral getFirstLiteral(URI subject) {
        return listFirstLiteralTripleMap.get(subject);
    }


    public URI getRest(URI subject, boolean consume) {
        if (consume) {
            return listRestTripleMap.remove(subject);
        }
        else {
            return listRestTripleMap.get(subject);
        }
    }


    public void addFirst(URI subject, OWLLiteral object) {
        listFirstLiteralTripleMap.put(subject, object);
    }


    public void addOntology(URI uri) {
        if (ontologyURIs.isEmpty()) {
            firstOntologyURI = uri;
        }
        ontologyURIs.add(uri);
    }


    public void addReifiedAxiom(URI axiomURI, OWLAxiom axiom) {
        reifiedAxiomsMap.put(axiomURI, axiom);
    }


    public boolean isAxiom(URI uri) {
        return reifiedAxiomsMap.containsKey(uri);
    }


    public OWLAxiom getAxiom(URI uri) {
        return reifiedAxiomsMap.get(uri);
    }


    public boolean isDataRange(URI uri) {
        return dataRangeURIs.contains(uri);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////
    //////  Triple Stuff
    //////
    //////
    //////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Originally we had a special Triple class, which was specialised into ResourceTriple and
        LiteralTriple - this was used to store triples.  However, with very large ontologies this
        proved to be inefficient in terms of memory usage.  Now we just store raw subjects, predicates and
        object directly in varous maps.
    */

    // Resource triples

    // Subject, predicate, object

    private Map<URI, Map<URI, Set<URI>>> resTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<URI, Map<URI, URI>> singleValuedResTriplesByPredicate = CollectionFactory.createMap();

    // Literal triples
    private Map<URI, Map<URI, Set<OWLLiteral>>> litTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<URI, Map<URI, OWLLiteral>> singleValuedLitTriplesByPredicate = CollectionFactory.createMap();


    public void addTriple(URI subject, URI predicate, URI object) {
        Map<URI, URI> subjObjMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, object);
        }
        else {
            Map<URI, Set<URI>> map = resTriplesBySubject.get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                resTriplesBySubject.put(subject, map);
            }
            Set<URI> objects = map.get(predicate);
            if (objects == null) {
                objects = new FakeSet();
                map.put(predicate, objects);
            }
            objects.add(object);
        }
    }


    public void addTriple(URI subject, URI predicate, OWLLiteral con) {
        Map<URI, OWLLiteral> subjObjMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, con);
        }
        else {
            Map<URI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                litTriplesBySubject.put(subject, map);
            }
            Set<OWLLiteral> objects = map.get(predicate);
            if (objects == null) {
                objects = new FakeSet();
                map.put(predicate, objects);
            }
            objects.add(con);
        }
    }


    public void setXMLBase(String base) {
        this.xmlBase = URI.create(base);
    }




    private static class FakeSet<O> extends ArrayList<O> implements Set<O> {

        public FakeSet() {
        }


        public FakeSet(Collection<? extends O> c) {
            super(c);
        }
    }
}
