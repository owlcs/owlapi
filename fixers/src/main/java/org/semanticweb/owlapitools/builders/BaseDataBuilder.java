package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLDataAllValuesFrom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type */
public abstract class BaseDataBuilder<T extends OWLObject, Type> extends
        BaseDataPropertyBuilder<T, Type> {
    protected OWLDataRange dataRange = null;

    /** @param df
     *            data factory */
    public BaseDataBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            range
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withRange(OWLDataRange arg) {
        dataRange = arg;
        return (Type) this;
    }
}
