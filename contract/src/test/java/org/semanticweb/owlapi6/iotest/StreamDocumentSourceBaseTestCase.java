package org.semanticweb.owlapi6.iotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.documents.ReaderDocumentSource;
import org.semanticweb.owlapi6.documents.StreamDocumentSourceBase;
import org.semanticweb.owlapi6.io.OWLParser;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

class StreamDocumentSourceBaseTestCase {

    @Test
    void shouldCreateRewindableReaderWithKnownContent() {
        StreamDocumentSourceBase source = new ReaderDocumentSource(
            new InputStreamReader(new ByteArrayInputStream(TestFiles.REWIND.getBytes()),
                StandardCharsets.UTF_8),
            "urn:test:test", null, null);
        StringWriter w = new StringWriter();
        OWLParser mockParser = new ParserForTestOperations(w);
        source.acceptParser(mockParser, mock(OWLOntology.class), mock(OntologyConfigurator.class));
        assertEquals(TestFiles.REWIND, w.toString());
    }
}
