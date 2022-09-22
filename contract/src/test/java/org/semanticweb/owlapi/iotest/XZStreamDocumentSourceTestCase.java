package org.semanticweb.owlapi.iotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.documents.XZStreamDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * Created by ses on 3/12/15.
 */
class XZStreamDocumentSourceTestCase {

    @Test
    void testReadKoalaDoc() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/koala.owl.xz")) {
            XZStreamDocumentSource source = new XZStreamDocumentSource(in);
            StringWriter w = new StringWriter();
            OWLParser mockParser = new ParserForTestOperations(w);
            source.acceptParser(mockParser, mock(OWLOntology.class),
                mock(OntologyConfigurator.class));
            assertEquals(TestFiles.KOALA_STREAM, w.toString());
        }
    }
}
