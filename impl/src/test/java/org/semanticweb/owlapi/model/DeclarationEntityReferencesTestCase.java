package org.semanticweb.owlapi.model;
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
 * Date: 01-May-2007<br><br>
 * <p/>
 * A test case which ensures that an ontology contains
 * entity references when that ontology only contains
 * entity declaration axioms.  In other words, entity
 * declaration axioms produce the correct entity references.
 */
public class DeclarationEntityReferencesTestCase extends AbstractOWLTestCase {

    public void testOWLClassDeclarationAxiom() throws Exception {
        OWLClass cls = getOWLDataFactory().getOWLClass(TestUtils.createIRI());
        OWLAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(cls);
        OWLOntologyManager man = getOWLOntologyManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getClassesInSignature().contains(cls));
    }

    public void testOWLObjectPropertyDeclarationAxiom() throws Exception {
        OWLObjectProperty prop = getOWLDataFactory().getOWLObjectProperty(TestUtils.createIRI());
        OWLAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(prop);
        OWLOntologyManager man = getOWLOntologyManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getObjectPropertiesInSignature().contains(prop));
    }

    public void testOWLDataPropertyDeclarationAxiom() throws Exception {
        OWLDataProperty prop = getOWLDataFactory().getOWLDataProperty(TestUtils.createIRI());
        OWLAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(prop);
        OWLOntologyManager man = getOWLOntologyManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getDataPropertiesInSignature().contains(prop));
    }

    public void testOWLIndividualDeclarationAxiom() throws Exception {
        OWLNamedIndividual ind = getOWLDataFactory().getOWLNamedIndividual(TestUtils.createIRI());
        OWLAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(ind);
        OWLOntologyManager man = getOWLOntologyManager();
        OWLOntology ont = man.createOntology(TestUtils.createIRI());
        man.applyChange(new AddAxiom(ont, ax));
        assertTrue(ont.getIndividualsInSignature().contains(ind));
    }
}
