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

    private static final long serialVersionUID = 40000L;

    /**
     * RDF format for {@link RDFFormat#NQUADS} documents.
     */
    public NQuadsDocumentFormat() {
        super(RDFFormat.NQUADS);
    }
}
