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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * This composite change adds a 'closure' axiom to an ontology for a given class
 * and object property. In this case, a closure axiom is defined for a given
 * class, A, and object property, P, to be a subclass axiom, whose subclass is
 * class A, and whose superclass is a universal restriction along the property,
 * P, whose filler is the union of any other existential (including hasValue
 * restrictions - i.e. nominals) restriction fillers that are the superclasses
 * of class A. <br>
 * This code is based on the tutorial examples by Sean Bechhofer (see the
 * tutorial module).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class AddClassExpressionClosureAxiom extends
        AbstractCompositeOntologyChange {

    private static final long serialVersionUID = 40000L;

    /**
     * Creates a composite change that will add a closure axiom for a given
     * class along a specified property.
     * 
     * @param dataFactory
     *        The data factory that should be used to create the necessary
     *        objects
     * @param cls
     *        The class for which the closure axiom will be generated
     * @param property
     *        The property that the closure axiom will act along
     * @param ontologies
     *        The ontologies that will be examined for subclass axioms
     * @param targetOntology
     *        The target ontology that changes will be applied to.
     */
    public AddClassExpressionClosureAxiom(@Nonnull OWLDataFactory dataFactory,
            @Nonnull OWLClass cls,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLOntology targetOntology) {
        super(dataFactory);
        generateChanges(checkNotNull(cls, "cls cannot be null"),
                checkNotNull(property, "property cannot be null"),
                checkNotNull(ontologies, "ontologies cannot be null"),
                checkNotNull(targetOntology, "targetOntology cannot be null"));
    }

    private void generateChanges(@Nonnull OWLClass cls,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLOntology targetOntology) {
        // We collect all of the fillers for existential restrictions along
        // the target property and all of the fillers for hasValue restrictions
        // as nominals
        FillerCollector collector = new FillerCollector(property);
        ontologies.forEach(o -> o.subClassAxiomsForSubClass(cls).forEach(
                ax -> ax.getSuperClass().accept(collector)));
        Set<OWLClassExpression> fillers = collector.getFillers();
        if (fillers.isEmpty()) {
            return;
        }
        OWLClassExpression closureAxiomDesc = df.getOWLObjectAllValuesFrom(
                property, df.getOWLObjectUnionOf(fillers));
        addChange(new AddAxiom(targetOntology, df.getOWLSubClassOfAxiom(cls,
                closureAxiomDesc)));
    }

    private class FillerCollector implements OWLClassExpressionVisitor {

        @Nonnull
        private final Set<OWLClassExpression> fillers = new HashSet<>();
        @Nonnull
        private final OWLObjectPropertyExpression property;

        /**
         * @param p
         *        the p
         */
        FillerCollector(@Nonnull OWLObjectPropertyExpression p) {
            property = checkNotNull(p, "p cannot be null");
        }

        /** @return the fillers */
        @Nonnull
        public Set<OWLClassExpression> getFillers() {
            return fillers;
        }

        @Override
        public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
            if (ce.getProperty().equals(property)) {
                fillers.add(ce.getFiller());
            }
        }

        @Override
        public void visit(@Nonnull OWLObjectHasValue ce) {
            if (ce.getProperty().equals(property)) {
                fillers.add(df.getOWLObjectOneOf(CollectionFactory.createSet(ce
                        .getFiller())));
            }
        }
    }
}
