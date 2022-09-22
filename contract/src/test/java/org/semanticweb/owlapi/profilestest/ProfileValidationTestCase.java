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
import static org.semanticweb.owlapi.search.Searcher.negValues;
import static org.semanticweb.owlapi.search.Searcher.values;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.net.URL;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class ProfileValidationTestCase extends TestBase {

    private static void checkProfile(OWLOntology ontology, OWLProfile profile,
        boolean shouldBeInProfile) {
        OWLProfileReport report = profile.checkOntology(ontology);
        assertEquals(Boolean.valueOf(shouldBeInProfile), Boolean.valueOf(report.isInProfile()));
    }

    @Test
    void testProfiles() {
        URL resourceURL = ProfileValidationTestCase.class.getResource("/all.rdf");
        OWLOntology testCasesOntology = loadFrom(iri(resourceURL));
        for (OWLClassAssertionAxiom ax : asList(
            testCasesOntology.classAssertionAxioms(CLASSES.profileIdentificationTestClass))) {
            OWLIndividual ind = ax.getIndividual();
            Collection<OWLLiteral> vals =
                asUnorderedSet(values(testCasesOntology.dataPropertyAssertionAxioms(ind),
                    DATAPROPS.rdfXML));
            if (vals.size() != 1) {
                continue;
            }
            String ontologySerialisation = vals.iterator().next().getLiteral();
            OWLOntology ontology = loadFrom(ontologySerialisation, new RDFXMLDocumentFormat());
            // FULL?
            Collection<OWLIndividual> finder =
                asUnorderedSet(values(testCasesOntology.objectPropertyAssertionAxioms(ind),
                    OBJPROPS.speciesProperty));
            if (finder.contains(INDIVIDUALS.full)) {
                checkProfile(ontology, new OWL2Profile(), true);
            }
            Collection<OWLIndividual> negativeFinder = asUnorderedSet(
                negValues(testCasesOntology.negativeObjectPropertyAssertionAxioms(ind),
                    OBJPROPS.speciesProperty));
            if (negativeFinder.contains(INDIVIDUALS.full)) {
                checkProfile(ontology, new OWL2Profile(), false);
            }
            // DL?
            if (finder.contains(INDIVIDUALS.dl)) {
                checkProfile(ontology, new OWL2DLProfile(), true);
            }
            if (negativeFinder.contains(INDIVIDUALS.dl)) {
                checkProfile(ontology, new OWL2DLProfile(), false);
            }
            // EL?
            if (finder.contains(INDIVIDUALS.el)) {
                checkProfile(ontology, new OWL2ELProfile(), true);
            }
            if (negativeFinder.contains(INDIVIDUALS.el)) {
                checkProfile(ontology, new OWL2ELProfile(), false);
            }
            // QL?
            if (finder.contains(INDIVIDUALS.ql)) {
                checkProfile(ontology, new OWL2QLProfile(), true);
            }
            if (negativeFinder.contains(INDIVIDUALS.ql)) {
                checkProfile(ontology, new OWL2QLProfile(), false);
            }
            // RL?
            if (finder.contains(INDIVIDUALS.rl)) {
                checkProfile(ontology, new OWL2RLProfile(), true);
            }
            if (negativeFinder.contains(INDIVIDUALS.rl)) {
                checkProfile(ontology, new OWL2RLProfile(), false);
            }
            m.removeOntology(ontology);
        }
    }

    @Test
    void shouldNotFailELBecauseOfBoolean() {
        OWLOntology o =
            o(AnnotationAssertion(RDFSLabel(), iri("urn:test#", "ELProfile"), LITERALS.LIT_TRUE));
        checkProfile(o, new OWL2ELProfile(), true);
    }
}
