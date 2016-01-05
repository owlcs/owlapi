package org.semanticweb.owlapitools.decomposition.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.Signature;
import org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker;

@SuppressWarnings("javadoc")
public class SyntacticLocalityTestCase {

    @Test
    public void shouldBeLocalowlDeclarationAxiom() {
        // declare a
        axiom = df.getOWLDeclarationAxiom(a);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, b);
    }

    @Test
    public void shouldBeLocalowlEquivalentClassesAxiom() {
        axiom = df.getOWLEquivalentClassesAxiom(a, b);
        // signature intersects
        test(axiom, false, a);
        // signature does not intersect
        test(axiom, true, c);
        // illegal axiom
        test(df.getOWLEquivalentClassesAxiom(a), true, a);
        // include bottom
        test(df.getOWLEquivalentClassesAxiom(owlNothing, a, b), false, a);
        // include top
        test(df.getOWLEquivalentClassesAxiom(owlThing, a, b), false, a);
        // include bottom and top
        test(df.getOWLEquivalentClassesAxiom(owlNothing, owlThing, a, b), false, a);
    }

    @Test
    public void shouldBeLocalowlDisjointClassesAxiom() {
        axiom = df.getOWLDisjointClassesAxiom(a, b);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, c);
        axiom = df.getOWLDisjointClassesAxiom(a, b, c);
        // signature intersects
        test(axiom, false, a, b);
        // signature does not intersect
        test(axiom, true, d);
        // include top
        test(df.getOWLDisjointClassesAxiom(owlThing, a, b), false, a);
    }

    @Test
    public void shouldBeLocalowlDisjointUnionAxiom() {
        axiom = disjointUnion(a, b, c);
        // signature intersects
        test(axiom, false, a);
        // signature does not intersect
        test(axiom, true, d);
        // partition top
        axiom = disjointUnion(owlThing, b, c);
        // signature intersects
        test(axiom, false, b);
        // partition top
        axiom = disjointUnion(owlThing, b, owlThing);
        // signature intersects
        test(axiom, false, b);
    }

    /** @return disjoint union of superclass and classes */
    private OWLDisjointUnionAxiom disjointUnion(OWLClass superclass, OWLClass... classes) {
        return df.getOWLDisjointUnionAxiom(superclass, new HashSet<OWLClassExpression>(Arrays.asList(classes)));
    }

    @Test
    public void shouldBeLocalowlEquivalentObjectPropertiesAxiom() {
        axiom = df.getOWLEquivalentObjectPropertiesAxiom(p, q);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, r);
        // illegal axiom
        test(df.getOWLEquivalentObjectPropertiesAxiom(q), true, q);
    }

    @Test
    public void shouldBeLocalowlEquivalentDataPropertiesAxiom() {
        axiom = df.getOWLEquivalentDataPropertiesAxiom(s, t);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, v);
        // illegal axiom
        test(df.getOWLEquivalentDataPropertiesAxiom(v), true, v);
    }

    @Test
    public void shouldBeLocalowlDisjointObjectPropertiesAxiom() {
        axiom = df.getOWLDisjointObjectPropertiesAxiom(p, q);
        // signature intersects
        test(axiom, true, p);
        test(axiom, false, true, p);
        // signature does not intersect
        test(axiom, false, true, r);
        // top locality sig
        test(df.getOWLDisjointObjectPropertiesAxiom(p, q), false, true, p);
        // top property
        test(df.getOWLDisjointObjectPropertiesAxiom(p, q, topObject), false, p);
        // bottom property
        test(df.getOWLDisjointObjectPropertiesAxiom(p, q, bottomObject), true, p);
    }

    @Test
    public void shouldBeLocalowlDisjointDataPropertiesAxiom() {
        axiom = df.getOWLDisjointDataPropertiesAxiom(s, t);
        // signature intersects
        test(axiom, true, s);
        // signature does not intersect
        test(axiom, true, v);
        // top locality
        test(axiom, false, true, p);
        // top property
        test(df.getOWLDisjointDataPropertiesAxiom(topData, s, t), false, s);
    }

    @Test
    public void shouldBeLocalowlSameIndividualAxiom() {
        axiom = df.getOWLSameIndividualAxiom(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, z);
    }

    @Test
    public void shouldBeLocalowlDifferentIndividualsAxiom() {
        axiom = df.getOWLDifferentIndividualsAxiom(x, y);
        // signature intersects
        test(axiom, false, x);
        // signature does not intersect
        test(axiom, false, z);
    }

    @Test
    public void shouldBeLocalowlInverseObjectPropertiesAxiom() {
        axiom = df.getOWLInverseObjectPropertiesAxiom(p, q);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, r);
        // top property
        axiom = df.getOWLInverseObjectPropertiesAxiom(p, topObject);
        test(axiom, false, true, p);
        axiom = df.getOWLInverseObjectPropertiesAxiom(topObject, p);
        test(axiom, false, true, p);
    }

    @Test
    public void shouldBeLocalowlSubObjectPropertyOfAxiom() {
        axiom = df.getOWLSubObjectPropertyOfAxiom(p, q);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, r);
        // top property
        axiom = df.getOWLSubObjectPropertyOfAxiom(p, topObject);
        test(axiom, true, p);
        axiom = df.getOWLSubObjectPropertyOfAxiom(topObject, p);
        test(axiom, false, p);
    }

    @Test
    public void shouldBeLocalowlSubDataPropertyOfAxiom() {
        axiom = df.getOWLSubDataPropertyOfAxiom(s, t);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, v);
        // top property
        axiom = df.getOWLSubDataPropertyOfAxiom(v, topData);
        // signature intersects
        test(axiom, true, v);
        axiom = df.getOWLSubDataPropertyOfAxiom(topData, v);
        test(axiom, false, v);
    }

    @Test
    public void shouldBeLocalowlObjectPropertyDomainAxiom() {
        axiom = df.getOWLObjectPropertyDomainAxiom(p, a);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, d);
        // top class
        axiom = df.getOWLObjectPropertyDomainAxiom(p, owlThing);
        test(axiom, true, p);
        // bottom property
        axiom = df.getOWLObjectPropertyDomainAxiom(bottomObject, a);
        test(axiom, true, a);
    }

    @Test
    public void shouldBeLocalowlDataPropertyDomainAxiom() {
        axiom = df.getOWLDataPropertyDomainAxiom(s, a);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, d);
        // top class
        axiom = df.getOWLDataPropertyDomainAxiom(v, owlThing);
        test(axiom, true, v);
        // bottom property
        axiom = df.getOWLDataPropertyDomainAxiom(bottomData, owlThing);
        test(axiom, true, a);
    }

    @Test
    public void shouldBeLocalowlObjectPropertyRangeAxiom() {
        axiom = df.getOWLObjectPropertyRangeAxiom(p, a);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, d);
    }

    @Test
    public void shouldBeLocalowlDataPropertyRangeAxiom() {
        axiom = df.getOWLDataPropertyRangeAxiom(s, i);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, p);
    }

    @Test
    public void shouldBeLocalowlTransitiveObjectPropertyAxiom() {
        axiom = df.getOWLTransitiveObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlReflexiveObjectPropertyAxiom() {
        axiom = df.getOWLReflexiveObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, false, q);
    }

    @Test
    public void shouldBeLocalowlIrreflexiveObjectPropertyAxiom() {
        axiom = df.getOWLIrreflexiveObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlSymmetricObjectPropertyAxiom() {
        axiom = df.getOWLSymmetricObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlAsymmetricObjectPropertyAxiom() {
        axiom = df.getOWLAsymmetricObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlFunctionalObjectPropertyAxiom() {
        axiom = df.getOWLFunctionalObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlFunctionalDataPropertyAxiom() {
        axiom = df.getOWLFunctionalDataPropertyAxiom(s);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, t);
    }

    @Test
    public void shouldBeLocalowlInverseFunctionalObjectPropertyAxiom() {
        axiom = df.getOWLInverseFunctionalObjectPropertyAxiom(p);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlSubClassOfAxiom() {
        axiom = df.getOWLSubClassOfAxiom(a, b);
        // signature intersects
        test(axiom, false, a);
        // signature does not intersect
        test(axiom, true, d);
    }

    @Test
    public void shouldBeLocalowlClassAssertionAxiom() {
        axiom = df.getOWLClassAssertionAxiom(a, x);
        // signature intersects
        test(axiom, false, a);
        // signature does not intersect
        test(axiom, false, d);
    }

    @Test
    public void shouldBeLocalowlObjectPropertyAssertionAxiom() {
        axiom = df.getOWLObjectPropertyAssertionAxiom(p, y, z);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, false, x);
    }

    @Test
    public void shouldBeLocalowlNegativeObjectPropertyAssertionAxiom() {
        axiom = df.getOWLNegativeObjectPropertyAssertionAxiom(p, x, y);
        // signature intersects
        test(axiom, false, p);
        // signature does not intersect
        test(axiom, true, z);
    }

    @Test
    public void shouldBeLocalowlDataPropertyAssertionAxiom() {
        axiom = df.getOWLDataPropertyAssertionAxiom(s, x, l);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, false, p);
    }

    @Test
    public void shouldBeLocalowlNegativeDataPropertyAssertionAxiom() {
        axiom = df.getOWLNegativeDataPropertyAssertionAxiom(s, x, j);
        // signature intersects
        test(axiom, false, s);
        // signature does not intersect
        test(axiom, true, p);
    }

    @Test
    public void shouldBeLocalowlAnnotationAssertionAxiom() {
        axiom = df.getOWLAnnotationAssertionAxiom(a.getIRI(), df.getOWLAnnotation(g, l));
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, b);
    }

    @Test
    public void shouldBeLocalowlSubAnnotationPropertyOfAxiom() {
        axiom = df.getOWLSubAnnotationPropertyOfAxiom(g, h);
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, p);
    }

    @Test
    public void shouldBeLocalowlAnnotationPropertyDomainAxiom() {
        axiom = df.getOWLAnnotationPropertyDomainAxiom(g, a.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    public void shouldBeLocalowlAnnotationPropertyRangeAxiom() {
        axiom = df.getOWLAnnotationPropertyRangeAxiom(g, a.getIRI());
        // signature intersects
        test(axiom, true, g);
        // signature does not intersect
        test(axiom, true, h);
    }

    @Test
    public void shouldBeLocalowlSubPropertyChainOfAxiom() {
        axiom = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(p, q), r);
        // signature intersects
        test(axiom, true, p);
        // signature does not intersect
        test(axiom, true, s);
        // signature equals
        test(axiom, false, p, q, r);
        // top property
        axiom = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(p, q), topObject);
        // signature intersects
        test(axiom, true, p);
    }

    @Test
    public void shouldBeLocalowlHasKeyAxiom() {
        axiom = df.getOWLHasKeyAxiom(a, p, s);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, q);
    }

    @Test
    public void shouldBeLocalowlDatatypeDefinitionAxiom() {
        axiom = df.getOWLDatatypeDefinitionAxiom(i, df.getOWLDatatypeMinMaxExclusiveRestriction(1, 3));
        // signature intersects
        test(axiom, true, i);
        // signature does not intersect
        test(axiom, true, d);
    }

    @Test
    public void shouldBeLocalswrlRule() {
        Set<SWRLAtom> head = new HashSet<>(Arrays.asList(df.getSWRLClassAtom(a, df.getSWRLIndividualArgument(x))));
        Set<SWRLAtom> body = new HashSet<>(Arrays.asList(df.getSWRLClassAtom(b, df.getSWRLIndividualArgument(y))));
        axiom = df.getSWRLRule(head, body);
        // signature intersects
        test(axiom, true, a);
        // signature does not intersect
        test(axiom, true, d);
    }

    @Test
    public void shouldResetSignature() {
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(a, b);
        testSubject.preprocessOntology(Arrays.asList(new AxiomWrapper(ax)));
        assertEquals(asSet(ax.signature()), testSubject.getSignature().getSignature());
    }

    private OWLAxiom axiom;
    private OWLDataFactory df = OWLManager.getOWLDataFactory();
    private OWLClass a = df.getOWLClass(IRI.create("urn:test#a"));
    private OWLClass b = df.getOWLClass(IRI.create("urn:test#b"));
    private OWLClass c = df.getOWLClass(IRI.create("urn:test#c"));
    private OWLClass d = df.getOWLClass(IRI.create("urn:test#d"));
    private OWLAnnotationProperty g = df.getOWLAnnotationProperty(IRI.create("urn:test#g"));
    private OWLAnnotationProperty h = df.getOWLAnnotationProperty(IRI.create("urn:test#h"));
    private OWLDatatype i = df.getOWLDatatype(IRI.create("urn:test#i"));
    private OWLLiteral j = df.getOWLLiteral(true);
    private OWLLiteral l = df.getOWLLiteral(3.5D);
    private OWLObjectProperty p = df.getOWLObjectProperty(IRI.create("urn:test#p"));
    private OWLObjectProperty q = df.getOWLObjectProperty(IRI.create("urn:test#q"));
    private OWLObjectProperty r = df.getOWLObjectProperty(IRI.create("urn:test#r"));
    private OWLDataProperty s = df.getOWLDataProperty(IRI.create("urn:test#s"));
    private OWLDataProperty t = df.getOWLDataProperty(IRI.create("urn:test#t"));
    private OWLDataProperty v = df.getOWLDataProperty(IRI.create("urn:test#v"));
    private OWLNamedIndividual x = df.getOWLNamedIndividual(IRI.create("urn:test#x"));
    private OWLNamedIndividual y = df.getOWLNamedIndividual(IRI.create("urn:test#y"));
    private OWLNamedIndividual z = df.getOWLNamedIndividual(IRI.create("urn:test#z"));
    private OWLClass owlNothing = df.getOWLNothing();
    private OWLClass owlThing = df.getOWLThing();
    private OWLDataProperty bottomData = df.getOWLBottomDataProperty();
    private OWLDataProperty topData = df.getOWLTopDataProperty();
    private OWLObjectProperty bottomObject = df.getOWLBottomObjectProperty();
    private OWLObjectProperty topObject = df.getOWLTopObjectProperty();
    private SyntacticLocalityChecker testSubject;

    @Before
    public void setUp() {
        testSubject = new SyntacticLocalityChecker();
    }

    private void set(OWLEntity... entities) {
        testSubject.setSignatureValue(new Signature(Stream.of(entities)));
    }

    private void test(OWLAxiom ax, boolean expected, OWLEntity... entities) {
        set(entities);
        boolean local = testSubject.local(ax);
        assertEquals(expected, local);
    }

    private void test(OWLAxiom ax, boolean expected, boolean locality, OWLEntity... entities) {
        set(entities);
        testSubject.getSignature().setLocality(locality);
        boolean local = testSubject.local(ax);
        assertEquals(expected, local);
    }
}
