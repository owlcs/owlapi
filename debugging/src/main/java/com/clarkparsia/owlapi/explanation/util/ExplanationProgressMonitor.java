package com.clarkparsia.owlapi.explanation.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Nov-2007<br><br>
 *
 * A progress monitor for an explanation generator.  The
 * progress monitor should be used with an <code>MultipleExpalanationGenerator</code>
 */
public interface ExplanationProgressMonitor {

    /**
     * Determines if the process of generating explanations has been
     * cancelled.
     * @return <code>true</code> if the process has been cancelled, or
     * <code>false</code> if the process hasn't been cancelled.
     */
    boolean isCancelled();


    /**
     * Called when an explanation has been found.
     * @param axioms The axioms that constitute the explanation
     */
    void foundExplanation(Set<OWLAxiom> axioms);


    /**
     * Called when all explanations have been found.
     */
    void foundAllExplanations();
}
