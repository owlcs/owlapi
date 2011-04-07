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
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * <p/>
 * This composite change adds a 'closure' axiom to an ontology for
 * a given class and object property.  In this case, a closure axiom
 * is defined for a given class, A, and object property, P, to be a subclass axiom,
 * whose subclass is class A, and whose superclass is a universal restriction
 * along the property, P, whose filler is the union of any other existential
 * (including hasValue restrictions - i.e. nominals) restriction fillers that are the
 * superclasses of class A.
 * <p/>
 * This code is based on the tutorial examples by Sean Bechhofer (see the tutoral module).
 */
public class AddClassExpressionClosureAxiom extends AbstractCompositeOntologyChange {

    private OWLClass cls;

    private OWLObjectPropertyExpression property;

    private Set<OWLOntology> ontologies;

    private OWLOntology targetOntology;

    private List<OWLOntologyChange> changes;


    /**
     * Creates a composite change that will add a closure axiom for a given class along a
     * specified property.
     *
     * @param dataFactory    The data factory that should be used to create the necessary objects
     * @param cls            The class for which the closure axiom will be generated
     * @param property       The property that the closure axiom will act along
     * @param ontologies     The ontologies that will be examined for subclass axioms
     * @param targetOntology The target ontology that changes will be applied to.
     */
    public AddClassExpressionClosureAxiom(OWLDataFactory dataFactory, OWLClass cls,
                                           OWLObjectPropertyExpression property, Set<OWLOntology> ontologies,
                                           OWLOntology targetOntology) {
        super(dataFactory);
        this.cls = cls;
        this.property = property;
        this.ontologies = ontologies;
        this.targetOntology = targetOntology;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        // We collect all of the fillers for existential restrictions along
        // the target property and all of the fillers for hasValue restrictions
        // as nominals
        FillerCollector collector = new FillerCollector();
        for (OWLOntology ont : ontologies) {
            for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(cls)) {
                ax.getSuperClass().accept(collector);
            }
        }
        Set<OWLClassExpression> fillers = collector.getFillers();
        if (fillers.isEmpty()) {
            return;
        }
        OWLClassExpression closureAxiomFiller = getDataFactory().getOWLObjectUnionOf(fillers);
        OWLClassExpression closureAxiomDesc = getDataFactory().getOWLObjectAllValuesFrom(property, closureAxiomFiller);
        changes.add(new AddAxiom(targetOntology, getDataFactory().getOWLSubClassOfAxiom(cls, closureAxiomDesc)));
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }


    private class FillerCollector extends OWLClassExpressionVisitorAdapter {

        private Set<OWLClassExpression> fillers;


        public FillerCollector() {
            fillers = new HashSet<OWLClassExpression>();
        }


        public Set<OWLClassExpression> getFillers() {
            return fillers;
        }


//        public void reset() {
//            fillers.clear();
//        }


        @Override
		public void visit(OWLObjectSomeValuesFrom desc) {
            if (desc.getProperty().equals(property)) {
                fillers.add(desc.getFiller());
            }
        }


        @Override
		public void visit(OWLObjectHasValue desc) {
            if (desc.getProperty().equals(property)) {
                fillers.add(getDataFactory().getOWLObjectOneOf(CollectionFactory.createSet(desc.getValue())));
            }
        }
    }
}
