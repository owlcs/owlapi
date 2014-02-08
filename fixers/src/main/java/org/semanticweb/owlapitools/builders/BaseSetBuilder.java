package org.semanticweb.owlapitools.builders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

/** abstract builder for entities
 * 
 * @author ignazio
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type
 * @param <Item>
 *            contained items type */
public abstract class BaseSetBuilder<T extends OWLObject, Type, Item> extends
        BaseBuilder<T, Type> {
    protected Set<Item> items = new HashSet<Item>();

    /** @param df
     *            data factory */
    public BaseSetBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            item to add
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withItem(Item arg) {
        items.add(arg);
        return (Type) this;
    }

    /** @param arg
     *            items to add
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withItems(Collection<? extends Item> arg) {
        items.addAll(arg);
        return (Type) this;
    }

    /** @param arg
     *            items to add
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withItems(Item... arg) {
        for (Item i : arg) {
            items.add(i);
        }
        return (Type) this;
    }
}
