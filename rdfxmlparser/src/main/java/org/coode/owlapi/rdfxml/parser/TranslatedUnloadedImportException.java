package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class TranslatedUnloadedImportException extends SAXException {

    public TranslatedUnloadedImportException(UnloadableImportException e) {
        super(e);
    }

    @Override
    public UnloadableImportException getCause() {
        return (UnloadableImportException) super.getCause();
    }
}
