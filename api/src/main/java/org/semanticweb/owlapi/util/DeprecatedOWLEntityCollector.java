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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

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
public class DeprecatedOWLEntityCollector implements OWLObjectVisitor, SWRLObjectVisitor {

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
        @Nullable Collection<OWLAnonymousIndividual> anonsToReturn) {
        objects = toReturn;
        anonymousIndividuals = anonsToReturn;
    }

    /**
     * @param toReturn
     *        the set that will contain the results
     */
    public DeprecatedOWLEntityCollector(Set<OWLEntity> toReturn) {
        this(toReturn, null);
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
        if (anonymousIndividuals != null) {
            anonymousIndividuals.clear();
        }
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
        if (anonymousIndividuals == null) {
            return CollectionFactory.createLinkedSet();
        }
        return CollectionFactory.createSet(anonymousIndividuals);
    }

    protected void processAxiomAnnotations(OWLAxiom ax) {
        // default behavior: iterate over the annotations outside the axiom
        ax.annotations().forEach(a -> a.accept(this));
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
        axiom.classExpressions().forEach(d -> d.accept(this));
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
        axiom.getProperties().forEach(p -> p.accept(this));
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
        axiom.individuals().forEach(i -> i.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        axiom.getProperties().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        axiom.getProperties().forEach(p -> p.accept(this));
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
        axiom.classExpressions().forEach(d -> d.accept(this));
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
        axiom.getProperties().forEach(p -> p.accept(this));
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
        axiom.classExpressions().forEach(d -> d.accept(this));
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
        axiom.individuals().forEach(i -> i.accept(this));
        processAxiomAnnotations(axiom);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        axiom.getPropertyChain().forEach(p -> p.accept(this));
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
        axiom.getPropertyExpressions().forEach(p -> p.accept(this));
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
        desc.operands().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        desc.operands().forEach(o -> o.accept(this));
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
        desc.individuals().forEach(i -> i.accept(this));
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
        node.values().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        node.getOperands().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        node.getOperands().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(o -> o.accept(this));
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
        annotation.annotations().forEach(o -> o.accept(this));
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
        if (anonymousIndividuals != null) {
            anonymousIndividuals.add(individual);
        }
    }

    @Override
    public void visit(IRI iri) {}

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
        rule.getBody().forEach(o -> o.accept(this));
        rule.getHead().forEach(o -> o.accept(this));
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
        node.getAllArguments().forEach(o -> o.accept(this));
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
}
