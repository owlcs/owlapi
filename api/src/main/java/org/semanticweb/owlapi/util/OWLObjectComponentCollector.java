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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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
import org.semanticweb.owlapi.model.SWRLDArgument;
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
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class OWLObjectComponentCollector implements OWLObjectVisitor {

    private final Set<OWLObject> result = new HashSet<>();

    /**
     * A convenience method that obtains the components of an OWL object. Note
     * that by definition, the components of the object include the object
     * itself.
     * 
     * @param object
     *        The object whose components are to be obtained.
     * @return The component of the specified object.
     */
    @Nonnull
    public Set<OWLObject> getComponents(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        result.clear();
        object.accept(this);
        return getResult();
    }

    /** @return the resulting owl objects */
    @Nonnull
    public Set<OWLObject> getResult() {
        return CollectionFactory
                .getCopyOnRequestSetFromMutableCollection(result);
    }

    private void process(@Nonnull Set<? extends OWLObject> objects) {
        checkNotNull(objects, "objects cannot be null");
        for (OWLObject obj : objects) {
            obj.accept(this);
        }
    }

    /**
     * Handles an object. By default, this method adds the object to the result
     * collection. This method may be overriden to do something else.
     * 
     * @param obj
     *        The object being added.
     */
    protected void handleObject(@Nonnull OWLObject obj) {
        checkNotNull(obj, "obj cannot be null");
        result.add(obj);
    }

    @Override
    public void visit(OWLOntology ontology) {
        process(ontology.getAxioms());
    }

    @Override
    public void visit(OWLClass ce) {
        handleObject(ce);
        ce.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        handleObject(property);
        property.getInverse().accept(this);
    }

    @Override
    public void visit(OWLDataProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLDatatype node) {
        handleObject(node);
        node.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        handleObject(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            op.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        handleObject(ce);
        process(ce.getOperands());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        handleObject(ce);
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        handleObject(ce);
        process(ce.getIndividuals());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        handleObject(ce);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getClassExpressions());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        handleObject(axiom);
        axiom.getOWLClass().accept(this);
        process(axiom.getClassExpressions());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        handleObject(axiom);
        axiom.getEntity().accept(this);
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getClassExpressions());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        handleObject(axiom);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }

    @Override
    public void visit(SWRLRule rule) {
        handleObject(rule);
        process(rule.getBody());
        process(rule.getHead());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        handleObject(node);
        node.getDataRange().accept(this);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        handleObject(node);
        process(node.getValues());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        handleObject(node);
        node.getDatatype().accept(this);
        process(node.getFacetRestrictions());
    }

    @Override
    public void visit(OWLLiteral node) {
        handleObject(node);
        node.getDatatype().accept(this);
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        handleObject(node);
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        handleObject(axiom);
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
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        handleObject(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        handleObject(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        handleObject(individual);
        individual.getIRI().accept(this);
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        handleObject(individual);
    }

    @Override
    public void visit(IRI iri) {
        handleObject(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {}

    @Override
    public void visit(SWRLClassAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        handleObject(node);
        for (SWRLDArgument obj : node.getArguments()) {
            obj.accept(this);
        }
    }

    @Override
    public void visit(SWRLVariable node) {
        handleObject(node);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        handleObject(node);
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        handleObject(node);
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        handleObject(axiom);
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
    }
}
