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

package org.semanticweb.owlapi.reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 * Indicates that a query whose signature contained fresh entities was posed to the reasoner. This exception is only thrown
 * if the fresh entity policy is set appropriately. (See {@link FreshEntityPolicy}
 * and {@link org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration}.
 */
public class FreshEntitiesException extends OWLReasonerRuntimeException {

    private List<OWLEntity> entities;

    public FreshEntitiesException(Set<OWLEntity> entities) {
        this.entities = Collections.unmodifiableList(new ArrayList<OWLEntity>(entities));
    }

    public FreshEntitiesException(OWLEntity entity) {
        this.entities = Collections.singletonList(entity);
    }


    /**
     * Gets the entities
     * @return The entities, none of which are contained in the signature of the union of a set of ontologies.
     */
    public List<OWLEntity> getEntities() {
        return entities;
    }


    /**
     * Returns the detail message string of this throwable.
     * @return the detail message string of this <tt>Throwable</tt> instance
     *         (which may be <tt>null</tt>).
     */
    @Override
	public String getMessage() {
        return entities + " not in signature";
    }
}
