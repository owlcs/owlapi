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
 * Date: 18-Mar-2009
 * </p>
 * Represents an ontology change where an import statement is added to an ontology.
 */
public class AddImport extends ImportChange {

    public AddImport(OWLOntology ont,
                     OWLImportsDeclaration importDeclaration) {
        super(ont, importDeclaration);
    }



    public int hashCode() {
        return getOntology().hashCode() * 37 + getImportDeclaration().hashCode();
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof AddImport)) {
            return false;
        }
        AddImport other = (AddImport) obj;
        return getImportDeclaration().equals(other.getImportDeclaration());
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ADD IMPORT: ");
        sb.append(getImportDeclaration().toString());
        return sb.toString();
    }

    public void accept(OWLOntologyChangeVisitor visitor) {
        visitor.visit(this);
    }
}
