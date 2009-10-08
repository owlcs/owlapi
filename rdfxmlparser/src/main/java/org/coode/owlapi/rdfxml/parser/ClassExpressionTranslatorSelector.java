package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.net.URI;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class ClassExpressionTranslatorSelector {


    private OWLRDFConsumer consumer;

    private IntersectionOfTranslator intersectionOfTranslator;

    private UnionOfTranslator unionOfTranslator;

    private ComplementOfTranslator complementOfTranslator;

    private OneOfTranslator oneOfTranslator;

    private SelfRestrictionTranslator selfRestrictionTranslator;

    private ObjectAllValuesFromTranslator objectAllValuesFromTranslator;

    private ObjectSomeValuesFromTranslator objectSomeValuesFromTranslator;

    private ObjectHasValueTranslator objectHasValueTranslator;

    private ObjectMinCardinalityTranslator objectMinCardinalityTranslator;

    private ObjectCardinalityTranslator objectCardinalityTranslator;

    private ObjectMaxCardinalityTranslator objectMaxCardinalityTranslator;

    private DataAllValuesFromTranslator dataAllValuesFromTranslator;

    private DataSomeValuesFromTranslator dataSomeValuesFromTranslator;

    private DataHasValueTranslator dataHasValueTranslator;

    private DataMinCardinalityTranslator dataMinCardinalityTranslator;

    private DataCardinalityTranslator dataCardinalityTranslator;

    private DataMaxCardinalityTranslator dataMaxCardinalityTranslator;

    private NamedClassTranslator namedClassTranslator;


    public ClassExpressionTranslatorSelector(OWLRDFConsumer con) {
        this.consumer = con;
        intersectionOfTranslator = new IntersectionOfTranslator(con);
        unionOfTranslator = new UnionOfTranslator(con);
        complementOfTranslator = new ComplementOfTranslator(con);
        oneOfTranslator = new OneOfTranslator(con);
        selfRestrictionTranslator = new SelfRestrictionTranslator(con);
        objectAllValuesFromTranslator = new ObjectAllValuesFromTranslator(con);
        objectSomeValuesFromTranslator = new ObjectSomeValuesFromTranslator(con);
        objectHasValueTranslator = new ObjectHasValueTranslator(con);
        objectMinCardinalityTranslator = new ObjectMinCardinalityTranslator(con);
        objectCardinalityTranslator = new ObjectCardinalityTranslator(con);
        objectMaxCardinalityTranslator = new ObjectMaxCardinalityTranslator(con);
        dataAllValuesFromTranslator = new DataAllValuesFromTranslator(con);
        dataSomeValuesFromTranslator = new DataSomeValuesFromTranslator(con);
        dataHasValueTranslator = new DataHasValueTranslator(con);
        dataMinCardinalityTranslator = new DataMinCardinalityTranslator(con);
        dataCardinalityTranslator = new DataCardinalityTranslator(con);
        dataMaxCardinalityTranslator = new DataMaxCardinalityTranslator(con);
        namedClassTranslator = new NamedClassTranslator(con);
    }

    public boolean isObjectRestriction(IRI mainNode, IRI property) {
        if(consumer.isObjectPropertyOnly(property)) {
            return true;
        }
        if(isClassExpressionObject(mainNode, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI())) {
            return true;
        }
        if(isClassExpressionObject(mainNode, OWLRDFVocabulary.OWL_ALL_VALUES_FROM.getIRI())) {
            return true;
        }
        if(isClassExpressionObject(mainNode, OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(isClassExpressionObject(mainNode, OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(isClassExpressionObject(mainNode, OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(consumer.getResourceObject(mainNode, OWLRDFVocabulary.OWL_HAS_VALUE.getIRI(), false) != null) {
            return true;
        }
        return false;

    }

    private boolean isClassExpressionObject(IRI mainNode, IRI predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && consumer.isClass(object);
    }

    public boolean isDataRestriction(IRI mainNode, IRI property) {
        if(consumer.isDataPropertyOnly(property)) {
            return true;
        }
        if(isDataRangeObject(mainNode, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI())) {
            return true;
        }
        if(isDataRangeObject(mainNode, OWLRDFVocabulary.OWL_ALL_VALUES_FROM.getIRI())) {
            return true;
        }
        if(isDataRangeObject(mainNode, OWLRDFVocabulary.OWL_MIN_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(isDataRangeObject(mainNode, OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(isDataRangeObject(mainNode, OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY.getIRI())) {
            return true;
        }
        if(consumer.getLiteralObject(mainNode, OWLRDFVocabulary.OWL_HAS_VALUE.getIRI(), false) != null) {
            return true;
        }
        return false;
    }

    private boolean isDataRangeObject(IRI mainNode, IRI predicate) {
        IRI object = consumer.getResourceObject(mainNode, predicate, false);
        return object != null && consumer.isDataRange(object);
    }

    /**
     * Gets a translator for a class expression. The selector ensures that the necessary triples are present.
     * @param mainNode The main node of the class expression
     * @return The translator that should be used to translate the class expression
     * @throws OWLException
     */
    public ClassExpressionTranslator getClassExpressionTranslator(IRI mainNode) {


        if (consumer.isRestriction(mainNode)) {
            // Check that the necessary triples are there
            IRI onPropertyIRI = consumer.getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), false);
            if (onPropertyIRI == null) {
                // Can't do anything
                return namedClassTranslator;
            }
            if (isObjectRestriction(mainNode, onPropertyIRI)) {
                return getObjectRestrictionTranslator(mainNode);
            }
            if (isDataRestriction(mainNode, onPropertyIRI)) {
                return getDataRestrictionTranslator(mainNode);
            }
            // Don't do any repair?
            return namedClassTranslator;
        }

        if (consumer.hasPredicate(mainNode, OWL_INTERSECTION_OF.getIRI())) {
            return intersectionOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_UNION_OF.getIRI())) {
            return unionOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_COMPLEMENT_OF.getIRI())) {
            return complementOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_ONE_OF.getIRI())) {
            return oneOfTranslator;
        }
        return namedClassTranslator;
    }

    private ClassExpressionTranslator getObjectRestrictionTranslator(IRI mainNode) {
        if (consumer.hasPredicate(mainNode, OWL_SOME_VALUES_FROM.getIRI())) {
            return objectSomeValuesFromTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_ALL_VALUES_FROM.getIRI())) {
            return objectAllValuesFromTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_HAS_VALUE.getIRI())) {
            return objectHasValueTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_MIN_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_MIN_QUALIFIED_CARDINALITY.getIRI())) {
            return objectMinCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_QUALIFIED_CARDINALITY.getIRI())) {
            return objectCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_MAX_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_MAX_QUALIFIED_CARDINALITY.getIRI())) {
            return objectMaxCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_HAS_SELF.getIRI())) {
            return selfRestrictionTranslator;
        }
        return namedClassTranslator;
    }

    private ClassExpressionTranslator getDataRestrictionTranslator(IRI mainNode) {
        if (consumer.hasPredicate(mainNode, OWL_SOME_VALUES_FROM.getIRI())) {
            return dataSomeValuesFromTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_ALL_VALUES_FROM.getIRI())) {
            return dataAllValuesFromTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_HAS_VALUE.getIRI())) {
            return dataHasValueTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_MIN_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_MIN_QUALIFIED_CARDINALITY.getIRI())) {
            return dataMinCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_QUALIFIED_CARDINALITY.getIRI())) {
            return dataCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_MAX_CARDINALITY.getIRI()) || consumer.hasPredicate(mainNode, OWL_MAX_QUALIFIED_CARDINALITY.getIRI())) {
            return dataMaxCardinalityTranslator;
        }
        return namedClassTranslator;
    }
}
