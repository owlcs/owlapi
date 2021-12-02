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

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
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
        m = new OWLOntologyManagerImpl(new OWLDataFactoryImpl(), new ReentrantReadWriteLock());
        m.getOntologyFactories()
            .add(new OWLOntologyFactoryImpl((om, id) -> new OWLOntologyImpl(om, id)));
    }

    @Test
    void testContains() {
        OWLOntology ont = getOWLOntology(nextOntology());
        assertTrue(m.contains(ont.getOntologyID()));
        assertNotNull(m.getOntology(ont.getOntologyID()));
        assertTrue(m.getOntologies().contains(ont));
        assertNotNull(m.getOntologyDocumentIRI(ont));
        m.removeOntology(ont);
        assertFalse(m.contains(ont.getOntologyID()));
    }

    protected IRI nextOntology() {
        return OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology");
    }

    @Test
    void testImports() {
        OWLOntology ontA = getOWLOntology(nextOntology());
        OWLOntology ontB = getOWLOntology(nextOntology());
        OWLImportsDeclaration decl =
            df.getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI().get());
        m.applyChange(new AddImport(ontA, decl));
        assertTrue(m.getDirectImports(ontA).contains(ontB));
        m.removeOntology(ontB);
        assertFalse(m.getDirectImports(ontA).contains(ontB));
    }

    @Test
    void testImportsClosure() {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = getOWLOntology(nextOntology());
        OWLOntology ontB = getOWLOntology(nextOntology());
        OWLOntology ontC = getOWLOntology(nextOntology());
        OWLImportsDeclaration declA =
            df.getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI().get());
        OWLImportsDeclaration declB =
            df.getOWLImportsDeclaration(ontC.getOntologyID().getOntologyIRI().get());
        m.applyChange(new AddImport(ontA, declA));
        m.applyChange(new AddImport(ontB, declB));
        assertTrue(m.getImportsClosure(ontA).contains(ontA));
        assertTrue(m.getImportsClosure(ontA).contains(ontB));
        assertTrue(m.getImportsClosure(ontA).contains(ontC));
        assertTrue(m.getImportsClosure(ontB).contains(ontB));
        assertTrue(m.getImportsClosure(ontB).contains(ontC));
    }

    @Test
    void testImportsLoad() {
        OWLOntology ontA = getOWLOntology(iri("urn:test:", "a"));
        assertEquals(0, ontA.getDirectImports().size());
        IRI b = iri("urn:test:", "b");
        OWLImportsDeclaration declB = df.getOWLImportsDeclaration(b);
        m.applyChange(new AddImport(ontA, declB));
        Set<IRI> directImportsDocuments = ontA.getDirectImportsDocuments();
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        OWLOntology ontB = getOWLOntology(b);
        directImportsDocuments = ontA.getDirectImportsDocuments();
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        assertEquals(1, ontA.getDirectImports().size());
        assertTrue(ontA.getDirectImports().contains(ontB));
    }
}
