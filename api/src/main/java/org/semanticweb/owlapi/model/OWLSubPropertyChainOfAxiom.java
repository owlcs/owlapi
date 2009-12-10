package org.semanticweb.owlapi.model;

import java.util.List;
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
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Object_Subproperties">SubObjectPropertyOf</a> axiom in the OWL 2 Specification where the subproperty is
 * a chain of properties.  Note that this axiom type is not explicit in the OWL 2 specification, but it is included
 * in the OWL API as a convenience to the programmer.
 */
public interface OWLSubPropertyChainOfAxiom extends OWLObjectPropertyAxiom {

    /**
     * Gets the chain of properties that represents the subproperty in this axiom.
     * @return A list of object property expressions that represents the chain of properties that represent the subproperty
     * in this axiom.
     */
    List<OWLObjectPropertyExpression> getPropertyChain();

    /**
     * Gets the super property of this axiom
     * @return The property expression that represents the superproperty in this expression.
     */
    OWLObjectPropertyExpression getSuperProperty();


    /**
     * Determines if this axiom is of the form:  P o P -> P, which
     * is an encoding of Transitive(P)
     *
     * @return <code>true</code> if this encodes that the super property
     *         is transitive, otherwise <code>false</code>.
     */
    boolean isEncodingOfTransitiveProperty();

    OWLSubPropertyChainOfAxiom getAxiomWithoutAnnotations();
}
