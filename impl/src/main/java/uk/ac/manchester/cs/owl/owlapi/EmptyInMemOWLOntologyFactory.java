package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.io.DefaultOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2006<br><br>
 */
public class EmptyInMemOWLOntologyFactory extends AbstractInMemOWLOntologyFactory {

    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler mediator) throws OWLOntologyCreationException {
        throw new OWLRuntimeException(new UnsupportedOperationException("Cannot load OWL ontologies."));
    }

    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler handler, OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        return loadOWLOntology(documentSource, handler);
    }

    @Override
	public OWLOntology createOWLOntology(OWLOntologyID ontologyID, IRI documentIRI, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException {
        OWLOntology ont = super.createOWLOntology(ontologyID, documentIRI, handler);
        handler.setOntologyFormat(ont, new DefaultOntologyFormat());
        return ont;
    }



    public boolean canLoad(OWLOntologyDocumentSource documentSource) {
        return false;
    }
}
