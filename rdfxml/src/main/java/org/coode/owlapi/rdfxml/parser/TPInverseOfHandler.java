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
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 * <br>
 * owl:inverseOf is used in both property expressions AND axioms.
 */
public class TPInverseOfHandler extends TriplePredicateHandler {

    private boolean axiomParsingMode = false;

    public TPInverseOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_INVERSE_OF.getIRI());
    }

    public boolean isAxiomParsingMode() {
        return axiomParsingMode;
    }

    public void setAxiomParsingMode(boolean axiomParsingMode) {
        this.axiomParsingMode = axiomParsingMode;
    }
    
    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addObjectProperty(subject, false);
        getConsumer().addObjectProperty(object, false);
        return false;
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object);
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // Only do axiom translation
        if (axiomParsingMode && getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) {
            addAxiom(getDataFactory().getOWLInverseObjectPropertiesAxiom(
                    translateObjectProperty(subject),
                    translateObjectProperty(object), getPendingAnnotations()
            ));
            consumeTriple(subject, predicate, object);
        }
    }
}
