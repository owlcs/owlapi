package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLDataAllValuesFrom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type */
public abstract class BaseObjectBuilder<T extends OWLObject, Type> extends
        BaseObjectPropertyBuilder<T, Type> {
    protected OWLClassExpression range = null;

    /** @param df
     *            data factory */
    public BaseObjectBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            range
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withRange(OWLClassExpression arg) {
        range = arg;
        return (Type) this;
    }
}
