package org.semanticweb.owlapi.util;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;

/** @param <O>
 *            axiom type
 * @param <K>
 *            key type */
public interface OWLAxiomSearchFilter<O extends OWLAxiom, K> extends Serializable {
    /** @return axiom type */
    @Nonnull
    AxiomType<O> getAxiomType();

    /** @param axiom
     *            axiom to check
     * @param key
     *            key
     * @return true if passed */
    boolean pass(@Nonnull O axiom, @Nonnull K key);
}
