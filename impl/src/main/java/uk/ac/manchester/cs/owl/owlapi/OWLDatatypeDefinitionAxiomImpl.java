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

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLDatatypeDefinitionAxiomImpl extends OWLAxiomImpl implements
        OWLDatatypeDefinitionAxiom {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLDatatype datatype;
    @Nonnull
    private final OWLDataRange dataRange;

    /**
     * @param datatype
     *        datatype
     * @param dataRange
     *        datarange
     * @param annotations
     *        annotations on the axiom
     */
    public OWLDatatypeDefinitionAxiomImpl(@Nonnull OWLDatatype datatype,
            @Nonnull OWLDataRange dataRange,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.datatype = checkNotNull(datatype, "datatype cannot be null");
        this.dataRange = checkNotNull(dataRange, "dataRange cannot be null");
    }

    @Override
    public OWLAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLDatatypeDefinitionAxiomImpl(getDatatype(),
                getDataRange(), NO_ANNOTATIONS);
    }

    @Override
    public OWLDatatypeDefinitionAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLDatatypeDefinitionAxiomImpl(getDatatype(),
                getDataRange(), mergeAnnos(annotations));
    }

    @Override
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
    public OWLDataRange getDataRange() {
        return dataRange;
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isLogicalAxiom() {
        return true;
    }

    @Override
    public boolean isAnnotationAxiom() {
        return false;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.DATATYPE_DEFINITION;
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
    protected int compareObjectOfSameType(OWLObject object) {
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) object;
        int diff = getDatatype().compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return getDataRange().compareTo(other.getDataRange());
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (!(obj instanceof OWLDatatypeDefinitionAxiom)) {
                return false;
            }
            OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) obj;
            return datatype.equals(other.getDatatype())
                    && dataRange.equals(other.getDataRange());
        }
        return false;
    }
}
