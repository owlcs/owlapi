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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.*;

/**
 * This composite change will convert a primitive class to a defined class by
 * replacing subclass axioms where the class in question is on the left hand
 * side of the subclass axiom to an equivalent classes axiom which makes the
 * class equivalent to the intersection of its superclasses. <br>
 * More formally, given a class A, a set of ontologies S, and a target
 * targetOntology T, for each targetOntology O in S, subclass axioms whose LHS
 * is A will be removed from O. The superclasses from these axioms will be
 * combined into an intersection class which will be made equivalent to A using
 * an equivalent classes axioms E. E will be added to the target targetOntology
 * T.<br>
 * This composite change supports the pattern of working where a primitive class
 * is converted to a defined class - functionality which is usually found in
 * editors.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class ConvertSuperClassesToEquivalentClass extends AbstractCompositeOntologyChange {

    /**
     * Instantiates a new convert super classes to equivalent class.
     * 
     * @param dataFactory
     *        A data factory which can be used to create the appropriate axioms
     * @param cls
     *        The class whose superclasses will be converted to an equivalent
     *        class.
     * @param ontologies
     *        The ontologies which should be examined for subclass axioms.
     * @param targetOntology
     *        The targetOntology which the equivalent classes axiom should be
     *        added to
     */
    public ConvertSuperClassesToEquivalentClass(OWLDataFactory dataFactory, OWLClass cls,
        Collection<OWLOntology> ontologies, OWLOntology targetOntology) {
        super(dataFactory);
        generateChanges(checkNotNull(targetOntology, "targetOntology cannot be null"),
            checkNotNull(cls, "cls cannot be null"), checkNotNull(ontologies, "ontologies cannot be null"));
    }

    private void generateChanges(OWLOntology targetOntology, OWLClass cls, Collection<OWLOntology> ontologies) {
        // We remove the existing superclasses and then combine these
        // into an intersection which is made equivalent.
        List<OWLClassExpression> descs = new ArrayList<>();
        for (OWLOntology ont : ontologies) {
            ont.subClassAxiomsForSubClass(cls).forEach(ax -> {
                addChange(new RemoveAxiom(ont, ax));
                descs.add(ax.getSuperClass());
            });
        }
        OWLClassExpression equivalentClass = df.getOWLObjectIntersectionOf(descs);
        addChange(new AddAxiom(targetOntology, df.getOWLEquivalentClassesAxiom(Arrays.asList(cls, equivalentClass))));
    }
}
