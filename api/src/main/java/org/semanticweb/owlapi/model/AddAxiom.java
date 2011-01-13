package org.semanticweb.owlapi.model;

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
 * Date: 25-Oct-2006<br><br>
 *
 * </p>
 * Represents an ontology change where axioms should be added
 * to an ontology.
 */
public class AddAxiom extends OWLAxiomChange {

    public AddAxiom(OWLOntology ont, OWLAxiom axiom) {
        super(ont, axiom);
    }


    @Override
	protected boolean isAdd() {
        return true;
    }


    @Override
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof AddAxiom)) {
            return false;
        }

        AddAxiom other = (AddAxiom) obj;
        return other.getOntology().equals(this.getOntology()) && other.getAxiom().equals(this.getAxiom());
    }


    @Override
	public int hashCode() {
        return 17 + getOntology().hashCode() * 13 + getAxiom().hashCode() * 13;
    }


    @Override
	public void accept(OWLOntologyChangeVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
    	return visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD AXIOM: ");
        sb.append(getAxiom().toString());
        return sb.toString();
    }
}
