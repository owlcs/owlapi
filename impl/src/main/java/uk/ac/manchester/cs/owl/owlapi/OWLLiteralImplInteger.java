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

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLLiteralImplInteger extends OWLObjectImpl implements OWLLiteral {

    private final int literal;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    /**
     * @param literal
     *        literal value
     */
    public OWLLiteralImplInteger(int literal) {
        this.literal = literal;
        hashcode = getHashCode();
    }

    private final int hashcode;

    @Override
    public int hashCode() {
        return hashcode;
    }

    private int getHashCode() {
        int hash = 277;
        hash = hash * 37 + getDatatype().hashCode();
        hash = hash * 37 + literal * 65536;
        return hash;
    }

    @Override
    public String getLiteral() {
        return Integer.toString(literal);
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public int parseInteger() {
        return literal;
    }

    @Override
    public OWLDatatype getDatatype() {
        return InternalizedEntities.XSDINTEGER;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (obj instanceof OWLLiteralImplInteger) {
            OWLLiteralImplInteger other = (OWLLiteralImplInteger) obj;
            return literal == other.literal;
        }
        if (!(obj instanceof OWLLiteral)) {
            return false;
        }
        return ((OWLLiteral) obj).isInteger()
        && ((OWLLiteral) obj).getLiteral().charAt(0) != '0'
                && literal == ((OWLLiteral) obj).parseInteger();
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = getLiteral().compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        int compareTo = getDatatype().compareTo(other.getDatatype());
        if (compareTo != 0) {
            return compareTo;
        }
        return Integer.compare(literal, other.parseInteger());
    }
}
