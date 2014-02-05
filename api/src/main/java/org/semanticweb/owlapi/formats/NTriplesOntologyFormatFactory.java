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
public class NTriplesOntologyFormatFactory extends AbstractRioRDFOntologyFormatFactory implements RioRDFOntologyFormatFactory
{
    public NTriplesOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public RioRDFOntologyFormat getNewFormat()
    {
        return new NTriplesOntologyFormat();
    }

}
