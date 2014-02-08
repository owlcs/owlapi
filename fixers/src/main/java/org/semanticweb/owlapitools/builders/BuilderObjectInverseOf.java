package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;

/** Builder class for OWLObjectInverseOf */
public class BuilderObjectInverseOf extends
        BaseObjectPropertyBuilder<OWLObjectInverseOf, BuilderObjectInverseOf> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectInverseOf(OWLObjectInverseOf expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getInverse());
    }

    /** @param df
     *            data factory */
    public BuilderObjectInverseOf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectInverseOf buildObject() {
        return df.getOWLObjectInverseOf(property);
    }
}
