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
package org.semanticweb.owlapitools.builders;

import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/**
 * A builder interface for building owl objects
 * 
 * @author ignazio
 * @param <T>
 *        builder type
 */
public interface Builder<T> {

    /** @return freshly built object */
    @Nonnull
    T buildObject();

    /**
     * If the builder is constructing an axiom, this method will add the axiom
     * and all needed changes to make the ontology fit in the expected profile;
     * the changes will be returned but there is no need to apply them, as they
     * have already been applied.
     * 
     * @param o
     *        ontology
     * @return changes the ontology to which the changes should be applied
     */
    @Nonnull
    List<OWLOntologyChange> applyChanges(@Nonnull OWLOntology o);
}
