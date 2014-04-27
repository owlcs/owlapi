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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.model.parameters.Search.*;

import java.io.File;
import java.io.FileOutputStream;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class OntologyContainsAxiomTestCase extends TestBase {

    @Test
    public void testOntologyContainsPlainAxiom() {
        OWLAxiom axiom = SubClassOf(Class(getIRI("A")), Class(getIRI("B")));
        OWLOntology ont = getOWLOntology("testont");
        ont.getOWLOntologyManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiom(axiom, EXCLUDED, IGNORE_ANNOTATIONS));
    }

    @Test
    public void testOntologyContainsAnnotatedAxiom() {
        OWLLiteral annoLiteral = Literal("value");
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        OWLAnnotation anno = Annotation(annoProp, annoLiteral);
        OWLAxiom axiom = SubClassOf(Class(getIRI("A")), Class(getIRI("B")),
                singleton(anno));
        OWLOntology ont = getOWLOntology("testont");
        ont.getOWLOntologyManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiom(axiom, EXCLUDED, IGNORE_ANNOTATIONS));
        assertFalse(ont.containsAxiom(axiom.getAxiomWithoutAnnotations()));
        assertTrue(ont.containsAxiom(axiom.getAxiomWithoutAnnotations(),
                EXCLUDED, IGNORE_ANNOTATIONS));
    }

    @Test
    public void testOntologyContainsAxiomsForRDFXML1() throws Exception {
        RDFXMLOntologyFormat format = createRDFXMLFormat();
        runTestOntologyContainsAxioms1(format);
    }

    @Nonnull
    private static RDFXMLOntologyFormat createRDFXMLFormat() {
        RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
        // This test case relies on certain declarations being in certain
        // ontologies. The default
        // behaviour is to add missing declarations. Therefore, this needs to be
        // turned off.
        format.setAddMissingTypes(false);
        return format;
    }

    @Test
    public void testOntologyContainsAxiomsForOWLXML1() throws Exception {
        runTestOntologyContainsAxioms1(new OWLXMLOntologyFormat());
    }

    @Test
    public void testOntologyContainsAxiomsForOWLFunctionalSyntax1()
            throws Exception {
        runTestOntologyContainsAxioms1(new OWLFunctionalSyntaxOntologyFormat());
    }

    @Test
    public void testOntologyContainsAxiomsForTurtleSyntax1() throws Exception {
        TurtleOntologyFormat format = createTurtleOntologyFormat();
        runTestOntologyContainsAxioms1(format);
    }

    @Nonnull
    private static TurtleOntologyFormat createTurtleOntologyFormat() {
        TurtleOntologyFormat format = new TurtleOntologyFormat();
        format.setAddMissingTypes(false);
        return format;
    }

    private void runTestOntologyContainsAxioms1(
            @Nonnull OWLOntologyFormat format) throws Exception {
        OWLOntology ont1 = getOWLOntology("testont1A");
        IRI ont1_iri = ont1.getOntologyID().getOntologyIRI().get();
        OWLOntology ont2 = getOWLOntology("testont2A");
        IRI ont2_iri = ont2.getOntologyID().getOntologyIRI().get();
        OWLImportsDeclaration ont2_import = ImportsDeclaration(ont1_iri);
        ont1.getOWLOntologyManager().applyChange(
                new AddImport(ont2, ont2_import));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        OWLAxiom ax_annoProp_decl = Declaration(annoProp);
        ont1.getOWLOntologyManager().addAxiom(ont1, ax_annoProp_decl);
        OWLAnnotation in_ont1_anno = Annotation(annoProp, ont1_iri);
        OWLAnnotation in_ont2_anno = Annotation(annoProp, ont2_iri);
        OWLClass A = Class(getIRI("A"));
        OWLAxiom ax_A_decl = Declaration(A, singleton(in_ont1_anno));
        ont1.getOWLOntologyManager().addAxiom(ont1, ax_A_decl);
        OWLClass B = Class(getIRI("B"));
        OWLAxiom ax_B_decl = Declaration(B, singleton(in_ont2_anno));
        ont2.getOWLOntologyManager().addAxiom(ont2, ax_B_decl);
        OWLAxiom ax_AsubB = SubClassOf(Class(getIRI("A")), Class(getIRI("B")),
                singleton(in_ont2_anno));
        ont2.getOWLOntologyManager().addAxiom(ont2, ax_AsubB);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(ont1.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertFalse(ont2.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_annoProp_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        // A is in ont1 and in the import closure of ont2
        assertTrue(ont1
                .containsAxiom(ax_A_decl, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertFalse(ont2.containsAxiom(ax_A_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_A_decl, INCLUDED, CONSIDER_ANNOTATIONS));
        // B is in only in ont2
        assertFalse(ont1.containsAxiom(ax_B_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_B_decl, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_B_decl, INCLUDED, CONSIDER_ANNOTATIONS));
        // A is a subclass of B is in only in ont2
        assertFalse(ont1
                .containsAxiom(ax_AsubB, INCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_AsubB, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_AsubB, INCLUDED, CONSIDER_ANNOTATIONS));
        File savedLocation1 = folder.newFile("testont1A.owl");
        FileOutputStream out1 = new FileOutputStream(savedLocation1);
        StreamDocumentTarget writer1 = new StreamDocumentTarget(out1);
        ont1.getOWLOntologyManager().saveOntology(ont1, format, writer1);
        File savedLocation2 = folder.newFile("testont2A.owl");
        FileOutputStream out2 = new FileOutputStream(savedLocation2);
        StreamDocumentTarget writer2 = new StreamDocumentTarget(out2);
        ont2.getOWLOntologyManager().saveOntology(ont2, format, writer2);
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont1L = man
                .loadOntologyFromOntologyDocument(savedLocation1);
        OWLOntology ont2L = man
                .loadOntologyFromOntologyDocument(savedLocation2);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(ont1L.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertFalse(ont2L.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2L.containsAxiom(ax_annoProp_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        // A is in ont1 and in the import closure of ont2
        assertTrue(ont1L.containsAxiom(ax_A_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertFalse(ont2L.containsAxiom(ax_A_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2L.containsAxiom(ax_A_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        // B is in only in ont2
        assertFalse(ont1L.containsAxiom(ax_B_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2L.containsAxiom(ax_B_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2L.containsAxiom(ax_B_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        // A is a subclass of B is in only in ont2
        assertFalse(ont1L.containsAxiom(ax_AsubB, INCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2L
                .containsAxiom(ax_AsubB, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2L
                .containsAxiom(ax_AsubB, INCLUDED, CONSIDER_ANNOTATIONS));
    }

    @Test
    public void testOntologyContainsAxiomsForRDFXML2() throws Exception {
        runTestOntologyContainsAxioms2(createRDFXMLFormat());
    }

    @Test
    public void testOntologyContainsAxiomsForOWLXML2() throws Exception {
        runTestOntologyContainsAxioms2(new OWLXMLOntologyFormat());
    }

    @Test
    public void testOntologyContainsAxiomsForOWLFunctionalSyntax2()
            throws Exception {
        runTestOntologyContainsAxioms2(new OWLFunctionalSyntaxOntologyFormat());
    }

    @Test
    public void testOntologyContainsAxiomsForTurtleSyntax2() throws Exception {
        runTestOntologyContainsAxioms2(createTurtleOntologyFormat());
    }

    private void runTestOntologyContainsAxioms2(
            @Nonnull OWLOntologyFormat format) throws Exception {
        OWLOntology ont1 = getOWLOntology("testont1B");
        IRI ont1_iri = ont1.getOntologyID().getOntologyIRI().get();
        OWLOntology ont2 = getOWLOntology("testont2B");
        IRI ont2_iri = ont2.getOntologyID().getOntologyIRI().get();
        assert ont1_iri != null;
        assert ont2_iri != null;
        OWLImportsDeclaration ont2_import = ImportsDeclaration(ont1_iri);
        ont2.getOWLOntologyManager().applyChange(
                new AddImport(ont2, ont2_import));
        OWLAnnotationProperty annoProp = AnnotationProperty(getIRI("annoProp"));
        OWLAxiom ax_annoProp_decl = Declaration(annoProp);
        ont1.getOWLOntologyManager().addAxiom(ont1, ax_annoProp_decl);
        OWLAnnotation in_ont1_anno = Annotation(annoProp, ont1_iri);
        OWLAnnotation in_ont2_anno = Annotation(annoProp, ont2_iri);
        OWLClass A = Class(getIRI("A"));
        OWLAxiom ax_A_decl = Declaration(A, singleton(in_ont1_anno));
        ont1.getOWLOntologyManager().addAxiom(ont1, ax_A_decl);
        OWLClass B = Class(getIRI("B"));
        OWLAxiom ax_B_decl = Declaration(B, singleton(in_ont2_anno));
        ont2.getOWLOntologyManager().addAxiom(ont2, ax_B_decl);
        OWLAxiom ax_AsubB = SubClassOf(Class(getIRI("A")), Class(getIRI("B")),
                singleton(in_ont2_anno));
        ont2.getOWLOntologyManager().addAxiom(ont2, ax_AsubB);
        // annoProp is in ont1 and in the import closure of ont2
        assertTrue(ont1.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertFalse(ont2.containsAxiom(ax_annoProp_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_annoProp_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        // A is in ont1 and in the import closure of ont2
        assertTrue(ont1
                .containsAxiom(ax_A_decl, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertFalse(ont2.containsAxiom(ax_A_decl, EXCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_A_decl, INCLUDED, CONSIDER_ANNOTATIONS));
        // B is in only in ont2
        assertFalse(ont1.containsAxiom(ax_B_decl, INCLUDED,
                CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_B_decl, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2
                .containsAxiom(ax_B_decl, INCLUDED, CONSIDER_ANNOTATIONS));
        // A is a subclass of B is in only in ont2
        assertFalse(ont1
                .containsAxiom(ax_AsubB, INCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_AsubB, EXCLUDED, CONSIDER_ANNOTATIONS));
        assertTrue(ont2.containsAxiom(ax_AsubB, INCLUDED, CONSIDER_ANNOTATIONS));
        File savedLocation1 = folder.newFile("testont1B.owl");
        FileOutputStream out1 = new FileOutputStream(savedLocation1);
        StreamDocumentTarget writer1 = new StreamDocumentTarget(out1);
        ont1.getOWLOntologyManager().saveOntology(ont1, format, writer1);
        File savedLocation2 = folder.newFile("testont2B.owl");
        FileOutputStream out2 = new FileOutputStream(savedLocation2);
        StreamDocumentTarget writer2 = new StreamDocumentTarget(out2);
        ont2.getOWLOntologyManager().saveOntology(ont2, format, writer2);
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        @SuppressWarnings("unused")
        OWLOntology ont1L = man
                .loadOntologyFromOntologyDocument(savedLocation1);
        OWLOntology ont2L = man
                .loadOntologyFromOntologyDocument(savedLocation2);
        for (OWLOntology importedOntology : ont2L.getImports()) {
            for (OWLAxiom importedAxiom : importedOntology.getAxioms()) {
                assertTrue(importedOntology.containsAxiom(importedAxiom,
                        EXCLUDED, CONSIDER_ANNOTATIONS));
                assertFalse(ont2L.containsAxiom(importedAxiom, EXCLUDED,
                        CONSIDER_ANNOTATIONS));
            }
        }
    }
}
