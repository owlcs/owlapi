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
package org.semanticweb.owlapi.rdf.rdfxml.parser;

/** Defines some well-known RDF constants. */
public interface RDFConstants {

    //@formatter:off
    /**XMLNS*/                  String XMLNS                    = "http://www.w3.org/XML/1998/namespace";
    /**XMLLANG*/                String XMLLANG                  = "xml:lang";
    /**RDFNS*/                  String RDFNS                    = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    /**ATTR_ID*/                String ATTR_ID                  = "ID";
    /**ATTR_RESOURCE*/          String ATTR_RESOURCE            = "resource";
    /**ATTR_BAG_ID*/            String ATTR_BAG_ID              = "bagID";
    /**ATTR_ABOUT*/             String ATTR_ABOUT               = "about";
    /**ATTR_ABOUT_EACH*/        String ATTR_ABOUT_EACH          = "aboutEach";
    /**ATTR_ABOUT_EACH_PREFIX*/ String ATTR_ABOUT_EACH_PREFIX   = "aboutEachPrefix";
    /**ATTR_PARSE_TYPE*/        String ATTR_PARSE_TYPE          = "parseType";
    /**ATTR_DATATYPE*/          String ATTR_DATATYPE            = "datatype";
    /**ATTR_NODE_ID*/           String ATTR_NODE_ID             = "nodeID";
    /**PARSE_TYPE_LITERAL*/     String PARSE_TYPE_LITERAL       = "Literal";
    /**PARSE_TYPE_RESOURCE*/    String PARSE_TYPE_RESOURCE      = "Resource";
    /**PARSE_TYPE_COLLECTION*/  String PARSE_TYPE_COLLECTION    = "Collection";
    /**ELT_TYPE*/               String ELT_TYPE                 = "type";
    /**ELT_RDF*/                String ELT_RDF                  = "RDF";
    /**ELT_DESCRIPTION*/        String ELT_DESCRIPTION          = "Description";
    /**ELT_BAG*/                String ELT_BAG                  = "Bag";
    /**ELT_SEQ*/                String ELT_SEQ                  = "Seq";
    /**ELT_ALT*/                String ELT_ALT                  = "Alt";
    /**ELT_LI*/                 String ELT_LI                   = "li";
    /**RDF_RDF*/                String RDF_RDF                  = RDFNS + ELT_RDF;
    /**RDF_ID*/                 String RDF_ID                   = RDFNS + ATTR_ID;
    /**RDF_LI*/                 String RDF_LI                   = RDFNS + ELT_LI;
    /**RDF_RESOURCE*/           String RDF_RESOURCE             = RDFNS + ATTR_RESOURCE;
    /**RDF_TYPE*/               String RDF_TYPE                 = RDFNS + ELT_TYPE;
    /**RDF_NODE_ID*/            String RDF_NODE_ID              = RDFNS + ATTR_NODE_ID;
    /**RDF_DESCRIPTION*/        String RDF_DESCRIPTION          = RDFNS + ELT_DESCRIPTION;
    /**RDF_ABOUT*/              String RDF_ABOUT                = RDFNS + ATTR_ABOUT;
    /**RDF_PARSE_TYPE*/         String RDF_PARSE_TYPE           = RDFNS + ATTR_PARSE_TYPE;
    /**RDF_DATATYPE*/           String RDF_DATATYPE             = RDFNS + ATTR_DATATYPE;
    /**RDF_SUBJECT*/            String RDF_SUBJECT              = RDFNS + "subject";
    /**RDF_OBJECT*/             String RDF_OBJECT               = RDFNS + "object";
    /**RDF_PREDICATE*/          String RDF_PREDICATE            = RDFNS + "predicate";
    /**RDF_STATEMENT*/          String RDF_STATEMENT            = RDFNS + "statement";
    /**RDF_BAG*/                String RDF_BAG                  = RDFNS + ELT_BAG;
    /**RDF_PROPERTY*/           String RDF_PROPERTY             = RDFNS + "Property";
    /**RDF_FIRST*/              String RDF_FIRST                = RDFNS + "first";
    /**RDF_REST*/               String RDF_REST                 = RDFNS + "rest";
    /**RDF_LIST*/               String RDF_LIST                 = RDFNS + "List";
    /**RDF_NIL*/                String RDF_NIL                  = RDFNS + "nil";
    /**RDF_XMLLITERAL*/         String RDF_XMLLITERAL           = RDFNS + "XMLLiteral";
    /**RDFSNS*/                 String RDFSNS                   = "http://www.w3.org/2000/01/rdf-schema#";
    /**RDFS_CLASS*/             String RDFS_CLASS               = RDFSNS + "Class";
    /**RDFS_SUBCLASSOF*/        String RDFS_SUBCLASSOF          = RDFSNS + "subClassOf";
    /**RDFS_SUBPROPERTYOF*/     String RDFS_SUBPROPERTYOF       = RDFSNS + "subPropertyOf";
    /**RDFS_LABEL*/             String RDFS_LABEL               = RDFSNS + "label";
    /**RDFS_COMMENT*/           String RDFS_COMMENT             = RDFSNS + "comment";
    /**RDFS_DOMAIN*/            String RDFS_DOMAIN              = RDFSNS + "domain";
    /**RDFS_RANGE*/             String RDFS_RANGE               = RDFSNS + "range";
    /**KAONNS*/                 String KAONNS                   = "http://kaon.semanticweb.org/2001/11/kaon-lexical#";
    //@formatter:on
}
