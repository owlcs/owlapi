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
public class OWL2DatatypeShortFormTestCase {

    @Test
    public void shouldReturnCorrectShortFormForXMLLiteral() {
        String shortForm = RDF_XML_LITERAL.getShortForm();
        assertEquals("XMLLiteral", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForLiteral() {
        String shortForm = RDFS_LITERAL.getShortForm();
        assertEquals("Literal", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForPlainLiteral() {
        String shortForm = RDF_PLAIN_LITERAL.getShortForm();
        assertEquals("PlainLiteral", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForreal() {
        String shortForm = OWL_REAL.getShortForm();
        assertEquals("real", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForrational() {
        String shortForm = OWL_RATIONAL.getShortForm();
        assertEquals("rational", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForstring() {
        String shortForm = XSD_STRING.getShortForm();
        assertEquals("string", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFornormalizedString() {
        String shortForm = XSD_NORMALIZED_STRING.getShortForm();
        assertEquals("normalizedString", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFortoken() {
        String shortForm = XSD_TOKEN.getShortForm();
        assertEquals("token", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForlanguage() {
        String shortForm = XSD_LANGUAGE.getShortForm();
        assertEquals("language", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForName() {
        String shortForm = XSD_NAME.getShortForm();
        assertEquals("Name", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForNCName() {
        String shortForm = XSD_NCNAME.getShortForm();
        assertEquals("NCName", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForNMTOKEN() {
        String shortForm = XSD_NMTOKEN.getShortForm();
        assertEquals("NMTOKEN", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFordecimal() {
        String shortForm = XSD_DECIMAL.getShortForm();
        assertEquals("decimal", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForinteger() {
        String shortForm = XSD_INTEGER.getShortForm();
        assertEquals("integer", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFornonNegativeInteger() {
        String shortForm = XSD_NON_NEGATIVE_INTEGER.getShortForm();
        assertEquals("nonNegativeInteger", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFornonPositiveInteger() {
        String shortForm = XSD_NON_POSITIVE_INTEGER.getShortForm();
        assertEquals("nonPositiveInteger", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForpositiveInteger() {
        String shortForm = XSD_POSITIVE_INTEGER.getShortForm();
        assertEquals("positiveInteger", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFornegativeInteger() {
        String shortForm = XSD_NEGATIVE_INTEGER.getShortForm();
        assertEquals("negativeInteger", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForlong() {
        String shortForm = XSD_LONG.getShortForm();
        assertEquals("long", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForint() {
        String shortForm = XSD_INT.getShortForm();
        assertEquals("int", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForshort() {
        String shortForm = XSD_SHORT.getShortForm();
        assertEquals("short", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForbyte() {
        String shortForm = XSD_BYTE.getShortForm();
        assertEquals("byte", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedLong() {
        String shortForm = XSD_UNSIGNED_LONG.getShortForm();
        assertEquals("unsignedLong", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedInt() {
        String shortForm = XSD_UNSIGNED_INT.getShortForm();
        assertEquals("unsignedInt", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedShort() {
        String shortForm = XSD_UNSIGNED_SHORT.getShortForm();
        assertEquals("unsignedShort", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForunsignedByte() {
        String shortForm = XSD_UNSIGNED_BYTE.getShortForm();
        assertEquals("unsignedByte", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFordouble() {
        String shortForm = XSD_DOUBLE.getShortForm();
        assertEquals("double", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForfloat() {
        String shortForm = XSD_FLOAT.getShortForm();
        assertEquals("float", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForboolean() {
        String shortForm = XSD_BOOLEAN.getShortForm();
        assertEquals("boolean", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForhexBinary() {
        String shortForm = XSD_HEX_BINARY.getShortForm();
        assertEquals("hexBinary", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForbase64Binary() {
        String shortForm = XSD_BASE_64_BINARY.getShortForm();
        assertEquals("base64Binary", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormForanyURI() {
        String shortForm = XSD_ANY_URI.getShortForm();
        assertEquals("anyURI", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFordateTime() {
        String shortForm = XSD_DATE_TIME.getShortForm();
        assertEquals("dateTime", shortForm);
    }

    @Test
    public void shouldReturnCorrectShortFormFordateTimeStamp() {
        String shortForm = XSD_DATE_TIME_STAMP.getShortForm();
        assertEquals("dateTimeStamp", shortForm);
    }
}
