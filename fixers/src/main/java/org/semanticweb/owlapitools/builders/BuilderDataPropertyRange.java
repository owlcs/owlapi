package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;

/** Builder class for OWLDataPropertyRangeAxiom */
public class BuilderDataPropertyRange extends
        BaseDataBuilder<OWLDataPropertyRangeAxiom, BuilderDataPropertyRange> {
    /** uninitialized builder */
    public BuilderDataPropertyRange(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderDataPropertyRange(OWLDataPropertyRangeAxiom expected,
            OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withRange(expected.getRange())
                .withAnnotations(expected.getAnnotations());
    }

    @Override
    public OWLDataPropertyRangeAxiom buildObject() {
        return df
                .getOWLDataPropertyRangeAxiom(property, dataRange, annotations);
    }
}
