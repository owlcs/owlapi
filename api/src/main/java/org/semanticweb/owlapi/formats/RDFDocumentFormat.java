package org.semanticweb.owlapi.formats;

import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

/**
 * An RDF OWL Document Format.
 */
public interface RDFDocumentFormat extends OWLDocumentFormat {
    public void addError(RDFResourceParseError error);
}
