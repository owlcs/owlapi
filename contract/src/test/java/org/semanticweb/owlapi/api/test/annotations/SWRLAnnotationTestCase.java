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

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings({ "javadoc", "null" })
public class SWRLAnnotationTestCase extends TestBase {

    @Nonnull
    private static final String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    @Nonnull
    OWLClass a = Class(IRI(NS + "#A"));
    @Nonnull
    OWLClass b = Class(IRI(NS + "#B"));
    @Nonnull
    OWLAxiom axiom;

    @Before
    public void setUpAtoms() {
        SWRLVariable x = df.getSWRLVariable(NS + "#x");
        SWRLAtom atom1 = df.getSWRLClassAtom(a, x);
        SWRLAtom atom2 = df.getSWRLClassAtom(b, x);
        Set<SWRLAtom> consequent = new TreeSet<>();
        consequent.add(atom1);
        OWLAnnotation annotation = df.getOWLAnnotation(RDFSComment(),
            Literal("Not a great rule"));
        Set<OWLAnnotation> annotations = new TreeSet<>();
        annotations.add(annotation);
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(atom2);
        axiom = df.getSWRLRule(body, consequent, annotations);
    }

    @Test
    public void shouldRoundTripAnnotation() throws Exception {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAxiom(axiom));
        StringDocumentTarget saveOntology = saveOntology(ontology);
        ontology = loadOntologyFromString(saveOntology);
        assertTrue(ontology.containsAxiom(axiom));
    }

    public OWLOntology createOntology() {
        OWLOntology ontology = getOWLOntology();
        ontology.addAxiom(axiom);
        return ontology;
    }

    @Test
    public void replicateFailure() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF\n"
            + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "    xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n"
            + "    xmlns=\"urn:test#\"\n"
            + "    xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n"
            + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "    xmlns:sqwrl=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#\"\n"
            + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
            + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
            + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "    xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\"\n"
            + "  xml:base=\"urn:test\">\n" + "  <owl:Ontology rdf:about=\"\">\n"
            + "  </owl:Ontology>\n"
            + "  <owl:AnnotationProperty rdf:about=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled\"/>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasDriver\">\n"
            + "    <owl:inverseOf>\n"
            + "      <owl:ObjectProperty rdf:ID=\"drives\"/>\n"
            + "    </owl:inverseOf>\n" + "  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:about=\"#drives\">\n"
            + "    <owl:inverseOf rdf:resource=\"#hasDriver\"/>\n"
            + "  </owl:ObjectProperty>\n"
            + "  <swrl:Imp rdf:ID=\"test-table5-prp-inv2-rule\">\n"
            + "    <swrl:body>\n" + "      <swrl:AtomList/>\n"
            + "    </swrl:body>\n" + "    <swrl:head>\n"
            + "      <swrl:AtomList>\n"
            + "        <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "        <rdf:first>\n"
            + "          <swrl:IndividualPropertyAtom>\n"
            + "            <swrl:argument2>\n"
            + "              <Person rdf:ID=\"i62\"/>\n"
            + "            </swrl:argument2>\n"
            + "            <swrl:argument1>\n"
            + "              <Person rdf:ID=\"i61\"/>\n"
            + "            </swrl:argument1>\n"
            + "            <swrl:propertyPredicate rdf:resource=\"#drives\"/>\n"
            + "          </swrl:IndividualPropertyAtom>\n"
            + "        </rdf:first>\n" + "      </swrl:AtomList>\n"
            + "    </swrl:head>\n"
            + "    <swrla:isRuleEnabled rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n"
            + "    >true</swrla:isRuleEnabled>\n"
            + "    <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n"
            + "    >:i62, :i61</rdfs:comment>\n" + "  </swrl:Imp>\n"
            + "</rdf:RDF>";
        OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
            input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE)
            .anyMatch(ax -> ax.toString().contains(
                "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )")));
    }

    @Test
    public void replicateSuccess() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF\n"
            + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "    xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n"
            + "    xmlns=\"urn:test#\"\n"
            + "    xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n"
            + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "    xmlns:sqwrl=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#\"\n"
            + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
            + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
            + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "    xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\"\n"
            + "  xml:base=\"urn:test\">\n" + "  <owl:Ontology rdf:about=\"\">\n"
            + "  </owl:Ontology>\n"
            + "  <owl:AnnotationProperty rdf:about=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled\"/>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasDriver\">\n"
            + "    <owl:inverseOf>\n"
            + "      <owl:ObjectProperty rdf:ID=\"drives\"/>\n"
            + "    </owl:inverseOf>\n" + "  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:about=\"#drives\">\n"
            + "    <owl:inverseOf rdf:resource=\"#hasDriver\"/>\n"
            + "  </owl:ObjectProperty>\n" + "  <swrl:Imp>\n"
            + "    <swrl:body>\n" + "      <swrl:AtomList/>\n"
            + "    </swrl:body>\n" + "    <swrl:head>\n"
            + "      <swrl:AtomList>\n"
            + "        <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "        <rdf:first>\n"
            + "          <swrl:IndividualPropertyAtom>\n"
            + "            <swrl:argument2>\n"
            + "              <Person rdf:ID=\"i62\"/>\n"
            + "            </swrl:argument2>\n"
            + "            <swrl:argument1>\n"
            + "              <Person rdf:ID=\"i61\"/>\n"
            + "            </swrl:argument1>\n"
            + "            <swrl:propertyPredicate rdf:resource=\"#drives\"/>\n"
            + "          </swrl:IndividualPropertyAtom>\n"
            + "        </rdf:first>\n" + "      </swrl:AtomList>\n"
            + "    </swrl:head>\n"
            + "    <swrla:isRuleEnabled rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</swrla:isRuleEnabled>\n"
            + "    <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">:i62, :i61</rdfs:comment>\n"
            + "  </swrl:Imp>\n" + "</rdf:RDF>";
        OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
            input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE)
            .anyMatch(ax -> ax.toString().contains(
                "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )")));
    }
}
