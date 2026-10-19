package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Complementary semantic locality tests for the OWL API contracts module.
 *
 * <p>Each assertion has a model-theoretic expected outcome. The tests avoid
 * comparing against the syntactic locality evaluator: syntactic locality is
 * an approximation and is therefore not an independent semantic oracle.</p>
 *
 * <p>Tests exercise (1) logical axiom families, (2) global TOP-property
 * cardinalities with proxies and negation, (3) finite data ranges, (4) explicit
 * failure behavior, and (5) resource and reuse invariants. Both evaluators
 * share a manager but own different empty ontologies and reasoners.</p>
 *
 * <p>HermiT is used deliberately; the structural reasoner cannot be used as
 * an oracle for these semantic checks.</p>
 */
@Execution(ExecutionMode.SAME_THREAD)
class SemanticLocalityEvaluatorCoverageTest {

    private static OWLOntologyManager manager;
    private static OWLDataFactory f;
    private static SemanticLocalityEvaluator bottom;
    private static SemanticLocalityEvaluator top;

    private static OWLClass a;
    private static OWLClass b;
    private static OWLClass c;
    private static OWLObjectProperty r;
    private static OWLObjectProperty s;
    private static OWLObjectProperty t;
    private static OWLDataProperty p;
    private static OWLDataProperty q;
    private static OWLNamedIndividual i;
    private static OWLNamedIndividual j;
    private static OWLNamedIndividual k;

    private static final String XSD = "http://www.w3.org/2001/XMLSchema#";

    /**
     * Smallest global TOP cardinality the evaluator refuses to encode.
     * Keep in sync with the limit in SemanticLocalityEvaluator.
     */
    private static final int TOO_LARGE_GLOBAL_CARDINALITY = 257;

    /** Reasoner methods a failing reasoner may still answer (bookkeeping only). */
    private static final Set<String> NON_QUERY_REASONER_METHODS = Set.of(
            "dispose", "flush", "interrupt", "getRootOntology", "getReasonerName",
            "getReasonerVersion", "getBufferingMode", "getTimeOut",
            "getFreshEntityPolicy", "getIndividualNodeSetPolicy",
            "getPendingChanges", "getPendingAxiomAdditions", "getPendingAxiomRemovals",
            "hashCode", "equals", "toString");

    @BeforeAll
    static void setUp() {
        manager = OWLManager.createOWLOntologyManager();
        f = manager.getOWLDataFactory();
        a = f.getOWLClass(IRI.create("urn:sem-locality:coverage#A"));
        b = f.getOWLClass(IRI.create("urn:sem-locality:coverage#B"));
        c = f.getOWLClass(IRI.create("urn:sem-locality:coverage#C"));
        r = f.getOWLObjectProperty(IRI.create("urn:sem-locality:coverage#r"));
        s = f.getOWLObjectProperty(IRI.create("urn:sem-locality:coverage#s"));
        t = f.getOWLObjectProperty(IRI.create("urn:sem-locality:coverage#t"));
        p = f.getOWLDataProperty(IRI.create("urn:sem-locality:coverage#p"));
        q = f.getOWLDataProperty(IRI.create("urn:sem-locality:coverage#q"));
        i = f.getOWLNamedIndividual(IRI.create("urn:sem-locality:coverage#i"));
        j = f.getOWLNamedIndividual(IRI.create("urn:sem-locality:coverage#j"));
        k = f.getOWLNamedIndividual(IRI.create("urn:sem-locality:coverage#k"));
        bottom = new SemanticLocalityEvaluator(LocalityClass.BOTTOM, manager,
                new ReasonerFactory());
        top = new SemanticLocalityEvaluator(LocalityClass.TOP, manager,
                new ReasonerFactory());
        assertEquals(2, manager.getOntologies().size());
    }

    @AfterAll
    static void tearDown() {
        try {
            if (bottom != null) {
                bottom.dispose();
            }
        } finally {
            if (top != null) {
                top.dispose();
            }
        }
        assertEquals(0, manager.getOntologies().size());
    }

    private static Set<OWLEntity> sig(OWLEntity... entities) {
        return new HashSet<>(Arrays.asList(entities));
    }

    private static void check(LocalityClass locality, boolean expected,
                              OWLAxiom axiom, OWLEntity... signature) {
        SemanticLocalityEvaluator evaluator = locality == LocalityClass.BOTTOM ? bottom : top;
        int ontologyCount = manager.getOntologies().size();
        boolean actual = evaluator.isLocal(axiom, sig(signature));
        assertEquals(expected, actual, () -> locality + " / " + axiom
                + " / Sigma=" + sig(signature));
        // Temporary auxiliary ontologies must not accumulate.
        assertEquals(ontologyCount, manager.getOntologies().size(),
                "A locality query leaked an auxiliary ontology");
        assertSharedOntologiesEmpty(manager);
    }

    /** The evaluators' persistent ontologies must never retain query axioms. */
    private static void assertSharedOntologiesEmpty(OWLOntologyManager owner) {
        owner.getOntologies().forEach(o -> assertEquals(0, o.getAxiomCount(),
                () -> "Axioms leaked into " + o.getOntologyID()));
    }

    private static OWLDatatype xsd(String localName) {
        return f.getOWLDatatype(IRI.create(XSD + localName));
    }

    private static OWLLiteral lit(String lexical, String xsdLocalName) {
        return f.getOWLLiteral(lexical, xsd(xsdLocalName));
    }

