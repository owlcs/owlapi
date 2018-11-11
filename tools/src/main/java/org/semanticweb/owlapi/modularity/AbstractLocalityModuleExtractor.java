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

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Abstract class for convenience implementation of locality-based
 * {@link ModuleExtractor}s. Offers protected methods for the improved algorithm
 * for module extraction.
 *
 * @author Marc Robin Nolte
 *
 */
@Nonnull
interface AbstractLocalityModuleExtractor extends ModuleExtractor {

	/**
	 * Part of the implementation of the improved algorithm for module extraction.
	 * See "Improved Algorithms for Module Extraction and Atomic Decomposition" by
	 * Dmitry Tsarkov, 2012
	 */
	static void addNonLocal(final OWLAxiom axiom, final Set<OWLEntity> signature, final Set<OWLAxiom> module,
			final Set<OWLEntity> workingSignature, final LocalityEvaluator evaluator) {
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
	 * Implementation of the improved algorithm for module extraction. See "Improved
	 * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov,
	 * 2012.
	 *
	 * @param guaranteeFunction The function to determine if axioms are contained in
	 *                          every module or in none. This translates to check if
	 *                          axioms are global or tautologies
	 */
	static @Nonnull Set<OWLAxiom> extractModule(final Stream<OWLAxiom> axioms, final Stream<OWLEntity> signature,
			final LocalityEvaluator evaluator, final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
		// Signature needs to be copied such that the original won't be changed.
		// STAR Modules would be wrong etc.
		final Set<OWLEntity> signatureCopy = Objects.requireNonNull(signature, "The given signature might not be null")
				.filter(Objects::nonNull).collect(Collectors.toSet());
		// Map associating each entity with its containing axioms
		final Map<OWLEntity, Set<OWLAxiom>> sa = new HashMap<>();
		// The set of the module
		final Set<OWLAxiom> module = new HashSet<>();
		// Set of axioms contained in each module regardless of the signature
		final Set<OWLAxiom> globals = new HashSet<>();

		Objects.requireNonNull(guaranteeFunction, "The given guarantee function might not be null");
		Objects.requireNonNull(axioms, "The given axiom base might not be null")
				.filter(axiom -> Objects.nonNull(axiom)
						&& !guaranteeFunction.apply(axiom).equals(ModuleGuarantee.NOT_CONTAINED_IN_ANY_MODULE))
				.forEach(axiom -> {
					// axiom is non null and not a tautology that can be found by the associated
					// locality

					if (guaranteeFunction.apply(axiom).equals(ModuleGuarantee.CONTAINED_IN_ALL_MODULES)) {
						// axiom is global
						globals.add(axiom);
						return;
					}
					axiom.signature().forEach(entity -> {
						Set<OWLAxiom> containingAxioms;
						if (!sa.containsKey(entity)) {
							containingAxioms = new HashSet<>();
							sa.put(entity, containingAxioms);
						} else {
							containingAxioms = sa.get(entity);
						}
						containingAxioms.add(axiom);
					});

				});

		final Set<OWLEntity> workingSignature = new HashSet<>(signatureCopy);
		globals.forEach(tautology -> addNonLocal(tautology, signatureCopy, module, workingSignature, evaluator));

		while (!workingSignature.isEmpty()) {
			final OWLEntity omega = workingSignature.iterator().next();
			workingSignature.remove(omega);
			if (sa.containsKey(omega)) {
				sa.get(omega).forEach(alpha -> addNonLocal(alpha, signatureCopy, module, workingSignature, evaluator));
			}
		}

		return module;
	}

}
