package uk.ac.manchester.cs.owl.explanation.ordering;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 */
public class EntailedAxiomTree extends ExplanationTree {
    
    public EntailedAxiomTree(OWLAxiom userObject) {
        super(userObject);
    }


    @Override
	public boolean isEntailed() {
        return true;
    }


    @Override
	public String toString() {
        return super.toString();
    }
}
