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
package org.semanticweb.owlapi.profiles.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.net.URL;
import java.util.Collection;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class ProfileValidationTestCase extends TestBase {

    @Test
    public void testProfiles() throws OWLOntologyCreationException {
        String TEST_NAMESPACE = "http://www.w3.org/2007/OWL/testOntology#";
        IRI PROFILE_IDENTIFICATION_TEST_IRI = IRI(TEST_NAMESPACE
                + "ProfileIdentificationTest");
        IRI SPECIES_IRI = IRI(TEST_NAMESPACE + "species");
        IRI FULL_IRI = IRI(TEST_NAMESPACE + "FULL");
        IRI DL_IRI = IRI(TEST_NAMESPACE + "DL");
        IRI EL_IRI = IRI(TEST_NAMESPACE + "EL");
        IRI QL_IRI = IRI(TEST_NAMESPACE + "QL");
        IRI RL_IRI = IRI(TEST_NAMESPACE + "RL");
        IRI RDF_XML_PREMISE_ONTOLOGY_IRI = IRI(TEST_NAMESPACE
                + "rdfXmlPremiseOntology");
        URL resourceURL = ProfileValidationTestCase.class
                .getResource("/all.rdf");
        IRI allTestURI = IRI.create(resourceURL);
        OWLOntology testCasesOntology = m
                .loadOntologyFromOntologyDocument(allTestURI);
        OWLClass profileIdentificationTestClass = Class(PROFILE_IDENTIFICATION_TEST_IRI);
        OWLNamedIndividual EL = df.getOWLNamedIndividual(EL_IRI);
        OWLNamedIndividual QL = df.getOWLNamedIndividual(QL_IRI);
        OWLNamedIndividual RL = df.getOWLNamedIndividual(RL_IRI);
        OWLObjectProperty speciesProperty = df
                .getOWLObjectProperty(SPECIES_IRI);
        OWLNamedIndividual FULL = df.getOWLNamedIndividual(FULL_IRI);
        OWLNamedIndividual DL = df.getOWLNamedIndividual(DL_IRI);
        OWLDataProperty rdfXMLPremiseOntologyProperty = df
                .getOWLDataProperty(RDF_XML_PREMISE_ONTOLOGY_IRI);
        for (OWLClassAssertionAxiom ax : testCasesOntology
                .getClassAssertionAxioms(profileIdentificationTestClass)) {
            OWLIndividual ind = ax.getIndividual();
            Collection<OWLLiteral> vals = values(
                    testCasesOntology.getDataPropertyAssertionAxioms(ind),
                    rdfXMLPremiseOntologyProperty);
            if (vals.size() != 1) {
                continue;
            }
            String ontologySerialisation = vals.iterator().next().getLiteral();
            OWLOntology ontology = loadOntologyFromString(ontologySerialisation);
            // FULL?
            Collection<OWLIndividual> finder = values(
                    testCasesOntology.getObjectPropertyAssertionAxioms(ind),
                    speciesProperty);
            if (finder.contains(FULL)) {
                checkProfile(ontology, new OWL2Profile(), true);
            }
            Collection<OWLIndividual> negativeFinder = negValues(
                    testCasesOntology
                            .getNegativeObjectPropertyAssertionAxioms(ind),
                    speciesProperty);
            if (negativeFinder.contains(FULL)) {
                checkProfile(ontology, new OWL2Profile(), false);
            }
            // DL?
            if (finder.contains(DL)) {
                checkProfile(ontology, new OWL2DLProfile(), true);
            }
            if (negativeFinder.contains(DL)) {
                checkProfile(ontology, new OWL2DLProfile(), false);
            }
            // EL?
            if (finder.contains(EL)) {
                checkProfile(ontology, new OWL2ELProfile(), true);
            }
            if (negativeFinder.contains(EL)) {
                checkProfile(ontology, new OWL2ELProfile(), false);
            }
            // QL?
            if (finder.contains(QL)) {
                checkProfile(ontology, new OWL2QLProfile(), true);
            }
            if (negativeFinder.contains(QL)) {
                checkProfile(ontology, new OWL2QLProfile(), false);
            }
            // RL?
            if (finder.contains(RL)) {
                checkProfile(ontology, new OWL2RLProfile(), true);
            }
            if (negativeFinder.contains(RL)) {
                checkProfile(ontology, new OWL2RLProfile(), false);
            }
            m.removeOntology(ontology);
        }
    }

    private void checkProfile(OWLOntology ontology, OWLProfile profile,
            boolean shouldBeInProfile) {
        OWLProfileReport report = profile.checkOntology(ontology);
        assertEquals(shouldBeInProfile, report.isInProfile());
    }

    @Test
    public void shouldNotFailELBecauseOfBoolean()
            throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        OWLAnnotation ann = df.getOWLAnnotation(df.getRDFSLabel(),
                df.getOWLLiteral(true));
        OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(
                IRI.create("urn:test:ELProfile"), ann);
        m.addAxiom(o, ax);
        m.addAxiom(o, Declaration(OWL2Datatype.XSD_BOOLEAN.getDatatype(df)));
        checkProfile(o, new OWL2ELProfile(), true);
    }
}
