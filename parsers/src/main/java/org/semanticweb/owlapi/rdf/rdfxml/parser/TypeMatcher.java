package org.semanticweb.owlapi.rdf.rdfxml.parser;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

interface TypeMatcher {

    boolean isTypeStrict(@Nonnull IRI node);
}
