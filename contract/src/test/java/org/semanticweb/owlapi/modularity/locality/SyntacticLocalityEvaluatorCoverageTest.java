package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Hand-written tests for syntactic BOTTOM and TOP locality.
 *
 * <p>Syntactic locality is a sound but incomplete approximation of semantic
 * locality. The tests are therefore split by what a failure means:</p>
 * <ul>
 * <li>{@code soundness}: every expected {@code false} is an axiom that is
 * semantically NOT local. A failure here is always a bug: the evaluator
 * would drop an axiom from a module that the module needs.</li>
 * <li>{@code completeness}: every expected {@code true} is covered by the
 * standard syntactic locality grammar (Cuenca Grau, Horrocks, Kazakov and
 * Sattler, JAIR 2008), lifted to OWL 2 in the usual way: built-in top and
 * bottom entities, unions via De Morgan, maximum and exact cardinalities,
 * self restrictions, values and data properties. A failure means a missing
 * rule (larger modules), not an incorrect result.</li>
 * </ul>
 *
 * <p>No test expects {@code false} for a semantically local axiom: such
 * "incompleteness pins" would break whenever the evaluator gets smarter.</p>
 */
class SyntacticLocalityEvaluatorCoverageTest {

    private static final SyntacticLocalityEvaluator BOTTOM = SyntacticLocalityEvaluator.BOTTOM;
    private static final SyntacticLocalityEvaluator TOP = SyntacticLocalityEvaluator.TOP;

    private static OWLDataFactory f;
    private static OWLClass a;
    private static OWLClass b;
    private static OWLClass c;
    private static OWLClass d;
    private static OWLObjectProperty r;
    private static OWLObjectProperty s;
    private static OWLObjectProperty t;
    private static OWLDataProperty p;
    private static OWLDataProperty q;
    private static OWLNamedIndividual i;
    private static OWLNamedIndividual j;
    private static OWLLiteral x;

