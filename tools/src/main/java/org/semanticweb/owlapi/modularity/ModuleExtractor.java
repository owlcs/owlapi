package org.semanticweb.owlapi.modularity;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Interface for classes that extract modules based on fixed axiom bases.
 * Implementations of this interface may use precomputation to optimize
 * calculating multiple modules for the same axiom base but for differing
 * signatures.
 *
 * @author Marc Robin Nolte
 *
 */
public interface ModuleExtractor {

	/**
	 * Return the axioms all modules of this {@link ModuleExtractor} are computed
	 * against, including global axioms and tautologies.
	 *
	 * @return The axioms as specified above
	 */
	@Nonnull
	Stream<OWLAxiom> axiomBase();

	/**
	 * Returns whether or not the axiom base of this {@link ModuleExtractor}
	 * contains the given {@link OWLAxiom}.
	 *
	 * @param axiom The axiom to test
	 * @return A boolean value as specified above
	 */
	default boolean containsAxiom(final OWLAxiom axiom) {
		return axiomBase().anyMatch(axiom::equals);
	}

	/**
	 * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 * is contained in every module calculated by the module extraction method this
	 * {@link ModuleExtractor} is based on; <code>false</code> when no such
	 * guarantee can be made (Note: This does not mean that there is some module
	 * regardless of other axioms or the signature that does not contain the given
	 * axiom).
	 *
	 * This methods returning <code>true</code> implies that
	 * {@link ModuleExtractor#noModuleContains(OWLAxiom)} returns <code>false</code>
	 * for the same axiom.
	 *
	 * @param axiom The {@link OWLAxiom} to check
	 * @return A boolean value as specified above
	 */
	default boolean everyModuleContains(final OWLAxiom axiom) {
		return extract(Stream.empty(), Optional.of(Collections.singleton(axiom))).count() == 1;
	}

	/**
	 * Extracts a module with respect to the given signature against the axiom base
	 * of this {@link ModuleExtractor}.
	 *
	 * @param signature The signature the module should be extracted against
	 *
	 * @return The axioms of the module with respect to the given signature
	 */
	default @Nonnull Stream<OWLAxiom> extract(final Stream<OWLEntity> signature) {
		return extract(signature, Optional.empty());
	}

	/**
	 * Extracts a module with respect to the given signature against the eventually
	 * given subset of the axiom base of this {@link ModuleExtractor}. If the given
	 * {@link OWLAxiom}s are not a subset of the axiom base of this
	 * {@link ModuleExtractor}, this method's behavior is not specified.
	 *
	 * @param signature    The signature the module should be extracted against.
	 * @param subAxiomBase The subset of this {@link ModuleExtractor}'s axiom base
	 *                     which the module should be calculated against. If not
	 *                     present, the {@link ModuleExtractor#axiomBase()} is
	 *                     considered as the axiom base the module should be
	 *                     calculated against. Will not be modified
	 *
	 * @return The axioms of the module with respect to the given signature
	 */
	@Nonnull
	Stream<OWLAxiom> extract(final Stream<OWLEntity> signature, final Optional<Set<OWLAxiom>> subAxiomBase);

	/**
	 * Extracts a module with respect to the given signature against the given
	 * subset of the axiom base of this {@link ModuleExtractor}. If the given
	 * {@link OWLAxiom}s are not a subset of the axiom base of this
	 * {@link ModuleExtractor}, this method's behavior is not specified.
	 *
	 * @param signature    The signature the module should be extracted against.
	 *                     Will not be modified
	 * @param subAxiomBase The subset of this {@link ModuleExtractor}'s axiom base
	 *                     which the module should be calculated against. May not be
	 *                     null. Will not be modified
	 *
	 * @return The axioms of the module with respect to the given signature
	 */
	default @Nonnull Stream<OWLAxiom> extract(final Stream<OWLEntity> signature, final Set<OWLAxiom> subAxiomBase) {
		return extract(signature, Optional.of(subAxiomBase));
	}

