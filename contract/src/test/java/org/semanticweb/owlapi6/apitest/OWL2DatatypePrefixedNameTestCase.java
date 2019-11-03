package org.semanticweb.owlapi6.apitest;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.OWL_RATIONAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.OWL_REAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.RDF_XML_LITERAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_ANY_URI;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_BASE_64_BINARY;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_BYTE;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_DATE_TIME;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_DATE_TIME_STAMP;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_DECIMAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_HEX_BINARY;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_INT;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_LANGUAGE;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_LONG;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NAME;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NCNAME;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NEGATIVE_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NMTOKEN;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NON_POSITIVE_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_NORMALIZED_STRING;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_POSITIVE_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_SHORT;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_STRING;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_TOKEN;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_UNSIGNED_BYTE;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_UNSIGNED_INT;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_UNSIGNED_LONG;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_UNSIGNED_SHORT;

import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
public class OWL2DatatypePrefixedNameTestCase {

    @Test
    public void shouldReturnCorrectPrefixNameForXMLLiteral() {
        String prefixedName = RDF_XML_LITERAL.getPrefixedName();
        assertEquals("rdf:XMLLiteral", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForLiteral() {
        String prefixedName = RDFS_LITERAL.getPrefixedName();
        assertEquals("rdfs:Literal", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForPlainLiteral() {
        String prefixedName = RDF_PLAIN_LITERAL.getPrefixedName();
        assertEquals("rdf:PlainLiteral", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForreal() {
        String prefixedName = OWL_REAL.getPrefixedName();
        assertEquals("owl:real", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForrational() {
        String prefixedName = OWL_RATIONAL.getPrefixedName();
        assertEquals("owl:rational", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForstring() {
        String prefixedName = XSD_STRING.getPrefixedName();
        assertEquals("xsd:string", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornormalizedString() {
        String prefixedName = XSD_NORMALIZED_STRING.getPrefixedName();
        assertEquals("xsd:normalizedString", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFortoken() {
        String prefixedName = XSD_TOKEN.getPrefixedName();
        assertEquals("xsd:token", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForlanguage() {
        String prefixedName = XSD_LANGUAGE.getPrefixedName();
        assertEquals("xsd:language", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForName() {
        String prefixedName = XSD_NAME.getPrefixedName();
        assertEquals("xsd:Name", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForNCName() {
        String prefixedName = XSD_NCNAME.getPrefixedName();
        assertEquals("xsd:NCName", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForNMTOKEN() {
        String prefixedName = XSD_NMTOKEN.getPrefixedName();
        assertEquals("xsd:NMTOKEN", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordecimal() {
        String prefixedName = XSD_DECIMAL.getPrefixedName();
        assertEquals("xsd:decimal", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForinteger() {
        String prefixedName = XSD_INTEGER.getPrefixedName();
        assertEquals("xsd:integer", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornonNegativeInteger() {
        String prefixedName = XSD_NON_NEGATIVE_INTEGER.getPrefixedName();
        assertEquals("xsd:nonNegativeInteger", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornonPositiveInteger() {
        String prefixedName = XSD_NON_POSITIVE_INTEGER.getPrefixedName();
        assertEquals("xsd:nonPositiveInteger", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForpositiveInteger() {
        String prefixedName = XSD_POSITIVE_INTEGER.getPrefixedName();
        assertEquals("xsd:positiveInteger", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFornegativeInteger() {
        String prefixedName = XSD_NEGATIVE_INTEGER.getPrefixedName();
        assertEquals("xsd:negativeInteger", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForlong() {
        String prefixedName = XSD_LONG.getPrefixedName();
        assertEquals("xsd:long", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForint() {
        String prefixedName = XSD_INT.getPrefixedName();
        assertEquals("xsd:int", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForshort() {
        String prefixedName = XSD_SHORT.getPrefixedName();
        assertEquals("xsd:short", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForbyte() {
        String prefixedName = XSD_BYTE.getPrefixedName();
        assertEquals("xsd:byte", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedLong() {
        String prefixedName = XSD_UNSIGNED_LONG.getPrefixedName();
        assertEquals("xsd:unsignedLong", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedInt() {
        String prefixedName = XSD_UNSIGNED_INT.getPrefixedName();
        assertEquals("xsd:unsignedInt", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedShort() {
        String prefixedName = XSD_UNSIGNED_SHORT.getPrefixedName();
        assertEquals("xsd:unsignedShort", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForunsignedByte() {
        String prefixedName = XSD_UNSIGNED_BYTE.getPrefixedName();
        assertEquals("xsd:unsignedByte", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordouble() {
        String prefixedName = XSD_DOUBLE.getPrefixedName();
        assertEquals("xsd:double", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForfloat() {
        String prefixedName = XSD_FLOAT.getPrefixedName();
        assertEquals("xsd:float", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForboolean() {
        String prefixedName = XSD_BOOLEAN.getPrefixedName();
        assertEquals("xsd:boolean", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForhexBinary() {
        String prefixedName = XSD_HEX_BINARY.getPrefixedName();
        assertEquals("xsd:hexBinary", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForbase64Binary() {
        String prefixedName = XSD_BASE_64_BINARY.getPrefixedName();
        assertEquals("xsd:base64Binary", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameForanyURI() {
        String prefixedName = XSD_ANY_URI.getPrefixedName();
        assertEquals("xsd:anyURI", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordateTime() {
        String prefixedName = XSD_DATE_TIME.getPrefixedName();
        assertEquals("xsd:dateTime", prefixedName);
    }

    @Test
    public void shouldReturnCorrectPrefixNameFordateTimeStamp() {
        String prefixedName = XSD_DATE_TIME_STAMP.getPrefixedName();
        assertEquals("xsd:dateTimeStamp", prefixedName);
    }
}
