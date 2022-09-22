package org.semanticweb.owlapi.atomicdecompositiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.atomicdecomposition.AxiomWrapper;
import org.semanticweb.owlapi.atomicdecomposition.Signature;
import org.semanticweb.owlapi.atomicdecomposition.SyntacticLocalityChecker;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;

class SyntacticLocalityTestCase extends TestBase {

    @Test
    void shouldBeLocalowlDeclarationAxiom() {
        // declare a
        axiom = Declaration(CLASSES.A);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.B);
    }

    @Test
    void shouldBeLocalowlEquivalentClassesAxiom() {
        axiom = EquivalentClasses(CLASSES.A, CLASSES.B);
        // signature intersects
        test(axiom, false, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.C);
        // illegal axiom
        test(EquivalentClasses(CLASSES.A), true, CLASSES.A);
        // include bottom
        test(EquivalentClasses(OWLNothing(), CLASSES.A, CLASSES.B), false, CLASSES.A);
        // include top
        test(EquivalentClasses(OWLThing(), CLASSES.A, CLASSES.B), false, CLASSES.A);
        // include bottom and top
        test(EquivalentClasses(OWLNothing(), OWLThing(), CLASSES.A, CLASSES.B), false, CLASSES.A);
    }

    @Test
    void shouldBeLocalowlDisjointClassesAxiom() {
        axiom = DisjointClasses(CLASSES.A, CLASSES.B);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.C);
        axiom = DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C);
        // signature intersects
        test(axiom, false, CLASSES.A, CLASSES.B);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
        // include top
        test(DisjointClasses(OWLThing(), CLASSES.A, CLASSES.B), false, CLASSES.A);
    }

    @Test
    void shouldBeLocalowlDisjointUnionAxiom() {
        axiom = DisjointUnion(CLASSES.A, CLASSES.B, CLASSES.C);
        // signature intersects
        test(axiom, false, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
        // partition top
        axiom = DisjointUnion(OWLThing(), CLASSES.B, CLASSES.C);
        // signature intersects
        test(axiom, false, CLASSES.B);
        // partition top
        axiom = DisjointUnion(OWLThing(), CLASSES.B, OWLThing());
        // signature intersects
        test(axiom, false, CLASSES.B);
    }

    @Test
    void shouldBeLocalowlEquivalentObjectPropertiesAxiom() {
        axiom = EquivalentObjectProperties(OBJPROPS.P, OBJPROPS.Q);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.R);
        // illegal axiom
        test(EquivalentObjectProperties(OBJPROPS.Q), true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlEquivalentDataPropertiesAxiom() {
        axiom = EquivalentDataProperties(DATAPROPS.s, DATAPROPS.dt);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, true, DATAPROPS.v);
        // illegal axiom
        test(EquivalentDataProperties(DATAPROPS.v), true, DATAPROPS.v);
    }

    @Test
    void shouldBeLocalowlDisjointObjectPropertiesAxiom() {
        axiom = DisjointObjectProperties(OBJPROPS.P, OBJPROPS.Q);
        // signature intersects
        test(axiom, true, OBJPROPS.P);
        test(axiom, false, true, OBJPROPS.P);
        // signature does not intersect
        test(axiom, false, true, OBJPROPS.R);
        // top locality sig
        test(DisjointObjectProperties(OBJPROPS.P, OBJPROPS.Q), false, true, OBJPROPS.P);
        // top property
        test(DisjointObjectProperties(OBJPROPS.P, OBJPROPS.Q, TopObjectProperty()), false,
            OBJPROPS.P);
        // bottom property
        test(DisjointObjectProperties(OBJPROPS.P, OBJPROPS.Q, BottomObjectProperty()), true,
            OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlDisjointDataPropertiesAxiom() {
        axiom = DisjointDataProperties(DATAPROPS.s, DATAPROPS.dt);
        // signature intersects
        test(axiom, true, DATAPROPS.s);
        // signature does not intersect
        test(axiom, true, DATAPROPS.v);
        // top locality
        test(axiom, false, true, OBJPROPS.P);
        // top property
        test(DisjointDataProperties(TopDataProperty(), DATAPROPS.s, DATAPROPS.dt), false,
            DATAPROPS.s);
    }

    @Test
    void shouldBeLocalowlSameIndividualAxiom() {
        axiom = SameIndividual(INDIVIDUALS.x, INDIVIDUALS.y);
        // signature intersects
        test(axiom, false, INDIVIDUALS.x);
        // signature does not intersect
        test(axiom, false, INDIVIDUALS.indz);
    }

    @Test
    void shouldBeLocalowlDifferentIndividualsAxiom() {
        axiom = DifferentIndividuals(INDIVIDUALS.x, INDIVIDUALS.y);
        // signature intersects
        test(axiom, false, INDIVIDUALS.x);
        // signature does not intersect
        test(axiom, false, INDIVIDUALS.indz);
    }

    @Test
    void shouldBeLocalowlInverseObjectPropertiesAxiom() {
        axiom = InverseObjectProperties(OBJPROPS.P, OBJPROPS.Q);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.R);
        // top property
        axiom = InverseObjectProperties(OBJPROPS.P, TopObjectProperty());
        test(axiom, false, true, OBJPROPS.P);
        axiom = InverseObjectProperties(TopObjectProperty(), OBJPROPS.P);
        test(axiom, false, true, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlSubObjectPropertyOfAxiom() {
        axiom = SubObjectPropertyOf(OBJPROPS.P, OBJPROPS.Q);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.R);
        // top property
        axiom = SubObjectPropertyOf(OBJPROPS.P, TopObjectProperty());
        test(axiom, true, OBJPROPS.P);
        axiom = SubObjectPropertyOf(TopObjectProperty(), OBJPROPS.P);
        test(axiom, false, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlSubDataPropertyOfAxiom() {
        axiom = SubDataPropertyOf(DATAPROPS.s, DATAPROPS.dt);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, true, DATAPROPS.v);
        // top property
        axiom = SubDataPropertyOf(DATAPROPS.v, TopDataProperty());
        // signature intersects
        test(axiom, true, DATAPROPS.v);
        axiom = SubDataPropertyOf(TopDataProperty(), DATAPROPS.v);
        test(axiom, false, DATAPROPS.v);
    }

    @Test
    void shouldBeLocalowlObjectPropertyDomainAxiom() {
        axiom = ObjectPropertyDomain(OBJPROPS.P, CLASSES.A);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
        // top class
        axiom = ObjectPropertyDomain(OBJPROPS.P, OWLThing());
        test(axiom, true, OBJPROPS.P);
        // bottom property
        axiom = ObjectPropertyDomain(BottomObjectProperty(), CLASSES.A);
        test(axiom, true, CLASSES.A);
    }

    @Test
    void shouldBeLocalowlDataPropertyDomainAxiom() {
        axiom = DataPropertyDomain(DATAPROPS.s, CLASSES.A);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
        // top class
        axiom = DataPropertyDomain(DATAPROPS.v, OWLThing());
        test(axiom, true, DATAPROPS.v);
        // bottom property
        axiom = DataPropertyDomain(BottomDataProperty(), OWLThing());
        test(axiom, true, CLASSES.A);
    }

    @Test
    void shouldBeLocalowlObjectPropertyRangeAxiom() {
        axiom = ObjectPropertyRange(OBJPROPS.P, CLASSES.A);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
    }

    @Test
    void shouldBeLocalowlDataPropertyRangeAxiom() {
        axiom = DataPropertyRange(DATAPROPS.s, DATATYPES.DT);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, true, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlTransitiveObjectPropertyAxiom() {
        axiom = TransitiveObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlReflexiveObjectPropertyAxiom() {
        axiom = ReflexiveObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, false, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlIrreflexiveObjectPropertyAxiom() {
        axiom = IrreflexiveObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlSymmetricObjectPropertyAxiom() {
        axiom = SymmetricObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlAsymmetricObjectPropertyAxiom() {
        axiom = AsymmetricObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlFunctionalObjectPropertyAxiom() {
        axiom = FunctionalObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlFunctionalDataPropertyAxiom() {
        axiom = FunctionalDataProperty(DATAPROPS.s);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, true, DATAPROPS.dt);
    }

    @Test
    void shouldBeLocalowlInverseFunctionalObjectPropertyAxiom() {
        axiom = InverseFunctionalObjectProperty(OBJPROPS.P);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlSubClassOfAxiom() {
        axiom = SubClassOf(CLASSES.A, CLASSES.B);
        // signature intersects
        test(axiom, false, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
    }

    @Test
    void shouldBeLocalowlClassAssertionAxiom() {
        axiom = ClassAssertion(CLASSES.A, INDIVIDUALS.x);
        // signature intersects
        test(axiom, false, CLASSES.A);
        // signature does not intersect
        test(axiom, false, CLASSES.D);
    }

    @Test
    void shouldBeLocalowlObjectPropertyAssertionAxiom() {
        axiom = ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.y, INDIVIDUALS.indz);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, false, INDIVIDUALS.x);
    }

    @Test
    void shouldBeLocalowlNegativeObjectPropertyAssertionAxiom() {
        axiom = NegativeObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.x, INDIVIDUALS.y);
        // signature intersects
        test(axiom, false, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, INDIVIDUALS.indz);
    }

    @Test
    void shouldBeLocalowlDataPropertyAssertionAxiom() {
        axiom = DataPropertyAssertion(DATAPROPS.s, INDIVIDUALS.x, LITERALS.lit1);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, false, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlNegativeDataPropertyAssertionAxiom() {
        axiom = NegativeDataPropertyAssertion(DATAPROPS.s, INDIVIDUALS.x, LITERALS.LIT_TRUE);
        // signature intersects
        test(axiom, false, DATAPROPS.s);
        // signature does not intersect
        test(axiom, false, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlAnnotationAssertionAxiom() {
        axiom = AnnotationAssertion(ANNPROPS.g, CLASSES.A.getIRI(), LITERALS.lit1);
        // signature intersects
        test(axiom, true, ANNPROPS.g);
        // signature does not intersect
        test(axiom, true, CLASSES.B);
    }

    @Test
    void shouldBeLocalowlSubAnnotationPropertyOfAxiom() {
        axiom = SubAnnotationPropertyOf(ANNPROPS.g, ANNPROPS.h);
        // signature intersects
        test(axiom, true, ANNPROPS.g);
        // signature does not intersect
        test(axiom, true, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyDomainAxiom() {
        axiom = AnnotationPropertyDomain(ANNPROPS.g, CLASSES.A.getIRI());
        // signature intersects
        test(axiom, true, ANNPROPS.g);
        // signature does not intersect
        test(axiom, true, ANNPROPS.h);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyRangeAxiom() {
        axiom = AnnotationPropertyRange(ANNPROPS.g, CLASSES.A.getIRI());
        // signature intersects
        test(axiom, true, ANNPROPS.g);
        // signature does not intersect
        test(axiom, true, ANNPROPS.h);
    }

    @Test
    void shouldBeLocalowlSubPropertyChainOfAxiom() {
        axiom = SubPropertyChainOf(l(OBJPROPS.P, OBJPROPS.Q), OBJPROPS.R);
        // signature intersects
        test(axiom, true, OBJPROPS.P);
        // signature does not intersect
        test(axiom, true, DATAPROPS.s);
        // signature equals
        test(axiom, false, OBJPROPS.P, OBJPROPS.Q, OBJPROPS.R);
        // top property
        axiom = SubPropertyChainOf(l(OBJPROPS.P, OBJPROPS.Q), TopObjectProperty());
        // signature intersects
        test(axiom, true, OBJPROPS.P);
    }

    @Test
    void shouldBeLocalowlHasKeyAxiom() {
        axiom = HasKey(CLASSES.A, OBJPROPS.P, DATAPROPS.s);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, OBJPROPS.Q);
    }

    @Test
    void shouldBeLocalowlDatatypeDefinitionAxiom() {
        axiom = DatatypeDefinition(DATATYPES.DT, DatatypeMinMaxExclusiveRestriction(1, 3));
        // signature intersects
        test(axiom, true, INDIVIDUALS.i);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
    }

    @Test
    void shouldBeLocalswrlRule() {
        Set<SWRLAtom> head = set(SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.x)));
        Set<SWRLAtom> body = set(SWRLClassAtom(CLASSES.B, SWRLIndividualArgument(INDIVIDUALS.y)));
        axiom = SWRLRule(head, body);
        // signature intersects
        test(axiom, true, CLASSES.A);
        // signature does not intersect
        test(axiom, true, CLASSES.D);
    }

    @Test
    void shouldResetSignature() {
        OWLSubClassOfAxiom ax = SubClassOf(CLASSES.A, CLASSES.B);
        testSubject.preprocessOntology(l(new AxiomWrapper(ax)));
        assertEquals(asSet(ax.signature()), testSubject.getSignature().getSignature());
    }

    private OWLAxiom axiom;
    private SyntacticLocalityChecker testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new SyntacticLocalityChecker();
    }

    private void set(OWLEntity... entities) {
        testSubject.setSignatureValue(new Signature(Stream.of(entities)));
    }

    private void test(OWLAxiom ax, boolean expected, OWLEntity... entities) {
        set(entities);
        boolean local = testSubject.local(ax);
        assertEquals(Boolean.valueOf(expected), Boolean.valueOf(local));
    }

    private void test(OWLAxiom ax, boolean expected, boolean locality, OWLEntity... entities) {
        set(entities);
        testSubject.getSignature().setLocality(locality);
        boolean local = testSubject.local(ax);
        assertEquals(Boolean.valueOf(expected), Boolean.valueOf(local));
    }
}
