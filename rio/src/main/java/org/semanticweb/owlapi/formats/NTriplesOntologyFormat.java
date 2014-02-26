/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class NTriplesOntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = -2440786029449269231L;

    /**
     * RDF format for {@link RDFFormat#NTRIPLES} documents.
     */
    public NTriplesOntologyFormat() {
        super(RDFFormat.NTRIPLES);
    }
}
