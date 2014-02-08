package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;

/** Builder class for OWLDataSomeValuesFrom */
public class BuilderDataSomeValuesFrom extends
        BaseDataBuilder<OWLDataSomeValuesFrom, BuilderDataSomeValuesFrom> {
    /** uninitialized builder */
    public BuilderDataSomeValuesFrom(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderDataSomeValuesFrom(OWLDataSomeValuesFrom expected,
            OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withRange(expected.getFiller());
    }

    @Override
    public OWLDataSomeValuesFrom buildObject() {
        return df.getOWLDataSomeValuesFrom(property, dataRange);
    }
}
