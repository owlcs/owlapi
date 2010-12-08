package org.semanticweb.owlapi.profiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

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
 * Date: 03-Aug-2009
 *
 * Describes a violation of an OWLProfile by one or more axioms.  Ultimately, there may
 * be part of an axiom(s) that violates the profile rather than the complete axiom.
 */
public class OWLProfileViolation {

    private OWLOntologyID ontologyID;

    private Map<OWLOntologyID, IRI> importsClosureMap = new HashMap<OWLOntologyID, IRI>();

    private OWLAxiom axiom;

    public OWLProfileViolation(OWLOntology ontology, OWLAxiom axiom) {
        this.axiom = axiom;
        this.ontologyID = ontology.getOntologyID();
        for(OWLOntology ont : ontology.getImportsClosure()) {
            importsClosureMap.put(ont.getOntologyID(), ont.getOWLOntologyManager().getOntologyDocumentIRI(ont));
        }
    }

    public OWLAxiom getAxiom() {
        return axiom;
    }

    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    public IRI getDocumentIRI(OWLOntologyID ontologyID) {
        return importsClosureMap.get(ontologyID);
    }

    public Set<OWLOntologyID> getImportsClosure() {
        return importsClosureMap.keySet();
    }

}
