package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;

/** Builder class for OWLDataPropertyAssertionAxiom */
public class BuilderDataPropertyAssertion
        extends
        BaseDataPropertyBuilder<OWLDataPropertyAssertionAxiom, BuilderDataPropertyAssertion> {
    private OWLIndividual subject = null;
    private OWLLiteral object = null;

    /** @param df
     *            data factory */
    public BuilderDataPropertyAssertion(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataPropertyAssertion(OWLDataPropertyAssertionAxiom expected,
            OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withSubject(expected.getSubject())
                .withValue(expected.getObject())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param arg
     *            value
     * @return builder */
    public BuilderDataPropertyAssertion withValue(OWLLiteral arg) {
        object = arg;
        return this;
    }

    /** @param arg
     *            individual
     * @return builder */
    public BuilderDataPropertyAssertion withSubject(OWLIndividual arg) {
        subject = arg;
        return this;
    }

    @Override
    public OWLDataPropertyAssertionAxiom buildObject() {
        return df.getOWLDataPropertyAssertionAxiom(property, subject, object,
                annotations);
    }
}
