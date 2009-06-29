package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 31-Jul-2007<br><br>
 */
public class OWLImportsClosureTestCase extends AbstractOWLTestCase {

    /**
     * Tests to see if the method which obtains the imports closure behaves correctly.
     */
    public void testImportsClosure() throws Exception {
        OWLOntology ontA = getOWLOntologyManager().createOntology(TestUtils.createIRI());
        OWLOntology ontB = getOWLOntologyManager().createOntology(TestUtils.createIRI());
        assertTrue(getOWLOntologyManager().getImportsClosure(ontA).contains(ontA));
        OWLImportsDeclaration importsDeclaration = getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        getOWLOntologyManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(getOWLOntologyManager().getImportsClosure(ontA).contains(ontB));
        getOWLOntologyManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertFalse(getOWLOntologyManager().getImportsClosure(ontA).contains(ontB));
        getOWLOntologyManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(getOWLOntologyManager().getImportsClosure(ontA).contains(ontB));
        getOWLOntologyManager().removeOntology(ontB);
        assertFalse(getOWLOntologyManager().getImportsClosure(ontA).contains(ontB));

    }
}
