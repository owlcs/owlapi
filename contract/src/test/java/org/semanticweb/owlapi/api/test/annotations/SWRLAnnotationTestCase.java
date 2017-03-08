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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

@SuppressWarnings({"javadoc", "null"})
public class SWRLAnnotationTestCase extends TestBase {

    private static final @Nonnull String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    private static final String HEAD = "<?xml version=\"1.0\"?>\n"
                    + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\" xmlns=\"urn:test#\" xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n"
                    + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:sqwrl=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
                    + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\" xml:base=\"urn:test\">\n"
                    + "  <owl:Ontology rdf:about=\"\"></owl:Ontology>\n"
                    + "  <owl:AnnotationProperty rdf:about=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled\"/>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasDriver\"><owl:inverseOf><owl:ObjectProperty rdf:ID=\"drives\"/></owl:inverseOf></owl:ObjectProperty>\n"
                    + "  <owl:ObjectProperty rdf:about=\"#drives\"><owl:inverseOf rdf:resource=\"#hasDriver\"/></owl:ObjectProperty>\n"
                    + "  <swrl:Imp";
    private static final String TAIL = "><swrl:body><swrl:AtomList/></swrl:body>\n"
                    + "    <swrl:head><swrl:AtomList><rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
                    + "        <rdf:first><swrl:IndividualPropertyAtom>\n"
                    + "            <swrl:argument2><Person rdf:ID=\"i62\"/></swrl:argument2>\n"
                    + "            <swrl:argument1><Person rdf:ID=\"i61\"/></swrl:argument1>\n"
                    + "            <swrl:propertyPredicate rdf:resource=\"#drives\"/>\n"
                    + "          </swrl:IndividualPropertyAtom></rdf:first></swrl:AtomList></swrl:head>\n"
                    + "    <swrla:isRuleEnabled rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</swrla:isRuleEnabled>\n"
                    + "    <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">:i62, :i61</rdfs:comment></swrl:Imp>\n"
                    + "</rdf:RDF>";
    protected @Nonnull OWLClass a = Class(IRI(NS + "#", "A"));
    protected @Nonnull OWLClass b = Class(IRI(NS + "#", "B"));
    protected @Nonnull OWLAxiom axiom;

    @Before
    public void setUpAtoms() {
        SWRLVariable x = df.getSWRLVariable(NS + "#", "x");
        SWRLAtom atom1 = df.getSWRLClassAtom(a, x);
        SWRLAtom atom2 = df.getSWRLClassAtom(b, x);
        Set<SWRLAtom> consequent = new TreeSet<>();
        consequent.add(atom1);
        OWLAnnotation annotation = df.getRDFSComment("Not a great rule");
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
        ontology = loadOntologyFromString(saveOntology, ontology.getNonnullFormat());
        assertTrue(ontology.containsAxiom(axiom));
    }

    public OWLOntology createOntology() {
        OWLOntology ontology = getOWLOntology();
        ontology.add(axiom);
        return ontology;
    }

    @Test
    public void replicateFailure() throws Exception {
        String input = HEAD + " rdf:ID=\"test-table5-prp-inv2-rule\"" + TAIL;
        OWLOntology ontology = loadOntologyFromString(
                        new StringDocumentSource(input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE).anyMatch(ax -> ax.toString().contains(
                        "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )")));
    }

    @Test
    public void replicateSuccess() throws Exception {
        String input = HEAD + TAIL;
        OWLOntology ontology = loadOntologyFromString(
                        new StringDocumentSource(input, "test", new RDFXMLDocumentFormat(), null));
        assertTrue(ontology.axioms(AxiomType.SWRL_RULE).anyMatch(ax -> ax.toString().contains(
                        "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )")));
    }
}
