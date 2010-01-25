package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.util.OWLOntologyURIChanger;
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
 * Date: 25-May-2007<br><br>
 */
public class ChangeOntologyURITestCase extends AbstractOWLTestCase {

    public void testChangeURI() throws Exception {
        OWLOntologyManager man = getOWLOntologyManager();
        IRI oldURI = IRI.create("http://www.semanticweb.org/ontologies/ontA");
        IRI newURI = IRI.create("http://www.semanticweb.org/ontologies/ontB");
        OWLOntology ont = man.createOntology(oldURI);
        OWLOntology importingOnt = man.createOntology(IRI.create("http://www.semanticweb.org/ontologies/ontC"));
        man.applyChange(new AddImport(importingOnt, man.getOWLDataFactory().getOWLImportsDeclaration(ont.getOntologyID().getOntologyIRI())));
        assertTrue(man.contains(oldURI));
        OWLOntologyURIChanger changer = new OWLOntologyURIChanger(man);
        man.applyChanges(changer.getChanges(ont, newURI));
        assertFalse(man.contains(oldURI));
        assertTrue(man.contains(newURI));
        assertTrue(man.getOntologies().contains(ont));
        assertTrue(man.getDirectImports(importingOnt).contains(ont));
        assertNotNull(man.getOntology(newURI));
        assertEquals(man.getOntology(newURI), ont);
        assertEquals(man.getOntology(newURI).getOntologyID().getOntologyIRI().toURI(), newURI);
        assertTrue(man.getImportsClosure(importingOnt).contains(ont));
        assertNotNull(man.getOntologyDocumentIRI(ont));
        // Document IRI will still be the same (in this case the old ont URI)
        assertEquals(man.getOntologyDocumentIRI(ont), oldURI);
        assertNotNull(man.getOntologyFormat(ont));

    }
}
