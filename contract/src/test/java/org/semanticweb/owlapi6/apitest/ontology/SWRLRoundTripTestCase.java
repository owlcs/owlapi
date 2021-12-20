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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLRule;

class SWRLRoundTripTestCase extends TestBase {

    @Test
    void shouldDoCompleteRoundtrip() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(SWRLDataPropertyAtom(DATAPROPS.DP, SWRL.vx, SWRL.vy));
        body.add(SWRLDataRangeAtom(String(), SWRL.vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(SWRLClassAtom(CLASSES.A, SWRL.vx));
        SWRLRule rule = SWRLRule(body, head);
        ontology.addAxiom(rule);
        ontology = roundTrip(ontology, new OWLXMLDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology, new OWLXMLDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    void shouldDoCompleteRoundtripManchesterOWLSyntax() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(SWRLDataPropertyAtom(DATAPROPS.DP, SWRL.vx, SWRL.vy));
        body.add(SWRLDataRangeAtom(String(), SWRL.vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(SWRLClassAtom(CLASSES.A, SWRL.vx));
        SWRLRule rule = SWRLRule(body, head);
        ontology.addAxiom(rule);
        ontology = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsOWLXML() {
        OWLOntology ontology = prepareOntology();
        OWLXMLDocumentFormat format = new OWLXMLDocumentFormat();
        OWLOntology ontology2 = loadFrom(saveOntology(ontology, format));
        equal(ontology, ontology2);
        assertions(ontology2);
    }

    protected void assertions(OWLOntology ontology2) {
        ontology2.axioms(AxiomType.SWRL_RULE).forEach(r -> assertFalse(noLabel(r)));
        ontology2.axioms(AxiomType.DATATYPE_DEFINITION).forEach(r -> assertFalse(noLabel(r)));
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsTurtle() {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat format = new TurtleDocumentFormat();
        OWLOntology ontology2 = equalRoundtrip(ontology, format);
        assertions(ontology2);
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsFunctional() {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat format = new FunctionalSyntaxDocumentFormat();
        OWLOntology ontology2 = equalRoundtrip(ontology, format);
        assertions(ontology2);
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsRDFXML() {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat format = new RDFXMLDocumentFormat();
        OWLOntology ontology2 = equalRoundtrip(ontology, format);
        assertions(ontology2);
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsDatatypeRDFXML() {
        OWLOntology ontology = prepareOntology1();
        OWLDocumentFormat format = new RDFXMLDocumentFormat();
        OWLOntology ontology2 = equalRoundtrip(ontology, format);
        ontology2.axioms(AxiomType.DATATYPE_DEFINITION).forEach(r -> assertFalse(noLabel(r)));
    }

    protected boolean noLabel(OWLAxiom r) {
        return r.annotations(RDFSLabel()).count() == 0;
    }

    @Disabled("man syntax does not like annotations")
    @Test
    void shouldDoCompleteRoundtripWithAnnotationsMan() {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat format = new ManchesterSyntaxDocumentFormat();
        OWLOntology ontology2 = equalRoundtrip(ontology, format);
        assertions(ontology2);
    }

    protected OWLOntology equalRoundtrip(OWLOntology ontology, OWLDocumentFormat format) {
        OWLOntology ontology2 = loadFrom(saveOntology(ontology, format), format);
        equal(ontology, ontology2);
        return ontology2;
    }

    OWLOntology prepareOntology() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(SWRLDataPropertyAtom(DATAPROPS.DP, SWRL.vx, SWRL.vy));
        body.add(SWRLDataRangeAtom(String(), SWRL.vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(SWRLClassAtom(CLASSES.A, SWRL.vx));
        SWRLRule rule = SWRLRule(RDFSLabel("test"), body, head);
        ontology.addAxiom(rule);
        OWLDatatypeDefinitionAxiom def = DatatypeDefinition(LABEL_DATATYPE_DEFINITION,
            DATATYPES.MY_DATATYPE, DatatypeMaxExclusiveRestriction(200D));
        ontology.addAxiom(def);
        return ontology;
    }

    OWLOntology prepareOntology1() {
        OWLOntology ontology = create(iri(NS, ""));
        OWLDatatypeDefinitionAxiom def = DatatypeDefinition(LABEL_DATATYPE_DEFINITION,
            DATATYPES.MY_DATATYPE, DatatypeMaxExclusiveRestriction(200D));
        ontology.addAxiom(def);
        return ontology;
    }

    @Test
    void shouldParse() {
        OWLOntology o = loadFrom(TestFiles.parseSWRL, new RDFXMLDocumentFormat());
        OWLDatatypeDefinitionAxiom def =
            DatatypeDefinition(LABEL_DATATYPE_DEFINITION, DATATYPES.MY_DATATYPE, Double());
        assertTrue(contains(o.axioms(), def));
    }

    @Test
    void shouldParse2() {
        OWLOntology o = loadFrom(TestFiles.parseSWRL2, new RDFXMLDocumentFormat());
        OWLSubClassOfAxiom def =
            SubClassOf(LABEL_DATATYPE_DEFINITION, Class(iri("myClass")), Class(IRIS.iriTest));
        assertTrue(contains(o.axioms(), def));
    }
}
