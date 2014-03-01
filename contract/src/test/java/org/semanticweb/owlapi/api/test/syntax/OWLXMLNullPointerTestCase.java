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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics
 *         Group, Date: 01-Jul-2010
 */
@SuppressWarnings("javadoc")
public class OWLXMLNullPointerTestCase extends AbstractOWLAPITestCase {

    private static final String NS = "urn:test";
    public static final String ANONYMOUS_INDIVIDUAL_ANNOTATION = "Anonymous individual for testing";

    @Test
    public void testRoundTrip() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLAnonymousIndividual i = factory.getOWLAnonymousIndividual();
        manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(
                factory.getRDFSLabel(), i,
                factory.getOWLLiteral(ANONYMOUS_INDIVIDUAL_ANNOTATION)));
        manager.addAxiom(
                ontology,
                factory.getOWLClassAssertionAxiom(Class(IRI(NS
                        + "#CheeseyPizza")), i));
        OWLIndividual j = factory.getOWLAnonymousIndividual();
        manager.addAxiom(
                ontology,
                factory.getOWLClassAssertionAxiom(Class(IRI(NS
                        + "#CheeseTopping")), j));
        manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(
                factory.getOWLObjectProperty(IRI(NS + "#hasTopping")), i, j));
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(ontology, new OWLXMLOntologyFormat(), target);
        OWLOntologyManager manager2 = Factory.getManager();
        manager2.loadOntologyFromOntologyDocument(new StringDocumentSource(
                target.toString()));
    }

    @Test
    public void shouldParse() throws Exception {
        OWLOntology o = OWLManager.createOWLOntologyManager().createOntology(
                IRI.create("urn:test"));
        OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
        o.getOWLOntologyManager().addAxiom(
                o,
                df.getOWLSubClassOfAxiom(df.getOWLClass(IRI.create("urn:c")),
                        df.getOWLObjectHasValue(
                                df.getOWLObjectProperty(IRI.create("urn:p")),
                                df.getOWLAnonymousIndividual())));
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, new OWLXMLOntologyFormat(),
                target);
        OWLOntology roundtrip = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(target.toString()));
        assertEquals(o.getAxioms(), roundtrip.getAxioms());
    }
}
