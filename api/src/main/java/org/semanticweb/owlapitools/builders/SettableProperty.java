package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.HasProperty;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * An object with a settable property attribute.
 * 
 * @author ignazio
 * @param
 *        <P>
 *        type of property
 * @param <B>
 *        type of builder
 */
public interface SettableProperty<P extends OWLObject, B> extends HasProperty<P> {

    /**
     * @param p
     *        property to set
     * @return builder
     */
    B withProperty(P p);
}
