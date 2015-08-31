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
package org.semanticweb.owlapi.dlsyntax.renderer;

import static org.semanticweb.owlapi.dlsyntax.renderer.DLSyntax.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.*;

/**
 * Renders objects in unicode DL syntax.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class DLSyntaxObjectRenderer extends OWLObjectVisitorAdapter implements OWLObjectRenderer, OWLObjectVisitor {

    private ShortFormProvider shortFormProvider;
    private final IRIShortFormProvider iriShortFormProvider;
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
    public void setFocusedObject(@Nonnull OWLObject focusedObject) {
        this.focusedObject = checkNotNull(focusedObject, "focusedObject cannot be null");
    }

    /**
     * @param obj
     *        obj
     * @return true if obj is equal to focusedObject
     */
    public boolean isFocusedObject(OWLObject obj) {
        if (focusedObject == null) {
            return false;
        }
        return focusedObject.equals(obj);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider, "shortFormProvider cannot be null");
    }

    @Nonnull
    @Override
    public String render(OWLObject object) {
        buffer = new StringBuilder();
        checkNotNull(object, "object cannot be null").accept(this);
        return buffer.toString();
    }

    @Override
    public void visit(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        for (OWLAxiom ax : CollectionFactory.sortOptionally(ontology.getLogicalAxioms())) {
            ax.accept(this);
            write("\n");
        }
    }

    protected void write(@Nonnull String s) {
        buffer.append(checkNotNull(s, "s cannot be null"));
    }

    @Nonnull
    protected String renderEntity(@Nonnull OWLEntity entity) {
        return shortFormProvider.getShortForm(checkNotNull(entity, "entity cannot be null"));
    }

    protected void writeEntity(@Nonnull OWLEntity entity) {
        write(renderEntity(checkNotNull(entity, "entity cannot be null")));
    }

    protected void write(@Nonnull DLSyntax keyword) {
        write(checkNotNull(keyword, "keyword cannot be null").toString());
    }

    protected void write(int i) {
        write(Integer.toString(i));
    }

    protected void writeNested(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        if (isBracketedIfNested(object)) {
            write("(");
        }
        object.accept(this);
        if (isBracketedIfNested(object)) {
            write(")");
        }
    }

    protected static boolean isBracketedIfNested(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        return !(object instanceof OWLEntity);
    }

    private void writeObject(@Nonnull OWLObject object, boolean nest) {
        checkNotNull(object, "object cannot be null");
        if (nest) {
            writeNested(object);
        } else {
            object.accept(this);
        }
    }

    protected void write(@Nonnull Collection<? extends OWLObject> objects, @Nonnull DLSyntax delim, boolean nest) {
        checkNotNull(objects, "objects cannot be null");
        checkNotNull(delim, "delim cannot be null");
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject o1 = it.next();
            OWLObject o2 = it.next();
            assert o1 != null;
            assert o2 != null;
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
            for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
                OWLObject o = it.next();
                assert o != null;
                writeObject(o, nest);
                if (it.hasNext()) {
                    writeSpace();
                    write(delim);
                    writeSpace();
                }
            }
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null").getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
    }

    private void writePropertyAssertion(@Nonnull OWLPropertyAssertionAxiom<?, ?> ax) {
        checkNotNull(ax, "ax cannot be null");
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
        List<OWLClassExpression> descs = new ArrayList<>(axiom.getClassExpressions());
        for (int i = 0; i < descs.size() - 1; i++) {
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
            if (i < descs.size() - 2) {
                write(", ");
            }
        }
    }

    private void writeDomainAxiom(@Nonnull OWLPropertyDomainAxiom<?> axiom) {
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

    private void writeRangeAxiom(@Nonnull OWLPropertyRangeAxiom<?, ?> axiom) {
        checkNotNull(axiom, "axiom cannot be null");
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

    private void writeFunctionalProperty(@Nonnull OWLPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
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
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(DISJOINT_WITH);
        writeSpace();
        axiom.getProperty().accept(this);
        write(INVERSE);
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
        OWLObject first;
        OWLObject second;
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
    public void visit(OWLClass ce) {
        if (ce.isOWLThing()) {
            write(TOP);
        } else if (ce.isOWLNothing()) {
            write(BOTTOM);
        } else {
            writeEntity(ce);
        }
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        write(ce.getOperands(), AND, true);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        write(ce.getOperands(), OR, true);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(NOT);
        writeNested(ce.getOperand());
    }

    private void writeCardinalityRestriction(OWLDataCardinalityRestriction restriction, @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeCardinalityRestriction(OWLObjectCardinalityRestriction restriction, @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeQuantifiedRestriction(OWLQuantifiedDataRestriction restriction, @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeQuantifiedRestriction(OWLQuantifiedObjectRestriction restriction, @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeQuantifiedRestriction(ce, EXISTS);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeQuantifiedRestriction(ce, FORALL);
    }

    private <V extends OWLObject> void writeValueRestriction(OWLHasValueRestriction<V> restriction,
        OWLPropertyExpression p) {
        write(EXISTS);
        writeSpace();
        p.accept(this);
        writeRestrictionSeparator();
        write("{");
        restriction.getFiller().accept(this);
        write("}");
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writeValueRestriction(ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeCardinalityRestriction(ce, MIN);
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeCardinalityRestriction(ce, EQUAL);
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeCardinalityRestriction(ce, MAX);
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(EXISTS);
        writeSpace();
        ce.getProperty().accept(this);
        write(" .");
        write(SELF);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        for (Iterator<OWLIndividual> it = ce.getIndividuals().iterator(); it.hasNext();) {
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
    public void visit(OWLDataSomeValuesFrom ce) {
        writeQuantifiedRestriction(ce, EXISTS);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeQuantifiedRestriction(ce, FORALL);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeValueRestriction(ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeCardinalityRestriction(ce, MIN);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeCardinalityRestriction(ce, EQUAL);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeCardinalityRestriction(ce, MAX);
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
        for (Iterator<OWLLiteral> it = node.getValues().iterator(); it.hasNext();) {
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

    @Override
    public void visit(OWLDataIntersectionOf node) {
        write(node.getOperands(), AND, true);
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        write(node.getOperands(), OR, true);
    }
}