    /** Invokes a delegate, rethrowing the real cause instead of the reflection wrapper. */
    private static Object forward(Object target, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    /** True for createReasoner, createNonBufferingReasoner and any overload. */
    private static boolean createsReasoner(Method method) {
        return OWLReasoner.class.isAssignableFrom(method.getReturnType());
    }

    private static void both(boolean expected, OWLAxiom axiom, OWLEntity... signature) {
        check(LocalityClass.BOTTOM, expected, axiom, signature);
        check(LocalityClass.TOP, expected, axiom, signature);
    }

    /** An axiom the evaluator cannot decide: isLocal answers the safe "non-local". */
    private static void undecided(LocalityClass locality, OWLAxiom axiom,
                                  OWLEntity... signature) {
        SemanticLocalityEvaluator evaluator = locality == LocalityClass.BOTTOM ? bottom : top;
        check(locality, false, axiom, signature);
        int ontologyCount = manager.getOntologies().size();
        assertThrows(UnsupportedOperationException.class,
                () -> evaluator.isLocalOrThrow(axiom, sig(signature)),
                () -> locality + " / " + axiom + " / Sigma=" + sig(signature));
        assertEquals(ontologyCount, manager.getOntologies().size(),
                "An undecided locality query leaked an auxiliary ontology");
        assertSharedOntologiesEmpty(manager);
    }

    private static OWLClassExpression min(int n, OWLClassExpression filler) {
        return f.getOWLObjectMinCardinality(n, r, filler);
    }

    private static OWLClassExpression max(int n, OWLClassExpression filler) {
        return f.getOWLObjectMaxCardinality(n, r, filler);
    }

    private static OWLClassExpression exact(int n, OWLClassExpression filler) {
        return f.getOWLObjectExactCardinality(n, r, filler);
    }

    /** Checks whether an expression is universally Thing / Nothing after rewriting. */
    private static void constant(LocalityClass locality, OWLClassExpression expression,
                                 boolean isThing, OWLEntity... signature) {
        check(locality, isThing, f.getOWLSubClassOfAxiom(f.getOWLThing(), expression), signature);
        check(locality, !isThing, f.getOWLSubClassOfAxiom(expression, f.getOWLNothing()), signature);
    }

    @Test @Tag("axiom-matrix")
    void classAssertionsKeepIndividualsAndRespectTheClassSignature() {
        OWLAxiom assertion = f.getOWLClassAssertionAxiom(a, i);
        check(LocalityClass.BOTTOM, false, assertion);
        check(LocalityClass.TOP, true, assertion);
        both(false, assertion, a);
        both(true, f.getOWLClassAssertionAxiom(f.getOWLObjectOneOf(i), i));
    }

    @Test @Tag("axiom-matrix")
    void individualEqualitiesAndInequalitiesAreNotDropped() {
        both(false, f.getOWLSameIndividualAxiom(i, j));
        both(false, f.getOWLDifferentIndividualsAxiom(i, j));
        both(false, f.getOWLSameIndividualAxiom(i, j), i, j);
        both(false, f.getOWLDifferentIndividualsAxiom(i, j), i, j);
    }

    @Test @Tag("axiom-matrix")
    void disjointUnionRetainsBothUnionEqualityAndPairwiseDisjointness() {
        OWLAxiom du = f.getOWLDisjointUnionAxiom(a, Set.of(b, c));
        // BOTTOM: Nothing = (Nothing union Nothing), with disjoint operands.
        check(LocalityClass.BOTTOM, true, du);
        // TOP: Thing = (Thing union Thing), but the RHS classes overlap.
        check(LocalityClass.TOP, false, du);
        both(false, du, a);
        both(false, du, a, b, c);
        OWLAxiom valid = f.getOWLDisjointUnionAxiom(a,
                Set.of(b, f.getOWLObjectComplementOf(b)));
        // With A retained, the equation A = Thing is not a tautology.
        both(false, valid, a, b);
        // If A is absent, TOP makes it Thing and the complementary RHS
        // is a disjoint partition; BOTTOM instead makes A Nothing.
        check(LocalityClass.BOTTOM, false, valid, b);
        check(LocalityClass.TOP, true, valid, b);
    }

    @Test @Tag("axiom-matrix")
    void objectPropertyDomainAndRangeUseTheCorrectRoleSemantics() {
        OWLAxiom domain = f.getOWLObjectPropertyDomainAxiom(r, a);
        OWLAxiom range = f.getOWLObjectPropertyRangeAxiom(r, a);
        check(LocalityClass.BOTTOM, true, domain, a);
        check(LocalityClass.TOP, false, domain, a);
        check(LocalityClass.BOTTOM, true, range, a);
        check(LocalityClass.TOP, false, range, a);
        check(LocalityClass.BOTTOM, false, domain, r);
        check(LocalityClass.TOP, true, domain, r);
        check(LocalityClass.BOTTOM, false, range, r);
        check(LocalityClass.TOP, true, range, r);
        both(false, domain, r, a);
        both(false, range, r, a);
    }

    @Test @Tag("axiom-matrix")
    void dataPropertyDomainAndRangeUseFixedDatatypeSemantics() {
        OWLAxiom domain = f.getOWLDataPropertyDomainAxiom(p, a);
        check(LocalityClass.BOTTOM, true, domain, a);
        check(LocalityClass.TOP, false, domain, a);
        check(LocalityClass.BOTTOM, false, domain, p);
        check(LocalityClass.TOP, true, domain, p);

        OWLDataRange finite = f.getOWLDataOneOf(f.getOWLLiteral("one"));
        OWLAxiom finiteRange = f.getOWLDataPropertyRangeAxiom(p, finite);
        OWLAxiom topRange = f.getOWLDataPropertyRangeAxiom(p, f.getTopDatatype());
        check(LocalityClass.BOTTOM, true, finiteRange);
        check(LocalityClass.TOP, false, finiteRange);
        both(true, topRange);
        both(false, finiteRange, p);
        both(true, topRange, p);
    }

    @Test @Tag("axiom-matrix")
    void objectSubpropertyInclusionsDistinguishAntecedentAndConsequent() {
        OWLAxiom sub = f.getOWLSubObjectPropertyOfAxiom(r, s);
        both(true, sub);
        check(LocalityClass.BOTTOM, false, sub, r);
        check(LocalityClass.TOP, true, sub, r);
        check(LocalityClass.BOTTOM, true, sub, s);
        check(LocalityClass.TOP, false, sub, s);
        both(false, sub, r, s);
    }

    @Test @Tag("axiom-matrix")
    void dataSubpropertyInclusionsDistinguishAntecedentAndConsequent() {
        OWLAxiom sub = f.getOWLSubDataPropertyOfAxiom(p, q);
        both(true, sub);
        check(LocalityClass.BOTTOM, false, sub, p);
        check(LocalityClass.TOP, true, sub, p);
        check(LocalityClass.BOTTOM, true, sub, q);
        // TOP requires U_data <= q, which fails for any q that is not full.
        check(LocalityClass.TOP, false, sub, q);
        both(false, sub, p, q);
    }

    @Test @Tag("axiom-matrix")
    void inverseObjectPropertiesRetainInverseSemantics() {
        OWLAxiom inverse = f.getOWLInverseObjectPropertiesAxiom(r, s);
        both(true, inverse);
        both(false, inverse, r);
        both(false, inverse, r, s);
        OWLAxiom reflexiveInverse = f.getOWLInverseObjectPropertiesAxiom(
                r, f.getOWLObjectInverseOf(r));
        both(true, reflexiveInverse, r);
    }

    @Test @Tag("axiom-matrix")
    void objectPropertyEquivalenceAndDisjointnessSurviveOperandCollisions() {
        OWLAxiom equiv = f.getOWLEquivalentObjectPropertiesAxiom(r, s);
        both(true, equiv);
        both(false, equiv, r);
        both(false, equiv, r, s);
        OWLAxiom disjoint = f.getOWLDisjointObjectPropertiesAxiom(r, s);
        check(LocalityClass.BOTTOM, true, disjoint);
        check(LocalityClass.TOP, false, disjoint);
        check(LocalityClass.BOTTOM, true, disjoint, r);
        check(LocalityClass.TOP, false, disjoint, r);
        both(false, disjoint, r, s);
    }

    @Test @Tag("axiom-matrix")
    void dataPropertyEquivalenceAndDisjointnessSurviveOperandCollisions() {
        OWLAxiom equiv = f.getOWLEquivalentDataPropertiesAxiom(p, q);
        both(true, equiv);
        check(LocalityClass.BOTTOM, false, equiv, p);
        // TOP needs U_data <= p, see dataSubpropertyInclusionsDistinguishAntecedentAndConsequent.
        check(LocalityClass.TOP, false, equiv, p);
        both(false, equiv, p, q);
        OWLAxiom disjoint = f.getOWLDisjointDataPropertiesAxiom(p, q);
        check(LocalityClass.BOTTOM, true, disjoint);
        check(LocalityClass.TOP, false, disjoint);
        check(LocalityClass.BOTTOM, true, disjoint, p);
        check(LocalityClass.TOP, false, disjoint, p);
        both(false, disjoint, p, q);
    }

    @Test @Tag("axiom-matrix")
    void positiveAndNegativeObjectPropertyAssertionsAreDual() {
        OWLAxiom positive = f.getOWLObjectPropertyAssertionAxiom(r, i, j);
        OWLAxiom negative = f.getOWLNegativeObjectPropertyAssertionAxiom(r, i, j);
        check(LocalityClass.BOTTOM, false, positive);
        check(LocalityClass.TOP, true, positive);
        check(LocalityClass.BOTTOM, true, negative);
        check(LocalityClass.TOP, false, negative);
        both(false, positive, r);
        both(false, negative, r);
    }

    @Test @Tag("axiom-matrix")
    void positiveAndNegativeDataPropertyAssertionsAreDual() {
        OWLAxiom positive = f.getOWLDataPropertyAssertionAxiom(p, i, f.getOWLLiteral("x"));
        OWLAxiom negative = f.getOWLNegativeDataPropertyAssertionAxiom(p, i, f.getOWLLiteral("x"));
        check(LocalityClass.BOTTOM, false, positive);
        check(LocalityClass.TOP, true, positive);
        check(LocalityClass.BOTTOM, true, negative);
        check(LocalityClass.TOP, false, negative);
        both(false, positive, p);
        both(false, negative, p);
    }

    @Test @Tag("axiom-matrix")
    void symmetricAndTransitiveRolesAreLocalWhenAbsentInEitherMode() {
        OWLAxiom sym = f.getOWLSymmetricObjectPropertyAxiom(r);
        OWLAxiom trans = f.getOWLTransitiveObjectPropertyAxiom(r);
        both(true, sym);
        both(true, trans);
        both(false, sym, r);
        both(false, trans, r);
    }

    @Test @Tag("axiom-matrix")
    void reflexivityIrreflexivityAndAsymmetryDistinguishUniversalAndEmptyRoles() {
        OWLAxiom reflexive = f.getOWLReflexiveObjectPropertyAxiom(r);
        OWLAxiom irreflexive = f.getOWLIrreflexiveObjectPropertyAxiom(r);
        OWLAxiom asymmetric = f.getOWLAsymmetricObjectPropertyAxiom(r);
        check(LocalityClass.BOTTOM, false, reflexive);
        check(LocalityClass.TOP, true, reflexive);
        check(LocalityClass.BOTTOM, true, irreflexive);
        check(LocalityClass.TOP, false, irreflexive);
        check(LocalityClass.BOTTOM, true, asymmetric);
        check(LocalityClass.TOP, false, asymmetric);
        both(false, reflexive, r);
        both(false, irreflexive, r);
        both(false, asymmetric, r);
    }

    @Test @Tag("axiom-matrix")
    void functionalityAndInverseFunctionalityUseCorrectTopRoleCardinality() {
        OWLAxiom functional = f.getOWLFunctionalObjectPropertyAxiom(r);
        OWLAxiom inverse = f.getOWLInverseFunctionalObjectPropertyAxiom(r);
        OWLAxiom data = f.getOWLFunctionalDataPropertyAxiom(p);
        check(LocalityClass.BOTTOM, true, functional);
        check(LocalityClass.TOP, false, functional);
        check(LocalityClass.BOTTOM, true, inverse);
        check(LocalityClass.TOP, false, inverse);
        check(LocalityClass.BOTTOM, true, data);
        check(LocalityClass.TOP, false, data);
        both(false, functional, r);
        both(false, inverse, r);
        both(false, data, p);
    }

    @Test @Tag("axiom-matrix")
    void propertyChainsAreLocalWhenTheAntecedentIsEmptyOrConsequentUniversal() {
        OWLAxiom chain = f.getOWLSubPropertyChainOfAxiom(List.of(r, s), t);
        check(LocalityClass.BOTTOM, true, chain, s, t);
        check(LocalityClass.TOP, true, chain, r, s); // t outside Sigma => universal
        // When a top role occurs in a nontrivial chain but t is retained,
        // the implementation intentionally refuses to generate an invalid
        // OWL 2 DL intermediate axiom, so the chain is treated as non-local.
        undecided(LocalityClass.TOP, chain, s, t);
        // BOTTOM with the consequent absent: r o s SubPropertyOf empty.
        check(LocalityClass.BOTTOM, false, chain, r, s);
        both(false, chain, r, s, t);
        // r o inverse(r) relates every r-subject to itself, so it is never
        // empty unless r is: not BOTTOM-local when t is absent.
        OWLAxiom backAndForth = f.getOWLSubPropertyChainOfAxiom(
                List.of(r, f.getOWLObjectInverseOf(r)), t);
        check(LocalityClass.BOTTOM, false, backAndForth, r);
        check(LocalityClass.TOP, true, backAndForth, r);
    }

    @Test @Tag("axiom-matrix")
    void naryObjectPropertyAxiomsSurvivePartialCollisions() {
        OWLAxiom equiv = f.getOWLEquivalentObjectPropertiesAxiom(r, s, t);
        both(true, equiv);
        both(false, equiv, r);       // r = empty or r = U, plus empty = empty
        both(false, equiv, r, s);
        OWLAxiom disjoint = f.getOWLDisjointObjectPropertiesAxiom(r, s, t);
        check(LocalityClass.BOTTOM, true, disjoint);
        check(LocalityClass.TOP, false, disjoint);
        // BOTTOM: Disjoint(r, empty, empty) holds; TOP: Disjoint(r, U, U) does not.
        check(LocalityClass.BOTTOM, true, disjoint, r);
        check(LocalityClass.TOP, false, disjoint, r);
        // BOTTOM: Disjoint(r, s, empty) still requires r and s to be disjoint.
        both(false, disjoint, r, s);
    }

    @Test @Tag("axiom-matrix")
    void naryDifferentIndividualsAreNeverLocal() {
        both(false, f.getOWLDifferentIndividualsAxiom(i, j, k));
        both(false, f.getOWLDifferentIndividualsAxiom(i, j, k), i, j, k);
    }

    @Test @Tag("axiom-matrix")
    void inverseRolesInsideClassExpressionsRespectTheSignature() {
        var inverse = f.getOWLObjectInverseOf(r);
        OWLClassExpression someInverseA = f.getOWLObjectSomeValuesFrom(inverse, a);
        // r absent: inverse(empty) = empty and inverse(U) = U.
        check(LocalityClass.BOTTOM, true, f.getOWLDisjointClassesAxiom(someInverseA, a), a);
        check(LocalityClass.TOP, false, f.getOWLDisjointClassesAxiom(someInverseA, a), a);
        check(LocalityClass.BOTTOM, false, f.getOWLSubClassOfAxiom(a, someInverseA), a);
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(a, someInverseA), a);
        // r retained: A SubClassOf all(r, some(inverse r, A)) is a tautology.
        both(true, f.getOWLSubClassOfAxiom(a,
                f.getOWLObjectAllValuesFrom(r, someInverseA)), r, a);
        both(false, f.getOWLDisjointClassesAxiom(someInverseA, a), r, a);
    }

