package org.semanticweb.owlapi.modularity.locality;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Class to extract modules based on semantic locality.
 *
 * @author Marc Robin Nolte (modified version)
 *
 */
public class SyntacticLocalityModuleExtractor extends LocalityModuleExtractor {
	
	/**
	 * Instantiates a new {@link SyntacticLocalityModuleExtractor}.
	 *
	 * @param localityClass The {@link LocalityClass} to use
	 * @param axiomBase     The axiom base of the new
	 *                      {@link SyntacticLocalityModuleExtractor}
	 *
	 *
	 */
	public SyntacticLocalityModuleExtractor(final LocalityClass localityClass, final Stream<OWLAxiom> axiomBase) {
		super(localityClass, axiomBase);
	}

	@Override
	protected LocalityEvaluator bottomEvaluator() {
		return SyntacticLocalityEvaluator.BOTTOM;
	}

	@Override
	protected LocalityEvaluator topEvaluator() {
		return SyntacticLocalityEvaluator.TOP;
	}
	
}
