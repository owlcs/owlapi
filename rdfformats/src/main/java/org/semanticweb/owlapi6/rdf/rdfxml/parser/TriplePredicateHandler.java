package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;

interface TriplePredicateHandler extends ResourceTripleHandler {

    IRI getPredicateIRI();
}
