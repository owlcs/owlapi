package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
class SharedBlankNodeTestCase extends TestBase {

    String NS = "urn:test";
    OWLAnonymousIndividual indAnon = AnonymousIndividual();
    OWLNamedIndividual indTest = NamedIndividual(IRI(NS + "#", "test"));

    static void testAnnotation(OWLOntology o) {
        for (OWLIndividual individual : o.getIndividualsInSignature()) {
            assertEquals(2, o.getObjectPropertyAssertionAxioms(individual).size());
        }
        for (OWLAnnotation annotation : o.getAnnotations()) {
            OWLIndividual individual = (OWLIndividual) annotation.getValue();
            assertEquals(1, o.getDataPropertyAssertionAxioms(individual).size());
        }
    }

    @Test
    void shouldSaveOneIndividual() {
        OWLOntology ontology = createOntology();
        StringDocumentTarget s = saveOntology(ontology, new RDFXMLDocumentFormat());
        StringDocumentTarget functionalSyntax =
            saveOntology(ontology, new FunctionalSyntaxDocumentFormat());
        testAnnotation(
            loadOntologyFromString(functionalSyntax, new FunctionalSyntaxDocumentFormat()));
        testAnnotation(loadOntologyFromString(s, new RDFXMLDocumentFormat()));
    }

    @Test
    void shouldParseOneIndividual() {
        testAnnotation(loadOntologyFromString(TestFiles.oneIndividual, new RDFXMLDocumentFormat()));
    }

    OWLOntology createOntology() {
        OWLOntology ontology = create(iri(NS, ""));
        annotate(ontology, NS + "#ann", indAnon);
        ontology.getOWLOntologyManager().addAxioms(ontology,
            set(dataAssertion(NS + "#p", indAnon, "hello world"),
                objectAssertion(NS + "#p1", indTest, indAnon),
                objectAssertion(NS + "#p2", indTest, indAnon)));
        return ontology;
    }

    static void annotate(OWLOntology o, String p, OWLAnnotationValue v) {
        o.getOWLOntologyManager()
            .applyChange(new AddOntologyAnnotation(o, Annotation(AnnotationProperty(IRI(p)), v)));
    }

    private static OWLAxiom dataAssertion(String p, OWLIndividual individual, String litForm) {
        return DataPropertyAssertion(DataProperty(IRI(p)), individual, Literal(litForm));
    }

    private static OWLAxiom objectAssertion(String p, OWLIndividual individual, OWLIndividual j) {
        return ObjectPropertyAssertion(ObjectProperty(IRI(p)), individual, j);
    }

    @Test
    void shouldRoundtripBlankNodeAnnotations() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(saveOntology(o, new FunctionalSyntaxDocumentFormat()),
                new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology(o1, new RDFXMLDocumentFormat()),
            new RDFXMLDocumentFormat());
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
            o2.getAnnotationAssertionAxioms(iri("http://E", ""));
        assertEquals(1, annotationAssertionAxioms.size());
        OWLAnnotationAssertionAxiom next = annotationAssertionAxioms.iterator().next();
        assertEquals(1,
            o2.getAnnotationAssertionAxioms((OWLAnnotationSubject) next.getValue()).size());
    }

    @Test
    void shouldRemapUponReading() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        Set<OWLAnnotationValue> values2 = new HashSet<>();
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        for (OWLAnnotationAssertionAxiom a : o2.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values2.add(value);
            }
        }
        assertEquals(1, values1.size(), values1.toString());
        assertEquals(1, values2.size(), values2.toString());
        assertNotEquals(values1, values2);
    }

    @Test
    void shouldHaveOnlyOneAnonIndividual() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        Set<OWLAnnotationValue> values2 = new HashSet<>();
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        for (OWLAnnotationAssertionAxiom a : o2.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values2.add(value);
            }
        }
        assertEquals(1, values1.size(), values1.toString());
        assertEquals(1, values2.size(), values2.toString());
        assertNotEquals(values1, values2);
    }

    @Test
    void shouldNotRemapUponReloading() {
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        try {
            Set<OWLAnnotationValue> values1 = new HashSet<>();
            values1
                .add(m.getOWLDataFactory().getOWLAnonymousIndividual("_:genid-nodeid-1058025095"));
            OWLOntology o1 =
                loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
            for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                OWLAnnotationValue value = a.getValue();
                if (value instanceof OWLAnonymousIndividual) {
                    values1.add(value);
                }
            }
            o1 = loadOntologyFromString(TestFiles.noRemapOnRead);
            for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                OWLAnnotationValue value = a.getValue();
                if (value instanceof OWLAnonymousIndividual) {
                    values1.add(value);
                }
            }
            assertEquals(1, values1.size(), values1.toString());
        } finally {
            // make sure config is restored
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    @Test
    void shouldNotOutputNodeIdWhenNotNeeded() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    void shouldOutputNodeIdEvenIfNotNeeded() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.unconditionalId, new RDFXMLDocumentFormat());
        AnonymousIndividualProperties.setSaveIdsForAllAnonymousIndividuals(true);
        try {
            StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
            assertTrue(result.toString().contains("rdf:nodeID"));
            OWLOntology reloaded = loadOntologyFromString(result, new RDFXMLDocumentFormat());
            StringDocumentTarget resaved = saveOntology(reloaded, new RDFXMLDocumentFormat());
            assertEquals(result.toString(), resaved.toString());
        } finally {
            // make sure the static variable is reset after the test
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    @Test
    void shouldOutputNodeIdWhenNeeded() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.conditionalId, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
