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
package org.semanticweb.owlapi6.latex.renderer;

import static org.semanticweb.owlapi6.documents.ToStringRenderer.getRendering;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi6.model.SWRLClassAtom;
import org.semanticweb.owlapi6.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi6.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.CollectionFactory;
import org.semanticweb.owlapi6.utility.SimpleShortFormProvider;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

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
    /** ALL. */           public static final String ALL      = "\\ensuremath{\\forall}";
    /** AND. */           public static final String AND      = "\\ensuremath{\\sqcap}";
    /** BOTTOM. */        public static final String BOTTOM   = "\\ensuremath{\\bot}";
    /** CIRC. */          public static final String CIRC     = "\\ensuremath{\\circ}";
    /** EQUAL. */         public static final String EQUAL    = "\\ensuremath{=}";
    /** EQUIV. */         public static final String EQUIV    = "\\ensuremath{\\equiv}";
    /** HASVALUE. */      public static final String HASVALUE = "\\ensuremath{hasValue}";
    /** INVERSE */		  public static final String INVERSE  = "\\ensuremath{^-}";
    /** MAX. */           public static final String MAX      = "\\ensuremath{\\leq}";
    /** MAXEX. */         public static final String MAXEX    = "\\ensuremath{<}";
    /** MIN. */           public static final String MIN      = "\\ensuremath{\\geq}";
    /** MINEX. */         public static final String MINEX    = "\\ensuremath{>}";
    /** NOT. */           public static final String NOT      = "\\ensuremath{\\lnot}";
    /** NOT_EQUIV. */     public static final String NOT_EQUIV= "\\ensuremath{\\not\\equiv}";
    /** OR. */            public static final String OR       = "\\ensuremath{\\sqcup}";
    /** SELF. */          public static final String SELF     = "\\ensuremath{\\Self}";
    /** SOME. */          public static final String SOME     = "\\ensuremath{\\exists}";
    /** SUBCLASS. */      public static final String SUBCLASS = "\\ensuremath{\\sqsubseteq}";
    /** TOP. */           public static final String TOP      = "\\ensuremath{\\top}";
    //@formatter:on
    private boolean prettyPrint = true;
    private ShortFormProvider shortFormProvider;
    private OWLObject subject = OWLRDFVocabulary.OWL_THING.getIRI();
    private final LatexWriter writer;

    /**
     * @param writer
     *        writer
     */
    public LatexObjectVisitor(LatexWriter writer) {
        this.writer = writer;
        shortFormProvider = new SimpleShortFormProvider();
    }

    private static String escapeName(String name) {
        return name.replace("_", "\\_").replace("#", "\\#");
    }

    /**
     * @param classExpression
     *        class expression
     * @return true if bracket required
     */
    protected static boolean requiresBracket(OWLClassExpression classExpression) {
        return !(classExpression.type() == OWLObjectType.CLASS || classExpression.type() == OWLObjectType.NOT_OBJECT);
    }

    protected LatexObjectVisitor braced(OWLObject o) {
        return write("{").accept(o).write("}");
    }

    private LatexObjectVisitor closeBracket(OWLClassExpression classExpression) {
        if (requiresBracket(classExpression)) {
            write(")");
        }
        return this;
    }

    protected LatexObjectVisitor dot() {
        return write(".");
    }

    /**
     * @return true if pretty print
     */
    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    protected <T extends OWLObject> LatexObjectVisitor iterate(Iterator<T> i) {
        return iterate(i, this::accept, () -> write(", "));
    }

    protected <T> LatexObjectVisitor iterate(Iterator<T> i, Consumer<T> c, Runnable separator) {
        while (i.hasNext()) {
            c.accept(i.next());
            if (i.hasNext()) {
                separator.run();
            }
        }
        return this;
    }

    protected <T extends OWLObject> LatexObjectVisitor iterate(Iterator<T> i, Runnable separator) {
        return iterate(i, this::accept, separator);
    }

    protected LatexObjectVisitor not() {
        return write(NOT);
    }

    private LatexObjectVisitor openBracket(OWLClassExpression classExpression) {
        if (requiresBracket(classExpression)) {
            write("(");
        }
        return this;
    }

    protected LatexObjectVisitor prefixedDouble(OWLObject a, OWLObject o1, OWLObject o2) {
        return accept(a).write("(").accept(o1).write(", ").accept(o2).write(")");
    }

    protected LatexObjectVisitor prefixedDouble(String a, OWLObject o1, OWLObject o2) {
        return write(a).accept(o1).writeSpace().accept(o2);
    }

    private LatexObjectVisitor rounded(OWLObject o) {
        return write("(").accept(o).write(")");
    }

    /**
     * @param prettyPrint
     *        prettyPrint
     */
    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    /**
     * @param shortFormProvder
     *        shortFormProvder
     */
    public void setShortFormProvider(ShortFormProvider shortFormProvder) {
        shortFormProvider = shortFormProvder;
    }

    /**
     * @param subject
     *        subject
     */
    public void setSubject(OWLObject subject) {
        this.subject = subject;
    }

    protected LatexObjectVisitor spaced(String delim) {
        return writeSpace().write(delim).writeSpace();
    }

    protected LatexObjectVisitor subClass() {
        return spaced(SUBCLASS);
    }

    protected LatexObjectVisitor top() {
        return write(TOP);
    }

    private LatexObjectVisitor accept(OWLObject o) {
        o.accept(this);
        return this;
    }

    @Override
    public void visit(IRI iri) {
        write(iri.getShortForm());
    }

    @Override
    public void visit(OWLAnnotation node) {
        write("Annotation").accept(node.getProperty().getIRI()).accept(node.getValue());
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        write("Annotation").accept(axiom.getSubject()).writeSpace().accept(axiom.getProperty()).writeSpace()
            .accept(axiom.getValue());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        write("AnnotationProperty").accept(property.getIRI());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        prefixedDouble("Domain", axiom.getProperty().getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        prefixedDouble("Range", axiom.getProperty().getIRI(), axiom.getRange());
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        accept(axiom.getProperty()).subClass().not().accept(axiom.getProperty()).write(INVERSE);
    }

    @Override
    public void visit(OWLClass ce) {
        write(escapeName(shortFormProvider.getShortForm(ce)));
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        accept(axiom.getIndividual()).write(" : ").accept(axiom.getClassExpression());
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(ALL, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        not().writeNested(node.getDataRange());
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeCardinality(EQUAL, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeRestriction(SOME, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        iterate(node.getOperandsAsList().iterator(), () -> spaced(AND));
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeCardinality(MAX, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeCardinality(MIN, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        iterate(node.getOperandsAsList().iterator(), this::braced, () -> spaced(OR));
    }

    @Override
    public void visit(OWLDataProperty property) {
        write(escapeName(shortFormProvider.getShortForm(property)));
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        accept(axiom.getProperty()).writeSpace().write("(").accept(axiom.getSubject()).writeSpace()
            .accept(axiom.getObject()).write(")");
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeRestriction(SOME, axiom.getProperty(), OWL2Datatype.RDFS_LITERAL.getIRI()).subClass()
            .accept(axiom.getDomain());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        top().subClass().writeRestriction(ALL, axiom.getProperty(), axiom.getRange());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(SOME, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDatatype node) {
        write(getRendering(node));
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        accept(axiom.getDatatype()).write(EQUIV).accept(axiom.getDataRange());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write("(").accept(node.getDatatype()).write(": ")
            .iterate(node.facetRestrictions().iterator(), () -> write(" and ")).write(")");
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        iterate(node.getOperandsAsList().iterator(), () -> spaced(OR));
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        write("Declaration").accept(axiom.getEntity());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        iterate(axiom.individuals().iterator(), this::braced, () -> spaced(NOT_EQUIV));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = axiom.getOperandsAsList();
        if (classExpressions.size() != 2) {
            renderDisjointLongList(classExpressions);
        } else {
            if (classExpressions.get(0).equals(subject)) {
                accept(classExpressions.get(0)).subClass().not().writeSpace().accept(classExpressions.get(1));
            } else {
                accept(classExpressions.get(1)).subClass().not().writeSpace().accept(classExpressions.get(0));
            }
        }
    }

    protected LatexObjectVisitor renderDisjointLongList(List<OWLClassExpression> classExpressions) {
        for (OWLClassExpression left : classExpressions) {
            for (OWLClassExpression right : classExpressions) {
                if (left != right) {
                    if (left.equals(subject)) {
                        accept(left).subClass().not().writeSpace().accept(right);
                    } else {
                        accept(right).subClass().not().writeSpace().accept(left);
                    }
                    writer.writeNewLine();
                }
            }
        }
        return this;
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        iterate(axiom.properties().iterator(), () -> spaced(NOT_EQUIV));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write("Disjoint").write("(").iterate(axiom.properties().iterator()).write(")");
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        // DO OTHER AXIOM HERE!
        write("DisjointClasses").write("(").iterate(axiom.classExpressions().iterator(), this::writeSpace).write(")");
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = axiom.getOperandsAsList();
        if (classExpressions.size() > 2) {
            renderLongList(classExpressions);
        } else if (classExpressions.size() == 2) {
            if (subject.equals(classExpressions.get(0))) {
                accept(classExpressions.get(0)).spaced(EQUIV).accept(classExpressions.get(1));
            } else {
                accept(classExpressions.get(1)).spaced(EQUIV).accept(classExpressions.get(0));
            }
        }
    }

    protected LatexObjectVisitor renderLongList(List<OWLClassExpression> classExpressions) {
        Set<Set<OWLClassExpression>> rendered = new HashSet<>();
        for (OWLClassExpression left : classExpressions) {
            for (OWLClassExpression right : classExpressions) {
                if (left != right) {
                    Set<OWLClassExpression> cur = CollectionFactory.createSet(left, right);
                    if (rendered.add(cur)) {
                        accept(left).spaced(EQUIV).accept(right);
                    }
                }
            }
        }
        return this;
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        iterate(axiom.properties().iterator(), () -> spaced(NOT_EQUIV));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        iterate(axiom.properties().iterator(), () -> spaced(EQUIV));
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
        accept(node.getFacetValue());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        top().subClass().writeCardinality(MAX, 1, axiom.getProperty(), OWL2Datatype.RDFS_LITERAL.getIRI());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        top().subClass().writeCardinality(MAX, 1, axiom.getProperty(), OWLRDFVocabulary.OWL_THING.getIRI());
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        write("HasKey").rounded(axiom.getClassExpression()).write(" = ").writeOpenBrace()
            .iterate(axiom.propertyExpressions().iterator()).writeCloseBrace();
    }

    /**
     * @param axiom
     *        the axiom
     */
    // XXX
    public void visit(OWLImportsDeclaration axiom) {
        write("ImportsDeclaration").accept(axiom.getIRI());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        top().subClass();
        OWLObjectPropertyExpression property = axiom.getProperty();
        if (property.isAnonymous()) {
            writeCardinality(MAX, 1, property, OWLRDFVocabulary.OWL_THING.getIRI());
        } else {
            writeCardinality(MAX, 1, property.asOWLObjectProperty().getInverseProperty(), INVERSE,
                OWLRDFVocabulary.OWL_THING.getIRI());
        }
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        accept(axiom.getFirstProperty()).spaced(EQUIV).accept(axiom.getSecondProperty()).write(INVERSE);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        write("IrreflexiveObjectProperty").rounded(axiom.getProperty());
    }

    @Override
    public void visit(OWLLiteral node) {
        writeOpenBrace().write("``").write(node.getLiteral()).write("\"\\^{}\\^{}")
            .write(getRendering(node.getDatatype())).writeCloseBrace();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write(escapeName(shortFormProvider.getShortForm(individual)));
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        not().prefixedDouble(axiom.getProperty(), axiom.getSubject(), axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        not().prefixedDouble(axiom.getProperty(), axiom.getSubject(), axiom.getObject());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(ALL, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        not().writeNested(ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeCardinality(EQUAL, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(SOME).accept(ce.getProperty()).dot().write(SELF);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        write(SOME).accept(ce.getProperty()).writeSpace().braced(ce.getFiller());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        iterate(ce.getOperandsAsList().iterator(), () -> spaced(AND));
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        accept(property.getInverse()).write(INVERSE);
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeCardinality(MAX, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeCardinality(MIN, ce.getCardinality(), ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        iterate(ce.individuals().iterator(), this::braced, () -> spaced(OR));
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write(escapeName(shortFormProvider.getShortForm(property)));
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        prefixedDouble(axiom.getProperty(), axiom.getSubject(), axiom.getObject());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeRestriction(SOME, axiom.getProperty(), OWLRDFVocabulary.OWL_THING.getIRI()).subClass()
            .accept(axiom.getDomain());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        top().subClass().writeRestriction(ALL, axiom.getProperty(), OWLRDFVocabulary.OWL_THING.getIRI());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(SOME, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        iterate(ce.getOperandsAsList().iterator(), () -> spaced(OR));
    }

    @Override
    public void visit(OWLOntology ontology) {
        // nothing to do here
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        write("ReflexiveProperty").rounded(axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        iterate(axiom.individuals().iterator(), x -> writeOpenBrace().accept(x).writeCloseBrace(), () -> write(" = "));
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        accept(axiom.getSubProperty()).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        setPrettyPrint(false);
        accept(axiom.getSubClass()).subClass().accept(axiom.getSuperClass()).writeSpace();
        setPrettyPrint(true);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        accept(axiom.getSubProperty()).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        accept(axiom.getSubProperty()).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        iterate(axiom.getPropertyChain().iterator(), () -> spaced(CIRC)).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        accept(axiom.getProperty()).spaced(EQUIV).accept(axiom.getProperty()).write(INVERSE);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        write("TransitiveProperty").rounded(axiom.getProperty());
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        accept(node.getPredicate()).writeSpace().iterate(node.argumentsAsList().iterator(), this::writeSpace);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        accept(node.getArgument());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        accept(node.getPredicate());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        accept(node.getPredicate());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        iterate(node.allArguments().iterator(), this::writeSpace);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        accept(node.getLiteral());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        accept(node.getPredicate());
    }

    @Override
    public void visit(SWRLRule rule) {
        write("SWRLRule");
        rule.head().forEach(this::accept);
        write("\\rightarrow");
        rule.body().forEach(this::accept);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        iterate(node.allArguments().iterator(), this::writeSpace);
    }

    @Override
    public void visit(SWRLVariable node) {
        accept(node.getIRI());
    }

    private LatexObjectVisitor write(int i) {
        writer.write(Integer.toString(i));
        return this;
    }

    private LatexObjectVisitor write(String o) {
        writer.write(o);
        return this;
    }

    protected LatexObjectVisitor writeCardinality(String s, int card, OWLObject p, OWLObject f) {
        return write(s).write(card).writeSpace().accept(p).dot().writeNested(f);
    }

    protected LatexObjectVisitor writeCardinality(String s, int card, OWLObject p, String suffix, OWLObject f) {
        return write(s).write(card).writeSpace().accept(p).write(suffix).dot().writeNested(f);
    }

    private LatexObjectVisitor writeCloseBrace() {
        writer.writeCloseBrace();
        return this;
    }

    private LatexObjectVisitor writeNested(OWLClassExpression classExpression) {
        return openBracket(classExpression).accept(classExpression).closeBracket(classExpression);
    }

    private LatexObjectVisitor writeNested(OWLObject expression) {
        return accept(expression);
    }

    private LatexObjectVisitor writeOpenBrace() {
        writer.writeOpenBrace();
        return this;
    }

    protected LatexObjectVisitor writeRestriction(String s, OWLObject p, OWLObject f) {
        return write(s).accept(p).dot().writeNested(f);
    }

    private LatexObjectVisitor writeSpace() {
        writer.writeSpace();
        return this;
    }
}
