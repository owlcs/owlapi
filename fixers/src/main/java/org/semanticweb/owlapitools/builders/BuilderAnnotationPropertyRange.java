package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;

/** Builder class for OWLAnnotationPropertyRangeAxiom */
public class BuilderAnnotationPropertyRange
        extends
        BaseAnnotationtPropertyBuilder<OWLAnnotationPropertyRangeAxiom, BuilderAnnotationPropertyRange> {
    private IRI iri = null;

    /** @param df
     *            data factory */
    public BuilderAnnotationPropertyRange(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderAnnotationPropertyRange(
            OWLAnnotationPropertyRangeAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withRange(expected.getRange())
                .withAnnotations(expected.getAnnotations());
    }

    /** @param arg
     *            range
     * @return builder */
    public BuilderAnnotationPropertyRange withRange(IRI arg) {
        iri = arg;
        return this;
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom buildObject() {
        return df
                .getOWLAnnotationPropertyRangeAxiom(property, iri, annotations);
    }
}
