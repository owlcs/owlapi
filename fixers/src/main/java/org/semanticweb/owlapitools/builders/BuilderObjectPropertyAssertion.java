package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;

/** Builder class for OWLObjectPropertyAssertionAxiom */
public class BuilderObjectPropertyAssertion
        extends
        BaseObjectPropertyBuilder<OWLObjectPropertyAssertionAxiom, BuilderObjectPropertyAssertion> {
    private OWLIndividual subject = null;
    private OWLIndividual value = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderObjectPropertyAssertion(
            OWLObjectPropertyAssertionAxiom expected, OWLDataFactory df) {
        this(df);
        withSubject(expected.getSubject()).withProperty(expected.getProperty())
                .withValue(expected.getObject())
                .withAnnotations(expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderObjectPropertyAssertion(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            subject
     * @return builder */
    public BuilderObjectPropertyAssertion withSubject(OWLIndividual arg) {
        subject = arg;
        return this;
    }

    /** @param arg
     *            value
     * @return builder */
    public BuilderObjectPropertyAssertion withValue(OWLIndividual arg) {
        value = arg;
        return this;
    }

    @Override
    public OWLObjectPropertyAssertionAxiom buildObject() {
        return df.getOWLObjectPropertyAssertionAxiom(property, subject, value,
                annotations);
    }
}
