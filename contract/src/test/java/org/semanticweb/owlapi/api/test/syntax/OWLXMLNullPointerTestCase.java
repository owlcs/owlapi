/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class OWLXMLNullPointerTestCase extends TestBase {

    private static final @Nonnull String NS = "urn:test";
    public static final @Nonnull String ANONYMOUS_INDIVIDUAL_ANNOTATION = "Anonymous individual for testing";

    @Test
    public void testRoundTrip() throws Exception {
        OWLOntology ontology = getOWLOntology(IRI(NS));
        OWLClass cheesy = Class(IRI(NS + "#CheeseyPizza"));
        OWLClass cheese = Class(IRI(NS + "#CheeseTopping"));
        OWLObjectProperty hasTopping = df.getOWLObjectProperty(NS + "#hasTopping");
        OWLAnonymousIndividual i = df.getOWLAnonymousIndividual();
        OWLLiteral lit = df.getOWLLiteral(ANONYMOUS_INDIVIDUAL_ANNOTATION);
        OWLAxiom annAss = df.getOWLAnnotationAssertionAxiom(df.getRDFSLabel(), i, lit);
        OWLAxiom classAss = df.getOWLClassAssertionAxiom(cheesy, i);
        OWLIndividual j = df.getOWLAnonymousIndividual();
        OWLAxiom classAssj = df.getOWLClassAssertionAxiom(cheese, j);
        OWLAxiom objAss = df.getOWLObjectPropertyAssertionAxiom(hasTopping, i, j);
        ontology.add(annAss, classAss, classAssj, objAss);
        roundTrip(ontology, new OWLXMLDocumentFormat());
    }

    @Test
    public void shouldParse() throws Exception {
        OWLOntology o = getOWLOntology();
        OWLClass c = df.getOWLClass("urn:c");
        OWLObjectProperty p = df.getOWLObjectProperty("urn:p");
        OWLAnonymousIndividual i = df.getOWLAnonymousIndividual();
        OWLSubClassOfAxiom sub = df.getOWLSubClassOfAxiom(c, df.getOWLObjectHasValue(p, i));
        o.getOWLOntologyManager().addAxiom(o, sub);
        OWLOntology roundtrip = roundTrip(o, new OWLXMLDocumentFormat());
        equal(o, roundtrip);
    }
}
