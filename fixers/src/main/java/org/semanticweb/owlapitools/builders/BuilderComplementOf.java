package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;

/** Builder class for OWLObjectComplementOf */
public class BuilderComplementOf extends
        BaseBuilder<OWLObjectComplementOf, BuilderComplementOf> {
    private OWLClassExpression c = null;

    /** @param df
     *            data factory */
    public BuilderComplementOf(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderComplementOf(OWLObjectComplementOf expected, OWLDataFactory df) {
        this(df);
        withClass(expected.getOperand());
    }

    /** @param arg
     *            class expression
     * @return builder */
    public BuilderComplementOf withClass(OWLClassExpression arg) {
        c = arg;
        return this;
    }

    @Override
    public OWLObjectComplementOf buildObject() {
        return df.getOWLObjectComplementOf(c);
    }
}
