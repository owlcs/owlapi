package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 * An ontology URI mapper that simply returns the ontology URI
 * without performing any mapping operation.
 */
public class NonMappingOntologyIRIMapper implements OWLOntologyIRIMapper {

    public IRI getDocumentIRI(IRI ontologyIRI) {
        return ontologyIRI;
    }
}
