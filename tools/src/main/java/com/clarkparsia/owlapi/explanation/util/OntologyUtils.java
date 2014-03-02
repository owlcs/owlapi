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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;

/** Some ontology utils methods. */
public class OntologyUtils {

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
    @Nonnull
    public static boolean containsUnreferencedEntity(
            @Nonnull OWLOntology ontology, @Nonnull OWLClassExpression desc) {
        checkNotNull(ontology, "ontology cannot be null");
        for (OWLEntity entity : checkNotNull(desc, "desc cannot be null")
                .getSignature()) {
            if (!ontology.containsEntityInSignature(entity)) {
                if (entity instanceof OWLClass
                        && (((OWLClass) entity).isOWLThing() || ((OWLClass) entity)
                                .isOWLNothing())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an axiom from all the given ontologies that contains the axiom
     * and returns those ontologies.
     * 
     * @param axiom
     *        axiom being removed
     * @param ontologies
     *        ontologies from which axiom is being removed
     * @param manager
     *        manager to apply the actual change
     * @return set of ontologies that have been affected
     */
    @Nonnull
    public static Set<OWLOntology> removeAxiom(@Nonnull OWLAxiom axiom,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLOntologyManager manager) {
        Set<OWLOntology> modifiedOnts = new HashSet<OWLOntology>();
        checkNotNull(axiom, "axiom cannot be null");
        checkNotNull(manager, "manager cannot be null");
        for (OWLOntology ont : checkNotNull(ontologies,
                "ontologies cannot be null")) {
            if (ont.getAxioms().contains(axiom)) {
                modifiedOnts.add(ont);
                manager.applyChange(new RemoveAxiom(ont, axiom));
            }
        }
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
     */
    @Nonnull
    public static void addAxiom(@Nonnull OWLAxiom axiom,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLOntologyManager manager) {
        for (OWLOntology ont : checkNotNull(ontologies,
                "ontologies cannot be null")) {
            checkNotNull(manager, "manager cannot be null").applyChange(
                    new AddAxiom(ont, checkNotNull(axiom,
                            "axiom cannot be null")));
        }
    }
}
