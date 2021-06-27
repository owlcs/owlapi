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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class FunctionalSyntaxCommentTestCase extends TestBase {

    static final OWLDataProperty city = DataProperty(IRI("urn:test.owl#", "city"));
    static final OWLClass contactInfo = Class(IRI("urn:test.owl#", "ContactInformation"));
    static final OWLLiteral multiline = Literal("blah \nblah");
    static final String plainOnto =
        "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";

    @Test
    void shouldParseCommentAndSkipIt() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parseComment, new FunctionalSyntaxDocumentFormat());
        OWLAxiom ax1 = Declaration(city);
        OWLAxiom ax2 = SubClassOf(contactInfo,
            DataMaxCardinality(1, city, Datatype(OWL2Datatype.XSD_STRING.getIRI())));
        OWLAxiom ax3 = Declaration(contactInfo);
        assertTrue(o.containsAxiom(ax1));
        assertTrue(o.containsAxiom(ax2));
        assertTrue(o.containsAxiom(ax3));
    }

    @Test
    void shouldSaveMultilineComment() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology(iri("file:test.owl", ""));
        o.addAxiom(df.getOWLAnnotationAssertionAxiom(IRI("urn:test.owl#", "ContactInformation"),
            df.getRDFSLabel(multiline)));
        o.addAxiom(Declaration(city));
        o.addAxiom(SubClassOf(contactInfo,
            DataMaxCardinality(1, city, Datatype(OWL2Datatype.XSD_STRING.getIRI()))));
        o.addAxiom(Declaration(Class(IRI("urn:test.owl#ContactInformation")),
            Collections.singleton(df.getRDFSLabel(multiline))));
        StringDocumentTarget saveOntology = saveOntology(o, new FunctionalSyntaxDocumentFormat());
        assertEquals(TestFiles.parseMultilineComment, saveOntology.toString());
        OWLOntology loadOntologyFromString =
            loadOntologyFromString(saveOntology.toString(), new FunctionalSyntaxDocumentFormat());
        equal(o, loadOntologyFromString);
    }

    @Test
    void shouldParseCardinalityRestrictionWithMoreThanOneDigitRange() {
        OWLOntology o = loadOntologyFromString(TestFiles.cardMultipleDigits,
            new FunctionalSyntaxDocumentFormat());
        assertTrue(o.containsAxiom(df.getOWLSubClassOfAxiom(A,
            df.getOWLDataMinCardinality(257, DPP, OWL2Datatype.RDFS_LITERAL.getDatatype(df)))));
    }

    @Test
    void testConvertGetLoadedOntology() {
        OWLOntology origOnt =
            loadOntologyFromString(plainOnto, new FunctionalSyntaxDocumentFormat());
        assertNotNull(origOnt);
        OWLOntologyManager manager = origOnt.getOWLOntologyManager();
        assertEquals(1, manager.ontologies().count());
        assertFalse(origOnt.getOntologyID().getVersionIRI().isPresent());
        assertTrue(origOnt.getAxiomCount() > 0);
        Optional<IRI> ontologyIRI = origOnt.getOntologyID().getOntologyIRI();
        assertTrue(ontologyIRI.isPresent());
        OWLOntology newOnt = manager.getOntology(get(ontologyIRI));
        assertNotNull(newOnt);
    }
}
