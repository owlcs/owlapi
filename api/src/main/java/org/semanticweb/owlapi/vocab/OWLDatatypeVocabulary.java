package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
import java.util.*;
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
 */
public enum OWLDatatypeVocabulary {

    OWL_REAL(Namespaces.OWL, "real", Category.NUMBER, false),

    OWL_RATIONAL(Namespaces.OWL, "rational", Category.NUMBER, false),

    XSD_DOUBLE(XSDVocabulary.DOUBLE, Category.NUMBER, true),

    XSD_FLOAT(XSDVocabulary.FLOAT, Category.NUMBER, true),

    XSD_DECIMAL(XSDVocabulary.DECIMAL, Category.NUMBER, false),

    XSD_INTEGER(XSDVocabulary.INTEGER, Category.NUMBER, false),

    XSD_NON_NEGATIVE_INTEGER(XSDVocabulary.NON_NEGATIVE_INTEGER, Category.NUMBER, false),

    XSD_NON_POSITIVE_INTEGER(XSDVocabulary.NON_POSITIVE_INTEGER, Category.NUMBER, false),

    XSD_POSITIVE_INTEGER(XSDVocabulary.POSITIVE_INTEGER, Category.NUMBER, false),

    XSD_NEGATIVE_INTEGER(XSDVocabulary.NEGATIVE_INTEGER, Category.NUMBER, false),

    XSD_LONG(XSDVocabulary.LONG, Category.NUMBER, true),

    XSD_INT(XSDVocabulary.INT, Category.NUMBER, true),

    XSD_SHORT(XSDVocabulary.SHORT, Category.NUMBER, true),

    XSD_BYTE(XSDVocabulary.BYTE, Category.NUMBER, true),

    XSD_UNSIGNED_LONG(XSDVocabulary.UNSIGNED_LONG, Category.NUMBER, true),

    XSD_UNSIGNED_INT(XSDVocabulary.UNSIGNED_INT, Category.NUMBER, true),

    XSD_UNSIGNED_SHORT(XSDVocabulary.UNSIGNED_SHORT, Category.NUMBER, true),

    XSD_UNSIGNED_BYTE(XSDVocabulary.UNSIGNED_BYTE, Category.NUMBER, true),

    XSD_STRING(XSDVocabulary.STRING, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_NORMALIZED_STRING(XSDVocabulary.NORMALIZED_STRING, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_TOKEN(XSDVocabulary.TOKEN, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_LANGUAGE(XSDVocabulary.LANGUAGE, Category.STRING_WITHOUT_LANGUAGE_TAG, true),

    XSD_NAME(XSDVocabulary.NAME, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_NCNAME(XSDVocabulary.NCNAME, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_NMTOKEN(XSDVocabulary.NMTOKEN, Category.STRING_WITHOUT_LANGUAGE_TAG, false),

    XSD_BOOLEAN(XSDVocabulary.BOOLEAN, Category.BOOLEAN, true),

    XSD_HEX_BINARY(XSDVocabulary.HEX_BINARY, Category.BINARY, false),

    XSD_BASE_64_BINARY(XSDVocabulary.BASE_64_BINARY, Category.BINARY, false),

    XSD_ANY_URI(XSDVocabulary.ANY_URI, Category.URI, false),

    OWL_DATE_TIME(Namespaces.OWL, "dateTime", Category.TIME, false),

    RDF_TEXT(Namespaces.RDF, "text", Category.STRING_WITH_LANGUAGE_TAG, false);

    private static final Set<URI> ALL_URIS;


    static {
        Set<URI> uris = new HashSet<URI>();
        for (OWLDatatypeVocabulary v : OWLDatatypeVocabulary.values()) {
            uris.add(v.getURI());
        }
        ALL_URIS = Collections.unmodifiableSet(new TreeSet<URI>(uris));
    }


    /**
     * Gets all of the built in datatype URIs
     *
     * @return A set of URIs corresponding to the built in datatype URIs
     */
    public static Set<URI> getDatatypeURIs() {
        return ALL_URIS;
    }


    /**
     * Determines if the specified URI identifies a built in datatype.
     *
     * @param datatypeURI The datatype URI
     * @return <code>true</code> if the URI identifies a built in datatype, or
     *         <code>false</code> if the URI does not identify a built in datatype.
     */
    public static boolean isBuiltIn(URI datatypeURI) {
        return ALL_URIS.contains(datatypeURI);
    }


    /**
     * Given a URI that identifies an OWLDatatype, this method obtains the
     * corresponding OWLDatatypeVocabulary
     *
     * @param datatype The datatype URI
     * @return The OWLDatatypeVocabulary
     * @throws OWLRuntimeException if the specified URI is not a built in datatype URI
     */
    public static OWLDatatypeVocabulary getDatatype(URI datatype) {
        if (!isBuiltIn(datatype)) {
            throw new OWLRuntimeException(datatype + " is not a built in datatype!");
        }
        for (OWLDatatypeVocabulary v : values()) {
            if (v.getURI().equals(datatype)) {
                return v;
            }
        }
        throw new OWLRuntimeException(datatype + " is not a built in datatype!");
    }

    private String shortName;

    private IRI iri;

    private Category category;

    private boolean finite;

    OWLDatatypeVocabulary(Namespaces namespace, String shortName, Category category, boolean finite) {
        this.iri = IRI.create(namespace + shortName);
        this.shortName = shortName;
        this.category = category;
        this.finite = finite;
    }

    OWLDatatypeVocabulary(XSDVocabulary xsd, Category category, boolean finite) {
        this.iri = xsd.getIRI();
        this.shortName = xsd.getShortName();
        this.category = category;
        this.finite = finite;
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


    public enum Category {

        NUMBER("Number",
                OWLFacet.MAX_INCLUSIVE,
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

        private List<OWLFacet> facets;


        Category(String name, OWLFacet... facets) {
            this.name = name;
            List<OWLFacet> f = new ArrayList<OWLFacet>(facets.length);
            for (OWLFacet facet : facets) {
                f.add(facet);
            }
            this.facets = Collections.unmodifiableList(new ArrayList<OWLFacet>(f));
        }


        public String getName() {
            return name;
        }


        public List<OWLFacet> getFacets() {
            return facets;
        }
    }
}
