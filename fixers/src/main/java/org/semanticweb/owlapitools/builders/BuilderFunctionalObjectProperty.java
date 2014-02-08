package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;

/** Builder class for OWLFunctionalObjectPropertyAxiom */
public class BuilderFunctionalObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLFunctionalObjectPropertyAxiom, BuilderFunctionalObjectProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderFunctionalObjectProperty(
            OWLFunctionalObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderFunctionalObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLFunctionalObjectPropertyAxiom buildObject() {
        return df.getOWLFunctionalObjectPropertyAxiom(property, annotations);
    }
}
