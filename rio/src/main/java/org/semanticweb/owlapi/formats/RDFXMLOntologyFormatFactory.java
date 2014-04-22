/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

import org.kohsuke.MetaInfServices;
import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyFormatFactory.class)
public class RDFXMLOntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements
        RioRDFOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public RioRDFXMLOntologyFormat createFormat() {
        return new RioRDFXMLOntologyFormat(RDFFormat.RDFXML);
    }
}
