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
public class KRSS2OntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements OWLOntologyFormatFactory
{
    public KRSS2OntologyFormatFactory()
    {
        super();
    }
    
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        return new KRSS2OntologyFormat();
    }
    
    @Override
    public String getKey()
    {
        return new KRSS2OntologyFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return Collections.emptyList();
    }
    
}
