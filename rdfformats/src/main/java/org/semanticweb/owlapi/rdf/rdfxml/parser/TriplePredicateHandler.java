package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;

interface TriplePredicateHandler extends ResourceTripleHandler {

    IRI getPredicateIRI();
}
