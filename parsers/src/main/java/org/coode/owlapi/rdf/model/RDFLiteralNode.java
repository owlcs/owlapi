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
package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 06-Dec-2006
 */
public class RDFLiteralNode extends RDFNode implements Comparable<RDFNode> {

    private String literal;
    private String lang;
    private IRI datatype;
    private int hashCode = 0;

    @Override
    public IRI getIRI() {
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    /**
     * @param literal
     *        lexical form
     * @param datatype
     *        type
     */
    public RDFLiteralNode(String literal, IRI datatype) {
        this.literal = literal;
        this.datatype = datatype;
    }

    /**
     * @param literal
     *        lexical form
     * @param lang
     *        language tag
     */
    public RDFLiteralNode(String literal, String lang) {
        this.literal = literal;
        this.lang = lang;
    }

    /**
     * Gets the lexical form of this literal.
     * 
     * @return The lexical form
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Gets the lang
     * 
     * @return The lang, or {@code null} if there is no lang
     */
    public String getLang() {
        return lang;
    }

    /** @return the datatype, or {@code null} if there is no datatype */
    public IRI getDatatype() {
        return datatype;
    }

    /** @return true if the node has a datatype */
    public boolean isTyped() {
        return datatype != null;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(literal);
        sb.append("\"");
        if (datatype != null) {
            sb.append("^^<");
            sb.append(datatype);
            sb.append(">");
        } else if (lang != null) {
            sb.append("@");
            sb.append(lang);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RDFLiteralNode)) {
            return false;
        }
        RDFLiteralNode other = (RDFLiteralNode) obj;
        if (!other.literal.equals(literal)) {
            return false;
        }
        if (datatype != null) {
            if (!datatype.equals(other.datatype)) {
                return false;
            }
        } else if (other.getDatatype() != null) {
            return false;
        }
        if (lang != null) {
            return lang.equals(other.lang);
        } else if (other.lang != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 37;
            hashCode = hashCode * 37 + literal.hashCode();
            if (lang != null) {
                hashCode = hashCode * 37 + lang.hashCode();
            }
            if (datatype != null) {
                hashCode = hashCode * 37 + datatype.hashCode();
            }
        }
        return hashCode;
    }

    @Override
    public int compareTo(RDFNode b) {
        if (!b.isLiteral()) {
            return -1;
        }
        if (equals(b)) {
            return 0;
        }
        int diff = 0;
        RDFLiteralNode lit2 = (RDFLiteralNode) b;
        if (isTyped()) {
            if (lit2.isTyped()) {
                diff = getLiteral().compareTo(lit2.getLiteral());
                if (diff == 0) {
                    diff = getDatatype().compareTo(lit2.getDatatype());
                }
            } else {
                diff = -1;
            }
        } else {
            if (lit2.isTyped()) {
                diff = 1;
            } else {
                if (getLang() != null) {
                    if (lit2.getLang() != null) {
                        diff = getLang().compareTo(lit2.getLang());
                    }
                } else {
                    diff = -1;
                }
                if (diff == 0) {
                    diff = getLiteral().compareTo(lit2.getLiteral());
                }
            }
        }
        return diff;
    }
}
