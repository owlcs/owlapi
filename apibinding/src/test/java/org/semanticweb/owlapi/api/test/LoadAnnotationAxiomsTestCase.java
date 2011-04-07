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
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class LoadAnnotationAxiomsTestCase extends AbstractOWLAPITestCase {


    public void testIgnoreAnnotations() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = man.createOntology();
        OWLDataFactory df = man.getOWLDataFactory();
        OWLClass clsA = df.getOWLClass(IRI.create("http://ont.com#A"));
        OWLClass clsB = df.getOWLClass(IRI.create("http://ont.com#B"));
        OWLSubClassOfAxiom sca = df.getOWLSubClassOfAxiom(clsA, clsB);
        man.addAxiom(ont, sca);

        OWLAnnotationProperty rdfsComment = df.getRDFSComment();
        OWLLiteral lit = df.getOWLLiteral("Hello world");

        OWLAnnotationAssertionAxiom annoAx1 = df.getOWLAnnotationAssertionAxiom(rdfsComment, clsA.getIRI(), lit);
        man.addAxiom(ont, annoAx1);

        OWLAnnotationPropertyDomainAxiom annoAx2 = df.getOWLAnnotationPropertyDomainAxiom(rdfsComment, clsA.getIRI());
        man.addAxiom(ont, annoAx2);

        OWLAnnotationPropertyRangeAxiom annoAx3 = df.getOWLAnnotationPropertyRangeAxiom(rdfsComment, clsB.getIRI());
        man.addAxiom(ont, annoAx3);

        OWLAnnotationProperty myComment = df.getOWLAnnotationProperty(IRI.create("http://ont.com#myComment"));
        OWLSubAnnotationPropertyOfAxiom annoAx4 = df.getOWLSubAnnotationPropertyOfAxiom(myComment, rdfsComment);
        man.addAxiom(ont, annoAx4);

        reload(ont, new RDFXMLOntologyFormat());
        reload(ont, new OWLXMLOntologyFormat());
        reload(ont, new TurtleOntologyFormat());
        reload(ont, new OWLFunctionalSyntaxOntologyFormat());

    }

    private void reload(OWLOntology ontology, OWLOntologyFormat format) throws Exception {
        Set<OWLAxiom> annotationAxioms = new HashSet<OWLAxiom>();
        for(OWLAxiom ax : ontology.getAxioms()) {
            if(ax.isAnnotationAxiom()) {
                annotationAxioms.add(ax);
            }
        }
        OWLOntologyLoaderConfiguration withAnnosConfig = new OWLOntologyLoaderConfiguration();
        OWLOntology reloadedWithAnnoAxioms = reload(ontology, format, withAnnosConfig);
        assertEquals(ontology.getAxioms(), reloadedWithAnnoAxioms.getAxioms());
//
        OWLOntologyLoaderConfiguration withoutAnnosConfig = new OWLOntologyLoaderConfiguration().setLoadAnnotationAxioms(false);
        OWLOntology reloadedWithoutAnnoAxioms = reload(ontology, format, withoutAnnosConfig);
        assertFalse(ontology.getAxioms().equals(reloadedWithoutAnnoAxioms.getAxioms()));

        Set<OWLAxiom> axiomsMinusAnnotationAxioms = new HashSet<OWLAxiom>(ontology.getAxioms());
        axiomsMinusAnnotationAxioms.removeAll(annotationAxioms);
        assertEquals(axiomsMinusAnnotationAxioms, reloadedWithoutAnnoAxioms.getAxioms());
    }

    private OWLOntology reload(OWLOntology ontology, OWLOntologyFormat format, OWLOntologyLoaderConfiguration configuration) throws IOException, OWLOntologyStorageException, OWLOntologyCreationException {
        OWLOntologyManager man = ontology.getOWLOntologyManager();
        File tempFile = File.createTempFile("Ontology", ".owl");

        man.saveOntology(ontology, format, new FileDocumentTarget(tempFile));

        OWLOntologyManager man2 = OWLManager.createOWLOntologyManager();
        OWLOntology reloaded =  man2.loadOntologyFromOntologyDocument(new FileDocumentSource(tempFile), configuration);
        man2.removeAxioms(reloaded, new HashSet<OWLAxiom>(reloaded.getAxioms(AxiomType.DECLARATION)));
        return reloaded;
    }

}
