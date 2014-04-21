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
package org.semanticweb.owlapi.latex.renderer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import javax.annotation.Nonnull;

/**
 * NOTE: this class was not designed as a general purpose renderer, i.e., some
 * ontologies might be misrepresented in the output. Please report any
 * formatting error you find to the bug tracker or the mailing list.
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public class LatexObjectVisitor implements OWLObjectVisitor {

    //@formatter:off
        /** AND */      private static final String AND     = "\\ensuremath{\\sqcap}";
    /** OR */           public static final String OR       = "\\ensuremath{\\sqcup}";
    /** NOT */          public static final String NOT      = "\\ensuremath{\\lnot}";
    /** ALL */          public static final String ALL      = "\\ensuremath{\\forall}";
    /** SOME */         public static final String SOME     = "\\ensuremath{\\exists}";
    /** HASVALUE */     public static final String HASVALUE = "\\ensuremath{hasValue}";
    /** MIN */          public static final String MIN      = "\\ensuremath{\\geq}";
    /** MAX */          public static final String MAX      = "\\ensuremath{\\leq}";
    /** EQUAL */        public static final String EQUAL    = "\\ensuremath{=}";
    /** SUBCLASS */     public static final String SUBCLASS = "\\ensuremath{\\sqsubseteq}";
    /** EQUIV */        public static final String EQUIV    = "\\ensuremath{\\equiv}";
    /** NOT_EQUIV */    public static final String NOT_EQUIV= "\\ensuremath{\\not\\equiv}";
    /** TOP */          public static final String TOP      = "\\ensuremath{\\top}";
    /** BOTTOM */       public static final String BOTTOM   = "\\ensuremath{\\bot}";
    /** SELF */         public static final String SELF     = "\\ensuremath{\\Self}";
    /** CIRC */         public static final String CIRC     = "\\ensuremath{\\circ}";
    //@formatter:on
    private OWLObject subject;
    private LatexWriter writer;
    private boolean prettyPrint = true;
    private OWLDataFactory df;
    private ShortFormProvider shortFormProvider;

    /**
     * @param writer
     *        writer
     * @param df
     *        data factory
     */
    public LatexObjectVisitor(LatexWriter writer, @Nonnull OWLDataFactory df) {
        this.writer = writer;
        this.df = df;
        shortFormProvider = new SimpleShortFormProvider();
        subject = df.getOWLThing();
    }

    /**
     * @param subject
     *        subject
     */
    public void setSubject(OWLObject subject) {
        this.subject = subject;
    }

    /**
     * @param shortFormProvder
     *        shortFormProvder
     */
    public void setShortFormProvider(ShortFormProvider shortFormProvder) {
        shortFormProvider = shortFormProvder;
    }

    private void writeSpace() {
        writer.writeSpace();
    }

    private void write(@Nonnull Object o) {
        writer.write(o);
    }

    private void writeOpenBrace() {
        writer.writeOpenBrace();
    }

    private void writeCloseBrace() {
        writer.writeCloseBrace();
    }

    /** @return true if pretty print */
    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    /**
     * @param prettyPrint
     *        prettyPrint
     */
    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf node) {
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it
                .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom node) {
        write(ALL);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue node) {
        write(HASVALUE);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom node) {
        write(ALL);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeOpenBrace();
        node.getFiller().accept(this);
        writeCloseBrace();
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf node) {
        write(NOT);
        writeNested(node.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf node) {
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it
                .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLClass node) {
        write(escapeName(shortFormProvider.getShortForm(node)));
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf node) {
        for (Iterator<OWLIndividual> it = node.getIndividuals().iterator(); it
                .hasNext();) {
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

    @Override
    public void visit(@Nonnull OWLDataProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf desc) {
        write(SOME);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        write(SELF);
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
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
            Iterator<OWLClassExpression> it = axiom.getClassExpressions()
                    .iterator();
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

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() > 2) {
            Set<Set<OWLClassExpression>> rendered = new HashSet<Set<OWLClassExpression>>();
            for (OWLClassExpression left : axiom.getClassExpressions()) {
                for (OWLClassExpression right : axiom.getClassExpressions()) {
                    if (left != right) {
                        Set<OWLClassExpression> cur = CollectionFactory
                                .createSet(left, right);
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
            Iterator<OWLClassExpression> it = axiom.getClassExpressions()
                    .iterator();
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

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        setPrettyPrint(false);
        axiom.getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeSpace();
        setPrettyPrint(true);
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        writeSpace();
        write(":");
        writeSpace();
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        write("AsymmetricProperty");
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write("(");
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype())
                .accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange())
                .accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        write("Declaration");
        axiom.getEntity().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it
                .hasNext();) {
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

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties()
                .iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        write("DisjointObjectProperties");
        writeSpace();
        for (OWLObjectPropertyExpression p : axiom.getProperties()) {
            p.accept(this);
            writeSpace();
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        write("DisjointUnion");
        writeSpace();
        for (OWLClassExpression p : axiom.getClassExpressions()) {
            p.accept(this);
            writeSpace();
        }
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        write("Annotation");
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties()
                .iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getProperties()
                .iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataMaxCardinality(1, axiom.getProperty()).accept(this);
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectMaxCardinality(1, axiom.getProperty()).accept(this);
    }

    /**
     * @param axiom
     *        the axiom
     */
    public void visit(@Nonnull OWLImportsDeclaration axiom) {
        write("ImportsDeclaration");
        axiom.getIRI().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        OWLObjectPropertyExpression prop = df.getOWLObjectInverseOf(axiom
                .getProperty());
        df.getOWLObjectMaxCardinality(1, prop).accept(this);
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        write(axiom.getFirstProperty());
        writeSpace();
        write(EQUIV);
        writeSpace();
        write(axiom.getSecondProperty());
        write("\\ensuremath{^-}");
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        write("IrreflexiveObjectProperty");
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom
                .getPropertyChain().iterator(); it.hasNext();) {
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

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing())
                .accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())
                .accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        write("ReflexiveProperty");
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it
                .hasNext();) {
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

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIV);
        writeSpace();
        axiom.getProperty().accept(this);
        write("\\ensuremath{^-}");
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        write("Datatype");
        axiom.getDatatype().accept(this);
        write(EQUIV);
        axiom.getDataRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        write("TransitiveProperty");
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLRule rule) {
        write("SWRLRule");
        for (SWRLAtom a : rule.getHead()) {
            a.accept(this);
        }
        write("\\rightarrow");
        for (SWRLAtom a : rule.getBody()) {
            a.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        write(node.getIRI());
    }

    private void writeNested(@Nonnull OWLClassExpression classExpression) {
        openBracket(classExpression);
        classExpression.accept(this);
        closeBracket(classExpression);
    }

    private void writeNested(@Nonnull OWLObject expression) {
        expression.accept(this);
    }

    private void openBracket(@Nonnull OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write("(");
        }
    }

    private void closeBracket(@Nonnull OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write(")");
        }
    }

    private String escapeName(@Nonnull String name) {
        return name.replace("_", "\\_");
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {}

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write("\\ensuremath{^-}");
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        write(NOT);
        writeNested(node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        for (Iterator<OWLLiteral> it = node.getValues().iterator(); it
                .hasNext();) {
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

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        write("Facet");
        write(node.getFacet());
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        write("DatatypeRestriction");
        node.getDatatype().accept(this);
        for (OWLFacetRestriction r : node.getFacetRestrictions()) {
            writeSpace();
            r.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        write("Datatype");
        write(node.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        write("\"");
        write(node.getLiteral());
        write("\"\\^\\^");
        write(node.getDatatype().getIRI());
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        node.getPredicate().accept(this);
        for (SWRLDArgument d : node.getArguments()) {
            writeSpace();
            d.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        node.getArgument().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        for (SWRLArgument a : node.getAllArguments()) {
            writeSpace();
            a.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        for (SWRLArgument a : node.getAllArguments()) {
            writeSpace();
            a.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        write("AnnotationProperty");
        property.getIRI().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLAnnotation annotation) {
        write("Annotation");
        annotation.getProperty().getIRI().accept(this);
        annotation.getValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        write("Domain");
        axiom.getProperty().getIRI().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        write("Range");
        axiom.getProperty().getIRI().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    /**
     * @param value
     *        value
     */
    public void visit(@Nonnull OWLAnnotationValue value) {
        value.accept(new OWLAnnotationValueVisitor() {

            @Override
            public void visit(@Nonnull IRI iri) {
                iri.accept(LatexObjectVisitor.this);
            }

            @Override
            public void visit(@Nonnull OWLAnonymousIndividual individual) {
                individual.accept(LatexObjectVisitor.this);
            }

            @Override
            public void visit(@Nonnull OWLLiteral literal) {
                literal.accept(LatexObjectVisitor.this);
            }
        });
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        write("HasKey");
        axiom.getClassExpression().accept(this);
        for (OWLPropertyExpression p : axiom.getPropertyExpressions()) {
            writeSpace();
            p.accept(this);
        }
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        for (Iterator<OWLDataRange> it = node.getOperands().iterator(); it
                .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        for (Iterator<OWLDataRange> it = node.getOperands().iterator(); it
                .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(@Nonnull IRI iri) {
        write(iri.getFragment());
    }
}
