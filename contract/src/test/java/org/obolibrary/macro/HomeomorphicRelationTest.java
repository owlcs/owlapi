package org.obolibrary.macro;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class HomeomorphicRelationTest extends OboFormatTestBasics {

    @Test
    public void testExpand() throws Exception {
        OWLOntology owlOnt = convertOBOFile("homrel.obo");
        assertNotNull(owlOnt);
    }
}
