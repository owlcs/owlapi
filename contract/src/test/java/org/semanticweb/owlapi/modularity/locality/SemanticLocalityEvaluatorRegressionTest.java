package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Semantic BOTTOM and TOP locality tests: bug regressions and preservation checks.
 *
 * <p>These intentionally assert the CORRECT semantic results, rather than the
 * implementation's results. The original regression and preservation checks
 * are intentionally retained during the transition to proxy-based rewriting.
 * This class covers structural rewriting and operand collisions; the separate
 * SemanticLocalityEvaluatorCoverageTest tests axiom families, nested TOP proxies,
 * datatype ranges, unsupported inputs, and resource ownership.</p>
 *
 * <p>Requires JUnit 5 and a complete OWL 2 DL reasoner such as HermiT.
 * Do not replace HermiT with the incomplete StructuralReasoner.</p>
 */
@Execution(ExecutionMode.SAME_THREAD)
class SemanticLocalityEvaluatorRegressionTest {

    private static OWLOntologyManager manager;
    private static OWLDataFactory df;
    private static SemanticLocalityEvaluator bottom;
    private static SemanticLocalityEvaluator top;

    private static OWLClass a;
    private static OWLClass b;
    private static OWLClass c;
    private static OWLClass d;
    private static OWLObjectProperty r;
    private static OWLDataProperty p;
    private static OWLNamedIndividual i;

    @BeforeAll
    static void setUp() {
        manager = OWLManager.createOWLOntologyManager();
        df = manager.getOWLDataFactory();
        a = df.getOWLClass(IRI.create("urn:semantic-locality:test#A"));
        b = df.getOWLClass(IRI.create("urn:semantic-locality:test#B"));
        c = df.getOWLClass(IRI.create("urn:semantic-locality:test#C"));
        d = df.getOWLClass(IRI.create("urn:semantic-locality:test#D"));
        r = df.getOWLObjectProperty(IRI.create("urn:semantic-locality:test#r"));
        p = df.getOWLDataProperty(IRI.create("urn:semantic-locality:test#p"));
        i = df.getOWLNamedIndividual(IRI.create("urn:semantic-locality:test#i"));

        bottom = new SemanticLocalityEvaluator(LocalityClass.BOTTOM, manager,
                new ReasonerFactory());
        top = new SemanticLocalityEvaluator(LocalityClass.TOP, manager,
                new ReasonerFactory());
    }

    @AfterAll
    static void tearDown() {
        if (bottom != null) {
            bottom.dispose();
        }
        if (top != null) {
            top.dispose();
        }
        // Both shared empty ontologies must have been removed from the
        // caller-owned manager by their respective evaluators.
        assertEquals(0, manager.getOntologies().size());
    }

    private static Set<OWLEntity> sig(OWLEntity... entities) {
        return new HashSet<>(Arrays.asList(entities));
    }

    private static void check(LocalityClass kind, boolean expected, OWLAxiom axiom,
                              OWLEntity... signature) {
        SemanticLocalityEvaluator evaluator = kind == LocalityClass.BOTTOM ? bottom : top;
        int ontologyCount = manager.getOntologies().size();
        assertEquals(expected, evaluator.isLocal(axiom, sig(signature)),
                () -> kind + " locality of " + axiom + " wrt " + sig(signature));
        assertEquals(ontologyCount, manager.getOntologies().size(),
                "A locality query leaked an auxiliary ontology");
        // The evaluators' shared ontologies must stay empty; an axiom left
        // behind would silently change the answers of later queries.
        manager.getOntologies().forEach(o -> assertEquals(0, o.getAxiomCount(),
                () -> "Axioms leaked into " + o.getOntologyID() + " after " + axiom));
    }

    private static void checkBoth(boolean expected, OWLAxiom axiom,
                                  OWLEntity... signature) {
        check(LocalityClass.BOTTOM, expected, axiom, signature);
        check(LocalityClass.TOP, expected, axiom, signature);
    }

    // --- Axiom reconstruction (both modes) ---

    @Tag("regression")
    @Test
    void subClassOfMustNotReverseItsOperands() {
        OWLClassExpression aOrB = df.getOWLObjectUnionOf(a, b);
        // A subset-of (A union B) is valid; the converse is not.
        checkBoth(true, df.getOWLSubClassOfAxiom(a, aOrB), a, b);
        checkBoth(false, df.getOWLSubClassOfAxiom(aOrB, a), a, b);
        // For Sigma={A}, B is Bottom in one mode and Top in the other.
        check(LocalityClass.BOTTOM, false, df.getOWLSubClassOfAxiom(a, b), a);
        check(LocalityClass.TOP, true, df.getOWLSubClassOfAxiom(a, b), a);
    }

