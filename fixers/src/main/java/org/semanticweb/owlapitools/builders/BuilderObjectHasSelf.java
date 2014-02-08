package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;

/** Builder class for OWLObjectHasSelf */
public class BuilderObjectHasSelf extends
        BaseObjectPropertyBuilder<OWLObjectHasSelf, BuilderObjectHasSelf> {
    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderObjectHasSelf(OWLObjectHasSelf expected, OWLDataFactory df) {
        this(df);
        withProperty(expected.getProperty());
    }

    /** @param df
     *            data factory */
    public BuilderObjectHasSelf(OWLDataFactory df) {
        super(df);
    }

    @Override
    public OWLObjectHasSelf buildObject() {
        return df.getOWLObjectHasSelf(property);
    }
}
