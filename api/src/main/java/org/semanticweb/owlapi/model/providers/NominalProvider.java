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
package org.semanticweb.owlapi.model.providers;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectOneOf;

/**
 * Nominal provider interface.
 */
public interface NominalProvider {

    /**
     * Gets an OWLDataOneOf
     * <a href= "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals" >(see
     * spec)</a>
     *
     * @param values The set of values that the data one of should contain.
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(Stream<? extends OWLLiteral> values);

    /**
     * Gets an OWLDataOneOf
     * <a href= "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals" >(see
     * spec)</a>
     *
     * @param values The set of values that the data one of should contain.
     * @return A data one of that enumerates the specified set of values
     */
    default OWLDataOneOf getOWLDataOneOf(Collection<? extends OWLLiteral> values) {
        checkIterableNotNull(values, "values cannot be null or contain null or empty", false);
        return getOWLDataOneOf(values.stream());
    }

    /**
     * Gets an OWLDataOneOf
     * <a href= "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals" >(see
     * spec)</a>
     *
     * @param values The set of values that the data one of should contain. Cannot be null or
     * contain null values.
     * @return A data one of that enumerates the specified set of values
     */
    default OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        checkIterableNotNull(values, "values cannot be null", true);
        return getOWLDataOneOf(Stream.of(values));
    }

    /**
     * @param values indivudals for restriction. Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals
     */
    OWLObjectOneOf getOWLObjectOneOf(Stream<? extends OWLIndividual> values);

    /**
     * @param values indivudals for restriction. Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals
     */
    default OWLObjectOneOf getOWLObjectOneOf(Collection<? extends OWLIndividual> values) {
        return getOWLObjectOneOf(checkNotNull(values, "values cannot be null").stream());
    }

    /**
     * @param individuals indivudals for restriction. Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals
     */
    default OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        checkNotNull(individuals, "individuals cannot be null");
        return getOWLObjectOneOf(Stream.of(individuals));
    }
}
