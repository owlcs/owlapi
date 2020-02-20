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
 * Class to represent the atomic decomposition.
 *
 * @author Marc Robin Nolte
 *
 */
public final class AtomicDecomposition implements HasAxioms {

	// TODO dependecy relation
	// TODO Deprecate all other classes

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
		private final @Nonnull Set<OWLAxiom> axioms;

		/**
		 * Instantiates a new {@link Atom}. The {@link OWLAxiom}s of this {@link Atom}
		 * are directly manipulated within {@link AtomicDecomposition#decompose()}.
		 *
		 * @param axiom The initial axiom of this atom
		 */
		private Atom(final OWLAxiom axiom) {
			axioms = new HashSet<>();
			axioms.add(axiom);
		}

		/**
		 * Returns the {@link OWLAxiom}s of this {@link Atom}.
		 *
		 * @return The {@link OWLAxiom}s of this {@link Atom}
		 */
		@Override
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
	private final @Nonnull SetMultimap<Atom, Atom> dependenciesOf;

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
	 * Constructs the {@link AtomicDecomposition} for the given axiom base based on
	 * the module extraction an {@link SyntacticLocalityModuleExtractor} provides by
	 * using {@link LocalityClass#STAR}. This operation may take some time based on
	 * the ontology size and the used module extraction algorithm.
	 *
	 * @param ontology The {@link OWLOntology} that is to be decomposed. Changes of
	 *                 the ontology will not be considered
	 */
	public AtomicDecomposition(final OWLOntology ontology) {
		this(ontology.axioms(Imports.INCLUDED));
	}

	/**
	 * Constructs the {@link AtomicDecomposition} for the given axiom base based on
	 * the module extraction the {@link ModuleExtractor} the given {@link Function}
	 * supplies. This operation may take some time based on the ontology size and
	 * the used module extraction algorithm.
	 *
	 * @param ontology The {@link OWLOntology} that is to be decomposed. Changes of
	 *                 the ontology will not be considered
	 * @param function The {@link AxiomsStep} used to build a
	 *                 {@link ModuleExtractor}. We recommend performing possible
	 *                 precalculations by the module extractor
	 */
	public AtomicDecomposition(final OWLOntology ontology,
			final Function<Stream<OWLAxiom>, ? extends ModuleExtractor> function) {
		this(Objects.requireNonNull(ontology, "The given ontology may not be null").axioms(), function);
	}

	/**
	 * Constructs the {@link AtomicDecomposition} for the given axiom base based on
	 * the module extraction an {@link SyntacticLocalityModuleExtractor} provides by
	 * using {@link LocalityClass#STAR}. This operation may take some time based on
	 * the ontology size and the used module extraction algorithm.
	 *
	 * @param ontology The axiom base that is to be decomposed
	 */
	public AtomicDecomposition(final Stream<OWLAxiom> axioms) {
		this(axioms, stream -> new SyntacticLocalityModuleExtractor(LocalityClass.STAR, stream));
	}

	/**
	 * Constructs the {@link AtomicDecomposition} for the given axiom base based on
	 * the module extraction the {@link ModuleExtractor} the given {@link Function}
	 * supplies. It must be ensured that it satisfies the properties required to be
	 * used for atomic decomposition, see "Modular Structures and Atomic
	 * Decomposition in Ontologies" by Del Vescovo et al, 2019. For example, one
	 * such module extraction algorithm is provided by the subclasses of
	 * {@link LocalityModuleExtractor}s.
	 *
	 * This operation may take some time based on the ontology size and the used
	 * module extraction algorithm.
	 *
	 * @param axioms   The axiom base that is to be decomposed
	 * @param function The {@link AxiomsStep} used to build a
	 *                 {@link ModuleExtractor}. We recommend performing possible
	 *                 precalculations by the module extractor
	 */
	public AtomicDecomposition(final Stream<OWLAxiom> axioms,
			final Function<Stream<OWLAxiom>, ? extends ModuleExtractor> function) {
		this.axioms = Objects.requireNonNull(axioms, "The given axioms may not be null").collect(Collectors.toSet());
		moduleExtractor = Objects.requireNonNull(function, "The given function may not be null").apply(axioms());
		Objects.requireNonNull(axioms, "The given function may not retrieve null");
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
	 * Part of the implementation of the improved algorithm for atomic
	 * decomposition. See "Improved Algorithms for Module Extraction and Atomic
	 * Decomposition" by Dmitry Tsarkov, 2012
	 */
	private @Nonnull OWLAxiom buildAtomsInModule(final OWLAxiom alpha, final Optional<OWLAxiom> beta) {

		assert alpha != null && beta != null;

		// The atom for alpha is already known
		if (atomOf.containsKey(alpha)) {
			return alpha;
		}

		final OWLAxiom delta = getAtomSeed(alpha, beta);
		final Atom atomOfDelta = atomOf.computeIfAbsent(delta, Atom::new);
		atomOfDelta.axioms.add(alpha);
		atomOf.put(alpha, atomOfDelta);

		if (beta.isPresent() && delta.equals(beta.get())) {
			return beta.get();
		}

		moduleToSignatureOf(alpha).filter(gamma -> !gamma.equals(alpha)).forEach(gamma -> {
			final OWLAxiom axiom = buildAtomsInModule(gamma, Optional.of(alpha));
			dependenciesOf.put(atomOf.get(axiom), atomOf.get(alpha));
		});

		return alpha;
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
		for (final OWLAxiom alpha : axioms) {
			buildAtomsInModule(alpha, Optional.empty());
		}
	}

	/**
	 * Part of the implementation of the improved algorithm for atomic
	 * decomposition. See "Improved Algorithms for Module Extraction and Atomic
	 * Decomposition" by Dmitry Tsarkov, 2012
	 */
	private @Nonnull OWLAxiom getAtomSeed(final OWLAxiom alpha, final Optional<OWLAxiom> beta) {

		assert alpha != null && beta != null;

		final Set<OWLAxiom> moduleOfBeta;
		if (beta.isPresent()) {
			moduleOfBeta = moduleToSignatureOf.get(beta.get());
			moduleExtractor.extract(alpha.signature(), Optional.of(moduleOfBeta))
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
