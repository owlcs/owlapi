package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

public class OBOCommentsTestCase extends TestBase {

    @Test
    public void shouldAllowCommentInDate() {
        String in1 = "format-version: 1.2\n" + "data-version: beta2 ! WSIO Beta 2\n"
            + "date: 19:06:2014 18:57 ! CE(S)T";
        OWLOntology o1 =
            loadOntologyFromString(in1, df.getIRI("urn:test#", "test1"), new OBODocumentFormat());
        String in2 = "format-version: 1.2\n" + "date: 19:06:2014 18:57 ! CE(S)T"
            + "data-version: beta2 ! WSIO Beta 2\n";
        OWLOntology o2 =
            loadOntologyFromString(in2, df.getIRI("urn:test#", "test2"), new OBODocumentFormat());
        assertTrue(o1.equalAxioms(o2));
    }

    @Test
    public void shouldAllowInstanceStanza() {
        loadOntologyFromString(TestFiles.allowInstanceStanza, df.getIRI("urn:test#", "test"),
            new OBODocumentFormat());
    }
}
