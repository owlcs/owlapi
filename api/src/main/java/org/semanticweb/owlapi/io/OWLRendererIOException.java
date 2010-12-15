package org.semanticweb.owlapi.io;

import java.io.IOException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLRendererIOException extends OWLRendererException {

    public OWLRendererIOException(IOException cause) {
        super(cause);
    }
}
