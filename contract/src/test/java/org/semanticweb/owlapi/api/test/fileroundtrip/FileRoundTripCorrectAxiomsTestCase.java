package org.semanticweb.owlapi.api.test.fileroundtrip;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Arrays;
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

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class FileRoundTripCorrectAxiomsTestCase extends TestBase {

    protected OWLDataProperty dp = DataProperty(iri("p"));
    protected OWLClass clA = Class(iri("A"));
    protected OWLObjectProperty or = ObjectProperty(iri("r"));
    protected OWLObjectProperty oq = ObjectProperty(iri("q"));
    protected OWLObjectProperty op = ObjectProperty(iri("p"));
    protected OWLDatatype dt = Datatype(iri("B"));
    protected OWLClass clB = Class(iri("B"));
    protected OWLClass classC = Class(iri("C"));

    protected void assertEqualsSet(String ontology, OWLAxiom... axioms) {
        assertEquals(asSet(ontologyFromClasspathFile(ontology).axioms()), Sets
            .newHashSet(axioms));
    }

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
        List<OWLObjectProperty> chain = Arrays.asList(op, oq);
        assertEqualsSet("ComplexSubProperty.rdf", df
            .getOWLSubPropertyChainOfAxiom(chain, or));
    }

    @Test
    public void testCorrectAxiomsDataAllValuesFrom() {
        assertEqualsSet("DataAllValuesFrom.rdf", SubClassOf(clA,
            DataAllValuesFrom(dp, dt)), Declaration(dt), Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataComplementOf() {
        OWLDataRange complement = DataComplementOf(Integer());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, complement);
        assertEqualsSet("DataComplementOf.rdf", ax, Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataHasValue() {
        assertEqualsSet("DataHasValue.rdf", SubClassOf(clA, DataHasValue(dp,
            Literal(3))), Declaration(dp), SubClassOf(clA, DataHasValue(dp,
                Literal("A", ""))));
    }

    @Test
    public void testCorrectAxiomsDataIntersectionOf() {
        OWLDataRange intersection = DataIntersectionOf(Integer(), Float());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, intersection);
        assertEqualsSet("DataIntersectionOf.rdf", ax, Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataMaxCardinality() {
        assertEqualsSet("DataMaxCardinality.rdf", SubClassOf(clA,
            DataMaxCardinality(3, dp, TopDatatype())), Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataMinCardinality() {
        assertEqualsSet("DataMinCardinality.rdf", SubClassOf(clA,
            DataMinCardinality(3, dp, TopDatatype())), Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataOneOf() {
        OWLDataRange oneOf = DataOneOf(Literal(30), Literal(31f));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, oneOf);
        assertEqualsSet("DataOneOf.rdf", ax, Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataSomeValuesFrom() {
        assertEqualsSet("DataSomeValuesFrom.rdf", SubClassOf(clA,
            DataSomeValuesFrom(dp, dt)), Declaration(dt), Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDataUnionOf() {
        OWLDataRange union = DataUnionOf(Integer(), Float());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, union);
        assertEqualsSet("DataUnionOf.rdf", ax, Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDatatypeRestriction() {
        OWLDataRange dr = DatatypeRestriction(Integer(), FacetRestriction(
            OWLFacet.MIN_INCLUSIVE, Literal(18)), FacetRestriction(
                OWLFacet.MAX_INCLUSIVE, Literal(30)));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, dr);
        assertEqualsSet("DatatypeRestriction.rdf", ax, Declaration(dp));
    }

    @Test
    public void testCorrectAxiomsDeclarations() {
        OWLClass c = Class(IRI(
            "http://www.semanticweb.org/ontologies/declarations#Cls"));
        OWLObjectProperty o = ObjectProperty(IRI(
            "http://www.semanticweb.org/ontologies/declarations#op"));
        OWLDataProperty d = DataProperty(IRI(
            "http://www.semanticweb.org/ontologies/declarations#dp"));
        OWLNamedIndividual i = NamedIndividual(IRI(
            "http://www.semanticweb.org/ontologies/declarations#ni"));
        OWLAnnotationProperty ap = AnnotationProperty(IRI(
            "http://www.semanticweb.org/ontologies/declarations#ap"));
        OWLDatatype datatype = Datatype(IRI(
            "http://www.semanticweb.org/ontologies/declarations#dt"));
        assertEqualsSet("TestDeclarations.rdf", Declaration(c), Declaration(o),
            Declaration(d), Declaration(i), Declaration(ap), Declaration(
                datatype));
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
        assertEqualsSet("DisjointClasses.rdf", DisjointClasses(clA, clB,
            classC));
    }

    @Test
    public void testCorrectAxiomsHasKey() {
        OWLClass cls = Class(IRI("http://example.com/Person"));
        OWLDataProperty propP = DataProperty(IRI(
            "http://example.com/dataProperty"));
        OWLObjectProperty propQ = ObjectProperty(IRI(
            "http://example.com/objectProperty"));
        assertEqualsSet("HasKey.rdf", HasKey(cls, propQ, propP), Declaration(
            cls), Declaration(propP), Declaration(propQ));
    }

    @Test
    public void testContainsInverseOf() {
        assertEqualsSet("InverseOf.rdf", InverseObjectProperties(op, oq));
    }

    @Test
    public void testCorrectAxiomsObjectAllValuesFrom() {
        assertEqualsSet("ObjectAllValuesFrom.rdf", SubClassOf(clA,
            ObjectAllValuesFrom(op, clB)), Declaration(clB), Declaration(op));
    }

    @Test
    public void testCorrectAxiomsObjectCardinality() {
        assertEqualsSet("ObjectCardinality.rdf", Declaration(op), SubClassOf(
            clA, ObjectExactCardinality(3, op, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectComplementOf() {
        assertEqualsSet("ObjectComplementOf.rdf", SubClassOf(clA,
            ObjectComplementOf(clB)));
    }

    @Test
    public void testCorrectAxiomsObjectHasSelf() {
        assertEqualsSet("ObjectHasSelf.rdf", SubClassOf(clA, ObjectHasSelf(op)),
            Declaration(op));
    }

    @Test
    public void testCorrectAxiomsObjectHasValue() {
        assertEqualsSet("ObjectHasValue.rdf", SubClassOf(clA, ObjectHasValue(op,
            NamedIndividual(iri("a")))), Declaration(op));
    }

    @Test
    public void testCorrectAxiomsObjectIntersectionOf() {
        assertEqualsSet("ObjectIntersectionOf.rdf", SubClassOf(clA,
            ObjectIntersectionOf(clB, classC)));
    }

    @Test
    public void testCorrectAxiomsObjectMaxCardinality() {
        assertEqualsSet("ObjectMaxCardinality.rdf", Declaration(op), SubClassOf(
            clA, ObjectMaxCardinality(3, op, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectMaxQualifiedCardinality() {
        assertEqualsSet("ObjectMaxQualifiedCardinality.rdf", Declaration(op),
            SubClassOf(clA, ObjectMaxCardinality(3, op, clB)));
    }

    @Test
    public void testCorrectAxiomsObjectMinCardinality() {
        assertEqualsSet("ObjectMinCardinality.rdf", Declaration(op), SubClassOf(
            clA, ObjectMinCardinality(3, op, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectMinQualifiedCardinality() {
        assertEqualsSet("ObjectMinQualifiedCardinality.rdf", Declaration(op),
            SubClassOf(clA, ObjectMinCardinality(3, op, clB)));
    }

    @Test
    public void testCorrectAxiomsObjectOneOf() {
        OWLNamedIndividual indA = NamedIndividual(iri("a"));
        OWLNamedIndividual indB = NamedIndividual(iri("b"));
        assertEqualsSet("ObjectOneOf.rdf", SubClassOf(clA, ObjectOneOf(indA,
            indB)));
    }

    @Test
    public void testCorrectAxiomsObjectQualifiedCardinality() {
        assertEqualsSet("ObjectQualifiedCardinality.rdf", Declaration(op),
            SubClassOf(clA, ObjectExactCardinality(3, op, clB)));
    }

    @Test
    public void testCorrectAxiomsObjectSomeValuesFrom() {
        assertEqualsSet("ObjectSomeValuesFrom.rdf", SubClassOf(clA,
            ObjectSomeValuesFrom(op, clB)), Declaration(clB), Declaration(op));
    }

    @Test
    public void testCorrectAxiomsObjectUnionOf() {
        assertEqualsSet("ObjectUnionOf.rdf", SubClassOf(clA, ObjectUnionOf(clB,
            classC)));
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
        assertEqualsSet("SubClassOf.rdf", SubClassOf(clA, clB));
    }

    /** Tests the isGCI method on OWLSubClassAxiom */
    @Test
    public void testIsGCIMethodSubClassAxiom() {
        OWLClassExpression desc = ObjectIntersectionOf(clA, classC);
        OWLSubClassOfAxiom ax1 = SubClassOf(clA, clB);
        assertFalse(ax1.isGCI());
        OWLSubClassOfAxiom ax2 = SubClassOf(desc, clB);
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
        assertEqualsSet("UntypedSubClassOf.rdf", SubClassOf(clA, clB));
    }
}
