package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

interface LiteralTripleHandler {

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     */
    void handleTriple(IRI subject, IRI predicate, OWLLiteral object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle
     */
    boolean canHandle(IRI subject, IRI predicate, OWLLiteral object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle streaming
     */
    boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object);
}
