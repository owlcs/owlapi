package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;

/** Builder class for OWLObjectIntersectionOf */
public class BuilderObjectIntersectionOf
        extends
        BaseSetBuilder<OWLObjectIntersectionOf, BuilderObjectIntersectionOf, OWLClassExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectIntersectionOf(OWLObjectIntersectionOf expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getOperands());
    }

    /** @param df
     *            data factory */
    public BuilderObjectIntersectionOf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectIntersectionOf buildObject() {
        return df.getOWLObjectIntersectionOf(items);
    }
}
