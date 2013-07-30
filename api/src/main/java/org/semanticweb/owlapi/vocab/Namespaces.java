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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.vocab.Namespaces.BuiltIn.*;
import static org.semanticweb.owlapi.vocab.Namespaces.Status.*;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006 */
@SuppressWarnings("javadoc")
public enum Namespaces {
    //@formatter:off
    // OWL2XML("http://www.w3.org/2006/12/owl2-xml#"),
    /** The OWL 2 namespace is here for legacy reasons. */
    OWL2    ("owl2",    "http://www.w3.org/2006/12/owl2#", LEGACY),
    /** legacy */                                                           
    OWL11XML("owl11xml", "http://www.w3.org/2006/12/owl11-xml#", LEGACY),
    /** The OWL 1.1 namespace is here for legacy reasons. */                
    OWL11   ("owl11",   "http://www.w3.org/2006/12/owl11#", LEGACY),
    OWL     ("owl",     "http://www.w3.org/2002/07/owl#", IN_USE, BUILT_IN),
    RDFS    ("rdfs",    "http://www.w3.org/2000/01/rdf-schema#", IN_USE, BUILT_IN),
    RDF     ("rdf",     "http://www.w3.org/1999/02/22-rdf-syntax-ns#", IN_USE, BUILT_IN),
    XSD     ("xsd",     "http://www.w3.org/2001/XMLSchema#", IN_USE, BUILT_IN),
    XML     ("xml",     "http://www.w3.org/XML/1998/namespace"),
    SWRL    ("swrl",    "http://www.w3.org/2003/11/swrl#"),
    SWRLB   ("swrlb",   "http://www.w3.org/2003/11/swrlb#"),
    SKOS    ("skos",    "http://www.w3.org/2004/02/skos/core#"),
    // Further namespaces from the RDFa Core Initial Context                       
    // http://www.w3.org/2011/rdfa-context/rdfa-1.1                                
    GRDDL   ("grddl",   "http://www.w3.org/2003/g/data-view#"),
    MA      ("ma",      "http://www.w3.org/ns/ma-ont#"),
    PROV    ("prov",    "http://www.w3.org/ns/prov#"),
    RDFA    ("rdfa",    "http://www.w3.org/ns/rdfa#"),
    RIF     ("rif",     "http://www.w3.org/2007/rif#"),
    R2RML   ("rr",      "http://www.w3.org/ns/r2rml#"),
    SD      ("sd",      "http://www.w3.org/ns/sparql-service-description#"),
    SKOSXL  ("skosxl",  "http://www.w3.org/2008/05/skos-xl#"),
    POWDER  ("wdr",     "http://www.w3.org/2007/05/powder#"),
    VOID    ("void",    "http://rdfs.org/ns/void#"),
    POWDERS ("wdrs",    "http://www.w3.org/2007/05/powder-s#"),
    XHV     ("xhv",     "http://www.w3.org/1999/xhtml/vocab#"),
    ORG     ("org",     "http://www.w3.org/ns/org#"),
    GLDP    ("gldp",    "http://www.w3.org/ns/people#"),
    CNT     ("cnt",     "http://www.w3.org/2008/content#"),
    DCAT    ("dcat",    "http://www.w3.org/ns/dcat#"),
    EARL    ("earl",    "http://www.w3.org/ns/earl#"),
    HT      ("ht",      "http://www.w3.org/2006/http#"),
    PTR     ("ptr",     "http://www.w3.org/2009/pointers#"),
    // Other widely used Semantic Web prefixes                                     
    CC      ("cc",      "http://creativecommons.org/ns#"),
    CTAG    ("ctag",    "http://commontag.org/ns#"),
    DCTERMS ("dcterms", "http://purl.org/dc/terms/"),
    DC      ("dc",      "http://purl.org/dc/elements/1.1/"),
    FOAF    ("foaf",    "http://xmlns.com/foaf/0.1/"),
    GR      ("gr",      "http://purl.org/goodrelations/v1#"),
    ICAL    ("ical",    "http://www.w3.org/2002/12/cal/icaltzd#"),
    OG      ("og",      "http://ogp.me/ns#"),
    REV     ("rev",     "http://purl.org/stuff/rev#"),
    SIOC    ("sioc",    "http://rdfs.org/sioc/ns#"),
    VCARD   ("vcard",   "http://www.w3.org/2006/vcard/ns#"),
    SCHEMA  ("schema",  "http://schema.org/"),
    GEO     ("geo",     "http://www.w3.org/2003/01/geo/wgs84_pos#"),
    SC      ("sc",      "http://purl.org/science/owl/sciencecommons/"),
    FB      ("fb",      "http://rdf.freebase.com/ns/", LEGACY),
    GEONAMES("geonames","http://www.geonames.org/ontology#", LEGACY),
    // DBpedia                                                              
    DBPEDIA("dbpedia",  "http://dbpedia.org/resource/", LEGACY),
    DBP    ("dbp",      "http://dbpedia.org/property/", LEGACY),
    DBO    ("dbo",      "http://dbpedia.org/ontology/"),
    YAGO   ("yago",     "http://dbpedia.org/class/yago/");
    //@formatter:on

    final String prefix;
    final String ns;
    final Status status;

    final BuiltIn builtIn;

    Namespaces(String prefix, String ns, Status status) {
        this(prefix, ns, status, NOT_BUILT_IN);
    }

    Namespaces(String prefix, String ns) {
        this(prefix, ns, IN_USE, NOT_BUILT_IN);
    }

    Namespaces(String prefix, String ns, Status status, BuiltIn builtIn) {
        this.prefix = prefix;
        this.ns = ns;
        this.status = status;
        this.builtIn = builtIn;
    }

    /** @return A short, human-readable, prefix name that matches, and expands to
     *         the full IRI. Not {@code null}. */
    public String getPrefixName() {
        return prefix;
    }

    /** @return The prefix IRI which matches the prefix name. Not {@code null}. */
    public String getPrefixIRI() {
        return ns;
    }

    /** @return {@code true} if this namespace is not obsolete and is currently in
     *         active use, otherwise {@code false}. */
    public boolean isInUse() {
        return status == IN_USE;
    }

    /** @return {@code true} if this namespace is defined as a core part of the
     *         OWL-2 specification, otherwise {@code false}. */
    public boolean isBuiltIn() {
        return builtIn == BUILT_IN;
    }

    @Override
    public String toString() {
        return ns;
    }

    /**
     * Indicates that a prefix is builtin - i.e. that it is either owl, rdf, rdfs, or xsd
     */
    public static enum BuiltIn {

        BUILT_IN,

        NOT_BUILT_IN
    }

    /**
     * Indicates whether a prefix is a legacy prefix or not.
     */
    public static enum Status {

        LEGACY,

        IN_USE
    }
}
