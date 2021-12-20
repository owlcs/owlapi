package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

class OBOCommentsTestCase extends TestBase {

    @Test
    void shouldAllowCommentInDate() {
        OWLOntology o1 = loadFrom(TestFiles.COMMENT_IN_DATE_1, new OBODocumentFormat());
        OWLOntology o2 = loadFrom(TestFiles.COMMENT_IN_DATE_2, new OBODocumentFormat());
        assertTrue(o1.equalAxioms(o2));
    }

    @Test
    void shouldAllowInstanceStanza() {
        loadFrom(TestFiles.allowInstanceStanza, IRIS.iriTest, new OBODocumentFormat());
    }
}
