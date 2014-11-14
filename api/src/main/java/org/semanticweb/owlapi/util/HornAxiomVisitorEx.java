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

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/** Returns true if the visited axioms are an ontology in Horn-SHIQ form. */
public class HornAxiomVisitorEx implements OWLAxiomVisitorEx<Boolean> {

    @Nonnull
    private final PositiveAppearanceVisitorEx positive = new PositiveAppearanceVisitorEx();
    @Nonnull
    private final NegativeAppearanceVisitorEx negative = new NegativeAppearanceVisitorEx();

    protected boolean checkNegative(OWLClassExpression c) {
        return c.accept(negative).booleanValue();
    }

    protected boolean checkPositive(OWLClassExpression c) {
        return c.accept(positive).booleanValue();
    }

    @Override
    public Boolean doDefault(Object object) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(@Nonnull OWLSubClassOfAxiom axiom) {
        return checkNegative(axiom.getSubClass())
                && checkNegative(axiom.getSuperClass());
    }

    @Override
    public Boolean visit(OWLDisjointClassesAxiom axiom) {
        return !axiom.classExpressions().anyMatch(c -> !checkNegative(c));
    }

    @Override
    public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
        return checkPositive(axiom.getDomain());
    }

    @Override
    public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
        return checkPositive(axiom.getRange());
    }

    @Override
    public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLDisjointUnionAxiom axiom) {
        if (neitherPositiveNorNegative(axiom.getOWLClass())) {
            return false;
        }
        return Boolean.valueOf(!axiom.classExpressions().anyMatch(
                c -> neitherPositiveNorNegative(c)));
    }

    protected boolean neitherPositiveNorNegative(OWLClassExpression c1) {
        return !checkPositive(c1) || !checkNegative(c1);
    }

    @Override
    public Boolean visit(OWLDeclarationAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLEquivalentClassesAxiom axiom) {
        return !axiom.classExpressions().anyMatch(
                c -> neitherPositiveNorNegative(c));
    }

    @Override
    public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
        return Boolean.TRUE;
    }

    private class PositiveAppearanceVisitorEx implements
            OWLClassExpressionVisitorEx<Boolean> {

        PositiveAppearanceVisitorEx() {}

        @Override
        public Boolean doDefault(Object object) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf ce) {
            return !ce.operands().anyMatch(c -> !c.accept(this));
        }

        @Override
        public Boolean visit(OWLObjectComplementOf ce) {
            return checkNegative(ce.getOperand());
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectAllValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectExactCardinality ce) {
            return ce.getCardinality() <= 1
                    && ce.getFiller().accept(this).booleanValue()
                    && checkNegative(ce.getFiller());
        }

        @Override
        public Boolean visit(OWLObjectMaxCardinality ce) {
            return ce.getCardinality() <= 1 && checkNegative(ce.getFiller());
        }
    }

    private class NegativeAppearanceVisitorEx implements
            OWLClassExpressionVisitorEx<Boolean> {

        NegativeAppearanceVisitorEx() {}

        @Override
        public Boolean doDefault(Object object) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf ce) {
            return !ce.operands().anyMatch(c -> !c.accept(this));
        }

        @Override
        public Boolean visit(OWLObjectUnionOf ce) {
            return !ce.operands().anyMatch(c -> !c.accept(this));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return ce.getFiller().accept(this);
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality ce) {
            return ce.getCardinality() <= 1
                    && ce.getFiller().accept(this).booleanValue();
        }
    }
}
