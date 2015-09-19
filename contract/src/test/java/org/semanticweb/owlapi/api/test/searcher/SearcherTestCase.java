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
package org.semanticweb.owlapi.api.test.searcher;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Collection;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.Filters;

@SuppressWarnings("javadoc")
public class SearcherTestCase extends TestBase {

    @Test
    public void shouldSearch() {
        // given
        OWLOntology o = getOWLOntology();
        OWLClass c = Class(IRI("urn:c"));
        OWLClass d = Class(IRI("urn:d"));
        OWLAxiom ax = SubClassOf(c, d);
        o.getOWLOntologyManager().addAxiom(o, ax);
        assertTrue(contains(o.axioms(AxiomType.SUBCLASS_OF), ax));
        assertTrue(contains(o.axioms(c), ax));
    }

    @Test
    public void shouldSearchObjectProperties() {
        // given
        OWLOntology o = getOWLOntology();
        OWLObjectProperty c = ObjectProperty(IRI("urn:c"));
        OWLObjectProperty d = ObjectProperty(IRI("urn:d"));
        OWLObjectProperty e = ObjectProperty(IRI("urn:e"));
        OWLClass x = Class(IRI("urn:x"));
        OWLClass y = Class(IRI("urn:Y"));
        OWLAxiom ax = SubObjectPropertyOf(c, d);
        OWLAxiom ax2 = ObjectPropertyDomain(c, x);
        OWLAxiom ax3 = ObjectPropertyRange(c, y);
        OWLAxiom ax4 = EquivalentObjectProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(contains(o.axioms(AxiomType.SUB_OBJECT_PROPERTY), ax));
        Collection<OWLAxiom> axioms = asSet(o.axioms(
            Filters.subObjectPropertyWithSuper, d, INCLUDED));
        assertTrue(contains(sub(axioms.stream()), c));
        axioms = asSet(o.axioms(Filters.subObjectPropertyWithSub, c, INCLUDED));
        assertTrue(contains(sup(axioms.stream()), d));
        assertTrue(contains(domain(o.objectPropertyDomainAxioms(c)), x));
        assertTrue(contains(equivalent(o.equivalentObjectPropertiesAxioms(c)),
            e));
    }

    @Test
    public void shouldSearchDataProperties() {
        // given
        OWLOntology o = getOWLOntology();
        OWLDataProperty c = DataProperty(IRI("urn:c"));
        OWLDataProperty d = DataProperty(IRI("urn:d"));
        OWLDataProperty e = DataProperty(IRI("urn:e"));
        OWLAxiom ax = SubDataPropertyOf(c, d);
        OWLClass x = Class(IRI("urn:x"));
        OWLAxiom ax2 = DataPropertyDomain(c, x);
        OWLAxiom ax3 = DataPropertyRange(c, Boolean());
        OWLAxiom ax4 = EquivalentDataProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(contains(o.axioms(AxiomType.SUB_DATA_PROPERTY), ax));
        assertTrue(contains(sub(o.axioms(Filters.subDataPropertyWithSuper, d,
            INCLUDED)), c));
        Collection<OWLAxiom> axioms = asSet(o.axioms(
            Filters.subDataPropertyWithSub, c, INCLUDED));
        assertTrue(contains(sup(axioms.stream()), d));
        assertTrue(contains(domain(o.dataPropertyDomainAxioms(c)), x));
        assertTrue(contains(range(o.dataPropertyRangeAxioms(c)), Boolean()));
        assertTrue(contains(equivalent(o.equivalentDataPropertiesAxioms(c)),
            e));
    }
}
