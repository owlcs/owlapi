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

import static org.semanticweb.owlapi.io.ToStringRenderer.getRendering;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

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
    /** AND. */           public static final String AND       = "\\ensuremath{\\sqcap}";
    /** OR. */            public static final String OR        = "\\ensuremath{\\sqcup}";
    /** NOT. */           public static final String NOT       = "\\ensuremath{\\lnot}";
    /** ALL. */           public static final String ALL       = "\\ensuremath{\\forall}";
    /** SOME. */          public static final String SOME      = "\\ensuremath{\\exists}";
    /** HASVALUE. */      public static final String HASVALUE  = "\\ensuremath{hasValue}";
    /** MIN. */           public static final String MIN       = "\\ensuremath{\\geq}";
    /** MAX. */           public static final String MAX       = "\\ensuremath{\\leq}";
    /** MINEX. */         public static final String MINEX     = "\\ensuremath{>}";
    /** MAXEX. */         public static final String MAXEX     = "\\ensuremath{<}";
    /** EQUAL. */         public static final String EQUAL     = "\\ensuremath{=}";
    /** SUBCLASS. */      public static final String SUBCLASS  = "\\ensuremath{\\sqsubseteq}";
    /** EQUIV. */         public static final String EQUIV     = "\\ensuremath{\\equiv}";
    /** NOT_EQUIV. */     public static final String NOT_EQUIV = "\\ensuremath{\\not\\equiv}";
    /** TOP. */           public static final String TOP       = "\\ensuremath{\\top}";
    /** BOTTOM. */        public static final String BOTTOM    = "\\ensuremath{\\bot}";
    /** SELF. */          public static final String SELF      = "\\ensuremath{\\Self}";
    /** CIRC. */          public static final String CIRC      = "\\ensuremath{\\circ}";
    /** INVERSE */		  public static final String INVERSE   = "\\ensuremath{^-}";
    //@formatter:on
    private OWLObject subject;
    private final LatexWriter writer;
    private boolean prettyPrint = true;
    private final OWLDataFactory df;
    private ShortFormProvider shortFormProvider;

    /**
     * @param writer
     *        writer
     * @param df
     *        data factory
     */
    public LatexObjectVisitor(LatexWriter writer, OWLDataFactory df) {
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

    private void write(String o) {
        writer.write(o);
    }

    private void write(int i) {
        writer.write(Integer.toString(i));
    }

    private void writeOpenBrace() {
        writer.writeOpenBrace();
    }

    private void writeCloseBrace() {
        writer.writeCloseBrace();
    }

    /**
     * @return true if pretty print
     */
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
    public void visit(OWLObjectIntersectionOf ce) {
        for (Iterator<? extends OWLClassExpression> it = ce.operands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        write(ALL);
        ce.getProperty().accept(this);
        write(".");
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        write(EQUAL);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
        write(".");
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        write(MAX);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        write(MIN);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        write(SOME);
        ce.getProperty().accept(this);
        write(".");
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        write(SOME);
        ce.getProperty().accept(this);
        write(".");
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        write(ALL);
        ce.getProperty().accept(this);
        write(".");
        writeNested(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        write(EQUAL);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
        write(".");
        writeNested(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        write(MAX);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
        write(".");
        writeNested(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        write(MIN);
        write(ce.getCardinality());
        writeSpace();
        ce.getProperty().accept(this);
        write(".");
        writeNested(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        write(SOME);
        ce.getProperty().accept(this);
        write(".");
        writeNested(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        write(SOME);
        ce.getProperty().accept(this);
        writeSpace();
        writeOpenBrace();
        ce.getFiller().accept(this);
        writeCloseBrace();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(NOT);
        writeNested(ce.getOperand());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        for (Iterator<? extends OWLClassExpression> it = ce.operands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLClass ce) {
        write(escapeName(shortFormProvider.getShortForm(ce)));
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        for (Iterator<? extends OWLIndividual> it = ce.individuals().iterator(); it.hasNext();) {
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
    public void visit(OWLDataProperty property) {
        write(escapeName(shortFormProvider.getShortForm(property)));
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write(escapeName(shortFormProvider.getShortForm(property)));
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write(escapeName(shortFormProvider.getShortForm(individual)));
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(SOME);
        ce.getProperty().accept(this);
        write(".");
        write(SELF);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() != 2) {
            for (OWLClassExpression left : classExpressions) {
                for (OWLClassExpression right : classExpressions) {
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
            Iterator<OWLClassExpression> it = classExpressions.iterator();
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
    public void visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() > 2) {
            Set<Set<OWLClassExpression>> rendered = new HashSet<>();
            for (OWLClassExpression left : classExpressions) {
                for (OWLClassExpression right : classExpressions) {
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
        } else if (classExpressions.size() == 2) {
            Iterator<OWLClassExpression> it = classExpressions.iterator();
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
    public void visit(OWLSubClassOfAxiom axiom) {
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
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        writeSpace();
        write(":");
        writeSpace();
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(NOT);
        axiom.getProperty().accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write("(");
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype()).accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        write("Declaration");
        axiom.getEntity().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.individuals().iterator(); it.hasNext();) {
            writeOpenBrace();
            it.next().accept(this);
            writeCloseBrace();
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write("Disjoint");
        write("(");
        for (Iterator<OWLObjectPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(",");
                writeSpace();
            }
        }
        write(")");
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        // DO OTHER AXIOM HERE!
        write("DisjointClasses");
        write("(");
        axiom.classExpressions().forEach(p -> {
            p.accept(this);
            writeSpace();
        });
        write(")");
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        write("Annotation");
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getValue().accept(this);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataMaxCardinality(1, axiom.getProperty()).accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
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
    public void visit(OWLImportsDeclaration axiom) {
        write("ImportsDeclaration");
        axiom.getIRI().accept(this);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        OWLObjectPropertyExpression property = axiom.getProperty();
        if (property.isAnonymous()) {
            df.getOWLObjectMaxCardinality(1, property).accept(this);
        } else {
            OWLObjectPropertyExpression prop = df.getOWLObjectInverseOf(property.asOWLObjectProperty());
            df.getOWLObjectMaxCardinality(1, prop).accept(this);
        }
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        writeSpace();
        write(EQUIV);
        writeSpace();
        axiom.getSecondProperty().accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        write("IrreflexiveObjectProperty");
        write("(");
        axiom.getProperty().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }

    @Override
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

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()).accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        write("ReflexiveProperty");
        write("(");
        axiom.getProperty().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.individuals().iterator(); it.hasNext();) {
            writeOpenBrace();
            it.next().accept(this);
            writeCloseBrace();
            if (it.hasNext()) {
                writeSpace();
                write("=");
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIV);
        writeSpace();
        axiom.getProperty().accept(this);
        write("\\ensuremath{^-}");
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        axiom.getDatatype().accept(this);
        write(EQUIV);
        axiom.getDataRange().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        write("TransitiveProperty");
        write("(");
        axiom.getProperty().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLRule rule) {
        write("SWRLRule");
        rule.head().forEach(a -> a.accept(this));
        write("\\rightarrow");
        rule.body().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(SWRLVariable node) {
        node.getIRI().accept(this);
    }

    private void writeNested(OWLClassExpression classExpression) {
        openBracket(classExpression);
        classExpression.accept(this);
        closeBracket(classExpression);
    }

    private void writeNested(OWLObject expression) {
        expression.accept(this);
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

    private static String escapeName(String name) {
        return name.replace("_", "\\_").replace("#", "\\#");
    }

    @Override
    public void visit(OWLOntology ontology) {
        // nothing to do here
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(NOT);
        writeNested(node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        for (Iterator<? extends OWLLiteral> it = node.values().iterator(); it.hasNext();) {
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
    public void visit(OWLFacetRestriction node) {
        String facet = node.getFacet().toString();
        if (facet.equalsIgnoreCase("minInclusive")) {
            write(MIN);
        } else if (facet.equalsIgnoreCase("minExclusive")) {
            write(MINEX);
        } else if (facet.equalsIgnoreCase("maxInclusive")) {
            write(MAX);
        } else if (facet.equalsIgnoreCase("maxExclusive")) {
            write(MAXEX);
        } else {
            write(facet);
        }
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write("(");
        node.getDatatype().accept(this);
        write(":");
        // Need to know when to stop printing "and"
        List<OWLFacetRestriction> facetRestrictions = asList(node.facetRestrictions());
        for (int i = 0; i < facetRestrictions.size(); i++) {
            OWLFacetRestriction r = facetRestrictions.get(i);
            writeSpace();
            r.accept(this);
            if (i != facetRestrictions.size() - 1) {
                writeSpace();
                write("and");
            }
        }
        write(")");
    }

    @Override
    public void visit(OWLDatatype node) {
        write(getRendering(node));
    }

    @Override
    public void visit(OWLLiteral node) {
        writeOpenBrace();
        write("``");
        write(node.getLiteral());
        write("\"\\^{}\\^{}");
        write(getRendering(node.getDatatype()));
        writeCloseBrace();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        node.getPredicate().accept(this);
        for (SWRLDArgument d : node.getArguments()) {
            writeSpace();
            d.accept(this);
        }
    }

    @Override
    public void visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        node.allArguments().forEach(a -> {
            writeSpace();
            a.accept(this);
        });
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        node.allArguments().forEach(a -> {
            writeSpace();
            a.accept(this);
        });
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        write("AnnotationProperty");
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLAnnotation node) {
        write("Annotation");
        node.getProperty().getIRI().accept(this);
        node.getValue().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        write("Domain");
        axiom.getProperty().getIRI().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        write("Range");
        axiom.getProperty().getIRI().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
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
    public void visit(OWLAnnotationValue value) {
        value.accept(new OWLAnnotationValueVisitor() {

            @Override
            public void visit(IRI iri) {
                iri.accept(this);
            }

            @Override
            public void visit(OWLAnonymousIndividual individual) {
                individual.accept(this);
            }

            @Override
            public void visit(OWLLiteral literal) {
                literal.accept(this);
            }
        });
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        write("HasKey");
        write("(");
        axiom.getClassExpression().accept(this);
        write(")");
        writeSpace();
        write("=");
        writeSpace();
        writeOpenBrace();
        for (Iterator<OWLPropertyExpression> it = axiom.propertyExpressions().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(",");
                writeSpace();
            }
        }
        writeCloseBrace();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        for (Iterator<? extends OWLDataRange> it = node.operands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        for (Iterator<? extends OWLDataRange> it = node.operands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(IRI iri) {
        write(iri.getShortForm());
    }
}
