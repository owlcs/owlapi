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
 * Copyright 2011, The University of Manchester
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
package uk.ac.manchester.cs.owlapi.dlsyntax;

import static uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntax.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Renders objects in unicode DL syntax.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 10-Feb-2008
 */
public class DLSyntaxObjectRenderer implements OWLObjectRenderer,
        OWLObjectVisitor {

    private ShortFormProvider shortFormProvider;
    private IRIShortFormProvider iriShortFormProvider;
    private StringBuilder buffer;
    private OWLObject focusedObject;

    /** default constructor */
    public DLSyntaxObjectRenderer() {
        shortFormProvider = new SimpleShortFormProvider();
        iriShortFormProvider = new SimpleIRIShortFormProvider();
        buffer = new StringBuilder();
    }

    /**
     * @param focusedObject
     *        focusedObject
     */
    public void setFocusedObject(OWLObject focusedObject) {
        this.focusedObject = focusedObject;
    }

    /**
     * @param obj
     *        obj
     * @return true if focused
     */
    public boolean isFocusedObject(OWLObject obj) {
        if (focusedObject == null) {
            return false;
        }
        return focusedObject.equals(obj);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    @Override
    public String render(OWLObject object) {
        buffer = new StringBuilder();
        object.accept(this);
        return buffer.toString();
    }

    @Override
    public void visit(OWLOntology ontology) {
        for (OWLAxiom ax : new TreeSet<OWLAxiom>(ontology.getLogicalAxioms())) {
            ax.accept(this);
            write("\n");
        }
    }

    protected void write(String s) {
        buffer.append(s);
    }

    protected String renderEntity(OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }

    protected void writeEntity(OWLEntity entity) {
        write(renderEntity(entity));
    }

    protected void write(DLSyntax keyword) {
        write(keyword.toString());
    }

    protected void write(int i) {
        write(Integer.toString(i));
    }

    protected void writeNested(OWLObject object) {
        if (isBracketedIfNested(object)) {
            write("(");
        }
        object.accept(this);
        if (isBracketedIfNested(object)) {
            write(")");
        }
    }

    protected boolean isBracketedIfNested(OWLObject object) {
        // if(object instanceof OWLObjectComplementOf) {
        // if(!((OWLObjectComplementOf) object).getOperand().isAnonymous()) {
        // return false;
        // }
        // }
        // return object instanceof OWLClassExpression && !((OWLClassExpression)
        // object).isClassExpressionLiteral();
        return !(object instanceof OWLEntity);
    }

    private void writeObject(OWLObject object, boolean nest) {
        if (nest) {
            writeNested(object);
        } else {
            object.accept(this);
        }
    }

    protected void write(Collection<? extends OWLObject> objects,
            DLSyntax delim, boolean nest) {
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject o1 = it.next();
            OWLObject o2 = it.next();
            if (isFocusedObject(o1) || !isFocusedObject(o2)) {
                writeObject(o1, nest);
                writeSpace();
                write(delim);
                writeSpace();
                writeObject(o2, nest);
            } else {
                writeObject(o2, nest);
                writeSpace();
                write(delim);
                writeSpace();
                writeObject(o1, nest);
            }
        } else {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it
                    .hasNext();) {
                OWLObject o = it.next();
                writeObject(o, nest);
                if (it.hasNext()) {
                    writeSpace();
                    write(delim);
                    writeSpace();
                }
            }
        }
    }

    // protected void write(Collection<? extends OWLObject> objects, DLSyntax
    // keyword, boolean nest) {
    // write(objects, keyword, nest);
    // }
    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
    }

    private void writePropertyAssertion(OWLPropertyAssertionAxiom<?, ?> ax) {
        if (ax instanceof OWLNegativeObjectPropertyAssertionAxiom
                || ax instanceof OWLNegativeDataPropertyAssertionAxiom) {
            write(NOT);
        }
        ax.getProperty().accept(this);
        write("(");
        ax.getSubject().accept(this);
        write(", ");
        ax.getObject().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        write(" .");
        write(SELF);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> descs = new ArrayList<OWLClassExpression>(
                axiom.getClassExpressions());
        for (int i = 0; i < descs.size(); i++) {
            for (int j = i + 1; j < descs.size(); j++) {
                descs.get(i).accept(this);
                writeSpace();
                write(DISJOINT_WITH);
                writeSpace();
                descs.get(j).accept(this);
                if (j < descs.size() - 1) {
                    write(", ");
                }
            }
        }
        // write(axiom.getClassExpressions(), DISJOINT_WITH, true);
    }

    private void writeDomainAxiom(OWLPropertyDomainAxiom<?> axiom) {
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        writeRestrictionSeparator();
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        writeNested(axiom.getDomain());
    }

    private void writeRestrictionSeparator() {
        write(".");
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeDomainAxiom(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeDomainAxiom(axiom);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        write(axiom.getIndividuals(), NOT_EQUAL, false);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), DISJOINT_WITH, false);
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), DISJOINT_WITH, false);
    }

    private void writeRangeAxiom(OWLPropertyRangeAxiom<?, ?> axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(FORALL);
        writeSpace();
        axiom.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(axiom.getRange());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeRangeAxiom(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }

    private void writeFunctionalProperty(OWLPropertyExpression<?, ?> property) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(MAX);
        writeSpace();
        write(1);
        writeSpace();
        property.accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writeFunctionalProperty(axiom.getProperty());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        write(EQUAL);
        write(axiom.getClassExpressions(), OR, false);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {}

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIVALENT_TO);
        writeSpace();
        axiom.getProperty().accept(this);
        write(INVERSE);
    }

    private void writeSpace() {
        write(" ");
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeRangeAxiom(axiom);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writeFunctionalProperty(axiom.getProperty());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            write("(");
        }
        axiom.getClassExpression().accept(this);
        if (axiom.getClassExpression().isAnonymous()) {
            write(")");
        }
        write("(");
        axiom.getIndividual().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        write(axiom.getClassExpressions(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(IN);
        writeSpace();
        write("R");
        write("\u207A");
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(NOT);
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        write(" .");
        write(SELF);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        write(SUBCLASS);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(MAX);
        writeSpace();
        write(1);
        writeSpace();
        axiom.getProperty().accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        write(axiom.getIndividuals(), EQUAL, false);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write(axiom.getPropertyChain(), COMP, false);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        OWLObject o1 = axiom.getFirstProperty();
        OWLObject o2 = axiom.getSecondProperty();
        OWLObject first, second;
        if (isFocusedObject(o1) || !isFocusedObject(o2)) {
            first = o1;
            second = o2;
        } else {
            first = o2;
            second = o1;
        }
        first.accept(this);
        writeSpace();
        write(EQUIVALENT_TO);
        writeSpace();
        second.accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(SWRLRule rule) {
        write(rule.getHead(), WEDGE, false);
        writeSpace();
        write(IMPLIES);
        writeSpace();
        write(rule.getBody(), WEDGE, false);
    }

    @Override
    public void visit(OWLClass desc) {
        if (desc.isOWLThing()) {
            write(TOP);
        } else if (desc.isOWLNothing()) {
            write(BOTTOM);
        } else {
            writeEntity(desc);
        }
    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        write(desc.getOperands(), AND, true);
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        write(desc.getOperands(), OR, true);
    }

    @Override
    public void visit(OWLObjectComplementOf desc) {
        write(NOT);
        writeNested(desc.getOperand());
    }

    private void writeCardinalityRestriction(
            OWLDataCardinalityRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        // if (restriction.isQualified()) {
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
        // }
    }

    private void writeCardinalityRestriction(
            OWLObjectCardinalityRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        // if (restriction.isQualified()) {
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
        // }
    }

    private void writeQuantifiedRestriction(
            OWLQuantifiedDataRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeQuantifiedRestriction(
            OWLQuantifiedObjectRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        writeQuantifiedRestriction(desc, EXISTS);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        writeQuantifiedRestriction(desc, FORALL);
    }

    private
            <R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, V extends OWLObject>
            void writeValueRestriction(
                    OWLHasValueRestriction<R, P, V> restriction) {
        write(EXISTS);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        write("{");
        restriction.getValue().accept(this);
        write("}");
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        writeValueRestriction(desc);
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        writeCardinalityRestriction(desc, MIN);
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        writeCardinalityRestriction(desc, EQUAL);
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        writeCardinalityRestriction(desc, MAX);
    }

    @Override
    public void visit(OWLObjectHasSelf desc) {
        write(EXISTS);
        writeSpace();
        desc.getProperty().accept(this);
        write(" .");
        write(SELF);
    }

    @Override
    public void visit(OWLObjectOneOf desc) {
        for (Iterator<OWLIndividual> it = desc.getIndividuals().iterator(); it
                .hasNext();) {
            write("{");
            it.next().accept(this);
            write("}");
            if (it.hasNext()) {
                write(" ");
                write(OR);
                write(" ");
            }
        }
    }

    @Override
    public void visit(OWLDataSomeValuesFrom desc) {
        writeQuantifiedRestriction(desc, EXISTS);
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        writeQuantifiedRestriction(desc, FORALL);
    }

    @Override
    public void visit(OWLDataHasValue desc) {
        writeValueRestriction(desc);
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
        writeCardinalityRestriction(desc, MIN);
    }

    @Override
    public void visit(OWLDataExactCardinality desc) {
        writeCardinalityRestriction(desc, EQUAL);
    }

    @Override
    public void visit(OWLDataMaxCardinality desc) {
        writeCardinalityRestriction(desc, MAX);
    }

    @Override
    public void visit(OWLDatatype node) {
        write(shortFormProvider.getShortForm(node));
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(NOT);
        node.getDataRange().accept(this);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        for (Iterator<OWLLiteral> it = node.getValues().iterator(); it
                .hasNext();) {
            write("{");
            it.next().accept(this);
            write("}");
            if (it.hasNext()) {
                write(OR);
            }
        }
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {}

    @Override
    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    @Override
    public void visit(OWLFacetRestriction node) {}

    @Override
    public void visit(OWLObjectProperty property) {
        writeEntity(property);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write(INVERSE);
    }

    @Override
    public void visit(OWLDataProperty property) {
        writeEntity(property);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        writeEntity(individual);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {}

    @Override
    public void visit(OWLHasKeyAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {}

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {}

    @Override
    public void visit(OWLDataIntersectionOf node) {}

    @Override
    public void visit(OWLDataUnionOf node) {}

    @Override
    public void visit(OWLAnnotationProperty property) {}

    @Override
    public void visit(OWLAnonymousIndividual individual) {}

    @Override
    public void visit(IRI iri) {}

    @Override
    public void visit(OWLAnnotation node) {}

    @Override
    public void visit(SWRLClassAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        write(node.getPredicate().toString());
        write("(");
        write(node.getArguments(), COMMA, true);
        write(")");
    }

    @Override
    public void visit(SWRLVariable node) {
        write("?");
        write(iriShortFormProvider.getShortForm(node.getIRI()));
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write("sameAs(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write("differentFrom(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }
}
