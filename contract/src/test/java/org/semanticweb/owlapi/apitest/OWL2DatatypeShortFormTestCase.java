package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class OWL2DatatypeShortFormTestCase {

    static Stream<Arguments> values() {
        return Stream.of(Arguments.of("XMLLiteral", OWL2Datatype.RDF_XML_LITERAL),
            Arguments.of("Literal", OWL2Datatype.RDFS_LITERAL),
            Arguments.of("PlainLiteral", OWL2Datatype.RDF_PLAIN_LITERAL),
            Arguments.of("real", OWL2Datatype.OWL_REAL),
            Arguments.of("rational", OWL2Datatype.OWL_RATIONAL),
            Arguments.of("string", OWL2Datatype.XSD_STRING),
            Arguments.of("normalizedString", OWL2Datatype.XSD_NORMALIZED_STRING),
            Arguments.of("token", OWL2Datatype.XSD_TOKEN),
            Arguments.of("language", OWL2Datatype.XSD_LANGUAGE),
            Arguments.of("Name", OWL2Datatype.XSD_NAME),
            Arguments.of("NCName", OWL2Datatype.XSD_NCNAME),
            Arguments.of("NMTOKEN", OWL2Datatype.XSD_NMTOKEN),
            Arguments.of("decimal", OWL2Datatype.XSD_DECIMAL),
            Arguments.of("integer", OWL2Datatype.XSD_INTEGER),
            Arguments.of("nonNegativeInteger", OWL2Datatype.XSD_NON_NEGATIVE_INTEGER),
            Arguments.of("nonPositiveInteger", OWL2Datatype.XSD_NON_POSITIVE_INTEGER),
            Arguments.of("positiveInteger", OWL2Datatype.XSD_POSITIVE_INTEGER),
            Arguments.of("negativeInteger", OWL2Datatype.XSD_NEGATIVE_INTEGER),
            Arguments.of("long", OWL2Datatype.XSD_LONG), Arguments.of("int", OWL2Datatype.XSD_INT),
            Arguments.of("short", OWL2Datatype.XSD_SHORT),
            Arguments.of("byte", OWL2Datatype.XSD_BYTE),
            Arguments.of("unsignedLong", OWL2Datatype.XSD_UNSIGNED_LONG),
            Arguments.of("unsignedInt", OWL2Datatype.XSD_UNSIGNED_INT),
            Arguments.of("unsignedShort", OWL2Datatype.XSD_UNSIGNED_SHORT),
            Arguments.of("unsignedByte", OWL2Datatype.XSD_UNSIGNED_BYTE),
            Arguments.of("double", OWL2Datatype.XSD_DOUBLE),
            Arguments.of("float", OWL2Datatype.XSD_FLOAT),
            Arguments.of("boolean", OWL2Datatype.XSD_BOOLEAN),
            Arguments.of("hexBinary", OWL2Datatype.XSD_HEX_BINARY),
            Arguments.of("base64Binary", OWL2Datatype.XSD_BASE_64_BINARY),
            Arguments.of("anyURI", OWL2Datatype.XSD_ANY_URI),
            Arguments.of("dateTime", OWL2Datatype.XSD_DATE_TIME),
            Arguments.of("dateTimeStamp", OWL2Datatype.XSD_DATE_TIME_STAMP));
    }

    @ParameterizedTest
    @MethodSource("values")
    void equal(String expected, OWL2Datatype rdfXmlLiteral) {
        assertEquals(expected, rdfXmlLiteral.getShortForm());
    }
}