    @Test @Tag("axiom-matrix")
    void domainAndRangeOfInverseRolesSwap() {
        var inverse = f.getOWLObjectInverseOf(r);
        OWLAxiom inverseDomain = f.getOWLObjectPropertyDomainAxiom(inverse, a);
        OWLAxiom inverseRange = f.getOWLObjectPropertyRangeAxiom(inverse, a);
        check(LocalityClass.BOTTOM, true, inverseDomain, a);
        check(LocalityClass.TOP, false, inverseDomain, a);
        check(LocalityClass.BOTTOM, false, inverseRange, r);
        check(LocalityClass.TOP, true, inverseRange, r);
        both(false, inverseDomain, r, a);
        both(false, inverseRange, r, a);
    }

    @Test @Tag("axiom-matrix")
    void builtInPropertyBoundsAreTautologies() {
        // Trivial bounds that hold for every r and p; they exercise property
        // entailment checks in the reasoner pipeline.
        both(true, f.getOWLSubObjectPropertyOfAxiom(r, f.getOWLTopObjectProperty()), r);
        both(true, f.getOWLSubObjectPropertyOfAxiom(f.getOWLBottomObjectProperty(), r), r);
        both(true, f.getOWLSubDataPropertyOfAxiom(p, f.getOWLTopDataProperty()), p);
        both(true, f.getOWLSubDataPropertyOfAxiom(f.getOWLBottomDataProperty(), p), p);
        both(true, f.getOWLSubPropertyChainOfAxiom(List.of(r, s),
                f.getOWLTopObjectProperty()), r, s);
    }

    @Test @Tag("axiom-matrix")
    void keysOverAbsentBottomPropertiesAreVacuous() {
        // With an empty key property no two individuals share a key value,
        // so the key's premise never holds. This is true independently of
        // which named individuals the vocabulary contains.
        check(LocalityClass.BOTTOM, true, f.getOWLHasKeyAxiom(a, Set.of(p)), a);
        check(LocalityClass.BOTTOM, true, f.getOWLHasKeyAxiom(a, Set.of(r)), a);
    }

    @Test @Tag("axiom-matrix")
    void anonymousIndividualsAreExistentialVariables() {
        // _:x in an assertion means "some element": C(_:x) holds iff C is
        // nonempty. Individuals, named or not, are never replaced.
        var x = f.getOWLAnonymousIndividual();
        var y = f.getOWLAnonymousIndividual();
        OWLAxiom classAssertion = f.getOWLClassAssertionAxiom(a, x);
        check(LocalityClass.BOTTOM, false, classAssertion);
        check(LocalityClass.TOP, true, classAssertion);
        both(false, classAssertion, a);
        both(true, f.getOWLClassAssertionAxiom(f.getOWLThing(), x));
        both(true, f.getOWLClassAssertionAxiom(
                f.getOWLObjectUnionOf(a, f.getOWLObjectComplementOf(a)), x), a);

        // Positive role assertions in every subject/object position.
        List<OWLAxiom> roleAssertions = List.of(
                f.getOWLObjectPropertyAssertionAxiom(r, i, x),
                f.getOWLObjectPropertyAssertionAxiom(r, x, i),
                f.getOWLObjectPropertyAssertionAxiom(r, x, y),
                f.getOWLObjectPropertyAssertionAxiom(r, x, x));
        for (OWLAxiom assertion : roleAssertions) {
            check(LocalityClass.BOTTOM, false, assertion);
            check(LocalityClass.TOP, true, assertion);
            both(false, assertion, r);
        }
        both(true, f.getOWLObjectPropertyAssertionAxiom(
                f.getOWLTopObjectProperty(), x, y));

        // Negative role assertions: dual in the modes; never valid for a
        // retained role, which may be universal.
        List<OWLAxiom> negativeAssertions = List.of(
                f.getOWLNegativeObjectPropertyAssertionAxiom(r, i, x),
                f.getOWLNegativeObjectPropertyAssertionAxiom(r, x, i),
                f.getOWLNegativeObjectPropertyAssertionAxiom(r, x, y),
                f.getOWLNegativeObjectPropertyAssertionAxiom(r, x, x));
        for (OWLAxiom assertion : negativeAssertions) {
            check(LocalityClass.BOTTOM, true, assertion);
            check(LocalityClass.TOP, false, assertion);
            both(false, assertion, r);
        }

        OWLLiteral value = f.getOWLLiteral("x");
        OWLAxiom dataAssertion = f.getOWLDataPropertyAssertionAxiom(p, x, value);
        check(LocalityClass.BOTTOM, false, dataAssertion);
        check(LocalityClass.TOP, true, dataAssertion);
        both(false, dataAssertion, p);
        OWLAxiom negativeData = f.getOWLNegativeDataPropertyAssertionAxiom(p, x, value);
        check(LocalityClass.BOTTOM, true, negativeData);
        check(LocalityClass.TOP, false, negativeData);
        both(false, negativeData, p);

        // An anonymous individual can always be chosen equal to another term,
        // but never forced apart from it in a one-element domain.
        both(true, f.getOWLSameIndividualAxiom(i, x));
        both(true, f.getOWLSameIndividualAxiom(x, y));
        both(false, f.getOWLSameIndividualAxiom(i, j, x));
        both(false, f.getOWLDifferentIndividualsAxiom(i, x));
        both(false, f.getOWLDifferentIndividualsAxiom(x, y));
    }

