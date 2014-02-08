package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/** Builder class for OWLNamedIndividual */
public class BuilderNamedIndividual extends
        BaseEntityBuilder<OWLNamedIndividual, BuilderNamedIndividual> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderNamedIndividual(OWLNamedIndividual expected, OWLDataFactory df) {
        this(df);
        withIRI(expected.getIRI());
    }

    /** @param df
     *            data factory */
    public BuilderNamedIndividual(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLNamedIndividual buildObject() {
        if (pm != null && string != null) {
            return df.getOWLNamedIndividual(string, pm);
        }
        return df.getOWLNamedIndividual(iri);
    }
}
