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

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
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
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Structure walker for object walkers.
 *
 * @param <O> type to visit
 */
public class StructureWalker<O extends OWLObject> implements OWLObjectVisitor {

    protected final OWLObjectWalker<O> walkerCallback;
    protected final Set<OWLObject> visited = new HashSet<>();
    protected final AnnotationWalkingControl annotationWalkFlag;

    /**
     * @param owlObjectWalker callback object walker
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker) {
        this(owlObjectWalker, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param owlObjectWalker callback object walker
     * @param annotationWalkFlag control flag for annotation walking
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker,
        AnnotationWalkingControl annotationWalkFlag) {
        this.walkerCallback = owlObjectWalker;
        this.annotationWalkFlag = annotationWalkFlag;
    }

    protected void process(OWLObject object) {
        if (object instanceof OWLAxiom) {
            walkerCallback.setAxiom((OWLAxiom) object);
        }
        if (object instanceof OWLAnnotation) {
            walkerCallback.setAnnotation((OWLAnnotation) object);
        }
        if (!walkerCallback.visitDuplicates) {
            if (visited.add(object)) {
                walkerCallback.passToVisitor(object);
            }
        } else {
            walkerCallback.passToVisitor(object);
        }
        annotationWalkFlag.walk(this, object);
    }

    @Override
    public void visit(IRI iri) {
        process(iri);
    }

    @Override
    public void visit(OWLOntology ontology) {
        walkerCallback.ontology = ontology;
        walkerCallback.setAxiom(null);
        process(ontology);
        ontology.axioms().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        process(axiom);
        axiom.getIndividual().accept(this);
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        process(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        process(axiom);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        process(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        process(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        process(axiom);
        axiom.getEntity().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        process(axiom);
        axiom.individuals().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        process(axiom);
        axiom.classExpressions().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        process(axiom);
        axiom.properties().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        process(axiom);
        axiom.properties().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        process(axiom);
        axiom.getOWLClass().accept(this);
        axiom.classExpressions().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        process(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        process(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLAnnotation node) {
        process(node);
        node.getProperty().accept(this);
        node.getValue().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        process(axiom);
        axiom.classExpressions().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        process(axiom);
        axiom.properties().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        process(axiom);
        axiom.properties().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        process(axiom);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        process(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        process(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        process(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        process(axiom);
        axiom.getPropertyChain().forEach(a -> a.accept(this));
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        process(axiom);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        process(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        process(axiom);
        axiom.individuals().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        process(axiom);
        // -ve polarity
        axiom.getSubClass().accept(this);
        // +ve polarity
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        process(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(SWRLRule rule) {
        process(rule);
        rule.body().forEach(a -> a.accept(this));
        rule.head().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        process(axiom);
        axiom.getClassExpression().accept(this);
        axiom.objectPropertyExpressions().forEach(a -> a.accept(this));
        axiom.dataPropertyExpressions().forEach(a -> a.accept(this));
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
        ce.operands().forEach(a -> a.accept(this));
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
        ce.individuals().forEach(a -> a.accept(this));
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
        ce.operands().forEach(a -> a.accept(this));
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
        node.values().forEach(a -> a.accept(this));
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        node.operands().forEach(a -> a.accept(this));
        walkerCallback.popDataRange();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        walkerCallback.pushDataRange(node);
        process(node);
        node.operands().forEach(a -> a.accept(this));
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
        node.facetRestrictions().forEach(a -> a.accept(this));
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
        node.getArguments().forEach(a -> a.accept(this));
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
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
    }
}
