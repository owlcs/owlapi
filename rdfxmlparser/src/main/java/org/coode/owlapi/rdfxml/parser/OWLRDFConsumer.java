package org.coode.owlapi.rdfxml.parser;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFOntologyHeaderStatus;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AddAxiom;
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
import org.xml.sax.SAXException;


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


    private OWLOntologyLoaderConfiguration configuration;

    //private Graph graph;

    private OWLOntologyManager owlOntologyManager;


    // A call back interface, which is used to check whether a node
    // is anonymous or not.
    private AnonymousNodeChecker anonymousNodeChecker;

    // The set of IRIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owl:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    private Set<IRI> classExpressionIRIs;

    // Same as classExpressionIRIs but for object properties
    private Set<IRI> objectPropertyExpressionIRIs;

    // Same as classExpressionIRIs but for data properties
    private Set<IRI> dataPropertyExpressionIRIs;

    // Same as classExpressionIRIs but for rdf properties
    // things neither typed as a data or object property - bad!
    private Set<IRI> propertyIRIs;


    // Set of IRIs that are typed by non-system types and
    // also owl:Thing
    private Set<IRI> individualIRIs;


    // Same as classExpressionIRIs but for annotation properties
    private Set<IRI> annotationPropertyIRIs;

    private Set<IRI> annotationIRIs;

    private Map<IRI, OWLAnnotation> annotationIRI2Annotation;


    // IRIs that had a type triple to rdfs:Datatange
    private Set<IRI> dataRangeIRIs;

    // The IRI of the first reource that is typed as an ontology
    private IRI firstOntologyIRI;

    // IRIs that had a type triple to owl:Ontology
    private Set<IRI> ontologyIRIs;

    // IRIs that had a type triple to owl:Restriction
    private Set<IRI> restrictionIRIs;

//    IRIs that had a type triple to rdf:List
//    private Set<IRI> listIRIs;

    // Maps rdf:next triple subjects to objects
    private Map<IRI, IRI> listRestTripleMap;

    private Map<IRI, IRI> listFirstResourceTripleMap;

    private Map<IRI, OWLLiteral> listFirstLiteralTripleMap;

//    private Map<IRI, OWLAxiom> reifiedAxiomsMap;

    private Set<IRI> axioms = new HashSet<IRI>();

    private Map<IRI, Set<OWLAnnotation>> annotationsBySubject;

    private Map<IRI, Object> sharedAnonymousNodes = new HashMap<IRI, Object>();

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

    // Handler for triples that denote nodes which represent axioms.
    // i.e.
    // owl:AllDisjointClasses
    // owl:AllDisjointProperties
    // owl:AllDifferent
    // owl:NegativePropertyAssertion
    // owl:Axiom
    // These need to be handled separately from other types, because the base triples for annotated
    // axioms should be in the ontology before annotations on the annotated versions of these axioms are parsed.
    private Map<IRI, BuiltInTypeHandler> axiomTypeTripleHandlers = new HashMap<IRI, BuiltInTypeHandler>();

    // Handlers for build in predicates
    private Map<IRI, TriplePredicateHandler> predicateHandlers;


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


    private List<AbstractObjectRestrictionTranslator> objectRestrictionTranslators = new ArrayList<AbstractObjectRestrictionTranslator>();

    private List<AbstractDataRestrictionTranslator> dataRestrictionTranslators = new ArrayList<AbstractDataRestrictionTranslator>();

    private List<ClassExpressionTranslator> nonRestrictionTranslators = new ArrayList<ClassExpressionTranslator>();


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

    private TPInverseOfHandler inverseOfHandler;
    private TPTypeHandler nonBuiltInTypeHandler;

