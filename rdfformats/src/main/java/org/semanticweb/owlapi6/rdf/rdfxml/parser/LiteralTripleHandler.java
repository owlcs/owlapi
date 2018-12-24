package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLLiteral;

interface LiteralTripleHandler {

    boolean handleTriple(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);

    boolean canHandle(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);

    boolean canHandleStreaming(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);
}
