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

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Apr-2007<br><br>
 * <p/>
 * Tests that the isOWLThing and isOWLNothing methods
 * return correct values.
 */
public class BuiltInClassTestCase extends TestCase {

    private OWLDataFactory dataFactory;


    @Override
	protected void setUp() throws Exception {
        super.setUp();
        dataFactory = OWLDataFactoryImpl.getInstance();
    }


    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
        dataFactory = null;
    }


    public void testOWLThing() {
        OWLClass thing = dataFactory.getOWLThing();
        assertTrue(thing.isOWLThing());
        assertFalse(thing.isOWLNothing());
    }

    public void testOWLThingFromURI() {
        OWLClassExpression desc = dataFactory.getOWLClass(OWLRDFVocabulary.OWL_THING.getIRI());
        assertTrue(desc.isOWLThing());
        assertFalse(desc.isOWLNothing());
    }

    public void testOWLNothing() {
        OWLClass nothing = dataFactory.getOWLNothing();
        assertTrue(nothing.isOWLNothing());
        assertFalse(nothing.isOWLThing());
    }

    public void testOWLNothingFromURI() {
        OWLClassExpression desc = dataFactory.getOWLClass(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        assertTrue(desc.isOWLNothing());
        assertFalse(desc.isOWLThing());
    }

    public void testAnonymousClass() {
        OWLClassExpression desc = dataFactory.getOWLObjectHasSelf(dataFactory.getOWLObjectProperty(TestUtils.createIRI()));
        assertFalse(desc.isOWLThing());
        assertFalse(desc.isOWLNothing());
    }


}
