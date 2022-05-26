package org.semanticweb.owlapi.apitest.annotations;

/**
 * Created by ses on 3/2/15.
 */
import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.pairs;

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
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

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
        final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        public TestSetting(Class<? extends OWLDocumentFormat> formatClass, OWLEntity... entities) {
            this.formatClass = formatClass;
            this.entities = entities;
        }
    }

    @Override
    public Description getDescription() {
        suiteDescription = Description.createSuiteDescription(testClass);
        addAllTests();
        return suiteDescription;
    }

    private void addAllTests() {
        OWLDataFactory df = OWLManager.getOWLDataFactory();
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
                testSettings.put(testDescription, new TestSetting(formatClass, v.i, v.j));
                suiteDescription.addChild(testDescription);
            });
            String name = "multiPun for " + formatClass.getName();
            Description testDescription = Description.createTestDescription(testClass, name);
            suiteDescription.addChild(testDescription);
            TestSetting setting =
                new TestSetting(formatClass, df.getOWLClass(iri), df.getOWLDatatype(iri),
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
        Class<? extends OWLDocumentFormat> formatClass, OWLOntologyManager ontologyManager,
        OWLEntity... entities) throws OWLOntologyCreationException, OWLOntologyStorageException,
        IllegalAccessException, InstantiationException {
        OWLDataFactory df = ontologyManager.getOWLDataFactory();
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
