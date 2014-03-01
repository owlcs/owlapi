package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntax;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorerBase;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractDlsyntaxTest {

    @Test
    public void shouldTestDLSyntax() throws Exception {
        DLSyntax testSubject0 = DLSyntax.AND;
        String result0 = testSubject0.toString();
        DLSyntax[] result1 = DLSyntax.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestDLSyntaxHTMLOntologyFormat() throws Exception {
        DLSyntaxHTMLOntologyFormat testSubject0 = new DLSyntaxHTMLOntologyFormat();
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

    public void shouldTestDLSyntaxHTMLOntologyStorer() throws Exception {
        DLSyntaxHTMLOntologyStorer testSubject0 = new DLSyntaxHTMLOntologyStorer();
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
    public void shouldTestDLSyntaxObjectRenderer() throws Exception {
        DLSyntaxObjectRenderer testSubject0 = new DLSyntaxObjectRenderer();
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        testSubject0.setFocusedObject(mock(OWLObject.class));
        boolean result1 = testSubject0.isFocusedObject(mock(OWLObject.class));
        String result2 = testSubject0.toString();
    }

    public void shouldTestDLSyntaxOntologyFormat() throws Exception {
        DLSyntaxOntologyFormat testSubject0 = new DLSyntaxOntologyFormat();
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

    public void shouldTestDLSyntaxOntologyStorer() throws Exception {
        DLSyntaxOntologyStorer testSubject0 = new DLSyntaxOntologyStorer();
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

    public void shouldTestDLSyntaxOntologyStorerBase() throws Exception {
        DLSyntaxOntologyStorerBase testSubject0 = new DLSyntaxOntologyStorerBase() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
                return false;
            }
        };
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
    }
}
