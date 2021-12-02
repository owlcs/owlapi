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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

class SWRLRoundTripTestCase extends TestBase {

    static final String DATATYPE_DEFINITION = "datatype definition";
    static final String NS = "urn:test";
    static final Set<OWLAnnotation> LABEL_DATATYPE_DEFINITION =
        singleton(df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(DATATYPE_DEFINITION)));
    static final SWRLVariable vy = df.getSWRLVariable(iri(NS + "#", "Y"));
    static final SWRLVariable vx = df.getSWRLVariable(iri(NS + "#", "X"));

    @Test
    void shouldDoCompleteRoundtrip() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(DP, vx, vy));
        body.add(df.getSWRLDataRangeAtom(df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(A, vx));
        SWRLRule rule = df.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        ontology = roundTrip(ontology, new OWLXMLDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology, new OWLXMLDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    void shouldDoCompleteRoundtripManchesterOWLSyntax() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(DP, vx, vy));
        body.add(df.getSWRLDataRangeAtom(df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(A, vx));
        SWRLRule rule = df.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        ontology = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    void shouldDoCompleteRoundtripWithAnnotationsOWLXML() {
        OWLOntology ontology = prepareOntology();
        OWLXMLDocumentFormat format = new OWLXMLDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, format));
        equal(ontology, ontology2);
        assertions(ontology2);
    }

    protected void assertions(OWLOntology ontology2) {
        ontology2.getAxioms(AxiomType.SWRL_RULE).forEach(r -> assertFalse(noLabel(r)));
        ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION).forEach(r -> assertFalse(noLabel(r)));
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
        ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION).forEach(r -> assertFalse(noLabel(r)));
    }

    protected boolean noLabel(OWLAxiom r) {
        return r.getAnnotations(df.getRDFSLabel()).isEmpty();
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
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, format), format);
        equal(ontology, ontology2);
        return ontology2;
    }

    /**
     * @return
     * @throws OWLOntologyCreationException
     */
    @Nonnull
    OWLOntology prepareOntology() {
        OWLOntology ontology = create(iri(NS, ""));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(DP, vx, vy));
        body.add(df.getSWRLDataRangeAtom(df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), vy));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(A, vx));
        OWLAnnotation ann = df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("test", ""));
        SWRLRule rule = df.getSWRLRule(body, head, singleton(ann));
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        OWLDatatypeDefinitionAxiom def =
            df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype(iri("urn:my#", "datatype")),
                df.getOWLDatatypeMaxExclusiveRestriction(200D), singleton(
                    df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(DATATYPE_DEFINITION))));
        ontology.getOWLOntologyManager().addAxiom(ontology, def);
        return ontology;
    }

    @Nonnull
    OWLOntology prepareOntology1() {
        OWLOntology ontology = create(iri(NS, ""));
        OWLDatatypeDefinitionAxiom def =
            df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype(iri("urn:my#", "datatype")),
                df.getOWLDatatypeMaxExclusiveRestriction(200D), singleton(
                    df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(DATATYPE_DEFINITION))));
        ontology.getOWLOntologyManager().addAxiom(ontology, def);
        return ontology;
    }

    @Test
    void shouldParse() {
        OWLOntology o = loadOntologyFromString(TestFiles.parseSWRL, new RDFXMLDocumentFormat());
        OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
            df.getOWLDatatype(iri("urn:my#", "datatype")), df.getDoubleOWLDatatype(), singleton(
                df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(DATATYPE_DEFINITION, ""))));
        System.out.println("SWRLRoundTripTestCase.shouldParse() \n" + o + "\n\n" + def);
        assertTrue(o.getAxioms().contains(def));
    }

    @Test
    void shouldParse2() {
        OWLOntology o = loadOntologyFromString(TestFiles.parseSWRL2, new RDFXMLDocumentFormat());
        OWLSubClassOfAxiom def = df.getOWLSubClassOfAxiom(
            df.getOWLClass(iri("urn:test#", "myClass")), df.getOWLClass(iri("urn:test#", "test")),
            singleton(
                df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(DATATYPE_DEFINITION, ""))));
        assertTrue(o.getAxioms().contains(def));
    }
}
