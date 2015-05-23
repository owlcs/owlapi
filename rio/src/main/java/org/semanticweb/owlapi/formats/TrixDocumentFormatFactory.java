/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class TrixDocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    private static final long serialVersionUID = 40000L;

    /** Default constructor. */
    public TrixDocumentFormatFactory() {
        super(RDFFormat.TRIX);
    }

    @Override
    public RioRDFDocumentFormat createFormat() {
        return new TrixDocumentFormat();
    }
}
