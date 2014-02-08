package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;

/** Builder class for OWLAsymmetricObjectPropertyAxiom */
public class BuilderAsymmetricObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLAsymmetricObjectPropertyAxiom, BuilderAsymmetricObjectProperty> {
    /** uninitialized builder */
    public BuilderAsymmetricObjectProperty(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderAsymmetricObjectProperty(
            OWLAsymmetricObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    @Override
    public OWLAsymmetricObjectPropertyAxiom buildObject() {
        return df.getOWLAsymmetricObjectPropertyAxiom(property, annotations);
    }
}
