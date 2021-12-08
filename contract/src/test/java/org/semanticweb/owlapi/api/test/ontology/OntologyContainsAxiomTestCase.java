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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class OntologyContainsAxiomTestCase extends TestBase {

    private static RDFXMLDocumentFormat createRDFXMLFormat() {
        RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
        // This test case relies on certain declarations being in certain
        // ontologies. The default
        // behaviour is to add missing declarations. Therefore, this needs to be
        // turned off.
        format.setAddMissingTypes(false);
        return format;
    }

    private static TurtleDocumentFormat createTurtleOntologyFormat() {
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setAddMissingTypes(false);
        return format;
    }

    @Test
    void testOntologyContainsPlainAxiom() {
        OWLAxiom axiom = SubClassOf(A, B);
        OWLOntology ont = create("testont");
        ont.addAxiom(axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiom(axiom, EXCLUDED, IGNORE_AXIOM_ANNOTATIONS));
    }

    @Test
    void testOntologyContainsAnnotatedAxiom() {
        OWLAxiom axiom = SubClassOf(Annotation(AP, Literal("value")), A, B);
        OWLOntology ont = create("testont");
        ont.addAxiom(axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiom(axiom, EXCLUDED, IGNORE_AXIOM_ANNOTATIONS));
        assertFalse(ont.containsAxiom(axiom.getAxiomWithoutAnnotations()));
        assertTrue(ont.containsAxiom(axiom.getAxiomWithoutAnnotations(), EXCLUDED,
            IGNORE_AXIOM_ANNOTATIONS));
    }

    @Test
    void testOntologyContainsAxiomsForRDFXML1() {
        RDFXMLDocumentFormat format = createRDFXMLFormat();
        runTestOntologyContainsAxioms1(format);
    }

    @Test
    void testOntologyContainsAxiomsForOWLXML1() {
        runTestOntologyContainsAxioms1(new OWLXMLDocumentFormat());
    }

    @Test
    void testOntologyContainsAxiomsForOWLFunctionalSyntax1() {
        runTestOntologyContainsAxioms1(new FunctionalSyntaxDocumentFormat());
    }

    @Test
    void testOntologyContainsAxiomsForTurtleSyntax1() {
        TurtleDocumentFormat format = createTurtleOntologyFormat();
        runTestOntologyContainsAxioms1(format);
    }

    private void runTestOntologyContainsAxioms1(OWLDocumentFormat format) {
        OWLOntology ont1 = create(iri("urn:test:", "testont1A"));
        IRI ont1iri = ont1.getOntologyID().getOntologyIRI().orElse(null);
        OWLOntology ont2 = create(iri("urn:test:", "testont2A"));
        IRI ont2iri = ont2.getOntologyID().getOntologyIRI().orElse(null);
        OWLImportsDeclaration ont2import = ImportsDeclaration(ont1iri);
        ont1.applyChange(new AddImport(ont2, ont2import));
        OWLAxiom annoPropdecl = Declaration(AP);
        ont1.addAxiom(annoPropdecl);
        OWLAnnotation inont1anno = Annotation(AP, ont1iri);
        OWLAnnotation inont2anno = Annotation(AP, ont2iri);
        OWLAxiom adecl = Declaration(inont1anno, A);
        ont1.addAxiom(adecl);
        OWLAxiom bdecl = Declaration(inont2anno, B);
        ont2.addAxiom(bdecl);
        OWLAxiom asubB = SubClassOf(inont2anno, B, A);
        ont2.addAxiom(asubB);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1, annoPropdecl));
        assertFalse(containsConsiderEx(ont2, annoPropdecl));
        assertTrue(containsConsider(ont2, annoPropdecl));
        // A is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1, adecl));
        assertFalse(containsConsiderEx(ont2, adecl));
        assertTrue(containsConsider(ont2, adecl));
        // B is in only in ont2
        assertFalse(containsConsider(ont1, bdecl));
        assertTrue(containsConsiderEx(ont2, bdecl));
        assertTrue(containsConsider(ont2, bdecl));
        // A is a subclass of B is in only in ont2
        assertFalse(containsConsider(ont1, asubB));
        assertTrue(containsConsiderEx(ont2, asubB));
        assertTrue(containsConsider(ont2, asubB));
        File savedLocation1 = save(format, ont1, "testont1A.owl");
        File savedLocation2 = save(format, ont2, "testont2A.owl");
        OWLOntology ont1L = loadFrom(savedLocation1, m1);
        OWLOntology ont2L = loadFrom(savedLocation2, m1);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1L, annoPropdecl));
        assertFalse(containsConsiderEx(ont2L, annoPropdecl));
        assertTrue(containsConsider(ont2L, annoPropdecl));
        // A is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1L, adecl));
        assertFalse(containsConsiderEx(ont2L, adecl));
        assertTrue(containsConsider(ont2L, adecl));
        // B is in only in ont2
        assertFalse(containsConsider(ont1L, bdecl));
        assertTrue(containsConsiderEx(ont2L, bdecl));
        assertTrue(containsConsider(ont2L, bdecl));
        // A is a subclass of B is in only in ont2
        assertFalse(containsConsider(ont1L, asubB));
        assertTrue(containsConsiderEx(ont2L, asubB));
        assertTrue(containsConsider(ont2L, asubB));
    }

    protected File save(OWLDocumentFormat format, OWLOntology ont1, String name) {
        File savedLocation1 = new File(folder, name);
        try (FileOutputStream out1 = new FileOutputStream(savedLocation1)) {
            StreamDocumentTarget writer1 = new StreamDocumentTarget(out1);
            ont1.getOWLOntologyManager().saveOntology(ont1, format, writer1);
            return savedLocation1;
        } catch (Exception ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    boolean containsConsider(OWLOntology o, OWLAxiom ax) {
        return o.containsAxiom(ax, INCLUDED, CONSIDER_AXIOM_ANNOTATIONS);
    }

    boolean containsConsiderEx(OWLOntology o, OWLAxiom ax) {
        return o.containsAxiom(ax, EXCLUDED, CONSIDER_AXIOM_ANNOTATIONS);
    }

    @Test
    void testOntologyContainsAxiomsForRDFXML2() {
        runTestOntologyContainsAxioms2(createRDFXMLFormat());
    }

    @Test
    void testOntologyContainsAxiomsForOWLXML2() {
        runTestOntologyContainsAxioms2(new OWLXMLDocumentFormat());
    }

    @Test
    void testOntologyContainsAxiomsForOWLFunctionalSyntax2() {
        runTestOntologyContainsAxioms2(new FunctionalSyntaxDocumentFormat());
    }

    @Test
    void testOntologyContainsAxiomsForTurtleSyntax2() {
        runTestOntologyContainsAxioms2(createTurtleOntologyFormat());
    }

    private void runTestOntologyContainsAxioms2(OWLDocumentFormat format) {
        OWLOntology ont1 = create(iri("urn:test:", "testont1B"));
        IRI ont1iri = ont1.getOntologyID().getOntologyIRI().orElse(null);
        OWLOntology ont2 = create(iri("urn:test:", "testont2B"));
        IRI ont2iri = ont2.getOntologyID().getOntologyIRI().orElse(null);
        OWLImportsDeclaration ont2import = ImportsDeclaration(ont1iri);
        ont2.applyChange(new AddImport(ont2, ont2import));
        OWLAxiom annoPropdecl = Declaration(AP);
        ont1.addAxiom(annoPropdecl);
        OWLAnnotation inont1anno = Annotation(AP, ont1iri);
        OWLAnnotation inont2anno = Annotation(AP, ont2iri);
        OWLAxiom adecl = Declaration(inont1anno, A);
        ont1.addAxiom(adecl);
        OWLAxiom bdecl = Declaration(inont2anno, B);
        ont2.addAxiom(bdecl);
        OWLAxiom asubB = SubClassOf(inont2anno, A, B);
        ont2.addAxiom(asubB);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1, annoPropdecl));
        assertFalse(containsConsiderEx(ont2, annoPropdecl));
        assertTrue(containsConsider(ont2, annoPropdecl));
        // A is in ont1 and in the import closure of ont2
        assertTrue(containsConsiderEx(ont1, adecl));
        assertFalse(containsConsiderEx(ont2, adecl));
        assertTrue(containsConsider(ont2, adecl));
        // B is in only in ont2
        assertFalse(containsConsider(ont1, bdecl));
        assertTrue(containsConsiderEx(ont2, bdecl));
        assertTrue(containsConsider(ont2, bdecl));
        // A is a subclass of B is in only in ont2
        assertFalse(containsConsider(ont1, asubB));
        assertTrue(containsConsiderEx(ont2, asubB));
        assertTrue(containsConsider(ont2, asubB));
        File savedLocation1 = save(format, ont1, "testont1B.owl");
        File savedLocation2 = save(format, ont2, "testont2B.owl");
        loadFrom(savedLocation1, m1);
        OWLOntology ont2L = loadFrom(savedLocation2, m1);
        ont2L.imports().forEach(o -> o.axioms().forEach(ax -> {
            assertTrue(containsConsiderEx(o, ax));
            assertFalse(containsConsiderEx(ont2L, ax));
        }));
    }
}
