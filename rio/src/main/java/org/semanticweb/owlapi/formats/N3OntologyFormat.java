/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.kohsuke.MetaInfServices;
import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(OWLOntologyFormat.class)
public class N3OntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = -2440786029449269231L;

    /**
     * RDF format for {@link RDFFormat#N3} documents.
     */
    public N3OntologyFormat() {
        super(RDFFormat.N3);
    }
}
