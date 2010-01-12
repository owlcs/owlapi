package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 12-Jan-2010
 * </p>
 * A marker interface for axioms that are essentially syntactic shortcuts for SubClassOf axioms.
 */
public interface OWLSubClassOfAxiomShortCut {

    /**
     * Gets this axiom as an {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}.
     * @return An {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom} that is equivalent to this axiom. Note that
     * annotations are not copied to the returned {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom} axiom.
     */
    OWLSubClassOfAxiom asOWLSubClassOfAxiom();

}
