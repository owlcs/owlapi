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


import static org.semanticweb.owlapi.vocab.Namespaces.BuiltIn.BUILT_IN;
import static org.semanticweb.owlapi.vocab.Namespaces.BuiltIn.NOT_BUILT_IN;
import static org.semanticweb.owlapi.vocab.Namespaces.Status.IN_USE;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public enum Namespaces {

    
    //    OWL2XML("http://www.w3.org/2006/12/owl2-xml#"),

    /**
     * The OWL 2 namespace is here for Status.LEGACY reasons.
     */
    OWL2("owl2", "http://www.w3.org/2006/12/owl2#", Status.LEGACY, NOT_BUILT_IN),

    /**
     * Status.LEGACY
     */
    OWL11XML("owl11xml", "http://www.w3.org/2006/12/owl11-xml#", Status.LEGACY, NOT_BUILT_IN),


    /**
     * The OWL 1.1 namespace is here for Status.LEGACY reasons.
     */
    OWL11("owl11", "http://www.w3.org/2006/12/owl11#", Status.LEGACY, NOT_BUILT_IN),


    OWL("owl", "http://www.w3.org/2002/07/owl#", IN_USE, BUILT_IN),

    RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#", IN_USE, BUILT_IN),

    RDF("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#", IN_USE, BUILT_IN),

    XSD("xsd", "http://www.w3.org/2001/XMLSchema#", IN_USE, BUILT_IN),

    XML("xml", "http://www.w3.org/XML/1998/namespace", IN_USE, NOT_BUILT_IN),

    SWRL("swrl", "http://www.w3.org/2003/11/swrl#", IN_USE, NOT_BUILT_IN),

    SWRLB("swrlb", "http://www.w3.org/2003/11/swrlb#", IN_USE, NOT_BUILT_IN),

    SKOS("skos", "http://www.w3.org/2004/02/skos/core#", IN_USE, NOT_BUILT_IN),

    // Further namespaces from the RDFa Core Initial Context
    // http://www.w3.org/2011/rdfa-context/rdfa-1.1


    GRDDL("grddl", "http://www.w3.org/2003/g/data-view#", IN_USE, NOT_BUILT_IN),

    MA("ma", "http://www.w3.org/ns/ma-ont#", IN_USE, NOT_BUILT_IN),

    PROV("prov", "http://www.w3.org/ns/prov#", IN_USE, NOT_BUILT_IN),

    RDFA("rdfa", "http://www.w3.org/ns/rdfa#", IN_USE, NOT_BUILT_IN),

    RIF("rif", "http://www.w3.org/2007/rif#", IN_USE, NOT_BUILT_IN),

    R2RML("rr", "http://www.w3.org/ns/r2rml#", IN_USE, NOT_BUILT_IN),

    SD("sd", "http://www.w3.org/ns/sparql-service-description#", IN_USE, NOT_BUILT_IN),

    SKOSXL("skosxl", "http://www.w3.org/2008/05/skos-xl#", IN_USE, NOT_BUILT_IN),

    POWDER("wdr", "http://www.w3.org/2007/05/powder#", IN_USE, NOT_BUILT_IN),

    VOID("void", "http://rdfs.org/ns/void#", IN_USE, NOT_BUILT_IN),

    POWDERS("wdrs", "http://www.w3.org/2007/05/powder-s#", IN_USE, NOT_BUILT_IN),

    XHV("xhv", "http://www.w3.org/1999/xhtml/vocab#", IN_USE, NOT_BUILT_IN),


    ORG("org", "http://www.w3.org/ns/org#", IN_USE, NOT_BUILT_IN),

    GLDP("gldp", "http://www.w3.org/ns/people#", IN_USE, NOT_BUILT_IN),

    CNT("cnt", "http://www.w3.org/2008/content#", IN_USE, NOT_BUILT_IN),

    DCAT("dcat", "http://www.w3.org/ns/dcat#", IN_USE, NOT_BUILT_IN),

    EARL("earl", "http://www.w3.org/ns/earl#", IN_USE, NOT_BUILT_IN),

    HT("ht", "http://www.w3.org/2006/http#", IN_USE, NOT_BUILT_IN),

    PTR("ptr", "http://www.w3.org/2009/pointers#", IN_USE, NOT_BUILT_IN),


    // Other widely used Semantic Web prefixes


    CC("cc", "http://creativecommons.org/ns#", IN_USE, NOT_BUILT_IN),

    CTAG("ctag", "http://commontag.org/ns#", IN_USE, NOT_BUILT_IN),

    DCTERMS("dcterms", "http://purl.org/dc/terms/", IN_USE, NOT_BUILT_IN),

    DC("dc", "http://purl.org/dc/elements/1.1/", IN_USE, NOT_BUILT_IN),

    FOAF("foaf", "http://xmlns.com/foaf/0.1/", IN_USE, NOT_BUILT_IN),

    GR("gr", "http://purl.org/goodrelations/v1#", IN_USE, NOT_BUILT_IN),

    ICAL("ical", "http://www.w3.org/2002/12/cal/icaltzd#", IN_USE, NOT_BUILT_IN),

    OG("og", "http://ogp.me/ns#", IN_USE, NOT_BUILT_IN),

    REV("rev", "http://purl.org/stuff/rev#", IN_USE, NOT_BUILT_IN),

    SIOC("sioc", "http://rdfs.org/sioc/ns#", IN_USE, NOT_BUILT_IN),

    VCARD("vcard", "http://www.w3.org/2006/vcard/ns#", IN_USE, NOT_BUILT_IN),

    SCHEMA("schema", "http://schema.org/", IN_USE, NOT_BUILT_IN),


    GEO("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#", IN_USE, NOT_BUILT_IN),

    SC("sc", "http://purl.org/science/owl/sciencecommons/", IN_USE, NOT_BUILT_IN),

    FB("fb", "http://rdf.freebase.com/ns/", Status.LEGACY, NOT_BUILT_IN),

    GEONAMES("geonames", "http://www.geonames.org/ontology#", Status.LEGACY, NOT_BUILT_IN),


    // DBpedia

    DBPEDIA("dbpedia", "http://dbpedia.org/resource/", IN_USE, NOT_BUILT_IN),

    DBP("dbp", "http://dbpedia.org/property/", IN_USE, NOT_BUILT_IN),

    DBO("dbo", "http://dbpedia.org/ontology/", IN_USE, NOT_BUILT_IN),

    YAGO("yago", "http://dbpedia.org/class/yago/", IN_USE, NOT_BUILT_IN);


    final String prefix;

    final String ns;

    final Status status;

    final BuiltIn builtIn;

    Namespaces(String prefix, String ns, Status status, BuiltIn builtIn) {
        this.prefix = prefix;
        this.ns = ns;
        this.status = status;
        this.builtIn = builtIn;
    }

    /**
     * @return A short, human-readable, prefix name that matches, and expands to the full IRI. Not {@code null}.
     */
    public String getPrefixName() {
        return prefix;
    }

    /**
     * @return The prefix IRI which matches the prefix name.  Not {@code null}.
     */
    public String getPrefixIRI() {
        return ns;
    }

    /**
     * @return {@code true} if this namespace is not obsolete and is currently in active use, otherwise {@code false}.
     */
    public boolean isInUse() {
        return status == IN_USE;
    }

    /**
     * @return {@code true} if this namespace is defined as a core part of the OWL-2 specification, otherwise
     * {@code false}.
     */
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
