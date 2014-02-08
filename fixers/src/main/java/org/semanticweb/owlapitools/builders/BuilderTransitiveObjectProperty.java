package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/** Builder class for OWLTransitiveObjectPropertyAxiom */
public class BuilderTransitiveObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLTransitiveObjectPropertyAxiom, BuilderTransitiveObjectProperty> {
    /** @param df
     *            data factory */
    public BuilderTransitiveObjectProperty(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderTransitiveObjectProperty(
            OWLTransitiveObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    @Override
    public OWLTransitiveObjectPropertyAxiom buildObject() {
        return df.getOWLTransitiveObjectPropertyAxiom(property, annotations);
    }
}
