package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br><br>
 */
public abstract class AbstractInMemOWLOntologyFactory implements OWLOntologyFactory {

    private OWLOntologyManager ontologyManager;

    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.ontologyManager = owlOntologyManager;
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return ontologyManager;
    }

    @SuppressWarnings("unused")
    public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return true;
    }

    /**
     * Creates an empty ontology that a concrete representation can be
     * parsed into.  Subclasses can override this method to change the implementation
     * of the ontology.
     * @param documentIRI
     */  @SuppressWarnings("unused")
    public OWLOntology createOWLOntology(OWLOntologyID ontologyID, IRI documentIRI, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException {
        if (ontologyManager == null) {
            throw new NullPointerException();
        }
        OWLOntology ont = new OWLOntologyImpl(ontologyManager, ontologyID);
        handler.ontologyCreated(ont);
        return ont;
    }
}
