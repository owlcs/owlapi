package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.model.OWLOntology;

/** Test for reading and converting the Relationship ontology. */
@SuppressWarnings("javadoc")
public class RoundTripOWLROTest extends RoundTripTest {

    /**
     * Test that the converted RO from OWL to OBO can be written and parsed back
     * into OBO, and also round-trip back into OWL.
     */
    @Test
    public void testRoundTrip() throws Exception {
        OWLOntology oo1 = parseOWLFile("ro.owl");
        OBODoc oboDoc1 = convert(oo1);
        // write OBO
        String oboString = renderOboToString(oboDoc1);
        // parse OBO
        OBOFormatParser p = new OBOFormatParser();
        OBODoc oboDoc2 = p
                .parse(new BufferedReader(new StringReader(oboString)));
        // check that the annotations are pre-served on the property values
        Frame typedefFrame = oboDoc2.getTypedefFrame("RO:0002224");
        @SuppressWarnings("null")
        Collection<Clause> propertyValues = typedefFrame
                .getClauses(OboFormatTag.TAG_PROPERTY_VALUE);
        boolean found = false;
        for (Clause clause : propertyValues) {
            if ("IAO:0000118".equals(clause.getValue())
                    && "started by".equals(clause.getValue2())) {
                Collection<QualifierValue> values = clause.getQualifierValues();
                assertEquals(1, values.size());
                QualifierValue value = values.iterator().next();
                assertEquals("http://purl.obolibrary.org/obo/IAO_0000116",
                        value.getQualifier());
                assertEquals("From Allen terminology", value.getValue());
                found = true;
            }
        }
        assertTrue(
                "The expected annotations on the property value are missing.",
                found);
        // convert back into OWL
        convert(oboDoc2);
        // check that the two oboDocs are equal
        List<Diff> diffs = OBODocDiffer.getDiffs(oboDoc1, oboDoc2);
        // the input uses version 1.4, but the output is hardcoded to be 1.2
        assertEquals(1, diffs.size());
    }
}
