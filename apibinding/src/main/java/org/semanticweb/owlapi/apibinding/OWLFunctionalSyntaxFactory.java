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

import static org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase.getNextDocumentIRI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

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

/**
 * A utility class whose methods may be statically imported so that OWL API
 * objects can be constructed by writing code that looks like the OWL 2
 * Functional Syntax. <br>
 * Note that this class is primarily intended for developers who need to write
 * test cases. Normal client code should probably use an
 * {@link org.semanticweb.owlapi.model.OWLDataFactory} for creating objects.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public final class OWLFunctionalSyntaxFactory {

    private static final String URNTESTS_URI = "urn:tests#uri";
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();

    private OWLFunctionalSyntaxFactory() {}

    @Nonnull
    public static OWLImportsDeclaration ImportsDeclaration(@Nonnull IRI i) {
        return DF.getOWLImportsDeclaration(i);
    }

    // Entities
    @Nonnull
    public static OWLClass Class(@Nonnull IRI iri) {
        return DF.getOWLClass(iri);
    }

    @Nonnull
    public static OWLClass createClass() {
        return Class(getNextDocumentIRI(URNTESTS_URI));
    }

    @Nonnull
    public static OWLObjectProperty createObjectProperty() {
        return ObjectProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    @Nonnull
    public static OWLDataProperty createDataProperty() {
        return DataProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    @Nonnull
    public static OWLNamedIndividual createIndividual() {
        return NamedIndividual(getNextDocumentIRI(URNTESTS_URI));
    }

    @Nonnull
    public static OWLAnnotationProperty createAnnotationProperty() {
        return AnnotationProperty(getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLLiteral createOWLLiteral() {
        return Literal("Test" + System.currentTimeMillis(),
                Datatype(getNextDocumentIRI(URNTESTS_URI)));
    }

    @Nonnull
    public static OWLClass Class(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager pm) {
        return DF.getOWLClass(abbreviatedIRI, pm);
    }

    @Nonnull
    public static OWLAnnotationProperty RDFSComment() {
        return DF.getRDFSComment();
    }

    @Nonnull
    public static OWLAnnotationProperty RDFSLabel() {
        return DF.getRDFSLabel();
    }

    @Nonnull
    public static OWLDatatype TopDatatype() {
        return DF.getTopDatatype();
    }

    @Nonnull
    public static OWLClass OWLThing() {
        return DF.getOWLThing();
    }

    @Nonnull
    public static OWLDatatype Integer() {
        return DF.getIntegerOWLDatatype();
    }

    @Nonnull
    public static OWLDatatype Double() {
        return DF.getDoubleOWLDatatype();
    }

    @Nonnull
    public static OWLDatatype Float() {
        return DF.getFloatOWLDatatype();
    }

    @Nonnull
    public static OWLDatatype Boolean() {
        return DF.getBooleanOWLDatatype();
    }

    @Nonnull
    public static OWLClass OWLNothing() {
        return DF.getOWLNothing();
    }

    @Nonnull
    public static OWLObjectProperty ObjectProperty(@Nonnull IRI iri) {
        return DF.getOWLObjectProperty(iri);
    }

    @Nonnull
    public static OWLObjectProperty ObjectProperty(
            @Nonnull String abbreviatedIRI, @Nonnull PrefixManager pm) {
        return DF.getOWLObjectProperty(abbreviatedIRI, pm);
    }

    @Nonnull
    public static OWLObjectInverseOf ObjectInverseOf(
            @Nonnull OWLObjectPropertyExpression pe) {
        return DF.getOWLObjectInverseOf(pe);
    }

    @Nonnull
    public static OWLDataProperty DataProperty(@Nonnull IRI iri) {
        return DF.getOWLDataProperty(iri);
    }

    @Nonnull
    public static OWLDataProperty DataProperty(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager pm) {
        return DF.getOWLDataProperty(abbreviatedIRI, pm);
    }

    @Nonnull
    public static OWLAnnotationProperty AnnotationProperty(@Nonnull IRI iri) {
        return DF.getOWLAnnotationProperty(iri);
    }

    @Nonnull
    public static OWLAnnotationProperty AnnotationProperty(
            @Nonnull String abbreviatedIRI, @Nonnull PrefixManager pm) {
        return DF.getOWLAnnotationProperty(abbreviatedIRI, pm);
    }

    @Nonnull
    public static OWLNamedIndividual NamedIndividual(@Nonnull IRI iri) {
        return DF.getOWLNamedIndividual(iri);
    }

    @Nonnull
    public static OWLAnonymousIndividual AnonymousIndividual() {
        return DF.getOWLAnonymousIndividual();
    }

    @Nonnull
    public static OWLAnonymousIndividual
            AnonymousIndividual(@Nonnull String id) {
        return DF.getOWLAnonymousIndividual(id);
    }

    @Nonnull
    public static OWLNamedIndividual NamedIndividual(
            @Nonnull String abbreviatedIRI, @Nonnull PrefixManager pm) {
        return DF.getOWLNamedIndividual(abbreviatedIRI, pm);
    }

    @Nonnull
    public static OWLDatatype Datatype(@Nonnull IRI iri) {
        return DF.getOWLDatatype(iri);
    }

    @Nonnull
    public static OWLDeclarationAxiom Declaration(@Nonnull OWLEntity entity) {
        return DF.getOWLDeclarationAxiom(entity);
    }

    @Nonnull
    public static OWLDeclarationAxiom Declaration(@Nonnull OWLEntity entity,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLDeclarationAxiom(entity, a);
    }

    // Class Expressions
    @Nonnull
    public static OWLObjectIntersectionOf ObjectIntersectionOf(
            @Nonnull OWLClassExpression... classExpressions) {
        return DF.getOWLObjectIntersectionOf(classExpressions);
    }

    @Nonnull
    public static OWLObjectUnionOf ObjectUnionOf(
            @Nonnull OWLClassExpression... classExpressions) {
        return DF.getOWLObjectUnionOf(classExpressions);
    }

    @Nonnull
    public static OWLObjectComplementOf ObjectComplementOf(
            @Nonnull OWLClassExpression classExpression) {
        return DF.getOWLObjectComplementOf(classExpression);
    }

    @Nonnull
    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(
            @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLClassExpression ce) {
        return DF.getOWLObjectSomeValuesFrom(pe, ce);
    }

    @Nonnull
    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(
            @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLClassExpression ce) {
        return DF.getOWLObjectAllValuesFrom(pe, ce);
    }

    @Nonnull
    public static OWLObjectHasValue ObjectHasValue(
            @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLIndividual individual) {
        return DF.getOWLObjectHasValue(pe, individual);
    }

    @Nonnull
    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLClassExpression ce) {
        return DF.getOWLObjectMinCardinality(cardinality, pe, ce);
    }

    @Nonnull
    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLClassExpression ce) {
        return DF.getOWLObjectMaxCardinality(cardinality, pe, ce);
    }

    @Nonnull
    public static OWLObjectExactCardinality ObjectExactCardinality(
            int cardinality, @Nonnull OWLObjectPropertyExpression pe,
            @Nonnull OWLClassExpression ce) {
        return DF.getOWLObjectExactCardinality(cardinality, pe, ce);
    }

    @Nonnull
    public static OWLObjectHasSelf ObjectHasSelf(
            @Nonnull OWLObjectPropertyExpression pe) {
        return DF.getOWLObjectHasSelf(pe);
    }

    @Nonnull
    public static OWLObjectOneOf ObjectOneOf(
            @Nonnull OWLIndividual... individuals) {
        return DF.getOWLObjectOneOf(individuals);
    }

    @Nonnull
    public static OWLDataSomeValuesFrom DataSomeValuesFrom(
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLDataRange dr) {
        return DF.getOWLDataSomeValuesFrom(pe, dr);
    }

    @Nonnull
    public static OWLDataAllValuesFrom DataAllValuesFrom(
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLDataRange dr) {
        return DF.getOWLDataAllValuesFrom(pe, dr);
    }

    @Nonnull
    public static OWLDataHasValue DataHasValue(
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLLiteral literal) {
        return DF.getOWLDataHasValue(pe, literal);
    }

    @Nonnull
    public static OWLDataMinCardinality DataMinCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLDataRange dr) {
        return DF.getOWLDataMinCardinality(cardinality, pe, dr);
    }

    @Nonnull
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLDataRange dr) {
        return DF.getOWLDataMaxCardinality(cardinality, pe, dr);
    }

    @Nonnull
    public static OWLDataExactCardinality DataExactCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression pe, @Nonnull OWLDataRange dr) {
        return DF.getOWLDataExactCardinality(cardinality, pe, dr);
    }

    // Data Ranges other than datatype
    @Nonnull
    public static OWLDataIntersectionOf DataIntersectionOf(
            @Nonnull OWLDataRange... dataRanges) {
        return DF.getOWLDataIntersectionOf(dataRanges);
    }

    @Nonnull
    public static OWLDataUnionOf DataUnionOf(
            @Nonnull OWLDataRange... dataRanges) {
        return DF.getOWLDataUnionOf(dataRanges);
    }

    @Nonnull
    public static OWLDataComplementOf DataComplementOf(
            @Nonnull OWLDataRange dataRange) {
        return DF.getOWLDataComplementOf(dataRange);
    }

    @Nonnull
    public static OWLDataOneOf DataOneOf(@Nonnull OWLLiteral... literals) {
        return DF.getOWLDataOneOf(literals);
    }

    @Nonnull
    public static OWLDatatypeRestriction DatatypeRestriction(
            @Nonnull OWLDatatype datatype,
            @Nonnull OWLFacetRestriction... facetRestrictions) {
        return DF.getOWLDatatypeRestriction(datatype, facetRestrictions);
    }

    @Nonnull
    public static OWLFacetRestriction FacetRestriction(@Nonnull OWLFacet facet,
            @Nonnull OWLLiteral facetValue) {
        return DF.getOWLFacetRestriction(facet, facetValue);
    }

    // Axioms
    @Nonnull
    public static OWLSubClassOfAxiom SubClassOf(
            @Nonnull OWLClassExpression subClass,
            @Nonnull OWLClassExpression superClass) {
        return DF.getOWLSubClassOfAxiom(subClass, superClass);
    }

    @Nonnull
    public static OWLSubClassOfAxiom SubClassOf(
            @Nonnull OWLClassExpression subClass,
            @Nonnull OWLClassExpression superClass,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLSubClassOfAxiom(subClass, superClass, a);
    }

    @Nonnull
    public static OWLEquivalentClassesAxiom EquivalentClasses(
            @Nonnull OWLClassExpression... classExpressions) {
        return DF.getOWLEquivalentClassesAxiom(classExpressions);
    }

    @Nonnull
    public static OWLEquivalentClassesAxiom EquivalentClasses(
            @Nonnull Set<OWLAnnotation> a,
            OWLClassExpression... classExpressions) {
        return DF.getOWLEquivalentClassesAxiom(
                new HashSet<>(Arrays.asList(classExpressions)), a);
    }

    @Nonnull
    public static OWLDisjointClassesAxiom DisjointClasses(
            @Nonnull OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointClassesAxiom(classExpressions);
    }

    @Nonnull
    public static OWLDisjointClassesAxiom DisjointClasses(
            @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return DF.getOWLDisjointClassesAxiom(classExpressions);
    }

    @Nonnull
    public static OWLDisjointClassesAxiom DisjointClasses(
            @Nonnull Set<OWLClassExpression> classExpressions,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLDisjointClassesAxiom(classExpressions, a);
    }

    @Nonnull
    public static OWLDisjointUnionAxiom DisjointUnion(@Nonnull OWLClass cls,
            @Nonnull OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointUnionAxiom(cls,
                CollectionFactory.createSet(classExpressions));
    }

    @Nonnull
    public static OWLDisjointClassesAxiom DisjointClasses(
            @Nonnull Set<OWLAnnotation> a,
            OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointClassesAxiom(
                new HashSet<>(Arrays.asList(classExpressions)), a);
    }

    @Nonnull
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty) {
        return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
    }

    @Nonnull
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty) {
        return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty);
    }

    @Nonnull
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty, a);
    }

    @Nonnull
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, a);
    }

    @Nonnull
    public static OWLEquivalentObjectPropertiesAxiom
            EquivalentObjectProperties(
                    @Nonnull OWLObjectPropertyExpression... properties) {
        return DF.getOWLEquivalentObjectPropertiesAxiom(properties);
    }

    @Nonnull
    public static OWLEquivalentObjectPropertiesAxiom
            EquivalentObjectProperties(@Nonnull Set<OWLAnnotation> a,
                    OWLObjectPropertyExpression... properties) {
        return DF.getOWLEquivalentObjectPropertiesAxiom(
                new HashSet<>(Arrays.asList(properties)), a);
    }

    @Nonnull
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
            @Nonnull OWLObjectPropertyExpression... properties) {
        return DF.getOWLDisjointObjectPropertiesAxiom(properties);
    }

    @Nonnull
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(
            @Nonnull Set<OWLAnnotation> a,
            OWLObjectPropertyExpression... properties) {
        return DF.getOWLDisjointObjectPropertiesAxiom(
                new HashSet<>(Arrays.asList(properties)), a);
    }

    @Nonnull
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(
            @Nonnull OWLObjectPropertyExpression peA,
            @Nonnull OWLObjectPropertyExpression peB) {
        return DF.getOWLInverseObjectPropertiesAxiom(peA, peB);
    }

    @Nonnull
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression domain) {
        return DF.getOWLObjectPropertyDomainAxiom(property, domain);
    }

    @Nonnull
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression domain, @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyDomainAxiom(property, domain, a);
    }

    @Nonnull
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range) {
        return DF.getOWLObjectPropertyRangeAxiom(property, range);
    }

    @Nonnull
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range, @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyRangeAxiom(property, range, a);
    }

    @Nonnull
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLFunctionalObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLFunctionalObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLInverseFunctionalObjectPropertyAxiom
            InverseFunctionalObjectProperty(
                    @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLInverseFunctionalObjectPropertyAxiom
            InverseFunctionalObjectProperty(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLReflexiveObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLReflexiveObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLSymmetricObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLSymmetricObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property) {
        return DF.getOWLTransitiveObjectPropertyAxiom(property);
    }

    @Nonnull
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLTransitiveObjectPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty) {
        return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
    }

    @Nonnull
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, a);
    }

    @Nonnull
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
            @Nonnull OWLDataPropertyExpression... properties) {
        return DF.getOWLEquivalentDataPropertiesAxiom(properties);
    }

    @Nonnull
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(
            @Nonnull Set<OWLAnnotation> a,
            OWLDataPropertyExpression... properties) {
        return DF.getOWLEquivalentDataPropertiesAxiom(
                new HashSet<>(Arrays.asList(properties)), a);
    }

    @Nonnull
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(
            @Nonnull OWLDataPropertyExpression... properties) {
        return DF.getOWLDisjointDataPropertiesAxiom(properties);
    }

    @Nonnull
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(
            @Nonnull Set<OWLAnnotation> a,
            OWLDataPropertyExpression... properties) {
        return DF.getOWLDisjointDataPropertiesAxiom(
                new HashSet<>(Arrays.asList(properties)), a);
    }

    @Nonnull
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLClassExpression domain) {
        return DF.getOWLDataPropertyDomainAxiom(property, domain);
    }

    @Nonnull
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLClassExpression domain, @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLDataPropertyDomainAxiom(property, domain, a);
    }

    @Nonnull
    public static OWLDataPropertyRangeAxiom DataPropertyRange(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange range) {
        return DF.getOWLDataPropertyRangeAxiom(property, range);
    }

    @Nonnull
    public static OWLDataPropertyRangeAxiom DataPropertyRange(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange range, @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLDataPropertyRangeAxiom(property, range, a);
    }

    @Nonnull
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
            @Nonnull OWLDataPropertyExpression property) {
        return DF.getOWLFunctionalDataPropertyAxiom(property);
    }

    @Nonnull
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLFunctionalDataPropertyAxiom(property, a);
    }

    @Nonnull
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(
            @Nonnull OWLDatatype datatype, @Nonnull OWLDataRange dataRange) {
        return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange);
    }

    @Nonnull
    public static OWLHasKeyAxiom HasKey(
            @Nonnull OWLClassExpression classExpression,
            @Nonnull OWLPropertyExpression... propertyExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression, propertyExpressions);
    }

    @Nonnull
    public static OWLHasKeyAxiom HasKey(@Nonnull Set<OWLAnnotation> a,
            @Nonnull OWLClassExpression classExpression,
            OWLPropertyExpression... propertyExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression,
                new HashSet<>(Arrays.asList(propertyExpressions)), a);
    }

    @Nonnull
    public static OWLSameIndividualAxiom SameIndividual(
            @Nonnull OWLIndividual... individuals) {
        return DF.getOWLSameIndividualAxiom(individuals);
    }

    @Nonnull
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(
            @Nonnull OWLIndividual... individuals) {
        return DF.getOWLDifferentIndividualsAxiom(individuals);
    }

    @Nonnull
    public static OWLClassAssertionAxiom ClassAssertion(
            @Nonnull OWLClassExpression ce, @Nonnull OWLIndividual ind,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLClassAssertionAxiom(ce, ind, a);
    }

    @Nonnull
    public static OWLClassAssertionAxiom ClassAssertion(
            @Nonnull OWLClassExpression ce, @Nonnull OWLIndividual ind) {
        return DF.getOWLClassAssertionAxiom(ce, ind);
    }

    @Nonnull
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual source, @Nonnull OWLIndividual target) {
        return DF.getOWLObjectPropertyAssertionAxiom(property, source, target);
    }

    @Nonnull
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual source, @Nonnull OWLIndividual target,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyAssertionAxiom(property, source, target,
                a);
    }

    @Nonnull
    public static
            OWLNegativeObjectPropertyAssertionAxiom
            NegativeObjectPropertyAssertion(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual source, @Nonnull OWLIndividual target) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source,
                target);
    }

    @Nonnull
    public static
            OWLNegativeObjectPropertyAssertionAxiom
            NegativeObjectPropertyAssertion(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual source,
                    @Nonnull OWLIndividual target, @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source,
                target, a);
    }

    @Nonnull
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual source, @Nonnull OWLLiteral target) {
        return DF.getOWLDataPropertyAssertionAxiom(property, source, target);
    }

    @Nonnull
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual source, @Nonnull OWLLiteral target,
            @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLDataPropertyAssertionAxiom(property, source, target, a);
    }

    @Nonnull
    public static OWLNegativeDataPropertyAssertionAxiom
            NegativeDataPropertyAssertion(
                    @Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual source, @Nonnull OWLLiteral target) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source,
                target);
    }

    @Nonnull
    public static OWLNegativeDataPropertyAssertionAxiom
            NegativeDataPropertyAssertion(
                    @Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual source, @Nonnull OWLLiteral target,
                    @Nonnull Set<OWLAnnotation> a) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source,
                target, a);
    }

    @Nonnull
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotationValue value) {
        return DF.getOWLAnnotationAssertionAxiom(property, subject, value);
    }

    @Nonnull
    public static OWLAnnotation Annotation(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value) {
        return DF.getOWLAnnotation(property, value);
    }

    @Nonnull
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(
            @Nonnull OWLAnnotationProperty subProperty,
            @Nonnull OWLAnnotationProperty superProperty) {
        return DF
                .getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);
    }

    @Nonnull
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
            @Nonnull OWLAnnotationProperty property, @Nonnull IRI iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(property, iri);
    }

    @Nonnull
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
            @Nonnull OWLAnnotationProperty property, @Nonnull IRI iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(property, iri);
    }

    @Nonnull
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(
            @Nonnull OWLAnnotationProperty property, @Nonnull String iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(property, IRI(iri));
    }

    @Nonnull
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(
            @Nonnull OWLAnnotationProperty property, @Nonnull String iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(property, IRI(iri));
    }

    @Nonnull
    public static IRI IRI(@Nonnull String iri) {
        return IRI.create(iri);
    }

    @Nonnull
    public static IRI IRI(String ns, String fragment) {
        return IRI.create(ns, fragment);
    }

    @Nonnull
    public static OWLLiteral PlainLiteral(@Nonnull String literal) {
        return DF.getOWLLiteral(literal, "");
    }

    @Nonnull
    public static OWLDatatype PlainLiteral() {
        return DF.getRDFPlainLiteral();
    }

    @Nonnull
    public static OWLLiteral Literal(@Nonnull String literal, String lang) {
        return DF.getOWLLiteral(literal, lang);
    }

    @Nonnull
    public static OWLLiteral Literal(@Nonnull String literal,
            @Nonnull OWLDatatype type) {
        return DF.getOWLLiteral(literal, type);
    }

    @Nonnull
    public static OWLLiteral Literal(@Nonnull String literal,
            @Nonnull OWL2Datatype type) {
        return DF.getOWLLiteral(literal, type);
    }

    @Nonnull
    public static OWLLiteral Literal(@Nonnull String literal) {
        return DF.getOWLLiteral(literal);
    }

    @Nonnull
    public static OWLLiteral Literal(boolean literal) {
        return DF.getOWLLiteral(literal);
    }

    @Nonnull
    public static OWLLiteral Literal(int literal) {
        return DF.getOWLLiteral(literal);
    }

    @Nonnull
    public static OWLLiteral Literal(double literal) {
        return DF.getOWLLiteral(literal);
    }

    @Nonnull
    public static OWLLiteral Literal(float literal) {
        return DF.getOWLLiteral(literal);
    }

    @Nonnull
    public static OWLOntology Ontology(@Nonnull OWLOntologyManager man,
            @Nonnull OWLAxiom... axioms) {
        try {
            return man.createOntology(CollectionFactory.createSet(axioms));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
