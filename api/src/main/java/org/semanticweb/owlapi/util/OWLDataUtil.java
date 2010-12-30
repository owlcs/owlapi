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
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


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

    private static Map<Class<?>, XSDVocabulary> numberTypeMap;


    static {
        numberTypeMap = new HashMap<Class<?>, XSDVocabulary>();
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

    public static Set<OWLFacetRestriction> getFacetRestrictionSet(OWLDataFactory dataFactory, OWLFacet facet, OWLLiteral facetValue) {
        return Collections.singleton(dataFactory.getOWLFacetRestriction(facet, facetValue));
    }

    /**
     * Given a number, returns a typed constant that represent the number.
     * @param val The number to be translated to a typed constant.
     * @return The typed constant, consisting of a string literal and datatype that
     *         corresponds to the translation of the specified number.
     */
    public static <N extends Number> OWLLiteral getTypedConstant(OWLDataFactory dataFactory, N val) {
        return dataFactory.getOWLLiteral(val.toString(), getDatatype(dataFactory, val));
    }


    public static <N extends Number> OWLDataRange getMinInclusiveRestrictedInt(OWLDataFactory dataFactory, N minInclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, minInclusive), getFacetRestrictionSet(dataFactory, OWLFacet.MIN_INCLUSIVE, getTypedConstant(dataFactory, minInclusive)));
    }

    public static <N extends Number> OWLDataRange getMinExclusiveRestrictedInt(OWLDataFactory dataFactory, N minExclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, minExclusive), getFacetRestrictionSet(dataFactory, OWLFacet.MIN_EXCLUSIVE, getTypedConstant(dataFactory, minExclusive)));
    }

    public static <N extends Number> OWLDataRange getMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxInclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, maxInclusive), getFacetRestrictionSet(dataFactory, OWLFacet.MAX_INCLUSIVE, getTypedConstant(dataFactory, maxInclusive)));
    }

    public static <N extends Number> OWLDataRange getMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N maxExclusive) {
        return dataFactory.getOWLDatatypeRestriction(getDatatype(dataFactory, maxExclusive), getFacetRestrictionSet(dataFactory, OWLFacet.MAX_EXCLUSIVE, getTypedConstant(dataFactory, maxExclusive)));
    }

    public static <N extends Number> OWLDataRange getMinMaxInclusiveRestrictedInteger(OWLDataFactory dataFactory, N minInclusive, N maxInclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minInclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getTypedConstant(dataFactory, minInclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, getTypedConstant(dataFactory, maxInclusive)));

        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }

    public static <N extends Number> OWLDataRange getMinMaxExclusiveRestrictedInteger(OWLDataFactory dataFactory, N minExclusive, N maxExclusive) {
        OWLDatatype dr = getDatatype(dataFactory, minExclusive);
        Set<OWLFacetRestriction> facetRestrictions = new HashSet<OWLFacetRestriction>();
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getTypedConstant(dataFactory, minExclusive)));
        facetRestrictions.add(dataFactory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, getTypedConstant(dataFactory, maxExclusive)));

        return dataFactory.getOWLDatatypeRestriction(dr, facetRestrictions);
    }


}
