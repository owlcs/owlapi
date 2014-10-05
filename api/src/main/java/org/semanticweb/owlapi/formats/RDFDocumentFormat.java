package org.semanticweb.owlapi.formats;

import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

/**
 * An RDF OWL Document Format.
 */
public interface RDFDocumentFormat extends OWLDocumentFormat {

    /**
     * @param error
     *        error to add to the error set
     */
    public void addError(RDFResourceParseError error);
}
