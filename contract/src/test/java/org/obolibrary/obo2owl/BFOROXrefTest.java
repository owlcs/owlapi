package org.obolibrary.obo2owl;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings({ "javadoc" })
public class BFOROXrefTest extends OboFormatTestBasics {

    public final OWLAnnotationProperty OBO_ID = df
            .getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#id");
    @Nonnull
    private final OWLOntology owlOnt = convertOBOFile("rel_xref_test.obo");

    @Test
    public void testCorrectIdAnnotationCount() {
        assertEquals(4, owlOnt.objectPropertiesInSignature().count());
        // Check ID Property Count Exactly 1
        assertAnnotationPropertyCountEquals(owlOnt,
                IRI.create("http://purl.obolibrary.org/obo/BAR_0000001"),
                OBO_ID, 1);
        assertAnnotationPropertyCountEquals(owlOnt,
                IRI.create("http://purl.obolibrary.org/obo/RO_0002111"),
                OBO_ID, 1);
        assertAnnotationPropertyCountEquals(owlOnt,
                IRI.create("http://purl.obolibrary.org/obo/BFO_0000050"),
                OBO_ID, 1);
        assertAnnotationPropertyCountEquals(owlOnt,
                IRI.create("http://purl.obolibrary.org/obo/BFO_0000051"),
                OBO_ID, 2);
    }

    @Test
    public void testRelationXrefConversion() {
        // test initial conversion
        OWLAnnotationProperty ap = df
                .getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#shorthand");
        assertEquals(4, owlOnt.objectPropertiesInSignature().count());
        Stream<OWLAnnotationAssertionAxiom> aaas = owlOnt
                .annotationAssertionAxioms(IRI
                        .create("http://purl.obolibrary.org/obo/BFO_0000051"));
        boolean ok = aaas.filter(ax -> ax.getProperty().equals(ap))
                .map(a -> (OWLLiteral) a.getValue())
                .anyMatch(v -> v.getLiteral().equals("has_part"));
        assertTrue(ok);
        aaas = owlOnt.annotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/BFO_0000050"));
        assertTrue(aaas.count() > 0);
        aaas = owlOnt.annotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/RO_0002111"));
        assertTrue(aaas.count() > 0);
        aaas = owlOnt.annotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/BAR_0000001"));
        assertTrue(aaas.count() > 0);
        OWLAPIOwl2Obo revbridge = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc d2 = revbridge.convert(owlOnt);
        Frame partOf = d2.getTypedefFrame("part_of");
        Collection<Clause> xrcs = partOf.getClauses(OboFormatTag.TAG_XREF);
        boolean okBfo = false;
        boolean okOboRel = false;
        for (Clause c : xrcs) {
            Xref value = c.getValue(Xref.class);
            if (value.getIdref().equals("BFO:0000050")) {
                okBfo = true;
            }
            if (value.getIdref().equals("OBO_REL:part_of")) {
                okOboRel = true;
            }
        }
        assertTrue(okBfo);
        assertTrue(okOboRel);
        Frame a = d2.getTermFrame("TEST:a");
        Clause rc = a.getClause(OboFormatTag.TAG_RELATIONSHIP);
        assertEquals("part_of", rc.getValue());
        assertEquals("TEST:b", rc.getValue2());
    }

    private static void assertAnnotationPropertyCountEquals(
            @Nonnull OWLOntology owlOnt, @Nonnull IRI subjectIRI,
            OWLAnnotationProperty property, int expected) {
        List<OWLAnnotationAssertionAxiom> matches = asList(owlOnt
                .annotationAssertionAxioms(subjectIRI).filter(
                        ax -> ax.getProperty().equals(property)));
        assertEquals(subjectIRI + " has too many annotations of type "
                + property + ":\n\t" + matches, expected, matches.size());
    }
}
