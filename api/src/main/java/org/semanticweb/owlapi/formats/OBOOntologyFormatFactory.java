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
public class OBOOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public OBOOntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new OBOOntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new OBOOntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Collections.emptyList();
    }
    
}
