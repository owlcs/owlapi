package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class NonConcurrentOWLOntologyBuilder implements OWLOntologyBuilder {

    private transient final OWLOntologyImplementationFactory implementationFactory;

    /**
     * @param implementationFactory
     *        implementation factory
     */
    @Inject
    public NonConcurrentOWLOntologyBuilder(
        @Nonnull OWLOntologyImplementationFactory implementationFactory) {
        this.implementationFactory = verifyNotNull(implementationFactory);
    }

    @Nonnull
    @Override
    public OWLOntology createOWLOntology(@Nonnull OWLOntologyManager manager,
        @Nonnull OWLOntologyID ontologyID) {
        return implementationFactory.createOWLOntology(manager, ontologyID);
    }
}
