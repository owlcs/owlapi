package org.semanticweb.owlapi6.apitest.fileroundtrip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataAllValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataHasValue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataOneOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Float;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectExactCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectHasSelf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectHasValue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectOneOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi6.reasoner.BufferingMode;
import org.semanticweb.owlapi6.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi6.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi6.search.Searcher;
import org.semanticweb.owlapi6.vocab.OWLFacet;

class FileRoundTripCorrectAxiomsTestCase extends TestBase {

    static final OWLDataProperty DAP = DataProperty(IRI("http://example.com/", "dataProperty"));
    static final OWLObjectProperty OP =
        ObjectProperty(IRI("http://example.com/", "objectProperty"));
    static final OWLClass PERSON = Class(IRI("http://example.com/", "Person"));
    static final String DECLARATIONS = "http://www.semanticweb.org/ontologies/declarations#";
    static final OWLDeclarationAxiom DATATYPE = Declaration(Datatype(IRI(DECLARATIONS, "dt")));
    static final OWLDeclarationAxiom ANNOTATIONP =
        Declaration(AnnotationProperty(IRI(DECLARATIONS, "ap")));
    static final OWLDeclarationAxiom NAMED_INDIVIDUAL =
        Declaration(NamedIndividual(IRI(DECLARATIONS, "ni")));
    static final OWLDeclarationAxiom DATA_PROPERTY =
        Declaration(DataProperty(IRI(DECLARATIONS, "dp")));
    static final String OWLAPI_TEST = "http://www.semanticweb.org/owlapi/test#";
    static final OWLDeclarationAxiom DECLARATION_A =
        Declaration(Class(IRI("http://owlapi.sourceforge.net/ontology#", "ClsA")));
    static final OWLClass subCls = Class(IRI(OWLAPI_TEST, "A"));
    static final OWLClass supCls = Class(IRI(OWLAPI_TEST, "B"));
    static final OWLClass fillerCls = Class(IRI(OWLAPI_TEST, "C"));
    static final OWLObjectProperty property = ObjectProperty(IRI(OWLAPI_TEST, "P"));
    static final OWLDatatype DATAB = Datatype(iri("B"));
    static final OWLDeclarationAxiom dpd = Declaration(DP);
    static final OWLDeclarationAxiom dbb = Declaration(DATAB);
    static final OWLDeclarationAxiom pd = Declaration(P);
    static final OWLDeclarationAxiom cd = Declaration(Class(IRI(DECLARATIONS, "Cls")));
    static final OWLDeclarationAxiom opd = Declaration(ObjectProperty(IRI(DECLARATIONS, "op")));
    static final OWLDeclarationAxiom bd = Declaration(B);
    static final IRI clsA = IRI(OWLAPI_TEST, "ClsA");
    static final IRI prop = IRI(OWLAPI_TEST, "prop");
    static final OWLNamedIndividual subject =
        NamedIndividual(IRI("http://Example.com#", "myBuilding"));
    static final OWLObjectProperty predicate =
        ObjectProperty(IRI("http://Example.com#", "located_at"));
    static final OWLNamedIndividual object =
        NamedIndividual(IRI("http://Example.com#", "myLocation"));

    protected void assertEqualsSet(String ontology, Set<OWLAxiom> axioms) {
        assertEquals(asUnorderedSet(ontologyFromClasspathFile(ontology).axioms()), axioms);
    }

