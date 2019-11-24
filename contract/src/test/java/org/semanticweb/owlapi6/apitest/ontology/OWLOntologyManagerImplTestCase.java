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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLException;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyManagerImplTestCase extends TestBase {

    @Test
    public void testContains() throws OWLOntologyCreationException {
        OWLOntology ont = m.createOntology(nextOntology());
        assertTrue(m.contains(ont.getOntologyID()));
        assertNotNull(m.getOntology(ont.getOntologyID()));
        assertTrue(contains(m.ontologies(), ont));
        assertNotNull(m.getOntologyDocumentIRI(ont));
        m.removeOntology(ont);
        assertFalse(m.contains(ont.getOntologyID()));
    }

    protected IRI nextOntology() {
        return df.getNextDocumentIRI("urn:testontology");
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntology ontA = m.createOntology(nextOntology());
        OWLOntology ontB = m.createOntology(nextOntology());
        OWLImportsDeclaration decl = m.getOWLDataFactory()
            .getOWLImportsDeclaration(get(ontB.getOntologyID().getOntologyIRI()));
        ontA.applyChange(new AddImport(ontA, decl));
        assertTrue(contains(m.directImports(ontA), ontB));
        m.removeOntology(ontB);
        assertFalse(contains(m.directImports(ontA), ontB));
    }

    @Test
    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = m.createOntology(nextOntology());
        OWLOntology ontB = m.createOntology(nextOntology());
        OWLOntology ontC = m.createOntology(nextOntology());
        OWLImportsDeclaration declA = m.getOWLDataFactory()
            .getOWLImportsDeclaration(get(ontB.getOntologyID().getOntologyIRI()));
        OWLImportsDeclaration declB = m.getOWLDataFactory()
            .getOWLImportsDeclaration(get(ontC.getOntologyID().getOntologyIRI()));
        ontA.applyChanges(new AddImport(ontA, declA), new AddImport(ontB, declB));
        assertTrue(contains(m.importsClosure(ontA), ontA));
        assertTrue(contains(m.importsClosure(ontA), ontB));
        assertTrue(contains(m.importsClosure(ontA), ontC));
        List<OWLOntology> importsClosure = asList(m.importsClosure(ontB));
        assertTrue(importsClosure.contains(ontB));
        assertTrue(importsClosure.contains(ontC));
    }

    @Test
    public void testImportsLoad() throws OWLException {
        OWLOntology ontA = m.createOntology(df.getIRI("urn:test:", "a"));
        assertEquals(0L, ontA.directImports().count());
        IRI b = df.getIRI("urn:test:", "b");
        OWLImportsDeclaration declB = m.getOWLDataFactory().getOWLImportsDeclaration(b);
        ontA.applyChange(new AddImport(ontA, declB));
        Set<IRI> directImportsDocuments = asUnorderedSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        OWLOntology ontB = m.createOntology(b);
        directImportsDocuments = asUnorderedSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        assertEquals(1, ontA.directImports().count());
        assertTrue(contains(ontA.directImports(), ontB));
    }
}
