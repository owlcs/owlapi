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
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * An OWLLiteral whose datatype is RDF_LANG_STRING or XSD_STRING
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 26-Oct-2006
 */
public class OWLLiteralImplPlain extends OWLObjectImpl implements OWLLiteral {

    private final @Nonnull String literal;
    private final @Nonnull OWLDatatype datatype;
    private final @Nonnull String lang;

    /**
     * @param literal
     *        the lexical form
     * @param lang
     *        the language; can be null or empty
     */
    public OWLLiteralImplPlain(String literal, @Nullable String lang) {
        this.literal = literal;
        if (lang == null || lang.isEmpty()) {
            this.lang = "";
            this.datatype = InternalizedEntities.XSDSTRING;
        } else {
            this.lang = lang.trim();
            this.datatype = InternalizedEntities.LANGSTRING;
        }
        hashCode = getHashCode();
    }

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public boolean hasLang() {
        return !lang.isEmpty();
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return false;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public boolean hasLang(@Nullable String l) {
        if (l == null || l.isEmpty()) {
            return lang.isEmpty();
        }
        return lang.equalsIgnoreCase(l.trim());
    }

    @Override
    public OWLDatatype getDatatype() {
        return this.datatype;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private final int getHashCode() {
        int hash = 277;
        hash = hash * 37 + getDatatype().hashCode();
        hash = hash * 37;
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
        if (super.equals(obj)) {
            if (!(obj instanceof OWLLiteral)) {
                return false;
            }
            OWLLiteral other = (OWLLiteral) obj;
            if (other instanceof OWLLiteralImplPlain) {
                return literal.equals(((OWLLiteralImplPlain) other).literal) && lang.equals(other.getLang());
            }
            return getLiteral().equals(other.getLiteral()) && getDatatype().equals(other.getDatatype())
                && lang.equals(other.getLang());
        }

        return false;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = getLiteral().compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        diff = lang.compareToIgnoreCase(other.getLang());
        if(diff != 0) {
            return diff;
        }
        return getDatatype().compareTo(other.getDatatype());
    }
}
