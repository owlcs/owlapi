/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class NQuadsDocumentFormat extends RioRDFNonPrefixDocumentFormat {

    /**
     * RDF format for {@link RDFFormat#NQUADS} documents.
     */
    public NQuadsDocumentFormat() {
        super(RDFFormat.NQUADS);
    }
}
