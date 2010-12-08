package org.semanticweb.owlapi.reasoner;


/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 */
public class IllegalConfigurationException extends OWLReasonerRuntimeException {


    private OWLReasonerConfiguration configuration;

    public IllegalConfigurationException(Throwable cause, OWLReasonerConfiguration configuration) {
        super(cause);
        this.configuration = configuration;
    }

    public IllegalConfigurationException(String message, OWLReasonerConfiguration configuration) {
        super(message);
        this.configuration = configuration;
    }

    public IllegalConfigurationException(String message, Throwable cause, OWLReasonerConfiguration configuration) {
        super(message, cause);
        this.configuration = configuration;
    }

    public OWLReasonerConfiguration getConfiguration() {
        return configuration;
    }
}
