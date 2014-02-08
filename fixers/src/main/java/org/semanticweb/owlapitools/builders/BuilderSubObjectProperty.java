package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

/** Builder class for OWLSubObjectPropertyOfAxiom */
public class BuilderSubObjectProperty
        extends
        BaseSubBuilder<OWLSubObjectPropertyOfAxiom, BuilderSubObjectProperty, OWLObjectPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSubObjectProperty(OWLSubObjectPropertyOfAxiom expected,
            OWLDataFactory df) {
        this(df);
        withSub(expected.getSubProperty()).withSup(expected.getSuperProperty())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderSubObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSubObjectPropertyOfAxiom buildObject() {
        return df.getOWLSubObjectPropertyOfAxiom(sub, sup, annotations);
    }
}
