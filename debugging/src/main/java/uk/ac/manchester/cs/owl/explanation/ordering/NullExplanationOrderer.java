package uk.ac.manchester.cs.owl.explanation.ordering;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 *
 * An implementation of an explanation order, which doesn't
 * really do any ordering! 
 */
public class NullExplanationOrderer implements ExplanationOrderer {

    public ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> axioms) {
        ExplanationTree root = new ExplanationTree(entailment);
        for(OWLAxiom ax : axioms) {
            root.addChild(new ExplanationTree(ax));
        }
        return root;
    }
}
