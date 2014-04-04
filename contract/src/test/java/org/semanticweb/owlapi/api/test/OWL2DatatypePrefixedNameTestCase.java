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
public class OWL2DatatypePrefixedNameTestCase {

    @Test
    public void shouldReturnCorrectPrefixNameFor_XMLLiteral() {
        String prefixedName = RDF_XML_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdf:XMLLiteral")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_Literal() {
        String prefixedName = RDFS_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdfs:Literal")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_PlainLiteral() {
        String prefixedName = RDF_PLAIN_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdf:PlainLiteral")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_real() {
        String prefixedName = OWL_REAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("owl:real")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_rational() {
        String prefixedName = OWL_RATIONAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("owl:rational")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_string() {
        String prefixedName = XSD_STRING.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:string")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_normalizedString() {
        String prefixedName = XSD_NORMALIZED_STRING.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:normalizedString")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_token() {
        String prefixedName = XSD_TOKEN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:token")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_language() {
        String prefixedName = XSD_LANGUAGE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:language")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_Name() {
        String prefixedName = XSD_NAME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:Name")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_NCName() {
        String prefixedName = XSD_NCNAME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:NCName")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_NMTOKEN() {
        String prefixedName = XSD_NMTOKEN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:NMTOKEN")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_decimal() {
        String prefixedName = XSD_DECIMAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:decimal")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_integer() {
        String prefixedName = XSD_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:integer")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_nonNegativeInteger() {
        String prefixedName = XSD_NON_NEGATIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:nonNegativeInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_nonPositiveInteger() {
        String prefixedName = XSD_NON_POSITIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:nonPositiveInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_positiveInteger() {
        String prefixedName = XSD_POSITIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:positiveInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_negativeInteger() {
        String prefixedName = XSD_NEGATIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:negativeInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_long() {
        String prefixedName = XSD_LONG.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:long")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_int() {
        String prefixedName = XSD_INT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:int")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_short() {
        String prefixedName = XSD_SHORT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:short")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_byte() {
        String prefixedName = XSD_BYTE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:byte")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_unsignedLong() {
        String prefixedName = XSD_UNSIGNED_LONG.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedLong")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_unsignedInt() {
        String prefixedName = XSD_UNSIGNED_INT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedInt")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_unsignedShort() {
        String prefixedName = XSD_UNSIGNED_SHORT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedShort")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_unsignedByte() {
        String prefixedName = XSD_UNSIGNED_BYTE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedByte")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_double() {
        String prefixedName = XSD_DOUBLE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:double")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_float() {
        String prefixedName = XSD_FLOAT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:float")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_boolean() {
        String prefixedName = XSD_BOOLEAN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:boolean")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_hexBinary() {
        String prefixedName = XSD_HEX_BINARY.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:hexBinary")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_base64Binary() {
        String prefixedName = XSD_BASE_64_BINARY.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:base64Binary")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_anyURI() {
        String prefixedName = XSD_ANY_URI.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:anyURI")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_dateTime() {
        String prefixedName = XSD_DATE_TIME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:dateTime")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFor_dateTimeStamp() {
        String prefixedName = XSD_DATE_TIME_STAMP.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:dateTimeStamp")));
    }
}
