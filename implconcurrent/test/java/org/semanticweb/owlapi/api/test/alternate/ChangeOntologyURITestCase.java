package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
 * Date: 25-May-2007<br>
 * <br>
 */
public class ChangeOntologyURITestCase extends AbstractOWLAPITestCase {
	public void testChangeURI() throws Exception {
		OWLOntologyManager man = getManager();
		IRI oldIRI = IRI.create("http://www.semanticweb.org/ontologies/ontA");
		IRI newIRI = IRI.create("http://www.semanticweb.org/ontologies/ontB");
		OWLOntology ont = man.createOntology(oldIRI);
		OWLOntology importingOnt = man.createOntology(IRI
				.create("http://www.semanticweb.org/ontologies/ontC"));
		man.applyChange(new AddImport(importingOnt, man.getOWLDataFactory()
				.getOWLImportsDeclaration(ont.getOntologyID().getOntologyIRI())));
		assertTrue(man.contains(oldIRI));
		OWLOntologyURIChanger changer = new OWLOntologyURIChanger(man);
		man.applyChanges(changer.getChanges(ont, newIRI));
		assertFalse(man.contains(oldIRI));
		assertTrue(man.contains(newIRI));
		assertTrue(man.getOntologies().contains(ont));
		assertTrue(man.getDirectImports(importingOnt).contains(ont));
		assertNotNull(man.getOntology(newIRI));
		assertEquals(man.getOntology(newIRI), ont);
		assertEquals(man.getOntology(newIRI).getOntologyID().getOntologyIRI(),
				newIRI);
		assertTrue(man.getImportsClosure(importingOnt).contains(ont));
		assertNotNull(man.getOntologyDocumentIRI(ont));
		// Document IRI will still be the same (in this case the old ont URI)
		assertEquals(man.getOntologyDocumentIRI(ont), oldIRI);
		assertNotNull(man.getOntologyFormat(ont));
	}
}
