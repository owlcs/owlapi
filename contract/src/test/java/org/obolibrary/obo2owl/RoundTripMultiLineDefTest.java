package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class RoundTripMultiLineDefTest extends OboFormatTestBasics {

    @Test
    public void testMultiLineDefinitions() throws Exception {
        // create minimal ontology
        OBODoc oboDocSource = new OBODoc();
        oboDocSource.setHeaderFrame(new Frame(FrameType.HEADER));
        oboDocSource.addDefaultOntologyHeader("caro");
        // add source frame that contains at least one new line
        Frame sourceFrame = new Frame(FrameType.TERM);
        sourceFrame.setId("CARO:0000049");
        sourceFrame
                .addClause(new Clause(
                        OboFormatTag.TAG_DEF,
                        "Sequential hermaphroditic organism that produces\ngametes first of the male sex, and then later of the\nfemale sex."));
        oboDocSource.addTermFrame(sourceFrame);
        // convert to OWL and retrieve def
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology owlOntology = bridge.convert(oboDocSource);
        final OWLDataFactory factory = owlOntology.getOWLOntologyManager()
                .getOWLDataFactory();
        // IRI
        IRI iri = bridge.oboIdToIRI("CARO:0000049");
        OWLClass c = factory.getOWLClass(iri);
        // Def
        OWLAnnotationProperty defProperty = factory
                .getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000115
                        .getIRI());
        int counter = 0;
        for (OWLAnnotationAssertionAxiom ax : owlOntology
                .getAnnotationAssertionAxioms(c.getIRI())) {
            if (ax.getProperty().equals(defProperty)) {
                counter++;
                assertTrue(ax.getValue() instanceof OWLLiteral);
                String owlDef = ((OWLLiteral) ax.getValue()).getLiteral();
                // check that owl def also contains at least one new line
                assertTrue(owlDef.indexOf('\n') > 0);
            }
        }
        assertEquals(1, counter);
        // convert back to OBO
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc convertedOboDoc = owl2Obo.convert(owlOntology);
        Frame convertedFrame = convertedOboDoc.getTermFrame("CARO:0000049");
        String convertedDef = convertedFrame.getTagValue(OboFormatTag.TAG_DEF,
                String.class);
        // check that round trip still contains newlines
        assertTrue(convertedDef.indexOf('\n') > 0);
    }
}
