package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.rdf.syntax.RDFConsumer;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.*;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import org.xml.sax.SAXException;

import java.io.PrintWriter;
import java.io.StringWriter;
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
 * A parser/interpreter for an RDF graph which represents an OWL ontology.  The
 * consumer interprets triple patterns in the graph to produce the appropriate
 * OWLAPI entities, class expressions and axioms.
 * The parser is based on triple handlers.  A given triple handler handles a specific
 * type of triple.  Generally speaking this is based on the predicate of a triple, for
 * example, A rdfs:subClassOf B is handled by a subClassOf handler.  A handler determines
 * if it can handle a triple in a streaming mode (i.e. while parsing is taking place) or
 * if it can handle a triple after parsing has taken place and the complete graph is in
 * memory.  Once a handler handles a triple, that triple is deemed to have been consumed
 * an is discarded.
 * The parser attempts to consume as many triples as possible while streaming parsing
 * is taking place. Whether or not a triple can be consumed dIRIng parsing is determined
 * by installed triple handlers.
 */
public class OWLRDFConsumer implements RDFConsumer {

    public static final Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    private static final Logger tripleProcessor = Logger.getLogger("Triple processor");

    private boolean strict = false;

    //private Graph graph;

    private OWLOntologyManager owlOntologyManager;

    private IRI xmlBase;

    // A call back interface, which is used to check whether a node
    // is anonymous or not.
    private AnonymousNodeChecker anonymousNodeChecker;

    // The set of IRIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owlapi:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    private Set<IRI> owlClassIRIs;

    // Same as owlClassIRIs but for object properties
    private Set<IRI> objectPropertyIRIs;

    // Same as owlClassIRIs but for data properties
    private Set<IRI> dataPropertyIRIs;

    // Same as owlClassIRIs but for rdf properties
    // things neither typed as a data or object property - bad!
    private Set<IRI> propertyIRIs;


    // Set of IRIs that are typed by non-system types and
    // also owl:Thing
    private Set<IRI> individualIRIs;


    // Same as owlClassIRIs but for annotation properties
    private Set<IRI> annotationPropertyIRIs;

    private Set<IRI> annotationIRIs;

    private Map<IRI, OWLAnnotation> annotationIRI2Annotation;


    private Set<IRI> ontologyPropertyIRIs;


    // IRIs that had a type triple to rdfs:DataRange
    private Set<IRI> dataRangeIRIs;

    // The IRI of the first reource that is typed as an ontology
    private IRI firstOntologyIRI;

    // IRIs that had a type triple to owl:Ontology
    private Set<IRI> ontologyIRIs;

    // IRIs that had a type triple to owl:Restriction
    private Set<IRI> restrictionIRIs;

    // IRIs that had a type triple to rdf:List
    private Set<IRI> listIRIs;

    // Maps rdf:next triple subjects to objects
    private Map<IRI, IRI> listRestTripleMap;

    private Map<IRI, IRI> listFirstResourceTripleMap;

    private Map<IRI, OWLLiteral> listFirstLiteralTripleMap;

    private Map<IRI, OWLAxiom> reifiedAxiomsMap;

    private Map<IRI, Set<OWLAnnotation>> annotationsBySubject;

    // A translator for lists of class expressions (such lists are used
    // in intersections, unions etc.)
    private OptimisedListTranslator<OWLClassExpression> classExpressionListTranslator;

    // A translator for individual lists (such lists are used in
    // object oneOf constructs)
    private OptimisedListTranslator<OWLIndividual> individualListTranslator;

    private OptimisedListTranslator<OWLObjectPropertyExpression> objectPropertyListTranslator;

    private OptimisedListTranslator<OWLLiteral> constantListTranslator;

    private OptimisedListTranslator<OWLDataPropertyExpression> dataPropertyListTranslator;

    private OptimisedListTranslator<OWLDataRange> dataRangeListTranslator;

    private OptimisedListTranslator<OWLFacetRestriction> faceRestrictionListTranslator;

    // Handlers for built in types
    private Map<IRI, BuiltInTypeHandler> builtInTypeTripleHandlers;

    // Handlers for build in predicates
    private Map<IRI, TriplePredicateHandler> predicateHandlers;

    private Map<IRI, AbstractLiteralTripleHandler> skosTripleHandlers;

    // Handlers for general literal triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary.  Such triples either constitute annotationIRIs of
    // relationships between an individual and a data literal (typed or
    // untyped)
    private List<AbstractLiteralTripleHandler> literalTripleHandlers;

    // Handlers for general resource triples (i.e. triples which
    // have predicates that are not part of the built in OWL/RDFS/RDF
    // vocabulary.  Such triples either constitute annotationIRIs or
    // relationships between an individual and another individual.
    private List<AbstractResourceTripleHandler> resourceTripleHandlers;

    private Set<OWLAnnotation> pendingAnnotations = new HashSet<OWLAnnotation>();

    private Map<IRI, Set<IRI>> annotatedAnonSource2AnnotationMap = new HashMap<IRI, Set<IRI>>();

    /**
     * The ontology that the RDF will be parsed into
     */
    private OWLOntology ontology;

    private RDFXMLOntologyFormat rdfxmlOntologyFormat;

    private OWLDataFactory dataFactory;

    private ClassExpressionTranslatorSelector classExpressionTranslatorSelector;

    private OWLAxiom lastAddedAxiom;

    private Map<IRI, IRI> synonymMap;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    // SWRL Stuff

    private Set<IRI> swrlRules;

    private Set<IRI> swrlIndividualPropertyAtoms;

    private Set<IRI> swrlDataValuedPropertyAtoms;

    private Set<IRI> swrlClassAtoms;

    private Set<IRI> swrlDataRangeAtoms;

    private Set<IRI> swrlBuiltInAtoms;

    private Set<IRI> swrlVariables;

    private Set<IRI> swrlSameAsAtoms;

    private Set<IRI> swrlDifferentFromAtoms;

