package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.HasCardinality;

/**
 * An object with a settable cardinality attribute.
 *
 * @param <B> returned type for builders
 * @author ignazio
 */
public interface SettableCardinality<B> extends HasCardinality {

    /**
     * @param cardinality cardinality to set
     * @return builder
     */
    B withCardinality(int cardinality);
}
