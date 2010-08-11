package com.clarkparsia.owlapi.explanation;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;

/*
* Copyright (C) 2007, Clark & Parsia
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * <p/>
 * Title: ExplanationGenerator
 * </p>
 * <p/>
 * Description: The explanation generator interface for returning one or more
 * explanations for an unsatisfiable class. This is the minimal interface an
 * explanation generator should implement
 * </p>
 * <p/>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * @author Evren Sirin
 */
public interface ExplanationGenerator {

    /**
     * Returns a single explanation for the given unsatisfiable class.
     * @param unsatClass The class that is unsatisfiable for which an explanation
     * will be generated.
     * @return A single explanation for the given unsatisfiable class, or
     *         empty set if the concept is satisfiable
     */
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass);


    /**
     * Returns all the explanations for the given unsatisfiable class.
     * @param unsatClass The class that is unsatisfiable for which an explanation
     * will be generated.
     * @return All explanations for the given unsatisfiable class, or an empty
     *         set if the concept is satisfiable
     */
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass);


    /**
     * Return a specified number of explanations for the given unsatisfiable
     * class. A smaller number of explanations can be returned if there are not
     * as many explanations for the given concept. The returned set will be
     * empty if the given class is satisfiable,
     * @param unsatClass The class that is unsatisfiable for which an explanation
     * will be generated.
     * @param maxExplanations Maximum number of explanations requested, or 0 to get all the
     * explanations
     * @return A specified number of explanations for the given unsatisfiable
     *         class, or an empty set if the concept is satisfiable
     */
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass, int maxExplanations);
}
