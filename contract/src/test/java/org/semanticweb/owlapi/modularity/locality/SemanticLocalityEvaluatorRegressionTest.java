package org.semanticweb.owlapi.modularity.locality;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

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

    private static OWLDatatype additionalDatatype(OWL2Datatype type) {
        return df.getOWLDatatype(type.getIRI());
    }

    private static OWLDataProperty additionalDataProperty(String name) {
        return df.getOWLDataProperty(IRI.create("urn:semantic-locality:additional#" + name));
    }

    private static void checkExtension(LocalityClass kind, OWLClassExpression expression,
                                       boolean alwaysThing, boolean alwaysNothing, OWLEntity... signature) {
        OWLClassExpression complement = df.getOWLObjectComplementOf(expression);
        assertAll(expression.toString(),
                () -> check(kind, alwaysThing,
                        df.getOWLSubClassOfAxiom(df.getOWLThing(), expression), signature),
                () -> check(kind, alwaysNothing,
                        df.getOWLSubClassOfAxiom(expression, df.getOWLNothing()), signature),
                () -> check(kind, alwaysNothing,
                        df.getOWLSubClassOfAxiom(df.getOWLThing(), complement), signature),
                () -> check(kind, alwaysThing,
                        df.getOWLSubClassOfAxiom(complement, df.getOWLNothing()), signature));
    }

    private static DynamicTest extensionTest(String name, LocalityClass kind,
                                             OWLClassExpression expression, boolean alwaysThing, boolean alwaysNothing,
                                             OWLEntity... signature) {
        return dynamicTest(name, () -> checkExtension(kind, expression,
                alwaysThing, alwaysNothing, signature));
    }

    private static final class AdditionalRangeCase {
        final String name;
        final OWLDataRange range;
        final int size; // -1 means infinite; otherwise the exact number of VALUES.
        final boolean universal;

        AdditionalRangeCase(String name, OWLDataRange range, int size, boolean universal) {
            this.name = name;
            this.range = range;
            this.size = size;
            this.universal = universal;
        }

        boolean atLeast(int n) {
            return size < 0 || size >= n;
        }
    }

    private static List<AdditionalRangeCase> additionalFixedRanges() {
        OWLDatatype bool = additionalDatatype(OWL2Datatype.XSD_BOOLEAN);
        OWLDatatype integer = additionalDatatype(OWL2Datatype.XSD_INTEGER);
        OWLDatatype decimal = additionalDatatype(OWL2Datatype.XSD_DECIMAL);
        OWLDatatype string = additionalDatatype(OWL2Datatype.XSD_STRING);
        OWLDataRange empty = df.getOWLDataComplementOf(df.getTopDatatype());
        OWLDataRange oneToThree = df.getOWLDatatypeRestriction(integer,
                df.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral(1)),
                df.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral(3)));
        OWLDataRange numericAliases = df.getOWLDataOneOf(
                df.getOWLLiteral("1", integer),
                df.getOWLLiteral("01", integer),
                df.getOWLLiteral("1.0", decimal));
        // The integer/decimal pair stays structurally distinct even if the factory
        // canonicalizes the two integer lexical forms. It still denotes ONE value.
        return Arrays.asList(
                new AdditionalRangeCase("empty", empty, 0, false),
                new AdditionalRangeCase("boolean", bool, 2, false),
                new AdditionalRangeCase("numeric-value-aliases", numericAliases, 1, false),
                new AdditionalRangeCase("integer-interval-1-to-3", oneToThree, 3, false),
                new AdditionalRangeCase("boolean-intersect-string",
                        df.getOWLDataIntersectionOf(bool, string), 0, false),
                new AdditionalRangeCase("boolean-without-true",
                        df.getOWLDataIntersectionOf(bool, df.getOWLDataComplementOf(
                                df.getOWLDataOneOf(df.getOWLLiteral(true)))), 1, false),
                new AdditionalRangeCase("double-complement-boolean",
                        df.getOWLDataComplementOf(df.getOWLDataComplementOf(bool)), 2, false),
                new AdditionalRangeCase("overlapping-union",
                        df.getOWLDataUnionOf(bool, df.getOWLDataOneOf(
                                df.getOWLLiteral(true))), 2, false),
                new AdditionalRangeCase("integer", integer, -1, false),
                new AdditionalRangeCase("complement-boolean",
                        df.getOWLDataComplementOf(bool), -1, false),
                new AdditionalRangeCase("top", df.getTopDatatype(), -1, true),
                new AdditionalRangeCase("boolean-or-not-boolean",
                        df.getOWLDataUnionOf(bool, df.getOWLDataComplementOf(bool)), -1, true));
    }

    // B is the union of the built-in value spaces, NOT rdfs:Literal.
// E = complement(B) can have 0, 1, 2, ... elements in different data domains.
// Assumes the standard datatype map represented by OWL2Datatype, without an
// additional disjoint value space from an externally extended datatype map.
// The backend must support these ranges; backend rejection is a coverage
// limitation and must not be mistaken for a demonstrated wrong Boolean answer.
    private static OWLDataRange additionalBuiltInValueUnion() {
        OWLDataRange[] operands = Arrays.stream(OWL2Datatype.values())
                .filter(type -> type != OWL2Datatype.RDFS_LITERAL)
                .map(SemanticLocalityEvaluatorRegressionTest::additionalDatatype)
                .toArray(OWLDataRange[]::new);
        return df.getOWLDataUnionOf(operands);
    }

    private static OWLDataRange additionalExtraDataValues() {
        return df.getOWLDataComplementOf(additionalBuiltInValueUnion());
    }

