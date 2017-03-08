package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.CollectionFactory.createLinkedSet;
import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TripleMapCollection<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(TripleMapCollection.class);
    private Map<IRI, Map<IRI, Collection<T>>> map = CollectionFactory.createMap();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    /**
     * Dump remaining triples.
     */
    public void dumpRemainingTriples() {
        // if info logging is disabled or all collections are empty, do not
        // output anything
        if (LOGGER.isInfoEnabled() && size() > 0) {
            map.forEach((p, m) -> m.forEach(
                (s, o) -> LOGGER.info("Unparsed triple: {} -> {} -> {}", s, p, o)));
        }
    }

    public Stream<IRI> keys(IRI subject) {
        Map<IRI, Collection<T>> m = map.get(subject);
        if (m != null) {
            return asList(m.keySet().stream()).stream();
        }
        return Stream.empty();
    }

    @Nullable
    public T get(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, Collection<T>> predObjMap = map.get(subject);
        if (predObjMap != null) {
            Collection<T> objects = predObjMap.get(predicate);
            if (objects != null && !objects.isEmpty()) {
                T object = objects.iterator().next();
                if (consume) {
                    objects.remove(object);
                }
                if (objects.isEmpty()) {
                    predObjMap.remove(predicate);
                    if (predObjMap.isEmpty()) {
                        map.remove(subject);
                    }
                }
                return object;
            }
        }
        return null;
    }

    public Stream<T> getAll(IRI subject, IRI predicate, boolean b) {
        Map<IRI, Collection<T>> predObjMap = map.get(subject);
        if (predObjMap != null) {
            Collection<T> objects = predObjMap.get(predicate);
            if (objects != null) {
                return objects.stream();
            }
        }
        return Stream.empty();
    }

    public boolean consume(IRI subject, IRI predicate, T object) {
        Map<IRI, Collection<T>> predObjMap = map.get(subject);
        if (predObjMap != null) {
            Collection<T> objects = predObjMap.get(predicate);
            if (objects != null && objects.contains(object)) {
                objects.remove(object);
                if (objects.isEmpty()) {
                    predObjMap.remove(predicate);
                    if (predObjMap.isEmpty()) {
                        map.remove(subject);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean contains(IRI subject, IRI predicate) {
        Map<IRI, Collection<T>> resPredObjMap = map.get(subject);
        return resPredObjMap != null && resPredObjMap.containsKey(predicate);
    }

    public boolean contains(IRI subject, IRI predicate, T value) {
        Map<IRI, Collection<T>> resPredObjMap = map.get(subject);
        if (resPredObjMap != null) {
            Collection<T> collection = resPredObjMap.get(predicate);
            if (collection != null) {
                return collection.contains(value);
            }
        }
        return false;
    }

    public boolean add(IRI subject, IRI predicate, T object) {
        return map.computeIfAbsent(subject, x -> createMap())
            .computeIfAbsent(predicate, x -> createLinkedSet()).add(object);
    }

    public void iterate(TripleIterator<T> iterator) {
        new ArrayList<>(map.entrySet()).forEach(e -> new ArrayList<>(e.getValue().entrySet())
            .forEach(p -> new ArrayList<>(p.getValue()).forEach(object -> iterator
                .handleResourceTriple(e.getKey(), p.getKey(), object))));
    }
}
