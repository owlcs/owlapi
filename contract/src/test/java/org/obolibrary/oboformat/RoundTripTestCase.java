package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.obo2owl.Obo2OWLConstants;
import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

import com.google.common.base.Optional;

class RoundTripTestCase extends RoundTripTestBasics {

    private static final String REGULATES = "regulates";

    private static void checkAsAltId(@Nonnull IRI iri, OWLOntology ont, String replacedBy) {
        Set<OWLAnnotationAssertionAxiom> annotations = ont.getAnnotationAssertionAxioms(iri);
        assertTrue(annotations.stream().anyMatch(ax -> ax.getProperty().isDeprecated()));
        assertTrue(annotations.stream()
            .filter(ax -> ax.getProperty().getIRI().equals(Obo2OWLConstants.IRI_IAO_0000231))
            .map(ax -> ax.getValue().asIRI()).filter(Optional::isPresent)
            .anyMatch(p -> Obo2OWLConstants.IRI_IAO_0000227.equals(p.get())));

        String altId = annotations.stream()
            .filter(ax -> Obo2OWLVocabulary.IRI_IAO_0100001.sameIRI(ax.getProperty()))
            .map(ax -> ax.getValue().asIRI()).filter(Optional::isPresent)
            .map(p -> OWLAPIOwl2Obo.getIdentifier(p.get())).findAny().orElse(null);
        assertEquals(replacedBy, altId);
    }

    @Test
    void testAltIds() {
        OBODoc input = parseOBOFile("alt_id_test.obo");
        OWLOntology owl = convert(input);
        // check round trip
        OBODoc output = convert(owl);
        String outObo = renderOboToString(output);
        assertEquals(readResource("alt_id_test.obo").trim(), outObo.trim());
        // check owl
        // check that both alt_id is declared as deprecated class and has
        // appropriate annotations
        IRI alt_id_t1 = iri(OBO, "TEST_1000");
        IRI alt_id_r1 = iri(OBO, "TEST_REL_1000");
        checkAsAltId(alt_id_t1, owl, "TEST:0001");
        checkAsAltId(alt_id_r1, owl, "TEST_REL:0001");
    }

    @Test
    void testRoundTripCardinality() {
        // create minimal ontology
        OBODoc oboDocSource = parseOBOFile("roundtrip_cardinality.obo");
        // convert to OWL and retrieve def
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(m1);
        OWLOntology owlOntology = convert(oboDocSource, bridge);
        OWLDataFactory factory = owlOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass cl = factory.getOWLClass(bridge.oboIdToIRI("PR:000027136"));
        // Relations
        boolean foundRel1 = false;
        boolean foundRel2 = false;
        List<OWLSubClassOfAxiom> axioms = asList(owlOntology.getSubClassAxiomsForSubClass(cl));
        assertEquals(3, axioms.size());
        for (OWLSubClassOfAxiom axiom : axioms) {
            OWLClassExpression superClass = axiom.getSuperClass();
            if (superClass instanceof OWLObjectExactCardinality) {
                OWLObjectExactCardinality cardinality = (OWLObjectExactCardinality) superClass;
                OWLClassExpression filler = cardinality.getFiller();
                assertFalse(filler.isAnonymous());
                IRI iri = filler.asOWLClass().getIRI();
                if (iri.equals(bridge.oboIdToIRI("PR:000005116"))) {
                    foundRel1 = true;
                    assertEquals(1, cardinality.getCardinality());
                } else if (iri.equals(bridge.oboIdToIRI("PR:000027122"))) {
                    foundRel2 = true;
                    assertEquals(2, cardinality.getCardinality());
                }
            }
        }
        assertTrue(foundRel1);
        assertTrue(foundRel2);
        // convert back to OBO
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(OWLManager.createOWLOntologyManager());
        OBODoc convertedOboDoc = owl2Obo.convert(owlOntology);
        Frame convertedFrame = convertedOboDoc.getTermFrame("PR:000027136");
        assert convertedFrame != null;
        Collection<Clause> clauses = convertedFrame.getClauses(OboFormatTag.TAG_RELATIONSHIP);
        // check that round trip still contains relationships
        assertEquals(2, clauses.size());
        for (Clause clause : clauses) {
            Collection<QualifierValue> qualifierValues = clause.getQualifierValues();
            assertEquals(1, qualifierValues.size());
            QualifierValue value = qualifierValues.iterator().next();
            assertEquals("cardinality", value.getQualifier());
            if (clause.getValue2().equals("PR:000005116")) {
                assertEquals("1", value.getValue());
            } else if (clause.getValue2().equals("PR:000027122")) {
                assertEquals("2", value.getValue());
            }
        }
    }

    @Test
    void testRoundTripLabeledXrefs() {
        OBODoc source = parseOBOFile("labeled_xrefs.obo");
        String written = renderOboToString(source);
        OBODoc parsed = parseOboToString(written);
        List<Diff> diffs = OBODocDiffer.getDiffs(source, parsed);
        assertEquals(0, diffs.size());
    }

