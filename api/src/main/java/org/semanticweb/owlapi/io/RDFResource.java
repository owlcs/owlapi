/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2
 */
public abstract class RDFResource extends RDFNode implements org.apache.commons.rdf.api.BlankNodeOrIRI {

    // XXX implement equals()
    /**
     * @return the resource IRI
     */
    public abstract IRI getResource();

    @Override
    public int compareTo(@Nullable RDFNode o) {
        checkNotNull(o);
        assert o != null;
        if (o.isLiteral()) {
            return 1;
        }
        if (equals(o)) {
            return 0;
        }
        int diff = 0;
        boolean anonA = isAnonymous();
        boolean anonB = o.isAnonymous();
        if (anonA == anonB) {
            // if both are anonymous or both are not anonymous,
            // comparing the id() values corresponds to comparing IRIs or
            // comparing bnode ids
            diff = getIRI().compareTo(o.getIRI());
        } else {
            // if one is anonymous and the other is not,
            // named nodes come first
            if (!anonA) {
                diff = -1;
            } else {
                diff = 1;
            }
        }
        return diff;
    }
    
	@Override
	public String ntriplesString() {
		return getResource().ntriplesString();
	}


}
