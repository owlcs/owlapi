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
package org.semanticweb.owlapi.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * A collection of utility methods for creating common data values and data
 * ranges.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 06-Jan-2007
 */
public class OWLDataUtil {

    private static final IRI INT_URI = XSDVocabulary.INT.getIRI();
    private static final Map<Class<?>, XSDVocabulary> numberTypeMap;
    static {
        numberTypeMap = new HashMap<Class<?>, XSDVocabulary>();
        numberTypeMap.put(Byte.class, XSDVocabulary.BYTE);
        numberTypeMap.put(Double.class, XSDVocabulary.DOUBLE);
        numberTypeMap.put(Float.class, XSDVocabulary.FLOAT);
        numberTypeMap.put(Integer.class, XSDVocabulary.INT);
        numberTypeMap.put(Short.class, XSDVocabulary.SHORT);
    }

    /**
     * @param dataFactory
     *        factory to use
     * @return int datatype
     */
    public static OWLDatatype getIntDatatype(OWLDataFactory dataFactory) {
        return dataFactory.getOWLDatatype(INT_URI);
    }

    /**
     * @param dataFactory
     *        factory to use
     * @return long datatype
     */
    public static OWLDatatype getLongDatatype(OWLDataFactory dataFactory) {
        return dataFactory.getOWLDatatype(XSDVocabulary.LONG.getIRI());
    }

    /**
     * @param dataFactory
     *        factory to use
     * @return float datatype
     */
    public static OWLDatatype getFloatDatatype(OWLDataFactory dataFactory) {
        return dataFactory.getOWLDatatype(XSDVocabulary.FLOAT.getIRI());
    }

    /**
     * @param dataFactory
     *        factory to use
     * @return double datatype
     */
    public static OWLDatatype getDoubleDatatype(OWLDataFactory dataFactory) {
        return dataFactory.getOWLDatatype(XSDVocabulary.DOUBLE.getIRI());
    }

    /**
     * Given a {@code Number}, this method returns the datatype of that number.
     * 
     * @param dataFactory
     *        the factory to use
     * @param n
     *        The {@code Number} whose datatype is to be obtained.
     * @return The datatype that corresponds to the type of number.
     * @param <N>
     *        number type
     */
    public static <N extends Number> OWLDatatype getDatatype(
            OWLDataFactory dataFactory, N n) {
        XSDVocabulary v = numberTypeMap.get(n.getClass());
        if (v == null) {
            throw new OWLRuntimeException("Don't know how to translate "
                    + n.getClass());
        }
        return dataFactory.getOWLDatatype(v.getIRI());
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param facet
     *        the facet to use
     * @param facetValue
     *        the value to use
     * @return a collection of facet restrictions
     */
    public static Set<OWLFacetRestriction> getFacetRestrictionSet(
            OWLDataFactory dataFactory, OWLFacet facet, OWLLiteral facetValue) {
        return Collections.singleton(dataFactory.getOWLFacetRestriction(facet,
                facetValue));
    }

    /**
     * Given a number, returns a typed constant that represent the number.
     * 
     * @param dataFactory
     *        the factory to use
     * @param val
     *        The number to be translated to a typed constant.
     * @return The typed constant, consisting of a string literal and datatype
     *         that corresponds to the translation of the specified number.
     * @param <N>
     *        number type
     */
    public static <N extends Number> OWLLiteral getTypedConstant(
            OWLDataFactory dataFactory, N val) {
        return dataFactory.getOWLLiteral(val.toString(),
                getDatatype(dataFactory, val));
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param minInclusive
     *        the value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange getMinInclusiveRestrictedInt(
            OWLDataFactory dataFactory, N minInclusive) {
        return dataFactory.getOWLDatatypeRestriction(
                getDatatype(dataFactory, minInclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MIN_INCLUSIVE,
                        getTypedConstant(dataFactory, minInclusive)));
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param minExclusive
     *        the value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange getMinExclusiveRestrictedInt(
            OWLDataFactory dataFactory, N minExclusive) {
        return dataFactory.getOWLDatatypeRestriction(
                getDatatype(dataFactory, minExclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MIN_EXCLUSIVE,
                        getTypedConstant(dataFactory, minExclusive)));
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param maxInclusive
     *        the value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange
            getMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory,
                    N maxInclusive) {
        return dataFactory.getOWLDatatypeRestriction(
                getDatatype(dataFactory, maxInclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MAX_INCLUSIVE,
                        getTypedConstant(dataFactory, maxInclusive)));
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param maxExclusive
     *        the value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange
            getMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory,
                    N maxExclusive) {
        return dataFactory.getOWLDatatypeRestriction(
                getDatatype(dataFactory, maxExclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MAX_EXCLUSIVE,
                        getTypedConstant(dataFactory, maxExclusive)));
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param minInclusive
     *        the min value
     * @param maxInclusive
     *        the max value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange
            getMinMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory,
                    N minInclusive, N maxInclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minInclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(
                OWLFacet.MIN_INCLUSIVE,
                getTypedConstant(dataFactory, minInclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE,
                getTypedConstant(dataFactory, maxInclusive)));
        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }

    /**
     * @param dataFactory
     *        the factory to use
     * @param minExclusive
     *        the min value
     * @param maxExclusive
     *        the max value
     * @param <N>
     *        number type
     * @return new data range
     */
    public static <N extends Number> OWLDataRange
            getMinMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory,
                    N minExclusive, N maxExclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minExclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(
                OWLFacet.MIN_EXCLUSIVE,
                getTypedConstant(dataFactory, minExclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(
                OWLFacet.MAX_EXCLUSIVE,
                getTypedConstant(dataFactory, maxExclusive)));
        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }
}
