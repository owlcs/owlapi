package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;

/** Builder class for OWLClassAssertionAxiom */
public class BuilderClassAssertion extends
        BaseBuilder<OWLClassAssertionAxiom, BuilderClassAssertion> {
    private OWLIndividual i = null;
    private OWLClassExpression ce = null;

    /** @param df
     *            data factory */
    public BuilderClassAssertion(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderClassAssertion(OWLClassAssertionAxiom expected,
            OWLDataFactory df) {
        this(df);
        withClass(expected.getClassExpression()).withIndividual(
                expected.getIndividual()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param arg
     *            individual
     * @return builder */
    public BuilderClassAssertion withIndividual(OWLIndividual arg) {
        i = arg;
        return this;
    }

    /** @param arg
     *            argument
     * @return builder */
    public BuilderClassAssertion withClass(OWLClassExpression arg) {
        ce = arg;
        return this;
    }

    @Override
    public OWLClassAssertionAxiom buildObject() {
        return df.getOWLClassAssertionAxiom(ce, i, annotations);
    }
}
