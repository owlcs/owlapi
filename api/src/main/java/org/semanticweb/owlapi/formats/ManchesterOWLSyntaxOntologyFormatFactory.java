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
public class ManchesterOWLSyntaxOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public ManchesterOWLSyntaxOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new ManchesterOWLSyntaxOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new ManchesterOWLSyntaxOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Arrays.asList("text/owl-manchester");
    }
    
}
