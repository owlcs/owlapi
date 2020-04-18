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
package org.semanticweb.owlapi.modularity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasAxioms;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.LocalityModuleExtractor;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityModuleExtractor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * Class to represent the atomic decomposition of a set of axioms.
 *
 * @author Marc Robin Nolte
 *
 */
public final class AtomicDecomposition implements HasAxioms {

    /**
     * Class to represent atoms.
     *
     * @author Marc Robin Nolte
     *
     */
    public final class Atom implements HasAxioms {

        /**
         * The {@link OWLAxiom}s of this {@link Atom}.
         */
        protected final @Nonnull Set<OWLAxiom> axiomSet = new HashSet<>();

        /**
         * The {@link Atom}s this Atom is dependent on.
         */
        protected final @Nonnull Set<Atom> dependencies = new HashSet<>();

        /**
         * The {@link Atom}s that are dependent on this {@link Atom}.
         */
        protected final @Nonnull Set<Atom> dependents = new HashSet<>();

        /**
         * Instantiates a new {@link Atom}. The {@link OWLAxiom}s of this {@link Atom} are directly
         * manipulated within {@link AtomicDecomposition#decompose()}.
         *
         * @param axiom The initial axiom of this atom
         */
        protected Atom(OWLAxiom axiom) {
            axiomSet.add(axiom);
        }

        /**
         * Returns the {@link OWLAxiom}s of this {@link Atom}.
         *
         * @return The {@link OWLAxiom}s of this {@link Atom}
         */
        @Override
        public @Nonnull Stream<OWLAxiom> axioms() {
            return axiomSet.stream();
        }

        /**
         * Returns the {@link Atom}s this {@link Atom} depends on. In the literature, this
         * {@link Atom} &lt;= returned atoms.
         *
         * @return The {@link Atom}s this {@link Atom} depends on
         */
        public @Nonnull Stream<Atom> dependencies() {
            return dependencies.stream();
        }

        /**
         * Returns the {@link Atom}s that depend on this {@link Atom}. In the literature, returned
         * atoms = this {@link Atom}.
         *
         * @return The {@link Atom}s that depend on this {@link Atom}
         */
        public @Nonnull Stream<Atom> dependents() {
            return dependents.stream();
        }

        /**
         * Checks if this {@link Atom} depends on the given {@link Atom}.
         *
         * @param atom The {@link Atom} to check if this {@link Atom} depends on it. Cannot be null.
         * @return If this {@link Atom} depends on the given {@link Atom}
         * @throws NullPointerException If the given {@link Atom} was {@code null}
         */
        public boolean dependsOn(Atom atom) {
            return dependencies.contains(Objects.requireNonNull(atom));
        }

        /**
         * Checks if the given {@link Atom} depends on this {@link Atom}.
         *
         * @param atom The {@link Atom} to check if it depends on this {@link Atom}. Cannot be null.
         * @return If the given {@link Atom} depends on the this {@link Atom}
         * @throws NullPointerException If the given {@link Atom} was {@code null}
         */
        public boolean isDependencyOf(Atom atom) {
            return dependents.contains(Objects.requireNonNull(atom));
        }

    }

    /**
     * A mapping from {@link OWLAxiom}s to their respective {@link Atom}. Gets computed within
     * {@link AtomicDecomposition#decompose()}.
     */
    private final @Nonnull Map<OWLAxiom, Atom> atomOf;

    /**
     * The axiom base of this {@link AtomicDecomposition}.
     */
    private final @Nonnull Set<OWLAxiom> axioms;

    /**
     * The {@link ModuleExtractor} used by this {@link AtomicDecomposition} to
     */
    private final @Nonnull ModuleExtractor moduleExtractor;

    /**
     * A mapping from {@link OWLAxiom}s to the modules of the axiom base of this
     * {@link AtomicDecomposition}. Gets computed within {@link AtomicDecomposition#decompose()}
     * when calling {@link AtomicDecomposition#moduleToSignatureOf(OWLAxiom)}.
     */
    private final @Nonnull SetMultimap<OWLAxiom, OWLAxiom> moduleToSignatureOf;

