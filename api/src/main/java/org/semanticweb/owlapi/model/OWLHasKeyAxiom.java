package org.semanticweb.owlapi.model;

import java.util.Set;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Jan-2009
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Keys">HasKey</a> axiom in the OWL 2 Specification.
 */
public interface OWLHasKeyAxiom extends OWLLogicalAxiom {

    /**
     * Gets the class expression, instances of which, this axiom acts as the key for
     * @return The class expression
     */
    OWLClassExpression getClassExpression();

    /**
     * Gets the set of property expressions that form the key
     * @return The set of property expression that form the key
     */
    Set<OWLPropertyExpression> getPropertyExpressions();

    /**
     * Gets the set of object property expressions that make up the key.  This is simply a convenience method that
     * filteres out the object property expressions in the key.  All of the properties returned by this method are
     * included in the return value of the {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     * @return The set of object property expressions in the key described by this axiom
     */
    Set<OWLObjectPropertyExpression> getObjectPropertyExpressions();

    /**
     * Gets the set of data property expressions that make up the key.  This is simply a convenience method that
     * filteres out the data property expressions in the key.  All of the properties returned by this method are
     * included in the return value of the {@link OWLHasKeyAxiom#getPropertyExpressions()} method.
     * @return The set of object property expressions in the key described by this axiom
     */
    Set<OWLDataPropertyExpression> getDataPropertyExpressions();

    OWLHasKeyAxiom getAxiomWithoutAnnotations();
}
