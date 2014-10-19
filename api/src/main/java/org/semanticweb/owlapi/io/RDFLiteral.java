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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2
 */
public class RDFLiteral extends RDFNode {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final String lexicalValue;
    @Nonnull
    private final String lang;
    @Nonnull
    private final IRI datatype;
    private int hashCode;

    /**
     * Constructor for plain literal wrappers.
     * 
     * @param literal
     *        lexical form
     * @param lang
     *        language tag
     * @param datatype
     *        datatype IRI
     */
    public RDFLiteral(@Nonnull String literal, @Nonnull String lang,
            @Nullable IRI datatype) {
        lexicalValue = checkNotNull(literal, "literal cannot be null");
        this.lang = lang;
        this.datatype = datatype == null ? OWL2Datatype.RDF_PLAIN_LITERAL
                .getIRI() : datatype;
    }

    /**
     * @param literal
     *        the wrapped literal
     */
    public RDFLiteral(@Nonnull OWLLiteral literal) {
        this(literal.getLiteral(), literal.getLang(), literal.getDatatype()
                .getIRI());
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RDFLiteral)) {
            return false;
        }
        RDFLiteral other = (RDFLiteral) obj;
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
        throw new UnsupportedOperationException("RDF Literals do not have IRIs");
    }

    /** @return the lexical form for this literal */
    @Nonnull
    public String getLexicalValue() {
        return lexicalValue;
    }

    /** @return the lang tag for this literal */
    @Nonnull
    public String getLang() {
        return lang;
    }

    /** @return the datatype for this literal */
    @Nonnull
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

    @Override
    public int compareTo(RDFNode o) {
        if (!o.isLiteral()) {
            return -1;
        }
        if (equals(o)) {
            return 0;
        }
        int diff = 0;
        RDFLiteral lit2 = (RDFLiteral) o;
        diff = lexicalValue.compareTo(lit2.lexicalValue);
        if (diff == 0) {
            diff = getDatatype().compareTo(lit2.getDatatype());
        }
        if (diff == 0) {
            diff = getLang().compareTo(lit2.getLang());
        }
        return diff;
    }
}
