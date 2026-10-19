package org.semanticweb.owlapi.modularity.comparison;

import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.A;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.B;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.C;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.D;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.F;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.I;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.J;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.K;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.P;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.Q;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.R;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.S;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.T;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.X;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.all;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.and;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.disjoint;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.equivalent;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.exact;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.iri;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.max;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.min;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.not;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.nothing;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.or;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.render;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sig;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.some;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.sub;
import static org.semanticweb.owlapi.modularity.comparison.Vocabulary.thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;

/**
 * Implementation-independent locality cases. Every expectation is the
 * model-theoretic truth (semantic locality), so that syntactic and semantic
 * checkers can be scored on the same table.
 *
 * <p>Categories of the {@link #shared()} table:</p>
 * <ul>
 * <li>{@code soundness}: the axiom is semantically NOT local. Answering
 * "local" is a bug: a module would silently lose a needed axiom.</li>
 * <li>{@code calculus}: the axiom is local and covered by the standard
 * syntactic locality calculus (Cuenca Grau, Horrocks, Kazakov, Sattler; JAIR
 * 2008), lifted to OWL 2 as usual (built-in top/bottom entities, De Morgan,
 * cardinalities, self restrictions, values, data properties, assertions).
 * Answering "non-local" only enlarges modules.</li>
 * <li>{@code non-logical}: declarations and annotation axioms, local by
 * definition.</li>
 * <li>{@code signature}: signature handling (punning, irrelevant entities).</li>
 * </ul>
 *
 * <p>The {@link #beyondCalculus()} table contains semantically local axioms that
 * the standard calculus does NOT recognise (e.g. {@code A SubClassOf A}). An
 * implementation that recognises them produces smaller, still correct modules.
 * These cases are kept out of the shared table and scored separately.</p>
 *
 * <p>Sources: the curated cases of {@code SyntacticLocalityEvaluatorCoverageTest}
 * and {@code SyntacticLocalityEvaluatorRegressionTest}, and the legacy
 * {@code owlapitools} {@code SyntacticLocalityTestCase}. Two expectations of the
 * legacy test are semantically wrong and are corrected here (marked
 * {@code legacy-corrected}).</p>
 */
final class LocalityCases {

    static final String SOUNDNESS = "soundness";
    static final String CALCULUS = "calculus";
    static final String NON_LOGICAL = "non-logical";
    static final String SIGNATURE = "signature";
    static final String BEYOND = "beyond-calculus";

    /** One locality question with its semantic answer. */
    static final class LocalityCase {
        final String id;
        final String category;
        final LocalityClass mode;
        final OWLAxiom axiom;
        final Set<OWLEntity> signature;
        final boolean expected;

        LocalityCase(String id, String category, LocalityClass mode, OWLAxiom axiom,
            Set<OWLEntity> signature, boolean expected) {
            this.id = id;
            this.category = category;
            this.mode = mode;
            this.axiom = axiom;
            this.signature = signature;
            this.expected = expected;
        }

        String describe() {
            return mode + " " + render(axiom) + " wrt " + render(signature) + " expected "
                + (expected ? "local" : "NOT local");
        }
    }

    private static final LocalityClass BOT = LocalityClass.BOTTOM;
    private static final LocalityClass TOP = LocalityClass.TOP;

    private final List<LocalityCase> cases = new ArrayList<>();
    private final Set<String> ids = new HashSet<>();
    private String source = "";

    private LocalityCases() {}

    private void add(String category, LocalityClass mode, boolean expected, OWLAxiom axiom,
        OWLEntity... signature) {
        Set<OWLEntity> sigma = sig(signature);
        for (LocalityCase existing : cases) {
            if (existing.mode == mode && existing.axiom.equals(axiom)
                && existing.signature.equals(sigma)) {
                if (existing.expected != expected) {
                    throw new IllegalStateException("Conflicting expectations for "
                        + existing.describe());
                }
                // The same question from another source: score it once.
                return;
            }
        }
        // Punned entities render alike, e.g. the class r and the property r.
        String sigText = Arrays.stream(signature).sorted()
            .map(e -> (e.isOWLClass() && !e.isBuiltIn() && !e.getIRI().getRemainder()
                .orElse("").matches("[A-Z].*") ? "Class:" : "") + render(e))
            .collect(java.util.stream.Collectors.joining(", ", "{", "}"));
        String base = source + ":" + mode.name().toLowerCase() + ":" + render(axiom) + " / "
            + sigText;
        String id = base;
        for (int n = 2; !ids.add(id); n++) {
            id = base + " #" + n;
        }
        cases.add(new LocalityCase(id, category, mode, axiom, sigma, expected));
    }

    private void local(String category, LocalityClass mode, OWLAxiom axiom,
        OWLEntity... signature) {
        add(category, mode, true, axiom, signature);
    }

    private void both(String category, boolean expected, OWLAxiom axiom,
        OWLEntity... signature) {
        add(category, BOT, expected, axiom, signature);
        add(category, TOP, expected, axiom, signature);
    }

    private void nonLocal(LocalityClass mode, OWLAxiom axiom, OWLEntity... signature) {
        add(SOUNDNESS, mode, false, axiom, signature);
    }

