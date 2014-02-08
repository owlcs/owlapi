package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;

/** Builder class for OWLDatatype */
public class BuilderDatatype extends
        BaseEntityBuilder<OWLDatatype, BuilderDatatype> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDatatype(OWLDatatype expected, OWLDataFactory df) {
        this(df);
        withIRI(expected.getIRI());
    }

    /** @param df
     *            data factory */
    public BuilderDatatype(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLDatatype buildObject() {
        if (pm != null && string != null) {
            return df.getOWLDatatype(string, pm);
        }
        return df.getOWLDatatype(iri);
    }
}
