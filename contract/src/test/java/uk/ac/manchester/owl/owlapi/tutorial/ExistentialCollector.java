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
package uk.ac.manchester.owl.owlapi.tutorial;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

/**
 * A visitor that collects existential restrictions. If the given expression is
 * an intersection, then the visitor will recurse and visit the operands.
 * Otherwise, if it's an existential restriction, the visitor will add the
 * restriction to the collection. <br>
 * The visitor returns a map of properties to collections of fillers using that
 * property.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management
 *         Group
 * @since 2.0.0
 */
public class ExistentialCollector extends OWLClassExpressionVisitorAdapter {

    /* Collected axioms */
    private final Map<OWLObjectPropertyExpression, Set<OWLClassExpression>> restrictions;

    @SuppressWarnings("javadoc")
    public ExistentialCollector(
            Map<OWLObjectPropertyExpression, Set<OWLClassExpression>> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public void visit(OWLObjectIntersectionOf expression) {
        for (OWLClassExpression operand : expression.getOperands()) {
            operand.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom classExpression) {
        Set<OWLClassExpression> fillers = restrictions.get(classExpression
                .getProperty());
        if (fillers == null) {
            fillers = new HashSet<OWLClassExpression>();
            restrictions.put(classExpression.getProperty(), fillers);
        }
        fillers.add(classExpression.getFiller());
    }
}
