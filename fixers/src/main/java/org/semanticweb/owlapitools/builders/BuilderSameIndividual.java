package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

/** Builder class for OWLSameIndividualAxiom */
public class BuilderSameIndividual
        extends
        BaseSetBuilder<OWLSameIndividualAxiom, BuilderSameIndividual, OWLIndividual> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderSameIndividual(OWLSameIndividualAxiom expected,
            OWLDataFactory df) {
        this(df);
        withItems(expected.getIndividuals()).withAnnotations(
                expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderSameIndividual(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLSameIndividualAxiom buildObject() {
        return df.getOWLSameIndividualAxiom(items, annotations);
    }
}
