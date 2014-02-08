package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLDataAllValuesFrom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type */
public abstract class BaseAnnotationtPropertyBuilder<T extends OWLObject, Type>
        extends BaseBuilder<T, Type> {
    protected OWLAnnotationProperty property = null;

    /** @param df
     *            data factory */
    public BaseAnnotationtPropertyBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            property
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withProperty(OWLAnnotationProperty arg) {
        property = arg;
        return (Type) this;
    }
}
