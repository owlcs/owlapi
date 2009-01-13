package org.coode.owl.latex;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.util.ShortFormProvider;
import org.semanticweb.owl.util.SimpleShortFormProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
 * Medical Informatics Group<br>
 * Date: 15-Jun-2006<br><br>
 */
public class LatexObjectVisitor implements OWLObjectVisitor {

    public static final String AND = "\\ensuremath{\\sqcap}";

    public static final String OR = "\\ensuremath{\\sqcup}";

    public static final String NOT = "\\ensuremath{\\lnot}";

    public static final String ALL = "\\ensuremath{\\forall}";

    public static final String SOME = "\\ensuremath{\\exists}";

    public static final String MIN = "\\ensuremath{\\geq}";

    public static final String MAX = "\\ensuremath{\\leq}";

    public static final String EQUAL = "\\ensuremath{=}";

    public static final String SUBCLASS = "\\ensuremath{\\sqsubseteq}";

    public static final String EQUIV = "\\ensuremath{\\equiv}";

    public static final String NOT_EQUIV = "\\ensuremath{\\not\\equiv}";

    public static final String TOP = "\\ensuremath{\\top}";

    public static final String BOTTOM = "\\ensuremath{\\bot}";

    public static final String SELF = "\\ensuremath{\\Self}";

    public static final String CIRC = "\\ensuremath{\\circ}";


    private OWLObject subject;

    private LatexWriter writer;

    private boolean prettyPrint = true;

    private OWLDataFactory df;

    private ShortFormProvider shortFormProvider;


    public LatexObjectVisitor(LatexWriter writer, OWLDataFactory df) {
        this.writer = writer;
        this.df = df;
        shortFormProvider = new SimpleShortFormProvider();
        subject = df.getOWLThing();
    }


    public void setSubject(OWLObject subject) {
        this.subject = subject;
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvder) {
        this.shortFormProvider = shortFormProvder;
    }


    private void writeSpace() {
        writer.writeSpace();
    }


    private void write(Object o) {
        writer.write(o);
    }


    private void writeOpenBrace() {
        writer.writeOpenBrace();
    }


    private void writeCloseBrace() {
        writer.writeCloseBrace();
    }


    public boolean isPrettyPrint() {
        return prettyPrint;
    }


    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }
