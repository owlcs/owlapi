/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.apibinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
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
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * A utility class whose methods may be statically imported so that OWL API
 * objects can be constructed by writing code that looks like the OWL 2
 * Functional Syntax. <br>
 * Note that this class is primarily intended for developers who need to write
 * test cases. Normal client code should probably use an
 * {@link org.semanticweb.owlapi.model.OWLDataFactory} for creating objects.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 17-Jan-2010
 */
@SuppressWarnings("javadoc")
public class OWLFunctionalSyntaxFactory {

    private static final OWLDataFactory dataFactory = OWLManager
            .getOWLDataFactory();

    public static OWLImportsDeclaration ImportsDeclaration(IRI i) {
        return dataFactory.getOWLImportsDeclaration(i);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Entities
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static OWLClass Class(IRI iri) {
        return dataFactory.getOWLClass(iri);
    }

    public static OWLClass Class(String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLClass(abbreviatedIRI, pm);
    }

    public static OWLAnnotationProperty RDFSComment() {
        return dataFactory.getRDFSComment();
    }

    public static OWLAnnotationProperty RDFSLabel() {
        return dataFactory.getRDFSLabel();
    }

    public static OWLDatatype TopDatatype() {
        return dataFactory.getTopDatatype();
    }

    public static OWLClass OWLThing() {
        return dataFactory.getOWLThing();
    }

    public static OWLDatatype Integer() {
        return dataFactory.getIntegerOWLDatatype();
    }

    public static OWLDatatype Double() {
        return dataFactory.getDoubleOWLDatatype();
    }

    public static OWLDatatype Float() {
        return dataFactory.getFloatOWLDatatype();
    }

    public static OWLDatatype Boolean() {
        return dataFactory.getBooleanOWLDatatype();
    }

    public static OWLClass OWLNothing() {
        return dataFactory.getOWLNothing();
    }

    public static OWLObjectProperty ObjectProperty(IRI iri) {
        return dataFactory.getOWLObjectProperty(iri);
    }

    public static OWLObjectProperty ObjectProperty(String abbreviatedIRI,
            PrefixManager pm) {
        return dataFactory.getOWLObjectProperty(abbreviatedIRI, pm);
    }

    public static OWLObjectInverseOf ObjectInverseOf(
            OWLObjectPropertyExpression pe) {
        return dataFactory.getOWLObjectInverseOf(pe);
    }

    public static OWLDataProperty DataProperty(IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    public static OWLDataProperty DataProperty(String abbreviatedIRI,
            PrefixManager pm) {
        return dataFactory.getOWLDataProperty(abbreviatedIRI, pm);
    }

    public static OWLAnnotationProperty AnnotationProperty(IRI iri) {
        return dataFactory.getOWLAnnotationProperty(iri);
    }

    public static OWLAnnotationProperty AnnotationProperty(
            String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLAnnotationProperty(abbreviatedIRI, pm);
    }

    public static OWLNamedIndividual NamedIndividual(IRI iri) {
        return dataFactory.getOWLNamedIndividual(iri);
    }

    public static OWLAnonymousIndividual AnonymousIndividual() {
        return dataFactory.getOWLAnonymousIndividual();
    }

    public static OWLAnonymousIndividual AnonymousIndividual(String id) {
        return dataFactory.getOWLAnonymousIndividual(id);
    }

    public static OWLNamedIndividual NamedIndividual(String abbreviatedIRI,
            PrefixManager pm) {
        return dataFactory.getOWLNamedIndividual(abbreviatedIRI, pm);
    }

    public static OWLDatatype Datatype(IRI iri) {
        return dataFactory.getOWLDatatype(iri);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity) {
        return dataFactory.getOWLDeclarationAxiom(entity);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity,
            Set<OWLAnnotation> a) {
        return dataFactory.getOWLDeclarationAxiom(entity, a);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Class Expressions
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static OWLObjectIntersectionOf ObjectIntersectionOf(
            OWLClassExpression... classExpressions) {
        return dataFactory.getOWLObjectIntersectionOf(classExpressions);
    }

    public static OWLObjectUnionOf ObjectUnionOf(
            OWLClassExpression... classExpressions) {
        return dataFactory.getOWLObjectUnionOf(classExpressions);
    }

    public static OWLObjectComplementOf ObjectComplementOf(
            OWLClassExpression classExpression) {
        return dataFactory.getOWLObjectComplementOf(classExpression);
    }

    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(
            OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectSomeValuesFrom(pe, ce);
    }

    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(
            OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectAllValuesFrom(pe, ce);
    }

    public static OWLObjectHasValue ObjectHasValue(
            OWLObjectPropertyExpression pe, OWLIndividual individual) {
        return dataFactory.getOWLObjectHasValue(pe, individual);
    }

    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality,
            OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectMinCardinality(cardinality, pe, ce);
    }

    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality,
            OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectMaxCardinality(cardinality, pe, ce);
    }

    public static OWLObjectExactCardinality ObjectExactCardinality(
            int cardinality, OWLObjectPropertyExpression pe,
            OWLClassExpression ce) {
        return dataFactory.getOWLObjectExactCardinality(cardinality, pe, ce);
    }

    public static OWLObjectHasSelf
            ObjectHasSelf(OWLObjectPropertyExpression pe) {
        return dataFactory.getOWLObjectHasSelf(pe);
    }

    public static OWLObjectOneOf ObjectOneOf(OWLIndividual... individuals) {
        return dataFactory.getOWLObjectOneOf(individuals);
    }

    public static OWLDataSomeValuesFrom DataSomeValuesFrom(
            OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataSomeValuesFrom(pe, dr);
    }

    public static OWLDataAllValuesFrom DataAllValuesFrom(
            OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataAllValuesFrom(pe, dr);
    }

    public static OWLDataHasValue DataHasValue(OWLDataPropertyExpression pe,
            OWLLiteral literal) {
        return dataFactory.getOWLDataHasValue(pe, literal);
    }

    public static OWLDataMinCardinality DataMinCardinality(int cardinality,
            OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataMinCardinality(cardinality, pe, dr);
    }

    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality,
            OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataMaxCardinality(cardinality, pe, dr);
    }

