package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Created by ses on 5/13/14.
 */
class AnnotatedPunningTestCase extends TestBase {

    OWLOntology makeOwlOntologyWithDeclarationsAndAnnotationAssertions(
        OWLAnnotationProperty annotationProperty, List<OWLEntity> entities) {
        Set<OWLAxiom> axioms = new HashSet<>();
        axioms.add(df.getOWLDeclarationAxiom(annotationProperty));
        for (OWLEntity entity : entities) {
            axioms.add(df.getOWLAnnotationAssertionAxiom(annotationProperty, entity.getIRI(),
                df.getOWLAnonymousIndividual()));
            axioms.add(df.getOWLDeclarationAxiom(entity));
        }
        return o(axioms);
    }

    String saveForRereading(OWLOntology o, OWLDocumentFormat format) {
        return saveOntology(o, format).toString();
    }

    static List<Arguments> allTests() {
        IRI iri = iri("http://localhost#", "a");
        List<? extends OWLEntity> entities = Arrays.asList(df.getOWLClass(iri),
            df.getOWLDatatype(iri), df.getOWLAnnotationProperty(iri), df.getOWLDataProperty(iri),
            df.getOWLObjectProperty(iri), df.getOWLNamedIndividual(iri));
        return Arrays.asList(Arguments.of(new RDFXMLDocumentFormat(), entities),
            Arguments.of(new TurtleDocumentFormat(), entities),
            Arguments.of(new FunctionalSyntaxDocumentFormat(), entities),
            Arguments.of(new ManchesterSyntaxDocumentFormat(), entities));
    }

    @ParameterizedTest
    @MethodSource("allTests")
    void runTestForAnnotationsOnPunnedEntitiesForFormat(OWLDocumentFormat format,
        List<OWLEntity> entities) {
        OWLOntology o = makeOwlOntologyWithDeclarationsAndAnnotationAssertions(AP, entities);
        for (int counter = 0; counter < 10; counter++) {
            String in = saveForRereading(o, format);
            m.removeOntology(o);
            o = loadOntologyFromString(in);
        }
        assertEquals(entities.size(), o.getAxiomCount(AxiomType.ANNOTATION_ASSERTION));
    }
}
