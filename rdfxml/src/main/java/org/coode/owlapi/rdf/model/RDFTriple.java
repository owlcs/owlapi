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

package org.coode.owlapi.rdf.model;
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 *
 * Represents an RDF triple (S, P, O)
 */
public class RDFTriple {

    private RDFResourceNode subject;

    private RDFResourceNode property;

    private RDFNode object;

    private int hashCode = 0;


    public RDFTriple(RDFResourceNode subject, RDFResourceNode property, RDFNode object) {
        this.object = object;
        this.property = property;
        this.subject = subject;
    }


    public RDFResourceNode getSubject() {
        return subject;
    }


    public RDFResourceNode getProperty() {
        return property;
    }


    public RDFNode getObject() {
        return object;
    }


    @Override
	public int hashCode() {
        if (hashCode == 0) {
            hashCode = 17;
            hashCode = hashCode * 37 + subject.hashCode();
            hashCode = hashCode * 37 + property.hashCode();
            hashCode = hashCode * 37 + object.hashCode();
        }
        return hashCode;
    }


    @Override
	public boolean equals(Object obj) {
        if(!(obj instanceof RDFTriple)) {
            return false;
        }
        RDFTriple other = (RDFTriple) obj;
        return other.subject.equals(subject) &&
                other.property.equals(property) &&
                other.object.equals(object);
    }


    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.toString());
        sb.append(" -> ");
        sb.append(property.toString());
        sb.append(" -> ");
        sb.append(object.toString());
        return sb.toString();
    }
}
