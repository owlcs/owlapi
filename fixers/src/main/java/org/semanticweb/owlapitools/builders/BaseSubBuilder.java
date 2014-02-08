package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLSubAnnotationPropertyOfAxiom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type
 * @param <Item>
 *            contained items type */
public abstract class BaseSubBuilder<T extends OWLObject, Type, Item> extends
        BaseBuilder<T, Type> {
    protected Item sub = null;
    protected Item sup = null;

    /** @param df
     *            data factory */
    public BaseSubBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            sub item
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withSub(Item arg) {
        sub = arg;
        return (Type) this;
    }

    /** @param arg
     *            sup item
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withSup(Item arg) {
        sup = arg;
        return (Type) this;
    }
}
