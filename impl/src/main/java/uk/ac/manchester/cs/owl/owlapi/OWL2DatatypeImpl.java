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
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * An optimised implementation of OWLDatatype for OWL2Datatypes.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
public class OWL2DatatypeImpl implements OWLDatatype {

    private final OWL2Datatype owl2Datatype;
    private final int hashCode;

    /**
     * Creates an instance of {@code OWLDatatypeImplForOWL2Datatype} for the specified
     * {@link OWL2Datatype}.
     * 
     * @param owl2Datatype The datatype. Not {@code null}.
     * @throws NullPointerException if {@code owl2Datatype} is {@code null}.
     */
    public OWL2DatatypeImpl(OWL2Datatype owl2Datatype) {
        this.owl2Datatype = checkNotNull(owl2Datatype, "owl2Datatype must not be null");
        hashCode = OWLObjectImpl.hash(hashIndex(), components());
    }

    @Override
    public OWL2Datatype getBuiltInDatatype() {
        return owl2Datatype;
    }

    @Override
    public boolean isString() {
        return owl2Datatype == XSD_STRING;
    }

    @Override
    public boolean isInteger() {
        return owl2Datatype == XSD_INTEGER;
    }

    @Override
    public boolean isFloat() {
        return owl2Datatype == XSD_FLOAT;
    }

    @Override
    public boolean isDouble() {
        return owl2Datatype == XSD_DOUBLE;
    }

    @Override
    public boolean isBoolean() {
        return owl2Datatype == XSD_BOOLEAN;
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return owl2Datatype == RDF_PLAIN_LITERAL;
    }

    @Override
    public boolean isTopDatatype() {
        return owl2Datatype == RDFS_LITERAL;
    }

    @Override
    public DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE;
    }

    @Override
    public boolean isBuiltIn() {
        return true;
    }

    @Override
    public boolean isOWLDatatype() {
        return true;
    }

    @Override
    public String toStringID() {
        return owl2Datatype.getIRI().toString();
    }

    @Override
    public String toString() {
        return toStringID();
    }

    @Override
    public IRI getIRI() {
        return owl2Datatype.getIRI();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDatatype)) {
            return false;
        }
        OWLDatatype other = (OWLDatatype) obj;
        return owl2Datatype.getIRI().equals(other.getIRI());
    }

    @Override
    public Stream<OWLEntity> signature() {
        return Stream.of((OWLEntity) this);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return equals(owlEntity);
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return Stream.of((OWLDatatype) this);
    }

    @Override
    public boolean isTopEntity() {
        return owl2Datatype == RDFS_LITERAL;
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        if (o == null) {
            throw new NullPointerException("o cannot be null in a compareTo call.");
        }
        int diff = Integer.compare(typeIndex(), o.typeIndex());
        if (diff != 0) {
            return diff;
        }
        if (o instanceof OWLDatatype) {
            diff = getIRI().compareTo(((OWLDatatype) o).getIRI());
        }
        return diff;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
