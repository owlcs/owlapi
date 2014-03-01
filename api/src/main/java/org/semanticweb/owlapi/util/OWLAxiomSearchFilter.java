package org.semanticweb.owlapi.util;

import java.io.Serializable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @param <O>
 *        axiom type
 * @param <K>
 *        key type
 */
public interface OWLAxiomSearchFilter<O extends OWLAxiom, K> extends
        Serializable {

    /** @return axiom type */
    AxiomType<O> getAxiomType();

    /**
     * @param axiom
     *        axiom to check
     * @param key
     *        key
     * @return true if passed
     */
    boolean pass(O axiom, K key);
}
