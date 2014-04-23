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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLDisjointUnionAxiomImpl extends OWLClassAxiomImpl implements
        OWLDisjointUnionAxiom {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLClass owlClass;
    @Nonnull
    private final Set<OWLClassExpression> classExpressions;

    /**
     * @param owlClass
     *        union
     * @param classExpressions
     *        disjoint classes
     * @param annotations
     *        annotations
     */
    public OWLDisjointUnionAxiomImpl(@Nonnull OWLClass owlClass,
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.owlClass = checkNotNull(owlClass, "owlClass cannot be null");
        this.classExpressions = new TreeSet<OWLClassExpression>(checkNotNull(
                classExpressions, "classExpressions cannot be null"));
    }

    @Override
    public Set<OWLClassExpression> getClassExpressions() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(classExpressions);
    }

    @Override
    public OWLDisjointUnionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLDisjointUnionAxiomImpl(getOWLClass(),
                getClassExpressions(), NO_ANNOTATIONS);
    }

    @Override
    public OWLDisjointUnionAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLDisjointUnionAxiomImpl(getOWLClass(),
                getClassExpressions(), mergeAnnos(annotations));
    }

    @Override
    public OWLClass getOWLClass() {
        return owlClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (!(obj instanceof OWLDisjointUnionAxiom)) {
                return false;
            }
            return ((OWLDisjointUnionAxiom) obj).getOWLClass().equals(owlClass);
        }
        return false;
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.DISJOINT_UNION;
    }

    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom() {
        return new OWLEquivalentClassesAxiomImpl(
                new HashSet<OWLClassExpression>(Arrays.asList(owlClass,
                        new OWLObjectUnionOfImpl(getClassExpressions()))),
                NO_ANNOTATIONS);
    }

    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom() {
        return new OWLDisjointClassesAxiomImpl(getClassExpressions(),
                NO_ANNOTATIONS);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLDisjointUnionAxiom other = (OWLDisjointUnionAxiom) object;
        int diff = owlClass.compareTo(other.getOWLClass());
        if (diff != 0) {
            return diff;
        }
        return compareSets(classExpressions, other.getClassExpressions());
    }
}
