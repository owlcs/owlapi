package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/** Builder class for OWLSubClassOfAxiom */
public class BuilderSubClass extends
        BaseSubBuilder<OWLSubClassOfAxiom, BuilderSubClass, OWLClassExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSubClass(OWLSubClassOfAxiom expected, OWLDataFactory df) {
        this(df);
        withSub(expected.getSubClass()).withSup(expected.getSuperClass())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderSubClass(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSubClassOfAxiom buildObject() {
        return df.getOWLSubClassOfAxiom(sub, sup, annotations);
    }
}
