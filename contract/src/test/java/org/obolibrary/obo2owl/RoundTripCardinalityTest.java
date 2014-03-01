package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

@SuppressWarnings("javadoc")
public class RoundTripCardinalityTest extends RoundTripTest {

    @Test
    public void testRoundTripCardinality() throws Exception {
        // create minimal ontology
        OBODoc oboDocSource = super.parseOBOFile("roundtrip_cardinality.obo");
        // convert to OWL and retrieve def
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology owlOntology = bridge.convert(oboDocSource);
        OWLDataFactory factory = owlOntology.getOWLOntologyManager()
                .getOWLDataFactory();
        OWLClass c = factory.getOWLClass(bridge.oboIdToIRI("PR:000027136"));
        // Relations
        boolean foundRel1 = false;
        boolean foundRel2 = false;
        Set<OWLSubClassOfAxiom> axioms = owlOntology
                .getSubClassAxiomsForSubClass(c);
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
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc convertedOboDoc = owl2Obo.convert(owlOntology);
        Frame convertedFrame = convertedOboDoc.getTermFrame("PR:000027136");
        Collection<Clause> clauses = convertedFrame
                .getClauses(OboFormatTag.TAG_RELATIONSHIP);
        // check that round trip still contains relationships
        assertEquals(2, clauses.size());
        for (Clause clause : clauses) {
            Collection<QualifierValue> qualifierValues = clause
                    .getQualifierValues();
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
    public void roundTrip() throws Exception {
        roundTripOBOFile("roundtrip_cardinality.obo", true);
    }
}
