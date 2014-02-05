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
package org.semanticweb.owlapi.io;

import java.io.Serializable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

/** @author Matthew Horridge, The University of Manchester, Bio-Health Informatics
 *         Group, Date: 21/12/2010
 * @since 3.2 */
public class RDFLiteral extends RDFNode implements Serializable {
    private static final long serialVersionUID = 30406L;

    private String literalString;
    private String lang;
    private IRI datatype;
    private int hashCode;

    /**
     * @param literal
     *            lexical form
     * @param datatype
     *            type
     */
    public RDFLiteral(String literal, IRI datatype) {
        this.literalString = literal;
        this.datatype = datatype;
    }

    /**
     * @param literal
     *            lexical form
     * @param lang
     *            language tag
     */
    public RDFLiteral(String literal, String lang) {
        this.literalString = literal;
        this.lang = lang;
    }    
    /**
     * @param literal the wrapped literal
     */
    public RDFLiteral(OWLLiteral literal) {
        if(literal.getLiteral() == null) {
            throw new IllegalArgumentException("A literal must always have a non-null value");
        }
        this.literalString = literal.getLiteral();
        this.lang = literal.getLang();
        if(literal.getDatatype() != null) {
            this.datatype = literal.getDatatype().getIRI();
        }
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 37;
            hashCode = hashCode * 37 + literalString.hashCode();
            if(lang != null) {
                hashCode = hashCode * 37 + lang.hashCode();
            }
            if(datatype != null) {
                hashCode = hashCode * 37 + datatype.hashCode();
            }
        }
        return hashCode;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RDFLiteral)) {
            return false;
        }
        RDFLiteral other = (RDFLiteral) o;
        
        if(!literalString.equals(other.stringValue())) {
            return false;
        }
        
        if(this.getLang() != null) {
            if(other.getLang() == null) {
                return false;
            } else {
                return this.getLang().equals(other.getLang());
            }
        } else if(other.getLang() != null) {
            return false;
        }
        
        if(this.getDatatype() != null) {
            if(other.getDatatype() == null) {
                return false;
            } else {
                return this.getDatatype().equals(other.getDatatype());
            }
        }
        else if(other.getDatatype() != null) {
            return false;
        }
        
        return true;
    }

    @Override
    public int compareTo(RDFNode b) {
        if (this == b) {
           return 0; 
        }
        if (!(b instanceof RDFLiteral)) {
            return -1;
        }
        RDFLiteral lit2 = (RDFLiteral) b;
        
        int compareTo = stringValue().compareTo(lit2.stringValue());
        if (compareTo != 0) {
            return compareTo;
        }
        
        if (getLang() != null) {
            if (lit2.getLang() != null) {
                return getLang().compareTo(lit2.getLang());
            } else {
                return 1;
            }
        } else if (lit2.getLang() != null) {
            return -1;
        }
        
        if (hasDatatype()) {
            if(lit2.hasDatatype()) {
                return getDatatype().compareTo(lit2.getDatatype());
            } else {
                return 1;
            }
        }
        else if(lit2.hasDatatype()) {
            return -1;
        }
        
        return 0;
    }
    
    @Override
    public String toString() {
        return literalString;
    }

    /**
     * 
     * @return The {@link #stringValue()} for this RDFLiteral.
     * @deprecated Use {@link #stringValue()} instead.
     */
    @Deprecated
    public String getLiteral() {
        return stringValue();
    }
    
    @Override
    public IRI getIRI() {
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }
    
    public String stringValue() {
        return this.literalString;
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public IRI getDatatype() {
        return this.datatype;
    }

    public boolean hasLang() {
        if(this.lang != null && !this.lang.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @return True if this RDF Literal has a datatype, and false otherwise.
     */
    public boolean hasDatatype() {
        return this.datatype != null;
    }

    /**
     * 
     * @return True if this RDF Literal has a datatype, and false otherwise.
     * @deprecated Use hasDatatype instead.
     */
    @Deprecated
    public boolean isTyped() {
        return hasDatatype();
    }

}
