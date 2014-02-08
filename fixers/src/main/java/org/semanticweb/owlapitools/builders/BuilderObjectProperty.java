package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/** Builder class for OWLObjectProperty */
public class BuilderObjectProperty extends
        BaseEntityBuilder<OWLObjectProperty, BuilderObjectProperty> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectProperty(OWLObjectProperty expected, OWLDataFactory df) {
        this(df);
        withIRI(expected.getIRI());
    }

    /** @param df
     *            data factory */
    public BuilderObjectProperty(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectProperty buildObject() {
        if (pm != null && string != null) {
            return df.getOWLObjectProperty(string, pm);
        }
        return df.getOWLObjectProperty(iri);
    }
}
