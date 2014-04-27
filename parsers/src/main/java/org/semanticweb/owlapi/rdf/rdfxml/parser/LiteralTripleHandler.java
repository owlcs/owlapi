package org.semanticweb.owlapi.rdf.rdfxml.parser;

import javax.annotation.Nonnull;

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
    void handleTriple(@Nonnull IRI subject, @Nonnull IRI predicate,
            @Nonnull OWLLiteral object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle
     */
    boolean canHandle(@Nonnull IRI subject, @Nonnull IRI predicate,
            @Nonnull OWLLiteral object);

    /**
     * @param subject
     *        subject
     * @param predicate
     *        predicate
     * @param object
     *        object
     * @return true if can handle streaming
     */
    boolean canHandleStreaming(@Nonnull IRI subject, @Nonnull IRI predicate,
            @Nonnull OWLLiteral object);
}
