package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 26-Feb-2007<br><br>
 * <p/>
 * A convenience class which is an ontology change listener which collects the
 * entities which are referenced in a set of ontology changes.
 */
public abstract class OWLEntityCollectingOntologyChangeListener implements OWLOntologyChangeListener {

    private Set<OWLEntity> entities;


    public OWLEntityCollectingOntologyChangeListener() {
        entities = new HashSet<OWLEntity>();
    }


    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        entities.clear();
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                OWLAxiomChange axiomChange = (OWLAxiomChange) change;
                entities.addAll(axiomChange.getEntities());
            }
        }
        ontologiesChanged();
    }


    /**
     * Called when a set of changes have been applied.
     */
    public abstract void ontologiesChanged() throws OWLException;


    /**
     * Gets the entities which were referenced in the last change set.
     */
    public Set<OWLEntity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }
}
