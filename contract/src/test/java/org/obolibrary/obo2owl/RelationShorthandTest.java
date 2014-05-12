package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/** @author cjm see 5.9.3 and 8.2.2 of spec */
@SuppressWarnings({ "javadoc", "null" })
public class RelationShorthandTest extends OboFormatTestBasics {

    @Test
    public void testConvert() {
        // PARSE TEST FILE, CONVERT TO OWL, AND WRITE TO OWL FILE
        OWLOntology ontology = convert(parseOBOFile("relation_shorthand_test.obo"));
        // TEST CONTENTS OF OWL ONTOLOGY
        Set<OWLSubClassOfAxiom> scas = ontology
                .getAxioms(AxiomType.SUBCLASS_OF);
        boolean ok = false;
        for (OWLSubClassOfAxiom sca : scas) {
            OWLClassExpression sup = sca.getSuperClass();
            if (sup instanceof OWLObjectSomeValuesFrom) {
                OWLObjectProperty p = (OWLObjectProperty) ((OWLObjectSomeValuesFrom) sup)
                        .getProperty();
                OWLClass v = (OWLClass) ((OWLObjectSomeValuesFrom) sup)
                        .getFiller();
                if (p.getIRI().toString()
                        .equals("http://purl.obolibrary.org/obo/BFO_0000051")
                        && v.getIRI()
                                .toString()
                                .equals("http://purl.obolibrary.org/obo/GO_0004055")) {
                    ok = true;
                }
            }
        }
        assertTrue(ok);
        scas = ontology.getAxioms(AxiomType.SUBCLASS_OF);
        ok = false;
        for (OWLSubClassOfAxiom sca : scas) {
            OWLClassExpression sup = sca.getSuperClass();
            if (sup instanceof OWLObjectSomeValuesFrom) {
                OWLObjectProperty p = (OWLObjectProperty) ((OWLObjectSomeValuesFrom) sup)
                        .getProperty();
                OWLClass v = (OWLClass) ((OWLObjectSomeValuesFrom) sup)
                        .getFiller();
                if (p.getIRI().toString()
                        .equals("http://purl.obolibrary.org/obo/BFO_0000050")
                        && v.getIRI()
                                .toString()
                                .equals("http://purl.obolibrary.org/obo/XX_0000001")) {
                    ok = true;
                }
            }
        }
        assertTrue(ok);
        // CONVERT BACK TO OBO
        OBODoc obodoc = convert(ontology);
        // test that relation IDs are converted back to symbolic form
        Frame tf = obodoc.getTermFrame("GO:0000050");
        Clause c = tf.getClause(OboFormatTag.TAG_RELATIONSHIP);
        Object v = c.getValue();
        // should be converted back to symbolic form
        assertEquals("has_part", v);
        tf = obodoc.getTermFrame("GO:0004055");
        c = tf.getClause(OboFormatTag.TAG_RELATIONSHIP);
        v = c.getValue();
        // should be converted back to symbolic form
        assertEquals("part_of", v);
        tf = obodoc.getTypedefFrame("has_part");
        Collection<Clause> cs = tf.getClauses(OboFormatTag.TAG_XREF);
        assertEquals(1, cs.size());
        v = cs.iterator().next().getValue(Xref.class).getIdref();
        // should be converted back to symbolic form
        assertEquals("BFO:0000051", v);
    }
}
