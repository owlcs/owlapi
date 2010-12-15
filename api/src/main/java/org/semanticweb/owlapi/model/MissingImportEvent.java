package org.semanticweb.owlapi.model;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Apr-2008<br><br>
 * </p>
 * An event that gets posted to objects that listen for missing imports when silent missing import handling is set
 * to <code>true</code> in an ontology manager.
 * @see org.semanticweb.owlapi.model.MissingImportListener
 * @see OWLOntologyManager#isSilentMissingImportsHandling() 
 * @see org.semanticweb.owlapi.model.OWLOntologyManager#addMissingImportListener(MissingImportListener)
 * @see org.semanticweb.owlapi.model.OWLOntologyManager#removeMissingImportListener(MissingImportListener) 
 */
public class MissingImportEvent {

    private URI ontologyURI;

    private OWLOntologyCreationException creationException;


    public MissingImportEvent(URI ontologyURI, OWLOntologyCreationException creationException) {
        this.ontologyURI = ontologyURI;
        this.creationException = creationException;
    }


    public URI getImportedOntologyURI() {
        return ontologyURI;
    }


    public OWLOntologyCreationException getCreationException() {
        return creationException;
    }
}
