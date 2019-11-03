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
package org.semanticweb.owlapi6.apitest.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;

public class SWRLAnnotationTestCase extends TestBase {

    private static final String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    protected OWLClass a = Class(IRI(NS + "#", "A"));
    protected OWLClass b = Class(IRI(NS + "#", "B"));
    protected OWLAxiom axiom;

    @Before
    public void setUpAtoms() {
        SWRLVariable x = df.getSWRLVariable(NS + "#", "x");
        SWRLAtom atom1 = df.getSWRLClassAtom(a, x);
        SWRLAtom atom2 = df.getSWRLClassAtom(b, x);
        Set<SWRLAtom> consequent = new TreeSet<>();
        consequent.add(atom1);
        OWLAnnotation annotation = df.getRDFSComment("Not a great rule");
        Set<OWLAnnotation> annotations = new TreeSet<>();
        annotations.add(annotation);
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(atom2);
        axiom = df.getSWRLRule(body, consequent, annotations);
    }

    @Test
    public void shouldRoundTripAnnotation() throws Exception {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAxiom(axiom));
        StringDocumentTarget saveOntology = saveOntology(ontology);
        ontology = loadOntologyFromString(saveOntology, ontology.getNonnullFormat());
        assertTrue(ontology.containsAxiom(axiom));
    }

    public OWLOntology createOntology() {
        OWLOntology ontology = getOWLOntology();
        ontology.add(axiom);
        return ontology;
    }

    @Test
    public void replicateFailure() throws Exception {
        String input = TestFiles.HEAD + " rdf:ID=\"test-table5-prp-inv2-rule\"" + TestFiles.TAIL;
        OWLOntology ontology = loadOntologyFromString(
            new StringDocumentSource(input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE)
            .anyMatch(ax -> ax.toString().contains(TestFiles.DL_RULE)));
    }

    @Test
    public void replicateSuccess() throws Exception {
        String input = TestFiles.HEAD + TestFiles.TAIL;
        OWLOntology ontology = loadOntologyFromString(
            new StringDocumentSource(input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE)
            .anyMatch(ax -> ax.toString().contains(TestFiles.DL_RULE)));
    }
}
