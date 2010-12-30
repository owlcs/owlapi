package com.clarkparsia.owlapi.explanation.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Nov-2007<br><br>
 */
public class SilentExplanationProgressMonitor implements ExplanationProgressMonitor {

	@SuppressWarnings("unused")
    public void foundExplanation(Set<OWLAxiom> axioms) {
    }


    public boolean isCancelled() {
        return false;
    }


    public void foundAllExplanations() {
    }
}
