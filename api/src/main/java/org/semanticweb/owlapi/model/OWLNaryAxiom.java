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

import java.util.Collection;
import java.util.Set;

/**
 * Represents an axiom that contains two or more operands that could also be
 * represented with multiple pairwise axioms.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @param <C>
 *        class of contained objects
 * @since 3.0.0
 */
public interface OWLNaryAxiom<C extends OWLObject> extends OWLAxiom {

    /**
     * Gets this axiom as a set of pairwise axioms. Note that annotations on
     * this axiom will not be copied to each axiom returned in the set of
     * pairwise axioms.<br>
     * Note: This will contain all pairs, i.e., for the set "a, b, c" the pairs
     * "a, b", "a, c", "b, c" will be returned. For some applications, only
     * "a, b", "b, c" are required.
     * 
     * @return This axiom as a set of pairwise axioms.
     */
    Set<? extends OWLNaryAxiom<C>> asPairwiseAxioms();

    /**
     * @param <T>
     *        type returned by visitor
     * @param visitor
     *        visitor to apply to all pairwise elements in this axiom
     * @return collection of all visitor return values that are not null
     */
    <T> Collection<T> walkPairwise(OWLPairwiseVisitor<T, C> visitor);

    /**
     * @param visitor
     *        visitor to apply to all pairwise elements in this axiom
     */
    void forEach(OWLPairwiseVoidVisitor<C> visitor);

    /**
     * @param visitor
     *        visitor to apply to all pairwise elements in this axiom
     * @return collection of all visitor return values that are not null
     */
    boolean anyMatch(OWLPairwiseBooleanVisitor<C> visitor);

    /**
     * @param visitor
     *        visitor to apply to all pairwise elements in this axiom
     * @return collection of all visitor return values that are not null
     */
    boolean allMatch(OWLPairwiseBooleanVisitor<C> visitor);

    /**
     * Splits this axiom to pairs, including annotations. This method implements
     * the process described at http://www.w3.org/TR/owl2-mapping-to-rdf/#
     * Axioms_that_are_Translated_to_Multiple_Triples which is used, for
     * example, in serializing EquivalentProperty axioms with three operands.
     * Note that annotations on this axiom will be copied to each axiom returned
     * in the set of pairwise axioms. Note: This will contain only the
     * "An, An+1" pairs, i.e., for the set "a, b, c" the pairs "a, b" and "b, c"
     * will be returned, but not "a, c".
     * 
     * @return This axiom as a set of pairwise axioms, annotations included.
     */
    Set<? extends OWLNaryAxiom<C>> splitToAnnotatedPairs();
}
