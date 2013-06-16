package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/** Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 24/10/2012 */
@SuppressWarnings("javadoc")
public class OWL2DatatypeImplTestCase {
    private OWLDatatype plainLiteral;

    @Before
    public void setUp() {
        plainLiteral = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());
    }

    @Test
    public void getBuiltInDatatype() {
        assertEquals(OWL2Datatype.RDF_PLAIN_LITERAL, plainLiteral.getBuiltInDatatype());
    }

    @Test
    public void isString() {
        assertFalse(plainLiteral.isString());
        OWLDatatype string = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_STRING.getIRI());
        assertTrue(string.isString());
    }

    @Test
    public void isInteger() {
        assertFalse(plainLiteral.isInteger());
        OWLDatatype integer = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_INTEGER.getIRI());
        assertTrue(integer.isInteger());
    }

    @Test
    public void isFloat() {
        assertFalse(plainLiteral.isFloat());
        OWLDatatype floatDatatype = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_FLOAT.getIRI());
        assertTrue(floatDatatype.isFloat());
    }

    @Test
    public void isDouble() {
        assertFalse(plainLiteral.isDouble());
        OWLDatatype doubleDatatype = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_DOUBLE.getIRI());
        assertTrue(doubleDatatype.isDouble());
    }

    @Test
    public void isBoolean() {
        assertFalse(plainLiteral.isBoolean());
        OWLDatatype booleanDatatype = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_BOOLEAN.getIRI());
        assertTrue(booleanDatatype.isBoolean());
    }

    @Test
    public void isRDFPlainLiteral() {
        assertTrue(plainLiteral.isRDFPlainLiteral());
        OWLDatatype stringDatatype = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_STRING.getIRI());
        assertFalse(stringDatatype.isRDFPlainLiteral());
    }

    @Test
    public void isDatatype() {
        assertTrue(plainLiteral.isDatatype());
    }

    @Test
    public void asOWLDatatype() {
        assertEquals(plainLiteral, plainLiteral.asOWLDatatype());
    }

    @Test
    public void isTopDatatype() {
        OWLDatatype rdfsLiteralDatatype = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.RDFS_LITERAL.getIRI());
        assertTrue(rdfsLiteralDatatype.isTopDatatype());
        assertFalse(plainLiteral.isTopDatatype());
    }

    @Test
    public void getDataRangeType() {
        assertEquals(DataRangeType.DATATYPE, plainLiteral.getDataRangeType());
    }

    @Test
    public void getEntityType() {
        assertEquals(EntityType.DATATYPE, plainLiteral.getEntityType());
    }

    @Test
    public void isType() {
        assertTrue(plainLiteral.isType(EntityType.DATATYPE));
        assertFalse(plainLiteral.isType(EntityType.CLASS));
        assertFalse(plainLiteral.isType(EntityType.OBJECT_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.DATA_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.ANNOTATION_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.NAMED_INDIVIDUAL));
    }

    @Test
    public void isBuiltIn() {
        assertTrue(plainLiteral.isBuiltIn());
    }

    @Test
    public void isOWLClass() {
        assertFalse(plainLiteral.isOWLClass());
    }

    @Test(expected = RuntimeException.class)
    public void asOWLClass() {
        plainLiteral.asOWLClass();
    }

    @Test
    public void isOWLObjectProperty() {
        assertFalse(plainLiteral.isOWLObjectProperty());
    }

    @Test(expected = RuntimeException.class)
    public void asOWLObjectProperty() {
        plainLiteral.asOWLObjectProperty();
    }

    @Test
    public void isOWLDataProperty() {
        assertFalse(plainLiteral.isOWLDataProperty());
    }

    @Test(expected = RuntimeException.class)
    public void asOWLDataProperty() {
        plainLiteral.asOWLDataProperty();
    }

    @Test
    public void isOWLAnnotationProperty() {
        assertFalse(plainLiteral.isOWLAnnotationProperty());
    }

    @Test(expected = RuntimeException.class)
    public void asOWLAnnotationProperty() {
        plainLiteral.asOWLAnnotationProperty();
    }

    @Test
    public void isOWLNamedIndividual() {
        assertFalse(plainLiteral.isOWLNamedIndividual());
    }

    @Test(expected = RuntimeException.class)
    public void asOWLNamedIndividual() {
        plainLiteral.asOWLNamedIndividual();
    }

    @Test
    public void toStringID() {
        assertNotNull(plainLiteral.toStringID());
        assertEquals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI().toString(),
                plainLiteral.toStringID());
    }

    @Test
    public void getIRI() {
        assertNotNull(plainLiteral.getIRI());
        assertEquals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI(), plainLiteral.getIRI());
    }

    @Test
    public void equals() {
        assertEquals(plainLiteral, plainLiteral);
        assertEquals(
                plainLiteral,
                Factory.getFactory().getOWLDatatype(
                        OWL2Datatype.RDF_PLAIN_LITERAL.getIRI()));
        assertNotEquals(plainLiteral,
                Factory.getFactory().getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI()));
    }

    @Test
    public void getSignature() {
        assertNotNull(plainLiteral.getSignature());
        assertEquals(plainLiteral.getSignature(), Collections.singleton(plainLiteral));
    }

    @Test
    public void getAnonymousIndividuals() {
        assertEquals(Collections.emptySet(), plainLiteral.getAnonymousIndividuals());
    }

    @Test
    public void getClassesInSignature() {
        assertEquals(Collections.emptySet(), plainLiteral.getClassesInSignature());
    }

    @Test
    public void getObjectPropertiesInSignature() {
        assertEquals(Collections.emptySet(),
                plainLiteral.getObjectPropertiesInSignature());
    }

    @Test
    public void getDataPropertiesInSignature() {
        assertEquals(Collections.emptySet(), plainLiteral.getDataPropertiesInSignature());
    }

    @Test
    public void getIndividualsInSignature() {
        assertEquals(Collections.emptySet(), plainLiteral.getIndividualsInSignature());
    }

    @Test
    public void getNestedClassExpressions() {
        assertEquals(Collections.emptySet(), plainLiteral.getNestedClassExpressions());
    }

    @Test
    public void isTopEntity() {
        assertTrue(Factory.getFactory()
                .getOWLDatatype(OWL2Datatype.RDFS_LITERAL.getIRI()).isTopDatatype());
        assertFalse(Factory.getFactory()
                .getOWLDatatype(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI()).isTopDatatype());
    }

    @Test
    public void isBottomEntity() {
        assertFalse(plainLiteral.isBottomEntity());
    }

    @Test
    public void contains() {
        Set<OWLDatatype> datatypes = new HashSet<OWLDatatype>();
        OWLDatatype dt2Impl = Factory.getFactory().getOWLDatatype(
                OWL2Datatype.XSD_BYTE.getIRI());
        datatypes.add(dt2Impl);
        assertTrue(datatypes.contains(dt2Impl));
    }
}
