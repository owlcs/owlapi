package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.Signature;
import org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker;

class SyntacticLocalityTestCase extends TestBase {

    @Test
    void shouldBeLocalowlDeclarationAxiom() {
        // declare a
        axiom = Declaration(A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, B);
    }

    @Test
    void shouldBeLocalowlEquivalentClassesAxiom() {
        axiom = EquivalentClasses(A, B);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        test(axiom, true, C);
        // illegal axiom
        test(EquivalentClasses(A), true, A);
        // include bottom
        test(EquivalentClasses(OWLNothing(), A, B), false, A);
        // include top
        test(EquivalentClasses(OWLThing(), A, B), false, A);
        // include bottom and top
        test(EquivalentClasses(OWLNothing(), OWLThing(), A, B), false, A);
    }

    @Test
    void shouldBeLocalowlDisjointClassesAxiom() {
        axiom = DisjointClasses(A, B);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, C);
        axiom = DisjointClasses(A, B, C);
        // signature intersects
        test(axiom, false, A, B);
        // signature does not intersect
        test(axiom, true, D);
        // include top
        test(DisjointClasses(OWLThing(), A, B), false, A);
    }

    @Test
    void shouldBeLocalowlDisjointUnionAxiom() {
        axiom = DisjointUnion(A, B, C);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        test(axiom, true, D);
        // partition top
        axiom = DisjointUnion(OWLThing(), B, C);
        // signature intersects
        test(axiom, false, B);
        // partition top
        axiom = DisjointUnion(OWLThing(), B, OWLThing());
        // signature intersects
        test(axiom, false, B);
    }

    @Test
    void shouldBeLocalowlEquivalentObjectPropertiesAxiom() {
        axiom = EquivalentObjectProperties(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, R);
        // illegal axiom
        test(EquivalentObjectProperties(Q), true, Q);
    }

    @Test
    void shouldBeLocalowlEquivalentDataPropertiesAxiom() {
        axiom = EquivalentDataProperties(s, dt);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, v);
        // illegal axiom
        test(EquivalentDataProperties(v), true, v);
    }

    @Test
    void shouldBeLocalowlDisjointObjectPropertiesAxiom() {
        axiom = DisjointObjectProperties(P, Q);
        // signature intersects
        test(axiom, true, P);
        test(axiom, false, true, P);
        // signature does not intersect
        test(axiom, false, true, R);
        // top locality sig
        test(DisjointObjectProperties(P, Q), false, true, P);
        // top property
        test(DisjointObjectProperties(P, Q, TopObjectProperty()), false, P);
        // bottom property
        test(DisjointObjectProperties(P, Q, BottomObjectProperty()), true, P);
    }

    @Test
    void shouldBeLocalowlDisjointDataPropertiesAxiom() {
        axiom = DisjointDataProperties(s, dt);
        // signature intersects
        test(axiom, true, s);
        // signature does not intersect
        test(axiom, true, v);
        // top locality
        test(axiom, false, true, P);
        // top property
        test(DisjointDataProperties(TopDataProperty(), s, dt), false, s);
    }

    @Test
    void shouldBeLocalowlSameIndividualAxiom() {
        axiom = SameIndividual(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, indz);
    }

    @Test
    void shouldBeLocalowlDifferentIndividualsAxiom() {
        axiom = DifferentIndividuals(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, indz);
    }

    @Test
    void shouldBeLocalowlInverseObjectPropertiesAxiom() {
        axiom = InverseObjectProperties(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, R);
        // top property
        axiom = InverseObjectProperties(P, TopObjectProperty());
        test(axiom, false, true, P);
        axiom = InverseObjectProperties(TopObjectProperty(), P);
        test(axiom, false, true, P);
    }

    @Test
    void shouldBeLocalowlSubObjectPropertyOfAxiom() {
        axiom = SubObjectPropertyOf(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, R);
        // top property
        axiom = SubObjectPropertyOf(P, TopObjectProperty());
        test(axiom, true, P);
        axiom = SubObjectPropertyOf(TopObjectProperty(), P);
        test(axiom, false, P);
    }

    @Test
    void shouldBeLocalowlSubDataPropertyOfAxiom() {
        axiom = SubDataPropertyOf(s, dt);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, v);
        // top property
        axiom = SubDataPropertyOf(v, TopDataProperty());
        // signature intersects
        test(axiom, true, v);
        axiom = SubDataPropertyOf(TopDataProperty(), v);
        test(axiom, false, v);
    }

    @Test
    void shouldBeLocalowlObjectPropertyDomainAxiom() {
        axiom = ObjectPropertyDomain(P, A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
        // top class
        axiom = ObjectPropertyDomain(P, OWLThing());
        test(axiom, true, P);
        // bottom property
        axiom = ObjectPropertyDomain(BottomObjectProperty(), A);
        test(axiom, true, A);
    }

    @Test
    void shouldBeLocalowlDataPropertyDomainAxiom() {
        axiom = DataPropertyDomain(s, A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
        // top class
        axiom = DataPropertyDomain(v, OWLThing());
        test(axiom, true, v);
        // bottom property
        axiom = DataPropertyDomain(BottomDataProperty(), OWLThing());
        test(axiom, true, A);
    }

    @Test
    void shouldBeLocalowlObjectPropertyRangeAxiom() {
        axiom = ObjectPropertyRange(P, A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
    }

    @Test
    void shouldBeLocalowlDataPropertyRangeAxiom() {
        axiom = DataPropertyRange(s, DT);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, P);
    }

    @Test
    void shouldBeLocalowlTransitiveObjectPropertyAxiom() {
        axiom = TransitiveObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlReflexiveObjectPropertyAxiom() {
        axiom = ReflexiveObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, false, Q);
    }

    @Test
    void shouldBeLocalowlIrreflexiveObjectPropertyAxiom() {
        axiom = IrreflexiveObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlSymmetricObjectPropertyAxiom() {
        axiom = SymmetricObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlAsymmetricObjectPropertyAxiom() {
        axiom = AsymmetricObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlFunctionalObjectPropertyAxiom() {
        axiom = FunctionalObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlFunctionalDataPropertyAxiom() {
        axiom = FunctionalDataProperty(s);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, dt);
    }

    @Test
    void shouldBeLocalowlInverseFunctionalObjectPropertyAxiom() {
        axiom = InverseFunctionalObjectProperty(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlSubClassOfAxiom() {
        axiom = SubClassOf(A, B);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        test(axiom, true, D);
    }

    @Test
    void shouldBeLocalowlClassAssertionAxiom() {
        axiom = ClassAssertion(A, x);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        test(axiom, false, D);
    }

    @Test
    void shouldBeLocalowlObjectPropertyAssertionAxiom() {
        axiom = ObjectPropertyAssertion(P, y, indz);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, false, x);
    }

    @Test
    void shouldBeLocalowlNegativeObjectPropertyAssertionAxiom() {
        axiom = NegativeObjectPropertyAssertion(P, x, y);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, true, indz);
    }

    @Test
    void shouldBeLocalowlDataPropertyAssertionAxiom() {
        axiom = DataPropertyAssertion(s, x, lit1);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, false, P);
    }

    @Test
    void shouldBeLocalowlNegativeDataPropertyAssertionAxiom() {
        axiom = NegativeDataPropertyAssertion(s, x, LIT_TRUE);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, false, P);
    }

    @Test
    void shouldBeLocalowlAnnotationAssertionAxiom() {
        axiom = AnnotationAssertion(g, A.getIRI(), lit1);
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, B);
    }

    @Test
    void shouldBeLocalowlSubAnnotationPropertyOfAxiom() {
        axiom = SubAnnotationPropertyOf(g, h);
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, P);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyDomainAxiom() {
        axiom = AnnotationPropertyDomain(g, A.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyRangeAxiom() {
        axiom = AnnotationPropertyRange(g, A.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    void shouldBeLocalowlSubPropertyChainOfAxiom() {
        axiom = SubPropertyChainOf(l(P, Q), R);
        // signature intersects
        test(axiom, true, P);
        // signature does not intersect
        test(axiom, true, s);
        // signature equals
        test(axiom, false, P, Q, R);
        // top property
        axiom = SubPropertyChainOf(l(P, Q), TopObjectProperty());
        // signature intersects
        test(axiom, true, P);
    }

    @Test
    void shouldBeLocalowlHasKeyAxiom() {
        axiom = HasKey(A, P, s);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlDatatypeDefinitionAxiom() {
        axiom = DatatypeDefinition(DT, DatatypeMinMaxExclusiveRestriction(1, 3));
        // signature intersects
        test(axiom, true, i);
        // signature does not intersect
        test(axiom, true, D);
    }

    @Test
    void shouldBeLocalswrlRule() {
        Set<SWRLAtom> head = new HashSet<>(l(SWRLClassAtom(A, SWRLIndividualArgument(x))));
        Set<SWRLAtom> body = new HashSet<>(l(SWRLClassAtom(B, SWRLIndividualArgument(y))));
        axiom = SWRLRule(head, body);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
    }

    @Test
    void shouldResetSignature() {
        OWLSubClassOfAxiom ax = SubClassOf(A, B);
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
