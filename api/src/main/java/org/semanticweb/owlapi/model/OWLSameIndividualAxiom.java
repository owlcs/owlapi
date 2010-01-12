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
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Individual_Equality">SameIndividual</a> axiom in the OWL 2 Specification.
 */
public interface OWLSameIndividualAxiom extends OWLNaryIndividualAxiom {

    OWLSameIndividualAxiom getAxiomWithoutAnnotations();

    /**
     * Determines whether this axiom contains anonymous individuals.  Anonymous individuals are not allowed in
     * same individuals axioms.
     * @return <code>true</code> if this axioms contains anonymous individual axioms
     */
    boolean containsAnonymousIndividuals();

    /**
     * Returns this axiom represented as set of {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}s.
     * @return This axiom represented as a set of {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}s.
     */
    Set<OWLSameIndividualAxiom> asPairwiseAxioms();
}
