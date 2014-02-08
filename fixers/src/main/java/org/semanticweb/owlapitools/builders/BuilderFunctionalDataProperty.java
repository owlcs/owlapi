package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;

/** Builder class for OWLFunctionalDataPropertyAxiom */
public class BuilderFunctionalDataProperty
        extends
        BaseDataPropertyBuilder<OWLFunctionalDataPropertyAxiom, BuilderFunctionalDataProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderFunctionalDataProperty(
            OWLFunctionalDataPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderFunctionalDataProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLFunctionalDataPropertyAxiom buildObject() {
        return df.getOWLFunctionalDataPropertyAxiom(property, annotations);
    }
}
