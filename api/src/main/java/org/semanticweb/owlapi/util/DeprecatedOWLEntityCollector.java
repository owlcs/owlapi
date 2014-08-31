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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
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
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * A utility class that visits axioms, class expressions etc. and accumulates
 * the named objects that are referred to in those axioms, class expressions
 * etc. For example, if the collector visited the axiom (propP some C)
 * subClassOf (propQ some D), it would contain the objects propP, C, propQ and
 * D.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 13-Nov-2006
 * @deprecated the old OWLEntityCollector is still used in non trivial ways in
 *             third party software. For new code, use OWLEntityCollector.
 */
@Deprecated
public class DeprecatedOWLEntityCollector implements OWLObjectVisitor,
        SWRLObjectVisitor {

    private Collection<OWLEntity> objects;
    private final Collection<OWLAnonymousIndividual> anonymousIndividuals;
    private boolean collectClasses = true;
    private boolean collectObjectProperties = true;
    private boolean collectDataProperties = true;
    private boolean collectIndividuals = true;
    private boolean collectDatatypes = true;

    /**
     * @param toReturn
     *        the set that will contain the results
     * @param anonsToReturn
     *        the set that will contain the anon individuals
     */
    public DeprecatedOWLEntityCollector(Set<OWLEntity> toReturn,
            Collection<OWLAnonymousIndividual> anonsToReturn) {
        objects = toReturn;
        anonymousIndividuals = anonsToReturn;
    }

    /**
     * @param toReturn
     *        the set that will contain the results
     */
    public DeprecatedOWLEntityCollector(Set<OWLEntity> toReturn) {
        objects = toReturn;
        anonymousIndividuals = FAKE;
    }

    /**
     * Deprecated default constructor: use one of the other constructors to get
     * more efficient set creation.
     */
    @Deprecated
    public DeprecatedOWLEntityCollector() {
        this(new HashSet<OWLEntity>(), new HashSet<OWLAnonymousIndividual>());
    }

    /**
     * Clears all objects that have accumulated during the course of visiting
     * axioms, class expressions etc.
     * 
     * @param toReturn
     *        the set that will contain the results
     */
    public void reset(Set<OWLEntity> toReturn) {
        objects = toReturn;
        anonymousIndividuals.clear();
    }

    /**
     * @param collectClasses
     *        true to collect classes
     */
    public void setCollectClasses(boolean collectClasses) {
        this.collectClasses = collectClasses;
    }

    /**
     * @param collectObjectProperties
     *        true to collect object properties
     */
    public void setCollectObjectProperties(boolean collectObjectProperties) {
        this.collectObjectProperties = collectObjectProperties;
    }

    /**
     * @param collectDataProperties
     *        true to collect data properties
     */
    public void setCollectDataProperties(boolean collectDataProperties) {
        this.collectDataProperties = collectDataProperties;
    }

    /**
     * @param collectIndividuals
     *        true to collect individuals
     */
    public void setCollectIndividuals(boolean collectIndividuals) {
        this.collectIndividuals = collectIndividuals;
    }

    /**
     * @param collectDatatypes
     *        true to collect datatypes
     */
    public void setCollectDatatypes(boolean collectDatatypes) {
        this.collectDatatypes = collectDatatypes;
    }

    /**
     * Gets the objects that are used by all axioms, class expressions etc. that
     * this collector has visited since it was constructed or reset. Deprecated:
     * if the non deprecated constructors are used, this method is useless and
     * inefficient
     * 
     * @return A set of entities. This will be a copy.
     */
    @Deprecated
    public Set<OWLEntity> getObjects() {
        return new HashSet<>(objects);
    }

    /**
     * A convenience method. Although anonymous individuals are not entities
     * they are collected by this collector and stored in a separate set. This
     * method returns collected individuals. Deprecated: if the non deprecated
     * constructors are used, this method is useless and inefficient
     * 
     * @return The set of anonymous individuals that were collected by the
     *         collector
     */
    @Deprecated
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return new HashSet<>(anonymousIndividuals);
    }

    protected void processAxiomAnnotations(OWLAxiom ax) {
        // default behavior: iterate over the annotations outside the axiom
        for (OWLAnnotation anno : ax.getAnnotations()) {
            anno.accept(this);
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept((OWLEntityVisitor) this);
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        axiom.getClassExpression().accept(this);
        for (OWLPropertyExpression prop : axiom.getPropertyExpressions()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLClass desc) {
        if (collectClasses) {
            objects.add(desc);
        }
    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectComplementOf desc) {
        desc.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasSelf desc) {
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectOneOf desc) {
        for (OWLIndividual ind : desc.getIndividuals()) {
            ind.accept(this);
        }
    }

    @Override
    public void visit(OWLDataSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataExactCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataMaxCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        node.getDataRange().accept(this);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        for (OWLLiteral val : node.getValues()) {
            val.accept(this);
        }
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        for (OWLDataRange dr : node.getOperands()) {
            dr.accept(this);
        }
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        for (OWLDataRange dr : node.getOperands()) {
            dr.accept(this);
        }
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        for (OWLFacetRestriction facetRestriction : node.getFacetRestrictions()) {
            facetRestriction.accept(this);
        }
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLLiteral node) {
        node.getDatatype().accept(this);
    }

    @Override
    public void visit(OWLObjectInverseOf expression) {
        expression.getInverse().accept(this);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        if (collectObjectProperties) {
            objects.add(property);
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        if (collectDataProperties) {
            objects.add(property);
        }
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        if (collectIndividuals) {
            objects.add(individual);
        }
    }

    @Override
    public void visit(OWLDatatype datatype) {
        if (collectDatatypes) {
            objects.add(datatype);
        }
    }

    @Override
    public void visit(OWLAnnotation annotation) {
        annotation.getProperty().accept(this);
        annotation.getValue().accept(this);
        for (OWLAnnotation anno : annotation.getAnnotations()) {
            anno.accept(this);
        }
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getValue().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        // Anon individuals aren't entities
        // But store them in a set anyway for utility
        anonymousIndividuals.add(individual);
    }

    @Override
    public void visit(IRI iri) {}

    // public void visit(OWLAnnotationValue value) {
    // if(value.isLiteral()) {
    // value.asLiteral().accept(this);
    // }
    // else if(value.isAnonymousIndividual()) {
    // value.asOWLAnonymousIndividual().accept(this);
    // }
    // }
    @Override
    public void visit(OWLOntology ontology) {
        objects.addAll(ontology.getSignature());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        objects.add(property);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(SWRLRule rule) {
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
        }
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
        }
        processAxiomAnnotations(rule);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        for (SWRLArgument obj : node.getAllArguments()) {
            obj.accept(this);
        }
    }

    @Override
    public void visit(SWRLVariable node) {}

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        node.getSecondArgument().accept(this);
    }

    private static final List<OWLAnonymousIndividual> FAKE = new ArrayList<OWLAnonymousIndividual>() {

        private static final long serialVersionUID = 40000L;

        @Override
        public <T> T[] toArray(T[] arg0) {
            return arg0;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public List<OWLAnonymousIndividual> subList(int arg0, int arg1) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public OWLAnonymousIndividual
                set(int arg0, OWLAnonymousIndividual arg1) {
            return null;
        }

        @Override
        public boolean retainAll(Collection<?> arg0) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> arg0) {
            return false;
        }

        @Override
        public OWLAnonymousIndividual remove(int arg0) {
            return null;
        }

        @Override
        public boolean remove(Object arg0) {
            return false;
        }

        @Override
        public ListIterator<OWLAnonymousIndividual> listIterator(int arg0) {
            return null;
        }

        @Override
        public ListIterator<OWLAnonymousIndividual> listIterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object arg0) {
            return 0;
        }

        @Override
        public Iterator<OWLAnonymousIndividual> iterator() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int indexOf(Object arg0) {
            return 0;
        }

        @Override
        public OWLAnonymousIndividual get(int arg0) {
            return null;
        }

        @Override
        public boolean containsAll(Collection<?> arg0) {
            return false;
        }

        @Override
        public boolean contains(Object arg0) {
            return false;
        }

        @Override
        public void clear() {}

        @Override
        public boolean addAll(int arg0,
                Collection<? extends OWLAnonymousIndividual> arg1) {
            return false;
        }

        @Override
        public boolean
                addAll(Collection<? extends OWLAnonymousIndividual> arg0) {
            return false;
        }

        @Override
        public void add(int arg0, OWLAnonymousIndividual arg1) {}

        @Override
        public boolean add(OWLAnonymousIndividual arg0) {
            return false;
        }
    };
}
