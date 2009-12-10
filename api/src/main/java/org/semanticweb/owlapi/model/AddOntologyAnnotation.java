package org.semanticweb.owlapi.model;
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
 * Date: 24-Mar-2009
 * </p>
 * Represents an ontology change where an annotation is added to an ontology.
 */
public class AddOntologyAnnotation extends OWLOntologyChange {

    private OWLAnnotation annotation;


    public AddOntologyAnnotation(OWLOntology ont,
                                 OWLAnnotation annotation) {
        super(ont);
        this.annotation = annotation;
    }


    public boolean isAxiomChange() {
        return false;
    }


    /**
     * Gets the annotation that was added to an ontology.
     * @return
     */
    public OWLAnnotation getAnnotation() {
        return annotation;
    }


    public OWLAxiom getAxiom() {
        return null;
    }


    public boolean isImportChange() {
        return false;
    }


    public void accept(OWLOntologyChangeVisitor visitor) {
        visitor.visit(this);
    }


    public int hashCode() {
        return annotation.hashCode() + getOntology().hashCode() + 317;
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof AddOntologyAnnotation)) {
            return false;
        }
        AddOntologyAnnotation other = (AddOntologyAnnotation) obj;
        return annotation.equals(other.getAnnotation()) && getOntology().equals(other.getOntology());
    }
}
