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

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.rdf.api.Literal;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2
 */
public class RDFLiteral extends RDFNode implements org.apache.commons.rdf.api.Literal {

    private final @Nonnull String lexicalValue;
    private final @Nonnull String lang;
    private final @Nonnull IRI datatype;
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
    public RDFLiteral(String literal, @Nullable String lang, @Nullable IRI datatype) {
        lexicalValue = checkNotNull(literal, "literal cannot be null");
        this.lang = lang == null ? "" : lang;
        OWL2Datatype defaultType = this.lang.isEmpty() ? OWL2Datatype.RDF_PLAIN_LITERAL : OWL2Datatype.RDF_LANG_STRING;
        this.datatype = datatype == null ? defaultType.getIRI() : datatype;
    }

    /**
     * @param literal
     *        the wrapped literal
     */
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
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RDFLiteral) {
            RDFLiteral other = (RDFLiteral) obj;
            if (!lexicalValue.equals(other.lexicalValue)) {
                return false;
            }
            if (!lang.equals(other.lang)) {
                return false;
            }
            return datatype.equals(other.datatype);
        }
        if (obj instanceof Literal) {
            // Note: This also works on RDFLiteral
            // but is slightly more expensive as it must call the
            // getter methods when accessing obj.
            //
            // To ensure future compatibility, the Commons RDF getter
            // methods are also called on this rather than using the fields.
            Literal literal = (Literal) obj;
            if (!getLexicalForm().equals(((Literal) obj).getLexicalForm())) {
                return false;
            }
            if (!getLanguageTag().equals(literal.getLanguageTag())) {
                return false;
            }
            return getDatatype().equals(literal.getDatatype());
        }
        return false;
    }

    @Override
    public String toString() {
        return lexicalValue;
    }

    @Override
    public IRI getIRI() {
        throw new UnsupportedOperationException("RDF Literals do not have IRIs");
    }

    /**
     * @return the lexical form for this literal
     */
    public String getLexicalValue() {
        return lexicalValue;
    }

    @Override
    public String getLexicalForm() {
        return getLexicalValue();
    }

    /**
     * @return the lang tag for this literal
     */
    public String getLang() {
        return lang;
    }

    @Override
    public Optional<String> getLanguageTag() {
        if (hasLang()) {
            return Optional.of(lang);
        }
        return Optional.empty();
    }

    @Override
    public IRI getDatatype() {
        return datatype;
    }

    /**
     * @return true if this literal has a non empty lang tag
     */
    public boolean hasLang() {
        return !lang.isEmpty();
    }

    /**
     * @return true if the datatype of this literal is plain literal
     */
    public boolean isPlainLiteral() {
        return OWL2Datatype.RDF_PLAIN_LITERAL.getIRI().equals(datatype);
    }

    @Override
    public int compareTo(@Nullable RDFNode o) {
        checkNotNull(o);
        assert o != null;
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

    @Override
    public String ntriplesString() {
        String escaped = '"' +
            EscapeUtils.escapeString(getLexicalValue()).replace("\n", "\\n").replace("\r", "\\r") + '"';
        if (datatype.equals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI()) ||
            datatype.equals(OWL2Datatype.XSD_STRING.getIRI())) {
            return escaped;
        } else if (hasLang()) {
            return escaped + "@" + getLang();
        } else {
            return escaped + "^^" + getDatatype().ntriplesString();
        }
    }
}