// -------------------------------------------------------------------------
// Every data-restriction constructor, both modes, absent/retained property,
// empty/finite/infinite/universal ranges, and both expression polarities.
// -------------------------------------------------------------------------

    @Tag("regression")
    @Tag("exact-result")
    @TestFactory
    Stream<DynamicTest> additionalDataRestrictionMatrix() {
        List<DynamicTest> tests = new ArrayList<>();
        for (LocalityClass kind : new LocalityClass[] {LocalityClass.BOTTOM, LocalityClass.TOP}) {
            for (boolean retained : new boolean[] {false, true}) {
                OWLEntity[] signature = retained ? new OWLEntity[] {p} : new OWLEntity[0];
                boolean emptyProperty = !retained && kind == LocalityClass.BOTTOM;
                boolean fullProperty = !retained && kind == LocalityClass.TOP;
                String prefix = kind + "/" + (retained ? "retained/" : "absent/");
                tests.add(extensionTest(prefix + "hasValue", kind,
                        df.getOWLDataHasValue(p, df.getOWLLiteral("x")),
                        fullProperty, emptyProperty, signature));
                for (AdditionalRangeCase range : additionalFixedRanges()) {
                    String name = prefix + range.name + "/";
                    tests.add(extensionTest(name + "some", kind,
                            df.getOWLDataSomeValuesFrom(p, range.range),
                            fullProperty && range.atLeast(1),
                            emptyProperty || !range.atLeast(1), signature));
                    tests.add(extensionTest(name + "all", kind,
                            df.getOWLDataAllValuesFrom(p, range.range),
                            emptyProperty || range.universal,
                            fullProperty && !range.universal, signature));
                    for (int n : new int[] {0, 1, 2, 3, 4}) {
                        boolean atLeastN = range.atLeast(n);
                        boolean moreThanN = range.atLeast(n + 1);
                        tests.add(extensionTest(name + "min-" + n, kind,
                                df.getOWLDataMinCardinality(n, p, range.range),
                                n == 0 || fullProperty && atLeastN,
                                n > 0 && (emptyProperty || !atLeastN), signature));
                        tests.add(extensionTest(name + "max-" + n, kind,
                                df.getOWLDataMaxCardinality(n, p, range.range),
                                emptyProperty || !moreThanN,
                                fullProperty && moreThanN, signature));
                        boolean exactAlways = emptyProperty ? n == 0
                                : fullProperty ? atLeastN && !moreThanN
                                : range.size == 0 && n == 0;
                        boolean exactNever = emptyProperty ? n != 0
                                : !atLeastN || fullProperty && moreThanN;
                        tests.add(extensionTest(name + "exact-" + n, kind,
                                df.getOWLDataExactCardinality(n, p, range.range),
                                exactAlways, exactNever, signature));
                    }
                }
            }
        }
        return tests.stream();
    }

    @Tag("regression")
    @Test
    void additionalBuiltinDatatypesAreIndependentOfSignatureMembership() {
        OWLDatatype bool = additionalDatatype(OWL2Datatype.XSD_BOOLEAN);
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLDataExactCardinality(2, p, bool));
        check(LocalityClass.TOP, true, axiom);
        check(LocalityClass.TOP, true, axiom, bool);
        check(LocalityClass.BOTTOM, false, axiom);
        check(LocalityClass.BOTTOM, false, axiom, bool);
        checkBoth(false, axiom, p);
        checkBoth(false, axiom, p, bool);
    }

    @Tag("regression")
    @Test
    void additionalExplicitBottomDataRestrictionsStayEmptyOrVacuousInBothModes() {
        OWLDataProperty zero = df.getOWLBottomDataProperty();
        OWLDatatype bool = additionalDatatype(OWL2Datatype.XSD_BOOLEAN);
        for (LocalityClass kind : new LocalityClass[] {LocalityClass.BOTTOM, LocalityClass.TOP}) {
            checkExtension(kind, df.getOWLDataSomeValuesFrom(zero, bool), false, true);
            checkExtension(kind, df.getOWLDataAllValuesFrom(zero, bool), true, false);
            checkExtension(kind, df.getOWLDataHasValue(zero, df.getOWLLiteral(true)), false, true);
            for (int n : new int[] {0, 1, 2}) {
                checkExtension(kind, df.getOWLDataMinCardinality(n, zero, bool), n == 0, n != 0);
                checkExtension(kind, df.getOWLDataMaxCardinality(n, zero, bool), true, false);
                checkExtension(kind, df.getOWLDataExactCardinality(n, zero, bool), n == 0, n != 0);
            }
        }
    }

    @Tag("regression")
    @Tag("extended-input")
    @Test
    void additionalExplicitTopDataRestrictionsHaveFixedPropertySemanticsInBothModes() {
        // As in the existing explicit-top tests: this tests the evaluator's
        // extension to such input expressions, not OWL 2 DL profile conformance.
        OWLDataProperty full = df.getOWLTopDataProperty();
        OWLDatatype bool = additionalDatatype(OWL2Datatype.XSD_BOOLEAN);
        for (LocalityClass kind : new LocalityClass[] {LocalityClass.BOTTOM, LocalityClass.TOP}) {
            checkExtension(kind, df.getOWLDataSomeValuesFrom(full, bool), true, false);
            checkExtension(kind, df.getOWLDataAllValuesFrom(full, bool), false, true);
            checkExtension(kind, df.getOWLDataHasValue(full, df.getOWLLiteral(true)), true, false);
            for (int n : new int[] {0, 1, 2, 3}) {
                checkExtension(kind, df.getOWLDataMinCardinality(n, full, bool), n <= 2, n > 2);
                checkExtension(kind, df.getOWLDataMaxCardinality(n, full, bool), n >= 2, n < 2);
                checkExtension(kind, df.getOWLDataExactCardinality(n, full, bool), n == 2, n != 2);
            }
        }
    }

