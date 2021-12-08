package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
class SharedBlankNodeTestCase extends TestBase {

    OWLAnonymousIndividual indAnon = AnonymousIndividual();
    OWLNamedIndividual indTest = NamedIndividual(iriTest);

    static void testAnnotation(OWLOntology o) {
        o.individualsInSignature().forEach(
            individual -> assertEquals(2L, o.objectPropertyAssertionAxioms(individual).count()));
        o.annotations().map(a -> (OWLIndividual) a.getValue()).forEach(
            individual -> assertEquals(1L, o.dataPropertyAssertionAxioms(individual).count()));
    }

    @Test
    void shouldSaveOneIndividual() {
        OWLOntology ontology = createOntology();
        StringDocumentTarget rdfXmlSyntax = saveOntology(ontology, new RDFXMLDocumentFormat());
        StringDocumentTarget functionalSyntax =
            saveOntology(ontology, new FunctionalSyntaxDocumentFormat());
        testAnnotation(loadFrom(functionalSyntax, new FunctionalSyntaxDocumentFormat()));
        testAnnotation(loadFrom(rdfXmlSyntax, new RDFXMLDocumentFormat()));
    }

    @Test
    void shouldParseOneIndividual() {
        testAnnotation(loadFrom(TestFiles.oneIndividual, new RDFXMLDocumentFormat()));
    }

    OWLOntology createOntology() {
        OWLOntology ontology = create(iri(""));
        annotate(ontology, indAnon);
        ontology.add(dataAssertion(indAnon, "hello world"), objectAssertion(op1, indTest, indAnon),
            objectAssertion(op2, indTest, indAnon));
        return ontology;
    }

    static void annotate(OWLOntology o, OWLAnnotationValue annValue) {
        o.applyChange(new AddOntologyAnnotation(o, Annotation(AP, annValue)));
    }

    private static OWLAxiom dataAssertion(OWLIndividual individual, String litForm) {
        return DataPropertyAssertion(DP, individual, Literal(litForm));
    }

    private static OWLAxiom objectAssertion(OWLObjectProperty p, OWLIndividual individual,
        OWLIndividual j) {
        return ObjectPropertyAssertion(p, individual, j);
    }

    @Test
    void shouldRoundtripBlankNodeAnnotations() {
        OWLOntology o = loadFrom(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o1 = loadFrom(saveOntology(o, new FunctionalSyntaxDocumentFormat()),
            new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 =
            loadFrom(saveOntology(o1, new RDFXMLDocumentFormat()), new RDFXMLDocumentFormat());
        assertEquals(1L, o2.annotationAssertionAxioms(iri("http://E", "")).count());
        Stream<OWLAnnotationSubject> subjects = o2.annotationAssertionAxioms(iri("http://E", ""))
            .map(a -> (OWLAnnotationSubject) a.getValue());
        subjects.forEach(a -> assertEquals(1L, o2.annotationAssertionAxioms(a).count()));
    }

    @Test
    void shouldRemapUponReading() {
        OWLOntology o1 = loadFrom(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 = loadFrom(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(1, values1.size(), values1.toString());
        assertEquals(1, values2.size(), values2.toString());
        assertNotEquals(values1, values2);
    }

    @Test
    void shouldHaveOnlyOneAnonIndividual() {
        OWLOntology o1 = loadFrom(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o2 = loadFrom(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(1, values1.size(), values1.toString());
        assertEquals(1, values2.size(), values2.toString());
        assertNotEquals(values1, values2);
    }

    @Test
    void shouldNotRemapUponReloading() {
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(false);
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        values1.add(AnonymousIndividual("_:genid-nodeid-1058025095"));
        OWLOntology o1 = loadFrom(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
            .filter(a -> a instanceof OWLAnonymousIndividual));
        o1 = loadFrom(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
            .filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(1, values1.size(), values1.toString());
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(true);
    }

    @Test
    void shouldNotOutputNodeIdWhenNotNeeded() {
        OWLOntology o1 = loadFrom(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    void shouldOutputNodeIdEvenIfNotNeeded() {
        OWLOntology o1 = loadFrom(TestFiles.unconditionalId, new RDFXMLDocumentFormat());
        masterConfigurator.withSaveIdsForAllAnonymousIndividuals(true);
        try {
            StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
            assertTrue(result.toString().contains("rdf:nodeID"));
            OWLOntology reloaded = loadFrom(result, new RDFXMLDocumentFormat());
            StringDocumentTarget resaved = saveOntology(reloaded, new RDFXMLDocumentFormat());
            assertEquals(result.toString(), resaved.toString());
        } finally {
            // make sure the static variable is reset after the test
            masterConfigurator.withSaveIdsForAllAnonymousIndividuals(false);
        }
    }

    @Test
    void shouldOutputNodeIdWhenNeeded() {
        OWLOntology o1 = loadFrom(TestFiles.conditionalId, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
