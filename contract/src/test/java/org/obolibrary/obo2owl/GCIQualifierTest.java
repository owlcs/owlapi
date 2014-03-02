package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/** @author cjm see 5.9.3 and 8.2.2 of spec */
@SuppressWarnings("javadoc")
public class GCIQualifierTest extends OboFormatTestBasics {

    @Test
    public void testConvert() throws Exception {
        // PARSE TEST FILE, CONVERT TO OWL, AND WRITE TO OWL FILE
        OWLOntology ontology = convert(parseOBOFile("gci_qualifier_test.obo"));
        if (true) {
            Set<OWLSubClassOfAxiom> scas = ontology
                    .getAxioms(AxiomType.SUBCLASS_OF);
            boolean ok = !scas.isEmpty();
            assertTrue(ok);
        }
        // CONVERT BACK TO OBO
        OBODoc obodoc = convert(ontology);
        // test that relation IDs are converted back to symbolic form
        Frame tf = obodoc.getTermFrame("X:1");
        Collection<Clause> clauses = tf
                .getClauses(OboFormatTag.TAG_RELATIONSHIP);
        assertEquals(2, clauses.size());
    }
}
