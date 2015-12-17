package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * Created by ses on 3/2/15.
 */
public class PunRunner extends org.junit.runner.Runner {

    private final Class<?> testClass;

    public PunRunner(Class<?> testClass) {
        this.testClass = testClass;
    }

    class TestSetting {

        OWLEntity[] entities;
        Class<? extends PrefixDocumentFormat> formatClass;

        public TestSetting(Class<? extends PrefixDocumentFormat> formatClass,
                OWLEntity... entities) {
            this.formatClass = formatClass;
            this.entities = entities;
        }
    }

    private Description suiteDescription;
    private Map<Description, TestSetting> testSettings = new HashMap<>();

    @Override
    public Description getDescription() {
        suiteDescription = Description.createSuiteDescription(testClass);
        addAllTests();
        return suiteDescription;
    }

    private void addAllTests() {
        DefaultPrefixManager pm = new DefaultPrefixManager(null, null,
                "http://localhost#");
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        List<? extends OWLEntity> entities = Arrays.asList(
                df.getOWLClass("a", pm), df.getOWLDatatype("a", pm),
                df.getOWLAnnotationProperty("a", pm),
                df.getOWLDataProperty("a", pm),
                df.getOWLObjectProperty("a", pm),
                df.getOWLNamedIndividual("a", pm));
        List<Class<? extends PrefixDocumentFormat>> formats = new ArrayList<>();
        formats.add(RDFXMLDocumentFormat.class);
        formats.add(TurtleDocumentFormat.class);
        formats.add(FunctionalSyntaxDocumentFormat.class);
        formats.add(ManchesterSyntaxDocumentFormat.class);
        for (Class<? extends PrefixDocumentFormat> formatClass : formats) {
            for (int i = 0; i < entities.size(); i++) {
                OWLEntity e1 = entities.get(i);
                for (int j = i + 1; j < entities.size(); j++) {
                    OWLEntity e2 = entities.get(j);
                    String formatClassName = formatClass.getName();
                    int i1 = formatClassName.lastIndexOf('.');
                    if (i1 > -1) {
                        formatClassName = formatClassName.substring(i1 + 1);
                    }
                    String name = String.format("%sVs%sFor%s",
                            e1.getEntityType(), e2.getEntityType(),
                            formatClassName);
                    Description testDescription = Description
                            .createTestDescription(testClass, name);
                    testSettings.put(testDescription, new TestSetting(
                            formatClass, e1, e2));
                    suiteDescription.addChild(testDescription);
                }
            }
            String name = "multiPun for " + formatClass.getName();
            Description testDescription = Description.createTestDescription(
                    testClass, name);
            suiteDescription.addChild(testDescription);
            TestSetting setting = new TestSetting(formatClass, df.getOWLClass(
                    "a", pm), df.getOWLDatatype("a", pm),
                    df.getOWLAnnotationProperty("a", pm),
                    df.getOWLDataProperty("a", pm), df.getOWLObjectProperty(
                            "a", pm), df.getOWLNamedIndividual("a", pm));
            testSettings.put(testDescription, setting);
        }
    }

    /**
     * Run the tests for this runner.
     *
     * @param notifier
     *        will be notified of events while tests are being run--tests being
     *        started, finishing, and failing
     */
    @Override
    public void run(RunNotifier notifier) {
        for (Map.Entry<Description, TestSetting> entry : testSettings
                .entrySet()) {
            Description description = entry.getKey();
            notifier.fireTestStarted(description);
            try {
                TestSetting setting = entry.getValue();
                runTestForAnnotationsOnPunnedEntitiesForFormat(
                        setting.formatClass, setting.entities);
            } catch (Throwable t) {
                notifier.fireTestFailure(new Failure(description, t));
            } finally {
                notifier.fireTestFinished(description);
            }
        }
    }

    /**
     * @param formatClass
     *        format class
     * @param entities
     *        entities
     * @throws OWLOntologyCreationException
     *         ontology cannot be created
     * @throws OWLOntologyStorageException
     *         ontology cannot be stored
     * @throws IllegalAccessException
     *         class cannot be accessed
     * @throws InstantiationException
     *         class cannot be instantiated
     */
    public void runTestForAnnotationsOnPunnedEntitiesForFormat(
            Class<? extends PrefixDocumentFormat> formatClass,
            OWLEntity... entities) throws OWLOntologyCreationException,
            OWLOntologyStorageException, IllegalAccessException,
            InstantiationException {
        OWLOntologyManager ontologyManager;
        OWLDataFactory df;
        synchronized (OWLManager.class) {
            ontologyManager = OWLManager.createOWLOntologyManager();
            df = ontologyManager.getOWLDataFactory();
        }
        OWLAnnotationProperty annotationProperty = df.getOWLAnnotationProperty(
                ":ap",
                new DefaultPrefixManager(null, null, "http://localhost#"));
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
                annotationProperty, ontologyManager, entities);
        for (int i = 0; i < 10; i++) {
            PrefixDocumentFormat format = formatClass.newInstance();
            format.setPrefixManager(new DefaultPrefixManager(null, null,
                    "http://localhost#"));
            ByteArrayInputStream in = saveForRereading(o, format,
                    ontologyManager);
            ontologyManager.removeOntology(o);
            o = ontologyManager.loadOntologyFromOntologyDocument(in);
        }
        assertEquals("annotationCount", entities.length,
                o.getAxioms(AxiomType.ANNOTATION_ASSERTION).size());
    }

    /**
     * @param annotationProperty
     *        annotation property
     * @param manager
     *        manager
     * @param entities
     *        entities
     * @return new ontology
     * @throws OWLOntologyCreationException
     *         if the ontology cannot be created
     */
    @Nonnull
    public static OWLOntology
            makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
                    @Nonnull OWLAnnotationProperty annotationProperty,
                    OWLOntologyManager manager, OWLEntity... entities)
                    throws OWLOntologyCreationException {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        axioms.add(dataFactory.getOWLDeclarationAxiom(annotationProperty));
        for (OWLEntity entity : entities) {
            axioms.add(dataFactory.getOWLAnnotationAssertionAxiom(
                    annotationProperty, entity.getIRI(),
                    dataFactory.getOWLAnonymousIndividual()));
            axioms.add(dataFactory.getOWLDeclarationAxiom(entity));
        }
        return manager.createOntology(axioms);
    }

    /**
     * @param o
     *        ontology to save
     * @param format
     *        format to use
     * @param manager
     *        manager
     * @return input stream with the saved ontology
     * @throws OWLOntologyStorageException
     *         if the ontology cannot be saved
     */
    @Nonnull
    public static ByteArrayInputStream saveForRereading(@Nonnull OWLOntology o,
            @Nonnull PrefixDocumentFormat format, OWLOntologyManager manager)
            throws OWLOntologyStorageException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(o, format, out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
