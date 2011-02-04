package org.coode.owlapi.obo.parser;

import org.coode.string.EscapeUtils;
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


    public void handle(String id, String value, String comment) {
        // Set the base to be the default base + default prefix
        String unescaped = EscapeUtils.unescapeString(value);

        String stripped = unescaped.replace(" ", "-");
        if (!stripped.startsWith("http:")) {
            getConsumer().setDefaultNamespace(value.toUpperCase());
            IRI iri = getIdIRI(stripped);
            applyChange(new SetOntologyID(getOntology(), new OWLOntologyID(iri)));
        }
        else {
            if(stripped.endsWith("/")) {
                stripped = stripped.substring(0, stripped.length() - 1);
            }
            IRI iri = IRI.create(stripped);
            applyChange(new SetOntologyID(getOntology(), new OWLOntologyID(iri)));
        }

    }
}
