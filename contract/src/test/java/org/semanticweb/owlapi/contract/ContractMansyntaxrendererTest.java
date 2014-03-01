package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.OWLAxiomFilter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.AbstractRenderer;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxFrameRenderer;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOWLObjectRendererImpl;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorerException;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxRenderer;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.RendererEvent;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.RendererListener;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.RenderingDirector;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.SectionMap;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractMansyntaxrendererTest {

    @Test
    public void shouldTestAbstractRenderer() throws Exception {
        AbstractRenderer testSubject0 = new AbstractRenderer(
                mock(Writer.class), mock(ShortFormProvider.class));
        testSubject0.flush();
        testSubject0.setUseTabbing(false);
        testSubject0.setUseWrapping(false);
        boolean result0 = testSubject0.isUseWrapping();
        boolean result1 = testSubject0.isUseTabbing();
        String result2 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxFrameRenderer() throws Exception {
        ManchesterOWLSyntaxFrameRenderer testSubject0 = new ManchesterOWLSyntaxFrameRenderer(
                Utils.mockSet(Utils.getMockOntology()), mock(Writer.class),
                mock(ShortFormProvider.class));
        ManchesterOWLSyntaxFrameRenderer testSubject1 = new ManchesterOWLSyntaxFrameRenderer(
                Utils.getMockOntology(), mock(Writer.class),
                mock(ShortFormProvider.class));
        Set<OWLAxiom> result0 = testSubject0.write(mock(SWRLRule.class));
        Set<OWLAxiom> result1 = testSubject0.write(mock(OWLDatatype.class));
        Set<OWLAxiom> result2 = testSubject0.write(mock(OWLIndividual.class));
        Set<OWLAxiom> result3 = testSubject0.write(mock(OWLDataProperty.class));
        Set<OWLAxiom> result4 = testSubject0.write(Utils.mockObjectProperty());
        Set<OWLAxiom> result5 = testSubject0.write(mock(OWLClass.class));
        Set<OWLAxiom> result6 = testSubject0
                .write(mock(OWLAnnotationProperty.class));
        Set<OWLOntology> result7 = testSubject0.getOntologies();
        Set<OWLAnnotationAssertionAxiom> result8 = testSubject0
                .writeAnnotations(mock(OWLAnnotationSubject.class));
        testSubject0.writeComment("", "", false);
        testSubject0.writeComment("", false);
        testSubject0.setRenderingDirector(mock(RenderingDirector.class));
        testSubject0.addRendererListener(mock(RendererListener.class));
        testSubject0.removeRendererListener(mock(RendererListener.class));
        testSubject0.setAxiomFilter(mock(OWLAxiomFilter.class));
        testSubject0.clearFilteredAxiomTypes();
        testSubject0.addFilteredAxiomType(mock(AxiomType.class));
        testSubject0.setRenderExtensions(false);
        testSubject0.writeOntology();
        testSubject0.writePrefixMap();
        testSubject0.writeOntologyHeader(Utils.getMockOntology());
        testSubject0.writeSection(mock(ManchesterOWLSyntax.class),
                mock(SectionMap.class), "", false, mock(OWLOntology[].class));
        testSubject0.writeSection(mock(ManchesterOWLSyntax.class),
                Utils.mockCollection(), "", false, mock(OWLOntology[].class));
        testSubject0.writeSection(mock(ManchesterOWLSyntax.class));
        testSubject0.writeFullURI("");
        boolean result9 = testSubject0.isFiltered(mock(AxiomType.class));
        boolean result10 = testSubject0.isDisplayed(mock(OWLAxiom.class));
        Set<OWLAxiom> result11 = testSubject0.writeFrame(Utils.mockOWLEntity());
        testSubject0.flush();
        testSubject0.setUseTabbing(false);
        testSubject0.setUseWrapping(false);
        boolean result12 = testSubject0.isUseWrapping();
        boolean result13 = testSubject0.isUseTabbing();
        String result14 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxObjectRenderer() throws Exception {
        ManchesterOWLSyntaxObjectRenderer testSubject0 = new ManchesterOWLSyntaxObjectRenderer(
                mock(Writer.class), mock(ShortFormProvider.class));
        testSubject0.flush();
        testSubject0.setUseTabbing(false);
        testSubject0.setUseWrapping(false);
        boolean result0 = testSubject0.isUseWrapping();
        boolean result1 = testSubject0.isUseTabbing();
        String result2 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxOntologyStorer() throws Exception {
        ManchesterOWLSyntaxOntologyStorer testSubject0 = new ManchesterOWLSyntaxOntologyStorer();
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
    public void shouldTestManchesterOWLSyntaxOntologyStorerException()
            throws Exception {
        ManchesterOWLSyntaxOntologyStorerException testSubject0 = new ManchesterOWLSyntaxOntologyStorerException(
                "");
        ManchesterOWLSyntaxOntologyStorerException testSubject1 = new ManchesterOWLSyntaxOntologyStorerException(
                "", new RuntimeException());
        ManchesterOWLSyntaxOntologyStorerException testSubject2 = new ManchesterOWLSyntaxOntologyStorerException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOWLObjectRendererImpl()
            throws Exception {
        ManchesterOWLSyntaxOWLObjectRendererImpl testSubject0 = new ManchesterOWLSyntaxOWLObjectRendererImpl();
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxPrefixNameShortFormProvider()
            throws Exception {
        ManchesterOWLSyntaxPrefixNameShortFormProvider testSubject0 = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
                mock(OWLOntologyFormat.class));
        ManchesterOWLSyntaxPrefixNameShortFormProvider testSubject2 = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
                mock(DefaultPrefixManager.class));
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        String result1 = testSubject0.getShortForm(IRI("urn:aFake"));
        PrefixManager result2 = testSubject0.getPrefixManager();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxRenderer() throws Exception {
        ManchesterOWLSyntaxRenderer testSubject0 = new ManchesterOWLSyntaxRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestRendererEvent() throws Exception {
        RendererEvent testSubject0 = new RendererEvent(
                mock(ManchesterOWLSyntaxFrameRenderer.class),
                mock(OWLObject.class));
        testSubject0.writeComment("");
        ManchesterOWLSyntaxFrameRenderer result0 = testSubject0
                .getFrameRenderer();
        testSubject0.writeCommentOnNewLine("");
        OWLObject result1 = testSubject0.getFrameSubject();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceRendererListener() throws Exception {
        RendererListener testSubject0 = mock(RendererListener.class);
        testSubject0.frameRenderingPrepared("", mock(RendererEvent.class));
        testSubject0.frameRenderingStarted("", mock(RendererEvent.class));
        testSubject0.frameRenderingFinished("", mock(RendererEvent.class));
        testSubject0.sectionRenderingPrepared("", mock(RendererEvent.class));
        testSubject0.sectionRenderingStarted("", mock(RendererEvent.class));
        testSubject0.sectionRenderingFinished("", mock(RendererEvent.class));
        testSubject0.sectionItemPrepared("", mock(RendererEvent.class));
        testSubject0.sectionItemFinished("", mock(RendererEvent.class));
    }

    public void shouldTestInterfaceRenderingDirector() throws Exception {
        RenderingDirector testSubject0 = mock(RenderingDirector.class);
        boolean result0 = testSubject0.renderEmptyFrameSection(
                mock(ManchesterOWLSyntax.class), mock(OWLOntology[].class));
    }

    @Test
    public void shouldTestSectionMap() throws Exception {
        SectionMap testSubject0 = new SectionMap();
        testSubject0.add(mock(Object.class), mock(OWLAxiom.class));
        boolean result0 = testSubject0.isEmpty();
        testSubject0.remove(mock(Object.class));
        Collection<Object> result1 = testSubject0.getSectionObjects();
        Set<Set<OWLAnnotation>> result2 = testSubject0
                .getAnnotationsForSectionObject(mock(Object.class));
        String result3 = testSubject0.toString();
    }
}
