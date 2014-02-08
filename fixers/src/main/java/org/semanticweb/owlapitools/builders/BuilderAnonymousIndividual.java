package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;

/** Builder class for OWLAnonymousIndividual */
public class BuilderAnonymousIndividual extends
        BaseBuilder<OWLAnonymousIndividual, BuilderAnonymousIndividual> {
    private String id = null;

    /** @param df
     *            data factory */
    public BuilderAnonymousIndividual(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderAnonymousIndividual(OWLAnonymousIndividual expected,
            OWLDataFactory df) {
        this(df);
        withId(expected.getID().getID());
    }

    /** @param arg
     *            blank node id
     * @return builder */
    public BuilderAnonymousIndividual withId(String arg) {
        id = arg;
        return this;
    }

    @Override
    public OWLAnonymousIndividual buildObject() {
        if (id == null) {
            return df.getOWLAnonymousIndividual();
        }
        return df.getOWLAnonymousIndividual(id);
    }
}
