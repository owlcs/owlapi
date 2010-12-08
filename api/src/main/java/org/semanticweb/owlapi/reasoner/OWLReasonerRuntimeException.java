package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 */
public abstract class OWLReasonerRuntimeException extends OWLRuntimeException {

    public OWLReasonerRuntimeException() {
    }

    public OWLReasonerRuntimeException(Throwable cause) {
        super(cause);
    }

    public OWLReasonerRuntimeException(String message) {
        super(message);
    }

    public OWLReasonerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
