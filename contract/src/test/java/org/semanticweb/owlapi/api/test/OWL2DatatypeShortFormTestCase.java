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
    public void shouldReturnCorrectShortFormForXMLLiteral() {
        String shortForm = RDF_XML_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("XMLLiteral")));
    }

    @Test
    public void shouldReturnCorrectShortFormForLiteral() {
        String shortForm = RDFS_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("Literal")));
    }

    @Test
    public void shouldReturnCorrectShortFormForPlainLiteral() {
        String shortForm = RDF_PLAIN_LITERAL.getShortForm();
        assertThat(shortForm, is(equalTo("PlainLiteral")));
    }

    @Test
    public void shouldReturnCorrectShortFormForreal() {
        String shortForm = OWL_REAL.getShortForm();
        assertThat(shortForm, is(equalTo("real")));
    }

    @Test
    public void shouldReturnCorrectShortFormForrational() {
        String shortForm = OWL_RATIONAL.getShortForm();
        assertThat(shortForm, is(equalTo("rational")));
    }

    @Test
    public void shouldReturnCorrectShortFormForstring() {
        String shortForm = XSD_STRING.getShortForm();
        assertThat(shortForm, is(equalTo("string")));
    }

    @Test
    public void shouldReturnCorrectShortFormFornormalizedString() {
        String shortForm = XSD_NORMALIZED_STRING.getShortForm();
        assertThat(shortForm, is(equalTo("normalizedString")));
    }

    @Test
    public void shouldReturnCorrectShortFormFortoken() {
        String shortForm = XSD_TOKEN.getShortForm();
        assertThat(shortForm, is(equalTo("token")));
    }

    @Test
    public void shouldReturnCorrectShortFormForlanguage() {
        String shortForm = XSD_LANGUAGE.getShortForm();
        assertThat(shortForm, is(equalTo("language")));
    }

    @Test
    public void shouldReturnCorrectShortFormForName() {
        String shortForm = XSD_NAME.getShortForm();
        assertThat(shortForm, is(equalTo("Name")));
    }

    @Test
    public void shouldReturnCorrectShortFormForNCName() {
        String shortForm = XSD_NCNAME.getShortForm();
        assertThat(shortForm, is(equalTo("NCName")));
    }

    @Test
    public void shouldReturnCorrectShortFormForNMTOKEN() {
        String shortForm = XSD_NMTOKEN.getShortForm();
        assertThat(shortForm, is(equalTo("NMTOKEN")));
    }

    @Test
    public void shouldReturnCorrectShortFormFordecimal() {
        String shortForm = XSD_DECIMAL.getShortForm();
        assertThat(shortForm, is(equalTo("decimal")));
    }

    @Test
    public void shouldReturnCorrectShortFormForinteger() {
        String shortForm = XSD_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("integer")));
    }

    @Test
    public void shouldReturnCorrectShortFormFornonNegativeInteger() {
        String shortForm = XSD_NON_NEGATIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("nonNegativeInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFornonPositiveInteger() {
        String shortForm = XSD_NON_POSITIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("nonPositiveInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormForpositiveInteger() {
        String shortForm = XSD_POSITIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("positiveInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormFornegativeInteger() {
        String shortForm = XSD_NEGATIVE_INTEGER.getShortForm();
        assertThat(shortForm, is(equalTo("negativeInteger")));
    }

    @Test
    public void shouldReturnCorrectShortFormForlong() {
        String shortForm = XSD_LONG.getShortForm();
        assertThat(shortForm, is(equalTo("long")));
    }

    @Test
    public void shouldReturnCorrectShortFormForint() {
        String shortForm = XSD_INT.getShortForm();
        assertThat(shortForm, is(equalTo("int")));
    }

    @Test
    public void shouldReturnCorrectShortFormForshort() {
        String shortForm = XSD_SHORT.getShortForm();
        assertThat(shortForm, is(equalTo("short")));
    }

    @Test
    public void shouldReturnCorrectShortFormForbyte() {
        String shortForm = XSD_BYTE.getShortForm();
        assertThat(shortForm, is(equalTo("byte")));
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedLong() {
        String shortForm = XSD_UNSIGNED_LONG.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedLong")));
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedInt() {
        String shortForm = XSD_UNSIGNED_INT.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedInt")));
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedShort() {
        String shortForm = XSD_UNSIGNED_SHORT.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedShort")));
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedByte() {
        String shortForm = XSD_UNSIGNED_BYTE.getShortForm();
        assertThat(shortForm, is(equalTo("unsignedByte")));
    }

    @Test
    public void shouldReturnCorrectShortFormFordouble() {
        String shortForm = XSD_DOUBLE.getShortForm();
        assertThat(shortForm, is(equalTo("double")));
    }

    @Test
    public void shouldReturnCorrectShortFormForfloat() {
        String shortForm = XSD_FLOAT.getShortForm();
        assertThat(shortForm, is(equalTo("float")));
    }

    @Test
    public void shouldReturnCorrectShortFormForboolean() {
        String shortForm = XSD_BOOLEAN.getShortForm();
        assertThat(shortForm, is(equalTo("boolean")));
    }

    @Test
    public void shouldReturnCorrectShortFormForhexBinary() {
        String shortForm = XSD_HEX_BINARY.getShortForm();
        assertThat(shortForm, is(equalTo("hexBinary")));
    }

    @Test
    public void shouldReturnCorrectShortFormForbase64Binary() {
        String shortForm = XSD_BASE_64_BINARY.getShortForm();
        assertThat(shortForm, is(equalTo("base64Binary")));
    }

    @Test
    public void shouldReturnCorrectShortFormForanyURI() {
        String shortForm = XSD_ANY_URI.getShortForm();
        assertThat(shortForm, is(equalTo("anyURI")));
    }

    @Test
    public void shouldReturnCorrectShortFormFordateTime() {
        String shortForm = XSD_DATE_TIME.getShortForm();
        assertThat(shortForm, is(equalTo("dateTime")));
    }

    @Test
    public void shouldReturnCorrectShortFormFordateTimeStamp() {
        String shortForm = XSD_DATE_TIME_STAMP.getShortForm();
        assertThat(shortForm, is(equalTo("dateTimeStamp")));
    }
}
