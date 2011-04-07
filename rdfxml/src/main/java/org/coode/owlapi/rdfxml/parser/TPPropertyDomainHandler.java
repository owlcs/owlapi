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
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPPropertyDomainHandler extends TriplePredicateHandler {

    public TPPropertyDomainHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_DOMAIN.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        return false;
    }

    @Override
	public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject) && getConsumer().isClassExpression(object)) {
            translateObjectPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isDataPropertyOnly(subject) && getConsumer().isClassExpression(object)) {
            translateDataPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isAnnotationProperty(subject) && getConsumer().isClassExpression(object) && !getConsumer().isAnonymousNode(object)) {
            translateAnnotationPropertyDomain(subject, predicate, object);
        }
        else if(!isStrict()) {
            translateAnnotationPropertyDomain(subject, predicate, object);
        }
    }

    private void translateAnnotationPropertyDomain(IRI subject, IRI predicate, IRI object) {
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
        addAxiom(getDataFactory().getOWLAnnotationPropertyDomainAxiom(prop, object, getPendingAnnotations()));
        // TODO: Handle anonymous domain - error?
        consumeTriple(subject, predicate, object);
    }


    private void translateDataPropertyDomain(IRI subject,
                                             IRI predicate,
                                             IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLDataPropertyDomainAxiom(translateDataProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }


    private void translateObjectPropertyDomain(IRI subject,
                                               IRI predicate,
                                               IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLObjectPropertyDomainAxiom(translateObjectProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
