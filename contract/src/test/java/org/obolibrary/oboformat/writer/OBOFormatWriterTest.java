package org.obolibrary.oboformat.writer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** Tests for {@link OBOFormatWriter}. */
@SuppressWarnings("javadoc")
public class OBOFormatWriterTest extends OboFormatTestBasics {

    /**
     * Test a special case of the specification. For intersections put the genus
     * before the differentia, instead of the default case-insensitive
     * alphabetical ordering.
     * 
     * @throws Exception
     */
    @Test
    public void testSortTermClausesIntersection_of() throws Exception {
        OBODoc oboDoc = parseOBOFile("equivtest.obo");
        Frame frame = oboDoc.getTermFrame("X:1");
        List<Clause> clauses = new ArrayList<Clause>(
                frame.getClauses(OboFormatTag.TAG_INTERSECTION_OF));
        OBOFormatWriter.sortTermClauses(clauses);
        assertEquals("Y:1", clauses.get(0).getValue());
        assertEquals("R:1", clauses.get(1).getValue());
        assertEquals("Z:1", clauses.get(1).getValue2());
    }

    /**
     * Test for sorting clauses according to alphabetical case-insensitive
     * order. Prefer upper-case over lower case for equal strings. Prefer
     * shorter strings over longer strings.
     */
    @Test
    public void testSortTermClausesSynonyms() {
        List<Clause> clauses = createSynonymClauses("cc", "ccc", "AAA", "aaa",
                "bbbb");
        OBOFormatWriter.sortTermClauses(clauses);
        assertEquals("AAA", clauses.get(0).getValue());
        assertEquals("aaa", clauses.get(1).getValue());
        assertEquals("bbbb", clauses.get(2).getValue());
        assertEquals("cc", clauses.get(3).getValue());
        assertEquals("ccc", clauses.get(4).getValue());
    }

    @Nonnull
    private static List<Clause> createSynonymClauses(@Nonnull String... labels) {
        List<Clause> clauses = new ArrayList<Clause>(labels.length);
        for (String label : labels) {
            Clause clause = new Clause(OboFormatTag.TAG_SYNONYM, label);
            clauses.add(clause);
        }
        return clauses;
    }

    @Test
    public void testWriteObsolete() throws Exception {
        assertEquals("", writeObsolete(Boolean.FALSE));
        assertEquals("", writeObsolete(Boolean.FALSE.toString()));
        assertEquals("is_obsolete: true", writeObsolete(Boolean.TRUE));
        assertEquals("is_obsolete: true",
                writeObsolete(Boolean.TRUE.toString()));
    }

    @Nonnull
    private static String writeObsolete(Object value) throws Exception {
        Clause cl = new Clause(OboFormatTag.TAG_IS_OBSELETE);
        cl.addValue(value);
        OBOFormatWriter writer = new OBOFormatWriter();
        StringWriter out = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        writer.write(cl, bufferedWriter, null);
        bufferedWriter.close();
        return out.toString().trim();
    }

    /**
     * Test that the OBO format writer only writes one new-line at the end of
     * the file.
     * 
     * @throws Exception
     */
    @Test
    public void testWriteEndOfFile() throws Exception {
        OBODoc oboDoc = parseOBOFile("caro.obo");
        String oboString = renderOboToString(oboDoc);
        int length = oboString.length();
        assertTrue(length > 0);
        int newLineCount = 0;
        for (int i = length - 1; i >= 0; i--) {
            char c = oboString.charAt(i);
            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    newLineCount++;
                }
            } else {
                break;
            }
        }
        assertEquals("GO always had an empty newline at the end.", 2,
                newLineCount);
    }

    @Test
    public void testWriteOpaqueIdsAsComments() throws Exception {
        OBODoc oboDoc = parseOBOFile("opaque_ids_test.obo");
        String oboString = renderOboToString(oboDoc);
        String[] lines = oboString.split("\n");
        boolean ok = false;
        for (String line : lines) {
            // System.out.println("LINE: "+line);
            if (line.startsWith("relationship:")) {
                if (line.contains("named relation y1")) {
                    ok = true;
                }
            }
        }
        assertTrue(ok);
    }

    @Test
    public void testPropertyValueOrder() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getInputStream("tag_order_test.obo")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        String input = sb.toString();
        OBODoc obodoc = parseOboToString(input);
        String written = renderOboToString(obodoc);
        assertEquals(input, written);
    }
}
