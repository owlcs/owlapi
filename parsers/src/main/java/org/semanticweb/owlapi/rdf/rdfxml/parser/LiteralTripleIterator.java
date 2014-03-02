package org.semanticweb.owlapi.rdf.rdfxml.parser;

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
    void handleLiteralTriple(IRI subject, IRI predicate, OWLLiteral object);
}
