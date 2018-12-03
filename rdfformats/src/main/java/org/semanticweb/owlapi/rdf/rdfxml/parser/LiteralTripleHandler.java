package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

interface LiteralTripleHandler {

    boolean handleTriple(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);

    boolean canHandle(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);

    boolean canHandleStreaming(OWLRDFConsumer c, IRI subject, IRI predicate, OWLLiteral object);
}
