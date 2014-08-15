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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.NNF;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 26-Oct-2006
 */
public abstract class OWLAxiomImpl extends OWLObjectImpl implements OWLAxiom,
        CollectionContainer<OWLAnnotation> {

    private static final long serialVersionUID = 30406L;
    private OWLAxiom nnf;
    private final List<OWLAnnotation> annotations;

    /**
     * @param annotations
     *        annotations on the axiom
     */
    public OWLAxiomImpl(Collection<? extends OWLAnnotation> annotations) {
        super();
        if (!annotations.isEmpty()) {
            this.annotations = new ArrayList<OWLAnnotation>(annotations);
            CollectionFactory.sortOptionally(this.annotations);
        } else {
            this.annotations = Collections.emptyList();
        }
    }

    @Override
    public boolean isAnnotated() {
        return !annotations.isEmpty();
    }

    // TODO when processing annotations on OWLOntology:: add axiom, needs
    // optimizing
    @Override
    public Set<OWLAnnotation> getAnnotations() {
        if (annotations.isEmpty()) {
            return Collections.emptySet();
        }
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(annotations);
    }

    @Override
    public void accept(CollectionContainerVisitor<OWLAnnotation> t) {
        final int size = annotations.size();
        for (int i = 0; i < size; i++) {
            t.visitItem(annotations.get(i));
        }
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(
            OWLAnnotationProperty annotationProperty) {
        if (annotations.isEmpty()) {
            return Collections.emptySet();
        } else {
            Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
            for (OWLAnnotation anno : annotations) {
                if (anno.getProperty().equals(annotationProperty)) {
                    result.add(anno);
                }
            }
            return result;
        }
    }

    @Override
    public boolean equalsIgnoreAnnotations(OWLAxiom axiom) {
        return getAxiomWithoutAnnotations().equals(
                axiom.getAxiomWithoutAnnotations());
    }

    @Override
    public boolean isOfType(AxiomType<?>... axiomTypes) {
        for (AxiomType<?> type : axiomTypes) {
            if (getAxiomType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOfType(Set<AxiomType<?>> types) {
        return types.contains(getAxiomType());
    }

    /**
     * A convenience method for implementation that returns a set containing the
     * annotations on this axiom plus the annotations in the specified set.
     * 
     * @param annos
     *        The annotations to add to the annotations on this axiom
     * @return The annotations
     */
    protected Set<OWLAnnotation> mergeAnnos(Set<OWLAnnotation> annos) {
        Set<OWLAnnotation> merged = new HashSet<OWLAnnotation>(annos);
        merged.addAll(annotations);
        return merged;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLAxiom)) {
            return false;
        }
        OWLAxiom other = (OWLAxiom) obj;
        // for OWLAxiomImpl comparisons, do not create wrapper objects
        if (other instanceof OWLAxiomImpl) {
            return annotations.equals(((OWLAxiomImpl) other).annotations);
        }
        return getAnnotations().equals(other.getAnnotations());
    }

    @Override
    public OWLAxiom getNNF() {
        if (nnf == null) {
            NNF con = new NNF(new OWLDataFactoryImpl());
            nnf = accept(con);
        }
        return nnf;
    }
}
