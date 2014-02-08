package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/** Builder class for OWLLiteral */
public class BuilderLiteral extends BaseBuilder<OWLLiteral, BuilderLiteral> {
    private String lang = null;
    private String literalForm = null;
    private Integer intValue = null;
    private Double doubleValue = null;
    private Float floatValue = null;
    private Boolean booleanValue = null;
    private OWLDatatype datatype;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderLiteral(OWLLiteral expected, OWLDataFactory df) {
        this(df);
        withDatatype(expected.getDatatype()).withLanguage(expected.getLang());
        if (expected.isBoolean()) {
            withValue(expected.parseBoolean());
        } else if (expected.isDouble()) {
            withValue(expected.parseDouble());
        } else if (expected.isFloat()) {
            withValue(expected.parseFloat());
        } else if (expected.isInteger()) {
            withValue(expected.parseInteger());
        } else {
            withLiteralForm(expected.getLiteral());
        }
    }

    /** @param df
     *            data factory */
    public BuilderLiteral(OWLDataFactory df) {
        super(df);
    }

    protected void clear() {
        literalForm = null;
        intValue = null;
        doubleValue = null;
        floatValue = null;
        booleanValue = null;
    }

    /** @param arg
     *            int value
     * @return builder */
    public BuilderLiteral withValue(int arg) {
        clear();
        intValue = arg;
        return this;
    }

    /** @param arg
     *            datatype
     * @return builder */
    public BuilderLiteral withDatatype(OWL2Datatype arg) {
        return withDatatype(df.getOWLDatatype(arg.getIRI()));
    }

    /** @param arg
     *            datatype
     * @return builder */
    public BuilderLiteral withDatatype(OWLDatatype arg) {
        lang = null;
        datatype = arg;
        return this;
    }

    /** @param arg
     *            boolean value
     * @return builder */
    public BuilderLiteral withValue(boolean arg) {
        clear();
        booleanValue = arg;
        return this;
    }

    /** @param arg
     *            double value
     * @return builder */
    public BuilderLiteral withValue(double arg) {
        clear();
        doubleValue = arg;
        return this;
    }

    /** @param arg
     *            float value
     * @return builder */
    public BuilderLiteral withValue(float arg) {
        clear();
        floatValue = arg;
        return this;
    }

    /** @param arg
     *            literal form
     * @return builder */
    public BuilderLiteral withLiteralForm(String arg) {
        clear();
        literalForm = arg;
        return this;
    }

    /** @param arg
     *            language
     * @return builder */
    public BuilderLiteral withLanguage(String arg) {
        datatype = null;
        lang = arg;
        return this;
    }

    @Override
    public OWLLiteral buildObject() {
        if (intValue != null) {
            return df.getOWLLiteral(intValue.intValue());
        }
        if (doubleValue != null) {
            return df.getOWLLiteral(doubleValue.doubleValue());
        }
        if (floatValue != null) {
            return df.getOWLLiteral(floatValue.floatValue());
        }
        if (booleanValue != null) {
            return df.getOWLLiteral(booleanValue.booleanValue());
        }
        if (lang != null) {
            return df.getOWLLiteral(literalForm, lang);
        }
        return df.getOWLLiteral(literalForm, datatype);
    }
}
