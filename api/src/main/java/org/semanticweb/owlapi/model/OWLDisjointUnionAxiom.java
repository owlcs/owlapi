package org.semanticweb.owlapi.model;

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
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Disjoint_Union_of_Class_Expressions">DisjointUnion</a> axiom in the OWL 2 Specification.
 */
public interface OWLDisjointUnionAxiom extends OWLClassAxiom {

    /**
     * Gets the class which is equivalent to the disjoint union.
     * @return the class that is equivalent to a disjoint union of other classes.
     */
    OWLClass getOWLClass();


    /**
     * Gets the class expressions which are operands of the disjoint union.
     *
     * @return A <code>Set</code> containing the operands of the disjoint union, note
     *         that this <b>does not</b> include the <code>OWLClass</code> that is equivalent
     *         to the disjoint union.
     */
    Set<OWLClassExpression> getClassExpressions();

    /**
     * Gets the part of this axiom that corresponds to an <code>EquivalentClasses</code> axiom.
     * @return The equivalent classes axiom part of this axiom.  This is essentially,
     * <code>EquivalentClasses(CE, CEUnion)</code> where <code>CEUnion</code> is the union of the classes
     * returned by the {@link #getClassExpressions()} method and <code>CE</code> is the class returned by
     * the {@link #getOWLClass()} method.
     */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom();

    /**
     * Gets the part of this axiom that corresponds to an <code>DisjointClasses</code> axiom.
     * @return The disjoint classes axiom part of this axiom. This is essentially,
     * <code>DisjointClasses(CE1, ..., CEn)</code> where <code>CEi in {CE1, ..., CEn}</code> is
     * contained in the classes returned by the {@link #getClassExpressions()} method.
     */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom();

    OWLDisjointUnionAxiom getAxiomWithoutAnnotations();
}
