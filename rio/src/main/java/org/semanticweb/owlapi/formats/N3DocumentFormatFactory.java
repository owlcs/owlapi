/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class N3DocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    /** Default constructor. */
    public N3DocumentFormatFactory() {
        super(RDFFormat.N3);
    }

    @Override
    public RioRDFDocumentFormat createFormat() {
        return new N3DocumentFormat();
    }
}