    public static OWLDataExactCardinality DataExactCardinality(int cardinality,
            OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataExactCardinality(cardinality, pe, dr);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Data Ranges other than datatype
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static OWLDataIntersectionOf DataIntersectionOf(
            OWLDataRange... dataRanges) {
        return dataFactory.getOWLDataIntersectionOf(dataRanges);
    }

    public static OWLDataUnionOf DataUnionOf(OWLDataRange... dataRanges) {
        return dataFactory.getOWLDataUnionOf(dataRanges);
    }

    public static OWLDataComplementOf DataComplementOf(OWLDataRange dataRange) {
        return dataFactory.getOWLDataComplementOf(dataRange);
    }

    public static OWLDataOneOf DataOneOf(OWLLiteral... literals) {
        return dataFactory.getOWLDataOneOf(literals);
    }

    public static OWLDatatypeRestriction DatatypeRestriction(
            OWLDatatype datatype, OWLFacetRestriction... facetRestrictions) {
        return dataFactory.getOWLDatatypeRestriction(datatype,
                facetRestrictions);
    }

    public static OWLFacetRestriction FacetRestriction(OWLFacet facet,
            OWLLiteral facetValue) {
        return dataFactory.getOWLFacetRestriction(facet, facetValue);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass,
            OWLClassExpression superClass) {
        return dataFactory.getOWLSubClassOfAxiom(subClass, superClass);
    }

    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass,
            OWLClassExpression superClass, Set<OWLAnnotation> a) {
        return dataFactory.getOWLSubClassOfAxiom(subClass, superClass, a);
    }

    public static OWLEquivalentClassesAxiom EquivalentClasses(
            OWLClassExpression... classExpressions) {
        return dataFactory.getOWLEquivalentClassesAxiom(classExpressions);
    }

