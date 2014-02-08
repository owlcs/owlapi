package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;

/** Builder class for OWLObjectExactCardinality */
public class BuilderObjectExactCardinality
        extends
        BaseObjectBuilder<OWLObjectExactCardinality, BuilderObjectExactCardinality> {
    private int cardinality = -1;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderObjectExactCardinality(OWLObjectExactCardinality expected,
            OWLDataFactory df) {
        this(df);
        withCardinality(expected.getCardinality()).withProperty(
                expected.getProperty()).withRange(expected.getFiller());
    }

    /** uninitialized builder */
    public BuilderObjectExactCardinality(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            cardinality
     * @return builder */
    public BuilderObjectExactCardinality withCardinality(int arg) {
        cardinality = arg;
        return this;
    }

    @Override
    public OWLObjectExactCardinality buildObject() {
        return df.getOWLObjectExactCardinality(cardinality, property, range);
    }
}
