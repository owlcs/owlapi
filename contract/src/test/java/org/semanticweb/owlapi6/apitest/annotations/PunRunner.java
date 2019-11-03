package org.semanticweb.owlapi6.apitest.annotations;

/**
 * Created by ses on 3/2/15.
 */
import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.pairs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

public class PunRunner extends org.junit.runner.Runner {

    private final Class<?> testClass;
    private Description suiteDescription;
    private final Map<Description, TestSetting> testSettings = new HashMap<>();

    public PunRunner(Class<?> testClass) {
        this.testClass = testClass;
    }

    class TestSetting {

        OWLEntity[] entities;
        Class<? extends OWLDocumentFormat> formatClass;
        OWLOntologyManager manager;

        public TestSetting(Class<? extends OWLDocumentFormat> formatClass, OWLOntologyManager m,
            OWLEntity... entities) {
            this.formatClass = formatClass;
            this.entities = entities;
            manager = m;
        }
    }

    @Override
    public Description getDescription() {
        suiteDescription = Description.createSuiteDescription(testClass);
        addAllTests();
        return suiteDescription;
    }

    private void addAllTests() {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLDataFactory df = m.getOWLDataFactory();
        IRI iri = df.getIRI("http://localhost#", "a");
        List<? extends OWLEntity> entities = Arrays.asList(df.getOWLClass(iri),
            df.getOWLDatatype(iri), df.getOWLAnnotationProperty(iri), df.getOWLDataProperty(iri),
            df.getOWLObjectProperty(iri), df.getOWLNamedIndividual(iri));
        List<Class<? extends OWLDocumentFormat>> formats = new ArrayList<>();
        formats.add(RDFXMLDocumentFormat.class);
        formats.add(TurtleDocumentFormat.class);
        formats.add(FunctionalSyntaxDocumentFormat.class);
        formats.add(ManchesterSyntaxDocumentFormat.class);
        for (Class<? extends OWLDocumentFormat> formatClass : formats) {
            pairs(entities).forEach(v -> {
                String formatClassName = formatClass.getName();
                int i1 = formatClassName.lastIndexOf('.');
                if (i1 > -1) {
                    formatClassName = formatClassName.substring(i1 + 1);
                }
                String name = String.format("%sVs%sFor%s", v.i.getEntityType(), v.j.getEntityType(),
                    formatClassName);
                Description testDescription = Description.createTestDescription(testClass, name);
                testSettings.put(testDescription, new TestSetting(formatClass, m, v.i, v.j));
                suiteDescription.addChild(testDescription);
            });
            String name = "multiPun for " + formatClass.getName();
            Description testDescription = Description.createTestDescription(testClass, name);
            suiteDescription.addChild(testDescription);
            TestSetting setting =
                new TestSetting(formatClass, m, df.getOWLClass(iri), df.getOWLDatatype(iri),
                    df.getOWLAnnotationProperty(iri), df.getOWLDataProperty(iri),
                    df.getOWLObjectProperty(iri), df.getOWLNamedIndividual(iri));
            testSettings.put(testDescription, setting);
        }
    }

    /**
     * Run the tests for this runner.
     *
     * @param notifier will be notified of events while tests are being run--tests being started,
     *        finishing, and failing
     */
    @Override
    public void run(@Nullable RunNotifier notifier) {
        checkNotNull(notifier);
        assert notifier != null;
        for (Map.Entry<Description, TestSetting> entry : testSettings.entrySet()) {
            Description description = entry.getKey();
            notifier.fireTestStarted(description);
            try {
                TestSetting setting = entry.getValue();
                runTestForAnnotationsOnPunnedEntitiesForFormat(setting.formatClass, setting.manager,
                    setting.entities);
            } catch (Throwable t) {
                notifier.fireTestFailure(new Failure(description, t));
            } finally {
                notifier.fireTestFinished(description);
            }
        }
    }

    public void runTestForAnnotationsOnPunnedEntitiesForFormat(
        Class<? extends OWLDocumentFormat> formatClass, OWLOntologyManager m, OWLEntity... entities)
        throws OWLOntologyCreationException, OWLOntologyStorageException, IllegalAccessException,
        InstantiationException {
        OWLOntologyManager ontologyManager;
        OWLDataFactory df;
        synchronized (OWLManager.class) {
            ontologyManager = m;
            ontologyManager.clearOntologies();
            df = ontologyManager.getOWLDataFactory();
        }
        OWLAnnotationProperty annotationProperty =
            df.getOWLAnnotationProperty("http://localhost#", ":ap");
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(annotationProperty,
            ontologyManager, entities);
        o.getPrefixManager().withDefaultPrefix("http://localhost#");
        for (int i = 0; i < 10; i++) {
            try {
                Constructor<? extends OWLDocumentFormat> constructor = formatClass.getConstructor();
                StringDocumentTarget in = saveForRereading(o, constructor.newInstance());
                ontologyManager.removeOntology(o);
                o = ontologyManager.loadOntologyFromOntologyDocument(new StringDocumentSource(
                    in.toString(), o.getOntologyID().getOntologyIRI().get().toString(),
                    constructor.newInstance(), null));
            } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals("annotationCount", entities.length,
            o.axioms(AxiomType.ANNOTATION_ASSERTION).count());
    }

    public static OWLOntology makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
        OWLAnnotationProperty annotationProperty, OWLOntologyManager manager, OWLEntity... entities)
        throws OWLOntologyCreationException {
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        axioms.add(dataFactory.getOWLDeclarationAxiom(annotationProperty));
        for (OWLEntity entity : entities) {
            axioms.add(dataFactory.getOWLAnnotationAssertionAxiom(annotationProperty,
                entity.getIRI(), dataFactory.getOWLAnonymousIndividual()));
            axioms.add(dataFactory.getOWLDeclarationAxiom(entity));
        }
        return manager.createOntology(axioms);
    }

    public static StringDocumentTarget saveForRereading(OWLOntology o, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        StringDocumentTarget out = new StringDocumentTarget();
        o.saveOntology(format, out);
        return out;
    }
}