    private IRIProvider iriProvider;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public OWLRDFConsumer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, AnonymousNodeChecker checker) {
        classExpressionTranslatorSelector = new ClassExpressionTranslatorSelector(this);
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        this.dataFactory = owlOntologyManager.getOWLDataFactory();
        this.anonymousNodeChecker = checker;
        owlClassIRIs = CollectionFactory.createSet();
        objectPropertyIRIs = CollectionFactory.createSet();
        dataPropertyIRIs = CollectionFactory.createSet();
        individualIRIs = CollectionFactory.createSet();
        annotationPropertyIRIs = CollectionFactory.createSet();
        for (IRI iri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            annotationPropertyIRIs.add(iri);
        }
        annotationIRIs = new HashSet<IRI>();
        annotationIRI2Annotation = new HashMap<IRI, OWLAnnotation>();
        annotationsBySubject = new HashMap<IRI, Set<OWLAnnotation>>();
        ontologyPropertyIRIs = CollectionFactory.createSet();
        ontologyPropertyIRIs.add(OWLRDFVocabulary.OWL_PRIOR_VERSION.getIRI());
        ontologyPropertyIRIs.add(OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
        ontologyPropertyIRIs.add(OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());

        addDublinCoreAnnotationIRIs();
        dataRangeIRIs = CollectionFactory.createSet();
        propertyIRIs = CollectionFactory.createSet();
        restrictionIRIs = CollectionFactory.createSet();
        ontologyIRIs = CollectionFactory.createSet();
        listIRIs = CollectionFactory.createSet();
        listFirstLiteralTripleMap = CollectionFactory.createMap();
        listFirstResourceTripleMap = CollectionFactory.createMap();
        listRestTripleMap = CollectionFactory.createMap();
        reifiedAxiomsMap = CollectionFactory.createMap();
        classExpressionListTranslator = new OptimisedListTranslator<OWLClassExpression>(this, new ClassExpressionListItemTranslator(this));
        individualListTranslator = new OptimisedListTranslator<OWLIndividual>(this, new IndividualListItemTranslator(this));
        constantListTranslator = new OptimisedListTranslator<OWLLiteral>(this, new TypedConstantListItemTranslator(this));
        objectPropertyListTranslator = new OptimisedListTranslator<OWLObjectPropertyExpression>(this, new ObjectPropertyListItemTranslator(this));

        dataPropertyListTranslator = new OptimisedListTranslator<OWLDataPropertyExpression>(this, new DataPropertyListItemTranslator(this));

        dataRangeListTranslator = new OptimisedListTranslator<OWLDataRange>(this, new DataRangeListItemTranslator(this));

        faceRestrictionListTranslator = new OptimisedListTranslator<OWLFacetRestriction>(this, new OWLFacetRestrictionListItemTranslator(this));

        builtInTypeTripleHandlers = CollectionFactory.createMap();
        setupTypeTripleHandlers();
        setupPredicateHandlers();

        // General literal triples - i.e. triples which have a predicate
        // that is not a built in IRI.  Annotation properties get precedence
        // over data properties, so that if we have the statement a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the statement will be
        // translated as an annotation on a:A
        literalTripleHandlers = new ArrayList<AbstractLiteralTripleHandler>();
        literalTripleHandlers.add(new GTPAnnotationLiteralHandler(this));
        literalTripleHandlers.add(new GTPDataPropertyAssertionHandler(this));
        literalTripleHandlers.add(new TPFirstLiteralHandler(this));

        // General resource/object triples - i.e. triples which have a predicate
        // that is not a built in IRI.  Annotation properties get precedence
        // over object properties, so that if we have the statement a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the statement will be
        // translated as an annotation on a:A
        resourceTripleHandlers = new ArrayList<AbstractResourceTripleHandler>();
        resourceTripleHandlers.add(new GTPAnnotationResourceTripleHandler(this));
        resourceTripleHandlers.add(new GTPObjectPropertyAssertionHandler(this));

        for (OWL2Datatype dt : OWL2Datatype.values()) {
            dataRangeIRIs.add(dt.getIRI());
        }
        dataRangeIRIs.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        for (OWL2Datatype dt : OWL2Datatype.values()) {
            dataRangeIRIs.add(dt.getIRI());
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

        owlClassIRIs.add(OWLRDFVocabulary.OWL_THING.getIRI());
        owlClassIRIs.add(OWLRDFVocabulary.OWL_NOTHING.getIRI());

        objectPropertyIRIs.add(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
        objectPropertyIRIs.add(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());

        dataPropertyIRIs.add(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
        dataPropertyIRIs.add(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());


        setupSynonymMap();
        setupSinglePredicateMaps();

        setupSKOSTipleHandlers();
    }

    /**
     * Determines if strict parsing should be used.  If strict parsing is in opertion then no patching, fixing or type
     * inference should be done.
     *
     * @return <code>true</code> if strict parsing should be used, otherwise <code>false</code>.
     */
    public boolean isStrict() {
        return strict;
    }

    public void setIRIProvider(IRIProvider iriProvider) {
        this.iriProvider = iriProvider;
    }

    private void addSingleValuedResPredicate(OWLRDFVocabulary v) {
        Map<IRI, IRI> map = CollectionFactory.createMap();
        singleValuedResTriplesByPredicate.put(v.getIRI(), map);
    }


    private void addDublinCoreAnnotationIRIs() {
        for (DublinCoreVocabulary v : DublinCoreVocabulary.values()) {
            annotationPropertyIRIs.add(v.getIRI());
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
        // Legacy protege-owlapi representation of QCRs
        synonymMap.put(IRI.create(Namespaces.OWL + "valuesFrom"), OWL_ON_CLASS.getIRI());

        // Intermediate OWL 2 spec
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());

        // Preliminary OWL 1.1 Vocab
        synonymMap.put(IRI.create(Namespaces.OWL + "cardinalityType"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL + "dataComplementOf"), OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(OWL_ANTI_SYMMETRIC_PROPERTY.getIRI(), OWL_ASYMMETRIC_PROPERTY.getIRI());

        synonymMap.put(OWL_DATA_RANGE.getIRI(), OWL_DATATYPE.getIRI());

        // DAML+OIL -> OWL
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#subClassOf"), RDFS_SUBCLASS_OF.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#imports"), OWL_IMPORTS.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#range"), RDFS_RANGE.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#hasValue"), OWL_HAS_VALUE.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#type"), RDF_TYPE.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#domain"), RDFS_DOMAIN.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#versionInfo"), OWL_VERSION_INFO.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#comment"), RDFS_COMMENT.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#onProperty"), OWL_ON_PROPERTY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#toClass"), OWL_ALL_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#hasClass"), OWL_SOME_VALUES_FROM.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#Restriction"), OWL_RESTRICTION.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#Class"), OWL_CLASS.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#Thing"), OWL_THING.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#Nothing"), OWL_NOTHING.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#minCardinality"), OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#cardinality"), OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#maxCardinality"), OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#inverseOf"), OWL_INVERSE_OF.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#samePropertyAs"), OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#hasClassQ"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#cardinalityQ"), OWL_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#maxCardinalityQ"), OWL_MAX_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#minCardinalityQ"), OWL_MIN_CARDINALITY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#complementOf"), OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#unionOf"), OWL_UNION_OF.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#intersectionOf"), OWL_INTERSECTION_OF.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#label"), RDFS_LABEL.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#ObjectProperty"), OWL_OBJECT_PROPERTY.getIRI());
        synonymMap.put(IRI.create("http://www.daml.org/2001/03/daml+oil#DatatypeProperty"), OWL_DATA_PROPERTY.getIRI());
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
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(IRI.create(Namespaces.OWL.toString() + v.getShortName()), v.getIRI());
            synonymMap.put(IRI.create(Namespaces.OWL11.toString() + v.getShortName()), v.getIRI());
            synonymMap.put(IRI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getIRI());
        }
        for (OWLFacet v : OWLFacet.values()) {
            synonymMap.put(IRI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getIRI());
        }

        synonymMap.put(OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getIRI(), OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        synonymMap.put(OWLRDFVocabulary.OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION.getIRI(), OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
    }


    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(IRI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL11.toString() + v.getShortName()), v.getIRI());
    }

    private void setupSKOSTipleHandlers() {
        skosTripleHandlers = new HashMap<IRI, AbstractLiteralTripleHandler>();
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
        skosTripleHandlers.put(dataPredicate.getIRI(), new SKOSDataTripleHandler(this, dataPredicate.getIRI()));
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
        builtInTypeTripleHandlers.put(handler.getTypeIRI(), handler);
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
        addBuiltInTypeTripleHandler(new TypeDeprecatedPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDataRangeHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDifferentHandler(this));
        addBuiltInTypeTripleHandler(new TypeOntologyHandler(this));
        addBuiltInTypeTripleHandler(new TypeNegativePropertyAssertionHandler(this));
        addBuiltInTypeTripleHandler(new TypeNegativeDataPropertyAssertionHandler(this));
        addBuiltInTypeTripleHandler(new TypeAxiomHandler(this));
        addBuiltInTypeTripleHandler(new TypeRDFPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeRDFSClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeSelfRestrictionHandler(this));
        addBuiltInTypeTripleHandler(new TypePropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDisjointClassesHandler(this));
        addBuiltInTypeTripleHandler(new TypeAllDisjointPropertiesHandler(this));
        addBuiltInTypeTripleHandler(new TypeNamedIndividualHandler(this));
        addBuiltInTypeTripleHandler(new TypeAnnotationHandler(this));

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
        predicateHandlers.put(predicateHandler.getPredicateIRI(), predicateHandler);
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
        addPredicateHandler(new TPHasKeyHandler(this));
        addPredicateHandler(new TPVersionIRIHandler(this));
        addPredicateHandler(new TPPropertyChainAxiomHandler(this));
        addPredicateHandler(new TPAnnotatedSourceHandler(this));
        addPredicateHandler(new TPPropertyDisjointWithHandler(this));

        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.BROADER));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.NARROWER));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.RELATED));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.HASTOPCONCEPT));
        addPredicateHandler(new SKOSObjectTripleHandler(this, SKOSVocabulary.SEMANTICRELATION));


    }


    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }


    // We cache IRIs to save memory!!
    private Map<String, IRI> IRIMap = CollectionFactory.createMap();

    int currentBaseCount = 0;

    /**
     * Gets any annotations that were translated since the last call of this method (calling
     * this method clears the current pending annotations)
     *
     * @return The set (possibly empty) of pending annotations.
     */
    public Set<OWLAnnotation> getPendingAnnotations() {
        if (!pendingAnnotations.isEmpty()) {
            Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(pendingAnnotations);
            pendingAnnotations.clear();
            return annos;
        }
        else {
            return Collections.emptySet();
        }
    }

    public void setPendingAnnotations(Set<OWLAnnotation> annotations) {
        pendingAnnotations.clear();
        pendingAnnotations.addAll(annotations);
    }


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

    public void importsClosureChanged() {
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            for (OWLAnnotationProperty prop : ont.getAnnotationPropertiesInSignature()) {
                annotationPropertyIRIs.add(prop.getIRI());
            }
        }
    }


    /**
     * Checks whether a node is anonymous.
     *
     * @param iri The IRI of the node to be checked.
     * @return <code>true</code> if the node is anonymous, or
     *         <code>false</code> if the node is not anonymous.
     */
    protected boolean isAnonymousNode(IRI iri) {
        return anonymousNodeChecker.isAnonymousNode(iri);
    }


    protected void addAxiom(OWLAxiom axiom) {
        owlOntologyManager.applyChange(new AddAxiom(ontology, axiom));
        lastAddedAxiom = axiom;
    }

    protected void applyChange(OWLOntologyChange change) {
        owlOntologyManager.applyChange(change);
    }

    protected void setOntologyID(OWLOntologyID ontologyID) {
        applyChange(new SetOntologyID(ontology, ontologyID));
    }

    protected void addOntologyAnnotation(OWLAnnotation annotation) {
        applyChange(new AddOntologyAnnotation(getOntology(), annotation));
    }

    protected void addImport(OWLImportsDeclaration declaration) {
        applyChange(new AddImport(ontology, declaration));
    }

    public OWLAxiom getLastAddedAxiom() {
        return lastAddedAxiom;
    }


    protected void addOWLClass(IRI iri) {
        owlClassIRIs.add(iri);
    }


    protected void addOWLObjectProperty(IRI iri) {
        objectPropertyIRIs.add(iri);
    }


    protected void addIndividual(IRI iri) {
        individualIRIs.add(iri);
    }


    protected boolean isIndividual(IRI iri) {
        return individualIRIs.contains(iri);
    }


    protected void addRDFProperty(IRI iri) {
        propertyIRIs.add(iri);
    }


    protected boolean isRDFProperty(IRI iri) {
        return propertyIRIs.contains(iri);
    }


    protected void addOWLDataProperty(IRI iri) {
        dataPropertyIRIs.add(iri);
    }


    protected void addOWLDatatype(IRI iri) {
        dataRangeIRIs.add(iri);
    }


    public void addOWLDataRange(IRI iri) {
        dataRangeIRIs.add(iri);
    }


    protected void addRestriction(IRI iri) {
        restrictionIRIs.add(iri);
    }


    protected void addAnnotationProperty(IRI iri) {
        annotationPropertyIRIs.add(iri);
    }

    protected void addAnnotationIRI(IRI iri) {
        annotationIRIs.add(iri);
    }


    public boolean isRestriction(IRI iri) {
        return restrictionIRIs.contains(iri);
    }


    protected boolean isClass(IRI iri) {
        if (owlClassIRIs.contains(iri)) {
            return true;
        }
        else {
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsClassInSignature(iri)) {
                    return true;
                }
            }
        }
        return false;
    }


    protected boolean isObjectPropertyOnly(IRI iri) {
        if (iri == null) {
            return false;
        }
        if (dataPropertyIRIs.contains(iri)) {
            return false;
        }
        if (objectPropertyIRIs.contains(iri)) {
            return true;
        }
        else {
            boolean containsObjectPropertyReference = false;
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsDataPropertyInSignature(iri)) {
                    dataPropertyIRIs.add(iri);
                    return false;
                }
                else if (ont.containsObjectPropertyInSignature(iri)) {
                    containsObjectPropertyReference = true;
                    objectPropertyIRIs.add(iri);
                }
            }
            return containsObjectPropertyReference;
        }
    }


    protected boolean isDataPropertyOnly(IRI iri) {
        // I don't like the fact that the check to see if something
        // is a data property only includes a check to make sure that
        // it is not an annotation property.  I think the OWL spec should
        // be altered
        if (objectPropertyIRIs.contains(iri)) {
            return false;
        }
        if (dataPropertyIRIs.contains(iri)) {
            return true;
        }
        else {
            boolean containsDataPropertyReference = false;
            for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
                if (ont.containsObjectPropertyInSignature(iri)) {
                    return false;
                }
                else if (ont.containsDataPropertyInSignature(iri)) {
                    containsDataPropertyReference = true;
                }
            }
            return containsDataPropertyReference;
        }
    }


    protected boolean isOntologyProperty(IRI iri) {
        return ontologyPropertyIRIs.contains(iri);
    }


    protected boolean isAnnotationProperty(IRI iri) {
        if (annotationPropertyIRIs.contains(iri)) {
            return true;
        }
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            if (!ont.equals(ontology)) {
                for (OWLAnnotationProperty prop : ont.getAnnotationPropertiesInSignature()) {
                    annotationPropertyIRIs.add(prop.getIRI());
                }
//                if (ont.getAnnotationIRIs().contains(iri)) {
//                     Cache IRI
//                    annotationPropertyIRIs.addAll(ont.getAnnotationIRIs());
//                    return annotationPropertyIRIs.contains(iri);
//                } else {
//                    OWLOntologyFormat format = owlOntologyManager.getOntologyFormat(ont);
//                    if (format instanceof RDFXMLOntologyFormat) {
//                        RDFXMLOntologyFormat rdfFormat = (RDFXMLOntologyFormat) format;
//                        annotationPropertyIRIs.addAll(rdfFormat.getAnnotationIRIs());
//                        return annotationPropertyIRIs.contains(iri);
//                    }
//                }
            }
        }
        return false;
    }


    protected boolean isOntology(IRI iri) {
        return ontologyIRIs.contains(iri);
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }

    /**
     * Records an annotation of an anonymous node (either an annotation of an annotation, or an annotation
     * of an axiom for example)
     *
     * @param annotatedAnonSource The source that the annotation annotates
     * @param annotationMainNode The annotations
     */
    public void addAnnotatedSource(IRI annotatedAnonSource, IRI annotationMainNode) {
        Set<IRI> annotationMainNodes = annotatedAnonSource2AnnotationMap.get(annotatedAnonSource);
        if (annotationMainNodes == null) {
            annotationMainNodes = new HashSet<IRI>();
            annotatedAnonSource2AnnotationMap.put(annotatedAnonSource, annotationMainNodes);
        }
        annotationMainNodes.add(annotationMainNode);
    }

    /**
     * Gets the main nodes of annotations that annotated the specified source
     *
     * @param source The source (axiom or annotation main node)
     * @return The set of main nodes that annotate the specified source
     */
    public Set<IRI> getAnnotatedSourceAnnotationMainNodes(IRI source) {
        Set<IRI> mainNodes = annotatedAnonSource2AnnotationMap.get(source);
        if (mainNodes != null) {
            return mainNodes;
        }
        else {
            return Collections.emptySet();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Helper methods for creating entities
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected OWLClass getOWLClass(IRI iri) {
        return getDataFactory().getOWLClass(iri);
    }


    protected OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return getDataFactory().getOWLObjectProperty(iri);
    }


    protected OWLDataProperty getOWLDataProperty(IRI iri) {
        return getDataFactory().getOWLDataProperty(iri);
    }


    protected OWLIndividual getOWLIndividual(IRI iri) {
        if (isAnonymousNode(iri)) {
            return getDataFactory().getOWLAnonymousIndividual(iri.toString());
        }
        else {
            return getDataFactory().getOWLNamedIndividual(iri);
        }
    }


    protected void consumeTriple(IRI subject, IRI predicate, IRI object) {
        isTriplePresent(subject, predicate, object, true);
    }


    protected void consumeTriple(IRI subject, IRI predicate, OWLLiteral con) {
        isTriplePresent(subject, predicate, con, true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL Stuff
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected void addSWRLRule(IRI iri) {
        swrlRules.add(iri);
    }


    protected boolean isSWRLRule(IRI iri) {
        return swrlRules.contains(iri);
    }


    protected void addSWRLIndividualPropertyAtom(IRI iri) {
        swrlIndividualPropertyAtoms.add(iri);
    }


    protected boolean isSWRLIndividualPropertyAtom(IRI iri) {
        return swrlIndividualPropertyAtoms.contains(iri);
    }


    protected void addSWRLDataPropertyAtom(IRI iri) {
        swrlDataValuedPropertyAtoms.add(iri);
    }


    protected boolean isSWRLDataValuedPropertyAtom(IRI iri) {
        return swrlDataValuedPropertyAtoms.contains(iri);
    }


    protected void addSWRLClassAtom(IRI iri) {
        swrlClassAtoms.add(iri);
    }


    protected boolean isSWRLClassAtom(IRI iri) {
        return swrlClassAtoms.contains(iri);
    }


    protected void addSWRLSameAsAtom(IRI iri) {
        swrlSameAsAtoms.add(iri);
    }


    protected boolean isSWRLSameAsAtom(IRI iri) {
        return swrlSameAsAtoms.contains(iri);
    }


    protected void addSWRLDifferentFromAtom(IRI iri) {
        swrlDifferentFromAtoms.add(iri);
    }


    protected boolean isSWRLDifferentFromAtom(IRI iri) {
        return swrlDifferentFromAtoms.contains(iri);
    }


    protected void addSWRLDataRangeAtom(IRI iri) {
        swrlDataRangeAtoms.add(iri);
    }


    protected boolean isSWRLDataRangeAtom(IRI iri) {
        return swrlDataRangeAtoms.contains(iri);
    }


    protected void addSWRLBuiltInAtom(IRI iri) {
        swrlBuiltInAtoms.add(iri);
    }


    protected boolean isSWRLBuiltInAtom(IRI iri) {
        return swrlBuiltInAtoms.contains(iri);
    }


    protected void addSWRLVariable(IRI iri) {
        swrlVariables.add(iri);
    }


    protected boolean isSWRLVariable(IRI iri) {
        return swrlVariables.contains(iri);
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


    public void handle(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
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


    public void handle(IRI subject, IRI predicate, OWLLiteral object) {
        for (AbstractLiteralTripleHandler handler : literalTripleHandlers) {
            if (handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
                break;
            }
        }
    }


    private static void printTriple(Object subject, Object predicate, Object object, PrintWriter w) {
        w.append(subject.toString());
        w.append(" -> ");
        w.append(predicate.toString());
        w.append(" -> ");
        w.append(object.toString());
        w.append("\n");
    }


    private void dumpRemainingTriples() {
        if (!logger.isLoggable(Level.FINE)) {
            return;
        }
        StringWriter sw = new StringWriter();
        PrintWriter w = new PrintWriter(sw);

        for (IRI predicate : singleValuedResTriplesByPredicate.keySet()) {
            Map<IRI, IRI> map = singleValuedResTriplesByPredicate.get(predicate);
            for (IRI subject : map.keySet()) {
                IRI object = map.get(subject);
                printTriple(subject, predicate, object, w);
            }
        }

        for (IRI predicate : singleValuedLitTriplesByPredicate.keySet()) {
            Map<IRI, OWLLiteral> map = singleValuedLitTriplesByPredicate.get(predicate);
            for (IRI subject : map.keySet()) {
                OWLLiteral object = map.get(subject);
                printTriple(subject, predicate, object, w);
            }
        }

        for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
            Map<IRI, Set<IRI>> map = resTriplesBySubject.get(subject);
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Set<IRI> objects = map.get(predicate);
                for (IRI object : objects) {
                    printTriple(subject, predicate, object, w);
                }
            }
        }
        for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
            Map<IRI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Set<OWLLiteral> objects = map.get(predicate);
                for (OWLLiteral object : objects) {
                    printTriple(subject, predicate, object, w);
                }
            }
        }
        w.flush();
        logger.fine(sw.getBuffer().toString());
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
//        try {
            IRIMap.clear();

            tripleProcessor.fine("Total number of triples: " + count);
            RDFXMLOntologyFormat format = getOntologyFormat();

            // First mop up any rules triples
            SWRLRuleTranslator translator = new SWRLRuleTranslator(this);
            for (IRI ruleIRI : swrlRules) {
                translator.translateRule(ruleIRI);
            }


            // We need to mop up all remaining triples.  These triples will be in the
            // triples by subject map.  Other triples which reside in the triples by
            // predicate (single valued) triple aren't "root" triples for axioms.  First
            // we translate all system triples and then go for triples whose predicates
            // are not system/reserved vocabulary IRIs to translate these into ABox assertions
            // or annotationIRIs
            for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
                Map<IRI, Set<IRI>> map = resTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Set<IRI> objects = map.get(predicate);
                    if (objects == null) {
                        continue;
                    }
                    for (IRI object : new ArrayList<IRI>(objects)) {
                        // We don't handle x rdf:type owl:Axiom because these must be handled after everything else
                        // so that the "base triples" that represent the axiom with out the annotations get mopped up first
                        if (!(predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI()) && (object.equals(OWLRDFVocabulary.OWL_AXIOM.getIRI()) || object.equals(OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES.getIRI())))) {
                            handle(subject, predicate, object);
                        }
                    }
                }
            }

            // Now handle axiom annotations
            // TODO: TIDY UP THIS COPY AND PASTE HACK!
            for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
                Map<IRI, Set<IRI>> map = resTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Set<IRI> objects = map.get(predicate);
                    if (objects == null) {
                        continue;
                    }
                    for (IRI object : new ArrayList<IRI>(objects)) {
                        if ((predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI()) && (object.equals(OWLRDFVocabulary.OWL_AXIOM.getIRI()) || object.equals(OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES.getIRI())))) {
                            handle(subject, predicate, object);
                        }
                    }
                }
            }

            // TODO: TIDY UP!  This is a copy and paste hack!!
            // Now for the ABox assertions and annotationIRIs
            for (IRI subject : new ArrayList<IRI>(resTriplesBySubject.keySet())) {
                Map<IRI, Set<IRI>> map = resTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Set<IRI> objects = map.get(predicate);
                    if (objects == null) {
                        continue;
                    }
                    for (IRI object : new ArrayList<IRI>(objects)) {
                        for (AbstractResourceTripleHandler resTripHandler : resourceTripleHandlers) {
                            if (resTripHandler.canHandle(subject, predicate, object)) {
                                resTripHandler.handleTriple(subject, predicate, object);
                                break;
                            }
                        }
                    }
                }
            }


            for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
                Map<IRI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
                if (map == null) {
                    continue;
                }
                for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                    Set<OWLLiteral> objects = map.get(predicate);
                    for (OWLLiteral object : new ArrayList<OWLLiteral>(objects)) {
                        handle(subject, predicate, object);
                    }
                }
            }

