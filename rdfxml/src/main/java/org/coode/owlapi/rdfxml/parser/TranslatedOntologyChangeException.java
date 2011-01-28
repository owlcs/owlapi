package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class TranslatedOntologyChangeException extends SAXException {

    public TranslatedOntologyChangeException(OWLOntologyChangeException e) {
        super(e);
    }

    @Override
    public OWLOntologyChangeException getCause() {
        return (OWLOntologyChangeException) super.getCause();
    }
}
