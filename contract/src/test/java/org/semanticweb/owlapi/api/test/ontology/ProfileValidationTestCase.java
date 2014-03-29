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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
 *         Management Group, Date: 18-Aug-2009
 */
@SuppressWarnings("javadoc")
public class ProfileValidationTestCase {

    private static final String TEST_NAMESPACE = "http://www.w3.org/2007/OWL/testOntology#";
    private static final IRI PROFILE_IDENTIFICATION_TEST_IRI = IRI(TEST_NAMESPACE
            + "ProfileIdentificationTest");
    private static final IRI SPECIES_IRI = IRI(TEST_NAMESPACE + "species");
    private static final IRI FULL_IRI = IRI(TEST_NAMESPACE + "FULL");
    private static final IRI DL_IRI = IRI(TEST_NAMESPACE + "DL");
    private static final IRI EL_IRI = IRI(TEST_NAMESPACE + "EL");
    private static final IRI QL_IRI = IRI(TEST_NAMESPACE + "QL");
    private static final IRI RL_IRI = IRI(TEST_NAMESPACE + "RL");
    private static final IRI RDF_XML_PREMISE_ONTOLOGY_IRI = IRI(TEST_NAMESPACE
            + "rdfXmlPremiseOntology");

    @Test
    public void testProfiles() throws OWLOntologyCreationException,
            URISyntaxException {
        OWLOntologyManager man = Factory.getManager();
        URL resourceURL = ProfileValidationTestCase.class
                .getResource("/all.rdf");
        IRI allTestURI = IRI.create(resourceURL);
        OWLOntology testCasesOntology = man
                .loadOntologyFromOntologyDocument(allTestURI);
        OWLDataFactory df = man.getOWLDataFactory();
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
            Set<OWLLiteral> vals = ind.getDataPropertyValues(
                    rdfXMLPremiseOntologyProperty, testCasesOntology);
            if (vals.size() != 1) {
                continue;
            }
            String ontologySerialisation = vals.iterator().next().getLiteral();
            OWLOntology ontology = man
                    .loadOntologyFromOntologyDocument(new StringDocumentSource(
                            ontologySerialisation));
            // FULL?
            if (ind.hasObjectPropertyValue(speciesProperty, FULL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2Profile(), true);
            }
            if (ind.hasNegativeObjectPropertyValue(speciesProperty, FULL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2Profile(), false);
            }
            // DL?
            if (ind.hasObjectPropertyValue(speciesProperty, DL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2DLProfile(), true);
            }
            if (ind.hasNegativeObjectPropertyValue(speciesProperty, DL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2DLProfile(), false);
            }
            // EL?
            if (ind.hasObjectPropertyValue(speciesProperty, EL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2ELProfile(), true);
            }
            if (ind.hasNegativeObjectPropertyValue(speciesProperty, EL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2ELProfile(), false);
            }
            // QL?
            if (ind.hasObjectPropertyValue(speciesProperty, QL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2QLProfile(), true);
            }
            if (ind.hasNegativeObjectPropertyValue(speciesProperty, QL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2QLProfile(), false);
            }
            // RL?
            if (ind.hasObjectPropertyValue(speciesProperty, RL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2RLProfile(), true);
            }
            if (ind.hasNegativeObjectPropertyValue(speciesProperty, RL,
                    testCasesOntology)) {
                checkProfile(ontology, new OWL2RLProfile(), false);
            }
            man.removeOntology(ontology);
        }
    }

    private void checkProfile(OWLOntology ontology, OWLProfile profile,
            boolean shouldBeInProfile) {
        OWLProfileReport report = profile.checkOntology(ontology);
        assertEquals(
                "FAIL: " + ontology.getOntologyID() + " should "
                        + (!shouldBeInProfile ? "not " : "") + "be in the "
                        + profile.getName() + " profile. Report: " + report,
                shouldBeInProfile, report.isInProfile());
    }

    @Test
    public void shouldNotFailELBecauseOfBoolean()
            throws OWLOntologyCreationException {
        OWLOntology o = OWLManager.createOWLOntologyManager().createOntology();
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        o.getOWLOntologyManager().addAxiom(o,
                Declaration(OWL2Datatype.XSD_BOOLEAN.getDatatype(df)));
        o.getOWLOntologyManager().addAxiom(
                o,
                df.getOWLAnnotationAssertionAxiom(
                        IRI.create("urn:test:ELProfile"),
                        df.getOWLAnnotation(df.getRDFSLabel(),
                                df.getOWLLiteral(true))));
        checkProfile(o, new OWL2ELProfile(), true);
    }
}