// -------------------------------------------------------------------------
// The five interpretation-dependent TOP datatype rewrites.
// No assertion here allows an exception to count as an exact result.
// -------------------------------------------------------------------------

    @Tag("regression")
    @Tag("datatype-domain")
    @Tag("exact-result")
    @TestFactory
    Stream<DynamicTest> additionalVariableDataDomainRestrictionsAreNotConstants() {
        OWLDataRange extra = additionalExtraDataValues();
        List<DynamicTest> tests = new ArrayList<>();
        tests.add(extensionTest("variable-domain/some", LocalityClass.TOP,
                df.getOWLDataSomeValuesFrom(p, extra), false, false));
        tests.add(extensionTest("variable-domain/all", LocalityClass.TOP,
                df.getOWLDataAllValuesFrom(p, additionalBuiltInValueUnion()), false, false));
        for (int n : new int[] {1, 2, 3}) {
            tests.add(extensionTest("variable-domain/min-" + n, LocalityClass.TOP,
                    df.getOWLDataMinCardinality(n, p, extra), false, false));
        }
        for (int n : new int[] {0, 1, 2}) {
            tests.add(extensionTest("variable-domain/max-" + n, LocalityClass.TOP,
                    df.getOWLDataMaxCardinality(n, p, extra), false, false));
            tests.add(extensionTest("variable-domain/exact-" + n, LocalityClass.TOP,
                    df.getOWLDataExactCardinality(n, p, extra), false, false));
        }
        return tests.stream();
    }

    @Tag("regression")
    @Tag("datatype-domain")
    @Test
    void additionalVariableDataRestrictionsPreserveCorrelations() {
        OWLDataRange extra = additionalExtraDataValues();
        OWLClassExpression some = df.getOWLDataSomeValuesFrom(p, extra);
        OWLClassExpression allBuiltIns = df.getOWLDataAllValuesFrom(p, additionalBuiltInValueUnion());
        OWLClassExpression minTwo = df.getOWLDataMinCardinality(2, p, extra);
        OWLClassExpression maxOne = df.getOWLDataMaxCardinality(1, p, extra);
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                some, df.getOWLObjectComplementOf(allBuiltIns)));
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                minTwo, df.getOWLObjectComplementOf(maxOne)));
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                df.getOWLDataExactCardinality(1, p, extra),
                df.getOWLObjectIntersectionOf(some, maxOne)));
        check(LocalityClass.TOP, true, df.getOWLSubClassOfAxiom(minTwo, some));
        // One extra value suffices for 'some', but not for 'minTwo'.
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(some, minTwo));
    }

    @Tag("regression")
    @Tag("datatype-domain")
    @Test
    void additionalVariableDataRestrictionsWithRetainedDataPropertiesAreConservative() {
        OWLDataProperty q = additionalDataProperty("q");
        OWLDataRange extra = additionalExtraDataValues();
        OWLClassExpression localSome = df.getOWLDataSomeValuesFrom(q, extra);
        OWLClassExpression globalSome = df.getOWLDataSomeValuesFrom(p, extra);
        // Exactly, the first and third axioms are local: if q has an extra value,
        // the data domain has one. The evaluator fixes the number of extra values
        // only for the absent p and leaves q to the reasoner, which may choose a
        // different number. OWL 2 cannot tell the reasoner "exactly k extra values",
        // so these correlations are lost. That direction is sound: the axioms are
        // reported non-local and kept in modules, never wrongly dropped.
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(localSome, globalSome), q);
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(globalSome, localSome), q);
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(
                df.getOWLDataMaxCardinality(0, p, extra),
                df.getOWLDataAllValuesFrom(q, additionalBuiltInValueUnion())), q);
    }

    @Tag("regression")
    @Tag("datatype-domain")
    @Test
    void additionalExactDataCardinalityChecksEveryDomainSize() {
        OWLDataRange extra = additionalExtraDataValues();
        // "At most one extra value" holds for 0 and 1 extra values but not for 2.
        // Regression: the threshold of the upper bound of '=1' was never
        // discovered when its lower bound already failed, so 2 was not tested.
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectUnionOf(df.getOWLDataExactCardinality(1, p, extra),
                        df.getOWLDataMaxCardinality(0, p, extra))));
        // Control: 0, exactly 1, or at least 2 extra values covers every domain.
        check(LocalityClass.TOP, true, df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectUnionOf(df.getOWLDataMaxCardinality(0, p, extra),
                        df.getOWLDataExactCardinality(1, p, extra),
                        df.getOWLDataMinCardinality(2, p, extra))));
    }

    @Tag("regression")
    @Tag("datatype-domain")
    @TestFactory
    Stream<DynamicTest> additionalVariableDataRestrictionsRemainSoundInEnclosingConstructs() {
        OWLDataRange extra = additionalExtraDataValues();
        OWLDataProperty q = additionalDataProperty("q");
        // Each expression is interpretation-dependent, but the old probe-based
        // rewriting maps it to Thing if E is reported satisfiable at these bounds.
        List<OWLClassExpression> expressions = Arrays.asList(
                df.getOWLDataSomeValuesFrom(p, extra),
                df.getOWLDataMinCardinality(2, p, extra),
                df.getOWLObjectComplementOf(df.getOWLDataMaxCardinality(0, p, extra)),
                df.getOWLObjectComplementOf(df.getOWLDataExactCardinality(0, p, extra)),
                df.getOWLObjectComplementOf(df.getOWLDataAllValuesFrom(
                        p, additionalBuiltInValueUnion())));
        List<DynamicTest> tests = new ArrayList<>();
        for (OWLClassExpression expression : expressions) {
            List<OWLAxiom> axioms = Arrays.asList(
                    df.getOWLEquivalentClassesAxiom(expression, df.getOWLThing()),
                    df.getOWLDisjointClassesAxiom(
                            df.getOWLObjectComplementOf(expression), df.getOWLThing()),
                    df.getOWLClassAssertionAxiom(expression, i),
                    df.getOWLObjectPropertyDomainAxiom(r, expression),
                    df.getOWLObjectPropertyRangeAxiom(r, expression),
                    df.getOWLDataPropertyDomainAxiom(q, expression),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectIntersectionOf(a, expression), a),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectUnionOf(a, df.getOWLObjectComplementOf(expression)), a),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectSomeValuesFrom(r, expression),
                            df.getOWLObjectSomeValuesFrom(r, df.getOWLThing())),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectAllValuesFrom(r, expression), df.getOWLThing()),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectMinCardinality(1, r, expression),
                            df.getOWLObjectMinCardinality(1, r, df.getOWLThing())),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectMaxCardinality(0, r, expression),
                            df.getOWLObjectMaxCardinality(0, r, df.getOWLThing())),
                    df.getOWLEquivalentClassesAxiom(
                            df.getOWLObjectExactCardinality(1, r, expression),
                            df.getOWLObjectExactCardinality(1, r, df.getOWLThing())),
                    df.getOWLHasKeyAxiom(df.getOWLObjectComplementOf(expression), q));
            for (OWLAxiom axiom : axioms) {
                tests.add(dynamicTest(axiom.toString(),
                        () -> check(LocalityClass.TOP, false, axiom, a, r, q, i)));
            }
            OWLAxiom disjointUnion = df.getOWLDisjointUnionAxiom(a,
                    Arrays.asList(expression, df.getOWLNothing()));
            // Here A is deliberately absent, hence becomes Thing in TOP.
            tests.add(dynamicTest(disjointUnion.toString(),
                    () -> check(LocalityClass.TOP, false, disjointUnion)));
        }
        return tests.stream();
    }

