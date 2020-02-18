package org.semanticweb.owlapi.modularity;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import com.google.common.collect.Sets;

/**
 * Abstract class for convenience implementation of {@link ModuleExtractor}s
 * able of precomputing global axioms and tautologies.
 *
 * @author Marc Robin Nolte
 *
 */
public abstract class AbstractModuleExtractor implements ModuleExtractor {

	/**
	 * The axiom base of this {@link AbstractModuleExtractor}.
	 */
	private final @Nonnull Set<OWLAxiom> axiomBase;

	/**
	 * The axioms that are guaranteed to be contained in every module calculated by
	 * this {@link AbstractModuleExtractor}. Will be calculated when first calling
	 * {@link AbstractModuleExtractor#precomputeGlobals()}.
	 */
	private @Nonnull Optional<Set<OWLAxiom>> globals;

	/**
	 * The axioms that are guaranteed to be contained in no module calculated by
	 * this {@link AbstractModuleExtractor}. Will be calculated when first calling
	 * {@link AbstractModuleExtractor#precomputeTautologies()}.
	 */
	private @Nonnull Optional<Set<OWLAxiom>> tautologies;

	/**
	 * Creates a new {@link AbstractModuleExtractor}.
	 *
	 * @param axiomBase the axiom base of the new {@link AbstractModuleExtractor}
	 */
	protected AbstractModuleExtractor(final Stream<OWLAxiom> axiomBase) {
		this.axiomBase = axiomBase.filter(OWLAxiom::isLogicalAxiom).collect(Collectors.toSet());
		globals = Optional.empty();
		tautologies = Optional.empty();
	}

	/**
	 * Return the axioms all modules of this {@link ModuleExtractor} are computed
	 * against, including global axioms and tautologies. Subclasses should use
	 * {@link AbstractModuleExtractor#precomputedAxioms()} which does not contain
	 * global axioms and tautologies (if they are precomputed, respectively).
	 *
	 * @return The axioms as specified above
	 */
	@Override
	public final @Nonnull Stream<OWLAxiom> axiomBase() {
		Stream<OWLAxiom> toReturn = axiomBase.stream();
		toReturn = Stream.concat(tautologies.stream().flatMap(Set::stream), toReturn);
		return Stream.concat(globals.stream().flatMap(Set::stream), toReturn);
	}

	@Override
	public boolean containsAxiom(final OWLAxiom axiom) {
		if (axiomBase.contains(axiom)) {
			return true;
		}
		if (tautologies.isPresent() && tautologies.get().contains(axiom)) {
			return true;
		}
		return globals.isPresent() && globals.get().contains(axiom);
	}

	@Override
	public boolean everyModuleContains(final OWLAxiom axiom) {
		if (globals.isPresent()) {
			return globals.get().contains(axiom);
		}
		return ModuleExtractor.super.everyModuleContains(axiom);
	}

	@Override
	public Stream<OWLAxiom> extract(final Stream<OWLEntity> signature, final Optional<Set<OWLAxiom>> subAxiomBase) {
		Objects.requireNonNull(signature, "The given signature may not be null");
		Objects.requireNonNull(subAxiomBase, "The given subAxiomBase may not be null");

		Stream<OWLEntity> cleanedSignature = signature;
		Optional<Set<OWLAxiom>> cleanedSubAxiomBase = subAxiomBase;
		if (globals.isPresent()) {
			cleanedSignature = Stream.concat(cleanedSignature, globals.get().stream().flatMap(OWLAxiom::signature));
			cleanedSubAxiomBase = cleanedSubAxiomBase.map(set -> Sets.difference(set, globals.get()));
		}
		if (tautologies.isPresent()) {
			cleanedSubAxiomBase = cleanedSubAxiomBase.map(set -> Sets.difference(set, tautologies.get()));
		}

		return extractModule(cleanedSignature, cleanedSubAxiomBase);
	}

