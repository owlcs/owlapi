package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.obo2owl.RoundTripPropertyValueTest;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * Comprehensive test for all tags, which support trailing qualifiers.
 * 
 * @see RoundTripPropertyValueTest for basic tests.
 */
@SuppressWarnings("javadoc")
public class TrailingQualifierTest extends OboFormatTestBasics {

    @Test
    public void testReadTrailingQualifiers() throws Exception {
        // load test file with trailing qualifiers
        OBODoc doc = parseOBOFile("trailing_qualifier.obo");
        checkAllTrailingQualifiers(doc);
        // write to OBO
        String oboString = renderOboToString(doc);
        // parse written OBO
        OBODoc doc2 = parseOboToString(oboString);
        checkAllTrailingQualifiers(doc2);
    }

    private void checkAllTrailingQualifiers(@Nonnull OBODoc doc) {
        Frame headerFrame = doc.getHeaderFrame();
        assert headerFrame != null;
        hasQualifierClause(headerFrame, OboFormatTag.TAG_AUTO_GENERATED_BY);
        hasQualifierClause(headerFrame, OboFormatTag.TAG_SUBSETDEF);
        hasQualifierClause(headerFrame, OboFormatTag.TAG_SYNONYMTYPEDEF);
        hasQualifierClause(headerFrame, OboFormatTag.TAG_DEFAULT_NAMESPACE);
        hasQualifierClause(headerFrame, OboFormatTag.TAG_IDSPACE);
        hasQualifierClauses(headerFrame, OboFormatTag.TAG_PROPERTY_VALUE);
        Frame frame = doc.getTermFrame("TEST:0001");
        assert frame != null;
        hasQualifierClause(frame, OboFormatTag.TAG_NAME);
        hasQualifierClause(frame, OboFormatTag.TAG_NAMESPACE);
        hasQualifierClause(frame, OboFormatTag.TAG_ALT_ID);
        hasQualifierClause(frame, OboFormatTag.TAG_DEF);
        hasQualifierClause(frame, OboFormatTag.TAG_COMMENT);
        hasQualifierClause(frame, OboFormatTag.TAG_SUBSET);
        hasQualifierClause(frame, OboFormatTag.TAG_SYNONYM);
        hasQualifierClause(frame, OboFormatTag.TAG_XREF);
        hasQualifierClause(frame, OboFormatTag.TAG_BUILTIN);
        hasQualifierClauses(frame, OboFormatTag.TAG_PROPERTY_VALUE);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_A);
        hasQualifierClauses(frame, OboFormatTag.TAG_INTERSECTION_OF);
        hasQualifierClauses(frame, OboFormatTag.TAG_UNION_OF);
        hasQualifierClause(frame, OboFormatTag.TAG_EQUIVALENT_TO);
        hasQualifierClause(frame, OboFormatTag.TAG_DISJOINT_FROM);
        hasQualifierClause(frame, OboFormatTag.TAG_RELATIONSHIP);
        hasQualifierClause(frame, OboFormatTag.TAG_CREATED_BY);
        frame = doc.getTermFrame("TEST:0008");
        assert frame != null;
        hasQualifierClause(frame, OboFormatTag.TAG_IS_OBSELETE);
        hasQualifierClause(frame, OboFormatTag.TAG_REPLACED_BY);
        frame = doc.getTermFrame("TEST:0009");
        assert frame != null;
        hasQualifierClause(frame, OboFormatTag.TAG_IS_OBSELETE);
        hasQualifierClause(frame, OboFormatTag.TAG_CONSIDER);
        frame = doc.getTypedefFrame("TEST_REL:0001");
        assert frame != null;
        hasQualifierClause(frame, OboFormatTag.TAG_DOMAIN);
        hasQualifierClause(frame, OboFormatTag.TAG_RANGE);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_ANTI_SYMMETRIC);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_CYCLIC);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_REFLEXIVE);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_SYMMETRIC);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_TRANSITIVE);
        hasQualifierClause(frame, OboFormatTag.TAG_INVERSE_OF);
        hasQualifierClause(frame, OboFormatTag.TAG_TRANSITIVE_OVER);
        hasQualifierClause(frame, OboFormatTag.TAG_HOLDS_OVER_CHAIN);
        hasQualifierClause(frame, OboFormatTag.TAG_IS_CLASS_LEVEL_TAG);
        frame = doc.getTypedefFrame("TEST_REL:0006");
        hasQualifierClause(frame, OboFormatTag.TAG_EQUIVALENT_TO_CHAIN);
        frame = doc.getTypedefFrame("TEST_REL:0007");
        hasQualifierClause(frame, OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL);
        frame = doc.getTypedefFrame("TEST_REL:0008");
        hasQualifierClause(frame, OboFormatTag.TAG_IS_FUNCTIONAL);
        frame = doc.getTypedefFrame("TEST_REL:0009");
        hasQualifierClause(frame, OboFormatTag.TAG_IS_METADATA_TAG);
    }

    void hasQualifierClause(@Nonnull Frame frame, @Nonnull OboFormatTag tag) {
        Clause clause = frame.getClause(tag);
        assertNotNull("Expected a clause " + tag.getTag() + " in frame: "
                + frame, clause);
        hasQualifier(clause);
    }

    void hasQualifierClauses(@Nonnull Frame frame, @Nonnull OboFormatTag tag) {
        Collection<Clause> clauses = frame.getClauses(tag);
        String message = "Expected clauses " + tag.getTag() + " in frame: "
                + frame;
        assertNotNull(message, clauses);
        assertFalse(message, clauses.isEmpty());
        for (Clause clause : clauses) {
            hasQualifier(clause);
        }
    }

    void hasQualifier(@Nonnull Clause clause) {
        Collection<QualifierValue> qualifierValues = clause
                .getQualifierValues();
        assertNotNull(
                "Expected a clause qualifier values in clause: " + clause,
                qualifierValues);
        assertFalse("Qualifier values should not be empty in clause:" + clause,
                qualifierValues.isEmpty());
        assertTrue("Expected two or more qualifier values",
                qualifierValues.size() >= 2);
        boolean foundOne = false;
        boolean foundTwo = false;
        // {val1="one",val2="two"}
        for (QualifierValue qualifierValue : qualifierValues) {
            if ("val1".equals(qualifierValue.getQualifier())) {
                foundOne = "one".equals(qualifierValue.getValue());
            } else if ("val2".equals(qualifierValue.getQualifier())) {
                foundTwo = "two".equals(qualifierValue.getValue());
            }
        }
        assertTrue("Did not find qualifier: val1=\"one\"", foundOne);
        assertTrue("Did not find qualifier: val2=\"two\"", foundTwo);
    }
}
