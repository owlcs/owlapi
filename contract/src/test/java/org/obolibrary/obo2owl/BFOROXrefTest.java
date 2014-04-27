package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
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
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class BFOROXrefTest extends OboFormatTestBasics {

    public static final OWLAnnotationProperty OBO_ID = OWLManager
            .getOWLDataFactory()
            .getOWLAnnotationProperty(
                    IRI.create("http://www.geneontology.org/formats/oboInOwl#id"));
    @SuppressWarnings("null")
    @Nonnull
    private OWLOntology owlOnt;

    @Before
    public void setup() throws Exception {
        owlOnt = convertOBOFile("rel_xref_test.obo");
    }

    @Test
    public void testCorrectIdAnnotationCount() {
        Set<OWLObjectProperty> ops = owlOnt.getObjectPropertiesInSignature();
        assertTrue(ops.size() == 4);
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

    @SuppressWarnings("null")
    @Test
    public void testRelationXrefConversion() {
        // test initial conversion
        Set<OWLObjectProperty> ops = owlOnt.getObjectPropertiesInSignature();
        assertTrue(ops.size() == 4);
        Set<OWLAnnotationAssertionAxiom> aaas = owlOnt
                .getAnnotationAssertionAxioms(IRI
                        .create("http://purl.obolibrary.org/obo/BFO_0000051"));
        boolean ok = false;
        for (OWLAnnotationAssertionAxiom a : aaas) {
            if (a.getProperty()
                    .getIRI()
                    .toString()
                    .equals("http://www.geneontology.org/formats/oboInOwl#shorthand")) {
                OWLLiteral v = (OWLLiteral) a.getValue();
                if (v.getLiteral().equals("has_part")) {
                    ok = true;
                }
            }
        }
        assertTrue(aaas.size() > 0);
        assertTrue(ok);
        aaas = owlOnt.getAnnotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/BFO_0000050"));
        assertTrue(aaas.size() > 0);
        aaas = owlOnt.getAnnotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/RO_0002111"));
        assertTrue(aaas.size() > 0);
        aaas = owlOnt.getAnnotationAssertionAxioms(IRI
                .create("http://purl.obolibrary.org/obo/BAR_0000001"));
        assertTrue(aaas.size() > 0);
        OWLAPIOwl2Obo revbridge = new OWLAPIOwl2Obo(
                OWLManager.createOWLOntologyManager());
        OBODoc d2 = revbridge.convert(owlOnt);
        Frame part_of = d2.getTypedefFrame("part_of");
        Collection<Clause> xrcs = part_of.getClauses(OboFormatTag.TAG_XREF);
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
        assertTrue(rc.getValue().equals("part_of"));
        assertTrue(rc.getValue2().equals("TEST:b"));
    }

    private static void assertAnnotationPropertyCountEquals(
            @Nonnull OWLOntology owlOnt, @Nonnull IRI subjectIRI,
            OWLAnnotationProperty property, int expected) {
        Set<OWLAnnotationAssertionAxiom> aaas = owlOnt
                .getAnnotationAssertionAxioms(subjectIRI);
        List<OWLAnnotationAssertionAxiom> matches = new ArrayList<OWLAnnotationAssertionAxiom>();
        for (OWLAnnotationAssertionAxiom annotationAssertionAxiom : aaas) {
            if (annotationAssertionAxiom.getProperty().equals(property)) {
                matches.add(annotationAssertionAxiom);
            }
        }
        assertEquals(subjectIRI + " has too many annotations of type "
                + property + ":\n\t" + matches, expected, matches.size());
    }
}
