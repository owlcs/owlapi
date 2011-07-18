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

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public abstract class AbstractClassExpressionTranslator implements ClassExpressionTranslator {

    private OWLRDFConsumer consumer;

    private ClassExpressionMatcher classExpressionMatcher = new ClassExpressionMatcher();

    private DataRangeMatcher dataRangeMatcher = new DataRangeMatcher();

    private IndividualMatcher individualMatcher = new IndividualMatcher();


    protected AbstractClassExpressionTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }

    public boolean matches(IRI mainNode, Mode mode) {
        if(mode.equals(Mode.LAX)) {
            return matchesLax(mainNode);
        }
        else {
            return matchesStrict(mainNode);
        }
    }

    public OWLRDFConsumer getConsumer() {
        return consumer;
    }

    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }

    protected boolean isAnonymous(IRI node) {
        return consumer.isAnonymousNode(node);
    }

    protected boolean isResourcePresent(IRI mainNode, OWLRDFVocabulary predicate) {
        return consumer.getResourceObject(mainNode, predicate, false) != null;
    }

    protected boolean isLiteralPresent(IRI mainNode, OWLRDFVocabulary predicate) {
        return consumer.getLiteralObject(mainNode, predicate, false) != null;
    }

    protected boolean isRestrictionStrict(IRI node) {
        return consumer.isRestriction(node);
    }

    protected boolean isRestrictionLax(IRI node) {
        return consumer.isRestriction(node);
    }

    protected boolean isNonNegativeIntegerStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
        if(literal == null) {
            return false;
        }
        OWLDatatype datatype = literal.getDatatype();
        OWL2Datatype nni = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
        return datatype.getIRI().equals(nni.getIRI()) && nni.isInLexicalSpace(literal.getLiteral());
    }

    protected boolean isNonNegativeIntegerLax(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
        if(literal == null) {
            return false;
        }
        return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(literal.getLiteral().trim());
    }

    protected int translateInteger(IRI mainNode, OWLRDFVocabulary predicate) {
        OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, true);
        if(literal == null) {
            return 0;
        }
        try {
            return Integer.parseInt(literal.getLiteral().trim());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    protected boolean isClassExpressionStrict(IRI node) {
        return consumer.isClassExpression(node) && !consumer.isDataRange(node);
    }

    protected boolean isClassExpressionStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isClassExpressionStrict(object);
    }

    protected boolean isClassExpressionLax(IRI mainNode) {
        return consumer.isClassExpression(mainNode) || consumer.isParsedAllTriples() && !consumer.isDataRange(mainNode);
    }

    protected boolean isClassExpressionLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isClassExpressionLax(object);
    }


    protected boolean isObjectPropertyStrict(IRI node) {
        return consumer.isObjectPropertyOnly(node);
    }

    protected boolean isObjectPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isObjectPropertyStrict(object);
    }

    protected boolean isObjectPropertyLax(IRI node) {
        return consumer.isObjectProperty(node);
    }

    protected boolean isObjectPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isObjectPropertyLax(object);
    }

    protected boolean isDataPropertyStrict(IRI node) {
        return consumer.isDataPropertyOnly(node);
    }

    protected boolean isDataPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataPropertyStrict(object);
    }

    protected boolean isDataPropertyLax(IRI node) {
        return consumer.isDataProperty(node);
    }

    protected boolean isDataPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataPropertyLax(object);
    }


    protected boolean isDataRangeStrict(IRI node) {
        return consumer.isDataRange(node) && !consumer.isClassExpression(node);
    }

    protected boolean isDataRangeStrict(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return isDataRangeStrict(object);
    }

    protected boolean isDataRangeLax(IRI node) {
        return consumer.isParsedAllTriples() && consumer.isDataRange(node);
    }

    protected boolean isDataRangeLax(IRI mainNode, OWLRDFVocabulary predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && isDataRangeLax(object);
    }


    protected boolean isClassExpressionListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, classExpressionMatcher, minSize);
    }

    protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, dataRangeMatcher, minSize);
    }

    protected boolean isIndividualListStrict(IRI mainNode, int minSize) {
        return isResourceListStrict(mainNode, individualMatcher, minSize);
    }

    protected boolean isResourceListStrict(IRI mainNode, TypeMatcher typeMatcher, int minSize) {
        if(mainNode == null) {
            return false;
        }
        IRI currentListNode = mainNode;
        Set<IRI> visitedListNodes = new HashSet<IRI>();
        int size = 0;
        while (true) {
            IRI firstObject = consumer.getResourceObject(currentListNode, RDF_FIRST, false);
            if(firstObject == null) {
                return false;
            }
            if(!typeMatcher.isTypeStrict(firstObject)) {
                // Something in the list that is not of the required type
                return false;
            }
            else {
                size++;
            }
            IRI restObject = consumer.getResourceObject(currentListNode, RDF_REST, false);
            if(visitedListNodes.contains(restObject)) {
                // Cycle - Non-terminating
                return false;
            }
            if(restObject == null) {
                // Not terminated properly
                return false;
            }
            if(restObject.equals(RDF_NIL.getIRI())) {
                // Terminated properly
                return size >= minSize;
            }
            // Carry on
            visitedListNodes.add(restObject);
            currentListNode = restObject;
        }
    }






    private interface TypeMatcher {
        boolean isTypeStrict(IRI node);
    }

    private class ClassExpressionMatcher implements TypeMatcher {
    	public ClassExpressionMatcher() {
    		
    	}

        public boolean isTypeStrict(IRI node) {
            return isClassExpressionStrict(node);
        }
    }

    private class DataRangeMatcher implements TypeMatcher {
    	
    	public DataRangeMatcher() {
		
		}

        public boolean isTypeStrict(IRI node) {
            return isDataRangeStrict(node);
        }
    }

    private class IndividualMatcher implements TypeMatcher {
    	public IndividualMatcher() {
		
		}

        public boolean isTypeStrict(IRI node) {
            return true;
        }
    }
}
