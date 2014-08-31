/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.2.0
 */
@SuppressWarnings("javadoc")
public class OWL2DatatypeImplTestCase extends TestBase {

    private OWLDatatype plainLiteral;

    @Before
    public void setUp() {
        plainLiteral = OWL2Datatype.RDF_PLAIN_LITERAL.getDatatype(df);
    }

    @Test
    public void getBuiltInDatatype() {
        assertEquals(OWL2Datatype.RDF_PLAIN_LITERAL,
                plainLiteral.getBuiltInDatatype());
    }

    @Test
    public void isString() {
        assertFalse(plainLiteral.isString());
        OWLDatatype string = OWL2Datatype.XSD_STRING.getDatatype(df);
        assertTrue(string.isString());
    }

    @Test
    public void isInteger() {
        assertFalse(plainLiteral.isInteger());
        OWLDatatype integer = OWL2Datatype.XSD_INTEGER.getDatatype(df);
        assertTrue(integer.isInteger());
    }

    @Test
    public void isFloat() {
        assertFalse(plainLiteral.isFloat());
        OWLDatatype floatDatatype = OWL2Datatype.XSD_FLOAT.getDatatype(df);
        assertTrue(floatDatatype.isFloat());
    }

    @Test
    public void isDouble() {
        assertFalse(plainLiteral.isDouble());
        OWLDatatype doubleDatatype = OWL2Datatype.XSD_DOUBLE.getDatatype(df);
        assertTrue(doubleDatatype.isDouble());
    }

    @Test
    public void isBoolean() {
        assertFalse(plainLiteral.isBoolean());
        OWLDatatype booleanDatatype = OWL2Datatype.XSD_BOOLEAN.getDatatype(df);
        assertTrue(booleanDatatype.isBoolean());
    }

    @Test
    public void isRDFPlainLiteral() {
        assertTrue(plainLiteral.isRDFPlainLiteral());
        OWLDatatype stringDatatype = OWL2Datatype.XSD_STRING.getDatatype(df);
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
        OWLDatatype rdfsLiteralDatatype = OWL2Datatype.RDFS_LITERAL
                .getDatatype(df);
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
        assertEquals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI(),
                plainLiteral.getIRI());
    }

    @Test
    public void shouldEquals() {
        assertEquals(plainLiteral, plainLiteral);
        assertEquals(plainLiteral,
                OWL2Datatype.RDF_PLAIN_LITERAL.getDatatype(df));
        assertNotSame(plainLiteral, OWL2Datatype.XSD_STRING.getDatatype(df));
    }

    @Test
    public void getSignature() {
        assertNotNull(plainLiteral.getSignature());
        assertEquals(plainLiteral.getSignature(), singleton(plainLiteral));
    }

    @Test
    public void getAnonymousIndividuals() {
        assertTrue(plainLiteral.getAnonymousIndividuals().isEmpty());
    }

    @Test
    public void getClassesInSignature() {
        assertTrue(plainLiteral.getClassesInSignature().isEmpty());
    }

    @Test
    public void getObjectPropertiesInSignature() {
        assertTrue(plainLiteral.getObjectPropertiesInSignature().isEmpty());
    }

    @Test
    public void getDataPropertiesInSignature() {
        assertTrue(plainLiteral.getDataPropertiesInSignature().isEmpty());
    }

    @Test
    public void getIndividualsInSignature() {
        assertTrue(plainLiteral.getIndividualsInSignature().isEmpty());
    }

    @Test
    public void getNestedClassExpressions() {
        assertTrue(plainLiteral.getNestedClassExpressions().isEmpty());
    }

    @Test
    public void isTopEntity() {
        assertTrue(OWL2Datatype.RDFS_LITERAL.getDatatype(df).isTopDatatype());
        assertFalse(OWL2Datatype.RDF_PLAIN_LITERAL.getDatatype(df)
                .isTopDatatype());
    }

    @Test
    public void isBottomEntity() {
        assertFalse(plainLiteral.isBottomEntity());
    }

    @Test
    public void contains() {
        IRI iri = OWL2Datatype.XSD_BYTE.getIRI();
        Set<OWLDatatype> datatypes = new HashSet<>();
        OWLDatatypeImpl dtImpl = new OWLDatatypeImpl(iri);
        OWLDatatype dt2Impl = OWL2Datatype.XSD_BYTE.getDatatype(df);
        assertEquals(dtImpl, dt2Impl);
        datatypes.add(dt2Impl);
        assertTrue(datatypes.contains(dtImpl));
        assertEquals(dt2Impl.hashCode(), dtImpl.hashCode());
    }
}
