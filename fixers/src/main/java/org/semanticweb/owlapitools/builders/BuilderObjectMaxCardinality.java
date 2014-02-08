package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;

/** Builder class for OWLObjectMaxCardinality */
public class BuilderObjectMaxCardinality extends
        BaseObjectBuilder<OWLObjectMaxCardinality, BuilderObjectMaxCardinality> {
    private int cardinality = -1;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectMaxCardinality(OWLObjectMaxCardinality expected,
            OWLDataFactory df) {
        this(df);
        withCardinality(expected.getCardinality()).withProperty(
                expected.getProperty()).withRange(expected.getFiller());
    }

    /** @param df
     *            data factory */
    public BuilderObjectMaxCardinality(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            cardinality
     * @return builder */
    public BuilderObjectMaxCardinality withCardinality(int arg) {
        cardinality = arg;
        return this;
    }

    @Override
    public OWLObjectMaxCardinality buildObject() {
        return df.getOWLObjectMaxCardinality(cardinality, property, range);
    }
}
