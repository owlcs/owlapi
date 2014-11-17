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
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;

/** HasKey provider interface. */
public interface HasKeyAxiomProvider {

    /**
     * @param ce
     *        class expression
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments
     */
    @Nonnull
    default OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull Set<? extends OWLPropertyExpression> properties) {
        return getOWLHasKeyAxiom(ce, properties, Collections.emptySet());
    }

    /**
     * @param ce
     *        class expression
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments
     */
    @Nonnull
    default OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull OWLPropertyExpression... properties) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        return getOWLHasKeyAxiom(ce, CollectionFactory.createSet(properties));
    }

    /**
     * @param ce
     *        class expression
     * @param objectProperties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments and annotations
     */
    @Nonnull
    OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull Set<? extends OWLPropertyExpression> objectProperties,
            @Nonnull Set<OWLAnnotation> annotations);
}
