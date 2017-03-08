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
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLNaryClassAxiom extends OWLClassAxiom, OWLNaryAxiom<OWLClassExpression>,
    OWLSubClassOfAxiomSetShortCut, HasOperands<OWLClassExpression> {

    @Override
    default Stream<?> components() {
        return Stream.of(classExpressions(), annotations());
    }

    @Override
    default Stream<?> componentsWithoutAnnotations() {
        return Stream.of(classExpressions());
    }

    @Override
    default Stream<?> componentsAnnotationsFirst() {
        return Stream.of(annotations(), classExpressions());
    }

    /**
     * Gets all of the top level class expressions that appear in this axiom.
     *
     * @return A {@code Set} of class expressions that appear in the axiom.
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLClassExpression> getClassExpressions() {
        return asSet(classExpressions());
    }

    /**
     * Gets all of the top level class expressions that appear in this axiom.
     *
     * @return A {@code Set} of class expressions that appear in the axiom.
     */
    Stream<OWLClassExpression> classExpressions();

    @Override
    default Stream<OWLClassExpression> operands() {
        return classExpressions();
    }

    /**
     * A convenience method that obtains the class expression returned by the
     * {@link #getClassExpressions()} method as a list of class expressions.
     *
     * @return A list of the class expressions in this axiom.
     * @deprecated use the stream method
     */
    @Deprecated
    default List<OWLClassExpression> getClassExpressionsAsList() {
        return asList(classExpressions());
    }

    /**
     * Determines if this class axiom contains the specified class expression as an operand.
     *
     * @param ce The class expression to test for
     * @return {@code true} if this axiom contains the specified class expression as an operand,
     * otherwise {@code false}.
     */
    boolean contains(OWLClassExpression ce);

    /**
     * Gets the set of class expressions that appear in this axiom minus the specfied class
     * expressions.
     *
     * @param desc The class expressions to subtract from the class expressions in this axiom
     * @return A set containing all of the class expressions in this axiom (the class expressions
     * returned by getClassExpressions()) minus the specified list of class expressions
     */
    Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc);
}
