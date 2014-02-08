package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;

/** Builder class for OWLSubAnnotationPropertyOfAxiom */
public class BuilderSubAnnotationPropertyOf
        extends
        BaseSubBuilder<OWLSubAnnotationPropertyOfAxiom, BuilderSubAnnotationPropertyOf, OWLAnnotationProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderSubAnnotationPropertyOf(
            OWLSubAnnotationPropertyOfAxiom expected, OWLDataFactory df) {
        this(df);
        withSub(expected.getSubProperty()).withSup(expected.getSuperProperty())
                .withAnnotations(expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderSubAnnotationPropertyOf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom buildObject() {
        return df.getOWLSubAnnotationPropertyOfAxiom(sub, sup, annotations);
    }
}
