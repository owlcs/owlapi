package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class OWL2DatatypePrefixedNameTestCase {

    @Test
    void shouldReturnCorrectPrefixName() {
        assertEquals("rdf:XMLLiteral", OWL2Datatype.RDF_XML_LITERAL.getPrefixedName());
        assertEquals("rdfs:Literal", OWL2Datatype.RDFS_LITERAL.getPrefixedName());
        assertEquals("rdf:PlainLiteral", OWL2Datatype.RDF_PLAIN_LITERAL.getPrefixedName());
        assertEquals("owl:real", OWL2Datatype.OWL_REAL.getPrefixedName());
        assertEquals("owl:rational", OWL2Datatype.OWL_RATIONAL.getPrefixedName());
        assertEquals("xsd:string", OWL2Datatype.XSD_STRING.getPrefixedName());
        assertEquals("xsd:normalizedString", OWL2Datatype.XSD_NORMALIZED_STRING.getPrefixedName());
        assertEquals("xsd:token", OWL2Datatype.XSD_TOKEN.getPrefixedName());
        assertEquals("xsd:language", OWL2Datatype.XSD_LANGUAGE.getPrefixedName());
        assertEquals("xsd:Name", OWL2Datatype.XSD_NAME.getPrefixedName());
        assertEquals("xsd:NCName", OWL2Datatype.XSD_NCNAME.getPrefixedName());
        assertEquals("xsd:NMTOKEN", OWL2Datatype.XSD_NMTOKEN.getPrefixedName());
        assertEquals("xsd:decimal", OWL2Datatype.XSD_DECIMAL.getPrefixedName());
        assertEquals("xsd:integer", OWL2Datatype.XSD_INTEGER.getPrefixedName());
        assertEquals("xsd:nonNegativeInteger",
            OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getPrefixedName());
        assertEquals("xsd:nonPositiveInteger",
            OWL2Datatype.XSD_NON_POSITIVE_INTEGER.getPrefixedName());
        assertEquals("xsd:positiveInteger", OWL2Datatype.XSD_POSITIVE_INTEGER.getPrefixedName());
        assertEquals("xsd:negativeInteger", OWL2Datatype.XSD_NEGATIVE_INTEGER.getPrefixedName());
        assertEquals("xsd:long", OWL2Datatype.XSD_LONG.getPrefixedName());
        assertEquals("xsd:int", OWL2Datatype.XSD_INT.getPrefixedName());
        assertEquals("xsd:short", OWL2Datatype.XSD_SHORT.getPrefixedName());
        assertEquals("xsd:byte", OWL2Datatype.XSD_BYTE.getPrefixedName());
        assertEquals("xsd:unsignedLong", OWL2Datatype.XSD_UNSIGNED_LONG.getPrefixedName());
        assertEquals("xsd:unsignedInt", OWL2Datatype.XSD_UNSIGNED_INT.getPrefixedName());
        assertEquals("xsd:unsignedShort", OWL2Datatype.XSD_UNSIGNED_SHORT.getPrefixedName());
        assertEquals("xsd:unsignedByte", OWL2Datatype.XSD_UNSIGNED_BYTE.getPrefixedName());
        assertEquals("xsd:double", OWL2Datatype.XSD_DOUBLE.getPrefixedName());
        assertEquals("xsd:float", OWL2Datatype.XSD_FLOAT.getPrefixedName());
        assertEquals("xsd:boolean", OWL2Datatype.XSD_BOOLEAN.getPrefixedName());
        assertEquals("xsd:hexBinary", OWL2Datatype.XSD_HEX_BINARY.getPrefixedName());
        assertEquals("xsd:base64Binary", OWL2Datatype.XSD_BASE_64_BINARY.getPrefixedName());
        assertEquals("xsd:anyURI", OWL2Datatype.XSD_ANY_URI.getPrefixedName());
        assertEquals("xsd:dateTime", OWL2Datatype.XSD_DATE_TIME.getPrefixedName());
        assertEquals("xsd:dateTimeStamp", OWL2Datatype.XSD_DATE_TIME_STAMP.getPrefixedName());
    }
}
