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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.junit.Before;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.anonymous.AnonymousIndividualsNormaliser;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br>
 * <br> */
@SuppressWarnings("javadoc")
public abstract class AbstractOWLAPITestCase {
    public static boolean equal(OWLOntology ont1, OWLOntology ont2) {
        if (!ont1.isAnonymous() && !ont2.isAnonymous()) {
            assertEquals("Ontologies supposed to be the same", ont1.getOntologyID(),
                    ont2.getOntologyID());
        }
        assertEquals("Annotations supposed to be the same", ont1.getAnnotations(),
                ont2.getAnnotations());
        Set<OWLAxiom> axioms1 = ont1.getAxioms();
        Set<OWLAxiom> axioms2 = ont2.getAxioms();
        // This isn't great - we normalise axioms by changing the ids of
        // individuals. This relies on the fact that
        // we iterate over objects in the same order for the same set of axioms!
        OWLDataFactory df = ont1.getOWLOntologyManager().getOWLDataFactory();
        AnonymousIndividualsNormaliser normaliser1 = new AnonymousIndividualsNormaliser(
                df);
        axioms1 = normaliser1.getNormalisedAxioms(axioms1);
        AnonymousIndividualsNormaliser normaliser2 = new AnonymousIndividualsNormaliser(
                df);
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
                    if (!(ax instanceof OWLDeclarationAxiom && ((OWLDeclarationAxiom) ax)
                            .getEntity().isBuiltIn())) {
                        sb.append("Add axiom: ");
                        sb.append(ax);
                        sb.append("\n");
                    }
                }
            }
            System.out
                    .println("AbstractOWLAPITestCase.roundTripOntology() Failing to match axioms: "
                            + sb.toString());
            return false;
        }
        assertEquals(axioms1, axioms2);
        return true;
    }

    private OWLOntologyManager manager;
    private String uriBase;

    public AbstractOWLAPITestCase() {
        manager = Factory.getManager();
        uriBase = "http://www.semanticweb.org/owlapi/test";
    }

    @Before
    public void setUp() {
        manager = Factory.getManager();
    }

    public OWLOntologyManager getManager() {
        return manager;
    }

    public OWLOntology getOWLOntology(String name) {
        try {
            IRI iri = IRI(uriBase + "/" + name);
            if (manager.contains(iri)) {
                return manager.getOntology(iri);
            } else {
                return manager.createOntology(iri);
            }
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public OWLOntology loadOntology(String fileName) {
        try {
            URL url = getClass().getResource("/" + fileName);
            return manager.loadOntologyFromOntologyDocument(IRI.create(url));
        } catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        } catch (URISyntaxException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        }
    }

    public IRI getIRI(String name) {
        return IRI(uriBase + "#" + name);
    }

    public void addAxiom(OWLOntology ont, OWLAxiom ax) {
        manager.addAxiom(ont, ax);
    }

    public void roundTripOntology(OWLOntology ont) throws Exception {
        roundTripOntology(ont, new RDFXMLOntologyFormat());
    }

    /** Saves the specified ontology in the specified format and reloads it.
     * Calling this method from a test will cause the test to fail if the
     * ontology could not be stored, could not be reloaded, or was reloaded and
     * the reloaded version is not equal (in terms of ontology URI and axioms)
     * with the original.
     * 
     * @param ont
     *            The ontology to be round tripped.
     * @param format
     *            The format to use when doing the round trip. */
    public OWLOntology roundTripOntology(OWLOntology ont, OWLOntologyFormat format)
            throws Exception {
        UnparsableOntologyException.setIncludeStackTraceInMessage(true);
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyFormat fromFormat = manager.getOntologyFormat(ont);
        if (fromFormat instanceof PrefixOWLOntologyFormat
                && format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat fromPrefixFormat = (PrefixOWLOntologyFormat) fromFormat;
            PrefixOWLOntologyFormat toPrefixFormat = (PrefixOWLOntologyFormat) format;
            toPrefixFormat.copyPrefixesFrom(fromPrefixFormat);
        }
        if (format instanceof RDFOntologyFormat) {
            ((RDFOntologyFormat) format).setAddMissingTypes(false);
        }
        manager.saveOntology(ont, format, target);
        handleSaved(target, format);
        OWLOntologyManager man = Factory.getManager();
        OWLOntology ont2 = man.loadOntologyFromOntologyDocument(new StringDocumentSource(
                target.toString()));
        equal(ont, ont2);
        return ont2;
    }

    @SuppressWarnings("unused")
    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return true;
    }

    @SuppressWarnings("unused")
    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        // System.out.println(target.toString());
    }
}
