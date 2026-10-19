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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.modularity.ModuleExtractor;

/**
 * Base class for locality-based module extractors, using the entity-indexed
 * worklist algorithm. Axioms that share logical anonymous individuals (existential
 * ABox witnesses) are checked and included together, as one extraction unit, so
 * their shared variables cannot be separated.
 *
 * @author Marc Robin Nolte
 */
public abstract class LocalityModuleExtractor implements ModuleExtractor {

    private final @Nonnull Set<OWLAxiom> axiomBase;
    private final @Nonnull LocalityClass localityClass;
    private final OWLDataFactory dataFactory;
    private final Map<OWLEntity, Set<OWLAxiom>> axiomsContainingEntity = new HashMap<>();
    /** The group of every axiom of the base with logical anonymous individuals. */
    private final Map<OWLAxiom, Set<OWLAxiom>> anonymousGroupOf = new HashMap<>();
    private final Map<LocalityEvaluator, Map<OWLAxiom, Boolean>> globalCache = new HashMap<>();

    LocalityModuleExtractor(LocalityClass localityClass, Stream<OWLAxiom> axiomBase) {
        this(localityClass, axiomBase, null);
    }

    /**
     * @param dataFactory factory for compiling connected anonymous assertions;
     *                    may be null, in which case every connected group of
     *                    logical anonymous assertions is kept in every module
     */
    LocalityModuleExtractor(LocalityClass localityClass, Stream<OWLAxiom> axiomBase,
                            OWLDataFactory dataFactory) {
        this.axiomBase = Objects.requireNonNull(axiomBase, "axiomBase")
                .collect(Collectors.toSet());
        this.localityClass = Objects.requireNonNull(localityClass, "localityClass");
        // The tools module must not depend on OWLManager from owlapi-apibinding,
        // so the factory cannot be created here.
        this.dataFactory = dataFactory;
        initialize();
    }

    @Override
    public @Nonnull Stream<OWLAxiom> axiomBase() {
        return axiomBase.stream();
    }

    @Override
    public boolean containsAxiom(OWLAxiom axiom) {
        return axiomBase.contains(axiom);
    }

    protected abstract LocalityEvaluator bottomEvaluator();

    protected abstract LocalityEvaluator topEvaluator();

    @Nonnull
    public LocalityClass getLocalityClass() {
        return localityClass;
    }

