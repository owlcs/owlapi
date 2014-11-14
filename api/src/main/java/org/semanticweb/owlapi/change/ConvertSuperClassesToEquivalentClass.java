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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

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
public class ConvertSuperClassesToEquivalentClass extends
        AbstractCompositeOntologyChange {

    private static final long serialVersionUID = 40000L;

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
    public ConvertSuperClassesToEquivalentClass(
            @Nonnull OWLDataFactory dataFactory, @Nonnull OWLClass cls,
            @Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLOntology targetOntology) {
        super(dataFactory);
        generateChanges(
                checkNotNull(targetOntology, "targetOntology cannot be null"),
                checkNotNull(cls, "cls cannot be null"),
                checkNotNull(ontologies, "ontologies cannot be null"));
    }

    private void generateChanges(@Nonnull OWLOntology targetOntology,
            @Nonnull OWLClass cls, @Nonnull Set<OWLOntology> ontologies) {
        // We remove the existing superclasses and then combine these
        // into an intersection which is made equivalent.
        Set<OWLClassExpression> descs = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            ont.subClassAxiomsForSubClass(cls).forEach(ax -> {
                addChange(new RemoveAxiom(ont, ax));
                descs.add(ax.getSuperClass());
            });
        }
        OWLClassExpression equivalentClass = df
                .getOWLObjectIntersectionOf(descs);
        Set<OWLClassExpression> equivalentClasses = new HashSet<>();
        equivalentClasses.add(cls);
        equivalentClasses.add(equivalentClass);
        addChange(new AddAxiom(targetOntology,
                df.getOWLEquivalentClassesAxiom(equivalentClasses)));
    }
}
