/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, Clark & Parsia, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clarkparsia.owlapi.modularity.locality;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

/** Semantic locality evaluator. */
public class SemanticLocalityEvaluator implements LocalityEvaluator {

    /** The Constant log. */
    protected static final Logger log = Logger
            .getLogger(SemanticLocalityEvaluator.class.getName());
    /** The df. */
    protected final OWLDataFactory df;
    /** The axiom visitor. */
    private final AxiomLocalityVisitor axiomVisitor = new AxiomLocalityVisitor();
    /** The bottom replacer. */
    private final BottomReplacer bottomReplacer = new BottomReplacer();
    /** The reasoner. */
    protected final OWLReasoner reasoner;

    /**
     * Instantiates a new semantic locality evaluator.
     * 
     * @param man
     *        ontology manager
     * @param reasonerFactory
     *        reasoner factory
     */
    public SemanticLocalityEvaluator(OWLOntologyManager man,
            OWLReasonerFactory reasonerFactory) {
        df = man.getOWLDataFactory();
        try {
            reasoner = reasonerFactory.createNonBufferingReasoner(man
                    .createOntology());
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    /** The Class AxiomLocalityVisitor. */
    private class AxiomLocalityVisitor extends OWLAxiomVisitorAdapter implements
            OWLAxiomVisitor {

        /** The is local. */
        private boolean isLocal;

        /** Instantiates a new axiom locality visitor. */
        public AxiomLocalityVisitor() {}

        /**
         * Checks if is local.
         * 
         * @return true, if is local
         */
        public boolean isLocal() {
            return isLocal;
        }

        /**
         * Checks if is local.
         * 
         * @param axiom
         *        the axiom
         * @return true, if is local
         */
        public boolean isLocal(OWLAxiom axiom) {
            reset();
            axiom.accept(this);
            return isLocal();
        }

        /** Reset. */
        public void reset() {
            isLocal = false;
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            // XXX this seems wrong
            if (log.isLoggable(Level.FINE)) {
                log.fine("Calling the Reasoner");
            }
            isLocal = true;
            if (log.isLoggable(Level.FINE)) {
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLClassExpression> eqClasses = axiom.getClassExpressions();
            if (eqClasses.size() != 2) {
                return;
            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("Calling the Reasoner");
            }
            isLocal = reasoner.isEntailed(axiom);
            if (log.isLoggable(Level.FINE)) {
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
            }
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (log.isLoggable(Level.FINE)) {
                log.fine("Calling the Reasoner");
            }
            isLocal = reasoner.isEntailed(axiom);
            if (log.isLoggable(Level.FINE)) {
                log.fine("DONE Calling the Reasoner. isLocal = " + isLocal);
            }
        }
    }

    /** The Class BottomReplacer. */
    private class BottomReplacer extends OWLAxiomVisitorAdapter implements
            OWLAxiomVisitor, OWLClassExpressionVisitor {

        /** The new axiom. */
        private OWLAxiom newAxiom;
        /** The new class expression. */
        private OWLClassExpression newClassExpression;
        /** The signature. */
        private Set<? extends OWLEntity> signature;

        /** Instantiates a new bottom replacer. */
        public BottomReplacer() {}

        /**
         * Gets the result.
         * 
         * @return the result
         */
        public OWLAxiom getResult() {
            return newAxiom;
        }

        /**
         * Replace bottom.
         * 
         * @param axiom
         *        the axiom
         * @param sig
         *        the sig
         * @return the oWL axiom
         */
        public OWLAxiom replaceBottom(OWLAxiom axiom,
                Set<? extends OWLEntity> sig) {
            reset(sig);
            axiom.accept(this);
            return getResult();
        }

        // Takes an OWLClassExpression and a signature replaces by bottom the
        // entities not in the signature
        /**
         * Replace bottom.
         * 
         * @param desc
         *        the desc
         * @return the oWL class expression
         */
        public OWLClassExpression replaceBottom(OWLClassExpression desc) {
            newClassExpression = null;
            desc.accept(this);
            if (newClassExpression == null) {
                throw new RuntimeException("Unsupported class expression "
                        + desc);
            }
            return newClassExpression;
        }

        /**
         * Replace bottom.
         * 
         * @param classExpressions
         *        the class expressions
         * @return the sets the
         */
        public Set<OWLClassExpression> replaceBottom(
                Set<OWLClassExpression> classExpressions) {
            Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
            for (OWLClassExpression desc : classExpressions) {
                result.add(replaceBottom(desc));
            }
            return result;
        }

        /**
         * Reset.
         * 
         * @param s
         *        the s
         */
        public void reset(Set<? extends OWLEntity> s) {
            signature = s;
            newAxiom = null;
        }

        @Override
        public void visit(OWLClass desc) {
            if (signature.contains(desc)) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataAllValuesFrom desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLDataExactCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataMaxCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLDataMinCardinality desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataSomeValuesFrom desc) {
            if (signature.contains(desc.getProperty().asOWLDataProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataHasValue desc) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLDisjointClassesAxiom ax) {
            Set<OWLClassExpression> disjointclasses = replaceBottom(ax
                    .getClassExpressions());
            newAxiom = df.getOWLDisjointClassesAxiom(disjointclasses);
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom ax) {
            Set<OWLClassExpression> eqclasses = replaceBottom(ax
                    .getClassExpressions());
            newAxiom = df.getOWLEquivalentClassesAxiom(eqclasses);
        }

        @Override
        public void visit(OWLObjectAllValuesFrom desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = df.getOWLObjectAllValuesFrom(
                        desc.getProperty(), replaceBottom(desc.getFiller()));
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLObjectComplementOf desc) {
            newClassExpression = df.getOWLObjectComplementOf(replaceBottom(desc
                    .getOperand()));
        }

        @Override
        public void visit(OWLObjectExactCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf desc) {
            Set<OWLClassExpression> operands = desc.getOperands();
            newClassExpression = df
                    .getOWLObjectIntersectionOf(replaceBottom(operands));
        }

        @Override
        public void visit(OWLObjectMaxCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLObjectMinCardinality desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = desc;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectOneOf desc) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLObjectHasSelf desc) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom desc) {
            if (signature.contains(desc.getProperty().getNamedProperty())) {
                newClassExpression = df.getOWLObjectSomeValuesFrom(
                        desc.getProperty(), replaceBottom(desc.getFiller()));
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectUnionOf desc) {
            Set<OWLClassExpression> operands = desc.getOperands();
            newClassExpression = df
                    .getOWLObjectUnionOf(replaceBottom(operands));
        }

        @Override
        public void visit(OWLObjectHasValue desc) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLSubClassOfAxiom ax) {
            OWLClassExpression sup = replaceBottom(ax.getSuperClass());
            OWLClassExpression sub = replaceBottom(ax.getSubClass());
            newAxiom = df.getOWLSubClassOfAxiom(sub, sup);
        }
    }

    @Override
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Replacing axiom by Bottom");
        }
        OWLAxiom newAxiom = bottomReplacer.replaceBottom(axiom, signature);
        if (log.isLoggable(Level.FINE)) {
            log.fine("DONE Replacing axiom by Bottom. Success: "
                    + (newAxiom != null));
        }
        return newAxiom != null && axiomVisitor.isLocal(newAxiom);
    }
}
