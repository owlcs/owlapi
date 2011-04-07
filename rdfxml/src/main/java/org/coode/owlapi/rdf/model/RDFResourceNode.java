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

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 */
public class RDFResourceNode extends RDFNode {

    private IRI iri;

    private int anonId;


    /**
     * Constructs a named resource (i.e. a resource with
     * a URI).
     */
    public RDFResourceNode(IRI iri) {
        this.iri = iri;
    }


    /**
     * Constructs an anonymous node, which has the specified ID.
     * @param anonId The id of the node
     */
    public RDFResourceNode(int anonId) {
        this.anonId = anonId;
    }


    @Override
	public IRI getIRI() {
        return iri;
    }


    @Override
	public boolean isLiteral() {
        return false;
    }


    @Override
	public boolean isAnonymous() {
        return iri == null;
    }


    @Override
	public int hashCode() {
        int hashCode = 17;
        hashCode = hashCode * 37 + (iri == null ? anonId : iri.hashCode());
        return hashCode;
    }


    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof RDFResourceNode)) {
            return false;
        }
        RDFResourceNode other = (RDFResourceNode) obj;
        if (iri != null) {
            if (other.iri != null) {
                return other.iri.equals(iri);
            }
            else {
                return false;
            }
        }
        else {
            return other.anonId == anonId;
        }
    }


    @Override
	public String toString() {
        return (iri != null ? "<" + iri.toString() + ">" : "genid" + Integer.toString(anonId));
    }
}
