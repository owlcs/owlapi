package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

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
 * <p/>
 * A collection of utility methods for creating common data values and data ranges
 */
public class OWLDataUtil {

    private static final IRI INT_URI = XSDVocabulary.INT.getIRI();

    private static Map<Class, XSDVocabulary> numberTypeMap;


    static {
        numberTypeMap = new HashMap<Class, XSDVocabulary>();
        numberTypeMap.put(Byte.class, XSDVocabulary.BYTE);
        numberTypeMap.put(Double.class, XSDVocabulary.DOUBLE);
        numberTypeMap.put(Float.class, XSDVocabulary.FLOAT);
        numberTypeMap.put(Integer.class, XSDVocabulary.INT);
        numberTypeMap.put(Short.class, XSDVocabulary.SHORT);
    }


    public static OWLDatatype getIntDatatype(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDatatype(INT_URI);
    }


    public static OWLDatatype getLongDatatype(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDatatype(XSDVocabulary.LONG.getIRI());
    }


    public static OWLDatatype getFloatDatatype(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDatatype(XSDVocabulary.FLOAT.getIRI());
    }


    public static OWLDatatype getDoubleDatatype(OWLDataFactory dataFactory) throws OWLException {
        return dataFactory.getOWLDatatype(XSDVocabulary.DOUBLE.getIRI());
    }

    /**
     * Given a <code>Number</code>, this method returns the datatype of
     * that number.
     *
     * @param n The <code>Number</code> whose datatype is to be obtained.
     * @return The datatype that corresponds to the type of number.
     */
    public static <N extends Number> OWLDatatype getDatatype(OWLDataFactory dataFactory, N n) {
        XSDVocabulary v = numberTypeMap.get(n.getClass());
        if (v == null) {
            throw new OWLRuntimeException("Don't know how to translate " + n.getClass());
        }
        return dataFactory.getOWLDatatype(v.getIRI());
    }

    public static Set<OWLFacetRestriction> getFacetRestrictionSet(OWLDataFactory dataFactory, OWLFacet facet, OWLTypedLiteral facetValue) {
        return Collections.singleton(dataFactory.getOWLFacetRestriction(facet, facetValue));
    }

    /**
     * Given a number, returns a typed constant that represent the number.
     *
     * @param val The number to be translated to a typed constant.
     * @return The typed constant, consisting of a string literal and datatype that
     *         corresponds to the translation of the specified number.
     */
    public static <N extends Number> OWLTypedLiteral getTypedConstant(OWLDataFactory dataFactory, N val) {
        return dataFactory.getOWLTypedLiteral(val.toString(), getDatatype(dataFactory, val));
    }


    public static <N extends Number> OWLDataRange getMinInclusiveRestrictedInt(OWLDataFactory dataFactory, N minInclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, minInclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MIN_INCLUSIVE,
                        getTypedConstant(dataFactory, minInclusive)));
    }

    public static <N extends Number> OWLDataRange getMinExclusiveRestrictedInt(OWLDataFactory dataFactory, N minExclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, minExclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MIN_EXCLUSIVE,
                        getTypedConstant(dataFactory, minExclusive)));
    }

    public static <N extends Number> OWLDataRange getMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxInclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, maxInclusive),
                getFacetRestrictionSet(dataFactory, OWLFacet.MAX_INCLUSIVE,
                        getTypedConstant(dataFactory, maxInclusive)));
    }

    public static <N extends Number> OWLDataRange getMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxExclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, maxExclusive),
                getFacetRestrictionSet(dataFactory,
                        OWLFacet.MAX_EXCLUSIVE,
                        getTypedConstant(dataFactory, maxExclusive)));
    }

    public static <N extends Number> OWLDataRange getMinMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N minInclusive, N maxInclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minInclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE,
                getTypedConstant(dataFactory, minInclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE,
                getTypedConstant(dataFactory, maxInclusive)));

        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }

    public static <N extends Number> OWLDataRange getMinMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N minExclusive, N maxExclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minExclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE,
                getTypedConstant(dataFactory, minExclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE,
                getTypedConstant(dataFactory, maxExclusive)));

        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }


}
