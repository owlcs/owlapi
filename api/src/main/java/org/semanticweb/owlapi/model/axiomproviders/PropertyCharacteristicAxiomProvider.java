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

import java.util.Collection;
import java.util.Collections;

import org.semanticweb.owlapi.model.*;

/**
 * Property characteristic axioms provider (functional, transitive, etc.).
 */
public interface PropertyCharacteristicAxiomProvider {

    /**
     * @param property
     *        property
     * @return a functional object property axiom
     */
    default OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a functional object property axiom with annotations
     */
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return an inverse functional object property axiom
     */
    default OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an inverse functional object property axiom with annotations
     */
    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property, Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a reflexive object property axiom
     */
    default OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a reflexive object property axiom with annotations
     */
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return an irreflexive object property axiom
     */
    default OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an irreflexive object property axiom with annotations
     */
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a symmetric property axiom
     */
    default OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a symmetric property axiom with annotations
     */
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations);

    /**
     * @param propertyExpression
     *        property Expression
     * @return an asymmetric object property axiom on the specified argument
     */
    default OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression, Collections.emptySet());
    }

    /**
     * @param propertyExpression
     *        property Expression
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an asymmetric object property axiom on the specified argument
     *         with annotations
     */
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression,
            Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a transitive object property axiom on the specified argument
     */
    default OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a transitive object property axiom on the specified argument with
     *         annotations
     */
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @return a functional data property axiom
     */
    default OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a functional data property axiom with annotations
     */
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property,
            Collection<OWLAnnotation> annotations);
}
