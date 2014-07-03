/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class TrixDocumentFormatFactory extends
        AbstractRioRDFDocumentFormatFactory {

    private static final long serialVersionUID = 40000L;

    /** default constructor */
    public TrixDocumentFormatFactory() {
        super(RDFFormat.TRIX);
    }

    @Nonnull
    @Override
    public RioRDFDocumentFormat createFormat() {
        return new TrixDocumentFormat();
    }
}
