package org.obolibrary.obo2owl;

import org.junit.Test;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class XrefIRITest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        OWLOntology ontology = parseOWLFile("xrefIRItest.owl");
        OBODoc doc = convert(ontology);
        String oboFile = "xrefIRIroundtrip.owl";
        Frame tf = doc.getTermFrame("FOO:1");
        // System.out.println(tf);
        writeOBO(doc, oboFile);
    }
}
