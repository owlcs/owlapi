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
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.EntitySearcher.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class PropertyCharacteristicsAccessorsTestCase extends TestBase {

    @Test
    public void testTransitive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isTransitive(prop, ont));
        OWLAxiom ax = TransitiveObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isTransitive(prop, ont));
    }

    @Test
    public void testSymmetric() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isSymmetric(prop, ont));
        OWLAxiom ax = SymmetricObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isSymmetric(prop, ont));
    }

    @Test
    public void testAsymmetric() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isAsymmetric(prop, ont));
        OWLAxiom ax = AsymmetricObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isAsymmetric(prop, ont));
    }

    @Test
    public void testReflexive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isReflexive(prop, ont));
        OWLAxiom ax = ReflexiveObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isReflexive(prop, ont));
    }

    @Test
    public void testIrreflexive() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isIrreflexive(prop, ont));
        OWLAxiom ax = IrreflexiveObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isIrreflexive(prop, ont));
    }

    @Test
    public void testFunctional() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isFunctional(prop, ont));
        OWLAxiom ax = FunctionalObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isFunctional(prop, ont));
    }

    @Test
    public void testInverseFunctional() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        assertFalse(isInverseFunctional(prop, ont));
        OWLAxiom ax = InverseFunctionalObjectProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isInverseFunctional(prop, ont));
    }

    @Test
    public void testFunctionalDataProperty() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLDataProperty prop = DataProperty(iri("prop"));
        assertFalse(isFunctional(prop, ont));
        OWLAxiom ax = FunctionalDataProperty(prop);
        m.addAxiom(ont, ax);
        assertTrue(isFunctional(prop, ont));
    }
}
