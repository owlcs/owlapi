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
package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
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
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
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
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;

/** Base for entity registration manager. */
public abstract class AbstractEntityRegistrationManager implements
        OWLObjectVisitor, SWRLObjectVisitor {

    protected void processAxiomAnnotations(@Nonnull OWLAxiom ax) {
        ax.annotations().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        axiom.classExpressions().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        axiom.properties().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        axiom.individuals().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        axiom.properties().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        axiom.properties().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept((OWLEntityVisitor) this);
        axiom.classExpressions().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        axiom.properties().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        axiom.classExpressions().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        axiom.individuals().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        axiom.getPropertyChain().forEach(a -> a.accept(this));
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        axiom.getClassExpression().accept(this);
        axiom.propertyExpressions().forEach(a -> a.accept(this));
        processAxiomAnnotations(axiom);
    }

    // OWLClassExpressionVisitor
    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        ce.operands().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        ce.operands().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        ce.individuals().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    // Data visitor
    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        node.getDataRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        node.values().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        node.operands().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        node.operands().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        node.getDatatype().accept(this);
    }

    // Property expression visitor
    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        property.getInverse().accept(this);
    }

    // Entity visitor
    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        node.getProperty().accept(this);
        node.getValue().accept(this);
        node.annotations().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getValue().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
        processAxiomAnnotations(axiom);
    }

    // SWRL Object Visitor
    @Override
    public void visit(@Nonnull SWRLRule rule) {
        rule.body().forEach(a -> a.accept(this));
        rule.head().forEach(a -> a.accept(this));
        processAxiomAnnotations(rule);
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        node.getAllArguments().forEach(a -> a.accept(this));
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }
}
