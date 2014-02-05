package org.semanticweb.owlapi.formats;

import java.util.List;

import org.openrdf.rio.RDFFormat;

/**
 * An abstract implementation of the RioRDFOntologyFormatFactory interface that uses the Rio RDFFormat class to provide information for common methods
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public abstract class AbstractRioRDFOntologyFormatFactory extends AbstractOWLOntologyFormatFactory implements RioRDFOntologyFormatFactory
{
    
    public AbstractRioRDFOntologyFormatFactory()
    {
        super();
    }

    @Override
    public String getKey()
    {
        return getNewFormat().getKey();
    }

    @Override
    public List<String> getMIMETypes()
    {
        return getRioFormat().getMIMETypes();
    }

    @Override
    public RDFFormat getRioFormat()
    {
        return ((RioRDFOntologyFormat)getNewFormat()).getRioFormat();
    }
    
}