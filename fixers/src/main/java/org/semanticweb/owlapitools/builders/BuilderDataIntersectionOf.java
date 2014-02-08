package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataRange;

/** Builder class for OWLDataIntersectionOf */
public class BuilderDataIntersectionOf
        extends
        BaseSetBuilder<OWLDataIntersectionOf, BuilderDataIntersectionOf, OWLDataRange> {
    /** @param df
     *            data factory */
    public BuilderDataIntersectionOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataIntersectionOf(OWLDataIntersectionOf expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getOperands());
    }

    @Override
    public OWLDataIntersectionOf buildObject() {
        return df.getOWLDataIntersectionOf(items);
    }
}
