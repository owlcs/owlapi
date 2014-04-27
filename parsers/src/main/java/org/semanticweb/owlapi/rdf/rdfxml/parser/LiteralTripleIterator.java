package org.semanticweb.owlapi.rdf.rdfxml.parser;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

interface LiteralTripleIterator {

    /**
     * Handle literal triple.
     * 
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    void handleLiteralTriple(@Nonnull IRI subject, @Nonnull IRI predicate,
            @Nonnull OWLLiteral object);
}
