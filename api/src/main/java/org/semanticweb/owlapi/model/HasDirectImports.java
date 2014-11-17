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

import javax.annotation.Nonnull;

/**
 * An interface to objects that have a direct set of imports.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0
 */
public interface HasDirectImports {

    /**
     * Gets the set of document IRIs that are directly imported by this
     * ontology. This corresponds to the IRIs defined by the
     * directlyImportsDocument association as discussed in Section 3.4 of the
     * OWL 2 Structural specification.
     * 
     * @return The set of directlyImportsDocument IRIs.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Deprecated
    @Nonnull
    default Set<IRI> getDirectImportsDocuments() {
        return asSet(directImportsDocuments());
    }

    /**
     * The stream of document IRIs that are directly imported by this ontology.
     * This corresponds to the IRIs defined by the directlyImportsDocument
     * association as discussed in Section 3.4 of the OWL 2 Structural
     * specification.
     * 
     * @return The stream of directlyImportsDocument IRIs.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Nonnull
    Stream<IRI> directImportsDocuments();

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the directlyImports relation. See Section 3.4 of the OWL 2
     * specification for the definition of the directlyImports relation. <br>
     * Note that there may be fewer ontologies in the set returned by this
     * method than there are IRIs in the set returned by the
     * {@link #getDirectImportsDocuments()} method. This will be the case if
     * some of the ontologies that are directly imported by this ontology are
     * not loaded for what ever reason.
     * 
     * @return A set of ontologies such that for this ontology O, and each
     *         ontology O' in the set, (O, O') is in the directlyImports
     *         relation.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Deprecated
    @Nonnull
    default Set<OWLOntology> getDirectImports() {
        return asSet(directImports());
    }

    /**
     * Stream of <em>loaded</em> ontologies that this ontology is related to via
     * the directlyImports relation. See Section 3.4 of the OWL 2 specification
     * for the definition of the directlyImports relation. <br>
     * Note that there may be fewer ontologies in the set returned by this
     * method than there are IRIs in the set returned by the
     * {@link #getDirectImportsDocuments()} method. This will be the case if
     * some of the ontologies that are directly imported by this ontology are
     * not loaded for what ever reason.
     * 
     * @return Stream of ontologies such that for this ontology O, and each
     *         ontology O' in the set, (O, O') is in the directlyImports
     *         relation.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Nonnull
    Stream<OWLOntology> directImports();
}
