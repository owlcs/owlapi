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

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public enum Namespaces {

    //    OWL2XML("http://www.w3.org/2006/12/owl2-xml#"),

    /**
     * The OWL 2 namespace is here for legacy reasons.
     */
    OWL2("owl2", "http://www.w3.org/2006/12/owl2#", false, false),

    /**
     * legacy
     */
    OWL11XML("owl11xml", "http://www.w3.org/2006/12/owl11-xml#", false, false),


    /**
     * The OWL 1.1 namespace is here for legacy reasons.
     */
    OWL11("owl11", "http://www.w3.org/2006/12/owl11#", false, false),

    /**
     * OWL namespace
     */
    OWL("owl", "http://www.w3.org/2002/07/owl#", true, true),
    /**
     * RDFS namespace
     */
    RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#", true, true),
    /**
     * RDF namespace
     */
    RDF("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#", true, true),
    /**
     * XSD namespace
     */
    XSD("xsd", "http://www.w3.org/2001/XMLSchema#", true, true),
    /**
     * XML namespace
     */
    XML("xml", "http://www.w3.org/XML/1998/namespace", true, false),
    /**
     * SWRL namespace
     */
    SWRL("swrl", "http://www.w3.org/2003/11/swrl#", true, false),
    /**
     * SWRLB namespace
     */
    SWRLB("swrlb", "http://www.w3.org/2003/11/swrlb#", true, false),
    /**
     * SKOS namespace
     */
    SKOS("skos", "http://www.w3.org/2004/02/skos/core#", true, false),
    
    // Further namespaces from the RDFa Core Initial Context
    // http://www.w3.org/2011/rdfa-context/rdfa-1.1
    
    /**GRDDL namespace*/
    GRDDL("grddl", "http://www.w3.org/2003/g/data-view#", true, false),
    /**MA namespace*/
    MA("ma", "http://www.w3.org/ns/ma-ont#", true, false),
    /**PROV namespace*/
    PROV("prov", "http://www.w3.org/ns/prov#", true, false),
    /**RDFA namespace*/
    RDFA("rdfa", "http://www.w3.org/ns/rdfa#", true, false),
    /**RIF namespace*/
    RIF("rif", "http://www.w3.org/2007/rif#", true, false),
    /**R2RML namespace*/
    R2RML("rr", "http://www.w3.org/ns/r2rml#", true, false),
    /**SD namespace*/
    SD("sd", "http://www.w3.org/ns/sparql-service-description#", true, false),
    /**SKOSXL namespace*/
    SKOSXL("skosxl", "http://www.w3.org/2008/05/skos-xl#", true, false),
    /**POWDER namespace*/
    POWDER("wdr", "http://www.w3.org/2007/05/powder#", true, false),
    /**VOID namespace*/
    VOID("void", "http://rdfs.org/ns/void#", true, false),
    /**POWDER-S namespace*/
    POWDERS("wdrs", "http://www.w3.org/2007/05/powder-s#", true, false),
    /**XHV namespace*/
    XHV("xhv", "http://www.w3.org/1999/xhtml/vocab#", true, false),
    
    
    /**ORG namespace*/
    ORG("org", "http://www.w3.org/ns/org#", true, false),
    /**GLDP namespace*/
    GLDP("gldp", "http://www.w3.org/ns/people#", true, false),
    /**CNT namespace*/
    CNT("cnt", "http://www.w3.org/2008/content#", true, false),
    /**DCAT namespace*/
    DCAT("dcat", "http://www.w3.org/ns/dcat#", true, false),
    /**EARL namespace*/
    EARL("earl", "http://www.w3.org/ns/earl#", true, false),
    /**HT namespace*/
    HT("ht", "http://www.w3.org/2006/http#", true, false),
    /**PTR namespace*/
    PTR("ptr", "http://www.w3.org/2009/pointers#", true, false),
    
    
    // Other widely used Semantic Web prefixes
    
    /**CC namespace*/
    CC("cc", "http://creativecommons.org/ns#", true, false),
    /**CTAG namespace*/
    CTAG("ctag", "http://commontag.org/ns#", true, false),
    /**DCTERMS namespace*/
    DCTERMS("dcterms", "http://purl.org/dc/terms/", true, false),
    /**DC elements namespace*/
    DC("dc", "http://purl.org/dc/elements/1.1/", true, false),
    /**FOAF namespace*/
    FOAF("foaf", "http://xmlns.com/foaf/0.1/", true, false),
    /**GR namespace*/
    GR("gr", "http://purl.org/goodrelations/v1#", true, false),
    /**ICAL namespace*/
    ICAL("ical", "http://www.w3.org/2002/12/cal/icaltzd#", true, false),
    /**OG namespace*/
    OG("og", "http://ogp.me/ns#", true, false),
    /**REV namespace*/
    REV("rev", "http://purl.org/stuff/rev#", true, false),
    /**SIOC namespace*/
    SIOC("sioc", "http://rdfs.org/sioc/ns#", true, false),
    /**VCARD namespace*/
    VCARD("vcard", "http://www.w3.org/2006/vcard/ns#", true, false),
    /**SCHEMA namespace*/
    SCHEMA("schema", "http://schema.org/", true, false),
    
    /**GEO namespace*/
    GEO("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#", true, false),
    
    ;

    final String prefix;

    final String ns;

    final boolean inUse;

    final boolean builtIn;

    Namespaces(String prefix, String ns, boolean inUse, boolean builtIn) {
        this.prefix = prefix;
        this.ns = ns;
        this.inUse = inUse;
        this.builtIn = builtIn;
    }

    /**
     * @return A short, human-readable, prefix name that matches, and expands to the full IRI.
     */
    public String getPrefixName() {
        return prefix;
    }

    /**
     * @return The prefix IRI which matches the prefix name.
     */
    public String getPrefixIRI() {
        return ns;
    }

    /**
     * @return True if this namespace is not obsolete and is currently in active use.
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * @return True if this namespace is defined as a core part of the OWL-2 specification.
     */
    public boolean isBuiltIn() {
        return builtIn;
    }

    @Override
    public String toString() {
        return ns;
    }
}
