package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

/**
 * Created by ses on 9/30/14.
 */
public interface RioRDFDocumentFormat extends RDFDocumentFormat {
    public RDFFormat getRioFormat();
}
