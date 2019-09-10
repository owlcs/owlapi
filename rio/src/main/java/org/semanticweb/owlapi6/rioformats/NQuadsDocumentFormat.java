/**
 *
 */
package org.semanticweb.owlapi6.rioformats;

import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class NQuadsDocumentFormat extends RioRDFDocumentFormatImpl {

    /**
     * RDF format for {@link RDFFormat#NQUADS} documents.
     */
    public NQuadsDocumentFormat() {
        super(RDFFormat.NQUADS);
    }

    @Override
    public boolean hasPrefixes() {
        return false;
    }

    @Override
    public boolean supportsRelativeIRIs() {
        return false;
    }
}
