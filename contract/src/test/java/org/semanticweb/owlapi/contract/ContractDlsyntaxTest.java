package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorerBase;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractDlsyntaxTest {
    public void shouldTestDLSyntaxHTMLOntologyFormat() {
        DLSyntaxHTMLOntologyFormat testSubject0 = new DLSyntaxHTMLOntologyFormat();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestDLSyntaxHTMLOntologyStorer() throws OWLOntologyStorageException {
        DLSyntaxHTMLOntologyStorer testSubject0 = new DLSyntaxHTMLOntologyStorer();
        boolean result0 = testSubject0.canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class), mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestDLSyntaxObjectRenderer() {
        DLSyntaxObjectRenderer testSubject0 = new DLSyntaxObjectRenderer();
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        testSubject0.setFocusedObject(mock(OWLObject.class));
        boolean result1 = testSubject0.isFocusedObject(mock(OWLObject.class));
    }

    public void shouldTestDLSyntaxOntologyFormat() {
        DLSyntaxOntologyFormat testSubject0 = new DLSyntaxOntologyFormat();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestDLSyntaxOntologyStorer() throws OWLOntologyStorageException {
        DLSyntaxOntologyStorer testSubject0 = new DLSyntaxOntologyStorer();
        boolean result0 = testSubject0.canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class), mock(OWLOntologyFormat.class));
    }

    public void shouldTestDLSyntaxOntologyStorerBase() throws OWLOntologyStorageException {
        DLSyntaxOntologyStorerBase testSubject0 = new DLSyntaxOntologyStorerBase() {
            private static final long serialVersionUID = 40000L;

            @Override
            public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
                return false;
            }
        };
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class), mock(OWLOntologyFormat.class));
        boolean result1 = testSubject0.canStoreOntology(mock(OWLOntologyFormat.class));
    }
}
