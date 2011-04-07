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
/**
 * Defines some well-known RDF constants.
 */
public interface RDFConstants {
    final String XMLNS="http://www.w3.org/XML/1998/namespace";
    final String XMLLANG="xml:lang";

    final String RDFNS="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    final String ATTR_ID="ID";
    final String ATTR_RESOURCE="resource";
    final String ATTR_BAG_ID="bagID";
    final String ATTR_ABOUT="about";
    final String ATTR_ABOUT_EACH="aboutEach";
    final String ATTR_ABOUT_EACH_PREFIX="aboutEachPrefix";
    final String ATTR_PARSE_TYPE="parseType";
    final String ATTR_DATATYPE="datatype";
    final String ATTR_NODE_ID="nodeID";
    final String PARSE_TYPE_LITERAL="Literal";
    final String PARSE_TYPE_RESOURCE="Resource";
    final String PARSE_TYPE_COLLECTION="Collection";
    final String ELT_TYPE="type";
    final String ELT_RDF="RDF";
    final String ELT_DESCRIPTION="Description";
    final String ELT_BAG="Bag";
    final String ELT_SEQ="Seq";
    final String ELT_ALT="Alt";
    final String ELT_LI="li";

    final String RDF_RDF=RDFNS+ELT_RDF;
    final String RDF_ID=RDFNS+ATTR_ID;
    final String RDF_LI=RDFNS+ELT_LI;
    final String RDF_RESOURCE=RDFNS+ATTR_RESOURCE;
    final String RDF_TYPE=RDFNS+ELT_TYPE;
    final String RDF_NODE_ID=RDFNS+ATTR_NODE_ID;
    final String RDF_DESCRIPTION=RDFNS+ELT_DESCRIPTION;
    final String RDF_ABOUT=RDFNS+ATTR_ABOUT;
    final String RDF_PARSE_TYPE=RDFNS+ATTR_PARSE_TYPE;
    final String RDF_DATATYPE=RDFNS+ATTR_DATATYPE;
    final String RDF_SUBJECT=RDFNS+"subject";
    final String RDF_OBJECT=RDFNS+"object";
    final String RDF_PREDICATE=RDFNS+"predicate";
    final String RDF_STATEMENT=RDFNS+"statement";
    final String RDF_BAG=RDFNS+ELT_BAG;
    final String RDF_PROPERTY=RDFNS+"Property";
    final String RDF_FIRST=RDFNS+"first";
    final String RDF_REST=RDFNS+"rest";
    final String RDF_LIST=RDFNS+"List";
    final String RDF_NIL=RDFNS+"nil";
    final String RDF_XMLLITERAL=RDFNS+"XMLLiteral";

    final String RDFSNS="http://www.w3.org/2000/01/rdf-schema#";
    final String RDFS_CLASS=RDFSNS+"Class";
    final String RDFS_SUBCLASSOF=RDFSNS+"subClassOf";
    final String RDFS_SUBPROPERTYOF=RDFSNS+"subPropertyOf";
    final String RDFS_LABEL=RDFSNS+"label";
    final String RDFS_COMMENT=RDFSNS+"comment";
    final String RDFS_DOMAIN=RDFSNS+"domain";
    final String RDFS_RANGE=RDFSNS+"range";

    final String KAONNS="http://kaon.semanticweb.org/2001/11/kaon-lexical#";
}

