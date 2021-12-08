package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.apitest.TestFilenames;

class TagTestCase extends OboFormatTestBasics {

    private static final String FOO = "foo";
    private static final String A_B_C = "a b c";

    private static Clause parseLine(String line) {
        StringReader sr = new StringReader(line);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        return p.parseTermFrameClause();
    }

    private static OBODoc parseFrames(String content) {
        StringReader sr = new StringReader(content);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        OBODoc obodoc = new OBODoc();
        p.parseTermFrame(obodoc);
        return obodoc;
    }

    private static OBODoc parseOBODoc(String content) {
        StringReader sr = new StringReader(content);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        OBODoc obodoc = new OBODoc();
        p.parseOBODoc(obodoc);
        return obodoc;
    }

    @Test
    void testParseOBOFile() {
        OBODoc obodoc = parseOBOFile(TestFilenames.TAG_TEST_OBO);
        assertEquals(4, obodoc.getTermFrames().size());
        assertEquals(1, obodoc.getTypedefFrames().size());
        Frame frame = obodoc.getTermFrame("X:1");
        assertNotNull(frame);
        assert frame != null;
        assertEquals("x1", frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    void testParseOBOFile2() {
        OBODoc obodoc = parseOBOFile(TestFilenames.TESTQVS_OBO);
        assertEquals(4, obodoc.getTermFrames().size());
        assertEquals(1, obodoc.getTypedefFrames().size());
    }

    @Test
    void testParseOBODoc() {
        OBODoc obodoc = parseOBODoc("[Term]\nid: x\nname: foo\n\n\n[Term]\nid: y\nname: y");
        assertEquals(2, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrame("x");
        assert frame != null;
        assertEquals(FOO, frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    void testParseFrames() {
        OBODoc obodoc = parseFrames("[Term]\nid: x\nname: foo");
        assertEquals(1, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrames().iterator().next();
        assertEquals(FOO, frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    void testParseDefTag() {
        Clause cl = parseLine("def: \"a b c\" [foo:1, bar:2]");
        assertEquals(OboFormatTag.TAG_DEF.getTag(), cl.getTag());
        assertEquals(A_B_C, cl.getValue());
        assertEquals(1, cl.getValues().size());
    }

    @Test
    void testParseDefTag2() {
        Clause cl = parseLine("def: \"a b c\" [foo:1 \"blah blah\", bar:2]");
        assertEquals(OboFormatTag.TAG_DEF.getTag(), cl.getTag());
        assertEquals(A_B_C, cl.getValue());
    }

    @Test
    void testParseCreationDateTag() {
        Clause cl = parseLine("creation_date: 2009-04-28T10:29:37Z");
        assertEquals(OboFormatTag.TAG_CREATION_DATE.getTag(), cl.getTag());
    }

    @Test
    void testParseNameTag() {
        Clause cl = parseLine("name: a b c");
        assertEquals(cl.getTag(), OboFormatTag.TAG_NAME.getTag());
        assertEquals(A_B_C, cl.getValue());
    }

    @Test
    void testParseNameTag2() {
        Clause cl = parseLine("name:    a b c");
        assertEquals(OboFormatTag.TAG_NAME.getTag(), cl.getTag());
        assertEquals(A_B_C, cl.getValue());
    }

    @Test
    void testParseNamespaceTag() {
        Clause cl = parseLine("namespace: foo");
        assertEquals(cl.getTag(), OboFormatTag.TAG_NAMESPACE.getTag());
        assertEquals(FOO, cl.getValue());
    }

    @Test
    void testParseIsATag() {
        Clause cl = parseLine("is_a: x ! foo");
        assertEquals(OboFormatTag.TAG_IS_A.getTag(), cl.getTag());
        assertEquals("x", cl.getValue());
    }
}
