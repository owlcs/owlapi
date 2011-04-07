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

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class TypeNegativePropertyAssertionHandler extends BuiltInTypeHandler {

    public TypeNegativePropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        IRI source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL.getIRI(), true);
        if (source == null) {
            source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getIRI(), true);
        }
        IRI property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ASSERTION_PROPERTY.getIRI(), true);
        if (property == null) {
            property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_PREDICATE.getIRI(), true);
        }
        Object target = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_TARGET_INDIVIDUAL.getIRI(), true);
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_TARGET_VALUE.getIRI(), true);
        }
        if (target == null) {
            target = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_OBJECT.getIRI(), true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getIRI(), true);
        }

        Set<OWLAnnotation> annos = getConsumer().translateAnnotations(subject);
        if (target instanceof OWLLiteral && (!isStrict() || getConsumer().isDataProperty(property))) {
            translateNegativeDataPropertyAssertion(subject, predicate, object, source, property, (OWLLiteral) target, annos);
        }
        else if(target instanceof IRI && (!isStrict() || getConsumer().isObjectProperty(property))) {
            translateNegativeObjectPropertyAssertion(subject, predicate, object, source, property, (IRI) target, annos);
        }
        // TODO LOG ERROR
    }

    private void translateNegativeObjectPropertyAssertion(IRI subject, IRI predicate, IRI object, IRI source, IRI property, IRI target, Set<OWLAnnotation> annos) {
        OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
        OWLObjectPropertyExpression prop = getConsumer().translateObjectPropertyExpression(property);
        OWLIndividual targetInd = getConsumer().getOWLIndividual(target);
        consumeTriple(subject, predicate, object);
        addAxiom(getDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(prop, sourceInd, targetInd, annos));
    }

    private void translateNegativeDataPropertyAssertion(IRI subject, IRI predicate, IRI object, IRI source, IRI property, OWLLiteral target, Set<OWLAnnotation> annos) {
        OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
        OWLDataPropertyExpression prop = getConsumer().translateDataPropertyExpression(property);
        OWLLiteral lit = target;
        consumeTriple(subject, predicate, object);
        addAxiom(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd, lit, annos));
    }
}
