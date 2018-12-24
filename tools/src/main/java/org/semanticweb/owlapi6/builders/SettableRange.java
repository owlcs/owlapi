package org.semanticweb.owlapi6.builders;

import org.semanticweb.owlapi6.model.HasRange;
import org.semanticweb.owlapi6.model.OWLObject;

/**
 * An interface for objects which have a settable range attribute.
 *
 * @param <B> type of builder
 * @param <R> type of range
 * @author ignazio
 */
public interface SettableRange<R extends OWLObject, B> extends HasRange<R> {

    /**
     * @param range range to set
     * @return builder
     */
    B withRange(R range);
}
