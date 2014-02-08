package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Builder class for OWLInverseObjectPropertiesAxiom */
public class BuilderInverseObjectProperties
        extends
        BaseObjectPropertyBuilder<OWLInverseObjectPropertiesAxiom, BuilderInverseObjectProperties> {
    private OWLObjectPropertyExpression inverseProperty = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderInverseObjectProperties(
            OWLInverseObjectPropertiesAxiom expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getFirstProperty()).withInverseProperty(
                expected.getSecondProperty()).withAnnotations(
                expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderInverseObjectProperties(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            inverse property
     * @return builder */
    public BuilderInverseObjectProperties withInverseProperty(
            OWLObjectPropertyExpression arg) {
        inverseProperty = arg;
        return this;
    }

    @Override
    public OWLInverseObjectPropertiesAxiom buildObject() {
        return df.getOWLInverseObjectPropertiesAxiom(property, inverseProperty,
                annotations);
    }
}
