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

import static org.semanticweb.owlapi.vocab.Namespaces.BuiltIn.*;
import static org.semanticweb.owlapi.vocab.Namespaces.Status.IN_USE;

import java.util.EnumSet;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public enum Namespaces {
    //@formatter:off
    // OWL2XML("http://www.w3.org/2006/12/owl2-xml#"),
    /** The OWL 2 namespace. */    OWL2        ("owl2",     "http://www.w3.org/2006/12/owl2#",      Status.LEGACY),
    /** Status.LEGACY. */          OWL11XML    ("owl11xml", "http://www.w3.org/2006/12/owl11-xml#", Status.LEGACY),
    /** The OWL 1.1 namespace. */  OWL11       ("owl11",    "http://www.w3.org/2006/12/owl11#",     Status.LEGACY),
    /**The OWL namespace. */       OWL         ("owl",      "http://www.w3.org/2002/07/owl#", IN_USE),
    /**The RDFS namespace. */      RDFS        ("rdfs",     "http://www.w3.org/2000/01/rdf-schema#", IN_USE),
    /** The RDF namespace. */      RDF         ("rdf",      "http://www.w3.org/1999/02/22-rdf-syntax-ns#", IN_USE),
    /** The XSD namespace. */      XSD         ("xsd",      "http://www.w3.org/2001/XMLSchema#",           IN_USE),
    /** The XML namespace. */      XML         ("xml",      "http://www.w3.org/XML/1998/namespace"),
    /** The SWRL namespace. */     SWRL        ("swrl",     "http://www.w3.org/2003/11/swrl#"),
    /** The SWRLB namespace. */    SWRLB       ("swrlb",    "http://www.w3.org/2003/11/swrlb#"),
    /** The SKOS namespace. */     SKOS        ("skos",     "http://www.w3.org/2004/02/skos/core#"),

    // Further namespaces from the RDFa Core Initial Context
    // http://www.w3.org/2011/rdfa-context/rdfa-1.1
    /** The GRDDL namespace. */    GRDDL       ("grddl",    "http://www.w3.org/2003/g/data-view#"),
    /** The MA namespace. */       MA          ("ma",       "http://www.w3.org/ns/ma-ont#"),
    /** The PROV namespace. */     PROV        ("prov",     "http://www.w3.org/ns/prov#"),
    /** The RDFA namespace. */     RDFA        ("rdfa",     "http://www.w3.org/ns/rdfa#"),
    /** The RIF namespace. */      RIF         ("rif",      "http://www.w3.org/2007/rif#"),
    /** The R2RML namespace. */    R2RML       ("rr",       "http://www.w3.org/ns/r2rml#"),
    /** The SD namespace. */       SD          ("sd",       "http://www.w3.org/ns/sparql-service-description#"),
    /** The SKOSXL namespace. */   SKOSXL      ("skosxl",   "http://www.w3.org/2008/05/skos-xl#"),
    /** The POWDER namespace. */   POWDER      ("wdr",      "http://www.w3.org/2007/05/powder#"),
    /** The VOID namespace. */     VOID        ("void",     "http://rdfs.org/ns/void#"),
    /** The POWDERS namespace. */  POWDERS     ("wdrs",     "http://www.w3.org/2007/05/powder-s#"),
    /** The XHV namespace. */      XHV         ("xhv",      "http://www.w3.org/1999/xhtml/vocab#"),
    /** The ORG namespace. */      ORG         ("org",      "http://www.w3.org/ns/org#"),
    /** The GLDP namespace. */     GLDP        ("gldp",     "http://www.w3.org/ns/people#"),
    /** The CNT namespace. */      CNT         ("cnt",      "http://www.w3.org/2008/content#"),
    /** The DCAT namespace. */     DCAT        ("dcat",     "http://www.w3.org/ns/dcat#"),
    /** The EARL namespace. */     EARL        ("earl",     "http://www.w3.org/ns/earl#"),
    /** The HT namespace. */       HT          ("ht",       "http://www.w3.org/2006/http#"),
    /** The PTR namespace. */      PTR         ("ptr",      "http://www.w3.org/2009/pointers#"),

    // Other widely used Semantic Web prefixes
    /** The CC namespace. */       CC          ("cc",       "http://creativecommons.org/ns#"),
    /** The CTAG namespace. */     CTAG        ("ctag",     "http://commontag.org/ns#"),
    /** The DCTERMS namespace. */  DCTERMS     ("dcterms",  "http://purl.org/dc/terms/"),
    /** The DC namespace. */       DC          ("dc",       "http://purl.org/dc/elements/1.1/"),
    /** The FOAF namespace. */     FOAF        ("foaf",     "http://xmlns.com/foaf/0.1/"),
    /** The GR namespace. */       GR          ("gr",       "http://purl.org/goodrelations/v1#"),
    /** The ICAL namespace. */     ICAL        ("ical",     "http://www.w3.org/2002/12/cal/icaltzd#"),
    /** The OG namespace. */       OG          ("og",       "http://ogp.me/ns#"),
    /** The REV namespace. */      REV         ("rev",      "http://purl.org/stuff/rev#"),
    /** The SIOC namespace. */     SIOC        ("sioc",     "http://rdfs.org/sioc/ns#"),
    /** The VCARD namespace. */    VCARD       ("vcard",    "http://www.w3.org/2006/vcard/ns#"),
    /** The SCHEMA namespace. */   SCHEMA      ("schema",   "http://schema.org/"),
    /** The GEO namespace. */      GEO         ("geo",      "http://www.w3.org/2003/01/geo/wgs84_pos#"),
    /** The SC namespace. */       SC          ("sc",       "http://purl.org/science/owl/sciencecommons/"),
    /** The FB namespace. */       FB          ("fb",       "http://rdf.freebase.com/ns/",                 Status.LEGACY),
    /** The GEONAMES namespace. */ GEONAMES    ("geonames", "http://www.geonames.org/ontology#",           Status.LEGACY),

    // DBpedia
    /** The DBPEDIA namespace. */  DBPEDIA     ("dbpedia", "http://dbpedia.org/resource/"),
    /** The DBP namespace. */      DBP         ("dbp",     "http://dbpedia.org/property/"),
    /** The DBO namespace. */      DBO         ("dbo",     "http://dbpedia.org/ontology/"),
    /** The YAGO namespace. */     YAGO        ("yago",    "http://dbpedia.org/class/yago/");
    //@formatter:on
    @Nonnull
    final String prefix;
    @Nonnull
    final String ns;
    final Status status;
    final BuiltIn builtIn;
    final String hashless;

    Namespaces(@Nonnull String prefix, @Nonnull String ns) {
        this(prefix, ns, IN_USE, NOT_BUILT_IN);
    }

    Namespaces(@Nonnull String prefix, @Nonnull String ns, Status status) {
        this(prefix, ns, status, status == Status.LEGACY ? NOT_BUILT_IN
                : BUILT_IN);
    }

    Namespaces(@Nonnull String prefix, @Nonnull String ns, Status status,
            BuiltIn builtIn) {
        this.prefix = prefix;
        this.ns = ns;
        this.status = status;
        this.builtIn = builtIn;
        hashless = hashless(prefix);
    }

    /**
     * @return A short, human-readable, prefix name that matches, and expands to
     *         the full IRI.
     */
    @Nonnull
    public String getPrefixName() {
        return prefix;
    }

    /** @return The prefix IRI which matches the prefix name. */
    @Nonnull
    public String getPrefixIRI() {
        return ns;
    }

    /**
     * @return {@code true} if this namespace is not obsolete and is currently
     *         in active use, otherwise {@code false}.
     */
    public boolean isInUse() {
        return status == IN_USE;
    }

    /**
     * @return {@code true} if this namespace is defined as a core part of the
     *         OWL-2 specification, otherwise {@code false}.
     */
    public boolean isBuiltIn() {
        return builtIn == BUILT_IN;
    }

    /**
     * @param ns
     *        namespace
     * @return this namespace without hash or slash at the end
     */
    private static String hashless(@Nonnull String ns) {
        int index = ns.length() - 1;
        if (ns.charAt(index) == '/' || ns.charAt(index) == '#') {
            return ns.substring(0, index);
        }
        return ns;
    }

    /** Ignored imports. */
    public static final EnumSet<Namespaces> defaultIgnoredImports = EnumSet.of(
            OWL, RDF, RDFS, SWRL, SWRLB, XML, XSD);

    /**
     * @param i
     *        the iri to check
     * @return true if the iri is for a namespace ignored by default
     */
    public static boolean isDefaultIgnoredImport(IRI i) {
        return defaultIgnoredImports.stream().anyMatch(
                n -> n.hashless.equals(i.toString()));
    }

    /**
     * @param i
     *        the string to check
     * @return true if the string is for a namespace ignored by default
     */
    public static boolean isDefaultIgnoredImport(String i) {
        return defaultIgnoredImports.stream().anyMatch(
                n -> n.hashless.equals(i));
    }

    @Nonnull
    @Override
    public String toString() {
        return ns;
    }

    /**
     * @param s
     *        string to check
     * @return true if s equals this namespace
     */
    public boolean inNamespace(String s) {
        return ns.equals(s);
    }

    /**
     * @param i
     *        iri to check
     * @return true if the namespace for i equals this namespace
     */
    public boolean inNamespace(IRI i) {
        return ns.equals(i.getNamespace());
    }

    /**
     * Indicates that a prefix is builtin - i.e. that it is either owl, rdf,
     * rdfs, or xsd
     */
    public enum BuiltIn {
        /** built in flag. */
        BUILT_IN,
        /** not built in flag. */
        NOT_BUILT_IN
    }

    /** Indicates whether a prefix is a legacy prefix or not. */
    public enum Status {
        /** legacy flag. */
        LEGACY,
        /** in use flag. */
        IN_USE
    }
}
