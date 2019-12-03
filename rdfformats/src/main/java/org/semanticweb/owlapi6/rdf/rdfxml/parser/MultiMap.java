package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import static org.semanticweb.owlapi6.utility.CollectionFactory.createMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;

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
        return map.values().stream().flatMap(Collection::stream).map(f).distinct();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