    private static final OWLDataProperty U = F.getOWLDataProperty(iri("u"));
    private static final OWLDatatype CUSTOM = F.getOWLDatatype(iri("DT"));
    private static final OWLAnnotationProperty NOTE = F.getOWLAnnotationProperty(iri("note"));
    private static final OWLAnnotationProperty NOTE2 = F.getOWLAnnotationProperty(iri("note2"));

    /** The table every implementation is scored on. */
    static List<LocalityCase> shared() {
        LocalityCases t = new LocalityCases();
        t.nonLogical();
        t.bottomCalculus();
        t.topCalculus();
        t.soundness();
        t.signatureHandling();
        t.regressions();
        t.legacyOwlapitools();
        return Collections.unmodifiableList(t.cases);
    }

    /** Semantically local axioms outside the standard calculus. */
    static List<LocalityCase> beyondCalculus() {
        LocalityCases t = new LocalityCases();
        t.source = "beyond";
        for (LocalityClass mode : new LocalityClass[] {BOT, TOP}) {
            t.local(BEYOND, mode, sub(A, A), A);
            t.local(BEYOND, mode, sub(and(A, B), A), A, B);
            t.local(BEYOND, mode, sub(A, or(A, B)), A, B);
            t.local(BEYOND, mode, sub(and(A, not(A)), B), A, B);
            t.local(BEYOND, mode, sub(B, or(A, not(A))), A, B);
            t.local(BEYOND, mode, disjoint(A, not(A)), A);
            t.local(BEYOND, mode, sub(some(R, A), some(R, thing())), R, A);
            t.local(BEYOND, mode, sub(all(R, A), all(R, or(A, B))), R, A, B);
            t.local(BEYOND, mode, sub(A, min(0, R, B)), A, R, B);
            t.local(BEYOND, mode, F.getOWLClassAssertionAxiom(or(A, not(A)), I), A);
            t.local(BEYOND, mode, equivalent(A), A);
            t.local(BEYOND, mode, F.getOWLEquivalentObjectPropertiesAxiom(R), R);
            t.local(BEYOND, mode, F.getOWLEquivalentDataPropertiesAxiom(P), P);
            t.local(BEYOND, mode, F.getOWLSubObjectPropertyOfAxiom(R, R), R);
            t.local(BEYOND, mode,
                F.getOWLSubObjectPropertyOfAxiom(R.getInverseProperty(), R.getInverseProperty()),
                R);
            t.local(BEYOND, mode, F.getOWLSubDataPropertyOfAxiom(P, P), P);
            t.local(BEYOND, mode, F.getOWLInverseObjectPropertiesAxiom(R, R.getInverseProperty()),
                R);
            t.local(BEYOND, mode,
                F.getOWLObjectPropertyAssertionAxiom(F.getOWLTopObjectProperty(), I, J));
            t.local(BEYOND, mode, F.getOWLSubObjectPropertyOfAxiom(R, F.getOWLTopObjectProperty()),
                R);
        }
        // Keys over empty properties can never fire.
        t.local(BEYOND, BOT, F.getOWLHasKeyAxiom(A, set(R, P)), A);
        // A SWRL rule with an empty body atom can never fire.
        Set<SWRLAtom> head = set(F.getSWRLClassAtom(A, F.getSWRLIndividualArgument(I)));
        Set<SWRLAtom> body = set(F.getSWRLClassAtom(B, F.getSWRLIndividualArgument(J)));
        t.local(BEYOND, BOT, F.getSWRLRule(body, head), A);
        t.local(BEYOND, BOT, F.getSWRLRule(body, head), D);
        return Collections.unmodifiableList(t.cases);
    }

