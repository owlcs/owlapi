package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 * <p/>
 * A convenience object that generates the changes which are necessary to
 * remove an entity from a set of ontologies.  This is accomplished by removing
 * all axioms that refer to the entity.  The entity remover follows the visitor
 * design pattern, entities that need to be removed from an ontology should
 * accept visits from the entity remover.  Changes are accumulated as the entity
 * remover visits various entities.
 */
public class OWLEntityRemover implements OWLEntityVisitor {

    private List<OWLOntologyChange> changes;

    private Set<OWLOntology> ontologies;


    /**
     * Creates an entity remover, which will remove entities (axioms referring to the entities
     * from the specified ontologies).
     *
     * @param owlOntologyManager The <code>OWLOntologyManager</code> which contains the ontologies
     *                           that contain entities to be removed.
     * @param ontologies         The set of ontologies that contain references to axioms to be removed.
     */
    public OWLEntityRemover(OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies) {
        changes = new ArrayList<OWLOntologyChange>();
        this.ontologies = new HashSet<OWLOntology>(ontologies);
    }


    /**
     * Gets the list of ontology changes that are required in order to remove
     * visited entities from the set of ontologies.
     */
    public List<OWLOntologyChange> getChanges() {
        return new ArrayList<OWLOntologyChange>(changes);
    }


    /**
     * Clears any changes which have accumulated over the course of visiting
     * different entities.
     */
    public void reset() {
        changes.clear();
    }

    private void generateChanges(OWLEntity entity) {
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getReferencingAxioms(entity)) {
                changes.add(new RemoveAxiom(ont, ax));
            }
            for(OWLAnnotationAssertionAxiom ax : ont.getAnnotationAssertionAxioms(entity.getIRI())) {
                changes.add(new RemoveAxiom(ont, ax));
            }
        }
    }

    public void visit(OWLClass cls) {
        generateChanges(cls);
    }


    public void visit(OWLDatatype datatype) {
        generateChanges(datatype);
    }


    public void visit(OWLNamedIndividual individual) {
        generateChanges(individual);
    }


    public void visit(OWLDataProperty property) {
        generateChanges(property);
    }


    public void visit(OWLObjectProperty property) {
        generateChanges(property);
    }

    public void visit(OWLAnnotationProperty property) {
        generateChanges(property);
    }
}
