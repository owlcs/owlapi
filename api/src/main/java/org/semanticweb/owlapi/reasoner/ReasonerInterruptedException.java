package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 29-Oct-2009
 */
public class ReasonerInterruptedException extends OWLRuntimeException {

    public ReasonerInterruptedException() {
    }

    public ReasonerInterruptedException(String message) {
        super(message);
    }

    public ReasonerInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReasonerInterruptedException(Throwable cause) {
        super(cause);
    }
}
