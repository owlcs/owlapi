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
package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.1
 */
@SuppressWarnings("javadoc")
public class SWRLTestCase extends TestBase {

    @Test
    public void testSWRLParser() {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF\n"
                        + "    xmlns:temporal=\"http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#\"\n"
                        + "    xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\"\n"
                        + "    xmlns:query=\"http://swrl.stanford.edu/ontologies/built-ins/3.3/query.owl#\"\n"
                        + "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
                        + "    xmlns:swrlx=\"http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#\"\n"
                        + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                        + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                        + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
                        + "    xmlns=\"http://www.owl-ontologies.com/Ontology1186164271.owl#\"\n"
                        + "    xmlns:abox=\"http://swrl.stanford.edu/ontologies/built-ins/3.3/abox.owl#\"\n"
                        + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                        + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                        + "    xmlns:tbox=\"http://swrl.stanford.edu/ontologies/built-ins/3.3/tbox.owl#\"\n"
                        + "  xml:base=\"http://www.owl-ontologies.com/Ontology1186164271.owl\">\n"
                        + "  <owl:Ontology rdf:about=\"\">\n" + "  </owl:Ontology>\n"
                        + "  <owl:Class rdf:ID=\"A\"/>\n" + "  <owl:Class rdf:ID=\"B\"/>\n"
                        + "  <owl:ObjectProperty rdf:ID=\"hasPart\"/>\n"
                        + "  <owl:ObjectProperty rdf:about=\"http://www.w3.org/2003/11/swrl#argument2\"/>\n"
                        + "  <swrl:Variable rdf:ID=\"z\"/>\n" + "  <swrl:Variable rdf:ID=\"y\"/>\n"
                        + "  <swrl:Imp rdf:ID=\"Rule-1\">\n" + "    <swrl:head>\n"
                        + "      <swrl:AtomList>\n" + "        <rdf:first>\n"
                        + "          <swrl:IndividualPropertyAtom>\n"
                        + "            <swrl:argument1>\n"
                        + "              <swrl:Variable rdf:ID=\"x\"/>\n"
                        + "            </swrl:argument1>\n"
                        + "            <swrl:propertyPredicate rdf:resource=\"#hasPart\"/>\n"
                        + "            <swrl:argument2 rdf:resource=\"#z\"/>\n"
                        + "          </swrl:IndividualPropertyAtom>\n" + "        </rdf:first>\n"
                        + "        <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
                        + "      </swrl:AtomList>\n" + "    </swrl:head>\n" + "    <swrl:body>\n"
                        + "      <swrl:AtomList>\n" + "        <rdf:first>\n"
                        + "          <swrl:IndividualPropertyAtom>\n"
                        + "            <swrl:argument2 rdf:resource=\"#y\"/>\n"
                        + "            <swrl:argument1 rdf:resource=\"#x\"/>\n"
                        + "            <swrl:propertyPredicate rdf:resource=\"#hasPart\"/>\n"
                        + "          </swrl:IndividualPropertyAtom>\n" + "        </rdf:first>\n"
                        + "        <rdf:rest>\n" + "          <swrl:AtomList>\n"
                        + "            <rdf:first>\n"
                        + "              <swrl:IndividualPropertyAtom>\n"
                        + "                <swrl:argument2 rdf:resource=\"#z\"/>\n"
                        + "                <swrl:argument1 rdf:resource=\"#y\"/>\n"
                        + "                <swrl:propertyPredicate rdf:resource=\"#hasPart\"/>\n"
                        + "              </swrl:IndividualPropertyAtom>\n"
                        + "            </rdf:first>\n"
                        + "            <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
                        + "          </swrl:AtomList>\n" + "        </rdf:rest>\n"
                        + "      </swrl:AtomList>\n" + "    </swrl:body>\n" + "  </swrl:Imp>\n"
                        + "</rdf:RDF>";
        OWLOntology ont = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        assertTrue(ont.individualsInSignature().count() == 0);
    }
}
