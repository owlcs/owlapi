package org.semanticweb.owlapi.util;

import java.net.URI;
import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Jan-2008<br><br>
 */
public class OntologyIRIShortFormProvider implements IRIShortFormProvider {

    public String getShortForm(OWLOntology ont) {
        if (!ont.isAnonymous()) {
            return getShortForm(ont.getOntologyID().getOntologyIRI());
        }
        else {
            return ont.getOntologyID().toString();
        }
    }

    public String getShortForm(IRI iri) {
        String shortForm = iri.toString();
        URI uri = iri.toURI();
        String path = uri.getPath();
        if (path != null && path.length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(path, "/", false);
            String lastPathComponent = "";
            while (tokenizer.hasMoreTokens()) {
                String tok = tokenizer.nextToken();
                if (tok.length() > 0) {
                    lastPathComponent = tok;
                }
            }
            if (lastPathComponent.endsWith(".owl")) {
                shortForm = lastPathComponent.substring(0, lastPathComponent.length() - 4);
            }
            else {
                shortForm = lastPathComponent;
            }
        }
        else if (uri.getHost() != null) {
            shortForm = iri.toString();
        }
//        if(!Character.isUpperCase(shortForm.charAt(0))) {
//            shortForm = Character.toUpperCase(shortForm.charAt(0)) + shortForm.substring(1, shortForm.length());
//        }

        return shortForm;

    }
}
