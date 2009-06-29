package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.util.OWLEntityCollector;

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
 * Date: 12-Dec-2006<br><br>
 */
public abstract class OWLAxiomChange extends OWLOntologyChange {

    private OWLAxiom axiom;


    public OWLAxiomChange(OWLOntology ont, OWLAxiom axiom) {
        super(ont);
        this.axiom = axiom;
    }

    public boolean isAxiomChange() {
        return true;
    }


    /**
     * Determines if this change is an import change
     * @return <code>true</code> if this change is an import change, otherwise <code>false</code>.
     */
    public boolean isImportChange() {
        return false;
    }


    /**
     * Determines if the change will add an axiom to an ontology,
     * or remove an axiom from an ontology.
     * @return <code>true</code> if the change will add an axiom
     * to an ontology, <code>false</code> if the change will remove
     * an axiom from an ontology.
     */
    protected abstract boolean isAdd();


    /**
     * Gets the axiom that is involved in the change (the
     * axiom to either be added or removed)
     */
    public OWLAxiom getAxiom() {
        return axiom;
    }


    /**
     * A convenience method that obtains the entities which are
     * referenced in the axiom contained within this change.
     * @return A <code>Set</code> of entities which are referenced
     * by the axiom contained within this change.
     */
    public Set<OWLEntity> getEntities() {
        OWLEntityCollector collector = new OWLEntityCollector();
        axiom.accept(collector);
        return collector.getObjects();
    }
}
