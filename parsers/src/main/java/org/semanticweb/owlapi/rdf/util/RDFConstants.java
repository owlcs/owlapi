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
package org.semanticweb.owlapi.rdf.util;

/** Defines some well-known RDF constants. */
@SuppressWarnings("javadoc")
public class RDFConstants {
    public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
    public static final String XMLLANG = "xml:lang";
    public static final String RDFNS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String ATTR_ID = "ID";
    public static final String ATTR_RESOURCE = "resource";
    public static final String ATTR_BAG_ID = "bagID";
    public static final String ATTR_ABOUT = "about";
    public static final String ATTR_ABOUT_EACH = "aboutEach";
    public static final String ATTR_ABOUT_EACH_PREFIX = "aboutEachPrefix";
    public static final String ATTR_PARSE_TYPE = "parseType";
    public static final String ATTR_DATATYPE = "datatype";
    public static final String ATTR_NODE_ID = "nodeID";
    public static final String PARSE_TYPE_LITERAL = "Literal";
    public static final String PARSE_TYPE_RESOURCE = "Resource";
    public static final String PARSE_TYPE_COLLECTION = "Collection";
    public static final String ELT_TYPE = "type";
    public static final String ELT_RDF = "RDF";
    public static final String ELT_DESCRIPTION = "Description";
    public static final String ELT_BAG = "Bag";
    public static final String ELT_SEQ = "Seq";
    public static final String ELT_ALT = "Alt";
    public static final String ELT_LI = "li";
    public static final String RDF_RDF = RDFNS + ELT_RDF;
    public static final String RDF_ID = RDFNS + ATTR_ID;
    public static final String RDF_LI = RDFNS + ELT_LI;
    public static final String RDF_RESOURCE = RDFNS + ATTR_RESOURCE;
    public static final String RDF_TYPE = RDFNS + ELT_TYPE;
    public static final String RDF_NODE_ID = RDFNS + ATTR_NODE_ID;
    public static final String RDF_DESCRIPTION = RDFNS + ELT_DESCRIPTION;
    public static final String RDF_ABOUT = RDFNS + ATTR_ABOUT;
    public static final String RDF_PARSE_TYPE = RDFNS + ATTR_PARSE_TYPE;
    public static final String RDF_DATATYPE = RDFNS + ATTR_DATATYPE;
    public static final String RDF_SUBJECT = RDFNS + "subject";
    public static final String RDF_OBJECT = RDFNS + "object";
    public static final String RDF_PREDICATE = RDFNS + "predicate";
    public static final String RDF_STATEMENT = RDFNS + "statement";
    public static final String RDF_BAG = RDFNS + ELT_BAG;
    public static final String RDF_PROPERTY = RDFNS + "Property";
    public static final String RDF_FIRST = RDFNS + "first";
    public static final String RDF_REST = RDFNS + "rest";
    public static final String RDF_LIST = RDFNS + "List";
    public static final String RDF_NIL = RDFNS + "nil";
    public static final String RDF_XMLLITERAL = RDFNS + "XMLLiteral";
    public static final String RDFSNS = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String RDFS_CLASS = RDFSNS + "Class";
    public static final String RDFS_SUBCLASSOF = RDFSNS + "subClassOf";
    public static final String RDFS_SUBPROPERTYOF = RDFSNS + "subPropertyOf";
    public static final String RDFS_LABEL = RDFSNS + "label";
    public static final String RDFS_COMMENT = RDFSNS + "comment";
    public static final String RDFS_DOMAIN = RDFSNS + "domain";
    public static final String RDFS_RANGE = RDFSNS + "range";
    public static final String KAONNS = "http://kaon.semanticweb.org/2001/11/kaon-lexical#";
}
