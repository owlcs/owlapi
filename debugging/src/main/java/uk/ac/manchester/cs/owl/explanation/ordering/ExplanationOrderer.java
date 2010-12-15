package uk.ac.manchester.cs.owl.explanation.ordering;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2008<br><br>
 */
public interface ExplanationOrderer {

    /**
     * Gets an ordered (and possibly indented) explanation.  The orderer really provides some
     * kind of "presentation" layer to make an explanation easier to read.
     * @param entailment The axiom that represents the entailment that the explanation
     * being ordered is a justification for.
     * @param axioms The axioms that constitute the unordered explanation
     * @return An order explanation.
     */
    ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> axioms);
}
