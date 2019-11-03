package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
public class SharedBlankNodeTestCase extends TestBase {

    String NS = "urn:test";
    OWLAnonymousIndividual i = AnonymousIndividual();
    OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#", "test"));

    public static void testAnnotation(OWLOntology o) {
        o.individualsInSignature()
            .forEach(i -> assertEquals(2L, o.objectPropertyAssertionAxioms(i).count()));
        o.annotations().map(a -> (OWLIndividual) a.getValue())
            .forEach(i -> assertEquals(1L, o.dataPropertyAssertionAxioms(i).count()));
    }

    @Test
    public void shouldSaveOneIndividual() throws Exception {
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
    public void shouldParseOneIndividual() {
        testAnnotation(loadOntologyFromString(TestFiles.oneIndividual, new RDFXMLDocumentFormat()));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
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
    public void shouldRoundtripBlankNodeAnnotations() throws OWLOntologyStorageException {
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
    public void shouldRemapUponReading() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.remapOnReading, new FunctionalSyntaxDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(values1.toString(), 1, values1.size());
        assertEquals(values1.toString(), 1, values2.size());
        assertNotEquals(values1, values2);
    }

    @Test
    public void shouldHaveOnlyOneAnonIndividual() {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        OWLOntology o2 =
            loadOntologyFromString(TestFiles.oneAnonIndividuall, new RDFXMLDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
            .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(values1.toString(), 1, values1.size());
        assertEquals(values1.toString(), 1, values2.size());
        assertNotEquals(values1, values2);
    }

    @Test
    public void shouldNotRemapUponReloading() {
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
        assertEquals(values1.toString(), values1.size(), 1);
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(true);
    }

    @Test
    public void shouldNotOutputNodeIdWhenNotNeeded() throws OWLOntologyStorageException {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.noRemapOnRead, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    public void shouldOutputNodeIdEvenIfNotNeeded() throws OWLOntologyStorageException {
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
    public void shouldOutputNodeIdWhenNeeded() throws OWLOntologyStorageException {
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.conditionalId, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
