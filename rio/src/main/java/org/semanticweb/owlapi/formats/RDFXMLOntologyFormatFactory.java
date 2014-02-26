/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.kohsuke.MetaInfServices;
import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyFormatFactory.class)
public class RDFXMLOntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements
        RioRDFOntologyFormatFactory {

    public RDFXMLOntologyFormatFactory() {
        super();
    }

    @Override
    public RioRDFXMLOntologyFormat createFormat() {
        return new RioRDFXMLOntologyFormat(RDFFormat.RDFXML);
    }
}
