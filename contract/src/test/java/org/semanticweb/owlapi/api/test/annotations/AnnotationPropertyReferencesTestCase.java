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
package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 28-May-2009
 */
@SuppressWarnings("javadoc")
public class AnnotationPropertyReferencesTestCase extends
        AbstractOWLAPITestCase {

    @Test
    public void testContainsReferenceForAnnotationAssertion() {
        OWLAnnotationProperty ap = AnnotationProperty(getIRI("prop"));
        OWLLiteral val = Literal("Test", "");
        OWLAnnotationSubject subject = Class(getIRI("A")).getIRI();
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(ap, subject, val);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().addAxiom(ont, ax);
        assertTrue(ont.containsAnnotationPropertyInSignature(ap.getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(ap));
    }

    @Test
    public void testContainsReferenceForAxiomAnnotation() {
        OWLAnnotationProperty ap = AnnotationProperty(getIRI("prop"));
        OWLLiteral val = Literal("Test", "");
        OWLAnnotation anno = Factory.getFactory().getOWLAnnotation(ap, val);
        Set<OWLAnnotation> annos = Collections.singleton(anno);
        OWLSubClassOfAxiom ax = Factory.getFactory().getOWLSubClassOfAxiom(
                Class(getIRI("A")), Class(getIRI("B")), annos);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().addAxiom(ont, ax);
        assertTrue(ont.containsAnnotationPropertyInSignature(anno.getProperty()
                .getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(
                anno.getProperty()));
    }

    @Test
    public void testContainsReferenceForOntologyAnnotation() throws Exception {
        OWLAnnotationProperty ap = AnnotationProperty(getIRI("prop"));
        OWLLiteral val = Literal("Test");
        OWLAnnotation anno = Factory.getFactory().getOWLAnnotation(ap, val);
        OWLOntology ont = getOWLOntology("Ont");
        getManager().applyChange(new AddOntologyAnnotation(ont, anno));
        assertTrue(ont.containsAnnotationPropertyInSignature(anno.getProperty()
                .getIRI()));
        assertTrue(ont.getAnnotationPropertiesInSignature().contains(
                anno.getProperty()));
    }
}
