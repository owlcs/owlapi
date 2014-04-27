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
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasValueRestriction;
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
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Renders objects in unicode DL syntax.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class DLSyntaxObjectRenderer extends OWLObjectVisitorAdapter implements
        OWLObjectRenderer, OWLObjectVisitor {

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
    public void setFocusedObject(@Nonnull OWLObject focusedObject) {
        this.focusedObject = checkNotNull(focusedObject,
                "focusedObject cannot be null");
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
        this.shortFormProvider = checkNotNull(shortFormProvider,
                "shortFormProvider cannot be null");
    }

    @SuppressWarnings("null")
    @Nonnull
    @Override
    public String render(OWLObject object) {
        buffer = new StringBuilder();
        checkNotNull(object, "object cannot be null").accept(this);
        return buffer.toString();
    }

    @Override
    public void visit(OWLOntology ontology) {
        for (OWLAxiom ax : new TreeSet<OWLAxiom>(checkNotNull(ontology,
                "ontology cannot be null").getLogicalAxioms())) {
            ax.accept(this);
            write("\n");
        }
    }

    protected void write(@Nonnull String s) {
        buffer.append(checkNotNull(s, "s cannot be null"));
    }

    @Nonnull
    protected String renderEntity(@Nonnull OWLEntity entity) {
        return shortFormProvider.getShortForm(checkNotNull(entity,
                "entity cannot be null"));
    }

    protected void writeEntity(@Nonnull OWLEntity entity) {
        write(renderEntity(checkNotNull(entity, "entity cannot be null")));
    }

    @SuppressWarnings("null")
    protected void write(@Nonnull DLSyntax keyword) {
        write(checkNotNull(keyword, "keyword cannot be null").toString());
    }

    @SuppressWarnings("null")
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

    protected boolean isBracketedIfNested(@Nonnull OWLObject object) {
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

    protected void write(@Nonnull Collection<? extends OWLObject> objects,
            @Nonnull DLSyntax delim, boolean nest) {
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
            for (Iterator<? extends OWLObject> it = objects.iterator(); it
                    .hasNext();) {
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

    private void writePropertyAssertion(
            @Nonnull OWLPropertyAssertionAxiom<?, ?> ax) {
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

    private void
            writeFunctionalProperty(@Nonnull OWLPropertyExpression property) {
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

    private void
            writeCardinalityRestriction(
                    OWLDataCardinalityRestriction restriction,
                    @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeCardinalityRestriction(
            OWLObjectCardinalityRestriction restriction,
            @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void
            writeQuantifiedRestriction(
                    OWLQuantifiedDataRestriction restriction,
                    @Nonnull DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeQuantifiedRestriction(
            OWLQuantifiedObjectRestriction restriction,
            @Nonnull DLSyntax keyword) {
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

    private <V extends OWLObject> void writeValueRestriction(
            OWLHasValueRestriction<V> restriction, OWLPropertyExpression p) {
        write(EXISTS);
        writeSpace();
        p.accept(this);
        writeRestrictionSeparator();
        write("{");
        restriction.getFiller().accept(this);
        write("}");
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        writeValueRestriction(desc, desc.getProperty());
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
        writeValueRestriction(desc, desc.getProperty());
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
    public void visit(@SuppressWarnings("unused") OWLDatatypeRestriction node) {}

    @Override
    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLFacetRestriction node) {}

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
}
