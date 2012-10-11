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
public class OWLFunctionalSyntaxOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public OWLFunctionalSyntaxOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new OWLFunctionalSyntaxOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new OWLFunctionalSyntaxOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Arrays.asList("text/owl-functional");
    }
    
}
