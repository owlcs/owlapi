package org.semanticweb.owlapi.registries;

/**
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapitools.profiles.OWLProfile;
import org.semanticweb.owlapitools.profiles.Profiles;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * Provides a registry for reasoners so they can be accessed using Service
 * Provider Interface methods.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLReasonerFactoryRegistry extends
        AbstractServiceLoader<String, OWLReasonerFactory> {

    private static final OWLReasonerFactoryRegistry instance = new OWLReasonerFactoryRegistry();

    public OWLReasonerFactoryRegistry() {
        super(OWLReasonerFactory.class);
    }

    /**
     * NOTE: This singleton instance is not mandatory, and users are encouraged
     * to create additional instances if they do not want to use all of the
     * reasoners available on the classpath for particular applications.
     * 
     * @return the reasoner factory registry
     */
    public static OWLReasonerFactoryRegistry getInstance() {
        return instance;
    }

    /**
     * clear all registered reasoner factories
     */
    public void clearReasonerFactories() {
        clear();
    }

    /**
     * @return the list of reasoners - changes will not be backed by the factory
     */
    public List<OWLReasonerFactory> getReasonerFactories() {
        return new ArrayList<OWLReasonerFactory>(getAll());
    }

    public OWLReasonerFactory getReasonerFactory(String key) {
        Collection<OWLReasonerFactory> collection = get(key);
        if (collection.isEmpty()) {
            return null;
        } else {
            return collection.iterator().next();
        }
    }

    /**
     * Returns all of the registered OWLReasonerFactory objects that support the
     * given OWLProfile.
     * 
     * @param profile
     *        The OWLProfile to support.
     * @return A collection of reasoner factories that can create reasoners
     *         which support the given profiles.
     */
    public Collection<OWLReasonerFactory> getReasonerFactoriesByProfile(
            OWLProfile profile) {
        Collection<OWLReasonerFactory> results = new HashSet<OWLReasonerFactory>();
        Profiles p = Profiles.valueForIRI(profile.getIRI());
        Collection<String> supportedFactories = null;
        if (p == null) {
            // unknown profile: return the most expressive profile with known
            // factories
            supportedFactories = Profiles.OWL2_FULL.supportingReasoners();
        } else {
            supportedFactories = p.supportingReasoners();
        }
        for (OWLReasonerFactory nextFactory : getAll()) {
            if (supportedFactories.contains(nextFactory.getClass().getName())) {
                results.add(nextFactory);
            }
        }
        return results;
    }

    /**
     * @param reasonerFactory
     *        the reasoner factory to register
     */
    public void registerReasonerFactory(OWLReasonerFactory reasonerFactory) {
        add(reasonerFactory);
    }

    /**
     * @param reasonerFactory
     *        the reasoner factory to remove
     */
    public void unregisterReasonerFactory(OWLReasonerFactory reasonerFactory) {
        remove(reasonerFactory);
    }

    @Override
    public String getKey(OWLReasonerFactory service) {
        return service.getReasonerName();
    }
}
