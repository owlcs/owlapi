package org.semanticweb.owlapi.api.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 04/04/2014
 */
@SuppressWarnings("javadoc")
public class OWL2DatatypeShortFormTestCase {

    @Test
    public void shouldReturnCorrectShortFormFor_XMLLiteral() {
        String shortForm = RDF_XML_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("XMLLiteral")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_Literal() {
        String shortForm = RDFS_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("Literal")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_PlainLiteral() {
        String shortForm = RDF_PLAIN_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("PlainLiteral")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_real() {
        String shortForm = OWL_REAL.getShortForm();
        assertThat(shortForm, is(equalTo("real")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_rational() {
        String shortForm = OWL_RATIONAL.getShortForm();
        assertThat(shortForm, is(equalTo("rational")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_string() {
        String shortForm = XSD_STRING.getShortForm();
        assertThat(shortForm, is(equalTo("string")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_normalizedString() {
        String shortForm = XSD_NORMALIZED_STRING.getShortForm();
        assertThat(shortForm, is(equalTo("normalizedString")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_token() {
        String shortForm = XSD_TOKEN.getShortForm();
        assertThat(shortForm, is(equalTo("token")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_language() {
        String shortForm = XSD_LANGUAGE.getShortForm();
        assertThat(shortForm, is(equalTo("language")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_Name() {
        String shortForm = XSD_NAME.getShortForm();
        assertThat(shortForm, is(equalTo("Name")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_NCName() {
        String shortForm = XSD_NCNAME.getShortForm();
        assertThat(shortForm, is(equalTo("NCName")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_NMTOKEN() {
        String shortForm = XSD_NMTOKEN.getShortForm();
        assertThat(shortForm, is(equalTo("NMTOKEN")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_decimal() {
        String shortForm = XSD_DECIMAL.getShortForm();
        assertThat(shortForm, is(equalTo("decimal")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_integer() {
        String shortForm = XSD_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("integer")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_nonNegativeInteger() {
        String shortForm = XSD_NON_NEGATIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("nonNegativeInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_nonPositiveInteger() {
        String shortForm = XSD_NON_POSITIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("nonPositiveInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_positiveInteger() {
        String shortForm = XSD_POSITIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("positiveInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_negativeInteger() {
        String shortForm = XSD_NEGATIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("negativeInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_long() {
        String shortForm = XSD_LONG.getShortForm();
        assertThat(shortForm, is(equalTo("long")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_int() {
        String shortForm = XSD_INT.getShortForm();
        assertThat(shortForm, is(equalTo("int")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_short() {
        String shortForm = XSD_SHORT.getShortForm();
        assertThat(shortForm, is(equalTo("short")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_byte() {
        String shortForm = XSD_BYTE.getShortForm();
        assertThat(shortForm, is(equalTo("byte")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_unsignedLong() {
        String shortForm = XSD_UNSIGNED_LONG.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedLong")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_unsignedInt() {
        String shortForm = XSD_UNSIGNED_INT.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedInt")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_unsignedShort() {
        String shortForm = XSD_UNSIGNED_SHORT.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedShort")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_unsignedByte() {
        String shortForm = XSD_UNSIGNED_BYTE.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedByte")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_double() {
        String shortForm = XSD_DOUBLE.getShortForm();
        assertThat(shortForm, is(equalTo("double")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_float() {
        String shortForm = XSD_FLOAT.getShortForm();
        assertThat(shortForm, is(equalTo("float")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_boolean() {
        String shortForm = XSD_BOOLEAN.getShortForm();
        assertThat(shortForm, is(equalTo("boolean")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_hexBinary() {
        String shortForm = XSD_HEX_BINARY.getShortForm();
        assertThat(shortForm, is(equalTo("hexBinary")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_base64Binary() {
        String shortForm = XSD_BASE_64_BINARY.getShortForm();
        assertThat(shortForm, is(equalTo("base64Binary")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_anyURI() {
        String shortForm = XSD_ANY_URI.getShortForm();
        assertThat(shortForm, is(equalTo("anyURI")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_dateTime() {
        String shortForm = XSD_DATE_TIME.getShortForm();
        assertThat(shortForm, is(equalTo("dateTime")));
    }

    @Test
    public void shouldReturnCorrectShortFormFor_dateTimeStamp() {
        String shortForm = XSD_DATE_TIME_STAMP.getShortForm();
        assertThat(shortForm, is(equalTo("dateTimeStamp")));
    }
}
