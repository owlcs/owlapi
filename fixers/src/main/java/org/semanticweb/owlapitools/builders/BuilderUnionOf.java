package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/** Builder class for OWLObjectUnionOf */
public class BuilderUnionOf extends
        BaseSetBuilder<OWLObjectUnionOf, BuilderUnionOf, OWLClassExpression> {
    /** uninitialized builder */
    public BuilderUnionOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderUnionOf(OWLObjectUnionOf expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getOperands());
    }

    @Override
    public OWLObjectUnionOf buildObject() {
        return df.getOWLObjectUnionOf(items);
    }
}
