/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asMap;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;
import static org.semanticweb.owlapi.vocab.Namespaces.OWL;
import static org.semanticweb.owlapi.vocab.Namespaces.RDF;
import static org.semanticweb.owlapi.vocab.Namespaces.RDFS;
import static org.semanticweb.owlapi.vocab.OWLFacet.LANG_RANGE;
import static org.semanticweb.owlapi.vocab.OWLFacet.LENGTH;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_INCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_LENGTH;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_LENGTH;
import static org.semanticweb.owlapi.vocab.OWLFacet.PATTERN;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.ANY_URI;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.BASE_64_BINARY;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.BOOLEAN;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.BYTE;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.DATE_TIME;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.DATE_TIME_STAMP;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.DECIMAL;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.DOUBLE;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.FLOAT;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.HEX_BINARY;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.INT;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.INTEGER;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.LANGUAGE;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.LONG;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NAME;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NCNAME;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NEGATIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NMTOKEN;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NON_NEGATIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NON_POSITIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.NORMALIZED_STRING;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.POSITIVE_INTEGER;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.SHORT;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.STRING;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.TOKEN;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.UNSIGNED_BYTE;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.UNSIGNED_INT;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.UNSIGNED_LONG;
import static org.semanticweb.owlapi.vocab.XSDVocabulary.UNSIGNED_SHORT;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.providers.DatatypeProvider;

