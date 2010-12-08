package org.semanticweb.owlapi.api.test;

import java.net.URISyntaxException;

import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 23-Jul-2008<br><br>
 */
public abstract class AbstractFileRoundTrippingTestCase extends AbstractRoundTrippingTest {

    protected OWLOntology createOntology() {
        try {
            String fileName = getFileName();
            IRI iri = IRI.create(getClass().getResource("/" + fileName).toURI());
            UnparsableOntologyException.setIncludeStackTraceInMessage(true);
            return getManager().loadOntologyFromOntologyDocument(iri);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getFileName();

}
