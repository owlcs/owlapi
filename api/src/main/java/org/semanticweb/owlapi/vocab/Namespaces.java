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