	/**
	 * Extracts a module with respect to the given signature against the given
	 * subset of the axiom base of this {@link ModuleExtractor}. If the given
	 * {@link OWLAxiom}s are not a subset of the axiom base of this
	 * {@link ModuleExtractor}, this method's behavior is not specified.
	 *
	 * @param signature    The signature the module should be extracted against
	 * @param subAxiomBase The subset of this {@link ModuleExtractor}'s axiom base
	 *                     which the module should be calculated against. May not be
	 *                     null
	 *
	 * @return The axioms of the module with respect to the given signature
	 */
	default @Nonnull Stream<OWLAxiom> extract(final Stream<OWLEntity> signature, final Stream<OWLAxiom> subAxiomBase) {
		final Optional<Set<OWLAxiom>> subAxiomBaseAsSet = Optional.of(Objects
				.requireNonNull(subAxiomBase, "The given subAxiomBase may not be null.").collect(Collectors.toSet()));
		return extract(signature, subAxiomBaseAsSet);
	}

	/**
	 *
	 * Extracts a module as an {@link OWLOntology} with respect to the given
	 * signature over the given axiom base.
	 *
	 * @param signature       The signature the module should be extracted against
	 * @param ontologyManager The {@link OWLOntologyManager} used to create the new
	 *                        {@link OWLOntology}
	 * @param ontologyIRI     The {@link IRI} of the new {@link OWLOntology}
	 * @return The module as an {@link OWLOntology}
	 * @throws OWLOntologyCreationException If there is an exception when creating
	 *                                      the {@link OWLOntology}
	 */
	default @Nonnull OWLOntology extractAsOntology(final Stream<OWLEntity> signature,
			final OWLOntologyManager ontologyManager, final IRI ontologyIRI) throws OWLOntologyCreationException {
		final OWLOntology ontology = Objects
				.requireNonNull(ontologyManager, "The given ontology manager may not be null")
				.createOntology(Objects.requireNonNull(ontologyIRI, "The given iri may not be null"));
		ontology.addAxioms(extract(Objects.requireNonNull(signature, "The given signature may not be null")));
		return ontology;
	}

	/**
	 * Returns from the axiom base of this extractor exactly those that are
	 * guaranteed to be contained in every module calculated by this
	 * {@link ModuleExtractor}.
	 *
	 * These axioms may be precomputed or calculated on every call of this method.
	 *
	 * @return The axioms as specified above
	 */
	default @Nonnull Stream<OWLAxiom> globals() {
		return extract(axiomBase().parallel().filter(this::everyModuleContains).flatMap(OWLAxiom::signature));
	}

	/**
	 * Returns <code>true</code> if it is guaranteed that the given {@link OWLAxiom}
	 * is not contained in any module (regardless of other axioms or the signature)
	 * calculated by the module extraction method this {@link ModuleExtractor} is
	 * based on; <code>false</code> when no such guarantee can be made (Note: This
	 * does not mean that there is some module that contains the given axiom).
	 *
	 * This methods returning <code>true</code> implies that
	 * {@link ModuleExtractor#everyModuleContains(OWLAxiom)} returns
	 * <code>false</code> for the same axiom.
	 *
	 * @param axiom The {@link OWLAxiom} to check
	 * @return A boolean value as specified above
	 */
	default boolean noModuleContains(final OWLAxiom axiom) {
		return extract(axiom.signature(), Collections.singleton(axiom)).count() == 0;
	}

	/**
	 * Returns from the axiom base of this extractor exactly those that are
	 * guaranteed not to be contained in any module calculated by this
	 * {@link ModuleExtractor}.
	 *
	 * These axioms may be precomputed or calculated on every call of this method.
	 *
	 * @return The axioms as specified above
	 */
	default @Nonnull Stream<OWLAxiom> tautologies() {
		return axiomBase().parallel().filter(this::noModuleContains);
	}

}
