/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 24-Mar-2009
 */
public class OWLDatatypeDefinitionAxiomImpl extends OWLAxiomImpl implements OWLDatatypeDefinitionAxiom {

    private OWLDatatype datatype;

    private OWLDataRange dataRange;


    public OWLDatatypeDefinitionAxiomImpl(OWLDataFactory dataFactory, OWLDatatype datatype, OWLDataRange dataRange, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.datatype = datatype;
        this.dataRange = dataRange;
    }

    public OWLAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange());
    }

    public OWLDatatypeDefinitionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange(), mergeAnnos(annotations));
    }

    public OWLDatatype getDatatype() {
        return datatype;
    }


    public OWLDataRange getDataRange() {
        return dataRange;
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isLogicalAxiom() {
        return true;
    }

    public boolean isAnnotationAxiom() {
        return false;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.DATATYPE_DEFINITION;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


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
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDatatypeDefinitionAxiom)) {
            return false;
        }
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) obj;
        return datatype.equals(other.getDatatype()) && dataRange.equals(other.getDataRange());
    }
}
