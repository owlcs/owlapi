package org.semanticweb.owl.api.test;

import junit.framework.TestCase;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.io.StringInputSource;
import org.semanticweb.owl.io.StringOutputTarget;
import org.semanticweb.owl.model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 10-May-2008<br><br>
 */
public abstract class AbstractOWLAPITestCase extends TestCase {

    private OWLOntologyManager manager;

    private URI uriBase;


    public AbstractOWLAPITestCase() {
        manager = OWLManager.createOWLOntologyManager();
        uriBase = URI.create("http://www.semanticweb.org/owlapi/test");
    }


    protected void setUp() throws Exception {
        manager = OWLManager.createOWLOntologyManager();
    }


    public OWLOntologyManager getManager() {
        return manager;
    }


    public OWLDataFactory getFactory() {
        return manager.getOWLDataFactory();
    }


    public OWLOntology getOWLOntology(String name) {
        try {
            URI uri = URI.create(uriBase + "/" + name);
            if (manager.contains(uri)) {
                return manager.getOntology(uri);
            }
            else {
                return manager.createOntology(uri);
            }
        }
        catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }


    public OWLOntology loadOntology(String fileName) {
        try {
            URL url = getClass().getResource("/" + fileName);
            return manager.loadOntologyFromPhysicalURI(url.toURI());
        }
        catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        }
        catch (URISyntaxException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        }
    }


    public OWLClass getOWLClass(String name) {
        return getFactory().getOWLClass(URI.create(uriBase + "#" + name));
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        return getFactory().getOWLObjectProperty(URI.create(uriBase + "#" + name));
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        return getFactory().getOWLDataProperty(URI.create(uriBase + "#" + name));
    }


    public OWLNamedIndividual getOWLIndividual(String name) {
        return getFactory().getOWLNamedIndividual(URI.create(uriBase + "#" + name));
    }

    public OWLDatatype getOWLDatatype(String name) {
        return getFactory().getOWLDatatype(URI.create(uriBase + "#" + name));
    }


    public void addAxiom(OWLOntology ont, OWLAxiom ax) {
        try {
            manager.addAxiom(ont, ax);
        }
        catch (OWLOntologyChangeException e) {
            fail(e.getMessage() + " " + e.getStackTrace().toString());
        }
    }


    public void roundTripOntology(OWLOntology ont) {
        roundTripOntology(ont, new RDFXMLOntologyFormat());
    }


    /**
     * Saves the specified ontology in the specified format and reloads it. Calling this method from a test will cause
     * the test to fail if the ontology could not be stored, could not be reloaded, or was reloaded and the reloaded
     * version is not equal (in terms of ontology URI and axioms) with the original.
     *
     * @param ont    The ontology to be round tripped.
     * @param format The format to use when doing the round trip.
     */
    public void roundTripOntology(OWLOntology ont, OWLOntologyFormat format) {
        try {
            StringOutputTarget target = new StringOutputTarget();
            manager.saveOntology(ont, format, target);
            handleSaved(target, format);
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont2 = man.loadOntology(new StringInputSource(target.toString()));
            assertEquals(ont, ont2);
            Set<OWLAxiom> axioms1 = ont.getAxioms();
            Set<OWLAxiom> axioms2 = ont2.getAxioms();
            if(!axioms1.equals(axioms2)) {
                StringBuilder sb = new StringBuilder();
                for(OWLAxiom ax : axioms1) {
                    if(!axioms2.contains(ax)) {
                        sb.append("Rem axiom: ");
                        sb.append(ax);
                        sb.append("\n");
                    }
                }
                for(OWLAxiom ax : axioms2) {
                    if(!axioms1.contains(ax)) {
                        sb.append("Add axiom: ");
                        sb.append(ax);
                        sb.append("\n");
                    }
                }
                fail(sb.toString());
            }
        }
        catch (OWLOntologyStorageException e) {
            fail(e.getMessage() + " " + e.getStackTrace().toString());
        }
        catch (OWLOntologyCreationException e) {
            fail(e.getMessage() + " " + e.getStackTrace().toString());
        }
    }


    protected void handleSaved(StringOutputTarget target, OWLOntologyFormat format) {
        System.out.println("Saved: ");
        System.out.println(target.toString());
        System.out.println("------------------------------------------------------------");
    }
}
