package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;

public interface OWLAxiomSearchFilter<O extends OWLAxiom, K> {
	AxiomType<O> getAxiomType();

	boolean pass(O axiom, K key);
}