    /**
     * Constructs the {@link AtomicDecomposition} for the given axiom base based on the module
     * extraction an {@link SyntacticLocalityModuleExtractor} provides by using
     * {@link LocalityClass#STAR}. This operation may take some time based on the ontology size and
     * the used module extraction algorithm.
     *
     * @param ontology The {@link OWLOntology} that is to be decomposed. Changes of the ontology
     *                 will not be considered
     */
    public AtomicDecomposition(OWLOntology ontology) {
        this(ontology.axioms(Imports.INCLUDED));
    }

    /**
     * Constructs the {@link AtomicDecomposition} for the given axiom base based on the module
     * extraction the {@link ModuleExtractor} the given {@link Function} supplies. This operation
     * may take some time based on the ontology size and the used module extraction algorithm.
     *
     * @param ontology The {@link OWLOntology} that is to be decomposed. Changes of the ontology
     *                 will not be considered
     * @param function The function used to build a {@link ModuleExtractor}. We recommend performing
     *                 possible precalculations by the module extractor
     */
    public AtomicDecomposition(OWLOntology ontology,
        Function<Stream<OWLAxiom>, ? extends ModuleExtractor> function) {
        this(Objects.requireNonNull(ontology, "The given ontology may not be null").axioms(),
            function);
    }

    /**
     * Constructs the {@link AtomicDecomposition} for the given axiom base based on the module
     * extraction an {@link SyntacticLocalityModuleExtractor} provides by using
     * {@link LocalityClass#STAR}. This operation may take some time based on the ontology size and
     * the used module extraction algorithm.
     *
     * @param axioms The axiom base that is to be decomposed
     */
    public AtomicDecomposition(Stream<OWLAxiom> axioms) {
        this(axioms, stream -> new SyntacticLocalityModuleExtractor(LocalityClass.STAR, stream));
    }

    /**
     * Constructs the {@link AtomicDecomposition} for the given axiom base based on the module
     * extraction the {@link ModuleExtractor} the given {@link Function} supplies. It must be
     * ensured that it satisfies the properties required to be used for atomic decomposition, see
     * "Modular Structures and Atomic Decomposition in Ontologies" by Del Vescovo et al, 2019. For
     * example, one such module extraction algorithm is provided by the subclasses of
     * {@link LocalityModuleExtractor}s.
     *
     * This operation may take some time based on the ontology size and the used module extraction
     * algorithm.
     *
     * @param axioms   The axiom base that is to be decomposed
     * @param function The function used to build a {@link ModuleExtractor}. We recommend performing
     *                 possible precalculations by the module extractor
     */
    public AtomicDecomposition(Stream<OWLAxiom> axioms,
        Function<Stream<OWLAxiom>, ? extends ModuleExtractor> function) {
        this.axioms = Objects.requireNonNull(axioms, "The given axioms may not be null")
            .collect(Collectors.toSet());
        moduleExtractor =
            Objects.requireNonNull(function, "The given function may not be null").apply(axioms());
        Objects.requireNonNull(axioms, "The given function may not retrieve null");
        moduleToSignatureOf = HashMultimap.create();
        atomOf = new HashMap<>();
        decompose();
    }

    /**
     * Returns the {@link Atom} associated with the given {@link OWLAxiom}. Those {@link Atom}s are
     * computed when constructing the {@link AtomicDecomposition}.
     *
     * @param axiom The {@link OWLAxiom} whose {@link Atom} should be returned
     * @return The {@link Atom} as specified above
     * @throws IllegalArgumentException If the given {@link OWLAxiom} is not part of the axiom base
     *                                  of this {@link AtomicDecomposition}
     */
    public @Nonnull Atom atomOf(OWLAxiom axiom) {
        if (!containsAxiom(Objects.requireNonNull(axiom, "The given axiom may not be null"))) {
            throw new IllegalArgumentException(
                "The given axiom must be contained in the axiom base of this atomic decomposition");
        }
        return atomOf.get(axiom);
    }

    /**
     * Returns a {@link Stream} of the {@link Atom}s computed by this {@link AtomicDecomposition}.
     *
     * @return The {@link Atom}s computed by this {@link AtomicDecomposition}
     */
    public Stream<Atom> atoms() {
        return atomOf.values().stream();
    }

