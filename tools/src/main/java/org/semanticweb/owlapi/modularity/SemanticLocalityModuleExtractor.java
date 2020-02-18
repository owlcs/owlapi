package org.semanticweb.owlapi.modularity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * Class to extract modules based on semantic locality.
 *
 * @author Marc Robin Nolte
 *
 */
public final class SemanticLocalityModuleExtractor extends LocalityModuleExtractor {

	/**
	 * The used {@link SemanticLocalityEvaluator}.
	 */
	private @Nonnull final SemanticLocalityEvaluator localityEvaluator;

	/**
	 * Instantiates a new {@link SemanticLocalityEvaluator}.
	 *
	 * @param axiomBase       The axiom base of the new
	 *                        {@link SemanticLocalityModuleExtractor}
	 * @param ontologyManager The manager that should be used to instantiate an
	 *                        {@link OWLReasoner}
	 * @param reasonerFactory The factory that should be used to instantiate an
	 *                        {@link OWLReasoner}
	 */
	public SemanticLocalityModuleExtractor(final Stream<OWLAxiom> owlAxiomBase,
			final OWLOntologyManager ontologyManager, final OWLReasonerFactory reasonerFactory) {
		super(owlAxiomBase);
		localityEvaluator = new SemanticLocalityEvaluator(ontologyManager, reasonerFactory);
	}

	@Override
	protected Stream<OWLAxiom> extractModule(final Stream<OWLEntity> signature,
			final Optional<Set<OWLAxiom>> subAxiomBase) {
		return extractLocalityBasedModule(signature, subAxiomBase, localityEvaluator).stream();
	}

}
