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
import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * An optimised implementation of OWLDatatype for OWL2Datatypes.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.2.0
 */
public class OWL2DatatypeImpl implements OWLDatatype {

    private static final long serialVersionUID = 40000L;

    /**
     * Creates an instance of {@code OWLDatatypeImplForOWL2Datatype} for the
     * specified {@link OWL2Datatype}.
     * 
     * @param owl2Datatype
     *        The datatype. Not {@code null}.
     * @throws NullPointerException
     *         if {@code owl2Datatype} is {@code null}.
     */
    public OWL2DatatypeImpl(@Nonnull OWL2Datatype owl2Datatype) {
        this.owl2Datatype = checkNotNull(owl2Datatype,
            "owl2Datatype must not be null");
    }

    @Nonnull
    private final OWL2Datatype owl2Datatype;

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
    public EntityType<?> getEntityType() {
        return EntityType.DATATYPE;
    }

    @Override
    public boolean isType(EntityType<?> entityType) {
        return EntityType.DATATYPE.equals(entityType);
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
    public boolean equals(Object obj) {
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
        return owl2Datatype == RDF_PLAIN_LITERAL;
    }

    @Override
    public int compareTo(OWLObject o) {
        if (!(o instanceof OWLDatatype)) {
            OWLObjectTypeIndexProvider provider = new OWLObjectTypeIndexProvider();
            return provider.getTypeIndex(o);
        }
        OWLDatatype other = (OWLDatatype) o;
        return getIRI().compareTo(other.getIRI());
    }

    @Override
    public int hashCode() {
        return HashCode.hashCode(this);
    }
}
