package org.semanticweb.owlapi.modularity.locality;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.modularity.AbstractModuleExtractor;
import org.semanticweb.owlapi.modularity.ModuleExtractor;

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
	 * The locality class used by this {@link SyntacticLocalityModuleExtractor}.
	 */
	private @Nonnull LocalityClass localityClass;

	/**
	 * Map associating each entity with the axioms that contains it.
	 */
	private @Nonnull final SetMultimap<OWLEntity, OWLAxiom> axiomsContainingEntity = HashMultimap.create();

	/**
	 * Creates a new {@link LocalityModuleExtractor}.
	 *
	 * @param localityClass The {@link LocalityClass} to use
	 * @param axiomBase     the axiom base of the new
	 *                      {@link LocalityModuleExtractor}
	 */
	protected LocalityModuleExtractor(final LocalityClass localityClass, final Stream<OWLAxiom> axiomBase) {
		super(axiomBase);
		setLocalityClass(localityClass);
		initialize();
	}

	/**
	 * @return The {@link LocalityEvaluator} corresponding to
	 *         {@link LocalityClass#BOTTOM}.
	 */
	protected abstract LocalityEvaluator bottomEvaluator();

	@Override
	public final Stream<OWLAxiom> extract(final Stream<OWLEntity> signature,
			final Optional<Predicate<OWLAxiom>> axiomFilter) {

		final Set<OWLEntity> signatureWithGlobals = Stream.concat(signature, globals().flatMap(OWLAxiom::signature))
				.collect(Collectors.toSet());

		Set<OWLAxiom> moduleWithoutGlobals;
		if (localityClass == LocalityClass.STAR) {
			moduleWithoutGlobals = extractStarModule(signatureWithGlobals, axiomFilter);
		} else {
			final LocalityEvaluator evaluator = localityClass == LocalityClass.BOTTOM ? bottomEvaluator()
					: topEvaluator();
			moduleWithoutGlobals = extractLocalityBasedModule(signatureWithGlobals, axiomFilter, evaluator);
		}
		return Stream.concat(globals(), moduleWithoutGlobals.stream());
	}

	/**
	 * Implementation of the improved algorithm for module extraction. See "Improved
	 * Algorithms for Module Extraction and Atomic Decomposition" by Dmitry Tsarkov,
	 * 2012.
	 *
	 * Uses a {@link Set} instead of a Stream for the sub axiom base in favor of
	 * STAR-module extraction.
	 */
	protected final @Nonnull Set<OWLAxiom> extractLocalityBasedModule(final Set<OWLEntity> signature,
			final Optional<Predicate<OWLAxiom>> axiomFilter, final LocalityEvaluator evaluator) {

		// Sub axiom base requires to filter the axioms
		Function<OWLEntity, Stream<OWLAxiom>> axiomsOfEntity = entity -> axiomsContainingEntity.get(entity).stream();
		if (axiomFilter.isPresent()) {
			axiomsOfEntity = axiomsOfEntity.andThen(stream -> stream.filter(axiomFilter.get()));
		}

		// Signature needs to be copied such that the original won't be changed.
		// STAR Modules would be wrong etc.
		final Set<OWLEntity> signatureCopy = new HashSet<>(signature);

		final Set<OWLAxiom> module = new HashSet<>();
		final Set<OWLEntity> workingSignature = new HashSet<>(signatureCopy);

		// actually extracting the module
		while (!workingSignature.isEmpty()) {
			final OWLEntity omega = workingSignature.stream().findAny().get();
			workingSignature.remove(omega);
			axiomsOfEntity.apply(omega)
					.forEach(alpha -> addNonLocal(alpha, signatureCopy, module, workingSignature, evaluator));
		}

		assert !axiomFilter.isPresent() || module.stream().allMatch(axiomFilter.get());

		return module;
	}

	/**
	 * Extracts the {@link SyntacticLocalityClass#STAR}-Module of the given axioms
	 * w.r.t. the given signature.
	 */
	protected final @Nonnull Set<OWLAxiom> extractStarModule(final Set<OWLEntity> signature,
			final Optional<Predicate<OWLAxiom>> axiomFilter) {

		final LocalityEvaluator bottom = bottomEvaluator(); // bot or empty_set
		final LocalityEvaluator top = topEvaluator(); // top or delta

		// Calculating the initial module
		Set<OWLAxiom> module = extractLocalityBasedModule(signature, axiomFilter, bottom);

		LocalityEvaluator nextExtractionType = top;
		int previousSize;
		// nesting modules until stabilization
		do {
			previousSize = module.size();
			module = extractLocalityBasedModule(signature, Optional.of(module::contains), nextExtractionType);
			nextExtractionType = nextExtractionType == bottom ? top : bottom;
		} while (previousSize != module.size());
		return module;
	}

	/**
	 * Returns the locality class used by this {@link LocalityModuleExtractor}.
	 *
	 * @return The locality class used by this {@link LocalityModuleExtractor}
	 */
	public final @Nonnull LocalityClass getLocalityClass() {
		return localityClass;
	}

	/**
	 * Initializes this {@link LocalityModuleExtractor}. Precomputes the globals and
	 * fills {@link LocalityModuleExtractor#axiomsContainingEntity}.
	 */
	private void initialize() {
		// initialize axiomsContainingEntity
		globals(); // precomputes globals
		axiomBase().filter(axiom -> !noModuleContains(axiom) && !everyModuleContains(axiom)).forEach(axiom -> {
			axiom.signature().forEach(entity -> {
				axiomsContainingEntity.put(entity, axiom);
			});
		});
	}

	/**
	 * Sets the locality class used by this {@link LocalityModuleExtractor} to the
	 * given one.
	 *
	 * @param localityClass The new locality class used by this
	 *                      {@link ocalityModuleExtractor}
	 */
	public final void setLocalityClass(final LocalityClass newLocalityClass) {
		localityClass = Objects.requireNonNull(newLocalityClass, "The given  locality class may not be null.");
	}

	/**
	 * @return The {@link LocalityEvaluator} corresponding to
	 *         {@link LocalityClass#TOP}.
	 */
	protected abstract LocalityEvaluator topEvaluator();

}
