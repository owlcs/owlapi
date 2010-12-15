package org.coode.owl.rdf;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-May-2007<br><br>
 */
public class TestUtils {

    private static int uriCounter = 0;

    public static IRI createIRI() {
        uriCounter++;
        return IRI.create("http://org.semanticweb.owlapi/tests#uri" + uriCounter);
    }
}
