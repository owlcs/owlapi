package org.semanticweb.owlapi.modularity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasAxioms;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.modularity.ModuleExtractor.ModuleGuarantee;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

/**
 * Class to represent the atomic decomposition.
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
	public final class Atom {

		/**
		 * The {@link OWLAxiom}s of this {@link Atom}.
		 */
		private final @Nonnull Set<OWLAxiom> axioms;

		/**
		 * Instantiates a new {@link Atom}. The {@link OWLAxiom}s of this {@link Atom}
		 * are directly manipulated within {@link AtomicDecomposition#decompose()}.
		 */
		private Atom() {
			axioms = new HashSet<>();
		}

		/**
		 * Returns the {@link OWLAxiom}s of this {@link Atom}.
		 *
		 * @return The {@link OWLAxiom}s of this {@link Atom}
		 */
		public @Nonnull Stream<OWLAxiom> axioms() {
			return axioms.stream();
		}

	}

	/**
	 * A mapping from {@link OWLAxiom}s to their respective {@link Atom}. Gets
	 * computed within {@link AtomicDecomposition#decompose()}.
	 */
	private final @Nonnull Map<OWLAxiom, Atom> atomOf;

	/**
	 * The axiom base of this {@link AtomicDecomposition}.
	 */
	private final @Nonnull Set<OWLAxiom> axioms;

	/**
	 * A mapping from {@link Atom}s to their dependencies. Gets computed within
	 * {@link AtomicDecomposition#decompose()} already closed transitively.
	 */
	private final @Nonnull Multimap<Atom, Atom> dependenciesOf;

	/**
	 * The {@link ModuleExtractor} used by this {@link AtomicDecomposition} to
	 */
	private final @Nonnull ModuleExtractor moduleExtractor;

	/**
	 * A mapping from {@link OWLAxiom}s to the modules of the axiom base of this
	 * {@link AtomicDecomposition}. Gets computed within
	 * {@link AtomicDecomposition#decompose()} when calling
	 * {@link AtomicDecomposition#moduleToSignatureOf(OWLAxiom)}.
	 */
	private final @Nonnull SetMultimap<OWLAxiom, OWLAxiom> moduleToSignatureOf;

	/**
	 * Constructs the {@link AtomicDecomposition} for the given {@link OWLOntology}
	 * based on the module extraction the given {@link ModuleExtractor} provides.
	 * This operation may take some time based on the ontology size and the used
	 * module extraction algorithm.
	 *
	 * @param ontology        The {@link OWLOntology} which
	 *                        {@link AtomicDecomposition} should be constructed.
	 *                        Changes of that ontology will not be considered
	 * @param moduleExtractor The {@link ModuleExtractor} that provides the module
	 *                        extraction
	 */
	public AtomicDecomposition(final OWLOntology ontology, final ModuleExtractor moduleExtractor) {
		this(Objects.requireNonNull(ontology, "The given ontology may not be null").axioms(), moduleExtractor);
	}

	/**
	 * Constructs the {@link AtomicDecomposition} for the given {@link OWLAxiom}s
	 * based on the module extraction the given {@link ModuleExtractor} provides.
	 * This operation may take some time based on the axiom count and the used
	 * module extraction algorithm.
	 *
	 * @param axioms          The {@link OWLAxiom}s which
	 *                        {@link AtomicDecomposition} should be constructed
	 * @param moduleExtractor The {@link ModuleExtractor} that provides the module
	 *                        extraction
	 */
	public AtomicDecomposition(final Stream<OWLAxiom> axioms, final ModuleExtractor moduleExtractor) {
		this.axioms = Objects.requireNonNull(axioms, "The given axioms may not be null").collect(Collectors.toSet());
		this.moduleExtractor = Objects.requireNonNull(moduleExtractor, "The given moduleExtractor may not be null");
		moduleToSignatureOf = HashMultimap.create();
		atomOf = new HashMap<>();
		dependenciesOf = HashMultimap.create();
		decompose();
	}

	/**
	 * Returns the {@link Atom} associated with the given {@link OWLAxiom}. Those
	 * {@link Atom}s are computed when constructing the {@link AtomicDecomposition}.
	 *
	 * @param axiom The {@link OWLAxiom} whose {@link Atom} should be returned
	 * @return The {@link Atom} as specified above
	 * @throws IllegalArgumentException If the given {@link OWLAxiom} is not part of
	 *                                  the axiom base of this
	 *                                  {@link AtomicDecomposition}
	 */
	public @Nonnull Atom atomOf(final OWLAxiom axiom) {
		if (!containsAxiom(Objects.requireNonNull(axiom, "The given axiom may not be null"))) {
			throw new IllegalArgumentException(
					"The given axiom must be contained in the axiom base of this atomic decomposition");
		}
		return atomOf.get(axiom);
	}

	@Override
	public @Nonnull Stream<OWLAxiom> axioms() {
		return axioms.stream();
	}

	/**
	 * Returns whether or not the given {@link OWLAxiom} is part of the axiom base
	 * of this {@link AtomicDecomposition}. Runs in O(1).
	 *
	 * @param axiom The {@link OWLAxiom} to check
	 * @return Whether or not the given {@link OWLAxiom} is part of the axiom base
	 *         of this {@link AtomicDecomposition}
	 */
	public boolean containsAxiom(final OWLAxiom axiom) {
		return axioms.contains(axiom);
	}

	/**
	 * Implementation of the improved algorithm for atomic decomposition. See
	 * "Improved Algorithms for Module Extraction and Atomic Decomposition" by
	 * Dmitry Tsarkov, 2012.
	 */
	private void decompose() {
		// TODO Precompute tautologies and globals
		final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction = moduleExtractor.cachedGuaranteeFor();
		final Set<OWLAxiom> tautologies = axioms.stream()
				.filter(axiom -> guaranteeFunction.apply(axiom).equals(ModuleGuarantee.NOT_CONTAINED_IN_ANY_MODULE))
				.collect(Collectors.toSet());
		final Set<OWLAxiom> nonTautologies = axioms.stream().filter(axiom -> !tautologies.contains(axiom))
				.collect(Collectors.toSet());
		for (final OWLAxiom axiom : axioms) {
			// TODO
		}
	}

	/**
	 * Part of the implementation of the improved algorithm for atomic
	 * decomposition. See "Improved Algorithms for Module Extraction and Atomic
	 * Decomposition" by Dmitry Tsarkov, 2012
	 *
	 * @param guaranteeFunction The function calculating the
	 *                          {@link ModuleGuarantee}s for the {@link OWLAxiom}s
	 */
	private @Nonnull OWLAxiom getAtomSeed(final OWLAxiom alpha, final OWLAxiom beta,
			final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
		moduleExtractor.extractModule(moduleToSignatureOf(beta), alpha.signature(), guaranteeFunction)
				.forEach(moduleAxiom -> moduleToSignatureOf.put(alpha, moduleAxiom));
		if (moduleToSignatureOf.get(alpha).equals(moduleToSignatureOf.get(beta))) {
			return beta;
		}
		return alpha;
	}

	/**
	 * Returns the module within the {@link AtomicDecomposition#axioms()} with
	 * respect to the signature of the given axiom. Those modules are computed when
	 * constructing the {@link AtomicDecomposition}.
	 *
	 * @param axiom The axiom for whose signature the precomputed module should be
	 *              returned
	 * @return The module as specified above
	 * @throws IllegalArgumentException If the given axiom is not part of the axiom
	 *                                  base of this {@link AtomicDecomposition}
	 */
	public @Nonnull Stream<OWLAxiom> moduleToSignatureOf(final OWLAxiom axiom) {
		if (!containsAxiom(Objects.requireNonNull(axiom, "The given axiom may not be null"))) {
			throw new IllegalArgumentException(
					"The given axiom must be contained in the axiom base of this atomic decomposition");
		}
		return moduleToSignatureOf.get(axiom).stream();
	}

}
