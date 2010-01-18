package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jan-2010
 */
public class ManualImportsTestCase extends AbstractOWLAPITestCase {

    public void testManualImports() throws Exception {
        OWLOntologyManager manager = getManager();
        OWLOntology baseOnt = manager.createOntology(IRI.create("http://semanticweb.org/ontologies/base"));
        IRI importedIRI = IRI.create("http://semanticweb.org/ontologies/imported");
        OWLOntology importedOnt = manager.createOntology(importedIRI);
        Set<OWLOntology> preImportsClosureCache = new HashSet<OWLOntology>(manager.getImportsClosure(baseOnt));
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        manager.applyChange(new AddImport(baseOnt, manager.getOWLDataFactory().getOWLImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = new HashSet<OWLOntology>(manager.getImportsClosure(baseOnt));
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));

    }
}