// -------------------------------------------------------------------------
// Every data-property axiom family, including missing/retained operands.
// -------------------------------------------------------------------------

    @Tag("regression")
    @Test
    void additionalDataSubpropertyInclusionsRespectDirectionAndSignature() {
        OWLDataProperty q = additionalDataProperty("q");
        OWLAxiom inclusion = df.getOWLSubDataPropertyOfAxiom(p, q);
        checkBoth(true, inclusion);
        check(LocalityClass.BOTTOM, false, inclusion, p);
        check(LocalityClass.TOP, true, inclusion, p);
        check(LocalityClass.BOTTOM, true, inclusion, q);
        check(LocalityClass.TOP, false, inclusion, q); // U_D <= q is not valid.
        checkBoth(false, inclusion, p, q);
        checkBoth(true, df.getOWLSubDataPropertyOfAxiom(p, p), p);
        checkBoth(true, df.getOWLSubDataPropertyOfAxiom(df.getOWLBottomDataProperty(), p), p);
        checkBoth(true, df.getOWLSubDataPropertyOfAxiom(p, df.getOWLTopDataProperty()), p);
        OWLAxiom toBottom = df.getOWLSubDataPropertyOfAxiom(p, df.getOWLBottomDataProperty());
        check(LocalityClass.BOTTOM, true, toBottom);
        check(LocalityClass.TOP, false, toBottom); // U_D <= empty is false, not unsupported.
        checkBoth(false, toBottom, p);
    }

    @Tag("regression")
    @Test
    void additionalEquivalentDataPropertiesKeepEveryInclusionAfterCollisions() {
        OWLDataProperty q = additionalDataProperty("q");
        OWLDataProperty s = additionalDataProperty("s");
        OWLAxiom pair = df.getOWLEquivalentDataPropertiesAxiom(p, q);
        OWLAxiom triple = df.getOWLEquivalentDataPropertiesAxiom(p, q, s);
        checkBoth(true, pair);
        checkBoth(true, triple);
        checkBoth(false, pair, p);
        checkBoth(false, pair, q);
        checkBoth(false, pair, p, q);
        checkBoth(false, triple, p);
        checkBoth(false, triple, p, q);
        OWLAxiom withBottom = df.getOWLEquivalentDataPropertiesAxiom(
                p, df.getOWLBottomDataProperty());
        check(LocalityClass.BOTTOM, true, withBottom);
        check(LocalityClass.TOP, false, withBottom);
        checkBoth(false, withBottom, p);
    }

    @Tag("regression")
    @Test
    void additionalDisjointDataPropertiesDoNotDropOtherPairsOrCollapsedPairs() {
        OWLDataProperty q = additionalDataProperty("q");
        OWLDataProperty s = additionalDataProperty("s");
        OWLAxiom pair = df.getOWLDisjointDataPropertiesAxiom(p, q);
        check(LocalityClass.BOTTOM, true, pair);
        check(LocalityClass.TOP, false, pair);
        check(LocalityClass.BOTTOM, true, pair, p);
        check(LocalityClass.TOP, false, pair, p);
        check(LocalityClass.BOTTOM, true, pair, q);
        check(LocalityClass.TOP, false, pair, q);
        checkBoth(false, pair, p, q);
        OWLAxiom triple = df.getOWLDisjointDataPropertiesAxiom(p, q, s);
        check(LocalityClass.BOTTOM, true, triple);
        check(LocalityClass.TOP, false, triple);
        checkBoth(false, triple, p, q); // Empty s does not remove Disjoint(p,q).
        checkBoth(true, df.getOWLDisjointDataPropertiesAxiom(
                p, df.getOWLBottomDataProperty()), p);
        checkBoth(false, df.getOWLDisjointDataPropertiesAxiom(
                p, q, df.getOWLBottomDataProperty()), p, q);
    }

    @Tag("regression")
    @Test
    void additionalDataPropertyDomainsRespectBothPropertyAndClassSubstitution() {
        OWLAxiom domain = df.getOWLDataPropertyDomainAxiom(p, a);
        checkBoth(true, domain);
        check(LocalityClass.BOTTOM, true, domain, a);
        check(LocalityClass.TOP, false, domain, a);
        check(LocalityClass.BOTTOM, false, domain, p);
        check(LocalityClass.TOP, true, domain, p);
        checkBoth(false, domain, p, a);
        checkBoth(true, df.getOWLDataPropertyDomainAxiom(p, df.getOWLThing()), p);
        checkBoth(true, df.getOWLDataPropertyDomainAxiom(df.getOWLBottomDataProperty(), a), a);
        OWLAxiom impossibleDomain = df.getOWLDataPropertyDomainAxiom(p, df.getOWLNothing());
        check(LocalityClass.BOTTOM, true, impossibleDomain);
        check(LocalityClass.TOP, false, impossibleDomain);
        checkBoth(false, impossibleDomain, p);
    }

    @Tag("regression")
    @TestFactory
    Stream<DynamicTest> additionalDataPropertyRangesUseWholeAxiomValidity() {
        List<DynamicTest> tests = new ArrayList<>();
        for (AdditionalRangeCase range : additionalFixedRanges()) {
            OWLAxiom axiom = df.getOWLDataPropertyRangeAxiom(p, range.range);
            tests.add(dynamicTest("range/absent/" + range.name, () -> {
                check(LocalityClass.BOTTOM, true, axiom);
                check(LocalityClass.TOP, range.universal, axiom);
            }));
            tests.add(dynamicTest("range/retained/" + range.name,
                    () -> checkBoth(range.universal, axiom, p)));
            tests.add(dynamicTest("range/bottom/" + range.name, () -> checkBoth(true,
                    df.getOWLDataPropertyRangeAxiom(df.getOWLBottomDataProperty(), range.range))));
        }
        return tests.stream();
    }

    @Tag("regression")
    @Tag("datatype-domain")
    @Test
    void additionalVariableBuiltinUnionIsNotAValidUniversalPropertyRange() {
        // This whole-axiom decision is false even though B covers some data domains.
        OWLAxiom axiom = df.getOWLDataPropertyRangeAxiom(p, additionalBuiltInValueUnion());
        check(LocalityClass.TOP, false, axiom);
        check(LocalityClass.BOTTOM, true, axiom);
        checkBoth(false, axiom, p);
    }

    @Tag("regression")
    @Test
    void additionalDataAssertionsAndTheirNegationsHaveOppositeAbsentPropertyResults() {
        OWLAxiom positive = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        OWLAxiom negative = df.getOWLNegativeDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        check(LocalityClass.BOTTOM, false, positive);
        check(LocalityClass.TOP, true, positive);
        check(LocalityClass.BOTTOM, true, negative);
        check(LocalityClass.TOP, false, negative);
        checkBoth(false, positive, p, i);
        checkBoth(false, negative, p, i);
        checkBoth(false, df.getOWLDataPropertyAssertionAxiom(
                df.getOWLBottomDataProperty(), i, df.getOWLLiteral(1)));
        checkBoth(true, df.getOWLNegativeDataPropertyAssertionAxiom(
                df.getOWLBottomDataProperty(), i, df.getOWLLiteral(1)));
    }

    @Tag("regression")
    @Test
    void additionalPositiveAnonymousDataAssertionIsNotAutomaticallyLocal() {
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("additional-data-subject");
        OWLAxiom assertion = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
        check(LocalityClass.BOTTOM, false, assertion);
        check(LocalityClass.TOP, true, assertion);
        checkBoth(false, assertion, p);
        // Deliberately no negative assertion with an anonymous subject: that is
        // outside OWL 2 DL and is not a valid-input semantic regression.
    }

    @Tag("regression")
    @Test
    void additionalFunctionalDataPropertiesDistinguishEmptyFullAndRetainedProperties() {
        OWLAxiom functionality = df.getOWLFunctionalDataPropertyAxiom(p);
        check(LocalityClass.BOTTOM, true, functionality);
        check(LocalityClass.TOP, false, functionality);
        checkBoth(false, functionality, p);
        checkBoth(true, df.getOWLFunctionalDataPropertyAxiom(df.getOWLBottomDataProperty()));
    }

