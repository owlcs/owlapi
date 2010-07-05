package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.profiles.OWLProfile;
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
 * Date: 22-Nov-2009
 * <p>
 * This exception indicates that the reasoner cannot handle the set of axioms that are in the imports closure
 * of the root ontology because the axioms fall outside of the "largest" OWL profile (i.e.
 * OWL2DL or OWL2EL or OWL2QL or OWL2RL) that the reasoner can handle.  The reasoner will indicate in this exception
 * which profile it expects the axioms to be in.
 */
public class ImportsClosureNotInProfileException extends OWLReasonerRuntimeException {

    private OWLProfile profile;

    public ImportsClosureNotInProfileException(OWLProfile profile) {
        this.profile = profile;
    }

    /**
     * Gets the profile which the reasoner can handle.
     * @return The profile.
     */
    public OWLProfile getProfile() {
        return profile;
    }
}
