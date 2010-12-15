package org.semanticweb.owlapi.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2007<br><br>
 *
 * An ontology document source which can read from a stream.
 */
public class StreamDocumentSource implements OWLOntologyDocumentSource {

    public static final String DOCUMENT_IRI_SCHEME = "inputstream";

    private static int counter = 0;

    private IRI documentIRI;

    private byte [] buffer;


    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param is The stream that the ontology representation will be
     * read from.
     */
    public StreamDocumentSource(InputStream is) {
        this(is, getNextDocumentIRI());
    }

    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create(DOCUMENT_IRI_SCHEME + ":ontology" + counter);
    }


    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param stream The stream that the ontology representation will be
     * read from.
     * @param documentIRI The document IRI
     */
    public StreamDocumentSource(InputStream stream, IRI documentIRI) {
        this.documentIRI = documentIRI;
        readIntoBuffer(stream);
    }


    /**
     * Reads all the bytes from the specified stream into a temporary buffer,
     * which is necessary because we may need to access the input stream more
     * than once.  In other words, this method caches the input stream.
     * @param stream The stream to be "cached"
     */
    private void readIntoBuffer(InputStream stream) {
        try {
            byte [] tempBuffer = new byte [4096];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read;
            while((read = stream.read(tempBuffer)) != -1) {
                bos.write(tempBuffer, 0, read);
            }
            buffer = bos.toByteArray();
        }
        catch (IOException e) {
            throw new OWLOntologyInputSourceException(e);
        }
    }

    public boolean isInputStreamAvailable() {
        return true;
    }


    public InputStream getInputStream() {
        return new ByteArrayInputStream(buffer);
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }


    public Reader getReader() {
        throw new OWLRuntimeException("Reader not available.  Check with StreamDocumentSource.isReaderAvailable() first!");
    }


    public boolean isReaderAvailable() {
        return false;
    }
}
