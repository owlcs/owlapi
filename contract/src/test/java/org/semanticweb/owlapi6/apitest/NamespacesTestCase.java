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
package org.semanticweb.owlapi6.apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.Namespaces;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.XSDVocabulary;

public class NamespacesTestCase extends TestBase {

    private static final String NS = "http://test.owl/test#";

    @Test
    public void shouldFindInNamespace() {
        EnumSet<Namespaces> reserved =
            EnumSet.of(Namespaces.OWL, Namespaces.RDF, Namespaces.RDFS, Namespaces.XSD);
        for (Namespaces n : Namespaces.values()) {
            IRI iri = df.getIRI(n.getPrefixIRI(), "test");
            boolean reservedVocabulary = iri.isReservedVocabulary();
            assertEquals(Boolean.valueOf(reserved.contains(n)),
                Boolean.valueOf(reservedVocabulary));
        }
    }

    @Test
    public void shouldParseXSDSTRING() {
        // given
        String s = "xsd:string";
        // when
        XSDVocabulary v = XSDVocabulary.parseShortName(s);
        // then
        assertEquals(XSDVocabulary.STRING, v);
        assertEquals(OWL2Datatype.XSD_STRING.getDatatype(df), df.getOWLDatatype(v));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToParseInvalidString() {
        // given
        String s = "xsd:st";
        // when
        XSDVocabulary.parseShortName(s);
        // then
        // an exception should have been thrown
    }

    @Test
    public void shouldSetPrefix() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLClass item = df.getOWLClass(NS, "item");
        OWLDeclarationAxiom declaration = df.getOWLDeclarationAxiom(item);
        OWLOntology o1 = m.createOntology();
        FunctionalSyntaxDocumentFormat pm1 = new FunctionalSyntaxDocumentFormat();
        o1.getPrefixManager().withPrefix(":", NS);
        m.setOntologyFormat(o1, pm1);
        o1.addAxiom(declaration);
        StringDocumentTarget t1 = new StringDocumentTarget();
        o1.saveOntology(t1);
        OWLOntology o2 = m1.createOntology();
        FunctionalSyntaxDocumentFormat pm2 = new FunctionalSyntaxDocumentFormat();
        o2.getPrefixManager().withPrefix(":", NS);
        o2.addAxiom(declaration);
        StringDocumentTarget t2 = new StringDocumentTarget();
        o1.saveOntology(pm2, t2);
        assertTrue(t2.toString().contains("Declaration(Class(:item))"));
        assertEquals(t1.toString(), t2.toString());
    }
}
