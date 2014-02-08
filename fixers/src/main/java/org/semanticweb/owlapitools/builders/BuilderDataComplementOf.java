package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;

/** Builder class for OWLDataComplementOf */
public class BuilderDataComplementOf extends
        BaseBuilder<OWLDataComplementOf, BuilderDataComplementOf> {
    private OWLDataRange dataRange = null;

    /** @param df
     *            data factory */
    public BuilderDataComplementOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataComplementOf(OWLDataComplementOf expected,
            OWLDataFactory df) {
        this(df);
        withRange(expected.getDataRange());
    }

    /** @param arg
     *            range
     * @return builder */
    public BuilderDataComplementOf withRange(OWLDataRange arg) {
        dataRange = arg;
        return this;
    }

    @Override
    public OWLDataComplementOf buildObject() {
        return df.getOWLDataComplementOf(dataRange);
    }
}
