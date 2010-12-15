package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 01-Sep-2008<br><br>
 */
public class DefaultNamespaceTagValueHandler extends AbstractTagValueHandler {


    public DefaultNamespaceTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.DEFAULT_NAMESPACE.getName(), consumer);
    }


    public void handle(String id, String value) {
        // Set the base to be the default base + default prefix
        String uri = OBOVocabulary.ONTOLOGY_URI_BASE + "/" + value.toLowerCase();
        getConsumer().setDefaultNamespace(uri + "#");
        applyChange(new SetOntologyID(getOntology(), new OWLOntologyID(IRI.create(uri))));
    }
}
