/**
 *
 */
package org.semanticweb.owlapi6.rioformats;

import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class NQuadsDocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    /**
     * Default constructor.
     */
    public NQuadsDocumentFormatFactory() {
        super(RDFFormat.NQUADS, new NQuadsDocumentFormat());
    }
}
