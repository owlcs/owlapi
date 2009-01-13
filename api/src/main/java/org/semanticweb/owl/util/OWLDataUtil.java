package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary;
import org.semanticweb.owl.vocab.XSDVocabulary;

import java.net.URI;
import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 06-Jan-2007<br><br>
 *
 * A collection of utility methods for creating common data values and data ranges 
 */
public class OWLDataUtil {

    private static final URI INT_URI = XSDVocabulary.INT.getURI();

    private static Map<Class, XSDVocabulary> numberTypeMap;


    static {
        numberTypeMap = new HashMap<Class, XSDVocabulary>();
        numberTypeMap.put(Byte.class, XSDVocabulary.BYTE);
        numberTypeMap.put(Double.class, XSDVocabulary.DOUBLE);
        numberTypeMap.put(Float.class, XSDVocabulary.FLOAT);
        numberTypeMap.put(Integer.class, XSDVocabulary.INT);
        numberTypeMap.put(Short.class, XSDVocabulary.SHORT);
    }


    public static OWLDataType getIntDataType(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDataType(INT_URI);
    }


    public static OWLDataType getLongDataType(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDataType(XSDVocabulary.LONG.getURI());
    }


    public static OWLDataType getFloatDataType(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDataType(XSDVocabulary.FLOAT.getURI());
    }


    public static OWLDataType getDoubleDataType(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDataType(XSDVocabulary.DOUBLE.getURI());
    }

    /**
     * Given a <code>Number</code>, this method returns the datatype of
     * that number.
     * @param n The <code>Number</code> whose datatype is to be obtained.
     * @return The datatype that corresponds to the type of number.
     */
    public static <N extends Number> OWLDataType getDataType(OWLDataFactory dataFactory, N n) {
        XSDVocabulary v = numberTypeMap.get(n.getClass());
        if (v == null) {
            throw new OWLRuntimeException("Don't know how to translate " + n.getClass());
        }
        return dataFactory.getOWLDataType(v.getURI());
    }

    public static Set<OWLDataRangeFacetRestriction> getFacetRestrictionSet(OWLDataFactory dataFactory, OWLRestrictedDataRangeFacetVocabulary facet, OWLTypedConstant facetValue) {
        return Collections.singleton(dataFactory.getOWLDataRangeFacetRestriction(facet, facetValue));
    }

    /**
     * Given a number, returns a typed constant that represent the number.
     * @param val The number to be translated to a typed constant.
     * @return The typed constant, consisting of a string literal and datatype that
     * corresponds to the translation of the specified number.
     */
    public static  <N extends Number> OWLTypedConstant getTypedConstant(OWLDataFactory dataFactory, N val) {
        return dataFactory.getOWLTypedConstant(val.toString(), getDataType(dataFactory, val));
    }


    public static  <N extends Number> OWLDataRange getMinInclusiveRestrictedInt(OWLDataFactory dataFactory, N minInclusive) {
        return dataFactory.getOWLDataRangeRestriction(getDataType(dataFactory, minInclusive),
                                                     getFacetRestrictionSet(dataFactory, OWLRestrictedDataRangeFacetVocabulary.MIN_INCLUSIVE,
                                                     getTypedConstant(dataFactory, minInclusive)));
    }

    public static  <N extends Number> OWLDataRange getMinExclusiveRestrictedInt(OWLDataFactory dataFactory, N minExclusive) {
        return dataFactory.getOWLDataRangeRestriction(getDataType(dataFactory, minExclusive),
                                                     getFacetRestrictionSet(dataFactory, OWLRestrictedDataRangeFacetVocabulary.MIN_EXCLUSIVE,
                                                     getTypedConstant(dataFactory, minExclusive)));
    }

    public static  <N extends Number> OWLDataRange getMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxInclusive) {
        return dataFactory.getOWLDataRangeRestriction(getDataType(dataFactory, maxInclusive),
                                                     getFacetRestrictionSet(dataFactory, OWLRestrictedDataRangeFacetVocabulary.MAX_INCLUSIVE,
                                                     getTypedConstant(dataFactory, maxInclusive)));
    }

    public static  <N extends Number> OWLDataRange getMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxExclusive) {
        return dataFactory.getOWLDataRangeRestriction(getDataType(dataFactory, maxExclusive),
                                                     getFacetRestrictionSet(dataFactory,
                                                                            OWLRestrictedDataRangeFacetVocabulary.MAX_EXCLUSIVE,
                                                     getTypedConstant(dataFactory, maxExclusive)));
    }

    public static  <N extends Number> OWLDataRange getMinMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N minInclusive, N maxInclusive) {
        OWLDataRange dr = getDataType(dataFactory, minInclusive);
        Set<OWLDataRangeFacetRestriction> facetRestrictions = new HashSet<OWLDataRangeFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary.MIN_INCLUSIVE,
                                                   getTypedConstant(dataFactory, minInclusive)));
        facetRestrictions.add(dataFactory.getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary.MAX_INCLUSIVE,
                                                   getTypedConstant(dataFactory, maxInclusive)));

        return dataFactory.getOWLDataRangeRestriction(dr, facetRestrictions);
    }

    public static  <N extends Number> OWLDataRange getMinMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N minExclusive, N maxExclusive)  {
        OWLDataRange dr = getDataType(dataFactory, minExclusive);
        Set<OWLDataRangeFacetRestriction> facetRestrictions = new HashSet<OWLDataRangeFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary.MIN_EXCLUSIVE,
                                                   getTypedConstant(dataFactory, minExclusive)));
        facetRestrictions.add(dataFactory.getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary.MAX_EXCLUSIVE,
                                                   getTypedConstant(dataFactory, maxExclusive)));

        return dataFactory.getOWLDataRangeRestriction(dr, facetRestrictions);
    }


}
