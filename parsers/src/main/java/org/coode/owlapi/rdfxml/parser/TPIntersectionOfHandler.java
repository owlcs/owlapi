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
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * A handler for top level intersection classes.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 11-Dec-2006
 */
public class TPIntersectionOfHandler extends
        AbstractNamedEquivalentClassAxiomHandler {

    /**
     * @param consumer
     *        consumer
     */
    public TPIntersectionOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_INTERSECTION_OF.getIRI());
    }

    @Override
    protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
        return getDataFactory().getOWLObjectIntersectionOf(
                getConsumer().translateToClassExpressionSet(mainNode));
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        if (getConsumer().isClassExpression(subject)) {
            getConsumer().addClassExpression(object, false);
        } else if (getConsumer().isClassExpression(object)) {
            getConsumer().addClassExpression(subject, false);
        } else if (getConsumer().isDataRange(subject)) {
            getConsumer().addDataRange(object, false);
        } else if (getConsumer().isDataRange(object)) {
            getConsumer().addDataRange(subject, false);
        }
        return super.canHandleStreaming(subject, predicate, object);
    }
}