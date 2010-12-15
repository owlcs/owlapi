package org.semanticweb.owlapi.io;

import java.io.InputStream;
import java.io.Reader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Apr-2007<br><br>
 */
public class IRIDocumentSource implements OWLOntologyDocumentSource {

    private IRI documentIRI;


    public IRIDocumentSource(IRI documentIRI) {
        this.documentIRI = documentIRI;
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        throw new OWLRuntimeException("InputStream not available.  Check with IRIDocumentSource.isInputStreamAvailable() first!");
    }


    public boolean isReaderAvailable() {
        return false;
    }


    public Reader getReader() {
        throw new OWLRuntimeException("Reader not available.  Check with IRIDocumentSource.isReaderAvailable() first!");
    }


    @Override
	public String toString() {
        return documentIRI.toString();
    }
}
