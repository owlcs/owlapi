package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;

@SuppressWarnings("javadoc")
public class TagTest extends OboFormatTestBasics {

    @Test
    public void testParseOBOFile() throws Exception {
        OBODoc obodoc = parseOBOFile("tag_test.obo");
        assertEquals(4, obodoc.getTermFrames().size());
        assertEquals(1, obodoc.getTypedefFrames().size());
        Frame frame = obodoc.getTermFrame("X:1");
        assertNotNull(frame);
        assertEquals("x1", frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    public void testParseOBOFile2() throws Exception {
        OBODoc obodoc = parseOBOFile("testqvs.obo");
        assertEquals(4, obodoc.getTermFrames().size());
        assertEquals(1, obodoc.getTypedefFrames().size());
    }

    @Test
    public void testParseOBODoc() {
        OBODoc obodoc = parseOBODoc("[Term]\nid: x\nname: foo\n\n\n[Term]\nid: y\nname: y");
        assertEquals(2, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrame("x");
        assertEquals("foo", frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    public void testParseFrames() {
        OBODoc obodoc = parseFrames("[Term]\nid: x\nname: foo");
        assertEquals(1, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrames().iterator().next();
        assertEquals("foo", frame.getTagValue(OboFormatTag.TAG_NAME));
    }

    @Test
    public void testParseDefTag() {
        Clause cl = parseLine("def: \"a b c\" [foo:1, bar:2]");
        assertEquals(OboFormatTag.TAG_DEF.getTag(), cl.getTag());
        assertEquals("a b c", cl.getValue());
        assertEquals(1, cl.getValues().size());
    }

    @Test
    public void testParseDefTag2() {
        Clause cl = parseLine("def: \"a b c\" [foo:1 \"blah blah\", bar:2]");
        assertEquals(OboFormatTag.TAG_DEF.getTag(), cl.getTag());
        assertEquals("a b c", cl.getValue());
    }

    @Test
    public void testParseCreationDateTag() {
        Clause cl = parseLine("creation_date: 2009-04-28T10:29:37Z");
        assertEquals(OboFormatTag.TAG_CREATION_DATE.getTag(), cl.getTag());
    }

    @Test
    public void testParseNameTag() {
        Clause cl = parseLine("name: a b c");
        assertTrue(cl.getTag().equals(OboFormatTag.TAG_NAME.getTag()));
        assertTrue(cl.getValue().equals("a b c"));
    }

    @Test
    public void testParseNameTag2() {
        Clause cl = parseLine("name:    a b c");
        assertEquals(OboFormatTag.TAG_NAME.getTag(), cl.getTag());
        assertEquals("a b c", cl.getValue());
    }

    @Test
    public void testParseNamespaceTag() {
        Clause cl = parseLine("namespace: foo");
        assertTrue(cl.getTag().equals(OboFormatTag.TAG_NAMESPACE.getTag()));
        assertTrue(cl.getValue().equals("foo"));
    }

    @Test
    public void testParseIsATag() {
        Clause cl = parseLine("is_a: x ! foo");
        assertEquals(OboFormatTag.TAG_IS_A.getTag(), cl.getTag());
        assertEquals("x", cl.getValue());
    }

    private Clause parseLine(String line) {
        StringReader sr = new StringReader(line);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        try {
            Clause cl = p.parseTermFrameClause();
            return cl;
        } catch (OBOFormatParserException e) {
            fail(e.getMessage());
            return null;
        }
    }

    private OBODoc parseFrames(String s) {
        StringReader sr = new StringReader(s);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        try {
            OBODoc obodoc = new OBODoc();
            p.parseTermFrame(obodoc);
            return obodoc;
        } catch (OBOFormatParserException e) {
            fail(e.getMessage());
            return null;
        }
    }

    private OBODoc parseOBODoc(String s) {
        StringReader sr = new StringReader(s);
        OBOFormatParser p = new OBOFormatParser();
        BufferedReader br = new BufferedReader(sr);
        p.setReader(br);
        try {
            OBODoc obodoc = new OBODoc();
            p.parseOBODoc(obodoc);
            return obodoc;
        } catch (OBOFormatParserException e) {
            fail(e.getMessage());
            return null;
        }
    }
}
