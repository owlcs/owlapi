package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;

/** Builder class for OWLObjectMinCardinality */
public class BuilderObjectMinCardinality extends
        BaseObjectBuilder<OWLObjectMinCardinality, BuilderObjectMinCardinality> {
    private int cardinality = -1;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectMinCardinality(OWLObjectMinCardinality expected,
            OWLDataFactory df) {
        this(df);
        withCardinality(expected.getCardinality()).withProperty(
                expected.getProperty()).withRange(expected.getFiller());
    }

    /** @param df
     *            data factory */
    public BuilderObjectMinCardinality(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            cardinality
     * @return builder */
    public BuilderObjectMinCardinality withCardinality(int arg) {
        cardinality = arg;
        return this;
    }

    @Override
    public OWLObjectMinCardinality buildObject() {
        return df.getOWLObjectMinCardinality(cardinality, property, range);
    }
}
