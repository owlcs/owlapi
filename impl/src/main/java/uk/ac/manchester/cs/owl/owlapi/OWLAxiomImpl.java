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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.NNF;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLAxiomImpl extends OWLObjectImpl implements OWLAxiom {

    private OWLAxiom nnf;

    private final Set<OWLAnnotation> annotations;

    public OWLAxiomImpl(OWLDataFactory dataFactory, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory);
        if (!annotations.isEmpty()) {
            this.annotations = CollectionFactory.getCopyOnRequestSet(new TreeSet<OWLAnnotation>(annotations));
        }
        else {
            this.annotations = Collections.emptySet();
        }
    }

    public boolean isAnnotated() {
        return !annotations.isEmpty();
    }

    public Set<OWLAnnotation> getAnnotations() {
        return annotations;
    }

    public Set<OWLAnnotation> getAnnotations(OWLAnnotationProperty annotationProperty) {
        if (annotations.isEmpty()) {
            return annotations;
        }
        else {
            Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
            for (OWLAnnotation anno : annotations) {
                if (anno.getProperty().equals(annotationProperty)) {
                    result.add(anno);
                }
            }
            return result;
        }
    }

    /**
     * Determines if another axiom is equal to this axiom not taking into consideration the annotations on the axiom
     * @param axiom The axiom to test if equal
     * @return <code>true</code> if <code>axiom</code> without annotations is equal to this axiom without annotations
     *         otherwise <code>false</code>.
     */
    public boolean equalsIgnoreAnnotations(OWLAxiom axiom) {
        return this.getAxiomWithoutAnnotations().equals(axiom.getAxiomWithoutAnnotations());
    }

    /**
     * Determines if this axiom is one of the specified types
     * @param axiomTypes The axiom types to check for
     * @return <code>true</code> if this axiom is one of the specified types, otherwise <code>false</code>
     * @since 3.0
     */
    public boolean isOfType(AxiomType<?>... axiomTypes) {
        for (AxiomType<?> type : axiomTypes) {
            if (getAxiomType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if this axiom is one of the specified types
     * @param types The axiom types to check for
     * @return <code>true</code> if this axioms is one of the specified types, otherwise <code>false</code>
     * @since 3.0
     */
    public boolean isOfType(Set<AxiomType<?>> types) {
        return types.contains(getAxiomType());
    }

    /**
     * A convenience method for implementation that returns a set containing the annotations on this axiom plus the
     * annoations in the specified set.
     * @param annos The annotations to add to the annotations on this axiom
     * @return The annotations
     */
    protected Set<OWLAnnotation> mergeAnnos(Set<OWLAnnotation> annos) {
        Set<OWLAnnotation> merged = new HashSet<OWLAnnotation>(annos);
        merged.addAll(getAnnotations());
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
        return annotations.equals(other.getAnnotations());
    }


    public Set<OWLEntity> getReferencedEntities() {
    	return getSignature();
    }


    public OWLAxiom getNNF() {
        if (nnf == null) {
            NNF con = new NNF(getOWLDataFactory());
            nnf = accept(con);
        }
        return nnf;
    }
}
