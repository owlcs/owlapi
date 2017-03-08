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
package org.semanticweb.owlapi.model.axiomproviders;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Disjoint classes and properties provider.
 */
public interface DisjointAxiomProvider {

    /**
     * @param classExpressions Disjoint classes. Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations
     */
    default OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        Collection<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions, Collections.emptySet());
    }

    /**
     * @param classExpressions Disjoint classes. Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations
     */
    default OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        OWLClassExpression... classExpressions) {
        checkIterableNotNull(classExpressions, "classExpressions cannot be null", true);
        return getOWLDisjointClassesAxiom(CollectionFactory.createSet(classExpressions));
    }

    /**
     * @param classExpressions Disjoint classes. Cannot be null or contain nulls.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations
     */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        Collection<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations);

    /**
     * @param classA Disjoint class.
     * @param classB Disjoint class.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations
     */
    default OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression classA,
        OWLClassExpression classB, Collection<OWLAnnotation> annotations) {
        return getOWLDisjointClassesAxiom(Arrays.asList(classA, classB), annotations);
    }

    /**
     * @param classExpressions Disjoint classes. Cannot be null or contain nulls.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations
     */
    default OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        Stream<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations) {
        return getOWLDisjointClassesAxiom(asList(classExpressions), annotations);
    }

    /**
     * @param classExpressions Disjoint classes. Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations
     */
    default OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        Stream<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(asList(classExpressions), Collections.emptySet());
    }

    /**
     * @param properties Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties
     */
    default OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        Collection<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties, Collections.emptySet());
    }

    /**
     * @param properties Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties
     */
    default OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        OWLObjectPropertyExpression... properties) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    /**
     * @param properties Cannot be null or contain nulls.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties and annotations
     */
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        Collection<? extends OWLObjectPropertyExpression> properties,
        Collection<OWLAnnotation> annotations);

    /**
     * @param p Disjoint property
     * @param q Disjoint property
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties and annotations
     */
    default OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        OWLObjectPropertyExpression p, OWLObjectPropertyExpression q,
        Collection<OWLAnnotation> annotations) {
        return getOWLDisjointObjectPropertiesAxiom(Arrays.asList(p, q), annotations);
    }

    /**
     * @param dataProperties Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties
     */
    default OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        OWLDataPropertyExpression... dataProperties) {
        checkIterableNotNull(dataProperties, "properties cannot be null", true);
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(dataProperties));
    }

    /**
     * @param properties Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties
     */
    default OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        Collection<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties, Collections.emptySet());
    }

    /**
     * @param properties Cannot be null or contain nulls.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties and annotations
     */
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        Collection<? extends OWLDataPropertyExpression> properties,
        Collection<OWLAnnotation> annotations);

    /**
     * @param p Disjoint property
     * @param q Disjoint property
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties and annotations
     */
    default OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        OWLDataPropertyExpression p, OWLDataPropertyExpression q,
        Collection<OWLAnnotation> annotations) {
        return getOWLDisjointDataPropertiesAxiom(Arrays.asList(p, q), annotations);
    }
}
