package org.obolibrary.obo2owl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class XPBridgeFileTest extends OboFormatTestBasics {

    @Test
    public void testConvertXPs() {
        OWLOntology owlOnt = convertOBOFile("xptest.obo");
        assertNotNull(owlOnt);
    }
}
