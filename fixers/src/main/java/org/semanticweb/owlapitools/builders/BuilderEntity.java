package org.semanticweb.owlapitools.builders;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;

/** Builder class for OWLEntity */
public class BuilderEntity extends BaseEntityBuilder<OWLEntity, BuilderEntity> {
    private EntityType<?> entityType = null;

    /** @param df
     *            data factory */
    public BuilderEntity(OWLDataFactory df) {
        super(df);
    }

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object
     * @param df
     *            data factory */
    public BuilderEntity(OWLClass expected, OWLDataFactory df) {
        this(df);
        withType(EntityType.CLASS).withIRI(expected.getIRI());
    }

    /** @param arg
     *            entity type
     * @return builder */
    public BuilderEntity withType(EntityType<?> arg) {
        entityType = arg;
        return this;
    }

    @Override
    public OWLEntity buildObject() {
        if (pm != null && string != null) {
            return df.getOWLEntity(entityType, pm.getIRI(string));
        }
        return df.getOWLEntity(entityType, iri);
    }
}
