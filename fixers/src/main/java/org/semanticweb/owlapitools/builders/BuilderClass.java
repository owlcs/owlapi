package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;

/** Builder class for OWLClass */
public class BuilderClass extends BaseEntityBuilder<OWLClass, BuilderClass> {
    /** @param df
     *            data factory */
    public BuilderClass(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderClass(OWLClass expected, OWLDataFactory df) {
        this(df);
        withIRI(expected.getIRI());
    }

    @Override
    public OWLClass buildObject() {
        if (pm != null && string != null) {
            return df.getOWLClass(string, pm);
        }
        return df.getOWLClass(iri);
    }
}
