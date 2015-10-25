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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * Structure walker for object walkers.
 * 
 * @param <O>
 *        type to visit
 */
public class StructureWalker<O extends OWLObject> implements OWLObjectVisitor {

    /**
     * Control flag for whether to walk annotations
     */
    public static enum AnnotationWalkingControl {
        /**
         * Do not walk any annotations
         */
        DONT_WALK_ANNOTATIONS,
        /**
         * Only walk ontology annotations (previous behaviour)
         */
        WALK_ONTOLOGY_ANNOTATIONS_ONLY,
        /**
         * Walk all annotations
         */
        WALK_ANNOTATIONS
    }

    protected final OWLObjectWalker<O> walkerCallback;
    protected final Set<OWLObject> visited = new HashSet<>();
    protected final AnnotationWalkingControl annotationWalkFlag;

    /**
     * @param owlObjectWalker
     *        object walker
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker) {
        this(owlObjectWalker, WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param owlObjectWalker
     *        callback object walker
     * @param annotationWalkFlag
     *        control flag for annotation walking
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker,
        AnnotationWalkingControl annotationWalkFlag) {
        this.walkerCallback = owlObjectWalker;
        this.annotationWalkFlag = annotationWalkFlag;
    }

    protected void process(@Nonnull OWLObject object) {
        if (object instanceof OWLAxiom) {
            walkerCallback.setAxiom((OWLAxiom) object);
        }
        if (object instanceof OWLAnnotation) {
            walkerCallback.setAnnotation((OWLAnnotation) object);
        }
        if (!walkerCallback.visitDuplicates) {
            if (!visited.contains(object)) {
                visited.add(object);
                walkerCallback.passToVisitor(object);
            }
        } else {
            walkerCallback.passToVisitor(object);
        }
        if (object instanceof HasAnnotations) {
            HasAnnotations hasAnnotations = (HasAnnotations) object;
            switch (annotationWalkFlag) {
                case DONT_WALK_ANNOTATIONS:
                    break;
                case WALK_ONTOLOGY_ANNOTATIONS_ONLY:
                    if (!(object instanceof OWLOntology)) {
                        break;
                    }
                    // fall-through
                case WALK_ANNOTATIONS:
                    for (OWLAnnotation anno : hasAnnotations.getAnnotations()) {
                        anno.accept(this);
                    }
            }
        }
    }

    @Override
    public void visit(IRI iri) {
        process(iri);
    }

    @Override
    public void visit(OWLOntology ontology) {
        walkerCallback.ontology = ontology;
        walkerCallback.ax = null;
        process(ontology);
        for (OWLAxiom a : ontology.getAxioms()) {
            a.accept(this);
        }
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getIndividual().accept(this);
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getEntity().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getOWLClass().accept(this);
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLAnnotation node) {
        process(node);
        walkerCallback.setAnnotation(node);
        node.getProperty().accept(this);
        node.getValue().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        // -ve polarity
        axiom.getSubClass().accept(this);
        // +ve polarity
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(SWRLRule rule) {
        process(rule);
        walkerCallback.ax = rule;
        for (SWRLAtom at : rule.getBody()) {
            at.accept(this);
        }
        for (SWRLAtom at : rule.getHead()) {
            at.accept(this);
        }
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getClassExpression().accept(this);
        for (OWLObjectPropertyExpression prop : axiom
            .getObjectPropertyExpressions()) {
            prop.accept(this);
        }
        for (OWLDataPropertyExpression prop : axiom
            .getDataPropertyExpressions()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLClass ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getIRI().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getOperand().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            op.accept(this);
        }
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        for (OWLIndividual ind : ce.getIndividuals()) {
            ind.accept(this);
        }
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            op.accept(this);
        }
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        walkerCallback.pushClassExpression(ce);
        process(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        walkerCallback.popClassExpression();
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        node.getDataRange().accept(this);
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        for (OWLLiteral con : node.getValues()) {
            con.accept(this);
        }
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        process(node);
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        walkerCallback.pushDataRange(node);
        process(node);
        node.getDatatype().accept(this);
        for (OWLFacetRestriction fr : node.getFacetRestrictions()) {
            fr.accept(this);
        }
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDatatype node) {
        walkerCallback.pushDataRange(node);
        process(node);
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLLiteral node) {
        process(node);
        node.getDatatype().accept(this);
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        process(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLDataProperty property) {
        process(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        process(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        process(property);
        property.getInverse().accept(this);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        process(individual);
        individual.getIRI().accept(this);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        process(individual);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        process(node);
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLVariable node) {
        process(node);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        process(node);
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        process(node);
        for (SWRLDArgument at : node.getArguments()) {
            at.accept(this);
        }
    }

    @Override
    public void visit(SWRLClassAtom node) {
        process(node);
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        process(node);
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        process(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        process(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        process(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        process(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        process(axiom);
        walkerCallback.ax = axiom;
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
    }
}
