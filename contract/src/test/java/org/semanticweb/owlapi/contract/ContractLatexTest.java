package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.OutputStream;
import java.io.Writer;

import org.coode.owlapi.latex.LatexAxiomsListOntologyFormat;
import org.coode.owlapi.latex.LatexBracketChecker;
import org.coode.owlapi.latex.LatexOWLObjectRenderer;
import org.coode.owlapi.latex.LatexObjectVisitor;
import org.coode.owlapi.latex.LatexOntologyFormat;
import org.coode.owlapi.latex.LatexOntologyStorer;
import org.coode.owlapi.latex.LatexRenderer;
import org.coode.owlapi.latex.LatexWriter;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractLatexTest {
    public void shouldTestLatexAxiomsListOntologyFormat() throws OWLException {
        LatexAxiomsListOntologyFormat testSubject0 = new LatexAxiomsListOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestLatexBracketChecker() throws OWLException {
        boolean result0 = LatexBracketChecker.requiresBracket(Utils.mockAnonClass());
    }

    @Test
    public void shouldTestLatexObjectVisitor() throws OWLException {
        LatexObjectVisitor testSubject0 = new LatexObjectVisitor(mock(LatexWriter.class),
                mock(OWLDataFactory.class));
        testSubject0.setSubject(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        boolean result0 = testSubject0.isPrettyPrint();
        testSubject0.setPrettyPrint(false);
        String result1 = testSubject0.toString();
    }

    public void shouldTestLatexOntologyFormat() throws OWLException {
        LatexOntologyFormat testSubject0 = new LatexOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestLatexOntologyStorer() throws OWLException {
        LatexOntologyStorer testSubject0 = new LatexOntologyStorer();
        boolean result0 = testSubject0.canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class), mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexOWLObjectRenderer() throws OWLException {
        LatexOWLObjectRenderer testSubject0 = new LatexOWLObjectRenderer(
                mock(OWLDataFactory.class));
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexRenderer() throws OWLException {
        LatexRenderer testSubject0 = new LatexRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestLatexWriter() throws OWLException {
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
