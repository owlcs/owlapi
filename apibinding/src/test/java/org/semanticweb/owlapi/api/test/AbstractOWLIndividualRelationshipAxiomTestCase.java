/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLIndividualRelationshipAxiomTestCase<P extends OWLPropertyExpression<?,?>, O extends OWLObject> extends AbstractOWLDataFactoryTest {

    protected abstract P createProperty() throws Exception;

    protected abstract O createObject() throws Exception;

    protected abstract OWLIndividualAxiom createAxiom(OWLIndividual subject, P property, O object) throws OWLException;

    @Override
	public void testCreation() throws Exception {
        assertNotNull(createAxiom(createOWLIndividual(), createProperty(), createObject()));
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA, axiomB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        // Different subject
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(createOWLIndividual(), property, object);
        OWLIndividualAxiom axiomB = createAxiom(createOWLIndividual(), property, object);
        assertNotEquals(axiomA, axiomB);
        // Different property
        OWLIndividual subject = createOWLIndividual();
        OWLIndividualAxiom axiomC = createAxiom(subject, createProperty(), object);
        OWLIndividualAxiom axiomD = createAxiom(subject, createProperty(), object);
        assertNotEquals(axiomC, axiomD);
        // Different object
        OWLIndividualAxiom axiomE = createAxiom(subject, property, createObject());
        OWLIndividualAxiom axiomF = createAxiom(subject, property, createObject());
        assertNotEquals(axiomE, axiomF);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA.hashCode(), axiomB.hashCode());
    }
}
