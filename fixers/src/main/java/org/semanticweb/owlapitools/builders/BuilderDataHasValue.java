package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLLiteral;

/** Builder class for OWLDataHasValue */
public class BuilderDataHasValue extends
        BaseDataPropertyBuilder<OWLDataHasValue, BuilderDataHasValue> {
    private OWLLiteral literal = null;

    /** @param df
     *            data factory */
    public BuilderDataHasValue(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderDataHasValue(OWLDataHasValue expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withLiteral(expected.getFiller());
    }

    /** @param arg
     *            literal
     * @return builder */
    public BuilderDataHasValue withLiteral(OWLLiteral arg) {
        literal = arg;
        return this;
    }

    @Override
    public OWLDataHasValue buildObject() {
        return df.getOWLDataHasValue(property, literal);
    }
}
