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
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Inverse_Object_Properties">ObjectInverseOf</a> property expression in the OWL 2 Specification.
 * </p>
 * Represents the inverse of a property expression.  This can be used to refer to
 * the inverse of a property, without actually naming the property. For example, consider
 * the property hasPart, the inverse property of hasPart (isPartOf) can be referred to using
 * this interface inverseOf(hasPart), which can be used in restrictions e.g.
 * inverseOf(hasPart) some Car refers to the set of things that are part of at least one car.
 */
public interface OWLObjectInverseOf extends OWLObjectPropertyExpression {

    /**
     * Gets the property expression that this is the inverse of.
     * @return The object property expression such that this object property expression is an inverse of it.
     */
    OWLObjectPropertyExpression getInverse();
}
