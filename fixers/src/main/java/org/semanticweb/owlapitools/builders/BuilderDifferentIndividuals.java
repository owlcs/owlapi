package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

/** Builder class for OWLDifferentIndividualsAxiom */
public class BuilderDifferentIndividuals
        extends
        BaseSetBuilder<OWLDifferentIndividualsAxiom, BuilderDifferentIndividuals, OWLIndividual> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderDifferentIndividuals(OWLDifferentIndividualsAxiom expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getIndividuals()).withAnnotations(
                expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderDifferentIndividuals(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLDifferentIndividualsAxiom buildObject() {
        return df.getOWLDifferentIndividualsAxiom(items, annotations);
    }
}
