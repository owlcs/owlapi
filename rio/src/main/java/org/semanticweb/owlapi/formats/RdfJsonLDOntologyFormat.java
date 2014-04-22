/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RdfJsonLDOntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = -2440786029449269231L;

    /**
     * RDF format for {@link RDFFormat#JSONLD} documents.
     */
    public RdfJsonLDOntologyFormat() {
        super(RDFFormat.JSONLD);
    }
}
