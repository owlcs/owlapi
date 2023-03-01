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
package org.semanticweb.owlapi.profilestest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.utility.OWLObjectPropertyManager;

class ForbiddenVocabularyTestCase extends TestBase {

    @Test
    void shouldFindViolation() {
        OWLOntology o = loadFrom(TestFiles.violation, new RDFXMLDocumentFormat());
        OWL2DLProfile p = new OWL2DLProfile();
        OWLProfileReport checkOntology = p.checkOntology(o);
        assertEquals(1, checkOntology.getViolations().size());
        OWLProfileViolation violation = checkOntology.getViolations().get(0);
        assertTrue(violation instanceof UseOfReservedVocabularyForAnnotationPropertyIRI);
    }

    @Test
    void shouldFindViolationOnDeclaration() {
        OWLOntology o = loadFrom(TestFiles.violationDeclaration, new RDFXMLDocumentFormat());
        OWL2DLProfile p = new OWL2DLProfile();
        OWLProfileReport checkOntology = p.checkOntology(o);
        assertEquals(1, checkOntology.getViolations().size());
        OWLProfileViolation violation = checkOntology.getViolations().get(0);
        assertTrue(violation instanceof UseOfReservedVocabularyForAnnotationPropertyIRI);
    }

    @Test
    void testGenIdGalenFragment() {
        OWLOntology o = loadFrom(TestFiles.galenFragment, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(o);
        assertTrue(report.isInProfile());
    }

    @Test
    void testOWLEL() {
        OWLOntology o = loadFrom(TestFiles.ontology, new RDFXMLDocumentFormat());
        OWL2RLProfile p = new OWL2RLProfile();
        OWLProfileReport report = p.checkOntology(o);
        assertTrue(report.getViolations().isEmpty());
    }

    @Test
    void shouldCauseViolationsWithUseOfPropertyInChain() {
        OWLAxiom brokenAxiom1 =
            SubPropertyChainOf(l(OBJPROPS.father, OBJPROPS.brother), OBJPROPS.uncle);
        OWLAxiom brokenAxiom2 =
            SubPropertyChainOf(l(OBJPROPS.child, OBJPROPS.uncle), OBJPROPS.brother);
        OWLOntology o = o(brokenAxiom1, brokenAxiom2);
        OWLObjectPropertyManager manager = new OWLObjectPropertyManager(o);
        assertTrue(manager.isLessThan(OBJPROPS.brother, OBJPROPS.uncle));
        assertTrue(manager.isLessThan(OBJPROPS.uncle, OBJPROPS.brother));
        assertTrue(manager.isLessThan(OBJPROPS.brother, OBJPROPS.brother));
        assertTrue(manager.isLessThan(OBJPROPS.uncle, OBJPROPS.uncle));
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertFalse(violations.isEmpty());
        violations.forEach(violation -> assertTrue(brokenAxiom1.equals(violation.getAxiom())
            || brokenAxiom2.equals(violation.getAxiom())));
    }

    @Test
    void shouldNotCauseViolations() {
        OWLSubPropertyChainOfAxiom brokenAxiom1 =
            SubPropertyChainOf(l(OBJPROPS.father, OBJPROPS.brother), OBJPROPS.uncle);
        OWLOntology o = o(brokenAxiom1);
        OWLObjectPropertyManager manager = new OWLObjectPropertyManager(o);
        assertTrue(manager.isLessThan(OBJPROPS.brother, OBJPROPS.uncle));
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
        violations.forEach(violation -> assertEquals(brokenAxiom1, violation.getAxiom()));
    }

    @Test
    void shouldNotCauseViolationsInput1() {
        OWLOntology o = loadFrom(TestFiles.forbiddenInput1, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCauseViolationsInput2() {
        OWLOntology o = loadFrom(TestFiles.forbiddenInput2, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
    }
}
