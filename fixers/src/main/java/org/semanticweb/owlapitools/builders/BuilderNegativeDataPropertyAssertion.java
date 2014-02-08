package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;

/** Builder class for OWLNegativeDataPropertyAssertionAxiom */
public class BuilderNegativeDataPropertyAssertion
        extends
        BaseDataPropertyBuilder<OWLNegativeDataPropertyAssertionAxiom, BuilderNegativeDataPropertyAssertion> {
    private OWLIndividual subject = null;
    private OWLLiteral value = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderNegativeDataPropertyAssertion(
            OWLNegativeDataPropertyAssertionAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withSubject(expected.getSubject())
                .withValue(expected.getObject())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderNegativeDataPropertyAssertion(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            value
     * @return builder */
    public BuilderNegativeDataPropertyAssertion withValue(OWLLiteral arg) {
        value = arg;
        return this;
    }

    /** @param arg
     *            subject
     * @return builder */
    public BuilderNegativeDataPropertyAssertion withSubject(OWLIndividual arg) {
        subject = arg;
        return this;
    }

    @Override
    public OWLNegativeDataPropertyAssertionAxiom buildObject() {
        return df.getOWLNegativeDataPropertyAssertionAxiom(property, subject,
                value, annotations);
    }
}
