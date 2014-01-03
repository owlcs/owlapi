package org.obolibrary.obo2owl;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

/** @author cjm */
@SuppressWarnings("javadoc")
public class SynonymTest extends OboFormatTestBasics {
    @Test
    public void testConvert() throws Exception {
        // PARSE TEST FILE
        OWLOntology ontology = convert(parseOBOFile("synonym_test.obo"));
    }
}
