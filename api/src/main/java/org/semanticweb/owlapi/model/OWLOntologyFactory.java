package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 * <p/>
 * An ontology factory is responsible from creating new ontologies and creating ontologies
 * from ontology document IRIs.
 */
public interface OWLOntologyFactory {

    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager);

    /**
     * Creates an (empty) ontology.
     * @param ontologyID The ID of the ontology to create. This MUST NOT BE <code>null</code>.
     * @param documentIRI The document IRI of the ontology
     * @param handler The ontology creation handler that will be notified when the
     * ontology has been created.  @return The newly created ontology
     * @return The created ontology
     * @throws OWLOntologyCreationException if the ontology could not be created.
     */
    public OWLOntology createOWLOntology(OWLOntologyID ontologyID, IRI documentIRI, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException;


    /**
     * Creates and loads an <code>OWLOntology</code>.
     * @param documentSource  The document source that provides the means of getting a representation of a document.
     * @param handler A pointer to an <code>OWLOntologyCreationHandler</code> which will be notified immediately
     * after an empty ontology has been created, but before the source data is read and the ontology is loaded
     * with axioms.
     * @return The newly created and loaded ontology
     * @throws OWLOntologyCreationException if the ontology could not be created.
     */
    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException;

    /**
     * Creates and loads an <code>OWLOntology</code>.
     * @param documentSource The document source that provides the means of getting a representation of a document.
     * @param handler A pointer to an <code>OWLOntologyCreationHandler</code> which will be notified immediately after
     * and empty ontology has been created, but before the source data is read and the ontology is loaded with axioms.
     * @param configuration A configuration object which can be used to pass various options to the loader.
     * @return The newly created and loaded ontology.
     * @throws OWLOntologyCreationException if the ontology could not be created
     */
    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler handler, OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException;

    /**
     * Determines if the factory can create an ontology for the specified ontology document IRI.
     * @param documentIRI The document IRI
     * @return <code>true</code> if the factory can create an ontology given the specified document IRI,
     *         or <code>false</code> if the factory cannot create an ontology given the specified document IRI.
     */
    public boolean canCreateFromDocumentIRI(IRI documentIRI);


    /**
     * Determines if the factory can load an ontology for the specified input souce
     * @param documentSource The input source from which to load the ontology
     * @return <code>true</code> if the factory can load from the specified input source.
     */
    public boolean canLoad(OWLOntologyDocumentSource documentSource);


    /**
     * An <code>OWLOntologyCreationHandler</code> gets notified when the factory has created an empty
     * ontology (during the loading process).  This may be needed to handle features such as cyclic imports.
     * For example if OntA and OntB are ontologies and OntA imports OntB and vice versa, OntA will probably be
     * partially loaded, but then will require the loading of OntB to ensure that all entities are declared.  OntB
     * will also require the partial loading of OntA for the same reason.  The handler allows a reference to
     * an ontology which is being loaded to be obtained before loading is finished.
     */
    public interface OWLOntologyCreationHandler {

        /**
         * The factory calls this method as soon as it has created an ontology.  If the
         * factory is loading an ontology then the ontology will not have been populated with
         * axioms at this stage.
         * @param ontology The newly created ontology.
         */
        void ontologyCreated(OWLOntology ontology);

        void setOntologyFormat(OWLOntology ontology, OWLOntologyFormat format);
    }
}
