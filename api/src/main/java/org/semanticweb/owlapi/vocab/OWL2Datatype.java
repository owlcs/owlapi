package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 11-Nov-2008<br><br>
 *
 * An enumeration of the datatypes in the OWL 2 specification.  These are the datatypes in the OWL 2 datatype map.
 */
public enum OWL2Datatype {

    RDF_XML_LITERAL(Namespaces.RDF, "XMLLiteral", Category.STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),

    OWL_REAL(Namespaces.OWL, "real", Category.NUMBER, false, ".*"),

    OWL_RATIONAL(Namespaces.OWL, "rational", Category.NUMBER, false, "(\\+|-)?([0-9]+)(\\s)*(/)(\\s)*([0-9]+)"),

    XSD_DECIMAL(XSDVocabulary.DECIMAL, Category.NUMBER, false, "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)"),

    XSD_INTEGER(XSDVocabulary.INTEGER, Category.NUMBER, false, "(\\+|-)?([0-9]+)"),

    XSD_NON_NEGATIVE_INTEGER(XSDVocabulary.NON_NEGATIVE_INTEGER, Category.NUMBER, false, "((\\+)?([0-9]+))|-(0+)"),

    XSD_NON_POSITIVE_INTEGER(XSDVocabulary.NON_POSITIVE_INTEGER, Category.NUMBER, false, "-([0-9]+)|(\\+(0+))"),

    XSD_POSITIVE_INTEGER(XSDVocabulary.POSITIVE_INTEGER, Category.NUMBER, false, "(\\+)?([0-9]+)"),

    XSD_NEGATIVE_INTEGER(XSDVocabulary.NEGATIVE_INTEGER, Category.NUMBER, false, "-([0-9]+)"),

    XSD_LONG(XSDVocabulary.LONG, Category.NUMBER, true, "(\\+|-)?([0-9]+)"),

    XSD_INT(XSDVocabulary.INT, Category.NUMBER, true, "(\\+|-)?([0-9]+)"),

    XSD_SHORT(XSDVocabulary.SHORT, Category.NUMBER, true, "(\\+|-)?([0-9]+)"),

    XSD_BYTE(XSDVocabulary.BYTE, Category.NUMBER, true, "(\\+|-)?([0-9]+)"),

    XSD_UNSIGNED_LONG(XSDVocabulary.UNSIGNED_LONG, Category.NUMBER, true, "(\\+)?([0-9]+)"),

    XSD_UNSIGNED_INT(XSDVocabulary.UNSIGNED_INT, Category.NUMBER, true, "(\\+)?([0-9]+)"),

    XSD_UNSIGNED_SHORT(XSDVocabulary.UNSIGNED_SHORT, Category.NUMBER, true, "(\\+)?([0-9]+)"),

    XSD_UNSIGNED_BYTE(XSDVocabulary.UNSIGNED_BYTE, Category.NUMBER, true, "(\\+)?([0-9]+)"),

    XSD_DOUBLE(XSDVocabulary.DOUBLE, Category.NUMBER, true, "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([Ee](\\+|-)?[0-9]+)? |(\\+|-)?INF|NaN"),

    XSD_FLOAT(XSDVocabulary.FLOAT, Category.NUMBER, true, "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([Ee](\\+|-)?[0-9]+)?|(\\+|-)?INF|NaN"),

    XSD_STRING(XSDVocabulary.STRING, Category.STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),

    XSD_NORMALIZED_STRING(XSDVocabulary.NORMALIZED_STRING, Category.STRING_WITHOUT_LANGUAGE_TAG, false, "([^\\r\\n\\t])*"),

    XSD_TOKEN(XSDVocabulary.TOKEN, Category.STRING_WITHOUT_LANGUAGE_TAG, false, "([^\\s])(\\s([^\\s])|([^\\s]))*"),

    XSD_LANGUAGE(XSDVocabulary.LANGUAGE, Category.STRING_WITHOUT_LANGUAGE_TAG, true, "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*"),

    XSD_NAME(XSDVocabulary.NAME, Category.STRING_WITHOUT_LANGUAGE_TAG, false, ": | [A-Z] | _ | [a-z] | [\\u00C0-\\u00D6] | [\\u00D8-\\u00F6] | [\\u00F8-\\u02FF] | [\\u0370-\\u037D] | [\\u037F-\\u1FFF] | [\\u200C-\\u200D] | [\\u2070-\\u218F] | [\\u2C00-\\u2FEF] | [\\u3001-\\uD7FF] | [\\uF900-\\uFDCF] | [\\uFDF0-\\uFFFD] (: | [A-Z] | _ | [a-z] | [\\u00C0-\\u00D6] | [\\u00D8-\\u00F6] | [\\u00F8-\\u02FF] | [\\u0370-\\u037D] | [\\u037F-\\u1FFF] | [\\u200C-\\u200D] | [\\u2070-\\u218F] | [\\u2C00-\\u2FEF] | [\\u3001-\\uD7FF] | [\\uF900-\\uFDCF] | [\\uFDF0-\\uFFFD] | \"-\" | \".\" | [0-9] | \\u00B7 | [\\u0300-\\u036F] | [\\u203F-\\u2040])*"),

