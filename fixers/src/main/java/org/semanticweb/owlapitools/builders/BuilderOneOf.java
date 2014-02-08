package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectOneOf;

/** Builder class for OWLObjectOneOf */
public class BuilderOneOf extends
        BaseSetBuilder<OWLObjectOneOf, BuilderOneOf, OWLIndividual> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderOneOf(OWLObjectOneOf expected, OWLDataFactory df) {
        this(df);
        withItems(expected.getIndividuals());
    }

    /** uninitialized builder */
    public BuilderOneOf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectOneOf buildObject() {
        return df.getOWLObjectOneOf(items);
    }
}
