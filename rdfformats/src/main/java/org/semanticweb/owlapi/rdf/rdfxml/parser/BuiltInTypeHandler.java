package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;

interface BuiltInTypeHandler extends TriplePredicateHandler {

    IRI getTypeIRI();
}
