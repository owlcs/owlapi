package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/** Builder class for OWLObjectUnionOf */
public class BuilderUnionOf extends
        BaseSetBuilder<OWLObjectUnionOf, BuilderUnionOf, OWLClassExpression> {
    /** @param df
     *            data factory */
    public BuilderUnionOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderUnionOf(OWLObjectUnionOf expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getOperands());
    }

    @Override
    public OWLObjectUnionOf buildObject() {
        return df.getOWLObjectUnionOf(items);
    }
}