/**
 * An enumeration of the datatypes in the OWL 2 specification. These are the datatypes in the OWL 2
 * datatype map.
 *
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public enum OWL2Datatype implements HasIRI, HasShortForm, HasPrefixedName {
//@formatter:off
    /** RDF_XML_LITERAL. */          RDF_XML_LITERAL          (RDF,  "XMLLiteral",   Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, ".*"), 
    /** RDFS_LITERAL. */             RDFS_LITERAL             (RDFS, "Literal",      Category.CAT_UNIVERSAL,                   false, ".*"),
    /** RDF_PLAIN_LITERAL. */        RDF_PLAIN_LITERAL        (RDF,  "PlainLiteral", Category.CAT_STRING_WITH_LANGUAGE_TAG,    false, ".*"),
    /** RDF_LANG_STRING. */          RDF_LANG_STRING          (RDF,  "langString",   Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),
    /** OWL_REAL. */                 OWL_REAL                 (OWL,  "real",         Category.CAT_NUMBER,                      false, ".*"),
    /** OWL_RATIONAL. */             OWL_RATIONAL             (OWL,  "rational",     Category.CAT_NUMBER,                      false, "(\\+|-)?([0-9]+)(\\s)*(/)(\\s)*([0-9]+)"),
    /** XSD_STRING. */               XSD_STRING               (STRING,               Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),
    /** XSD_NORMALIZED_STRING. */    XSD_NORMALIZED_STRING    (NORMALIZED_STRING,    Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, "([^\\r\\n\\t])*"),
    /** XSD_TOKEN. */                XSD_TOKEN                (TOKEN,                Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, "([^\\s])(\\s([^\\s])|([^\\s]))*"),
    /** XSD_LANGUAGE. */             XSD_LANGUAGE             (LANGUAGE,             Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, true,  "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*"),
    /** XSD_NAME. */                 XSD_NAME                 (NAME,                 Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, ":|[A-Z]|_|[a-z]|[\\u00C0-\\u00D6]|[\\u00D8-\\u00F6]|[\\u00F8-\\u02FF]|[\\u0370-\\u037D]|[\\u037F-\\u1FFF]|[\\u200C-\\u200D]|[\\u2070-\\u218F]|[\\u2C00-\\u2FEF]|[\\u3001-\\uD7FF]|[\\uF900-\\uFDCF]|[\\uFDF0-\\uFFFD](:|[A-Z]|_|[a-z]|[\\u00C0-\\u00D6]|[\\u00D8-\\u00F6]|[\\u00F8-\\u02FF]|[\\u0370-\\u037D]|[\\u037F-\\u1FFF]|[\\u200C-\\u200D]|[\\u2070-\\u218F]|[\\u2C00-\\u2FEF]|[\\u3001-\\uD7FF]|[\\uF900-\\uFDCF]|[\\uFDF0-\\uFFFD]|\"-\"|\".\"|[0-9]|\\u00B7|[\\u0300-\\u036F]|[\\u203F-\\u2040])*"),
    /** XSD_NCNAME. */               XSD_NCNAME               (NCNAME,               Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, "[A-Z]|_|[a-z]|[\\u00C0-\\u00D6]|[\\u00D8-\\u00F6]|[\\u00F8-\\u02FF]|[\\u0370-\\u037D]|[\\u037F-\\u1FFF]|[\\u200C-\\u200D]|[\\u2070-\\u218F]|[\\u2C00-\\u2FEF]|[\\u3001-\\uD7FF]|[\\uF900-\\uFDCF]|[\\uFDF0-\\uFFFD]([A-Z]|_|[a-z]|[\\u00C0-\\u00D6]|[\\u00D8-\\u00F6]|[\\u00F8-\\u02FF]|[\\u0370-\\u037D]|[\\u037F-\\u1FFF]|[\\u200C-\\u200D]|[\\u2070-\\u218F]|[\\u2C00-\\u2FEF]|[\\u3001-\\uD7FF]|[\\uF900-\\uFDCF]|[\\uFDF0-\\uFFFD]|\"-\"|\".\"|[0-9]|\\u00B7|[\\u0300-\\u036F]|[\\u203F-\\u2040])*"),
    /** XSD_NMTOKEN. */              XSD_NMTOKEN              (NMTOKEN,              Category.CAT_STRING_WITHOUT_LANGUAGE_TAG, false, ".*"),
    /** XSD_DECIMAL. */              XSD_DECIMAL              (DECIMAL,              Category.CAT_NUMBER,  false, "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)"),
    /** XSD_INTEGER. */              XSD_INTEGER              (INTEGER,              Category.CAT_NUMBER,  false, "(\\+|-)?([0-9]+)"),
    /** XSD_NON_NEGATIVE_INTEGER. */ XSD_NON_NEGATIVE_INTEGER (NON_NEGATIVE_INTEGER, Category.CAT_NUMBER,  false, "((\\+)?([0-9]+))|-(0+)"),
    /** XSD_NON_POSITIVE_INTEGER. */ XSD_NON_POSITIVE_INTEGER (NON_POSITIVE_INTEGER, Category.CAT_NUMBER,  false, "-([0-9]+)|(\\+(0+))"),
    /** XSD_POSITIVE_INTEGER. */     XSD_POSITIVE_INTEGER     (POSITIVE_INTEGER,     Category.CAT_NUMBER,  false, "(\\+)?([0-9]+)"),
    /** XSD_NEGATIVE_INTEGER. */     XSD_NEGATIVE_INTEGER     (NEGATIVE_INTEGER,     Category.CAT_NUMBER,  false, "-([0-9]+)"),
    /** XSD_LONG. */                 XSD_LONG                 (LONG,                 Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+)"),
    /** XSD_INT. */                  XSD_INT                  (INT,                  Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+)"),
    /** XSD_SHORT. */                XSD_SHORT                (SHORT,                Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+)"),
    /** XSD_BYTE. */                 XSD_BYTE                 (BYTE,                 Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+)"),
    /** XSD_UNSIGNED_LONG. */        XSD_UNSIGNED_LONG        (UNSIGNED_LONG,        Category.CAT_NUMBER,  true,  "(\\+)?([0-9]+)"),
    /** XSD_UNSIGNED_INT. */         XSD_UNSIGNED_INT         (UNSIGNED_INT,         Category.CAT_NUMBER,  true,  "(\\+)?([0-9]+)"),
    /** XSD_UNSIGNED_SHORT. */       XSD_UNSIGNED_SHORT       (UNSIGNED_SHORT,       Category.CAT_NUMBER,  true,  "(\\+)?([0-9]+)"),
    /** XSD_UNSIGNED_BYTE. */        XSD_UNSIGNED_BYTE        (UNSIGNED_BYTE,        Category.CAT_NUMBER,  true,  "(\\+)?([0-9]+)"),
    /** XSD_DOUBLE. */               XSD_DOUBLE               (DOUBLE,               Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([Ee](\\+|-)?[0-9]+)?|(\\+|-)?INF|NaN"),
    /** XSD_FLOAT. */                XSD_FLOAT                (FLOAT,                Category.CAT_NUMBER,  true,  "(\\+|-)?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([Ee](\\+|-)?[0-9]+)?|(\\+|-)?INF|NaN"),
    /** XSD_BOOLEAN. */              XSD_BOOLEAN              (BOOLEAN,              Category.CAT_BOOLEAN, true,  "true|false|1|0"),
    /** XSD_HEX_BINARY. */           XSD_HEX_BINARY           (HEX_BINARY,           Category.CAT_BINARY,  false, "([0-9a-fA-F]{2})*"),
    /** XSD_BASE_. */                XSD_BASE_64_BINARY       (BASE_64_BINARY,       Category.CAT_BINARY,  false, "((([A-Za-z0-9+/] ?){4})*(([A-Za-z0-9+/] ?){3}[A-Za-z0-9+/]|([A-Za-z0-9+/] ?){2}[AEIMQUYcgkosw048] ?=|[A-Za-z0-9+/] ?[AQgw] ?= ?=))?"),
    /** XSD_ANY_URI. */              XSD_ANY_URI              (ANY_URI,              Category.CAT_URI,     false, ".*"),
    /** XSD_DATE_TIME. */            XSD_DATE_TIME            (DATE_TIME,            Category.CAT_TIME,    false, "-?([1-9][0-9]{3,}|0[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\\.[0-9]+)?|(24:00:00(\\.0+)?))(Z|(\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))?"),
    /** XSD_DATE_TIME_STAMP. */      XSD_DATE_TIME_STAMP      (DATE_TIME_STAMP,      Category.CAT_TIME,    false, "-?([1-9][0-9]{3,}|0[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\\\\.[0-9]+)?|(24:00:00(\\\\.0+)?))(Z|(\\\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))");
//@formatter:on
    /**
     * Datatypes allowed in the EL and QL profiles.
     */
    public static final List<OWL2Datatype> EL_DATATYPES =
        Arrays.asList(RDF_PLAIN_LITERAL, RDF_XML_LITERAL, RDFS_LITERAL, OWL_RATIONAL, OWL_REAL,
            XSD_DECIMAL, XSD_INTEGER, XSD_NON_NEGATIVE_INTEGER, XSD_STRING, XSD_NORMALIZED_STRING,
            XSD_TOKEN, XSD_NAME, XSD_NCNAME, XSD_NMTOKEN, XSD_HEX_BINARY, XSD_BASE_64_BINARY,
            XSD_ANY_URI, XSD_DATE_TIME, XSD_DATE_TIME_STAMP);
    /**
     * Datatypes supported in the RL profile.
     */
    public static final List<OWL2Datatype> RL_DATATYPES =
        Arrays.asList(RDF_PLAIN_LITERAL, RDF_XML_LITERAL, RDFS_LITERAL, XSD_DECIMAL, XSD_INTEGER,
            XSD_NON_NEGATIVE_INTEGER, XSD_NON_POSITIVE_INTEGER, XSD_POSITIVE_INTEGER,
            XSD_NEGATIVE_INTEGER, XSD_LONG, XSD_INT, XSD_SHORT, XSD_BYTE, XSD_UNSIGNED_LONG,
            XSD_UNSIGNED_BYTE, XSD_FLOAT, XSD_DOUBLE, XSD_STRING, XSD_NORMALIZED_STRING, XSD_TOKEN,
            XSD_LANGUAGE, XSD_NAME, XSD_NCNAME, XSD_NMTOKEN, XSD_BOOLEAN, XSD_HEX_BINARY,
            XSD_BASE_64_BINARY, XSD_ANY_URI, XSD_DATE_TIME, XSD_DATE_TIME_STAMP);
    private static final Map<IRI, OWL2Datatype> ALL_IRIS = asMap(stream(), HasIRI::getIRI);
    private final String shortForm;
    private final IRI iri;
    private final Category category;
    private final boolean finite;
    private final Pattern pattern;
    private final String regExpression;
    private final String prefixedName;

    OWL2Datatype(Namespaces namespace, String shortForm, Category category, boolean finite,
        String regEx) {
        iri = IRI.create(namespace.toString(), shortForm);
        this.shortForm = shortForm;
        prefixedName = namespace.getPrefixName() + ':' + shortForm;
        this.category = category;
        this.finite = finite;
        regExpression = regEx;
        pattern = Pattern.compile(regEx, Pattern.DOTALL);
    }

    OWL2Datatype(XSDVocabulary xsd, Category category, boolean finite, String regEx) {
        iri = xsd.getIRI();
        shortForm = xsd.getShortForm();
        prefixedName = xsd.getPrefixedName();
        this.category = category;
        this.finite = finite;
        regExpression = regEx;
        pattern = Pattern.compile(regEx, Pattern.DOTALL);
    }

    private static Stream<OWL2Datatype> stream() {
        return Stream.of(values());
    }

    /**
     * Gets all of the built in datatype IRIs.
     *
     * @return A set of IRIs corresponding to the set of IRIs of all built in {@code OWL2Datatype}s.
     */
    public static Set<IRI> getDatatypeIRIs() {
        return ALL_IRIS.keySet();
    }

    /**
     * Determines if the specified IRI identifies a built in datatype.
     *
     * @param datatypeIRI The datatype IRI
     * @return {@code true} if the IRI identifies a built in datatype, or {@code false} if the IRI
     *         does not identify a built in datatype.
     */
    public static boolean isBuiltIn(IRI datatypeIRI) {
        return ALL_IRIS.containsKey(datatypeIRI);
    }

    /**
     * Given an IRI that identifies an {@link OWLDatatype}, this method obtains the corresponding
     * {@code OWL2Datatype}.
     *
     * @param datatype The datatype IRI. Not {@code null}.
     * @return The {@code OWL2Datatype} that has the specified {@link IRI}.
     * @throws OWLRuntimeException if the specified IRI is not a built in datatype IRI.
     */
    public static OWL2Datatype getDatatype(IRI datatype) {
        OWL2Datatype knownDatatype = ALL_IRIS.get(datatype);
        if (knownDatatype == null) {
            throw new OWLRuntimeException(datatype + " is not a built in datatype!");
        }
        return knownDatatype;
    }

    /**
     * Given an IRI that identifies an {@link OWLDatatype}, this method obtains the corresponding
     * {@code OWL2Datatype}.
     *
     * @param datatype The datatype IRI. Not {@code null}.
     * @return The {@code OWL2Datatype} that has the specified {@link IRI}.
     * @throws OWLRuntimeException if the specified IRI is not a built in datatype IRI.
     */
    public static OWL2Datatype getDatatype(HasIRI datatype) {
        return getDatatype(datatype.getIRI());
    }

    /**
     * Gets the Pattern that specifies the regular expression for the allowed lexical values of a
     * datatype.
     *
     * @return The Pattern, or {@code null}
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Gets the Pattern string that specifies the regular expression for the allowed lexical values
     * of a datatype.
     *
     * @return The Pattern string. Not null.
     */
    public String getPatternString() {
        return regExpression;
    }

    @Override
    public String getShortForm() {
        return shortForm;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * Gets the category for this datatype.
     *
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Determines if this datatype is a numeric datatype.
     *
     * @return {@code true} if this datatype is a numeric datatype
     */
    public boolean isNumeric() {
        return category.equals(Category.CAT_NUMBER);
    }

    /**
     * Determines whether or not this datatype is finite.
     *
     * @return {@code true} if this datatype is finite, or {@code false} if this datatype is
     *         infinite.
     */
    public boolean isFinite() {
        return finite;
    }

    /**
     * Gets the facets that are allowed for facet restrictions of this datatype.
     *
     * @return The allowed facets
     */
    public Collection<OWLFacet> getFacets() {
        return category.getFacets();
    }

    /**
     * Gets the equivalent OWLDatatype from the given factory.
     *
     * @param factory the OWLDataFactory.
     * @return An {@link OWLDatatype} that has the same IRI as this {@code OWL2Datatype}.
     */
    public OWLDatatype getDatatype(DatatypeProvider factory) {
        checkNotNull(factory, "factory cannot be null");
        return factory.getOWLDatatype(this);
    }

    /**
     * Determines if the specified string is the lexical space of this datatype.
     *
     * @param s The string to test
     * @return {@code true} if the string is in the lexical space, otherwise {@code false}
     */
    public boolean isInLexicalSpace(String s) {
        return pattern.matcher(s).matches();
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }

    /**
     * @param e entity to check
     * @return true if the entity and the enum value have the same IRI
     */
    public boolean matches(OWLEntity e) {
        return e.getIRI().equals(iri);
    }

    /**
     * Category enum.
     */
    public enum Category {
        //@formatter:off
        /** NUMBER.                      */ CAT_NUMBER                     ("Number",                        MIN_INCLUSIVE, MAX_INCLUSIVE, MIN_EXCLUSIVE, MAX_EXCLUSIVE), 
        /** STRING_WITH_LANGUAGE_TAG.    */ CAT_STRING_WITH_LANGUAGE_TAG   ("String with a language tag",    MIN_LENGTH,    MAX_LENGTH,    LENGTH,        PATTERN,      LANG_RANGE), 
        /** STRING_WITHOUT_LANGUAGE_TAG. */ CAT_STRING_WITHOUT_LANGUAGE_TAG("String without a language tag", MIN_LENGTH,    MAX_LENGTH,    LENGTH,        PATTERN), 
        /** BINARY.                      */ CAT_BINARY                     ("Binary data",                   MIN_LENGTH,    MAX_LENGTH,    LENGTH), 
        /** URI.                         */ CAT_URI                        ("URI",                           MIN_LENGTH,    MAX_LENGTH,    PATTERN), 
        /** TIME.                        */ CAT_TIME                       ("Time instant",                  MIN_INCLUSIVE, MAX_INCLUSIVE, MIN_EXCLUSIVE, MAX_EXCLUSIVE),
        /** BOOLEAN.                     */ CAT_BOOLEAN                    ("Boolean value"), 
        /** UNIVERSAL.                   */ CAT_UNIVERSAL                  ("Universal literal");
        //@formatter:on
        private final String name;
        private final Set<OWLFacet> facets;

        Category(String name, OWLFacet... facets) {
            this.name = name;
            this.facets = createSet(facets);
        }

        /**
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * @return facets
         * @deprecated use {@link #facets()}
         */
        @Deprecated
        public Set<OWLFacet> getFacets() {
            return asSet(facets());
        }

        /**
         * @return facets
         */
        public Stream<OWLFacet> facets() {
            return facets.stream();
        }
    }

    /**
     * Normalization enum.
     */
    public enum WhiteSpaceNormalisation {
        /**
         * No normalization is done, the value is not changed (this is the behavior required by
         * [XML] for element content).
         */
        PRESERVE {
            @Override
            public String getNormalisedString(String s) {
                return s;
            }
        },
        /**
         * All occurrences of #x9 (tab), #xA (line feed) and #xD (carriage return) are replaced with
         * #x20 (space).
         */
        REPLACE {
            @Override
            public String getNormalisedString(String s) {
                return s.replaceAll("\\t|\\n|\\r", " ");
            }
        },
        /**
         * After the processing implied by replace, contiguous sequences of #x20's are collapsed to
         * a single #x20, and any #x20 at the start or end of the string is then removed.
         */
        COLLAPSE {
            @Override
            public String getNormalisedString(String s) {
                return REPLACE.getNormalisedString(s).replaceAll("\\s+", " ").trim();
            }
        };

        /**
         * Gets the normalised version of a string.
         *
         * @param s The string to normalise
         * @return The normalised string
         */
        public abstract String getNormalisedString(String s);
    }
}
