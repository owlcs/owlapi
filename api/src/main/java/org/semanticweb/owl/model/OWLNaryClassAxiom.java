package org.semanticweb.owl.model;

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
 */
public interface OWLNaryClassAxiom extends OWLClassAxiom {

    /**
     * Gets all of the descriptions that appear in this
     * axiom.
     * @return A <code>Set</code> of descriptions that appear in the
     * axiom.
     */
    public Set<OWLDescription> getDescriptions();


    /**
     * Gets the set of descriptions that appear in this axiom minus the specfied
     * descriptions.
     * @param desc The descriptions to subtract from the descriptions in this axiom
     * @return A set containing all of the description in this axiom (the descriptions
     * returned by getDescriptions()) minus the specified list of descriptions
     */
    public Set<OWLDescription> getDescriptionsMinus(OWLDescription ... desc);
}
