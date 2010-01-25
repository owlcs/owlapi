package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

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

    private IRI uriBase;


    public AbstractOWLAPITestCase() {
        manager = OWLManager.createOWLOntologyManager();
        uriBase = IRI.create("http://www.semanticweb.org/owlapi/test");
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
            IRI iri = IRI.create(uriBase + "/" + name);
            if (manager.contains(iri)) {
                return manager.getOntology(iri);
            }
            else {
                return manager.createOntology(iri);
            }
        }
        catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }


    public OWLOntology loadOntology(String fileName) {
        try {
            URL url = getClass().getResource("/" + fileName);
            return manager.loadOntologyFromOntologyDocument(IRI.create(url));
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
        return getFactory().getOWLClass(IRI.create(uriBase + "#" + name));
    }


    public OWLObjectProperty getOWLObjectProperty(String name) {
        return getFactory().getOWLObjectProperty(IRI.create(uriBase + "#" + name));
    }


    public OWLDataProperty getOWLDataProperty(String name) {
        return getFactory().getOWLDataProperty(IRI.create(uriBase + "#" + name));
    }


    public OWLNamedIndividual getOWLIndividual(String name) {
        return getFactory().getOWLNamedIndividual(IRI.create(uriBase + "#" + name));
    }

    public OWLDatatype getOWLDatatype(String name) {
        return getFactory().getOWLDatatype(IRI.create(uriBase + "#" + name));
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        return getFactory().getOWLAnnotationProperty(IRI.create(uriBase + "#" + name));
    }


    public void addAxiom(OWLOntology ont, OWLAxiom ax) {
        manager.addAxiom(ont, ax);
    }


    public void roundTripOntology(OWLOntology ont) throws Exception {
        roundTripOntology(ont, new RDFXMLOntologyFormat());
    }


    /**
     * Saves the specified ontology in the specified format and reloads it. Calling this method from a test will cause
     * the test to fail if the ontology could not be stored, could not be reloaded, or was reloaded and the reloaded
     * version is not equal (in terms of ontology URI and axioms) with the original.
     *
     * @param ont The ontology to be round tripped.
     * @param format The format to use when doing the round trip.
     */
    public void roundTripOntology(OWLOntology ont, OWLOntologyFormat format) throws Exception {
//        try {
        UnparsableOntologyException.setIncludeStackTraceInMessage(true);
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyFormat fromFormat = manager.getOntologyFormat(ont);
        if (fromFormat instanceof PrefixOWLOntologyFormat && format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat fromPrefixFormat = (PrefixOWLOntologyFormat) fromFormat;
            PrefixOWLOntologyFormat toPrefixFormat = (PrefixOWLOntologyFormat) format;
            toPrefixFormat.copyPrefixesFrom(fromPrefixFormat);
        }
        manager.saveOntology(ont, format, target);
        handleSaved(target, format);
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont2 = man.loadOntologyFromOntologyDocument(new StringDocumentSource(target.toString()));
        if (!ont.isAnonymous() && !ont2.isAnonymous()) {
            assertEquals(ont, ont2);
        }
        Set<OWLAxiom> axioms1;
        Set<OWLAxiom> axioms2;
        if (!isIgnoreDeclarationAxioms(format)) {
            axioms1 = ont.getAxioms();
            axioms2 = ont2.getAxioms();
        }
        else {
            axioms1 = AxiomType.getAxiomsWithoutTypes(ont.getAxioms(), AxiomType.DECLARATION);
            axioms2 = AxiomType.getAxiomsWithoutTypes(ont2.getAxioms(), AxiomType.DECLARATION);
        }
        // This isn't great - we normalise axioms by changing the ids of individuals.  This relies on the fact that
        // we iterate over objects in the same order for the same set of axioms!
        AnonymousIndividualsNormaliser normaliser1 = new AnonymousIndividualsNormaliser(manager.getOWLDataFactory());
        axioms1 = normaliser1.getNormalisedAxioms(axioms1);
        AnonymousIndividualsNormaliser normaliser2 = new AnonymousIndividualsNormaliser(manager.getOWLDataFactory());
        axioms2 = normaliser2.getNormalisedAxioms(axioms2);
        if (!axioms1.equals(axioms2)) {
            StringBuilder sb = new StringBuilder();
            for (OWLAxiom ax : axioms1) {
                if (!axioms2.contains(ax)) {
                    sb.append("Rem axiom: ");
                    sb.append(ax);
                    sb.append("\n");
                }
            }
            for (OWLAxiom ax : axioms2) {
                if (!axioms1.contains(ax)) {
                    sb.append("Add axiom: ");
                    sb.append(ax);
                    sb.append("\n");
                }
            }
            fail(sb.toString());
        }
        // Now for ontology annotations
        assertEquals(ont.getAnnotations(), ont2.getAnnotations());
//        }
//        catch (OWLOntologyStorageException e) {
//            fail(e.getMessage() + " " + e.getStackTrace().toString());
//        }
//        catch (OWLOntologyCreationException e) {
//            fail(e.getMessage() + " " + e.getStackTrace().toString());
//        }
    }

    protected final boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return true;
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println("Saved: ");
        System.out.println(target.toString());
        System.out.println("------------------------------------------------------------");
    }
}
