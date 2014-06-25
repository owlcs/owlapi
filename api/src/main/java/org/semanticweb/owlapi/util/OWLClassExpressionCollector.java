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
package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * Collects all of the nested class expression that are used in some OWLObject.
 * For example, given SubClassOf(ObjectUnionOf(D C) ObjectSomeValuesFrom(R F))
 * the collector could be used to obtain ObjectUnionOf(D C), D, C,
 * ObjectSomeValuesFrom(R F), F
 * 
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class OWLClassExpressionCollector extends
        OWLObjectVisitorExAdapter<Set<OWLClassExpression>> {

    /**
     * default constructor
     */
    public OWLClassExpressionCollector() {
        super(CollectionFactory.<OWLClassExpression> emptySet());
    }

    @Override
    public Set<OWLClassExpression> visit(SWRLClassAtom node) {
        return node.getPredicate().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLSubClassOfAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.addAll(axiom.getSubClass().accept(this));
        result.addAll(axiom.getSuperClass().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLOntology ontology) {
        Set<OWLClassExpression> result = new HashSet<>();
        for (OWLAxiom ax : ontology.getLogicalAxioms()) {
            result.addAll(ax.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLClass ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectIntersectionOf ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectUnionOf ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectComplementOf ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getOperand().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectSomeValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectAllValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<>();
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectHasValue ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectMinCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectExactCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectMaxCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectHasSelf ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectOneOf ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataSomeValuesFrom ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataAllValuesFrom ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataHasValue ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataMinCardinality ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLObjectPropertyRangeAxiom axiom) {
        return axiom.getRange().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataExactCardinality ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDataMaxCardinality ce) {
        return toSet(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<>();
        result.add(axiom.getOWLClass());
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLDeclarationAxiom axiom) {
        return axiom.getEntity().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<>();
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(OWLHasKeyAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Override
    public Set<OWLClassExpression> visit(SWRLRule rule) {
        Set<OWLClassExpression> result = new HashSet<>();
        for (SWRLAtom atom : rule.getBody()) {
            result.addAll(atom.accept(this));
        }
        for (SWRLAtom atom : rule.getHead()) {
            result.addAll(atom.accept(this));
        }
        return result;
    }

    @Nonnull
    private static Set<OWLClassExpression> toSet(@Nonnull OWLClassExpression t) {
        return CollectionFactory.createSet(t);
    }
}
