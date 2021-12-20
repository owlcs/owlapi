package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;

class CreatedBySpaceTestCase extends OboFormatTestBasics {

    @Test
    void testCreatedByWithSpace() {
        loadFrom(TestFiles.CREATED_BY_WITH_SPACE, NextIRI(""), new OBODocumentFormat());
    }
}
