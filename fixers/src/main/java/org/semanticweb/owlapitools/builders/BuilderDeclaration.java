package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/** Builder class for OWLDeclarationAxiom */
public class BuilderDeclaration extends
        BaseBuilder<OWLDeclarationAxiom, BuilderDeclaration> {
    private OWLEntity entity = null;

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderDeclaration(OWLDeclarationAxiom expected, OWLDataFactory df) {
        this(df);
        withEntity(expected.getEntity()).withAnnotations(
                expected.getAnnotations());
    }

    /** @param df
     *            data factory */
    public BuilderDeclaration(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            entity
     * @return builder */
    public BuilderDeclaration withEntity(OWLEntity arg) {
        entity = arg;
        return this;
    }

    @Override
    public OWLDeclarationAxiom buildObject() {
        return df.getOWLDeclarationAxiom(entity, annotations);
    }
}
