package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

@FunctionalInterface
interface HandlerFunction<T> {

    public static HandlerFunction<IRI> NO_OP_IRI = (c, s, p, o) -> false;
    public static HandlerFunction<OWLLiteral> NO_OP_LIT = (c, s, p, o) -> false;

    boolean handle(OWLRDFConsumer c, IRI s, IRI p, T o);
}
