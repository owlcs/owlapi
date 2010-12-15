package org.semanticweb.owlapi.vocab;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 13-Dec-2006<br><br>
 */
public enum Namespaces {

//    OWL2XML("http://www.w3.org/2006/12/owl2-xml#"),

    /**
     * The OWL 2 namespace is here for legacy reasons.
     */
    OWL2("http://www.w3.org/2006/12/owl2#"),

    OWL11XML("http://www.w3.org/2006/12/owl11-xml#"),


    /**
     * The OWL 1.1 namespace is here for legacy reasons.
     */
    OWL11("http://www.w3.org/2006/12/owl11#"),

    OWL("http://www.w3.org/2002/07/owl#"),

    RDFS("http://www.w3.org/2000/01/rdf-schema#"),

    RDF("http://www.w3.org/1999/02/22-rdf-syntax-ns#"),

    XSD("http://www.w3.org/2001/XMLSchema#"),

    XML("http://www.w3.org/XML/1998/namespace"),

    SWRL("http://www.w3.org/2003/11/swrl#"),

    SWRLB("http://www.w3.org/2003/11/swrlb#"),

    SKOS("http://www.w3.org/2004/02/skos/core#");

    String ns;


    Namespaces(String ns) {
        this.ns = ns;
    }


    @Override
	public String toString() {
        return ns;
    }
}
