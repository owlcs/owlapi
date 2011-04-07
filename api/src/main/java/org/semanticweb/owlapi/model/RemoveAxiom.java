/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.model;
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 *
 * Represents an ontology change were an axiom will be removed
 * from an ontology if the change is applied to an ontology.
 */
public class RemoveAxiom extends OWLAxiomChange {

    public RemoveAxiom(OWLOntology ont, OWLAxiom axiom) {
        super(ont, axiom);
    }


    @Override
	protected boolean isAdd() {
        return false;
    }


    @Override
	public int hashCode() {
        return 37 + getOntology().hashCode() * 13 + getAxiom().hashCode() * 13;
    }


    @Override
	public boolean equals(Object obj) {

        if(obj == this) {
            return true;
        }
        if(!(obj instanceof RemoveAxiom)) {
            return false;
        }

        RemoveAxiom other = (RemoveAxiom) obj;
        return other.getOntology().equals(this.getOntology()) && other.getAxiom().equals(this.getAxiom());
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
        sb.append("REMOVE AXIOM: ");
        sb.append(getAxiom().toString());
        return sb.toString();
    }
}
