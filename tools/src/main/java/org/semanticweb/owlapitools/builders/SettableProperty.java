package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.HasProperty;
import org.semanticweb.owlapi.model.OWLObject;

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
