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
package org.semanticweb.owlapi.change;/*
* Copyright (C) 2007, University of Manchester
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

import static org.semanticweb.owlapi.model.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Given a set of ontologies, this composite change will convert all property
 * assertion axioms whose subject is a 'punned' individual (i.e. an individual
 * that shares a name with a class), removes these axioms and replaces them with
 * annotations on the class that shares the same name as the individual. For
 * example for a class A and an individual A, the data property assertion
 * hasX(A, "Val") would be converted to an entity annotation on the class A with
 * an annotation URI of "hasX" and a value of "Val".<br>
 * This composite change supports refactoring an ontology where punning was used
 * to simulate annotations on a class rather than using actual annotations on a
 * class.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class ConvertPropertyAssertionsToAnnotations extends
        AbstractCompositeOntologyChange {

    private final Set<OWLOntology> ontologies;

    /**
     * Instantiates a new convert property assertions to annotations.
     * 
     * @param dataFactory
     *        factory to use
     * @param ontologies
     *        ontologies to change
     */
    public ConvertPropertyAssertionsToAnnotations(
            @Nonnull OWLDataFactory dataFactory,
            @Nonnull Set<OWLOntology> ontologies) {
        super(dataFactory);
        this.ontologies = checkNotNull(ontologies, "ontologies cannot be null");
        generateChanges();
    }

    /**
     * Gets the punned individuals.
     * 
     * @param individuals
     *        the individuals
     * @return the punned individuals
     */
    @Nonnull
    private Collection<OWLNamedIndividual> getPunnedIndividuals(
            Collection<OWLNamedIndividual> individuals) {
        List<OWLNamedIndividual> punned = new ArrayList<OWLNamedIndividual>();
        for (OWLNamedIndividual ind : individuals) {
            for (OWLOntology ont : ontologies) {
                if (ont.containsClassInSignature(ind.getIRI(), EXCLUDED)) {
                    punned.add(ind);
                }
            }
        }
        return punned;
    }

    private void generateChanges() {
        Collection<OWLNamedIndividual> individuals = getPunnedIndividuals(collectIndividuals());
        Set<OWLDataProperty> convertedDataProperties = new HashSet<OWLDataProperty>();
        for (OWLNamedIndividual ind : individuals) {
            convertDataAssertionsToAnnotations(convertedDataProperties, ind);
            removeDeclarationsAndClassAssertions(ind);
        }
        for (OWLDataProperty prop : convertedDataProperties) {
            removeDeclarationsAndAxioms(prop);
        }
    }

    private void removeDeclarationsAndAxioms(@Nonnull OWLDataProperty prop) {
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getDeclarationAxioms(prop)) {
                addChange(new RemoveAxiom(ont, ax));
            }
            for (OWLAxiom ax : ont.getAxioms(prop, EXCLUDED)) {
                addChange(new RemoveAxiom(ont, ax));
            }
        }
    }

    private void removeDeclarationsAndClassAssertions(
            @Nonnull OWLNamedIndividual ind) {
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getDeclarationAxioms(ind)) {
                addChange(new RemoveAxiom(ont, ax));
            }
            for (OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(ind)) {
                addChange(new RemoveAxiom(ont, ax));
            }
        }
    }

    private void
            convertDataAssertionsToAnnotations(
                    Set<OWLDataProperty> convertedDataProperties,
                    OWLNamedIndividual ind) {
        for (OWLOntology ont : ontologies) {
            for (OWLDataPropertyAssertionAxiom ax : ont
                    .getDataPropertyAssertionAxioms(ind)) {
                if (!ax.getProperty().isAnonymous()) {
                    addChange(new RemoveAxiom(ont, ax));
                    addChange(new AddAxiom(ont, convertToAnnotation(ind, ax)));
                    convertedDataProperties.add((OWLDataProperty) ax
                            .getProperty());
                }
            }
        }
    }

    @Nonnull
    private OWLAnnotationAssertionAxiom convertToAnnotation(
            @Nonnull OWLNamedIndividual ind,
            @Nonnull OWLDataPropertyAssertionAxiom ax) {
        OWLDataFactory df = getDataFactory();
        OWLAnnotation anno = df.getOWLAnnotation(
                df.getOWLAnnotationProperty(ax.getProperty()
                        .asOWLDataProperty().getIRI()), ax.getObject());
        OWLAnnotationAssertionAxiom annoAx = df.getOWLAnnotationAssertionAxiom(
                ind.getIRI(), anno);
        return annoAx;
    }

    @Nonnull
    private Set<OWLNamedIndividual> collectIndividuals() {
        Set<OWLNamedIndividual> individuals = new HashSet<OWLNamedIndividual>();
        for (OWLOntology ont : ontologies) {
            individuals.addAll(ont.getIndividualsInSignature());
        }
        return individuals;
    }
}
