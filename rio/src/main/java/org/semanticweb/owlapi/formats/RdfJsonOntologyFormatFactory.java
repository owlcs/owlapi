/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.kohsuke.MetaInfServices;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(OWLOntologyFormatFactory.class)
public class RdfJsonOntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements OWLOntologyFormatFactory {

    public RdfJsonOntologyFormatFactory() {}

    @Override
    public RioRDFOntologyFormat createFormat() {
        return new RdfJsonOntologyFormat();
    }
}
