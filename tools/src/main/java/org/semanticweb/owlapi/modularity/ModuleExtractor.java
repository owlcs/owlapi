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
 * interface may use precomputation to optimize calculating multiple modules for the same axiom base
 * but for differing signatures.
 *
 * @author Marc Robin Nolte
 *
 */
public interface ModuleExtractor {

    /**
     * Return the axioms all modules of this {@link ModuleExtractor} are computed against, including
     * global axioms and tautologies.
     *
     * @return The axioms as specified above
     */
    @Nonnull
    Stream<OWLAxiom> axiomBase();

    /**
     * Returns whether or not the axiom base of this {@link ModuleExtractor} contains the given
     * {@link OWLAxiom}.
     *
     * @param axiom The axiom to test
     * @return A boolean value as specified above
     */
    default boolean containsAxiom(OWLAxiom axiom) {
        return axiomBase().anyMatch(axiom::equals);
    }

    /**
     * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom} is contained in
     * every module calculated by the module extraction method this {@link ModuleExtractor} is based
     * on; <code>false</code> when no such guarantee can be made (Note: This does not mean that
     * there is some module regardless of other axioms or the signature that does not contain the
     * given axiom).
     *
     * This methods returning <code>true</code> implies that
     * {@link ModuleExtractor#noModuleContains(OWLAxiom)} returns <code>false</code> for the same
     * axiom.
     *
     * @param axiom The {@link OWLAxiom} to check
     * @return A boolean value as specified above
     */
    default boolean everyModuleContains(OWLAxiom axiom) {
        return extract(Stream.empty(), axiom::equals).count() == 1;
    }

    /**
     * Extracts a module with respect to the given signature against the axiom base of this
     * {@link ModuleExtractor}.
     *
     * @param signature The signature the module should be extracted against
     *
     * @return The axioms of the module with respect to the given signature
     */
    default @Nonnull Stream<OWLAxiom> extract(Stream<OWLEntity> signature) {
        return extract(signature, Optional.empty());
    }

    /**
     * Extracts a module with respect to the given signature against the subset of the axiom base
     * this {@link ModuleExtractor}s axiom base that matches the given {@link Predicate}, if any.
     *
     * @param signature   The signature the module should be extracted against.
     * @param axiomFilter An {@link Optional} {@link Predicate} that filters a subset of the axiom
     *                    base to extract the module against. Note that ignoring some axiom may lead
     *                    to other axioms not be contained in the module either. For example,
     *                    consider the Ontology O:= {A⊑B, B⊑C, C⊑D} and the signature {A}.
     *                    {@link SyntacticLocalityEvaluator} with {@link LocalityClass#BOTTOM}
     *                    returns O as a whole, but when ignoring the axiom B⊑C it only returns
     *                    {A⊑B}.
     *
     * @return The axioms of the module with respect to the given signature
     */
    @Nonnull
    Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
        Optional<Predicate<OWLAxiom>> axiomFilter);

    /**
     * Extracts a module with respect to the given signature against the subset of the axiom base
     * this {@link ModuleExtractor}s axiom base that matches the given {@link Predicate}.
     *
     * @param signature   The signature the module should be extracted against.
     * @param axiomFilter An {@link Predicate} that filters a subset of the axiom base to extract
     *                    the module against. Note that ignoring some axiom may lead to other axioms
     *                    not be contained in the module either. For example, consider the Ontologie
     *                    O:= {A⊑B, B⊑C, C⊑D} and the signature {A,E}.
     *                    {@link SyntacticLocalityEvaluator} with {@link LocalityClass#BOTTOM}
     *                    returns O as a whole, but when ignoring the axiom B⊑C, it will only return
     *                    {A⊑B}.
     *
     * @return The axioms of the module with respect to the given signature
     */
    default @Nonnull Stream<OWLAxiom> extract(Stream<OWLEntity> signature,
        Predicate<OWLAxiom> axiomFilter) {
        return extract(signature, Optional.ofNullable(axiomFilter));
    }

    /**
     *
     * Extracts a module as an {@link OWLOntology} with respect to the given signature over the
     * given axiom base.
     *
     * @param signature       The signature the module should be extracted against
     * @param ontologyManager The {@link OWLOntologyManager} used to create the new
     *                        {@link OWLOntology}
     * @param ontologyIRI     The {@link IRI} of the new {@link OWLOntology}
     * @return The module as an {@link OWLOntology}
     * @throws OWLOntologyCreationException If there is an exception when creating the
     *                                      {@link OWLOntology}
     */
    default @Nonnull OWLOntology extractAsOntology(Stream<OWLEntity> signature,
        OWLOntologyManager ontologyManager, IRI ontologyIRI) throws OWLOntologyCreationException {
        OWLOntology ontology = Objects
            .requireNonNull(ontologyManager, "The given ontology manager may not be null")
            .createOntology(Objects.requireNonNull(ontologyIRI, "The given iri may not be null"));
        ontology.addAxioms(
            extract(Objects.requireNonNull(signature, "The given signature may not be null")));
        return ontology;
    }

    /**
     * Returns from the axiom base of this extractor exactly those that are guaranteed to be
     * contained in every module calculated by this {@link ModuleExtractor}.
     *
     * These axioms may be precomputed or calculated on every call of this method.
     *
     * @return The axioms as specified above
     */
    default @Nonnull Stream<OWLAxiom> globals() {
        return extract(
            axiomBase().parallel().filter(this::everyModuleContains).flatMap(OWLAxiom::signature));
    }

    /**
     * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom} is not
     * contained in any module (regardless of other axioms or the signature) calculated by the
     * module extraction method this {@link ModuleExtractor} is based on; <code>false</code> when no
     * such guarantee can be made (Note: This does not mean that there is some module that contains
     * the given axiom).
     *
     * This methods returning <code>true</code> implies that
     * {@link ModuleExtractor#everyModuleContains(OWLAxiom)} returns <code>false</code> for the same
     * axiom.
     *
     * @param axiom The {@link OWLAxiom} to check
     * @return A boolean value as specified above
     */
    default boolean noModuleContains(OWLAxiom axiom) {
        return extract(axiom.signature(), axiom::equals).count() == 0;
    }

    /**
     * Returns from the axiom base of this extractor exactly those that are guaranteed not to be
     * contained in any module calculated by this {@link ModuleExtractor}.
     *
     * These axioms may be precomputed or calculated on every call of this method.
     *
     * @return The axioms as specified above
     */
    default @Nonnull Stream<OWLAxiom> tautologies() {
        return axiomBase().parallel().filter(this::noModuleContains);
    }

}
