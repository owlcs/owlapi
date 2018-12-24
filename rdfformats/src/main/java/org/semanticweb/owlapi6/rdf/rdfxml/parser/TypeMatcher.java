package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;

@FunctionalInterface
interface TypeMatcher {

    boolean isTypeStrict(IRI node);
}
