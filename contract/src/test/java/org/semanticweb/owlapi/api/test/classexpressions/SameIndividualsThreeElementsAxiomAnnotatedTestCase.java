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
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.api.test.baseclasses.AbstractAnnotatedAxiomRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormatFactory;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TrigDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class SameIndividualsThreeElementsAxiomAnnotatedTestCase
    extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override
    protected OWLAxiom getMainAxiom(@Nonnull Set<OWLAnnotation> annos) {
        Set<OWLNamedIndividual> asList = new HashSet<>(Arrays.asList(NamedIndividual(iri("A")),
            NamedIndividual(iri("B")), NamedIndividual(iri("C"))));
        return df.getOWLSameIndividualAxiom(asList, annos);
    }

    @Override
    public void testRDFXML() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new RDFXMLDocumentFormatFactory());
    }

    @Override
    public void testRioRDFXML() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioRDFXMLDocumentFormatFactory());
    }

    @Override
    public void testTrig() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new TrigDocumentFormatFactory());
    }

    @Override
    public void testRDFJSON() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new RDFJsonDocumentFormatFactory());
    }

    @Override
    public void testNTriples() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new NTriplesDocumentFormatFactory());
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        // super.roundTripRDFXMLAndFunctionalShouldBeSame();
    }

    @Override
    public void testJSONLD() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new RDFJsonDocumentFormatFactory());
    }

    @Override
    public void testNQuads() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new NQuadsDocumentFormatFactory());
    }

    @Override
    public void testTurtle() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(getOnt(), new TurtleDocumentFormatFactory());
    }

    @Override
    public void testRioTurtle() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioTurtleDocumentFormatFactory());
    }

    protected void sameIndividualAssertion(OWLOntology ont, OWLDocumentFormatFactory f)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(saveOntology(ont, f.createFormat()), f.createFormat());
        assertEquals(2, o.getAxiomCount(AxiomType.SAME_INDIVIDUAL));
        Set<OWLSameIndividualAxiom> axioms = ont.getAxioms(AxiomType.SAME_INDIVIDUAL);
        axioms.stream().map(OWLSameIndividualAxiom::splitToAnnotatedPairs)
            .forEach(axs -> axs.forEach(a -> assertTrue(o.containsAxiom(a))));
    }
}
