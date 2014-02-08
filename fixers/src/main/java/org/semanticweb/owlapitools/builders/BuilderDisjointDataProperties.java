package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;

/** Builder class for OWLDisjointDataPropertiesAxiom */
public class BuilderDisjointDataProperties
        extends
        BaseSetBuilder<OWLDisjointDataPropertiesAxiom, BuilderDisjointDataProperties, OWLDataPropertyExpression> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDisjointDataProperties(
            OWLDisjointDataPropertiesAxiom expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getProperties()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderDisjointDataProperties(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLDisjointDataPropertiesAxiom buildObject() {
        return df.getOWLDisjointDataPropertiesAxiom(items, annotations);
    }
}
