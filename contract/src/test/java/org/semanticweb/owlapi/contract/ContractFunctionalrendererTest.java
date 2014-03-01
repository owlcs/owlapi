package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

import org.coode.owlapi.functionalrenderer.OWLFunctionalSyntaxOntologyStorer;
import org.coode.owlapi.functionalrenderer.OWLFunctionalSyntaxRenderer;
import org.coode.owlapi.functionalrenderer.OWLObjectRenderer;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalrendererTest {

    public void shouldTestOWLFunctionalSyntaxOntologyStorer() throws Exception {
        OWLFunctionalSyntaxOntologyStorer testSubject0 = new OWLFunctionalSyntaxOntologyStorer();
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
    public void shouldTestOWLFunctionalSyntaxRenderer() throws Exception {
        OWLFunctionalSyntaxRenderer testSubject0 = new OWLFunctionalSyntaxRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }

    public void shouldTestOWLObjectRenderer() throws Exception {
        OWLObjectRenderer testSubject0 = new OWLObjectRenderer(
                Utils.getMockOntology(), mock(Writer.class));
        testSubject0.write(mock(OWLAnnotation.class));
        testSubject0.write(OWLXMLVocabulary.COMMENT, mock(OWLObject.class));
        Set<OWLAxiom> result0 = testSubject0.writeAnnotations(Utils
                .mockOWLEntity());
        testSubject0.writeAnnotations(mock(OWLAxiom.class));
        testSubject0.writeOpenBracket();
        testSubject0.writeSpace();
        testSubject0.writeCloseBracket();
        testSubject0.setPrefixManager(mock(DefaultPrefixManager.class));
        testSubject0.setFocusedObject(mock(OWLObject.class));
        testSubject0.writePrefix("", "");
        testSubject0.writePrefixes();
        Set<OWLAxiom> result1 = testSubject0.writeAxioms(Utils.mockOWLEntity());
        Set<OWLAxiom> result2 = testSubject0.writeDeclarations(Utils
                .mockOWLEntity());
        testSubject0.writeAxiomStart(OWLXMLVocabulary.COMMENT,
                mock(OWLAxiom.class));
        testSubject0.writeAxiomEnd();
        testSubject0.writePropertyCharacteristic(OWLXMLVocabulary.COMMENT,
                mock(OWLAxiom.class), mock(OWLPropertyExpression.class));
        String result3 = testSubject0.toString();
    }
}
