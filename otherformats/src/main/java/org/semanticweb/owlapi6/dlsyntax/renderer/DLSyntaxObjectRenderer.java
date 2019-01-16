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
package org.semanticweb.owlapi6.dlsyntax.renderer;

import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.AND;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.BOTTOM;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.COMMA;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.COMP;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.DISJOINT_WITH;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.EQUAL;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.EQUIVALENT_TO;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.EXISTS;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.FORALL;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.IMPLIES;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.IN;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.INVERSE;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.MAX;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.MIN;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.NOT;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.NOT_EQUAL;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.OR;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.SELF;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.SUBCLASS;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.TOP;
import static org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntax.WEDGE;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.annotations.Renders;
import org.semanticweb.owlapi6.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi6.io.OWLObjectRenderer;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.IsAnonymous;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
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
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasValueRestriction;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLLogicalAxiom;
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
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLPropertyRange;
import org.semanticweb.owlapi6.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.PrefixManager;
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
import org.semanticweb.owlapi6.utilities.IRIShortFormProvider;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi6.utility.SimpleShortFormProvider;

/**
 * Renders objects in unicode DL syntax.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
@Renders(DLSyntaxDocumentFormat.class)
public class DLSyntaxObjectRenderer implements OWLObjectRenderer, OWLObjectVisitor {

    private static final String OBJECT_CANNOT_BE_NULL = "object cannot be null";
    private final IRIShortFormProvider iriShortFormProvider = new SimpleIRIShortFormProvider();
    protected ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
    private StringBuilder buffer = new StringBuilder();
    @Nullable private OWLObject focusedObject;

    protected static boolean isBracketedIfNested(OWLObject object) {
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        return !(object instanceof OWLEntity);
    }

    private DLSyntaxObjectRenderer accept(OWLObject o) {
        o.accept(this);
        return this;
    }

    /**
     * @param focusedObject
     *        focusedObject
     */
    public void setFocusedObject(@Nullable OWLObject focusedObject) {
        this.focusedObject = focusedObject;
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
        return verifyNotNull(focusedObject).equals(obj);
    }

    @Override
    public void setPrefixManager(PrefixManager shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider, "shortFormProvider cannot be null");
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = checkNotNull(shortFormProvider, "shortFormProvider cannot be null");
    }

    @Override
    public String render(OWLObject object) {
        buffer = new StringBuilder();
        accept(checkNotNull(object, OBJECT_CANNOT_BE_NULL));
        return buffer.toString();
    }

    protected void println(OWLLogicalAxiom ax) {
        accept(ax).write("\n");
    }

    protected DLSyntaxObjectRenderer write(String s) {
        buffer.append(checkNotNull(s, "s cannot be null"));
        return this;
    }

    protected DLSyntaxObjectRenderer writeEntity(OWLEntity entity) {
        return write(shortFormProvider.getShortForm(checkNotNull(entity, "entity cannot be null")));
    }

    protected DLSyntaxObjectRenderer write(DLSyntax keyword) {
        return write(checkNotNull(keyword, "keyword cannot be null").toString());
    }

    protected DLSyntaxObjectRenderer write(int i) {
        return write(Integer.toString(i));
    }

    private DLSyntaxObjectRenderer writeObject(OWLObject object, boolean nest) {
        checkNotNull(object, OBJECT_CANNOT_BE_NULL);
        if (nest) {
            return roundedAnon(object);
        }
        return accept(object);
    }

    private DLSyntaxObjectRenderer writeRestrictionSeparator() {
        return write(".");
    }

    protected DLSyntaxObjectRenderer write(Collection<? extends OWLObject> objects, DLSyntax delim, boolean nest) {
        checkNotNull(objects, "objects cannot be null");
        checkNotNull(delim, "delim cannot be null");
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject o1 = it.next();
            OWLObject o2 = it.next();
            if (isFocusedObject(o1) || !isFocusedObject(o2)) {
                writeObject(o1, nest);
                spaced(delim);
                writeObject(o2, nest);
            } else {
                writeObject(o2, nest);
                spaced(delim);
                writeObject(o1, nest);
            }
        } else {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
                writeObject(it.next(), nest);
                if (it.hasNext()) {
                    spaced(delim);
                }
            }
        }
        return this;
    }

    protected void spaced(DLSyntax delim) {
        writeSpace();
        write(delim);
        writeSpace();
    }

    protected void braced(DLSyntax delim) {
        write("{");
        write(delim);
        write("}");
    }

    protected void braced(OWLObject o) {
        write("{");
        accept(o).write("}");
    }

    private void writePropertyAssertion(OWLPropertyAssertionAxiom<?, ?> ax) {
        checkNotNull(ax, "ax cannot be null");
        if (ax instanceof OWLNegativeObjectPropertyAssertionAxiom
            || ax instanceof OWLNegativeDataPropertyAssertionAxiom) {
            write(NOT);
        }
        triplet(ax.getProperty(), ax.getSubject(), ax.getObject());
    }

    protected void triplet(OWLObject a, OWLObject o1, OWLObject o2) {
        accept(a).write("(").accept(o1).write(", ").accept(o2).write(")");
    }

    protected void triplet(String a, OWLObject o1, OWLObject o2) {
        write(a).write("(").accept(o1).write(", ").accept(o2).write(")");
    }

    protected DLSyntaxObjectRenderer subClass() {
        return writeSpace().write(SUBCLASS).writeSpace();
    }

    private void writeDomainAxiom(OWLPropertyDomainAxiom<?> axiom) {
        write(EXISTS).writeSpace().accept(axiom.getProperty()).writeRestrictionSeparator().write(TOP).subClass()
            .roundedAnon(axiom.getDomain());
    }

    private void writeRangeAxiom(OWLPropertyRangeAxiom<?, ?> axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        write(TOP).subClass().write(FORALL).writeSpace().accept(axiom.getProperty()).writeRestrictionSeparator()
            .roundedAnon(axiom.getRange());
    }

    private void writeFunctionalProperty(OWLPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        write(TOP).subClass().write(MAX).writeSpace().write(1).writeSpace().accept(property);
    }

    private DLSyntaxObjectRenderer writeSpace() {
        return write(" ");
    }

    protected DLSyntaxObjectRenderer rounded(OWLObject o) {
        return write("(").accept(o).write(")");
    }

    protected <T extends OWLObject & IsAnonymous> DLSyntaxObjectRenderer roundedAnon(T o) {
        if (o.isAnonymous()) {
            return rounded(o);
        }
        return accept(o);
    }

    private void equivalent(OWLObject first, OWLObject second) {
        accept(first).writeSpace().write(EQUIVALENT_TO).writeSpace().accept(second).write(INVERSE);
    }

    private <T extends OWLPropertyRange> void writeCardinalityRestriction(OWLCardinalityRestriction<T> restriction,
        DLSyntax keyword) {
        write(keyword).writeSpace().write(restriction.getCardinality()).writeSpace().accept(restriction.getProperty())
            .writeRestrictionSeparator().roundedAnon(restriction.getFiller());
    }

    private <T extends OWLPropertyRange> void writeQuantifiedRestriction(OWLQuantifiedRestriction<T> restriction,
        DLSyntax keyword) {
        write(keyword).writeSpace().accept(restriction.getProperty()).writeRestrictionSeparator()
            .roundedAnon(restriction.getFiller());
    }

    private <V extends OWLObject> void writeValueRestriction(OWLHasValueRestriction<V> restriction,
        OWLPropertyExpression p) {
        write(EXISTS).writeSpace().accept(p).writeRestrictionSeparator().braced(restriction.getFiller());
    }

    protected void existPropertySelf(OWLObject o) {
        write(EXISTS).writeSpace().accept(o).writeRestrictionSeparator().write(SELF);
    }

    protected void iterate(List<? extends OWLObject> it) {
        for (int i = 0; i < it.size(); i++) {
            if (i > 0) {
                spaced(OR);
            }
            braced(it.get(i));
        }
    }

    protected void duet(OWLObject a, OWLObject o) {
        duet("", a, o);
    }

    protected void duet(String prefix, OWLObject a, OWLObject o) {
        write(prefix).accept(a).rounded(o);
    }

    @Override
    public void visit(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        ontology.logicalAxioms().sorted().forEach(this::println);
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        accept(axiom.getSubClass()).subClass().accept(axiom.getSuperClass());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT).writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        write(TOP).subClass().existPropertySelf(axiom.getProperty());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> descs = axiom.getOperandsAsList();
        for (int i = 0; i < descs.size() - 1; i++) {
            for (int j = i + 1; j < descs.size(); j++) {
                accept(descs.get(i)).writeSpace().write(DISJOINT_WITH).writeSpace().accept(descs.get(j));
                if (j < descs.size() - 1) {
                    write(", ");
                }
            }
            if (i < descs.size() - 2) {
                write(", ");
            }
        }
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
        write(axiom.getOperandsAsList(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT).writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        write(axiom.getOperandsAsList(), NOT_EQUAL, false);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.getOperandsAsList(), DISJOINT_WITH, false);
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write(axiom.getOperandsAsList(), DISJOINT_WITH, false);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeRangeAxiom(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writeFunctionalProperty(axiom.getProperty());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        accept(axiom.getSubProperty()).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        accept(axiom.getOWLClass()).write(EQUAL).write(axiom.getOperandsAsList(), OR, false);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        equivalent(axiom.getProperty(), axiom.getProperty());
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
        write(axiom.getOperandsAsList(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        roundedAnon(axiom.getClassExpression()).rounded(axiom.getIndividual());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        write(axiom.getOperandsAsList(), EQUIVALENT_TO, false);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        accept(axiom.getProperty()).writeSpace().write(IN).writeSpace().write("R").write("\u207A");
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        write(TOP).subClass().write(NOT).existPropertySelf(axiom.getProperty());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        accept(axiom.getProperty()).writeSpace().write(DISJOINT_WITH).writeSpace().accept(axiom.getProperty())
            .write(INVERSE);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        accept(axiom.getSubProperty()).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP).subClass().write(MAX).writeSpace().write(1).writeSpace().accept(axiom.getProperty()).write(INVERSE);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        write(axiom.getOperandsAsList(), EQUAL, false);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write(axiom.getPropertyChain(), COMP, false).subClass().accept(axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        if (isFocusedObject(axiom.getFirstProperty()) || !isFocusedObject(axiom.getSecondProperty())) {
            equivalent(axiom.getFirstProperty(), axiom.getSecondProperty());
        } else {
            equivalent(axiom.getSecondProperty(), axiom.getFirstProperty());
        }
    }

    @Override
    public void visit(SWRLRule rule) {
        write(rule.headList(), WEDGE, false).writeSpace().write(IMPLIES).writeSpace().write(rule.bodyList(), WEDGE,
            false);
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
        write(ce.getOperandsAsList(), AND, true);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        write(ce.getOperandsAsList(), OR, true);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(NOT).roundedAnon(ce.getOperand());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeQuantifiedRestriction(ce, EXISTS);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeQuantifiedRestriction(ce, FORALL);
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
        existPropertySelf(ce.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        iterate(ce.getOperandsAsList());
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
        writeEntity(node);
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(NOT).accept(node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        iterate(node.getOperandsAsList());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        // XXX complete
    }

    @Override
    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        // XXX complete
    }

    @Override
    public void visit(OWLObjectProperty property) {
        writeEntity(property);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        accept(property.getInverse()).write(INVERSE);
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
        duet(node.getPredicate(), node.getArgument());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        duet(node.getPredicate(), node.getArgument());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        triplet(node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        triplet(node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(IRI iri) {
        write(iriShortFormProvider.getShortForm(iri));
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        accept(node.getPredicate()).write("(").write(node.argumentsAsList(), COMMA, true).write(")");
    }

    @Override
    public void visit(SWRLVariable node) {
        write("?").accept(node.getIRI());
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
    public void visit(SWRLSameIndividualAtom node) {
        triplet("sameAs", node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        triplet("differentFrom", node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        write(node.getOperandsAsList(), AND, true);
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        write(node.getOperandsAsList(), OR, true);
    }
}
