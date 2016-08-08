package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.HasCardinality;

/**
 * An object with a settable cardinality attribute.
 * 
 * @author ignazio
 * @param <B>
 *        returned type for builders
 */
public interface SettableCardinality<B> extends HasCardinality {

    /**
     * @param cardinality
     *        cardinality to set
     * @return builder
     */
    B withCardinality(int cardinality);
}
