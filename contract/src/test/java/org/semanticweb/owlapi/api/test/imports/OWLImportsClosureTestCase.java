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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ImportsDeclaration;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.RemoveImport;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class OWLImportsClosureTestCase extends TestBase {

    /**
     * Tests to see if the method which obtains the imports closure behaves
     * correctly.
     * 
     * @throws OWLOntologyCreationException
     *         exception
     */
    @Test
    public void testImportsClosure() throws OWLOntologyCreationException {
        OWLOntology ontA = m.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontB = m.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        assertTrue(m.getImportsClosure(ontA).contains(ontA));
        OWLImportsDeclaration importsDeclaration = ImportsDeclaration(ontB.getOntologyID().getOntologyIRI().get());
        m.applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(m.getImportsClosure(ontA).contains(ontB));
        m.applyChange(new RemoveImport(ontA, importsDeclaration));
        assertFalse(m.getImportsClosure(ontA).contains(ontB));
        m.applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(m.getImportsClosure(ontA).contains(ontB));
        m.removeOntology(ontB);
        assertFalse(m.getImportsClosure(ontA).contains(ontB));
    }
}