    @Test
    void testDefinitionsMultipleDefXref() {
        OWLAnnotationProperty hasDbXref = df.getOWLAnnotationProperty(
            iri("http://www.geneontology.org/formats/oboInOwl#", "hasDbXref"));
        OWLOntology owlOnt = convertOBOFile("multiple_def_xref_test.obo");
        AtomicInteger n = new AtomicInteger(0);
        owlOnt.getAxioms().stream().forEach(ax -> ax.getAnnotations(hasDbXref).forEach(a -> {
            OWLLiteral v = (OWLLiteral) a.getValue();
            // expect this twice, as we have annotations on synonyms
            if (v.getLiteral().equals("BTO:0001750")) {
                n.incrementAndGet();
            }
            if (v.getLiteral().equals("Wikipedia:Mandibular_condyle")) {
                n.incrementAndGet();
            }
        }));
        assertEquals(3, n.intValue());
    }

    @Test
    void testWriteNamespaceIdRule() {
        OBODoc oboDoc = parseOBOFile("namespace-id-rule.obo");
        String oboString = renderOboToString(oboDoc);
        assertTrue(oboString.contains("\nnamespace-id-rule: * test:$sequence(7,0,9999999)$\n"));
    }

    @Test
    void testWriteReadConvertedOWLNamespaceIdRule() {
        OBODoc oboDoc = parseOBOFile("namespace-id-rule.obo");
        OWLOntology owlOntology = convert(oboDoc);
        StringDocumentTarget documentTarget = saveOntology(owlOntology, new OWLXMLDocumentFormat());
        String owlString = documentTarget.toString();
        OWLOntology reloadedOwl = loadOntologyFromString(owlString, new OWLXMLDocumentFormat());
        assertEquals(owlOntology.getAxiomCount(), reloadedOwl.getAxiomCount());
    }

    @Test
    void shouldRoundTripVersionInfo() {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/myont.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n\n"
            + "Ontology(<http://purl.obolibrary.org/obo/myont.owl>\n"
            + "Annotation(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"1.2\")\n"
            + "Annotation(owl:versionInfo \"2020-06-30\")\n"
            + "Declaration(AnnotationProperty(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion>))\n"
            + "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"has_obo_format_version\")\n)";

        OWLOntology o = loadOntologyFromString(in, new FunctionalSyntaxDocumentFormat());
        StringDocumentTarget saved = saveOntology(o, new OBODocumentFormat());
        OWLOntology o1 = loadOntologyFromString(saved, new OBODocumentFormat());
        equal(o, o1);

        OBODoc oboDoc1 = convert(o);
        // write OBO
        String expected = "format-version: 1.2\n" + "ontology: myont\n"
            + "property_value: owl:versionInfo \"2020-06-30\" xsd:string";
        String actual = renderOboToString(oboDoc1).trim();
        assertEquals(expected, actual);
        // parse OBO
        OBODoc oboDoc2 = parseOboToString(actual);
        assertEquals(expected, renderOboToString(oboDoc2).trim());

        List<Diff> diffs = OBODocDiffer.getDiffs(oboDoc1, oboDoc2);
        assertEquals(0, diffs.size(), diffs.toString());
    }

    /**
     * Test that the converted RO from OWL to OBO can be written and parsed back into OBO, and also
     * round-trip back into OWL.
     */
    @Test
    void testRoundTripOWLRO() {
        OWLOntology oo1 = parseOWLFile("ro.owl");
        OBODoc oboDoc1 = convert(oo1);
        // write OBO
        String oboString = renderOboToString(oboDoc1);
        // parse OBO
        OBODoc oboDoc2 = parseOboToString(oboString);
        // check that the annotations are pre-served on the property values
        Frame typedefFrame = oboDoc2.getTypedefFrame("RO:0002224");
        assert typedefFrame != null;
        Collection<Clause> propertyValues =
            typedefFrame.getClauses(OboFormatTag.TAG_PROPERTY_VALUE);
        boolean found = false;
        for (Clause clause : propertyValues) {
            if ("IAO:0000118".equals(clause.getValue())
                && "started by".equals(clause.getValue2())) {
                Collection<QualifierValue> values = clause.getQualifierValues();
                assertEquals(1, values.size());
                QualifierValue value = values.iterator().next();
                assertEquals("IAO:0000116", value.getQualifier());
                assertEquals("From Allen terminology", value.getValue());
                found = true;
            }
        }
        assertTrue(found, "The expected annotations on the property value are missing.");
        // convert back into OWL
        convert(oboDoc2);
        // check that the two oboDocs are equal
        List<Diff> diffs = OBODocDiffer.getDiffs(oboDoc1, oboDoc2);
        // the input uses version 1.4, but the output is hard coded to be 1.2
        assertEquals(1, diffs.size(),
            "Expected one diff, the oboformat diff is missing from the conversion");
    }

