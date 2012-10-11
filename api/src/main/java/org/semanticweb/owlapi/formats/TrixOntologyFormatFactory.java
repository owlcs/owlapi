/**
 * 
 */
package org.semanticweb.owlapi.formats;


import org.kohsuke.MetaInfServices;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
@MetaInfServices(OWLOntologyFormatFactory.class)
public class TrixOntologyFormatFactory extends AbstractRioRDFOntologyFormatFactory implements RioRDFOntologyFormatFactory
{
    public TrixOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public RioRDFOntologyFormat getNewFormat()
    {
        return new TrixOntologyFormat();
    }

    
}
