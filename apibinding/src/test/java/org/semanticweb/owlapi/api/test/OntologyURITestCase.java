package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 07-Sep-2008<br><br>
 */
public class OntologyURITestCase extends AbstractOWLAPITestCase {

    public void testOntologyID() throws Exception {
        IRI iriA = IRI.create("http://www.another.com/ont");
        IRI iriB = IRI.create("http://www.another.com/ont/version");
        OWLOntologyID ontIDBoth = new OWLOntologyID(iriA, iriB);
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(iriA, iriB);
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(iriA);
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    public void testOntologyURI() throws Exception {
        IRI iri = IRI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        assertEquals(ont.getOntologyID().getOntologyIRI(), iri);
        assertTrue(getManager().contains(iri));
        assertTrue(getManager().getOntologies().contains(ont));
        OWLOntologyID ontID = new OWLOntologyID(iri);
        assertEquals(ont.getOntologyID(), ontID);
    }

    public void testDuplicateOntologyURI() throws Exception {
        IRI uri = IRI.create("http://www.another.com/ont");
        getManager().createOntology(uri);
        try {
            getManager().createOntology(uri);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }


    public void testSetOntologyURI() throws Exception {
        IRI iri = IRI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        IRI newIRI = IRI.create("http://www.another.com/newont");
        SetOntologyID sou = new SetOntologyID(ont, new OWLOntologyID(newIRI));
        getManager().applyChange(sou);
        assertFalse(getManager().contains(iri));
        assertTrue(getManager().contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), newIRI);
    }

    public void testVersionURI() throws Exception {
        IRI ontIRI = IRI.create("http://www.another.com/ont");
        IRI verIRI = IRI.create("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getManager().createOntology(new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }

    public void testNullVersionURI() throws Exception {
        IRI ontIRI = IRI.create("http://www.another.com/ont");
        IRI verIRI = null;
        OWLOntology ont = getManager().createOntology(new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }
}
