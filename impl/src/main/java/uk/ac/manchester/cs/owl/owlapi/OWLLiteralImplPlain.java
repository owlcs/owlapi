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

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import com.google.common.base.Optional;

/**
 * An OWLLiteral whose datatype is RDF_PLAIN_LITERAL
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 26-Oct-2006
 */
public class OWLLiteralImplPlain extends OWLObjectImplWithoutEntityAndAnonCaching implements OWLLiteral {

    private static final long serialVersionUID = 30406L;
    @Nonnull
    private static final OWLDatatype RDF_PLAIN_LITERAL = new OWL2DatatypeImpl(OWL2Datatype.RDF_PLAIN_LITERAL);
    @Nonnull
    private final String literal;
    @Nonnull
    private final String lang;

    /**
     * @param literal
     *        the lexical form
     * @param lang
     *        the language; can be null or empty
     */
    public OWLLiteralImplPlain(@Nonnull String literal, @Nullable String lang) {
        this.literal = literal;
        if (lang == null || lang.length() == 0) {
            this.lang = "";
        } else {
            this.lang = lang;
        }
        hashCode = getHashCode();
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        entities.add(RDF_PLAIN_LITERAL);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {}

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
        return !lang.equals("");
    }

    @Override
    public int parseInteger() throws NumberFormatException {
        return Integer.parseInt(getLiteral());
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return true;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public boolean isFloat() {
        return false;
    }

    @Override
    public boolean parseBoolean() {
        throw new OWLRuntimeException(getClass().getName() + " does not have a boolean value");
    }

    @Override
    public double parseDouble() {
        throw new OWLRuntimeException(getClass().getName() + " does not have a double value");
    }

    @Override
    public float parseFloat() {
        throw new OWLRuntimeException(getClass().getName() + " does not have a float value");
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public boolean hasLang(String l) {
        if (l == null) {
            return lang.isEmpty();
        }
        return lang.equalsIgnoreCase(l.trim());
    }

    @Override
    public OWLDatatype getDatatype() {
        return RDF_PLAIN_LITERAL;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private final int getHashCode() {
        int code = 277;
        code = code * 37 + getDatatype().hashCode();
        code = code * 37;
        try {
            if (isInteger()) {
                code += parseInteger() * 65536;
            } else if (isDouble()) {
                code += (int) parseDouble() * 65536;
            } else if (isFloat()) {
                code += (int) parseFloat() * 65536;
            } else if (isBoolean()) {
                code += parseBoolean() ? 65536 : 0;
            } else {
                code += getLiteral().hashCode() * 65536;
            }
        } catch (NumberFormatException e) {
            // it is possible that a literal does not have a value that's valid
            // for its datatype; not very useful for a consistent ontology but
            // some W3C reasoner tests use them
            code += getLiteral().hashCode() * 65536;
        }
        if (hasLang()) {
            code = code * 37 + getLang().hashCode();
        }
        return code;
    }

    @Override
    public boolean equals(Object obj) {
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
        if (other instanceof OWLLiteralImplPlain) {
            return literal.equals(((OWLLiteralImplPlain) other).literal) && lang.equals(other.getLang());
        }
        return getLiteral().equals(other.getLiteral()) && getDatatype().equals(other.getDatatype()) && lang.equals(other
            .getLang());
    }

    @Override
    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = getLiteral().compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        diff = getDatatype().compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return lang.compareTo(other.getLang());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<IRI> asIRI() {
        return Optional.absent();
    }

    @Override
    public Optional<OWLAnonymousIndividual> asAnonymousIndividual() {
        return Optional.absent();
    }

    @Override
    public Optional<OWLLiteral> asLiteral() {
        return Optional.<OWLLiteral> of(this);
    }
}
