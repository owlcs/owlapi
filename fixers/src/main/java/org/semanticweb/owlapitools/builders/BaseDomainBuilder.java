package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

/** Builder class for OWLDataPropertyDomainAxiom
 * 
 * @param <T>
 *            type built
 * @param <Type>
 *            builder type
 * @param <Property>
 *            contained items type */
public abstract class BaseDomainBuilder<T extends OWLObject, Type, Property>
        extends BaseBuilder<T, Type> {
    protected Property property = null;
    protected OWLClassExpression domain = null;

    /** @param df
     *            data factory */
    public BaseDomainBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            domain
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withDomain(OWLClassExpression arg) {
        domain = arg;
        return (Type) this;
    }

    /** @param arg
     *            property
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withProperty(Property arg) {
        property = arg;
        return (Type) this;
    }
}
