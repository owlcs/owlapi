package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.CollectionFactory.createList;
import static org.semanticweb.owlapi.util.CollectionFactory.createMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;

class MultiMap<K, V> {

    private Map<K, Collection<V>> map;
    private Function<K, Collection<V>> creator;

    public MultiMap(Function<K, Collection<V>> creator) {
        this.creator = creator;
        map = createMap();
    }

    Collection<V> get(@Nullable K k) {
        return k == null ? Collections.emptyList() : map.getOrDefault(k, Collections.emptyList());
    }

    boolean put(K k, V v) {
        return map.computeIfAbsent(k, creator).add(v);
    }

    boolean put(K k, Collection<V> v) {
        return map.computeIfAbsent(k, creator).addAll(v);
    }

    <T> Stream<T> allValues(Function<V, T> f) {
        return map.values().stream().flatMap(x -> x.stream()).map(f).distinct();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}


class AnonMap extends MultiMap<IRI, IRI> {

    public AnonMap() {
        super(x -> createList());
    }

    /**
     * Records an annotation on an anonymous node (either an annotation on an annotation, or an
     * annotation on an axiom).
     *
     * @param subject The subject that the annotation annotates
     * @param annotation The annotation
     * @return true if added
     */
    protected boolean addAnnotatedSource(IRI subject, IRI annotation) {
        return put(subject, annotation);
    }

    /**
     * Gets the main nodes of annotations that annotated the specified source.
     *
     * @param source The source (axiom or annotation main node)
     * @return The set of main nodes that annotate the specified source
     */
    protected Collection<IRI> getAnnotatedSource(IRI source) {
        return get(source);
    }

    protected void consumeAnnotatedSource(IRI source, Consumer<IRI> dest) {
        get(source).forEach(dest);
    }

    @Nullable
    protected IRI getAnnotatedSource(IRI source, Predicate<IRI> p) {
        return get(source).stream().filter(p).findAny().orElse(null);
    }
}
