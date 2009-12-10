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
 * Bio-Health Informatics Group<br>
 * Date: 29-Nov-2006<br><br>
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Inverse_Object_Properties_2">InverseObjectProperties</a> axiom in the OWL 2 Specification.
 * <p/>
 * Represents a statement that two properties are the inverse of each other.  This
 * property axiom contains a set of two properties.  inverseOf(P, Q) is considered
 * to be equal to inverseOf(Q, P) - i.e. the order in which the properties are specified
 * isn't important
 */
public interface OWLInverseObjectPropertiesAxiom extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>, OWLObjectPropertyAxiom {

    /**
     * Gets the first of the two object properties.
     */
    OWLObjectPropertyExpression getFirstProperty();


    /**
     * Gets the second of the two object properties.
     */
    OWLObjectPropertyExpression getSecondProperty();

    Set<OWLSubObjectPropertyOfAxiom> asSubObjectPropertyOfAxioms();

    OWLInverseObjectPropertiesAxiom getAxiomWithoutAnnotations();
}
