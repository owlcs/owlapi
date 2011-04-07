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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 * <p/>
 * The base for test cases that need a data factory.
 */
public abstract class AbstractOWLDataFactoryTest extends AbstractOWLAPITestCase {

    @Override
	protected void setUp() throws Exception {
        super.setUp();
    }

    public static IRI createIRI() {
        return TestUtils.createIRI();
    }

    public abstract void testCreation() throws Exception;

    public abstract void testEqualsPositive() throws Exception;

    public abstract void testEqualsNegative() throws Exception;

    public abstract void testHashCode() throws Exception;

    public static void assertNotEquals(Object objA, Object objB) {
        assertFalse(objA.equals(objB));
    }

    protected OWLObjectProperty createOWLObjectProperty() throws Exception {
        return getFactory().getOWLObjectProperty(createIRI());
    }

    protected OWLClass createOWLClass() throws Exception {
        return getFactory().getOWLClass(createIRI());
    }

    protected OWLIndividual createOWLIndividual() throws Exception {
        return getFactory().getOWLNamedIndividual(createIRI());
    }

    protected OWLDataProperty createOWLDataProperty() throws OWLException {
        return getFactory().getOWLDataProperty(createIRI());
    }

    protected OWLDatatype createOWLDatatype() throws OWLException {
        return getFactory().getOWLDatatype(createIRI());
    }

    protected OWLLiteral createOWLLiteral() throws OWLException {
        return getFactory().getOWLLiteral("Test" + System.currentTimeMillis(), createOWLDatatype());
    }


}
