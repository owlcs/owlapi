package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;

interface BuiltInTypeHandler extends TriplePredicateHandler {

    IRI getTypeIRI();
}