    @Test @Tag("exceptions")
    void anonymousIndividualsInsideClassExpressionsAreUndecided() {
        var x = f.getOWLAnonymousIndividual();
        OWLAxiom nominal = f.getOWLSubClassOfAxiom(a, f.getOWLObjectOneOf(x));
        OWLAxiom hasValue = f.getOWLSubClassOfAxiom(a, f.getOWLObjectHasValue(r, x));
        for (LocalityClass locality : List.of(LocalityClass.BOTTOM, LocalityClass.TOP)) {
            undecided(locality, nominal, a);
            undecided(locality, hasValue, a, r);
        }
        assertSharedOntologiesEmpty(manager);
    }

    @Test @Tag("tautology")
    void aboxTautologiesAreLocalWithAndWithoutReplacement() {
        // Assertions that hold in every interpretation. Reasoners differ in
        // how they check entailment of ABox axioms (e.g. {i}(i) needed a
        // rewrite to i SubClassOf {i}), so check each one with a full
        // signature (no replacement) and with an empty one.
        OWLLiteral x = f.getOWLLiteral("x");
        OWLClassExpression aOrNotA = f.getOWLObjectUnionOf(a, f.getOWLObjectComplementOf(a));
        OWLClassExpression selfOrNot = f.getOWLObjectUnionOf(f.getOWLObjectHasSelf(r),
                f.getOWLObjectComplementOf(f.getOWLObjectHasSelf(r)));
        List<OWLAxiom> tautologies = List.of(
                f.getOWLClassAssertionAxiom(f.getOWLThing(), i),
                f.getOWLClassAssertionAxiom(f.getOWLObjectOneOf(i), i),
                f.getOWLClassAssertionAxiom(f.getOWLObjectOneOf(i, j), i),
                f.getOWLClassAssertionAxiom(aOrNotA, i),
                f.getOWLClassAssertionAxiom(selfOrNot, i),
                f.getOWLClassAssertionAxiom(f.getOWLObjectHasValue(
                        f.getOWLTopObjectProperty(), j), i),
                f.getOWLClassAssertionAxiom(f.getOWLObjectSomeValuesFrom(
                        f.getOWLTopObjectProperty(), f.getOWLObjectOneOf(j)), i),
                f.getOWLClassAssertionAxiom(f.getOWLDataHasValue(
                        f.getOWLTopDataProperty(), x), i),
                f.getOWLObjectPropertyAssertionAxiom(f.getOWLTopObjectProperty(), i, j),
                f.getOWLDataPropertyAssertionAxiom(f.getOWLTopDataProperty(), i, x),
                f.getOWLNegativeObjectPropertyAssertionAxiom(
                        f.getOWLBottomObjectProperty(), i, j),
                f.getOWLNegativeDataPropertyAssertionAxiom(
                        f.getOWLBottomDataProperty(), i, x));
        for (OWLAxiom axiom : tautologies) {
            both(true, axiom, a, r, i, j);
            both(true, axiom);
        }
        // Their non-tautological neighbours.
        both(false, f.getOWLClassAssertionAxiom(f.getOWLObjectOneOf(j), i));
        both(false, f.getOWLClassAssertionAxiom(
                f.getOWLObjectComplementOf(f.getOWLObjectOneOf(j)), i));
    }

    @Test @Tag("tautology")
    void nominalTautologiesInTBoxAxioms() {
        OWLClassExpression ij = f.getOWLObjectOneOf(i, j);
        OWLClassExpression iOrJ = f.getOWLObjectUnionOf(
                f.getOWLObjectOneOf(i), f.getOWLObjectOneOf(j));
        both(true, f.getOWLSubClassOfAxiom(f.getOWLObjectOneOf(i), ij));
        both(true, f.getOWLEquivalentClassesAxiom(ij, iOrJ));
        both(true, f.getOWLSubClassOfAxiom(
                f.getOWLObjectIntersectionOf(f.getOWLObjectOneOf(i), a), a), a);
        both(false, f.getOWLSubClassOfAxiom(ij, f.getOWLObjectOneOf(i)));
    }

