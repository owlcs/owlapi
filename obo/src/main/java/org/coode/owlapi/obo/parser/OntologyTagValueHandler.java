package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class OntologyTagValueHandler extends AbstractTagValueHandler {

    public OntologyTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.ONTOLOGY.getName(), consumer);
    }


    public void handle(String id, String value, String comment) {
        // The ontology name appears in lower case apparently
        IRI ontIRI = OBOVocabulary.ID2IRI(value);
        OWLOntologyID ontId = new OWLOntologyID(ontIRI);
        applyChange(new SetOntologyID(getOntology(), ontId));
    }
}
