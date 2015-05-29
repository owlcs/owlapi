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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * A {@code Comparator} which compares entities. Entities are compared first by
 * their type (in the following order: Class, Object property, Data property,
 * Individual, Datatype) then by their short form (using the specified short
 * form provider).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEntityComparator implements Comparator<OWLEntity>, OWLEntityVisitor {

    private final @Nonnull ShortFormProvider shortFormProvider;
    int lastValue;

    /**
     * Constructs an entity comparator which uses the specified short form
     * provider.
     * 
     * @param shortFormProvider
     *        the short form provider to use
     */
    public OWLEntityComparator(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider, "shortFormProvider cannot be null");
    }

    @Override
    public int compare(@Nullable OWLEntity o1, @Nullable OWLEntity o2) {
        verifyNotNull(o1).accept(this);
        int i1 = lastValue;
        verifyNotNull(o2).accept(this);
        int i2 = lastValue;
        int delta = i1 - i2;
        if (delta != 0) {
            return delta;
        }
        String s1 = getShortForm(verifyNotNull(o1));
        String s2 = getShortForm(verifyNotNull(o2));
        return s1.compareTo(s2);
    }

    private String getShortForm(OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }

    @Override
    public void visit(OWLClass cls) {
        lastValue = 0;
    }

    @Override
    public void visit(OWLObjectProperty property) {
        lastValue = 1;
    }

    @Override
    public void visit(OWLDataProperty property) {
        lastValue = 2;
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        lastValue = 3;
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        lastValue = 4;
    }

    @Override
    public void visit(OWLDatatype datatype) {
        lastValue = 5;
    }
}
