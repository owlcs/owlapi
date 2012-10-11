/**
 * 
 */
package org.semanticweb.owlapi.formats;

import java.util.Arrays;
import java.util.List;

import org.kohsuke.MetaInfServices;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
@MetaInfServices(org.semanticweb.owlapi.formats.OWLOntologyFormatFactory.class)
public class OWLXMLOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public OWLXMLOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new OWLXMLOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new OWLXMLOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Arrays.asList("application/owl+xml");
    }
    
}
