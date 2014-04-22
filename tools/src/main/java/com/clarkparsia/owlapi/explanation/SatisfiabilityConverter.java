/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;


import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Satisfiability converter. */
public class SatisfiabilityConverter {

    /** The Class AxiomConverter. */
    private class AxiomConverter extends
            OWLAxiomVisitorExAdapter<OWLClassExpression> {

        private static final long serialVersionUID = 40000L;

        public AxiomConverter() {}

        @Nonnull
        private OWLObjectIntersectionOf and(@Nonnull OWLClassExpression desc1,
                @Nonnull OWLClassExpression desc2) {
            return factory.getOWLObjectIntersectionOf(set(desc1, desc2));
        }

        @Nonnull
        private OWLObjectIntersectionOf
                and(@Nonnull Set<OWLClassExpression> set) {
            return factory.getOWLObjectIntersectionOf(set);
        }

        @Nonnull
        private OWLObjectComplementOf not(@Nonnull OWLClassExpression desc) {
            return factory.getOWLObjectComplementOf(desc);
        }

        @Nonnull
        private OWLObjectOneOf oneOf(@Nonnull OWLIndividual ind) {
            return factory.getOWLObjectOneOf(Collections.singleton(ind));
        }

        @Nonnull
        private OWLObjectUnionOf or(@Nonnull OWLClassExpression desc1,
                @Nonnull OWLClassExpression desc2) {
            return factory.getOWLObjectUnionOf(set(desc1, desc2));
        }

        @Nonnull
        private <T> Set<T> set(@Nonnull T desc1, @Nonnull T desc2) {
            Set<T> set = new HashSet<T>();
            set.add(desc1);
            set.add(desc2);
            return set;
        }

        @Override
        protected OWLClassExpression doDefault(OWLAxiom axiom) {
            throw new OWLRuntimeException(
                    "Not implemented: Cannot generate explanation for " + axiom);
        }

        @Override
        public OWLClassExpression visit(OWLClassAssertionAxiom axiom) {
            OWLIndividual ind = axiom.getIndividual();
            OWLClassExpression c = axiom.getClassExpression();
            return and(oneOf(ind), not(c));
        }

        @Override
        public OWLClassExpression visit(OWLDataPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLDataHasValue(
                    axiom.getProperty(), axiom.getObject());
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
            return ax.accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLDataPropertyDomainAxiom axiom) {
            OWLClassExpression sub = factory.getOWLDataSomeValuesFrom(
                    axiom.getProperty(), factory.getTopDatatype());
            return and(sub, not(axiom.getDomain()));
        }

        @Override
        public OWLClassExpression visit(OWLDataPropertyRangeAxiom axiom) {
            return factory.getOWLDataSomeValuesFrom(axiom.getProperty(),
                    factory.getOWLDataComplementOf(axiom.getRange()));
        }

        @Override
        public OWLClassExpression visit(OWLDifferentIndividualsAxiom axiom) {
            Set<OWLClassExpression> nominals = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : axiom.getIndividuals()) {
                nominals.add(oneOf(ind));
            }
            return factory.getOWLObjectIntersectionOf(nominals);
        }

        @Override
        public OWLClassExpression visit(OWLDisjointClassesAxiom axiom) {
            return and(axiom.getClassExpressions());
        }

        @Override
        public OWLClassExpression visit(OWLEquivalentClassesAxiom axiom) {
            Iterator<OWLClassExpression> classes = axiom.getClassExpressions()
                    .iterator();
            OWLClassExpression c1 = classes.next();
            OWLClassExpression c2 = classes.next();
            if (classes.hasNext()) {
                LOGGER.warn("EquivalentClassesAxiom with more than two elements not supported!");
            }
            // apply simplification for the cases where either concept is
            // owl:Thing or owlapi:Nothing
            if (c1.isOWLNothing()) {
                return c2;
            } else if (c2.isOWLNothing()) {
                return c1;
            } else if (c1.isOWLThing()) {
                return not(c2);
            } else if (c2.isOWLThing()) {
                return not(c1);
            } else {
                return or(and(c1, not(c2)), and(not(c1), c2));
            }
        }

        @Override
        public OWLClassExpression visit(
                OWLNegativeDataPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLDataHasValue(
                    axiom.getProperty(), axiom.getObject());
            return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
        }

        @Override
        public OWLClassExpression visit(
                OWLNegativeObjectPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLObjectHasValue(
                    axiom.getProperty(), axiom.getObject());
            return factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLObjectPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLObjectHasValue(
                    axiom.getProperty(), axiom.getObject());
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
            return ax.accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLObjectPropertyDomainAxiom axiom) {
            return and(factory.getOWLObjectSomeValuesFrom(axiom.getProperty(),
                    factory.getOWLThing()), not(axiom.getDomain()));
        }

        @Override
        public OWLClassExpression visit(OWLObjectPropertyRangeAxiom axiom) {
            return factory.getOWLObjectSomeValuesFrom(axiom.getProperty(),
                    not(axiom.getRange()));
        }

        @Override
        public OWLClassExpression visit(OWLSameIndividualAxiom axiom) {
            Set<OWLClassExpression> nominals = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : axiom.getIndividuals()) {
                nominals.add(not(oneOf(ind)));
            }
            return and(nominals);
        }

        @Override
        public OWLClassExpression visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression sub = axiom.getSubClass();
            OWLClassExpression sup = axiom.getSuperClass();
            if (sup.isOWLNothing()) {
                return sub;
            } else if (sub.isOWLThing()) {
                return not(sup);
            } else {
                return and(sub, not(sup));
            }
        }
    }

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(SatisfiabilityConverter.class);
    private final AxiomConverter converter;
    protected final OWLDataFactory factory;

    /**
     * Instantiates a new satisfiability converter.
     * 
     * @param factory
     *        the factory to use
     */
    public SatisfiabilityConverter(@Nonnull OWLDataFactory factory) {
        this.factory = checkNotNull(factory, "factory cannot be null");
        converter = new AxiomConverter();
    }

    /**
     * Convert.
     * 
     * @param axiom
     *        axiom to convert
     * @return converted class expression
     */
    @Nonnull
    public OWLClassExpression convert(@Nonnull OWLAxiom axiom) {
        return checkNotNull(axiom, "axiom cannot be null").accept(converter);
    }
}
