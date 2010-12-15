package org.semanticweb.owlapi.io;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Apr-2007<br><br>
 * </p>
 * An ontology input source that wraps a string.
 */
public class StringDocumentSource implements OWLOntologyDocumentSource {

    public static final String DOCUMENT_IRI_SCHEME = "string";

    private static int counter = 0;

    private IRI documentIRI;

    private String string;

    public StringDocumentSource(String string) {
        this.string = string;
        documentIRI = getNextDocumentIRI();
    }

    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create(DOCUMENT_IRI_SCHEME + ":ontology" + counter);
    }



    /**
     * Specifies a string as an ontology document.
     * @param string The string
     * @param documentIRI The document IRI
     */
    public StringDocumentSource(String string, IRI documentIRI) {
        this.string = string;
        this.documentIRI = documentIRI;
    }


    public boolean isReaderAvailable() {
        return true;
    }


    public Reader getReader() {
        return new StringReader(string);
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        throw new OWLRuntimeException("InputStream not available.  Check with StringDocumentSource.isInputStreamAvailable() first!");
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }
}
