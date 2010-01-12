package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLNaryClassAxiom extends OWLClassAxiom, OWLNaryAxiom, OWLSubClassOfAxiomSetShortCut {

    /**
     * Gets all of the top level class expressions that appear in this
     * axiom.
     * @return A <code>Set</code> of class expressions that appear in the
     * axiom.
     */
    public Set<OWLClassExpression> getClassExpressions();

    /**
     * A convenience method that obtains the class expression returned by the {@link #getClassExpressions()} method
     * as a list of class expressions.
     * @return A list of the class expressions in this axiom.
     */
    public List<OWLClassExpression> getClassExpressionsAsList();


    /**
     * Determines if this class axiom contains the specified class expression as an operand
     * @param ce The class expression to test for
     * @return <code>true</code> if this axiom contains the specified class expression as an operand,
     * otherwise <code>false</code>.
     */
    boolean contains(OWLClassExpression ce);


    /**
     * Gets the set of class expressions that appear in this axiom minus the specfied
     * class expressions.
     * @param desc The class expressions to subtract from the class expressions in this axiom
     * @return A set containing all of the class expressions in this axiom (the class expressions
     * returned by getClassExpressions()) minus the specified list of class expressions
     */
    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc);
}
