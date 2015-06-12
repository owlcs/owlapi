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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import com.google.common.base.Optional;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLLiteralImplDouble extends OWLObjectImplWithoutEntityAndAnonCaching implements OWLLiteral {

    private static final long serialVersionUID = 40000L;
    private final double literal;
    @Nonnull
    private final OWLDatatype datatype;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        entities.add(datatype);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {}

    /**
     * @param literal
     *        literal value
     * @param datatype
     *        datatype
     */
    public OWLLiteralImplDouble(double literal, @Nonnull OWLDatatype datatype) {
        this.literal = literal;
        this.datatype = checkNotNull(datatype, "datatype cannot be null");
        hashcode = getHashCode();
    }

    private final int hashcode;

    @Override
    public int hashCode() {
        return hashcode;
    }

    private int getHashCode() {
        int code = 277;
        code = code * 37 + getDatatype().hashCode();
        code = code * 37 + (int) (literal * 65536);
        return code;
    }

    @Override
    public String getLiteral() {
        return Double.toString(literal);
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return false;
    }

    @Override
    public boolean hasLang() {
        return false;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public int parseInteger() {
        throw new NumberFormatException("this literal is not an integer but a double");
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean parseBoolean() {
        throw new NumberFormatException("this literal is not a boolean but a double");
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double parseDouble() {
        return literal;
    }

    @Override
    public boolean isFloat() {
        return false;
    }

    @Override
    public float parseFloat() {
        throw new NumberFormatException("this literal is not a float but a double");
    }

    @Nonnull
    @Override
    public String getLang() {
        return "";
    }

    @Override
    public boolean hasLang(String lang) {
        return false;
    }

    @Override
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (obj instanceof OWLLiteralImplDouble) {
            OWLLiteralImplDouble other = (OWLLiteralImplDouble) obj;
            return literal == other.literal;
        }
        if (obj instanceof OWLLiteral) {
            return ((OWLLiteral) obj).isDouble() && Double.compare(literal, ((OWLLiteral) obj).parseDouble()) == 0;
        }
        return false;
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
        int compareTo = datatype.compareTo(other.getDatatype());
        if (compareTo != 0) {
            return compareTo;
        }
        return Double.compare(literal, other.parseDouble());
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