    @Tag("regression")
    @Test
    void trivialDisjointnessAndEquivalenceRemainLocal() {
        checkBoth(true, df.getOWLDisjointClassesAxiom(a, df.getOWLNothing()), a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                a, df.getOWLObjectComplementOf(df.getOWLObjectComplementOf(a))), a);
        checkBoth(false, df.getOWLDisjointClassesAxiom(a, b), a, b);
    }

    @Tag("regression")
    @Test
    void equivalentClassesOutsideSignatureAreLocalInBothModes() {
        // Equivalence of two missing names becomes Nothing=Nothing or Thing=Thing.
        // This also checks that the implementation handles merged operands.
        checkBoth(true, df.getOWLEquivalentClassesAxiom(a, b));
    }

    @Tag("regression")
    @Test
    void disjointClassesOutsideSignatureDifferBetweenModes() {
        // Disjoint(Nothing, Nothing) is true; Disjoint(Thing, Thing) is false.
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(a, b));
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(a, b));
    }

    @Tag("regression")
    @Test
    void nonLogicalAxiomsAreLocal() {
        checkBoth(true, df.getOWLDeclarationAxiom(a));
    }

    // --- owl:Thing and owl:Nothing must not be substituted ---

    @Tag("regression")
    @Test
    void bottomPreservesOwlThing() {
        // Disjoint(Thing, A) is not universally true even if Thing is not in Sigma.
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLThing(), a), a);
    }

    @Tag("regression")
    @Test
    void topPreservesOwlNothing() {
        // Disjoint(Nothing, A) is always true even if Nothing is not in Sigma.
        check(LocalityClass.TOP, true,
                df.getOWLDisjointClassesAxiom(df.getOWLNothing(), a), a);
    }

    // --- Class expressions whose properties ARE in Sigma ---

    @Tag("regression")
    @Test
    void bottomPreservesObjectHasSelfWhenRoleIsInSignature() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectHasSelf(r), a), r, a);
    }

    @Tag("regression")
    @Test
    void topPreservesObjectHasSelfWhenRoleIsInSignature() {
        check(LocalityClass.TOP, false,
                df.getOWLEquivalentClassesAxiom(df.getOWLObjectHasSelf(r), df.getOWLThing()), r);
    }

    @Tag("regression")
    @Test
    void bottomPreservesObjectHasValueWhenRoleIsInSignature() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectHasValue(r, i), a), r, a);
    }

    @Tag("regression")
    @Test
    void topPreservesObjectHasValueWhenRoleIsInSignature() {
        check(LocalityClass.TOP, false,
                df.getOWLEquivalentClassesAxiom(df.getOWLObjectHasValue(r, i), df.getOWLThing()), r);
    }

    @Tag("regression")
    @Test
    void bottomPreservesDataHasValueWhenPropertyIsInSignature() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLDataHasValue(p, df.getOWLLiteral("x")), a), p, a);
    }

    @Tag("regression")
    @Test
    void topPreservesDataHasValueWhenPropertyIsInSignature() {
        check(LocalityClass.TOP, false,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLDataHasValue(p, df.getOWLLiteral("x")), df.getOWLThing()), p);
    }

    // --- Nominals are not interchangeable with empty/full classes ---

    @Tag("regression")
    @Test
    void bottomDoesNotReplaceNominalsWithNothing() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectOneOf(i), a), a);
    }

    @Tag("regression")
    @Test
    void topDoesNotReplaceNominalsWithThing() {
        check(LocalityClass.TOP, false,
                df.getOWLEquivalentClassesAxiom(df.getOWLObjectOneOf(i), df.getOWLThing()));
    }

    // --- Cardinality zero: min(0) and exact(0) are Thing for an empty role ---

    @Tag("regression")
    @Test
    void bottomHandlesZeroObjectCardinalities() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectMinCardinality(0, r), a), a);
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectExactCardinality(0, r), a), a);
    }

    @Tag("regression")
    @Test
    void bottomHandlesZeroDataCardinalities() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLDataMinCardinality(0, p), a), a);
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(df.getOWLDataExactCardinality(0, p), a), a);
    }

    // --- Recursive transformation of cardinality fillers ---

    @Tag("regression")
    @Test
    void bottomReplacesFillersInsideObjectCardinalities() {
        // r in Sigma, B not in Sigma: (>= 1 r.B) becomes (>= 1 r.Nothing) = Nothing.
        check(LocalityClass.BOTTOM, true,
                df.getOWLDisjointClassesAxiom(df.getOWLObjectMinCardinality(1, r, b), a), r, a);
    }

    @Tag("regression")
    @Test
    void topReplacesFillersInsideObjectCardinalities() {
        // r in Sigma, B not in Sigma: (>= 1 r.B) becomes (>= 1 r.Thing).
        check(LocalityClass.TOP, true,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLObjectMinCardinality(1, r, b),
                        df.getOWLObjectMinCardinality(1, r, df.getOWLThing())), r);
    }

    @Tag("regression")
    @Test
    void bottomReplacesFillersInsideMaxAndExactCardinalities() {
        // For B outside Sigma, both restrictions with B become restrictions with Nothing.
        check(LocalityClass.BOTTOM, true,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLObjectMaxCardinality(0, r, b),
                        df.getOWLObjectMaxCardinality(0, r, df.getOWLNothing())), r);
        check(LocalityClass.BOTTOM, true,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLObjectExactCardinality(1, r, b),
                        df.getOWLObjectExactCardinality(1, r, df.getOWLNothing())), r);
    }



    @Tag("regression")
    @Test
    void topReplacesFillersInsideMaxAndExactCardinalities() {
        // For B outside Sigma, both restrictions with B become restrictions with Thing.
        check(LocalityClass.TOP, true,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLObjectMaxCardinality(0, r, b),
                        df.getOWLObjectMaxCardinality(0, r, df.getOWLThing())), r);
        check(LocalityClass.TOP, true,
                df.getOWLEquivalentClassesAxiom(
                        df.getOWLObjectExactCardinality(1, r, b),
                        df.getOWLObjectExactCardinality(1, r, df.getOWLThing())), r);
    }

    // --- TOP locality: universal role does NOT imply constant class results ---
    //
    // The next five tests use restrictions whose value is fixed by the filler
    // alone (e.g. all(r, Thing) = Thing for EVERY r). They catch shortcuts that
    // map a restriction to a constant purely because its role is absent, and
    // they exercise the reasoner pipeline on tautologies. They cannot detect a
    // wrong interpretation of the role; topUniversalRoleIsGlobal* below can.

    @Tag("regression")
    @Test
    void topAllValuesFromWithThingFillerIsThing() {
        // With r outside Sigma, all(U, Thing) = Thing, not Nothing.
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectAllValuesFrom(r, df.getOWLThing()), a), a);
    }

    @Tag("regression")
    @Test
    void topSomeValuesFromWithNothingFillerIsNothing() {
        // With r outside Sigma, some(U, Nothing) = Nothing, not Thing.
        check(LocalityClass.TOP, true,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectSomeValuesFrom(r, df.getOWLNothing()), a), a);
    }

    @Tag("regression")
    @Test
    void topObjectMaxCardinalityDependsOnFiller() {
        // At most one universal-role successor in Nothing is always satisfied.
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectMaxCardinality(1, r, df.getOWLNothing()), a), a);
    }

    @Tag("regression")
    @Test
    void topObjectExactCardinalityDependsOnFiller() {
        // Exactly one universal-role successor in Nothing is impossible.
        check(LocalityClass.TOP, true,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectExactCardinality(1, r, df.getOWLNothing()), a), a);
    }

    @Tag("regression")
    @Test
    void topDataAllValuesFromTopDatatypeIsThing() {
        // Every data-property value is an instance of rdfs:Literal.
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLDataAllValuesFrom(p, df.getTopDatatype()), a), a);
    }

    @Tag("regression")
    @Test
    void topUniversalRoleIsGlobalForExistentialRestrictions() {
        // Sigma={A}, r absent. some(U, A) is Thing iff A is nonempty.
        // A SubClassOf some(U, A): every A-instance is its own U-witness.
        check(LocalityClass.TOP, true,
                df.getOWLSubClassOfAxiom(a, df.getOWLObjectSomeValuesFrom(r, a)), a);
        // some(U, A) SubClassOf A would require A = Thing whenever A is nonempty.
        check(LocalityClass.TOP, false,
                df.getOWLSubClassOfAxiom(df.getOWLObjectSomeValuesFrom(r, a), a), a);
        // BOTTOM is the mirror image: some(empty, A) = Nothing.
        check(LocalityClass.BOTTOM, false,
                df.getOWLSubClassOfAxiom(a, df.getOWLObjectSomeValuesFrom(r, a)), a);
        check(LocalityClass.BOTTOM, true,
                df.getOWLSubClassOfAxiom(df.getOWLObjectSomeValuesFrom(r, a), a), a);
    }

    @Tag("regression")
    @Test
    void topUniversalRoleIsGlobalForUniversalRestrictions() {
        // Sigma={A}, r absent. all(U, A) is Thing iff A = Thing, else Nothing.
        check(LocalityClass.TOP, true,
                df.getOWLSubClassOfAxiom(df.getOWLObjectAllValuesFrom(r, a), a), a);
        // all(U, A) nonempty forces A = Thing, so some(U, A) = Thing.
        check(LocalityClass.TOP, true, df.getOWLSubClassOfAxiom(
                df.getOWLObjectAllValuesFrom(r, a),
                df.getOWLObjectSomeValuesFrom(r, a)), a);
        check(LocalityClass.TOP, false,
                df.getOWLSubClassOfAxiom(a, df.getOWLObjectAllValuesFrom(r, a)), a);
        // BOTTOM: all(empty, A) = Thing and some(empty, A) = Nothing.
        check(LocalityClass.BOTTOM, false,
                df.getOWLSubClassOfAxiom(df.getOWLObjectAllValuesFrom(r, a), a), a);
        check(LocalityClass.BOTTOM, false, df.getOWLSubClassOfAxiom(
                df.getOWLObjectAllValuesFrom(r, a),
                df.getOWLObjectSomeValuesFrom(r, a)), a);
        check(LocalityClass.BOTTOM, true,
                df.getOWLSubClassOfAxiom(a, df.getOWLObjectAllValuesFrom(r, a)), a);
    }

    // --- Built-in roles have fixed semantics independently of Sigma ---

    @Tag("regression")
    @Test
    void bottomDoesNotTreatTopObjectPropertyAsEmpty() {
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectSomeValuesFrom(
                                df.getOWLTopObjectProperty(), df.getOWLThing()), a), a);
    }

    @Tag("regression")
    @Test
    void topDoesNotTreatBottomObjectPropertyAsUniversal() {
        check(LocalityClass.TOP, true,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLObjectSomeValuesFrom(
                                df.getOWLBottomObjectProperty(), df.getOWLThing()), a), a);
    }

    @Tag("regression")
    @Test
    void bottomDoesNotTreatTopDataPropertyAsEmpty() {
        // Every individual has every data value under owl:topDataProperty.
        check(LocalityClass.BOTTOM, false,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLDataSomeValuesFrom(
                                df.getOWLTopDataProperty(), df.getTopDatatype()), a), a);
    }

    @Tag("regression")
    @Test
    void topDoesNotTreatBottomDataPropertyAsUniversal() {
        check(LocalityClass.TOP, true,
                df.getOWLDisjointClassesAxiom(
                        df.getOWLDataSomeValuesFrom(
                                df.getOWLBottomDataProperty(), df.getTopDatatype()), a), a);
    }

    // -------------------------------------------------------------------------
    // Arity / operand-collision regression tests.
    //
    // Replacing distinct operands can produce identical class expressions.
    // OWL API factories (and OWL 2's structural constraints) may reject
    // EquivalentClasses/DisjointClasses/IntersectionOf/UnionOf with fewer
    // than two distinct operands. These tests use VALID, distinct operands
    // in their input axioms; only the replacement creates duplicates.
    //
    // For DisjointClasses, simply dropping duplicate operands is NOT enough:
    // DisjointClasses(C,C) means C SubClassOf Nothing, NOT a tautology.
    // For EquivalentClasses, repeated operands impose no extra constraint.
    // A failed transformation must fail the test instead of being skipped.
    // -------------------------------------------------------------------------

    // --- EquivalentClasses: complete and partial collisions ---
    // (The binary case is equivalentClassesOutsideSignatureAreLocalInBothModes.)

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void replacementDoesNotRejectTernaryEquivalentClassesCollapsedToOne() {
        checkBoth(true, df.getOWLEquivalentClassesAxiom(a, b, c));
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void replacementDoesNotRejectQuaternaryEquivalentClassesCollapsedToOne() {
        checkBoth(true, df.getOWLEquivalentClassesAxiom(a, b, c, d));
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesWithTwoCollapsedAndOneRetainedClassAreNonLocal() {
        // Bottom: Equivalent(A, Nothing, Nothing)
        // Top:    Equivalent(A, Thing, Thing)
        checkBoth(false, df.getOWLEquivalentClassesAxiom(a, b, c), a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesWithSomeRetainedAndSomeCollapsedOperandsStayNonLocal() {
        // Bottom: Equivalent(A, B, Nothing, Nothing)
        // Top:    Equivalent(A, B, Thing, Thing)
        checkBoth(false, df.getOWLEquivalentClassesAxiom(a, b, c, d), a, b);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesMayCollapseToOwlBuiltInClasses() {
        // Explicit built-ins do not change meaning merely because Sigma is empty.
        OWLAxiom equivalentToNothing = df.getOWLEquivalentClassesAxiom(a,
                df.getOWLNothing());
        OWLAxiom equivalentToThing = df.getOWLEquivalentClassesAxiom(a,
                df.getOWLThing());
        check(LocalityClass.BOTTOM, true, equivalentToNothing);
        check(LocalityClass.TOP, false, equivalentToNothing);
        check(LocalityClass.BOTTOM, false, equivalentToThing);
        check(LocalityClass.TOP, true, equivalentToThing);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesWithCollapsedComplementsRemainLocal() {
        // Bottom: Equivalent(not Nothing, not Nothing)
        // Top:    Equivalent(not Thing, not Thing)
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectComplementOf(a), df.getOWLObjectComplementOf(b)));
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesCanCollapseToAnUnchangedNamedClass() {
        // Sigma={A}. Bottom: A = (A or Nothing) = (A and not Nothing).
        // In Top, the latter two become Thing and Nothing, so not local.
        OWLAxiom axiom = df.getOWLEquivalentClassesAxiom(a,
                df.getOWLObjectUnionOf(a, b),
                df.getOWLObjectIntersectionOf(a, df.getOWLObjectComplementOf(b)));
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesPreserveConstraintsWhenOnlySomeComplexOperandsMerge() {
        // Sigma={A}. Bottom: Equivalent(A, Nothing, A).
        // Top: Equivalent(A, A, Thing). Neither is a tautology.
        checkBoth(false, df.getOWLEquivalentClassesAxiom(a,
                df.getOWLObjectIntersectionOf(a, b),
                df.getOWLObjectUnionOf(a, b)), a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesCanMergeAfterRecursiveRestrictionReplacement() {
        // r in Sigma, fillers outside: two distinct expressions become identical.
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, a),
                df.getOWLObjectSomeValuesFrom(r, b)), r);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void equivalentClassesCanMergeAfterDataPropertyReplacement() {
        // For a missing data property, these two distinct expressions both
        // denote Nothing (Bottom) or Thing (Top). They are equivalent for
        // every p, so this checks collision handling, not the replacement;
        // disjointClassesCanMergeAfterDataPropertyReplacement covers that.
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()),
                df.getOWLDataMinCardinality(1, p)));
    }

    // --- DisjointClasses: complete and partial collisions ---
    // (The binary case is disjointClassesOutsideSignatureDifferBetweenModes.)

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void replacementDoesNotRejectTernaryDisjointClassesCollapsedToOne() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a, b, c);
        check(LocalityClass.BOTTOM, true, axiom);
        check(LocalityClass.TOP, false, axiom);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void replacementDoesNotRejectQuaternaryDisjointClassesCollapsedToOne() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a, b, c, d);
        check(LocalityClass.BOTTOM, true, axiom);
        check(LocalityClass.TOP, false, axiom);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesWithTwoCollapsedAndOneRetainedClassHaveCorrectLocality() {
        // Bottom: Disjoint(A, Nothing, Nothing) is valid.
        // Top:    Disjoint(A, Thing, Thing) is not valid.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a, b, c);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesKeepOtherPairsWhenOnlySomeOperandsMerge() {
        // Bottom: Disjoint(A, B, Nothing, Nothing) still requires
        // A and B to be disjoint! Discarding the entire axiom would be wrong.
        // Top: Disjoint(A, B, Thing, Thing) is also not valid.
        checkBoth(false, df.getOWLDisjointClassesAxiom(a, b, c, d), a, b);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesWithCollapsedComplementsHaveOppositeResults() {
        // Bottom: Disjoint(Thing, Thing) is false.
        // Top:    Disjoint(Nothing, Nothing) is true.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectComplementOf(a), df.getOWLObjectComplementOf(b));
        check(LocalityClass.BOTTOM, false, axiom);
        check(LocalityClass.TOP, true, axiom);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesCollapsingOntoRetainedClassAreNotAutomaticallyLocal() {
        // Bottom: Disjoint(A, A or Nothing) -> Disjoint(A, A).
        // Its meaning is A SubClassOf Nothing, not a tautology.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a,
                df.getOWLObjectUnionOf(a, b));
        checkBoth(false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void topDisjointClassesCollapsingOntoRetainedClassAreNotLocal() {
        // Bottom: Disjoint(A, A and Nothing), tautological.
        // Top:    Disjoint(A, A and Thing) -> Disjoint(A, A), nonlocal.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a,
                df.getOWLObjectIntersectionOf(a, b));
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void ternaryDisjointClassesDoNotDropDuplicatePairConstraints() {
        // Top: Disjoint(A, A, not A) is still nonlocal even though
        // Disjoint(A, not A) alone is a tautology.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(a,
                df.getOWLObjectIntersectionOf(a, b),
                df.getOWLObjectComplementOf(a));
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesPreserveUnmergedConstraintsAlongsideCollapsedPair() {
        // Bottom: Disjoint(A, not A, Nothing, Nothing) is local.
        // Top: Disjoint(A, not A, Thing, Thing) is not local.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                a, df.getOWLObjectComplementOf(a), b, c);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesCanMergeAfterRecursiveRestrictionReplacement() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, a),
                df.getOWLObjectSomeValuesFrom(r, b));
        // r is retained; missing fillers become Nothing or Thing.
        check(LocalityClass.BOTTOM, true, axiom, r);
        check(LocalityClass.TOP, false, axiom, r);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void disjointClassesCanMergeAfterDataPropertyReplacement() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()),
                df.getOWLDataMinCardinality(1, p));
        check(LocalityClass.BOTTOM, true, axiom);
        check(LocalityClass.TOP, false, axiom);
    }

    // --- ObjectIntersectionOf: operand collisions inside class expressions ---

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionOfTwoMissingClassesMayCollapseToSingleOperand() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(b, c), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionOfThreeMissingClassesMayCollapseToSingleOperand() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(b, c, d), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionOfThreeClassesMayPartiallyCollapse() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(a, b, c), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionOfMissingComplementsMayCollapse() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(
                        df.getOWLObjectComplementOf(b),
                        df.getOWLObjectComplementOf(c)), a);
        check(LocalityClass.BOTTOM, false, axiom, a);
        check(LocalityClass.TOP, true, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionMayCollapseOntoRetainedClassExpression() {
        // In Bottom, A and (A or B) both become A.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(a, df.getOWLObjectUnionOf(a, b)), a);
        checkBoth(false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void intersectionMayCollapseAfterRecursiveFillerReplacement() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(
                        df.getOWLObjectSomeValuesFrom(r, b),
                        df.getOWLObjectSomeValuesFrom(r, c)), a);
        check(LocalityClass.BOTTOM, true, axiom, a, r);
        check(LocalityClass.TOP, false, axiom, a, r);
    }

    // --- ObjectUnionOf: operand collisions inside class expressions ---

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionOfTwoMissingClassesMayCollapseToSingleOperand() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(b, c), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionOfThreeMissingClassesMayCollapseToSingleOperand() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(b, c, d), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionOfThreeClassesMayPartiallyCollapse() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(a, b, c), a);
        checkBoth(false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionOfMissingComplementsMayCollapse() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(
                        df.getOWLObjectComplementOf(b),
                        df.getOWLObjectComplementOf(c)), a);
        check(LocalityClass.BOTTOM, false, axiom, a);
        check(LocalityClass.TOP, true, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionMayCollapseOntoRetainedClassExpression() {
        // In Bottom, A and (A and not B) both become A.
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(a,
                        df.getOWLObjectIntersectionOf(a, df.getOWLObjectComplementOf(b))), a);
        checkBoth(false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void unionMayCollapseAfterRecursiveFillerReplacement() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectUnionOf(
                        df.getOWLObjectSomeValuesFrom(r, b),
                        df.getOWLObjectSomeValuesFrom(r, c)), a);
        check(LocalityClass.BOTTOM, true, axiom, a, r);
        check(LocalityClass.TOP, false, axiom, a, r);
    }

    // --- Nested collisions and successive calls ---

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void nestedUnionAndIntersectionCanEachCollapse() {
        // Both inner unions collapse, then their intersection collapses.
        OWLClassExpression left = df.getOWLObjectUnionOf(b, c);
        OWLClassExpression right = df.getOWLObjectUnionOf(c, d);
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(left, right), a);
        check(LocalityClass.BOTTOM, true, axiom, a);
        check(LocalityClass.TOP, false, axiom, a);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void nestedCollisionsCanAlsoMergeEquivalentClassesAxiomOperands() {
        // Inner intersection and union both become Nothing (Bottom) or
        // Thing (Top), so the enclosing EquivalentClasses also collapses.
        OWLAxiom axiom = df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectIntersectionOf(b, c),
                df.getOWLObjectUnionOf(c, d));
        checkBoth(true, axiom);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void nestedCollisionsCanAlsoMergeDisjointClassesAxiomOperands() {
        OWLAxiom axiom = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectIntersectionOf(b, c),
                df.getOWLObjectUnionOf(c, d));
        check(LocalityClass.BOTTOM, true, axiom);
        check(LocalityClass.TOP, false, axiom);
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void subclassAxiomCanHaveIdenticalOperandsAfterReplacement() {
        // Unlike EquivalentClasses/DisjointClasses, SubClassOf is binary
        // with named roles for its operands; identical operands are fine.
        checkBoth(true, df.getOWLSubClassOfAxiom(a, b));
    }

    @Tag("regression")
    @Tag("operand-collision")
    @Test
    void consecutiveCollapsingAndNonCollapsingAxiomsDoNotLeaveVisitorState() {
        OWLAxiom collapsing = df.getOWLDisjointClassesAxiom(b, c, d);
        OWLAxiom nonCollapsing = df.getOWLDisjointClassesAxiom(a, b);
        // Collapses to Disjoint(Nothing, Nothing) in TOP: a TOP-local axiom,
        // so that the TOP sequence alternates between true and false.
        OWLAxiom topLocalCollapsing = df.getOWLDisjointClassesAxiom(
                df.getOWLObjectComplementOf(b), df.getOWLObjectComplementOf(c));
        check(LocalityClass.BOTTOM, true, collapsing);
        check(LocalityClass.BOTTOM, false, nonCollapsing, a, b);
        check(LocalityClass.BOTTOM, true, collapsing);
        check(LocalityClass.BOTTOM, false, topLocalCollapsing);
        check(LocalityClass.BOTTOM, true, collapsing);
        check(LocalityClass.TOP, true, topLocalCollapsing);
        check(LocalityClass.TOP, false, collapsing);
        check(LocalityClass.TOP, true, topLocalCollapsing);
        check(LocalityClass.TOP, false, nonCollapsing, a, b);
        check(LocalityClass.TOP, true, topLocalCollapsing);
    }

    // -------------------------------------------------------------------------
    // Preservation tests: these exercise behaviour that SHOULD ALREADY WORK in
    // the supplied implementation. Keep them green while fixing the failures
    // above. Each expectation is a semantic statement, not a snapshot of the
    // implementation. The comments indicate why it holds.
    // -------------------------------------------------------------------------

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesNamedClassesWithinSignature() {
        // Neither A nor B may be substituted, in either locality mode.
        checkBoth(false, df.getOWLEquivalentClassesAxiom(a, b), a, b);
        checkBoth(false, df.getOWLDisjointClassesAxiom(a, b), a, b);
        checkBoth(true, df.getOWLDisjointClassesAxiom(a,
                df.getOWLObjectComplementOf(a)), a, b);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourHandlesBasicSubclassTautologiesAndNonTautologies() {
        // These are deliberately invariant under reversing the arguments;
        // the dedicated regression test above detects the reversal bug.
        checkBoth(true, df.getOWLSubClassOfAxiom(a, a), a);
        checkBoth(false, df.getOWLSubClassOfAxiom(a, b), a, b);
        // OWL API stores n-ary operands sorted, so (A and B) and (B and A)
        // are the same object. Use a structurally different equivalent RHS.
        checkBoth(true, df.getOWLSubClassOfAxiom(
                df.getOWLObjectIntersectionOf(a, b),
                df.getOWLObjectComplementOf(df.getOWLObjectUnionOf(
                        df.getOWLObjectComplementOf(b),
                        df.getOWLObjectComplementOf(a)))), a, b);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourRespectsPartialClassSignatures() {
        OWLAxiom disjoint = df.getOWLDisjointClassesAxiom(a, b);
        OWLAxiom equivalent = df.getOWLEquivalentClassesAxiom(a, b);
        // B outside Sigma: Bottom gives Nothing; Top gives Thing.
        check(LocalityClass.BOTTOM, true, disjoint, a);
        check(LocalityClass.TOP, false, disjoint, a);
        check(LocalityClass.BOTTOM, false, equivalent, a);
        check(LocalityClass.TOP, false, equivalent, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesComplements() {
        OWLClassExpression notA = df.getOWLObjectComplementOf(a);
        checkBoth(true, df.getOWLDisjointClassesAxiom(a, notA), a);
        checkBoth(false, df.getOWLEquivalentClassesAxiom(a, notA), a);
        // When B is outside Sigma: Bottom has B=Nothing, Top has B=Thing.
        // In either case, B and its complement are disjoint.
        checkBoth(true, df.getOWLDisjointClassesAxiom(
                b, df.getOWLObjectComplementOf(b)), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesIntersections() {
        OWLClassExpression both = df.getOWLObjectIntersectionOf(a, b);
        checkBoth(true, df.getOWLDisjointClassesAxiom(
                both, df.getOWLObjectComplementOf(a)), a, b);
        checkBoth(false, df.getOWLEquivalentClassesAxiom(both, a), a, b);
        // B=Nothing gives an empty intersection; B=Thing gives A.
        check(LocalityClass.BOTTOM, true,
                df.getOWLDisjointClassesAxiom(both, a), a);
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(both, a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesUnions() {
        OWLClassExpression either = df.getOWLObjectUnionOf(a, b);
        checkBoth(false, df.getOWLDisjointClassesAxiom(either, a), a, b);
        checkBoth(false, df.getOWLEquivalentClassesAxiom(either, a), a, b);
        // B=Nothing gives A; B=Thing gives Thing. Use disjointness
        // to avoid an equivalence with two syntactically equal operands.
        OWLClassExpression notA = df.getOWLObjectComplementOf(a);
        check(LocalityClass.BOTTOM, true,
                df.getOWLDisjointClassesAxiom(either, notA), a);
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(either, notA), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesNestedBooleanConstructors() {
        OWLClassExpression lhs = df.getOWLObjectUnionOf(a, b);
        OWLClassExpression rhs = df.getOWLObjectComplementOf(
                df.getOWLObjectIntersectionOf(
                        df.getOWLObjectComplementOf(a),
                        df.getOWLObjectComplementOf(b)));
        // De Morgan's law is true for every interpretation of A and B.
        checkBoth(true, df.getOWLEquivalentClassesAxiom(lhs, rhs), a, b);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourReplacesMissingObjectPropertyWithBottom() {
        // With r empty: exists r.A = Nothing, and forall r.A = Thing.
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, a), a), a);
        check(LocalityClass.BOTTOM, false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectAllValuesFrom(r, a), a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourReplacesMissingObjectPropertyWithTopInSafeCases() {
        // B is outside Sigma as well. Consequently B=Thing and not B=Nothing.
        // Under the universal role U: exists U.Thing=Thing,
        // and forall U.Nothing=Nothing (nonempty interpretation domain).
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, b), a), a);
        check(LocalityClass.TOP, true, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectAllValuesFrom(r,
                        df.getOWLObjectComplementOf(b)), a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourRebuildsSomeValuesFromFillers() {
        // Existential restrictions distribute over unions.
        OWLClassExpression lhs = df.getOWLObjectSomeValuesFrom(r,
                df.getOWLObjectUnionOf(a, b));
        OWLClassExpression rhs = df.getOWLObjectUnionOf(
                df.getOWLObjectSomeValuesFrom(r, a),
                df.getOWLObjectSomeValuesFrom(r, b));
        checkBoth(true, df.getOWLEquivalentClassesAxiom(lhs, rhs), r, a, b);
        checkBoth(false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, a), a), r, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourRebuildsAllValuesFromFillers() {
        // Universal restrictions distribute over intersections.
        OWLClassExpression lhs = df.getOWLObjectAllValuesFrom(r,
                df.getOWLObjectIntersectionOf(a, b));
        OWLClassExpression rhs = df.getOWLObjectIntersectionOf(
                df.getOWLObjectAllValuesFrom(r, a),
                df.getOWLObjectAllValuesFrom(r, b));
        checkBoth(true, df.getOWLEquivalentClassesAxiom(lhs, rhs), r, a, b);
        checkBoth(false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectAllValuesFrom(r, a), a), r, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourRebuildsFillersWhenTheirClassIsMissing() {
        // r is in Sigma but B is not. Quantifier dualities remain true
        // after B becomes Nothing (Bottom) or Thing (Top). The two sides
        // remain structurally different, avoiding degenerate equivalences.
        // These dualities hold for EVERY value of B: they check that rebuilt
        // expressions reach the reasoner intact, not which value B received.
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectSomeValuesFrom(r, b),
                df.getOWLObjectComplementOf(df.getOWLObjectAllValuesFrom(r,
                        df.getOWLObjectComplementOf(b)))), r);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectAllValuesFrom(r, b),
                df.getOWLObjectComplementOf(df.getOWLObjectSomeValuesFrom(r,
                        df.getOWLObjectComplementOf(b)))), r);
        // These depend on the replacement: B=Nothing in Bottom, Thing in Top.
        OWLAxiom everythingAllB = df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectAllValuesFrom(r, b));
        OWLAxiom someBIsEmpty = df.getOWLSubClassOfAxiom(
                df.getOWLObjectSomeValuesFrom(r, b), df.getOWLNothing());
        check(LocalityClass.BOTTOM, false, everythingAllB, r);
        check(LocalityClass.TOP, true, everythingAllB, r);
        check(LocalityClass.BOTTOM, true, someBIsEmpty, r);
        check(LocalityClass.TOP, false, someBIsEmpty, r);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesDataRestrictionsWithinSignature() {
        OWLClassExpression some = df.getOWLDataSomeValuesFrom(p, df.getTopDatatype());
        OWLClassExpression minOne = df.getOWLDataMinCardinality(1, p);
        // DataSomeValuesFrom(p, rdfs:Literal) = DataMinCardinality(1,p).
        checkBoth(true, df.getOWLEquivalentClassesAxiom(some, minOne), p);
        checkBoth(false, df.getOWLDisjointClassesAxiom(some, a), p, a);
        // Every data value belongs to rdfs:Literal, so DataAllValuesFrom
        // with the top datatype denotes Thing regardless of p.
        checkBoth(false, df.getOWLDisjointClassesAxiom(
                df.getOWLDataAllValuesFrom(p, df.getTopDatatype()), a), p, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourReplacesMissingDataPropertyInBottom() {
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()), a), a);
        check(LocalityClass.BOTTOM, false, df.getOWLDisjointClassesAxiom(
                df.getOWLDataAllValuesFrom(p, df.getTopDatatype()), a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourReplacesMissingDataPropertyInSafeTopCases() {
        // The universal data property has a nonempty range: some p.Literal=Thing.
        // It is NOT true that all its values are xsd:string, so all p.string=Nothing.
        OWLClassExpression allStrings = df.getOWLDataAllValuesFrom(p,
                df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string")));
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(
                df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()), a), a);
        check(LocalityClass.TOP, true, df.getOWLDisjointClassesAxiom(allStrings, a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourReplacesHasSelfAndHasValueForMissingProperties() {
        OWLClassExpression self = df.getOWLObjectHasSelf(r);
        OWLClassExpression objectValue = df.getOWLObjectHasValue(r, i);
        OWLClassExpression dataValue = df.getOWLDataHasValue(p, df.getOWLLiteral("x"));
        // Missing properties are empty in Bottom and universal in Top.
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(self, a), a);
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(self, a), a);
        check(LocalityClass.BOTTOM, true,
                df.getOWLDisjointClassesAxiom(objectValue, a), a);
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(objectValue, a), a);
        check(LocalityClass.BOTTOM, true,
                df.getOWLDisjointClassesAxiom(dataValue, a), a);
        check(LocalityClass.TOP, false,
                df.getOWLDisjointClassesAxiom(dataValue, a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesObjectCardinalitiesWithinSignature() {
        OWLClassExpression minOne = df.getOWLObjectMinCardinality(1, r, a);
        OWLClassExpression maxOne = df.getOWLObjectMaxCardinality(1, r, a);
        OWLClassExpression exactOne = df.getOWLObjectExactCardinality(1, r, a);
        OWLClassExpression maxZero = df.getOWLObjectMaxCardinality(0, r, a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                minOne, df.getOWLObjectSomeValuesFrom(r, a)), r, a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                maxZero, df.getOWLObjectAllValuesFrom(r,
                        df.getOWLObjectComplementOf(a))), r, a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                exactOne, df.getOWLObjectIntersectionOf(minOne, maxOne)), r, a);
        checkBoth(false, df.getOWLDisjointClassesAxiom(exactOne, a), r, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesZeroObjectCardinalityWhenRoleIsKept() {
        OWLClassExpression minZero = df.getOWLObjectMinCardinality(0, r, a);
        OWLClassExpression exactZero = df.getOWLObjectExactCardinality(0, r, a);
        OWLClassExpression maxZero = df.getOWLObjectMaxCardinality(0, r, a);
        // >= 0 is always Thing; = 0 is equivalent to <= 0.
        checkBoth(false, df.getOWLDisjointClassesAxiom(minZero, a), r, a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(exactZero, maxZero), r, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesDataCardinalitiesWithinSignature() {
        OWLClassExpression minOne = df.getOWLDataMinCardinality(1, p);
        OWLClassExpression maxOne = df.getOWLDataMaxCardinality(1, p);
        OWLClassExpression exactOne = df.getOWLDataExactCardinality(1, p);
        OWLClassExpression maxZero = df.getOWLDataMaxCardinality(0, p);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                minOne, df.getOWLDataSomeValuesFrom(p, df.getTopDatatype())), p);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                maxZero, df.getOWLObjectComplementOf(
                        df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()))), p);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(
                exactOne, df.getOWLObjectIntersectionOf(minOne, maxOne)), p);
        checkBoth(false, df.getOWLDisjointClassesAxiom(exactOne, a), p, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourPreservesZeroDataCardinalityWhenPropertyIsKept() {
        OWLClassExpression minZero = df.getOWLDataMinCardinality(0, p);
        OWLClassExpression exactZero = df.getOWLDataExactCardinality(0, p);
        OWLClassExpression maxZero = df.getOWLDataMaxCardinality(0, p);
        checkBoth(false, df.getOWLDisjointClassesAxiom(minZero, a), p, a);
        checkBoth(true, df.getOWLEquivalentClassesAxiom(exactZero, maxZero), p, a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourHandlesPositiveCardinalitiesOfMissingBottomProperties() {
        // With an empty relation, positive min/exact denote Nothing;
        // every max-cardinality restriction denotes Thing.
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectMinCardinality(1, r, a), a), a);
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectExactCardinality(1, r, a), a), a);
        check(LocalityClass.BOTTOM, false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectMaxCardinality(1, r, a), a), a);
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLDataMinCardinality(1, p), a), a);
        check(LocalityClass.BOTTOM, true, df.getOWLDisjointClassesAxiom(
                df.getOWLDataExactCardinality(1, p), a), a);
        check(LocalityClass.BOTTOM, false, df.getOWLDisjointClassesAxiom(
                df.getOWLDataMaxCardinality(1, p), a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourHandlesSafeCardinalitiesOfMissingTopProperties() {
        // Here B is also outside Sigma, so the filler becomes Thing.
        // For nonempty domains: >=1 U.Thing=Thing, <=0 U.Thing=Nothing.
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectMinCardinality(1, r, b), a), a);
        check(LocalityClass.TOP, true, df.getOWLDisjointClassesAxiom(
                df.getOWLObjectMaxCardinality(0, r, b), a), a);
        // Every OWL 2 datatype map has at least one data value.
        check(LocalityClass.TOP, false, df.getOWLDisjointClassesAxiom(
                df.getOWLDataMinCardinality(1, p), a), a);
        check(LocalityClass.TOP, true, df.getOWLDisjointClassesAxiom(
                df.getOWLDataMaxCardinality(0, p), a), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourTreatsNonLogicalAxiomsAsLocalRegardlessOfSignature() {
        // All nonlogical axioms are handled before either replacer is invoked.
        checkBoth(true, df.getOWLDeclarationAxiom(a));
        checkBoth(true, df.getOWLDeclarationAxiom(a), a, b, r, p);
        checkBoth(true, df.getOWLAnnotationAssertionAxiom(
                df.getRDFSLabel(), a.getIRI(), df.getOWLLiteral("A class")));
        checkBoth(true, df.getOWLAnnotationAssertionAxiom(
                df.getRDFSComment(), a.getIRI(), df.getOWLLiteral("description")), a);
    }

    @Tag("preservation")
    @Test
    void existingBehaviourDoesNotRetainStateBetweenEvaluations() {
        OWLAxiom local = df.getOWLDisjointClassesAxiom(
                a, df.getOWLObjectComplementOf(a));
        OWLAxiom nonLocal = df.getOWLDisjointClassesAxiom(a, b);
        checkBoth(false, nonLocal, a, b);
        checkBoth(true, local, a);
        checkBoth(false, nonLocal, a, b);
        check(LocalityClass.BOTTOM, true, nonLocal);
        check(LocalityClass.TOP, false, nonLocal);
        checkBoth(true, local, a);
    }

    @Tag("lifecycle")
    @Test
    void disposingEvaluatorShouldNotThrow() {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();

        SemanticLocalityEvaluator evaluator =
                new SemanticLocalityEvaluator(
                        LocalityClass.BOTTOM,
                        ownManager,
                        new ReasonerFactory()
                );

        assertDoesNotThrow(evaluator::dispose);
        assertDoesNotThrow(evaluator::dispose); // idempotent
        assertEquals(0, ownManager.getOntologies().size());
        assertThrows(IllegalStateException.class,
                () -> evaluator.isLocal(df.getOWLSubClassOfAxiom(a, a), sig(a)));
    }
}