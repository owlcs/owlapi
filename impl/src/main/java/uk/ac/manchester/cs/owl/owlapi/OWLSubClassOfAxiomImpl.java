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
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSubClassOfAxiomImpl extends OWLClassAxiomImpl implements OWLSubClassOfAxiom {

    private OWLClassExpression subClass;

    private OWLClassExpression superClass;


    public OWLSubClassOfAxiomImpl(OWLDataFactory dataFactory, OWLClassExpression subClass, OWLClassExpression superClass, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subClass = subClass;
        this.superClass = superClass;
    }

    public Set<OWLClassExpression> getClassExpressions() {
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>(3);
        classExpressions.add(subClass);
        classExpressions.add(superClass);
        return classExpressions;
    }

    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc) {
        Set<OWLClassExpression> classExpressions = getClassExpressions();
        for (OWLClassExpression ce : desc) {
            classExpressions.remove(ce);
        }
        return classExpressions;
    }

    public OWLSubClassOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass, mergeAnnos(annotations));
    }

    public OWLSubClassOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
    }

    public boolean contains(OWLClassExpression ce) {
        return subClass.equals(ce) || superClass.equals(ce);
    }

    public OWLClassExpression getSubClass() {
        return subClass;
    }


    public OWLClassExpression getSuperClass() {
        return superClass;
    }


    public boolean isGCI() {
        return subClass.isAnonymous();
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLSubClassOfAxiom)) {
            return false;
        }
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) obj;
        return other.getSubClass().equals(subClass) && other.getSuperClass().equals(superClass);
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.SUBCLASS_OF;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) object;
        int diff = subClass.compareTo(other.getSubClass());
        if (diff != 0) {
            return diff;
        }
        return superClass.compareTo(other.getSuperClass());
    }
}
