package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;

/** Builder class for OWLSubDataPropertyOfAxiom */
public class BuilderSubDataProperty
        extends
        BaseSubBuilder<OWLSubDataPropertyOfAxiom, BuilderSubDataProperty, OWLDataPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSubDataProperty(OWLSubDataPropertyOfAxiom expected,
            OWLDataFactory df) {
        this(df);
        withSub(expected.getSubProperty()).withSup(expected.getSuperProperty())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderSubDataProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSubDataPropertyOfAxiom buildObject() {
        return df.getOWLSubDataPropertyOfAxiom(sub, sup, annotations);
    }
}
