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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;

/**
 * An {@code OWLOntologySetProvider} which provides a set of ontologies which
 * correspond to the imports closure of a given ontology. Note that the set of
 * provided ontologies will be updated if the imports closure gets updated.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyImportsClosureSetProvider implements
        OWLOntologySetProvider {

    private final OWLOntologyManager manager;
    private final OWLOntology rootOntology;

    /**
     * Constructs an {@code OWLOntologySetProvider} which provides a set
     * containing the imports closure of a given ontology.
     * 
     * @param manager
     *        The manager which should be used to determine the imports closure.
     * @param rootOntology
     *        The ontology which is the "root" of the imports closure.
     */
    public OWLOntologyImportsClosureSetProvider(
            @Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntology rootOntology) {
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.rootOntology = checkNotNull(rootOntology,
                "rootOntology cannot be null");
    }

    @Override
    public Set<OWLOntology> getOntologies() {
        return manager.getImportsClosure(rootOntology);
    }
}
