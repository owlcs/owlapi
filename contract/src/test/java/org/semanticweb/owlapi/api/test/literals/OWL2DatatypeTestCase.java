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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BYTE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
class OWL2DatatypeTestCase extends TestBase {

    private OWLDatatype plainLiteral;

    @BeforeEach
    void setUpLiteral() {
        plainLiteral = RDF_PLAIN_LITERAL.getDatatype(df);
    }

    @Test
    void getBuiltInDatatype() {
        assertEquals(RDF_PLAIN_LITERAL, plainLiteral.getBuiltInDatatype());
    }

    @Test
    void isString() {
        assertFalse(plainLiteral.isString());
        assertTrue(XSD_STRING.getDatatype(df).isString());
    }

    @Test
    void isInteger() {
        assertFalse(plainLiteral.isInteger());
        assertTrue(XSD_INTEGER.getDatatype(df).isInteger());
    }

    @Test
    void isFloat() {
        assertFalse(plainLiteral.isFloat());
        assertTrue(XSD_FLOAT.getDatatype(df).isFloat());
    }

    @Test
    void isDouble() {
        assertFalse(plainLiteral.isDouble());
        assertTrue(XSD_DOUBLE.getDatatype(df).isDouble());
    }

    @Test
    void isBoolean() {
        assertFalse(plainLiteral.isBoolean());
        assertTrue(XSD_BOOLEAN.getDatatype(df).isBoolean());
    }

    @Test
    void isRDFPlainLiteral() {
        assertTrue(plainLiteral.isRDFPlainLiteral());
        assertFalse(XSD_STRING.getDatatype(df).isRDFPlainLiteral());
    }

    @Test
    void isDatatype() {
        assertTrue(plainLiteral.isOWLDatatype());
    }

    @Test
    void asOWLDatatype() {
        assertEquals(plainLiteral, plainLiteral.asOWLDatatype());
    }

    @Test
    void isTopDatatype() {
        assertTrue(RDFS_LITERAL.getDatatype(df).isTopDatatype());
        assertFalse(plainLiteral.isTopDatatype());
    }

    @Test
    void getDataRangeType() {
        assertEquals(DataRangeType.DATATYPE, plainLiteral.getDataRangeType());
    }

    @Test
    void getEntityType() {
        assertEquals(EntityType.DATATYPE, plainLiteral.getEntityType());
    }

    @Test
    void isType() {
        assertTrue(plainLiteral.isType(EntityType.DATATYPE));
        assertFalse(plainLiteral.isType(EntityType.CLASS));
        assertFalse(plainLiteral.isType(EntityType.OBJECT_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.DATA_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.ANNOTATION_PROPERTY));
        assertFalse(plainLiteral.isType(EntityType.NAMED_INDIVIDUAL));
    }

    @Test
    void isBuiltIn() {
        assertTrue(plainLiteral.isBuiltIn());
    }

    @Test
    void isOWLClass() {
        assertFalse(plainLiteral.isOWLClass());
    }

    @Test
    void asOWLClass() {
        assertThrows(RuntimeException.class, plainLiteral::asOWLClass);
    }

    @Test
    void isOWLObjectProperty() {
        assertFalse(plainLiteral.isOWLObjectProperty());
    }

    @Test
    void asOWLObjectProperty() {
        assertThrows(RuntimeException.class, plainLiteral::asOWLObjectProperty);
    }

    @Test
    void isOWLDataProperty() {
        assertFalse(plainLiteral.isOWLDataProperty());
    }

    @Test
    void asOWLDataProperty() {
        assertThrows(RuntimeException.class, plainLiteral::asOWLDataProperty);
    }

    @Test
    void isOWLAnnotationProperty() {
        assertFalse(plainLiteral.isOWLAnnotationProperty());
    }

    @Test
    void asOWLAnnotationProperty() {
        assertThrows(RuntimeException.class, plainLiteral::asOWLAnnotationProperty);
    }

    @Test
    void isOWLNamedIndividual() {
        assertFalse(plainLiteral.isOWLNamedIndividual());
    }

    @Test
    void asOWLNamedIndividual() {
        assertThrows(RuntimeException.class, plainLiteral::asOWLNamedIndividual);
    }

    @Test
    void toStringID() {
        assertNotNull(plainLiteral.toStringID());
        assertEquals(RDF_PLAIN_LITERAL.getIRI().toString(), plainLiteral.toStringID());
    }

    @Test
    void getIRI() {
        assertNotNull(plainLiteral.getIRI());
        assertEquals(RDF_PLAIN_LITERAL.getIRI(), plainLiteral.getIRI());
    }

    @Test
    void shouldEquals() {
        assertEquals(plainLiteral, plainLiteral);
        assertEquals(plainLiteral, RDF_PLAIN_LITERAL.getDatatype(df));
        assertNotSame(plainLiteral, XSD_STRING.getDatatype(df));
    }

    @Test
    void getSignature() {
        assertEquals(asUnorderedSet(plainLiteral.signature()), set(plainLiteral));
    }

    @Test
    void getAnonymousIndividuals() {
        assertEquals(0L, plainLiteral.anonymousIndividuals().count());
    }

    @Test
    void getClassesInSignature() {
        assertEquals(0L, plainLiteral.classesInSignature().count());
    }

    @Test
    void getObjectPropertiesInSignature() {
        assertEquals(0L, plainLiteral.objectPropertiesInSignature().count());
    }

    @Test
    void getDataPropertiesInSignature() {
        assertEquals(0L, plainLiteral.dataPropertiesInSignature().count());
    }

    @Test
    void getIndividualsInSignature() {
        assertEquals(0L, plainLiteral.individualsInSignature().count());
    }

    @Test
    void getNestedClassExpressions() {
        assertEquals(0L, plainLiteral.nestedClassExpressions().count());
    }

    @Test
    void isTopEntity() {
        assertTrue(RDFS_LITERAL.getDatatype(df).isTopDatatype());
        assertFalse(RDF_PLAIN_LITERAL.getDatatype(df).isTopDatatype());
    }

    @Test
    void isBottomEntity() {
        assertFalse(plainLiteral.isBottomEntity());
    }

    @Test
    void contains() {
        IRI iri = XSD_BYTE.getIRI();
        Set<OWLDatatype> datatypes = new HashSet<>();
        OWLDatatype dtImpl = Datatype(iri);
        OWLDatatype dt2Impl = XSD_BYTE.getDatatype(df);
        assertEquals(dtImpl, dt2Impl);
        datatypes.add(dt2Impl);
        assertTrue(datatypes.contains(dtImpl));
        assertEquals(dt2Impl.hashCode(), dtImpl.hashCode());
    }
}
