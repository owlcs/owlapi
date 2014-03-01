package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;

@SuppressWarnings("javadoc")
public class OWLLiteralCorruptionTestCase {

    @Test
    public void testmain() {
        String TEST_STRING;
        StringBuilder sb = new StringBuilder();
        int count = 17;
        while (count-- > 0) {
            sb.append("200 \u00B5Liters + character above U+0FFFF = ");
            sb.appendCodePoint(0x10192);  // happens to be "ROMAN SEMUNCIA SIGN"
            sb.append("\n");
        }
        TEST_STRING = sb.toString();
        OWLLiteral literal = Literal(TEST_STRING);
        assertEquals("Out = in ? false", literal.getLiteral(), TEST_STRING);
    }
}
