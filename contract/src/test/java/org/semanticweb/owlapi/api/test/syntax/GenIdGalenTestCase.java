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
 * Copyright 2011, The University of Manchester
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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

@SuppressWarnings("javadoc")
public class GenIdGalenTestCase {

    @Test
    public void testGenIdGalenFragment() throws Exception {
        String test = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF \n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <owl:Ontology rdf:about=\"http://www.co-ode.org/ontologies/galen\"/>\n"
                + "<owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
                + "<owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#test\">\n"
                + "<rdfs:subClassOf><owl:Restriction>\n"
                + "<owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
                + "<owl:someValuesFrom><owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#anotherTest\"/></owl:someValuesFrom>\n"
                + "</owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        OWLOntologyManager m = Factory.getManager();
        OWLOntology o = m
                .loadOntologyFromOntologyDocument(new StringDocumentSource(test));
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(o);
        assertTrue(report.isInProfile());
    }
}
