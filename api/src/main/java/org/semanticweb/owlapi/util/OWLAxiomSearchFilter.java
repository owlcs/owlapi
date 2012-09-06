package org.semanticweb.owlapi.util;

import java.io.Serializable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;

@SuppressWarnings("javadoc")
public interface OWLAxiomSearchFilter<O extends OWLAxiom, K> extends Serializable{
    AxiomType<O> getAxiomType();

    boolean pass(O axiom, K key);
}
