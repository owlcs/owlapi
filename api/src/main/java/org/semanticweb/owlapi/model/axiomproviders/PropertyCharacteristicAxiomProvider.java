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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * Property characteristic axioms provider (functional, transitive, etc.).
 */
public interface PropertyCharacteristicAxiomProvider {

    /**
     * @param property
     *        property
     * @return a functional object property axiom
     */
    @Nonnull
    default OWLFunctionalObjectPropertyAxiom
            getOWLFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a functional object property axiom with annotations
     */
    @Nonnull
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return an inverse functional object property axiom
     */
    @Nonnull
    default OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an inverse functional object property axiom with annotations
     */
    @Nonnull
    OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a reflexive object property axiom
     */
    @Nonnull
    default OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a reflexive object property axiom with annotations
     */
    @Nonnull
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return an irreflexive object property axiom
     */
    @Nonnull
    default OWLIrreflexiveObjectPropertyAxiom
            getOWLIrreflexiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an irreflexive object property axiom with annotations
     */
    @Nonnull
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a symmetric property axiom
     */
    @Nonnull
    default OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a symmetric property axiom with annotations
     */
    @Nonnull
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param propertyExpression
     *        property Expression
     * @return an asymmetric object property axiom on the specified argument
     */
    @Nonnull
    default OWLAsymmetricObjectPropertyAxiom
            getOWLAsymmetricObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression,
                Collections.emptySet());
    }

    /**
     * @param propertyExpression
     *        property Expression
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an asymmetric object property axiom on the specified argument
     *         with annotations
     */
    @Nonnull
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression propertyExpression,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a transitive object property axiom on the specified argument
     */
    @Nonnull
    default OWLTransitiveObjectPropertyAxiom
            getOWLTransitiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a transitive object property axiom on the specified argument with
     *         annotations
     */
    @Nonnull
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a functional data property axiom
     */
    @Nonnull
    default OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a functional data property axiom with annotations
     */
    @Nonnull
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull Set<OWLAnnotation> annotations);
}
