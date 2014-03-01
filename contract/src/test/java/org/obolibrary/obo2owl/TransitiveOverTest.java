package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/** see 5.4 of spec */
@SuppressWarnings("javadoc")
public class TransitiveOverTest extends RoundTripTest {

    @Test
    public void testConvert() throws Exception {
        // PARSE TEST FILE, CONVERT TO OWL
        OWLOntology ontology = convert(parseOBOFile("relation_shorthand_test.obo"));
        // TEST CONTENTS OF OWL ONTOLOGY
        IRI regulatesIRI = getIriByLabel(ontology, "regulates");
        assertNotNull(regulatesIRI);
        boolean ok = false;
        // test that transitive over is translated to a property chain
        Set<OWLSubPropertyChainOfAxiom> axioms = ontology
                .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF);
        for (OWLSubPropertyChainOfAxiom axiom : axioms) {
            OWLObjectProperty p = (OWLObjectProperty) axiom.getSuperProperty();
            if (regulatesIRI.equals(p.getIRI())) {
                List<OWLObjectPropertyExpression> chain = axiom
                        .getPropertyChain();
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
        if (true) {
            Frame tf = obodoc.getTypedefFrame("regulates");
            assertEquals(3, tf.getClauses().size());
            assertEquals("regulates", tf.getTagValue(OboFormatTag.TAG_ID));
            assertEquals("regulates", tf.getTagValue(OboFormatTag.TAG_NAME));
            Clause clause = tf.getClause(OboFormatTag.TAG_TRANSITIVE_OVER);
            assertEquals(1, clause.getValues().size());
            assertEquals("part_of", clause.getValue());
            assertTrue(clause.getQualifierValues().isEmpty());
        }
        // renderOBO(obodoc);
    }

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("relation_shorthand_test.obo", true);
    }
}
