package org.semanticweb.owl.api.test;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyID;
import org.semanticweb.owl.model.SetOntologyURI;

import java.net.URI;
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
        URI uriA = URI.create("http://www.another.com/ont");
        URI uriB = URI.create("http://www.another.com/ont/version");
        OWLOntologyID ontIDBoth = new OWLOntologyID(getFactory().getIRI(uriA),
                getFactory().getIRI(uriB));
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(getFactory().getIRI(uriA),
                getFactory().getIRI(uriB));
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(getFactory().getIRI(uriA));
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    public void testOntologyURI() throws Exception {
        URI uri = URI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(uri);
        assertEquals(ont.getURI(), uri);
        assertTrue(getManager().contains(uri));
        assertTrue(getManager().getOntologies().contains(ont));
        OWLOntologyID ontID = new OWLOntologyID(getFactory().getIRI(uri));
        assertEquals(ont.getOntologyID(), ontID);
    }

    public void testDuplicateOntologyURI() throws Exception {
        URI uri = URI.create("http://www.another.com/ont");
        getManager().createOntology(uri);
        try {
            getManager().createOntology(uri);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }


    public void testSetOntologyURI() throws Exception {
        URI uri = URI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(uri);
        URI newURI = URI.create("http://www.another.com/newont");
        SetOntologyURI sou = new SetOntologyURI(ont, newURI);
        getManager().applyChange(sou);
        assertFalse(getManager().contains(uri));
        assertTrue(getManager().contains(newURI));
        assertEquals(ont.getURI(), newURI);
    }

    public void testVersionURI() throws Exception {
        URI ontURI = URI.create("http://www.another.com/ont");
        URI verURI = URI.create("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getManager().createOntology(ontURI, verURI);
        assertEquals(ont.getURI(), ontURI);
        assertEquals(ont.getIRI().toURI(), ontURI);
        assertEquals(ont.getVersionIRI().toURI(), verURI);
    }

    public void testNullVersionURI() throws Exception {
        URI ontURI = URI.create("http://www.another.com/ont");
        URI verURI = null;
        OWLOntology ont = getManager().createOntology(ontURI, verURI);
        assertEquals(ont.getURI(), ontURI);
        assertEquals(ont.getIRI().toURI(), ontURI);
        assertEquals(ont.getVersionIRI(), null);
    }
}
