package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;
import static org.semanticweb.owl.util.DLExpressivityChecker.Construct.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Feb-2007<br><br>
 */
public class DLExpressivityChecker implements OWLObjectVisitor {

    private Set<Construct> constructs;

    private Set<OWLOntology> ontologies;


    public List<Construct> getConstructs() throws OWLException {
        return getOrderedConstructs();
    }


    public enum Construct {

        AL("AL"),
        U("U"),
        C("C"),
        E("E"),
        N("N"),
        Q("Q"),
        H("H"),
        I("I"),
        O("O"),
        F("F"),
        TRAN("+"),
        D("(D)"),
        R("R"),
        S("S"),
        EL("EL"),
        ELPLUSPLUS("EL++");


        Construct(String s) {
            this.s = s;
        }


        private String s;


        public String toString() {
            return s;
        }
    }


    public DLExpressivityChecker(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
        constructs = new HashSet<Construct>();
    }


    public String getDescriptionLogicName() {
        List<Construct> orderedConstructs = getOrderedConstructs();
        String s = "";
        for (Construct c : orderedConstructs) {
            s += c.toString();
        }
        return s;
    }


    private void pruneConstructs() {
        if (constructs.contains(AL)) {
            // AL + U + E  can be represented using ALC
            if (constructs.contains(C)) {
                // Remove existential because this can be represented
                // with AL + Neg
                constructs.remove(E);
                // Remove out union (intersection + negation (demorgan))
                constructs.remove(U);
            }
            else if (constructs.contains(E) && constructs.contains(U)) {
                // Simplify to ALC
                constructs.add(AL);
                constructs.add(C);
                constructs.remove(E);
                constructs.remove(U);
            }
        }

        if (constructs.contains(N) || constructs.contains(Q)) {
            constructs.remove(F);
        }
        if (constructs.contains(Q)) {
            constructs.remove(N);
        }
        if (constructs.contains(AL) && constructs.contains(C) && constructs.contains(TRAN)) {
            constructs.remove(AL);
            constructs.remove(C);
            constructs.remove(TRAN);
            constructs.add(S);
        }
        if(constructs.contains(R)) {
            constructs.remove(H);
        }
    }


