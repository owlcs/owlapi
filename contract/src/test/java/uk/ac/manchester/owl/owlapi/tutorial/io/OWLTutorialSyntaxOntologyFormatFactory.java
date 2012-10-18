/**
 * 
 */
package uk.ac.manchester.owl.owlapi.tutorial.io;

import java.util.List;

import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public class OWLTutorialSyntaxOntologyFormatFactory implements OWLOntologyFormatFactory
{
    
    /**
     * 
     */
    public OWLTutorialSyntaxOntologyFormatFactory()
    {
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#getNewFormat()
     */
    @Override
    public OWLOntologyFormat getNewFormat()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#getKey()
     */
    @Override
    public String getKey()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#getDefaultMIMEType()
     */
    @Override
    public String getDefaultMIMEType()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#getMIMETypes()
     */
    @Override
    public List<String> getMIMETypes()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#handlesMimeType(java.lang.String)
     */
    @Override
    public boolean handlesMimeType(String mimeType)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.formats.OWLOntologyFormatFactory#isTextual()
     */
    @Override
    public boolean isTextual()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
}
