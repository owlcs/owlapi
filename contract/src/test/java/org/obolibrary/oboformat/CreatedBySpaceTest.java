package org.obolibrary.oboformat;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.IRI;

@SuppressWarnings({ "javadoc" })
public class CreatedBySpaceTest extends OboFormatTestBasics {

    @Test
    public void testCreatedByWithSpace() throws Exception {
        String input = "ontology: test\n[Typedef]\nid: R:1\nname: r1\ncreated_by: John Doe";
        loadOntologyFromString(input, IRI.generateDocumentIRI(), new OBODocumentFormat());
    }
}
