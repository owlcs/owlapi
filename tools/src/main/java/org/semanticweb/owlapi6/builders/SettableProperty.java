package org.semanticweb.owlapi6.builders;

import org.semanticweb.owlapi6.model.HasProperty;
import org.semanticweb.owlapi6.model.OWLObject;

/**
 * An object with a settable property attribute.
 *
 * @param <P> type of property
 * @param <B> type of builder
 * @author ignazio
 */
public interface SettableProperty<P extends OWLObject, B> extends HasProperty<P> {

    /**
     * @param p property to set
     * @return builder
     */
    B withProperty(P p);
}
