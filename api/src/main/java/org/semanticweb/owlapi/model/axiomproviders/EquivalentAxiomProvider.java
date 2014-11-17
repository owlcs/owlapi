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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Equivalent classes and properties provider.
 */
public interface EquivalentAxiomProvider {

    /**
     * @param classExpressions
     *        equivalent classes. Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations
     */
    @Nonnull
    default OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions,
                Collections.emptySet());
    }

    /**
     * @param classExpressions
     *        equivalent classes. Cannot be null or contain nulls.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations
     */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param classExpressions
     *        equivalent classes. Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations
     */
    @Nonnull
    default OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression... classExpressions) {
        checkIterableNotNull(classExpressions,
                "classExpressions cannot be null", true);
        return getOWLEquivalentClassesAxiom(CollectionFactory
                .createSet(classExpressions));
    }

    /**
     * @param clsA
     *        one class for equivalence
     * @param clsB
     *        one class for equivalence
     * @return an equivalent classes axiom with specified operands and no
     *         annotations (special case with only two operands)
     */
    @Nonnull
    default OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB) {
        return getOWLEquivalentClassesAxiom(clsA, clsB, Collections.emptySet());
    }

    /**
     * @param clsA
     *        one class for equivalence
     * @param clsB
     *        one class for equivalence
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations (special case with only two operands)
     */
    @Nonnull
    default OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB,
            @Nonnull Set<OWLAnnotation> annotations) {
        return getOWLEquivalentClassesAxiom(
                CollectionFactory.createSet(clsA, clsB), annotations);
    }

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties
     */
    @Nonnull
    default
            OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties,
                Collections.emptySet());
    }

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations
     */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties
     */
    @Nonnull
    default OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression... properties) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    /**
     * @param propertyA
     *        property A
     * @param propertyB
     *        property B
     * @return an equivalent properties axiom with specified properties
     */
    @Nonnull
    default OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyA,
                    @Nonnull OWLObjectPropertyExpression propertyB) {
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB,
                Collections.emptySet());
    }

    /**
     * @param propertyA
     *        property A
     * @param propertyB
     *        property B
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations
     */
    @Nonnull
    default OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyA,
                    @Nonnull OWLObjectPropertyExpression propertyB,
                    @Nonnull Set<OWLAnnotation> annotations) {
        return getOWLEquivalentObjectPropertiesAxiom(
                CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom
     */
    @Nonnull
    default
            OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties,
                Collections.emptySet());
    }

    /**
     * @param properties
     *        properties
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations
     */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom
     */
    @Nonnull
    default OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression... properties) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    /**
     * @param propertyA
     *        property A
     * @param propertyB
     *        property B
     * @return an equivalent data properties axiom
     */
    @Nonnull
    default OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression propertyA,
                    @Nonnull OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB,
                Collections.emptySet());
    }

    /**
     * @param propertyA
     *        property A
     * @param propertyB
     *        property B
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations
     */
    @Nonnull
    default OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression propertyA,
                    @Nonnull OWLDataPropertyExpression propertyB,
                    @Nonnull Set<OWLAnnotation> annotations) {
        return getOWLEquivalentDataPropertiesAxiom(
                CollectionFactory.createSet(propertyA, propertyB), annotations);
    }
}
