package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOME_VALUES_FROM;

import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TripleMap<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(TripleMap.class);
    private Map<IRI, Map<IRI, T>> map = CollectionFactory.createMap();

    public Object put(IRI iri) {
        return map.put(iri, createMap());
    }

    public void setupSinglePredicateMaps() {
        Stream.of(OWL_ON_PROPERTY, OWL_SOME_VALUES_FROM, OWL_ALL_VALUES_FROM, OWL_ON_CLASS,
            OWL_ON_DATA_RANGE).forEach(v -> put(v.getIRI()));
    }

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

    @Nullable
    public T get(IRI subject, IRI predicate, boolean consume) {
        Map<IRI, T> subjPredMap = map.get(predicate);
        if (subjPredMap != null) {
            T obj = subjPredMap.get(subject);
            if (obj != null) {
                if (consume) {
                    subjPredMap.remove(subject);
                }
            }
            return obj;
        }
        return null;
    }

    public boolean consume(IRI subject, IRI predicate, T object) {
        Map<IRI, T> subjPredMap = map.get(predicate);
        if (subjPredMap != null) {
            T obj = subjPredMap.get(subject);
            if (object.equals(obj)) {
                subjPredMap.remove(subject);
                return true;
            }
        }
        return false;
    }

    public boolean contains(IRI subject, IRI predicate) {
        Map<IRI, T> litPredMap = map.get(predicate);
        return litPredMap != null && litPredMap.containsKey(subject);
    }

    public boolean add(IRI subject, IRI predicate, T object) {
        Map<IRI, T> subjObjMap = map.get(predicate);
        if (subjObjMap != null && !object.equals(subjObjMap.get(subject))) {
            subjObjMap.put(subject, object);
            return true;
        }
        return false;
    }
}
