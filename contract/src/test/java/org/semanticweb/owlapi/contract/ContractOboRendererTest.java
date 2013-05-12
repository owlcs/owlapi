package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.coode.owlapi.obo.renderer.OBOExceptionHandler;
import org.coode.owlapi.obo.renderer.OBOFlatFileOntologyStorer;
import org.coode.owlapi.obo.renderer.OBOFlatFileRenderer;
import org.coode.owlapi.obo.renderer.OBORelationship;
import org.coode.owlapi.obo.renderer.OBORelationshipGenerator;
import org.coode.owlapi.obo.renderer.OBOStorageException;
import org.coode.owlapi.obo.renderer.OBOStorageIncompleteException;
import org.coode.owlapi.obo.renderer.OBOTagValuePairList;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOboRendererTest {
    @Test
    public void shouldTestInterfaceOBOExceptionHandler() throws Exception {
        OBOExceptionHandler testSubject0 = mock(OBOExceptionHandler.class);
        List<OBOStorageException> result0 = testSubject0.getExceptions();
        testSubject0.addException(mock(OBOStorageException.class));
    }

    public void shouldTestOBOFlatFileOntologyStorer() throws Exception {
        OBOFlatFileOntologyStorer testSubject0 = new OBOFlatFileOntologyStorer();
        boolean result0 = testSubject0.canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(), Utils.getMockOntology(),
                IRI("urn:aFake"), mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(), Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class), mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    public void shouldTestOBOFlatFileRenderer() throws Exception {
        OBOFlatFileRenderer testSubject0 = new OBOFlatFileRenderer() {};
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        List<OBOStorageException> result0 = testSubject0.getExceptions();
        testSubject0.addException(mock(OBOStorageException.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBORelationship() throws Exception {
        OBORelationship testSubject0 = new OBORelationship(mock(OWLObjectProperty.class),
                mock(OWLNamedIndividual.class));
        OBORelationship testSubject1 = new OBORelationship(mock(OWLObjectProperty.class),
                mock(OWLClass.class));
        OWLObjectProperty result0 = testSubject0.getProperty();
        int result1 = testSubject0.getCardinality();
        OWLEntity result2 = testSubject0.getFiller();
        int result3 = testSubject0.getMaxCardinality();
        int result4 = testSubject0.getMinCardinality();
        testSubject0.setMaxCardinality(0);
        testSubject0.setMinCardinality(0);
        testSubject0.setCardinality(0);
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBORelationshipGenerator() throws Exception {
        OBORelationshipGenerator testSubject0 = new OBORelationshipGenerator(
                mock(OBOExceptionHandler.class));
        testSubject0.clear();
        testSubject0.setClass(mock(OWLClass.class));
        Set<OBORelationship> result0 = testSubject0.getOBORelationships();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOStorageException() throws Exception {
        OBOStorageException testSubject0 = new OBOStorageException(mock(OWLObject.class),
                mock(OWLObject.class), "");
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOBOStorageIncompleteException() throws Exception {
        OBOStorageIncompleteException testSubject0 = new OBOStorageIncompleteException(
                Utils.mockList(mock(OBOStorageException.class)));
        String result0 = testSubject0.getMessage();
        List<OBOStorageException> result1 = testSubject0.getCauses();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestOBOTagValuePairList() throws Exception {
        OBOTagValuePairList testSubject0 = new OBOTagValuePairList(
                Utils.mockList(mock(OBOVocabulary.class)));
        testSubject0.write(mock(Writer.class));
        testSubject0.setDefault(mock(OBOVocabulary.class), "");
        testSubject0.setDefault(IRI("urn:aFake"), "");
        Set<String> result0 = testSubject0.getValues(mock(OBOVocabulary.class));
        testSubject0.addPair(mock(OBOVocabulary.class), "");
        testSubject0.addPair(IRI("urn:aFake"), "");
        testSubject0.setPair(mock(OBOVocabulary.class), "");
        String result1 = testSubject0.toString();
    }
}
