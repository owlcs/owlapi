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
package org.semanticweb.owlapi.expression;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;

/**
 * An entity checker that maps from string to entities using a bidirectional short form provider.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ShortFormEntityChecker implements OWLEntityChecker {

    private final BidirectionalShortFormProvider shortFormProvider;

    /**
     * Creates a short form entity checker, which uses the specified bidirectional short form
     * provider to map entity name strings to entities.
     *
     * @param shortFormProvider The BidirectionalShortFormProvider that should be used to perform
     * the required mapping.
     */
    public ShortFormEntityChecker(BidirectionalShortFormProvider shortFormProvider) {
        this.shortFormProvider =
            checkNotNull(shortFormProvider, "shortFormProvider cannot be null");
    }

    @Override
    @Nullable
    public OWLClass getOWLClass(String name) {
        return find(name, OWLEntity::isOWLClass, OWLEntity::asOWLClass);
    }

    @Override
    @Nullable
    public OWLDataProperty getOWLDataProperty(String name) {
        return find(name, OWLEntity::isOWLDataProperty, OWLEntity::asOWLDataProperty);
    }

    @Override
    @Nullable
    public OWLDatatype getOWLDatatype(String name) {
        return find(name, OWLEntity::isOWLDatatype, OWLEntity::asOWLDatatype);
    }

    @Override
    @Nullable
    public OWLNamedIndividual getOWLIndividual(String name) {
        return find(name, OWLEntity::isOWLNamedIndividual, OWLEntity::asOWLNamedIndividual);
    }

    @Override
    @Nullable
    public OWLObjectProperty getOWLObjectProperty(String name) {
        return find(name, OWLEntity::isOWLObjectProperty, OWLEntity::asOWLObjectProperty);
    }

    @Override
    @Nullable
    public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
        return find(name, OWLEntity::isOWLAnnotationProperty, OWLEntity::asOWLAnnotationProperty);
    }

    @Nullable
    protected <T extends OWLEntity> T find(String name, Predicate<OWLEntity> p,
        Function<OWLEntity, T> f) {
        checkNotNull(name, "name cannot be null");
        Optional<OWLEntity> findFirst = shortFormProvider.entities(name).filter(p).findFirst();
        if (findFirst.isPresent()) {
            return f.apply(findFirst.get());
        }
        return null;
    }
}
