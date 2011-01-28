package org.coode.owlapi.latex;

import java.io.IOException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
public class LatexRendererIOException extends LatexRendererException {

    public LatexRendererIOException(IOException cause) {
        super(cause);
    }
}
