package org.semanticweb.owlapi.model;

import junit.framework.TestCase;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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


    protected void setUp() throws Exception {
        super.setUp();
        dataFactory = OWLDataFactoryImpl.getInstance();
    }


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
