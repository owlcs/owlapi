/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RioRDFXMLDocumentFormatFactory extends
        AbstractRioRDFDocumentFormatFactory implements
        RioRDFDocumentFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public RioRDFXMLDocumentFormat createFormat() {
        return new RioRDFXMLDocumentFormat();
    }
}
