package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Tests for the handling of axioms, which cannot be translated to OBO. Such
 * axioms will be added in a tag in the ontology header.
 */
@SuppressWarnings("javadoc")
public class UntranslatableAxiomsInHeaderTest extends OboFormatTestBasics {

    @Test
    public void testUntranslatableAxioms() throws Exception {
        final OWLOntology original = parseOWLFile("untranslatable_axioms.owl");
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc obo = owl2Obo.convert(original);
        renderOboToString(obo);
        Frame headerFrame = obo.getHeaderFrame();
        String owlAxiomString = headerFrame.getTagValue(
                OboFormatTag.TAG_OWL_AXIOMS, String.class);
        assertNotNull(owlAxiomString);
        OWLAPIObo2Owl obo2Owl = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology converted = obo2Owl.convert(obo);
        Set<OWLEquivalentClassesAxiom> originalEqAxioms = original
                .getAxioms(AxiomType.EQUIVALENT_CLASSES);
        Set<OWLEquivalentClassesAxiom> convertedEqAxioms = converted
                .getAxioms(AxiomType.EQUIVALENT_CLASSES);
        assertEquals(originalEqAxioms.size(), convertedEqAxioms.size());
        assertTrue(originalEqAxioms.containsAll(convertedEqAxioms));
        assertTrue(convertedEqAxioms.containsAll(originalEqAxioms));
    }

    @Test
    public void testUntranslatableAxioms2() throws Exception {
        final OWLOntology original = parseOWLFile("untranslatable_axioms2.owl");
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc obo = owl2Obo.convert(original);
        renderOboToString(obo);
        Frame headerFrame = obo.getHeaderFrame();
        String owlAxiomString = headerFrame.getTagValue(
                OboFormatTag.TAG_OWL_AXIOMS, String.class);
        assertNotNull(owlAxiomString);
        OWLAPIObo2Owl obo2Owl = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology converted = obo2Owl.convert(obo);
        Set<OWLEquivalentClassesAxiom> originalEqAxioms = original
                .getAxioms(AxiomType.EQUIVALENT_CLASSES);
        Set<OWLEquivalentClassesAxiom> convertedEqAxioms = converted
                .getAxioms(AxiomType.EQUIVALENT_CLASSES);
        assertEquals(originalEqAxioms.size(), convertedEqAxioms.size());
        assertTrue(originalEqAxioms.containsAll(convertedEqAxioms));
        assertTrue(convertedEqAxioms.containsAll(originalEqAxioms));
    }
}
