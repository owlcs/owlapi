package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;

/** Builder class for OWLSymmetricObjectPropertyAxiom */
public class BuilderSymmetricObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLSymmetricObjectPropertyAxiom, BuilderSymmetricObjectProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSymmetricObjectProperty(
            OWLSymmetricObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderSymmetricObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom buildObject() {
        return df.getOWLSymmetricObjectPropertyAxiom(property, annotations);
    }
}
