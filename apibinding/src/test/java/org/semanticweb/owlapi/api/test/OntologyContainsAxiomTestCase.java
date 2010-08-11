package org.semanticweb.owlapi.api.test;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
/*
 * Copyright (C) 2009, University of Manchester
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
    
    public void testOntologyContainsAxiomsForRDFXML1() throws Exception {
    	runTestOntologyContainsAxioms1(new RDFXMLOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLXML1() throws Exception {
    	runTestOntologyContainsAxioms1(new OWLXMLOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForManchesterOWLSyntax1() throws Exception {
    	runTestOntologyContainsAxioms1(new ManchesterOWLSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLFunctionalSyntax1() throws Exception {
    	runTestOntologyContainsAxioms1(new OWLFunctionalSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForTurtleSyntax1() throws Exception {
    	runTestOntologyContainsAxioms1(new TurtleOntologyFormat());
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
        
        File savedLocation2 = File.createTempFile("testont2A", ".owl");
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
    	runTestOntologyContainsAxioms2(new RDFXMLOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLXML2() throws Exception {
    	runTestOntologyContainsAxioms2(new OWLXMLOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForManchesterOWLSyntax2() throws Exception {
    	runTestOntologyContainsAxioms2(new ManchesterOWLSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForOWLFunctionalSyntax2() throws Exception {
    	runTestOntologyContainsAxioms2(new OWLFunctionalSyntaxOntologyFormat());
    }
    
    public void testOntologyContainsAxiomsForTurtleSyntax2() throws Exception {
    	runTestOntologyContainsAxioms2(new TurtleOntologyFormat());
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
