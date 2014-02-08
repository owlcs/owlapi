package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;

/** Builder class for OWLIrreflexiveObjectPropertyAxiom */
public class BuilderIrreflexiveObjectProperty
        extends
        BaseObjectPropertyBuilder<OWLIrreflexiveObjectPropertyAxiom, BuilderIrreflexiveObjectProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderIrreflexiveObjectProperty(
            OWLIrreflexiveObjectPropertyAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderIrreflexiveObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLIrreflexiveObjectPropertyAxiom buildObject() {
        return df.getOWLIrreflexiveObjectPropertyAxiom(property, annotations);
    }
}
