package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;

@FunctionalInterface
interface TypeMatcher {

    boolean isTypeStrict(IRI node);
}
