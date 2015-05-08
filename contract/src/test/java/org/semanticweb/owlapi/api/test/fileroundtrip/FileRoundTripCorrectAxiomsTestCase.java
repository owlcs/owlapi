package org.semanticweb.owlapi.api.test.fileroundtrip;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.search.Searcher;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings("javadoc")
public class FileRoundTripCorrectAxiomsTestCase extends TestBase {

    @Test
    public void testCorrectAxiomAnnotatedPropertyAssertions() {
        OWLOntology ontology = ontologyFromClasspathFile(
        "AnnotatedPropertyAssertions.rdf");
        OWLNamedIndividual subject = NamedIndividual(IRI(
        "http://Example.com#myBuilding"));
        OWLObjectProperty predicate = ObjectProperty(IRI(
        "http://Example.com#located_at"));
        OWLNamedIndividual object = NamedIndividual(IRI(
        "http://Example.com#myLocation"));
        OWLAxiom ax = ObjectPropertyAssertion(predicate, subject, object);
        assertTrue(ontology.containsAxiom(ax, EXCLUDED,
        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
        Set<OWLAxiom> axioms = ontology.getAxiomsIgnoreAnnotations(ax,
        EXCLUDED);
        assertEquals(1, axioms.size());
        OWLAxiom theAxiom = axioms.iterator().next();
        assertTrue(theAxiom.isAnnotated());
    }

    @Test
    public void testContainsComplexSubPropertyAxiom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLObjectProperty propQ = ObjectProperty(iri("q"));
        OWLObjectProperty propR = ObjectProperty(iri("r"));
        List<OWLObjectProperty> chain = new ArrayList<>();
        chain.add(propP);
        chain.add(propQ);
        axioms.add(df.getOWLSubPropertyChainOfAxiom(chain, propR));
        assertEquals(asSet(ontologyFromClasspathFile("ComplexSubProperty.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataAllValuesFrom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLDatatype dt = Datatype(iri("B"));
        OWLDataProperty propP = DataProperty(iri("p"));
        axioms.add(SubClassOf(clsA, DataAllValuesFrom(propP, dt)));
        axioms.add(Declaration(dt));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("DataAllValuesFrom.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataComplementOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataRange intdr = Integer();
        OWLDataRange complement = DataComplementOf(intdr);
        OWLDataProperty p = DataProperty(iri("p"));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(p, complement);
        axioms.add(ax);
        axioms.add(Declaration(p));
        assertEquals(asSet(ontologyFromClasspathFile("DataComplementOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataHasValue() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLLiteral literal = Literal(3);
        OWLLiteral stringLiteral = Literal("A", "");
        OWLDataProperty propP = DataProperty(iri("p"));
        axioms.add(SubClassOf(clsA, DataHasValue(propP, literal)));
        axioms.add(Declaration(propP));
        axioms.add(SubClassOf(clsA, DataHasValue(propP, stringLiteral)));
        assertEquals(asSet(ontologyFromClasspathFile("DataHasValue.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataIntersectionOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataRange intdr = df.getIntegerOWLDatatype();
        OWLDataRange floatdr = df.getFloatOWLDatatype();
        OWLDataRange intersection = df.getOWLDataIntersectionOf(intdr, floatdr);
        OWLDataProperty p = DataProperty(iri("p"));
        OWLDataPropertyRangeAxiom ax = df.getOWLDataPropertyRangeAxiom(p,
        intersection);
        axioms.add(ax);
        axioms.add(Declaration(p));
        assertEquals(asSet(ontologyFromClasspathFile("DataIntersectionOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataMaxCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLDataProperty prop = DataProperty(iri("p"));
        axioms.add(SubClassOf(clsA, DataMaxCardinality(3, prop,
        TopDatatype())));
        axioms.add(Declaration(prop));
        assertEquals(asSet(ontologyFromClasspathFile("DataMaxCardinality.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataMinCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLDataProperty prop = DataProperty(iri("p"));
        axioms.add(SubClassOf(clsA, DataMinCardinality(3, prop,
        TopDatatype())));
        axioms.add(Declaration(prop));
        assertEquals(asSet(ontologyFromClasspathFile("DataMinCardinality.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataOneOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataRange oneOf = DataOneOf(Literal(30), Literal(31f));
        OWLDataProperty p = DataProperty(iri("p"));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(p, oneOf);
        axioms.add(ax);
        axioms.add(Declaration(p));
        assertEquals(asSet(ontologyFromClasspathFile("DataOneOf.rdf").axioms()),
        axioms);
    }

    @Test
    public void testCorrectAxiomsDataSomeValuesFrom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLDatatype dt = Datatype(iri("B"));
        OWLDataProperty propP = DataProperty(iri("p"));
        axioms.add(SubClassOf(clsA, DataSomeValuesFrom(propP, dt)));
        axioms.add(Declaration(dt));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("DataSomeValuesFrom.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDataUnionOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataRange intdr = Integer();
        OWLDataRange floatdr = Float();
        OWLDataRange union = DataUnionOf(intdr, floatdr);
        OWLDataProperty p = DataProperty(iri("p"));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(p, union);
        axioms.add(ax);
        axioms.add(Declaration(p));
        assertEquals(asSet(ontologyFromClasspathFile("DataUnionOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDatatypeRestriction() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataRange dr = DatatypeRestriction(Integer(), FacetRestriction(
        OWLFacet.MIN_INCLUSIVE, Literal(18)), FacetRestriction(
        OWLFacet.MAX_INCLUSIVE, Literal(30)));
        OWLDataProperty p = DataProperty(iri("p"));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(p, dr);
        axioms.add(ax);
        axioms.add(Declaration(p));
        assertEquals(asSet(ontologyFromClasspathFile("DatatypeRestriction.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsDeclarations() {
        Set<OWLAxiom> axioms = new HashSet<>();
        axioms.add(Declaration(Class(IRI(
        "http://www.semanticweb.org/ontologies/declarations#Cls"))));
        axioms.add(Declaration(ObjectProperty(IRI(
        "http://www.semanticweb.org/ontologies/declarations#op"))));
        axioms.add(Declaration(DataProperty(IRI(
        "http://www.semanticweb.org/ontologies/declarations#dp"))));
        axioms.add(Declaration(NamedIndividual(IRI(
        "http://www.semanticweb.org/ontologies/declarations#ni"))));
        axioms.add(Declaration(AnnotationProperty(IRI(
        "http://www.semanticweb.org/ontologies/declarations#ap"))));
        axioms.add(Declaration(Datatype(IRI(
        "http://www.semanticweb.org/ontologies/declarations#dt"))));
        assertEquals(asSet(ontologyFromClasspathFile("TestDeclarations.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testDeprecatedAnnotationAssertionsPresent() {
        OWLOntology ont = ontologyFromClasspathFile("Deprecated.rdf");
        OWLClass cls = Class(iri(
        "http://www.semanticweb.org/owlapi/test#ClsA"));
        Searcher.annotations(ont.annotationAssertionAxioms(cls.getIRI(),
        INCLUDED)).forEach(a -> a.isDeprecatedIRIAnnotation());
        OWLDataProperty prop = DataProperty(iri(
        "http://www.semanticweb.org/owlapi/test#prop"));
        Searcher.annotations(ont.annotationAssertionAxioms(prop.getIRI(),
        INCLUDED)).forEach(a -> assertTrue(a.isDeprecatedIRIAnnotation()));
    }

    @Test
    public void testContainsDisjointClasses() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        axioms.add(DisjointClasses(clsA, clsB, clsC));
        assertEquals(asSet(ontologyFromClasspathFile("DisjointClasses.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsHasKey() {
        OWLClass cls = Class(IRI("http://example.com/Person"));
        OWLDataProperty propP = DataProperty(IRI(
        "http://example.com/dataProperty"));
        OWLObjectProperty propQ = ObjectProperty(IRI(
        "http://example.com/objectPoperty"));
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLHasKeyAxiom owlHasKeyAxiom = HasKey(cls, propQ, propP);
        axioms.add(owlHasKeyAxiom);
        Set<OWLAxiom> axioms2 = asSet(ontologyFromClasspathFile("HasKey.rdf")
        .axioms());
        assertTrue(axioms2.containsAll(axioms));
    }

    @Test
    public void testContainsInverseOf() {
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLObjectProperty propQ = ObjectProperty(iri("q"));
        Set<OWLAxiom> axioms = new HashSet<>();
        axioms.add(InverseObjectProperties(propP, propQ));
        assertEquals(axioms, asSet(ontologyFromClasspathFile("InverseOf.rdf")
        .axioms()));
    }

    @Test
    public void testCorrectAxiomsObjectAllValuesFrom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        axioms.add(SubClassOf(clsA, ObjectAllValuesFrom(propP, clsB)));
        axioms.add(Declaration(clsB));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectAllValuesFrom.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectExactCardinality(3, prop,
        OWLThing())));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectCardinality.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectComplementOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        axioms.add(SubClassOf(clsA, ObjectComplementOf(clsB)));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectComplementOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectHasSelf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        axioms.add(SubClassOf(clsA, ObjectHasSelf(propP)));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectHasSelf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectHasValue() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLIndividual ind = NamedIndividual(iri("a"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        axioms.add(SubClassOf(clsA, ObjectHasValue(propP, ind)));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectHasValue.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectIntersectionOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        axioms.add(SubClassOf(clsA, ObjectIntersectionOf(clsB, clsC)));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectIntersectionOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectMaxCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectMaxCardinality(3, prop, OWLThing())));
        assertEquals(axioms, asSet(ontologyFromClasspathFile(
        "ObjectMaxCardinality.rdf").axioms()));
    }

    @Test
    public void testCorrectAxiomsObjectMaxQualifiedCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectMaxCardinality(3, prop, clsB)));
        assertEquals(asSet(ontologyFromClasspathFile(
        "ObjectMaxQualifiedCardinality.rdf").axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectMinCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectMinCardinality(3, prop, OWLThing())));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectMinCardinality.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectMinQualifiedCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectMinCardinality(3, prop, clsB)));
        assertEquals(asSet(ontologyFromClasspathFile(
        "ObjectMinQualifiedCardinality.rdf").axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectOneOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLNamedIndividual indA = NamedIndividual(iri("a"));
        OWLNamedIndividual indB = NamedIndividual(iri("b"));
        axioms.add(SubClassOf(clsA, ObjectOneOf(indA, indB)));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectOneOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectQualifiedCardinality() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        axioms.add(Declaration(prop));
        axioms.add(SubClassOf(clsA, ObjectExactCardinality(3, prop, clsB)));
        assertEquals(asSet(ontologyFromClasspathFile(
        "ObjectQualifiedCardinality.rdf").axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectSomeValuesFrom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        axioms.add(SubClassOf(clsA, ObjectSomeValuesFrom(propP, clsB)));
        axioms.add(Declaration(clsB));
        axioms.add(Declaration(propP));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectSomeValuesFrom.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsObjectUnionOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        axioms.add(SubClassOf(clsA, ObjectUnionOf(clsB, clsC)));
        assertEquals(asSet(ontologyFromClasspathFile("ObjectUnionOf.rdf")
        .axioms()), axioms);
    }

    @Test
    public void testCorrectAxiomsRDFSClass() {
        OWLOntology ont = ontologyFromClasspathFile("RDFSClass.rdf");
        IRI clsIRI = IRI("http://owlapi.sourceforge.net/ontology#ClsA");
        OWLClass cls = Class(clsIRI);
        OWLDeclarationAxiom ax = Declaration(cls);
        assertTrue(ont.containsAxiom(ax));
    }

    @Test
    public void testStructuralReasonerRecusion() {
        OWLOntology ontology = ontologyFromClasspathFile("koala.owl");
        String ontName = ontology.getOntologyID().getOntologyIRI().get()
        .toString();
        StructuralReasoner reasoner = new StructuralReasoner(ontology,
        new SimpleConfiguration(), BufferingMode.BUFFERING);
        OWLClass cls = Class(IRI(ontName + "#Koala"));
        reasoner.getSubClasses(cls, false);
        reasoner.getSuperClasses(cls, false);
    }

    @Test
    public void testCorrectAxiomsSubClassAxiom() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        axioms.add(SubClassOf(clsA, clsB));
        assertEquals(asSet(ontologyFromClasspathFile("SubClassOf.rdf")
        .axioms()), axioms);
    }

    /** Tests the isGCI method on OWLSubClassAxiom */
    @Test
    public void testIsGCIMethodSubClassAxiom() {
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        OWLClassExpression desc = ObjectIntersectionOf(clsA, clsC);
        OWLSubClassOfAxiom ax1 = SubClassOf(clsA, clsB);
        assertFalse(ax1.isGCI());
        OWLSubClassOfAxiom ax2 = SubClassOf(desc, clsB);
        assertTrue(ax2.isGCI());
    }

    @Test
    public void testParsedAxiomsSubClassOfUntypedOWLClass() {
        OWLOntology ontology = ontologyFromClasspathFile(
        "SubClassOfUntypedOWLClass.rdf");
        Set<OWLSubClassOfAxiom> axioms = ontology.getAxioms(
        AxiomType.SUBCLASS_OF);
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.iterator().next();
        OWLClass subCls = Class(IRI(
        "http://www.semanticweb.org/owlapi/test#A"));
        OWLClass supCls = Class(IRI(
        "http://www.semanticweb.org/owlapi/test#B"));
        assertEquals(subCls, ax.getSubClass());
        assertEquals(supCls, ax.getSuperClass());
    }

    @Test
    public void testParsedAxiomsSubClassOfUntypedSomeValuesFrom() {
        OWLOntology ontology = ontologyFromClasspathFile(
        "SubClassOfUntypedSomeValuesFrom.rdf");
        Set<OWLSubClassOfAxiom> axioms = ontology.getAxioms(
        AxiomType.SUBCLASS_OF);
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.iterator().next();
        OWLClass subCls = Class(IRI(
        "http://www.semanticweb.org/owlapi/test#A"));
        assertEquals(subCls, ax.getSubClass());
        OWLClassExpression supCls = ax.getSuperClass();
        assertTrue(supCls instanceof OWLObjectSomeValuesFrom);
        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) supCls;
        OWLObjectProperty property = ObjectProperty(IRI(
        "http://www.semanticweb.org/owlapi/test#P"));
        OWLClass fillerCls = Class(IRI(
        "http://www.semanticweb.org/owlapi/test#C"));
        assertEquals(property, someValuesFrom.getProperty());
        assertEquals(fillerCls, someValuesFrom.getFiller());
    }

    @Test
    public void testContainsAxiomsUntypedSubClassOf() {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        axioms.add(SubClassOf(clsA, clsB));
        assertEquals(axioms, asSet(ontologyFromClasspathFile(
        "UntypedSubClassOf.rdf").axioms()));
    }
}
