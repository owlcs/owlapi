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
package org.semanticweb.owlapi6.impl;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi6.vocab.OWL2Datatype.XSD_STRING;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.documents.ToStringRenderer;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.DataRangeType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

/**
 * An optimised implementation of OWLDatatype for OWL2Datatypes.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.2.0
 */
public class OWL2DatatypeImpl implements OWLDatatype {

    private final OWL2Datatype owl2Datatype;
    private final int hashCode;

    /**
     * Creates an instance of {@code OWLDatatypeImplForOWL2Datatype} for the
     * specified {@link OWL2Datatype}.
     * 
     * @param owl2Datatype
     *        The datatype. Not {@code null}.
     * @throws NullPointerException
     *         if {@code owl2Datatype} is {@code null}.
     */
    public OWL2DatatypeImpl(OWL2Datatype owl2Datatype) {
        this.owl2Datatype = checkNotNull(owl2Datatype, "owl2Datatype must not be null");
        hashCode = initHashCode();
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
        return OWLObjectType.equals(this, (OWLDatatype) obj);
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
    public int compareTo(@Nullable OWLObject obj) {
        return OWLObjectType.compareTo(this, verifyNotNull(obj));
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toFunctionalSyntax(PrefixManager pm) {
        return owl2Datatype.getIRI().toFunctionalSyntax(pm);
    }

    @Override
    public String toManchesterSyntax(PrefixManager pm) {
        return owl2Datatype.getIRI().toSyntax(new ManchesterSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toFunctionalSyntax() {
        return owl2Datatype.getIRI().toSyntax(new FunctionalSyntaxDocumentFormat());
    }

    @Override
    public String toManchesterSyntax() {
        return owl2Datatype.getIRI().toSyntax(new ManchesterSyntaxDocumentFormat());
    }

    @Override
    public String toSyntax(OWLDocumentFormat format) {
        return ToStringRenderer.getInstance(format).render(owl2Datatype.getIRI());
    }

    @Override
    public String toSyntax(OWLDocumentFormat format, PrefixManager pm) {
        return ToStringRenderer.getInstance(format, pm).render(owl2Datatype.getIRI());
    }
}
