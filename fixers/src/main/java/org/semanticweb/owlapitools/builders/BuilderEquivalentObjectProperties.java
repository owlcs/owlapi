package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Builder class for OWLEquivalentObjectPropertiesAxiom */
public class BuilderEquivalentObjectProperties
        extends
        BaseSetBuilder<OWLEquivalentObjectPropertiesAxiom, BuilderEquivalentObjectProperties, OWLObjectPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderEquivalentObjectProperties(
            OWLEquivalentObjectPropertiesAxiom expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getProperties()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderEquivalentObjectProperties(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom buildObject() {
        return df.getOWLEquivalentObjectPropertiesAxiom(items, annotations);
    }
}
