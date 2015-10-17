/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioRDFXMLDocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    /** Default constructor. */
    public RioRDFXMLDocumentFormatFactory() {
        super(RDFFormat.RDFXML);
    }

    @Override
    public RioRDFXMLDocumentFormat createFormat() {
        return new RioRDFXMLDocumentFormat();
    }
}
