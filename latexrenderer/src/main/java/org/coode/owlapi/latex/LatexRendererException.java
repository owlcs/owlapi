package org.coode.owlapi.latex;

import org.semanticweb.owlapi.io.OWLRendererException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
public abstract class LatexRendererException extends OWLRendererException {

    public LatexRendererException(Throwable cause) {
        super(cause);
    }


    public LatexRendererException(String message) {
        super(message);
    }


    public LatexRendererException(String message, Throwable cause) {
        super(message, cause);
    }
}
