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
package org.semanticweb.owlapi6.utility;

import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
public class MaximumModalDepthFinder implements OWLObjectVisitorEx<Integer> {

    private static final Integer _1 = Integer.valueOf(1);
    private static final Integer _0 = Integer.valueOf(0);

    @Override
    public Integer doDefault(OWLObject o) {
        return _0;
    }

    Integer counter(Stream<? extends OWLObject> stream) {
        return Integer.valueOf(stream.mapToInt(o -> o.accept(this).intValue()).max().orElse(0));
    }

    @Override
    public Integer visit(OWLSubClassOfAxiom axiom) {
        int subClassModalDepth = axiom.getSubClass().accept(this).intValue();
        int superClassModalDepth = axiom.getSuperClass().accept(this).intValue();
        return Integer.valueOf(Math.max(subClassModalDepth, superClassModalDepth));
    }

    @Override
    public Integer visit(OWLOntology ontology) {
        return counter(ontology.logicalAxioms());
    }

    @Override
    public Integer visit(OWLObjectIntersectionOf ce) {
        return counter(ce.operands());
    }

    @Override
    public Integer visit(OWLObjectUnionOf ce) {
        return counter(ce.operands());
    }

    @Override
    public Integer visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    @Override
    public Integer visit(OWLObjectSomeValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectAllValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLDisjointClassesAxiom axiom) {
        return counter(axiom.classExpressions());
    }

    @Override
    public Integer visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectMinCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectExactCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectMaxCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectHasSelf ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataSomeValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataAllValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataHasValue ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataMinCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataExactCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataMaxCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Override
    public Integer visit(OWLEquivalentClassesAxiom axiom) {
        return counter(axiom.classExpressions());
    }
}
