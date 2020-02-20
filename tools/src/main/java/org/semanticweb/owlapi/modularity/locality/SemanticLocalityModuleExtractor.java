package org.semanticweb.owlapi.modularity.locality;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAxiom;
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
	 * The manager to instantiate an {@link OWLReasoner}.
	 */
	private final OWLReasonerFactory reasonerFactory;

	/**
	 * The factory to instantiate an {@link OWLReasoner}.
	 */
	private final OWLOntologyManager ontologyManager;

	/**
	 * Instantiates a new {@link SemanticLocalityEvaluator}.
	 *
	 *
	 * @param localityClass   The {@link LocalityClass} to use
	 * @param axiomBase       The axiom base of the new
	 *                        {@link SemanticLocalityModuleExtractor}
	 * @param ontologyManager The manager that should be used to instantiate an
	 *                        {@link OWLReasoner}
	 * @param reasonerFactory The factory that should be used to instantiate an
	 *                        {@link OWLReasoner}
	 */
	public SemanticLocalityModuleExtractor(final LocalityClass localityClass, final Stream<OWLAxiom> owlAxiomBase,
			final OWLOntologyManager ontologyManager, final OWLReasonerFactory reasonerFactory) {
		super(localityClass, owlAxiomBase);
		this.ontologyManager = ontologyManager;
		this.reasonerFactory = reasonerFactory;
	}

	@Override
	protected LocalityEvaluator bottomEvaluator() {
		return new SemanticLocalityEvaluator(LocalityClass.BOTTOM, ontologyManager, reasonerFactory);
	}

	@Override
	protected LocalityEvaluator topEvaluator() {
		return new SemanticLocalityEvaluator(LocalityClass.TOP, ontologyManager, reasonerFactory);
	}

}
