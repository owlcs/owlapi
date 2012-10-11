/**
 * 
 */
package org.semanticweb.owlapi.formats;

import java.util.Collections;
import java.util.List;

import org.kohsuke.MetaInfServices;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
@MetaInfServices(org.semanticweb.owlapi.formats.OWLOntologyFormatFactory.class)
public class KRSSOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public KRSSOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new KRSSOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new KRSSOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Collections.emptyList();
    }
    
}
