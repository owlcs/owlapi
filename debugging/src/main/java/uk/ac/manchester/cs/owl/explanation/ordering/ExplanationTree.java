package uk.ac.manchester.cs.owl.explanation.ordering;

import org.semanticweb.owlapi.model.OWLAxiom;

import uk.ac.manchester.cs.bhig.util.MutableTree;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 */
public class ExplanationTree extends MutableTree<OWLAxiom> {

    public ExplanationTree(OWLAxiom userObject) {
        super(userObject);
    }

    public boolean isEntailed() {
        return false;
    }
}