    @Test @Tag("proxy")
    void globalTopObjectMinAndMaxUseTrueGlobalCounting() {
        OWLClassExpression minTwo = min(2, c);
        OWLClassExpression maxOne = max(1, c);
        OWLClassExpression exactOne = exact(1, c);
        check(LocalityClass.TOP, false, f.getOWLSubClassOfAxiom(f.getOWLThing(), minTwo), c);
        check(LocalityClass.TOP, false, f.getOWLSubClassOfAxiom(f.getOWLThing(), maxOne), c);
        check(LocalityClass.TOP, false, f.getOWLSubClassOfAxiom(f.getOWLThing(), exactOne), c);
        // Every possible |C| is either <= 1 or >= 2.
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(maxOne, minTwo)), c);
        // Cardinality = 1 entails cardinality >= 1, but not conversely.
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(exactOne, min(1, c)), c);
        check(LocalityClass.TOP, false, f.getOWLSubClassOfAxiom(min(1, c), exactOne), c);
    }

    @Test @Tag("proxy")
    void globalExactIsTheIntersectionOfGlobalMinAndMax() {
        for (int n = 0; n <= 3; n++) {
            OWLClassExpression exactN = exact(n, c);
            OWLClassExpression conjunction = f.getOWLObjectIntersectionOf(min(n, c), max(n, c));
            check(LocalityClass.TOP, true,
                    f.getOWLEquivalentClassesAxiom(exactN, conjunction), c);
        }
    }

    @Test @Tag("proxy")
    void globalProxiesWorkInsideComplementAndBooleanContexts() {
        OWLClassExpression maxOne = max(1, c);
        OWLClassExpression minTwo = min(2, c);
        check(LocalityClass.TOP, true, f.getOWLEquivalentClassesAxiom(
                f.getOWLObjectComplementOf(maxOne), minTwo), c);
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(
                f.getOWLObjectIntersectionOf(
                        f.getOWLObjectComplementOf(maxOne),
                        f.getOWLObjectComplementOf(minTwo)),
                f.getOWLNothing()), c);
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(exact(0, c), exact(1, c), minTwo)), c);
        // This is NOT a tautology: there may be no C instances.
        check(LocalityClass.TOP, false, f.getOWLEquivalentClassesAxiom(
                exact(1, c), maxOne), c);
    }

    @Test @Tag("proxy")
    void nestedGlobalCardinalityProxiesRemainConsistent() {
        // (<= 1 r.X) or (>= 2 r.X) is Thing for any r and X, so these two
        // checks verify that nested proxies reach the reasoner consistently,
        // not that the inner proxy has the right meaning.
        OWLClassExpression inner = f.getOWLObjectMinCardinality(2, s, c);
        OWLClassExpression outerAtMost = max(1, inner);
        OWLClassExpression outerAtLeast = min(2, inner);
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(outerAtMost, outerAtLeast)), c);
        check(LocalityClass.TOP, true, f.getOWLEquivalentClassesAxiom(
                f.getOWLObjectComplementOf(outerAtMost), outerAtLeast), c);
    }

    @Test @Tag("proxy")
    void nestedGlobalProxyWithConstantInnerExpression() {
        // Sigma={C}: s is universal too, so inner = (|C| >= 2) is constant.
        // A constant class is nonempty iff it is Thing, hence
        // (>= 1 U.inner) = inner, and (>= 2 U.inner) = inner as well because
        // |C| >= 2 already implies a domain of at least two elements.
        OWLClassExpression inner = f.getOWLObjectMinCardinality(2, s, c);
        check(LocalityClass.TOP, true,
                f.getOWLEquivalentClassesAxiom(min(1, inner), inner), c);
        check(LocalityClass.TOP, true,
                f.getOWLEquivalentClassesAxiom(min(2, inner), inner), c);
        check(LocalityClass.TOP, false,
                f.getOWLSubClassOfAxiom(f.getOWLThing(), min(1, inner)), c);
    }

    @Test @Tag("proxy")
    void nestedGlobalProxyWithLocalInnerExpression() {
        // Sigma={C, s}: inner = (>= 2 s.C) is an ordinary local expression.
        // Any instance of inner witnesses that inner is nonempty.
        OWLClassExpression inner = f.getOWLObjectMinCardinality(2, s, c);
        check(LocalityClass.TOP, true,
                f.getOWLSubClassOfAxiom(inner, min(1, inner)), c, s);
        check(LocalityClass.TOP, false,
                f.getOWLSubClassOfAxiom(min(1, inner), inner), c, s);
        // BOTTOM: r is empty, so (>= 1 r.inner) = Nothing.
        check(LocalityClass.BOTTOM, false,
                f.getOWLSubClassOfAxiom(inner, min(1, inner)), c, s);
        check(LocalityClass.BOTTOM, true,
                f.getOWLSubClassOfAxiom(min(1, inner), inner), c, s);
    }

    @Test @Tag("proxy")
    void globalCountingOverComplexFillers() {
        // Sigma={A, B}, r universal: min(n, X) means |X| >= n.
        OWLClassExpression aOrB = f.getOWLObjectUnionOf(a, b);
        OWLClassExpression someA = min(1, a);
        OWLClassExpression someB = min(1, b);
        // |A u B| >= 2 implies A or B is nonempty.
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(min(2, aOrB),
                f.getOWLObjectUnionOf(someA, someB)), a, b);
        // A and B nonempty implies |A u B| >= 1 ...
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(
                f.getOWLObjectIntersectionOf(someA, someB), min(1, aOrB)), a, b);
        // ... but not |A u B| >= 2: A = B = {x} is a countermodel.
        check(LocalityClass.TOP, false, f.getOWLSubClassOfAxiom(
                f.getOWLObjectIntersectionOf(someA, someB), min(2, aOrB)), a, b);
        // In BOTTOM all three restrictions are Nothing.
        check(LocalityClass.BOTTOM, true, f.getOWLSubClassOfAxiom(
                f.getOWLObjectIntersectionOf(someA, someB), min(2, aOrB)), a, b);
    }

    @Test @Tag("proxy")
    void cardinalityBoundsZeroAndOneDoNotAssumeASingletonDomain() {
        check(LocalityClass.TOP, false,
                f.getOWLSubClassOfAxiom(f.getOWLThing(), exact(1, f.getOWLThing())));
        check(LocalityClass.TOP, false,
                f.getOWLSubClassOfAxiom(f.getOWLThing(), max(1, f.getOWLThing())));
        constant(LocalityClass.TOP, min(1, f.getOWLThing()), true);
        constant(LocalityClass.TOP, max(0, f.getOWLThing()), false);
        constant(LocalityClass.TOP, exact(0, f.getOWLNothing()), true);
        constant(LocalityClass.TOP, min(0, c), true, c);
    }

    @Test @Tag("proxy")
    void cardinalityFillerRewritingIsRecursiveEvenThroughNestedRestrictions() {
        OWLClassExpression nested = f.getOWLObjectSomeValuesFrom(s, b);
        // s is kept, B is outside signature. TOP substitutes Thing for B.
        // Both sides then have identical semantics under global r.
        OWLClassExpression rewritten = f.getOWLObjectSomeValuesFrom(s, f.getOWLThing());
        check(LocalityClass.TOP, true, f.getOWLEquivalentClassesAxiom(
                min(2, nested), min(2, rewritten)), s);
    }

    @Test @Tag("data-cardinality")
    void finiteTwoElementDataRangeGivesExactTopPropertyCardinality() {
        OWLDataRange two = f.getOWLDataOneOf(f.getOWLLiteral("first"), f.getOWLLiteral("second"));
        constant(LocalityClass.TOP, f.getOWLDataSomeValuesFrom(p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(0, p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(1, p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(2, p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(3, p, two), false);
        constant(LocalityClass.TOP, f.getOWLDataMaxCardinality(1, p, two), false);
        constant(LocalityClass.TOP, f.getOWLDataMaxCardinality(2, p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(1, p, two), false);
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(2, p, two), true);
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(3, p, two), false);
    }

    @Test @Tag("data-cardinality")
    void dataAllValuesFromWithFiniteRangeIsBottomUnderUniversalDataProperty() {
        OWLDataRange one = f.getOWLDataOneOf(f.getOWLLiteral("value"));
        constant(LocalityClass.TOP, f.getOWLDataAllValuesFrom(p, one), false);
        constant(LocalityClass.TOP, f.getOWLDataAllValuesFrom(p, f.getTopDatatype()), true);
        constant(LocalityClass.BOTTOM, f.getOWLDataAllValuesFrom(p, one), true);
    }

    @Test @Tag("data-cardinality")
    void finiteDataRangesWorkUnderNegationAndIntersection() {
        OWLDataRange one = f.getOWLDataOneOf(f.getOWLLiteral("one"));
        OWLClassExpression exactlyOne = f.getOWLDataExactCardinality(1, p, one);
        OWLClassExpression atLeastTwo = f.getOWLDataMinCardinality(2, p, one);
        OWLClassExpression complement = f.getOWLObjectComplementOf(atLeastTwo);
        // Under universal p, the range has exactly one value, so both
        // exactlyOne and not(atLeastTwo) denote owl:Thing.
        check(LocalityClass.TOP, true, f.getOWLEquivalentClassesAxiom(
                exactlyOne, complement));
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectIntersectionOf(exactlyOne, complement)));
    }

    @Test @Tag("data-cardinality")
    void finiteDatatypeValueSpacesAreCountedExactly() {
        // xsd:boolean has exactly TWO semantic values, even though there are
        // more than two lexical representations ("true", "1", ...).
        OWLDatatype bool = xsd("boolean");
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(2, p, bool), true);
        constant(LocalityClass.TOP, f.getOWLDataMaxCardinality(1, p, bool), false);
    }

    @Test @Tag("data-cardinality")
    void dataValuesAreNotCountedByLexicalFormAlone() {
        // Two lexical forms, one value: {"true", "1"} as xsd:boolean is a singleton.
        OWLDataRange sameBoolean = f.getOWLDataOneOf(
                lit("true", "boolean"), lit("1", "boolean"));
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(1, p, sameBoolean), true);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(2, p, sameBoolean), false);
        // Leading zeros do not create a new integer.
        OWLDataRange sameInteger = f.getOWLDataOneOf(
                lit("1", "integer"), lit("01", "integer"));
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(1, p, sameInteger), true);
    }

    @Test @Tag("data-cardinality")
    void numericValuesAcrossDatatypesFollowOwl2ValueSpaces() {
        // In OWL 2, xsd:integer and xsd:decimal share the owl:real value
        // space, so "1"^^integer and "1.0"^^decimal are the same value ...
        OWLDataRange integerAndDecimal = f.getOWLDataOneOf(
                lit("1", "integer"), lit("1.0", "decimal"));
        constant(LocalityClass.TOP,
                f.getOWLDataExactCardinality(1, p, integerAndDecimal), true);
        // ... whereas xsd:float is disjoint from owl:real.
        OWLDataRange integerAndFloat = f.getOWLDataOneOf(
                lit("1", "integer"), lit("1", "float"));
        constant(LocalityClass.TOP,
                f.getOWLDataExactCardinality(2, p, integerAndFloat), true);
    }

    @Test @Tag("data-cardinality")
    void facetRestrictionsAndDataBooleansAreCounted() {
        // xsd:integer[>= 1, <= 3] = {1, 2, 3}.
        OWLDataRange oneToThree = f.getOWLDatatypeMinMaxInclusiveRestriction(1, 3);
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(3, p, oneToThree), true);
        constant(LocalityClass.TOP, f.getOWLDataMaxCardinality(2, p, oneToThree), false);
        // An empty facet range: no value, under any interpretation of p.
        OWLDataRange empty = f.getOWLDatatypeMinMaxInclusiveRestriction(5, 3);
        constant(LocalityClass.TOP, f.getOWLDataSomeValuesFrom(p, empty), false);
        both(true, f.getOWLSubClassOfAxiom(
                f.getOWLDataSomeValuesFrom(p, empty), f.getOWLNothing()), p);
        // boolean intersected with {true, "x"} = {true}.
        OWLDataRange justTrue = f.getOWLDataIntersectionOf(xsd("boolean"),
                f.getOWLDataOneOf(lit("true", "boolean"), f.getOWLLiteral("x")));
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(1, p, justTrue), true);
        // {"a"} union {"b"} has two values.
        OWLDataRange ab = f.getOWLDataUnionOf(
                f.getOWLDataOneOf(f.getOWLLiteral("a")),
                f.getOWLDataOneOf(f.getOWLLiteral("b")));
        constant(LocalityClass.TOP, f.getOWLDataExactCardinality(2, p, ab), true);
    }

    @Test @Tag("data-cardinality")
    void infiniteDataRangesHaveNoUpperBoundUnderUniversalDataProperty() {
        OWLDatatype integer = xsd("integer");
        constant(LocalityClass.TOP, f.getOWLDataMaxCardinality(5, p, integer), false);
        constant(LocalityClass.TOP, f.getOWLDataMinCardinality(5, p, integer), true);
        constant(LocalityClass.BOTTOM, f.getOWLDataMaxCardinality(5, p, integer), true);
    }

    @Test @Tag("exceptions")
    void swrlRulesAreUndecidedInsteadOfGuessedLocal() {
        var variable = f.getSWRLVariable(IRI.create("urn:sem-locality:coverage#x"));
        OWLAxiom rule = f.getSWRLRule(
                Set.of(f.getSWRLClassAtom(a, variable)),
                Set.of(f.getSWRLClassAtom(b, variable)));
        undecided(LocalityClass.BOTTOM, rule, a, b);
        undecided(LocalityClass.TOP, rule, a, b);
    }

    @Test @Tag("data-cardinality")
    void topDataPropertyCanHandleComplementaryFiniteDataRanges() {
        OWLDataRange singleton = f.getOWLDataOneOf(f.getOWLLiteral("one"));
        OWLDataRange complement = f.getOWLDataComplementOf(singleton);
        // The full data domain is not a singleton; its complement is nonempty.
        constant(LocalityClass.TOP, f.getOWLDataSomeValuesFrom(p, complement), true);
        constant(LocalityClass.TOP, f.getOWLDataAllValuesFrom(p, complement), false);
    }

    @Test @Tag("axiom-matrix")
    void inverseObjectPropertyExpressionsRespectTheSignature() {
        var inverse = f.getOWLObjectInverseOf(r);
        OWLAxiom assertion = f.getOWLObjectPropertyAssertionAxiom(inverse, i, j);
        check(LocalityClass.BOTTOM, false, assertion);
        check(LocalityClass.TOP, true, assertion);
        both(false, assertion, r);
        OWLAxiom invertedSubproperty = f.getOWLSubObjectPropertyOfAxiom(inverse, s);
        check(LocalityClass.BOTTOM, true, invertedSubproperty, s);
        check(LocalityClass.TOP, false, invertedSubproperty, s);
    }

    @Test @Tag("exceptions")
    void unsupportedDatatypeDefinitionsAreUndecidedInsteadOfReportedLocal() {
        OWLDatatype custom = f.getOWLDatatype(IRI.create("urn:sem-locality:coverage#Custom"));
        OWLAxiom definition = f.getOWLDatatypeDefinitionAxiom(custom,
                f.getOWLDataOneOf(f.getOWLLiteral("x")));
        undecided(LocalityClass.BOTTOM, definition);
        undecided(LocalityClass.TOP, definition);
    }

    @Test @Tag("exceptions")
    void undefinedCustomDatatypesInUniversalDataProbesAreUndecided() {
        OWLDatatype custom = f.getOWLDatatype(IRI.create("urn:sem-locality:coverage#Undefined"));
        OWLAxiom axiom = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLDataSomeValuesFrom(p, custom));
        undecided(LocalityClass.TOP, axiom);
        // A BOTTOM interpretation of the absent property never needs a
        // datatype probe; it is simply an empty relation.
        check(LocalityClass.BOTTOM, false, axiom);
    }

    @Test @Tag("axiom-matrix")
    void keyWithMissingDataPropertyUsesEmptyOrUniversalKey() {
        OWLAxiom hasKey = f.getOWLHasKeyAxiom(a, Set.of(p));
        // BOTTOM: no two individuals share a value of the empty property.
        check(LocalityClass.BOTTOM, true, hasKey, a);
        // TOP: all named A-individuals share every value of U_data, so the
        // key says that A has at most one named instance, which is not valid.
        check(LocalityClass.TOP, false, hasKey, a);
    }

    @Test @Tag("exceptions")
    void excessiveGlobalCardinalityIsUndecidedButDoesNotLeak() {
        OWLAxiom axiom = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                min(TOO_LARGE_GLOBAL_CARDINALITY, c));
        undecided(LocalityClass.TOP, axiom, c);
        // A failed query must not poison subsequent calls to the shared reasoner.
        check(LocalityClass.TOP, true,
                f.getOWLSubClassOfAxiom(a, a), a);
    }

    @Test @Tag("axiom-matrix")
    void nonLogicalAnnotationAxiomsAreAlwaysLocal() {
        OWLAxiom declaration = f.getOWLDeclarationAxiom(a);
        OWLAxiom label = f.getOWLAnnotationAssertionAxiom(
                f.getRDFSLabel(), a.getIRI(), f.getOWLLiteral("test"));
        OWLAxiom domain = f.getOWLAnnotationPropertyDomainAxiom(
                f.getRDFSLabel(), IRI.create("urn:sem-locality:coverage#target"));
        OWLAxiom range = f.getOWLAnnotationPropertyRangeAxiom(
                f.getRDFSLabel(), IRI.create("urn:sem-locality:coverage#target"));
        both(true, declaration);
        both(true, label);
        both(true, domain);
        both(true, range);
    }

    @Test @Tag("signature")
    void signatureMembershipIsByEntityNotByIri() {
        // Punning: a CLASS with r's IRI in Sigma does not retain PROPERTY r.
        OWLClass punnedR = f.getOWLClass(r.getIRI());
        OWLAxiom axiom = f.getOWLDisjointClassesAxiom(
                f.getOWLObjectSomeValuesFrom(r, f.getOWLThing()), a);
        check(LocalityClass.BOTTOM, true, axiom, a, punnedR);
        check(LocalityClass.TOP, false, axiom, a, punnedR);
        // Retaining the property itself makes the axiom nonlocal in BOTTOM.
        check(LocalityClass.BOTTOM, false, axiom, a, r);
        // And the reverse: the property r in Sigma does not retain class r.
        OWLAxiom classAxiom = f.getOWLDisjointClassesAxiom(punnedR, a);
        check(LocalityClass.BOTTOM, true, classAxiom, a, r);
        check(LocalityClass.TOP, false, classAxiom, a, r);
    }

    @Test @Tag("signature")
    void irrelevantAndBuiltInSignatureMembersDoNotChangeResults() {
        OWLAxiom disjoint = f.getOWLDisjointClassesAxiom(a, b);
        OWLAxiom someR = f.getOWLSubClassOfAxiom(a,
                f.getOWLObjectSomeValuesFrom(r, f.getOWLThing()));
        OWLEntity[] extras = { a, c, s, q, i, f.getOWLThing(), f.getOWLNothing(),
                f.getOWLTopObjectProperty(), f.getOWLBottomDataProperty() };
        for (OWLEntity[] signature : List.of(new OWLEntity[] { a }, extras)) {
            check(LocalityClass.BOTTOM, true, disjoint, signature);
            check(LocalityClass.TOP, false, disjoint, signature);
            // r absent in both signatures: A SubClassOf Nothing vs A SubClassOf Thing.
            check(LocalityClass.BOTTOM, false, someR, signature);
            check(LocalityClass.TOP, true, someR, signature);
        }
    }

    @Test @Tag("metamorphic")
    void rewritingDoesNotModifyTheCallersSignature() {
        Set<OWLEntity> signature = new HashSet<>(Set.of(c));
        OWLAxiom axiom = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(max(1, c), min(2, c)));
        assertTrue(top.isLocal(axiom, signature));
        assertEquals(Set.of(c), signature);
        // An unmodifiable view also catches transient add-then-remove mutation.
        assertTrue(top.isLocal(axiom, Collections.unmodifiableSet(signature)));
        assertTrue(bottom.isLocal(f.getOWLDisjointClassesAxiom(a, b),
                Collections.unmodifiableSet(signature)));
    }

    @Test @Tag("lifecycle")
    void oneSharedEmptyOntologyPerEvaluatorAndNoNewOnesForSimpleCalls() {
        int count = manager.getOntologies().size();
        OWLAxiom trivial = f.getOWLSubClassOfAxiom(a, a);
        for (int repeat = 0; repeat < 4; repeat++) {
            both(true, trivial, a);
            both(true, f.getOWLDeclarationAxiom(a));
        }
        assertEquals(count, manager.getOntologies().size());
    }

    @Test @Tag("lifecycle")
    void repeatedProxyChecksRestoreTheManagerOntologyCount() {
        int count = manager.getOntologies().size();
        OWLAxiom axiom = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(max(1, c), min(2, c)));
        for (int repeat = 0; repeat < 3; repeat++) {
            check(LocalityClass.TOP, true, axiom, c);
            assertEquals(count, manager.getOntologies().size());
        }
    }

    @Test @Tag("lifecycle")
    void disposingOneEvaluatorDoesNotRemoveCallerOntologiesOrOtherEvaluators() throws Exception {
        OWLOntologyManager ownedManager = OWLManager.createOWLOntologyManager();
        var callerOntology = ownedManager.createOntology();
        int before = ownedManager.getOntologies().size();
        SemanticLocalityEvaluator first = new SemanticLocalityEvaluator(
                LocalityClass.BOTTOM, ownedManager, new ReasonerFactory());
        SemanticLocalityEvaluator second = new SemanticLocalityEvaluator(
                LocalityClass.TOP, ownedManager, new ReasonerFactory());
        try {
            assertEquals(before + 2, ownedManager.getOntologies().size());
            first.dispose();
            assertEquals(before + 1, ownedManager.getOntologies().size());
            assertTrue(ownedManager.getOntologies().contains(callerOntology));
            assertTrue(second.isLocal(f.getOWLSubClassOfAxiom(a, a), sig(a)));
            assertDoesNotThrow(first::dispose);
            assertThrows(IllegalStateException.class, () -> first.isLocal(
                    f.getOWLSubClassOfAxiom(a, a), sig(a)));
        } finally {
            first.dispose();
            second.dispose();
        }
        assertEquals(before, ownedManager.getOntologies().size());
        assertTrue(ownedManager.getOntologies().contains(callerOntology));
        ownedManager.removeOntology(callerOntology);
    }


    @Test @Tag("lifecycle")
    void failingSharedReasonerConstructionRemovesTheEmptyOntology() {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        ReasonerFactory delegate = new ReasonerFactory();
        // Fails every factory method that returns a reasoner, buffering or not.
        OWLReasonerFactory failsImmediately = (OWLReasonerFactory) Proxy.newProxyInstance(
                OWLReasonerFactory.class.getClassLoader(),
                new Class<?>[] { OWLReasonerFactory.class },
                (proxy, method, args) -> {
                    if (createsReasoner(method)) {
                        throw new IllegalStateException("simulated constructor failure");
                    }
                    return forward(delegate, method, args);
                });
        int before = ownManager.getOntologies().size();
        assertThrows(IllegalStateException.class,
                () -> new SemanticLocalityEvaluator(
                        LocalityClass.TOP, ownManager, failsImmediately));
        assertEquals(before, ownManager.getOntologies().size());
    }

    @Test @Tag("lifecycle")
    void reasonerWithClosedDataDomainIsRejectedWithoutLeaks() {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        ReasonerFactory delegate = new ReasonerFactory();
        AtomicInteger reasonersDisposed = new AtomicInteger();
        // Simulates a reasoner equating rdfs:Literal with the union of the
        // built-in datatypes: values outside all of them are unsatisfiable.
        OWLReasonerFactory closedDataDomain = (OWLReasonerFactory) Proxy.newProxyInstance(
                OWLReasonerFactory.class.getClassLoader(),
                new Class<?>[] { OWLReasonerFactory.class },
                (proxy, method, args) -> {
                    Object returned = forward(delegate, method, args);
                    if (!createsReasoner(method)) {
                        return returned;
                    }
                    OWLReasoner actual = (OWLReasoner) returned;
                    return Proxy.newProxyInstance(OWLReasoner.class.getClassLoader(),
                            new Class<?>[] { OWLReasoner.class },
                            (reasonerProxy, called, parameters) -> {
                                if (called.getName().equals("isSatisfiable")) {
                                    return false;
                                }
                                if (called.getName().equals("dispose")) {
                                    reasonersDisposed.incrementAndGet();
                                }
                                return forward(actual, called, parameters);
                            });
                });
        int before = ownManager.getOntologies().size();
        assertThrows(IllegalArgumentException.class,
                () -> new SemanticLocalityEvaluator(
                        LocalityClass.TOP, ownManager, closedDataDomain));
        assertEquals(before, ownManager.getOntologies().size());
        assertEquals(1, reasonersDisposed.get());
    }

    @Test @Tag("lifecycle")
    void failingTemporaryQueryDisposesReasonerAndRemovesOntology() {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        ReasonerFactory delegate = new ReasonerFactory();
        AtomicInteger reasonersCreated = new AtomicInteger();
        AtomicInteger reasonersDisposed = new AtomicInteger();
        OWLReasonerFactory throwingFactory = (OWLReasonerFactory) Proxy.newProxyInstance(
                OWLReasonerFactory.class.getClassLoader(),
                new Class<?>[] { OWLReasonerFactory.class },
                (proxy, method, args) -> {
                    Object returned = forward(delegate, method, args);
                    if (!createsReasoner(method)) {
                        return returned;
                    }
                    final int index = reasonersCreated.incrementAndGet();
                    final OWLReasoner actual = (OWLReasoner) returned;
                    return Proxy.newProxyInstance(OWLReasoner.class.getClassLoader(),
                            new Class<?>[] { OWLReasoner.class },
                            (reasonerProxy, called, parameters) -> {
                                if (called.getName().equals("dispose")) {
                                    reasonersDisposed.incrementAndGet();
                                }
                                // The temporary reasoner fails on whichever query the
                                // evaluator issues: isEntailed, isSatisfiable,
                                // isConsistent, precomputeInferences, ...
                                if (index == 2
                                        && !NON_QUERY_REASONER_METHODS.contains(called.getName())) {
                                    throw new IllegalStateException("simulated query failure");
                                }
                                return forward(actual, called, parameters);
                            });
                });
        int original = ownManager.getOntologies().size();
        SemanticLocalityEvaluator evaluator = new SemanticLocalityEvaluator(
                LocalityClass.TOP, ownManager, throwingFactory);
        try {
            OWLAxiom requiresProxy = f.getOWLSubClassOfAxiom(f.getOWLThing(), max(1, c));
            assertThrows(IllegalStateException.class,
                    () -> evaluator.isLocal(requiresProxy, sig(c)));
            assertEquals(original + 1, ownManager.getOntologies().size());
            assertSharedOntologiesEmpty(ownManager);
            assertEquals(1, reasonersDisposed.get(), "temporary reasoner must be disposed");
            assertTrue(evaluator.isLocal(f.getOWLSubClassOfAxiom(a, a), sig(a)));
        } finally {
            evaluator.dispose();
        }
        assertEquals(original, ownManager.getOntologies().size());
        assertEquals(2, reasonersCreated.get());
        assertEquals(2, reasonersDisposed.get());
    }

    @Test @Tag("lifecycle")
    void failingTemporaryReasonerCreationStillRemovesItsOntology() {
        OWLOntologyManager ownManager = OWLManager.createOWLOntologyManager();
        ReasonerFactory delegate = new ReasonerFactory();
        AtomicInteger creations = new AtomicInteger();
        OWLReasonerFactory failsOnSecondCreation = (OWLReasonerFactory) Proxy.newProxyInstance(
                OWLReasonerFactory.class.getClassLoader(),
                new Class<?>[] { OWLReasonerFactory.class },
                (proxy, method, args) -> {
                    if (createsReasoner(method) && creations.incrementAndGet() == 2) {
                        throw new IllegalStateException("simulated factory failure");
                    }
                    return forward(delegate, method, args);
                });
        int original = ownManager.getOntologies().size();
        SemanticLocalityEvaluator evaluator = new SemanticLocalityEvaluator(
                LocalityClass.TOP, ownManager, failsOnSecondCreation);
        try {
            assertEquals(original + 1, ownManager.getOntologies().size());
            OWLAxiom requiresProxy = f.getOWLSubClassOfAxiom(f.getOWLThing(), max(1, c));
            assertThrows(IllegalStateException.class,
                    () -> evaluator.isLocal(requiresProxy, sig(c)));
            // One permanent ontology; the failed temporary ontology is gone.
            assertEquals(original + 1, ownManager.getOntologies().size());
            assertSharedOntologiesEmpty(ownManager);
            assertTrue(evaluator.isLocal(f.getOWLSubClassOfAxiom(a, a), sig(a)));
        } finally {
            evaluator.dispose();
        }
        assertEquals(original, ownManager.getOntologies().size());
        assertEquals(2, creations.get());
    }

    @Test @Tag("lifecycle")
    void failedRewriteLeavesThePersistentEmptyOntologyUntouched() {
        int before = manager.getOntologies().size();
        OWLAxiom invalidForRewriter = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                min(TOO_LARGE_GLOBAL_CARDINALITY, c));
        assertThrows(UnsupportedOperationException.class,
                () -> top.isLocalOrThrow(invalidForRewriter, sig(c)));
        assertEquals(before, manager.getOntologies().size());
        // "Untouched" means no axioms were added, not just no ontologies.
        assertSharedOntologiesEmpty(manager);
        // A query whose answer would change if a leaked axiom were present:
        // Thing SubClassOf (<= 1 U.C) or (>= 2 U.C) must still be local, and
        // Thing SubClassOf (>= 2 U.C) must still be nonlocal.
        check(LocalityClass.TOP, true, f.getOWLSubClassOfAxiom(f.getOWLThing(),
                f.getOWLObjectUnionOf(max(1, c), min(2, c))), c);
        check(LocalityClass.TOP, false,
                f.getOWLSubClassOfAxiom(f.getOWLThing(), min(2, c)), c);
    }

    @Test @Tag("lifecycle")
    void concurrentQueriesOnOneEvaluatorReturnStableAnswers() throws Exception {
        // isLocal() is synchronized because many OWL reasoners are not safe for
        // concurrent queries. Verify that repeated calls return the same result,
        // including calls that create and remove temporary proxy ontologies.
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            List<Callable<Boolean>> calls = new ArrayList<>();
            OWLAxiom local = f.getOWLSubClassOfAxiom(a, a);
            OWLAxiom nonLocal = f.getOWLSubClassOfAxiom(a, b);
            OWLAxiom proxyLocal = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                    f.getOWLObjectUnionOf(max(1, c), min(2, c)));
            OWLAxiom proxyNonLocal = f.getOWLSubClassOfAxiom(f.getOWLThing(), max(1, c));
            for (int index = 0; index < 16; index++) {
                final int kind = index % 4;
                calls.add(() -> {
                    if (kind == 0) {
                        return top.isLocal(local, sig(a, b));
                    } else if (kind == 1) {
                        return !top.isLocal(nonLocal, sig(a, b));
                    } else if (kind == 2) {
                        return top.isLocal(proxyLocal, sig(c));
                    }
                    return !top.isLocal(proxyNonLocal, sig(c));
                });
            }
            List<Future<Boolean>> results = executor.invokeAll(calls);
            for (Future<Boolean> result : results) {
                assertTrue(result.get(60, TimeUnit.SECONDS));
            }
            assertEquals(2, manager.getOntologies().size());
            assertSharedOntologiesEmpty(manager);
        } finally {
            executor.shutdownNow();
        }
    }

    @Test @Tag("lifecycle")
    void concurrentQueriesOnTwoEvaluatorsSharingAManager() throws Exception {
        // Each evaluator serializes its own queries, but two evaluators sharing
        // a manager can add and remove temporary ontologies at the same time.
        // A concurrent manager isolates the evaluators' own synchronization;
        // with a plain manager the caller would be responsible for exclusion.
        OWLOntologyManager shared = OWLManager.createConcurrentOWLOntologyManager();
        SemanticLocalityEvaluator ownBottom = new SemanticLocalityEvaluator(
                LocalityClass.BOTTOM, shared, new ReasonerFactory());
        SemanticLocalityEvaluator ownTop = new SemanticLocalityEvaluator(
                LocalityClass.TOP, shared, new ReasonerFactory());
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            OWLAxiom proxyLocal = f.getOWLSubClassOfAxiom(f.getOWLThing(),
                    f.getOWLObjectUnionOf(max(1, c), min(2, c)));
            OWLAxiom proxyNonLocal = f.getOWLSubClassOfAxiom(f.getOWLThing(), max(1, c));
            OWLAxiom bottomLocal = f.getOWLDisjointClassesAxiom(a, b);
            List<Callable<Boolean>> calls = new ArrayList<>();
            for (int index = 0; index < 24; index++) {
                final int kind = index % 3;
                calls.add(() -> {
                    if (kind == 0) {
                        return ownTop.isLocal(proxyLocal, sig(c));
                    } else if (kind == 1) {
                        return !ownTop.isLocal(proxyNonLocal, sig(c));
                    }
                    return ownBottom.isLocal(bottomLocal, sig(a));
                });
            }
            for (Future<Boolean> result : executor.invokeAll(calls)) {
                assertTrue(result.get(60, TimeUnit.SECONDS));
            }
            assertEquals(2, shared.getOntologies().size());
            assertSharedOntologiesEmpty(shared);
        } finally {
            executor.shutdownNow();
            ownBottom.dispose();
            ownTop.dispose();
        }
        assertEquals(0, shared.getOntologies().size());
    }

    @Test @Tag("tautology")
    void tautologiesAreLocalForEverySignature() {
        // A tautology is local for every Sigma in both modes. Checked with
        // full, partial and empty signatures, including one with global TOP
        // proxies; the full signature exercises the reasoner on the
        // unrewritten axiom.
        List<OWLAxiom> tautologies = List.of(
                f.getOWLSubClassOfAxiom(a, f.getOWLObjectUnionOf(a, b)),
                f.getOWLSubClassOfAxiom(f.getOWLObjectIntersectionOf(a, b), a),
                f.getOWLEquivalentClassesAxiom(a,
                        f.getOWLObjectComplementOf(f.getOWLObjectComplementOf(a))),
                f.getOWLSubClassOfAxiom(f.getOWLThing(),
                        f.getOWLObjectUnionOf(max(1, c), min(2, c))));
        for (OWLAxiom axiom : tautologies) {
            both(true, axiom, a, b, c, r);
            both(true, axiom, a, c);
            both(true, axiom);
        }
    }

    @Test @Tag("metamorphic")
    void localityIsAntiMonotoneInTheSignature() {
        // If alpha is local w.r.t. Sigma, it is local w.r.t. every subset of
        // Sigma in the same mode: shrinking Sigma only restricts the set of
        // interpretations that must satisfy alpha. These are non-tautologies,
        // so their locality genuinely changes with Sigma.
        List<OWLEntity> universe = List.of(a, b, r, p);
        List<OWLAxiom> axioms = List.of(
                f.getOWLDisjointClassesAxiom(a, b),
                f.getOWLSubClassOfAxiom(a, b),
                f.getOWLSubClassOfAxiom(f.getOWLObjectSomeValuesFrom(r, a), b),
                f.getOWLSubClassOfAxiom(a, f.getOWLObjectSomeValuesFrom(r, b)),
                f.getOWLEquivalentClassesAxiom(a, f.getOWLObjectIntersectionOf(b,
                        f.getOWLObjectSomeValuesFrom(r, f.getOWLThing()))),
                f.getOWLDataPropertyDomainAxiom(p, a),
                f.getOWLSubClassOfAxiom(a,
                        f.getOWLDataSomeValuesFrom(p, f.getTopDatatype())),
                f.getOWLObjectPropertyRangeAxiom(r, a),
                f.getOWLFunctionalObjectPropertyAxiom(r),
                f.getOWLClassAssertionAxiom(a, i),
                // A global TOP proxy whenever r is absent.
                f.getOWLSubClassOfAxiom(a, max(1, b)));
        int subsets = 1 << universe.size();
        for (LocalityClass mode : List.of(LocalityClass.BOTTOM, LocalityClass.TOP)) {
            SemanticLocalityEvaluator evaluator = mode == LocalityClass.BOTTOM ? bottom : top;
            boolean sawLocal = false;
            for (OWLAxiom axiom : axioms) {
                boolean[] local = new boolean[subsets];
                for (int mask = 0; mask < subsets; mask++) {
                    local[mask] = evaluator.isLocal(axiom, subset(universe, mask));
                    sawLocal |= local[mask];
                }
                assertFalse(local[subsets - 1],
                        () -> mode + ": non-tautology local for its full signature: " + axiom);
                for (int mask = 0; mask < subsets; mask++) {
                    for (int smaller = 0; smaller < subsets; smaller++) {
                        if ((smaller & mask) != smaller || !local[mask]) {
                            continue;
                        }
                        final int larger = mask;
                        final int sub = smaller;
                        assertTrue(local[sub], () -> mode + ": " + axiom
                                + " is local for " + subset(universe, larger)
                                + " but not for its subset " + subset(universe, sub));
                    }
                }
            }
            assertTrue(sawLocal, mode + ": property checked only vacuously");
        }
        assertSharedOntologiesEmpty(manager);
    }

    private static Set<OWLEntity> subset(List<OWLEntity> universe, int mask) {
        Set<OWLEntity> result = new HashSet<>();
        for (int bit = 0; bit < universe.size(); bit++) {
            if ((mask & (1 << bit)) != 0) {
                result.add(universe.get(bit));
            }
        }
        return result;
    }
}