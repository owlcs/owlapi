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

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class LoadAnnotationAxiomsTestCase extends TestBase {

    @Test
    public void testIgnoreAnnotations() throws Exception {
        OWLOntology ont = getOWLOntology();
        OWLClass clsA = Class(IRI("http://ont.com#A"));
        OWLClass clsB = Class(IRI("http://ont.com#B"));
        OWLSubClassOfAxiom sca = SubClassOf(clsA, clsB);
        ont.addAxiom(sca);
        OWLAnnotationProperty rdfsComment = RDFSComment();
        OWLLiteral lit = Literal("Hello world");
        OWLAnnotationAssertionAxiom annoAx1 = AnnotationAssertion(rdfsComment,
            clsA.getIRI(), lit);
        ont.addAxiom(annoAx1);
        OWLAnnotationPropertyDomainAxiom annoAx2 = df
            .getOWLAnnotationPropertyDomainAxiom(rdfsComment, clsA.getIRI());
        ont.addAxiom(annoAx2);
        OWLAnnotationPropertyRangeAxiom annoAx3 = df
            .getOWLAnnotationPropertyRangeAxiom(rdfsComment, clsB.getIRI());
        ont.addAxiom(annoAx3);
        OWLAnnotationProperty myComment = AnnotationProperty(
            IRI("http://ont.com#myComment"));
        OWLSubAnnotationPropertyOfAxiom annoAx4 = df
            .getOWLSubAnnotationPropertyOfAxiom(myComment, rdfsComment);
        ont.addAxiom(annoAx4);
        reload(ont, new RDFXMLDocumentFormat());
        reload(ont, new OWLXMLDocumentFormat());
        reload(ont, new TurtleDocumentFormat());
        reload(ont, new FunctionalSyntaxDocumentFormat());
    }

    private void reload(@Nonnull OWLOntology ontology,
        @Nonnull OWLDocumentFormat format)
            throws OWLOntologyStorageException, OWLOntologyCreationException {
        Set<OWLAxiom> annotationAxioms = new HashSet<>();
        Set<OWLAxiom> axioms = asSet(ontology.axioms());
        for (OWLAxiom ax : axioms) {
            if (ax.isAnnotationAxiom()) {
                annotationAxioms.add(ax);
            }
        }
        OWLOntologyLoaderConfiguration withAnnosConfig = new OWLOntologyLoaderConfiguration();
        OWLOntology reloadedWithAnnoAxioms = reload(ontology, format,
            withAnnosConfig);
        Set<OWLAxiom> axioms2 = reloadedWithAnnoAxioms.axioms()
            .collect(toSet());
        assertEquals(axioms, axioms2);
        OWLOntologyLoaderConfiguration withoutAnnosConfig = new OWLOntologyLoaderConfiguration()
            .setLoadAnnotationAxioms(false);
        OWLOntology reloadedWithoutAnnoAxioms = reload(ontology, format,
            withoutAnnosConfig);
        assertFalse(
            axioms.equals(reloadedWithoutAnnoAxioms.axioms().collect(toSet())));
        Set<OWLAxiom> axiomsMinusAnnotationAxioms = new HashSet<>(axioms);
        axiomsMinusAnnotationAxioms.removeAll(annotationAxioms);
        assertEquals(axiomsMinusAnnotationAxioms,
            reloadedWithoutAnnoAxioms.axioms().collect(toSet()));
    }

    private OWLOntology reload(@Nonnull OWLOntology ontology,
        @Nonnull OWLDocumentFormat format,
        @Nonnull OWLOntologyLoaderConfiguration configuration)
            throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLOntology reloaded = loadOntologyWithConfig(
            saveOntology(ontology, format), configuration);
        Set<OWLDeclarationAxiom> declarations = reloaded
            .getAxioms(AxiomType.DECLARATION);
        reloaded.getOWLOntologyManager().removeAxioms(reloaded, declarations);
        return reloaded;
    }
}
