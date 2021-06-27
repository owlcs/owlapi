package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.SemanticLocalityChecker;

@Disabled
@SuppressWarnings("boxing")
class SemanticLocalityTestCase extends TestBase {

    private OWLAxiom axiom;
    private SemanticLocalityChecker testSubject;

    @Test
    void shouldBeLocalowlDeclarationAxiom() {
        // declare a
        axiom = df.getOWLDeclarationAxiom(A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, B);
    }

    @Test
    void shouldBeLocalowlEquivalentClassesAxiom() {
        axiom = df.getOWLEquivalentClassesAxiom(A, B);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        // test(axiom, true, c);
        // illegal axiom
        test(df.getOWLEquivalentClassesAxiom(A), true, A);
        // include bottom
        test(df.getOWLEquivalentClassesAxiom(owlNothing, A, B), false, A);
        // include top
        test(df.getOWLEquivalentClassesAxiom(owlThing, A, B), false, A);
        // include bottom and top
        test(df.getOWLEquivalentClassesAxiom(owlNothing, owlThing, A, B), false, A);
    }

    @Test
    void shouldBeLocalowlDisjointClassesAxiom() {
        axiom = df.getOWLDisjointClassesAxiom(A, B);
        // signature intersects
        // test(axiom, true, a);
        // signature does not intersect
        // test(axiom, true, c);
        axiom = df.getOWLDisjointClassesAxiom(A, B, C);
        // signature intersects
        test(axiom, false, A, B);
        // signature does not intersect
        // test(axiom, true, d);
        // include top
        test(df.getOWLDisjointClassesAxiom(owlThing, A, B), false, A);
    }

    @Test
    void shouldBeLocalowlDisjointUnionAxiom() {
        axiom = disjointUnion(A, B, C);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        // test(axiom, true, d);
        // partition top
        axiom = disjointUnion(owlThing, B, C);
        // signature intersects
        test(axiom, false, B);
        // partition top
        axiom = disjointUnion(owlThing, B, owlThing);
        // signature intersects
        test(axiom, false, B);
    }

    /** @return disjoint union of superclass and classes */
    private OWLDisjointUnionAxiom disjointUnion(OWLClass superclass, OWLClass... classes) {
        return df.getOWLDisjointUnionAxiom(superclass,
            new HashSet<OWLClassExpression>(Arrays.asList(classes)));
    }

    @Test
    void shouldBeLocalowlEquivalentObjectPropertiesAxiom() {
        axiom = df.getOWLEquivalentObjectPropertiesAxiom(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, r);
        // illegal axiom
        test(df.getOWLEquivalentObjectPropertiesAxiom(Q), true, Q);
    }

    @Test
    void shouldBeLocalowlEquivalentDataPropertiesAxiom() {
        axiom = df.getOWLEquivalentDataPropertiesAxiom(s, t);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        // test(axiom, true, v);
        // illegal axiom
        test(df.getOWLEquivalentDataPropertiesAxiom(v), true, v);
    }

    @Test
    void shouldBeLocalowlDisjointObjectPropertiesAxiom() {
        axiom = df.getOWLDisjointObjectPropertiesAxiom(P, Q);
        // signature intersects
        // test(axiom, true, p);
        test(axiom, false, true, P);
        // signature does not intersect
        test(axiom, false, true, R);
        // top locality sig
        test(df.getOWLDisjointObjectPropertiesAxiom(P, Q), false, true, P);
        // top property
        test(df.getOWLDisjointObjectPropertiesAxiom(P, Q, topObject), false, P);
        // bottom property
        // test(df.getOWLDisjointObjectPropertiesAxiom(p, q, bottomObject),
        // true, p);
    }

    @Test
    void shouldBeLocalowlDisjointDataPropertiesAxiom() {
        axiom = df.getOWLDisjointDataPropertiesAxiom(s, t);
        // signature intersects
        // test(axiom, true, s);
        // signature does not intersect
        // test(axiom, true, v);
        // top locality
        test(axiom, false, true, P);
        // top property
        test(df.getOWLDisjointDataPropertiesAxiom(topData, s, t), false, s);
    }

    @Test
    void shouldBeLocalowlSameIndividualAxiom() {
        axiom = df.getOWLSameIndividualAxiom(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, z);
    }

    @Test
    void shouldBeLocalowlDifferentIndividualsAxiom() {
        axiom = df.getOWLDifferentIndividualsAxiom(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, z);
    }

    @Test
    void shouldBeLocalowlInverseObjectPropertiesAxiom() {
        axiom = df.getOWLInverseObjectPropertiesAxiom(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, r);
        // top property
        axiom = df.getOWLInverseObjectPropertiesAxiom(P, topObject);
        // test(axiom, false, true, p);
        axiom = df.getOWLInverseObjectPropertiesAxiom(topObject, P);
        test(axiom, false, true, P);
    }

