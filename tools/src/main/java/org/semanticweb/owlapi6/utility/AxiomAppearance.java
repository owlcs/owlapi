package org.semanticweb.owlapi6.utility;

import org.semanticweb.owlapi6.model.OWLAxiom;

/**
 * An interface for checking if an axiom appears as annotated source multiple times. This requires
 * an id to be emitted.
 */
@FunctionalInterface
public interface AxiomAppearance {

    /**
     * @param ax an axiom
     * @return true if ax appears more than once as annotated source.
     */
    boolean appearsMultipleTimes(OWLAxiom ax);
}
