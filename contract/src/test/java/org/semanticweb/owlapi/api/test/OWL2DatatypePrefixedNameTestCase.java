package org.semanticweb.owlapi.api.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.OWL_RATIONAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.OWL_REAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_XML_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_ANY_URI;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BASE_64_BINARY;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BYTE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DATE_TIME;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DATE_TIME_STAMP;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DECIMAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_HEX_BINARY;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_INT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_LANGUAGE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_LONG;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NAME;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NCNAME;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NEGATIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NMTOKEN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NON_POSITIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_NORMALIZED_STRING;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_POSITIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_SHORT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_TOKEN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_UNSIGNED_BYTE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_UNSIGNED_INT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_UNSIGNED_LONG;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_UNSIGNED_SHORT;

import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
@SuppressWarnings("javadoc")
public class OWL2DatatypePrefixedNameTestCase {

    @Test
    public void shouldReturnCorrectPrefixNameForXMLLiteral() {
        String prefixedName = RDF_XML_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdf:XMLLiteral")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForLiteral() {
        String prefixedName = RDFS_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdfs:Literal")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForPlainLiteral() {
        String prefixedName = RDF_PLAIN_LITERAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("rdf:PlainLiteral")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForreal() {
        String prefixedName = OWL_REAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("owl:real")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForrational() {
        String prefixedName = OWL_RATIONAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("owl:rational")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForstring() {
        String prefixedName = XSD_STRING.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:string")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornormalizedString() {
        String prefixedName = XSD_NORMALIZED_STRING.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:normalizedString")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFortoken() {
        String prefixedName = XSD_TOKEN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:token")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForlanguage() {
        String prefixedName = XSD_LANGUAGE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:language")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForName() {
        String prefixedName = XSD_NAME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:Name")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForNCName() {
        String prefixedName = XSD_NCNAME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:NCName")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForNMTOKEN() {
        String prefixedName = XSD_NMTOKEN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:NMTOKEN")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordecimal() {
        String prefixedName = XSD_DECIMAL.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:decimal")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForinteger() {
        String prefixedName = XSD_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:integer")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornonNegativeInteger() {
        String prefixedName = XSD_NON_NEGATIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:nonNegativeInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornonPositiveInteger() {
        String prefixedName = XSD_NON_POSITIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:nonPositiveInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForpositiveInteger() {
        String prefixedName = XSD_POSITIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:positiveInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornegativeInteger() {
        String prefixedName = XSD_NEGATIVE_INTEGER.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:negativeInteger")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForlong() {
        String prefixedName = XSD_LONG.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:long")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForint() {
        String prefixedName = XSD_INT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:int")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForshort() {
        String prefixedName = XSD_SHORT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:short")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForbyte() {
        String prefixedName = XSD_BYTE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:byte")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedLong() {
        String prefixedName = XSD_UNSIGNED_LONG.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedLong")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedInt() {
        String prefixedName = XSD_UNSIGNED_INT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedInt")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedShort() {
        String prefixedName = XSD_UNSIGNED_SHORT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedShort")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedByte() {
        String prefixedName = XSD_UNSIGNED_BYTE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:unsignedByte")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordouble() {
        String prefixedName = XSD_DOUBLE.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:double")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForfloat() {
        String prefixedName = XSD_FLOAT.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:float")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForboolean() {
        String prefixedName = XSD_BOOLEAN.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:boolean")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForhexBinary() {
        String prefixedName = XSD_HEX_BINARY.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:hexBinary")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForbase64Binary() {
        String prefixedName = XSD_BASE_64_BINARY.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:base64Binary")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameForanyURI() {
        String prefixedName = XSD_ANY_URI.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:anyURI")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordateTime() {
        String prefixedName = XSD_DATE_TIME.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:dateTime")));
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordateTimeStamp() {
        String prefixedName = XSD_DATE_TIME_STAMP.getPrefixedName();
        assertThat(prefixedName, is(equalTo("xsd:dateTimeStamp")));
    }
}
