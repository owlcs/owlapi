/**
 * 
 */
package com.github.ansell.abstractserviceloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract service loader based on the model that there is a single key for
 * each service, but each key may be provided in more than one service. Services
 * are automatically loaded in the constructors, and additional services can be
 * added programmatically.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractServiceLoader<K, S> implements Serializable {

    private static final long serialVersionUID = -7240751520278670861L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final transient ConcurrentMap<K, Collection<S>> services = new ConcurrentHashMap<K, Collection<S>>();

    /**
     * Loads instances of the currently available services into a cache in
     * memory. Equivalent to AbstractServiceLoader(serviceClass,
     * Thread.currentThread().getContextClassLoader())
     * 
     * @param serviceClass
     *        The service class to search for.
     */
    protected AbstractServiceLoader(final Class<S> serviceClass) {
        this(serviceClass, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Loads instances of the currently available services into a cache in
     * memory using the given ClassLoader.
     * 
     * @param serviceClass
     *        The service class to search for.
     * @param classLoader
     *        The ClassLoader to use when searching for services.
     */
    protected AbstractServiceLoader(final Class<S> serviceClass,
            final ClassLoader classLoader) {
        // Java6 version:
        final ServiceLoader<S> serviceLoader = java.util.ServiceLoader.load(
                serviceClass, classLoader);
        final Iterator<S> servicesIterator = serviceLoader.iterator();
        // Java5 version:
        // Iterator<S> services =
        // javax.imageio.spi.ServiceRegistry.lookupProviders(serviceClass,
        // classLoader);
        // Loop through this way so we can catch all errors for each iteration
        // and only discard plugins that are invalid
        while (true) {
            try {
                if (!servicesIterator.hasNext()) {
                    if (this.log.isInfoEnabled()) {
                        this.log.info(
                                "Completed loading {} services for class {}",
                                this.services.size(), serviceClass.getName());
                    }
                    break;
                }
                final S service = servicesIterator.next();
                this.add(service);
            }
            // This error is thrown when .hasNext() or .next() encounter an
            // invalid service
            // description file or the class is not a valid service
            catch (final ServiceConfigurationError e) {
                this.log.error("Failed to instantiate service", e);
            }
        }
    }

    public void add(final S service) {
        if (service == null) {
            throw new NullPointerException("Service must not be null");
        }
        final K key = this.getKey(service);
        if (key == null) {
            throw new NullPointerException("Key for service must not be null");
        }
        Collection<S> set = this.services.get(key);
        if (set == null) {
            set = Collections.newSetFromMap(new ConcurrentHashMap<S, Boolean>(
                    2, 0.75f, 2));
            final Collection<S> existingSet = this.services.putIfAbsent(key,
                    set);
            if (existingSet != null) {
                set = existingSet;
                this.log.info(
                        "Found duplicate service with key: {} service class: {}",
                        key, service.getClass().getName());
            }
        }
        set.add(service);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Added service with key: {}", key);
        }
    }

    public void clear() {
        this.services.clear();
    }

    public Collection<S> get(final K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (this.services.containsKey(key)) {
            return this.services.get(key);
        } else {
            return Collections.emptyList();
        }
    }

    public Collection<S> getAll() {
        final Collection<S> result = new ArrayList<S>();
        for (final Collection<S> nextServices : new ArrayList<Collection<S>>(
                this.services.values())) {
            result.addAll(nextServices);
        }
        return result;
    }

    /**
     * This method needs to be overriden to provide a unique key, based on the
     * generic key type (K) to use as the identifier for the given service.
     * 
     * @param service
     *        A service to return a key for
     * @return The distinct key for the given service.
     * @since 0.2
     */
    public abstract K getKey(S service);

    /**
     * Gets the keys for the registered services.
     * 
     * @return An unmodifiable view on the keys for the registered services.
     */
    public Set<K> getKeys() {
        return Collections.unmodifiableSet(this.services.keySet());
    }

    /**
     * Determines whether there are any current services that have the given
     * key.
     * 
     * @param key
     *        The key to search for.
     * @return True if there is at least one registered service with the given
     *         key, and false otherwise.
     */
    public boolean has(final K key) {
        final Collection<S> collection = this.services.get(key);
        return collection != null && !collection.isEmpty();
    }

    public synchronized void remove(final S service) {
        for (final K nextKey : this.getKeys()) {
            final Collection<S> nextServices = this.get(nextKey);
            if (nextServices != null && nextServices.contains(service)) {
                this.services.get(nextKey).remove(service);
                if (this.services.get(nextKey).isEmpty()) {
                    this.services.remove(nextKey);
                }
            }
        }
    }

    public synchronized void removeByKey(final K key) {
        if (this.services.containsKey(key)) {
            this.services.remove(key);
        }
    }
}
