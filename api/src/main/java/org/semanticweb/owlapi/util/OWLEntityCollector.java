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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * A utility class that visits axioms, class expressions etc. and accumulates
 * the named objects that are referred to in those axioms, class expressions
 * etc. For example, if the collector visited the axiom (propP some C)
 * subClassOf (propQ some D), it would contain the objects propP, C, propQ and
 * D.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEntityCollector
        implements OWLObjectVisitorEx<Collection<OWLEntity>>, SWRLObjectVisitorEx<Collection<OWLEntity>> {

    private final @Nonnull Collection<OWLEntity> objects;

    /**
     * @param toReturn
     *        the set that will contain the results
     */
    public OWLEntityCollector(Set<OWLEntity> toReturn) {
        objects = checkNotNull(toReturn, "toReturn cannot be null");
    }

    protected void processAxiomAnnotations(OWLAxiom ax) {
        // default behavior: iterate over the annotations outside the axiom
        ax.annotations().forEach(a -> a.accept(this));
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointClassesAxiom axiom) {
        axiom.classExpressions().forEach(d -> d.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        axiom.properties().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDifferentIndividualsAxiom axiom) {
        axiom.individuals().forEach(i -> i.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointDataPropertiesAxiom axiom) {
        axiom.properties().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        axiom.properties().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectPropertyRangeAxiom axiom) {
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        axiom.classExpressions().forEach(d -> d.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDeclarationAxiom axiom) {
        axiom.getEntity().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        axiom.properties().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLEquivalentClassesAxiom axiom) {
        axiom.classExpressions().forEach(d -> d.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSameIndividualAxiom axiom) {
        axiom.individuals().forEach(i -> i.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubPropertyChainOfAxiom axiom) {
        axiom.getPropertyChain().forEach(p -> p.accept(this));
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLHasKeyAxiom axiom) {
        axiom.getClassExpression().accept(this);
        axiom.propertyExpressions().forEach(p -> p.accept(this));
        processAxiomAnnotations(axiom);
        return objects;
    }

    // OWLClassExpressionVisitor
    @Override
    public Collection<OWLEntity> visit(OWLClass ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectIntersectionOf ce) {
        ce.operands().forEach(o -> o.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectUnionOf ce) {
        ce.operands().forEach(o -> o.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectComplementOf ce) {
        ce.getOperand().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectAllValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectHasValue ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectMinCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectExactCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectMaxCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectHasSelf ce) {
        ce.getProperty().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectOneOf ce) {
        ce.individuals().forEach(i -> i.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataSomeValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataAllValuesFrom ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataHasValue ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataMinCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataExactCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataMaxCardinality ce) {
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
        return objects;
    }

    // Data visitor
    @Override
    public Collection<OWLEntity> visit(OWLDataComplementOf node) {
        node.getDataRange().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataOneOf node) {
        node.values().forEach(val -> val.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataIntersectionOf node) {
        node.operands().forEach(dr -> dr.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataUnionOf node) {
        node.operands().forEach(dr -> dr.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(f -> f.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLFacetRestriction node) {
        node.getFacetValue().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLLiteral node) {
        node.getDatatype().accept(this);
        return objects;
    }

    // Property expression visitor
    @Override
    public Collection<OWLEntity> visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        return objects;
    }

    // Entity visitor
    @Override
    public Collection<OWLEntity> visit(OWLObjectProperty property) {
        objects.add(property);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataProperty property) {
        objects.add(property);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLNamedIndividual individual) {
        objects.add(individual);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDatatype node) {
        objects.add(node);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotation node) {
        node.getProperty().accept(this);
        node.getValue().accept(this);
        node.annotations().forEach(a -> a.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotationAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getValue().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnonymousIndividual individual) {
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(IRI iri) {
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLOntology ontology) {
        add(ontology.signature(), objects);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotationProperty property) {
        objects.add(property);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotationPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotationPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDatatypeDefinitionAxiom axiom) {
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    // SWRL Object Visitor
    @Override
    public Collection<OWLEntity> visit(SWRLRule rule) {
        rule.body().forEach(a -> a.accept(this));
        rule.head().forEach(a -> a.accept(this));
        processAxiomAnnotations(rule);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLClassAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLDataRangeAtom node) {
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLBuiltInAtom node) {
        node.getAllArguments().forEach(o -> o.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLVariable node) {
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLDifferentIndividualsAtom node) {
        node.getFirstArgument().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(SWRLSameIndividualAtom node) {
        node.getSecondArgument().accept(this);
        return objects;
    }
}
