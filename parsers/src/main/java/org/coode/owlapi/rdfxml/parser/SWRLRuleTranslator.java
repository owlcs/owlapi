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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 18-Feb-2007
 */
public class SWRLRuleTranslator {

    private OWLRDFConsumer consumer;
    private OptimisedListTranslator<SWRLAtom> listTranslator;

    /**
     * @param consumer
     *        consumer
     */
    public SWRLRuleTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
        listTranslator = new OptimisedListTranslator<SWRLAtom>(consumer,
                new SWRLAtomListItemTranslator(consumer));
    }

    /**
     * @param mainNode
     *        rule to translate
     */
    public void translateRule(IRI mainNode) {
        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        Set<IRI> predicates = consumer.getPredicatesBySubject(mainNode);
        for (IRI i : predicates) {
            if (consumer.isAnnotationProperty(i)) {
                OWLAnnotationProperty p = consumer.getDataFactory()
                        .getOWLAnnotationProperty(i);
                OWLLiteral literal = consumer.getLiteralObject(mainNode, i,
                        true);
                while (literal != null) {
                    annotations.add(consumer.getDataFactory().getOWLAnnotation(
                            p, literal));
                    literal = consumer.getLiteralObject(mainNode, i, true);
                }
            }
        }
        Set<SWRLAtom> consequent = Collections.emptySet();
        // XXX annotations on rules are not parsed correctly
        IRI ruleHeadIRI = consumer.getResourceObject(mainNode,
                SWRLVocabulary.HEAD.getIRI(), true);
        if (ruleHeadIRI != null) {
            consequent = listTranslator.translateToSet(ruleHeadIRI);
        }
        Set<SWRLAtom> antecedent = Collections.emptySet();
        IRI ruleBodyIRI = consumer.getResourceObject(mainNode,
                SWRLVocabulary.BODY.getIRI(), true);
        if (ruleBodyIRI != null) {
            antecedent = listTranslator.translateToSet(ruleBodyIRI);
        }
        SWRLRule rule = null;
        if (!consumer.isAnonymousNode(mainNode)) {
            rule = consumer.getDataFactory().getSWRLRule(antecedent,
                    consequent, annotations);
        } else {
            rule = consumer.getDataFactory().getSWRLRule(antecedent,
                    consequent, annotations);
        }
        consumer.addAxiom(rule);
    }
}
