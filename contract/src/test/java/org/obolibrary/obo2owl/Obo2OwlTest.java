package org.obolibrary.obo2owl;

import static junit.framework.Assert.assertNotNull;

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

    private OWLOntology convertOBOFile(String fn) throws Exception {
        return convert(parseOBOFile(fn), fn);
    }
}
