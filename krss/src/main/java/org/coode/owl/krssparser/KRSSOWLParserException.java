package org.coode.owl.krssparser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class KRSSOWLParserException extends OWLParserException {

    public KRSSOWLParserException(ParseException e) {
        super(e.getMessage(), e.currentToken.beginLine, e.currentToken.beginColumn);
    }


    public KRSSOWLParserException(Throwable cause) {
        super(cause);
    }
}
