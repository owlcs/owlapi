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
package org.semanticweb.owlapi6;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataRange;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;

/**
 * A utility class whose methods may be statically imported so that OWL API objects can be
 * constructed by writing code that looks like the OWL 2 Functional Syntax. <br>
 * Note that this class is primarily intended for developers who need to write test cases. Normal
 * client code should probably use an {@link org.semanticweb.owlapi6.model.OWLDataFactory} for
 * creating objects.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
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
        return Class(DF.getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLObjectProperty createObjectProperty() {
        return ObjectProperty(DF.getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLDataProperty createDataProperty() {
        return DataProperty(DF.getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLNamedIndividual createIndividual() {
        return NamedIndividual(DF.getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLAnnotationProperty createAnnotationProperty() {
        return AnnotationProperty(DF.getNextDocumentIRI(URNTESTS_URI));
    }

    public static OWLLiteral createOWLLiteral() {
        return Literal("Test" + System.currentTimeMillis(),
            Datatype(DF.getNextDocumentIRI(URNTESTS_URI)));
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

    public static OWLAnnotationProperty AnnotationProperty(String iri, PrefixManager pm) {
        return DF.getOWLAnnotationProperty(iri, pm);
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

    public static OWLNamedIndividual NamedIndividual(String iri, PrefixManager pm) {
        return DF.getOWLNamedIndividual(iri, pm);
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
        return DF.getOWLDeclarationAxiom(entity, Arrays.asList(a));
    }

    // Class Expressions
    public static OWLObjectIntersectionOf ObjectIntersectionOf(OWLClassExpression... c) {
        return DF.getOWLObjectIntersectionOf(c);
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
        OWLIndividual i) {
        return DF.getOWLObjectHasValue(pe, i);
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
        return DF.getOWLDisjointUnionAxiom(cls, Arrays.asList(classExpressions));
    }

    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a,
        OWLClassExpression... classExpressions) {
        return DF.getOWLDisjointClassesAxiom(Arrays.asList(classExpressions), a);
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(
        OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {
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
        OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty,
        Collection<OWLAnnotation> a) {
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

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLObjectPropertyExpression p,
        OWLClassExpression domain) {
        return DF.getOWLObjectPropertyDomainAxiom(p, domain);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLObjectPropertyExpression p,
        OWLClassExpression domain, Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyDomainAxiom(p, domain, a);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLObjectPropertyExpression p,
        OWLClassExpression range) {
        return DF.getOWLObjectPropertyRangeAxiom(p, range);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLObjectPropertyExpression p,
        OWLClassExpression range, Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyRangeAxiom(p, range, a);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLFunctionalObjectPropertyAxiom(p);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLFunctionalObjectPropertyAxiom(p, a);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(p);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLInverseFunctionalObjectPropertyAxiom(p, a);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLReflexiveObjectPropertyAxiom(p);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLReflexiveObjectPropertyAxiom(p, a);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(p);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLIrreflexiveObjectPropertyAxiom(p, a);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLSymmetricObjectPropertyAxiom(p);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLSymmetricObjectPropertyAxiom(p, a);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(p);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLAsymmetricObjectPropertyAxiom(p, a);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
        OWLObjectPropertyExpression p) {
        return DF.getOWLTransitiveObjectPropertyAxiom(p);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(
        OWLObjectPropertyExpression p, Collection<OWLAnnotation> a) {
        return DF.getOWLTransitiveObjectPropertyAxiom(p, a);
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

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression p,
        OWLClassExpression domain) {
        return DF.getOWLDataPropertyDomainAxiom(p, domain);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression p,
        OWLClassExpression domain, Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyDomainAxiom(p, domain, a);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression p,
        OWLDataRange range) {
        return DF.getOWLDataPropertyRangeAxiom(p, range);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression p,
        OWLDataRange range, Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyRangeAxiom(p, range, a);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(
        OWLDataPropertyExpression p) {
        return DF.getOWLFunctionalDataPropertyAxiom(p);
    }

    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLDataPropertyExpression p,
        Collection<OWLAnnotation> a) {
        return DF.getOWLFunctionalDataPropertyAxiom(p, a);
    }

    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLDatatype datatype,
        OWLDataRange dataRange) {
        return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange);
    }

    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression,
        OWLPropertyExpression... pExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression, pExpressions);
    }

    public static OWLHasKeyAxiom HasKey(Collection<OWLAnnotation> a,
        OWLClassExpression classExpression, OWLPropertyExpression... pExpressions) {
        return DF.getOWLHasKeyAxiom(classExpression, Arrays.asList(pExpressions), a);
    }

    public static OWLSameIndividualAxiom SameIndividual(OWLIndividual... individuals) {
        return DF.getOWLSameIndividualAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLIndividual... individuals) {
        return DF.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLSameIndividualAxiom SameIndividual(
        Collection<? extends OWLIndividual> individuals, Collection<OWLAnnotation> a) {
        return DF.getOWLSameIndividualAxiom(individuals, a);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(
        Collection<? extends OWLIndividual> individuals) {
        return DF.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(
        Collection<? extends OWLIndividual> individuals, Collection<OWLAnnotation> a) {
        return DF.getOWLDifferentIndividualsAxiom(individuals, a);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind,
        Collection<OWLAnnotation> a) {
        return DF.getOWLClassAssertionAxiom(ce, ind, a);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind) {
        return DF.getOWLClassAssertionAxiom(ce, ind);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
        OWLObjectPropertyExpression p, OWLIndividual source, OWLIndividual target) {
        return DF.getOWLObjectPropertyAssertionAxiom(p, source, target);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(
        OWLObjectPropertyExpression p, OWLIndividual source, OWLIndividual target,
        Collection<OWLAnnotation> a) {
        return DF.getOWLObjectPropertyAssertionAxiom(p, source, target, a);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(
        OWLObjectPropertyExpression p, OWLIndividual source, OWLIndividual target) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(p, source, target);
    }

    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(
        OWLObjectPropertyExpression p, OWLIndividual source, OWLIndividual target,
        Collection<OWLAnnotation> a) {
        return DF.getOWLNegativeObjectPropertyAssertionAxiom(p, source, target, a);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLDataPropertyExpression p,
        OWLIndividual source, OWLLiteral target) {
        return DF.getOWLDataPropertyAssertionAxiom(p, source, target);
    }

    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLDataPropertyExpression p,
        OWLIndividual source, OWLLiteral target, Collection<OWLAnnotation> a) {
        return DF.getOWLDataPropertyAssertionAxiom(p, source, target, a);
    }

    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(
        OWLDataPropertyExpression p, OWLIndividual source, OWLLiteral target) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(p, source, target);
    }

    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(
        OWLDataPropertyExpression p, OWLIndividual source, OWLLiteral target,
        Collection<OWLAnnotation> a) {
        return DF.getOWLNegativeDataPropertyAssertionAxiom(p, source, target, a);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty p,
        OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return DF.getOWLAnnotationAssertionAxiom(p, subject, value);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty p,
        OWLAnnotationSubject subject, OWLAnnotationValue value, Collection<OWLAnnotation> set) {
        return DF.getOWLAnnotationAssertionAxiom(p, subject, value, set);
    }

    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty p,
        OWLAnnotationSubject subject, OWLAnnotationValue value, OWLAnnotation... set) {
        return DF.getOWLAnnotationAssertionAxiom(p, subject, value, Arrays.asList(set));
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty p, OWLAnnotationValue value) {
        return DF.getOWLAnnotation(p, value);
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty p, OWLAnnotationValue value,
        Collection<OWLAnnotation> anns) {
        if (anns.isEmpty()) {
            return DF.getOWLAnnotation(p, value);
        }
        return DF.getOWLAnnotation(p, value, anns.stream());
    }

    public static OWLAnnotation Annotation(OWLAnnotationProperty p, OWLAnnotationValue value,
        OWLAnnotation... anns) {
        if (anns.length == 0) {
            return DF.getOWLAnnotation(p, value);
        }
        return DF.getOWLAnnotation(p, value, Stream.of(anns));
    }

    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(
        OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {
        return DF.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty p,
        IRI iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(p, iri);
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty p,
        IRI iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(p, iri);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty p,
        String iri) {
        return DF.getOWLAnnotationPropertyDomainAxiom(p, IRI(iri));
    }

    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty p,
        String iri) {
        return DF.getOWLAnnotationPropertyRangeAxiom(p, IRI(iri));
    }

    public static IRI IRI(String iri) {
        return DF.getIRI(iri);
    }

    public static IRI IRI(String ns, String fragment) {
        return DF.getIRI(ns, fragment);
    }

    public static OWLLiteral PlainLiteral(String literal) {
        return DF.getOWLLiteral(literal, "");
    }

    public static OWLDatatype PlainLiteral() {
        return DF.getRDFPlainLiteral();
    }

    public static OWLLiteral Literal(String literal, String lang) {
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
            return man.createOntology(Arrays.asList(axioms));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
