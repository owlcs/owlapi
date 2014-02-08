package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/** Builder class for OWLHasKeyAxiom */
public class BuilderHasKey extends
        BaseSetBuilder<OWLHasKeyAxiom, BuilderHasKey, OWLPropertyExpression> {
    private OWLClassExpression ce;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderHasKey(OWLHasKeyAxiom expected, OWLDataFactory df) {
        this(df);
        withClass(expected.getClassExpression())
                .withAnnotations(expected.getAnnotations())
                .withItems(expected.getDataPropertyExpressions())
                .withItems(expected.getObjectPropertyExpressions());
    }

    /** uninitialized builder */
    public BuilderHasKey(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            class expression
     * @return builder */
    public BuilderHasKey withClass(OWLClassExpression arg) {
        ce = arg;
        return this;
    }

    @Override
    public OWLHasKeyAxiom buildObject() {
        return df.getOWLHasKeyAxiom(ce, items, annotations);
    }
}
