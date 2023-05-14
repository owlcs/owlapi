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
class OWL2DatatypePrefixedNameTestCase {

    static Stream<Arguments> prefixName() {
        return Stream.of(Arguments.of("rdf:XMLLiteral", OWL2Datatype.RDF_XML_LITERAL),
            Arguments.of("rdfs:Literal", OWL2Datatype.RDFS_LITERAL),
            Arguments.of("rdf:PlainLiteral", OWL2Datatype.RDF_PLAIN_LITERAL),
            Arguments.of("owl:real", OWL2Datatype.OWL_REAL),
            Arguments.of("owl:rational", OWL2Datatype.OWL_RATIONAL),
            Arguments.of("xsd:string", OWL2Datatype.XSD_STRING),
            Arguments.of("xsd:normalizedString", OWL2Datatype.XSD_NORMALIZED_STRING),
            Arguments.of("xsd:token", OWL2Datatype.XSD_TOKEN),
            Arguments.of("xsd:language", OWL2Datatype.XSD_LANGUAGE),
            Arguments.of("xsd:Name", OWL2Datatype.XSD_NAME),
            Arguments.of("xsd:NCName", OWL2Datatype.XSD_NCNAME),
            Arguments.of("xsd:NMTOKEN", OWL2Datatype.XSD_NMTOKEN),
            Arguments.of("xsd:decimal", OWL2Datatype.XSD_DECIMAL),
            Arguments.of("xsd:integer", OWL2Datatype.XSD_INTEGER),
            Arguments.of("xsd:nonNegativeInteger", OWL2Datatype.XSD_NON_NEGATIVE_INTEGER),
            Arguments.of("xsd:nonPositiveInteger", OWL2Datatype.XSD_NON_POSITIVE_INTEGER),
            Arguments.of("xsd:positiveInteger", OWL2Datatype.XSD_POSITIVE_INTEGER),
            Arguments.of("xsd:negativeInteger", OWL2Datatype.XSD_NEGATIVE_INTEGER),
            Arguments.of("xsd:long", OWL2Datatype.XSD_LONG),
            Arguments.of("xsd:int", OWL2Datatype.XSD_INT),
            Arguments.of("xsd:short", OWL2Datatype.XSD_SHORT),
            Arguments.of("xsd:byte", OWL2Datatype.XSD_BYTE),
            Arguments.of("xsd:unsignedLong", OWL2Datatype.XSD_UNSIGNED_LONG),
            Arguments.of("xsd:unsignedInt", OWL2Datatype.XSD_UNSIGNED_INT),
            Arguments.of("xsd:unsignedShort", OWL2Datatype.XSD_UNSIGNED_SHORT),
            Arguments.of("xsd:unsignedByte", OWL2Datatype.XSD_UNSIGNED_BYTE),
            Arguments.of("xsd:double", OWL2Datatype.XSD_DOUBLE),
            Arguments.of("xsd:float", OWL2Datatype.XSD_FLOAT),
            Arguments.of("xsd:boolean", OWL2Datatype.XSD_BOOLEAN),
            Arguments.of("xsd:hexBinary", OWL2Datatype.XSD_HEX_BINARY),
            Arguments.of("xsd:base64Binary", OWL2Datatype.XSD_BASE_64_BINARY),
            Arguments.of("xsd:anyURI", OWL2Datatype.XSD_ANY_URI),
            Arguments.of("xsd:dateTime", OWL2Datatype.XSD_DATE_TIME),
            Arguments.of("xsd:dateTimeStamp", OWL2Datatype.XSD_DATE_TIME_STAMP));
    }

    @ParameterizedTest
    @MethodSource("prefixName")
    void expectEquals(String expected, OWL2Datatype prefixed) {
        assertEquals(expected, prefixed.getPrefixedName());
    }
}