//    private GTPObjectPropertyAssertionHandler objectPropertyAssertionHandler;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public OWLRDFConsumer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, AnonymousNodeChecker checker, OWLOntologyLoaderConfiguration configuration) {
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        this.dataFactory = owlOntologyManager.getOWLDataFactory();
        this.anonymousNodeChecker = checker;
        this.configuration = configuration;

        objectRestrictionTranslators.add(new ObjectSomeValuesFromTranslator(this));
        objectRestrictionTranslators.add(new ObjectAllValuesFromTranslator(this));
        objectRestrictionTranslators.add(new ObjectHasValueTranslator(this));
        objectRestrictionTranslators.add(new ObjectHasSelfTranslator(this));
        objectRestrictionTranslators.add(new ObjectMinQualifiedCardinalityTranslator(this));
        objectRestrictionTranslators.add(new ObjectMaxQualifiedCardinalityTranslator(this));
        objectRestrictionTranslators.add(new ObjectQualifiedCardinalityTranslator(this));
        objectRestrictionTranslators.add(new ObjectMinCardinalityTranslator(this));
        objectRestrictionTranslators.add(new ObjectMaxCardinalityTranslator(this));
        objectRestrictionTranslators.add(new ObjectCardinalityTranslator(this));


        dataRestrictionTranslators.add(new DataSomeValuesFromTranslator(this));
        dataRestrictionTranslators.add(new DataAllValuesFromTranslator(this));
        dataRestrictionTranslators.add(new DataHasValueTranslator(this));
        dataRestrictionTranslators.add(new DataMinQualifiedCardinalityTranslator(this));
        dataRestrictionTranslators.add(new DataMaxQualifiedCardinalityTranslator(this));
        dataRestrictionTranslators.add(new DataQualifiedCardinalityTranslator(this));
        dataRestrictionTranslators.add(new DataMinCardinalityTranslator(this));
        dataRestrictionTranslators.add(new DataMaxCardinalityTranslator(this));
        dataRestrictionTranslators.add(new DataCardinalityTranslator(this));

        nonRestrictionTranslators.add(new NamedClassTranslator(this));
        nonRestrictionTranslators.add(new ObjectIntersectionOfTranslator(this));
        nonRestrictionTranslators.add(new ObjectUnionOfTranslator(this));
        nonRestrictionTranslators.add(new ObjectComplementOfTranslator(this));
        nonRestrictionTranslators.add(new ObjectOneOfTranslator(this));


        classExpressionIRIs = CollectionFactory.createSet();
        objectPropertyExpressionIRIs = CollectionFactory.createSet();
        dataPropertyExpressionIRIs = CollectionFactory.createSet();
        individualIRIs = CollectionFactory.createSet();
        annotationPropertyIRIs = CollectionFactory.createSet();
        for (IRI iri : OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS) {
            annotationPropertyIRIs.add(iri);
        }
        annotationIRIs = new HashSet<IRI>();
        annotationIRI2Annotation = new HashMap<IRI, OWLAnnotation>();
        annotationsBySubject = new HashMap<IRI, Set<OWLAnnotation>>();

        dataRangeIRIs = CollectionFactory.createSet();
        propertyIRIs = CollectionFactory.createSet();
        restrictionIRIs = CollectionFactory.createSet();
        ontologyIRIs = CollectionFactory.createSet();
        listFirstLiteralTripleMap = CollectionFactory.createMap();
        listFirstResourceTripleMap = CollectionFactory.createMap();
        listRestTripleMap = CollectionFactory.createMap();
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
        literalTripleHandlers.add(new GTPDataPropertyAssertionHandler(this));
        literalTripleHandlers.add(new TPFirstLiteralHandler(this));
        literalTripleHandlers.add(new GTPAnnotationLiteralHandler(this));


        // General resource/object triples - i.e. triples which have a predicate
        // that is not a built in IRI.  Annotation properties get precedence
        // over object properties, so that if we have the statement a:A a:foo a:B and a:foo
        // is typed as both an annotation and data property then the statement will be
        // translated as an annotation on a:A
        resourceTripleHandlers = new ArrayList<AbstractResourceTripleHandler>();
        resourceTripleHandlers.add(new GTPObjectPropertyAssertionHandler(this));
        resourceTripleHandlers.add(new GTPAnnotationResourceTripleHandler(this));


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
    }

    public void setIRIProvider(IRIProvider iriProvider) {
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
        // We can load legacy ontologies by providing synonyms for built in vocabulary
        // where the vocabulary has simply changed (e.g. DAML+OIL -> OWL)

        synonymMap = CollectionFactory.createMap();
        // Legacy protege-owlapi representation of QCRs
        synonymMap.put(IRI.create(Namespaces.OWL + "valuesFrom"), OWL_ON_CLASS.getIRI());


        if (!configuration.isStrict()) {
            addDAMLOILVocabulary();
            addIntermediateOWLSpecVocabulary();
        }
    }

    private void addDAMLOILVocabulary() {
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
    }


    /**
     * There may be some ontologies floating about that use early versions
     * of the OWL 1.1 vocabulary.  We can map early versions of the vocabulary
     * to the current OWL 1.1 vocabulary.
     */
    @SuppressWarnings("deprecation")
    private void addIntermediateOWLSpecVocabulary() {
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

        // Intermediate OWL 2 spec
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());

        // Preliminary OWL 1.1 Vocab
        synonymMap.put(IRI.create(Namespaces.OWL + "cardinalityType"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL + "dataComplementOf"), OWL_COMPLEMENT_OF.getIRI());

        synonymMap.put(OWL_ANTI_SYMMETRIC_PROPERTY.getIRI(), OWL_ASYMMETRIC_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_DATA_PROPERTY.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_OBJECT_PROPERTY.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());

        synonymMap.put(OWL_SUB_DATA_PROPERTY_OF.getIRI(), RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(OWL_SUB_OBJECT_PROPERTY_OF.getIRI(), RDFS_SUB_PROPERTY_OF.getIRI());

        synonymMap.put(OWL_OBJECT_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_OBJECT_PROPERTY_DOMAIN.getIRI(), RDFS_DOMAIN.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_DOMAIN.getIRI(), RDFS_DOMAIN.getIRI());

        synonymMap.put(OWL_DISJOINT_DATA_PROPERTIES.getIRI(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(OWL_DISJOINT_OBJECT_PROPERTIES.getIRI(), OWL_PROPERTY_DISJOINT_WITH.getIRI());

        synonymMap.put(OWL_EQUIVALENT_DATA_PROPERTIES.getIRI(), OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(OWL_EQUIVALENT_OBJECT_PROPERTIES.getIRI(), OWL_EQUIVALENT_PROPERTY.getIRI());


        synonymMap.put(OWL_OBJECT_RESTRICTION.getIRI(), OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RESTRICTION.getIRI(), OWL_RESTRICTION.getIRI());

        synonymMap.put(OWL_DATA_RANGE.getIRI(), OWL_DATATYPE.getIRI());

        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_SOURCE_INDIVIDUAL.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ASSERTION_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_TARGET_INDIVIDUAL.getIRI());

    }


    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(IRI.create(Namespaces.OWL2.toString() + v.getShortName()), v.getIRI());
        synonymMap.put(IRI.create(Namespaces.OWL11.toString() + v.getShortName()), v.getIRI());
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

    private void addAxiomTypeTripleHandler(BuiltInTypeHandler handler) {
        axiomTypeTripleHandlers.put(handler.getTypeIRI(), handler);
    }

    private void setupTypeTripleHandlers() {
        setupBasicTypeHandlers();
        setupAxiomTypeHandlers();
    }

    private void setupBasicTypeHandlers() {
        addBuiltInTypeTripleHandler(new TypeOntologyPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeAsymmetricPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeClassHandler(this));
        addBuiltInTypeTripleHandler(new TypeObjectPropertyHandler(this));

        addBuiltInTypeTripleHandler(new TypeDataPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeDatatypeHandler(this));
        addBuiltInTypeTripleHandler(new TypeFunctionalPropertyHandler(this));
        addBuiltInTypeTripleHandler(new TypeInverseFunctionalPropertyHandler(this));
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
        addBuiltInTypeTripleHandler(new TypeNegativeDataPropertyAssertionHandler(this));
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
            addBuiltInTypeTripleHandler(new TypeSWRLDataValuedPropertyAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLDifferentIndividualsAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLImpHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLIndividualPropertyAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLSameIndividualAtomHandler(this));
            addBuiltInTypeTripleHandler(new TypeSWRLVariableHandler(this));
        }


    }

    private void setupAxiomTypeHandlers() {
        addAxiomTypeTripleHandler(new TypeAxiomHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDifferentHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDisjointClassesHandler(this));
        addAxiomTypeTripleHandler(new TypeAllDisjointPropertiesHandler(this));
       // addAxiomTypeTripleHandler(new TypeAllDifferentHandler(this));
        addAxiomTypeTripleHandler(new TypeNegativePropertyAssertionHandler(this));
    }


    private void addPredicateHandler(TriplePredicateHandler predicateHandler) {
        predicateHandlers.put(predicateHandler.getPredicateIRI(), predicateHandler);
    }


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
      //  addPredicateHandler(new TPDistinctMembersHandlerAlternateForm(this));
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


    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }


    // We cache IRIs to save memory!!
    private Map<String, IRI> IRIMap = CollectionFactory.createMap();

    int currentBaseCount = 0;

    /**
     * Gets any annotations that were translated since the last call of this method (calling
     * this method clears the current pending annotations)
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
        if (!pendingAnnotations.isEmpty())
            throw new OWLRuntimeException(pendingAnnotations.size() + " pending annotations should have been used by now.");
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
        // NOTE:  This method only gets called when the ontology being parsed adds a direct import.  This is enough
        // for resolving the imports closure.
        // We cache IRIs of various entities here.
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(ontology)) {
            for (OWLAnnotationProperty prop : ont.getAnnotationPropertiesInSignature()) {
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
     * Checks whether a node is anonymous.
     * @param iri The IRI of the node to be checked.
     * @return <code>true</code> if the node is anonymous, or
     *         <code>false</code> if the node is not anonymous.
     */
    protected boolean isAnonymousNode(IRI iri) {
        return anonymousNodeChecker.isAnonymousNode(iri);
    }

    protected boolean isSharedAnonymousNode(IRI iri) {
        return anonymousNodeChecker.isAnonymousSharedNode(iri.toString());
    }

    protected void addSharedAnonymousNode(IRI iri, Object translation) {
        sharedAnonymousNodes.put(iri, translation);
    }

    protected Object getSharedAnonymousNode(IRI iri) {
        return sharedAnonymousNodes.get(iri);
    }

    protected void addAxiom(OWLAxiom axiom) {
        boolean add = (!(axiom instanceof OWLAnnotationAxiom) || configuration.isLoadAnnotationAxioms());
        if (add) {
            owlOntologyManager.applyChange(new AddAxiom(ontology, axiom));
        }
        lastAddedAxiom = axiom;
    }

    protected void checkForAndProcessAnnotatedDeclaration(IRI mainNode) throws UnloadableImportException {
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
            handle(annotatedSubjectObject, annotatedPropertyObject, annotatedTargetObject);
        }

    }

    /**
     * Determines if the specified IRI is an IRI corresponding to owl:Class, owl:DatatypeProperty,
     * rdfs:Datatype, owl:ObjectProperty, owl:AnnotationProperty, or owl:NamedIndividual
     * @param iri The IRI to check
     * @return <code>true</code> if the IRI corresponds to a built in OWL entity IRI otherwise
     *         <code>false</code>.
     */
    private boolean isEntityTypeIRI(IRI iri) {
        return iri.equals(OWL_CLASS.getIRI()) || iri.equals(OWL_OBJECT_PROPERTY.getIRI()) || iri.equals(OWL_DATA_PROPERTY.getIRI()) || iri.equals(OWL_ANNOTATION_PROPERTY.getIRI()) || iri.equals(RDFS_DATATYPE.getIRI()) || iri.equals(OWL_NAMED_INDIVIDUAL.getIRI());
    }


    protected void applyChange(OWLOntologyChange change) {
        owlOntologyManager.applyChange(change);
    }

    protected void setOntologyID(OWLOntologyID ontologyID) {
        applyChange(new SetOntologyID(ontology, ontologyID));
    }

    protected void addOntologyAnnotation(OWLAnnotation annotation) {
        applyChange(new AddOntologyAnnotation(ontology, annotation));
    }

    protected void addImport(OWLImportsDeclaration declaration) {
        applyChange(new AddImport(ontology, declaration));
    }

    public OWLAxiom getLastAddedAxiom() {
        return lastAddedAxiom;
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

    public void addClassExpression(IRI iri, boolean explicitlyTyped) {
        addType(iri, classExpressionIRIs, explicitlyTyped);
    }

    public boolean isClassExpression(IRI iri) {
        return classExpressionIRIs.contains(iri);
    }

    public void addObjectProperty(IRI iri, boolean explicitlyTyped) {
        addType(iri, objectPropertyExpressionIRIs, explicitlyTyped);
    }


    public void addDataProperty(IRI iri, boolean explicitlyTyped) {
        addType(iri, dataPropertyExpressionIRIs, explicitlyTyped);
    }

    protected void addAnnotationProperty(IRI iri, boolean explicitlyTyped) {
        addType(iri, annotationPropertyIRIs, explicitlyTyped);
    }


    public void addDataRange(IRI iri, boolean explicitlyTyped) {
        addType(iri, dataRangeIRIs, explicitlyTyped);
    }


    protected void addOWLNamedIndividual(IRI iri, boolean explicitlyType) {
        addType(iri, individualIRIs, explicitlyType);
    }

    protected void addOWLRestriction(IRI iri, boolean explicitlyTyped) {
        addType(iri, restrictionIRIs, explicitlyTyped);
    }

    private void addType(IRI iri, Set<IRI> types, boolean explicitlyTyped) {
        if (configuration.isStrict()) {
            if (explicitlyTyped) {
                types.add(iri);
            }
        }
        else {
            types.add(iri);
        }
    }


    public boolean isRestriction(IRI iri) {
        return restrictionIRIs.contains(iri);
    }

    protected void addAnnotationIRI(IRI iri) {
        annotationIRIs.add(iri);
    }

    protected boolean isAnnotation(IRI iri) {
        return annotationIRIs.contains(iri);
    }


    /**
     * Determines if a given IRI is currently an object property IRI and not a data property IRI and not
     * an annotation property IRI.  Note that this method is only guaranteed to return the same value once
     * all triples in the imports closure of the RDF graph being parsed have been parsed.
     * @param iri The IRI to check.
     * @return <code>true</code> if the IRI is an object property IRI and not a data property IRI and not
     *         an annotation property IRI.  Otherwise, <code>false</code>.
     */
    protected boolean isObjectPropertyOnly(IRI iri) {
        return iri != null && !dataPropertyExpressionIRIs.contains(iri) && !annotationPropertyIRIs.contains(iri) && objectPropertyExpressionIRIs.contains(iri);
    }

    protected boolean isObjectProperty(IRI iri) {
        return objectPropertyExpressionIRIs.contains(iri);
    }


    /**
     * Determines if a given IRI is currently a data property IRI and not an object property IRI and not
     * an annotation property IRI.  Note that this method is only guaranteed to return the same value once
     * all triples in the imports closure of the RDF graph being parsed have been parsed.
     * @param iri The IRI to check.
     * @return <code>true</code> if the IRI is a data property IRI and not an object property IRI and not
     *         an annotation property IRI.  Otherwise, <code>false</code>.
     */
    protected boolean isDataPropertyOnly(IRI iri) {
        return iri != null && !objectPropertyExpressionIRIs.contains(iri) && !annotationPropertyIRIs.contains(iri) && dataPropertyExpressionIRIs.contains(iri);
    }

    protected boolean isDataProperty(IRI iri) {
        return dataPropertyExpressionIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently an annotation property IRI and not a data property IRI and not
     * an object property IRI.  Note that this method is only guaranteed to return the same value once
     * all triples in the imports closure of the RDF graph being parsed have been parsed.
     * @param iri The IRI to check.
     * @return <code>true</code> if the IRI is an annotation property IRI and not a data property IRI and not
     *         an object property IRI.  Otherwise, <code>false</code>.
     */
    protected boolean isAnnotationPropertyOnly(IRI iri) {
        return iri != null && !objectPropertyExpressionIRIs.contains(iri) && !dataPropertyExpressionIRIs.contains(iri) && annotationPropertyIRIs.contains(iri);
    }

    protected boolean isAnnotationProperty(IRI iri) {
        return annotationPropertyIRIs.contains(iri);
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
            return dataFactory.getOWLAnonymousIndividual(iri.toString());
        }
        else {
            return dataFactory.getOWLNamedIndividual(iri);
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

    /**
     * Handles triples in a non-streaming mode.  Type triples whose type is an axiom type, are NOT handled.
     * @param subject The subject of the triple
     * @param predicate The predicate of the triple
     * @param object The object of the triple
     * @throws UnloadableImportException .
     */
    public void handle(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
            BuiltInTypeHandler typeHandler = builtInTypeTripleHandlers.get(object);
            if (typeHandler != null) {
                typeHandler.handleTriple(subject, predicate, object);
            }
            else if (axiomTypeTripleHandlers.get(object) == null) {
                // C(a)
                OWLIndividual ind = translateIndividual(subject);
                OWLClassExpression ce = translateClassExpression(object);
                addAxiom(dataFactory.getOWLClassAssertionAxiom(ce, ind, getPendingAnnotations()));
            }
        }
        else {
            AbstractResourceTripleHandler handler = predicateHandlers.get(predicate);
            if (handler != null && handler.canHandle(subject, predicate, object)) {
                handler.handleTriple(subject, predicate, object);
            }
            else {
                for (AbstractResourceTripleHandler resHandler : resourceTripleHandlers) {
                    if (resHandler.canHandle(subject, predicate, object)) {
                        resHandler.handleTriple(subject, predicate, object);
                        break;
                    }
                }
            }
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
//        if (!logger.isLoggable(Level.FINE)) {
//            return;
//        }
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
            Map<IRI, Collection<IRI>> map = resTriplesBySubject.get(subject);
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Collection<IRI> objects = map.get(predicate);
                for (IRI object : objects) {
                    printTriple(subject, predicate, object, w);
                }
            }
        }
        for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
            for (IRI predicate : new ArrayList<IRI>(map.keySet())) {
                Collection<OWLLiteral> objects = map.get(predicate);
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
    }


    /**
     * This is where we do all remaining parsing
     */
    public void endModel() throws SAXException {
        try {

            // We are now left with triples that could not be consumed during streaming parsing

//            classExpressionIRIs.removeAll(restrictionIRIs);
//            classExpressionIRIs.removeAll(dataRangeIRIs);

            IRIMap.clear();

            tripleProcessor.fine("Total number of triples: " + count);
            RDFXMLOntologyFormat format = rdfxmlOntologyFormat;


            consumeSWRLRules();


            // We need to mop up all remaining triples.  These triples will be in the
            // triples by subject map.  Other triples which reside in the triples by
            // predicate (single valued) triple aren't "root" triples for axioms.  First
            // we translate all system triples and then go for triples whose predicates
            // are not system/reserved vocabulary IRIs to translate these into ABox assertions
            // or annotationIRIs
            iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {
                public void handleResourceTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
                    handle(subject, predicate, object);
                }
            });

            iterateLiteralTriples(new LiteralTripleIterator<UnloadableImportException>() {
                public void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object) throws UnloadableImportException {
                    handle(subject, predicate, object);
                }
            });

            // Inverse property axioms
            inverseOfHandler.setAxiomParsingMode(true);
            iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {
                public void handleResourceTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
                    if (inverseOfHandler.canHandle(subject, predicate, object)) {
                        inverseOfHandler.handleTriple(subject, predicate, object);
                    }
                }
            });


            // Now handle non-reserved predicate triples
            consumeNonReservedPredicateTriples();

            // Now axiom annotations
            consumeAnnotatedAxioms();

            final Set<RDFTriple> remainingTriples = getRemainingTriples();


            if (format != null) {
                RDFParserMetaData metaData = new RDFParserMetaData(RDFOntologyHeaderStatus.PARSED_ONE_HEADER, count, remainingTriples);
                format.setOntologyLoaderMetaData(metaData);
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

    private Set<RDFTriple> getRemainingTriples() {
        final Set<RDFTriple> remainingTriples = new HashSet<RDFTriple>();
        iterateResourceTriples(new ResourceTripleIterator<RuntimeException>() {
            public void handleResourceTriple(IRI subject, IRI predicate, IRI object) {
                remainingTriples.add(new RDFTriple(subject, isAnonymousNode(subject), predicate, isAnonymousNode(predicate), object, isAnonymousNode(object)));
            }
        });

        iterateLiteralTriples(new LiteralTripleIterator<RuntimeException>() {
            public void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object) {
                remainingTriples.add(new RDFTriple(subject, isAnonymousNode(subject), predicate, isAnonymousNode(predicate), object));
            }
        });
        return remainingTriples;
    }

    private void consumeNonReservedPredicateTriples() throws UnloadableImportException {
        iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {
            public void handleResourceTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
                if (isGeneralPredicate(predicate)) {
                    for (AbstractResourceTripleHandler resTripHandler : resourceTripleHandlers) {
                        if (resTripHandler.canHandle(subject, predicate, object)) {
                            resTripHandler.handleTriple(subject, predicate, object);
                            break;
                        }
                    }
                }
            }
        });
        iterateLiteralTriples(new LiteralTripleIterator<UnloadableImportException>() {
            public void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object) throws UnloadableImportException {
                if (isGeneralPredicate(predicate)) {
                    for (AbstractLiteralTripleHandler literalTripleHandler : literalTripleHandlers) {
                        if (literalTripleHandler.canHandle(subject, predicate, object)) {
                            literalTripleHandler.handleTriple(subject, predicate, object);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void consumeSWRLRules() {
        SWRLRuleTranslator translator = new SWRLRuleTranslator(this);
        for (IRI ruleIRI : swrlRules) {
            translator.translateRule(ruleIRI);
        }
    }

    private void consumeAnnotatedAxioms() throws UnloadableImportException {
        iterateResourceTriples(new ResourceTripleIterator<UnloadableImportException>() {
            public void handleResourceTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
                BuiltInTypeHandler builtInTypeHandler = axiomTypeTripleHandlers.get(object);
                if (builtInTypeHandler != null) {
                    if (builtInTypeHandler.canHandle(subject, predicate, object)) {
                        builtInTypeHandler.handleTriple(subject, predicate, object);
                    }
                }
            }
        });
    }

    /**
     * Selects an IRI to be the ontology IRI
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


    public void addModelAttribte(String string, String string1) throws SAXException {
    }


    public void includeModel(String string, String string1) throws SAXException {

    }


    public void logicalURI(String string) throws SAXException {

    }


    public IRI getSynonym(IRI original) {
        if (!configuration.isStrict()) {
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
        predicateIRI = getSynonym(predicateIRI);
        handleStreaming(subjectIRI, predicateIRI, object, datatype, lang);
    }


    public void statementWithResourceValue(String subject, String predicate, String object) throws SAXException {
        try {
            incrementTripleCount();
            IRI subjectIRI = getIRI(subject);
            IRI predicateIRI = getIRI(predicate);
            predicateIRI = getSynonym(predicateIRI);
            IRI objectIRI = getSynonym(getIRI(object));
            handleStreaming(subjectIRI, predicateIRI, objectIRI);
        }
        catch (UnloadableImportException e) {
            throw new TranslatedUnloadedImportException(e);
        }
    }


    private int addCount = 0;


    /**
     * Called when a resource triple has been parsed.
     * @param subject The subject of the triple that has been parsed
     * @param predicate The predicate of the triple that has been parsed
     * @param object The object of the triple that has been parsed
     * @throws org.semanticweb.owlapi.model.UnloadableImportException
     *          .
     */
    private void handleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        boolean consumed = false;
        if (predicate.equals(RDF_TYPE.getIRI())) {
            BuiltInTypeHandler handler = builtInTypeTripleHandlers.get(object);
            if (handler != null) {
                if (handler.canHandleStreaming(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                    consumed = true;
                }
            }
            else if (axiomTypeTripleHandlers.get(object) == null) {
                // Not a built in type
                addOWLNamedIndividual(subject, false);
                if (nonBuiltInTypeHandler.canHandleStreaming(subject, predicate, object)) {
                    nonBuiltInTypeHandler.handleTriple(subject, predicate, object);
                    consumed = true;
                }
            }
            else {
                addAxiom(subject);
            }
        }
        else {
            AbstractResourceTripleHandler handler = predicateHandlers.get(predicate);
            if (handler != null) {
                if (handler.canHandleStreaming(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                    consumed = true;
                }
            }
        }
        if (!consumed) {
            // Not consumed, so add the triple
            addTriple(subject, predicate, object);
        }
    }

    private void handleStreaming(IRI subject, IRI predicate, String literal, String datatype, String lang) {
        // Convert all literals to OWLConstants
        OWLLiteral con = getOWLLiteral(literal, datatype, lang);
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
     * @param literal The literal - must NOT be <code>null</code>
     * @param datatype The data type - may be <code>null</code>
     * @param lang The lang - may be <code>null</code>
     * @return The <code>OWLConstant</code> (either typed or untyped depending on the params)
     */
    private OWLLiteral getOWLLiteral(String literal, String datatype, String lang) {
        if (datatype != null) {
            return dataFactory.getOWLLiteral(literal, dataFactory.getOWLDatatype(getIRI(datatype)));
        }
        else {
            return dataFactory.getOWLLiteral(literal, lang);
        }
    }


    /**
     * Given a main node, translated data ranges according to Table 12
     * @param mainNode The main node
     * @return The translated data range.  If the data range could not be translated then
     *         an OWLDatatype with the given IRI is returned.
     */
    public OWLDataRange translateDataRange(IRI mainNode) {
        if (!isDataRange(mainNode) && configuration.isStrict()) {
            // Can't translated ANY according to Table 12
            return generateAndLogParseError(EntityType.DATATYPE, mainNode);
        }

        if (!isAnonymousNode(mainNode) && isDataRange(mainNode)) {
            return dataFactory.getOWLDatatype(mainNode);
        }

        IRI intersectionOfObject = getResourceObject(mainNode, OWL_INTERSECTION_OF, true);
        if (intersectionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(intersectionOfObject);
            return dataFactory.getOWLDataIntersectionOf(dataRanges);
        }

        IRI unionOfObject = getResourceObject(mainNode, OWL_UNION_OF, true);
        if (unionOfObject != null) {
            Set<OWLDataRange> dataRanges = translateToDataRangeSet(unionOfObject);
            return dataFactory.getOWLDataUnionOf(dataRanges);
        }

        // The plain complement of triple predicate is in here for legacy reasons
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
            Set<OWLLiteral> literals = translateToConstantSet(oneOfObject);
            return dataFactory.getOWLDataOneOf(literals);
        }


        IRI onDatatypeObject = getResourceObject(mainNode, OWL_ON_DATA_TYPE, true);
        if (onDatatypeObject != null) {

            if (isAnonymousNode(onDatatypeObject)) {
                // TODO LOG ERROR
                return dataFactory.getOWLDatatype(mainNode);
            }
            OWLDatatype restrictedDataRange = (OWLDatatype) translateDataRange(onDatatypeObject);

            // Now we have to get the restricted facets - there is some legacy translation code here... the current
            // spec uses a list of triples where the predicate is a facet and the object a literal that is restricted
            // by the facet.  Originally, there just used to be multiple facet-"facet value" triples
            Set<OWLFacetRestriction> restrictions = new HashSet<OWLFacetRestriction>();

            IRI facetRestrictionList = getResourceObject(mainNode, OWL_WITH_RESTRICTIONS, true);
            if (facetRestrictionList != null) {
                restrictions = translateToFacetRestrictionSet(facetRestrictionList);
            }
            else if (!configuration.isStrict()) {
                // Try the legacy encoding
                for (IRI facetIRI : OWLFacet.FACET_IRIS) {
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
        if (!isAnonymousNode(mainNode)) {
            // Simple object property
            prop = dataFactory.getOWLObjectProperty(mainNode);
            translatedProperties.put(mainNode, prop);
        }
        else {
            // Inverse of a property expression
            IRI inverseOfObject = getResourceObject(mainNode, OWL_INVERSE_OF, true);
            if (inverseOfObject != null) {
                OWLObjectPropertyExpression otherProperty = translateObjectPropertyExpression(inverseOfObject);
                prop = dataFactory.getOWLObjectInverseOf(otherProperty);
            }
            else {
                prop = dataFactory.getOWLObjectInverseOf(dataFactory.getOWLObjectProperty(mainNode));
            }
            objectPropertyExpressionIRIs.add(mainNode);
            translatedProperties.put(mainNode, prop);
        }
        return prop;
    }


    public OWLIndividual translateIndividual(IRI node) {
        return getOWLIndividual(node);
    }

    /**
     * Translates the annotation on a main node.  Triples whose subject is the specified main node and whose subject
     * is typed an an annotation property (or is a built in annotation property) will be translated to annotation on
     * this main node.
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
                    OWLAnnotationProperty prop = dataFactory.getOWLAnnotationProperty(predicate);
                    OWLAnnotationValue val;
                    if (isAnonymousNode(resVal)) {
                        val = dataFactory.getOWLAnonymousIndividual(resVal.toString());
                    }
                    else {
                        val = resVal;
                    }
                    mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop, val, annosOnMainNodeAnnotations));
                    resVal = getResourceObject(mainNode, predicate, true);
                }
                OWLLiteral litVal = getLiteralObject(mainNode, predicate, true);
                while (litVal != null) {
                    OWLAnnotationProperty prop = dataFactory.getOWLAnnotationProperty(predicate);
                    mainNodeAnnotations.add(dataFactory.getOWLAnnotation(prop, litVal, annosOnMainNodeAnnotations));
                    litVal = getLiteralObject(mainNode, predicate, true);
                }
            }
        }
        return mainNodeAnnotations;
    }

    private Map<IRI, OWLClassExpression> translatedClassExpression = new HashMap<IRI, OWLClassExpression>();

    public OWLClassExpression translateClassExpression(IRI mainNode) {
        OWLClassExpression ce = translatedClassExpression.get(mainNode);
        if (ce == null) {
            ce = translateClassExpressionInternal(mainNode);
            translatedClassExpression.put(mainNode, ce);
        }
        return ce;
    }

    private int errorCounter = 0;


    private <E extends OWLEntity> E getErrorEntity(EntityType<E> entityType) {
        errorCounter++;
        IRI iri = IRI.create("http://org.semanticweb.owlapi/error#Error" + errorCounter);
        return dataFactory.getOWLEntity(entityType, iri);
    }


    private OWLClassExpression translateClassExpressionInternal(IRI mainNode) {
        // Some optimisations...
        // We either have a class or a restriction
        if (isRestriction(mainNode)) {
            // We MUST have an owl:onProperty triple whether strict parsing or not
            IRI onPropertyObject = getResourceObject(mainNode, OWL_ON_PROPERTY, false);
            if (onPropertyObject == null) {
                return generateAndLogParseError(EntityType.CLASS, mainNode);
            }

            for (ClassExpressionTranslator translator : objectRestrictionTranslators) {
                if (translator.matches(mainNode)) {
                    return translator.translate(mainNode);
                }
            }
            for (ClassExpressionTranslator translator : dataRestrictionTranslators) {
                if (translator.matches(mainNode)) {
                    return translator.translate(mainNode);
                }
            }

        }
        else if (isClassExpression(mainNode)) {
            for (ClassExpressionTranslator translator : nonRestrictionTranslators) {
                if (translator.matches(mainNode)) {
                    return translator.translate(mainNode);
                }
            }

            return generateAndLogParseError(EntityType.CLASS, mainNode);
        }
        return generateAndLogParseError(EntityType.CLASS, mainNode);
    }


    private RDFResource getRDFResource(IRI iri) {
        return new RDFResource(iri, isAnonymousNode(iri));
    }

    private RDFTriple getRDFTriple(IRI subject, IRI predicate, IRI object) {
        return new RDFTriple(getRDFResource(subject), getRDFResource(predicate), getRDFResource(object));
    }


    private RDFTriple getRDFTriple(IRI subject, IRI predicate, OWLLiteral object) {
        return new RDFTriple(getRDFResource(subject), getRDFResource(predicate), new RDFLiteral(object));
    }

    private Set<RDFTriple> getTriplesForMainNode(IRI mainNode, IRI... augmentingTypes) {
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
            triples.add(getRDFTriple(mainNode, OWLRDFVocabulary.RDF_TYPE.getIRI(), augmentingType));
        }
        return triples;
    }

    private void logError(RDFResourceParseError error) {

    }

    private <E extends OWLEntity> E generateAndLogParseError(EntityType<E> entityType, IRI mainNode) {
        E entity = getErrorEntity(entityType);
        RDFResource mainNodeResource = getRDFResource(mainNode);
        Set<RDFTriple> mainNodeTriples = getTriplesForMainNode(mainNode);
        RDFResourceParseError error = new RDFResourceParseError(entity, mainNodeResource, mainNodeTriples);
        logError(error);
        return entity;
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
        Map<IRI, Collection<IRI>> predObjMap = resTriplesBySubject.get(subject);
        if (predObjMap != null) {
            IRIs.addAll(predObjMap.keySet());
        }
        Map<IRI, Collection<OWLLiteral>> predObjMapLit = litTriplesBySubject.get(subject);
        if (predObjMapLit != null) {
            IRIs.addAll(predObjMapLit.keySet());
        }
        return IRIs;
    }


    public IRI getResourceObject(IRI subject, OWLRDFVocabulary predicate, boolean consume) {
        return getResourceObject(subject, predicate.getIRI(), consume);
    }

//    private IRI cachedSubject = null;
//    private IRI cachedPredicate = null;
//    private IRI cachedObject = null;

    private static int cacheHit = 0;

    public IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
//        if (!consume) {
//            if (cachedSubject != null && cachedPredicate != null) {
//                if (cachedSubject.equals(subject) && cachedPredicate.equals(predicate)) {
//                    cacheHit++;
//                    System.out.println("CACHE HITS: " + cacheHit);
//                    return cachedObject;
//                }
//            }
//        }
//        else {
//            cachedSubject = null;
//            cachedObject = null;
//        }

        Map<IRI, IRI> subjPredMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjPredMap != null) {
            IRI obj = subjPredMap.get(subject);
            if (consume) {
                subjPredMap.remove(subject);
            }
//            else {
//                cachedSubject = subject;
//                cachedPredicate = predicate;
//                cachedObject = obj;
//            }
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
//                    if(!consume) {
//                        cachedSubject = subject;
//                                            cachedPredicate = predicate;
//                                            cachedObject = object;
//
//                    }
                    return object;
                }
            }
        }
//        if(!consume) {
//            cachedSubject = subject;
//                    cachedPredicate = predicate;
//                    cachedObject = null;
//
//        }
        return null;//searchGeneralResourceTriples(subject, predicate, null, consume);
    }

    public Set<IRI> getResourceObjects(IRI subject, IRI predicate) {
        Set<IRI> result = new HashSet<IRI>();
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

    public OWLLiteral getLiteralObject(IRI subject, OWLRDFVocabulary predicate, boolean consume) {
        return getLiteralObject(subject, predicate.getIRI(), consume);
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
        Map<IRI, Collection<OWLLiteral>> predObjMap = litTriplesBySubject.get(subject);
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

    public Set<OWLLiteral> getLiteralObjects(IRI subject, IRI predicate) {
        Set<OWLLiteral> result = new HashSet<OWLLiteral>();
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


    public boolean isTriplePresent(IRI subject, IRI predicate, IRI object, boolean consume) {
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
        return false;// searchGeneralResourceTriples(subject, predicate, object, consume) != null;
    }

//    private IRI searchGeneralResourceTriples(IRI subject, IRI predicate, IRI object, boolean consume) {
//        if(isGeneralPredicate(predicate)) {
//            for(Iterator<ResourceTriple> it = resourceTriples.iterator(); it.hasNext(); ) {
//                ResourceTriple triple = it.next();
//                if(triple.getSubject().equals(subject) && triple.getPredicate().equals(predicate) && (object == null || triple.getObject().equals(object))) {
//                    if (consume) {
//                        it.remove();
//                    }
//                    return triple.getObject();
//                }
//            }
//        }
//        return null;
//    }

    private boolean isGeneralPredicate(IRI predicate) {
        return !predicate.isReservedVocabulary() || OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS.contains(predicate) || predicate.getStart().indexOf(Namespaces.SWRL.toString()) != -1 || predicate.getStart().indexOf(Namespaces.SWRLB.toString()) != -1;
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


    public boolean hasPredicate(IRI subject, IRI predicate) {
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
        return false;//searchGeneralResourceTriples(subject, predicate, null, false) != null;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void addAxiom(IRI axiomIRI) {
        axioms.add(axiomIRI);
    }


    public boolean isAxiom(IRI iri) {
        return axioms.contains(iri);
    }


    public boolean isDataRange(IRI iri) {
        return dataRangeIRIs.contains(iri);
    }

    public OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
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


    private interface ResourceTripleIterator<E extends Throwable> {
        void handleResourceTriple(IRI subject, IRI predicate, IRI object) throws E;
    }

    private interface LiteralTripleIterator<E extends Throwable> {
        void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object) throws E;
    }

    public <E extends Throwable> void iterateResourceTriples(ResourceTripleIterator<E> iterator) throws E {
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
//        for(ResourceTriple triple : new ArrayList<ResourceTriple>(resourceTriples)) {
//            iterator.handleResourceTriple(triple.getSubject(), triple.getPredicate(), triple.getObject());
//        }
    }

    public <E extends Throwable> void iterateLiteralTriples(LiteralTripleIterator<E> iterator) throws E {
        for (IRI subject : new ArrayList<IRI>(litTriplesBySubject.keySet())) {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
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
        Originally we had a special Triple class, which was specialised into ResourceTriple and
        LiteralTriple - this was used to store triples.  However, with very large ontologies this
        proved to be inefficient in terms of memory usage.  Now we just store raw subjects, predicates and
        object directly in varous maps.
    */

    // Resource triples

    // Subject, predicate, object

    private Map<IRI, Map<IRI, Collection<IRI>>> resTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<IRI, Map<IRI, IRI>> singleValuedResTriplesByPredicate = CollectionFactory.createMap();

    // Literal triples
    private Map<IRI, Map<IRI, Collection<OWLLiteral>>> litTriplesBySubject = CollectionFactory.createMap();

    // Predicate, subject, object
    private Map<IRI, Map<IRI, OWLLiteral>> singleValuedLitTriplesByPredicate = CollectionFactory.createMap();

//    private List<ResourceTriple> resourceTriples = new ArrayList<ResourceTriple>();

    public void addTriple(IRI subject, IRI predicate, IRI object) {
//        if(isGeneralPredicate(predicate)) {
//            resourceTriples.add(new ResourceTriple(subject, predicate, object));
//        }
        Map<IRI, IRI> subjObjMap = singleValuedResTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, object);
        }
        else {
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


    public void addTriple(IRI subject, IRI predicate, OWLLiteral con) {
        Map<IRI, OWLLiteral> subjObjMap = singleValuedLitTriplesByPredicate.get(predicate);
        if (subjObjMap != null) {
            subjObjMap.put(subject, con);
        }
        else {
            Map<IRI, Collection<OWLLiteral>> map = litTriplesBySubject.get(subject);
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
