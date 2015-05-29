/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;

/** a generator for explanations. */
public interface ExplanationGenerator {

    /**
     * Returns a single explanation for the given unsatisfiable class.
     * 
     * @param unsatClass
     *        The class that is unsatisfiable for which an explanation will be
     *        generated.
     * @return A single explanation for the given unsatisfiable class, or empty
     *         set if the concept is satisfiable
     */
    Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass);

    /**
     * Returns all the explanations for the given unsatisfiable class.
     * 
     * @param unsatClass
     *        The class that is unsatisfiable for which an explanation will be
     *        generated.
     * @return All explanations for the given unsatisfiable class, or an empty
     *         set if the concept is satisfiable
     */
    Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass);

    /**
     * Return a specified number of explanations for the given unsatisfiable
     * class. A smaller number of explanations can be returned if there are not
     * as many explanations for the given concept. The returned set will be
     * empty if the given class is satisfiable,
     * 
     * @param unsatClass
     *        The class that is unsatisfiable for which an explanation will be
     *        generated.
     * @param maxExplanations
     *        Maximum number of explanations requested, or 0 to get all the
     *        explanations
     * @return A specified number of explanations for the given unsatisfiable
     *         class, or an empty set if the concept is satisfiable
     */
    Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass, int maxExplanations);
}