    public static OWLEquivalentClassesAxiom EquivalentClasses(
            Set<OWLAnnotation> a, OWLClassExpression... classExpressions) {
        return dataFactory
                .getOWLEquivalentClassesAxiom(new HashSet<OWLClassExpression>(
                        Arrays.asList(classExpressions)), a);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(
            OWLClassExpression... classExpressions) {
        return dataFactory.getOWLDisjointClassesAxiom(classExpressions);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(
            Set<? extends OWLClassExpression> classExpressions) {
        return dataFactory.getOWLDisjointClassesAxiom(classExpressions);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(
            Set<OWLClassExpression> classExpressions, Set<OWLAnnotation> a) {
        return dataFactory.getOWLDisjointClassesAxiom(classExpressions, a);
    }

    public static OWLDisjointUnionAxiom DisjointUnion(OWLClass cls,
            OWLClassExpression... classExpressions) {
        return dataFactory.getOWLDisjointUnionAxiom(cls,
                CollectionFactory.createSet(classExpressions));
    }

    public static OWLDisjointClassesAxiom DisjointClasses(Set<OWLAnnotation> a,
            OWLClassExpression... classExpressions) {
        return dataFactory
                .getOWLDisjointClassesAxiom(new HashSet<OWLClassExpression>(
                        Arrays.asList(classExpressions)), a);
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
            OWLObjectPropertyExpression subProperty,
            OWLObjectPropertyExpression superProperty) {
        return dataFactory.getOWLSubObjectPropertyOfAxiom(subProperty,
                superProperty);
    }

    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
            List<? extends OWLObjectPropertyExpression> chain,
            OWLObjectPropertyExpression superProperty) {
        return dataFactory.getOWLSubPropertyChainOfAxiom(chain, superProperty);
    }

    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
            List<? extends OWLObjectPropertyExpression> chain,
            OWLObjectPropertyExpression superProperty, Set<OWLAnnotation> a) {
        return dataFactory.getOWLSubPropertyChainOfAxiom(chain, superProperty,
                a);
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
            OWLObjectPropertyExpression subProperty,
            OWLObjectPropertyExpression superProperty, Set<OWLAnnotation> a) {
        return dataFactory.getOWLSubObjectPropertyOfAxiom(subProperty,
                superProperty, a);
    }

    public static OWLEquivalentObjectPropertiesAxiom
            EquivalentObjectProperties(
                    OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentObjectPropertiesAxiom(properties);
    }

    public static OWLEquivalentObjectPropertiesAxiom
            EquivalentObjectProperties(Set<OWLAnnotation> a,
                    OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentObjectPropertiesAxiom(
                new HashSet<OWLObjectPropertyExpression>(Arrays
                        .asList(properties)), a);
    }

    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
            OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLDisjointObjectPropertiesAxiom(properties);
    }

    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
            Set<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLDisjointObjectPropertiesAxiom(
                new HashSet<OWLObjectPropertyExpression>(Arrays
                        .asList(properties)), a);
    }

    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(
            OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {
        return dataFactory.getOWLInverseObjectPropertiesAxiom(peA, peB);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
            OWLObjectPropertyExpression property, OWLClassExpression domain) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(property, domain);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
            OWLObjectPropertyExpression property, OWLClassExpression domain,
            Set<OWLAnnotation> a) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(property, domain, a);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
            OWLObjectPropertyExpression property, OWLClassExpression range) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(property, range);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
            OWLObjectPropertyExpression property, OWLClassExpression range,
            Set<OWLAnnotation> a) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(property, range, a);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLFunctionalObjectPropertyAxiom(property);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLFunctionalObjectPropertyAxiom(property, a);
    }

    public static
            OWLInverseFunctionalObjectPropertyAxiom
            InverseFunctionalObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(property);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom
            InverseFunctionalObjectProperty(
                    OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(property,
                a);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLReflexiveObjectPropertyAxiom(property);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLReflexiveObjectPropertyAxiom(property, a);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(property);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(property, a);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLSymmetricObjectPropertyAxiom(property);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLSymmetricObjectPropertyAxiom(property, a);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLAsymmetricObjectPropertyAxiom(property);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLAsymmetricObjectPropertyAxiom(property, a);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
            OWLObjectPropertyExpression property) {
        return dataFactory.getOWLTransitiveObjectPropertyAxiom(property);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
            OWLObjectPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLTransitiveObjectPropertyAxiom(property, a);
    }

    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(
            OWLDataPropertyExpression subProperty,
            OWLDataPropertyExpression superProperty) {
        return dataFactory.getOWLSubDataPropertyOfAxiom(subProperty,
                superProperty);
    }

    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(
            OWLDataPropertyExpression subProperty,
            OWLDataPropertyExpression superProperty, Set<OWLAnnotation> a) {
        return dataFactory.getOWLSubDataPropertyOfAxiom(subProperty,
                superProperty, a);
    }

    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
            OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentDataPropertiesAxiom(properties);
    }

    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
            Set<OWLAnnotation> a, OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentDataPropertiesAxiom(
                new HashSet<OWLDataPropertyExpression>(Arrays
                        .asList(properties)), a);
    }

    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(
            OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLDisjointDataPropertiesAxiom(properties);
    }

    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(
            Set<OWLAnnotation> a, OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLDisjointDataPropertiesAxiom(
                new HashSet<OWLDataPropertyExpression>(Arrays
                        .asList(properties)), a);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(
            OWLDataPropertyExpression property, OWLClassExpression domain) {
        return dataFactory.getOWLDataPropertyDomainAxiom(property, domain);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(
            OWLDataPropertyExpression property, OWLClassExpression domain,
            Set<OWLAnnotation> a) {
        return dataFactory.getOWLDataPropertyDomainAxiom(property, domain, a);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(
            OWLDataPropertyExpression property, OWLDataRange range) {
        return dataFactory.getOWLDataPropertyRangeAxiom(property, range);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(
            OWLDataPropertyExpression property, OWLDataRange range,
            Set<OWLAnnotation> a) {
        return dataFactory.getOWLDataPropertyRangeAxiom(property, range, a);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
            OWLDataPropertyExpression property) {
        return dataFactory.getOWLFunctionalDataPropertyAxiom(property);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
            OWLDataPropertyExpression property, Set<OWLAnnotation> a) {
        return dataFactory.getOWLFunctionalDataPropertyAxiom(property, a);
    }

    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(
            OWLDatatype datatype, OWLDataRange dataRange) {
        return dataFactory.getOWLDatatypeDefinitionAxiom(datatype, dataRange);
    }

    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression,
            OWLPropertyExpression<?, ?>... propertyExpressions) {
        return dataFactory.getOWLHasKeyAxiom(classExpression,
                propertyExpressions);
    }

    public static OWLHasKeyAxiom HasKey(Set<OWLAnnotation> a,
            OWLClassExpression classExpression,
            OWLPropertyExpression<?, ?>... propertyExpressions) {
        return dataFactory.getOWLHasKeyAxiom(
                classExpression,
                new HashSet<OWLPropertyExpression<?, ?>>(Arrays
                        .asList(propertyExpressions)), a);
    }

    public static OWLSameIndividualAxiom SameIndividual(
            OWLIndividual... individuals) {
        return dataFactory.getOWLSameIndividualAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(
            OWLIndividual... individuals) {
        return dataFactory.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce,
            OWLIndividual ind, Set<OWLAnnotation> a) {
        return dataFactory.getOWLClassAssertionAxiom(ce, ind, a);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce,
            OWLIndividual ind) {
        return dataFactory.getOWLClassAssertionAxiom(ce, ind);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
            OWLObjectPropertyExpression property, OWLIndividual source,
            OWLIndividual target) {
        return dataFactory.getOWLObjectPropertyAssertionAxiom(property, source,
                target);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
            OWLObjectPropertyExpression property, OWLIndividual source,
            OWLIndividual target, Set<OWLAnnotation> a) {
        return dataFactory.getOWLObjectPropertyAssertionAxiom(property, source,
                target, a);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom
            NegativeObjectPropertyAssertion(
                    OWLObjectPropertyExpression property, OWLIndividual source,
                    OWLIndividual target) {
        return dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(property,
                source, target);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom
            NegativeObjectPropertyAssertion(
                    OWLObjectPropertyExpression property, OWLIndividual source,
                    OWLIndividual target, Set<OWLAnnotation> a) {
        return dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(property,
                source, target, a);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
            OWLDataPropertyExpression property, OWLIndividual source,
            OWLLiteral target) {
        return dataFactory.getOWLDataPropertyAssertionAxiom(property, source,
                target);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
            OWLDataPropertyExpression property, OWLIndividual source,
            OWLLiteral target, Set<OWLAnnotation> a) {
        return dataFactory.getOWLDataPropertyAssertionAxiom(property, source,
                target, a);
    }

    public static OWLNegativeDataPropertyAssertionAxiom
            NegativeDataPropertyAssertion(OWLDataPropertyExpression property,
                    OWLIndividual source, OWLLiteral target) {
        return dataFactory.getOWLNegativeDataPropertyAssertionAxiom(property,
                source, target);
    }

    public static OWLNegativeDataPropertyAssertionAxiom
            NegativeDataPropertyAssertion(OWLDataPropertyExpression property,
                    OWLIndividual source, OWLLiteral target,
                    Set<OWLAnnotation> a) {
        return dataFactory.getOWLNegativeDataPropertyAssertionAxiom(property,
                source, target, a);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(
            OWLAnnotationProperty property, OWLAnnotationSubject subject,
            OWLAnnotationValue value) {
        return dataFactory.getOWLAnnotationAssertionAxiom(property, subject,
                value);
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty property,
            OWLAnnotationValue value) {
        return dataFactory.getOWLAnnotation(property, value);
    }

    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(
            OWLAnnotationProperty subProperty,
            OWLAnnotationProperty superProperty) {
        return dataFactory.getOWLSubAnnotationPropertyOfAxiom(subProperty,
                superProperty);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
            OWLAnnotationProperty property, IRI iri) {
        return dataFactory.getOWLAnnotationPropertyDomainAxiom(property, iri);
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
            OWLAnnotationProperty property, IRI iri) {
        return dataFactory.getOWLAnnotationPropertyRangeAxiom(property, iri);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
            OWLAnnotationProperty property, String iri) {
        return dataFactory.getOWLAnnotationPropertyDomainAxiom(property,
                IRI(iri));
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
            OWLAnnotationProperty property, String iri) {
        return dataFactory.getOWLAnnotationPropertyRangeAxiom(property,
                IRI(iri));
    }

    public static IRI IRI(String iri) {
        return IRI.create(iri);
    }

    public static OWLLiteral PlainLiteral(String literal) {
        return dataFactory.getOWLLiteral(literal, "");
    }

    public static OWLDatatype PlainLiteral() {
        return dataFactory.getRDFPlainLiteral();
    }

    public static OWLLiteral Literal(String literal, String lang) {
        return dataFactory.getOWLLiteral(literal, lang);
    }

    public static OWLLiteral Literal(String literal, OWLDatatype type) {
        return dataFactory.getOWLLiteral(literal, type);
    }

    public static OWLLiteral Literal(String literal, OWL2Datatype type) {
        return dataFactory.getOWLLiteral(literal, type);
    }

    public static OWLLiteral Literal(String literal) {
        return dataFactory.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(boolean literal) {
        return dataFactory.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(int literal) {
        return dataFactory.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(double literal) {
        return dataFactory.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(float literal) {
        return dataFactory.getOWLLiteral(literal);
    }

    public static OWLOntology Ontology(OWLOntologyManager man,
            OWLAxiom... axioms) throws OWLOntologyCreationException {
        return man.createOntology(CollectionFactory.createSet(axioms));
    }
}
