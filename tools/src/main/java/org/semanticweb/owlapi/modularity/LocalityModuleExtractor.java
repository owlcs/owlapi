package org.semanticweb.owlapi.modularity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * Abstract class for convenience implementation of locality-based
 * {@link ModuleExtractor}s. Offers protected methods for the improved algorithm
 * for module extraction.
 *
 * @author Marc Robin Nolte
 *
 */
@Nonnull
public abstract class LocalityModuleExtractor extends AbstractModuleExtractor {

	/**
	 * Part of the implementation of the improved algorithm for module extraction.
	 * See "Improved Algorithms for Module Extraction and Atomic Decomposition" by
	 * Dmitry Tsarkov, 2012
	 */
	private final static void addNonLocal(final OWLAxiom axiom, final Set<OWLEntity> signature,
			final Set<OWLAxiom> module, final Set<OWLEntity> workingSignature, final LocalityEvaluator evaluator) {
		if (!module.contains(axiom) && !evaluator.isLocal(axiom, signature)) {
			// M ← M ∪ α
			module.add(axiom);
			// S ← S ∪ (̃α \ Σ)
			final Set<OWLEntity> axiomSignature = axiom.signature().collect(Collectors.toSet());
			axiomSignature.removeAll(signature);
			workingSignature.addAll(axiomSignature);
			// Σ ← Σ ∪ ̃α
			axiom.signature().forEach(signature::add);
		}
	}

	/**
	 * Map associating each entity with its containing axioms.
	 */
	private @Nonnull final SetMultimap<OWLEntity, OWLAxiom> sa;

	/**
	 * Creates a new {@link LocalityModuleExtractor}.
	 *
	 * @param axiomBase the axiom base of the new {@link LocalityModuleExtractor}
	 */
	protected LocalityModuleExtractor(final Stream<OWLAxiom> axiomBase) {
		super(axiomBase);

		// initialize sa
		sa = HashMultimap.create();
		precomputedAxioms().forEach(axiom -> {
			axiom.signature().forEach(entity -> {
				sa.put(entity, axiom);
			});
		});
	}

	/**
	 * Implementation of the improved algorithm for module extraction. See "Improved
	 * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov,
	 * 2012.
	 *
	 * Uses a {@link Set} instead of a Stream for the sub axiom base in favor of
	 * STAR-module extraction.
	 */
	protected final @Nonnull Set<OWLAxiom> extractLocalityBasedModule(final Stream<OWLEntity> signature,
			final Optional<Set<OWLAxiom>> subAxiomBase, final LocalityEvaluator evaluator) {

		// Sub axiom base requires to filter the axioms
		Function<OWLEntity, Stream<OWLAxiom>> axiomsOfEntity = entity -> sa.get(entity).stream();
		if (subAxiomBase.isPresent()) {
			axiomsOfEntity = axiomsOfEntity
					.andThen(stream -> stream.filter(axiom -> subAxiomBase.get().contains(axiom)));
		}

		// Signature needs to be copied such that the original won't be changed.
		// STAR Modules would be wrong etc.
		final Set<OWLEntity> signatureSet = signature.collect(Collectors.toSet());

		final Set<OWLAxiom> module = new HashSet<>();
		final Set<OWLEntity> workingSignature = new HashSet<>(signatureSet);

		// actually extracting the module
		while (!workingSignature.isEmpty()) {
			final OWLEntity omega = workingSignature.stream().findAny().get();
			workingSignature.remove(omega);
			axiomsOfEntity.apply(omega)
					.forEach(alpha -> addNonLocal(alpha, signatureSet, module, workingSignature, evaluator));
		}

		assert !subAxiomBase.isPresent() || subAxiomBase.get().containsAll(module);

		return module;
	}

	@Override
	protected final void ignoreAxioms(final Stream<OWLAxiom> toIgnore) {
		toIgnore.forEach(axiom -> axiom.signature().forEach(entity -> sa.remove(entity, axiom)));
	}

}
