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
package org.semanticweb.owlapi.debugging;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.Iterator;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class DebuggerClassExpressionGenerator implements OWLAxiomVisitor {

    private final OWLDataFactory dataFactory;
    @Nullable
    private OWLClassExpression desc;

    /**
     * Instantiates a new debugger class expression generator.
     *
     * @param dataFactory factory to use
     */
    public DebuggerClassExpressionGenerator(OWLDataFactory dataFactory) {
        this.dataFactory = checkNotNull(dataFactory, "dataFactory cannot be null");
    }

    /**
     * Gets the debugger class expression.
     *
     * @return the class expression
     */
    @Nullable
    public OWLClassExpression getDebuggerClassExpression() {
        return desc;
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        // A and not (B)
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(axiom.getSuperClass());
        desc = dataFactory.getOWLObjectIntersectionOf(
            CollectionFactory.createSet(axiom.getSubClass(), complement));
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLDataSomeValuesFrom(axiom.getProperty(),
            dataFactory.getTopDatatype());
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // prop some Thing subclassOf domain
        OWLClassExpression sub = dataFactory.getOWLObjectSomeValuesFrom(axiom.getProperty(),
            dataFactory.getOWLThing());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        // Thing subclassOf prop only Range
        OWLClassExpression sup = dataFactory.getOWLObjectAllValuesFrom(axiom.getProperty(),
            axiom.getRange());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(dataFactory.getOWLThing(), sup);
        ax.accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        // subProp some {a} subClassOf supProp some {a}
        OWLIndividual ind = dataFactory
            .getOWLNamedIndividual(IRI.getNextDocumentIRI("http://debugger.com#A"));
        OWLClassExpression sub = dataFactory.getOWLObjectHasValue(axiom.getSubProperty(), ind);
        OWLClassExpression sup = dataFactory.getOWLObjectHasValue(axiom.getSuperProperty(), ind);
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, sup);
        ax.accept(this);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLObjectOneOf(axiom.getIndividual());
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getClassExpression());
        ax.accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.classExpressions().count() == 2
            && contains(axiom.classExpressions(), dataFactory.getOWLNothing())) {
            desc = axiom.classExpressions().filter(c -> !c.isOWLNothing()).findFirst().orElse(null);
            if (desc != null) {
                return;
            }
        }
        // (C and not D) or (not C and D)
        Iterator<OWLClassExpression> it = axiom.classExpressions().iterator();
        OWLClassExpression descC = it.next();
        OWLClassExpression notC = dataFactory.getOWLObjectComplementOf(descC);
        OWLClassExpression descD = it.next();
        OWLClassExpression notD = dataFactory.getOWLObjectComplementOf(descD);
        OWLObjectIntersectionOf left = dataFactory
            .getOWLObjectIntersectionOf(CollectionFactory.createSet(descC, notD));
        OWLObjectIntersectionOf right = dataFactory
            .getOWLObjectIntersectionOf(CollectionFactory.createSet(notC, descD));
        desc = dataFactory.getOWLObjectUnionOf(CollectionFactory.createSet(left, right));
    }
}
