package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.obo2owl.Obo2OWLConstants;
import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

class Owl2OboTestCase extends OboFormatTestBasics {

    private static final String TEST1 = "test1";
    private static final String TEST_00022 = "TEST:0002";
    private static final String TEST_0002 = "TEST_0002";
    private static final String TEST_00012 = "TEST_0001";
    private static final String TERMID = "termid";
    private static final String MY_ONT = "http://purl.obolibrary.org/obo/MyOnt#";
    private static final String TEST_0001 = "TEST:0001";
    private static final String COMMENT = "Comment";

    private static void addLabelAndId(OWLNamedObject obj, String label, String idValue,
        OWLOntology o) {
        addAnnotation(obj, RDFSLabel(), Literal(label), o);
        OWLAnnotationProperty idProp =
            AnnotationProperty(OWLAPIObo2Owl.trTagToIRI(OboFormatTag.TAG_ID.getTag()));
        addAnnotation(obj, idProp, Literal(idValue), o);
    }

    private static void setAltId(OWLNamedObject obj, OWLOntology o) {
        addAnnotation(obj, AnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0100001.getIRI()),
            Literal(TEST_0001), o);
        addAnnotation(obj, AnnotationProperty(Obo2OWLConstants.IRI_IAO_0000231),
            Obo2OWLConstants.IRI_IAO_0000227, o);
        addAnnotation(obj, Deprecated(), Literal(true), o);
    }

    private static void addAnnotation(OWLNamedObject obj, OWLAnnotationProperty property,
        OWLAnnotationValue a, OWLOntology ont) {
        ont.add(AnnotationAssertion(property, obj.getIRI(), a));
    }

    @Test
    void testIRTsConversion() {
        OWLOntology ontology = create(iri(OBO, "test.owl"));
        convert(ontology);
        String ontId = OWLAPIOwl2Obo.getOntologyId(ontology);
        assertEquals("test", ontId);
        IRI iri = iri(OBO, "OBI_0000306");
        String idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertTrue("OBI:0000306".endsWith(idValue));
        iri = iri(OBO, "IAO_0000119");
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("IAO:0000119", idValue);
        iri = iri(OBO, "caro_part_of");
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("http://purl.obolibrary.org/obo/caro_part_of", idValue);
        iri = iri(MY_ONT, "_part_of");
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("MyOnt:part_of", idValue);
        iri = iri(MY_ONT, TERMID);
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals(TERMID, idValue);
        // unprefixed IDs from different ontology
        iri = iri(MY_ONT, TERMID);
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        // assertTrue("http://purl.obolibrary.org/obo/MyOnt#termid".equals(id));
        iri = TopObjectProperty().getIRI();
        idValue = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("owl:topObjectProperty", idValue);
    }

    @Test
    void testOwl2OboAltIdClass() {
        OWLOntology simple = create();
        // add class A
        OWLClass classA = Class(iri(Obo2OWLConstants.DEFAULT_IRI_PREFIX, TEST_00012));
        simple.add(Declaration(classA));
        // add a label and OBO style ID
        addLabelAndId(classA, TEST1, TEST_0001, simple);
        // add deprecated class B as an alternate ID for A
        OWLClass classB = Class(iri(Obo2OWLConstants.DEFAULT_IRI_PREFIX, TEST_0002));
        simple.add(Declaration(classB));
        setAltId(classB, simple);
        // add comment to alt_id class, which is not expressible in OBO
        addAnnotation(classB, RDFSComment(), Literal(COMMENT), simple);
        // translate to OBO
        OWLAPIOwl2Obo owl2obo = new OWLAPIOwl2Obo(simple.getOWLOntologyManager());
        OBODoc oboDoc = owl2obo.convert(simple);
        // check result: expect only one term frame for class TEST:0001 with
        // alt_id Test:0002
        Collection<Frame> termFrames = oboDoc.getTermFrames();
        assertEquals(1, termFrames.size());
        Frame frame = termFrames.iterator().next();
        assertEquals(TEST_0001, frame.getId());
        Collection<Clause> altIdClauses = frame.getClauses(OboFormatTag.TAG_ALT_ID);
        assertEquals(1, altIdClauses.size());
        String altId = altIdClauses.iterator().next().getValue(String.class);
        assertEquals(TEST_00022, altId);
        // roundtrip back to OWL, check that comment is still there
        OWLOntology roundTripped = convert(oboDoc);
        // three for the alt-id plus one
        assertEquals(4, roundTripped.annotationAssertionAxioms(classB.getIRI()).count());
        // for the comment
        Optional<OWLLiteral> comment = findComment(classB.getIRI(), roundTripped);
        assertTrue(comment.isPresent());
        assertEquals(COMMENT, comment.get().getLiteral());
    }

    protected Optional<OWLLiteral> findComment(IRI iri, OWLOntology roundTripped) {
        return roundTripped.annotationAssertionAxioms(iri)
            .filter(ax -> ax.getProperty().isComment()).map(ax -> ax.getValue().asLiteral())
            .filter(lit -> lit.isPresent()).findAny().orElse(Optional.empty());
    }

    @Test
    void testOwl2OboProperty() {
        OWLOntology simple = create();
        // add prop1
        OWLObjectProperty p1 = ObjectProperty(iri(Obo2OWLConstants.DEFAULT_IRI_PREFIX, TEST_00012));
        simple.add(Declaration(p1));
        // add label and OBO style id for
        addLabelAndId(p1, "prop1", TEST_0001, simple);
        // add deprecated prop 2 as an alternate ID for prop 1
        OWLObjectProperty p2 = ObjectProperty(iri(Obo2OWLConstants.DEFAULT_IRI_PREFIX, TEST_0002));
        simple.add(Declaration(p2));
        setAltId(p2, simple);
        // add comment to alt_id class, which is not expressible in OBO
        addAnnotation(p2, RDFSComment(), Literal(COMMENT), simple);
        // translate to OBO
        OWLAPIOwl2Obo owl2obo = new OWLAPIOwl2Obo(simple.getOWLOntologyManager());
        OBODoc oboDoc = owl2obo.convert(simple);
        // check result: expect only one typdef frame for prop TEST:0001 with
        // alt_id Test:0002
        Collection<Frame> termFrames = oboDoc.getTypedefFrames();
        assertEquals(1, termFrames.size());
        Frame frame = termFrames.iterator().next();
        assertEquals(TEST_0001, frame.getId());
        Collection<Clause> altIdClauses = frame.getClauses(OboFormatTag.TAG_ALT_ID);
        assertEquals(1, altIdClauses.size());
        String altId = altIdClauses.iterator().next().getValue(String.class);
        assertEquals(TEST_00022, altId);
        // roundtrip back to OWL, check that comment is still there
        OWLOntology roundTripped = convert(oboDoc);
        // three for the alt-id plus one for the comment
        assertEquals(4, roundTripped.annotationAssertionAxioms(p2.getIRI()).count());
        // for the comment
        Optional<OWLLiteral> comment = findComment(p2.getIRI(), roundTripped);
        assertTrue(comment.isPresent());
        assertEquals(COMMENT, comment.get().getLiteral());
    }
}
