package org.semanticweb.owlapi.modularity;
/*
 * Following uk.ac.manchester.modularity.owlapi.modularity.SyntacticLocalityModuleExtractor and com.clarkparsia.owlapi.modularity.locality.LocalityClass, and
 * modified for a more modern, thread safe and maintainable implementation. Original Copyright is:
 *
 * This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Enum of syntactic locality module classes to extract modules with.
 *
 * @author Marc Robin Nolte (modified version)
 *
 */
public class SyntacticLocalityModuleExtractor extends LocalityModuleExtractor {

	/**
	 * Types of locality classes that can be used.
	 *
	 * @author Marc Robin Nolte
	 *
	 */
	public enum SyntacticLocalityClass {

		/**
		 * Locality module class obtained by giving concepts and roles not present in
		 * the signature empty interpretations when extracting a locality based module.
		 */
		BOTTOM,

		/**
		 * Locality class obtained when nesting {@link SyntacticLocalityType#BOTTOM} and
		 * {@link SyntacticLocalityType#TOP} modules until stabilization.
		 */
		STAR,

		/**
		 * /** Locality module class obtained by giving concepts in the signature top
		 * concept interpretation and roles not present universal role interpretation
		 * when extracting a locality based module.
		 */
		TOP

	}

	/**
	 * The locality class used by this {@link SyntacticLocalityModuleExtractor}.
	 */
	private @Nonnull SyntacticLocalityClass syntacticLocalityClass;

	/**
	 * Instantiates a new {@link SyntacticLocalityModuleExtractor}.
	 *
	 * @param axiomBase              The axiom base of the new
	 *                               {@link SyntacticLocalityModuleExtractor}
	 * @param syntacticLocalityClass The class of locality of which modules should
	 *                               be calculated of
	 *
	 */
	public SyntacticLocalityModuleExtractor(final Stream<OWLAxiom> axiomBase,
			final SyntacticLocalityClass syntacticLocalityClass) {
		super(axiomBase);
		this.syntacticLocalityClass = syntacticLocalityClass;
	}

	@Override
	protected Stream<OWLAxiom> extractModule(final Stream<OWLEntity> signature,
			final Optional<Set<OWLAxiom>> subAxiomBase) {
		switch (syntacticLocalityClass) {
		case STAR:
			return extractStarModule(signature, subAxiomBase);
		case TOP:
			return extractLocalityBasedModule(signature, subAxiomBase, SyntacticLocalityEvaluator.TOP).stream();
		case BOTTOM:
			return extractLocalityBasedModule(signature, subAxiomBase, SyntacticLocalityEvaluator.BOTTOM).stream();
		default:
			throw new UnsupportedOperationException("Unkown syntactic locality class");
		}
	}

	/**
	 * Extracts the {@link SyntacticLocalityClass#STAR}-Module of the given axioms
	 * w.r.t. the given signature.
	 */
	private final @Nonnull Stream<OWLAxiom> extractStarModule(final Stream<OWLEntity> signature,
			final Optional<Set<OWLAxiom>> subAxiomBase) {
		// Calculating the initial module
		final Set<OWLEntity> collectedSignature = signature.collect(Collectors.toSet());
		Set<OWLAxiom> module = extractLocalityBasedModule(collectedSignature.stream(), subAxiomBase,
				SyntacticLocalityEvaluator.BOTTOM);

		SyntacticLocalityEvaluator nextExtractionType = SyntacticLocalityEvaluator.TOP;
		int previousSize;
		// nesting modules until stabilization
		do {
			previousSize = module.size();
			module = extractLocalityBasedModule(collectedSignature.stream(), Optional.of(module), nextExtractionType);
			nextExtractionType = nextExtractionType == SyntacticLocalityEvaluator.BOTTOM
					? SyntacticLocalityEvaluator.TOP
					: SyntacticLocalityEvaluator.BOTTOM;
		} while (previousSize != module.size());
		return module.stream();
	}

	/**
	 * Returns the locality class used by this
	 * {@link SyntacticLocalityModuleExtractor}.
	 *
	 * @return The locality class used by this
	 *         {@link SyntacticLocalityModuleExtractor}
	 */
	public final @Nonnull SyntacticLocalityClass getSyntacticLocalityClass() {
		return syntacticLocalityClass;
	}

	/**
	 * Sets the locality class used by this {@link SyntacticLocalityModuleExtractor}
	 * to the given one.
	 *
	 * @param syntacticLocalityClass The new locality class used by this
	 *                               {@link SyntacticLocalityModuleExtractor}
	 */
	public final void setSyntacticLocalityClass(final SyntacticLocalityClass syntacticLocalityClass) {
		this.syntacticLocalityClass = Objects.requireNonNull(syntacticLocalityClass,
				"The given syntactic locality class may not be null.");
	}

}
