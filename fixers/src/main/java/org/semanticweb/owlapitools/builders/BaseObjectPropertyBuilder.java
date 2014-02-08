package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Builder class for OWLDataAllValuesFrom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type */
public abstract class BaseObjectPropertyBuilder<T extends OWLObject, Type>
        extends BaseBuilder<T, Type> {
    protected OWLObjectPropertyExpression property = null;

    /** @param df
     *            data factory */
    public BaseObjectPropertyBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            property
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withProperty(OWLObjectPropertyExpression arg) {
        property = arg;
        return (Type) this;
    }
}
