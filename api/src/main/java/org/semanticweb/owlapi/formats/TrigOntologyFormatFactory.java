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
public class TrigOntologyFormatFactory extends AbstractRioRDFOntologyFormatFactory implements RioRDFOntologyFormatFactory
{
    public TrigOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public RioRDFOntologyFormat getNewFormat()
    {
        return new TrigOntologyFormat();
    }

}