//        translateDanglingEntities();

            if (format != null) {
                RDFOntologyFormat.ParserMetaData metaData = new RDFOntologyFormat.ParserMetaData(count, RDFOntologyFormat.OntologyHeaderStatus.PARSED_ONE_HEADER);
                format.setParserMetaData(metaData);
            }


            // Do we need to change the ontology IRI?
            IRI ontologyIRIToSet = chooseOntologyIRI();
            if (ontologyIRIToSet != null) {
                IRI versionIRI = ontology.getOntologyID().getVersionIRI();
                applyChange(new SetOntologyID(ontology, new OWLOntologyID(ontologyIRIToSet, versionIRI)));
            }

            if (tripleProcessor.isLoggable(Level.FINE)) {
                tripleProcessor.fine("Loaded " + ontology.getOntologyID());
            }


            dumpRemainingTriples();
            cleanup();
        }
        catch (UnloadableImportException e) {
            throw new TranslatedUnloadedImportException(e);
        }
    }

    /**
     * Selects an IRI to be the ontology IRI
     *
     * @return An IRI that should be used as the IRI of the parsed ontology, or <code>null</code>
     *         if the parsed ontology does not have an IRI
     */
    private IRI chooseOntologyIRI() {
        IRI ontologyIRIToSet = null;
        if (ontologyIRIs.isEmpty()) {
            // No ontology IRIs
            // We used to use the xml:base here.  But this is probably incorrect for OWL 2 now.
        }
        else if (ontologyIRIs.size() == 1) {
            // Exactly one ontologyIRI
            IRI ontologyIRI = ontologyIRIs.iterator().next();
            if (!isAnonymousNode(ontologyIRI)) {
                ontologyIRIToSet = ontologyIRI;
            }
        }
        else {
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
            }
            else if (!candidateIRIs.isEmpty()) {
                // Just pick any
                ontologyIRIToSet = candidateIRIs.iterator().next();
            }

        }
        return ontologyIRIToSet;
    }


    private void cleanup() {
        owlClassIRIs.clear();
        objectPropertyIRIs.clear();
        dataPropertyIRIs.clear();
        dataRangeIRIs.clear();
        restrictionIRIs.clear();
        listFirstLiteralTripleMap.clear();
        listFirstResourceTripleMap.clear();
        listRestTripleMap.clear();
        translatedClassExpression.clear();
        listIRIs.clear();
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


    public IRI checkForSynonym(IRI original) {
        if (!strict) {
            IRI synonymIRI = synonymMap.get(original);
            if (synonymIRI != null) {
                return synonymIRI;
            }
        }
        return original;
    }

    public void statementWithLiteralValue(String subject, String predicate, String object, String lang, String datatype) throws SAXException {
        incrementTripleCount();
        IRI subjectIRI = getIRI(subject);
        IRI predicateIRI = getIRI(predicate);
        predicateIRI = checkForSynonym(predicateIRI);
        handleStreaming(subjectIRI, predicateIRI, object, datatype, lang);
    }


    public void statementWithResourceValue(String subject, String predicate, String object) throws SAXException {
        try {
            incrementTripleCount();
            IRI subjectIRI = getIRI(subject);
            IRI predicateIRI = getIRI(predicate);
            predicateIRI = checkForSynonym(predicateIRI);
            IRI objectIRI = checkForSynonym(getIRI(object));
            handleStreaming(subjectIRI, predicateIRI, objectIRI);
        }
        catch (UnloadableImportException e) {
            throw new TranslatedUnloadedImportException(e);
        }
    }


    private int addCount = 0;


    /**
     * Called when a resource triple has been parsed.
     *
     * @param subject The subject of the triple that has been parsed
     * @param predicate The predicate of the triple that has been parsed
     * @param object The object of the triple that has been parsed
     */
    private void handleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (predicate.equals(RDF_TYPE.getIRI())) {
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
//            if(!predicate.equals(OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI())) {
//                addCount++;
//            }

//        }
        // Not consumed, so add the triple
        addTriple(subject, predicate, object);
    }

    private void handleStreaming(IRI subject, IRI predicate, String literal, String datatype, String lang) {
        // Convert all literals to OWLConstants
        OWLLiteral con = getOWLConstant(literal, datatype, lang);
        AbstractLiteralTripleHandler skosHandler = skosTripleHandlers.get(predicate);
        if (skosHandler != null) {
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
     *
     * @param literal The literal - must NOT be <code>null</code>
     * @param datatype The data type - may be <code>null</code>
     * @param lang The lang - may be <code>null</code>
     * @return The <code>OWLConstant</code> (either typed or untyped depending on the params)
     */
    private OWLLiteral getOWLConstant(String literal, String datatype, String lang) {
        if (datatype != null) {
            return dataFactory.getOWLTypedLiteral(literal, dataFactory.getOWLDatatype(getIRI(datatype)));
        }
        else {
            return dataFactory.getOWLStringLiteral(literal, lang);
        }
    }


    public OWLDataRange translateDataRange(IRI iri) {
        IRI oneOfObject = getResourceObject(iri, OWL_ONE_OF.getIRI(), true);
        if (oneOfObject != null) {
            Set<OWLLiteral> literals = translateToConstantSet(oneOfObject);
            Set<OWLTypedLiteral> typedConstants = new HashSet<OWLTypedLiteral>(literals.size());
            for (OWLLiteral con : literals) {
                if (con.isOWLTypedLiteral()) {
                    typedConstants.add((OWLTypedLiteral) con);
                }
                else {
                    typedConstants.add(getDataFactory().getOWLTypedLiteral(con.getLiteral(), getDataFactory().getOWLDatatype(XSDVocabulary.STRING.getIRI())));
                }
            }
            return getDataFactory().getOWLDataOneOf(typedConstants);
        }
        IRI intersectionOfObject = getResourceObject(iri, OWL_INTERSECTION_OF.getIRI(), true);
        if (intersectionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(intersectionOfObject);
            return getDataFactory().getOWLDataIntersectionOf(dataRanges);
        }
        IRI unionOfObject = getResourceObject(iri, OWL_UNION_OF.getIRI(), true);
        if (unionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(unionOfObject);
            return getDataFactory().getOWLDataUnionOf(dataRanges);
        }
        // The plain complement of triple predicate is in here for legacy reasons
        IRI complementOfObject = getResourceObject(iri, OWL_DATATYPE_COMPLEMENT_OF.getIRI(), true);
        if (!strict && complementOfObject == null) {
            complementOfObject = getResourceObject(iri, OWL_COMPLEMENT_OF.getIRI(), true);
        }
        if (complementOfObject != null) {
            OWLDataRange operand = translateDataRange(complementOfObject);
            return getDataFactory().getOWLDataComplementOf(operand);
        }

        IRI onDatatypeObject = getResourceObject(iri, OWL_ON_DATA_TYPE.getIRI(), true);
        if (onDatatypeObject != null) {
            OWLDatatype restrictedDataRange = (OWLDatatype) translateDataRange(onDatatypeObject);

            // Consume the datatype type triple
            getResourceObject(iri, RDF_TYPE.getIRI(), true);
            // Now we have to get the restricted facets - there is some legacy translation code here... the current
            // spec uses a list of triples where the predicate is a facet and the object a literal that is restricted
            // by the facet.  Originally, there just used to be multiple facet-"facet value" triples

            Set<OWLFacetRestriction> restrictions = new HashSet<OWLFacetRestriction>();

            IRI facetRestrictionList = getResourceObject(iri, OWL_WITH_RESTRICTIONS.getIRI(), true);
            if (facetRestrictionList != null) {
                restrictions = translateToFacetRestrictionSet(facetRestrictionList);
            }
            else if (!strict) {
                // Try the legacy encoding
                for (IRI facetIRI : OWLFacet.FACET_IRIS) {
                    OWLLiteral val;
                    while ((val = getLiteralObject(iri, facetIRI, true)) != null) {
                        restrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.getFacet(facetIRI), val));
                    }
                }
            }


            return dataFactory.getOWLDatatypeRestriction(restrictedDataRange, restrictions);
        }
        return getDataFactory().getOWLDatatype(iri);
    }


    public OWLDataPropertyExpression translateDataPropertyExpression(IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Basic node translation - translation of entities
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<IRI, OWLObjectPropertyExpression> translatedProperties = new HashMap<IRI, OWLObjectPropertyExpression>();

    public OWLObjectPropertyExpression translateObjectPropertyExpression(IRI mainNode) {
        OWLObjectPropertyExpression prop = translatedProperties.get(mainNode);
        if (prop != null) {
            return prop;
        }
//        addOWLObjectProperty(mainNode);
        if (!isAnonymousNode(mainNode)) {
            // Simple object property
            prop = getDataFactory().getOWLObjectProperty(mainNode);
            translatedProperties.put(mainNode, prop);
            return prop;
        }
        else {
            // Inverse of a property expression

            IRI inverseOfObject = getResourceObject(mainNode, OWL_INVERSE_OF.getIRI(), true);
            if (inverseOfObject != null) {
                OWLObjectPropertyExpression otherProperty = translateObjectPropertyExpression(inverseOfObject);
                prop = getDataFactory().getOWLObjectInverseOf(otherProperty);
            }
            else {
                prop = getDataFactory().getOWLObjectInverseOf(getDataFactory().getOWLObjectProperty(mainNode));
            }
            translatedProperties.put(mainNode, prop);
            return prop;
        }
    }


    public OWLIndividual translateIndividual(IRI node) {
        return getOWLIndividual(node);
    }

    /**
     * Translates the annotation on a main node.  Triples whose subject is the specified main node and whose subject
     * is typed an an annotation property (or is a built in annotation property) will be translated to annotation on
     * this main node.
     *
     * @param mainNode The main node
     * @return The set of annotations on the main node
     */
    public Set<OWLAnnotation> translateAnnotations(IRI mainNode) {
        // Are we the subject of an annotation?  If so, we need to ensure that the annotations annotate us.  This
        // will only happen if we are an annotation!
        Set<OWLAnnotation> annosOnMainNodeAnnotations = new HashSet<OWLAnnotation>();
        Set<IRI> annotationMainNodes = getAnnotatedSourceAnnotationMainNodes(mainNode);
        if (!annotationMainNodes.isEmpty()) {
            for (IRI annotationMainNode : annotationMainNodes) {
                annosOnMainNodeAnnotations.addAll(translateAnnotations(annotationMainNode));
            }
        }

        Set<OWLAnnotation> mainNodeAnnotations = new HashSet<OWLAnnotation>();
        Set<IRI> predicates = getPredicatesBySubject(mainNode);
        for (IRI predicate : predicates) {
            if (isAnnotationProperty(predicate)) {
                IRI resVal = getResourceObject(mainNode, predicate, true);
                while (resVal != null) {
                    OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(predicate);
                    OWLAnnotationValue val;
                    if (isAnonymousNode(resVal)) {
                        val = getDataFactory().getOWLAnonymousIndividual(resVal.toString());
                    }
                    else {
                        val = resVal;
                    }
                    mainNodeAnnotations.add(getDataFactory().getOWLAnnotation(prop, val, annosOnMainNodeAnnotations));
                    resVal = getResourceObject(mainNode, predicate, true);
                }
                OWLLiteral litVal = getLiteralObject(mainNode, predicate, true);
                while (litVal != null) {
                    OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(predicate);
                    mainNodeAnnotations.add(getDataFactory().getOWLAnnotation(prop, litVal, annosOnMainNodeAnnotations));
                    litVal = getLiteralObject(mainNode, predicate, true);
                }
            }
        }
        return mainNodeAnnotations;
    }

    private Map<IRI, OWLClassExpression> translatedClassExpression = new HashMap<IRI, OWLClassExpression>();


    public OWLClassExpression translateClassExpression(IRI mainNode) {
        if (!isAnonymousNode(mainNode)) {
            return getDataFactory().getOWLClass(mainNode);
        }
        OWLClassExpression desc = translatedClassExpression.get(mainNode);
        if (desc == null) {
            ClassExpressionTranslator translator = classExpressionTranslatorSelector.getClassExpressionTranslator(mainNode);
            if (translator != null) {
                desc = translator.translate(mainNode);
                translatedClassExpression.put(mainNode, desc);
                restrictionIRIs.remove(mainNode);
            }
            else {
                return getDataFactory().getOWLClass(mainNode);
            }
        }
        return desc;
    }


    public OWLClassExpression getClassExpressionIfTranslated(IRI mainNode) {
        return translatedClassExpression.get(mainNode);
    }


    public List<OWLObjectPropertyExpression> translateToObjectPropertyList(IRI mainNode) {
        return objectPropertyListTranslator.translateList(mainNode);
    }

    public List<OWLDataPropertyExpression> translateToDataPropertyList(IRI mainNode) {
        return dataPropertyListTranslator.translateList(mainNode);
    }

    public Set<OWLClassExpression> translateToClassExpressionSet(IRI mainNode) {
        return classExpressionListTranslator.translateToSet(mainNode);
    }


    public Set<OWLLiteral> translateToConstantSet(IRI mainNode) {
        return constantListTranslator.translateToSet(mainNode);
    }


    public Set<OWLIndividual> translateToIndividualSet(IRI mainNode) {
        return individualListTranslator.translateToSet(mainNode);
    }

    public Set<OWLDataRange> translateToDataRangeSet(IRI mainNode) {
        return dataRangeListTranslator.translateToSet(mainNode);
    }

    public Set<OWLFacetRestriction> translateToFacetRestrictionSet(IRI mainNode) {
        return faceRestrictionListTranslator.translateToSet(mainNode);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Set<IRI> getPredicatesBySubject(IRI subject) {
        Set<IRI> IRIs = new HashSet<IRI>();
        Map<IRI, Set<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            IRIs.addAll(predObjMap.keySet());
        }
        Map<IRI, Set<OWLLiteral>> predObjMapLit = litTriplesBySubject.get(subject);
        if (predObjMapLit != null) {
            IRIs.addAll(predObjMapLit.keySet());
        }
        return IRIs;
    }

    public IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<IRI, Set<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<IRI> objects = predObjMap.get(predicate);
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


    public OWLLiteral getLiteralObject(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj;
        }
        Map<IRI, Set<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
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


    public boolean isTriplePresent(IRI subject, IRI predicate, IRI object, boolean consume) {
        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<IRI, Set<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<IRI> objects = predObjMap.get(predicate);
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


    public boolean isTriplePresent(IRI subject, IRI predicate, OWLLiteral object, boolean consume) {
        Map<IRI, OWLLiteral> subjPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            OWLLiteral obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
            return obj != null;
        }
        Map<IRI, Set<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
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


    public boolean hasPredicate(IRI subject, IRI predicate) {
        Map<IRI, IRI> resPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (resPredMap != null) {
            return resPredMap.containsKey(subject);
        }
        Map<IRI, OWLLiteral> litPredMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (litPredMap != null) {
            return litPredMap.containsKey(subject);
        }
        Map<IRI, Set<IRI>> resPredObjMap = resTriplesBySubject.get(subject);
        if (resPredObjMap != null) {
            boolean b = resPredObjMap.containsKey(predicate);
            if (b) {
                return true;
            }
        }
        Map<IRI, Set<OWLLiteral>> litPredObjMap = litTriplesBySubject.get(subject);
        if (litPredObjMap != null) {
            return litPredObjMap.containsKey(predicate);
        }
        return false;
    }


    public boolean hasPredicateObject(IRI subject, IRI predicate, IRI object) {
        Map<IRI, IRI> predMap = singleValuedResTriplesByPredicate.get(predicate);
        if (predMap != null) {
            IRI objectIRI = predMap.get(subject);
            if (objectIRI == null) {
                return false;
            }
            return objectIRI.equals(object);
        }
        Map<IRI, Set<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            Set<IRI> objects = predObjMap.get(predicate);
            if (objects != null) {
                return objects.contains(object);
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    public void addList(IRI iri) {
        listIRIs.add(iri);
    }


    public boolean isList(IRI iri, boolean consume) {
        if (consume) {
            return listIRIs.remove(iri);
        }
        else {
            return listIRIs.contains(iri);
        }
    }


    public void addRest(IRI subject, IRI object) {
        listRestTripleMap.put(subject, object);
    }


    public void addFirst(IRI subject, IRI object) {
        listFirstResourceTripleMap.put(subject, object);
    }


    public IRI getFirstResource(IRI subject, boolean consume) {
        if (consume) {
            return listFirstResourceTripleMap.remove(subject);
        }
        else {
            return listFirstResourceTripleMap.get(subject);
        }
    }


    public OWLLiteral getFirstLiteral(IRI subject) {
        return listFirstLiteralTripleMap.get(subject);
    }


    public IRI getRest(IRI subject, boolean consume) {
        if (consume) {
            return listRestTripleMap.remove(subject);
        }
        else {
            return listRestTripleMap.get(subject);
        }
    }


    public void addFirst(IRI subject, OWLLiteral object) {
        listFirstLiteralTripleMap.put(subject, object);
    }


    public void addOntology(IRI iri) {
        if (ontologyIRIs.isEmpty()) {
            firstOntologyIRI = iri;
        }
        ontologyIRIs.add(iri);
    }

    public Set<IRI> getOntologies() {
        return ontologyIRIs;
    }

    public void addReifiedAxiom(IRI axiomIRI, OWLAxiom axiom) {
        reifiedAxiomsMap.put(axiomIRI, axiom);
    }


    public boolean isAxiom(IRI iri) {
        return reifiedAxiomsMap.containsKey(iri);
    }


    public OWLAxiom getAxiom(IRI iri) {
        return reifiedAxiomsMap.get(iri);
    }


    public boolean isDataRange(IRI iri) {
        return dataRangeIRIs.contains(iri);
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

    private Map<IRI, Map<IRI, Set<IRI>>> resTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<IRI, Map<IRI, IRI>> singleValuedResTriplesByPredicate = CollectionFactory.createMap();

    // Literal triples
    private Map<IRI, Map<IRI, Set<OWLLiteral>>> litTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<IRI, Map<IRI, OWLLiteral>> singleValuedLitTriplesByPredicate = CollectionFactory.createMap();


    public void addTriple(IRI subject, IRI predicate, IRI object) {
        Map<IRI, IRI> subjObjMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, object);
        }
        else {
            Map<IRI, Set<IRI>> map = resTriplesBySubject.get(subject);
            if (map == null) {
                map = CollectionFactory.createMap();
                resTriplesBySubject.put(subject, map);
            }
            Set<IRI> objects = map.get(predicate);
            if (objects == null) {
                objects = new FakeSet();
                map.put(predicate, objects);
            }
            objects.add(object);
        }
    }


    public void addTriple(IRI subject, IRI predicate, OWLLiteral con) {
        Map<IRI, OWLLiteral> subjObjMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, con);
        }
        else {
            Map<IRI, Set<OWLLiteral>> map = litTriplesBySubject.get(subject);
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
        this.xmlBase = IRI.create(base);
    }


    private static class FakeSet<O> extends ArrayList<O> implements Set<O> {

        public FakeSet() {
        }


        public FakeSet(Collection<? extends O> c) {
            super(c);
        }
    }
}
