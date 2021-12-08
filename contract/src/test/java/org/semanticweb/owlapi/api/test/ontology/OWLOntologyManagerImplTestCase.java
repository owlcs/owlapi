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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
class OWLOntologyManagerImplTestCase extends TestBase {

    @BeforeEach
    void setUpManager() {
        m = new OWLOntologyManagerImpl(df, new ReentrantReadWriteLock());
        m.getOntologyFactories()
            .add(new OWLOntologyFactoryImpl((om, ontID) -> new OWLOntologyImpl(om, ontID)));
    }

    @Test
    void testContains() {
        OWLOntology ont = create(nextOntology());
        assertTrue(m.contains(ont.getOntologyID()));
        assertNotNull(m.getOntology(ont.getOntologyID()));
        assertTrue(contains(m.ontologies(), ont));
        assertNotNull(m.getOntologyDocumentIRI(ont));
        m.removeOntology(ont);
        assertFalse(m.contains(ont.getOntologyID()));
    }

    protected IRI nextOntology() {
        return IRI.getNextDocumentIRI("urn:testontology");
    }

    @Test
    void testImports() {
        OWLOntology ontA = create(nextOntology());
        OWLOntology ontB = create(nextOntology());
        OWLImportsDeclaration decl =
            ImportsDeclaration(ontB.getOntologyID().getOntologyIRI().orElse(null));
        ontA.applyChange(new AddImport(ontA, decl));
        assertTrue(contains(m.directImports(ontA), ontB));
        m.removeOntology(ontB);
        assertFalse(contains(m.directImports(ontA), ontB));
    }

    @Test
    void testImportsClosure() {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = create(nextOntology());
        OWLOntology ontB = create(nextOntology());
        OWLOntology ontC = create(nextOntology());
        OWLImportsDeclaration declA =
            ImportsDeclaration(ontB.getOntologyID().getOntologyIRI().orElse(null));
        OWLImportsDeclaration declB =
            ImportsDeclaration(ontC.getOntologyID().getOntologyIRI().orElse(null));
        ontA.applyChanges(new AddImport(ontA, declA));
        ontB.applyChanges(new AddImport(ontB, declB));
        assertTrue(contains(m.importsClosure(ontA), ontA));
        assertTrue(contains(m.importsClosure(ontA), ontB));
        assertTrue(contains(m.importsClosure(ontA), ontC));
        assertTrue(contains(m.importsClosure(ontB), ontB));
        assertTrue(contains(m.importsClosure(ontB), ontC));
    }

    @Test
    void testImportsLoad() {
        OWLOntology ontA = create(iri("urn:test:", "a"));
        assertEquals(0L, ontA.directImports().count());
        IRI b = iri("urn:test:", "b");
        OWLImportsDeclaration declB = ImportsDeclaration(b);
        ontA.applyChange(new AddImport(ontA, declB));
        Set<IRI> directImportsDocuments = asUnorderedSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        OWLOntology ontB = create(b);
        directImportsDocuments = asUnorderedSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        assertEquals(1, ontA.directImports().count());
        assertTrue(contains(ontA.directImports(), ontB));
    }
}
