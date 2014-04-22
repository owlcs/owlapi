/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

import org.kohsuke.MetaInfServices;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@MetaInfServices(org.semanticweb.owlapi.model.OWLOntologyFormatFactory.class)
public class TurtleOntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements
        RioRDFOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public RioRDFOntologyFormat createFormat() {
        return new RioTurtleOntologyFormat();
    }
}
