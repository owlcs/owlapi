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
package org.semanticweb.owlapi.api.test.anonymous;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

@SuppressWarnings("javadoc")
public class AnonymousTestCase extends TestBase {

    @Test
    public void shouldRoundTrip() throws Exception {
        OWLClass c = Class(IRI("urn:test#C"));
        OWLClass d = Class(IRI("urn:test#D"));
        OWLObjectProperty p = ObjectProperty(IRI("urn:test#p"));
        OWLDataProperty q = DataProperty(IRI("urn:test#q"));
        OWLIndividual i = AnonymousIndividual();
        OWLOntology ontology = m.createOntology();
        List<OWLOntologyChange> changes = new ArrayList<>();
        changes.add(new AddAxiom(ontology, SubClassOf(c, ObjectHasValue(p, i))));
        changes.add(new AddAxiom(ontology, ClassAssertion(d, i)));
        changes.add(new AddAxiom(ontology, DataPropertyAssertion(q, i,
                Literal("hello"))));
        m.applyChanges(changes);
        OWLOntology ontologyReloaded = loadOntologyFromString(saveOntology(ontology));
        equal(ontology, ontologyReloaded);
    }

    @Test
    public void testRoundTripWithAnonymousIndividuals() throws Exception {
        String ns = "http://test.com/genid#";
        IRI ont = IRI.create(ns + "ontology.owl");
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI.create(ns, "i"));
        OWLObjectProperty p = df.getOWLObjectProperty(IRI.create(ns, "p"));
        OWLDataProperty q = df.getOWLDataProperty(IRI.create(ns, "q"));
        OWLOntology ontology = m.createOntology(ont);
        OWLIndividual ind = df.getOWLAnonymousIndividual();
        OWLObjectPropertyAssertionAxiom ax1 = df
                .getOWLObjectPropertyAssertionAxiom(p, i, ind);
        OWLDataPropertyAssertionAxiom ax2 = df
                .getOWLDataPropertyAssertionAxiom(q, ind, df.getOWLLiteral(5));
        m.addAxiom(ontology, ax1);
        m.addAxiom(ontology, ax2);
        OWLOntology reload = roundTrip(ontology);
        equal(ontology, reload);
    }
}
