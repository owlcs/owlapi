package org.semanticweb.owlapi.util;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public class SimpleIRIMapper implements OWLOntologyIRIMapper {

    private IRI ontologyIRI;

    private IRI documentIRI;

    public SimpleIRIMapper(IRI ontologyIRI, IRI documentIRI) {
        this.ontologyIRI = ontologyIRI;
        this.documentIRI = documentIRI;
    }

    public SimpleIRIMapper(URI ontologyURI, IRI documentIRI) {
        this(IRI.create(ontologyURI), documentIRI);
    }


    public IRI getDocumentIRI(IRI ontologyIRI) {
        if(this.ontologyIRI.equals(ontologyIRI)) {
            return documentIRI;
        }
        else {
            return null;
        }
    }
}