	/**
	 * This method is wrapped by
	 * {@link AbstractModuleExtractor#extract(Stream, Stream)} to properly and
	 * efficiently deal with global axioms and tautologies.
	 *
	 * This method returns the module of the given {@link OWLAxiom}s to the given
	 * signature. The explanation is as follows:
	 *
	 * If tautologies are precomputed, the given {@link OWLAxiom}s do not contain
	 * tautologies. If global axioms are precomputed, the given {@link OWLAxiom}s do
	 * not contain global axioms, but the signature given by
	 * {@link AbstractModuleExtractor#extract(Stream, Stream)} is extended by the
	 * signature of the global axioms and
	 * {@link AbstractModuleExtractor#extract(Stream, Stream)} automatically appends
	 * the global axioms to the return of this method.
	 *
	 * There is no guarantee, that the given axioms are not global or tautologies,
	 * since by default this method is used to check whether they are or not.
	 *
	 * @param signature    The signature the module should be extracted against.
	 *                     Guaranteed to be non-<code>null</code>. May contain
	 *                     duplicates
	 * @param subAxiomBase The subset of this {@link ModuleExtractor}'s axiom base
	 *                     which the module should be calculated against. The given
	 *                     {@link Set} may be unmodifiable, but must never be
	 *                     modified regardless. Guaranteed to be
	 *                     non-<code>null</code>
	 *
	 * @return The axioms of the module with respect to the given signature
	 */
	protected abstract @Nonnull Stream<OWLAxiom> extractModule(Stream<OWLEntity> signature,
			Optional<Set<OWLAxiom>> subAxiomBase);

	@Override
	public final @Nonnull Stream<OWLAxiom> globals() {
		if (!globals.isPresent()) {
			precomputeGlobals();
		}
		return globals.get().stream();
	}

	/**
	 * Advises the implementing subclasses that the given axioms where removed from
	 * the precomputed axiom base due to being identified as tautologies or globals.
	 *
	 * @param toIgnore the axiom that should be ignored in the future
	 */
	protected abstract void ignoreAxioms(Stream<OWLAxiom> toIgnore);

	@Override
	public boolean noModuleContains(final OWLAxiom axiom) {
		if (tautologies.isPresent()) {
			return tautologies.get().contains(axiom);
		}
		return ModuleExtractor.super.noModuleContains(axiom);
	}

	/**
	 * Returns the axioms all modules of this {@link ModuleExtractor} are computed
	 * against, respectively excluding the global axioms and tautologies, if they
	 * have been precomputed.
	 *
	 * @return The axioms as specified above
	 */
	protected final @Nonnull Stream<OWLAxiom> precomputedAxioms() {
		return axiomBase.stream();
	}

	/**
	 * Precomputes globals (if not already done) to extract modules more
	 * efficiently.
	 */
	public final synchronized void precomputeGlobals() {
		if (globals.isPresent()) {
			return;
		}
		globals = Optional.of(precomputingAxioms(ModuleExtractor.super::globals));
	}

	/**
	 * Precomputes tautologies (if not already done) to extract modules more
	 * efficiently.
	 */
	public final synchronized void precomputeTautologies() {
		if (tautologies.isPresent()) {
			return;
		}
		tautologies = Optional.of(precomputingAxioms(ModuleExtractor.super::tautologies));
	}

	/**
	 * Helper method for class instantiation. Calculates the result of the given
	 * {@link Supplier} if the given flag is <code>true</code>, removes those
	 * {@link OWLAxiom}s from this {@link AbstractModuleExtractor}s axiom base and
	 * returns them. Returns <code>null</code> otherwise.
	 *
	 * @param precompute    if the above actions should be invoked
	 * @param axiomSupplier The supplier calculating the axioms
	 * @return The {@link Supplier}s result if the given flag is <code>true</code>,
	 *         <code>null</code> otherwise
	 */
	private Set<OWLAxiom> precomputingAxioms(final Supplier<Stream<OWLAxiom>> axiomSupplier) {
		final Set<OWLAxiom> axioms = axiomSupplier.get().collect(Collectors.toUnmodifiableSet());
		axiomBase.removeAll(axioms);
		ignoreAxioms(axioms.stream());
		return axioms;
	}

	@Override
	public final @Nonnull Stream<OWLAxiom> tautologies() {
		if (!tautologies.isPresent()) {
			precomputeTautologies();
		}
		return tautologies.get().stream();
	}

}
