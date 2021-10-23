package org.obolibrary.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.IRI;

class CreatedBySpaceTestCase extends OboFormatTestBasics {

    @Test
    void testCreatedByWithSpace() {
        String input = "ontology: test\n[Typedef]\nid: R:1\nname: r1\ncreated_by: John Doe";
        loadOntologyFromString(input, IRI.generateDocumentIRI(), new OBODocumentFormat());
    }
}
