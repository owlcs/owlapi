package com.clarkparsia.owlapi.modularity.locality;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p/>
 * Title: </p>
 * <p/>
 * Description: Implements syntactic locality evaluation for axioms </p>
 * <p/>
 * Copyright: Copyright (c) 2007 </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> </p>
 *
 * @author Mike Smith
 *         <p/>
 *         Essential bug fixes by Thomas Schneider, School of Computer Science, University of Manchester
 */
public class SyntacticLocalityEvaluator implements LocalityEvaluator {

    private LocalityClass localityCls;

    private AxiomLocalityVisitor axiomVisitor;

    private static EnumSet<LocalityClass> supportedLocalityClasses = EnumSet.of(LocalityClass.TOP_BOTTOM,
                                                                                LocalityClass.BOTTOM_BOTTOM,
                                                                                LocalityClass.TOP_TOP);

    /**
     * Constructs a new locality evaluator for the given locality class.
     *
     * @param localityClass the locality class for this evaluator
     */
    public SyntacticLocalityEvaluator(LocalityClass localityClass) {
        this.localityCls = localityClass;
        this.axiomVisitor = new AxiomLocalityVisitor();
        if (!supportedLocalityClasses.contains(localityClass)) {
            throw new RuntimeException("Unsupported locality class: " + localityClass);
        }
    }


    /**
     * Returns all supported locality classes.
     * @return a set containing all supported locality classes
     */
    public Set<LocalityClass> supportedLocalityClasses() {
        return supportedLocalityClasses;
    }

    /**
     * This is a convenience method for determining whether a given data range expression is the top data range
     * or a built-in infinite datatype. This is used in the bottom- and top-equivalence evaluators
     * for treating cardinality restrictions.
     *
     * @param dataRange a data range expression
     * @return <code>true</code> if the specified data range expression is the top data range
     *         or a built-in infinite datatype; <code>false</code> otherwise
     */
    protected static boolean isTopEquivalentDataRange(OWLDataRange dataRange) {
        if (dataRange.isDatatype()){
            OWLDatatype dataType = dataRange.asOWLDatatype();
            return (dataType.isTopDatatype() || (dataType.isBuiltIn() && !dataType.getBuiltInDatatype().isFinite()));
        }
        else return false;
    }

    // TODO (TS): only visit logical axioms if possible
    private class AxiomLocalityVisitor implements OWLAxiomVisitor {

        private BottomEquivalenceEvaluator bottomEvaluator;

        private boolean isLocal;

        private Collection<? extends OWLEntity> signature;

        private TopEquivalenceEvaluator topEvaluator;


        public AxiomLocalityVisitor() {
            bottomEvaluator = new BottomEquivalenceEvaluator();
            topEvaluator = new TopEquivalenceEvaluator();
            topEvaluator.setBottomEvaluator(bottomEvaluator);
            bottomEvaluator.setTopEvaluator(topEvaluator);
        }


        public boolean isLocal(OWLAxiom axiom, Collection<? extends OWLEntity> signature) {
            this.signature = signature;
            isLocal = false;
            axiom.accept(this);
            return isLocal;
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            throw new RuntimeException("NOT IMPLEMENTED");
        }

