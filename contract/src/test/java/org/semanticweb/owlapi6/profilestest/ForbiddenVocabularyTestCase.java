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
package org.semanticweb.owlapi6.profilestest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.profiles.OWL2DLProfile;
import org.semanticweb.owlapi6.profiles.OWL2RLProfile;
import org.semanticweb.owlapi6.profiles.OWLProfileReport;
import org.semanticweb.owlapi6.profiles.OWLProfileViolation;
import org.semanticweb.owlapi6.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi6.utility.OWLObjectPropertyManager;

public class ForbiddenVocabularyTestCase extends TestBase {

    private static final String URN_TEST = "urn:test:";
    OWLObjectProperty father = df.getOWLObjectProperty(URN_TEST, "hasFather");
    OWLObjectProperty brother = df.getOWLObjectProperty(URN_TEST, "hasBrother");
    OWLObjectProperty child = df.getOWLObjectProperty(URN_TEST, "hasChild");
    OWLObjectProperty uncle = df.getOWLObjectProperty(URN_TEST, "hasUncle");

    @Test
    public void shouldFindViolation() {
        OWLOntology o = loadOntologyFromString(TestFiles.violation, new RDFXMLDocumentFormat());
        OWL2DLProfile p = new OWL2DLProfile();
        OWLProfileReport checkOntology = p.checkOntology(o);
        assertEquals(1, checkOntology.getViolations().size());
        OWLProfileViolation v = checkOntology.getViolations().get(0);
        assertTrue(v instanceof UseOfReservedVocabularyForAnnotationPropertyIRI);
    }

    @Test
    public void testGenIdGalenFragment() {
        OWLOntology o = loadOntologyFromString(TestFiles.galenFragment, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(o);
        assertTrue(report.isInProfile());
    }

    @Test
    public void testOWLEL() {
        OWLOntology o = loadOntologyFromString(TestFiles.ontology, new RDFXMLDocumentFormat());
        OWL2RLProfile p = new OWL2RLProfile();
        OWLProfileReport report = p.checkOntology(o);
        assertTrue(report.getViolations().isEmpty());
    }

    @Test
    public void shouldCauseViolationsWithUseOfPropertyInChain() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        o.addAxiom(df.getOWLDeclarationAxiom(father));
        o.addAxiom(df.getOWLDeclarationAxiom(brother));
        o.addAxiom(df.getOWLDeclarationAxiom(child));
        o.addAxiom(df.getOWLDeclarationAxiom(uncle));
        OWLSubPropertyChainOfAxiom brokenAxiom1 = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(father, brother),
            uncle);
        OWLSubPropertyChainOfAxiom brokenAxiom2 = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(child, uncle),
            brother);
        OWLObjectPropertyManager manager = new OWLObjectPropertyManager(o);
        o.addAxiom(brokenAxiom1);
        o.addAxiom(brokenAxiom2);
        assertTrue(manager.isLessThan(brother, uncle));
        assertTrue(manager.isLessThan(uncle, brother));
        assertTrue(manager.isLessThan(brother, brother));
        assertTrue(manager.isLessThan(uncle, uncle));
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertFalse(violations.isEmpty());
        for (OWLProfileViolation v : violations) {
            assertTrue(brokenAxiom1.equals(v.getAxiom()) || brokenAxiom2.equals(v.getAxiom()));
        }
    }

    @Test
    public void shouldNotCauseViolations() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        o.addAxiom(df.getOWLDeclarationAxiom(father));
        o.addAxiom(df.getOWLDeclarationAxiom(brother));
        o.addAxiom(df.getOWLDeclarationAxiom(child));
        o.addAxiom(df.getOWLDeclarationAxiom(uncle));
        OWLSubPropertyChainOfAxiom brokenAxiom1 = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(father, brother),
            uncle);
        OWLObjectPropertyManager manager = new OWLObjectPropertyManager(o);
        o.addAxiom(brokenAxiom1);
        assertTrue(manager.isLessThan(brother, uncle));
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
        for (OWLProfileViolation v : violations) {
            assertEquals(brokenAxiom1, v.getAxiom());
        }
    }

    @Test
    public void shouldNotCauseViolationsInput1() {
        OWLOntology o = loadOntologyFromString(TestFiles.forbiddenInput1, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldNotCauseViolationsInput2() {
        OWLOntology o = loadOntologyFromString(TestFiles.forbiddenInput2, new RDFXMLDocumentFormat());
        OWL2DLProfile profile = new OWL2DLProfile();
        List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
    }
}
