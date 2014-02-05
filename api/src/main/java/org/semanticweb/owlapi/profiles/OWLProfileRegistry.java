/**
 * 
 */
package org.semanticweb.owlapi.profiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public class OWLProfileRegistry extends AbstractServiceLoader<IRI, OWLProfile>
{
    private static final OWLProfileRegistry instance = new OWLProfileRegistry();
    
    /**
     * @return A static, pre-initialized, instance of this registry.
     */
    public static OWLProfileRegistry getInstance()
    {
        return OWLProfileRegistry.instance;
    }
    
    public OWLProfileRegistry()
    {
        super(OWLProfile.class);
    }
    
    /**
     * clear all registered profiles
     */
    public void clearProfileFactories()
    {
        this.clear();
    }
    
    @Override
    public IRI getKey(final OWLProfile service)
    {
        return service.getIRI();
    }
    
    public OWLProfile getProfile(IRI key)
    {
        Collection<OWLProfile> collection = this.get(key);
        
        if(collection.isEmpty())
        {
            return null;
        }
        else
        {
            return collection.iterator().next();
        }
    }
    
    /**
     * @return the list of profiles - changes will not be backed by the registry
     */
    public List<OWLProfile> getProfiles()
    {
        return new ArrayList<OWLProfile>(this.getAll());
    }
    
    /**
     * @param profile
     *            the profile to register
     */
    public void registerProfileFactory(final OWLProfile profile)
    {
        this.add(profile);
    }
    
    /**
     * @param profile
     *            the profile to remove
     */
    public void unregisterProfileFactory(final OWLProfile profile)
    {
        this.remove(profile);
    }
    
}
