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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class MaximumModalDepthFinder extends OWLObjectVisitorExAdapter<Integer> {

    @Nonnull
    static Integer i(int b) {
        return b;
    }

    @Nonnull
    private static final Integer _1 = i(1);

    /** default constructor, setting default value to 0 */
    public MaximumModalDepthFinder() {
        super(0);
    }

    @Override
    public Integer visit(OWLSubClassOfAxiom axiom) {
        int subClassModalDepth = axiom.getSubClass().accept(this).intValue();
        int superClassModalDepth = axiom.getSuperClass().accept(this).intValue();
        return i(Math.max(subClassModalDepth, superClassModalDepth));
    }

    @Override
    public Integer visit(OWLOntology ontology) {
        int max = 0;
        for (OWLAxiom axiom : ontology.getLogicalAxioms()) {
            int depth = axiom.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return i(max);
    }

    @Override
    public Integer visit(OWLObjectIntersectionOf ce) {
        int max = 0;
        for (OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return i(max);
    }

    @Override
    public Integer visit(OWLObjectUnionOf ce) {
        int max = 0;
        for (OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return i(max);
    }

    @Override
    public Integer visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    @Override
    public Integer visit(OWLObjectSomeValuesFrom ce) {
        return i(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectAllValuesFrom ce) {
        return i(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLDisjointClassesAxiom axiom) {
        int max = 0;
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return i(max);
    }

    @Override
    public Integer visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectMinCardinality ce) {
        return i(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectExactCardinality ce) {
        return i(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectMaxCardinality ce) {
        return i(1 + ce.getFiller().accept(this).intValue());
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
        int max = 0;
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return i(max);
    }
}
