package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;

/** Builder class for OWLDataMinCardinality */
public class BuilderDataMinCardinality extends
        BaseDataBuilder<OWLDataMinCardinality, BuilderDataMinCardinality> {
    private int cardinality = -1;

    /** @param df
     *            data factory */
    public BuilderDataMinCardinality(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataMinCardinality(OWLDataMinCardinality expected,
            OWLDataFactory df) {
        this(df);
        withCardinality(expected.getCardinality()).withProperty(
                expected.getProperty()).withRange(expected.getFiller());
    }

    /** @param arg
     *            cardinality
     * @return builder */
    public BuilderDataMinCardinality withCardinality(int arg) {
        cardinality = arg;
        return this;
    }

    @Override
    public OWLDataMinCardinality buildObject() {
        return df.getOWLDataMinCardinality(cardinality, property, dataRange);
    }
}
