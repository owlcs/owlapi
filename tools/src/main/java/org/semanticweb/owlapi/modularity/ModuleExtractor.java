package org.semanticweb.owlapi.modularity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Interface for classes that extract modules.
 *
 * @author Marc Robin Nolte
 *
 */
public interface ModuleExtractor {

	/**
	 * Enumeration of pairwise contradictory possible guarantees for an
	 * {@link OWLAxiom} regarding modularity. Each axiom can be assigned to such a
	 * guarantee using {@link ModuleExtractor#guaranteeFor(OWLAxiom)}, where
	 * {@link ModuleGuarantee#NONE} is the default one for axioms that cannot be
	 * assigned to another.
	 *
	 * @author Marc Robin Nolte
	 *
	 */
	public enum ModuleGuarantee {

		/**
		 * The associated {@link OWLAxiom} is contained in all modules calculated by the
		 * assigning {@link ModuleExtractor} regardless of the axiom base or signature.
		 */
		CONTAINED_IN_ALL_MODULES,

		/**
		 * No other guarantee can be made by the assigning {@link ModuleExtractor} for
		 * the associated {@link OWLAxiom}. This is not to be equated with the negation
		 * of all other guarantees - one of them could apply, but not be determined by
		 * the assigning extractor.
		 */
		NONE,

		/**
		 * The associated {@link OWLAxiom} is not contained in any module calculated by
		 * the assigning {@link ModuleExtractor} regardless of the axiom base or
		 * signature.
		 */
		NOT_CONTAINED_IN_ANY_MODULE

	}

	/**
	 * Returns a {@link Function} that lazily calculates and caches the
	 * {@link ModuleGuarantee} for a given {@link OWLAxiom}. That {@link Function}
	 * is useful as a parameter for
	 * {@link ModuleExtractor#extractModule(Stream, Stream, Function)} when
	 * calculating lots of modules over the same axiom base.
	 *
	 * @return A {@link Function} as specified above
	 */
	default @Nonnull Function<OWLAxiom, ModuleGuarantee> cachedGuaranteeFor() {
		final Map<OWLAxiom, ModuleGuarantee> guaranteeCache = new HashMap<>();
		return axiom -> guaranteeCache.computeIfAbsent(axiom, this::guaranteeFor);
	}

	/**
	 * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 * is contained in every module calculated by this {@link ModuleExtractor};
	 * <code>false</code> when no such guarantee can be made (Note: This does not
	 * mean that there is some module (regardless of other axioms or the signature)
	 * that does not contain the given axiom).
	 *
	 * This methods returning <code>true</code> needs to imply that
	 * {@link ModuleExtractor#noModuleContains(OWLAxiom)} returns <code>false</code>
	 * for the same axiom.
	 *
	 * @param axiom The {@link OWLAxiom} to check
	 * @return <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 *         is contained in every module (regardless of other axioms or the
	 *         signature) calculated by this {@link ModuleExtractor};
	 *         <code>false</code> when no such guarantee can be made
	 */
	boolean everyModuleContains(OWLAxiom axiom);

	/**
	 *
	 * Extracts a module as an {@link OWLOntology} with respect to the given
	 * signature over the given axiom base.
	 *
	 * @param signature       The signature the module should be extracted against
	 * @param ontologyManager The {@link OWLOntologyManager} used to create the new
	 *                        {@link OWLOntology}
	 * @param ontologyIRI     The {@link IRI} of the new {@link OWLOntology}
	 * @param axiomBase       The axioms the calculated module should be based on
	 * @return The module as an {@link OWLOntology}
	 * @throws OWLOntologyCreationException If there is an exception when creating
	 *                                      the {@link OWLOntology}
	 */
	default @Nonnull OWLOntology extractAsOntology(final Stream<OWLAxiom> axiomBase, final Stream<OWLEntity> signature,
			final OWLOntologyManager ontologyManager, final IRI ontologyIRI) throws OWLOntologyCreationException {
		final OWLOntology ontology = Objects
				.requireNonNull(ontologyManager, "The given ontology manager may not be null")
				.createOntology(Objects.requireNonNull(ontologyIRI, "The given iri may not be null"));
		ontology.addAxioms(
				extractModule(axiomBase, Objects.requireNonNull(signature, "The given signature may not be null")));
		return ontology;
	}

	/**
	 * Extracts a module with respect to the given signature.
	 *
	 * @param axiomBase The axioms the calculated module should be based on
	 * @param signature The signature the module should be extracted against
	 * @return The axioms of the module with respect to the given signature
	 */
	default @Nonnull Stream<OWLAxiom> extractModule(final Stream<OWLAxiom> axiomBase,
			final Stream<OWLEntity> signature) {
		return extractModule(axiomBase, signature, this::guaranteeFor);
	}

	/**
	 * Extracts a module with respect to the given signature.
	 *
	 * @param axiomBase         The axioms the calculated module should be based on
	 * @param signature         The signature the module should be extracted against
	 * @param guaranteeFunction The function to determine if axioms are contained in
	 *                          every module or in none. This parameter allows those
	 *                          information to be precalculated if, for example,
	 *                          lots of modules are to be calculated over the same
	 *                          axiom base.
	 * @return The axioms of the module with respect to the given signature
	 */
	@Nonnull
	Stream<OWLAxiom> extractModule(Stream<OWLAxiom> axiomBase, final Stream<OWLEntity> signature,
			final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction);

	/**
	 * Assigns the {@link ModuleGuarantee} to the given {@link OWLAxiom}. Returns
	 * {@link ModuleGuarantee#CONTAINED_IN_ALL_MODULES} if
	 * {@link ModuleExtractor#everyModuleContains(OWLAxiom)} is <code>true</code>,
	 * {@link ModuleGuarantee#NOT_CONTAINED_IN_ANY_MODULE} if
	 * {@link ModuleExtractor#noModuleContains(OWLAxiom)} is <code>true</code> and
	 * {@link ModuleGuarantee#NONE} if both return <code>false</code>.
	 *
	 * @param axiom The axiom which assigned {@link ModuleGuarantee} should be
	 *              returned
	 * @return The {@link ModuleGuarantee} this {@link ModuleExtractor} assigns to
	 *         the given {@link OWLAxiom}
	 */
	default @Nonnull ModuleGuarantee guaranteeFor(final OWLAxiom axiom) {
		if (everyModuleContains(axiom)) {
			return ModuleGuarantee.CONTAINED_IN_ALL_MODULES;
		}
		if (noModuleContains(axiom)) {
			return ModuleGuarantee.NOT_CONTAINED_IN_ANY_MODULE;
		}
		return ModuleGuarantee.NONE;
	}

	/**
	 * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 * is not contained in any module (regardless of other axioms or the signature)
	 * calculated by this {@link ModuleExtractor}; <code>false</code> when no such
	 * guarantee can be made (Note: This does not mean that there is some module
	 * that contains the given axiom).
	 *
	 * This methods returning <code>true</code> needs to imply that
	 * {@link ModuleExtractor#everyModuleContains(OWLAxiom)} returns
	 * <code>false</code> for the same axiom.
	 *
	 * @param axiom The {@link OWLAxiom} to check
	 * @return <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 *         is not contained in any module (regardless of other axioms or the
	 *         signature) calculated by this {@link ModuleExtractor};
	 *         <code>false</code> when no such guarantee can be made
	 */
	boolean noModuleContains(OWLAxiom axiom);

}