    static Stream<Arguments> axioms() {
        return Stream.of(
            //@formatter:off
            of("DataComplementOf.rdf",             DataPropertyRange(DP, DataComplementOf(Integer())), dpd),
            of("DataIntersectionOf.rdf",           DataPropertyRange(DP, DataIntersectionOf(Integer(), Float())), dpd),
            of("DataOneOf.rdf",                    DataPropertyRange(DP, DataOneOf(Literal(30), Literal(31f))), dpd),
            of("DataUnionOf.rdf",                  DataPropertyRange(DP, DataUnionOf(Integer(), Float())), dpd),
            of("DatatypeRestriction.rdf",          DataPropertyRange(DP, DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MIN_INCLUSIVE, Literal(18)), FacetRestriction(OWLFacet.MAX_INCLUSIVE, Literal(30)))), dpd),
            of("ComplexSubProperty.rdf",           df.getOWLSubPropertyChainOfAxiom(Arrays.asList(P, Q), R)),
            of("TestDeclarations.rdf",             DATA_PROPERTY, NAMED_INDIVIDUAL, ANNOTATIONP, DATATYPE, cd, opd),
            of("DisjointClasses.rdf",              DisjointClasses(A, B, C)),
            of("HasKey.rdf",                       HasKey(PERSON, OP, DAP), Declaration(PERSON), Declaration(DAP), Declaration(OP)),
            of("InverseOf.rdf",                    InverseObjectProperties(P, Q)),
            of("DataAllValuesFrom.rdf",            SubClassOf(A, DataAllValuesFrom(DP, DATAB)), dbb, dpd),
            of("DataHasValue.rdf",                 SubClassOf(A, DataHasValue(DP, Literal(3))), dpd, SubClassOf(A, DataHasValue(DP, Literal("A", "")))),
            of("DataMaxCardinality.rdf",           SubClassOf(A, DataMaxCardinality(3, DP, TopDatatype())), dpd),
            of("DataMinCardinality.rdf",           SubClassOf(A, DataMinCardinality(3, DP, TopDatatype())), dpd),
            of("DataSomeValuesFrom.rdf",           SubClassOf(A, DataSomeValuesFrom(DP, DATAB)), dbb, dpd),
            of("ObjectAllValuesFrom.rdf",          SubClassOf(A, ObjectAllValuesFrom(P, B)), bd, pd),
            of("ObjectCardinality.rdf",            SubClassOf(A, ObjectExactCardinality(3, P, OWLThing())), pd),
            of("ObjectComplementOf.rdf",           SubClassOf(A, ObjectComplementOf(B))),
            of("ObjectHasSelf.rdf",                SubClassOf(A, ObjectHasSelf(P)), pd),
            of("ObjectHasValue.rdf",               SubClassOf(A, ObjectHasValue(P, NamedIndividual(iri("a")))), pd),
            of("ObjectIntersectionOf.rdf",         SubClassOf(A, ObjectIntersectionOf(B, C))),
            of("ObjectMaxCardinality.rdf",         SubClassOf(A, ObjectMaxCardinality(3, P, OWLThing())), pd),
            of("ObjectMaxQualifiedCardinality.rdf",SubClassOf(A, ObjectMaxCardinality(3, P, B)), pd),
            of("ObjectMinCardinality.rdf",         SubClassOf(A, ObjectMinCardinality(3, P, OWLThing())), pd),
            of("ObjectMinQualifiedCardinality.rdf",SubClassOf(A, ObjectMinCardinality(3, P, B)), pd),
            of("ObjectOneOf.rdf",                  SubClassOf(A, ObjectOneOf(NamedIndividual(iri("a")), NamedIndividual(iri("b"))))),
            of("ObjectQualifiedCardinality.rdf",   SubClassOf(A, ObjectExactCardinality(3, P, B)), pd),
            of("ObjectSomeValuesFrom.rdf",         SubClassOf(A, ObjectSomeValuesFrom(P, B)), bd, pd),
            of("ObjectUnionOf.rdf",                SubClassOf(A, ObjectUnionOf(B, C))),
            of("SubClassOf.rdf",                   SubClassOf(A, B)),
            of("UntypedSubClassOf.rdf",            SubClassOf(A, B))
            //@formatter:on
            );
    }

    static Arguments of(String s, OWLAxiom... axioms) {
        return Arguments.of(s, new HashSet<>(Arrays.asList(axioms)));
    }

    @ParameterizedTest
    @MethodSource("axioms")
    void testContainsComplexSubPropertyAxiom(String name, Set<OWLAxiom> axioms) {
        assertEqualsSet(name, axioms);
    }

    @Test
    void testCorrectAxiomAnnotatedPropertyAssertions() {
        OWLOntology ontology = ontologyFromClasspathFile("AnnotatedPropertyAssertions.rdf");
        OWLAxiom ax = ObjectPropertyAssertion(predicate, subject, object);
        assertTrue(ontology.containsAxiom(ax, EXCLUDED, AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
        Set<OWLAxiom> axioms = asUnorderedSet(ontology.axiomsIgnoreAnnotations(ax, EXCLUDED));
        assertEquals(1, axioms.size());
        OWLAxiom theAxiom = axioms.iterator().next();
        assertTrue(theAxiom.isAnnotated());
    }

    @Test
    void testDeprecatedAnnotationAssertionsPresent() {
        OWLOntology ont = ontologyFromClasspathFile("Deprecated.rdf");
        Searcher.annotationObjects(ont.annotationAssertionAxioms(clsA, INCLUDED))
        .forEach(a -> a.isDeprecatedIRIAnnotation());
        Searcher.annotationObjects(ont.annotationAssertionAxioms(prop, INCLUDED))
        .forEach(a -> assertTrue(a.isDeprecatedIRIAnnotation()));
    }

    @Test
    void testCorrectAxiomsRDFSClass() {
        OWLOntology ont = ontologyFromClasspathFile("RDFSClass.rdf");
        assertTrue(ont.containsAxiom(DECLARATION_A));
    }

    @Test
    void testStructuralReasonerRecusion() {
        OWLOntology ontology = ontologyFromClasspathFile("koala.owl");
        String ontName = ontology.getOntologyID().getOntologyIRI().get().toString();
        StructuralReasoner reasoner =
            new StructuralReasoner(ontology, new SimpleConfiguration(), BufferingMode.BUFFERING);
        OWLClass cls = Class(IRI(ontName + "#", "Koala"));
        reasoner.getSubClasses(cls, false);
        reasoner.getSuperClasses(cls, false);
    }

    @Test
    void testIsGCIMethodSubClassAxiom() {
        assertFalse(SubClassOf(A, B).isGCI());
        assertTrue(SubClassOf(ObjectIntersectionOf(A, C), B).isGCI());
    }

    @Test
    void testParsedAxiomsSubClassOfUntypedOWLClass() {
        OWLOntology ontology = ontologyFromClasspathFile("SubClassOfUntypedOWLClass.rdf");
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        assertEquals(subCls, ax.getSubClass());
        assertEquals(supCls, ax.getSuperClass());
    }

    @Test
    void testParsedAxiomsSubClassOfUntypedSomeValuesFrom() {
        OWLOntology ontology = ontologyFromClasspathFile("SubClassOfUntypedSomeValuesFrom.rdf");
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        assertEquals(subCls, ax.getSubClass());
        assertTrue(ax.getSuperClass() instanceof OWLObjectSomeValuesFrom);
        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) ax.getSuperClass();
        assertEquals(property, someValuesFrom.getProperty());
        assertEquals(fillerCls, someValuesFrom.getFiller());
    }
}
