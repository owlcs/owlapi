package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;

/** Builder class for OWLEquivalentDataPropertiesAxiom */
public class BuilderEquivalentDataProperties
        extends
        BaseSetBuilder<OWLEquivalentDataPropertiesAxiom, BuilderEquivalentDataProperties, OWLDataPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderEquivalentDataProperties(
            OWLEquivalentDataPropertiesAxiom expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getProperties()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderEquivalentDataProperties(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom buildObject() {
        return df.getOWLEquivalentDataPropertiesAxiom(items, annotations);
    }
}
