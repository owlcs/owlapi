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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityEvaluator;

/**
 * Interface for classes that extract modules based on fixed axiom bases. Implementations of this
 * interface may use pre-computation to optimize calculating multiple modules for the same axiom base
 * but for differing signatures.
 *
 * <p>The default methods {@link #globals()}, {@link #everyModuleContains(OWLAxiom)},
 * {@link #noModuleContains(OWLAxiom)} and {@link #tautologies()} rely on properties
 * of locality-based module extraction, not on the minimal {@link #extract(Stream, Optional)}
 * contract alone. In particular, {@code globals()} requires monotonicity in the seed
 * signature; {@code noModuleContains()} assumes that an axiom's inclusion can be
 * determined when it is the only eligible axiom and its full signature is used as the
 * seed. Other extraction algorithms must override defaults they cannot guarantee.
 *
 * <p>Unless stated otherwise, references to modules below mean unfiltered modules
 * extracted from the complete, fixed axiom base.
 *
 * @author Marc Robin Nolte
 */
public interface ModuleExtractor {

    /**
     * Return the axioms all modules of this ModuleExtractor are computed against, including
     * global axioms and tautologies.
     *
     * @return The axioms as specified above
     */
    @Nonnull
    Stream<OWLAxiom> axiomBase();

    /**
     * Returns whether the axiom base of this ModuleExtractor contains the given
     * {@link OWLAxiom}.
     *
     * @param axiom The axiom to test
     * @return A boolean value as specified above
     */
    default boolean containsAxiom(OWLAxiom axiom) {
        return axiomBase().anyMatch(axiom::equals);
    }

    /**
     * Returns {@code true} if it is guaranteed that the given {@link OWLAxiom} is contained in
     * every unfiltered module calculated by this extractor, assuming monotonicity in
     * the seed signature; {@code false} otherwise. The default implementation checks
     * membership in the empty-seed module. Filtered extractions are not covered.
     * A {@code true} result implies that
     * {@link ModuleExtractor#noModuleContains(OWLAxiom)} returns {@code false} for the same
     * axiom.
     *
     * @param axiom The {@link OWLAxiom} to check
     * @return A boolean value as specified above
     */
    default boolean everyModuleContains(OWLAxiom axiom) {
        return globals().anyMatch(axiom::equals);
    }

    /**
     * Extracts a module with respect to the given signature against the axiom base of this
     * ModuleExtractor.
     *
     * @param signature The signature the module should be extracted against
     * @return The axioms of the module with respect to the given signature
     */
    @Nonnull
    default Stream<OWLAxiom> extract(Stream<OWLEntity> signature) {
        return extract(signature, Optional.empty());
    }

