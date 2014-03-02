package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;

interface ResourceTripleHandler {

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     */
    void handleTriple(IRI subject, IRI predicate, IRI object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle streaming
     */
    boolean canHandleStreaming(IRI subject, IRI predicate, IRI object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle
     */
    boolean canHandle(IRI subject, IRI predicate, IRI object);
}
