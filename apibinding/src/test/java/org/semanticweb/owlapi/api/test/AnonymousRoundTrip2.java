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
import java.io.IOException;

import junit.framework.TestCase;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class AnonymousRoundTrip2 extends TestCase{
	public void testRoundTrip() {
	        try {
	            AnonymousRoundTrip ma = new AnonymousRoundTrip();
	            ma.buildOntology();
	            ma.printAxioms();
	            ma.write();
	            
	            System.out.println(" -------------- Take 2");
	            
	            ma.loadOntology();
	            ma.printAxioms();
	        }
	        catch (Throwable t) {
	            t.printStackTrace();
	        }
	    }

}
class AnonymousRoundTrip {
    public static final String NS = "http://smi-protege.stanford.edu/ontologies/AnonymousIndividuals.owl";
    
    private OWLOntologyManager manager;
    private OWLOntology ontology;
    private OWLClass a;
    private OWLAnonymousIndividual h, i;
    private OWLAnnotationProperty p;
    private OWLObjectProperty q;
    
    private File savedLocation;

    public AnonymousRoundTrip() throws OWLOntologyCreationException {
        OWLDataFactory factory = new OWLDataFactoryImpl();
        a = factory.getOWLClass(IRI.create(NS + "#A"));
        p = factory.getOWLAnnotationProperty(IRI.create(NS + "#p"));
        q = factory.getOWLObjectProperty(IRI.create(NS + "#q"));
        h = factory.getOWLAnonymousIndividual();
        i = factory.getOWLAnonymousIndividual();
    }
    
    public void buildOntology() throws OWLOntologyCreationException {
        manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        ontology = manager.createOntology(IRI.create(NS));
        
        OWLAnnotation annotation1 = factory.getOWLAnnotation(p, h);
        manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(a.getIRI(), annotation1));
        manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(a, h));
        manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(q, h, i));
        OWLAnnotation annotation2 = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral("Second", "en"));
        manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(h, annotation2));
    }
    
    public void loadOntology() throws OWLOntologyCreationException {
        System.out.println("Reading from location " + savedLocation);
        manager = OWLManager.createOWLOntologyManager();
        ontology = manager.loadOntologyFromOntologyDocument(savedLocation);
    } 
    
    public void printAxioms() {
        System.out.println("Printing axioms --- ");
        for (OWLAxiom axiom : ontology.getAxioms()) {
            System.out.println("\tAxiom: " + axiom);
        }
        System.out.println("End of axiom printout");
    }
    
    public void write() throws OWLOntologyStorageException, IOException {
        savedLocation = File.createTempFile("RoundTripTest", ".owl");
        FileOutputStream out = new FileOutputStream(savedLocation);
        StreamDocumentTarget writer = new StreamDocumentTarget(out);
        manager.saveOntology(ontology, new ManchesterOWLSyntaxOntologyFormat(), writer);
        out.flush();
        out.close();
        System.out.println("Saved to location " + savedLocation);
    }
    

}
