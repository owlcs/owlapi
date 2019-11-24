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
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.search.Searcher.negValues;
import static org.semanticweb.owlapi6.search.Searcher.values;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.net.URL;
import java.util.Collection;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.profiles.OWL2DLProfile;
import org.semanticweb.owlapi6.profiles.OWL2ELProfile;
import org.semanticweb.owlapi6.profiles.OWL2Profile;
import org.semanticweb.owlapi6.profiles.OWL2QLProfile;
import org.semanticweb.owlapi6.profiles.OWL2RLProfile;
import org.semanticweb.owlapi6.profiles.OWLProfile;
import org.semanticweb.owlapi6.profiles.OWLProfileReport;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class ProfileValidationTestCase extends TestBase {

    private static void checkProfile(OWLOntology ontology, OWLProfile profile,
        boolean shouldBeInProfile) {
        OWLProfileReport report = profile.checkOntology(ontology);
        assertEquals(Boolean.valueOf(shouldBeInProfile), Boolean.valueOf(report.isInProfile()));
    }

    @Test
    public void testProfiles() throws OWLOntologyCreationException {
        String ns = "http://www.w3.org/2007/OWL/testOntology#";
        IRI profile = IRI(ns, "ProfileIdentificationTest");
        IRI species = IRI(ns, "species");
        IRI fullIRI = IRI(ns, "FULL");
        IRI dlIRI = IRI(ns, "DL");
        IRI elIRI = IRI(ns, "EL");
        IRI qlIRI = IRI(ns, "QL");
        IRI rlIRI = IRI(ns, "RL");
        IRI premiseIRI = IRI(ns, "rdfXmlPremiseOntology");
        URL resourceURL = ProfileValidationTestCase.class.getResource("/all.rdf");
        IRI allTestURI = df.getIRI(resourceURL);
        OWLOntology testCasesOntology = m.loadOntologyFromOntologyDocument(allTestURI);
        OWLClass profileIdentificationTestClass = Class(profile);
        OWLNamedIndividual el = df.getOWLNamedIndividual(elIRI);
        OWLNamedIndividual ql = df.getOWLNamedIndividual(qlIRI);
        OWLNamedIndividual rl = df.getOWLNamedIndividual(rlIRI);
        OWLObjectProperty speciesProperty = df.getOWLObjectProperty(species);
        OWLNamedIndividual full = df.getOWLNamedIndividual(fullIRI);
        OWLNamedIndividual dl = df.getOWLNamedIndividual(dlIRI);
        OWLDataProperty rdfXMLPremiseOntologyProperty = df.getOWLDataProperty(premiseIRI);
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
            OWLOntology ontology =
                loadOntologyFromString(ontologySerialisation, new RDFXMLDocumentFormat());
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
    public void shouldNotFailELBecauseOfBoolean() {
        OWLOntology o = getOWLOntology();
        OWLAnnotation ann = df.getRDFSLabel(df.getOWLLiteral(true));
        OWLAnnotationAssertionAxiom ax =
            df.getOWLAnnotationAssertionAxiom(df.getIRI("urn:test#", "ELProfile"), ann);
        o.add(ax, Declaration(OWL2Datatype.XSD_BOOLEAN.getDatatype(df)));
        checkProfile(o, new OWL2ELProfile(), true);
    }
}
