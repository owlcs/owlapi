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
package org.semanticweb.owlapi.apibinding;

import static org.semanticweb.owlapi.model.IRI.getNextDocumentIRI;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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
import org.semanticweb.owlapi.model.OWLRuntimeException;
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

import com.google.common.collect.Sets;

/**
 * A utility class whose methods may be statically imported so that OWL API objects can be
 * constructed by writing code that looks like the OWL 2 Functional Syntax. <br>
 * Note that this class is primarily intended for developers who need to write test cases. Normal
 * client code should probably use an {@link org.semanticweb.owlapi.model.OWLDataFactory} for
 * creating objects.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public final class OWLFunctionalSyntaxFactory {

    private static final String URNTESTS_URI = "urn:tests#uri";
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();

    private OWLFunctionalSyntaxFactory() {}

    public static OWLImportsDeclaration ImportsDeclaration(IRI i) {
        return DF.getOWLImportsDeclaration(i);
    }

    // Entities
    public static OWLClass Class(IRI iri) {
        return DF.getOWLClass(iri);
    }

    public static OWLClass createClass() {
        return Class(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLObjectProperty createObjectProperty() {
        return ObjectProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLDataProperty createDataProperty() {
        return DataProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLNamedIndividual createIndividual() {
        return NamedIndividual(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLAnnotationProperty createAnnotationProperty() {
        return AnnotationProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLLiteral createOWLLiteral() {
        return Literal("Test" + System.currentTimeMillis(),
            Datatype(getNextDocumentIRI(URNTESTS_URI)));
    }

    public static OWLClass Class(String abbreviatedIRI, PrefixManager pm) {
        return DF.getOWLClass(abbreviatedIRI, pm);
    }

    public static OWLAnnotationProperty RDFSComment() {
        return DF.getRDFSComment();
    }

    public static OWLAnnotationProperty RDFSLabel() {
        return DF.getRDFSLabel();
    }

    public static OWLDatatype TopDatatype() {
        return DF.getTopDatatype();
    }

    public static OWLClass OWLThing() {
        return DF.getOWLThing();
    }

    public static OWLDatatype Integer() {
        return DF.getIntegerOWLDatatype();
    }

    public static OWLDatatype Double() {
        return DF.getDoubleOWLDatatype();
    }

    public static OWLDatatype Float() {
        return DF.getFloatOWLDatatype();
    }

    public static OWLDatatype Boolean() {
        return DF.getBooleanOWLDatatype();
    }

    public static OWLClass OWLNothing() {
        return DF.getOWLNothing();
    }

    public static OWLObjectProperty ObjectProperty(IRI iri) {
        return DF.getOWLObjectProperty(iri);
    }

    public static OWLObjectProperty ObjectProperty(String abbreviatedIRI, PrefixManager pm) {
        return DF.getOWLObjectProperty(abbreviatedIRI, pm);
    }

    public static OWLObjectInverseOf ObjectInverseOf(OWLObjectProperty pe) {
        return DF.getOWLObjectInverseOf(pe);
    }

    public static OWLDataProperty DataProperty(IRI iri) {
        return DF.getOWLDataProperty(iri);
    }

    public static OWLDataProperty DataProperty(String abbreviatedIRI, PrefixManager pm) {
        return DF.getOWLDataProperty(abbreviatedIRI, pm);
    }

    public static OWLAnnotationProperty AnnotationProperty(IRI iri) {
        return DF.getOWLAnnotationProperty(iri);
    }

    public static OWLAnnotationProperty AnnotationProperty(String abbreviatedIRI,
        PrefixManager pm) {
        return DF.getOWLAnnotationProperty(abbreviatedIRI, pm);
    }

    public static OWLNamedIndividual NamedIndividual(IRI iri) {
        return DF.getOWLNamedIndividual(iri);
    }

    public static OWLAnonymousIndividual AnonymousIndividual() {
        return DF.getOWLAnonymousIndividual();
    }

    public static OWLAnonymousIndividual AnonymousIndividual(String id) {
        return DF.getOWLAnonymousIndividual(id);
    }

    public static OWLNamedIndividual NamedIndividual(String abbreviatedIRI, PrefixManager pm) {
        return DF.getOWLNamedIndividual(abbreviatedIRI, pm);
    }

    public static OWLDatatype Datatype(IRI iri) {
        return DF.getOWLDatatype(iri);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity) {
        return DF.getOWLDeclarationAxiom(entity);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity, Collection<OWLAnnotation> a) {
        return DF.getOWLDeclarationAxiom(entity, a);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity, OWLAnnotation... a) {
        return DF.getOWLDeclarationAxiom(entity, Sets.newHashSet(a));
    }

    // Class Expressions
    public static OWLObjectIntersectionOf ObjectIntersectionOf(
        OWLClassExpression... classExpressions) {
        return DF.getOWLObjectIntersectionOf(classExpressions);
    }

    public static OWLObjectUnionOf ObjectUnionOf(OWLClassExpression... classExpressions) {
        return DF.getOWLObjectUnionOf(classExpressions);
    }

    public static OWLObjectComplementOf ObjectComplementOf(OWLClassExpression classExpression) {
        return DF.getOWLObjectComplementOf(classExpression);
    }

    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(OWLObjectPropertyExpression pe,
        OWLClassExpression ce) {
        return DF.getOWLObjectSomeValuesFrom(pe, ce);
    }

    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(OWLObjectPropertyExpression pe,
        OWLClassExpression ce) {
        return DF.getOWLObjectAllValuesFrom(pe, ce);
    }

    public static OWLObjectHasValue ObjectHasValue(OWLObjectPropertyExpression pe,
        OWLIndividual individual) {
        return DF.getOWLObjectHasValue(pe, individual);
    }

    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return DF.getOWLObjectMinCardinality(cardinality, pe, ce);
    }

    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return DF.getOWLObjectMaxCardinality(cardinality, pe, ce);
    }

    public static OWLObjectExactCardinality ObjectExactCardinality(int cardinality,
        OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return DF.getOWLObjectExactCardinality(cardinality, pe, ce);
    }

    public static OWLObjectHasSelf ObjectHasSelf(OWLObjectPropertyExpression pe) {
        return DF.getOWLObjectHasSelf(pe);
    }

    public static OWLObjectOneOf ObjectOneOf(OWLIndividual... individuals) {
        return DF.getOWLObjectOneOf(individuals);
    }

    public static OWLDataSomeValuesFrom DataSomeValuesFrom(OWLDataPropertyExpression pe,
        OWLDataRange dr) {
        return DF.getOWLDataSomeValuesFrom(pe, dr);
    }

    public static OWLDataAllValuesFrom DataAllValuesFrom(OWLDataPropertyExpression pe,
        OWLDataRange dr) {
        return DF.getOWLDataAllValuesFrom(pe, dr);
    }

    public static OWLDataHasValue DataHasValue(OWLDataPropertyExpression pe, OWLLiteral literal) {
        return DF.getOWLDataHasValue(pe, literal);
    }

    public static OWLDataMinCardinality DataMinCardinality(int cardinality,
        OWLDataPropertyExpression pe, OWLDataRange dr) {
        return DF.getOWLDataMinCardinality(cardinality, pe, dr);
    }

    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality,
        OWLDataPropertyExpression pe, OWLDataRange dr) {
        return DF.getOWLDataMaxCardinality(cardinality, pe, dr);
    }

    public static OWLDataExactCardinality DataExactCardinality(int cardinality,
        OWLDataPropertyExpression pe, OWLDataRange dr) {
        return DF.getOWLDataExactCardinality(cardinality, pe, dr);
    }

    // Data Ranges other than datatype
    public static OWLDataIntersectionOf DataIntersectionOf(OWLDataRange... dataRanges) {
        return DF.getOWLDataIntersectionOf(dataRanges);
    }

    public static OWLDataUnionOf DataUnionOf(OWLDataRange... dataRanges) {
        return DF.getOWLDataUnionOf(dataRanges);
    }

    public static OWLDataComplementOf DataComplementOf(OWLDataRange dataRange) {
        return DF.getOWLDataComplementOf(dataRange);
    }

    public static OWLDataOneOf DataOneOf(OWLLiteral... literals) {
        return DF.getOWLDataOneOf(literals);
    }

    public static OWLDatatypeRestriction DatatypeRestriction(OWLDatatype datatype,
        OWLFacetRestriction... facetRestrictions) {
        return DF.getOWLDatatypeRestriction(datatype, facetRestrictions);
    }

    public static OWLFacetRestriction FacetRestriction(OWLFacet facet, OWLLiteral facetValue) {
        return DF.getOWLFacetRestriction(facet, facetValue);
    }

    // Axioms
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass,
        OWLClassExpression superClass) {
        return DF.getOWLSubClassOfAxiom(subClass, superClass);
    }

    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass,
        OWLClassExpression superClass, Collection<OWLAnnotation> a) {
        return DF.getOWLSubClassOfAxiom(subClass, superClass, a);
    }

    public static OWLEquivalentClassesAxiom EquivalentClasses(
        OWLClassExpression... classExpressions) {
        return DF.getOWLEquivalentClassesAxiom(classExpressions);
    }

    public static OWLEquivalentClassesAxiom EquivalentClasses(Collection<OWLAnnotation> a,
        OWLClassExpression... classExpressions) {
        return DF.getOWLEquivalentClassesAxiom(Arrays.asList(classExpressions), a);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointClassesAxiom(classExpressions);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(
        Collection<? extends OWLClassExpression> classExpressions) {
        return DF.getOWLDisjointClassesAxiom(classExpressions);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(
        Collection<OWLClassExpression> classExpressions, Collection<OWLAnnotation> a) {
        return DF.getOWLDisjointClassesAxiom(classExpressions, a);
    }

    public static OWLDisjointUnionAxiom DisjointUnion(OWLClass cls,
        OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointUnionAxiom(cls, CollectionFactory.createSet(classExpressions));
    }

    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a,
        OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointClassesAxiom(Arrays.asList(classExpressions), a);
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
        OWLObjectPropertyExpression subProperty,
        OWLObjectPropertyExpression superProperty) {
        return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
        List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty) {
        return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty);
    }

    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
        List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty, Collection<OWLAnnotation> a) {
        return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty, a);
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
        OWLObjectPropertyExpression subProperty,
        OWLObjectPropertyExpression superProperty, Collection<OWLAnnotation> a) {
        return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, a);
    }

    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(
        OWLObjectPropertyExpression... properties) {
        return DF.getOWLEquivalentObjectPropertiesAxiom(properties);
    }

    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(
        Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {
        return DF.getOWLEquivalentObjectPropertiesAxiom(Arrays.asList(properties), a);
    }

    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
        OWLObjectPropertyExpression... properties) {
        return DF.getOWLDisjointObjectPropertiesAxiom(properties);
    }

    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
        Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {
        return DF.getOWLDisjointObjectPropertiesAxiom(Arrays.asList(properties), a);
    }

    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(
        OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {
        return DF.getOWLInverseObjectPropertiesAxiom(peA, peB);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
        OWLObjectPropertyExpression property, OWLClassExpression domain) {
        return DF.getOWLObjectPropertyDomainAxiom(property, domain);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
        OWLObjectPropertyExpression property, OWLClassExpression domain,
        Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyDomainAxiom(property, domain, a);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
        OWLObjectPropertyExpression property, OWLClassExpression range) {
        return DF.getOWLObjectPropertyRangeAxiom(property, range);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
        OWLObjectPropertyExpression property, OWLClassExpression range,
        Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyRangeAxiom(property, range, a);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLFunctionalObjectPropertyAxiom(property);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLFunctionalObjectPropertyAxiom(property, a);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(property);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(property, a);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLReflexiveObjectPropertyAxiom(property);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLReflexiveObjectPropertyAxiom(property, a);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(property);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(property, a);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLSymmetricObjectPropertyAxiom(property);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLSymmetricObjectPropertyAxiom(property, a);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(property);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(property, a);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
        OWLObjectPropertyExpression property) {
        return DF.getOWLTransitiveObjectPropertyAxiom(property);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLTransitiveObjectPropertyAxiom(property, a);
    }

    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLDataPropertyExpression subProperty,
        OWLDataPropertyExpression superProperty) {
        return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLDataPropertyExpression subProperty,
        OWLDataPropertyExpression superProperty, Collection<OWLAnnotation> a) {
        return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, a);
    }

    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
        OWLDataPropertyExpression... properties) {
        return DF.getOWLEquivalentDataPropertiesAxiom(properties);
    }

    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
        Collection<OWLAnnotation> a, OWLDataPropertyExpression... properties) {
        return DF.getOWLEquivalentDataPropertiesAxiom(Arrays.asList(properties), a);
    }

    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(
        OWLDataPropertyExpression... properties) {
        return DF.getOWLDisjointDataPropertiesAxiom(properties);
    }

    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(Collection<OWLAnnotation> a,
        OWLDataPropertyExpression... properties) {
        return DF.getOWLDisjointDataPropertiesAxiom(Arrays.asList(properties), a);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression property,
        OWLClassExpression domain) {
        return DF.getOWLDataPropertyDomainAxiom(property, domain);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression property,
        OWLClassExpression domain, Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyDomainAxiom(property, domain, a);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression property,
        OWLDataRange range) {
        return DF.getOWLDataPropertyRangeAxiom(property, range);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression property,
        OWLDataRange range, Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyRangeAxiom(property, range, a);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
        OWLDataPropertyExpression property) {
        return DF.getOWLFunctionalDataPropertyAxiom(property);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
        OWLDataPropertyExpression property, Collection<OWLAnnotation> a) {
        return DF.getOWLFunctionalDataPropertyAxiom(property, a);
    }

    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLDatatype datatype,
        OWLDataRange dataRange) {
        return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange);
    }

    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression,
        OWLPropertyExpression... propertyExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression, propertyExpressions);
    }

    public static OWLHasKeyAxiom HasKey(Collection<OWLAnnotation> a,
        OWLClassExpression classExpression,
        OWLPropertyExpression... propertyExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression, Arrays.asList(propertyExpressions), a);
    }

    public static OWLSameIndividualAxiom SameIndividual(OWLIndividual... individuals) {
        return DF.getOWLSameIndividualAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLIndividual... individuals) {
        return DF.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLSameIndividualAxiom SameIndividual(
        Collection<? extends OWLIndividual> individuals) {
        return DF.getOWLSameIndividualAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(
        Collection<? extends OWLIndividual> individuals) {
        return DF.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind,
        Collection<OWLAnnotation> a) {
        return DF.getOWLClassAssertionAxiom(ce, ind, a);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind) {
        return DF.getOWLClassAssertionAxiom(ce, ind);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
        OWLObjectPropertyExpression property, OWLIndividual source,
        OWLIndividual target) {
        return DF.getOWLObjectPropertyAssertionAxiom(property, source, target);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
        OWLObjectPropertyExpression property, OWLIndividual source,
        OWLIndividual target, Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyAssertionAxiom(property, source, target, a);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(
        OWLObjectPropertyExpression property, OWLIndividual source,
        OWLIndividual target) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(
        OWLObjectPropertyExpression property, OWLIndividual source,
        OWLIndividual target, Collection<OWLAnnotation> a) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target, a);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
        OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {
        return DF.getOWLDataPropertyAssertionAxiom(property, source, target);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
        OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target,
        Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyAssertionAxiom(property, source, target, a);
    }

    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(
        OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source, target);
    }

    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(
        OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target,
        Collection<OWLAnnotation> a) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source, target, a);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property,
        OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return DF.getOWLAnnotationAssertionAxiom(property, subject, value);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property,
        OWLAnnotationSubject subject, OWLAnnotationValue value,
        Collection<OWLAnnotation> set) {
        return DF.getOWLAnnotationAssertionAxiom(property, subject, value, set);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property,
        OWLAnnotationSubject subject, OWLAnnotationValue value, OWLAnnotation... set) {
        return DF.getOWLAnnotationAssertionAxiom(property, subject, value, Sets.newHashSet(set));
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty property,
        OWLAnnotationValue value) {
        return DF.getOWLAnnotation(property, value);
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        Collection<OWLAnnotation> anns) {
        return DF.getOWLAnnotation(property, value, anns.stream());
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        OWLAnnotation... anns) {
        return DF.getOWLAnnotation(property, value, Stream.of(anns));
    }

    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(
        OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {
        return DF.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
        OWLAnnotationProperty property, IRI iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(property, iri);
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
        OWLAnnotationProperty property, IRI iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(property, iri);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
        OWLAnnotationProperty property, String iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(property, IRI(iri));
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
        OWLAnnotationProperty property, String iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(property, IRI(iri));
    }

    public static IRI IRI(String iri) {
        return IRI.create(iri);
    }

    public static IRI IRI(String ns, String fragment) {
        return IRI.create(ns, fragment);
    }

    public static OWLLiteral PlainLiteral(String literal) {
        return DF.getOWLLiteral(literal, "");
    }

    public static OWLDatatype PlainLiteral() {
        return DF.getRDFPlainLiteral();
    }

    public static OWLLiteral Literal(String literal, @Nullable String lang) {
        return DF.getOWLLiteral(literal, lang);
    }

    public static OWLLiteral Literal(String literal, OWLDatatype type) {
        return DF.getOWLLiteral(literal, type);
    }

    public static OWLLiteral Literal(String literal, OWL2Datatype type) {
        return DF.getOWLLiteral(literal, type);
    }

    public static OWLLiteral Literal(String literal) {
        return DF.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(boolean literal) {
        return DF.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(int literal) {
        return DF.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(double literal) {
        return DF.getOWLLiteral(literal);
    }

    public static OWLLiteral Literal(float literal) {
        return DF.getOWLLiteral(literal);
    }

    public static OWLOntology Ontology(OWLOntologyManager man, OWLAxiom... axioms) {
        try {
            return man.createOntology(CollectionFactory.createSet(axioms));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