// -------------------------------------------------------------------------
// Data properties in keys: universal components, empty components, mixed keys,
// recursive class expressions, and absence of a unique-name assumption.
// -------------------------------------------------------------------------

    @Tag("regression")
    @Test
    void additionalTopDataKeyOnNothingIsVacuous() {
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(df.getOWLNothing(), p));
    }

    @Tag("regression")
    @Test
    void additionalTopDataKeyOnSingletonIsLocal() {
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(df.getOWLObjectOneOf(i), p), i);
    }

    @Tag("regression")
    @Test
    void additionalTopDataKeyOnThingIsNotLocal() {
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(df.getOWLThing(), p));
    }

    @Tag("regression")
    @Test
    void additionalTopDataKeyOnTwoNominalsIsNotLocal() {
        OWLNamedIndividual j = df.getOWLNamedIndividual(IRI.create("urn:semantic-locality:additional#j"));
        check(LocalityClass.TOP, false,
                df.getOWLHasKeyAxiom(df.getOWLObjectOneOf(i, j), p), i, j);
    }

    @Tag("regression")
    @Test
    void additionalRemovingAllUniversalDataKeyComponentsMustNotEraseTheKey() {
        OWLDataProperty q = additionalDataProperty("q");
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(df.getOWLThing(), p, q));
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(df.getOWLObjectOneOf(i), p, q), i);
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(a, p), a);
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(a, p));
        check(LocalityClass.BOTTOM, true, df.getOWLHasKeyAxiom(a, p));
    }

    @Tag("regression")
    @Test
    void additionalEmptyKeyComponentMakesEvenAMixedTopDataKeyVacuous() {
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(
                df.getOWLThing(), df.getOWLBottomDataProperty(), p));
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(
                df.getOWLThing(), df.getOWLBottomObjectProperty(), p));
        check(LocalityClass.BOTTOM, true, df.getOWLHasKeyAxiom(df.getOWLThing(), r, p), p);
        check(LocalityClass.BOTTOM, true, df.getOWLHasKeyAxiom(df.getOWLThing(), r, p), r);
    }

    @Tag("regression")
    @Test
    void additionalRetainedKeyComponentsRemainSignificantBesideUniversalDataComponents() {
        OWLDataProperty q = additionalDataProperty("q");
        OWLClassExpression noQValues = df.getOWLDataMaxCardinality(0, q);
        OWLClassExpression someQValue = df.getOWLDataSomeValuesFrom(q, df.getTopDatatype());
        // A q-key cannot match two individuals with zero q-values.
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(noQValues, q, p), q);
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(someQValue, q, p), q);
        check(LocalityClass.TOP, false, df.getOWLHasKeyAxiom(df.getOWLThing(), r, p), r);
        check(LocalityClass.TOP, true, df.getOWLHasKeyAxiom(df.getOWLObjectOneOf(i), r, p), r, i);
        checkBoth(false, df.getOWLHasKeyAxiom(df.getOWLThing(), r, p), r, p);
    }

