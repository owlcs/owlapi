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

package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class GTPAnnotationLiteralHandler extends AbstractLiteralTripleHandler {

    public GTPAnnotationLiteralHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return !isAnonymous(subject) && !getConsumer().isAnnotation(subject) && getConsumer().isAnnotationProperty(predicate);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        if(isStrict()) {
            return isAnnotationPropertyOnly(predicate);
        }
        boolean axiom = getConsumer().isAxiom(subject);
        if(axiom) {
            return false;
        }
        boolean annotation = getConsumer().isAnnotation(subject);
        if(annotation) {
            return false;
        }
        if(getConsumer().isAnnotationProperty(predicate)) {
            return true;
        }
        if (!isAnonymous(subject)) {
            if(isClassExpressionLax(subject)) {
                return true;
            }
            if(isDataRangeLax(subject)) {
                return true;
            }
            if(isObjectPropertyLax(subject)) {
                return true;
            }
            if(isDataPropertyLax(subject)) {
                return true;
            }
        }
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        consumeTriple(subject, predicate, object);
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(predicate);
        OWLAnnotationSubject annotationSubject;
        if(isAnonymous(subject)) {
            annotationSubject = getDataFactory().getOWLAnonymousIndividual(subject.toString());
        }
        else {
            annotationSubject = subject;
        }
        if(getConsumer().isOntology(subject)) {
        	getConsumer().addOntologyAnnotation(getDataFactory().getOWLAnnotation(prop, object, getPendingAnnotations()));
        }
        else {
            OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(prop, annotationSubject, object, getPendingAnnotations());
            addAxiom(ax);
        }
    }
}
