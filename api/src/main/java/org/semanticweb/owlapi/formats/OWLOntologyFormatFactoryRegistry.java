/**
 * 
 */
package org.semanticweb.owlapi.formats;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public class OWLOntologyFormatFactoryRegistry extends AbstractServiceLoader<String, OWLOntologyFormatFactory>
{
    private static final OWLOntologyFormatFactoryRegistry instance = new OWLOntologyFormatFactoryRegistry();
    
    /**
     * @return the instance
     */
    public static OWLOntologyFormatFactoryRegistry getInstance()
    {
        return OWLOntologyFormatFactoryRegistry.instance;
    }
    
    public OWLOntologyFormatFactoryRegistry()
    {
        super(OWLOntologyFormatFactory.class);
    }
    
    @Override
    public String getKey(final OWLOntologyFormatFactory service)
    {
        return service.getKey();
    }
    
    /**
     * Returns the first OWLOntologyFormatFactory matching the mime type
     * 
     * NOTE: The order in which the services are loaded an examined is not deterministic so this
     * method may return different results if the MIME-Type matches more than one
     * OWLOntologyFormatFactory. However, if the default MIME-Types are always unique, the correct
     * factory will always be chosen
     * 
     * @param mimeType A MIME type to return an {@link OWLOntologyFormatFactory} for
     * @return An {@link OWLOntologyFormatFactory} matching the given mime type or null if none were found.
     */
    public OWLOntologyFormatFactory getByMIMEType(String mimeType)
    {
        if(mimeType == null)
        {
            throw new IllegalArgumentException("MIME-Type cannot be null");
        }
        
        for(OWLOntologyFormatFactory nextFactory : this.getAll())
        {
            if(mimeType.equals(nextFactory.getDefaultMIMEType()))
            {
                return nextFactory;
            }
        }
        
        for(OWLOntologyFormatFactory nextFactory : this.getAll())
        {
            if(nextFactory.getMIMETypes().contains((mimeType)))
            {
                return nextFactory;
            }
        }
        
        return null;
    }
    
}