    @BeforeAll
    static void setUp() {
        f = OWLManager.createOWLOntologyManager().getOWLDataFactory();
        a = f.getOWLClass(iri("A"));
        b = f.getOWLClass(iri("B"));
        c = f.getOWLClass(iri("C"));
        d = f.getOWLClass(iri("D"));
        r = f.getOWLObjectProperty(iri("r"));
        s = f.getOWLObjectProperty(iri("s"));
        t = f.getOWLObjectProperty(iri("t"));
        p = f.getOWLDataProperty(iri("p"));
        q = f.getOWLDataProperty(iri("q"));
        i = f.getOWLNamedIndividual(iri("i"));
        j = f.getOWLNamedIndividual(iri("j"));
        x = f.getOWLLiteral("x");
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private static IRI iri(String name) {
        return IRI.create("urn:syntactic-coverage#" + name);
    }

    private static Set<OWLEntity> sig(OWLEntity... entities) {
        return new HashSet<>(Arrays.asList(entities));
    }

    private static void check(SyntacticLocalityEvaluator kind, boolean expected,
                              OWLAxiom axiom, OWLEntity... signature) {
        Set<OWLEntity> sigma = sig(signature);
        assertEquals(expected, kind.isLocal(axiom, sigma),
                () -> kind + ": " + axiom + " signature=" + sigma);
    }

    private static void both(boolean expected, OWLAxiom axiom, OWLEntity... signature) {
        check(BOTTOM, expected, axiom, signature);
        check(TOP, expected, axiom, signature);
    }

    private static OWLAxiom sub(OWLClassExpression subClass, OWLClassExpression superClass) {
        return f.getOWLSubClassOfAxiom(subClass, superClass);
    }

    private static OWLAxiom disjoint(OWLClassExpression... operands) {
        return f.getOWLDisjointClassesAxiom(operands);
    }

    private static OWLAxiom equivalent(OWLClassExpression... operands) {
        return f.getOWLEquivalentClassesAxiom(operands);
    }

    private static OWLClassExpression not(OWLClassExpression operand) {
        return f.getOWLObjectComplementOf(operand);
    }

    private static OWLClassExpression and(OWLClassExpression... operands) {
        return f.getOWLObjectIntersectionOf(operands);
    }

    private static OWLClassExpression or(OWLClassExpression... operands) {
        return f.getOWLObjectUnionOf(operands);
    }

    private static OWLClassExpression some(OWLObjectPropertyExpression role,
                                           OWLClassExpression filler) {
        return f.getOWLObjectSomeValuesFrom(role, filler);
    }

    private static OWLClassExpression all(OWLObjectPropertyExpression role,
                                          OWLClassExpression filler) {
        return f.getOWLObjectAllValuesFrom(role, filler);
    }

    private static OWLClassExpression min(int n, OWLObjectPropertyExpression role,
                                          OWLClassExpression filler) {
        return f.getOWLObjectMinCardinality(n, role, filler);
    }

    private static OWLClassExpression max(int n, OWLObjectPropertyExpression role,
                                          OWLClassExpression filler) {
        return f.getOWLObjectMaxCardinality(n, role, filler);
    }

    private static OWLClassExpression exact(int n, OWLObjectPropertyExpression role,
                                            OWLClassExpression filler) {
        return f.getOWLObjectExactCardinality(n, role, filler);
    }

    private static OWLClass thing() {
        return f.getOWLThing();
    }

    private static OWLClass nothing() {
        return f.getOWLNothing();
    }

    // ------------------------------------------------------------------
    // Non-logical axioms
    // ------------------------------------------------------------------

    @Test @Tag("completeness")
    void nonLogicalAxiomsAreAlwaysLocal() {
        OWLAnnotationProperty label = f.getRDFSLabel();
        OWLAnnotationProperty note = f.getOWLAnnotationProperty(iri("note"));
        List<OWLAxiom> axioms = List.of(
                f.getOWLDeclarationAxiom(a),
                f.getOWLDeclarationAxiom(r),
                f.getOWLAnnotationAssertionAxiom(label, a.getIRI(), f.getOWLLiteral("A")),
                f.getOWLSubAnnotationPropertyOfAxiom(note, label),
                f.getOWLAnnotationPropertyDomainAxiom(note, iri("target")),
                f.getOWLAnnotationPropertyRangeAxiom(note, iri("target")));
        for (OWLAxiom axiom : axioms) {
            both(true, axiom);
            both(true, axiom, a, b, r, p, i);
        }
    }

    // ------------------------------------------------------------------
    // Completeness: BOTTOM grammar (absent symbols are empty)
    // ------------------------------------------------------------------

    @Test @Tag("completeness")
    void bottomRecognisesBottomEquivalentClassExpressions() {
        // Sigma = {A}: B, C, r, p are absent, hence empty.
        List<OWLClassExpression> bottomEquivalent = List.of(
                b,
                nothing(),
                not(thing()),
                not(not(b)),
                and(a, b),
                or(b, c),
                some(r, a),
                some(r.getInverseProperty(), a),
                min(1, r, a),
                min(2, r, a),
                exact(1, r, a),
                f.getOWLObjectHasSelf(r),
                f.getOWLObjectHasValue(r, i),
                f.getOWLDataSomeValuesFrom(p, f.getTopDatatype()),
                f.getOWLDataMinCardinality(1, p),
                f.getOWLDataExactCardinality(1, p),
                f.getOWLDataHasValue(p, x));
        for (OWLClassExpression expression : bottomEquivalent) {
            check(BOTTOM, true, sub(expression, a), a);
            check(BOTTOM, true, disjoint(expression, a), a);
        }
    }

    @Test @Tag("completeness")
    void bottomRecognisesTopEquivalentClassExpressions() {
        // Sigma = {A}: complements of empty expressions and restrictions
        // over an empty role that are vacuously satisfied.
        List<OWLClassExpression> topEquivalent = List.of(
                thing(),
                not(b),
                not(nothing()),
                or(a, not(b)),
                and(not(b), not(c)),
                not(some(r, a)),
                all(r, a),
                max(1, r, a),
                f.getOWLDataAllValuesFrom(p, f.getStringOWLDatatype()),
                f.getOWLDataMaxCardinality(1, p));
        for (OWLClassExpression expression : topEquivalent) {
            check(BOTTOM, true, sub(a, expression), a);
        }
    }

    @Test @Tag("completeness")
    void bottomRecognisesZeroCardinalitiesOverEmptyRoles() {
        // >= 0 and = 0 over an empty role denote Thing; <= 0 over an empty
        // role is vacuously true as well.
        for (OWLClassExpression expression : List.of(
                min(0, r, a), exact(0, r, a), max(0, r, a),
                f.getOWLDataMinCardinality(0, p),
                f.getOWLDataExactCardinality(0, p),
                f.getOWLDataMaxCardinality(0, p))) {
            check(BOTTOM, true, sub(a, expression), a);
        }
    }

    @Test @Tag("completeness")
    void bottomHandlesRetainedRolesWithAbsentFillers() {
        // Sigma = {A, r}: the role is kept, but B is empty.
        for (OWLClassExpression expression : List.of(
                some(r, b), min(1, r, b), min(2, r, b), exact(1, r, b))) {
            check(BOTTOM, true, sub(expression, a), a, r);
        }
        for (OWLClassExpression expression : List.of(
                all(r, not(b)), max(0, r, b), max(1, r, b))) {
            check(BOTTOM, true, sub(a, expression), a, r);
        }
    }

    @Test @Tag("completeness")
    void bottomRecognisesLocalClassAxioms() {
        check(BOTTOM, true, equivalent(b, and(c, a)), a);
        check(BOTTOM, true, equivalent(b, c, d));
        // At most one operand that is not bottom-equivalent.
        check(BOTTOM, true, disjoint(b, c, a), a);
        check(BOTTOM, true, f.getOWLDisjointUnionAxiom(b, Set.of(c, d)));
        check(BOTTOM, true, f.getOWLClassAssertionAxiom(not(b), i));
        check(BOTTOM, true, f.getOWLHasKeyAxiom(b, Set.of(r)));
    }

    @Test @Tag("completeness")
    void bottomRecognisesLocalPropertyAxioms() {
        check(BOTTOM, true, f.getOWLSubObjectPropertyOfAxiom(r, s), s);
        check(BOTTOM, true, f.getOWLSubObjectPropertyOfAxiom(r.getInverseProperty(), s), s);
        check(BOTTOM, true, f.getOWLSubPropertyChainOfAxiom(List.of(r, s), t), s, t);
        check(BOTTOM, true, f.getOWLSubDataPropertyOfAxiom(p, q), q);
        check(BOTTOM, true, f.getOWLEquivalentObjectPropertiesAxiom(r, s));
        check(BOTTOM, true, f.getOWLEquivalentDataPropertiesAxiom(p, q));
        check(BOTTOM, true, f.getOWLDisjointObjectPropertiesAxiom(r, s), s);
        check(BOTTOM, true, f.getOWLDisjointDataPropertiesAxiom(p, q), q);
        check(BOTTOM, true, f.getOWLInverseObjectPropertiesAxiom(r, s));
        check(BOTTOM, true, f.getOWLObjectPropertyDomainAxiom(r, a), a);
        check(BOTTOM, true, f.getOWLObjectPropertyRangeAxiom(r, a), a);
        check(BOTTOM, true, f.getOWLDataPropertyDomainAxiom(p, a), a);
        check(BOTTOM, true, f.getOWLDataPropertyRangeAxiom(p, f.getStringOWLDatatype()));
        // Domain with a top-equivalent class is local even for a kept role.
        check(BOTTOM, true, f.getOWLObjectPropertyDomainAxiom(r, not(b)), r);
        for (OWLAxiom characteristic : List.of(
                f.getOWLFunctionalObjectPropertyAxiom(r),
                f.getOWLInverseFunctionalObjectPropertyAxiom(r),
                f.getOWLSymmetricObjectPropertyAxiom(r),
                f.getOWLTransitiveObjectPropertyAxiom(r),
                f.getOWLIrreflexiveObjectPropertyAxiom(r),
                f.getOWLAsymmetricObjectPropertyAxiom(r),
                f.getOWLFunctionalDataPropertyAxiom(p))) {
            check(BOTTOM, true, characteristic);
        }
        check(BOTTOM, true, f.getOWLNegativeObjectPropertyAssertionAxiom(r, i, j));
        check(BOTTOM, true, f.getOWLNegativeDataPropertyAssertionAxiom(p, i, x));
    }

    // ------------------------------------------------------------------
    // Completeness: TOP grammar (absent symbols are universal)
    // ------------------------------------------------------------------

    @Test @Tag("completeness")
    void topRecognisesTopEquivalentClassExpressions() {
        // Sigma = {A}: B, C are Thing; r, p are universal. The domain is
        // nonempty, so one universal-role successor always exists.
        List<OWLClassExpression> topEquivalent = List.of(
                b,
                thing(),
                not(nothing()),
                not(not(b)),
                or(a, b),
                and(b, c),
                some(r, b),
                some(r, thing()),
                some(r.getInverseProperty(), b),
                min(1, r, b),
                f.getOWLObjectHasSelf(r),
                f.getOWLObjectHasValue(r, i),
                f.getOWLDataSomeValuesFrom(p, f.getTopDatatype()),
                f.getOWLDataMinCardinality(1, p),
                f.getOWLDataHasValue(p, x));
        for (OWLClassExpression expression : topEquivalent) {
            check(TOP, true, sub(a, expression), a);
        }
    }

    @Test @Tag("completeness")
    void topRecognisesBottomEquivalentClassExpressions() {
        List<OWLClassExpression> bottomEquivalent = List.of(
                nothing(),
                not(b),
                not(thing()),
                and(a, not(b)),
                or(not(b), not(c)),
                all(r, not(b)),
                max(0, r, b),
                f.getOWLDataMaxCardinality(0, p));
        for (OWLClassExpression expression : bottomEquivalent) {
            check(TOP, true, sub(expression, a), a);
            check(TOP, true, disjoint(expression, a), a);
        }
    }

    @Test @Tag("completeness")
    void topHandlesRetainedRolesWithAbsentFillers() {
        // Sigma = {A, r}: the role is kept, but B is Thing.
        for (OWLClassExpression expression : List.of(
                all(r, b), max(1, r, not(b)), max(0, r, not(b)))) {
            check(TOP, true, sub(a, expression), a, r);
        }
        for (OWLClassExpression expression : List.of(
                some(r, not(b)), min(1, r, not(b)), exact(1, r, not(b)))) {
            check(TOP, true, sub(expression, a), a, r);
        }
    }

    @Test @Tag("completeness")
    void topRecognisesLocalClassAxioms() {
        check(TOP, true, equivalent(b, or(c, a)), a);
        check(TOP, true, equivalent(b, c, d));
        check(TOP, true, disjoint(not(b), not(c), a), a);
        check(TOP, true, f.getOWLClassAssertionAxiom(b, i));
        check(TOP, true, f.getOWLClassAssertionAxiom(some(r, b), i));
    }

    @Test @Tag("completeness")
    void topRecognisesLocalPropertyAxioms() {
        check(TOP, true, f.getOWLSubObjectPropertyOfAxiom(r, s), r);
        check(TOP, true, f.getOWLSubPropertyChainOfAxiom(List.of(r, s), t), r, s);
        check(TOP, true, f.getOWLSubDataPropertyOfAxiom(p, q), p);
        check(TOP, true, f.getOWLEquivalentObjectPropertiesAxiom(r, s));
        check(TOP, true, f.getOWLEquivalentDataPropertiesAxiom(p, q));
        check(TOP, true, f.getOWLInverseObjectPropertiesAxiom(r, s));
        check(TOP, true, f.getOWLObjectPropertyDomainAxiom(r, b), r);
        check(TOP, true, f.getOWLObjectPropertyRangeAxiom(r, b), r);
        check(TOP, true, f.getOWLDataPropertyDomainAxiom(p, b), p);
        check(TOP, true, f.getOWLDataPropertyRangeAxiom(p, f.getTopDatatype()), p);
        for (OWLAxiom characteristic : List.of(
                f.getOWLReflexiveObjectPropertyAxiom(r),
                f.getOWLSymmetricObjectPropertyAxiom(r),
                f.getOWLTransitiveObjectPropertyAxiom(r))) {
            check(TOP, true, characteristic);
        }
        check(TOP, true, f.getOWLObjectPropertyAssertionAxiom(r, i, j));
        check(TOP, true, f.getOWLDataPropertyAssertionAxiom(p, i, x));
    }

    @Test @Tag("completeness")
    void syntacticTautologiesAreLocalForTheFullSignature() {
        // No replacement happens; only built-in constants make these local.
        both(true, sub(a, thing()), a);
        both(true, sub(nothing(), a), a);
        both(true, disjoint(a, nothing()), a);
        both(true, f.getOWLClassAssertionAxiom(thing(), i));
    }

    // ------------------------------------------------------------------
    // Soundness: every expectation below is semantically NOT local
    // ------------------------------------------------------------------

    @Test @Tag("soundness")
    void builtInClassesAreNeverReplaced() {
        // owl:Thing is not empty in BOTTOM, owl:Nothing not full in TOP.
        check(BOTTOM, false, disjoint(thing(), a), a);
        check(BOTTOM, false, sub(thing(), a), a);
        check(TOP, false, equivalent(nothing(), a), a);
        check(TOP, false, sub(a, nothing()), a);
    }

    @Test @Tag("soundness")
    void builtInPropertiesAreNeverReplaced() {
        check(BOTTOM, false, sub(some(f.getOWLTopObjectProperty(), thing()), a), a);
        check(BOTTOM, false, sub(f.getOWLDataSomeValuesFrom(
                f.getOWLTopDataProperty(), f.getTopDatatype()), a), a);
        check(TOP, false, sub(a, some(f.getOWLBottomObjectProperty(), thing())), a);
        check(TOP, false, sub(a, f.getOWLDataSomeValuesFrom(
                f.getOWLBottomDataProperty(), f.getTopDatatype())), a);
        check(TOP, false, f.getOWLObjectPropertyAssertionAxiom(
                f.getOWLBottomObjectProperty(), i, j));
        check(BOTTOM, false, f.getOWLNegativeObjectPropertyAssertionAxiom(
                f.getOWLTopObjectProperty(), i, j));
    }

    @Test @Tag("soundness")
    void nominalsAreNeitherEmptyNorFull() {
        check(BOTTOM, false, sub(f.getOWLObjectOneOf(i), a), a);
        check(BOTTOM, false, disjoint(f.getOWLObjectOneOf(i), a), a);
        check(TOP, false, sub(thing(), f.getOWLObjectOneOf(i)));
        check(TOP, false, equivalent(f.getOWLObjectOneOf(i), thing()));
        both(false, disjoint(f.getOWLObjectOneOf(i), f.getOWLObjectOneOf(j)));
    }

    @Test @Tag("soundness")
    void restrictionsOnRetainedRolesAreNotConstants() {
        check(BOTTOM, false, disjoint(f.getOWLObjectHasSelf(r), a), r, a);
        check(BOTTOM, false, disjoint(f.getOWLObjectHasValue(r, i), a), r, a);
        check(BOTTOM, false, disjoint(f.getOWLDataHasValue(p, x), a), p, a);
        check(TOP, false, equivalent(f.getOWLObjectHasSelf(r), thing()), r);
        check(TOP, false, equivalent(f.getOWLObjectHasValue(r, i), thing()), r);
        check(TOP, false, equivalent(f.getOWLDataHasValue(p, x), thing()), p);
    }

    @Test @Tag("soundness")
    void bottomZeroAndMaxCardinalitiesAreTopNotBottom() {
        // Over an empty role these denote Thing, so "X SubClassOf A" fails.
        for (OWLClassExpression expression : List.of(
                min(0, r, b), exact(0, r, b), max(1, r, b), all(r, a),
                f.getOWLDataMinCardinality(0, p),
                f.getOWLDataExactCardinality(0, p),
                f.getOWLDataMaxCardinality(1, p),
                f.getOWLDataAllValuesFrom(p, f.getTopDatatype()))) {
            check(BOTTOM, false, sub(expression, a), a);
        }
    }

    @Test @Tag("soundness")
    void topCountingOverTheUniversalRoleDependsOnTheDomainSize() {
        // >= 2, <= 1 and = 1 over U depend on |Delta|; <= 0 over U is
        // Nothing. None of these are Thing in every interpretation.
        for (OWLClassExpression expression : List.of(
                min(2, r, b), max(1, r, b), exact(1, r, b), max(0, r, b),
                f.getOWLDataMaxCardinality(0, p))) {
            check(TOP, false, sub(a, expression), a);
        }
        // >= 2 over U is not Nothing either.
        check(TOP, false, sub(min(2, r, b), a), a);
    }

    @Test @Tag("soundness")
    void universalRoleRestrictionsWithRetainedFillersAreNotConstants() {
        // Sigma = {A}, r universal: some(U, A) iff A nonempty; all(U, A)
        // iff A = Thing.
        check(TOP, false, sub(a, all(r, a)), a);
        check(TOP, false, sub(some(r, a), a), a);
        check(TOP, false, sub(some(r.getInverseProperty(), a), a), a);
        check(BOTTOM, false, sub(all(r, a), a), a);
        check(BOTTOM, false, sub(a, some(r.getInverseProperty(), a)), a);
    }

    @Test @Tag("soundness")
    void topDataRestrictionsRespectTheDataDomain() {
        OWLDataRange single = f.getOWLDataOneOf(f.getOWLLiteral("a"));
        OWLDataRange empty = f.getOWLDatatypeMinMaxInclusiveRestriction(5, 3);
        // A one-element range has no second value; an empty range has none.
        check(TOP, false, sub(thing(), f.getOWLDataMinCardinality(2, p, single)));
        check(TOP, false, sub(thing(), f.getOWLDataSomeValuesFrom(p, empty)));
        // The universal data role has non-string values.
        check(TOP, false, sub(a, f.getOWLDataAllValuesFrom(p, f.getStringOWLDatatype())), a);
        check(TOP, false, f.getOWLDataPropertyRangeAxiom(p, f.getStringOWLDatatype()));
        check(TOP, false, f.getOWLDataPropertyRangeAxiom(p, single));
        check(TOP, false, f.getOWLFunctionalDataPropertyAxiom(p));
    }

    @Test @Tag("soundness")
    void disjointnessCollisionsAreNotTautologies() {
        // Operands that collapse onto each other keep their constraint.
        check(TOP, false, disjoint(b, c));
        check(BOTTOM, false, disjoint(not(b), not(c)));
        both(false, disjoint(a, or(a, b)), a);
        check(TOP, false, disjoint(a, and(a, b)), a);
        check(TOP, false, disjoint(a, and(a, b), not(a)), a);
        both(false, disjoint(a, b, c, d), a, b);
        check(TOP, false, disjoint(a, b), a);
    }

    @Test @Tag("soundness")
    void equivalenceCollisionsAreNotTautologies() {
        both(false, equivalent(a, b), a);
        both(false, equivalent(a, b, c), a);
        both(false, equivalent(a, b, c, d), a, b);
        both(false, equivalent(a, and(a, b), or(a, b)), a);
        check(TOP, false, equivalent(a, or(a, b), and(a, not(b))), a);
    }

    @Test @Tag("soundness")
    void disjointUnionKeepsBothOfItsParts() {
        // TOP: Thing = Thing u Thing holds, but the parts overlap.
        check(TOP, false, f.getOWLDisjointUnionAxiom(a, Set.of(b, c)));
        // BOTTOM with A retained: A = Nothing does not hold.
        check(BOTTOM, false, f.getOWLDisjointUnionAxiom(a, Set.of(b, c)), a);
    }

    @Test @Tag("soundness")
    void propertyInclusionsDistinguishSubAndSuperProperty() {
        check(BOTTOM, false, f.getOWLSubObjectPropertyOfAxiom(r, s), r);
        check(TOP, false, f.getOWLSubObjectPropertyOfAxiom(r, s), s);
        check(BOTTOM, false, f.getOWLSubDataPropertyOfAxiom(p, q), p);
        check(TOP, false, f.getOWLSubDataPropertyOfAxiom(p, q), q);
        check(BOTTOM, false, f.getOWLSubPropertyChainOfAxiom(List.of(r, s), t), r, s);
        // U o s SubPropertyOf t: t may be empty while s is not.
        check(TOP, false, f.getOWLSubPropertyChainOfAxiom(List.of(r, s), t), s, t);
        both(false, f.getOWLEquivalentObjectPropertiesAxiom(r, s), r);
        both(false, f.getOWLInverseObjectPropertiesAxiom(r, s), r);
    }

    @Test @Tag("soundness")
    void propertyDisjointnessWithUniversalRoles() {
        check(TOP, false, f.getOWLDisjointObjectPropertiesAxiom(r, s));
        check(TOP, false, f.getOWLDisjointObjectPropertiesAxiom(r, s), r);
        check(TOP, false, f.getOWLDisjointDataPropertiesAxiom(p, q));
        both(false, f.getOWLDisjointObjectPropertiesAxiom(r, s), r, s);
    }

    @Test @Tag("soundness")
    void domainAndRangeDependOnTheMode() {
        check(TOP, false, f.getOWLObjectPropertyDomainAxiom(r, a), a);
        check(TOP, false, f.getOWLObjectPropertyRangeAxiom(r, a), a);
        check(TOP, false, f.getOWLDataPropertyDomainAxiom(p, a), a);
        check(BOTTOM, false, f.getOWLObjectPropertyDomainAxiom(r, b), r);
        check(BOTTOM, false, f.getOWLObjectPropertyRangeAxiom(r, b), r);
        check(BOTTOM, false, f.getOWLDataPropertyDomainAxiom(p, b), p);
        both(false, f.getOWLObjectPropertyDomainAxiom(r, a), r, a);
    }

    @Test @Tag("soundness")
    void roleCharacteristicsDistinguishEmptyAndUniversalRoles() {
        check(BOTTOM, false, f.getOWLReflexiveObjectPropertyAxiom(r));
        for (OWLAxiom axiom : List.of(
                f.getOWLIrreflexiveObjectPropertyAxiom(r),
                f.getOWLAsymmetricObjectPropertyAxiom(r),
                f.getOWLFunctionalObjectPropertyAxiom(r),
                f.getOWLInverseFunctionalObjectPropertyAxiom(r))) {
            check(TOP, false, axiom);
        }
        for (OWLAxiom axiom : List.of(
                f.getOWLReflexiveObjectPropertyAxiom(r),
                f.getOWLIrreflexiveObjectPropertyAxiom(r),
                f.getOWLAsymmetricObjectPropertyAxiom(r),
                f.getOWLSymmetricObjectPropertyAxiom(r),
                f.getOWLTransitiveObjectPropertyAxiom(r),
                f.getOWLFunctionalObjectPropertyAxiom(r))) {
            both(false, axiom, r);
        }
    }

    @Test @Tag("soundness")
    void assertionsAreDualBetweenTheModes() {
        check(BOTTOM, false, f.getOWLClassAssertionAxiom(b, i));
        check(TOP, false, f.getOWLClassAssertionAxiom(not(b), i));
        check(BOTTOM, false, f.getOWLObjectPropertyAssertionAxiom(r, i, j));
        check(BOTTOM, false, f.getOWLObjectPropertyAssertionAxiom(r.getInverseProperty(), i, j));
        check(TOP, false, f.getOWLNegativeObjectPropertyAssertionAxiom(r, i, j));
        check(BOTTOM, false, f.getOWLDataPropertyAssertionAxiom(p, i, x));
        check(TOP, false, f.getOWLNegativeDataPropertyAssertionAxiom(p, i, x));
        both(false, f.getOWLClassAssertionAxiom(a, i), a);
        both(false, f.getOWLObjectPropertyAssertionAxiom(r, i, j), r);
    }

    @Test @Tag("soundness")
    void individualEqualityIsNeverLocal() {
        both(false, f.getOWLSameIndividualAxiom(i, j));
        both(false, f.getOWLDifferentIndividualsAxiom(i, j));
        both(false, f.getOWLSameIndividualAxiom(i, j), a, r, p);
    }

    // ------------------------------------------------------------------
    // Signature handling
    // ------------------------------------------------------------------

    @Test @Tag("signature")
    void signatureMembershipIsByEntityNotByIri() {
        // A class punned with r's IRI does not retain the property r.
        OWLClass classR = f.getOWLClass(r.getIRI());
        OWLAxiom someR = sub(some(r, thing()), a);
        check(BOTTOM, true, someR, a, classR);
        check(BOTTOM, false, someR, a, r);
        // And the property r does not retain the class with the same IRI.
        check(BOTTOM, true, disjoint(classR, a), a, r);
        check(TOP, false, disjoint(classR, a), a, r);
    }

    @Test @Tag("signature")
    void irrelevantAndBuiltInSignatureMembersDoNotChangeResults() {
        OWLEntity[] minimal = { a };
        OWLEntity[] extended = { a, c, d, s, q, i, thing(), nothing(),
                f.getOWLTopObjectProperty(), f.getOWLBottomObjectProperty(),
                f.getOWLTopDataProperty(), f.getOWLBottomDataProperty() };
        for (OWLEntity[] signature : List.of(minimal, extended)) {
            check(BOTTOM, true, disjoint(a, b), signature);
            check(TOP, false, disjoint(a, b), signature);
            check(BOTTOM, false, sub(a, some(r, thing())), signature);
            check(TOP, true, sub(a, some(r, thing())), signature);
        }
    }

    @Test @Tag("signature")
    void theCallersSignatureIsNotModified() {
        Set<OWLEntity> signature = sig(a, r);
        OWLAxiom axiom = sub(and(a, some(r, b)), c);
        BOTTOM.isLocal(axiom, signature);
        TOP.isLocal(axiom, signature);
        assertEquals(sig(a, r), signature);
        // An unmodifiable view also catches transient mutation.
        BOTTOM.isLocal(axiom, Collections.unmodifiableSet(signature));
        TOP.isLocal(axiom, Collections.unmodifiableSet(signature));
    }

    @Test @Tag("signature")
    void annotationsOnAnAxiomDoNotChangeItsLocality() {
        var annotation = f.getOWLAnnotation(f.getRDFSComment(), f.getOWLLiteral("note"));
        OWLAxiom plain = disjoint(a, b);
        OWLAxiom annotated = plain.getAnnotatedAxiom(Set.of(annotation));
        check(BOTTOM, true, annotated, a);
        check(TOP, false, annotated, a);
        both(false, annotated, a, b);
    }
}