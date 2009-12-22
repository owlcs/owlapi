package org.semanticweb.owlapi.model;

import junit.framework.TestCase;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 02-Jan-2007<br><br>
 */
public class OWLOntologyManagerImplTestCase extends TestCase {

    private OWLOntologyManager manager;


    protected void setUp() throws Exception {
        super.setUp();
        manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        manager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        manager.addIRIMapper(new NonMappingOntologyIRIMapper());
    }

    public void testContains() throws Exception {
        OWLOntology ont = manager.createOntology(TestUtils.createIRI());
        assertTrue(manager.contains(ont.getOntologyID()));
        assertNotNull(manager.getOntology(ont.getOntologyID()));
        assertTrue(manager.getOntologies().contains(ont));
        assertNotNull(manager.getOntologyDocumentIRI(ont));
        manager.removeOntology(ont);
        assertFalse(manager.contains(ont.getOntologyID()));
    }

    public void testImports() throws Exception {
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration decl = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, decl));
        assertTrue(manager.getDirectImports(ontA).contains(ontB));
        manager.removeOntology(ontB);
        assertFalse(manager.getDirectImports(ontA).contains(ontB));
    }

    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontC = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration declA = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        OWLImportsDeclaration declB = manager.getOWLDataFactory().getOWLImportsDeclaration(ontC.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, declA));
        manager.applyChange(new AddImport(ontB, declB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontA));
        assertTrue(manager.getImportsClosure(ontA).contains(ontB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontC));
        assertTrue(manager.getImportsClosure(ontB).contains(ontB));
        assertTrue(manager.getImportsClosure(ontB).contains(ontC));
    }

}
