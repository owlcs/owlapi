/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, University of Manchester
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
package org.coode.owlapi.latex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 15-Jun-2006<br>
 * <br>
 * 
 * NOTE: this class was not designed as a general purpose renderer, i.e., 
 * some ontologies might be misrepresented in the output. Please report 
 * any formatting error you find to the bug tracker or the mailing list.
 */
@SuppressWarnings("unused")
public class LatexObjectVisitor implements OWLObjectVisitor {
	public static final String AND = "\\ensuremath{\\sqcap}";
	public static final String OR = "\\ensuremath{\\sqcup}";
	public static final String NOT = "\\ensuremath{\\lnot}";
	public static final String ALL = "\\ensuremath{\\forall}";
	public static final String SOME = "\\ensuremath{\\exists}";
	public static final String HASVALUE = "\\ensuremath{hasValue}";
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

	public void visit(OWLDataAllValuesFrom node) {
		write(ALL);
		writeSpace();
		node.getProperty().accept(this);
		writeSpace();
		node.getFiller().accept(this);
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
		write(HASVALUE);
		writeSpace();
		node.getProperty().accept(this);
		writeSpace();
		node.getValue().accept(this);
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

	public void visit(OWLClass node) {
		write(escapeName(shortFormProvider.getShortForm(node)));
	}

	public void visit(OWLObjectOneOf node) {
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

	public void visit(OWLEquivalentClassesAxiom axiom) {
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
		write("AsymmetricProperty");
		axiom.getProperty().accept(this);
	}

	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		axiom.getProperty().accept(this);
		writeSpace();
		write("(");
		axiom.getSubject().accept(this);
		writeSpace();
		axiom.getObject().accept(this);
		write(")");
	}

	public void visit(OWLDataPropertyDomainAxiom axiom) {
		df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype())
				.accept(this);
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		axiom.getDomain().accept(this);
	}