// -------------------------------------------------------------------------
// Preservation of TOP object-counting proxies while repairing data rewrites.
// -------------------------------------------------------------------------

    @Tag("preservation")
    @Test
    void additionalGlobalObjectCountsRespectNominalsWithoutUniqueNames() {
        OWLNamedIndividual j = df.getOWLNamedIndividual(IRI.create("urn:semantic-locality:additional#j"));
        OWLClassExpression oneOrTwo = df.getOWLObjectOneOf(i, j);
        checkExtension(LocalityClass.TOP, df.getOWLObjectMaxCardinality(2, r, oneOrTwo), true, false, i, j);
        checkExtension(LocalityClass.TOP, df.getOWLObjectMinCardinality(3, r, oneOrTwo), false, true, i, j);
        checkExtension(LocalityClass.TOP, df.getOWLObjectMinCardinality(2, r, oneOrTwo), false, false, i, j);
        checkExtension(LocalityClass.TOP, df.getOWLObjectExactCardinality(1, r, oneOrTwo), false, false, i, j);
        checkExtension(LocalityClass.TOP, df.getOWLObjectExactCardinality(2, r, oneOrTwo), false, false, i, j);
    }

    @Tag("preservation")
    @Test
    void additionalGlobalObjectCountProxiesKeepTheirComplementAndExactRelationships() {
        OWLClassExpression minTwo = df.getOWLObjectMinCardinality(2, r, a);
        OWLClassExpression maxOne = df.getOWLObjectMaxCardinality(1, r, a);
        checkExtension(LocalityClass.TOP, minTwo, false, false, a);
        checkExtension(LocalityClass.TOP, maxOne, false, false, a);
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                maxOne, df.getOWLObjectComplementOf(minTwo)), a);
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectExactCardinality(2, r, a),
                df.getOWLObjectIntersectionOf(minTwo, df.getOWLObjectMaxCardinality(2, r, a))), a);
        check(LocalityClass.TOP, false, df.getOWLSubClassOfAxiom(
                df.getOWLObjectMinCardinality(1, r, a), minTwo), a);
    }

    @Tag("preservation")
    @Test
    void additionalNestedGlobalObjectCountsDoNotTreatProxiesAsIndependent() {
        OWLClassExpression atLeastTwoA = df.getOWLObjectMinCardinality(2, r, a);
        // The inner expression is empty or the whole domain. If true, the domain
        // already has at least two elements, since A has at least two elements.
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectMinCardinality(2, r, atLeastTwoA), atLeastTwoA), a);
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectMaxCardinality(1, r, atLeastTwoA),
                df.getOWLObjectComplementOf(atLeastTwoA)), a);
    }

    @Tag("preservation")
    @Test
    void additionalDataRestrictionsCanOccurInsideGlobalObjectCardinalityProxies() {
        OWLDatatype bool = additionalDatatype(OWL2Datatype.XSD_BOOLEAN);
        OWLClassExpression allObjects = df.getOWLDataExactCardinality(2, p, bool);
        OWLClassExpression noObjects = df.getOWLDataExactCardinality(3, p, bool);
        check(LocalityClass.TOP, true, df.getOWLEquivalentClassesAxiom(
                df.getOWLObjectMinCardinality(2, r, allObjects),
                df.getOWLObjectMinCardinality(2, r, df.getOWLThing())));
        checkExtension(LocalityClass.TOP,
                df.getOWLObjectMinCardinality(2, r, noObjects), false, true);
        checkExtension(LocalityClass.TOP,
                df.getOWLObjectMaxCardinality(2, r, noObjects), true, false);
        checkExtension(LocalityClass.TOP,
                df.getOWLObjectExactCardinality(2, r, noObjects), false, true);
    }

