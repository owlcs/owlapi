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
package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLLiteralImplNoCompression extends OWLObjectImpl implements OWLLiteral {

    private static final @Nonnull OWLDatatype RDF_PLAIN_LITERAL = new OWL2DatatypeImpl(OWL2Datatype.RDF_PLAIN_LITERAL);
    private static final @Nonnull OWLDatatype RDF_LANG_STRING = new OWL2DatatypeImpl(OWL2Datatype.RDF_LANG_STRING);
    private static final @Nonnull OWLDatatype XSD_STRING = new OWL2DatatypeImpl(OWL2Datatype.XSD_STRING);
    private final @Nonnull String literal;
    private final @Nonnull OWLDatatype datatype;
    private final @Nonnull String language;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    /**
     * @param literal
     *        actual literal form
     * @param lang
     *        language for literal, can be null
     * @param datatype
     *        datatype for literal
     */
    public OWLLiteralImplNoCompression(String literal, @Nullable String lang, @Nullable OWLDatatype datatype) {
        this.literal = literal;
        if (lang == null || lang.isEmpty()) {
            language = "";
            if (datatype == null) {
                this.datatype = XSD_STRING;
            } else {
                this.datatype = datatype;
            }
        } else {
            if (datatype != null && !(datatype.equals(RDF_LANG_STRING) || datatype.equals(RDF_PLAIN_LITERAL))) {
                // ERROR: attempting to build a literal with a language tag and
                // type different from RDF_LANG_STRING or RDF_PLAIN_LITERAL
                throw new OWLRuntimeException(
                        "Error: cannot build a literal with type: " + datatype.getIRI() + " and language: " + lang);
            }
            language = lang;
            this.datatype = RDF_LANG_STRING;
        }
        hashCode = getHashCode();
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return datatype.getIRI().equals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());
    }

    @Override
    public boolean hasLang() {
        return !language.isEmpty();
    }

    @Override
    public boolean isInteger() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_INTEGER.getIRI());
    }

    @Override
    public int parseInteger() {
        return Integer.parseInt(getLiteral());
    }

    @Override
    public boolean isBoolean() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_BOOLEAN.getIRI());
    }

    @Override
    public boolean parseBoolean() {
        if (literal.equals("0")) {
            return false;
        }
        if (literal.equals("1")) {
            return true;
        }
        if (literal.equals("true")) {
            return true;
        }
        if (literal.equals("false")) {
            return false;
        }
        return Boolean.parseBoolean(literal);
    }

    @Override
    public boolean isDouble() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_DOUBLE.getIRI());
    }

    @Override
    public double parseDouble() {
        return Double.parseDouble(literal);
    }

    @Override
    public boolean isFloat() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_FLOAT.getIRI());
    }

    @Override
    public float parseFloat() {
        if ("inf".equalsIgnoreCase(literal)) {
            return Float.POSITIVE_INFINITY;
        }
        if ("-inf".equalsIgnoreCase(literal)) {
            return Float.NEGATIVE_INFINITY;
        }
        return Float.parseFloat(literal);
    }

    @Override
    public String getLang() {
        return language;
    }

    @Override
    public boolean hasLang(@Nullable String lang) {
        if (lang == null) {
            return language.isEmpty();
        }
        return language.equalsIgnoreCase(lang.trim());
    }

    @Override
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private final int getHashCode() {
        int hash = 277;
        hash = hash * 37 + getDatatype().hashCode();
        hash *= 37;
        try {
            if (isInteger()) {
                hash += parseInteger() * 65536;
            } else if (isDouble()) {
                hash += (int) parseDouble() * 65536;
            } else if (isFloat()) {
                hash += (int) parseFloat() * 65536;
            } else if (isBoolean()) {
                hash += parseBoolean() ? 65536 : 0;
            } else {
                hash += getLiteral().hashCode() * 65536;
            }
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            // it is possible that a literal does not have a value that's valid
            // for its datatype; not very useful for a consistent ontology but
            // some W3C reasoner tests use them
            hash += getLiteral().hashCode() * 65536;
        }
        if (hasLang()) {
            hash = hash * 37 + getLang().hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLLiteral)) {
            return false;
        }
        OWLLiteral other = (OWLLiteral) obj;
        if (other instanceof OWLLiteralImplNoCompression) {
            return literal.equals(((OWLLiteralImplNoCompression) other).literal) && datatype.equals(other.getDatatype())
                    && language.equals(other.getLang());
        }
        return literal.equals(other.getLiteral()) && datatype.equals(other.getDatatype())
                && language.equals(other.getLang());
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = literal.compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        diff = datatype.compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return language.compareTo(other.getLang());
    }
}
