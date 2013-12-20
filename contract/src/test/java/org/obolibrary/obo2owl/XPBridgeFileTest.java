package org.obolibrary.obo2owl;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class XPBridgeFileTest extends OboFormatTestBasics {
    @Test
    public void testConvertXPs() throws Exception {
        OWLOntology owlOnt = convertOBOFile("xptest.obo");
        assertNotNull(owlOnt);
    }

    private OWLOntology convertOBOFile(String fn) throws Exception {
        return convert(parseOBOFile(fn), fn);
    }
}
