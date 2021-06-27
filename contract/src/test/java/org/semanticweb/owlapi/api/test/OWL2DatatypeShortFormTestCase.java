package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class OWL2DatatypeShortFormTestCase {

    @Test
    void shouldReturnCorrectShortForm() {
        assertEquals("XMLLiteral", OWL2Datatype.RDF_XML_LITERAL.getShortForm());
        assertEquals("Literal", OWL2Datatype.RDFS_LITERAL.getShortForm());
        assertEquals("PlainLiteral", OWL2Datatype.RDF_PLAIN_LITERAL.getShortForm());
        assertEquals("real", OWL2Datatype.OWL_REAL.getShortForm());
        assertEquals("rational", OWL2Datatype.OWL_RATIONAL.getShortForm());
        assertEquals("string", OWL2Datatype.XSD_STRING.getShortForm());
        assertEquals("normalizedString", OWL2Datatype.XSD_NORMALIZED_STRING.getShortForm());
        assertEquals("token", OWL2Datatype.XSD_TOKEN.getShortForm());
        assertEquals("language", OWL2Datatype.XSD_LANGUAGE.getShortForm());
        assertEquals("Name", OWL2Datatype.XSD_NAME.getShortForm());
        assertEquals("NCName", OWL2Datatype.XSD_NCNAME.getShortForm());
        assertEquals("NMTOKEN", OWL2Datatype.XSD_NMTOKEN.getShortForm());
        assertEquals("decimal", OWL2Datatype.XSD_DECIMAL.getShortForm());
        assertEquals("integer", OWL2Datatype.XSD_INTEGER.getShortForm());
        assertEquals("nonNegativeInteger", OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getShortForm());
        assertEquals("nonPositiveInteger", OWL2Datatype.XSD_NON_POSITIVE_INTEGER.getShortForm());
        assertEquals("positiveInteger", OWL2Datatype.XSD_POSITIVE_INTEGER.getShortForm());
        assertEquals("negativeInteger", OWL2Datatype.XSD_NEGATIVE_INTEGER.getShortForm());
        assertEquals("long", OWL2Datatype.XSD_LONG.getShortForm());
        assertEquals("int", OWL2Datatype.XSD_INT.getShortForm());
        assertEquals("short", OWL2Datatype.XSD_SHORT.getShortForm());
        assertEquals("byte", OWL2Datatype.XSD_BYTE.getShortForm());
        assertEquals("unsignedLong", OWL2Datatype.XSD_UNSIGNED_LONG.getShortForm());
        assertEquals("unsignedInt", OWL2Datatype.XSD_UNSIGNED_INT.getShortForm());
        assertEquals("unsignedShort", OWL2Datatype.XSD_UNSIGNED_SHORT.getShortForm());
        assertEquals("unsignedByte", OWL2Datatype.XSD_UNSIGNED_BYTE.getShortForm());
        assertEquals("double", OWL2Datatype.XSD_DOUBLE.getShortForm());
        assertEquals("float", OWL2Datatype.XSD_FLOAT.getShortForm());
        assertEquals("boolean", OWL2Datatype.XSD_BOOLEAN.getShortForm());
        assertEquals("hexBinary", OWL2Datatype.XSD_HEX_BINARY.getShortForm());
        assertEquals("base64Binary", OWL2Datatype.XSD_BASE_64_BINARY.getShortForm());
        assertEquals("anyURI", OWL2Datatype.XSD_ANY_URI.getShortForm());
        assertEquals("dateTime", OWL2Datatype.XSD_DATE_TIME.getShortForm());
        assertEquals("dateTimeStamp", OWL2Datatype.XSD_DATE_TIME_STAMP.getShortForm());
    }
}
