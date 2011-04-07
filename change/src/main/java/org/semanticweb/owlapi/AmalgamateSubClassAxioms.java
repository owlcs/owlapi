/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;


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