// -------------------------------------------------------------------------
// Explicit policy tests: no external datatype definitions are available.
// Such axioms cannot be decided: isLocal reports them as non-local (safe for
// modules), and isLocalOrThrow throws UnsupportedOperationException, rather
// than claiming undefined custom datatype inputs are valid OWL 2 DL.
// -------------------------------------------------------------------------

    private static void checkAdditionalUnsupported(LocalityClass kind, OWLAxiom axiom,
                                                   OWLEntity... signature) {
        SemanticLocalityEvaluator evaluator = kind == LocalityClass.BOTTOM ? bottom : top;
        int count = manager.getOntologies().size();
        try {
            assertFalse(evaluator.isLocal(axiom, sig(signature)), axiom.toString());
            assertThrows(UnsupportedOperationException.class,
                    () -> evaluator.isLocalOrThrow(axiom, sig(signature)), axiom.toString());
        } finally {
            assertEquals(count, manager.getOntologies().size(), "Unsupported query leaked an ontology");
            manager.getOntologies().forEach(o -> assertEquals(0, o.getAxiomCount(),
                    "Unsupported query polluted a shared ontology"));
        }
    }

    @Tag("regression")
    @Tag("unsupported-datatype-policy")
    @TestFactory
    Stream<DynamicTest> additionalRetainedDataPropertiesDoNotBypassCustomDatatypeValidation() {
        OWLDatatype custom = df.getOWLDatatype(IRI.create("urn:semantic-locality:additional#Custom"));
        OWLDataProperty q = additionalDataProperty("q");
        List<OWLAxiom> axioms = new ArrayList<>();
        for (OWLDataRange range : Arrays.asList(custom,
                df.getOWLDataComplementOf(custom),
                df.getOWLDataIntersectionOf(custom, additionalDatatype(OWL2Datatype.XSD_BOOLEAN)))) {
            OWLClassExpression[] expressions = {
                    df.getOWLDataSomeValuesFrom(p, range),
                    df.getOWLDataAllValuesFrom(p, range),
                    df.getOWLDataMinCardinality(1, p, range),
                    df.getOWLDataMaxCardinality(1, p, range),
                    df.getOWLDataExactCardinality(1, p, range)
            };
            for (OWLClassExpression expression : expressions) {
                axioms.add(df.getOWLSubClassOfAxiom(df.getOWLThing(), expression));
                axioms.add(df.getOWLClassAssertionAxiom(expression, i));
                axioms.add(df.getOWLObjectPropertyDomainAxiom(r, expression));
                axioms.add(df.getOWLObjectPropertyRangeAxiom(r, expression));
                axioms.add(df.getOWLDataPropertyDomainAxiom(q, expression));
                axioms.add(df.getOWLHasKeyAxiom(expression, q));
            }
            axioms.add(df.getOWLDataPropertyRangeAxiom(p, range));
        }
        List<DynamicTest> tests = new ArrayList<>();
        for (LocalityClass kind : new LocalityClass[] {LocalityClass.BOTTOM, LocalityClass.TOP}) {
            for (OWLAxiom axiom : axioms) {
                tests.add(dynamicTest(kind + "/" + axiom,
                        () -> checkAdditionalUnsupported(kind, axiom, p, q, r, i, custom)));
            }
        }
        return tests.stream();
    }

    @Tag("regression")
    @Tag("unsupported-datatype-policy")
    @Test
    void additionalUndecidableAxiomsAreKeptInModules() {
        OWLDatatype custom = df.getOWLDatatype(IRI.create("urn:semantic-locality:additional#Custom"));
        OWLAxiom definition = df.getOWLDatatypeDefinitionAxiom(custom,
                additionalDatatype(OWL2Datatype.XSD_BOOLEAN));
        OWLAxiom ordinary = df.getOWLSubClassOfAxiom(a, b);
        OWLAxiom unrelated = df.getOWLSubClassOfAxiom(c, d);
        Set<OWLAxiom> source = new HashSet<>(Arrays.asList(definition, ordinary, unrelated));
        // The definition cannot be decided, so it counts as non-local for every
        // signature: extraction keeps it instead of failing. A SubClassOf B is
        // only BOTTOM-non-local for {A}; under TOP, B becomes Thing.
        assertEquals(new HashSet<>(Arrays.asList(definition, ordinary)),
                additionalExtract(LocalityClass.BOTTOM, source, a));
        assertEquals(Collections.singleton(definition),
                additionalExtract(LocalityClass.TOP, source, a));
        assertEquals(Collections.singleton(definition),
                additionalExtract(LocalityClass.STAR, source, a));
    }

    @Tag("regression")
    @Tag("unsupported-datatype-policy")
    @Test
    void additionalDatatypeDefinitionsAreExplicitlyRejectedInBothModes() {
        OWLDatatype custom = df.getOWLDatatype(IRI.create("urn:semantic-locality:additional#Custom"));
        OWLAxiom definition = df.getOWLDatatypeDefinitionAxiom(custom,
                additionalDatatype(OWL2Datatype.XSD_BOOLEAN));
        checkAdditionalUnsupported(LocalityClass.BOTTOM, definition, custom);
        checkAdditionalUnsupported(LocalityClass.TOP, definition, custom);
        checkBoth(true, df.getOWLSubClassOfAxiom(a, a), a);
        checkBoth(false, df.getOWLSubClassOfAxiom(df.getOWLThing(), a), a);
    }

