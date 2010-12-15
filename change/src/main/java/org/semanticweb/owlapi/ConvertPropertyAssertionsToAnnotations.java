package org.semanticweb.owlapi;/*
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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Jul-2007<br><br>
 * <p/>
 * Given a set of ontologies, this composite change will convert all property assertion
 * axioms whose subject is a 'punned' individual (i.e. an individual that shares a name
 * with a class), removes these axioms and replaces them with annotations on the class
 * that shares the same name as the individual.  For example for a class A and an individual
 * A, the data property assertion hasX(A, "Val") would be converted to an entity annotation on
 * the class A with an annotation URI of "hasX" and a value of "Val".
 * <p/>
 * This composite change supports refactoring an ontology where punning was used to simulate
 * annotations on a class rather than using actual annotations on a class.
 */
public class ConvertPropertyAssertionsToAnnotations extends AbstractCompositeOntologyChange {

    private Set<OWLOntology> ontologies;

    private List<OWLOntologyChange> changes;


    public ConvertPropertyAssertionsToAnnotations(OWLDataFactory dataFactory, Set<OWLOntology> ontologies) {
        super(dataFactory);
        this.ontologies = ontologies;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        Set<OWLNamedIndividual> individuals = new HashSet<OWLNamedIndividual>();

        for (OWLOntology ont : ontologies) {
            individuals.addAll(ont.getIndividualsInSignature());
        }

        Set<OWLDataProperty> convertedDataProperties = new HashSet<OWLDataProperty>();
        for (OWLNamedIndividual ind : individuals) {
            boolean punned = false;
            for (OWLOntology ont : ontologies) {
                if (ont.containsClassInSignature(ind.getIRI())) {
                    punned = true;
                    break;
                }
            }
            if (!punned) {
                // Next individual
                continue;
            }

            for (OWLOntology ont : ontologies) {
                for (OWLDataPropertyAssertionAxiom ax : ont.getDataPropertyAssertionAxioms(ind)) {
                    if (!ax.getProperty().isAnonymous()) {
                        changes.add(new RemoveAxiom(ont, ax));
                        OWLDataFactory df = getDataFactory();
                        OWLAnnotation anno = df.getOWLAnnotation(df.getOWLAnnotationProperty(ax.getProperty().asOWLDataProperty().getIRI()),
                                ax.getObject());
                        OWLAnnotationAssertionAxiom annoAx = df.getOWLAnnotationAssertionAxiom(ind.getIRI(), anno);
                        changes.add(new AddAxiom(ont, annoAx));
                        convertedDataProperties.add((OWLDataProperty) ax.getProperty());
                    }
                }
            }
            for (OWLOntology ont : ontologies) {
                for (OWLAxiom ax : ont.getDeclarationAxioms(ind)) {
                    changes.add(new RemoveAxiom(ont, ax));
                }
                for (OWLClassAssertionAxiom ax : ont.getClassAssertionAxioms(ind)) {
                    changes.add(new RemoveAxiom(ont, ax));
                }
            }
        }
        for (OWLDataProperty prop : convertedDataProperties) {
            for (OWLOntology ont : ontologies) {
                for (OWLAxiom ax : ont.getDeclarationAxioms(prop)) {
                    changes.add(new RemoveAxiom(ont, ax));
                }
                for (OWLAxiom ax : ont.getAxioms(prop)) {
                    changes.add(new RemoveAxiom(ont, ax));
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
