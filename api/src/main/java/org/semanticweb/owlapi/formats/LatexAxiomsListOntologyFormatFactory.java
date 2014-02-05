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
public class LatexAxiomsListOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public LatexAxiomsListOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new LatexAxiomsListOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new LatexAxiomsListOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Arrays.asList("text/x-latex", "application/x-latex", "application/x-tex");
    }
    
}