    @Override
    public final Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
                                          Optional<Predicate<OWLAxiom>> axiomFilter) {
        Set<OWLEntity> seed = Objects.requireNonNull(signature).collect(Collectors.toSet());
        Predicate<OWLAxiom> filter = Objects.requireNonNull(axiomFilter)
                .orElse(axiom -> true);
        Set<OWLAxiom> allowed = axiomBase.stream().filter(filter).collect(Collectors.toSet());

        LocalityEvaluator bottom = localityClass == LocalityClass.TOP ? null : bottomEvaluator();
        LocalityEvaluator top = localityClass == LocalityClass.BOTTOM ? null : topEvaluator();
        LocalityEvaluator first = localityClass == LocalityClass.TOP ? top : bottom;
        Set<OWLAxiom> module = extractIndexed(seed, allowed, first);
        if (localityClass == LocalityClass.STAR) {
            LocalityEvaluator next = top;
            int oldSize;
            do {
                oldSize = module.size();
                module = extractIndexed(seed, module, next);
                next = next == top ? bottom : top;
            } while (oldSize != module.size());
        }
        return module.stream();
    }

    /**
     * Axioms sharing anonymous individuals are only included as a whole group, so the
     * signature of the whole group decides whether they occur in modules.
     */
    @Override
    public @Nonnull Stream<OWLEntity> seedSignature(OWLAxiom axiom) {
        Set<OWLAxiom> group = anonymousGroupOf.get(Objects.requireNonNull(axiom, "axiom"));
        return group == null ? axiom.signature()
                : group.stream().flatMap(OWLAxiom::signature).distinct();
    }

    /**
     * Decides the default's question (is the axiom absent from the module for its own
     * signature, with only it eligible) directly, without scanning the axiom base.
     * An axiom sharing anonymous individuals is tested together with its group,
     * because the group is only ever included as a whole.
     */
    @Override
    public boolean noModuleContains(OWLAxiom axiom) {
        Set<OWLAxiom> group = anonymousGroupOf.get(Objects.requireNonNull(axiom, "axiom"));
        ExtractionUnit unit = group != null ? compileAnonymousUnit(group, dataFactory)
                : hasLogicalAnonymousIndividuals(axiom)
                        ? compileAnonymousUnit(Collections.singleton(axiom), dataFactory)
                        : new ExtractionUnit(Collections.singleton(axiom), axiom, false);
        // Locality only depends on the unit's own symbols, all of which are in the seed.
        switch (localityClass) {
            case BOTTOM:
                return unit.isLocal(unit.signature, bottomEvaluator());
            case TOP:
                return unit.isLocal(unit.signature, topEvaluator());
            default:
                // A STAR module keeps the unit only if both nested passes do.
                return unit.isLocal(unit.signature, bottomEvaluator())
                        || unit.isLocal(unit.signature, topEvaluator());
        }
    }

    private static boolean hasLogicalAnonymousIndividuals(OWLAxiom axiom) {
        return axiom.isLogicalAxiom()
                && axiom.getAxiomWithoutAnnotations().anonymousIndividuals().findAny().isPresent();
    }

    /**
     * The indexed worklist. Global axioms must be checked even for empty seeds.
     * Axioms with logical anonymous individuals are handled as units, indexed
     * like ordinary axioms; filtering happens before the units are formed, so a
     * filtered-out assertion neither constrains nor links a surviving witness.
     */
    private Set<OWLAxiom> extractIndexed(Set<OWLEntity> seed, Set<OWLAxiom> allowed,
                                         LocalityEvaluator evaluator) {
        List<ExtractionUnit> units = anonymousUnits(allowed);
        Map<OWLEntity, List<ExtractionUnit>> unitsContainingEntity = new HashMap<>();
        for (ExtractionUnit unit : units) {
            unit.signature.forEach(entity -> unitsContainingEntity
                    .computeIfAbsent(entity, ignored -> new ArrayList<>()).add(unit));
        }
        Set<OWLAxiom> result = new HashSet<>();
        Set<ExtractionUnit> includedUnits = new HashSet<>();
        Set<OWLEntity> signature = new HashSet<>(seed);
        Set<OWLEntity> work = new HashSet<>(seed);
        // With the empty signature, none of the original names is retained.
        // Every non-local axiom in that interpretation must be included before
        // the entity-indexed worklist can discover dependent axioms.
        Set<OWLEntity> empty = Collections.emptySet();
        for (OWLAxiom axiom : allowed) {
            if (!anonymousGroupOf.containsKey(axiom)
                    && globallyNonLocal(axiom, evaluator, empty)) {
                addNonLocal(axiom, signature, result, work, evaluator, true);
            }
        }
        for (ExtractionUnit unit : units) {
            if (unit.conservativelyNonLocal
                    || globallyNonLocal(unit.localityAxiom, evaluator, empty)) {
                addUnit(unit, signature, result, includedUnits, work);
            }
        }
        while (!work.isEmpty()) {
            OWLEntity entity = work.iterator().next();
            work.remove(entity);
            for (OWLAxiom axiom : axiomsContainingEntity.getOrDefault(entity,
                    Collections.emptySet())) {
                if (allowed.contains(axiom) && !anonymousGroupOf.containsKey(axiom)) {
                    addNonLocal(axiom, signature, result, work, evaluator, false);
                }
            }
            for (ExtractionUnit unit : unitsContainingEntity.getOrDefault(entity,
                    Collections.emptyList())) {
                if (!includedUnits.contains(unit) && !unit.isLocal(signature, evaluator)) {
                    addUnit(unit, signature, result, includedUnits, work);
                }
            }
        }
        return result;
    }

    /** Cache the empty-signature test without evaluating filtered-out axioms. */
    private synchronized boolean globallyNonLocal(OWLAxiom axiom, LocalityEvaluator evaluator,
                                                  Set<OWLEntity> emptySignature) {
        return globalCache.computeIfAbsent(evaluator, ignored -> new HashMap<>())
                .computeIfAbsent(axiom, ignored -> !evaluator.isLocal(axiom, emptySignature));
    }

    private static void addNonLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                                    Set<OWLAxiom> module, Set<OWLEntity> work,
                                    LocalityEvaluator evaluator, boolean knownNonLocal) {
        if (!module.contains(axiom) && (knownNonLocal || !evaluator.isLocal(axiom, signature))) {
            module.add(axiom);
            axiom.signature().filter(signature::add).forEach(work::add);
        }
    }

    private static void addUnit(ExtractionUnit unit, Set<OWLEntity> signature,
                                Set<OWLAxiom> module, Set<ExtractionUnit> includedUnits,
                                Set<OWLEntity> work) {
        if (includedUnits.add(unit)) {
            module.addAll(unit.originals);
            unit.signature.stream().filter(signature::add).forEach(work::add);
        }
    }

    private static final class ExtractionUnit {
        final Set<OWLAxiom> originals;
        final Set<OWLEntity> signature;
        final OWLAxiom localityAxiom;
        final boolean conservativelyNonLocal;

        ExtractionUnit(Set<OWLAxiom> originals, OWLAxiom localityAxiom,
                       boolean conservativelyNonLocal) {
            this.originals = Collections.unmodifiableSet(new HashSet<>(originals));
            this.signature = originals.stream().flatMap(OWLAxiom::signature)
                    .collect(Collectors.toSet());
            this.localityAxiom = localityAxiom;
            this.conservativelyNonLocal = conservativelyNonLocal;
        }

        boolean isLocal(Set<OWLEntity> signature, LocalityEvaluator evaluator) {
            return !conservativelyNonLocal && evaluator.isLocal(localityAxiom, signature);
        }
    }

    /** One unit per connected group of the allowed axioms with anonymous individuals. */
    private List<ExtractionUnit> anonymousUnits(Set<OWLAxiom> allowed) {
        if (anonymousGroupOf.isEmpty()) {
            return Collections.emptyList();
        }
        Set<OWLAxiom> candidates = anonymousGroupOf.keySet().stream()
                .filter(allowed::contains).collect(Collectors.toSet());
        return connectedGroups(candidates).stream()
                .map(group -> compileAnonymousUnit(group, dataFactory))
                .collect(Collectors.toList());
    }

    private static Set<OWLAnonymousIndividual> logicalAnonymousIndividuals(OWLAxiom axiom) {
        return axiom.getAxiomWithoutAnnotations().anonymousIndividuals()
                .collect(Collectors.toSet());
    }

    /** Connected components of axioms linked by shared logical anonymous individuals. */
    private static List<Set<OWLAxiom>> connectedGroups(Set<OWLAxiom> axioms) {
        Map<OWLAnonymousIndividual, Set<OWLAxiom>> byIndividual = new HashMap<>();
        for (OWLAxiom axiom : axioms) {
            logicalAnonymousIndividuals(axiom).forEach(individual ->
                    byIndividual.computeIfAbsent(individual, ignored -> new HashSet<>())
                            .add(axiom));
        }
        Set<OWLAxiom> pending = new HashSet<>(axioms);
        List<Set<OWLAxiom>> groups = new ArrayList<>();
        while (!pending.isEmpty()) {
            OWLAxiom first = pending.iterator().next();
            pending.remove(first);
            Set<OWLAxiom> group = new HashSet<>();
            group.add(first);
            Set<OWLAnonymousIndividual> seen = new HashSet<>();
            Set<OWLAnonymousIndividual> work = logicalAnonymousIndividuals(first);
            while (!work.isEmpty()) {
                OWLAnonymousIndividual current = work.iterator().next();
                work.remove(current);
                if (!seen.add(current)) {
                    continue;
                }
                for (OWLAxiom connected : byIndividual.getOrDefault(current,
                        Collections.emptySet())) {
                    if (pending.remove(connected)) {
                        group.add(connected);
                        logicalAnonymousIndividuals(connected).stream()
                                .filter(x -> !seen.contains(x)).forEach(work::add);
                    }
                }
            }
            groups.add(group);
        }
        return groups;
    }

    private static final class AnonymousEdge {
        final OWLAnonymousIndividual from;
        final OWLAnonymousIndividual to;
        final OWLObjectPropertyExpression property;

        AnonymousEdge(OWLAnonymousIndividual from, OWLAnonymousIndividual to,
                      OWLObjectPropertyExpression property) {
            this.from = from;
            this.to = to;
            this.property = property;
        }

        OWLAnonymousIndividual opposite(OWLAnonymousIndividual node) {
            return from.equals(node) ? to : from;
        }

        OWLObjectPropertyExpression fromNode(OWLAnonymousIndividual node) {
            return from.equals(node) ? property : property.getInverseProperty();
        }
    }

    /**
     * Roll up an OWL 2 DL anonymous assertion forest to a single existential
     * GCI. Shared nodes must be conjoined BEFORE existential closure: separate
     * closures would give every assertion an independent witness.
     *
     * Unsupported/invalid graph shapes, and every group when no factory is
     * available, are kept conservatively as a whole unit, never silently
     * approximated by a spanning tree.
     */
    private static ExtractionUnit compileAnonymousUnit(Set<OWLAxiom> group,
                                                       OWLDataFactory factory) {
        if (factory == null) {
            return new ExtractionUnit(group, null, true);
        }
        Map<OWLAnonymousIndividual, List<OWLClassExpression>> at = new HashMap<>();
        List<AnonymousEdge> edges = new ArrayList<>();
        boolean unsupported = false;
        for (OWLAxiom axiom : group) {
            if (axiom instanceof OWLClassAssertionAxiom) {
                OWLClassAssertionAxiom ca = (OWLClassAssertionAxiom) axiom;
                if (!ca.getIndividual().isAnonymous()
                        || ca.getClassExpression().anonymousIndividuals().findAny().isPresent()) {
                    unsupported = true;
                    break;
                }
                at.computeIfAbsent(ca.getIndividual().asOWLAnonymousIndividual(),
                        ignored -> new ArrayList<>()).add(ca.getClassExpression());
            } else if (axiom instanceof OWLDataPropertyAssertionAxiom) {
                OWLDataPropertyAssertionAxiom da = (OWLDataPropertyAssertionAxiom) axiom;
                if (!da.getSubject().isAnonymous()) {
                    unsupported = true;
                    break;
                }
                at.computeIfAbsent(da.getSubject().asOWLAnonymousIndividual(),
                                ignored -> new ArrayList<>())
                        .add(factory.getOWLDataHasValue(da.getProperty(), da.getObject()));
            } else if (axiom instanceof OWLObjectPropertyAssertionAxiom) {
                OWLObjectPropertyAssertionAxiom oa = (OWLObjectPropertyAssertionAxiom) axiom;
                OWLIndividual from = oa.getSubject(), to = oa.getObject();
                if (from.isAnonymous() && to.isAnonymous()) {
                    OWLAnonymousIndividual fromAnon = from.asOWLAnonymousIndividual();
                    OWLAnonymousIndividual toAnon = to.asOWLAnonymousIndividual();
                    if (fromAnon.equals(toAnon)) {
                        // OWL 2 DL does not permit an anonymous assertion cycle.
                        unsupported = true;
                        break;
                    }
                    edges.add(new AnonymousEdge(fromAnon, toAnon, oa.getProperty()));
                    at.computeIfAbsent(fromAnon, ignored -> new ArrayList<>());
                    at.computeIfAbsent(toAnon, ignored -> new ArrayList<>());
                } else if (from.isAnonymous()) {
                    at.computeIfAbsent(from.asOWLAnonymousIndividual(), ignored -> new ArrayList<>())
                            .add(factory.getOWLObjectHasValue(oa.getProperty(), to));
                } else if (to.isAnonymous()) {
                    at.computeIfAbsent(to.asOWLAnonymousIndividual(), ignored -> new ArrayList<>())
                            .add(factory.getOWLObjectHasValue(
                                    oa.getProperty().getInverseProperty(), from));
                } else {
                    unsupported = true;
                    break;
                }
            } else {
                // This includes shared anonymous same/different individuals,
                // unsupported negative assertions, and unusual RDF shapes.
                unsupported = true;
                break;
            }
        }
        if (unsupported || at.isEmpty()) {
            return new ExtractionUnit(group, null, true);
        }
        OWLAnonymousIndividual root = at.keySet().iterator().next();
        Set<OWLAnonymousIndividual> visited = new HashSet<>();
        OWLClassExpression tree = rollUp(root, null, at, edges, visited, factory);
        if (tree == null || visited.size() != at.size()) {
            return new ExtractionUnit(group, null, true);
        }
        OWLAxiom summary = factory.getOWLSubClassOfAxiom(factory.getOWLThing(),
                factory.getOWLObjectSomeValuesFrom(factory.getOWLTopObjectProperty(), tree));
        return new ExtractionUnit(group, summary, false);
    }

    /** Returns null if the anonymous assertion graph is not a forest. */
    private static OWLClassExpression rollUp(OWLAnonymousIndividual node,
                                             OWLAnonymousIndividual parent,
                                             Map<OWLAnonymousIndividual, List<OWLClassExpression>> at,
                                             List<AnonymousEdge> edges,
                                             Set<OWLAnonymousIndividual> visited,
                                             OWLDataFactory factory) {
        if (!visited.add(node)) {
            return null;
        }
        List<OWLClassExpression> conditions = new ArrayList<>(at.get(node));
        int parentLinks = 0;
        for (AnonymousEdge edge : edges) {
            if (!edge.from.equals(node) && !edge.to.equals(node)) {
                continue;
            }
            OWLAnonymousIndividual child = edge.opposite(node);
            if (child.equals(parent)) {
                // Multiple edges to the same parent share a witness and
                // cannot be represented by independent existential fillers.
                if (++parentLinks > 1) {
                    return null;
                }
                continue;
            }
            OWLClassExpression descendant = rollUp(child, node, at, edges, visited, factory);
            if (descendant == null) {
                return null;
            }
            conditions.add(factory.getOWLObjectSomeValuesFrom(edge.fromNode(node), descendant));
        }
        // Distinct annotated assertions can impose the same class condition.
        // Deduplicate BEFORE invoking a factory that requires two operands.
        Set<OWLClassExpression> unique = new HashSet<>(conditions);
        if (unique.isEmpty()) {
            return factory.getOWLThing();
        }
        return unique.size() == 1 ? unique.iterator().next()
                : factory.getOWLObjectIntersectionOf(unique.stream());
    }

    private void initialize() {
        axiomBase().forEach(axiom -> axiom.signature().forEach(entity ->
                axiomsContainingEntity.computeIfAbsent(entity, x -> new HashSet<>()).add(axiom)));
        Set<OWLAxiom> anonymous = axiomBase().filter(LocalityModuleExtractor::hasLogicalAnonymousIndividuals)
                .collect(Collectors.toSet());
        for (Set<OWLAxiom> group : connectedGroups(anonymous)) {
            Set<OWLAxiom> shared = Collections.unmodifiableSet(group);
            group.forEach(axiom -> anonymousGroupOf.put(axiom, shared));
        }
    }
}
