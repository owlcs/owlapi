package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;

import java.net.URI;
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
 * Date: 06-Dec-2006<br><br>
 */
public class RDFLiteralNode extends RDFNode {

    private String literal;

    private String lang;

    private IRI datatype;

    private int hashCode = 0;

    public RDFLiteralNode(String literal) {
        this.literal = literal;
    }


    public IRI getIRI() {
        return null;
    }


    public boolean isAnonymous() {
        return false;
    }


    public RDFLiteralNode(String literal, IRI datatype) {
        this.literal = literal;
        this.datatype = datatype;
    }


    public RDFLiteralNode(String literal, String lang) {
        this.literal = literal;
        this.lang = lang;
    }


    public String getLiteral() {
        return literal;
    }


    public String getLang() {
        return lang;
    }


    public IRI getDatatype() {
        return datatype;
    }


    public boolean isTyped() {
        return datatype != null;
    }

    public boolean isLiteral() {
        return true;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(literal);
        sb.append("\"");
        if(datatype != null) {
            sb.append("^^<");
            sb.append(datatype);
            sb.append(">");
        }
        else if(lang != null) {
            sb.append("@");
            sb.append(lang);
        }
        return sb.toString();
    }


    public boolean equals(Object obj) {
        if(!(obj instanceof RDFLiteralNode)) {
            return false;
        }
        RDFLiteralNode other = (RDFLiteralNode) obj;
        if(!other.literal.equals(literal)) {
            return false;
        }
        if(datatype != null) {
            if(!datatype.equals(other.datatype)) {
                return false;
            }
        }
        else if(other.getDatatype() != null) {
            return false;
        }
        if(lang != null) {
            return lang.equals(other.lang);
        }
        else if(other.lang != null) {
            return false;
        }
        return true;
    }


    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 37;
            hashCode = hashCode * 37 + literal.hashCode();
            if(lang != null) {
                hashCode = hashCode * 37 + lang.hashCode();
            }
            if(datatype != null) {
                hashCode = hashCode * 37 + datatype.hashCode();
            }
        }
        return hashCode;
    }
}
