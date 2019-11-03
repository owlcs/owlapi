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
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;

public class FunctionalSyntaxIRIProblemTestCase extends TestBase {

    private static final String ex = "example";
    private static final String NSroma = "http://www.dis.uniroma1.it/example/";
    private static final String NS = "http://example.org/";

    @Test
    public void testmain() throws Exception {
        OWLOntology ontology = getOWLOntology();
        OWLObjectProperty p = df.getOWLObjectProperty("http://example.org/A_#", "part_of");
        OWLClass a = Class(IRI(NS, "A_A"));
        OWLClass b = Class(IRI(NS, "A_B"));
        ontology.add(Declaration(p), Declaration(a), Declaration(b),
            SubClassOf(b, df.getOWLObjectSomeValuesFrom(p, a)));
        OWLOntology loadOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        FunctionalSyntaxDocumentFormat functionalFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().withPrefix(ex, NS);
        OWLOntology loadOntology2 = roundTrip(ontology, functionalFormat);
        // won't reach here if functional syntax fails - comment it out and
        // uncomment this to test Manchester
        ManchesterSyntaxDocumentFormat manchesterFormat = new ManchesterSyntaxDocumentFormat();
        ontology.getPrefixManager().withPrefix(ex, NS);
        OWLOntology loadOntology3 = roundTrip(ontology, manchesterFormat);
        assertEquals(ontology, loadOntology);
        assertEquals(ontology, loadOntology2);
        assertEquals(ontology, loadOntology3);
        assertTrue(ontology.equalAxioms(loadOntology));
        assertTrue(ontology.equalAxioms(loadOntology2));
        assertTrue(ontology.equalAxioms(loadOntology3));
    }

    @Test
    public void shouldRespectDefaultPrefix()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = m.createOntology(df.getIRI(NSroma));
        PrefixManager pm = new PrefixManagerImpl().withPrefix(ex, NSroma);
        OWLClass pizza = df.getOWLClass("example:pizza", pm);
        OWLDeclarationAxiom declarationAxiom = df.getOWLDeclarationAxiom(pizza);
        ontology.addAxiom(declarationAxiom);
        FunctionalSyntaxDocumentFormat ontoFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().copyPrefixesFrom(pm);
        m.setOntologyFormat(ontology, ontoFormat);
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        ontology.saveOntology(documentTarget);
        assertTrue(documentTarget.toString().contains("example:pizza"));
    }

    @Test
    public void shouldConvertToFunctionalCorrectly() throws OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(TestFiles.convertToFunctional,
            new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(saveOntology(o, new FunctionalSyntaxDocumentFormat()),
                new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
    }

    @Test
    public void shouldPreservePrefix()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String prefix = "http://www.dis.uniroma1.it/pizza";
        OWLOntology ontology = m.createOntology(df.getIRI(prefix));
        PrefixManager pm = new PrefixManagerImpl().withPrefix("pizza", prefix);
        OWLClass pizza = df.getOWLClass("pizza:PizzaBase", pm);
        assertEquals(prefix + "PizzaBase", pizza.getIRI().toString());
        OWLDeclarationAxiom declarationAxiom = df.getOWLDeclarationAxiom(pizza);
        ontology.addAxiom(declarationAxiom);
        FunctionalSyntaxDocumentFormat ontoFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().withPrefix("pizza", prefix);
        m.setOntologyFormat(ontology, ontoFormat);
        OWLOntologyDocumentTarget stream = new StringDocumentTarget();
        ontology.saveOntology(stream);
        assertTrue(stream.toString().contains("pizza:PizzaBase"));
    }

    @Test
    public void shouldRoundtripIRIsWithQueryString() throws OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(TestFiles.roundtripRIWithQuery, new RDFXMLDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o, new FunctionalSyntaxDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(saveOntology, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
    }
}
