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

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/** Syntactic locality evaluator. */
public class SyntacticLocalityEvaluator implements LocalityEvaluator {

    @Nonnull
    protected final LocalityClass localityCls;
    @Nonnull
    private final AxiomLocalityVisitor axiomVisitor = new AxiomLocalityVisitor();
    @Nonnull
    private static final EnumSet<LocalityClass> SUPPORTED_LOCALITY_CLASSES = EnumSet.of(LocalityClass.TOP_BOTTOM,
        LocalityClass.BOTTOM_BOTTOM, LocalityClass.TOP_TOP);

    /**
     * Constructs a new locality evaluator for the given locality class.
     * 
     * @param localityClass
     *        the locality class for this evaluator
     */
    public SyntacticLocalityEvaluator(@Nonnull LocalityClass localityClass) {
        localityCls = checkNotNull(localityClass, "localityClass cannot be null");
        if (!SUPPORTED_LOCALITY_CLASSES.contains(localityClass)) {
            throw new OWLRuntimeException("Unsupported locality class: " + localityClass);
        }
    }

    /**
     * Returns all supported locality classes.
     * 
     * @return a set containing all supported locality classes
     */
    public static Set<LocalityClass> supportedLocalityClasses() {
        return SUPPORTED_LOCALITY_CLASSES;
    }

    /**
     * This is a convenience method for determining whether a given data range
     * expression is the top datatype or a built-in datatype. This is used in
     * the bottom- and top-equivalence evaluators for treating cardinality
     * restrictions.
     * 
     * @param dataRange
     *        a data range expression
     * @return {@code true} if the specified data range expression is the top
     *         datatype or a built-in datatype; {@code false} otherwise
     */
    protected static boolean isTopOrBuiltInDatatype(@Nonnull OWLDataRange dataRange) {
        if (dataRange.isDatatype()) {
            OWLDatatype dataType = dataRange.asOWLDatatype();
            return dataType.isTopDatatype() || dataType.isBuiltIn();
        } else {
            return false;
        }
    }

    /**
     * This is a convenience method for determining whether a given data range
     * expression is the top datatype or a built-in infinite datatype. This is
     * used in the bottom- and top-equivalence evaluators for treating
     * cardinality restrictions.
     * 
     * @param dataRange
     *        a data range expression
     * @return {@code true} if the specified data range expression is the top
     *         datatype or a built-in infinite datatype; {@code false} otherwise
     */
    protected static boolean isTopOrBuiltInInfiniteDatatype(@Nonnull OWLDataRange dataRange) {
        if (dataRange.isDatatype()) {
            OWLDatatype dataType = dataRange.asOWLDatatype();
            return dataType.isTopDatatype() || dataType.isBuiltIn() && !dataType.getBuiltInDatatype().isFinite();
        } else {
            return false;
        }
    }

    // TODO (TS): only visit logical axioms if possible
    private class AxiomLocalityVisitor implements OWLAxiomVisitor {

        @Nonnull
        private final BottomEquivalenceEvaluator bottomEvaluator = new BottomEquivalenceEvaluator();
        private boolean isLocal;
        private Collection<? extends OWLEntity> signature;
        @Nonnull
        private final TopEquivalenceEvaluator topEvaluator = new TopEquivalenceEvaluator();

        /** Instantiates a new axiom locality visitor. */
        AxiomLocalityVisitor() {
            topEvaluator.setBottomEvaluator(bottomEvaluator);
            bottomEvaluator.setTopEvaluator(topEvaluator);
        }

        @Nonnull
        protected Collection<? extends OWLEntity> getSignature() {
            return verifyNotNull(signature);
        }

        /**
         * Checks if is local.
         * 
         * @param axiom
         *        the axiom
         * @param sig
         *        the sig
         * @return true, if is local
         */
        public boolean isLocal(@Nonnull OWLAxiom axiom, @Nonnull Collection<? extends OWLEntity> sig) {
            signature = checkNotNull(sig, "sig cannot be null");
            isLocal = false;
            checkNotNull(axiom, "axiom cannot be null").accept(this);
            return isLocal;
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            isLocal = true;
        }

