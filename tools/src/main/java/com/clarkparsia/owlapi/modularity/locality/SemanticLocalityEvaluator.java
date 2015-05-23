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
package com.clarkparsia.owlapi.modularity.locality;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Semantic locality evaluator. */
public class SemanticLocalityEvaluator implements LocalityEvaluator {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SemanticLocalityEvaluator.class);
    protected final OWLDataFactory df;
    private final AxiomLocalityVisitor axiomVisitor = new AxiomLocalityVisitor();
    private final BottomReplacer bottomReplacer = new BottomReplacer();
    protected final OWLReasoner reasoner;

    /**
     * Instantiates a new semantic locality evaluator.
     * 
     * @param man
     *        ontology manager
     * @param reasonerFactory
     *        reasoner factory
     */
    public SemanticLocalityEvaluator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory) {
        df = checkNotNull(man, "man cannot be null").getOWLDataFactory();
        try {
            reasoner = checkNotNull(reasonerFactory, "reasonerFactory cannot be null")
                    .createNonBufferingReasoner(man.createOntology());
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    /** The Class AxiomLocalityVisitor. */
    private class AxiomLocalityVisitor implements OWLAxiomVisitor {

        private boolean isLocal;

        AxiomLocalityVisitor() {}

        /**
         * @return true, if is local
         */
        public boolean isLocal() {
            return isLocal;
        }

        /**
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
            isLocal = true;
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (axiom.classExpressions().count() != 2) {
                return;
            }
            LOGGER.info("Calling the Reasoner");
            isLocal = reasoner.isEntailed(axiom);
            LOGGER.info("DONE Calling the Reasoner. isLocal = {}", isLocal);
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            LOGGER.info("Calling the Reasoner");
            isLocal = reasoner.isEntailed(axiom);
            LOGGER.info("DONE Calling the Reasoner. isLocal = {}", isLocal);
        }
    }

    /** The Class BottomReplacer. */
    private class BottomReplacer implements OWLAxiomVisitor, OWLClassExpressionVisitor {

        private OWLAxiom newAxiom;
        private OWLClassExpression newClassExpression;
        private Set<? extends OWLEntity> signature;

        BottomReplacer() {}

        /**
         * @return the result
         */
        public OWLAxiom getResult() {
            return verifyNotNull(newAxiom);
        }

        /**
         * Replace bottom.
         * 
         * @param axiom
         *        the axiom
         * @param sig
         *        the sig
         * @return the modified OWL axiom
         */
        public OWLAxiom replaceBottom(OWLAxiom axiom, Set<? extends OWLEntity> sig) {
            reset(checkNotNull(sig, "sig cannot be null"));
            checkNotNull(axiom, "axiom cannot be null").accept(this);
            return getResult();
        }

        /**
         * Takes an OWLClassExpression and a signature replaces by bottom the
         * entities not in the signature.
         * 
         * @param desc
         *        the desc
         * @return the modified OWL class expression
         */
        public OWLClassExpression replaceBottom(OWLClassExpression desc) {
            newClassExpression = null;
            checkNotNull(desc, "desc cannot be null").accept(this);
            if (newClassExpression == null) {
                throw new OWLRuntimeException("Unsupported class expression " + desc);
            }
            return verifyNotNull(newClassExpression);
        }

        /**
         * @param exps
         *        the class expressions
         * @return the set of modified OWL class expressions
         */
        public Set<OWLClassExpression> replaceBottom(Stream<? extends OWLClassExpression> exps) {
            checkNotNull(exps, "exps cannot be null");
            return asSet(exps.map(ce -> replaceBottom(ce)));
        }

        /**
         * Reset.
         * 
         * @param s
         *        the signature
         */
        public void reset(Set<? extends OWLEntity> s) {
            signature = checkNotNull(s, "s cannot be null");
            newAxiom = null;
        }

        @Override
        public void visit(OWLClass ce) {
            if (signature.contains(ce)) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataAllValuesFrom ce) {
            if (signature.contains(ce.getProperty().asOWLDataProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLDataExactCardinality ce) {
            if (signature.contains(ce.getProperty().asOWLDataProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataMaxCardinality ce) {
            if (signature.contains(ce.getProperty().asOWLDataProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLDataMinCardinality ce) {
            if (signature.contains(ce.getProperty().asOWLDataProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataSomeValuesFrom ce) {
            if (signature.contains(ce.getProperty().asOWLDataProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLDataHasValue ce) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            Set<OWLClassExpression> disjointclasses = replaceBottom(axiom.classExpressions());
            newAxiom = df.getOWLDisjointClassesAxiom(disjointclasses);
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLClassExpression> eqclasses = replaceBottom(axiom.classExpressions());
            newAxiom = df.getOWLEquivalentClassesAxiom(eqclasses);
        }

        @Override
        public void visit(OWLObjectAllValuesFrom ce) {
            if (signature.contains(ce.getProperty().getNamedProperty())) {
                newClassExpression = df.getOWLObjectAllValuesFrom(ce.getProperty(), replaceBottom(ce.getFiller()));
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLObjectComplementOf ce) {
            newClassExpression = df.getOWLObjectComplementOf(replaceBottom(ce.getOperand()));
        }

        @Override
        public void visit(OWLObjectExactCardinality ce) {
            if (signature.contains(ce.getProperty().getNamedProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            newClassExpression = df.getOWLObjectIntersectionOf(replaceBottom(ce.operands()));
        }

        @Override
        public void visit(OWLObjectMaxCardinality ce) {
            if (signature.contains(ce.getProperty().getNamedProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLThing();
            }
        }

        @Override
        public void visit(OWLObjectMinCardinality ce) {
            if (signature.contains(ce.getProperty().getNamedProperty())) {
                newClassExpression = ce;
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectOneOf ce) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLObjectHasSelf ce) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom ce) {
            if (signature.contains(ce.getProperty().getNamedProperty())) {
                newClassExpression = df.getOWLObjectSomeValuesFrom(ce.getProperty(), replaceBottom(ce.getFiller()));
            } else {
                newClassExpression = df.getOWLNothing();
            }
        }

        @Override
        public void visit(OWLObjectUnionOf ce) {
            newClassExpression = df.getOWLObjectUnionOf(replaceBottom(ce.operands()));
        }

        @Override
        public void visit(OWLObjectHasValue ce) {
            newClassExpression = df.getOWLNothing();
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression sup = replaceBottom(axiom.getSuperClass());
            OWLClassExpression sub = replaceBottom(axiom.getSubClass());
            newAxiom = df.getOWLSubClassOfAxiom(sub, sup);
        }
    }

    @Override
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
        LOGGER.info("Replacing axiom by Bottom");
        OWLAxiom newAxiom = bottomReplacer.replaceBottom(checkNotNull(axiom, "axiom cannot be null"),
                checkNotNull(signature, "signature cannot be null"));
        return axiomVisitor.isLocal(newAxiom);
    }
}
