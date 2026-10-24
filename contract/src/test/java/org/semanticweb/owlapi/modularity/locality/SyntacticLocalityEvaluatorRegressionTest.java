package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/** Focused semantic-soundness regressions for the syntactic locality checker. */
class SyntacticLocalityEvaluatorRegressionTest {
    private static OWLDataFactory f;
    private static OWLClass a;
    private static OWLClass b;
    private static OWLClass c;
    private static OWLObjectProperty r;
    private static OWLObjectProperty s;
    private static OWLDataProperty p;

    @BeforeAll
    static void setup() {
        f = OWLManager.createOWLOntologyManager().getOWLDataFactory();
        a = f.getOWLClass(IRI.create("urn:syntactic-regression#A"));
        b = f.getOWLClass(IRI.create("urn:syntactic-regression#B"));
        c = f.getOWLClass(IRI.create("urn:syntactic-regression#C"));
        r = f.getOWLObjectProperty(IRI.create("urn:syntactic-regression#r"));
        s = f.getOWLObjectProperty(IRI.create("urn:syntactic-regression#s"));
        p = f.getOWLDataProperty(IRI.create("urn:syntactic-regression#p"));
    }

    private static void check(SyntacticLocalityEvaluator kind, boolean expected,
                              OWLAxiom axiom, OWLEntity... signature) {
        Set<OWLEntity> sig = new HashSet<>(Arrays.asList(signature));
        assertEquals(expected, kind.isLocal(axiom, sig),
            () -> kind + ": " + axiom + " signature=" + sig);
    }

    @Test
    void intersectionMustNotBecomeBottomWhenBothConjunctsAreUnknown() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectIntersectionOf(a, b), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.BOTTOM, false, ax, a, b);
        check(SyntacticLocalityEvaluator.TOP, false, ax, a, b);
    }

    @Test
    void unionIsBottomOnlyWhenAllOperandsAreBottom() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectUnionOf(f.getOWLNothing(), a), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.BOTTOM, false, ax, a);
        check(SyntacticLocalityEvaluator.TOP, false, ax, a);
    }

    @Test
    void bottomDoesNotMistakePositiveDataMinForTop() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLThing(), f.getOWLDataMinCardinality(1, p));
        check(SyntacticLocalityEvaluator.BOTTOM, false, ax);
    }

    @Test
    void bottomDataSomeIsBottomForMissingProperty() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLDataSomeValuesFrom(p, f.getTopDatatype()), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.BOTTOM, true, ax);
    }

    @Test
    void topObjectMinimumTwoIsNotUniversallyTop() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLThing(), f.getOWLObjectMinCardinality(2, r));
        check(SyntacticLocalityEvaluator.TOP, false, ax);
    }

    @Test
    void topObjectMaximumOneIsNotUniversallyBottom() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectMaxCardinality(1, r), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.TOP, false, ax);
    }

    @Test
    void topObjectMaximumZeroIsUniversallyBottom() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectMaxCardinality(0, r), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.TOP, true, ax);
    }

    @Test
    void exactOneUniversalObjectRoleIsNotAlwaysBottom() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectExactCardinality(1, r), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.TOP, false, ax);
    }

    @Test
    void complexTopDataRangeMustNotBeMistakenForProperRange() {
        var string = f.getStringOWLDatatype();
        var allData = f.getOWLDataUnionOf(string, f.getOWLDataComplementOf(string));
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLDataAllValuesFrom(p, allData), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.TOP, false, ax);
    }

    @Test
    void booleanDataRangeHasExactlyTwoValuesUnderUniversalDataRole() {
        var bool = f.getBooleanOWLDatatype();
        OWLAxiom maxOneImpliesBottom = f.getOWLSubClassOfAxiom(
            f.getOWLDataMaxCardinality(1, p, bool), f.getOWLNothing());
        OWLAxiom maxTwoImpliesBottom = f.getOWLSubClassOfAxiom(
            f.getOWLDataMaxCardinality(2, p, bool), f.getOWLNothing());
        check(SyntacticLocalityEvaluator.TOP, true, maxOneImpliesBottom);
        check(SyntacticLocalityEvaluator.TOP, false, maxTwoImpliesBottom);
    }

    @Test
    void inversePropertiesMustCountAsRetainedInDisjointness() {
        OWLAxiom ax = f.getOWLDisjointObjectPropertiesAxiom(
            r.getInverseProperty(), s.getInverseProperty());
        check(SyntacticLocalityEvaluator.BOTTOM, false, ax, r, s);
    }

    @Test
    void builtInTopRoleIsNotReplacedByBottomRole() {
        OWLAxiom ax = f.getOWLSubClassOfAxiom(
            f.getOWLObjectSomeValuesFrom(f.getOWLTopObjectProperty(), f.getOWLThing()),
            f.getOWLNothing());
        check(SyntacticLocalityEvaluator.BOTTOM, false, ax);
    }

    @Test
    void topDisjointUnionNeedsExactlyOneTopRightHandSide() {
        OWLAxiom ax = f.getOWLDisjointUnionAxiom(a, java.util.Arrays.asList(b, c));
        check(SyntacticLocalityEvaluator.BOTTOM, true, ax);
        check(SyntacticLocalityEvaluator.TOP, false, ax);
    }

    @Test
    void positiveAndNegativePropertyAssertionsRespectBuiltInRoles() {
        var x = f.getOWLNamedIndividual(IRI.create("urn:syntactic-regression#x"));
        var y = f.getOWLNamedIndividual(IRI.create("urn:syntactic-regression#y"));
        check(SyntacticLocalityEvaluator.TOP, true,
            f.getOWLObjectPropertyAssertionAxiom(r, x, y));
        check(SyntacticLocalityEvaluator.BOTTOM, true,
            f.getOWLNegativeObjectPropertyAssertionAxiom(r, x, y));
        check(SyntacticLocalityEvaluator.TOP, false,
            f.getOWLObjectPropertyAssertionAxiom(f.getOWLBottomObjectProperty(), x, y));
    }
}