    @Test
    void testOBOIsInferredAnnotation() {
        OBODoc input = parseOBOFile("is_inferred_annotation.obo");
        OWLOntology owl = convert(input);
        // check round trip
        OBODoc output = convert(owl);
        String outObo = renderOboToString(output);
        assertEquals(readResource("is_inferred_annotation.obo"), outObo);
        // check owl
        IRI t1 = iri(OBO, "TEST_0001");
        IRI t3 = iri(OBO, "TEST_0003");
        IRI isInferredIRI = iri(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX, "is_inferred");
        AtomicBoolean hasAnnotation = new AtomicBoolean(false);
        OWLAnnotationProperty infIRI = df.getOWLAnnotationProperty(isInferredIRI);
        owl.getAxioms(AxiomType.SUBCLASS_OF).forEach(axiom -> {
            OWLClassExpression superClassCE = axiom.getSuperClass();
            OWLClassExpression subClassCE = axiom.getSubClass();
            if (!superClassCE.isAnonymous() && !subClassCE.isAnonymous()) {
                OWLClass superClass = (OWLClass) superClassCE;
                OWLClass subClass = (OWLClass) subClassCE;
                if (superClass.getIRI().equals(t1) && subClass.getIRI().equals(t3)) {
                    axiom.getAnnotations().stream().filter(a -> a.getProperty().equals(infIRI))
                        .map(OWLAnnotation::getValue).forEach(v -> {
                            if (v instanceof OWLLiteral) {
                                assertEquals("true", ((OWLLiteral) v).getLiteral());
                            } else {
                                fail(
                                    "The value is not the expected type, expected OWLiteral but was: "
                                        + v.getClass().getName());
                            }
                            hasAnnotation.set(true);
                        });
                }
            }
        });
        assertTrue(hasAnnotation.get(),
            "The sub class relation between t3 and t1 should have an is_inferred=true annotation");
    }

    @Test
    void testRequireEmptyXrefList() {
        OBODoc obo = parseOBOFile("synonym_test.obo");
        // Get synonym clause with an empty xref list
        Frame frame = obo.getTermFrame("GO:0009579");
        assertNotNull(frame);
        // write frame
        try (StringWriter stringWriter = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(stringWriter)) {
            OBOFormatWriter oboWriter = new OBOFormatWriter();
            oboWriter.write(frame, bufferedWriter, null);
            bufferedWriter.flush();
            // get written frame
            String line = stringWriter.getBuffer().toString();
            // check that written frame has line:
            // synonym: "photosynthetic membrane" RELATED []
            assertTrue(line.contains("\nsynonym: \"photosynthetic membrane\" RELATED []\n"));
        } catch (IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Test
    void testConvertVersionIRI() {
        OWLOntology owlOnt = convertOBOFile("version_iri_test.obo");
        assertNotNull(owlOnt);
        IRI v = owlOnt.getOntologyID().getVersionIRI().get();
        assertEquals("http://purl.obolibrary.org/obo/go/2012-01-01/go.owl", v.toString());
    }

    @Test
    void testConvertTransitiveOver() {
        // PARSE TEST FILE, CONVERT TO OWL
        OWLOntology ontology = convert(parseOBOFile("relation_shorthand_test.obo"));
        // TEST CONTENTS OF OWL ONTOLOGY
        IRI regulatesIRI = getIriByLabel(ontology, REGULATES);
        assertNotNull(regulatesIRI);
        boolean ok = false;
        // test that transitive over is translated to a property chain
        for (OWLSubPropertyChainOfAxiom axiom : ontology
            .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            OWLObjectProperty p = (OWLObjectProperty) axiom.getSuperProperty();
            if (regulatesIRI.equals(p.getIRI())) {
                List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
                assertEquals(2, chain.size());
                assertEquals(p, chain.get(0));
                assertEquals("http://purl.obolibrary.org/obo/BFO_0000050",
                    ((OWLObjectProperty) chain.get(1)).getIRI().toString());
                ok = true;
            }
        }
        assertTrue(ok);
        // CONVERT BACK TO OBO
        OBODoc obodoc = convert(ontology);
        // test that transitive over is converted back
        Frame tf = obodoc.getTypedefFrame(REGULATES);
        assert tf != null;
        assertEquals(3, tf.getClauses().size());
        assertEquals(REGULATES, tf.getTagValue(OboFormatTag.TAG_ID));
        assertEquals(REGULATES, tf.getTagValue(OboFormatTag.TAG_NAME));
        Clause clause = tf.getClause(OboFormatTag.TAG_TRANSITIVE_OVER);
        assert clause != null;
        assertEquals(1, clause.getValues().size());
        assertEquals("part_of", clause.getValue());
        assertTrue(clause.getQualifierValues().isEmpty());
    }

    @Test
    void shouldRoundtripAll() {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/uni.obo#>)\n"
            + "Ontology(<http://purl.obolibrary.org/obo/uni.obo.owl>\n" + "Declaration(Class(:A))\n"
            + "Declaration(Class(:B))\n" + "Declaration(ObjectProperty(:part_of))\n"
            + "SubClassOf(:A ObjectAllValuesFrom(:part_of :B)))";
        OWLOntology o1 = loadOntologyFromString(in, new FunctionalSyntaxDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o1, new OBODocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology, new OBODocumentFormat());
        assertEquals(o1.getLogicalAxioms(), o2.getLogicalAxioms());
    }
}
