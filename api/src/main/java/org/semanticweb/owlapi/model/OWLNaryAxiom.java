package org.semanticweb.owlapi.model;

import java.util.Set;
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
 * Date: 05-Jan-2010
 * </p>
 * Represents an axiom that contains two or more operands that could also be represented with multiple pairwise axioms
 */
public interface OWLNaryAxiom extends OWLAxiom {

    /**
     * Gets this axiom as a set of pairwise axioms.  Note that annotations on this axiom will not be copied to each axiom
     * returned in the set of pairwise axioms.
     * @return This axiom as a set of pairwise axioms.
     */
    Set<? extends OWLNaryAxiom> asPairwiseAxioms();
}
