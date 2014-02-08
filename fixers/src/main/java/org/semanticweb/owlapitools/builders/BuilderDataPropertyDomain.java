package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/** Builder class for OWLDataPropertyDomainAxiom */
public class BuilderDataPropertyDomain
        extends
        BaseDomainBuilder<OWLDataPropertyDomainAxiom, BuilderDataPropertyDomain, OWLDataPropertyExpression> {
    /** @param df
     *            data factory */
    public BuilderDataPropertyDomain(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDataPropertyDomain(OWLDataPropertyDomainAxiom expected,
            OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withDomain(expected.getDomain())
                .withAnnotations(expected.getAnnotations());
    }

    @Override
    public OWLDataPropertyDomainAxiom buildObject() {
        return df.getOWLDataPropertyDomainAxiom(property, domain, annotations);
    }
}
