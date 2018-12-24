package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;

@FunctionalInterface
interface TripleIterator<T> {

    /**
     * Handle resource triple.
     *
     * @param subject the subject
     * @param predicate the predicate
     * @param object the object
     */
    void handleResourceTriple(IRI subject, IRI predicate, T object);
}
