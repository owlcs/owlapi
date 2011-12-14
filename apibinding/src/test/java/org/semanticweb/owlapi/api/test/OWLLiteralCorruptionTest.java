package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.VersionInfo;

public class OWLLiteralCorruptionTest extends TestCase{


    public void testmain() throws Exception{
    	String TEST_STRING;
        StringBuilder sb = new StringBuilder();
        int count = 17;
        while (count-- > 0) {
            sb.append("200 \u00B5Liters + character above U+0FFFF = ");
            sb.appendCodePoint(0x10192);  // happens to be "ROMAN SEMUNCIA SIGN"
            sb.append("\n");
        }
        TEST_STRING = sb.toString();

        System.out.println("OWL api version = " + VersionInfo.getVersionInfo().getVersion());
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        OWLLiteral literal = factory.getOWLLiteral(TEST_STRING);
        System.out.println("Out = in ? " + literal.getLiteral().equals(TEST_STRING));
        assertEquals(literal.getLiteral(),TEST_STRING);
    }
}