    XSD_NCNAME(XSDVocabulary.NCNAME, Category.STRING_WITHOUT_LANGUAGE_TAG, false, "[A-Z] | _ | [a-z] | [\\u00C0-\\u00D6] | [\\u00D8-\\u00F6] | [\\u00F8-\\u02FF] | [\\u0370-\\u037D] | [\\u037F-\\u1FFF] | [\\u200C-\\u200D] | [\\u2070-\\u218F] | [\\u2C00-\\u2FEF] | [\\u3001-\\uD7FF] | [\\uF900-\\uFDCF] | [\\uFDF0-\\uFFFD] ([A-Z] | _ | [a-z] | [\\u00C0-\\u00D6] | [\\u00D8-\\u00F6] | [\\u00F8-\\u02FF] | [\\u0370-\\u037D] | [\\u037F-\\u1FFF] | [\\u200C-\\u200D] | [\\u2070-\\u218F] | [\\u2C00-\\u2FEF] | [\\u3001-\\uD7FF] | [\\uF900-\\uFDCF] | [\\uFDF0-\\uFFFD] | \"-\" | \".\" | [0-9] | \\u00B7 | [\\u0300-\\u036F] | [\\u203F-\\u2040])*"),

    XSD_NMTOKEN(XSDVocabulary.NMTOKEN, Category.STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),

    XSD_BOOLEAN(XSDVocabulary.BOOLEAN, Category.BOOLEAN, true, "true|false|1|0"),

    XSD_HEX_BINARY(XSDVocabulary.HEX_BINARY, Category.BINARY, false, "([0-9a-fA-F]{2})*"),

    XSD_BASE_64_BINARY(XSDVocabulary.BASE_64_BINARY, Category.BINARY, false, "((([A-Za-z0-9+/] ?){4})*(([A-Za-z0-9+/] ?){3}[A-Za-z0-9+/]|([A-Za-z0-9+/] ?){2}[AEIMQUYcgkosw048] ?=|[A-Za-z0-9+/] ?[AQgw] ?= ?=))?"),

    XSD_ANY_URI(XSDVocabulary.ANY_URI, Category.URI, false, ".*"),

    XSD_DATE_TIME(XSDVocabulary.DATE_TIME, Category.TIME, false, "-?([1-9][0-9]{3,}|0[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\\.[0-9]+)?|(24:00:00(\\.0+)?))(Z|(\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))?"),

    XSD_DATE_TIME_STAMP(XSDVocabulary.DATE_TIME_STAMP, Category.TIME, false, "-?([1-9][0-9]{3,}|0[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\\\\.[0-9]+)?|(24:00:00(\\\\.0+)?))(Z|(\\\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))");

    
    private static final Set<IRI> ALL_IRIS;



    static {
        Set<IRI> uris = new HashSet<IRI>();
        for (OWL2Datatype v : OWL2Datatype.values()) {
            uris.add(v.getIRI());
        }
        ALL_IRIS = Collections.unmodifiableSet(new TreeSet<IRI>(uris));
    }


    /**
     * Gets all of the built in datatype URIs
     * @return A set of URIs corresponding to the built in datatype URIs
     */
    public static Set<IRI> getDatatypeIRIs() {
        return ALL_IRIS;
    }

    /**
     * Gets the Pattern that specifies the regular expression for a datatype
     * @return The Pattern, or <code>null</code>
     */
    public Pattern getPattern() {
        return pattern;
    }


    /**
     * Determines if the specified IRI identifies a built in datatype.
     * @param datatypeIRI The datatype IRI
     * @return <code>true</code> if the IRI identifies a built in datatype, or
     *         <code>false</code> if the IRI does not identify a built in datatype.
     */
    public static boolean isBuiltIn(IRI datatypeIRI) {
        return ALL_IRIS.contains(datatypeIRI);
    }


    /**
     * Given a URI that identifies an OWLDatatype, this method obtains the
     * corresponding OWLDatatypeVocabulary
     * @param datatype The datatype URI
     * @return The OWLDatatypeVocabulary
     * @throws OWLRuntimeException if the specified URI is not a built in datatype URI
     */
    public static OWL2Datatype getDatatype(IRI datatype) {
        if (!isBuiltIn(datatype)) {
            throw new OWLRuntimeException(datatype + " is not a built in datatype!");
        }
        for (OWL2Datatype v : values()) {
            if (v.getIRI().equals(datatype)) {
                return v;
            }
        }
        throw new OWLRuntimeException(datatype + " is not a built in datatype!");
    }

    private String shortName;

    private IRI iri;

    private Category category;

    private boolean finite;

    private Pattern pattern;

