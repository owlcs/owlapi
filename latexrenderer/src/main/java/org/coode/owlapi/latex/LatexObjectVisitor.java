package org.coode.owlapi.latex;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

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
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }


    public void visit(OWLDataAllValuesFrom node) {

    }


    public void visit(OWLDataExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }


    public void visit(OWLDataHasValue node) {
    }


    public void visit(OWLObjectAllValuesFrom node) {
        write(ALL);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectHasValue node) {
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
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it.hasNext();) {
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


    public void visit(OWLNamedIndividual entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLObjectHasSelf desc) {
        write(SOME);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        write(SELF);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() != 2) {
            for (OWLClassExpression left : axiom.getClassExpressions()) {
                for (OWLClassExpression right : axiom.getClassExpressions()) {
                    if (left != right) {
                        if (left.equals(subject)) {
                            left.accept(this);
                            writeSpace();
                            write(SUBCLASS);
                            writeSpace();
                            write(NOT);
                            writeSpace();
                            right.accept(this);
                        } else {
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
        } else {
            Iterator<OWLClassExpression> it = axiom.getClassExpressions().iterator();
            OWLClassExpression descA = it.next();
            OWLClassExpression descB = it.next();
            OWLClassExpression lhs;
            OWLClassExpression rhs;
            if (descA.equals(subject)) {
                lhs = descA;
                rhs = descB;
            } else {
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
        if (axiom.getClassExpressions().size() > 2) {
            Set<Set<OWLClassExpression>> rendered = new HashSet<Set<OWLClassExpression>>();
            for (OWLClassExpression left : axiom.getClassExpressions()) {
                for (OWLClassExpression right : axiom.getClassExpressions()) {
                    if (left != right) {
                        Set<OWLClassExpression> cur = CollectionFactory.createSet(left, right);
                        if (!rendered.contains(cur)) {
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
        } else if (axiom.getClassExpressions().size() == 2) {
            Iterator<OWLClassExpression> it = axiom.getClassExpressions().iterator();
            OWLClassExpression descA = it.next();
            OWLClassExpression descB = it.next();
            OWLClassExpression lhs;
            OWLClassExpression rhs;
            if (subject.equals(descA)) {
                lhs = descA;
                rhs = descB;
            } else {
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


    public void visit(OWLSubClassOfAxiom axiom) {
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
        axiom.getClassExpression().accept(this);
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
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


    public void visit(OWLAnnotationAssertionAxiom axiom) {
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
        df.getOWLDataMaxCardinality(1, axiom.getProperty()).accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectMaxCardinality(1, axiom.getProperty()).accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        OWLObjectPropertyExpression prop = df.getOWLObjectInverseOf(axiom.getProperty());
        df.getOWLObjectMaxCardinality(1, prop).accept(this);
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


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
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
        df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()).accept(this);
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
        df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualAxiom axiom) {
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


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }

    public void visit(SWRLVariable node) {
    }

    private void writeNested(OWLClassExpression classExpression) {
        openBracket(classExpression);
        classExpression.accept(this);
        closeBracket(classExpression);
    }


    private void openBracket(OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write("(");
        }
    }


    private void closeBracket(OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write(")");
        }
    }



    private String escapeName(String name) {
        return name.replace("_", "\\_");
    }


    public void visit(OWLOntology ontology) {
    }


    public void visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write("\\ensuremath{^-}");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
    }


    public void visit(OWLDataOneOf node) {
    }


    public void visit(OWLFacetRestriction node) {
    }


    public void visit(OWLDatatypeRestriction node) {
    }


    public void visit(OWLDatatype node) {
    }


    public void visit(OWLTypedLiteral node) {
    }


    public void visit(OWLStringLiteral node) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(SWRLLiteralArgument node) {
    }


    public void visit(SWRLIndividualArgument node) {
    }


    public void visit(SWRLBuiltInAtom node) {
    }


    public void visit(SWRLClassAtom node) {
    }


    public void visit(SWRLDataRangeAtom node) {
    }


    public void visit(SWRLDataPropertyAtom node) {
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
    }


    public void visit(SWRLObjectPropertyAtom node) {
    }


    public void visit(SWRLSameIndividualAtom node) {
    }

    public void visit(OWLAnnotationProperty property) {
    }

    public void visit(OWLAnnotation annotation) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLAnnotationValue value) {
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLDataIntersectionOf node) {
    }

    public void visit(OWLDataUnionOf node) {
    }

    public void visit(OWLAnonymousIndividual individual) {
    }

    public void visit(IRI iri) {
    }
}
