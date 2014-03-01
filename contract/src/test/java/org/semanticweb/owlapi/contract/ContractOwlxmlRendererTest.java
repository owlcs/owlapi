package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.OutputStream;
import java.io.Writer;

import org.coode.owlapi.owlxml.renderer.OWLXMLObjectRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorageException;
import org.coode.owlapi.owlxml.renderer.OWLXMLRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLWriter;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlxmlRendererTest {

    @Test
    public void shouldTestOWLXMLObjectRenderer() throws Exception {
        OWLXMLObjectRenderer testSubject0 = new OWLXMLObjectRenderer(
                mock(OWLXMLWriter.class));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLXMLOntologyStorageException() throws Exception {
        OWLXMLOntologyStorageException testSubject0 = new OWLXMLOntologyStorageException(
                "");
        OWLXMLOntologyStorageException testSubject1 = new OWLXMLOntologyStorageException(
                "", new RuntimeException());
        OWLXMLOntologyStorageException testSubject2 = new OWLXMLOntologyStorageException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLRenderer() throws Exception {
        OWLXMLRenderer testSubject0 = new OWLXMLRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }
}
