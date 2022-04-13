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

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.modularity.ModuleExtractor;

/**
 * Abstract class for convenience implementation of locality-based {@link ModuleExtractor}s. Offers
 * protected methods for the improved algorithm for module extraction.
 *
 * @author Marc Robin Nolte
 */
public abstract class LocalityModuleExtractor implements ModuleExtractor {

    /**
     * Part of the implementation of the improved algorithm for module extraction. See "Improved
     * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov, 2012
     *
     * @param axiom            current axiom
     * @param signature        current signature
     * @param module           current module
     * @param workingSignature working signature
     * @param evaluator        evaluator to use
     */
    private static void addNonLocal(OWLAxiom axiom, Set<OWLEntity> signature, Collection<OWLAxiom> module,
                                    Collection<OWLEntity> workingSignature, LocalityEvaluator evaluator) {
        if (!module.contains(axiom) && !evaluator.isLocal(axiom, signature)) {
            // M ← M ∪ α
            module.add(axiom);
            // S ← S ∪ (̃α \ Σ)
            Set<OWLEntity> axiomSignature = axiom.signature().collect(Collectors.toSet());
            axiomSignature.removeAll(signature);
            workingSignature.addAll(axiomSignature);
            // Σ ← Σ ∪ ̃α
            axiom.signature().forEach(signature::add);
        }
    }

    /**
     * The axiom base of this {@link LocalityModuleExtractor}.
     */
    private final @Nonnull Set<OWLAxiom> axiomBase;
    /**
     * The locality class used by this {@link SyntacticLocalityModuleExtractor}.
     */
    private @Nonnull final LocalityClass localityClass;
    /**
     * Map associating each entity with the axioms that contains it.
     */
    private @Nonnull final Map<OWLEntity, Set<OWLAxiom>> axiomsContainingEntity = new HashMap<>();

    /**
     * Creates a new LocalityModuleExtractor.
     *
     * @param localityClass The {@link LocalityClass} to use
     * @param axiomBase     the axiom base of the new LocalityModuleExtractor
     */
    LocalityModuleExtractor(LocalityClass localityClass, Stream<OWLAxiom> axiomBase) {
        this.axiomBase = axiomBase.collect(Collectors.toSet());
        this.localityClass =
            Objects.requireNonNull(localityClass, "The given locality class may not be null.");
        initialize();
    }

    @Override
    public @Nonnull Stream<OWLAxiom> axiomBase() {
        return axiomBase.stream();
    }

    /**
     * @return The {@link LocalityEvaluator} corresponding to {@link LocalityClass#BOTTOM}.
     */
    protected abstract LocalityEvaluator bottomEvaluator();

    @Override
    public boolean containsAxiom(OWLAxiom axiom) {
        return axiomBase.contains(axiom);
    }

    @Override
    public final Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
                                          Optional<Predicate<OWLAxiom>> axiomFilter) {
        Set<OWLEntity> signatureSet = signature.collect(Collectors.toSet());
        if (localityClass == LocalityClass.STAR) {
            return extractStarModule(signatureSet, axiomFilter).stream();
        } else {
            LocalityEvaluator evaluator =
                localityClass == LocalityClass.BOTTOM ? bottomEvaluator() : topEvaluator();
            return extractLocalityBasedModule(signatureSet, axiomFilter, evaluator).stream();
        }
    }

    /**
     * Implementation of the improved algorithm for module extraction. See "Improved Algorithms for
     * Module Extraction and Atomic Decomposition" by Dmitry Tsarkov, 2012. Uses a {@link Set}
     * instead of a Stream for the sub axiom base in favor of STAR-module extraction.
     *
     * @param signature   signature to use in the extraction
     * @param axiomFilter optional filter to apply to the axioms
     * @param evaluator   locality evaluator
     * @return module as a set of axioms
     */
    @Nonnull
    private Set<OWLAxiom> extractLocalityBasedModule(Set<OWLEntity> signature,
                                                     Optional<Predicate<OWLAxiom>> axiomFilter,
                                                     LocalityEvaluator evaluator) {
        // Sub axiom base requires to filter the axioms
        Function<OWLEntity, Stream<OWLAxiom>> axiomsOfEntity =
            entity -> axiomsContainingEntity.get(entity).stream();
        if (axiomFilter.isPresent()) {
            axiomsOfEntity = axiomsOfEntity.andThen(stream -> stream.filter(axiomFilter.get()));
        }
        // Signature needs to be copied such that the original won't be changed.
        // STAR Modules would be wrong etc.
        Set<OWLEntity> signatureCopy = new HashSet<>(signature);
        Set<OWLAxiom> module = new HashSet<>();
        Set<OWLEntity> workingSignature = new HashSet<>(signatureCopy);
        // actually extracting the module
        while (!workingSignature.isEmpty()) {
            OWLEntity omega = workingSignature.stream().findAny().get();
            workingSignature.remove(omega);
            axiomsOfEntity.apply(omega).forEach(
                alpha -> addNonLocal(alpha, signatureCopy, module, workingSignature, evaluator));
        }
        assert !axiomFilter.isPresent() || module.stream().allMatch(axiomFilter.get());
        return module;
    }

    /**
     * Extracts the {@link LocalityClass#STAR}-Module of the given axioms w.r.t. the given
     * signature.
     *
     * @param signature   signature to use in the extraction
     * @param axiomFilter optional filter to apply to the axioms
     * @return module as a set of axioms
     */
    @Nonnull
    private Set<OWLAxiom> extractStarModule(Set<OWLEntity> signature, Optional<Predicate<OWLAxiom>> axiomFilter) {
        LocalityEvaluator bottom = bottomEvaluator(); // bot or empty_set
        LocalityEvaluator top = topEvaluator(); // top or delta
        // Calculating the initial module
        Set<OWLAxiom> module = extractLocalityBasedModule(signature, axiomFilter, bottom);
        LocalityEvaluator nextExtractionType = top;
        int previousSize;
        // nesting modules until stabilization
        do {
            previousSize = module.size();
            module = extractLocalityBasedModule(signature, Optional.of(module::contains),
                nextExtractionType);
            nextExtractionType = nextExtractionType == bottom ? top : bottom;
        } while (previousSize != module.size());
        return module;
    }

    /**
     * Returns the locality class used by this LocalityModuleExtractor.
     *
     * @return The locality class used by this LocalityModuleExtractor
     */
    @Nonnull
    public LocalityClass getLocalityClass() {
        return localityClass;
    }

    /**
     * Initializes this LocalityModuleExtractor. Precomputes the globals and fills
     * {@link LocalityModuleExtractor#axiomsContainingEntity}.
     */
    private void initialize() {
        axiomBase().forEach(axiom -> axiom.signature().forEach(entity -> axiomsContainingEntity
            .computeIfAbsent(entity, x -> new HashSet<>()).add(axiom)));
    }

    /**
     * @return The {@link LocalityEvaluator} corresponding to {@link LocalityClass#TOP}.
     */
    protected abstract LocalityEvaluator topEvaluator();
}
