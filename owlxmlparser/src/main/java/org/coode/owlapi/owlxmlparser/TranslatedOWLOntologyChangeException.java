package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class TranslatedOWLOntologyChangeException extends SAXException {

    public TranslatedOWLOntologyChangeException(OWLOntologyChangeException e) {
        super(e);
    }

    @Override
    public OWLOntologyChangeException getCause() {
        return (OWLOntologyChangeException) super.getCause();
    }
}
