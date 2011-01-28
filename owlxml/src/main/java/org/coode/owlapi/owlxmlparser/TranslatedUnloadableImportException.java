package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class TranslatedUnloadableImportException extends SAXException {

    private UnloadableImportException unloadableImportException;

    public TranslatedUnloadableImportException(UnloadableImportException e) {
        super(e);
        this.unloadableImportException = e;
    }

    public UnloadableImportException getUnloadableImportException() {
        return unloadableImportException;
    }
}
