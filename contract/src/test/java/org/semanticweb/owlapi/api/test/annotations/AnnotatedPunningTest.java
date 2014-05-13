package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
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
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * Created by ses on 5/13/14.
 */
@SuppressWarnings("javadoc")
public class AnnotatedPunningTest {

    private OWLOntologyManager m;
    private OWLDataFactory df;

    @Before
    public void setUp() {
        m = OWLManager.createOWLOntologyManager();
        df = m.getOWLDataFactory();
    }

    @Test
    public void testAllTwoWayPuns() throws Exception {
        DefaultPrefixManager pm = new DefaultPrefixManager("http://localhost#");
        List<? extends OWLEntity> entities = Arrays.asList(
                df.getOWLClass("a", pm), df.getOWLDatatype("a", pm),
                df.getOWLAnnotationProperty("a", pm),
                df.getOWLDataProperty("a", pm),
                df.getOWLObjectProperty("a", pm),
                df.getOWLNamedIndividual("a", pm));
        OWLAnnotationProperty annotationProperty = df.getOWLAnnotationProperty(
                ":ap", pm);
        for (OWLEntity e1 : entities) {
            for (OWLEntity e2 : entities) {
                testFormats(annotationProperty, e1, e2);
            }
        }
    }

    @Test
    public void testMultiPun() throws Exception {
        DefaultPrefixManager pm = new DefaultPrefixManager("http://localhost#");
        OWLAnnotationProperty annotationProperty = df.getOWLAnnotationProperty(
                ":ap", pm);
        testFormats(annotationProperty, df.getOWLClass("a", pm),
                df.getOWLDatatype("a", pm),
                df.getOWLAnnotationProperty("a", pm),
                df.getOWLDataProperty("a", pm),
                df.getOWLObjectProperty("a", pm),
                df.getOWLNamedIndividual("a", pm));
    }

    public void testFormats(OWLAnnotationProperty annotationProperty,
            OWLEntity... entities) throws OWLOntologyCreationException,
            OWLOntologyStorageException, IllegalAccessException,
            InstantiationException {
        runTestForAnnotationsOnPunnedEntitiesForFormat(
                RDFXMLOntologyFormat.class, annotationProperty, entities);
        runTestForAnnotationsOnPunnedEntitiesForFormat(
                TurtleOntologyFormat.class, annotationProperty, entities);
        runTestForAnnotationsOnPunnedEntitiesForFormat(
                OWLFunctionalSyntaxOntologyFormat.class, annotationProperty,
                entities);
        runTestForAnnotationsOnPunnedEntitiesForFormat(
                ManchesterOWLSyntaxOntologyFormat.class, annotationProperty,
                entities);
    }

    public void runTestForAnnotationsOnPunnedEntitiesForFormat(
            Class<? extends PrefixOWLOntologyFormat> formatClass,
            OWLAnnotationProperty annotationProperty, OWLEntity... entities)
            throws OWLOntologyCreationException, OWLOntologyStorageException,
            IllegalAccessException, InstantiationException {
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
                annotationProperty, entities);
        for (int i = 0; i < 10; i++) {
            PrefixOWLOntologyFormat format = formatClass.newInstance();
            format.setPrefixManager(new DefaultPrefixManager(
                    "http://localhost#"));
            ByteArrayInputStream in = saveForRereading(o, format);
            m.removeOntology(o);
            o = m.loadOntologyFromOntologyDocument(in);
        }
        assertEquals("annotationCount", entities.length,
                o.getAxioms(AxiomType.ANNOTATION_ASSERTION).size());
    }

    public OWLOntology makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
            OWLAnnotationProperty annotationProperty, OWLEntity... entities)
            throws OWLOntologyCreationException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(df.getOWLDeclarationAxiom(annotationProperty));
        for (OWLEntity entity : entities) {
            axioms.add(df.getOWLAnnotationAssertionAxiom(annotationProperty,
                    entity.getIRI(), df.getOWLAnonymousIndividual()));
            axioms.add(df.getOWLDeclarationAxiom(entity));
        }
        return m.createOntology(axioms);
    }

    public ByteArrayInputStream saveForRereading(OWLOntology o,
            PrefixOWLOntologyFormat format) throws OWLOntologyStorageException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        m.saveOntology(o, format, out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
