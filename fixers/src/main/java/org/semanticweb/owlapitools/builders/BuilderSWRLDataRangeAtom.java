package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;

/** Builder class for SWRLDataRangeAtom */
public class BuilderSWRLDataRangeAtom extends
        BaseBuilder<SWRLDataRangeAtom, BuilderSWRLDataRangeAtom> {
    private SWRLDArgument argument;
    private OWLDataRange predicate;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSWRLDataRangeAtom(SWRLDataRangeAtom expected,
            OWLDataFactory df) {
        this(df);
        with(expected.getArgument()).with(expected.getPredicate());
    }

    /** @param df
     *            data factory */
    public BuilderSWRLDataRangeAtom(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            argument
     * @return builder */
    public BuilderSWRLDataRangeAtom with(SWRLDArgument arg) {
        argument = arg;
        return this;
    }

    /** @param arg
     *            predicate
     * @return builder */
    public BuilderSWRLDataRangeAtom with(OWLDataRange arg) {
        predicate = arg;
        return this;
    }

    @Override
    public SWRLDataRangeAtom buildObject() {
        return df.getSWRLDataRangeAtom(predicate, argument);
    }
}
