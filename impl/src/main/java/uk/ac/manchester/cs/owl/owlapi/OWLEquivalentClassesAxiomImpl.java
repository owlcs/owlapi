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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEquivalentClassesAxiomImpl extends OWLNaryClassAxiomImpl
        implements OWLEquivalentClassesAxiom {

    private static final long serialVersionUID = 40000L;
    private transient WeakReference<Set<OWLClass>> namedClasses = null;

    /**
     * @param classExpressions
     *        equivalent classes
     * @param annotations
     *        annotations
     */
    public OWLEquivalentClassesAxiomImpl(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(classExpressions, annotations);
    }

    @Override
    public OWLEquivalentClassesAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLEquivalentClassesAxiomImpl(getClassExpressions(),
                NO_ANNOTATIONS);
    }

    @Override
    public OWLEquivalentClassesAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLEquivalentClassesAxiomImpl(getClassExpressions(),
                mergeAnnos(annotations));
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> asPairwiseAxioms() {
        List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(
                getClassExpressions());
        Set<OWLEquivalentClassesAxiom> result = new HashSet<OWLEquivalentClassesAxiom>();
        for (int i = 0; i < classExpressions.size() - 1; i++) {
            OWLClassExpression ceI = classExpressions.get(i);
            OWLClassExpression ceJ = classExpressions.get(i + 1);
            result.add(new OWLEquivalentClassesAxiomImpl(
                    new HashSet<OWLClassExpression>(Arrays.asList(ceI, ceJ)),
                    NO_ANNOTATIONS));
        }
        return result;
    }

    @Override
    public boolean containsNamedEquivalentClass() {
        return !getNamedClasses().isEmpty();
    }

    @Override
    public boolean containsOWLNothing() {
        for (OWLClassExpression desc : getClassExpressions()) {
            if (desc.isOWLNothing()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOWLThing() {
        for (OWLClassExpression desc : getClassExpressions()) {
            if (desc.isOWLThing()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<OWLClass> getNamedClasses() {
        Set<OWLClass> toReturn = null;
        if (namedClasses != null) {
            toReturn = namedClasses.get();
        }
        if (toReturn == null) {
            Set<OWLClass> clses = new HashSet<OWLClass>(1);
            for (OWLClassExpression desc : getClassExpressions()) {
                if (!desc.isAnonymous() && !desc.isOWLNothing()
                        && !desc.isOWLThing()) {
                    clses.add(desc.asOWLClass());
                }
            }
            toReturn = CollectionFactory
                    .getCopyOnRequestSetFromImmutableCollection(clses);
            namedClasses = new WeakReference<Set<OWLClass>>(toReturn);
        }
        return toReturn;
    }

    @Override
    public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
        Set<OWLSubClassOfAxiom> result = new HashSet<OWLSubClassOfAxiom>();
        List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(
                getClassExpressions());
        for (int i = 0; i < classExpressions.size(); i++) {
            for (int j = 0; j < classExpressions.size(); j++) {
                if (i != j) {
                    result.add(new OWLSubClassOfAxiomImpl(classExpressions
                            .get(i), classExpressions.get(j), NO_ANNOTATIONS));
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLEquivalentClassesAxiom;
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
        return AxiomType.EQUIVALENT_CLASSES;
    }
}
