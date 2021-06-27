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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
class SharedBlankNodeTestCase extends TestBase {

    String NS = "urn:test";
    OWLAnonymousIndividual i = AnonymousIndividual();
    OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#", "test"));

    static void testAnnotation(OWLOntology o) {
        o.individualsInSignature()
            .forEach(i -> assertEquals(2L, o.objectPropertyAssertionAxioms(i).count()));
        o.annotations().map(a -> (OWLIndividual) a.getValue())
            .forEach(i -> assertEquals(1L, o.dataPropertyAssertionAxioms(i).count()));
    }

    @Test
    void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        StringDocumentTarget s = saveOntology(ontology, new RDFXMLDocumentFormat());
        StringDocumentTarget functionalSyntax =
            saveOntology(ontology, new FunctionalSyntaxDocumentFormat());
        testAnnotation(
            loadOntologyFromString(functionalSyntax, new FunctionalSyntaxDocumentFormat()));
        OWLOntology o1 = loadOntologyFromString(s, new RDFXMLDocumentFormat());
        testAnnotation(o1);
    }

    @Test
    void shouldParseOneIndividual() {
        testAnnotation(loadOntologyFromString(TestFiles.oneIndividual, new RDFXMLDocumentFormat()));
    }

    OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntology ontology = m.createOntology(IRI(NS, ""));
        annotate(ontology, NS + "#ann", i);
        ontology.add(
            //
            dataAssertion(NS + "#p", i, "hello world"),
            //
            objectAssertion(NS + "#p1", ind, i),
            //
            objectAssertion(NS + "#p2", ind, i));
        return ontology;
    }

    private static void annotate(OWLOntology o, String p, OWLAnnotationValue v) {
        o.applyChange(new AddOntologyAnnotation(o, Annotation(AnnotationProperty(IRI(p)), v)));
    }

    private static OWLAxiom dataAssertion(String p, OWLIndividual i, String l) {
        return DataPropertyAssertion(DataProperty(IRI(p)), i, Literal(l));
    }

    private static OWLAxiom objectAssertion(String p, OWLIndividual i, OWLIndividual j) {
        return ObjectPropertyAssertion(ObjectProperty(IRI(p)), i, j);
    }

    @Test
    void shouldRoundtripBlankNodeAnnotations() throws OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(saveOntology(o, new FunctionalSyntaxDocumentFormat()),
                new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology(o1, new RDFXMLDocumentFormat()),
            new RDFXMLDocumentFormat());
        assertEquals(1L, o2.annotationAssertionAxioms(IRI("http://E", "")).count());
        Stream<OWLAnnotationSubject> s = o2.annotationAssertionAxioms(IRI("http://E", ""))
            .map(a -> (OWLAnnotationSubject) a.getValue());
        s.forEach(a -> assertEquals(1L, o2.annotationAssertionAxioms(a).count()));
    }

    @Test
    void shouldRemapUponReading() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
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
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
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
        values1.add(m.getOWLDataFactory().getOWLAnonymousIndividual("_:genid-nodeid-1058025095"));
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
            .filter(a -> a instanceof OWLAnonymousIndividual));
        o1 = loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
            .filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(1, values1.size(), values1.toString());
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(true);
    }

    @Test
    void shouldNotOutputNodeIdWhenNotNeeded() throws OWLOntologyStorageException {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    void shouldOutputNodeIdEvenIfNotNeeded() throws OWLOntologyStorageException {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.unconditionalId, new RDFXMLDocumentFormat());
        masterConfigurator.withSaveIdsForAllAnonymousIndividuals(true);
        try {
            StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
            assertTrue(result.toString().contains("rdf:nodeID"));
            OWLOntology reloaded = loadOntologyFromString(result, new RDFXMLDocumentFormat());
            StringDocumentTarget resaved = saveOntology(reloaded, new RDFXMLDocumentFormat());
            assertEquals(result.toString(), resaved.toString());
        } finally {
            // make sure the static variable is reset after the test
            masterConfigurator.withSaveIdsForAllAnonymousIndividuals(false);
        }
    }

    @Test
    void shouldOutputNodeIdWhenNeeded() throws OWLOntologyStorageException {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.conditionalId, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
