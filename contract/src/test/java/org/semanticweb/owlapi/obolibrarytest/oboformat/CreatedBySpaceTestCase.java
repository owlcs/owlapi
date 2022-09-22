package org.semanticweb.owlapi.obolibrarytest.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OBODocumentFormat;

class CreatedBySpaceTestCase extends OboFormatTestBasics {

    @Test
    void testCreatedByWithSpace() {
        loadFrom(TestFiles.CREATED_BY_WITH_SPACE, NextIRI(""), new OBODocumentFormat());
    }
}
