package org.semanticweb.owlapi.model;

import java.io.IOException;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jan-2007<br><br>
 *
 * An ontology storer stores an ontology in a particular format at a location
 * specified by a particular URI.
 */
public interface OWLOntologyStorer {

    /**
     * Determines if this storer can store an ontology in the specified ontology format.
     * @param ontologyFormat The desired ontology format.
     * @return <code>true</code> if this storer can store an ontology in the desired
     * format.
     */
    boolean canStoreOntology(OWLOntologyFormat ontologyFormat);


    /**
     * Stores an ontology to the specified ontology document IRI in the specified format
     * @param manager The manager
     * @param ontology The ontology to be stored
     * @param documentIRI The ontology document IRI where the ontology will be saved to
     * @param ontologyFormat The format that the ontology should be stored in  @throws OWLOntologyStorageException if there was a problem storing the ontology.
     * @throws IOException if there was an IOException when storing the ontology
     * @throws OWLOntologyStorageException if there was a problem storing the ontology
     */
    void storeOntology(OWLOntologyManager manager, OWLOntology ontology, IRI documentIRI, OWLOntologyFormat ontologyFormat) throws OWLOntologyStorageException, IOException;


    /**
     * Stores an ontology to the specified target.  This method assumes the storer can write the
     * ontology to some stream.
     * @param manager The manager
     * @param ontology The ontology to be stored
     * @param target The target which describes the ontology document where the ontology should be stored
     * @param format The format in which to store the ontology
     * @throws OWLOntologyStorageException if there was a problem storing the ontology.
     * @throws IOException if there was an IOException when storing the ontology.
     */
    void storeOntology(OWLOntologyManager manager, OWLOntology ontology, OWLOntologyDocumentTarget target, OWLOntologyFormat format) throws OWLOntologyStorageException, IOException;
}