    /**
     * Extracts a module with respect to the given signature against the subset of the axiom base
     * of this ModuleExtractor that matches the given {@link Predicate}, if any.
     * An absent filter uses the complete axiom base; a present filter changes the
     * axiom base for this extraction rather than filtering a completed module.
     *
     * @param signature The signature the module should be extracted against.
     * @param axiomFilter An {@link Optional} {@link Predicate} that filters a subset of the axiom
     * base to extract the module against. Note that ignoring some axiom may lead to other
     * axioms not be contained in the module either. For example, consider the Ontology O:=
     * {A⊑B, B⊑C, C⊑D} and the signature {A}. {@link SyntacticLocalityEvaluator} with
     * {@link LocalityClass#BOTTOM} returns O as a whole, but when ignoring the axiom B⊑C it
     * only returns {A⊑B}.
     * @return The axioms of the module with respect to the given signature
     */
    @Nonnull
    Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
                             Optional<Predicate<OWLAxiom>> axiomFilter);

    /**
     * Extracts a module with respect to the given signature against the subset of the axiom base
     * this ModuleExtractor's axiom base that matches the given {@link Predicate}.
     *
     * @param signature The signature the module should be extracted against.
     * @param axiomFilter A {@link Predicate} that filters a subset of the axiom base to extract
     * the module against. Note that ignoring some axiom may lead to other axioms not be
     * contained in the module either. For example, consider the ontology O:= {A⊑B, B⊑C, C⊑D}
     * and the signature {A,E}. {@link SyntacticLocalityEvaluator} with
     * {@link LocalityClass#BOTTOM} returns O as a whole, but when ignoring the axiom B⊑C, it
     * will only return {A⊑B}.
     * @return The axioms of the module with respect to the given signature
     */
    default @Nonnull Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
                                              Predicate<OWLAxiom> axiomFilter) {
        return extract(signature, Optional.ofNullable(axiomFilter));
    }

    /**
     * Extracts a module as an {@link OWLOntology} with respect to the given signature over the
     * given axiom base.
     *
     * <p>The caller owns the returned ontology. If extraction or axiom insertion fails,
     * the newly created ontology is removed from its manager. The extracted axiom
     * stream is closed after consumption, whether extraction succeeds or fails.
     *
     * @param signature The signature the module should be extracted against
     * @param ontologyManager The {@link OWLOntologyManager} used to create the new
     * {@link OWLOntology}
     * @param ontologyIRI The {@link IRI} of the new {@link OWLOntology}
     * @return The module as an {@link OWLOntology}
     * @throws OWLOntologyCreationException If there is an exception when creating the
     * {@link OWLOntology}
     */
    default @Nonnull OWLOntology extractAsOntology(Stream<OWLEntity> signature,
                                                   OWLOntologyManager ontologyManager, IRI ontologyIRI) throws OWLOntologyCreationException {
        Objects.requireNonNull(signature, "The given signature may not be null");
        OWLOntologyManager manager = Objects.requireNonNull(ontologyManager,
                "The given ontology manager may not be null");
        OWLOntology ontology = manager.createOntology(
                Objects.requireNonNull(ontologyIRI, "The given iri may not be null"));
        try (Stream<OWLAxiom> module = extract(signature)) {
            ontology.addAxioms(module);
            return ontology;
        } catch (RuntimeException | Error e) {
            try {
                manager.removeOntology(ontology);
            } catch (RuntimeException | Error cleanupFailure) {
                e.addSuppressed(cleanupFailure);
            }
            throw e;
        }
    }

    /**
     * Returns the axioms in the empty-seed module. Assuming monotonicity in the
     * seed signature, these are exactly the axioms present in every unfiltered module.
     * Implementations without this property must override this method.
     *
     * @return The axioms as specified above
     */
    default @Nonnull Stream<OWLAxiom> globals() {
        return extract(Stream.empty());
    }

    /**
     * Returns the seed signature that decides whether the given axiom occurs in modules
     * at all: if the axiom is not in the module for this signature, it is in no module.
     * This is the axiom's own signature by default. Extractors that include some axioms
     * only together with others must return the signature of the whole group, so that
     * e.g. {@link AtomicDecomposition} keeps the group in one atom.
     *
     * @param axiom The {@link OWLAxiom} to get the seed signature for
     * @return The seed signature as specified above
     */
    default @Nonnull Stream<OWLEntity> seedSignature(OWLAxiom axiom) {
        return axiom.signature();
    }

    /**
     * Returns {@code true} if the given {@link OWLAxiom} is absent from the module
     * extracted with only that axiom eligible and its full signature as the seed.
     * For locality-based extractors this is equivalent to absence from every
     * unfiltered module: adding unrelated axioms or seed entities cannot change
     * the locality of an axiom whose entire signature is already present.
     * Other module notions must override this method unless they satisfy that
     * implication. A {@code true} result implies that
     * {@link ModuleExtractor#everyModuleContains(OWLAxiom)} returns {@code false} for the same
     * axiom.
     *
     * @param axiom The {@link OWLAxiom} to check
     * @return A boolean value as specified above
     */
    default boolean noModuleContains(OWLAxiom axiom) {
        return !extract(axiom.signature(), axiom::equals).findAny().isPresent();
    }

    /**
     * Returns axioms for which {@link #noModuleContains(OWLAxiom)} is {@code true}.
     * Under its documented assumptions, these axioms occur in no unfiltered module.
     * They need not be logical tautologies for arbitrary module notions.
     * The default stream is sequential because extraction need not be thread-safe.
     *
     * @return The axioms as specified above
     */
    @Nonnull
    default Stream<OWLAxiom> tautologies() {
        return axiomBase().sequential().filter(this::noModuleContains);
    }
}