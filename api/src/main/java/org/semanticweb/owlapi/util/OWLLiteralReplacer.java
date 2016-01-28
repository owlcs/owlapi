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

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * Replaces a literal with another.
 * 
 * @author Ignazio Palmisano
 * @since 4.1.4
 */
public class OWLLiteralReplacer {

    private final OWLOntologyManager owlOntologyManager;
    private final Set<OWLOntology> ontologies;

    /**
     * @param owlOntologyManager
     *        the ontology manager to use
     * @param ontologies
     *        the ontologies to use
     */
    public OWLLiteralReplacer(@Nonnull OWLOntologyManager owlOntologyManager, @Nonnull Set<OWLOntology> ontologies) {
        this.owlOntologyManager = checkNotNull(owlOntologyManager, "owlOntologyManager cannot be null");
        this.ontologies = checkNotNull(ontologies, "ontologies cannot be null");
    }

    /**
     * Changes a literal for another literal. This creates the appropriate
     * changes to be applied.
     * 
     * @param literal
     *        The literal to be changed
     * @param newLiteral
     *        The literal to use in replacements.
     * @return A list of ontology changes that should be applied.
     */
    @Nonnull
    public List<OWLOntologyChange> changeLiteral(@Nonnull OWLLiteral literal, @Nonnull OWLLiteral newLiteral) {
        checkNotNull(literal, "literal cannot be null");
        checkNotNull(newLiteral, "newLiteral cannot be null");
        Map<OWLLiteral, OWLLiteral> uriMap = new HashMap<>();
        uriMap.put(literal, newLiteral);
        List<OWLOntologyChange> changes = new ArrayList<>();
        OWLObjectDuplicator dup = new OWLObjectDuplicator(owlOntologyManager.getOWLDataFactory(), Collections
            .<IRI, IRI> emptyMap(), uriMap);
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            fillListWithTransformChanges(changes, getAxioms(ont, literal), ont, dup);
        }
        return changes;
    }

    /**
     * @param literalToLiteralMap
     *        map of literals to change
     * @return list of changes
     */
    public List<OWLOntologyChange> changeLiterals(@Nonnull Map<OWLLiteral, OWLLiteral> literalToLiteralMap) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        OWLObjectDuplicator duplicator = new OWLObjectDuplicator(Collections.<OWLEntity, IRI> emptyMap(),
            owlOntologyManager.getOWLDataFactory(), literalToLiteralMap);
        for (OWLOntology ont : ontologies) {
            assert ont != null;
            for (OWLLiteral ent : literalToLiteralMap.keySet()) {
                assert ent != null;
                fillListWithTransformChanges(changes, getAxioms(ont, ent), ont, duplicator);
            }
        }
        return changes;
    }

    private static Set<OWLAxiom> getAxioms(@Nonnull OWLOntology ont, @Nonnull OWLLiteral entity) {
        Set<OWLAxiom> axioms = ont.getReferencingAxioms(entity, EXCLUDED);
        axioms.addAll(ont.getDeclarationAxioms(entity.getDatatype()));
        return axioms;
    }

    /**
     * Fills a list with ontology changes which will replace a set of axioms
     * with duplicated/transformed axioms.
     * 
     * @param changes
     *        A list that will be filled with ontology changes which will remove
     *        the specified axioms from the specified ontology, and add the
     *        duplicated/transformed version
     * @param axioms
     *        The axioms to be duplicated/transformed
     * @param ont
     *        The ontology to which the changed should be applied
     * @param duplicator
     *        The duplicator that will do the duplicating
     */
    private static void fillListWithTransformChanges(List<OWLOntologyChange> changes, Set<OWLAxiom> axioms,
        @Nonnull OWLOntology ont, OWLObjectDuplicator duplicator) {
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            changes.add(new RemoveAxiom(ont, ax));
            OWLAxiom dupAx = duplicator.duplicateObject(ax);
            changes.add(new AddAxiom(ont, dupAx));
        }
    }
}
