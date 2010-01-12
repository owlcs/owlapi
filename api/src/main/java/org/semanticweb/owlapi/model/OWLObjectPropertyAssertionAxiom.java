package org.semanticweb.owlapi.model;
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
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Positive_Object_Property_Assertions">ObjectPropertyAssertion</a> axiom in the OWL 2 Specification.
 */
public interface OWLObjectPropertyAssertionAxiom extends OWLPropertyAssertionAxiom<OWLObjectPropertyExpression, OWLIndividual>, OWLSubClassOfAxiomShortCut {

    OWLObjectPropertyAssertionAxiom getAxiomWithoutAnnotations();

    /**
     * Gets a simplified version of this object property axiom.  This is defined recursively as follows:
     * <ul>
     * <li>ObjectPropertyAssertion(P S O) = ObjectPropertyAssertion(P S O)
     * <li>ObjectPropertyAssertion(ObjectInverseOf(P) S O) = ObjectPropertyAssertion(P O S)
     * </ul>
     * @return
     */
    OWLObjectPropertyAssertionAxiom getSimplified();

    /**
     * Determines if this axiom is in a simplified form, i.e. a form where the property is not a property inverse.
     * ObjectPropertyAssertion(P S O) is in a simplified form, where as ObjectPropertyAssertion(ObjectInverseOf(P) S O)
     * is not because it contains an inverse object property.
     * @return <code>true</code> if this axiom is in a simplified form, otherwise <code>false</code>
     */
    boolean isInSimplifiedForm();
}
