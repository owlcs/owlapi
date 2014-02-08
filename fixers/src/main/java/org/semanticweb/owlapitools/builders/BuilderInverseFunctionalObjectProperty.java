package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;

/** Builder class for OWLInverseFunctionalObjectPropertyAxiom */
public class BuilderInverseFunctionalObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLInverseFunctionalObjectPropertyAxiom, BuilderInverseFunctionalObjectProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderInverseFunctionalObjectProperty(
            OWLInverseFunctionalObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderInverseFunctionalObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLInverseFunctionalObjectPropertyAxiom buildObject() {
        return df.getOWLInverseFunctionalObjectPropertyAxiom(property,
                annotations);
    }
}
