package org.semanticweb.owlapi.util;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;

@SuppressWarnings("javadoc")
public interface OWLAxiomSearchFilter<O extends OWLAxiom, K> extends Serializable {
    @Nonnull
    AxiomType<O> getAxiomType();

    boolean pass(@Nonnull O axiom, @Nonnull K key);
}
