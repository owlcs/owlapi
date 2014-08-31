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
package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class DisjointClassesRoundTripTestCase extends TestBase {

    @Nonnull
    private static final String NS = "http://ns.owl";

    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        OWLOntology ontology = buildOntology();
        String input = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n Prefix: piz: <http://ns.owl#>\n Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n\n Ontology: <http://ns.owl>\n"
                + " Class: piz:F\n Class: piz:E\n Class: piz:D\n Class: piz:C\n DisjointClasses: \n ( piz:D or piz:C),\n (piz:E or piz:C),\n (piz:F or piz:C)";
        OWLOntology roundtripped = loadOntologyFromString(input);
        assertEquals(input, ontology.getLogicalAxioms(),
                roundtripped.getLogicalAxioms());
    }

    @Test
    public void shouldRoundTrip() throws Exception {
        OWLOntology ontology = buildOntology();
        PrefixDocumentFormat format = new ManchesterSyntaxDocumentFormat();
        format.setPrefix("piz", NS + '#');
        OWLOntology roundtripped = roundTrip(ontology, format);
        assertEquals(ontology.getLogicalAxioms(),
                roundtripped.getLogicalAxioms());
    }

    @Nonnull
    private OWLOntology buildOntology() throws OWLOntologyCreationException {
        OWLClass c = Class(IRI(NS + "#C"));
        OWLClass d = Class(IRI(NS + "#D"));
        OWLClass e = Class(IRI(NS + "#E"));
        OWLClass f = Class(IRI(NS + "#F"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        OWLDisjointClassesAxiom disjointClasses = DisjointClasses(
                ObjectUnionOf(c, d), ObjectUnionOf(c, e), ObjectUnionOf(c, f));
        m.addAxiom(ontology, disjointClasses);
        return ontology;
    }
}
