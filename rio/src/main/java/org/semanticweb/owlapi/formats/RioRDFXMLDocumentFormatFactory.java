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
public class RioRDFXMLDocumentFormatFactory extends
        AbstractRioRDFDocumentFormatFactory {

    private static final long serialVersionUID = 40000L;

    /** default constructor */
    public RioRDFXMLDocumentFormatFactory() {
        super(RDFFormat.RDFXML);
    }

    @Nonnull
    @Override
    public RioRDFXMLDocumentFormat createFormat() {
        return new RioRDFXMLDocumentFormat();
    }
}
