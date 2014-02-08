package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;

/** Builder class for OWLDisjointClassesAxiom */
public class BuilderDisjointClasses
        extends
        BaseSetBuilder<OWLDisjointClassesAxiom, BuilderDisjointClasses, OWLClassExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDisjointClasses(OWLDisjointClassesAxiom expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getClassExpressions()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderDisjointClasses(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLDisjointClassesAxiom buildObject() {
        return df.getOWLDisjointClassesAxiom(items, annotations);
    }
}
