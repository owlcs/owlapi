package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;

/** Builder class for SWRLClassAtom */
public class BuilderSWRLClassAtom extends
        BaseBuilder<SWRLClassAtom, BuilderSWRLClassAtom> {
    private SWRLIArgument argument = null;
    private OWLClassExpression predicate = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSWRLClassAtom(SWRLClassAtom expected, OWLDataFactory df) {
        this(df);
        with(expected.getPredicate()).with(expected.getArgument());
    }

    /** @param df
     *            data factory */
    public BuilderSWRLClassAtom(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            argument
     * @return builder */
    public BuilderSWRLClassAtom with(SWRLIArgument arg) {
        argument = arg;
        return this;
    }

    /** @param arg
     *            class
     * @return builder */
    public BuilderSWRLClassAtom with(OWLClassExpression arg) {
        predicate = arg;
        return this;
    }

    @Override
    public SWRLClassAtom buildObject() {
        return df.getSWRLClassAtom(predicate, argument);
    }
}
