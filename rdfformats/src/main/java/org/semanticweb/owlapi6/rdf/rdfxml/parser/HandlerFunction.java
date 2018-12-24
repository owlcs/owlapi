package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLLiteral;

@FunctionalInterface
interface HandlerFunction<T> {

    public static HandlerFunction<IRI> NO_OP_IRI = (c, s, p, o) -> false;
    public static HandlerFunction<OWLLiteral> NO_OP_LIT = (c, s, p, o) -> false;

    boolean handle(OWLRDFConsumer c, IRI s, IRI p, T o);
}
