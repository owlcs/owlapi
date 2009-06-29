package org.coode.owlapi.manchesterowlsyntax;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLAxiom;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 18-Feb-2009
 */
public class OntologyAxiomPair {

    private OWLOntology ontology;

    private OWLAxiom axiom;

    public OntologyAxiomPair(OWLOntology ontology, OWLAxiom axiom) {
        this.ontology = ontology;
        this.axiom = axiom;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public OWLAxiom getAxiom() {
        return axiom;
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OntologyAxiomPair)) {
            return false;
        }
        OntologyAxiomPair other = (OntologyAxiomPair) obj;
        if (ontology != null && other.ontology != null) {
            return ontology.equals(other.ontology) && axiom.equals(other.axiom);
        }
        else if(ontology != null && other.ontology == null) {
            return false;
        }
        else if(ontology == null && other.ontology != null) {
            return false;
        }
        else {
            return axiom.equals(other.axiom);
        }
    }

    public int hashCode() {
        if (ontology != null) {
            return ontology.hashCode() + axiom.hashCode();
        }
        else {
            return 37 + axiom.hashCode();
        }
    }

    @Override
    public String toString() {
        return axiom.toString() + " in " + (ontology != null ? ontology.toString() : "");
    }
}
