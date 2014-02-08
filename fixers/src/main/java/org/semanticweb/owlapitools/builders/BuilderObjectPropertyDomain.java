package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Builder class for OWLObjectPropertyDomainAxiom */
public class BuilderObjectPropertyDomain
        extends
        BaseDomainBuilder<OWLObjectPropertyDomainAxiom, BuilderObjectPropertyDomain, OWLObjectPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectPropertyDomain(OWLObjectPropertyDomainAxiom expected,
            OWLDataFactory df) {
        this(df);
        withDomain(expected.getDomain()).withProperty(expected.getProperty())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderObjectPropertyDomain(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectPropertyDomainAxiom buildObject() {
        return df
                .getOWLObjectPropertyDomainAxiom(property, domain, annotations);
    }
}
