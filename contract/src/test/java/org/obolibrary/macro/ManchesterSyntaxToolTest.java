package org.obolibrary.macro;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Nullable;

import org.junit.Test;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/** Tests for {@link ManchesterSyntaxTool}. */
@SuppressWarnings("javadoc")
public class ManchesterSyntaxToolTest extends OboFormatTestBasics {

    @Nullable
    private OWLOntology owlOntology = null;
    @Nullable
    private ManchesterSyntaxTool parser = null;

    @Test
    public void testParseManchesterIds() throws Exception {
        setup();
        OWLClassExpression expression = parser
                .parseManchesterExpression("GO_0018901 AND BFO:0000050 some GO_0055124");
        checkIntersection(expression, "GO:0018901", "BFO:0000050", "GO:0055124");
    }

    @Test
    public void testParseManchesterNames() throws Exception {
        setup();
        OWLClassExpression expression = parser
                .parseManchesterExpression("'2,4-dichlorophenoxyacetic acid metabolic process' AND 'part_of' some 'premature neural plate formation'");
        checkIntersection(expression, "GO:0018901", "BFO:0000050", "GO:0055124");
    }

    private synchronized void setup() throws Exception {
        if (owlOntology == null) {
            owlOntology = convert(parseOBOFile("simplego.obo"));
            parser = new ManchesterSyntaxTool(owlOntology);
        }
    }

    private static void checkIntersection(OWLClassExpression expression,
            String genus, String relId, String differentia) {
        OWLObjectIntersectionOf intersection = (OWLObjectIntersectionOf) expression;
        List<OWLClassExpression> list = intersection.getOperandsAsList();
        OWLClass cls = (OWLClass) list.get(0);
        assertEquals(genus, OWLAPIOwl2Obo.getIdentifier(cls.getIRI()));
        OWLClassExpression rhs = list.get(1);
        OWLClass cls2 = rhs.getClassesInSignature().iterator().next();
        assertEquals(differentia, OWLAPIOwl2Obo.getIdentifier(cls2.getIRI()));
        OWLObjectProperty property = rhs.getObjectPropertiesInSignature()
                .iterator().next();
        assertEquals(relId, OWLAPIOwl2Obo.getIdentifier(property.getIRI()));
    }
}
