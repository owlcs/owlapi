package org.obolibrary.obo2owl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class Obo2OwlTest extends OboFormatTestBasics {

    @Test
    public void testConvertCARO() throws Exception {
        OWLOntology owlOnt = convertOBOFile("caro.obo");
        assertNotNull(owlOnt);
    }

    @Test
    public void testConvertXPWithQV() throws Exception {
        OWLOntology owlOnt = convertOBOFile("testqvs.obo");
        assertNotNull(owlOnt);
    }
}
