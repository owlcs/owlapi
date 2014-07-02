/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class NQuadsDocumentFormatFactory extends
        AbstractRioRDFDocumentFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public RioRDFDocumentFormat createFormat() {
        return new NQuadsDocumentFormat();
    }
}
