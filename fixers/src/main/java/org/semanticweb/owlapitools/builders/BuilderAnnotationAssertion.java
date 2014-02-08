package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDataFactory;

/** Builder class for OWLAnnotationAssertionAxiom */
public class BuilderAnnotationAssertion
        extends
        BaseAnnotationtPropertyBuilder<OWLAnnotationAssertionAxiom, BuilderAnnotationAssertion> {
    private OWLAnnotationSubject subject = null;
    private OWLAnnotationValue value;

    /** @param df
     *            data factory */
    public BuilderAnnotationAssertion(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderAnnotationAssertion(OWLAnnotationAssertionAxiom expected,
            OWLDataFactory df) {
        this(df);
        withAnnotations(expected.getAnnotations())
                .withSubject(expected.getSubject())
                .withProperty(expected.getProperty())
                .withValue(expected.getValue());
    }

    /** @param arg
     *            subject
     * @return builder */
    public BuilderAnnotationAssertion withSubject(OWLAnnotationSubject arg) {
        subject = arg;
        return this;
    }

    /** @param arg
     *            value
     * @return builder */
    public BuilderAnnotationAssertion withValue(OWLAnnotationValue arg) {
        value = arg;
        return this;
    }

    @Override
    public OWLAnnotationAssertionAxiom buildObject() {
        return df.getOWLAnnotationAssertionAxiom(property, subject, value,
                annotations);
    }
}
