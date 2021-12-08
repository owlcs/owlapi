package org.obolibrary.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.IRI;

class CreatedBySpaceTestCase extends OboFormatTestBasics {

    @Test
    void testCreatedByWithSpace() {
        loadFrom(TestFiles.CREATED_BY_WITH_SPACE, IRI.generateDocumentIRI(),
            new OBODocumentFormat());
    }
}
