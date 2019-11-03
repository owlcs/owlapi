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
package org.semanticweb.owlapi6.apitest.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.search.Searcher.getAnnotationObjects;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLPrimitive;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
@RunWith(Parameterized.class)
public class AnnotationAccessorsTestCase extends TestBase {

    @Parameters
    public static Collection<OWLPrimitive> getData() {
        IRI subject = OWLManager.getOWLDataFactory()
            .getIRI("http://owlapi.sourceforge.net/ontologies/test#", "X");
        return Arrays.asList(Class(subject), NamedIndividual(subject), DataProperty(subject),
            ObjectProperty(subject), Datatype(subject), AnnotationProperty(subject),
            AnonymousIndividual());
    }

    private final OWLPrimitive e;

    public AnnotationAccessorsTestCase(OWLPrimitive e) {
        this.e = e;
    }

    private static OWLAnnotationAssertionAxiom createAnnotationAssertionAxiom() {
        OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
        OWLAnnotationValue value = Literal("value");
        IRI subject = df.getIRI("http://owlapi.sourceforge.net/ontologies/test#", "X");
        return AnnotationAssertion(prop, subject, value);
    }

    @Test
    public void testClassAccessor() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        ont.addAxiom(ax);
        IRI subject = df.getIRI("http://owlapi.sourceforge.net/ontologies/test#", "X");
        assertTrue(ont.annotationAssertionAxioms(subject).anyMatch(a -> a.equals(ax)));
        if (e instanceof OWLEntity) {
            assertTrue(ont.annotationAssertionAxioms(((OWLEntity) e).getIRI())
                .anyMatch(a -> a.equals(ax)));
            assertTrue(contains(getAnnotationObjects((OWLEntity) e, ont), ax.getAnnotation()));
        }
    }
}
