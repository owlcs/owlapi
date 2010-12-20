package org.semanticweb.owlapi.api.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 */
public abstract class AbstractFileTestCase extends AbstractOWLAPITestCase {

    protected OWLOntology createOntology() {
        try {
            String fileName = getFileName();
            URI uri = getClass().getResource("/" + fileName).toURI();
            return getManager().loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(uri)), getConfiguration());
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    protected OWLOntologyLoaderConfiguration getConfiguration() {
        return new OWLOntologyLoaderConfiguration();
    }

    protected abstract String getFileName();
}