    @Override
    public @Nonnull Stream<OWLAxiom> axioms() {
        return axioms.stream();
    }

    /**
     * Part of the implementation of the improved algorithm for atomic decomposition. See "Improved
     * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov, 2012
     * 
     * @param alpha alpha axiom
     * @param beta  beta axiom
     * @return seed for alpha
     */
    private @Nonnull OWLAxiom buildAtomsInModule(OWLAxiom alpha, Optional<OWLAxiom> beta) {

        assert alpha != null && beta != null;

        // The atom for alpha is already known
        if (atomOf.containsKey(alpha)) {
            return alpha;
        }

        OWLAxiom delta = getAtomSeed(alpha, beta);
        Atom atomOfDelta = atomOf.computeIfAbsent(delta, Atom::new);
        atomOfDelta.axiomSet.add(alpha);
        atomOf.put(alpha, atomOfDelta);

        if (beta.isPresent() && delta.equals(beta.get())) {
            return beta.get();
        }

        moduleToSignatureOf(alpha).filter(gamma -> !gamma.equals(alpha)).forEach(gamma -> {
            OWLAxiom axiom = buildAtomsInModule(gamma, Optional.of(alpha));
            Atom first = atomOf.get(axiom);
            Atom second = atomOf.get(alpha);
            first.dependencies.add(second); // first <= second
            second.dependents.add(first);
        });

        return alpha;
    }

    /**
     * Returns whether or not the given {@link OWLAxiom} is part of the axiom base of this
     * {@link AtomicDecomposition}. Runs in O(1).
     *
     * @param axiom The {@link OWLAxiom} to check
     * @return Whether or not the given {@link OWLAxiom} is part of the axiom base of this
     *         {@link AtomicDecomposition}
     */
    public boolean containsAxiom(OWLAxiom axiom) {
        return axioms.contains(axiom);
    }

    /**
     * Implementation of the improved algorithm for atomic decomposition. See "Improved Algorithms
     * for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov, 2012.
     */
    private void decompose() {
        for (OWLAxiom alpha : axioms) {
            buildAtomsInModule(alpha, Optional.empty());
        }
    }

    /**
     * Part of the implementation of the improved algorithm for atomic decomposition. See "Improved
     * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov, 2012
     * 
     * @param alpha alpha axiom
     * @param beta  beta axiom
     * @return seed for alpha
     */
    private @Nonnull OWLAxiom getAtomSeed(OWLAxiom alpha, Optional<OWLAxiom> beta) {

        assert alpha != null && beta != null;

        Set<OWLAxiom> moduleOfBeta;
        if (beta.isPresent()) {
            moduleOfBeta = moduleToSignatureOf.get(beta.get());
            moduleExtractor.extract(alpha.signature(), moduleOfBeta::contains)
                .forEach(moduleAxiom -> moduleToSignatureOf.put(alpha, moduleAxiom));
        } else {
            moduleOfBeta = axioms;
            moduleExtractor.extract(alpha.signature())
                .forEach(moduleAxiom -> moduleToSignatureOf.put(alpha, moduleAxiom));
        }

        // modules are equal if size are equal
        if (moduleToSignatureOf.get(alpha).size() == moduleOfBeta.size()) {
            return beta.get();
        }

        return alpha;
    }

    /**
     * Returns the module within the {@link AtomicDecomposition#axioms()} with respect to the
     * signature of the given axiom. Those modules are computed when constructing the
     * {@link AtomicDecomposition}.
     *
     * @param axiom The axiom for whose signature the precomputed module should be returned
     * @return The module as specified above
     * @throws IllegalArgumentException If the given axiom is not part of the axiom base of this
     *                                  {@link AtomicDecomposition}
     */
    public @Nonnull Stream<OWLAxiom> moduleToSignatureOf(OWLAxiom axiom) {
        if (!containsAxiom(Objects.requireNonNull(axiom, "The given axiom may not be null"))) {
            throw new IllegalArgumentException(
                "The given axiom must be contained in the axiom base of this atomic decomposition");
        }
        return moduleToSignatureOf.get(axiom).stream();
    }

}
