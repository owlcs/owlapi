package org.semanticweb.owlapi6.iotest;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi6.io.OWLParser;
import org.semanticweb.owlapi6.io.OWLParserParameters;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDocumentFormatFactory;

class ParserForTest implements OWLParser {

    private final StringWriter w;

    ParserForTest(StringWriter w) {
        this.w = w;
    }

    @Override
    public OWLDocumentFormat parse(Reader r, OWLParserParameters p) {
        try {
            char[] buffer = new char[128];
            int i = r.read(buffer);
            while (i > -1) {
                w.write(buffer, 0, i);
                i = r.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return null;
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new RDFXMLDocumentFormatFactory();
    }
}
