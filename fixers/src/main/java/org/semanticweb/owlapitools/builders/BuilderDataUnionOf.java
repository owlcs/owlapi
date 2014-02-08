package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;

/** Builder class for OWLDataUnionOf */
public class BuilderDataUnionOf extends
        BaseSetBuilder<OWLDataUnionOf, BuilderDataUnionOf, OWLDataRange> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataUnionOf(OWLDataUnionOf expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getOperands());
    }

    /** @param df
     *            data factory */
    public BuilderDataUnionOf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLDataUnionOf buildObject() {
        return df.getOWLDataUnionOf(items);
    }
}
