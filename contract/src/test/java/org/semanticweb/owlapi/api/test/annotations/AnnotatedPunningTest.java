package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * Created by ses on 5/13/14.
 */
@SuppressWarnings("javadoc")
public class AnnotatedPunningTest extends TestBase {

    @Test
    public void testAllTwoWayPuns() throws Exception {
        IRI a = IRI.create("http://localhost#", "a");
        IRI ap = IRI.create("http://localhost#", "ap");
        List<? extends OWLEntity> entities = Arrays.asList(df.getOWLClass(a),
                df.getOWLDatatype(a), df.getOWLAnnotationProperty(a),
                df.getOWLDataProperty(a), df.getOWLObjectProperty(a),
                df.getOWLNamedIndividual(a));
        OWLAnnotationProperty annotationProperty = df
                .getOWLAnnotationProperty(ap);
        for (OWLEntity e1 : entities) {
            for (OWLEntity e2 : entities) {
                testFormats(annotationProperty, e1, e2);
            }
        }
    }

    @Test
    public void testMultiPun() throws Exception {
        IRI a = IRI.create("http://localhost#", "a");
        IRI ap = IRI.create("http://localhost#", "ap");
        OWLAnnotationProperty annotationProperty = df
                .getOWLAnnotationProperty(ap);
        testFormats(annotationProperty, df.getOWLClass(a),
                df.getOWLDatatype(a), df.getOWLAnnotationProperty(a),
                df.getOWLDataProperty(a), df.getOWLObjectProperty(a),
                df.getOWLNamedIndividual(a));
    }

    public void testFormats(@Nonnull OWLAnnotationProperty annotationProperty,
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
            @Nonnull OWLAnnotationProperty annotationProperty,
            OWLEntity... entities) throws OWLOntologyCreationException,
            OWLOntologyStorageException, IllegalAccessException,
            InstantiationException {
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
                annotationProperty, entities);
        for (int i = 0; i < 10; i++) {
            PrefixOWLOntologyFormat format = formatClass.newInstance();
            format.setPrefixManager(new DefaultPrefixManager(null, null,
                    "http://localhost#"));
            o = roundTrip(o);
        }
        assertEquals("annotationCount", entities.length,
                o.getAxioms(AxiomType.ANNOTATION_ASSERTION).size());
    }

    @Nonnull
    public OWLOntology makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
            @Nonnull OWLAnnotationProperty annotationProperty,
            OWLEntity... entities) throws OWLOntologyCreationException {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(df.getOWLDeclarationAxiom(annotationProperty));
        for (OWLEntity entity : entities) {
            axioms.add(df.getOWLAnnotationAssertionAxiom(annotationProperty,
                    entity.getIRI(), df.getOWLAnonymousIndividual()));
            axioms.add(df.getOWLDeclarationAxiom(entity));
        }
        return m.createOntology(axioms);
    }
}
