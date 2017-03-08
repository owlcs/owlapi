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
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

/**
 * An interface to objects that provide an imports closure of themselves.
 *
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5.0
 */
@FunctionalInterface
public interface HasImportsClosure {

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related to via the
     * <em>reflexive transitive closure</em> of the directlyImports relation as defined in Section
     * 3.4 of the OWL 2 Structural Specification. For example, if this ontology imports ontology B,
     * and ontology B imports ontology C, then this method will return the set consisting of this
     * ontology, ontology B and ontology C.
     *
     * @return The set of ontologies in the reflexive transitive closure of the directlyImports
     * relation.
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager
     * because it was removed from the manager.
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLOntology> getImportsClosure() {
        return asSet(importsClosure());
    }

    /**
     * Gets the imports closure, including the root object.
     *
     * @return Stream of ontologies representing the imports closure of this object (includes this
     * object).
     */
    Stream<OWLOntology> importsClosure();
}
