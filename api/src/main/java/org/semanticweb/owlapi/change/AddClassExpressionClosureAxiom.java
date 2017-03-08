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
package org.semanticweb.owlapi.change;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * This composite change adds a 'closure' axiom to an ontology for a given class and object
 * property. In this case, a closure axiom is defined for a given class, A, and object property, P,
 * to be a subclass axiom, whose subclass is class A, and whose superclass is a universal
 * restriction along the property, P, whose filler is the union of any other existential (including
 * hasValue restrictions - i.e. nominals) restriction fillers that are the superclasses of class A.
 * <br>
 * This code is based on the tutorial examples by Sean Bechhofer (see the tutorial module).
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.0
 */
public class AddClassExpressionClosureAxiom extends AbstractCompositeOntologyChange {

    /**
     * Creates a composite change that will add a closure axiom for a given class along a specified
     * property.
     *
     * @param dataFactory The data factory that should be used to create the necessary objects
     * @param cls The class for which the closure axiom will be generated
     * @param property The property that the closure axiom will act along
     * @param ontologies The ontologies that will be examined for subclass axioms
     * @param targetOntology The target ontology that changes will be applied to.
     */
    public AddClassExpressionClosureAxiom(OWLDataFactory dataFactory, OWLClass cls,
        OWLObjectPropertyExpression property, Collection<OWLOntology> ontologies,
        OWLOntology targetOntology) {
        super(dataFactory);
        checkNotNull(cls, "cls cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNull(ontologies, "ontologies cannot be null");
        checkNotNull(targetOntology, "targetOntology cannot be null");
        generateChanges(cls, property, ontologies, targetOntology);
    }

    private void generateChanges(OWLClass cls, OWLObjectPropertyExpression property,
        Collection<OWLOntology> ontologies, OWLOntology targetOntology) {
        // We collect all of the fillers for existential restrictions along
        // the target property and all of the fillers for hasValue restrictions
        // as nominals
        FillerCollector collector = new FillerCollector(property);
        ontologies.forEach(o -> o.subClassAxiomsForSubClass(cls)
            .forEach(ax -> ax.getSuperClass().accept(collector)));
        if (collector.fillers.isEmpty()) {
            return;
        }
        OWLObjectUnionOf union = df.getOWLObjectUnionOf(collector.fillers);
        OWLClassExpression closureAxiomDesc = df.getOWLObjectAllValuesFrom(property, union);
        addChange(new AddAxiom(targetOntology, df.getOWLSubClassOfAxiom(cls, closureAxiomDesc)));
    }

    private class FillerCollector implements OWLClassExpressionVisitor {

        final List<OWLClassExpression> fillers = new ArrayList<>();
        final OWLObjectPropertyExpression property;

        /**
         * @param p the p
         */
        FillerCollector(OWLObjectPropertyExpression p) {
            property = checkNotNull(p, "p cannot be null");
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom ce) {
            if (ce.getProperty().equals(property)) {
                fillers.add(ce.getFiller());
            }
        }

        @Override
        public void visit(OWLObjectHasValue ce) {
            if (ce.getProperty().equals(property)) {
                fillers.add(df.getOWLObjectOneOf(ce.getFiller()));
            }
        }
    }
}
