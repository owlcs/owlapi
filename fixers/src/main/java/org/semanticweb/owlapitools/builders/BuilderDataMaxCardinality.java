package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;

/** Builder class for OWLDataMaxCardinality */
public class BuilderDataMaxCardinality extends
        BaseDataBuilder<OWLDataMaxCardinality, BuilderDataMaxCardinality> {
    private int cardinality = -1;

    /** @param df
     *            data factory */
    public BuilderDataMaxCardinality(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataMaxCardinality(OWLDataMaxCardinality expected,
            OWLDataFactory df) {
        this(df);
        withCardinality(expected.getCardinality()).withProperty(
                expected.getProperty()).withRange(expected.getFiller());
    }

    /** @param arg
     *            cardinality
     * @return builder */
    public BuilderDataMaxCardinality withCardinality(int arg) {
        cardinality = arg;
        return this;
    }

    @Override
    public OWLDataMaxCardinality buildObject() {
        return df.getOWLDataMaxCardinality(cardinality, property, dataRange);
    }
}
