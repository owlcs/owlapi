package org.obolibrary.obo2owl;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Tests for the conversion of rdfs:comment in OWL to remark tag in OBO. This is
 * necessary as OBO-Edit won't load any OBO ontology containing a comment-tag in
 * the ontology header. WARNING: This conversion will not conserve the order of
 * remark tags in a round-trip via OWL.
 */
@SuppressWarnings("javadoc")
public class CommentRemarkConversionTest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        OBODoc obo = parseOBOFile("comment_remark_conversion.obo", true);
        Frame headerFrame = obo.getHeaderFrame();
        Collection<String> remarks = headerFrame.getTagValues(
                OboFormatTag.TAG_REMARK, String.class);
        OWLAPIObo2Owl obo2Owl = new OWLAPIObo2Owl(
                OWLManager.createOWLOntologyManager());
        OWLOntology owlOntology = obo2Owl.convert(obo);
        Set<String> comments = asSet(owlOntology
                .annotations(df.getRDFSComment()).map(a -> a.getValue())
                .filter(a -> a instanceof OWLLiteral)
                .map(a -> ((OWLLiteral) a).getLiteral()));
        // check that all remarks have been translated to rdfs:comment
        assertEquals(remarks.size(), comments.size());
        assertTrue(comments.containsAll(remarks));
        assertTrue(remarks.containsAll(comments));
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc oboRoundTrip = owl2Obo.convert(owlOntology);
        Frame headerFrameRoundTrip = oboRoundTrip.getHeaderFrame();
        Collection<String> remarksRoundTrip = headerFrameRoundTrip
                .getTagValues(OboFormatTag.TAG_REMARK, String.class);
        assertEquals(remarks.size(), remarksRoundTrip.size());
        assertTrue(remarksRoundTrip.containsAll(remarks));
        assertTrue(remarks.containsAll(remarksRoundTrip));
    }
}
