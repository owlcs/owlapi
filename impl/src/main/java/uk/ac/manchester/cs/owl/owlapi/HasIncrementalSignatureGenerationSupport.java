package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Created by ses on 10/7/14.
 */
public interface HasIncrementalSignatureGenerationSupport {

    void addSignatureEntitiesToSet(Set<OWLEntity> entities);

    void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons);
}
