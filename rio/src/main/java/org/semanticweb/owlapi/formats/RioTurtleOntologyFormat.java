package org.semanticweb.owlapi.formats;

import org.kohsuke.MetaInfServices;
import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(OWLOntologyFormat.class)
public class RioTurtleOntologyFormat extends
        org.semanticweb.owlapi.formats.RioRDFOntologyFormat {

    private static final long serialVersionUID = -5780245294872788586L;

    /**
     * RDF format for {@link RDFFormat#TURTLE} documents.
     */
    public RioTurtleOntologyFormat() {
        super(RDFFormat.TURTLE);
    }
}
