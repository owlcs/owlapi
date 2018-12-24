package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;

interface ResourceTripleHandler {

    boolean handleTriple(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);

    boolean canHandleStreaming(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);

    boolean canHandle(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);
    // { return false; }
}
