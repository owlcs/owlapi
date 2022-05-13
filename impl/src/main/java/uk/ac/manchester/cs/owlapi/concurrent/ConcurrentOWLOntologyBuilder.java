package uk.ac.manchester.cs.owlapi.concurrent;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.verifyNotNull;

import java.util.concurrent.locks.ReadWriteLock;

import javax.inject.Inject;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class ConcurrentOWLOntologyBuilder implements OWLOntologyBuilder {

    private final OWLOntologyBuilder builder;
    private ReadWriteLock readWriteLock;

    /**
     * @param builder ontology builder
     * @param readWriteLock lock
     */
    @Inject
    public ConcurrentOWLOntologyBuilder(@NonConcurrentDelegate OWLOntologyBuilder builder,
        ReadWriteLock readWriteLock) {
        this.builder = verifyNotNull(builder);
        this.readWriteLock = verifyNotNull(readWriteLock);
    }

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID,
        OntologyConfigurator config) {
        OWLOntology owlOntology = builder.createOWLOntology(manager, ontologyID, config);
        return new ConcurrentOWLOntologyImpl(owlOntology, readWriteLock);
    }

    @Override
    public void setLock(ReadWriteLock lock) {
        readWriteLock = lock;
    }
}
