package org.semanticweb.owlapi6.rioformats;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.semanticweb.owlapi6.formats.RDFDocumentFormat;

/**
 * Created by ses on 9/30/14.
 */
public interface RioRDFDocumentFormat extends RDFDocumentFormat {

    /**
     * @return Rio format for this format
     */
    RDFFormat getRioFormat();
}
