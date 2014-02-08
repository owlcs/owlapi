package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;

/** Builder class for SWRLObjectPropertyAtom */
public class BuilderSWRLObjectPropertyAtom
        extends
        BaseObjectPropertyBuilder<SWRLObjectPropertyAtom, BuilderSWRLObjectPropertyAtom> {
    private SWRLIArgument arg1;
    private SWRLIArgument arg0;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderSWRLObjectPropertyAtom(SWRLObjectPropertyAtom expected,
            OWLDataFactory df) {
        this(df);
        withArg0(expected.getFirstArgument()).withArg1(
                expected.getSecondArgument()).withProperty(
                expected.getPredicate());
    }

    /** @param df
     *            data factory */
    public BuilderSWRLObjectPropertyAtom(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            individual
     * @return builder */
    public BuilderSWRLObjectPropertyAtom withArg0(SWRLIArgument arg) {
        arg0 = arg;
        return this;
    }

    /** @param arg
     *            individual
     * @return builder */
    public BuilderSWRLObjectPropertyAtom withArg1(SWRLIArgument arg) {
        arg1 = arg;
        return this;
    }

    @Override
    public SWRLObjectPropertyAtom buildObject() {
        return df.getSWRLObjectPropertyAtom(property, arg0, arg1);
    }
}
