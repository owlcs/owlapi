/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class NQuadsDocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    /** Default constructor. */
    public NQuadsDocumentFormatFactory() {
        super(RDFFormat.NQUADS);
    }

    @Override
    public RioRDFDocumentFormat createFormat() {
        return new NQuadsDocumentFormat();
    }
}
