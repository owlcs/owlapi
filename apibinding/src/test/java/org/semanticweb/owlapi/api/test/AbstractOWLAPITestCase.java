/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


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


    @Override
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
     * @param ont The ontology to be round tripped.
     * @param format The format to use when doing the round trip.
     */
    public OWLOntology roundTripOntology(OWLOntology ont, OWLOntologyFormat format) throws Exception {
//        try {
        UnparsableOntologyException.setIncludeStackTraceInMessage(true);
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyFormat fromFormat = manager.getOntologyFormat(ont);
        if (fromFormat instanceof PrefixOWLOntologyFormat && format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat fromPrefixFormat = (PrefixOWLOntologyFormat) fromFormat;
            PrefixOWLOntologyFormat toPrefixFormat = (PrefixOWLOntologyFormat) format;
            toPrefixFormat.copyPrefixesFrom(fromPrefixFormat);
        }
        if(format instanceof RDFOntologyFormat) {
            ((RDFOntologyFormat) format).setAddMissingTypes(false);
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
            System.out.println(sb.toString());
            fail(sb.toString());
        }

        assertEquals(ont.getAnnotations(), ont2.getAnnotations());
        return ont2;
    }

    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return true;
    }

    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println("Saved: ");
        System.out.println(target.toString());
        System.out.println("------------------------------------------------------------");
    }
}
