package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class ConcurrentOWLOntologyBuilder implements OWLOntologyBuilder {

    private final OWLOntologyBuilder builder;
    private final @Nonnull ReadWriteLock readWriteLock;

    /**
     * @param builder
     *        ontology builder
     * @param readWriteLock
     *        lock
     */
    @Inject
    public ConcurrentOWLOntologyBuilder(@NonConcurrentDelegate OWLOntologyBuilder builder,
            ReadWriteLock readWriteLock) {
        this.builder = verifyNotNull(builder);
        this.readWriteLock = verifyNotNull(readWriteLock);
    }

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        OWLOntology owlOntology = builder.createOWLOntology(manager, ontologyID);
        return new ConcurrentOWLOntologyImpl(owlOntology, readWriteLock);
    }
}
