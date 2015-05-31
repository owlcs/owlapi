/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/** Some ontology utils methods. */
public final class OntologyUtils {

    private OntologyUtils() {}

    /**
     * Determines if a class description contains any unreferenced entities with
     * respect to the ontology that contains the entailments which are being
     * explained.
     * 
     * @param ontology
     *        the ontology to search
     * @param desc
     *        The description to be searched
     * @return {@code true} if the description references entities that the
     *         ontology that contains entailments which are being explained,
     *         otherwise {@code false}
     */
    public static boolean containsUnreferencedEntity(OWLOntology ontology, OWLClassExpression desc) {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(desc, "desc cannot be null");
        return desc.signature().anyMatch(e -> !ontology.containsEntityInSignature(e) && !thingOrNothing(e));
    }

    protected static boolean thingOrNothing(OWLEntity entity) {
        return entity.isOWLClass() && (entity.isTopEntity() || entity.isBottomEntity());
    }

    /**
     * Removes an axiom from all the given ontologies that contains the axiom
     * and returns those ontologies.
     * 
     * @param axiom
     *        axiom being removed
     * @param ontologies
     *        ontologies from which axiom is being removed
     * @return set of ontologies that have been affected
     */
    public static Set<OWLOntology> removeAxiom(OWLAxiom axiom, Stream<OWLOntology> ontologies) {
        Set<OWLOntology> modifiedOnts = new HashSet<>();
        checkNotNull(axiom, "axiom cannot be null");
        checkNotNull(ontologies, "ontologies cannot be null");
        ontologies.filter(o -> o.containsAxiom(axiom)).forEach(ont -> {
            modifiedOnts.add(ont);
            ont.remove(axiom);
        } );
        return modifiedOnts;
    }

    /**
     * Add the axiom to all the given ontologies.
     * 
     * @param axiom
     *        the axiom to add
     * @param ontologies
     *        the ontologies to add the axiom to
     * @param manager
     *        the manager for the application
     * @deprecated use {@link #addAxiom(OWLAxiom, Stream)}
     */
    @Deprecated
    public static void addAxiom(OWLAxiom axiom, Set<OWLOntology> ontologies, OWLOntologyManager manager) {
        checkNotNull(manager, "manager cannot be null");
        checkNotNull(axiom, "axiom cannot be null");
        checkNotNull(ontologies, "ontologies cannot be null");
        addAxiom(axiom, ontologies.stream());
    }

    /**
     * Add the axiom to all the given ontologies.
     * 
     * @param axiom
     *        the axiom to add
     * @param ontologies
     *        the ontologies to add the axiom to
     */
    public static void addAxiom(OWLAxiom axiom, Stream<OWLOntology> ontologies) {
        checkNotNull(axiom, "axiom cannot be null");
        checkNotNull(ontologies, "ontologies cannot be null");
        ontologies.forEach(o -> o.add(axiom));
    }
}