        // BUGFIX: (TS) Asymm OP axioms are local in the *_BOTTOM case:
        // The empty object property is asymmetric!
        // BUGFIX: (DT) OP in signature makes the axiom non-local
        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            isLocal = topEvaluator.isTopEquivalent(axiom.getClassExpression(), getSignature(), localityCls);
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = false;
                break;
            case TOP_TOP:
                isLocal = !getSignature().contains(axiom.getProperty().asOWLDataProperty());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().asOWLDataProperty()) || topEvaluator
                    .isTopEquivalent(axiom.getDomain(), getSignature(), localityCls);
                break;
            case TOP_TOP:
                isLocal = topEvaluator.isTopEquivalent(axiom.getDomain(), getSignature(), localityCls);
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().asOWLDataProperty()) || axiom.getRange()
                    .isTopDatatype();
                break;
            case TOP_TOP:
                isLocal = axiom.getRange().isTopDatatype();
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getSubProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isLocal = !getSignature().contains(axiom.getSuperProperty().asOWLDataProperty());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Declaration axioms are local.
        // They need to be added to the module after the locality checks have
        // been performed.
        @Override
        public void visit(OWLDeclarationAxiom axiom) {
            isLocal = true;
        }

        // BUGFIX: (TS) Different individuals axioms are local, too.
        // They need to be added to the module after the locality checks have
        // been performed.
        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            isLocal = true;
        }

        // BUGFIX: (TS) An n-ary disj classes axiom is local
        // iff at most one of the involved class expressions is not
        // bot-equivalent.
        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            Collection<OWLClassExpression> disjs = axiom.getClassExpressions();
            int size = disjs.size();
            if (size == 1) {
                // XXX actually being here means the axiom is not OWL 2
                // conformant
                isLocal = true;
            } else {
                boolean nonBottomEquivDescFound = false;
                for (OWLClassExpression desc : disjs) {
                    assert desc != null;
                    if (!bottomEvaluator.isBottomEquivalent(desc, getSignature(), localityCls)) {
                        if (nonBottomEquivDescFound) {
                            isLocal = false;
                            return;
                        } else {
                            nonBottomEquivDescFound = true;
                        }
                    }
                }
            }
            isLocal = true;
        }

        // BUGFIX (TS): Added the case where it *is* local
        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                Collection<OWLDataPropertyExpression> disjs = axiom.getProperties();
                int size = disjs.size();
                if (size == 1) {
                    // XXX actually being here means the axiom is not OWL 2
                    // conformant
                    isLocal = true;
                } else {
                    boolean nonBottomEquivPropFound = false;
                    for (OWLDataPropertyExpression dpe : disjs) {
                        if (getSignature().contains(dpe.asOWLDataProperty())) {
                            if (nonBottomEquivPropFound) {
                                isLocal = false;
                                return;
                            } else {
                                nonBottomEquivPropFound = true;
                            }
                        }
                    }
                }
                isLocal = true;
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): Added the case where it *is* local
        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                Collection<OWLObjectPropertyExpression> disjs = axiom.getProperties();
                int size = disjs.size();
                if (size == 1) {
                    // XXX actually being here means the axiom is not OWL 2
                    // conformant
                    isLocal = true;
                } else {
                    boolean nonBottomEquivPropFound = false;
                    for (OWLObjectPropertyExpression ope : disjs) {
                        if (getSignature().contains(ope.getNamedProperty())) {
                            if (nonBottomEquivPropFound) {
                                isLocal = false;
                                return;
                            } else {
                                nonBottomEquivPropFound = true;
                            }
                        }
                    }
                }
                isLocal = true;
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): added the two cases where a disj union axiom *is* local:
        // - if LHS and all class expr on RHS are bot-equiv
        // - if LHS is top-equiv, one expr on RHS is top-equiv and the others
        // are bot-equiv
        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            OWLClass lhs = axiom.getOWLClass();
            Collection<OWLClassExpression> rhs = axiom.getClassExpressions();
            if (localityCls == LocalityClass.BOTTOM_BOTTOM) {
                // TODO (TS): "!signature.contains(lhs)" is not enough
                // because lhs could be bot
                if (!getSignature().contains(lhs)) {
                    for (OWLClassExpression desc : rhs) {
                        assert desc != null;
                        if (!bottomEvaluator.isBottomEquivalent(desc, getSignature(), localityCls)) {
                            isLocal = false;
                            return;
                        }
                    }
                    isLocal = true;
                } else {
                    isLocal = false;
                }
            } else {
                // case TOP_BOTTOM:
                // case TOP_TOP:
                // TODO (TS): "!signature.contains(lhs)" is not enough
                // because lhs could be top
                if (!getSignature().contains(lhs)) {
                    boolean topEquivDescFound = false;
                    for (OWLClassExpression desc : rhs) {
                        assert desc != null;
                        if (!bottomEvaluator.isBottomEquivalent(desc, getSignature(), localityCls)) {
                            if (topEvaluator.isTopEquivalent(desc, getSignature(), localityCls)) {
                                if (topEquivDescFound) {
                                    isLocal = false;
                                    return;
                                } else {
                                    topEquivDescFound = true;
                                }
                            } else {
                                isLocal = false;
                                return;
                            }
                        }
                    }
                    isLocal = true;
                } else {
                    isLocal = false;
                }
            }
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {
            isLocal = true;
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            isLocal = true;
            Iterator<OWLClassExpression> eqs = axiom.getClassExpressions().iterator();
            OWLClassExpression first = eqs.next();
            assert first != null;
            // axiom is local if it contains a single class expression
            if (!eqs.hasNext()) {
                return;
            }
            // axiom is local iff either all class expressions evaluate to TOP
            // or all evaluate to BOTTOM
            // check if first class expr. is BOTTOM
            boolean isBottom = bottomEvaluator.isBottomEquivalent(first, getSignature(), localityCls);
            // if not BOTTOM or not TOP then this axiom is non-local
            if (!isBottom && !topEvaluator.isTopEquivalent(first, getSignature(), localityCls)) {
                isLocal = false;
            }
            if (isBottom) {
                // unless we find a non-locality, process all the class
                // expressions
                while (isLocal && eqs.hasNext()) {
                    OWLClassExpression next = eqs.next();
                    assert next != null;
                    // first class expr. was BOTTOM, so this one should be
                    // BOTTOM too
                    if (!bottomEvaluator.isBottomEquivalent(next, getSignature(), localityCls)) {
                        isLocal = false;
                    }
                }
            } else {
                // unless we find a non-locality, process all the class
                // expressions
                while (isLocal && eqs.hasNext()) {
                    OWLClassExpression next = eqs.next();
                    assert next != null;
                    // first class expr. was TOP, so this one should be TOP too
                    if (!topEvaluator.isTopEquivalent(next, getSignature(), localityCls)) {
                        isLocal = false;
                    }
                }
            }
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            Collection<OWLDataPropertyExpression> eqs = axiom.getProperties();
            int size = eqs.size();
            if (size == 1) {
                isLocal = true;
            } else {
                for (OWLDataPropertyExpression p : eqs) {
                    if (getSignature().contains(p.asOWLDataProperty())) {
                        isLocal = false;
                        return;
                    }
                }
                isLocal = true;
            }
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Collection<OWLObjectPropertyExpression> eqs = axiom.getProperties();
            int size = eqs.size();
            if (size == 1) {
                isLocal = true;
            } else {
                for (OWLObjectPropertyExpression p : eqs) {
                    if (getSignature().contains(p.getNamedProperty())) {
                        isLocal = false;
                        return;
                    }
                }
                isLocal = true;
            }
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): replaced call to asOWLObjectProperty() with
        // getNamedProperty()
        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            isLocal = !getSignature().contains(axiom.getFirstProperty().getNamedProperty()) && !getSignature().contains(
                axiom.getSecondProperty().getNamedProperty());
        }

        // BUGFIX: (TS) Irreflexive OP axioms are local in the *_BOTTOM case:
        // The empty object property is irreflexive!
        // BUGFIX: (DT) OP in signature makes the axiom non-local
        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Added the case where this is local. (This is dual to the
        // case of a "positive" DP assertion.)
        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Added the case where this is local. (This is dual to the
        // case of a "positive" OP assertion.)
        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = false;
                break;
            case TOP_TOP:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Added the cases where this is local
        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                // Axiom is local iff at least one prop in the chain is
                // bot-equiv
                for (OWLObjectPropertyExpression ope : axiom.getPropertyChain()) {
                    if (!getSignature().contains(ope.getNamedProperty())) {
                        isLocal = true;
                        return;
                    }
                }
                isLocal = false;
                break;
            case TOP_TOP:
                // Axiom is local iff RHS is top-equiv
                if (!getSignature().contains(axiom.getSuperProperty().getNamedProperty())) {
                    isLocal = true;
                } else {
                    isLocal = false;
                }
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty()) || topEvaluator
                    .isTopEquivalent(axiom.getDomain(), getSignature(), localityCls);
                break;
            case TOP_TOP:
                isLocal = topEvaluator.isTopEquivalent(axiom.getDomain(), getSignature(), localityCls);
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty()) || topEvaluator
                    .isTopEquivalent(axiom.getRange(), getSignature(), localityCls);
                break;
            case TOP_TOP:
                isLocal = topEvaluator.isTopEquivalent(axiom.getRange(), getSignature(), localityCls);
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = !getSignature().contains(axiom.getSubProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isLocal = !getSignature().contains(axiom.getSuperProperty().getNamedProperty());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (DT) Bottom property is not reflexive
        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            switch (localityCls) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isLocal = false;
                break;
            case TOP_TOP:
                isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Same individuals axioms are local, too.
        // They need to be added to the module after the locality checks have
        // been performed.
        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            isLocal = true;
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            isLocal = bottomEvaluator.isBottomEquivalent(axiom.getSubClass(), getSignature(), localityCls)
                || topEvaluator.isTopEquivalent(axiom.getSuperClass(), getSignature(), localityCls);
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            isLocal = !getSignature().contains(axiom.getProperty().getNamedProperty());
        }

        // TODO: (TS) Can't we treat this in a more differentiated way?
        @Override
        public void visit(SWRLRule rule) {
            isLocal = false;
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            isLocal = true;
        }

        @Override
        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            isLocal = true;
        }

        @Override
        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            isLocal = true;
        }

        @Override
        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            isLocal = true;
        }
    }

    /**
     * Used to determine if class expressions are equivalent to \bottom using
     * the provided locality class.
     */
    private static class BottomEquivalenceEvaluator implements OWLClassExpressionVisitor {

        private boolean isBottomEquivalent;
        private LocalityClass localityCls;
        private Collection<? extends OWLEntity> signature;
        private TopEquivalenceEvaluator topEvaluator;

        /** Instantiates a new bottom equivalence evaluator. */
        BottomEquivalenceEvaluator() {}

        /**
         * Checks if is bottom equivalent.
         * 
         * @param desc
         *        the desc
         * @return true, if is bottom equivalent
         */
        private boolean isBottomEquivalent(@Nonnull OWLClassExpression desc) {
            checkNotNull(desc, "desc cannot be null").accept(this);
            return isBottomEquivalent;
        }

        /**
         * Checks if is bottom equivalent.
         * 
         * @param desc
         *        the desc
         * @param sig
         *        the sig
         * @param locality
         *        the locality
         * @return true, if is bottom equivalent
         */
        public boolean isBottomEquivalent(@Nonnull OWLClassExpression desc,
            @Nonnull Collection<? extends OWLEntity> sig, @Nonnull LocalityClass locality) {
            localityCls = checkNotNull(locality, "locality cannot be null");
            signature = checkNotNull(sig, "sig cannot be null");
            checkNotNull(desc, "desc cannot be null").accept(this);
            return isBottomEquivalent;
        }

        @Nonnull
        protected Collection<? extends OWLEntity> getSignature() {
            return verifyNotNull(signature);
        }

        @Nonnull
        protected LocalityClass getLocality() {
            return verifyNotNull(localityCls);
        }

        /**
         * Sets the top evaluator.
         * 
         * @param evaluator
         *        the new top evaluator
         */
        public void setTopEvaluator(@Nonnull TopEquivalenceEvaluator evaluator) {
            topEvaluator = checkNotNull(evaluator, "evaluator cannot be null");
        }

        @Override
        public void visit(OWLClass ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
                isBottomEquivalent = ce.isOWLNothing() || !ce.isOWLThing() && !getSignature().contains(ce);
                break;
            case TOP_BOTTOM:
            case TOP_TOP:
                isBottomEquivalent = ce.isOWLNothing();
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Even in the TOP_TOP case, this is not bottom-equiv:
        // "forall top.D" is not necessarily empty
        // BUGFIX: (TS, 3): In the TOP_TOP case, there is a bottom-equiv
        // possibility.
        @Override
        public void visit(OWLDataAllValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = false;
                break;
            case TOP_TOP:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().asOWLDataProperty()) && !ce.getFiller()
                    .isTopDatatype();
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Corrected both conditions; included case n==0
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        // BUGFIX: (TS, 3) Repaired the cases where the filler is top-equiv
        @Override
        public void visit(OWLDataExactCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = ce.getCardinality() > 0 && !getSignature().contains(ce.getProperty()
                    .asOWLDataProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = ce.getCardinality() == 0 && !getSignature().contains(ce.getProperty()
                    .asOWLDataProperty()) && isTopOrBuiltInDatatype(ce.getFiller()) || ce.getCardinality() > 0
                        && !getSignature().contains(ce.getProperty().asOWLDataProperty())
                        && isTopOrBuiltInInfiniteDatatype(ce.getFiller());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) A data max card restriction is never bottom-equiv.
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        // BUGFIX: (TS, 3) Repaired the cases where the filler is top-equiv
        @Override
        public void visit(OWLDataMaxCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = false;
                break;
            case TOP_TOP:
                isBottomEquivalent = ce.getCardinality() == 0 && !getSignature().contains(ce.getProperty()
                    .asOWLDataProperty()) && isTopOrBuiltInDatatype(ce.getFiller()) || ce.getCardinality() == 1
                        && !getSignature().contains(ce.getProperty().asOWLDataProperty()) && isTopOrBuiltInDatatype(ce
                            .getFiller()) || ce.getCardinality() > 1 && !getSignature().contains(ce.getProperty()
                                .asOWLDataProperty()) && isTopOrBuiltInInfiniteDatatype(ce.getFiller());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) The *_BOTTOM case only works if n > 0.
        @Override
        public void visit(OWLDataMinCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = ce.getCardinality() > 0 && !getSignature().contains(ce.getProperty()
                    .asOWLDataProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLDataSomeValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLDataHasValue ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): TOP_TOP case was missing the first conjunct
        @Override
        public void visit(OWLObjectAllValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = false;
                break;
            case TOP_TOP:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().getNamedProperty())
                    && isBottomEquivalent(ce.getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectComplementOf ce) {
            isBottomEquivalent = topEvaluator.isTopEquivalent(ce.getOperand(), getSignature(), getLocality());
        }

        // BUGFIX: (TS) Since an exact card restriction is a conjunction of a
        // min and a max card restriction,
        // there are cases where it is bottom-local
        @Override
        public void visit(OWLObjectExactCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = ce.getCardinality() > 0 && (!getSignature().contains(ce.getProperty()
                    .getNamedProperty()) || isBottomEquivalent(ce.getFiller()));
                break;
            case TOP_TOP:
                isBottomEquivalent = ce.getCardinality() > 0 && (isBottomEquivalent(ce.getFiller()) || !getSignature()
                    .contains(ce.getProperty().getNamedProperty()) && topEvaluator.isTopEquivalent(ce.getFiller(),
                        getSignature(), getLocality()));
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            for (OWLClassExpression conj : ce.getOperands()) {
                assert conj != null;
                if (isBottomEquivalent(conj)) {
                    isBottomEquivalent = true;
                    return;
                }
            }
            isBottomEquivalent = false;
        }

        // BUGFIX (TS): Corrected all conditions.
        // The n==0 case doesn't affect bottom-equivalence of this type of
        // restriction,
        // but n>0 does!
        @Override
        public void visit(OWLObjectMaxCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = false;
                break;
            case TOP_TOP:
                isBottomEquivalent = ce.getCardinality() > 0 && !getSignature().contains(ce.getProperty()
                    .getNamedProperty()) && topEvaluator.isTopEquivalent(ce.getFiller(), getSignature(), getLocality());
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): Corrected all conditions, considering the case n==0
        @Override
        public void visit(OWLObjectMinCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = ce.getCardinality() > 0 && (!getSignature().contains(ce.getProperty()
                    .getNamedProperty()) || isBottomEquivalent(ce.getFiller()));
                break;
            case TOP_TOP:
                isBottomEquivalent = ce.getCardinality() > 0 && isBottomEquivalent(ce.getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectOneOf ce) {
            isBottomEquivalent = ce.getIndividuals().isEmpty();
        }

        @Override
        public void visit(OWLObjectHasSelf ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = false;
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().getNamedProperty())
                    || isBottomEquivalent(ce.getFiller());
                break;
            case TOP_TOP:
                isBottomEquivalent = isBottomEquivalent(ce.getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectUnionOf ce) {
            for (OWLClassExpression disj : ce.getOperands()) {
                assert disj != null;
                if (!isBottomEquivalent(disj)) {
                    isBottomEquivalent = false;
                    return;
                }
            }
            isBottomEquivalent = true;
        }

        // BUGFIX (TS): desc.getValue() is an individual and therefore is *not*
        // bot-equiv if not in the signature
        // -> disjunct removed from *_BOTTOM case
        @Override
        public void visit(OWLObjectHasValue ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isBottomEquivalent = !getSignature().contains(ce.getProperty().getNamedProperty());
                break;
            case TOP_TOP:
                isBottomEquivalent = false;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Used to determine if class expressions are equivalent to \top using the
     * provided locality class.
     */
    private static class TopEquivalenceEvaluator implements OWLClassExpressionVisitor {

        private BottomEquivalenceEvaluator bottomEvaluator;
        private boolean isTopEquivalent;
        private LocalityClass localityCls;
        private Collection<? extends OWLEntity> signature;

        /** Instantiates a new top equivalence evaluator. */
        TopEquivalenceEvaluator() {}

        private boolean isTopEquivalent(@Nonnull OWLClassExpression desc) {
            checkNotNull(desc, "desc cannot be null").accept(this);
            return isTopEquivalent;
        }

        @Nonnull
        protected Collection<? extends OWLEntity> getSignature() {
            return verifyNotNull(signature);
        }

        @Nonnull
        protected LocalityClass getLocality() {
            return verifyNotNull(localityCls);
        }

        /**
         * Checks if is top equivalent.
         * 
         * @param desc
         *        the desc
         * @param sig
         *        the sig
         * @param locality
         *        the locality
         * @return true, if is top equivalent
         */
        public boolean isTopEquivalent(@Nonnull OWLClassExpression desc, @Nonnull Collection<? extends OWLEntity> sig,
            @Nonnull LocalityClass locality) {
            localityCls = checkNotNull(locality, "locality cannot be null");
            signature = checkNotNull(sig, "sig cannot be null");
            checkNotNull(desc, "desc cannot be null").accept(this);
            return isTopEquivalent;
        }

        /**
         * Sets the bottom evaluator.
         * 
         * @param evaluator
         *        the new bottom evaluator
         */
        public void setBottomEvaluator(@Nonnull BottomEquivalenceEvaluator evaluator) {
            bottomEvaluator = checkNotNull(evaluator, "evaluator cannot be null");
        }

        @Override
        public void visit(OWLClass ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
                isTopEquivalent = ce.isOWLThing();
                break;
            case TOP_BOTTOM:
            case TOP_TOP:
                isTopEquivalent = ce.isOWLThing() || !ce.isOWLNothing() && !signature.contains(ce);
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        @Override
        public void visit(OWLDataAllValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty()) || ce.getFiller()
                    .isTopDatatype();
                break;
            case TOP_TOP:
                isTopEquivalent = ce.getFiller().isTopDatatype();
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Added the case where this is top-equiv (including n==0).
        @Override
        public void visit(OWLDataExactCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = ce.getCardinality() == 0 && !signature.contains(ce.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isTopEquivalent = false;
                break;
            default:
                break;
            }
        }

        // (TS) No special handling for n==0 required.
        @Override
        public void visit(OWLDataMaxCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty());
                break;
            case TOP_TOP:
                isTopEquivalent = false;
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) A data min card restriction is top-equiv iff the
        // cardinality is 0.
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        // BUGFIX: (TS, 2) Left out redundant check cardinality > 0 in TOP_TOP
        // case
        // BUGFIX: (TS, 3) Extended the cases where the filler is top-equiv in
        // TOP_TOP
        @Override
        public void visit(OWLDataMinCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = ce.getCardinality() == 0;
                break;
            case TOP_TOP:
                isTopEquivalent = ce.getCardinality() == 0 || ce.getCardinality() == 1 && !signature.contains(ce
                    .getProperty().asOWLDataProperty()) && isTopOrBuiltInDatatype(ce.getFiller()) || ce
                        .getCardinality() > 1 && !signature.contains(ce.getProperty().asOWLDataProperty())
                        && isTopOrBuiltInInfiniteDatatype(ce.getFiller());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        // BUGFIX: (TS, 3) Extended the cases where the filler is top-equiv
        @Override
        public void visit(OWLDataSomeValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = false;
                break;
            case TOP_TOP:
                isTopEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty()) && isTopOrBuiltInDatatype(ce
                    .getFiller());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS, 2) Added the cases where this is top-equiv
        @Override
        public void visit(OWLDataHasValue ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = false;
                break;
            case TOP_TOP:
                isTopEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectAllValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) || isTopEquivalent(ce
                    .getFiller());
                break;
            case TOP_TOP:
                isTopEquivalent = isTopEquivalent(ce.getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectComplementOf ce) {
            isTopEquivalent = bottomEvaluator.isBottomEquivalent(ce.getOperand(), getSignature(), getLocality());
        }

        // BUGFIX: (TS) added the cases where this is top-equiv, including n==0
        @Override
        public void visit(OWLObjectExactCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = ce.getCardinality() == 0 && (!signature.contains(ce.getProperty().getNamedProperty())
                    || bottomEvaluator.isBottomEquivalent(ce.getFiller(), getSignature(), getLocality()));
                break;
            case TOP_TOP:
                isTopEquivalent = ce.getCardinality() == 0 && bottomEvaluator.isBottomEquivalent(ce.getFiller(),
                    getSignature(), getLocality());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            for (OWLClassExpression conj : ce.getOperands()) {
                assert conj != null;
                if (!isTopEquivalent(conj)) {
                    isTopEquivalent = false;
                    return;
                }
            }
            isTopEquivalent = true;
        }

        // BUGFIX: (TS) Added the case of a bottom-equivalent filler to both
        // conditions.
        // The n==0 case doesn't affect top-equivalence of this type of
        // restriction.
        @Override
        public void visit(OWLObjectMaxCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) || bottomEvaluator
                    .isBottomEquivalent(ce.getFiller(), getSignature(), getLocality());
                break;
            case TOP_TOP:
                isTopEquivalent = bottomEvaluator.isBottomEquivalent(ce.getFiller(), getSignature(), getLocality());
                break;
            default:
                break;
            }
        }

        // BUGFIX: (TS) Added the case n==0; repaired TOP_TOP condition
        // BUGFIX: (TS, 2) Left out redundant check cardinality > 0 in TOP_TOP
        // case
        @Override
        public void visit(OWLObjectMinCardinality ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = ce.getCardinality() == 0;
                break;
            case TOP_TOP:
                // isTopEquivalent =
                // !signature.contains(desc.getProperty().getNamedProperty())
                // && (desc.getCardinality() <= 1);
                isTopEquivalent = ce.getCardinality() == 0 || !signature.contains(ce.getProperty().getNamedProperty())
                    && isTopEquivalent(ce.getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectOneOf ce) {
            isTopEquivalent = false;
        }

        @Override
        public void visit(OWLObjectHasSelf ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = false;
                break;
            case TOP_TOP:
                isTopEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
                break;
            default:
                break;
            }
        }

        // BUGFIX (TS): added ".getNamedProperty()"
        @Override
        public void visit(OWLObjectSomeValuesFrom ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = false;
                break;
            case TOP_TOP:
                isTopEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) && isTopEquivalent(ce
                    .getFiller());
                break;
            default:
                break;
            }
        }

        @Override
        public void visit(OWLObjectUnionOf ce) {
            for (OWLClassExpression conj : ce.getOperands()) {
                assert conj != null;
                if (isTopEquivalent(conj)) {
                    isTopEquivalent = true;
                    return;
                }
            }
            isTopEquivalent = false;
        }

        @Override
        public void visit(OWLObjectHasValue ce) {
            switch (getLocality()) {
            case BOTTOM_BOTTOM:
            case TOP_BOTTOM:
                isTopEquivalent = false;
                break;
            case TOP_TOP:
                isTopEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
                break;
            default:
                break;
            }
        }
    }

    @Override
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
        return axiomVisitor.isLocal(axiom, signature);
    }
}
