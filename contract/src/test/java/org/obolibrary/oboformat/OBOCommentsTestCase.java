package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class OBOCommentsTestCase extends TestBase {

    @Test
    void shouldAllowCommentInDate() {
        OWLOntology o1 = loadFrom(TestFiles.COMMENT_IN_DATE_1, new OBODocumentFormat());
        OWLOntology o2 = loadFrom(TestFiles.COMMENT_IN_DATE_2, new OBODocumentFormat());
        assertTrue(o1.equalAxioms(o2));
    }

    @Test
    void shouldAllowInstanceStanza() {
        loadFrom(TestFiles.allowInstanceStanza, iriTest, new OBODocumentFormat());
    }
}
