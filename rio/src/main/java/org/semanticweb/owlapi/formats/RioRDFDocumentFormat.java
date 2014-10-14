package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * Created by ses on 9/30/14.
 */
public interface RioRDFDocumentFormat extends RDFDocumentFormat {

    /**
     * @return Rio RDF format
     */
    public RDFFormat getRioFormat();
}
