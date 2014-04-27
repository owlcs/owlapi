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

import java.util.Collection;
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
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
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
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
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
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEntityCollector implements
        OWLObjectVisitorEx<Collection<OWLEntity>>,
        SWRLObjectVisitorEx<Collection<OWLEntity>> {

    @Nonnull
    private Collection<OWLEntity> objects;

    /**
     * @param toReturn
     *        the set that will contain the results
     */
    public OWLEntityCollector(@Nonnull Set<OWLEntity> toReturn) {
        objects = checkNotNull(toReturn, "toReturn cannot be null");
    }

    protected void processAxiomAnnotations(@Nonnull OWLAxiom ax) {
        // default behavior: iterate over the annotations outside the axiom
        for (OWLAnnotation anno : ax.getAnnotations()) {
            anno.accept(this);
        }
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(
            OWLNegativeObjectPropertyAssertionAxiom axiom) {
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
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
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
    public Collection<OWLEntity>
            visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(
            OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDifferentIndividualsAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
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
        axiom.getOWLClass().accept((OWLEntityVisitor) this);
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
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
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
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
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
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
    public Collection<OWLEntity> visit(
            OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSameIndividualAxiom axiom) {
        for (OWLIndividual ind : axiom.getIndividuals()) {
            ind.accept(this);
        }
        processAxiomAnnotations(axiom);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLSubPropertyChainOfAxiom axiom) {
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
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
        for (OWLPropertyExpression prop : axiom.getPropertyExpressions()) {
            prop.accept(this);
        }
        processAxiomAnnotations(axiom);
        return objects;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLClassExpressionVisitor
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<OWLEntity> visit(OWLClass desc) {
        objects.add(desc);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectIntersectionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectUnionOf desc) {
        for (OWLClassExpression operand : desc.getOperands()) {
            operand.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectComplementOf desc) {
        desc.getOperand().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectAllValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectHasValue desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectMinCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectExactCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectMaxCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectHasSelf desc) {
        desc.getProperty().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLObjectOneOf desc) {
        for (OWLIndividual ind : desc.getIndividuals()) {
            ind.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataSomeValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataAllValuesFrom desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataHasValue desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataMinCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataExactCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataMaxCardinality desc) {
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        return objects;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data visitor
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<OWLEntity> visit(OWLDataComplementOf node) {
        node.getDataRange().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataOneOf node) {
        for (OWLLiteral val : node.getValues()) {
            val.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataIntersectionOf node) {
        for (OWLDataRange dr : node.getOperands()) {
            dr.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDataUnionOf node) {
        for (OWLDataRange dr : node.getOperands()) {
            dr.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        for (OWLFacetRestriction facetRestriction : node.getFacetRestrictions()) {
            facetRestriction.accept(this);
        }
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

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression visitor
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<OWLEntity> visit(OWLObjectInverseOf expression) {
        expression.getInverse().accept(this);
        return objects;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Entity visitor
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public Collection<OWLEntity> visit(OWLDatatype datatype) {
        objects.add(datatype);
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLAnnotation annotation) {
        annotation.getProperty().accept(this);
        annotation.getValue().accept(this);
        for (OWLAnnotation anno : annotation.getAnnotations()) {
            anno.accept(this);
        }
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
    public Collection<OWLEntity> visit(
            @SuppressWarnings("unused") OWLAnonymousIndividual individual) {
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(@SuppressWarnings("unused") IRI iri) {
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(OWLOntology ontology) {
        objects.addAll(ontology.getSignature());
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

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL Object Visitor
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Collection<OWLEntity> visit(SWRLRule rule) {
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
        }
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
        }
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
        for (SWRLArgument obj : node.getAllArguments()) {
            obj.accept(this);
        }
        return objects;
    }

    @Override
    public Collection<OWLEntity> visit(
            @SuppressWarnings("unused") SWRLVariable node) {
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
