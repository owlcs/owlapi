package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;

/** Builder class for SWRLLiteralArgument */
public class BuilderSWRLLiteralArgument extends
        BaseBuilder<SWRLLiteralArgument, BuilderSWRLLiteralArgument> {
    private OWLLiteral literal;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSWRLLiteralArgument(SWRLLiteralArgument expected,
            OWLDataFactory df) {
        this(df);
        with(expected.getLiteral());
    }

    /** @param df
     *            data factory */
    public BuilderSWRLLiteralArgument(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            literal
     * @return builder */
    public BuilderSWRLLiteralArgument with(OWLLiteral arg) {
        literal = arg;
        return this;
    }

    @Override
    public SWRLLiteralArgument buildObject() {
        return df.getSWRLLiteralArgument(literal);
    }
}
