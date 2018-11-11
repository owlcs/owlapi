package org.semanticweb.owlapi.modularity;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * Wraps a {@link ModuleExtractor} and offers the same methods but deprecated as
 * {@link uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor}
 * for convenience migration.
 *
 * @author Marc Robin Nolte
 *
 */
public class AxiomBasedModuleExtractor {

	/**
	 * Either this or {@link AxiomBasedModuleExtractor#ontology} are
	 * <code>null</code>; the other one is not <code>null</code> and functions as
	 * the collection of {@link OWLAxiom}s used as a base for extracted modules.
	 */
	@Nullable
	private final Set<OWLAxiom> axioms;

	/**
	 * Either this or {@link AxiomBasedModuleExtractor#axioms} are
	 * <code>null</code>; the other one is not <code>null</code> and functions as
	 * the collection of {@link OWLAxiom}s used as a base for extracted modules.
	 */
	@Nullable
	private final OWLOntology ontology;

	/**
	 * The wrapped {@link ModuleExtractor}.
	 */
	@Nonnull
	private final ModuleExtractor wrappedModuleExtractor;

	/**
	 * Instantiates a new {@link AxiomBasedModuleExtractor}.
	 *
	 * @param ontology               The {@link OWLOntology} whose import closure
	 *                               should be used as the module base. Changes of
	 *                               the ontology are considered when extracting a
	 *                               module.
	 * @param wrappedModuleExtractor The wrapped module extractor
	 */
	public AxiomBasedModuleExtractor(final OWLOntology ontology, final ModuleExtractor wrappedModuleExtractor) {
		this.ontology = Objects.requireNonNull(ontology, "The given ontology may not be null");
		this.wrappedModuleExtractor = Objects.requireNonNull(wrappedModuleExtractor,
				"The given module extractor may not be null");
		axioms = null;
	}

	/**
	 * Instantiates a new {@link AxiomBasedModuleExtractor}.
	 *
	 * @param axioms                 The axioms which should be used as the module
	 *                               base
	 * @param wrappedModuleExtractor The wrapped module extractor
	 */
	public AxiomBasedModuleExtractor(final Stream<OWLAxiom> axioms, final ModuleExtractor wrappedModuleExtractor) {
		this.axioms = Objects.requireNonNull(axioms, "The given axioms may not be null").collect(Collectors.toSet());
		ontology = null;
		this.wrappedModuleExtractor = Objects.requireNonNull(wrappedModuleExtractor,
				"The given module extractor may not be null");
	}

	/**
	 * Returns a {@link Stream} of {@link OWLAxiom}s which are the base of extracted
	 * modules. Depending on whether this object was instantiated with an
	 * {@link OWLOntology} or with a collection of axioms, the associated axioms are
	 * returned (including axioms of eventually imported ontologies).
	 *
	 * @return A stream of axioms as specified above
	 */
	public final @Nonnull Stream<OWLAxiom> axioms() {
		if (ontology != null) {
			return ontology.axioms(Imports.INCLUDED);
		}
		return axioms.stream();
	}

	/**
	 *
	 * Extracts a module as an {@link OWLOntology} with respect to the given
	 * signature.
	 *
	 * @param signature       The signature the module should be extracted against
	 * @param ontologyManager The {@link OWLOntologyManager} used to create the new
	 *                        {@link OWLOntology}
	 * @param ontologyIRI     The {@link IRI} of the new {@link OWLOntology}
	 * @return The module as an {@link OWLOntology}
	 * @throws OWLOntologyCreationException If there is an exception when creating
	 *                                      the {@link OWLOntology}
	 */
	public @Nonnull OWLOntology extractAsOntology(final Stream<OWLEntity> signature,
			final OWLOntologyManager ontologyManager, final IRI ontologyIRI) throws OWLOntologyCreationException {
		final OWLOntology ontology = Objects
				.requireNonNull(ontologyManager, "The given ontology manager may not be null")
				.createOntology(Objects.requireNonNull(ontologyIRI, "The given iri may not be null"));
		ontology.addAxioms(extractModule(Objects.requireNonNull(signature, "The given signature may not be null")));
		return ontology;
	}

	/**
	 * Extracts a module with respect to the given signature.
	 *
	 * @param signature The signature the module should be extracted against
	 * @return The axioms of the module with respect to the given signature
	 */
	public @Nonnull Stream<OWLAxiom> extractModule(final Stream<OWLEntity> signature) {
		return wrappedModuleExtractor.extractModule(axioms(), signature);
	}

}