    private List<Construct> getOrderedConstructs() {
        constructs.clear();
        constructs.add(AL);
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                ax.accept(this);
            }
        }
        pruneConstructs();
        List<Construct> cons = new ArrayList<Construct>(constructs);
        Collections.sort(cons, new ConstructComparator());
        return cons;
    }


    /**
     * A comparator that orders DL constucts to produce a traditional DL
     * name.
     */
    private class ConstructComparator implements Comparator<Construct> {

        private List<Construct> order = new ArrayList<Construct>();


        public ConstructComparator() {
            order.add(S);
            order.add(AL);
            order.add(C);
            order.add(U);
            order.add(E);
            order.add(R);
            order.add(H);
            order.add(O);
            order.add(I);
            order.add(N);
            order.add(Q);
            order.add(F);
            order.add(TRAN);
            order.add(D);
        }


        public int compare(Construct o1, Construct o2) {
            return order.indexOf(o1) - order.indexOf(o2);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLIndividual individual) {
    }


    public void visit(OWLObjectAnnotation annotation) {
    }


    public void visit(OWLConstantAnnotation annotation) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectProperty property) {
    }


    public void visit(OWLObjectPropertyInverse property) {
        constructs.add(I);
    }


    public void visit(OWLDataProperty property) {
        constructs.add(D);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data stuff
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataType node) {
    }


    public void visit(OWLDataComplementOf node) {
        constructs.add(D);
    }


    public void visit(OWLDataOneOf node) {
        constructs.add(D);
    }


    public void visit(OWLDataRangeRestriction node) {
        constructs.add(D);
    }


    public void visit(OWLTypedLiteral node) {
        constructs.add(D);
    }


    public void visit(OWLRDFTextLiteral node) {
        constructs.add(D);
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        constructs.add(D);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Descriptions
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLClass desc) {
    }


    public void visit(OWLObjectIntersectionOf desc) {
        constructs.add(AL);
        for (OWLDescription operands : desc.getOperands()) {
            operands.accept(this);
        }
    }


    public void visit(OWLObjectUnionOf desc) {
        constructs.add(U);
        for (OWLDescription operands : desc.getOperands()) {
            operands.accept(this);
        }
    }


    private boolean isTop(OWLDescription description) {
        if (!description.isAnonymous()) {
            return OWLRDFVocabulary.OWL_THING.getURI().equals(((OWLClass) description).getURI());
        }
        else {
            return false;
        }
    }


    private boolean isAtomic(OWLDescription description) {
        if (description.isAnonymous()) {
            return false;
        }
        else {
            for (OWLOntology ont : ontologies) {
                if (!ont.getAxioms(((OWLClass) description)).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }


    public void visit(OWLObjectComplementOf desc) {
        if (isAtomic(desc)) {
            constructs.add(AL);
        }
        else {
            constructs.add(C);
        }
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectSomeRestriction desc) {
        if (isTop(desc.getFiller())) {
            constructs.add(AL);
        }
        else {
            constructs.add(E);
        }
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectAllRestriction desc) {
        if (isTop(desc.getFiller())) {
            constructs.add(AL);
        }
        else {
            // TODO: DOUBLE CHECK
            constructs.add(AL);
        }
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectValueRestriction desc) {
        constructs.add(O);
        constructs.add(E);
        desc.getProperty().accept(this);
    }


    private void checkCardinality(OWLCardinalityRestriction restriction) {
        if (restriction.isQualified()) {
            constructs.add(Q);
        }
        else {
            constructs.add(N);
        }
        restriction.getFiller().accept(this);
        restriction.getProperty().accept(this);
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        checkCardinality(desc);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        checkCardinality(desc);
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        checkCardinality(desc);
    }


    public void visit(OWLObjectSelfRestriction desc) {
        desc.getProperty().accept(this);
        constructs.add(R);
    }


    public void visit(OWLObjectOneOf desc) {
        constructs.add(U);
        constructs.add(O);
    }


    public void visit(OWLDataSomeRestriction desc) {
        constructs.add(E);
        desc.getFiller().accept(this);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataAllRestriction desc) {
        desc.getFiller().accept(this);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataValueRestriction desc) {
        constructs.add(D);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        checkCardinality(desc);
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        checkCardinality(desc);
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        checkCardinality(desc);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLSubClassAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        constructs.add(C);
        for (OWLDescription desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        constructs.add(AL);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {

    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {

    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        constructs.add(AL);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        constructs.add(H);
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        constructs.add(U);
        constructs.add(O);
        constructs.add(C);
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        constructs.add(D);
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        constructs.add(R);
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        constructs.add(AL);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        constructs.add(F);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        constructs.add(H);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        constructs.add(U);
        constructs.add(C);
        for (OWLDescription desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDeclarationAxiom axiom) {

    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        constructs.add(I);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        constructs.add(AL);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        constructs.add(F);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        constructs.add(H);
        constructs.add(D);
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getDescription().accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        for (OWLDescription desc : axiom.getDescriptions()) {
            desc.accept(this);
        }
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        constructs.add(D);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        constructs.add(TRAN);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        constructs.add(H);
        constructs.add(D);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        constructs.add(I);
        constructs.add(F);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        constructs.add(O);
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        constructs.add(R);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        constructs.add(I);
    }


    public void visit(SWRLRule rule) {
    }


    public void visit(OWLOntology ontology) {
    }


    public void visit(SWRLClassAtom node) {
    }


    public void visit(SWRLDataRangeAtom node) {
    }


    public void visit(SWRLObjectPropertyAtom node) {
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
    }


    public void visit(SWRLBuiltInAtom node) {
    }


    public void visit(SWRLAtomDVariable node) {
    }


    public void visit(SWRLAtomIVariable node) {
    }


    public void visit(SWRLAtomIndividualObject node) {
    }


    public void visit(SWRLAtomConstantObject node) {
    }


    public void visit(SWRLSameAsAtom node) {
    }


    public void visit(SWRLDifferentFromAtom node) {
    }
}
