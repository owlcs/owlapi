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
package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class FunctionalSyntaxCommentTestCase extends TestBase {

    private static final OWLDataProperty DP = df.getOWLDataProperty(df.getIRI("urn:test#", "dp"));
    private static final OWLClass A = df.getOWLClass(df.getIRI("urn:test#", "A"));
    private static final OWLDataProperty city = DataProperty(IRI("urn:test.owl#", "city"));
    private static final OWLClass contactInfo = Class(IRI("urn:test.owl#", "ContactInformation"));
    private static final OWLLiteral multiline = Literal("blah \nblah");
    public static final String plainOnto =
        "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";

    @Test
    public void shouldParseCommentAndSkipIt() {
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
    public void shouldSaveMultilineComment()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI("file:test.owl"));
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
    public void shouldParseCardinalityRestrictionWithMoreThanOneDigitRange()
        throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.cardMultipleDigits,
                new FunctionalSyntaxDocumentFormat()));
        assertTrue(o.containsAxiom(df.getOWLSubClassOfAxiom(A,
            df.getOWLDataMinCardinality(257, DP, OWL2Datatype.RDFS_LITERAL.getDatatype(df)))));
    }

    @Test
    public void testConvertGetLoadedOntology() {
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