        // BUGFIX: (TS) Antisymm OP axioms are local in the *_BOTTOM case:
        //              The empty object property is antisymmetric!
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = true;
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            isLocal = topEvaluator.isTopEquivalent(axiom.getClassExpression(), signature, localityCls);
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = false;
                    break;
                case TOP_TOP:
                    isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
                    break;
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty())
                            || topEvaluator.isTopEquivalent(axiom.getDomain(), signature, localityCls);
                    break;
                case TOP_TOP:
                    isLocal = topEvaluator.isTopEquivalent(axiom.getDomain(), signature, localityCls);
                    break;
            }
        }


        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty())
                            || axiom.getRange().isTopDatatype();
                    break;
                case TOP_TOP:
                    isLocal = axiom.getRange().isTopDatatype();
                    break;
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getSubProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isLocal = !signature.contains(axiom.getSuperProperty().asOWLDataProperty());
                    break;
            }
        }


        // BUGFIX: (TS) Individual declaration axioms are local, too.
        //              They need to be added to the module after the locality checks have been performed.
        public void visit(OWLDeclarationAxiom axiom) {
            isLocal = true;
//            isLocal = !(axiom.getEntity().isOWLNamedIndividual());
        }


        // BUGFIX: (TS) Different individuals axioms are local, too.
        //              They need to be added to the module after the locality checks have been performed.
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            isLocal = true;
//            isLocal = false;
        }


        // BUGFIX: (TS) An n-ary disj classes axiom is local
        //              iff at most one of the involved class expressions is not bot-equivalent.
        public void visit(OWLDisjointClassesAxiom axiom) {
            Collection<OWLClassExpression> disjs = axiom.getClassExpressions();
            int size = disjs.size();
            if (size == 1) {
                throw new RuntimeException("Unary disjoint axiom.");
            }
            else {
                boolean nonBottomEquivDescFound = false;
                for (OWLClassExpression desc : disjs) {
                    if (!bottomEvaluator.isBottomEquivalent(desc, signature, localityCls)) {
                        if (nonBottomEquivDescFound) {
                            isLocal = false;
                            return;
                        }
                        else {
                            nonBottomEquivDescFound = true;
                        }
                    }
                }
            }
            isLocal = true;
        }

        // BUGFIX (TS): Added the case where it *is* local
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    Collection<OWLDataPropertyExpression> disjs = axiom.getProperties();
                    int size = disjs.size();
                    if (size == 1) {
                        throw new RuntimeException("Unary disjoint axiom.");
                    }
                    else {
                        boolean nonBottomEquivPropFound = false;
                        for (OWLDataPropertyExpression dpe : disjs) {
                            if (signature.contains(dpe.asOWLDataProperty())) {
                                if (nonBottomEquivPropFound) {
                                    isLocal = false;
                                    return;
                                }
                                else {
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
            }
        }


        // BUGFIX (TS): Added the case where it *is* local
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    Collection<OWLObjectPropertyExpression> disjs = axiom.getProperties();
                    int size = disjs.size();
                    if (size == 1) {
                        throw new RuntimeException("Unary disjoint axiom.");
                    }
                    else {
                        boolean nonBottomEquivPropFound = false;
                        for (OWLObjectPropertyExpression ope : disjs) {
                            if (signature.contains(ope.getNamedProperty())) {
                                if (nonBottomEquivPropFound) {
                                    isLocal = false;
                                    return;
                                }
                                else {
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
            }
        }


        // BUGFIX (TS): added the two cases where a disj union axiom *is* local:
        // - if LHS and all class expr on RHS are bot-equiv
        // - if RHS is top-equiv, one expr on LHS is top-equiv and the others are bot-equiv
        public void visit(OWLDisjointUnionAxiom axiom) {
            OWLClass lhs = axiom.getOWLClass();
            Collection<OWLClassExpression> rhs = axiom.getClassExpressions();
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                    if (!signature.contains(lhs)) {
                        for (OWLClassExpression desc : rhs) {
                            if (!bottomEvaluator.isBottomEquivalent(desc, signature, localityCls)) {
                                isLocal = false;
                                return;
                            }
                        }
                        isLocal = true;
                    }
                    else {
                        isLocal = false;
                    }
                    break;
                case TOP_BOTTOM:
                case TOP_TOP:
                    if (!signature.contains(rhs)) {
                        boolean bottomEquivDescFound = false;
                        for (OWLClassExpression desc : rhs) {
                            if (!bottomEvaluator.isBottomEquivalent(desc, signature, localityCls)) {
                                if (topEvaluator.isTopEquivalent(desc, signature, localityCls)) {
                                    if (bottomEquivDescFound) {
                                        isLocal = false;
                                        return;
                                    }
                                    else {
                                        bottomEquivDescFound = true;
                                    }
                                }
                                else {
                                    isLocal = false;
                                    return;
                                }
                            }
                        }
                        isLocal = true;
                    }
                    else {
                        isLocal = false;
                    }
                    break;
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            isLocal = true;
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            isLocal = true;

            Iterator<OWLClassExpression> eqs = axiom.getClassExpressions().iterator();
            OWLClassExpression first = eqs.next();

            // axiom is local if it contains a single class expression
            if (!eqs.hasNext())
                return;

            // axiom is local iff either all class expressions evaluate to TOP
            // or all evaluate to BOTTOM

            // check if first class expr. is BOTTOM
            boolean isBottom = bottomEvaluator.isBottomEquivalent(first, signature, localityCls);

            // if not BOTTOM or not TOP then this axiom is non-local
            if (!isBottom && !topEvaluator.isTopEquivalent(first, signature, localityCls))
                isLocal = false;

//            // unless we find a non-locality, process all the class expressions
//            while (isLocal && eqs.hasNext()) {
//                OWLClassExpression next = eqs.next();
//
//                if (isBottom) {
//                    // other concepts were BOTTOM so this one should be BOTTOM
//                    // too
//                    if (!bottomEvaluator.isBottomEquivalent(next, signature, localityCls)) {
//                        isLocal = false;
//                    }
//                }
//                else {
//                    // other concepts were TOP so this one should be TOP too
//                    if (!topEvaluator.isTopEquivalent(next, signature, localityCls)) {
//                        isLocal = false;
//                    }
//                }
//            }

            if (isBottom) {
                // unless we find a non-locality, process all the class expressions
                while (isLocal && eqs.hasNext()) {
                    OWLClassExpression next = eqs.next();
                    // first class expr. was BOTTOM, so this one should be BOTTOM too
                    if (!bottomEvaluator.isBottomEquivalent(next, signature, localityCls)) {
                        isLocal = false;
                    }
                }
            }
            else {
                // unless we find a non-locality, process all the class expressions
                while (isLocal && eqs.hasNext()) {
                    OWLClassExpression next = eqs.next();
                    // first class expr. was TOP, so this one should be TOP too
                    if (!topEvaluator.isTopEquivalent(next, signature, localityCls)) {
                        isLocal = false;
                    }
                }
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            Collection<OWLDataPropertyExpression> eqs = axiom.getProperties();
            int size = eqs.size();
            if (size == 1) {
                isLocal = true;
            }
            else {
                for (OWLDataPropertyExpression p : eqs) {
                    if (signature.contains(p.asOWLDataProperty())) {
                        isLocal = false;
                        return;
                    }
                }
                isLocal = true;
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Collection<OWLObjectPropertyExpression> eqs = axiom.getProperties();
            int size = eqs.size();
            if (size == 1) {
                isLocal = true;
            }
            else {
                for (OWLObjectPropertyExpression p : eqs) {
                    if (signature.contains(p.getNamedProperty())) {
                        isLocal = false;
                        return;
                    }
                }
                isLocal = true;
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        //BUGFIX (TS): replaced call to asOWLObjectProperty() with getNamedProperty()
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        public void visit(OWLImportsDeclaration axiom) {
            isLocal = false;
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            isLocal = !signature.contains(axiom.getFirstProperty().getNamedProperty()) && !signature.contains(axiom.getSecondProperty().getNamedProperty());
        }


        // BUGFIX: (TS) Irreflexive OP axioms are local in the *_BOTTOM case:
        //              The empty object property is irreflexive!
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = true;
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        // BUGFIX: (TS) Added the case where this is local. (This is dual to the case of a "positive" DP assertion.)
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        // BUGFIX: (TS) Added the case where this is local. (This is dual to the case of a "positive" OP assertion.)
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isLocal = false;
                    break;
            }
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = false;
                    break;
                case TOP_TOP:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
                    break;
            }
        }


        // BUGFIX: (TS) Added the cases where this is local
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    // Axiom is local iff at least one prop in the chain is bot-equiv
                    for (OWLObjectPropertyExpression ope : axiom.getPropertyChain()) {
                        if (!signature.contains(ope.getNamedProperty())) {
                            isLocal = true;
                            return;
                        }
                    }
                    isLocal = false;
                    break;
                case TOP_TOP:
                    // Axiom is local iff RHS is top-equiv
                    if (!signature.contains(axiom.getSuperProperty().getNamedProperty())) {
                        isLocal = true;
                    }
                    else {
                        isLocal = false;
                    }
                    break;
            }
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty()) || topEvaluator.isTopEquivalent(
                            axiom.getDomain(),
                            signature,
                            localityCls);
                    break;
                case TOP_TOP:
                    isLocal = topEvaluator.isTopEquivalent(axiom.getDomain(), signature, localityCls);
                    break;
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getProperty().getNamedProperty()) || topEvaluator.isTopEquivalent(
                            axiom.getRange(),
                            signature,
                            localityCls);
                    break;
                case TOP_TOP:
                    isLocal = topEvaluator.isTopEquivalent(axiom.getRange(), signature, localityCls);
                    break;
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isLocal = !signature.contains(axiom.getSubProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isLocal = !signature.contains(axiom.getSuperProperty().getNamedProperty());
                    break;
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
        }


        // BUGFIX: (TS) Same individuals axioms are local, too.
        //              They need to be added to the module after the locality checks have been performed.
        public void visit(OWLSameIndividualAxiom axiom) {
//            isLocal = false;
            isLocal = true;
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            isLocal = bottomEvaluator.isBottomEquivalent(axiom.getSubClass(),
                                                         signature,
                                                         localityCls) || topEvaluator.isTopEquivalent(axiom.getSuperClass(),
                                                                                                      signature,
                                                                                                      localityCls);
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
        }


        //TODO: (TS) Can't we treat this in a more differentiated way?
        public void visit(SWRLRule axiom) {
            isLocal = false;
        }

        public void visit(OWLHasKeyAxiom axiom) {
            throw new OWLRuntimeException("NOT IMPLEMENTED");
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            throw new OWLRuntimeException("NOT IMPLEMENTED");
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            throw new OWLRuntimeException("NOT IMPLEMENTED");
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            throw new OWLRuntimeException("NOT IMPLEMENTED");
        }
    }


    /**
     * Used to determine if class expressions are equivalent to \bottom using the provided locality class
     */
    private static class BottomEquivalenceEvaluator implements OWLClassExpressionVisitor {

        private boolean isBottomEquivalent;

        private LocalityClass localityCls;

        private Collection<? extends OWLEntity> signature;

        private TopEquivalenceEvaluator topEvaluator;


        public BottomEquivalenceEvaluator() {
        }


        private boolean isBottomEquivalent(OWLClassExpression desc) {
            desc.accept(this);
            return isBottomEquivalent;
        }


        public boolean isBottomEquivalent(OWLClassExpression desc, Collection<? extends OWLEntity> signature,
                                          LocalityClass localityCls) {
            this.localityCls = localityCls;
            this.signature = signature;
            desc.accept(this);
            return isBottomEquivalent;
        }


        public void setTopEvaluator(TopEquivalenceEvaluator evaluator) {
            this.topEvaluator = evaluator;
        }


        public void visit(OWLClass desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                    isBottomEquivalent = desc.isOWLNothing() || (!desc.isOWLThing() && !signature.contains(desc));
                    break;
                case TOP_BOTTOM:
                case TOP_TOP:
                    isBottomEquivalent = desc.isOWLNothing();
                    break;
            }
        }


        // BUGFIX: (TS) Even in the TOP_TOP case, this is not bottom-equiv:
        //              "forall top.D" is not necessarily empty
        public void visit(OWLDataAllValuesFrom desc) {
//            switch (localityCls) {
//                case BOTTOM_BOTTOM:
//                case TOP_BOTTOM:
//                    isBottomEquivalent = false;
//                    break;
//                case TOP_TOP:
//                    // FIXME: This ignores TOP_DATA case
//                    isBottomEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty());
//                    break;
//            }
            isBottomEquivalent = false;
        }


        // BUGFIX: (TS) Corrected both conditions; included case n==0
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        public void visit(OWLDataExactCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = (desc.getCardinality() > 0)
                            && (!signature.contains(desc.getProperty().asOWLDataProperty()));
                    break;
                case TOP_TOP:
                    isBottomEquivalent = (desc.getCardinality() > 0) && (
                            (!signature.contains(desc.getProperty().asOWLDataProperty()))
                            && isTopEquivalentDataRange(desc.getFiller())
                    );
                    break;
            }
        }


        // BUGFIX: (TS) A data max card restriction is never bottom-equiv.
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        public void visit(OWLDataMaxCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = false;
                    break;
                case TOP_TOP:
                    isBottomEquivalent = (desc.getCardinality() > 0)
                            && (!signature.contains(desc.getProperty().asOWLDataProperty()))
                            && isTopEquivalentDataRange(desc.getFiller());
                    break;
            }
        }


        // BUGFIX: (TS) The *_BOTTOM case only works if n > 0.
        public void visit(OWLDataMinCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = (desc.getCardinality() > 0)
                            && (!signature.contains(desc.getProperty().asOWLDataProperty()));
                    break;
                case TOP_TOP:
                    isBottomEquivalent = false;
                    break;
            }
        }


        public void visit(OWLDataSomeValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isBottomEquivalent = false;
                    break;
            }
        }


        public void visit(OWLDataHasValue desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isBottomEquivalent = false;
                    break;
            }
        }


        // BUGFIX (TS): TOP_TOP case was missing the first conjunct
        public void visit(OWLObjectAllValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = false;
                    break;
                case TOP_TOP:
                    isBottomEquivalent = !signature.contains(desc.getProperty().getNamedProperty()) && isBottomEquivalent(desc.getFiller());
                    break;
            }
        }


        public void visit(OWLObjectComplementOf desc) {
            isBottomEquivalent = topEvaluator.isTopEquivalent(desc.getOperand(), signature, localityCls);
        }


        // BUGFIX: (TS) Since an exact card restriction is a conjunction of a min and a max card restriction,
        //              there are cases where it is bottom-local
        public void visit(OWLObjectExactCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = (desc.getCardinality() > 0) && (
                            !signature.contains(desc.getProperty().getNamedProperty())
                            || isBottomEquivalent(desc.getFiller())
                    );
                    break;
                case TOP_TOP:
                    isBottomEquivalent = (
                            (desc.getCardinality() > 0) && (
                                    isBottomEquivalent(desc.getFiller()) || (
                                            (!signature.contains(desc.getProperty().getNamedProperty()))
                                            && topEvaluator.isTopEquivalent(desc.getFiller(), signature, localityCls)
                                    )
                            )
                    );
                    break;
            }
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression conj : desc.getOperands()) {
                if (isBottomEquivalent(conj)) {
                    isBottomEquivalent = true;
                    return;
                }
            }
            isBottomEquivalent = false;
        }


        // BUGFIX (TS): Corrected all conditions.
        //              The n==0 case doesn't affect bottom-equivalence of this type of restriction,
        //              but n>0 does!
        public void visit(OWLObjectMaxCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = false;
                    break;
                case TOP_TOP:
                    isBottomEquivalent = (desc.getCardinality() > 0)
                            && (!signature.contains(desc.getProperty().getNamedProperty()))
                            && topEvaluator.isTopEquivalent(desc.getFiller(), signature, localityCls);
                    break;
            }
        }


        // BUGFIX (TS): Corrected all conditions, considering the case n==0
        public void visit(OWLObjectMinCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = (desc.getCardinality() > 0) && (
                            !signature.contains(desc.getProperty().getNamedProperty())
                            || isBottomEquivalent(desc.getFiller())
                    );
                    break;
                case TOP_TOP:
                    isBottomEquivalent = (desc.getCardinality() > 0)
                            && isBottomEquivalent(desc.getFiller());
                    break;
            }
        }


        public void visit(OWLObjectOneOf desc) {
            isBottomEquivalent = desc.getIndividuals().isEmpty();
        }


        public void visit(OWLObjectHasSelf desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = !signature.contains(desc.getProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isBottomEquivalent = false;
                    break;
            }
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = !signature.contains(desc.getProperty().getNamedProperty())
                            || isBottomEquivalent(desc.getFiller());
                    break;
                case TOP_TOP:
                    isBottomEquivalent = isBottomEquivalent(desc.getFiller());
                    break;
            }
        }


        public void visit(OWLObjectUnionOf desc) {
            for (OWLClassExpression disj : desc.getOperands()) {
                if (!isBottomEquivalent(disj)) {
                    isBottomEquivalent = false;
                    return;
                }
            }
            isBottomEquivalent = true;
        }


        // BUGFIX (TS): desc.getValue() is an individual and therefore is *not* bot-equiv if not in the signature
        //              -> disjunct removed from *_BOTTOM case
        public void visit(OWLObjectHasValue desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isBottomEquivalent = !signature.contains(desc.getProperty().getNamedProperty());
                    break;
                case TOP_TOP:
                    isBottomEquivalent = false;
                    break;
            }
        }
    }


    /**
     * Used to determine if class expressions are equivalent to \top using the provided locality class
     */
    private static class TopEquivalenceEvaluator implements OWLClassExpressionVisitor {

        private BottomEquivalenceEvaluator bottomEvaluator;

        private boolean isTopEquivalent;

        private LocalityClass localityCls;

        private Collection<? extends OWLEntity> signature;


        public TopEquivalenceEvaluator() {
        }


        private boolean isTopEquivalent(OWLClassExpression desc) {
            desc.accept(this);
            return isTopEquivalent;
        }


        public boolean isTopEquivalent(OWLClassExpression desc, Collection<? extends OWLEntity> signature,
                                       LocalityClass localityCls) {
            this.localityCls = localityCls;
            this.signature = signature;
            desc.accept(this);
            return isTopEquivalent;
        }


        public void setBottomEvaluator(BottomEquivalenceEvaluator evaluator) {
            this.bottomEvaluator = evaluator;
        }


        public void visit(OWLClass desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                    isTopEquivalent = desc.isOWLThing();
                    break;
                case TOP_BOTTOM:
                case TOP_TOP:
                    isTopEquivalent = desc.isOWLThing() || (!desc.isOWLNothing() && !signature.contains(desc));
                    break;
            }
        }


        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        public void visit(OWLDataAllValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty())
                            || desc.getFiller().isTopDatatype();
                    break;
                case TOP_TOP:
                    isTopEquivalent = desc.getFiller().isTopDatatype();
                    break;
            }
        }


        // BUGFIX: (TS) Added the case where this is top-equiv (including n==0).
        public void visit(OWLDataExactCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = (desc.getCardinality() == 0)
                            && (!signature.contains(desc.getProperty().asOWLDataProperty()));
                    break;
                case TOP_TOP:
                    isTopEquivalent = false;
                    break;
            }
        }


        // (TS) No special handling for n==0 required.
        public void visit(OWLDataMaxCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty());
                    break;
                case TOP_TOP:
                    isTopEquivalent = false;
                    break;
            }
        }


        // BUGFIX: (TS) A data min card restriction is top-equiv iff the cardinality is 0.
        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        // BUGFIX: (TS, 2) Left out redundant check cardinality > 0 in TOP_TOP case
        public void visit(OWLDataMinCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = (desc.getCardinality() == 0);
                    break;
                case TOP_TOP:
                    isTopEquivalent = (desc.getCardinality() == 0) || (
                            (!signature.contains(desc.getProperty().asOWLDataProperty()))
                            && isTopEquivalentDataRange(desc.getFiller())
                    );
                    break;
            }
        }


        // BUGFIX: (TS, 2) Added the cases where the filler is top-equiv
        public void visit(OWLDataSomeValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = false;
                    break;
                case TOP_TOP:
                    isTopEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty())
                            && isTopEquivalentDataRange(desc.getFiller());
                    break;
            }
        }


        // BUGFIX: (TS, 2) Added the cases where this is top-equiv
        public void visit(OWLDataHasValue desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = false;
                    break;
                case TOP_TOP:
                    isTopEquivalent = !signature.contains(desc.getProperty().asOWLDataProperty());
                    break;
            }
        }


        public void visit(OWLObjectAllValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = !signature.contains(desc.getProperty().getNamedProperty()) || isTopEquivalent(desc.getFiller());
                    break;
                case TOP_TOP:
                    isTopEquivalent = isTopEquivalent(desc.getFiller());
                    break;
            }
        }


        public void visit(OWLObjectComplementOf desc) {
            isTopEquivalent = bottomEvaluator.isBottomEquivalent(desc.getOperand(), signature, localityCls);
        }


        // BUGFIX: (TS) added the cases where this is top-equiv, including n==0
        public void visit(OWLObjectExactCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = (desc.getCardinality() == 0) && (
                            (!signature.contains(desc.getProperty().getNamedProperty()))
                            || bottomEvaluator.isBottomEquivalent(desc.getFiller(), signature, localityCls)
                    );
                    break;
                case TOP_TOP:
                    isTopEquivalent = (desc.getCardinality() == 0)
                            && bottomEvaluator.isBottomEquivalent(desc.getFiller(), signature, localityCls);
                    break;
            }
        }


        public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression conj : desc.getOperands()) {
                if (!isTopEquivalent(conj)) {
                    isTopEquivalent = false;
                    return;
                }
            }
            isTopEquivalent = true;
        }


        // BUGFIX: (TS) Added the case of a bottom-equivalent filler to both conditions.
        //              The n==0 case doesn't affect top-equivalence of this type of restriction.
        public void visit(OWLObjectMaxCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = (!signature.contains(desc.getProperty().getNamedProperty()))
                            || bottomEvaluator.isBottomEquivalent(desc.getFiller(), signature, localityCls);
                    break;
                case TOP_TOP:
                    isTopEquivalent =
                            bottomEvaluator.isBottomEquivalent(desc.getFiller(), signature, localityCls);
                    break;
            }
        }


        // BUGFIX: (TS) Added the case n==0; repaired TOP_TOP condition 
        // BUGFIX: (TS, 2) Left out redundant check cardinality > 0 in TOP_TOP case
        public void visit(OWLObjectMinCardinality desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = (desc.getCardinality() == 0);
                    break;
                case TOP_TOP:
//                    isTopEquivalent = !signature.contains(desc.getProperty().getNamedProperty()) && (desc.getCardinality() <= 1);
                    isTopEquivalent = (desc.getCardinality() == 0) || (
                            (!signature.contains(desc.getProperty().getNamedProperty()))
                            && (isTopEquivalent(desc.getFiller()))
                    );
                    break;
            }
        }


        public void visit(OWLObjectOneOf desc) {
            isTopEquivalent = false;
        }


        public void visit(OWLObjectHasSelf desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = false;
                    break;
                case TOP_TOP:
                    isTopEquivalent = !signature.contains(desc.getProperty().getNamedProperty());
                    break;
            }
        }


        // BUGFIX (TS): added ".getNamedProperty()"
        public void visit(OWLObjectSomeValuesFrom desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = false;
                    break;
                case TOP_TOP:
                    isTopEquivalent = !signature.contains(desc.getProperty().getNamedProperty()) && isTopEquivalent(desc.getFiller());
                    break;
            }
        }


        public void visit(OWLObjectUnionOf desc) {
            for (OWLClassExpression conj : desc.getOperands()) {
                if (isTopEquivalent(conj)) {
                    isTopEquivalent = true;
                    return;
                }
            }
            isTopEquivalent = false;
        }


        public void visit(OWLObjectHasValue desc) {
            switch (localityCls) {
                case BOTTOM_BOTTOM:
                case TOP_BOTTOM:
                    isTopEquivalent = false;
                    break;
                case TOP_TOP:
                    isTopEquivalent = !signature.contains(desc.getProperty().getNamedProperty());
                    break;
            }
        }
    }


    /**
     * Tests whether a given axiom is local with respect to a given signature.
     * @param axiom the axiom to test
     * @param signature the signature to test against
     * @return <code>true</code> if the axiom is local w.r.t. the signature; <code>false</code> otherwise
     */
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature) {
        return axiomVisitor.isLocal(axiom, signature);
    }
}