    OWL2Datatype(Namespaces namespace, String shortName, Category category, boolean finite, String regEx) {
        this.iri = IRI.create(namespace + shortName);
        this.shortName = shortName;
        this.category = category;
        this.finite = finite;
        if (regEx != null) {
            this.pattern = Pattern.compile(regEx, Pattern.DOTALL);
        }
    }

    OWL2Datatype(XSDVocabulary xsd, Category category, boolean finite, String regEx) {
        this.iri = xsd.getIRI();
        this.shortName = xsd.getShortName();
        this.category = category;
        this.finite = finite;
        this.pattern = Pattern.compile(regEx, Pattern.DOTALL);
    }


    /**
     * Gets the short human readable name for this datatype
     *
     * @return The short human readable name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Gets the IRI of this datatype
     * @return
     */
    public IRI getIRI() {
        return iri;
    }

    /**
     * Gets the URI of this datatype
     *
     * @return The URI that identifies the datatype
     */
    public URI getURI() {
        return iri.toURI();
    }


    /**
     * Gets the category for this datatype
     *
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Determines if this datatype is a numeric datatype
     * @return <code>true</code> if this datatype is a numeric datatype
     */
    public boolean isNumeric() {
        return category.equals(Category.NUMBER);
    }

    /**
     * Determines whether or not this datatype is finite.
     *
     * @return <code>true</code> if this datatype is finite, or
     *         <code>false</code> if this datatype is infinite.
     */
    public boolean isFinite() {
        return finite;
    }


    /**
     * Gets the facets that are allowed for facet restrictions of this
     * datatype
     *
     * @return The allowed facets
     */
    public Collection<OWLFacet> getFacets() {
        return category.getFacets();
    }


    /**
     * Determines if the specified string is the lexical space of this datatype
     * @param s The string to test
     * @return <code>true</code> if the string is in the lexical space, otherwise <code>false</code>
     */
    public boolean isInLexicalSpace(String s) {
        return pattern.matcher(s).matches();
    }

    public enum Category {

        NUMBER("Number",
                OWLFacet.MIN_INCLUSIVE,
                OWLFacet.MAX_INCLUSIVE,
                OWLFacet.MIN_EXCLUSIVE,
                OWLFacet.MAX_EXCLUSIVE),

        STRING_WITH_LANGUAGE_TAG("String with a language tag",
                OWLFacet.MIN_LENGTH,
                OWLFacet.MAX_LENGTH,
                OWLFacet.LENGTH,
                OWLFacet.PATTERN,
                OWLFacet.LANG_PATTERN),

        STRING_WITHOUT_LANGUAGE_TAG("String without a language tag",
                OWLFacet.MIN_LENGTH,
                OWLFacet.MAX_LENGTH,
                OWLFacet.LENGTH,
                OWLFacet.PATTERN),

        BOOLEAN("Boolean value"),

        BINARY("Binary data",
                OWLFacet.MIN_LENGTH,
                OWLFacet.MAX_LENGTH,
                OWLFacet.LENGTH),

        URI("URI",
                OWLFacet.MIN_LENGTH,
                OWLFacet.MAX_LENGTH,
                OWLFacet.PATTERN),

        TIME("Time instant",
                OWLFacet.MIN_INCLUSIVE,
                OWLFacet.MAX_INCLUSIVE,
                OWLFacet.MIN_EXCLUSIVE,
                OWLFacet.MAX_EXCLUSIVE);

        private String name;

        private Set<OWLFacet> facets;


        Category(String name, OWLFacet... facets) {
            this.name = name;
            Set<OWLFacet> f = new HashSet<OWLFacet>(facets.length);
            f.addAll(Arrays.asList(facets));
            this.facets = Collections.unmodifiableSet(f);
        }


        public String getName() {
            return name;
        }


        public Set<OWLFacet> getFacets() {
            return facets;
        }
    }

    public enum WhiteSpaceNormalisation {

        /** No normalization is done, the value is not changed
         * (this is the behavior required by [XML] for element content)
         */
        PRESERVE,

        /** All occurrences of #x9 (tab), #xA (line feed) and #xD (carriage return)
         * are replaced with #x20 (space)
         */
        REPLACE,

        /**
         * After the processing implied by replace, contiguous sequences of #x20's are collapsed
         * to a single #x20, and any #x20 at the start or end of the string is then removed.
         * */
        COLLAPSE;

        /**
         * Gets the normalised version of a string
         * @param s The string to normalise
         * @return The normalised string
         */
        public String getNormalisedString(String s) {
            switch (this) {
                case REPLACE:
                    return s.replaceAll("\\t|\\n|\\r", " ");
                case COLLAPSE:
                    return REPLACE.getNormalisedString(s).replaceAll("\\s+", " ").trim();
                default:
                    return s;
            }
        }
    }

    public static void main(String[] args) {
        for(OWL2Datatype v : OWL2Datatype.values()) {
            System.out.println(v);
        }

        boolean matches =
        OWL2Datatype.OWL_RATIONAL.getPattern().matcher("22/7").matches();
        System.out.println("MATCH: " + matches);
    }
}
