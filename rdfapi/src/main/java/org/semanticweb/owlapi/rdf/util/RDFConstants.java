package org.semanticweb.owlapi.rdf.util;
/*
 * Copyright (C) 2009, University of Manchester
 * Original code by Boris Motik.  The original code formed part of KAON
 * which is licensed under the Lesser General Public License. The original package
 * name was edu.unika.aifb.rdf.api
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

