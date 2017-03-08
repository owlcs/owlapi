package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLEntityCollector;

/**
 * Created by ses on 10/7/14.
 */
public interface HasIncrementalSignatureGenerationSupport extends OWLObject {

    /**
     * @param entities entity set where entities will be added
     * @return the modified input entities
     */
    default Set<OWLEntity> addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        accept(new OWLEntityCollector(entities));
        return entities;
    }

    /**
     * @param anons anonymous individuals set where individuals will be added
     * @return the modified input individuals
     */
    default Set<OWLAnonymousIndividual> addAnonymousIndividualsToSet(
                    Set<OWLAnonymousIndividual> anons) {
        accept(new AnonymousIndividualCollector(anons));
        return anons;
    }
}
