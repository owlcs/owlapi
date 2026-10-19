/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2020, Marc Robin Nolte
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.modularity.locality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Checks semantic bottom- and top-locality of OWL 2 DL axioms.
 *
 * <p>For a signature Sigma, non-signature classes and properties are interpreted
 * as empty (BOTTOM) or universal (TOP). The rewritten axiom is checked for
 * entailment from a set of <em>auxiliary axioms</em> over fresh symbols.
 * The auxiliary axioms are assumptions, not additional entailment goals.</p>
 *
 * <p>In TOP locality, a global object-cardinality restriction such as
 * {@code ObjectMaxCardinality(2 owl:topObjectProperty C)} violates OWL 2 DL's
 * simplicity restrictions. A fresh proxy class is used instead. Its auxiliary
 * definition uses only legal simple-role cardinalities and nominals; this
 * works under negation and arbitrary nesting.</p>
 *
 * <p>Datatype value spaces are fixed, but the data domain may also contain
 * values outside all built-in value spaces. TOP data restrictions depending on
 * their number are tested together, once for each distinguishable domain-size
 * case. A case is never inferred from a single satisfiability probe.</p>
 *
 * <p>A single empty ontology and reasoner are reused for all locality checks
 * that require no auxiliary axioms, including datatype cardinality probes.
 * Checks with auxiliary axioms use a short-lived ontology and reasoner, both
 * disposed of when that check ends. The caller owns the supplied ontology
 * manager: this evaluator never disposes of it or removes caller ontologies.
 * Call {@link #dispose()} (or {@link #close()}) when finished to dispose of
 * the shared reasoner and remove its empty ontology from the manager.</p>
 *
 * <p>The evaluator serializes calls to {@link #isLocal(OWLAxiom, Collection)}
 * and {@link #dispose()} because OWL reasoners are generally not guaranteed
 * to permit concurrent queries. Unsupported cases throw
 * {@link UnsupportedOperationException} rather than returning a guessed
 * locality result. The caller supplies a reasoner factory supporting the
 * OWL 2 DL constructs used in the checks and accepting fresh query entities.</p>
 *
 * @author Marc Robin Nolte (original implementation)
 */
public class SemanticLocalityEvaluator implements LocalityEvaluator, AutoCloseable {

    private final OWLOntologyManager manager;
    private final OWLDataFactory dataFactory;
    private final OWLReasonerFactory reasonerFactory;

    private final LocalityClass localityClass;

    /** The sole persistent ontology created by this evaluator; it remains empty. */
    private final OWLOntology emptyOntology;

    /** Shared reasoner for tests without auxiliary assumptions and datatype probes. */
    private final OWLReasoner emptyReasoner;
    private boolean disposed;

    /**
     * Creates an evaluator using the ontology manager owned by the caller.
     * A single empty ontology is registered with that manager, and its
     * reasoner is reused for all independent (axiom-free) validity checks.
     *
     * <p>The reasoner must allow fresh entities in query axioms, since a
     * genuinely empty ontology declares no classes, individuals or properties.
     * The caller retains ownership of the manager and all preexisting ontologies.
     * The evaluator owns only the empty ontology and the reasoners it creates.</p>
     *
     * @param localityClass either {@link LocalityClass#BOTTOM} or
     *        {@link LocalityClass#TOP}
     * @param manager the manager in which evaluator-owned ontologies are created
     * @param reasonerFactory factory for the shared and temporary reasoners
     * @throws OWLRuntimeException if the empty ontology cannot be created
     * @throws IllegalArgumentException if the reasoner disallows fresh query entities
     */
    public SemanticLocalityEvaluator(LocalityClass localityClass,
                                     OWLOntologyManager manager, OWLReasonerFactory reasonerFactory) {
        this.localityClass = Objects.requireNonNull(localityClass, "localityClass");
        if (localityClass != LocalityClass.BOTTOM && localityClass != LocalityClass.TOP) {
            throw new IllegalArgumentException("Expected BOTTOM or TOP locality");
        }
        this.manager = Objects.requireNonNull(manager, "manager");
        this.dataFactory = manager.getOWLDataFactory();
        this.reasonerFactory = Objects.requireNonNull(reasonerFactory, "reasonerFactory");

        OWLOntology createdOntology = null;
        OWLReasoner createdReasoner = null;
        boolean initialized = false;
        try {
            createdOntology = manager.createOntology();
            synchronized (reasonerFactory) {
                createdReasoner = reasonerFactory.createReasoner(createdOntology);
            }
            if (createdReasoner.getFreshEntityPolicy() != FreshEntityPolicy.ALLOW) {
                throw new IllegalArgumentException(
                        "The shared empty-ontology reasoner must allow fresh query entities");
            }
            initialized = true;
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException("Cannot create shared empty locality ontology", e);
        } finally {
            // Do not leak either resource if the constructor fails midway.
            if (!initialized) {
                try {
                    if (createdReasoner != null) {
                        createdReasoner.dispose();
                    }
                } finally {
                    if (createdOntology != null) {
                        manager.removeOntology(createdOntology);
                    }
                }
            }
        }
        emptyOntology = createdOntology;
        emptyReasoner = createdReasoner;
    }

    /**
     * Tests the validity of the rewritten axiom.
     *
     * <p>Without auxiliary premises, queries are answered by the persistent
     * reasoner over the empty ontology. When a rewriter introduces fresh proxy
     * definitions, this method creates an independent temporary ontology and
     * reasoner, which are both cleaned up in the {@code finally} block.</p>
     *
     * @param axiom the OWL 2 DL axiom to check
     * @param signature entities whose interpretation remains unrestricted
     * @return {@code true} iff the axiom is semantically local
     * @throws IllegalStateException if this evaluator has been disposed
     * @throws UnsupportedOperationException if rewriting or entailment is unsupported
     */
    @Override
    public synchronized boolean isLocal(OWLAxiom axiom, Collection<OWLEntity> signature) {
        if (disposed) {
            throw new IllegalStateException("Evaluator has been disposed");
        }
        Objects.requireNonNull(axiom, "axiom");
        Objects.requireNonNull(signature, "signature");
        if (!axiom.isLogicalAxiom()) {
            return true;
        }
        if (axiom.datatypesInSignature().anyMatch(dt -> !dt.isBuiltIn())) {
            throw new UnsupportedOperationException(
                    "A datatype definition/theory is required for: " + axiom);
        }

        // A single pass gathers ALL domain-size thresholds. They must share
        // one extra-value count: choosing a value independently for each TOP
        // restriction would be wrong, especially under negation.
        Set<Integer> thresholds = new TreeSet<>();
        LocalityRewriter initial = rewriter(signature, 0, thresholds);
        Rewriting first = initial.rewrite(axiom);
        if (!entailsRewriting(first)) {
            return false;
        }
        for (int extraValues : thresholds) {
            if (!entailsRewriting(rewriter(signature, extraValues, null).rewrite(axiom))) {
                return false;
            }
        }
        return true;
    }

    private LocalityRewriter rewriter(Collection<OWLEntity> signature, int extraValues,
                                      Set<Integer> thresholds) {
        return localityClass == LocalityClass.BOTTOM
                ? new BottomRewriter(dataFactory, signature, emptyReasoner, extraValues, thresholds)
                : new TopRewriter(dataFactory, signature, emptyReasoner, extraValues, thresholds);
    }

    private boolean entailsRewriting(Rewriting result) {
        if (result.auxiliaryAxioms.isEmpty()) {
            return entailsAll(emptyReasoner, result.rewrittenAxioms);
        }
        OWLOntology temporaryOntology = null;
        OWLReasoner temporaryReasoner = null;
        try {
            Set<OWLAxiom> premises = new HashSet<>(result.auxiliaryAxioms);
            for (OWLAxiom goal : result.rewrittenAxioms) {
                goal.signature().filter(e -> !e.isBuiltIn()).forEach(e ->
                        premises.add(dataFactory.getOWLDeclarationAxiom(e)));
            }
            temporaryOntology = manager.createOntology(premises);
            synchronized (reasonerFactory) {
                temporaryReasoner = reasonerFactory.createReasoner(temporaryOntology);
            }
            return entailsAll(temporaryReasoner, result.rewrittenAxioms);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException("Cannot create auxiliary locality ontology", e);
        } finally {
            try {
                if (temporaryReasoner != null) {
                    temporaryReasoner.dispose();
                }
            } finally {
                if (temporaryOntology != null) {
                    manager.removeOntology(temporaryOntology);
                }
            }
        }
    }

    /** Checks a conjunction of entailment goals against a fixed ontology. */
    private static boolean entailsAll(OWLReasoner reasoner, Collection<OWLAxiom> goals) {
        for (OWLAxiom goal : goals) {
            if (!reasoner.isEntailmentCheckingSupported(goal.getAxiomType())) {
                throw new UnsupportedOperationException(
                        "Reasoner cannot check entailment of " + goal.getAxiomType());
            }
        }
        for (OWLAxiom goal : goals) {
            if (!reasoner.isEntailed(goal)) {
                return false;
            }
        }
        return true;
    }

    /** The result of a single rewrite: premises and entailment goals. */
    private static final class Rewriting {
        final Set<OWLAxiom> auxiliaryAxioms;
        final List<OWLAxiom> rewrittenAxioms;

        Rewriting(Collection<OWLAxiom> auxiliaryAxioms,
                  Collection<OWLAxiom> rewrittenAxioms) {
            this.auxiliaryAxioms = Set.copyOf(auxiliaryAxioms);
            this.rewrittenAxioms = List.copyOf(rewrittenAxioms);
        }
    }

    /**
     * Disposes of the persistent reasoner and removes the empty ontology
     * created by this evaluator. The caller-provided manager is not disposed.
     * Repeated calls have no effect. Concurrent locality checks cannot overlap
     * with disposal because both operations synchronize on this evaluator.
     */
    @Override
    public synchronized void close() {
        if (disposed) {
            return;
        }
        disposed = true;
        try {
            emptyReasoner.dispose();
        } finally {
            manager.removeOntology(emptyOntology);
        }
    }

    /** Alias for {@link #close()}, following the OWLReasoner lifecycle API. */
    public void dispose() {
        close();
    }

    /**
     * Rewrites one axiom according to either empty (BOTTOM) or universal
     * (TOP) interpretation of classes and properties outside the signature.
     *
     * <p>An axiom can expand into several entailment goals. The returned list
     * is interpreted conjunctively; it is separate from the auxiliary premises.
     * In particular, we must
     * not turn {@code DisjointClasses(T,T)} into a one-operand disjointness
     * axiom, which would incorrectly lose its contradiction.</p>
     *
     * <p>This mutable visitor is strictly one-shot: call {@link #rewrite(OWLAxiom)}
     * once and discard it. Recursive class-expression rewriting is safe because
     * the previous expression result is saved and restored on each invocation.</p>
     */
    private abstract static class LocalityRewriter
            implements OWLAxiomVisitor, OWLClassExpressionVisitor {

        protected final OWLDataFactory dataFactory;
        protected final Set<OWLEntity> signature;
        protected OWLClassExpression rewrittenClassExpression;
        private final List<OWLAxiom> rewrittenAxioms = new ArrayList<>();
        private final Set<OWLAxiom> auxiliaryAxioms = new HashSet<>();
        private final OWLReasoner emptyReasoner;
        private final String freshPrefix = "urn:owlapi:semantic-locality:" + UUID.randomUUID() + ":";
        private final Map<OWLDataRange, Map<Integer, Boolean>> dataRangeChecks = new HashMap<>();
        private final int extraValues;
        private final Set<Integer> thresholds; // non-null only in the discovery pass
        private OWLDataRange fixedDatatypeUniverse;
        private boolean retainedUniversalDataRange;
        private boolean retainedRestrictedDataRange;
        private int nextFreshId;
        private boolean used;

        /** Maximal allowed cardinality for nominal-based global counting. */
        private static final int MAX_GLOBAL_CARDINALITY = 256;
        private static final int MAX_DATA_DOMAIN_THRESHOLD = 256;

        protected LocalityRewriter(OWLDataFactory dataFactory,
                                   Collection<OWLEntity> signature, OWLReasoner emptyReasoner,
                                   int extraValues, Set<Integer> thresholds) {
            this.dataFactory = Objects.requireNonNull(dataFactory);
            this.signature = new HashSet<>(Objects.requireNonNull(signature));
            this.emptyReasoner = Objects.requireNonNull(emptyReasoner);
            this.extraValues = extraValues;
            this.thresholds = thresholds;
        }

        /** Replacement interpretation for a named class outside the signature. */
        protected abstract OWLClass absentClassReplacement();

        /** Replacement interpretation for a named object property outside the signature. */
        protected abstract OWLObjectProperty absentObjectPropertyReplacement();

        /** Replacement interpretation for a named data property outside the signature. */
        protected abstract OWLDataProperty absentDataPropertyReplacement();

        /**
         * Rewrites exactly one axiom.
         *
         * @return separate immutable collections of auxiliary assumptions and
         *         jointly required entailment goals
         */
        public final Rewriting rewrite(OWLAxiom axiom) {
            if (used) {
                throw new IllegalStateException("Rewriters are single-use");
            }
            used = true;
            Objects.requireNonNull(axiom, "axiom").accept(this);
            if (retainedUniversalDataRange && retainedRestrictedDataRange) {
                // Even ranges that are individually correct under the fixed
                // datatype map can distinguish unnamed extra-domain values
                // when combined with a restriction on rdfs:Literal.
                throw new UnsupportedOperationException(
                        "Mixed retained data-range tests require explicit extra-value semantics: "
                                + axiom);
            }
            if (rewrittenAxioms.isEmpty()) {
                throw new UnsupportedOperationException("Unsupported logical axiom: " + axiom);
            }
            return new Rewriting(auxiliaryAxioms, rewrittenAxioms);
        }

        protected final void addRewrittenAxiom(OWLAxiom axiom) {
            rewrittenAxioms.add(Objects.requireNonNull(axiom));
        }

        protected final Collection<OWLAxiom> getRewrittenAxioms() {
            return List.copyOf(rewrittenAxioms);
        }


        /** Adds a premise used ONLY to define a fresh proxy interpretation. */
        protected final void addAuxiliaryAxiom(OWLAxiom axiom) {
            auxiliaryAxioms.add(Objects.requireNonNull(axiom));
        }

        /** Fresh IRIs never coincide with another IRI created by this query. */
        protected final IRI freshIri(String kind) {
            return IRI.create(freshPrefix + kind + (nextFreshId++));
        }

        /**
         * Creates a global Boolean concept: {@code X == exists U.X} forces
         * X to denote either the empty set or the entire nonempty object domain.
         */
        private OWLClass freshGlobalBoolean() {
            OWLClass proxy = dataFactory.getOWLClass(freshIri("global-"));
            addAuxiliaryAxiom(dataFactory.getOWLEquivalentClassesAxiom(
                    proxy, dataFactory.getOWLObjectSomeValuesFrom(
                            dataFactory.getOWLTopObjectProperty(), proxy)));
            return proxy;
        }

        /**
         * A single nominal cover says that C contains at most n individuals.
         * For n=0, C must be empty; for n>0, fresh individuals can be chosen
         * to cover all members of C whenever |C|<=n. No unique-name
         * assumption is made about those individuals.
         */
        private OWLClassExpression coverWithNominals(int n) {
            if (n == 0) {
                return dataFactory.getOWLNothing();
            }
            Set<OWLIndividual> names = new HashSet<>();
            for (int i = 0; i < n; i++) {
                names.add(dataFactory.getOWLNamedIndividual(freshIri("cover-")));
            }
            return dataFactory.getOWLObjectOneOf(names);
        }

        /**
         * An existential over the universal role makes the cardinality test
         * global while the fresh inner role stays simple for OWL 2 DL.
         * It is satisfiable exactly when C has at least n different elements.
         */
        private OWLClassExpression enoughObjects(int n, OWLClassExpression filler) {
            OWLObjectProperty simple = dataFactory.getOWLObjectProperty(freshIri("simple-"));
            return dataFactory.getOWLObjectSomeValuesFrom(
                    dataFactory.getOWLTopObjectProperty(),
                    dataFactory.getOWLObjectMinCardinality(n, simple, filler));
        }

        private void checkCardinalityBound(int n) {
            if (n > MAX_GLOBAL_CARDINALITY) {
                throw new UnsupportedOperationException(
                        "Global object cardinality " + n + " exceeds proxy limit "
                                + MAX_GLOBAL_CARDINALITY);
            }
        }

        /**
         * Proxy X for |C| >= n. The axioms say:
         * (1) X is empty or universal;
         * (2) X nonempty implies >=n elements of C (a fresh simple role
         *     witnesses these elements);
         * (3) X empty implies <=n-1 elements of C (nominal cover).
         * Thus X is uniquely forced to the intended truth value, irrespective
         * of the enclosing class expression, even under complement.
         */
        private OWLClassExpression globalAtLeast(int n, OWLClassExpression filler) {
            if (n == 0) {
                return dataFactory.getOWLThing();
            }
            if (n == 1) {
                return dataFactory.getOWLObjectSomeValuesFrom(
                        dataFactory.getOWLTopObjectProperty(), filler);
            }
            checkCardinalityBound(n);
            OWLClass x = freshGlobalBoolean();
            addAuxiliaryAxiom(dataFactory.getOWLSubClassOfAxiom(x, enoughObjects(n, filler)));
            addAuxiliaryAxiom(dataFactory.getOWLSubClassOfAxiom(
                    dataFactory.getOWLObjectIntersectionOf(
                            dataFactory.getOWLObjectComplementOf(x), filler),
                    coverWithNominals(n - 1)));
            return x;
        }

        /**
         * Proxy X for |C| <= n. When X is true, all C-elements must fit into
         * n fresh nominals. When X is false, a fresh simple role must witness
         * n+1 different C-elements. Either branch becomes impossible if X has
         * the wrong truth value. This provides an equisatisfiable expansion
         * for every interpretation of the non-fresh vocabulary.
         */
        private OWLClassExpression globalAtMost(int n, OWLClassExpression filler) {
            if (n == 0) {
                return dataFactory.getOWLObjectAllValuesFrom(
                        dataFactory.getOWLTopObjectProperty(),
                        dataFactory.getOWLObjectComplementOf(filler));
            }
            checkCardinalityBound(n);
            OWLClass x = freshGlobalBoolean();
            addAuxiliaryAxiom(dataFactory.getOWLSubClassOfAxiom(
                    dataFactory.getOWLObjectIntersectionOf(x, filler),
                    coverWithNominals(n)));
            addAuxiliaryAxiom(dataFactory.getOWLSubClassOfAxiom(
                    dataFactory.getOWLObjectComplementOf(x),
                    enoughObjects(n + 1, filler)));
            return x;
        }

        /** All fixed built-in value spaces, excluding rdfs:Literal (= the data domain). */
        private OWLDataRange fixedDatatypeUniverse() {
            if (fixedDatatypeUniverse == null) {
                fixedDatatypeUniverse = dataFactory.getOWLDataUnionOf(
                        java.util.Arrays.stream(OWL2Datatype.values())
                                .filter(dt -> dt != OWL2Datatype.RDFS_LITERAL)
                                .map(dt -> dataFactory.getOWLDatatype(dt.getIRI())));
            }
            return fixedDatatypeUniverse;
        }

        /** Every extra-domain value has the same membership in each supported range. */
        private boolean includesExtraValues(OWLDataRange range) {
            if (range.isTopDatatype()) {
                return true;
            }
            if (range instanceof OWLDataComplementOf) {
                return !includesExtraValues(((OWLDataComplementOf) range).getDataRange());
            }
            if (range instanceof OWLDataIntersectionOf) {
                return ((OWLDataIntersectionOf) range).operands()
                        .allMatch(this::includesExtraValues);
            }
            if (range instanceof OWLDataUnionOf) {
                return ((OWLDataUnionOf) range).operands()
                        .anyMatch(this::includesExtraValues);
            }
            if (range instanceof OWLDatatype || range instanceof OWLDataOneOf
                    || range instanceof OWLDatatypeRestriction) {
                return false;
            }
            throw new UnsupportedOperationException("Unsupported data range: " + range);
        }

        /** Only fixed-value-space cardinalities may be queried independently. */
        private boolean fixedPartHasAtLeast(OWLDataRange range, int n) {
            if (n <= 0) {
                return true;
            }
            OWLDataRange fixed = includesExtraValues(range)
                    ? dataFactory.getOWLDataIntersectionOf(range, fixedDatatypeUniverse())
                    : range;
            Map<Integer, Boolean> cache = dataRangeChecks.get(fixed);
            Boolean previous = cache == null ? null : cache.get(n);
            if (previous != null) {
                return previous;
            }
            OWLDataProperty fresh = dataFactory.getOWLDataProperty(freshIri("datatype-probe-"));
            OWLClassExpression probe = dataFactory.getOWLDataMinCardinality(n, fresh, fixed);
            boolean result = emptyReasoner.isSatisfiable(probe);
            dataRangeChecks.computeIfAbsent(fixed, ignored -> new HashMap<>()).put(n, result);
            return result;
        }

        /**
         * For |D|>=n, the fixed part contributes c values and all extra
         * values contribute either zero or t values. A single threshold t>=n-c
         * captures the remaining dependence on the data domain. All checks
         * in an axiom share t, including negated and nested restrictions.
         */
        private boolean dataRangeHasAtLeast(OWLDataRange range, int n) {
            if (n <= 0 || fixedPartHasAtLeast(range, n)) {
                return true;
            }
            if (!includesExtraValues(range)) {
                return false;
            }
            // Find c (the fixed cardinality, capped at n-1) by binary search.
            int low = 0, high = n;
            while (low + 1 < high) {
                int mid = low + (high - low) / 2;
                if (fixedPartHasAtLeast(range, mid)) {
                    low = mid;
                } else {
                    high = mid;
                }
            }
            int threshold = n - low;
            if (threshold > MAX_DATA_DOMAIN_THRESHOLD) {
                throw new UnsupportedOperationException(
                        "Data-domain threshold " + threshold + " exceeds supported limit "
                                + MAX_DATA_DOMAIN_THRESHOLD);
            }
            if (thresholds != null) {
                thresholds.add(threshold);
            }
            return extraValues >= threshold;
        }

        /** A data range is universally equal to the entire data domain. */
        private boolean dataRangeIsUniversal(OWLDataRange range) {
            OWLDataRange complement = dataFactory.getOWLDataComplementOf(range);
            return !includesExtraValues(complement) && !fixedPartHasAtLeast(complement, 1);
        }

        /**
         * A reasoner that assumes a fixed data domain cannot decide claims
         * involving ordinary properties and unnamed extra values. Do not allow
         * such claims to slip through as incorrect 'local' answers.
         */
        private void requireSupportedRetainedDataRange(OWLDataPropertyExpression p,
                                                       OWLDataRange range) {
            if (bottomData(p) || topData(p)) {
                return;
            }
            // The second case is just as important: D may cover ALL known
            // built-in values, yet omit extra data-domain values. Treating
            // DataSome(p,rdfs:Literal) and DataSome(p,D) as equivalent would
            // then be unsound, even though D itself contains no extras.
            boolean includesExtra = includesExtraValues(range);
            boolean dependsOnExtra = includesExtra ? !dataRangeIsUniversal(range)
                    : !fixedPartHasAtLeast(dataFactory.getOWLDataComplementOf(range), 1);
            if (dependsOnExtra) {
                throw new UnsupportedOperationException(
                        "Retained data property with interpretation-dependent data range: "
                                + p + " / " + range);
            }
            if (includesExtra) {
                retainedUniversalDataRange = true;
            } else {
                retainedRestrictedDataRange = true;
            }
        }

        /** Maps a globally true/false datatype claim to Thing/Nothing. */
        private OWLClass dataTruth(boolean proposition) {
            return proposition ? dataFactory.getOWLThing() : dataFactory.getOWLNothing();
        }

        /** Rewrites all class expressions lazily; caller must consume stream immediately. */
        protected final Stream<OWLClassExpression> rewrite(
                Stream<? extends OWLClassExpression> expressions) {
            return expressions.map(this::rewrite);
        }

        /**
         * Rewrites a class expression without losing the caller's visitor state.
         * The result may be non-trivial when the role becomes universal:
         * e.g. {@code some(topObjectProperty, C)} is NOT always {@code Thing}.
         */
        protected final OWLClassExpression rewrite(OWLClassExpression expression) {
            OWLClassExpression previous = rewrittenClassExpression;
            rewrittenClassExpression = null;
            try {
                Objects.requireNonNull(expression).accept(this);
                if (rewrittenClassExpression == null) {
                    throw new UnsupportedOperationException("Unsupported class expression: " + expression);
                }
                return rewrittenClassExpression;
            } finally {
                rewrittenClassExpression = previous;
            }
        }

        /**
         * Preserves built-in top/bottom roles regardless of the signature.
         * Inverses preserve their direction, while inverse of the universal or
         * empty role denotes that same role.
         */
        protected final OWLObjectPropertyExpression rewriteProperty(
                OWLObjectPropertyExpression property) {
            OWLObjectProperty named = property.getNamedProperty();
            if (signature.contains(named) || named.isOWLTopObjectProperty()
                    || named.isOWLBottomObjectProperty()) {
                return property;
            }
            return absentObjectPropertyReplacement();
        }

        /** Fixed built-in data properties are not replaceable signature entities. */
        protected final OWLDataPropertyExpression rewriteProperty(
                OWLDataPropertyExpression property) {
            OWLDataProperty named = property.asOWLDataProperty();
            if (signature.contains(named) || named.isOWLTopDataProperty()
                    || named.isOWLBottomDataProperty()) {
                return property;
            }
            return absentDataPropertyReplacement();
        }

        protected final boolean bottomObject(OWLObjectPropertyExpression p) {
            return p.getNamedProperty().isOWLBottomObjectProperty();
        }
        protected final boolean topObject(OWLObjectPropertyExpression p) {
            return p.getNamedProperty().isOWLTopObjectProperty();
        }
        protected final boolean bottomData(OWLDataPropertyExpression p) {
            return p.asOWLDataProperty().isOWLBottomDataProperty();
        }
        protected final boolean topData(OWLDataPropertyExpression p) {
            return p.asOWLDataProperty().isOWLTopDataProperty();
        }

        /** An axiom that is true in every interpretation. */
        protected final OWLAxiom tautology() {
            return dataFactory.getOWLSubClassOfAxiom(
                    dataFactory.getOWLThing(), dataFactory.getOWLThing());
        }

        /** An axiom that is false in every nonempty OWL interpretation. */
        protected final OWLAxiom contradiction() {
            return dataFactory.getOWLSubClassOfAxiom(
                    dataFactory.getOWLThing(), dataFactory.getOWLNothing());
        }

        // ---- Class expressions: these recursive rules apply to BOTH locality modes. ----

        @Override
        public final void visit(OWLClass ce) {
            // owl:Thing and owl:Nothing retain their fixed meaning even if
            // absent from the supplied signature.
            rewrittenClassExpression = signature.contains(ce) || ce.isOWLThing()
                    || ce.isOWLNothing() ? ce : absentClassReplacement();
        }

        @Override
        public final void visit(OWLObjectComplementOf ce) {
            rewrittenClassExpression = dataFactory.getOWLObjectComplementOf(
                    rewrite(ce.getOperand()));
        }

        @Override
        public final void visit(OWLObjectIntersectionOf ce) {
            // Each model interprets intersection as the intersection of its
            // rewritten operands; duplicates can be removed by the factory.
            rewrittenClassExpression = dataFactory.getOWLObjectIntersectionOf(
                    rewrite(ce.operands()));
        }

        @Override
        public final void visit(OWLObjectUnionOf ce) {
            rewrittenClassExpression = dataFactory.getOWLObjectUnionOf(
                    rewrite(ce.operands()));
        }

        /**
         * Rejects anonymous individuals inside class expressions. There they
         * are existential variables whose scope spans the whole axiom, e.g.
         * {@code A SubClassOf some(r, {_:x})} means "exists x such that every A
         * has r-successor x", which has no class-expression encoding.
         */
        private void requireNamed(OWLIndividual individual, OWLClassExpression context) {
            if (individual.isAnonymous()) {
                throw new UnsupportedOperationException(
                        "Anonymous individuals in class expressions are not supported: " + context);
            }
        }

        @Override
        public final void visit(OWLObjectOneOf ce) {
            ce.individuals().forEach(ind -> requireNamed(ind, ce));
            // Semantic locality does not replace individuals by Thing/Nothing.
            // A nominal continues denoting the listed individuals.
            rewrittenClassExpression = ce;
        }

        @Override
        public final void visit(OWLObjectSomeValuesFrom ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            OWLClassExpression f = rewrite(ce.getFiller());
            if (bottomObject(p) || f.isOWLNothing()) {
                rewrittenClassExpression = dataFactory.getOWLNothing();
            } else {
                // The universal role relates every pair. Existence is global:
                // if C is empty, some(top,C)=Nothing; otherwise it is Thing.
                // Keeping the restriction preserves this dependency on C.
                rewrittenClassExpression = dataFactory.getOWLObjectSomeValuesFrom(p, f);
            }
        }

        @Override
        public final void visit(OWLObjectAllValuesFrom ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            OWLClassExpression f = rewrite(ce.getFiller());
            if (bottomObject(p) || f.isOWLThing()) {
                // Universal restriction over an empty property is vacuously true.
                rewrittenClassExpression = dataFactory.getOWLThing();
            } else {
                // forall(top,C) holds for every individual exactly when the
                // whole interpretation domain is contained in C.
                rewrittenClassExpression = dataFactory.getOWLObjectAllValuesFrom(p, f);
            }
        }

        @Override
        public final void visit(OWLObjectHasValue ce) {
            requireNamed(ce.getFiller(), ce);
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            // A full relation connects every individual to the named target;
            // an empty relation connects none. Targets stay unchanged.
            rewrittenClassExpression = bottomObject(p) ? dataFactory.getOWLNothing()
                    : topObject(p) ? dataFactory.getOWLThing()
                    : dataFactory.getOWLObjectHasValue(p, ce.getFiller());
        }

        @Override
        public final void visit(OWLObjectHasSelf ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            // Every domain element is related to itself by the universal role.
            rewrittenClassExpression = bottomObject(p) ? dataFactory.getOWLNothing()
                    : topObject(p) ? dataFactory.getOWLThing()
                    : dataFactory.getOWLObjectHasSelf(p);
        }

        @Override
        public final void visit(OWLObjectMinCardinality ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            OWLClassExpression f = rewrite(ce.getFiller());
            int n = ce.getCardinality();
            if (n == 0) {
                rewrittenClassExpression = dataFactory.getOWLThing();
            } else if (bottomObject(p) || f.isOWLNothing()) {
                rewrittenClassExpression = dataFactory.getOWLNothing();
            } else if (topObject(p)) {
                // Cardinality over the universal relation counts ALL C-elements.
                // Use a legal global proxy rather than a non-simple role.
                rewrittenClassExpression = globalAtLeast(n, f);
            } else {
                rewrittenClassExpression = dataFactory.getOWLObjectMinCardinality(n, p, f);
            }
        }

        @Override
        public final void visit(OWLObjectMaxCardinality ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            OWLClassExpression f = rewrite(ce.getFiller());
            int n = ce.getCardinality();
            if (bottomObject(p) || f.isOWLNothing()) {
                rewrittenClassExpression = dataFactory.getOWLThing();
            } else if (topObject(p)) {
                rewrittenClassExpression = globalAtMost(n, f);
            } else {
                rewrittenClassExpression = dataFactory.getOWLObjectMaxCardinality(n, p, f);
            }
        }

        @Override
        public final void visit(OWLObjectExactCardinality ce) {
            OWLObjectPropertyExpression p = rewriteProperty(ce.getProperty());
            OWLClassExpression f = rewrite(ce.getFiller());
            int n = ce.getCardinality();
            if (bottomObject(p) || f.isOWLNothing()) {
                rewrittenClassExpression = n == 0
                        ? dataFactory.getOWLThing() : dataFactory.getOWLNothing();
            } else if (topObject(p)) {
                // |C|=n iff |C|>=n and |C|<=n. Both global tests have
                // legal OWL 2 DL representations, and nesting is safe.
                rewrittenClassExpression = dataFactory.getOWLObjectIntersectionOf(
                        globalAtLeast(n, f), globalAtMost(n, f));
            } else {
                rewrittenClassExpression = dataFactory.getOWLObjectExactCardinality(n, p, f);
            }
        }

        @Override
        public final void visit(OWLDataSomeValuesFrom ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            requireSupportedRetainedDataRange(p, ce.getFiller());
            rewrittenClassExpression = bottomData(p) ? dataFactory.getOWLNothing()
                    : topData(p) ? dataTruth(dataRangeHasAtLeast(ce.getFiller(), 1))
                    : dataFactory.getOWLDataSomeValuesFrom(p, ce.getFiller());
        }

        @Override
        public final void visit(OWLDataAllValuesFrom ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            if (!ce.getFiller().isTopDatatype()) {
                requireSupportedRetainedDataRange(p, dataFactory.getOWLDataComplementOf(ce.getFiller()));
            }
            if (bottomData(p) || ce.getFiller().isTopDatatype()) {
                // Empty property: no violating data values. Top datatype:
                // every possible value meets the range condition.
                rewrittenClassExpression = dataFactory.getOWLThing();
            } else if (topData(p)) {
                // Every data value is a p-successor; universal restriction
                // holds precisely if the data range covers the data domain.
                rewrittenClassExpression = dataTruth(!dataRangeHasAtLeast(
                        dataFactory.getOWLDataComplementOf(ce.getFiller()), 1));
            } else {
                rewrittenClassExpression = dataFactory.getOWLDataAllValuesFrom(
                        p, ce.getFiller());
            }
        }

        @Override
        public final void visit(OWLDataHasValue ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            if (!bottomData(p) && !topData(p)) {
                retainedRestrictedDataRange = true; // This literal is a fixed value.
            }
            rewrittenClassExpression = bottomData(p) ? dataFactory.getOWLNothing()
                    : topData(p) ? dataFactory.getOWLThing()
                    : dataFactory.getOWLDataHasValue(p, ce.getFiller());
        }

        @Override
        public final void visit(OWLDataMinCardinality ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            int n = ce.getCardinality();
            if (n > 0) {
                requireSupportedRetainedDataRange(p, ce.getFiller());
            }
            rewrittenClassExpression = n == 0 ? dataFactory.getOWLThing()
                    : bottomData(p) ? dataFactory.getOWLNothing()
                    : topData(p) ? dataTruth(dataRangeHasAtLeast(ce.getFiller(), n))
                    : dataFactory.getOWLDataMinCardinality(n, p, ce.getFiller());
        }

        @Override
        public final void visit(OWLDataMaxCardinality ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            int n = ce.getCardinality();
            requireSupportedRetainedDataRange(p, ce.getFiller());
            if (bottomData(p)) {
                rewrittenClassExpression = dataFactory.getOWLThing();
            } else if (topData(p)) {
                if (n == Integer.MAX_VALUE) {
                    throw new UnsupportedOperationException("Data max cardinality overflows int");
                }
                rewrittenClassExpression = dataTruth(!dataRangeHasAtLeast(ce.getFiller(), n + 1));
            } else {
                rewrittenClassExpression = dataFactory.getOWLDataMaxCardinality(n, p, ce.getFiller());
            }
        }

        @Override
        public final void visit(OWLDataExactCardinality ce) {
            OWLDataPropertyExpression p = rewriteProperty(ce.getProperty());
            int n = ce.getCardinality();
            requireSupportedRetainedDataRange(p, ce.getFiller());
            if (bottomData(p)) {
                rewrittenClassExpression = dataTruth(n == 0);
            } else if (topData(p)) {
                if (n == Integer.MAX_VALUE) {
                    throw new UnsupportedOperationException("Data exact cardinality overflows int");
                }
                rewrittenClassExpression = dataTruth(
                        dataRangeHasAtLeast(ce.getFiller(), n)
                                && !dataRangeHasAtLeast(ce.getFiller(), n + 1));
            } else {
                rewrittenClassExpression = dataFactory.getOWLDataExactCardinality(n, p, ce.getFiller());
            }
        }

        // ---- Axioms: all resulting axioms must hold together (conjunction). ----

        @Override
        public final void visit(OWLSubClassOfAxiom axiom) {
            // Direction matters: rewriting must NEVER swap sub/super classes.
            addRewrittenAxiom(dataFactory.getOWLSubClassOfAxiom(
                    rewrite(axiom.getSubClass()), rewrite(axiom.getSuperClass())));
        }

        private void emitEquivalentClasses(List<OWLClassExpression> operands) {
            // n-ary equivalence is conjunction of C0<->Ci. Pairwise GCIs do
            // not discard constraints if two classes collapse to the same CE.
            OWLClassExpression first = operands.get(0);
            for (int i = 1; i < operands.size(); i++) {
                OWLClassExpression other = operands.get(i);
                addRewrittenAxiom(dataFactory.getOWLSubClassOfAxiom(first, other));
                addRewrittenAxiom(dataFactory.getOWLSubClassOfAxiom(other, first));
            }
        }

        private void emitDisjointClasses(List<OWLClassExpression> operands) {
            // Disjoint(C1,...,Cn) iff every Ci AND Cj is empty (i<j).
            // Even if Ci and Cj become IDENTICAL after replacement, we must
            // require Ci<=Nothing, not simply deduplicate the arguments.
            for (int i = 0; i < operands.size(); i++) {
                for (int j = i + 1; j < operands.size(); j++) {
                    addRewrittenAxiom(dataFactory.getOWLSubClassOfAxiom(
                            dataFactory.getOWLObjectIntersectionOf(
                                    operands.get(i), operands.get(j)),
                            dataFactory.getOWLNothing()));
                }
            }
        }

        @Override
        public final void visit(OWLEquivalentClassesAxiom axiom) {
            emitEquivalentClasses(rewrite(axiom.classExpressions()).collect(Collectors.toList()));
        }

        @Override
        public final void visit(OWLDisjointClassesAxiom axiom) {
            emitDisjointClasses(rewrite(axiom.classExpressions()).collect(Collectors.toList()));
        }

        @Override
        public final void visit(OWLDisjointUnionAxiom axiom) {
            List<OWLClassExpression> rhs = rewrite(axiom.classExpressions())
                    .collect(Collectors.toList());
            OWLClassExpression lhs = rewrite(axiom.getOWLClass());
            // DisjointUnion(A, C1,...,Cn) means both A = union(Ci)
            // AND every pair of Ci is disjoint. The original A may become
            // Thing/Nothing and cannot stay the named lhs of DisjointUnion.
            OWLClassExpression union = rhs.size() == 1 ? rhs.get(0)
                    : dataFactory.getOWLObjectUnionOf(rhs.stream());
            emitEquivalentClasses(List.of(lhs, union));
            emitDisjointClasses(rhs);
        }

        /**
         * Expresses "condition D holds at term t" as a subclass entailment.
         *
         * <p>A named individual i denotes one fixed element, so D(i) iff
         * {@code {i} SubClassOf D}. The nominal {i} denotes exactly the
         * interpretation of i; individuals are never replaced under semantic
         * locality.</p>
         *
         * <p>Under the OWL 2 Direct Semantics an anonymous individual is an
         * existentially quantified variable, so D(_:x) iff D is nonempty, i.e.
         * {@code Thing SubClassOf some(owl:topObjectProperty, D)} (the domain
         * is nonempty). This is valid for an isolated axiom only: the module
         * extractor must preserve witnesses shared between several axioms.</p>
         *
         * <p>Both forms are subclass entailments, which avoids relying on the
         * reasoner's handling of ABox assertions about individuals absent
         * from the shared empty ontology.</p>
         */
        protected final OWLAxiom holdsAt(OWLIndividual term, OWLClassExpression condition) {
            if (term.isNamed()) {
                return dataFactory.getOWLSubClassOfAxiom(
                        dataFactory.getOWLObjectOneOf(term.asOWLNamedIndividual()), condition);
            }
            return dataFactory.getOWLSubClassOfAxiom(dataFactory.getOWLThing(),
                    dataFactory.getOWLObjectSomeValuesFrom(
                            dataFactory.getOWLTopObjectProperty(), condition));
        }

        @Override
        public final void visit(OWLClassAssertionAxiom axiom) {
            // C(t) iff C holds at t; see holdsAt for named and anonymous t.
            addRewrittenAxiom(holdsAt(axiom.getIndividual(),
                    rewrite(axiom.getClassExpression())));
        }

        @Override
        public final void visit(OWLSameIndividualAxiom axiom) {
            // Individuals are not bottom/top-replaced. Equality constraints
            // remain logically significant regardless of their signature.
            // An anonymous individual is existential and can always be chosen
            // equal to the others, so only equalities between named ones remain.
            List<OWLIndividual> named = axiom.individuals()
                    .filter(OWLIndividual::isNamed).collect(Collectors.toList());
            if (named.size() == axiom.individuals().count()) {
                addRewrittenAxiom(axiom.getAxiomWithoutAnnotations());
            } else {
                addRewrittenAxiom(named.size() < 2 ? tautology()
                        : dataFactory.getOWLSameIndividualAxiom(named));
            }
        }

        @Override
        public final void visit(OWLDifferentIndividualsAxiom axiom) {
            if (axiom.individuals().anyMatch(OWLIndividual::isAnonymous)) {
                // Two or more distinct terms can never all denote different
                // elements of a one-element domain, so this is never valid.
                // (The same holds for named individuals; their case is left
                // to the reasoner as before.)
                addRewrittenAxiom(contradiction());
            } else {
                addRewrittenAxiom(axiom.getAxiomWithoutAnnotations());
            }
        }

        @Override
        public final void visit(OWLObjectPropertyDomainAxiom axiom) {
            addRewrittenAxiom(dataFactory.getOWLObjectPropertyDomainAxiom(
                    rewriteProperty(axiom.getProperty()), rewrite(axiom.getDomain())));
        }

        @Override
        public final void visit(OWLObjectPropertyRangeAxiom axiom) {
            addRewrittenAxiom(dataFactory.getOWLObjectPropertyRangeAxiom(
                    rewriteProperty(axiom.getProperty()), rewrite(axiom.getRange())));
        }

        @Override
        public final void visit(OWLDataPropertyDomainAxiom axiom) {
            OWLDataPropertyExpression p = rewriteProperty(axiom.getProperty());
            OWLClassExpression d = rewrite(axiom.getDomain());
            // Domain(topData,C) iff Thing<=C; Domain(bottomData,C) is true.
            addRewrittenAxiom(bottomData(p) ? tautology()
                    : topData(p) ? dataFactory.getOWLSubClassOfAxiom(dataFactory.getOWLThing(), d)
                    : dataFactory.getOWLDataPropertyDomainAxiom(p, d));
        }

        @Override
        public final void visit(OWLDataPropertyRangeAxiom axiom) {
            OWLDataPropertyExpression p = rewriteProperty(axiom.getProperty());
            // Range(topData,D) holds iff D covers the entire data domain.
            // A range axiom mentioning topData is not legal OWL 2 DL, so
            // the independent datatype probe determines its exact truth.
            // A retained data property is arbitrary: its range axiom is valid
            // iff D covers the entire data domain, exactly as for topData.
            // Direct validity avoids a reasoner's potentially fixed data domain.
            addRewrittenAxiom(bottomData(p) || dataRangeIsUniversal(axiom.getRange())
                    ? tautology() : contradiction());
        }

        @Override
        public final void visit(OWLSubObjectPropertyOfAxiom axiom) {
            addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(
                    rewriteProperty(axiom.getSubProperty()),
                    rewriteProperty(axiom.getSuperProperty())));
        }

        @Override
        public final void visit(OWLSubDataPropertyOfAxiom axiom) {
            addRewrittenAxiom(rewriteDataSubPropertyAxiom(
                    rewriteProperty(axiom.getSubProperty()),
                    rewriteProperty(axiom.getSuperProperty())));
        }

        /**
         * Data top is permitted only on the RIGHT of a data-subproperty
         * axiom in OWL 2 DL. A nontrivial topData <= p requirement cannot
         * simply be materialized in OWL 2 DL, so it is explicitly rejected.
         */
        private OWLAxiom rewriteDataSubPropertyAxiom(
                OWLDataPropertyExpression sub, OWLDataPropertyExpression sup) {
            if (bottomData(sub) || topData(sup) || sub.equals(sup)) {
                return tautology();
            }
            if (topData(sub)) {
                // Whole property-only inclusion is never universally valid
                // when the superproperty is ordinary or bottom.
                return contradiction();
            }
            return dataFactory.getOWLSubDataPropertyOfAxiom(sub, sup);
        }

        @Override
        public final void visit(OWLSubPropertyChainOfAxiom axiom) {
            List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain().stream()
                    .map(this::rewriteProperty).collect(Collectors.toList());
            OWLObjectPropertyExpression sup = rewriteProperty(axiom.getSuperProperty());
            // A bottom role in the antecedent makes the whole chain empty.
            // Every chain is a subproperty of the universal role.
            if (chain.stream().anyMatch(this::bottomObject) || topObject(sup)) {
                addRewrittenAxiom(tautology());
            } else if (bottomObject(sup)) {
                // R1 o ... o Rn <= bottom means "no chain of this length".
                // Express this via universally quantified negation rather
                // than a role-chain axiom with a special bottom super-role.
                OWLClassExpression noPath = dataFactory.getOWLNothing();
                for (int i = chain.size() - 1; i >= 0; i--) {
                    noPath = dataFactory.getOWLObjectAllValuesFrom(chain.get(i), noPath);
                }
                addRewrittenAxiom(dataFactory.getOWLSubClassOfAxiom(
                        dataFactory.getOWLThing(), noPath));
            } else if (chain.stream().anyMatch(this::topObject)) {
                // A top role in a nontrivial chain can break OWL 2 DL's
                // regularity restrictions. No generally applicable rewrite
                // is supplied here, so reject it explicitly.
                throw new UnsupportedOperationException(
                        "TOP role in a nontrivial property chain: " + axiom);
            } else {
                addRewrittenAxiom(dataFactory.getOWLSubPropertyChainOfAxiom(chain, sup));
            }
        }

        @Override
        public final void visit(OWLInverseObjectPropertiesAxiom axiom) {
            addRewrittenAxiom(dataFactory.getOWLInverseObjectPropertiesAxiom(
                    rewriteProperty(axiom.getFirstProperty()),
                    rewriteProperty(axiom.getSecondProperty())));
        }

        @Override
        public final void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            List<OWLObjectPropertyExpression> ps = axiom.properties()
                    .map(this::rewriteProperty).collect(Collectors.toList());
            // Equivalence is bidirectional inclusion. Unlike an n-ary
            // factory call, pairwise inclusion survives collapsing operands.
            OWLObjectPropertyExpression first = ps.get(0);
            for (int i = 1; i < ps.size(); i++) {
                addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(first, ps.get(i)));
                addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(ps.get(i), first));
            }
        }

        @Override
        public final void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            List<OWLDataPropertyExpression> ps = axiom.properties()
                    .map(this::rewriteProperty).collect(Collectors.toList());
            OWLDataPropertyExpression first = ps.get(0);
            for (int i = 1; i < ps.size(); i++) {
                addRewrittenAxiom(rewriteDataSubPropertyAxiom(first, ps.get(i)));
                addRewrittenAxiom(rewriteDataSubPropertyAxiom(ps.get(i), first));
            }
        }

        private void emitDisjointObjectProperties(List<OWLObjectPropertyExpression> ps) {
            for (int i = 0; i < ps.size(); i++) {
                for (int j = i + 1; j < ps.size(); j++) {
                    OWLObjectPropertyExpression p = ps.get(i), q = ps.get(j);
                    if (bottomObject(p) || bottomObject(q)) {
                        addRewrittenAxiom(tautology());
                    } else if (topObject(p) && topObject(q)) {
                        // Universal relation intersects itself; domain nonempty.
                        addRewrittenAxiom(contradiction());
                    } else if (topObject(p)) {
                        // top and q disjoint iff q is empty.
                        addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(
                                q, dataFactory.getOWLBottomObjectProperty()));
                    } else if (topObject(q)) {
                        addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(
                                p, dataFactory.getOWLBottomObjectProperty()));
                    } else if (p.equals(q)) {
                        addRewrittenAxiom(dataFactory.getOWLSubObjectPropertyOfAxiom(
                                p, dataFactory.getOWLBottomObjectProperty()));
                    } else {
                        addRewrittenAxiom(dataFactory.getOWLDisjointObjectPropertiesAxiom(p, q));
                    }
                }
            }
        }

        private void emitDisjointDataProperties(List<OWLDataPropertyExpression> ps) {
            for (int i = 0; i < ps.size(); i++) {
                for (int j = i + 1; j < ps.size(); j++) {
                    OWLDataPropertyExpression p = ps.get(i), q = ps.get(j);
                    if (bottomData(p) || bottomData(q)) {
                        addRewrittenAxiom(tautology());
                    } else if (topData(p) && topData(q)) {
                        addRewrittenAxiom(contradiction());
                    } else if (topData(p)) {
                        addRewrittenAxiom(dataFactory.getOWLSubDataPropertyOfAxiom(
                                q, dataFactory.getOWLBottomDataProperty()));
                    } else if (topData(q)) {
                        addRewrittenAxiom(dataFactory.getOWLSubDataPropertyOfAxiom(
                                p, dataFactory.getOWLBottomDataProperty()));
                    } else if (p.equals(q)) {
                        addRewrittenAxiom(dataFactory.getOWLSubDataPropertyOfAxiom(
                                p, dataFactory.getOWLBottomDataProperty()));
                    } else {
                        addRewrittenAxiom(dataFactory.getOWLDisjointDataPropertiesAxiom(p, q));
                    }
                }
            }
        }

        @Override
        public final void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            emitDisjointObjectProperties(axiom.properties()
                    .map(this::rewriteProperty).collect(Collectors.toList()));
        }

        @Override
        public final void visit(OWLDisjointDataPropertiesAxiom axiom) {
            emitDisjointDataProperties(axiom.properties()
                    .map(this::rewriteProperty).collect(Collectors.toList()));
        }

        /**
         * The class of elements that are p-related to o, for p retained:
         * {@code hasValue(p, o)} for named o, {@code hasSelf(p)} when o is the
         * subject's own anonymous variable, and {@code some(p, Thing)} for a
         * distinct anonymous o (any witness will do).
         */
        private OWLClassExpression relatedTo(OWLObjectPropertyExpression p,
                                             OWLIndividual subject, OWLIndividual object) {
            if (object.isNamed()) {
                return dataFactory.getOWLObjectHasValue(p, object);
            }
            return object.equals(subject) ? dataFactory.getOWLObjectHasSelf(p)
                    : dataFactory.getOWLObjectSomeValuesFrom(p, dataFactory.getOWLThing());
        }

        @Override
        public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            if (topObject(p)) {
                addRewrittenAxiom(tautology());
            } else if (bottomObject(p)) {
                addRewrittenAxiom(contradiction());
            } else {
                // p(s, o) iff "related to o" holds at s.
                addRewrittenAxiom(holdsAt(axiom.getSubject(),
                        relatedTo(p, axiom.getSubject(), axiom.getObject())));
            }
        }

        @Override
        public final void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            OWLIndividual s = axiom.getSubject();
            OWLIndividual o = axiom.getObject();
            if (topObject(p)) {
                addRewrittenAxiom(contradiction());
            } else if (bottomObject(p)) {
                addRewrittenAxiom(tautology());
            } else if (o.isNamed() || o.equals(s)) {
                // not p(s, o) iff "not related to o" holds at s.
                addRewrittenAxiom(holdsAt(s, dataFactory.getOWLObjectComplementOf(
                        relatedTo(p, s, o))));
            } else if (s.isNamed()) {
                // Some element y with not p(s, y): anchor at y instead, i.e.
                // y is not an inverse-p-successor of s.
                addRewrittenAxiom(holdsAt(o, dataFactory.getOWLObjectComplementOf(
                        dataFactory.getOWLObjectHasValue(p.getInverseProperty(), s))));
            } else {
                // Two distinct anonymous terms: exists x, y with not p(x, y).
                // p is retained here, and an interpretation may make it
                // universal, so this is never valid.
                addRewrittenAxiom(contradiction());
            }
        }

        @Override
        public final void visit(OWLDataPropertyAssertionAxiom axiom) {
            OWLDataPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(topData(p) ? tautology()
                    : bottomData(p) ? contradiction()
                    : holdsAt(axiom.getSubject(),
                    dataFactory.getOWLDataHasValue(p, axiom.getObject())));
        }

        @Override
        public final void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            OWLDataPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(topData(p) ? contradiction()
                    : bottomData(p) ? tautology()
                    : holdsAt(axiom.getSubject(), dataFactory.getOWLObjectComplementOf(
                    dataFactory.getOWLDataHasValue(p, axiom.getObject()))));
        }

        @Override
        public final void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            // Both empty and universal relations are symmetric.
            addRewrittenAxiom(bottomObject(p) || topObject(p) ? tautology()
                    : dataFactory.getOWLSymmetricObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            // Both empty and universal relations are transitive.
            addRewrittenAxiom(bottomObject(p) || topObject(p) ? tautology()
                    : dataFactory.getOWLTransitiveObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(topObject(p) ? tautology()
                    : bottomObject(p) ? contradiction()
                    : dataFactory.getOWLReflexiveObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(bottomObject(p) ? tautology()
                    : topObject(p) ? contradiction()
                    : dataFactory.getOWLIrreflexiveObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(bottomObject(p) ? tautology()
                    : topObject(p) ? contradiction()
                    : dataFactory.getOWLAsymmetricObjectPropertyAxiom(p));
        }

        private OWLAxiom singletonDomainAxiom() {
            // Functional(topObjectProperty) and inverse-functional(top)
            // demand |Delta|<=1. Since Delta is nonempty, that is exactly
            // Thing <= {a}, for ANY named a, even a fresh one.
            OWLNamedIndividual witness = dataFactory.getOWLNamedIndividual(
                    freshIri("singleton-witness-"));
            return dataFactory.getOWLSubClassOfAxiom(dataFactory.getOWLThing(),
                    dataFactory.getOWLObjectOneOf(witness));
        }

        @Override
        public final void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(bottomObject(p) ? tautology()
                    : topObject(p) ? singletonDomainAxiom()
                    : dataFactory.getOWLFunctionalObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            OWLObjectPropertyExpression p = rewriteProperty(axiom.getProperty());
            addRewrittenAxiom(bottomObject(p) ? tautology()
                    : topObject(p) ? singletonDomainAxiom()
                    : dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLFunctionalDataPropertyAxiom axiom) {
            OWLDataPropertyExpression p = rewriteProperty(axiom.getProperty());
            // owl:topDataProperty associates an individual with ALL data
            // values. OWL 2's fixed datatype map has more than one value,
            // so this relation can never be functional.
            addRewrittenAxiom(bottomData(p) ? tautology()
                    : topData(p) ? contradiction()
                    : dataFactory.getOWLFunctionalDataPropertyAxiom(p));
        }

        @Override
        public final void visit(OWLHasKeyAxiom axiom) {
            // Keys constrain equalities between named individuals. They must
            // not be declared tautological just because their class or roles
            // are outside the signature; substitute them instead.
            Set<OWLPropertyExpression> properties = new HashSet<>();
            axiom.objectPropertyExpressions().map(this::rewriteProperty)
                    .forEach(properties::add);
            axiom.dataPropertyExpressions().map(this::rewriteProperty)
                    .forEach(properties::add);
            if (properties.stream().anyMatch(p -> p instanceof OWLObjectPropertyExpression
                    && bottomObject((OWLObjectPropertyExpression) p)
                    || p instanceof OWLDataPropertyExpression
                    && bottomData((OWLDataPropertyExpression) p))) {
                addRewrittenAxiom(tautology()); // No two objects can share a bottom-key value.
                return;
            }
            // Universal key components impose no matching requirement.
            properties.removeIf(p -> p instanceof OWLObjectPropertyExpression
                    && topObject((OWLObjectPropertyExpression) p)
                    || p instanceof OWLDataPropertyExpression
                    && topData((OWLDataPropertyExpression) p));
            if (properties.isEmpty()) {
                // Keep the key legal. A universal object-key component is
                // semantically vacuous, leaving at most one NAMED C-individual.
                properties.add(dataFactory.getOWLTopObjectProperty());
            }
            addRewrittenAxiom(dataFactory.getOWLHasKeyAxiom(
                    rewrite(axiom.getClassExpression()), properties));
        }

        @Override
        public final void visit(OWLDatatypeDefinitionAxiom axiom) {
            // Datatype definitions modify the datatype interpretation itself.
            // An empty-context datatype satisfiability probe cannot account
            // for such definitions, and many OWL 2 reasoners do not support
            // entailment checking for them. Do not silently assume locality.
            throw new UnsupportedOperationException(
                    "DatatypeDefinition axioms require a separate datatype theory: " + axiom);
        }

        @Override
        public final void visit(SWRLRule rule) {
            // SWRL variables/atoms require a different rewriting semantics;
            // leaving the rule unchanged could incorrectly mark it local.
            throw new UnsupportedOperationException("SWRL rules are not supported: " + rule);
        }
    }

    /** Empty interpretations for every ordinary absent class or property. */
    private static final class BottomRewriter extends LocalityRewriter {
        private BottomRewriter(OWLDataFactory f, Collection<OWLEntity> sig,
                               OWLReasoner emptyReasoner, int extraValues,
                               Set<Integer> thresholds) {
            super(f, sig, emptyReasoner, extraValues, thresholds);
        }
        @Override
        protected OWLClass absentClassReplacement() {
            return dataFactory.getOWLNothing();
        }
        @Override
        protected OWLObjectProperty absentObjectPropertyReplacement() {
            return dataFactory.getOWLBottomObjectProperty();
        }
        @Override
        protected OWLDataProperty absentDataPropertyReplacement() {
            return dataFactory.getOWLBottomDataProperty();
        }
    }

    /** Universal interpretations for every ordinary absent class or property. */
    private static final class TopRewriter extends LocalityRewriter {
        private TopRewriter(OWLDataFactory f, Collection<OWLEntity> sig,
                            OWLReasoner emptyReasoner, int extraValues,
                            Set<Integer> thresholds) {
            super(f, sig, emptyReasoner, extraValues, thresholds);
        }
        @Override
        protected OWLClass absentClassReplacement() {
            return dataFactory.getOWLThing();
        }
        @Override
        protected OWLObjectProperty absentObjectPropertyReplacement() {
            return dataFactory.getOWLTopObjectProperty();
        }
        @Override
        protected OWLDataProperty absentDataPropertyReplacement() {
            return dataFactory.getOWLTopDataProperty();
        }
    }
}