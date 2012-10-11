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
public class RdfJsonOntologyFormatFactory extends AbstractRioRDFOntologyFormatFactory implements
        OWLOntologyFormatFactory
{
    public RdfJsonOntologyFormatFactory()
    {
    }
    
    @Override
    public RioRDFOntologyFormat getNewFormat()
    {
        return new RdfJsonOntologyFormat();
    }
    
}
