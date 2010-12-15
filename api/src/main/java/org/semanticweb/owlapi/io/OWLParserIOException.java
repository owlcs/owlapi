package org.semanticweb.owlapi.io;

import java.io.IOException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 * Describes a parse exception which was caused by an <code>IOException</code>
 */
public class OWLParserIOException extends OWLParserException {

    public OWLParserIOException(IOException cause) {
        super(cause);
    }


    /**
     * Gets the cause of this exception which will be
     * an <code>IOException</code>
     */
    @Override
	public IOException getCause() {
        return (IOException) super.getCause();
    }


    @Override
	public String getMessage() {
        return getCause().getMessage();
    }
}
