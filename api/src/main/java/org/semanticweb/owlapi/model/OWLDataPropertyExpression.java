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
 *
 * A high level interface to describe different types of data properties.
 */
public interface OWLDataPropertyExpression extends OWLPropertyExpression<OWLDataRange, OWLDataPropertyExpression>, SWRLPredicate {

    /**
     * If the property is a named data property then this method will
     * obtain the property as such.  The general pattern of use is that
     * the <code>isAnonymous</code> method should first be used to determine
     * if the property is named (i.e. not an object property expression such
     * as inv(p)).  If the property is named then this method may be used
     * to obtain the property as a named property without casting.
     * @return The property as an <code>OWLDataProperty</code> if possible.
     * @throws OWLRuntimeException if the property is not a named property.
     */
    OWLDataProperty asOWLDataProperty();
}
