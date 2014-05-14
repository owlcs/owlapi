/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class N3OntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = 40000L;

    /**
     * RDF format for {@link RDFFormat#N3} documents.
     */
    public N3OntologyFormat() {
        super(RDFFormat.N3);
    }
}
