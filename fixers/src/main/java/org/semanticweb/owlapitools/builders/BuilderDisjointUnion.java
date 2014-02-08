package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;

/** Builder class for OWLDisjointUnionAxiom */
public class BuilderDisjointUnion
        extends
        BaseSetBuilder<OWLDisjointUnionAxiom, BuilderDisjointUnion, OWLClassExpression> {
    private OWLClass ce = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDisjointUnion(OWLDisjointUnionAxiom expected,
            OWLDataFactory df) {
        this(df);
        withClass(expected.getOWLClass()).withItems(
                expected.getClassExpressions()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderDisjointUnion(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            right hand entity
     * @return builder */
    public BuilderDisjointUnion withClass(OWLClass arg) {
        ce = arg;
        return this;
    }

    @Override
    public OWLDisjointUnionAxiom buildObject() {
        return df.getOWLDisjointUnionAxiom(ce, items, annotations);
    }
}
