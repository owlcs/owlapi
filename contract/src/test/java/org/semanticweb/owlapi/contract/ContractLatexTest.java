package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.coode.owlapi.latex.LatexAxiomsListOntologyFormat;
import org.coode.owlapi.latex.LatexBracketChecker;
import org.coode.owlapi.latex.LatexOWLObjectRenderer;
import org.coode.owlapi.latex.LatexObjectVisitor;
import org.coode.owlapi.latex.LatexOntologyFormat;
import org.coode.owlapi.latex.LatexOntologyStorer;
import org.coode.owlapi.latex.LatexRenderer;
import org.coode.owlapi.latex.LatexRendererException;
import org.coode.owlapi.latex.LatexRendererIOException;
import org.coode.owlapi.latex.LatexWriter;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractLatexTest {

    public void shouldTestLatexAxiomsListOntologyFormat() throws Exception {
        LatexAxiomsListOntologyFormat testSubject0 = new LatexAxiomsListOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestLatexBracketChecker() throws Exception {
        boolean result0 = LatexBracketChecker.requiresBracket(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestLatexObjectVisitor() throws Exception {
        LatexObjectVisitor testSubject0 = new LatexObjectVisitor(
                mock(LatexWriter.class), mock(OWLDataFactory.class));
        testSubject0.setSubject(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        boolean result0 = testSubject0.isPrettyPrint();
        testSubject0.setPrettyPrint(false);
        String result1 = testSubject0.toString();
    }

    public void shouldTestLatexOntologyFormat() throws Exception {
        LatexOntologyFormat testSubject0 = new LatexOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestLatexOntologyStorer() throws Exception {
        LatexOntologyStorer testSubject0 = new LatexOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexOWLObjectRenderer() throws Exception {
        LatexOWLObjectRenderer testSubject0 = new LatexOWLObjectRenderer(
                mock(OWLDataFactory.class));
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexRenderer() throws Exception {
        LatexRenderer testSubject0 = new LatexRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexRendererException() throws Exception {
        LatexRendererException testSubject0 = new LatexRendererException(
                new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        LatexRendererException testSubject1 = new LatexRendererException("") {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        LatexRendererException testSubject2 = new LatexRendererException("",
                new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestLatexRendererIOException() throws Exception {
        LatexRendererIOException testSubject0 = new LatexRendererIOException(
                mock(IOException.class));
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestLatexWriter() throws Exception {
        LatexWriter testSubject0 = new LatexWriter(mock(Writer.class));
        testSubject0.write(mock(Object.class));
        testSubject0.flush();
        testSubject0.writeSpace();
        testSubject0.writeOpenBrace();
        testSubject0.writeCloseBrace();
        testSubject0.writeNewLine();
        String result0 = testSubject0.toString();
    }
}
