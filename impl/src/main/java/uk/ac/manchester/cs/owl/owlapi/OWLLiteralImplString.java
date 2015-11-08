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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * An OWLLiteral with xsd:string datatype and no language tag.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 26-Oct-2006
 */
public class OWLLiteralImplString extends OWLObjectImpl implements OWLLiteral {

    private final @Nonnull String literal;

    /**
     * @param literal
     *        the lexical form
     */
    public OWLLiteralImplString(String literal) {
        this.literal = literal;
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
    public OWLDatatype getDatatype() {
        return InternalizedEntities.XSDSTRING;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private final int getHashCode() {
        int hash = 277;
        hash = hash * 37 + getDatatype().hashCode();
        hash *= 37;
        hash += getLiteral().hashCode() * 65536;
        if (hasLang()) {
            hash = hash * 37 + getLang().hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OWLLiteral)) {
            return false;
        }
        OWLLiteral other = (OWLLiteral) obj;
        return getLiteral().equals(other.getLiteral()) && getDatatype().equals(other.getDatatype())
            && getLang().equals(other.getLang());
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
        return getLang().compareTo(other.getLang());
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        checkNotNull(o);
        assert o != null;
        int thisTypeIndex = index();
        int otherTypeIndex = 0;
        if (o instanceof OWLObjectImpl) {
            otherTypeIndex = ((OWLObjectImpl) o).index();
        } else {
            otherTypeIndex = OWLObjectImpl.OWLOBJECT_TYPEINDEX_PROVIDER.getTypeIndex(o);
        }
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff == 0) {
            // Objects are the same type
            return compareObjectOfSameType(o);
        } else {
            return diff;
        }
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
