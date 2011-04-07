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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
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
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class OntologyContainsAxiomTestCase extends AbstractOWLAPITestCase {

    public void testOntologyContainsPlainAxiom() throws Exception {
        OWLAxiom axiom = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"));
        OWLOntology ont = getOWLOntology("testont");
        getManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom));
    }



    public void testOntologyContainsAnnotatedAxiom() throws Exception {
        OWLLiteral annoLiteral = getFactory().getOWLLiteral("value");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
        OWLAnnotation anno = getFactory().getOWLAnnotation(annoProp, annoLiteral);
        OWLAxiom axiom = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(anno));
        OWLOntology ont = getOWLOntology("testont");
        getManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom));
        assertFalse(ont.containsAxiom(axiom.getAxiomWithoutAnnotations()));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom.getAxiomWithoutAnnotations()));
    }
    
//    public void testOntologyContainsAxiomsForRDFXML1() throws Exception {
//        RDFXMLOntologyFormat format = createRDFXMLFormat();
//        runTestOntologyContainsAxioms1(format);
//    }

    private RDFXMLOntologyFormat createRDFXMLFormat() {
        RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
        // This test case relies on certain declarations being in certain ontologies.  The default
        // behaviour is to add missing declarations.  Therefore, this needs to be turned off.
        format.setAddMissingTypes(false);
        return format;
    }

