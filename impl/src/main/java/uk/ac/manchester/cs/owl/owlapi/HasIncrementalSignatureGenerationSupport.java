package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * Created by ses on 10/7/14.
 */
public interface HasIncrementalSignatureGenerationSupport extends OWLObject {

    /**
     * @param entities
     *        entity set where entities will be added
     * @return the modified input entities
     */
    @Nonnull
    default Set<OWLEntity> addSignatureEntitiesToSet(
            @Nonnull Set<OWLEntity> entities) {
        accept(new EntityCollector(entities));
        return entities;
    }

    /**
     * @param anons
     *        anonymous individuals set where individuals will be added
     * @return the modified input individuals
     */
    @Nonnull
    default Set<OWLAnonymousIndividual> addAnonymousIndividualsToSet(
            @Nonnull Set<OWLAnonymousIndividual> anons) {
        accept(new AnonymousIndividualCollector(anons));
        return anons;
    }
}
