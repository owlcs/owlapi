package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLLiteral;

/** Builder class for OWLDataOneOf */
public class BuilderDataOneOf extends
        BaseSetBuilder<OWLDataOneOf, BuilderDataOneOf, OWLLiteral> {
    /** uninitialized builder */
    public BuilderDataOneOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderDataOneOf(OWLDataOneOf expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getValues());
    }

    @Override
    public OWLDataOneOf buildObject() {
        return df.getOWLDataOneOf(items);
    }
}
