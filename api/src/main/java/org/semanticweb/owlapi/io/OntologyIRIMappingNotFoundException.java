package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2008<br><br>
 */
public class OntologyIRIMappingNotFoundException extends OWLOntologyCreationException {

    private IRI ontologyIRI;

    public OntologyIRIMappingNotFoundException(IRI ontologyIRI) {
        super("Document IRI mapping not found for " + ontologyIRI);
        this.ontologyIRI = ontologyIRI;
    }

    public IRI getOntologyIRI() {
        return ontologyIRI;
    }
}
