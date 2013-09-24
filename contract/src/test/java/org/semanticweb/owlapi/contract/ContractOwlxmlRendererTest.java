package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.OutputStream;
import java.io.Writer;

import org.coode.owlapi.owlxml.renderer.OWLXMLObjectRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLWriter;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlxmlRendererTest {
    @Test
    public void shouldTestOWLXMLObjectRenderer() throws OWLException {
        OWLXMLObjectRenderer testSubject0 = new OWLXMLObjectRenderer(
                mock(OWLXMLWriter.class));

    }

    @Test
    public void shouldTestOWLXMLRenderer() throws OWLException {
        OWLXMLRenderer testSubject0 = new OWLXMLRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));

    }
}