//
//     private void beginTabbing() {
//        write("\\begin{tabbing}\n");
//    }
//
//    private void endTabbing() {
//        write("\\end{tabbing}\n");
//    }


    public void visit(OWLObjectIntersectionOf node) {
        for (Iterator<OWLDescription> it = node.getOperands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }


    public void visit(OWLDataAllRestriction node) {

    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataSomeRestriction node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }


    public void visit(OWLDataValueRestriction node) {
    }


    public void visit(OWLObjectAllRestriction node) {
        write(ALL);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectSomeRestriction node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectValueRestriction node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeOpenBrace();
        node.getValue().accept(this);
        writeCloseBrace();
    }


    public void visit(OWLObjectComplementOf node) {
        write(NOT);
        writeNested(node.getOperand());
    }


    public void visit(OWLObjectUnionOf node) {
        for (Iterator<OWLDescription> it = node.getOperands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }


    public void visit(OWLClass node) {
        write(escapeName(shortFormProvider.getShortForm(node)));
    }


    public void visit(OWLObjectOneOf node) {
        for (Iterator<OWLIndividual> it = node.getIndividuals().iterator(); it.hasNext();) {
            writeOpenBrace();
            it.next().accept(this);
            writeCloseBrace();
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }


    public void visit(OWLDataProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLObjectProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLIndividual entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLObjectSelfRestriction desc) {
        write(SOME);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        write(SELF);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getDescriptions().size() != 2) {
            for (OWLDescription left : axiom.getDescriptions()) {
                for (OWLDescription right : axiom.getDescriptions()) {
                    if (left != right) {
                        if (left.equals(subject)) {
                            left.accept(this);
                            writeSpace();
                            write(SUBCLASS);
                            writeSpace();
                            write(NOT);
                            writeSpace();
                            right.accept(this);
                        }
                        else {
                            right.accept(this);
                            writeSpace();
                            write(SUBCLASS);
                            writeSpace();
                            write(NOT);
                            writeSpace();
                            left.accept(this);
                        }
                        writer.writeNewLine();
                    }
                }
            }
        }
        else {
            Iterator<OWLDescription> it = axiom.getDescriptions().iterator();
            OWLDescription descA = it.next();
            OWLDescription descB = it.next();
            OWLDescription lhs;
            OWLDescription rhs;
            if(descA.equals(subject)) {
                lhs = descA;
                rhs = descB;
            }
            else {
                lhs = descB;
                rhs = descA;
            }
            lhs.accept(this);
            writeSpace();
            write(SUBCLASS);
            writeSpace();
            write(NOT);
            writeSpace();
            rhs.accept(this);
        }
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getDescriptions().size() > 2) {
            Set<Set<OWLDescription>> rendered = new HashSet<Set<OWLDescription>>();
            for (OWLDescription left : axiom.getDescriptions()) {
                for (OWLDescription right : axiom.getDescriptions()) {
                    if (left != right) {
                        Set<OWLDescription> cur = CollectionFactory.createSet(left, right);
                        if(!rendered.contains(cur)) {
                            rendered.add(cur);
                            left.accept(this);
                            writeSpace();
                            write(EQUIV);
                            writeSpace();
                            right.accept(this);
                        }

                    }
                }
            }
        }
        else if(axiom.getDescriptions().size() == 2) {
            Iterator<OWLDescription> it = axiom.getDescriptions().iterator();
            OWLDescription descA = it.next();
            OWLDescription descB = it.next();
            OWLDescription lhs;
            OWLDescription rhs;
            if(subject.equals(descA)) {
                lhs = descA;
                rhs = descB;
            }
            else {
                lhs = descB;
                rhs = descA;
            }
            lhs.accept(this);
            writeSpace();
            write(EQUIV);
            writeSpace();
            rhs.accept(this);
        }
    }


    public void visit(OWLSubClassAxiom axiom) {
        this.setPrettyPrint(false);
        axiom.getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeSpace();
        this.setPrettyPrint(true);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        writeSpace();
        write(":");
        writeSpace();
        axiom.getDescription().accept(this);
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
    }


    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it.hasNext();) {
            write("\\{");
            it.next().accept(this);
            write("\\}");
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataMaxCardinalityRestriction(axiom.getProperty(), 1).accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectMaxCardinalityRestriction(axiom.getProperty(), 1).accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        OWLObjectPropertyExpression prop = df.getOWLObjectPropertyInverse(axiom.getProperty());
        df.getOWLObjectMaxCardinalityRestriction(prop, 1).accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        write(axiom.getFirstProperty());
        writeSpace();
        write(EQUIV);
        writeSpace();
        write(axiom.getSecondProperty());
        write("\\ensuremath{^-}");
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(CIRC);
                writeSpace();
            }
        }
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        df.getOWLObjectSomeRestriction(axiom.getProperty(), df.getOWLThing()).accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectAllRestriction(axiom.getProperty(), axiom.getRange()).accept(this);
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it.hasNext();) {
            write("\\{");
            it.next().accept(this);
            write("\\}");
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIV);
        writeSpace();
        axiom.getProperty().accept(this);
        write("\\ensuremath{^-}");
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }


    private void writeNested(OWLDescription description) {
        openBracket(description);
        description.accept(this);
        closeBracket(description);
    }


    private void openBracket(OWLDescription description) {
        if (LatexBracketChecker.requiresBracket(description)) {
            write("(");
        }
    }


    private void closeBracket(OWLDescription description) {
        if (LatexBracketChecker.requiresBracket(description)) {
            write(")");
        }
    }


    private String escapeName(String name) {
        return name.replace("_", "\\_");
    }


    public void visit(OWLOntology ontology) {
    }


    public void visit(OWLObjectPropertyInverse property) {
        property.getInverse().accept(this);
        write("\\ensuremath{^-}");
    }


    public void visit(OWLConstantAnnotation annotation) {

    }


    public void visit(OWLObjectAnnotation annotation) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
    }


    public void visit(OWLDataOneOf node) {
    }


    public void visit(OWLDataRangeFacetRestriction node) {
    }


    public void visit(OWLDataRangeRestriction node) {
    }


    public void visit(OWLDataType node) {
    }


    public void visit(OWLTypedConstant node) {
    }


    public void visit(OWLUntypedConstant node) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(SWRLAtomConstantObject node) {
    }


    public void visit(SWRLAtomDVariable node) {
    }


    public void visit(SWRLAtomIndividualObject node) {
    }


    public void visit(SWRLAtomIVariable node) {
    }


    public void visit(SWRLBuiltInAtom node) {
    }


    public void visit(SWRLClassAtom node) {
    }


    public void visit(SWRLDataRangeAtom node) {
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
    }


    public void visit(SWRLDifferentFromAtom node) {
    }


    public void visit(SWRLObjectPropertyAtom node) {
    }


    public void visit(SWRLSameAsAtom node) {
    }
}
