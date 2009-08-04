package org.semanticweb.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
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
 * Date: 24-Jul-2007<br><br>
 * Given a set of ontologies, this composite change will remove all disjoint
 * classes axioms from these ontologies.
 */
public class RemoveAllDisjointAxioms extends AbstractCompositeOntologyChange {

    private Set<OWLOntology> ontologies;

    private List<OWLOntologyChange> changes;

    public RemoveAllDisjointAxioms(OWLDataFactory dataFactory, Set<OWLOntology> ontologies) {
        super(dataFactory);
        this.ontologies = ontologies;
        generateChanges();
    }

    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLClassAxiom ax : ont.getAxioms(AxiomType.DISJOINT_CLASSES)) {
                changes.add(new RemoveAxiom(ont, ax));
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