    @Test
    void shouldBeLocalowlSubObjectPropertyOfAxiom() {
        axiom = df.getOWLSubObjectPropertyOfAxiom(P, Q);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, r);
        // top property
        axiom = df.getOWLSubObjectPropertyOfAxiom(P, topObject);
        test(axiom, true, P);
        axiom = df.getOWLSubObjectPropertyOfAxiom(topObject, P);
        test(axiom, false, P);
    }

    @Test
    void shouldBeLocalowlSubDataPropertyOfAxiom() {
        axiom = df.getOWLSubDataPropertyOfAxiom(s, t);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        // test(axiom, true, v);
        // top property
        axiom = df.getOWLSubDataPropertyOfAxiom(v, topData);
        // signature intersects
        test(axiom, true, v);
        axiom = df.getOWLSubDataPropertyOfAxiom(topData, v);
        test(axiom, false, v);
    }

    @Test
    void shouldBeLocalowlObjectPropertyDomainAxiom() {
        axiom = df.getOWLObjectPropertyDomainAxiom(P, A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
        // top class
        axiom = df.getOWLObjectPropertyDomainAxiom(P, owlThing);
        test(axiom, true, P);
        // bottom property
        axiom = df.getOWLObjectPropertyDomainAxiom(bottomObject, A);
        test(axiom, true, A);
    }

    @Test
    void shouldBeLocalowlDataPropertyDomainAxiom() {
        axiom = df.getOWLDataPropertyDomainAxiom(s, A);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
        // top class
        axiom = df.getOWLDataPropertyDomainAxiom(v, owlThing);
        test(axiom, true, v);
        // bottom property
        axiom = df.getOWLDataPropertyDomainAxiom(bottomData, owlThing);
        test(axiom, true, A);
    }

    @Test
    void shouldBeLocalowlObjectPropertyRangeAxiom() {
        axiom = df.getOWLObjectPropertyRangeAxiom(P, A);
        // signature intersects
        // test(axiom, true, a);
        // signature does not intersect
        // test(axiom, true, d);
    }

    @Test
    void shouldBeLocalowlDataPropertyRangeAxiom() {
        axiom = df.getOWLDataPropertyRangeAxiom(s, i);
        // signature intersects
        // test(axiom, false, s);
        // signature does not intersect
        // test(axiom, true, p);
    }

    @Test
    void shouldBeLocalowlTransitiveObjectPropertyAxiom() {
        axiom = df.getOWLTransitiveObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlReflexiveObjectPropertyAxiom() {
        axiom = df.getOWLReflexiveObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, false, Q);
    }

    @Test
    void shouldBeLocalowlIrreflexiveObjectPropertyAxiom() {
        axiom = df.getOWLIrreflexiveObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlSymmetricObjectPropertyAxiom() {
        axiom = df.getOWLSymmetricObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlAsymmetricObjectPropertyAxiom() {
        axiom = df.getOWLAsymmetricObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlFunctionalObjectPropertyAxiom() {
        axiom = df.getOWLFunctionalObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlFunctionalDataPropertyAxiom() {
        axiom = df.getOWLFunctionalDataPropertyAxiom(s);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        // test(axiom, true, t);
    }

    @Test
    void shouldBeLocalowlInverseFunctionalObjectPropertyAxiom() {
        axiom = df.getOWLInverseFunctionalObjectPropertyAxiom(P);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, q);
    }

    @Test
    void shouldBeLocalowlSubClassOfAxiom() {
        axiom = df.getOWLSubClassOfAxiom(A, B);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        // test(axiom, true, d);
    }

    @Test
    void shouldBeLocalowlClassAssertionAxiom() {
        axiom = df.getOWLClassAssertionAxiom(A, x);
        // signature intersects
        test(axiom, false, A);
        // signature does not intersect
        test(axiom, false, D);
    }

    @Test
    void shouldBeLocalowlObjectPropertyAssertionAxiom() {
        axiom = df.getOWLObjectPropertyAssertionAxiom(P, y, z);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        test(axiom, false, x);
    }

    @Test
    void shouldBeLocalowlNegativeObjectPropertyAssertionAxiom() {
        axiom = df.getOWLNegativeObjectPropertyAssertionAxiom(P, x, y);
        // signature intersects
        test(axiom, false, P);
        // signature does not intersect
        // test(axiom, true, z);
    }

    @Test
    void shouldBeLocalowlDataPropertyAssertionAxiom() {
        axiom = df.getOWLDataPropertyAssertionAxiom(s, x, l);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, false, P);
    }

    @Test
    void shouldBeLocalowlNegativeDataPropertyAssertionAxiom() {
        axiom = df.getOWLNegativeDataPropertyAssertionAxiom(s, x, j);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        // test(axiom, true, p);
    }

    @Test
    void shouldBeLocalowlAnnotationAssertionAxiom() {
        axiom = df.getOWLAnnotationAssertionAxiom(A.getIRI(), df.getOWLAnnotation(g, l));
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, B);
    }

    @Test
    void shouldBeLocalowlSubAnnotationPropertyOfAxiom() {
        axiom = df.getOWLSubAnnotationPropertyOfAxiom(g, h);
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, P);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyDomainAxiom() {
        axiom = df.getOWLAnnotationPropertyDomainAxiom(g, A.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    void shouldBeLocalowlAnnotationPropertyRangeAxiom() {
        axiom = df.getOWLAnnotationPropertyRangeAxiom(g, A.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    void shouldBeLocalowlSubPropertyChainOfAxiom() {
        axiom = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(P, Q), R);
        // signature intersects
        // test(axiom, true, p);
        // signature does not intersect
        // test(axiom, true, s);
        // signature equals
        test(axiom, false, P, Q, R);
        // top property
        axiom = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(P, Q), topObject);
        // signature intersects
        test(axiom, true, P);
    }

    @Test
    void shouldBeLocalowlHasKeyAxiom() {
        axiom = df.getOWLHasKeyAxiom(A, P, s);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, Q);
    }

    @Test
    void shouldBeLocalowlDatatypeDefinitionAxiom() {
        axiom =
            df.getOWLDatatypeDefinitionAxiom(i, df.getOWLDatatypeMinMaxExclusiveRestriction(1, 3));
        // signature intersects
        // test(axiom, true, i);
        // signature does not intersect
        // test(axiom, true, d);
    }

    @Test
    void shouldBeLocalswrlRule() {
        Set<SWRLAtom> head =
            new HashSet<>(Arrays.asList(df.getSWRLClassAtom(A, df.getSWRLIndividualArgument(x))));
        Set<SWRLAtom> body =
            new HashSet<>(Arrays.asList(df.getSWRLClassAtom(B, df.getSWRLIndividualArgument(y))));
        axiom = df.getSWRLRule(head, body);
        // signature intersects
        test(axiom, true, A);
        // signature does not intersect
        test(axiom, true, D);
    }

    @Test
    void shouldResetSignature() {
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(A, B);
        testSubject.preprocessOntology(Arrays.asList(new AxiomWrapper(ax)));
    }

    static final String NS = "urn:test#";
    static final OWLAnnotationProperty g = df.getOWLAnnotationProperty(iri(NS, "g"));
    static final OWLAnnotationProperty h = df.getOWLAnnotationProperty(iri(NS, "h"));
    static final OWLDatatype i = df.getOWLDatatype(iri(NS, "i"));
    static final OWLLiteral j = df.getOWLLiteral(true);
    static final OWLLiteral l = df.getOWLLiteral(3.5D);
    static final OWLDataProperty s = df.getOWLDataProperty(iri(NS, "s"));
    static final OWLDataProperty t = df.getOWLDataProperty(iri(NS, "t"));
    static final OWLDataProperty v = df.getOWLDataProperty(iri(NS, "v"));
    static final OWLNamedIndividual x = df.getOWLNamedIndividual(iri(NS, "x"));
    static final OWLNamedIndividual y = df.getOWLNamedIndividual(iri(NS, "y"));
    static final OWLNamedIndividual z = df.getOWLNamedIndividual(iri(NS, "z"));
    static final OWLClass owlNothing = df.getOWLNothing();
    static final OWLClass owlThing = df.getOWLThing();
    static final OWLDataProperty bottomData = df.getOWLBottomDataProperty();
    static final OWLDataProperty topData = df.getOWLTopDataProperty();
    static final OWLObjectProperty bottomObject = df.getOWLBottomObjectProperty();
    static final OWLObjectProperty topObject = df.getOWLTopObjectProperty();

    @BeforeEach
    void setUp() {
        // XXX add a reasoner factory
        testSubject = new SemanticLocalityChecker(new StructuralReasonerFactory(),
            OWLManager.createOWLOntologyManager());
    }

    private void set(OWLEntity... entities) {
        testSubject.getSignature().addAll(Stream.of(entities));
    }

    private void test(OWLAxiom ax, boolean expected, OWLEntity... entities) {
        testSubject.preprocessOntology(Arrays.asList(new AxiomWrapper(ax)));
        set(entities);
        boolean local = testSubject.local(ax);
        assertEquals(Boolean.valueOf(expected), Boolean.valueOf(local));
    }

    private void test(OWLAxiom ax, boolean expected, boolean locality, OWLEntity... entities) {
        testSubject.preprocessOntology(Arrays.asList(new AxiomWrapper(ax)));
        set(entities);
        testSubject.getSignature().setLocality(locality);
        boolean local = testSubject.local(ax);
        assertEquals(Boolean.valueOf(expected), Boolean.valueOf(local));
    }
}
