/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.kohsuke.MetaInfServices;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyFormatFactory.class)
public class TurtleOntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements
        RioRDFOntologyFormatFactory {

    public TurtleOntologyFormatFactory() {
        super();
    }

    @Override
    public RioRDFOntologyFormat createFormat() {
        return new RioTurtleOntologyFormat();
    }
}
