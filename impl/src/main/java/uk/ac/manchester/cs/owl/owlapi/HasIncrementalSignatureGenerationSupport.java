package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Created by ses on 10/7/14.
 */
public interface HasIncrementalSignatureGenerationSupport {

    /**
     * @param entities
     *        entity set where entities will be added
     */
    void addSignatureEntitiesToSet(@Nonnull Set<OWLEntity> entities);

    /**
     * @param anons
     *        anonymous individuals set where individuals will be added
     */
    void
            addAnonymousIndividualsToSet(
                    @Nonnull Set<OWLAnonymousIndividual> anons);
}
