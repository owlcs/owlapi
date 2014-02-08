package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.PrefixManager;

/** abstract builder for entities
 * 
 * @author ignazio
 * @param <T>
 *            OWL type
 * @param <Type>
 *            buolder type */
public abstract class BaseEntityBuilder<T extends OWLEntity, Type> extends
        BaseBuilder<T, Type> {
    protected IRI iri = null;
    protected String string = null;
    protected PrefixManager pm = null;

    /** @param df
     *            data factory */
    public BaseEntityBuilder(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            property iri
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withIRI(IRI arg) {
        iri = arg;
        return (Type) this;
    }

    /** @param arg
     *            prefix manager
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withPrefixManager(PrefixManager arg) {
        pm = arg;
        return (Type) this;
    }

    /** @param arg
     *            prefixed iri
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withPrefixedIRI(String arg) {
        string = arg;
        return (Type) this;
    }
}
