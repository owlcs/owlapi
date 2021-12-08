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
package org.semanticweb.owlapi.profiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.search.Searcher.negValues;
import static org.semanticweb.owlapi.search.Searcher.values;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.net.URL;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class ProfileValidationTestCase extends TestBase {

    private static void checkProfile(OWLOntology ontology, OWLProfile profile,
        boolean shouldBeInProfile) {
        OWLProfileReport report = profile.checkOntology(ontology);
        assertTrue(shouldBeInProfile == report.isInProfile());
    }

    @Test
    void testProfiles() {
        String ns = "http://www.w3.org/2007/OWL/testOntology#";
        IRI profile = iri(ns, "ProfileIdentificationTest");
        IRI species = iri(ns, "species");
        IRI fullIRI = iri(ns, "FULL");
        IRI dlIRI = iri(ns, "DL");
        IRI elIRI = iri(ns, "EL");
        IRI qlIRI = iri(ns, "QL");
        IRI rlIRI = iri(ns, "RL");
        IRI premiseIRI = iri(ns, "rdfXmlPremiseOntology");
        URL resourceURL = ProfileValidationTestCase.class.getResource("/all.rdf");
        IRI allTestURI = IRI.create(resourceURL);
        OWLOntology testCasesOntology = loadFrom(allTestURI);
        OWLClass profileIdentificationTestClass = Class(profile);
        OWLNamedIndividual el = NamedIndividual(elIRI);
        OWLNamedIndividual ql = NamedIndividual(qlIRI);
        OWLNamedIndividual rl = NamedIndividual(rlIRI);
        OWLObjectProperty speciesProperty = ObjectProperty(species);
        OWLNamedIndividual full = NamedIndividual(fullIRI);
        OWLNamedIndividual dl = NamedIndividual(dlIRI);
        OWLDataProperty rdfXMLPremiseOntologyProperty = DataProperty(premiseIRI);
        for (OWLClassAssertionAxiom ax : asList(
            testCasesOntology.classAssertionAxioms(profileIdentificationTestClass))) {
            OWLIndividual ind = ax.getIndividual();
            Collection<OWLLiteral> vals =
                asUnorderedSet(values(testCasesOntology.dataPropertyAssertionAxioms(ind),
                    rdfXMLPremiseOntologyProperty));
            if (vals.size() != 1) {
                continue;
            }
            String ontologySerialisation = vals.iterator().next().getLiteral();
            OWLOntology ontology = loadFrom(ontologySerialisation);
            // FULL?
            Collection<OWLIndividual> finder = asUnorderedSet(
                values(testCasesOntology.objectPropertyAssertionAxioms(ind), speciesProperty));
            if (finder.contains(full)) {
                checkProfile(ontology, new OWL2Profile(), true);
            }
            Collection<OWLIndividual> negativeFinder = asUnorderedSet(negValues(
                testCasesOntology.negativeObjectPropertyAssertionAxioms(ind), speciesProperty));
            if (negativeFinder.contains(full)) {
                checkProfile(ontology, new OWL2Profile(), false);
            }
            // DL?
            if (finder.contains(dl)) {
                checkProfile(ontology, new OWL2DLProfile(), true);
            }
            if (negativeFinder.contains(dl)) {
                checkProfile(ontology, new OWL2DLProfile(), false);
            }
            // EL?
            if (finder.contains(el)) {
                checkProfile(ontology, new OWL2ELProfile(), true);
            }
            if (negativeFinder.contains(el)) {
                checkProfile(ontology, new OWL2ELProfile(), false);
            }
            // QL?
            if (finder.contains(ql)) {
                checkProfile(ontology, new OWL2QLProfile(), true);
            }
            if (negativeFinder.contains(ql)) {
                checkProfile(ontology, new OWL2QLProfile(), false);
            }
            // RL?
            if (finder.contains(rl)) {
                checkProfile(ontology, new OWL2RLProfile(), true);
            }
            if (negativeFinder.contains(rl)) {
                checkProfile(ontology, new OWL2RLProfile(), false);
            }
            m.removeOntology(ontology);
        }
    }

    @Test
    void shouldNotFailELBecauseOfBoolean() {
        OWLOntology o =
            o(AnnotationAssertion(RDFSLabel(), iri("urn:test#", "ELProfile"), LIT_TRUE));
        checkProfile(o, new OWL2ELProfile(), true);
    }
}
