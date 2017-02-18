package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;

interface ResourceTripleHandler {

    boolean handleTriple(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);

    boolean canHandleStreaming(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);

    boolean canHandle(OWLRDFConsumer c, IRI subject, IRI predicate, IRI object);
    // { return false; }
}
