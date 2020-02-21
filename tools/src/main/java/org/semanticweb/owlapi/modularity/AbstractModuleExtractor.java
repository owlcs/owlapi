package org.semanticweb.owlapi.modularity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;

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
	 * {@link AbstractModuleExtractor#globals()}.
	 */
	private @Nonnull Optional<Set<OWLAxiom>> globals = Optional.empty();

	/**
	 * The axioms that are guaranteed to be contained in no module calculated by
	 * this {@link AbstractModuleExtractor}. Will be calculated when first calling
	 * {@link AbstractModuleExtractor#tautologies()}.
	 */
	private @Nonnull Optional<Set<OWLAxiom>> tautologies = Optional.empty();

	/**
	 * Creates a new {@link AbstractModuleExtractor}.
	 *
	 * @param axiomBase the axiom base of the new {@link AbstractModuleExtractor}
	 */
	protected AbstractModuleExtractor(final Stream<OWLAxiom> axiomBase) {
		this.axiomBase = axiomBase.filter(OWLAxiom::isLogicalAxiom).collect(Collectors.toSet());
	}

	@Override
	public final @Nonnull Stream<OWLAxiom> axiomBase() {
		return axiomBase.stream();
	}

	@Override
	public boolean containsAxiom(final OWLAxiom axiom) {
		return axiomBase.contains(axiom);
	}

	@Override
	public final boolean everyModuleContains(final OWLAxiom axiom) {
		if (globals.isPresent()) {
			return globals.get().contains(axiom);
		}
		return ModuleExtractor.super.everyModuleContains(axiom);
	}

	@Override
	public final @Nonnull Stream<OWLAxiom> globals() {
		if (!globals.isPresent()) {
			globals = Optional.of(ModuleExtractor.super.globals().collect(Collectors.toSet()));
		}
		return globals.get().stream();
	}

	@Override
	public final boolean noModuleContains(final OWLAxiom axiom) {
		if (tautologies.isPresent()) {
			return tautologies.get().contains(axiom);
		}
		return ModuleExtractor.super.noModuleContains(axiom);
	}

	@Override
	public final @Nonnull Stream<OWLAxiom> tautologies() {
		if (!tautologies.isPresent()) {
			tautologies = Optional.of(ModuleExtractor.super.tautologies().collect(Collectors.toSet()));
		}
		return tautologies.get().stream();
	}

}
