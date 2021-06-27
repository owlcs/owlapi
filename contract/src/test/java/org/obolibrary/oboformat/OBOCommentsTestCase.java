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
        String in1 = "format-version: 1.2\n" + "data-version: beta2 ! WSIO Beta 2\n"
            + "date: 19:06:2014 18:57 ! CE(S)T";
        OWLOntology o1 =
            loadOntologyFromString(in1, iri("urn:test#", "test1"), new OBODocumentFormat());
        String in2 = "format-version: 1.2\n" + "date: 19:06:2014 18:57 ! CE(S)T"
            + "data-version: beta2 ! WSIO Beta 2\n";
        OWLOntology o2 =
            loadOntologyFromString(in2, iri("urn:test#", "test2"), new OBODocumentFormat());
        assertTrue(o1.equalAxioms(o2));
    }

    @Test
    void shouldAllowInstanceStanza() {
        loadOntologyFromString(TestFiles.allowInstanceStanza, iri("urn:test#", "test"),
            new OBODocumentFormat());
    }
}
