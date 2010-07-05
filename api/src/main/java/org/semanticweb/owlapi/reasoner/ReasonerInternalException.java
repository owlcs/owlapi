package org.semanticweb.owlapi.reasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05-Jul-2010
 * <p>
 * Specifies that a some internal error occurred during reasoning.  Each reasoner implementation will specify
 * the cause of the error in the message.
 * <p>
 * This exception is only thrown when an internal error (due to errors in the reasoner code) has occurred and the reasoner
 * cannot recover silently from the error.  If the reasoner can recover silently this exception will not be thrown.
 * <p>
 * Clients should dispose of the reasoner when an internal reasoner exception is thrown because the error is
 * unrecoverable and the internal state of the reasoner may be corrupt.
 */
public class ReasonerInternalException extends OWLReasonerRuntimeException {

    public ReasonerInternalException(Throwable cause) {
        super(cause);
    }

    public ReasonerInternalException(String message) {
        super(message);
    }

    public ReasonerInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
