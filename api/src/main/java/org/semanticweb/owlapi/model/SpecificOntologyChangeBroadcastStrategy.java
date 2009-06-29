package org.semanticweb.owlapi.model;

import java.util.ArrayList;
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
 * Date: 20-Nov-2006<br><br>
 *
 * A change broadcast strategy that broadcasts changes that have been applied to a
 * specific ontology.
 */
public class SpecificOntologyChangeBroadcastStrategy implements OWLOntologyChangeBroadcastStrategy {

    private OWLOntology ontology;

    /**
     * Constructs a change broadcast strategy which only causes changes that
     * have been applied to the specific ontology to be broadcast.
     * @param ontology  The ontology.
     */
    public SpecificOntologyChangeBroadcastStrategy(OWLOntology ontology) {
        this.ontology = ontology;
    }


    public void broadcastChanges(OWLOntologyChangeListener listener, List<? extends OWLOntologyChange> changes) throws Exception {
        List<OWLOntologyChange> broadcastChanges = new ArrayList<OWLOntologyChange>();
        for(OWLOntologyChange change : changes) {
            if(change.getOntology().equals(ontology)) {
                broadcastChanges.add(change);
            }
        }
        listener.ontologiesChanged(broadcastChanges);
    }
}
