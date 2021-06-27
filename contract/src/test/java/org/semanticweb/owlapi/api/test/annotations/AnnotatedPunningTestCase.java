package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Created by ses on 5/13/14.
 */
class AnnotatedPunningTestCase extends TestBase {

    static OWLOntology makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
        OWLAnnotationProperty annotationProperty, OWLOntologyManager manager,
        List<OWLEntity> entities) throws OWLOntologyCreationException {
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

    static ByteArrayInputStream saveForRereading(OWLOntology o, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        o.saveOntology(format, out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    static List<Arguments> allTests() {
        List<? extends OWLEntity> entities = Arrays.asList(df.getOWLClass("http://localhost#", "a"),
            df.getOWLDatatype("http://localhost#", "a"),
            df.getOWLAnnotationProperty("http://localhost#", "a"),
            df.getOWLDataProperty("http://localhost#", "a"),
            df.getOWLObjectProperty("http://localhost#", "a"),
            df.getOWLNamedIndividual("http://localhost#", "a"));
        return Arrays.asList(Arguments.of(new RDFXMLDocumentFormat(), entities),
            Arguments.of(new TurtleDocumentFormat(), entities),
            Arguments.of(new FunctionalSyntaxDocumentFormat(), entities),
            Arguments.of(new ManchesterSyntaxDocumentFormat(), entities));
    }

    @ParameterizedTest
    @MethodSource("allTests")
    void runTestForAnnotationsOnPunnedEntitiesForFormat(OWLDocumentFormat format,
        List<OWLEntity> entities) throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(AP, m, entities);
        for (int counter = 0; counter < 10; counter++) {
            ByteArrayInputStream in = saveForRereading(o, format);
            m.removeOntology(o);
            o = m.loadOntologyFromOntologyDocument(in);
        }
        assertEquals(entities.size(), o.axioms(AxiomType.ANNOTATION_ASSERTION).count());
    }
}
