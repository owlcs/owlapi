package org.semanticweb.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Aug-2007<br><br>
 * <p/>
 * Given a set of ontologies S, for each ontology, O, in S, this change
 * combines multiple subclass axioms with a common left hand side into
 * one subclass axiom.  For example, given A subClassOf B, A subClassOf C,
 * this change will remove these two axioms and replace them by adding
 * one subclass axiom, A subClassOf (B and C).
 */
public class AmalgamateSubClassAxioms extends AbstractCompositeOntologyChange {

    private List<OWLOntologyChange> changes;


    public AmalgamateSubClassAxioms(Set<OWLOntology> ontologies, OWLDataFactory dataFactory) {
        super(dataFactory);
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                Set<OWLSubClassOfAxiom> axioms = ont.getSubClassAxiomsForSubClass(cls);
                if (axioms.size() > 1) {
                    Set<OWLClassExpression> superClasses = new HashSet<OWLClassExpression>();
                    for (OWLSubClassOfAxiom ax : axioms) {
                        changes.add(new RemoveAxiom(ont, ax));
                        superClasses.add(ax.getSuperClass());
                    }
                    OWLClassExpression combinedSuperClass = getDataFactory().getOWLObjectIntersectionOf(superClasses);
                    changes.add(new AddAxiom(ont, getDataFactory().getOWLSubClassOfAxiom(cls, combinedSuperClass)));
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