//    public void testOntologyContainsAxiomsForOWLXML1() throws Exception {
//    	runTestOntologyContainsAxioms1(new OWLXMLOntologyFormat());
//    }

    public void testOntologyContainsAxiomsForOWLFunctionalSyntax1() throws Exception {
    	runTestOntologyContainsAxioms1(new OWLFunctionalSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForTurtleSyntax1() throws Exception {
        TurtleOntologyFormat format = createTurtleOntologyFormat();
        runTestOntologyContainsAxioms1(format);
    }

    private TurtleOntologyFormat createTurtleOntologyFormat() {
        TurtleOntologyFormat format = new TurtleOntologyFormat();
        format.setAddMissingTypes(false);
        return format;
    }

    private void runTestOntologyContainsAxioms1(OWLOntologyFormat format) throws Exception {
    	
    	OWLOntology ont1 = getOWLOntology("testont1A");
    	IRI ont1_iri = ont1.getOntologyID().getOntologyIRI();

    	OWLOntology ont2 = getOWLOntology("testont2A");
    	IRI ont2_iri = ont2.getOntologyID().getOntologyIRI();

    	OWLImportsDeclaration ont2_import = getFactory().getOWLImportsDeclaration(ont1_iri);
    	getManager().applyChange(new AddImport(ont2, ont2_import));

    	OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
    	OWLAxiom ax_annoProp_decl = getFactory().getOWLDeclarationAxiom(annoProp);
    	getManager().addAxiom(ont1, ax_annoProp_decl);

    	OWLAnnotation in_ont1_anno = getFactory().getOWLAnnotation(annoProp, ont1_iri);
    	OWLAnnotation in_ont2_anno = getFactory().getOWLAnnotation(annoProp, ont2_iri);

    	OWLClass A = getOWLClass("A");
        OWLAxiom ax_A_decl = getFactory().getOWLDeclarationAxiom(A, Collections.singleton(in_ont1_anno));
        getManager().addAxiom(ont1, ax_A_decl);

        OWLClass B = getOWLClass("B");
        OWLAxiom ax_B_decl = getFactory().getOWLDeclarationAxiom(B, Collections.singleton(in_ont2_anno));
        getManager().addAxiom(ont2, ax_B_decl);

        OWLAxiom ax_AsubB = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(in_ont2_anno));
        getManager().addAxiom(ont2, ax_AsubB);

        // annoProp is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1.containsAxiom(ax_annoProp_decl, false));
        assertFalse(ont2.containsAxiom(ax_annoProp_decl, false));
        assertTrue(ont2.containsAxiom(ax_annoProp_decl, true));

        // A is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1.containsAxiom(ax_A_decl, false));
        assertFalse(ont2.containsAxiom(ax_A_decl, false));
        assertTrue(ont2.containsAxiom(ax_A_decl, true));

        // B is in only in ont2
        //
        assertFalse(ont1.containsAxiom(ax_B_decl, true));
        assertTrue(ont2.containsAxiom(ax_B_decl, false));
        assertTrue(ont2.containsAxiom(ax_B_decl, true));
     	// A is a subclass of B is in only in ont2
        //
        assertFalse(ont1.containsAxiom(ax_AsubB, true));
        assertTrue(ont2.containsAxiom(ax_AsubB, false));
        assertTrue(ont2.containsAxiom(ax_AsubB, true));

        File savedLocation1 = File.createTempFile("testont1A", ".owl");
        FileOutputStream out1 = new FileOutputStream(savedLocation1);
        StreamDocumentTarget writer1 = new StreamDocumentTarget(out1);
        getManager().saveOntology(ont1, format, writer1);

        File savedLocation2 = File.createTempFile("testont2A.owl", ".owl");
        FileOutputStream out2 = new FileOutputStream(savedLocation2);
        StreamDocumentTarget writer2 = new StreamDocumentTarget(out2);
        getManager().saveOntology(ont2, format, writer2);


        OWLOntologyManager man = OWLManager.createOWLOntologyManager();

        OWLOntology ont1L = man.loadOntologyFromOntologyDocument(savedLocation1);
        OWLOntology ont2L = man.loadOntologyFromOntologyDocument(savedLocation2);

        // annoProp is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1L.containsAxiom(ax_annoProp_decl, false));
        assertFalse(ont2L.containsAxiom(ax_annoProp_decl, false));
        assertTrue(ont2L.containsAxiom(ax_annoProp_decl, true));

        // A is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1L.containsAxiom(ax_A_decl, false));
        assertFalse(ont2L.containsAxiom(ax_A_decl, false));
        assertTrue(ont2L.containsAxiom(ax_A_decl, true));

        // B is in only in ont2
        //
        assertFalse(ont1L.containsAxiom(ax_B_decl, true));
        assertTrue(ont2L.containsAxiom(ax_B_decl, false));
        assertTrue(ont2L.containsAxiom(ax_B_decl, true));
     	// A is a subclass of B is in only in ont2
        //
        assertFalse(ont1L.containsAxiom(ax_AsubB, true));
        assertTrue(ont2L.containsAxiom(ax_AsubB, false));
        assertTrue(ont2L.containsAxiom(ax_AsubB, true));
    }
    
    public void testOntologyContainsAxiomsForRDFXML2() throws Exception {
    	runTestOntologyContainsAxioms2(createRDFXMLFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLXML2() throws Exception {
    	runTestOntologyContainsAxioms2(new OWLXMLOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLFunctionalSyntax2() throws Exception {
    	runTestOntologyContainsAxioms2(new OWLFunctionalSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForTurtleSyntax2() throws Exception {
    	runTestOntologyContainsAxioms2(createTurtleOntologyFormat());
    }
    
    private void runTestOntologyContainsAxioms2(OWLOntologyFormat format) throws Exception {
    	
    	OWLOntology ont1 = getOWLOntology("testont1B");
    	IRI ont1_iri = ont1.getOntologyID().getOntologyIRI();
    	
    	OWLOntology ont2 = getOWLOntology("testont2B");
    	IRI ont2_iri = ont2.getOntologyID().getOntologyIRI();
    	
    	OWLImportsDeclaration ont2_import = getFactory().getOWLImportsDeclaration(ont1_iri);
    	getManager().applyChange(new AddImport(ont2, ont2_import));
    	
    	OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
    	OWLAxiom ax_annoProp_decl = getFactory().getOWLDeclarationAxiom(annoProp);
    	getManager().addAxiom(ont1, ax_annoProp_decl);
    	
    	OWLAnnotation in_ont1_anno = getFactory().getOWLAnnotation(annoProp, ont1_iri);
    	OWLAnnotation in_ont2_anno = getFactory().getOWLAnnotation(annoProp, ont2_iri);
         
    	OWLClass A = getOWLClass("A");
        OWLAxiom ax_A_decl = getFactory().getOWLDeclarationAxiom(A, Collections.singleton(in_ont1_anno));
        getManager().addAxiom(ont1, ax_A_decl);
        
        OWLClass B = getOWLClass("B");
        OWLAxiom ax_B_decl = getFactory().getOWLDeclarationAxiom(B, Collections.singleton(in_ont2_anno));
        getManager().addAxiom(ont2, ax_B_decl);
        
        OWLAxiom ax_AsubB = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(in_ont2_anno));
        getManager().addAxiom(ont2, ax_AsubB);
       
        // annoProp is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1.containsAxiom(ax_annoProp_decl, false));
        assertFalse(ont2.containsAxiom(ax_annoProp_decl, false));
        assertTrue(ont2.containsAxiom(ax_annoProp_decl, true));
        
        // A is in ont1 and in the import closure of ont2
        //
        assertTrue(ont1.containsAxiom(ax_A_decl, false));
        assertFalse(ont2.containsAxiom(ax_A_decl, false));
        assertTrue(ont2.containsAxiom(ax_A_decl, true));
        
        // B is in only in ont2
        //
        assertFalse(ont1.containsAxiom(ax_B_decl, true));
        assertTrue(ont2.containsAxiom(ax_B_decl, false));
        assertTrue(ont2.containsAxiom(ax_B_decl, true));
     	// A is a subclass of B is in only in ont2
        //
        assertFalse(ont1.containsAxiom(ax_AsubB, true));
        assertTrue(ont2.containsAxiom(ax_AsubB, false));
        assertTrue(ont2.containsAxiom(ax_AsubB, true));
        
        File savedLocation1 = File.createTempFile("testont1B", ".owl");
        FileOutputStream out1 = new FileOutputStream(savedLocation1);
        StreamDocumentTarget writer1 = new StreamDocumentTarget(out1);
        getManager().saveOntology(ont1, format, writer1);
        
        File savedLocation2 = File.createTempFile("testont2B", ".owl");
        FileOutputStream out2 = new FileOutputStream(savedLocation2);
        StreamDocumentTarget writer2 = new StreamDocumentTarget(out2);
        getManager().saveOntology(ont2, format, writer2);
        
        
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        
        @SuppressWarnings("unused")
		OWLOntology ont1L = man.loadOntologyFromOntologyDocument(savedLocation1);
        OWLOntology ont2L = man.loadOntologyFromOntologyDocument(savedLocation2);
        
        for (OWLOntology importedOntology : ont2L.getImports()) {
        	for (OWLAxiom importedAxiom : importedOntology.getAxioms()) {
        		assertTrue(importedOntology.containsAxiom(importedAxiom, false));
        		assertFalse(ont2L.containsAxiom(importedAxiom, false));
        	}
        }
        
    }
}
