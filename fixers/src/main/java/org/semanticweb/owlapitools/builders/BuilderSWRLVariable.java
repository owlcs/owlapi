package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLVariable;

/** Builder class for SWRLVariable */
public class BuilderSWRLVariable extends
        BaseBuilder<SWRLVariable, BuilderSWRLVariable> {
    private IRI iri;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSWRLVariable(SWRLVariable expected, OWLDataFactory df) {
        this(df);
        with(expected.getIRI());
    }

    /** @param df
     *            data factory */
    public BuilderSWRLVariable(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            iri
     * @return builder */
    public BuilderSWRLVariable with(IRI arg) {
        iri = arg;
        return this;
    }

    @Override
    public SWRLVariable buildObject() {
        return df.getSWRLVariable(iri);
    }
}
