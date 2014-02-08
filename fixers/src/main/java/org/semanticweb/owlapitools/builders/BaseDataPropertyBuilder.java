package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLDataAllValuesFrom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type */
public abstract class BaseDataPropertyBuilder<T extends OWLObject, Type>
        extends BaseBuilder<T, Type> {
    protected OWLDataPropertyExpression property = null;

    /** @param df
     *            data factory */
    public BaseDataPropertyBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            property
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withProperty(OWLDataPropertyExpression arg) {
        property = arg;
        return (Type) this;
    }
}