// -------------------------------------------------------------------------
// Integration regression: shared anonymous witnesses in module extraction.
// Uses LocalityModuleExtractor's protected evaluator hooks, so no additional
// evaluators are allocated. This distinguishes isolated-axiom validity from
// extraction correctness; it does NOT demand a false answer for a valid
// isolated existential nominal assertion.
// -------------------------------------------------------------------------

    private static Set<OWLAxiom> additionalExtract(LocalityClass kind, Set<OWLAxiom> source,
                                                   OWLEntity... signature) {
        LocalityModuleExtractor extractor = new LocalityModuleExtractor(kind, source.stream(), df) {
            @Override
            protected LocalityEvaluator bottomEvaluator() {
                return bottom;
            }

            @Override
            protected LocalityEvaluator topEvaluator() {
                return top;
            }
        };
        return extractor.extract(sig(signature).stream(), Optional.empty())
                .collect(Collectors.toSet());
    }

    private static void checkAdditionalEntailment(boolean expected, Set<OWLAxiom> premises,
                                                  OWLAxiom goal) throws OWLOntologyCreationException {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        Set<OWLAxiom> declared = new HashSet<>(premises);
        Stream.concat(premises.stream(), Stream.of(goal))
                .flatMap(OWLAxiom::signature).filter(entity -> !entity.isBuiltIn())
                .map(df::getOWLDeclarationAxiom).forEach(declared::add);
        OWLOntology ontology = ownManager.createOntology(declared);
        OWLReasoner reasoner = null;
        try {
            reasoner = new ReasonerFactory().createReasoner(ontology);
            assertEquals(true, reasoner.isConsistent(), "Entailment fixture must be consistent");
            assertEquals(expected, reasoner.isEntailed(goal),
                    () -> "Entailment of " + goal + " from " + premises);
        } finally {
            try {
                if (reasoner != null) {
                    reasoner.dispose();
                }
            } finally {
                ownManager.removeOntology(ontology);
            }
        }
    }

    @Tag("preservation")
    @Test
    void additionalIsolatedAnonymousNominalAssertionIsExistentiallyValid() {
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("additional-isolated-witness");
        checkBoth(true, df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x), i);
    }

    @Tag("regression")
    @Tag("integration")
    @TestFactory
    Stream<DynamicTest> additionalExtractionPreservesSharedAnonymousDataWitnesses() {
        return Stream.of(LocalityClass.BOTTOM, LocalityClass.TOP).map(kind -> dynamicTest(
                kind + "/shared-anonymous-data-witness", () -> {
                    OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("additional-shared-witness");
                    OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
                    OWLAxiom edge = df.getOWLDataPropertyAssertionAxiom(p, x, df.getOWLLiteral(1));
                    OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
                    Set<OWLAxiom> source = new HashSet<>(Arrays.asList(anchor, edge));
                    checkAdditionalEntailment(true, source, goal);
                    checkAdditionalEntailment(false, new HashSet<>(Arrays.asList(edge)), goal);
                    Set<OWLAxiom> module = additionalExtract(kind, source, i, p);
                    checkAdditionalEntailment(true, module, goal);
                }));
    }

    @Tag("preservation")
    @Tag("integration")
    @Test
    void additionalDifferentAnonymousDataWitnessesAreNotAccidentallyIdentified()
            throws OWLOntologyCreationException {
        OWLAnonymousIndividual x = df.getOWLAnonymousIndividual("additional-witness-x");
        OWLAnonymousIndividual y = df.getOWLAnonymousIndividual("additional-witness-y");
        OWLAxiom anchor = df.getOWLClassAssertionAxiom(df.getOWLObjectOneOf(i), x);
        OWLAxiom edge = df.getOWLDataPropertyAssertionAxiom(p, y, df.getOWLLiteral(1));
        OWLAxiom goal = df.getOWLDataPropertyAssertionAxiom(p, i, df.getOWLLiteral(1));
        checkAdditionalEntailment(false, new HashSet<>(Arrays.asList(anchor, edge)), goal);
    }

}