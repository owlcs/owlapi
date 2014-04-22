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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class LoadAnnotationAxiomsTestCase extends TestBase {

    @Test
    public void testIgnoreAnnotations() throws OWLOntologyStorageException,
            OWLOntologyCreationException {
        OWLOntology ont = m.createOntology();
        OWLClass clsA = Class(IRI("http://ont.com#A"));
        OWLClass clsB = Class(IRI("http://ont.com#B"));
        OWLSubClassOfAxiom sca = SubClassOf(clsA, clsB);
        m.addAxiom(ont, sca);
        OWLAnnotationProperty rdfsComment = RDFSComment();
        OWLLiteral lit = Literal("Hello world");
        OWLAnnotationAssertionAxiom annoAx1 = AnnotationAssertion(rdfsComment,
                clsA.getIRI(), lit);
        m.addAxiom(ont, annoAx1);
        OWLAnnotationPropertyDomainAxiom annoAx2 = df
                .getOWLAnnotationPropertyDomainAxiom(rdfsComment, clsA.getIRI());
        m.addAxiom(ont, annoAx2);
        OWLAnnotationPropertyRangeAxiom annoAx3 = df
                .getOWLAnnotationPropertyRangeAxiom(rdfsComment, clsB.getIRI());
        m.addAxiom(ont, annoAx3);
        OWLAnnotationProperty myComment = AnnotationProperty(IRI("http://ont.com#myComment"));
        OWLSubAnnotationPropertyOfAxiom annoAx4 = df
                .getOWLSubAnnotationPropertyOfAxiom(myComment, rdfsComment);
        m.addAxiom(ont, annoAx4);
        reload(ont, new RDFXMLOntologyFormat());
        reload(ont, new OWLXMLOntologyFormat());
        reload(ont, new TurtleOntologyFormat());
        reload(ont, new OWLFunctionalSyntaxOntologyFormat());
    }

    private void
            reload(@Nonnull OWLOntology ontology, OWLOntologyFormat format)
                    throws OWLOntologyStorageException,
                    OWLOntologyCreationException {
        Set<OWLAxiom> annotationAxioms = new HashSet<OWLAxiom>();
        Set<OWLAxiom> axioms = ontology.getAxioms();
        for (OWLAxiom ax : axioms) {
            if (ax.isAnnotationAxiom()) {
                annotationAxioms.add(ax);
            }
        }
        OWLOntologyLoaderConfiguration withAnnosConfig = new OWLOntologyLoaderConfiguration();
        OWLOntology reloadedWithAnnoAxioms = reload(ontology, format,
                withAnnosConfig);
        Set<OWLAxiom> axioms2 = reloadedWithAnnoAxioms.getAxioms();
        assertEquals(axioms, axioms2);
        OWLOntologyLoaderConfiguration withoutAnnosConfig = new OWLOntologyLoaderConfiguration()
                .setLoadAnnotationAxioms(false);
        OWLOntology reloadedWithoutAnnoAxioms = reload(ontology, format,
                withoutAnnosConfig);
        assertFalse(axioms.equals(reloadedWithoutAnnoAxioms.getAxioms()));
        Set<OWLAxiom> axiomsMinusAnnotationAxioms = new HashSet<OWLAxiom>(
                axioms);
        axiomsMinusAnnotationAxioms.removeAll(annotationAxioms);
        assertEquals(axiomsMinusAnnotationAxioms,
                reloadedWithoutAnnoAxioms.getAxioms());
    }

    private OWLOntology reload(OWLOntology ontology, OWLOntologyFormat format,
            OWLOntologyLoaderConfiguration configuration)
            throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLOntology reloaded = loadOntologyWithConfig(
                saveOntology(ontology, format), configuration);
        HashSet<OWLAxiom> declarations = new HashSet<OWLAxiom>(
                reloaded.getAxioms(AxiomType.DECLARATION));
        reloaded.getOWLOntologyManager().removeAxioms(reloaded, declarations);
        return reloaded;
    }
}
