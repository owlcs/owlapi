package org.semanticweb.owlapi.modularity;

import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * Class to extract modules based on semantic locality.
 *
 * @author Marc Robin Nolte
 *
 */
public class SemanticLocalityModuleExtractor implements AbstractLocalityModuleExtractor {

	/**
	 * The used {@link LocalityEvaluator}.
	 */
	private @Nonnull final LocalityEvaluator localityEvaluator;

	/**
	 * Instantiates a new {@link SemanticLocalityEvaluator}.
	 *
	 * @param ontologyManager The {@link OWLOntologyManager} to create a reasoner
	 *                        with
	 * @param reasonerFactory The {@link OWLReasonerFactory} to create a reasoner
	 *                        with
	 */
	public SemanticLocalityModuleExtractor(final OWLOntologyManager ontologyManager,
			final OWLReasonerFactory reasonerFactory) {
		localityEvaluator = new SemanticLocalityEvaluator(ontologyManager, reasonerFactory);
	}

	@Override
	public boolean everyModuleContains(final OWLAxiom axiom) {
		return !localityEvaluator.isLocal(axiom, Stream.empty());
	}

	@Override
	public @Nonnull Stream<OWLAxiom> extractModule(final Stream<OWLAxiom> axiomBase, final Stream<OWLEntity> signature,
			final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
		return AbstractLocalityModuleExtractor.extractModule(axiomBase, signature, localityEvaluator, guaranteeFunction)
				.stream();
	}

	@Override
	public boolean noModuleContains(final OWLAxiom axiom) {
		return localityEvaluator.isLocal(axiom, axiom.signature());
	}

}
