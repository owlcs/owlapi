package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;

import java.util.Set;

/**
 * Created by ses on 10/7/14.
 */
public interface NonCachedSignatureImplSupport {
    void addSignatureEntitiesToSet(Set<OWLEntity> entities);

    void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons);
}
