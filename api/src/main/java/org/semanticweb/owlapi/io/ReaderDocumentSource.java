package org.semanticweb.owlapi.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Nov-2007<br><br>
 *
 * An ontology document source which reads an ontology from a reader.
 */
public class ReaderDocumentSource implements OWLOntologyDocumentSource {

    private static int counter = 0;

    public static final String DOCUMENT_IRI_SCHEME = "reader";

    private IRI documentIRI;

    private String buffer;


    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     */
    public ReaderDocumentSource(Reader reader) {
        this(reader, getNextDocumentIRI());
    }

    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create(DOCUMENT_IRI_SCHEME + ":ontology" + counter);
    }


    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param documentIRI The ontology document IRI which will be used as the base
     * of the document if needed.
     */
    public ReaderDocumentSource(Reader reader, IRI documentIRI) {
        this.documentIRI = documentIRI;
        fillBuffer(reader);
    }

    private void fillBuffer(Reader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            char [] tempBuffer = new char [4096];
            int read;
            while((read = reader.read(tempBuffer)) != -1) {
                builder.append(tempBuffer, 0, read);
            }
            buffer = builder.toString();
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public IRI getDocumentIRI() {
        return documentIRI;
    }

    public Reader getReader() {

        return new StringReader(buffer);
    }


    public boolean isReaderAvailable() {
        return true;
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        throw new OWLRuntimeException("InputStream not available.  Check with ReaderDocumentSource.isReaderAvailable() first!");
    }
}

