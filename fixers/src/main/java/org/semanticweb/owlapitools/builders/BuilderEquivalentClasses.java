package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;

/** Builder class for OWLEquivalentClassesAxiom */
public class BuilderEquivalentClasses
        extends
        BaseSetBuilder<OWLEquivalentClassesAxiom, BuilderEquivalentClasses, OWLClassExpression> {
    /** uninitialized builder */
    public BuilderEquivalentClasses(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderEquivalentClasses(OWLEquivalentClassesAxiom expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getClassExpressions()).withAnnotations(
                expected.getAnnotations());
    }

    @Override
    public OWLEquivalentClassesAxiom buildObject() {
        return df.getOWLEquivalentClassesAxiom(items, annotations);
    }
}
