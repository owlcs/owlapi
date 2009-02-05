package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import static org.semanticweb.owl.vocab.OWLRDFVocabulary.*;

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
public class DescriptionTranslatorSelector {

    private enum RestrictionType{OBJECT, DATA, UNKNOWN};

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


    public DescriptionTranslatorSelector(OWLRDFConsumer con) {
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
    }


    public DescriptionTranslator getDescriptionTranslator(URI mainNode) throws OWLException {


        if (consumer.isSelfRestriction(mainNode)) {
            return selfRestrictionTranslator;
        }
        if (consumer.isRestriction(mainNode)) {
            URI onPropertyURI = consumer.getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_PROPERTY.getURI(), false);
            if (onPropertyURI == null) {
                throw new OWLRDFParserException("Malformed restriction.  owl:onProperty triple not present.");
            }

            if (consumer.isObjectPropertyOnly(onPropertyURI)) {
                return getRestrictionTranslator(mainNode, RestrictionType.OBJECT);
            }
            if (consumer.isDataPropertyOnly(onPropertyURI)) {
                return getRestrictionTranslator(mainNode, RestrictionType.DATA);
            }
            return getRestrictionTranslator(mainNode, RestrictionType.UNKNOWN);
//            throw new OWLException("Cannot translate description.  Ambiguous type of restriction (untyped property: " + onPropertyTriple.getResourceObject() + ").");
        }

        if (consumer.hasPredicate(mainNode, OWL_INTERSECTION_OF.getURI())) {
            return intersectionOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_UNION_OF.getURI())) {
            return unionOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_COMPLEMENT_OF.getURI())) {
            return complementOfTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_ONE_OF.getURI())) {
            return oneOfTranslator;
        }
        if (consumer.isObjectRestriction(mainNode)) {
            return getRestrictionTranslator(mainNode, RestrictionType.OBJECT);
        }
        if (consumer.isDataRestriction(mainNode)) {
            return getRestrictionTranslator(mainNode, RestrictionType.DATA);
        }
        return null;
//        throw new OWLException(
//                "Unable to translate description.  Cannot determine the type of description from available triples.");
    }


    private DescriptionTranslator getRestrictionTranslator(URI mainNode, RestrictionType type) throws OWLException {
        if (consumer.hasPredicate(mainNode, OWL_SOME_VALUES_FROM.getURI())) {
            if(type.equals(RestrictionType.OBJECT)) {
                return objectSomeValuesFromTranslator;
            }
            else if(type.equals(RestrictionType.DATA)) {
                return dataSomeValuesFromTranslator;
            }
            else {
                if(consumer.getResourceObject(mainNode, OWL_SOME_VALUES_FROM.getURI(), false) != null) {
                    return objectSomeValuesFromTranslator;
                }
                else {
                    return dataSomeValuesFromTranslator;
                }
            }

        }
        if (consumer.hasPredicate(mainNode, OWL_ALL_VALUES_FROM.getURI())) {
            if(type.equals(RestrictionType.OBJECT)) {
                return objectAllValuesFromTranslator;
            }
            else if(type.equals(RestrictionType.DATA)) {
                return dataAllValuesFromTranslator;
            }
            else {
                if(consumer.getResourceObject(mainNode, OWL_ALL_VALUES_FROM.getURI(), false) != null) {
                    return objectAllValuesFromTranslator;
                }
                else {
                    return dataAllValuesFromTranslator;
                }
            }
        }
        if (consumer.hasPredicate(mainNode, OWL_HAS_VALUE.getURI())) {
            if(type.equals(RestrictionType.OBJECT)) {
                return objectHasValueTranslator;
            }
            else if(type.equals(RestrictionType.DATA)) {
                return dataHasValueTranslator;
            }
            else {
                if(consumer.getResourceObject(mainNode, OWL_HAS_VALUE.getURI(), false) != null) {
                    return objectHasValueTranslator;
                }
                else {
                    return dataHasValueTranslator;
                }
            }
        }
        if (consumer.hasPredicate(mainNode, OWL_MIN_CARDINALITY.getURI()) || consumer.hasPredicate(mainNode, OWL_MIN_QUALIFIED_CARDINALITY.getURI())) {
            return type.equals(RestrictionType.DATA) ? dataMinCardinalityTranslator : objectMinCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_CARDINALITY.getURI()) || consumer.hasPredicate(mainNode, OWL_QUALIFIED_CARDINALITY.getURI())) {
            return type.equals(RestrictionType.DATA) ? dataCardinalityTranslator : objectCardinalityTranslator;
        }
        if (consumer.hasPredicate(mainNode, OWL_MAX_CARDINALITY.getURI()) || consumer.hasPredicate(mainNode, OWL_MAX_QUALIFIED_CARDINALITY.getURI())) {
            return type.equals(RestrictionType.DATA) ? dataMaxCardinalityTranslator : objectMaxCardinalityTranslator;
        }
        if(consumer.isSelfRestriction(mainNode) || consumer.hasPredicate(mainNode, OWL_HAS_SELF.getURI())) {
            return selfRestrictionTranslator;
        }
        throw new OWLRDFParserException("Unable to determine the type of restriction!");
    }
}
