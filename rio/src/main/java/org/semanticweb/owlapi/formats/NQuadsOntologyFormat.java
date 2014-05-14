/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class NQuadsOntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = 40000L;

    /**
     * RDF format for {@link RDFFormat#NQUADS} documents.
     */
    public NQuadsOntologyFormat() {
        super(RDFFormat.NQUADS);
    }
}
