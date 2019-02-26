/**
 *
 */
package org.semanticweb.owlapi6.rioformats;

import org.eclipse.rdf4j.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class N3DocumentFormatFactory extends AbstractRioRDFDocumentFormatFactory {

    /**
     * Default constructor.
     */
    public N3DocumentFormatFactory() {
        super(RDFFormat.N3, new N3DocumentFormat());
    }
}