	public void visit(OWLDataPropertyRangeAxiom axiom) {
		write(TOP);
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		df.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange())
				.accept(this);
	}

	public void visit(OWLSubDataPropertyOfAxiom axiom) {
		axiom.getSubProperty();
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		axiom.getSuperProperty().accept(this);
	}

	public void visit(OWLDeclarationAxiom axiom) {
		write("Declaration");
		axiom.getEntity().accept(this);
	}

	public void visit(OWLDifferentIndividualsAxiom axiom) {
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

	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
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

	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		write("DisjointObjectProperties");
		writeSpace();
		for (OWLObjectPropertyExpression p : axiom.getProperties()) {
			p.accept(this);
			writeSpace();
		}
	}

	public void visit(OWLDisjointUnionAxiom axiom) {
		write("DisjointUnion");
		writeSpace();
		for (OWLClassExpression p : axiom.getClassExpressions()) {
			p.accept(this);
			writeSpace();
		}
	}

	public void visit(OWLAnnotationAssertionAxiom axiom) {
		write("Annotation");
		axiom.getSubject().accept(this);
		writeSpace();
		axiom.getProperty().accept(this);
		writeSpace();
		axiom.getValue().accept(this);
	}

	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
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

	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
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
		write("ImportsDeclaration");
		axiom.getIRI().accept(this);
	}

	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		write(TOP);
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		OWLObjectPropertyExpression prop = df.getOWLObjectInverseOf(axiom
				.getProperty());
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
		write("IrreflexiveObjectProperty");
		axiom.getProperty().accept(this);
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

	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing())
				.accept(this);
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
		df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())
				.accept(this);
	}

	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		axiom.getSubProperty();
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		axiom.getSuperProperty().accept(this);
	}

	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		write("ReflexiveProperty");
		axiom.getProperty().accept(this);
	}

	public void visit(OWLSameIndividualAxiom axiom) {
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

	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		writeSpace();
		write(EQUIV);
		writeSpace();
		axiom.getProperty().accept(this);
		write("\\ensuremath{^-}");
	}

	public void visit(OWLDatatypeDefinitionAxiom axiom) {
		write("Datatype");
		axiom.getDatatype().accept(this);
		write(EQUIV);
		axiom.getDataRange().accept(this);
	}

	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		write("TransitiveProperty");
		axiom.getProperty().accept(this);
	}

	public void visit(SWRLRule rule) {
		write("SWRLRule");
		for (SWRLAtom a : rule.getHead()) {
			a.accept(this);
		}
		write("\\rightarrow");
		for (SWRLAtom a : rule.getBody()) {
			a.accept(this);
		}
	}

	public void visit(SWRLVariable node) {
		write(node.getIRI());
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
		write(NOT);
		writeNested(node.getDataRange());
	}

	public void visit(OWLDataOneOf node) {
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

	public void visit(OWLFacetRestriction node) {
		write("Facet");
		write(node.getFacet());
		node.getFacetValue().accept(this);
	}

	public void visit(OWLDatatypeRestriction node) {
		write("DatatypeRestriction");
		node.getDatatype().accept(this);
		for (OWLFacetRestriction r : node.getFacetRestrictions()) {
			writeSpace();
			r.accept(this);
		}
	}

	public void visit(OWLDatatype node) {
		write("Datatype");
		write(node.getIRI());
	}

	public void visit(OWLLiteral node) {
		write("\"");
		write(node.getLiteral());
		write("\"\\^\\^");
		write(node.getDatatype().getIRI());
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	public void visit(SWRLLiteralArgument node) {
		node.getLiteral().accept(this);
	}

	public void visit(SWRLIndividualArgument node) {
		node.getIndividual().accept(this);
	}

	public void visit(SWRLBuiltInAtom node) {
		node.getPredicate().accept(this);
		for (SWRLDArgument d : node.getArguments()) {
			writeSpace();
			d.accept(this);
		}
	}

	public void visit(SWRLClassAtom node) {
		node.getArgument().accept(this);
	}

	public void visit(SWRLDataRangeAtom node) {
		node.getPredicate().accept(this);
	}

	public void visit(SWRLDataPropertyAtom node) {
		node.getPredicate().accept(this);
	}

	public void visit(SWRLDifferentIndividualsAtom node) {
		for (SWRLArgument a : node.getAllArguments()) {
			writeSpace();
			a.accept(this);
		}
	}

	public void visit(SWRLObjectPropertyAtom node) {
		node.getPredicate().accept(this);
	}

	public void visit(SWRLSameIndividualAtom node) {
		for (SWRLArgument a : node.getAllArguments()) {
			writeSpace();
			a.accept(this);
		}
	}

	public void visit(OWLAnnotationProperty property) {
		write("AnnotationProperty");
		property.getIRI().accept(this);
	}

	public void visit(OWLAnnotation annotation) {
		write("Annotation");
		annotation.getProperty().getIRI().accept(this);
		annotation.getValue().accept(this);
	}

	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
		write("Domain");
		axiom.getProperty().getIRI().accept(this);
		writeSpace();
		axiom.getDomain().accept(this);
	}

	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
		write("Range");
		axiom.getProperty().getIRI().accept(this);
		writeSpace();
		axiom.getRange().accept(this);
	}

	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		axiom.getSubProperty();
		writeSpace();
		write(SUBCLASS);
		writeSpace();
		axiom.getSuperProperty().accept(this);
	}

	public void visit(OWLAnnotationValue value) {
		value.accept(new OWLAnnotationValueVisitor() {
			public void visit(IRI iri) {
				iri.accept(LatexObjectVisitor.this);
			}

			public void visit(OWLAnonymousIndividual individual) {
				individual.accept(LatexObjectVisitor.this);
			}

			public void visit(OWLLiteral literal) {
				literal.accept(LatexObjectVisitor.this);
			}
		});
	}

	public void visit(OWLHasKeyAxiom axiom) {
		write("HasKey");
		axiom.getClassExpression().accept(this);
		for (OWLPropertyExpression<?, ?> p : axiom.getPropertyExpressions()) {
			writeSpace();
			p.accept(this);
		}
	}

	public void visit(OWLDataIntersectionOf node) {
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

	public void visit(OWLDataUnionOf node) {
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

	public void visit(OWLAnonymousIndividual individual) {
		write(individual.getID().toString());
	}

	public void visit(IRI iri) {
		write(iri.getFragment());
	}
}