    @SafeVarargs
    private static <E> Set<E> set(E... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    private void nonLogical() {
        source = "coverage";
        List<OWLAxiom> axioms = Arrays.asList(F.getOWLDeclarationAxiom(A),
            F.getOWLDeclarationAxiom(R),
            F.getOWLAnnotationAssertionAxiom(F.getRDFSLabel(), A.getIRI(), F.getOWLLiteral("A")),
            F.getOWLSubAnnotationPropertyOfAxiom(NOTE, F.getRDFSLabel()),
            F.getOWLAnnotationPropertyDomainAxiom(NOTE, iri("target")),
            F.getOWLAnnotationPropertyRangeAxiom(NOTE, iri("target")));
        for (OWLAxiom axiom : axioms) {
            both(NON_LOGICAL, true, axiom);
            both(NON_LOGICAL, true, axiom, A, B, R, P, I);
        }
    }

    private void bottomCalculus() {
        source = "coverage";
        // Sigma = {A}: B, C, r, p are absent, hence empty.
        for (OWLClassExpression e : Arrays.asList(B, nothing(), not(thing()), not(not(B)),
            and(A, B), or(B, C), some(R, A), some(R.getInverseProperty(), A), min(1, R, A),
            min(2, R, A), exact(1, R, A), F.getOWLObjectHasSelf(R), F.getOWLObjectHasValue(R, I),
            F.getOWLDataSomeValuesFrom(P, F.getTopDatatype()), F.getOWLDataMinCardinality(1, P),
            F.getOWLDataExactCardinality(1, P), F.getOWLDataHasValue(P, X))) {
            local(CALCULUS, BOT, sub(e, A), A);
            local(CALCULUS, BOT, disjoint(e, A), A);
        }
        for (OWLClassExpression e : Arrays.asList(thing(), not(B), not(nothing()), or(A, not(B)),
            and(not(B), not(C)), not(some(R, A)), all(R, A), max(1, R, A),
            F.getOWLDataAllValuesFrom(P, F.getStringOWLDatatype()),
            F.getOWLDataMaxCardinality(1, P))) {
            local(CALCULUS, BOT, sub(A, e), A);
        }
        for (OWLClassExpression e : Arrays.asList(min(0, R, A), exact(0, R, A), max(0, R, A),
            F.getOWLDataMinCardinality(0, P), F.getOWLDataExactCardinality(0, P),
            F.getOWLDataMaxCardinality(0, P))) {
            local(CALCULUS, BOT, sub(A, e), A);
        }
        // Sigma = {A, r}: the role is kept, but B is empty.
        for (OWLClassExpression e : Arrays.asList(some(R, B), min(1, R, B), min(2, R, B),
            exact(1, R, B))) {
            local(CALCULUS, BOT, sub(e, A), A, R);
        }
        for (OWLClassExpression e : Arrays.asList(all(R, not(B)), max(0, R, B), max(1, R, B))) {
            local(CALCULUS, BOT, sub(A, e), A, R);
        }
        local(CALCULUS, BOT, equivalent(B, and(C, A)), A);
        local(CALCULUS, BOT, equivalent(B, C, D));
        local(CALCULUS, BOT, disjoint(B, C, A), A);
        local(CALCULUS, BOT, F.getOWLDisjointUnionAxiom(B, set(C, D)));
        local(CALCULUS, BOT, F.getOWLClassAssertionAxiom(not(B), I));
        local(CALCULUS, BOT, F.getOWLHasKeyAxiom(B, set(R)));
        local(CALCULUS, BOT, F.getOWLSubObjectPropertyOfAxiom(R, S), S);
        local(CALCULUS, BOT, F.getOWLSubObjectPropertyOfAxiom(R.getInverseProperty(), S), S);
        local(CALCULUS, BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), S, T);
        local(CALCULUS, BOT, F.getOWLSubDataPropertyOfAxiom(P, Q), Q);
        local(CALCULUS, BOT, F.getOWLEquivalentObjectPropertiesAxiom(R, S));
        local(CALCULUS, BOT, F.getOWLEquivalentDataPropertiesAxiom(P, Q));
        local(CALCULUS, BOT, F.getOWLDisjointObjectPropertiesAxiom(R, S), S);
        local(CALCULUS, BOT, F.getOWLDisjointDataPropertiesAxiom(P, Q), Q);
        local(CALCULUS, BOT, F.getOWLInverseObjectPropertiesAxiom(R, S));
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(R, A), A);
        local(CALCULUS, BOT, F.getOWLObjectPropertyRangeAxiom(R, A), A);
        local(CALCULUS, BOT, F.getOWLDataPropertyDomainAxiom(P, A), A);
        local(CALCULUS, BOT, F.getOWLDataPropertyRangeAxiom(P, F.getStringOWLDatatype()));
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(R, not(B)), R);
        for (OWLAxiom c : Arrays.asList(F.getOWLFunctionalObjectPropertyAxiom(R),
            F.getOWLInverseFunctionalObjectPropertyAxiom(R),
            F.getOWLSymmetricObjectPropertyAxiom(R), F.getOWLTransitiveObjectPropertyAxiom(R),
            F.getOWLIrreflexiveObjectPropertyAxiom(R), F.getOWLAsymmetricObjectPropertyAxiom(R),
            F.getOWLFunctionalDataPropertyAxiom(P))) {
            local(CALCULUS, BOT, c);
        }
        local(CALCULUS, BOT, F.getOWLNegativeObjectPropertyAssertionAxiom(R, I, J));
        local(CALCULUS, BOT, F.getOWLNegativeDataPropertyAssertionAxiom(P, I, X));
        // Only built-in constants make these local.
        both(CALCULUS, true, sub(A, thing()), A);
        both(CALCULUS, true, sub(nothing(), A), A);
        both(CALCULUS, true, disjoint(A, nothing()), A);
        both(CALCULUS, true, F.getOWLClassAssertionAxiom(thing(), I));
    }

    private void topCalculus() {
        source = "coverage";
        // Sigma = {A}: B, C are Thing; r, p are universal.
        for (OWLClassExpression e : Arrays.asList(B, thing(), not(nothing()), not(not(B)),
            or(A, B), and(B, C), some(R, B), some(R, thing()), some(R.getInverseProperty(), B),
            min(1, R, B), F.getOWLObjectHasSelf(R), F.getOWLObjectHasValue(R, I),
            F.getOWLDataSomeValuesFrom(P, F.getTopDatatype()), F.getOWLDataMinCardinality(1, P),
            F.getOWLDataHasValue(P, X))) {
            local(CALCULUS, TOP, sub(A, e), A);
        }
        for (OWLClassExpression e : Arrays.asList(nothing(), not(B), not(thing()), and(A, not(B)),
            or(not(B), not(C)), all(R, not(B)), max(0, R, B), F.getOWLDataMaxCardinality(0, P))) {
            local(CALCULUS, TOP, sub(e, A), A);
            local(CALCULUS, TOP, disjoint(e, A), A);
        }
        // Sigma = {A, r}: the role is kept, but B is Thing.
        for (OWLClassExpression e : Arrays.asList(all(R, B), max(1, R, not(B)),
            max(0, R, not(B)))) {
            local(CALCULUS, TOP, sub(A, e), A, R);
        }
        for (OWLClassExpression e : Arrays.asList(some(R, not(B)), min(1, R, not(B)),
            exact(1, R, not(B)))) {
            local(CALCULUS, TOP, sub(e, A), A, R);
        }
        local(CALCULUS, TOP, equivalent(B, or(C, A)), A);
        local(CALCULUS, TOP, equivalent(B, C, D));
        local(CALCULUS, TOP, disjoint(not(B), not(C), A), A);
        local(CALCULUS, TOP, F.getOWLClassAssertionAxiom(B, I));
        local(CALCULUS, TOP, F.getOWLClassAssertionAxiom(some(R, B), I));
        local(CALCULUS, TOP, F.getOWLSubObjectPropertyOfAxiom(R, S), R);
        local(CALCULUS, TOP, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), R, S);
        local(CALCULUS, TOP, F.getOWLSubDataPropertyOfAxiom(P, Q), P);
        local(CALCULUS, TOP, F.getOWLEquivalentObjectPropertiesAxiom(R, S));
        local(CALCULUS, TOP, F.getOWLEquivalentDataPropertiesAxiom(P, Q));
        local(CALCULUS, TOP, F.getOWLInverseObjectPropertiesAxiom(R, S));
        local(CALCULUS, TOP, F.getOWLObjectPropertyDomainAxiom(R, B), R);
        local(CALCULUS, TOP, F.getOWLObjectPropertyRangeAxiom(R, B), R);
        local(CALCULUS, TOP, F.getOWLDataPropertyDomainAxiom(P, B), P);
        local(CALCULUS, TOP, F.getOWLDataPropertyRangeAxiom(P, F.getTopDatatype()), P);
        for (OWLAxiom c : Arrays.asList(F.getOWLReflexiveObjectPropertyAxiom(R),
            F.getOWLSymmetricObjectPropertyAxiom(R), F.getOWLTransitiveObjectPropertyAxiom(R))) {
            local(CALCULUS, TOP, c);
        }
        local(CALCULUS, TOP, F.getOWLObjectPropertyAssertionAxiom(R, I, J));
        local(CALCULUS, TOP, F.getOWLDataPropertyAssertionAxiom(P, I, X));
    }

    private void soundness() {
        source = "coverage";
        nonLocal(BOT, disjoint(thing(), A), A);
        nonLocal(BOT, sub(thing(), A), A);
        nonLocal(TOP, equivalent(nothing(), A), A);
        nonLocal(TOP, sub(A, nothing()), A);
        nonLocal(BOT, sub(some(F.getOWLTopObjectProperty(), thing()), A), A);
        nonLocal(BOT, sub(F.getOWLDataSomeValuesFrom(F.getOWLTopDataProperty(), F.getTopDatatype()),
            A), A);
        nonLocal(TOP, sub(A, some(F.getOWLBottomObjectProperty(), thing())), A);
        nonLocal(TOP, sub(A, F.getOWLDataSomeValuesFrom(F.getOWLBottomDataProperty(),
            F.getTopDatatype())), A);
        nonLocal(TOP, F.getOWLObjectPropertyAssertionAxiom(F.getOWLBottomObjectProperty(), I, J));
        nonLocal(BOT,
            F.getOWLNegativeObjectPropertyAssertionAxiom(F.getOWLTopObjectProperty(), I, J));
        // Nominals are neither empty nor full.
        nonLocal(BOT, sub(F.getOWLObjectOneOf(I), A), A);
        nonLocal(BOT, disjoint(F.getOWLObjectOneOf(I), A), A);
        nonLocal(TOP, sub(thing(), F.getOWLObjectOneOf(I)));
        nonLocal(TOP, equivalent(F.getOWLObjectOneOf(I), thing()));
        both(SOUNDNESS, false, disjoint(F.getOWLObjectOneOf(I), F.getOWLObjectOneOf(J)));
        // Restrictions on retained roles are not constants.
        nonLocal(BOT, disjoint(F.getOWLObjectHasSelf(R), A), R, A);
        nonLocal(BOT, disjoint(F.getOWLObjectHasValue(R, I), A), R, A);
        nonLocal(BOT, disjoint(F.getOWLDataHasValue(P, X), A), P, A);
        nonLocal(TOP, equivalent(F.getOWLObjectHasSelf(R), thing()), R);
        nonLocal(TOP, equivalent(F.getOWLObjectHasValue(R, I), thing()), R);
        nonLocal(TOP, equivalent(F.getOWLDataHasValue(P, X), thing()), P);
        // Over an empty role these denote Thing, so "X SubClassOf A" fails.
        for (OWLClassExpression e : Arrays.asList(min(0, R, B), exact(0, R, B), max(1, R, B),
            all(R, A), F.getOWLDataMinCardinality(0, P), F.getOWLDataExactCardinality(0, P),
            F.getOWLDataMaxCardinality(1, P),
            F.getOWLDataAllValuesFrom(P, F.getTopDatatype()))) {
            nonLocal(BOT, sub(e, A), A);
        }
        // Counting over the universal role depends on the domain size.
        for (OWLClassExpression e : Arrays.asList(min(2, R, B), max(1, R, B), exact(1, R, B),
            max(0, R, B), F.getOWLDataMaxCardinality(0, P))) {
            nonLocal(TOP, sub(A, e), A);
        }
        nonLocal(TOP, sub(min(2, R, B), A), A);
        nonLocal(TOP, sub(A, all(R, A)), A);
        nonLocal(TOP, sub(some(R, A), A), A);
        nonLocal(TOP, sub(some(R.getInverseProperty(), A), A), A);
        nonLocal(BOT, sub(all(R, A), A), A);
        nonLocal(BOT, sub(A, some(R.getInverseProperty(), A)), A);
        // The data domain is fixed.
        OWLDataRange single = F.getOWLDataOneOf(F.getOWLLiteral("a"));
        OWLDataRange empty = F.getOWLDatatypeMinMaxInclusiveRestriction(5, 3);
        nonLocal(TOP, sub(thing(), F.getOWLDataMinCardinality(2, P, single)));
        nonLocal(TOP, sub(thing(), F.getOWLDataSomeValuesFrom(P, empty)));
        nonLocal(TOP, sub(A, F.getOWLDataAllValuesFrom(P, F.getStringOWLDatatype())), A);
        nonLocal(TOP, F.getOWLDataPropertyRangeAxiom(P, F.getStringOWLDatatype()));
        nonLocal(TOP, F.getOWLDataPropertyRangeAxiom(P, single));
        nonLocal(TOP, F.getOWLFunctionalDataPropertyAxiom(P));
        // Operands that collapse onto each other keep their constraint.
        nonLocal(TOP, disjoint(B, C));
        nonLocal(BOT, disjoint(not(B), not(C)));
        both(SOUNDNESS, false, disjoint(A, or(A, B)), A);
        nonLocal(TOP, disjoint(A, and(A, B)), A);
        nonLocal(TOP, disjoint(A, and(A, B), not(A)), A);
        both(SOUNDNESS, false, disjoint(A, B, C, D), A, B);
        nonLocal(TOP, disjoint(A, B), A);
        both(SOUNDNESS, false, equivalent(A, B), A);
        both(SOUNDNESS, false, equivalent(A, B, C), A);
        both(SOUNDNESS, false, equivalent(A, B, C, D), A, B);
        both(SOUNDNESS, false, equivalent(A, and(A, B), or(A, B)), A);
        nonLocal(TOP, equivalent(A, or(A, B), and(A, not(B))), A);
        nonLocal(TOP, F.getOWLDisjointUnionAxiom(A, set(B, C)));
        nonLocal(BOT, F.getOWLDisjointUnionAxiom(A, set(B, C)), A);
        nonLocal(BOT, F.getOWLSubObjectPropertyOfAxiom(R, S), R);
        nonLocal(TOP, F.getOWLSubObjectPropertyOfAxiom(R, S), S);
        nonLocal(BOT, F.getOWLSubDataPropertyOfAxiom(P, Q), P);
        nonLocal(TOP, F.getOWLSubDataPropertyOfAxiom(P, Q), Q);
        nonLocal(BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), R, S);
        nonLocal(TOP, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), S, T);
        both(SOUNDNESS, false, F.getOWLEquivalentObjectPropertiesAxiom(R, S), R);
        both(SOUNDNESS, false, F.getOWLInverseObjectPropertiesAxiom(R, S), R);
        nonLocal(TOP, F.getOWLDisjointObjectPropertiesAxiom(R, S));
        nonLocal(TOP, F.getOWLDisjointObjectPropertiesAxiom(R, S), R);
        nonLocal(TOP, F.getOWLDisjointDataPropertiesAxiom(P, Q));
        both(SOUNDNESS, false, F.getOWLDisjointObjectPropertiesAxiom(R, S), R, S);
        nonLocal(TOP, F.getOWLObjectPropertyDomainAxiom(R, A), A);
        nonLocal(TOP, F.getOWLObjectPropertyRangeAxiom(R, A), A);
        nonLocal(TOP, F.getOWLDataPropertyDomainAxiom(P, A), A);
        nonLocal(BOT, F.getOWLObjectPropertyDomainAxiom(R, B), R);
        nonLocal(BOT, F.getOWLObjectPropertyRangeAxiom(R, B), R);
        nonLocal(BOT, F.getOWLDataPropertyDomainAxiom(P, B), P);
        both(SOUNDNESS, false, F.getOWLObjectPropertyDomainAxiom(R, A), R, A);
        nonLocal(BOT, F.getOWLReflexiveObjectPropertyAxiom(R));
        for (OWLAxiom axiom : Arrays.asList(F.getOWLIrreflexiveObjectPropertyAxiom(R),
            F.getOWLAsymmetricObjectPropertyAxiom(R), F.getOWLFunctionalObjectPropertyAxiom(R),
            F.getOWLInverseFunctionalObjectPropertyAxiom(R))) {
            nonLocal(TOP, axiom);
        }
        for (OWLAxiom axiom : Arrays.asList(F.getOWLReflexiveObjectPropertyAxiom(R),
            F.getOWLIrreflexiveObjectPropertyAxiom(R), F.getOWLAsymmetricObjectPropertyAxiom(R),
            F.getOWLSymmetricObjectPropertyAxiom(R), F.getOWLTransitiveObjectPropertyAxiom(R),
            F.getOWLFunctionalObjectPropertyAxiom(R))) {
            both(SOUNDNESS, false, axiom, R);
        }
        nonLocal(BOT, F.getOWLClassAssertionAxiom(B, I));
        nonLocal(TOP, F.getOWLClassAssertionAxiom(not(B), I));
        nonLocal(BOT, F.getOWLObjectPropertyAssertionAxiom(R, I, J));
        nonLocal(BOT, F.getOWLObjectPropertyAssertionAxiom(R.getInverseProperty(), I, J));
        nonLocal(TOP, F.getOWLNegativeObjectPropertyAssertionAxiom(R, I, J));
        nonLocal(BOT, F.getOWLDataPropertyAssertionAxiom(P, I, X));
        nonLocal(TOP, F.getOWLNegativeDataPropertyAssertionAxiom(P, I, X));
        both(SOUNDNESS, false, F.getOWLClassAssertionAxiom(A, I), A);
        both(SOUNDNESS, false, F.getOWLObjectPropertyAssertionAxiom(R, I, J), R);
        both(SOUNDNESS, false, F.getOWLSameIndividualAxiom(I, J));
        both(SOUNDNESS, false, F.getOWLDifferentIndividualsAxiom(I, J));
        both(SOUNDNESS, false, F.getOWLSameIndividualAxiom(I, J), A, R, P);
    }

    private void signatureHandling() {
        source = "coverage";
        // A class punned with r's IRI does not retain the property r, and vice versa.
        OWLClass classR = F.getOWLClass(R.getIRI());
        OWLAxiom someR = sub(some(R, thing()), A);
        add(SIGNATURE, BOT, true, someR, A, classR);
        add(SIGNATURE, BOT, false, someR, A, R);
        add(SIGNATURE, BOT, true, disjoint(classR, A), A, R);
        add(SIGNATURE, TOP, false, disjoint(classR, A), A, R);
        OWLEntity[] extended = {A, C, D, S, Q, I, thing(), nothing(), F.getOWLTopObjectProperty(),
            F.getOWLBottomObjectProperty(), F.getOWLTopDataProperty(),
            F.getOWLBottomDataProperty()};
        for (OWLEntity[] signature : Arrays.asList(new OWLEntity[] {A}, extended)) {
            add(SIGNATURE, BOT, true, disjoint(A, B), signature);
            add(SIGNATURE, TOP, false, disjoint(A, B), signature);
            add(SIGNATURE, BOT, false, sub(A, some(R, thing())), signature);
            add(SIGNATURE, TOP, true, sub(A, some(R, thing())), signature);
        }
        OWLAxiom annotated = disjoint(A, B).getAnnotatedAxiom(Collections.singleton(
            F.getOWLAnnotation(F.getRDFSComment(), F.getOWLLiteral("note"))));
        add(SIGNATURE, BOT, true, annotated, A);
        add(SIGNATURE, TOP, false, annotated, A);
        both(SIGNATURE, false, annotated, A, B);
    }

    private void regressions() {
        source = "regression";
        both(SOUNDNESS, false, sub(and(A, B), nothing()), A, B);
        both(SOUNDNESS, false, sub(or(nothing(), A), nothing()), A);
        nonLocal(BOT, sub(thing(), F.getOWLDataMinCardinality(1, P)));
        local(CALCULUS, BOT, sub(F.getOWLDataSomeValuesFrom(P, F.getTopDatatype()), nothing()));
        nonLocal(TOP, sub(thing(), min(2, R, thing())));
        nonLocal(TOP, sub(max(1, R, thing()), nothing()));
        local(CALCULUS, TOP, sub(max(0, R, thing()), nothing()));
        nonLocal(TOP, sub(exact(1, R, thing()), nothing()));
        OWLDataRange string = F.getStringOWLDatatype();
        nonLocal(TOP, sub(F.getOWLDataAllValuesFrom(P,
            F.getOWLDataUnionOf(string, F.getOWLDataComplementOf(string))), nothing()));
        local(CALCULUS, TOP,
            sub(F.getOWLDataMaxCardinality(1, P, F.getBooleanOWLDatatype()), nothing()));
        nonLocal(TOP, sub(F.getOWLDataMaxCardinality(2, P, F.getBooleanOWLDatatype()), nothing()));
        nonLocal(BOT,
            F.getOWLDisjointObjectPropertiesAxiom(R.getInverseProperty(), S.getInverseProperty()),
            R, S);
        nonLocal(BOT, sub(some(F.getOWLTopObjectProperty(), thing()), nothing()));
        local(CALCULUS, BOT, F.getOWLDisjointUnionAxiom(A, set(B, C)));
        nonLocal(TOP, F.getOWLDisjointUnionAxiom(A, set(B, C)));
        local(CALCULUS, TOP, F.getOWLObjectPropertyAssertionAxiom(R, I, J));
        local(CALCULUS, BOT, F.getOWLNegativeObjectPropertyAssertionAxiom(R, I, J));
        nonLocal(TOP, F.getOWLObjectPropertyAssertionAxiom(F.getOWLBottomObjectProperty(), I, J));
    }

    /**
     * The cases of the legacy owlapitools SyntacticLocalityTestCase, renamed to the
     * shared vocabulary (P, Q, R to r, s, t; s, dt, v to p, q, u; x, y, z to i, j, k).
     * Single-operand n-ary axioms, keys over empty properties and SWRL rules are
     * beyond the calculus and live in {@link #beyondCalculus()}; the datatype
     * definition case is omitted because locality conventions for datatypes differ.
     */
    private void legacyOwlapitools() {
        source = "legacy";
        nonLocal(BOT, equivalent(A, B), A);
        local(CALCULUS, BOT, equivalent(A, B), C);
        nonLocal(BOT, equivalent(nothing(), A, B), A);
        nonLocal(BOT, equivalent(thing(), A, B), A);
        nonLocal(BOT, equivalent(nothing(), thing(), A, B), A);
        local(CALCULUS, BOT, disjoint(A, B), A);
        local(CALCULUS, BOT, disjoint(A, B), C);
        nonLocal(BOT, disjoint(A, B, C), A, B);
        local(CALCULUS, BOT, disjoint(A, B, C), D);
        nonLocal(BOT, disjoint(thing(), A, B), A);
        nonLocal(BOT, F.getOWLDisjointUnionAxiom(A, set(B, C)), A);
        local(CALCULUS, BOT, F.getOWLDisjointUnionAxiom(A, set(B, C)), D);
        nonLocal(BOT, F.getOWLDisjointUnionAxiom(thing(), set(B, C)), B);
        nonLocal(BOT, F.getOWLDisjointUnionAxiom(thing(), set(B, thing())), B);
        nonLocal(BOT, F.getOWLEquivalentObjectPropertiesAxiom(R, S), R);
        local(CALCULUS, BOT, F.getOWLEquivalentObjectPropertiesAxiom(R, S), T);
        nonLocal(BOT, F.getOWLEquivalentDataPropertiesAxiom(P, Q), P);
        local(CALCULUS, BOT, F.getOWLEquivalentDataPropertiesAxiom(P, Q), U);
        local(CALCULUS, BOT, F.getOWLDisjointObjectPropertiesAxiom(R, S), R);
        nonLocal(TOP, F.getOWLDisjointObjectPropertiesAxiom(R, S), R);
        nonLocal(TOP, F.getOWLDisjointObjectPropertiesAxiom(R, S), T);
        nonLocal(BOT, F.getOWLDisjointObjectPropertiesAxiom(R, S, F.getOWLTopObjectProperty()), R);
        local(CALCULUS, BOT,
            F.getOWLDisjointObjectPropertiesAxiom(R, S, F.getOWLBottomObjectProperty()), R);
        local(CALCULUS, BOT, F.getOWLDisjointDataPropertiesAxiom(P, Q), P);
        local(CALCULUS, BOT, F.getOWLDisjointDataPropertiesAxiom(P, Q), U);
        nonLocal(TOP, F.getOWLDisjointDataPropertiesAxiom(P, Q), R);
        nonLocal(BOT, F.getOWLDisjointDataPropertiesAxiom(F.getOWLTopDataProperty(), P, Q), P);
        nonLocal(BOT, F.getOWLSameIndividualAxiom(I, J), I);
        nonLocal(BOT, F.getOWLSameIndividualAxiom(I, J), K);
        nonLocal(BOT, F.getOWLDifferentIndividualsAxiom(I, J), I);
        nonLocal(BOT, F.getOWLDifferentIndividualsAxiom(I, J), K);
        nonLocal(BOT, F.getOWLInverseObjectPropertiesAxiom(R, S), R);
        local(CALCULUS, BOT, F.getOWLInverseObjectPropertiesAxiom(R, S), T);
        nonLocal(TOP, F.getOWLInverseObjectPropertiesAxiom(R, F.getOWLTopObjectProperty()), R);
        nonLocal(TOP, F.getOWLInverseObjectPropertiesAxiom(F.getOWLTopObjectProperty(), R), R);
        nonLocal(BOT, F.getOWLSubObjectPropertyOfAxiom(R, S), R);
        local(CALCULUS, BOT, F.getOWLSubObjectPropertyOfAxiom(R, S), T);
        local(CALCULUS, BOT, F.getOWLSubObjectPropertyOfAxiom(R, F.getOWLTopObjectProperty()), R);
        nonLocal(BOT, F.getOWLSubObjectPropertyOfAxiom(F.getOWLTopObjectProperty(), R), R);
        nonLocal(BOT, F.getOWLSubDataPropertyOfAxiom(P, Q), P);
        local(CALCULUS, BOT, F.getOWLSubDataPropertyOfAxiom(P, Q), U);
        local(CALCULUS, BOT, F.getOWLSubDataPropertyOfAxiom(U, F.getOWLTopDataProperty()), U);
        nonLocal(BOT, F.getOWLSubDataPropertyOfAxiom(F.getOWLTopDataProperty(), U), U);
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(R, A), A);
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(R, A), D);
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(R, thing()), R);
        local(CALCULUS, BOT, F.getOWLObjectPropertyDomainAxiom(F.getOWLBottomObjectProperty(), A),
            A);
        local(CALCULUS, BOT, F.getOWLDataPropertyDomainAxiom(P, A), A);
        local(CALCULUS, BOT, F.getOWLDataPropertyDomainAxiom(P, A), D);
        local(CALCULUS, BOT, F.getOWLDataPropertyDomainAxiom(U, thing()), U);
        local(CALCULUS, BOT,
            F.getOWLDataPropertyDomainAxiom(F.getOWLBottomDataProperty(), thing()), A);
        local(CALCULUS, BOT, F.getOWLObjectPropertyRangeAxiom(R, A), A);
        local(CALCULUS, BOT, F.getOWLObjectPropertyRangeAxiom(R, A), D);
        nonLocal(BOT, F.getOWLDataPropertyRangeAxiom(P, CUSTOM), P);
        local(CALCULUS, BOT, F.getOWLDataPropertyRangeAxiom(P, CUSTOM), R);
        nonLocal(BOT, F.getOWLTransitiveObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLTransitiveObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLReflexiveObjectPropertyAxiom(R), R);
        nonLocal(BOT, F.getOWLReflexiveObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLIrreflexiveObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLIrreflexiveObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLSymmetricObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLSymmetricObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLAsymmetricObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLAsymmetricObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLFunctionalObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLFunctionalObjectPropertyAxiom(R), S);
        nonLocal(BOT, F.getOWLFunctionalDataPropertyAxiom(P), P);
        local(CALCULUS, BOT, F.getOWLFunctionalDataPropertyAxiom(P), Q);
        nonLocal(BOT, F.getOWLInverseFunctionalObjectPropertyAxiom(R), R);
        local(CALCULUS, BOT, F.getOWLInverseFunctionalObjectPropertyAxiom(R), S);
        nonLocal(BOT, sub(A, B), A);
        local(CALCULUS, BOT, sub(A, B), D);
        nonLocal(BOT, F.getOWLClassAssertionAxiom(A, I), A);
        nonLocal(BOT, F.getOWLClassAssertionAxiom(A, I), D);
        nonLocal(BOT, F.getOWLObjectPropertyAssertionAxiom(R, J, K), R);
        nonLocal(BOT, F.getOWLObjectPropertyAssertionAxiom(R, J, K), I);
        nonLocal(BOT, F.getOWLNegativeObjectPropertyAssertionAxiom(R, I, J), R);
        local(CALCULUS, BOT, F.getOWLNegativeObjectPropertyAssertionAxiom(R, I, J), K);
        nonLocal(BOT, F.getOWLDataPropertyAssertionAxiom(P, I, X), P);
        nonLocal(BOT, F.getOWLDataPropertyAssertionAxiom(P, I, X), R);
        nonLocal(BOT, F.getOWLNegativeDataPropertyAssertionAxiom(P, I, F.getOWLLiteral(true)), P);
        // legacy-corrected: the legacy test expected "not local", but p is empty here,
        // so the negative assertion holds in every interpretation.
        source = "legacy-corrected";
        local(CALCULUS, BOT, F.getOWLNegativeDataPropertyAssertionAxiom(P, I, F.getOWLLiteral(true)),
            R);
        source = "legacy";
        OWLAxiom annotation = F.getOWLAnnotationAssertionAxiom(NOTE, A.getIRI(), X);
        local(NON_LOGICAL, BOT, annotation, NOTE);
        local(NON_LOGICAL, BOT, annotation, B);
        local(NON_LOGICAL, BOT, F.getOWLSubAnnotationPropertyOfAxiom(NOTE, NOTE2), NOTE);
        local(NON_LOGICAL, BOT, F.getOWLSubAnnotationPropertyOfAxiom(NOTE, NOTE2), R);
        local(NON_LOGICAL, BOT, F.getOWLAnnotationPropertyDomainAxiom(NOTE, A.getIRI()), NOTE);
        local(NON_LOGICAL, BOT, F.getOWLAnnotationPropertyRangeAxiom(NOTE, A.getIRI()), NOTE2);
        local(CALCULUS, BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), R);
        local(CALCULUS, BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), P);
        nonLocal(BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S), T), R, S, T);
        local(CALCULUS, BOT, F.getOWLSubPropertyChainOfAxiom(Arrays.asList(R, S),
            F.getOWLTopObjectProperty()), R);
        // HasKey(A, r, p) wrt {s}: A is empty.
        local(CALCULUS, BOT, F.getOWLHasKeyAxiom(A, set(R, P)), S);
    }
}
