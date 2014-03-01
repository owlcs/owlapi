package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.ImportsClosureNotInProfileException;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.NullReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasonerRuntimeException;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.TimedConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractReasonerTest {

    @Test
    public void shouldTestAxiomNotInProfileException() throws Exception {
        AxiomNotInProfileException testSubject0 = new AxiomNotInProfileException(
                mock(OWLAxiom.class), mock(OWLProfile.class));
        OWLAxiom result0 = testSubject0.getAxiom();
        OWLProfile result1 = testSubject0.getProfile();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestBufferingMode() throws Exception {
        BufferingMode testSubject0 = BufferingMode.BUFFERING;
        BufferingMode[] result0 = BufferingMode.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestClassExpressionNotInProfileException()
            throws Exception {
        ClassExpressionNotInProfileException testSubject0 = new ClassExpressionNotInProfileException(
                Utils.mockAnonClass(), mock(OWLProfile.class));
        OWLClassExpression result0 = testSubject0.getClassExpression();
        OWLProfile result1 = testSubject0.getProfile();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestConsoleProgressMonitor() throws Exception {
        ConsoleProgressMonitor testSubject0 = new ConsoleProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestFreshEntitiesException() throws Exception {
        FreshEntitiesException testSubject0 = new FreshEntitiesException(
                Utils.mockSet(Utils.mockOWLEntity()));
        FreshEntitiesException testSubject1 = new FreshEntitiesException(
                Utils.mockOWLEntity());
        String result0 = testSubject0.getMessage();
        List<OWLEntity> result1 = testSubject0.getEntities();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestFreshEntityPolicy() throws Exception {
        FreshEntityPolicy testSubject0 = FreshEntityPolicy.ALLOW;
        FreshEntityPolicy[] result0 = FreshEntityPolicy.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestIllegalConfigurationException() throws Exception {
        IllegalConfigurationException testSubject0 = new IllegalConfigurationException(
                new RuntimeException(), mock(OWLReasonerConfiguration.class));
        IllegalConfigurationException testSubject1 = new IllegalConfigurationException(
                "", mock(OWLReasonerConfiguration.class));
        IllegalConfigurationException testSubject2 = new IllegalConfigurationException(
                "", new RuntimeException(),
                mock(OWLReasonerConfiguration.class));
        OWLReasonerConfiguration result0 = testSubject0.getConfiguration();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestImportsClosureNotInProfileException()
            throws Exception {
        ImportsClosureNotInProfileException testSubject0 = new ImportsClosureNotInProfileException(
                mock(OWLProfile.class));
        OWLProfile result0 = testSubject0.getProfile();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInconsistentOntologyException() throws Exception {
        InconsistentOntologyException testSubject0 = new InconsistentOntologyException();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestIndividualNodeSetPolicy() throws Exception {
        IndividualNodeSetPolicy testSubject0 = IndividualNodeSetPolicy.BY_NAME;
        IndividualNodeSetPolicy[] result0 = IndividualNodeSetPolicy.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInferenceType() throws Exception {
        InferenceType testSubject0 = InferenceType.CLASS_ASSERTIONS;
        String result0 = testSubject0.toString();
        InferenceType[] result1 = InferenceType.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInterfaceNode() throws Exception {
        Node<OWLObject> testSubject0 = Utils.mockNode(OWLObject.class);
        boolean result0 = testSubject0.contains(mock(OWLObject.class));
        int result1 = testSubject0.getSize();
        Set<OWLObject> result2 = testSubject0.getEntities();
        boolean result3 = testSubject0.isTopNode();
        boolean result4 = testSubject0.isBottomNode();
        Set<OWLObject> result5 = testSubject0
                .getEntitiesMinus(mock(OWLObject.class));
        Set<OWLObject> result6 = testSubject0.getEntitiesMinusTop();
        Set<OWLObject> result7 = testSubject0.getEntitiesMinusBottom();
        boolean result8 = testSubject0.isSingleton();
        OWLObject result9 = testSubject0.getRepresentativeElement();
        Iterator<OWLObject> result10 = testSubject0.iterator();
    }

    @Test
    public void shouldTestInterfaceNodeSet() throws Exception {
        NodeSet<OWLObject> testSubject0 = Utils.mockNodeSet(OWLObject.class);
        boolean result0 = testSubject0.isEmpty();
        boolean result1 = testSubject0.isSingleton();
        Set<OWLObject> result2 = testSubject0.getFlattened();
        boolean result3 = testSubject0.containsEntity(mock(OWLObject.class));
        boolean result4 = testSubject0.isTopSingleton();
        boolean result5 = testSubject0.isBottomSingleton();
        Set<Node<OWLObject>> result6 = testSubject0.getNodes();
        Iterator<Node<OWLObject>> result7 = testSubject0.iterator();
    }

    @Test
    public void shouldTestNullReasonerProgressMonitor() throws Exception {
        NullReasonerProgressMonitor testSubject0 = new NullReasonerProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLReasonerConfiguration() throws Exception {
        OWLReasonerConfiguration testSubject0 = mock(OWLReasonerConfiguration.class);
        ReasonerProgressMonitor result0 = testSubject0.getProgressMonitor();
        long result1 = testSubject0.getTimeOut();
        FreshEntityPolicy result2 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result3 = testSubject0
                .getIndividualNodeSetPolicy();
    }

    @Test
    public void shouldTestInterfaceOWLReasonerFactory() throws Exception {
        OWLReasonerFactory testSubject0 = mock(OWLReasonerFactory.class);
        String result0 = testSubject0.getReasonerName();
        OWLReasoner result1 = testSubject0.createNonBufferingReasoner(Utils
                .getMockOntology());
        OWLReasoner result2 = testSubject0.createNonBufferingReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
        OWLReasoner result3 = testSubject0.createReasoner(Utils
                .getMockOntology());
        OWLReasoner result4 = testSubject0.createReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
    }

    @Test
    public void shouldTestOWLReasonerRuntimeException() throws Exception {
        OWLReasonerRuntimeException testSubject0 = new OWLReasonerRuntimeException() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLReasonerRuntimeException testSubject1 = new OWLReasonerRuntimeException(
                new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLReasonerRuntimeException testSubject2 = new OWLReasonerRuntimeException(
                "") {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLReasonerRuntimeException testSubject3 = new OWLReasonerRuntimeException(
                "", new RuntimeException()) {

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
    public void shouldTestReasonerInternalException() throws Exception {
        ReasonerInternalException testSubject0 = new ReasonerInternalException(
                new RuntimeException());
        ReasonerInternalException testSubject1 = new ReasonerInternalException(
                "");
        ReasonerInternalException testSubject2 = new ReasonerInternalException(
                "", new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestReasonerInterruptedException() throws Exception {
        ReasonerInterruptedException testSubject0 = new ReasonerInterruptedException();
        ReasonerInterruptedException testSubject1 = new ReasonerInterruptedException(
                "");
        ReasonerInterruptedException testSubject2 = new ReasonerInterruptedException(
                "", new RuntimeException());
        ReasonerInterruptedException testSubject3 = new ReasonerInterruptedException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceReasonerProgressMonitor() throws Exception {
        ReasonerProgressMonitor testSubject0 = mock(ReasonerProgressMonitor.class);
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
    }

    public void shouldTestTimedConsoleProgressMonitor() throws Exception {
        TimedConsoleProgressMonitor testSubject0 = new TimedConsoleProgressMonitor();
        testSubject0.reasonerTaskStarted("");
        testSubject0.reasonerTaskStopped();
        testSubject0.reasonerTaskProgressChanged(0, 0);
        testSubject0.reasonerTaskBusy();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestTimeOutException() throws Exception {
        TimeOutException testSubject0 = new TimeOutException();
        TimeOutException testSubject1 = new TimeOutException("");
        TimeOutException testSubject2 = new TimeOutException("",
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestUnsupportedEntailmentTypeException() throws Exception {
        UnsupportedEntailmentTypeException testSubject0 = new UnsupportedEntailmentTypeException(
                mock(OWLAxiom.class));
        OWLAxiom result0 = testSubject0.getAxiom();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }
}
