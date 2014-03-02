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
package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

@SuppressWarnings("javadoc")
public class SWRLAnnotationTestCase extends TestBase {

    private static final String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    OWLClass a;
    OWLClass b;
    OWLAxiom axiom;

    @Before
    public void setUp() {
        OWLDataFactory factory = df;
        a = Class(IRI(NS + "#A"));
        b = Class(IRI(NS + "#B"));
        SWRLVariable x = factory.getSWRLVariable(IRI(NS + "#x"));
        SWRLAtom atom1 = factory.getSWRLClassAtom(a, x);
        SWRLAtom atom2 = factory.getSWRLClassAtom(b, x);
        Set<SWRLAtom> consequent = new TreeSet<SWRLAtom>();
        consequent.add(atom1);
        OWLAnnotation annotation = factory.getOWLAnnotation(RDFSComment(),
                Literal("Not a great rule"));
        Set<OWLAnnotation> annotations = new TreeSet<OWLAnnotation>();
        annotations.add(annotation);
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(atom2);
        axiom = factory.getSWRLRule(body, consequent, annotations);
    }

    @Test
    public void shouldRoundTripAnnotation()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAxiom(axiom));
        ontology = loadOntologyFromString(saveOntology(ontology));
        assertTrue(ontology.containsAxiom(axiom));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntology ontology = m.createOntology(IRI(NS));
        List<AddAxiom> changes = new ArrayList<AddAxiom>();
        changes.add(new AddAxiom(ontology, axiom));
        m.applyChanges(changes);
        return ontology;
    }
}
