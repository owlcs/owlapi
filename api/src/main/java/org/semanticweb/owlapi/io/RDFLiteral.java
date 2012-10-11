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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/** Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * 
 * @since 3.2 */
public class RDFLiteral extends RDFNode {
    private final String lexicalValue;
    private final String lang;
    private final IRI datatype;
    private int hashCode;

    /** Constructor for plain literal wrappers
     * 
     * @param literal
     *            lexical form
     * @param lang
     *            language tag
     * @param datatype
     *            datatype IRI */
    public RDFLiteral(String literal, String lang, IRI datatype) {
        lexicalValue = literal;
        this.lang = lang == null ? "" : lang.trim();
        this.datatype = datatype;
    }

    /** @param literal
     *            the wrapped literal */
    public RDFLiteral(OWLLiteral literal) {
        this(literal.getLiteral(), literal.getLang(), literal.getDatatype().getIRI());
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 37;
            hashCode = hashCode * 37 + lexicalValue.hashCode();
            hashCode = hashCode * 37 + lang.hashCode();
            hashCode = hashCode * 37 + datatype.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof RDFLiteral)) {
            return false;
        }
        RDFLiteral other = (RDFLiteral) o;
        if (!lexicalValue.equals(other.lexicalValue)) {
            return false;
        }
        if (!lang.equals(other.lang)) {
            return false;
        }
        return datatype.equals(other.datatype);
    }

    @Override
    public String toString() {
        return lexicalValue;
    }

    @Override
    public IRI getIRI() {
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    /** @return the lexical form for this literal */
    public String getLexicalValue() {
        return lexicalValue;
    }

    /** @return the lang tag for this literal */
    public String getLang() {
        return lang;
    }

    /** @return the datatype for this literal */
    public IRI getDatatype() {
        return datatype;
    }

    /** @return true if this literal has a non empty lang tag */
    public boolean hasLang() {
        return !lang.isEmpty();
    }

    /** @return true if the datatype of this literal is plain literal */
    public boolean isPlainLiteral() {
        return OWL2Datatype.RDF_PLAIN_LITERAL.getIRI().equals(datatype);
    }
}
