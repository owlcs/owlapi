package org.semanticweb.owlapi.apibinding;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.util.Arrays;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-Jan-2010
 * </p>
 * A utility class whose methods may be statically imported so that OWL API objects can be constructed by
 * writing code that looks like the OWL 2 Functional Syntax.
 * </p>
 * Note that this class is primarily intended for developers who need to write test cases.  Normal client code
 * should probably use an {@link org.semanticweb.owlapi.model.OWLDataFactory} for creating objects.
 */
public class OWLFunctionalSyntaxFactory {

    private static OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Entities
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static OWLClass Class(IRI iri) {
        return dataFactory.getOWLClass(iri);
    }

    public static OWLClass Class(String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLClass(abbreviatedIRI, pm);
    }

    public static OWLClass OWLThing() {
        return dataFactory.getOWLThing();
    }

    public static OWLClass OWLNothing() {
        return dataFactory.getOWLNothing();
    }

    public static OWLObjectProperty ObjectProperty(IRI iri) {
        return dataFactory.getOWLObjectProperty(iri);
    }

    public static OWLObjectProperty ObjectProperty(String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLObjectProperty(abbreviatedIRI, pm);
    }

    public static OWLObjectInverseOf ObjectInverseOf(OWLObjectPropertyExpression pe) {
        return dataFactory.getOWLObjectInverseOf(pe);
    }

    public static OWLDataProperty DataProperty(IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    public static OWLDataProperty DataProperty(String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLDataProperty(abbreviatedIRI, pm);
    }

    public static OWLAnnotationProperty AnnotationProperty(IRI iri) {
        return dataFactory.getOWLAnnotationProperty(iri);
    }

    public static OWLAnnotationProperty AnnotationProperty(String abbreviatedIRI, PrefixManager pm) {
           return dataFactory.getOWLAnnotationProperty(abbreviatedIRI, pm);
       }


    public static OWLNamedIndividual NamedIndividual(IRI iri) {
        return dataFactory.getOWLNamedIndividual(iri);
    }

    public static OWLNamedIndividual NamedIndividual(String abbreviatedIRI, PrefixManager pm) {
        return dataFactory.getOWLNamedIndividual(abbreviatedIRI, pm);
    }


    public static OWLDatatype Datatype(IRI iri) {
        return dataFactory.getOWLDatatype(iri);
    }

    public static OWLDeclarationAxiom Declaration(OWLEntity entity) {
        return dataFactory.getOWLDeclarationAxiom(entity);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Class Expressions
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static OWLObjectIntersectionOf ObjectIntersectionOf(OWLClassExpression... classExpressions) {
        return dataFactory.getOWLObjectIntersectionOf(classExpressions);
    }

    public static OWLObjectUnionOf ObjectUnionOf(OWLClassExpression... classExpressions) {
        return dataFactory.getOWLObjectUnionOf(classExpressions);
    }

    public static OWLObjectComplementOf ObjectComplementOf(OWLClassExpression classExpression) {
        return dataFactory.getOWLObjectComplementOf(classExpression);
    }

    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectSomeValuesFrom(pe, ce);
    }

    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectAllValuesFrom(pe, ce);
    }

    public static OWLObjectHasValue ObjectHasValue(OWLObjectPropertyExpression pe, OWLIndividual individual) {
        return dataFactory.getOWLObjectHasValue(pe, individual);
    }

    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectMinCardinality(cardinality, pe, ce);
    }

    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectMaxCardinality(cardinality, pe, ce);
    }

    public static OWLObjectExactCardinality ObjectExactCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {
        return dataFactory.getOWLObjectExactCardinality(cardinality, pe, ce);
    }

    public static OWLObjectHasSelf ObjectHasSelf(OWLObjectPropertyExpression pe) {
        return dataFactory.getOWLObjectHasSelf(pe);
    }

    public static OWLObjectOneOf ObjectOneOf(OWLIndividual... individuals) {
        return dataFactory.getOWLObjectOneOf(individuals);
    }

    public static OWLDataSomeValuesFrom DataSomeValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataSomeValuesFrom(pe, dr);
    }

    public static OWLDataAllValuesFrom DataAllValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataAllValuesFrom(pe, dr);
    }

    public static OWLDataHasValue DataHasValue(OWLDataPropertyExpression pe, OWLLiteral literal) {
        return dataFactory.getOWLDataHasValue(pe, literal);
    }

    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataMinCardinality(cardinality, pe, dr);
    }

    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataMaxCardinality(cardinality, pe, dr);
    }

    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {
        return dataFactory.getOWLDataExactCardinality(cardinality, pe, dr);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Data Ranges other than datatype
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static OWLDataIntersectionOf DataIntersectionOf(OWLDataRange... dataRanges) {
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

    public static OWLDatatypeRestriction DatatypeRestriction(OWLDatatype datatype, OWLFacetRestriction... facetRestrictions) {
        return dataFactory.getOWLDatatypeRestriction(datatype, facetRestrictions);
    }

    public OWLFacetRestriction FacetRestriction(OWLFacet facet, OWLLiteral facetValue) {
        return dataFactory.getOWLFacetRestriction(facet, facetValue);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass, OWLClassExpression superClass) {
        return dataFactory.getOWLSubClassOfAxiom(subClass, superClass);
    }

    public static OWLEquivalentClassesAxiom EquivalentClasses(OWLClassExpression... classExpressions) {
        return dataFactory.getOWLEquivalentClassesAxiom(classExpressions);
    }

    public static OWLDisjointClassesAxiom DisjointClasses(OWLClassExpression... classExpressions) {
        return dataFactory.getOWLDisjointClassesAxiom(classExpressions);
    }

    public static OWLDisjointUnionAxiom DisjointUnion(OWLClass cls, OWLClassExpression... classExpressions) {
        return dataFactory.getOWLDisjointUnionAxiom(cls, CollectionFactory.createSet(classExpressions));
    }

    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {
        return dataFactory.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentObjectPropertiesAxiom(properties);
    }

    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(OWLObjectPropertyExpression... properties) {
        return dataFactory.getOWLDisjointObjectPropertiesAxiom(properties);
    }

    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {
        return dataFactory.getOWLInverseObjectPropertiesAxiom(peA, peB);
    }

    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLObjectPropertyExpression property, OWLClassExpression domain) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(property, domain);
    }

    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLObjectPropertyExpression property, OWLClassExpression range) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(property, range);
    }

    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLFunctionalObjectPropertyAxiom(property);
    }

    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(property);
    }

    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLReflexiveObjectPropertyAxiom(property);
    }

    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLIrreflexiveObjectPropertyAxiom(property);
    }

    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLSymmetricObjectPropertyAxiom(property);
    }

    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLAsymmetricObjectPropertyAxiom(property);
    }

    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(OWLObjectPropertyExpression property) {
        return dataFactory.getOWLTransitiveObjectPropertyAxiom(property);
    }


    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {
        return dataFactory.getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLEquivalentDataPropertiesAxiom(properties);
    }

    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(OWLDataPropertyExpression... properties) {
        return dataFactory.getOWLDisjointDataPropertiesAxiom(properties);
    }

    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression property, OWLClassExpression domain) {
        return dataFactory.getOWLDataPropertyDomainAxiom(property, domain);
    }

    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression property, OWLDataRange range) {
        return dataFactory.getOWLDataPropertyRangeAxiom(property, range);
    }


    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLDataPropertyExpression property) {
        return dataFactory.getOWLFunctionalDataPropertyAxiom(property);
    }

    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLDatatype datatype, OWLDataRange dataRange) {
        return dataFactory.getOWLDatatypeDefinitionAxiom(datatype, dataRange);
    }

    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {
        return dataFactory.getOWLHasKeyAxiom(classExpression, propertyExpressions);
    }

    public static OWLSameIndividualAxiom SameIndividual(OWLIndividual... individuals) {
        return dataFactory.getOWLSameIndividualAxiom(individuals);
    }

    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLIndividual... individuals) {
        return dataFactory.getOWLDifferentIndividualsAxiom(individuals);
    }

    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind) {
        return dataFactory.getOWLClassAssertionAxiom(ce, ind);
    }

    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {
        return dataFactory.getOWLObjectPropertyAssertionAxiom(property, source, target);
    }


    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {
        return dataFactory.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target);
    }


    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {
        return dataFactory.getOWLDataPropertyAssertionAxiom(property, source, target);
    }


    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {
        return dataFactory.getOWLNegativeDataPropertyAssertionAxiom(property, source, target);
    }


    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return dataFactory.getOWLAnnotationAssertionAxiom(property, subject, value);
    }

    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {
        return dataFactory.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);
    }

    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty property, IRI iri) {
        return dataFactory.getOWLAnnotationPropertyDomainAxiom(property, iri);
    }


    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty property, IRI iri) {
        return dataFactory.getOWLAnnotationPropertyRangeAxiom(property, iri);
    }


    public static IRI IRI(String iri) {
        return IRI.create(iri);
    }

    public static OWLStringLiteral StringLiteral(String literal) {
        return dataFactory.getOWLStringLiteral(literal);
    }


    public static OWLStringLiteral StringLiteral(String literal, String lang) {
        return dataFactory.getOWLStringLiteral(literal, lang);
    }

    public static OWLTypedLiteral TypedLiteral(String literal) {
        return dataFactory.getOWLTypedLiteral(literal);
    }


    public static OWLTypedLiteral TypedLiteral(boolean literal) {
        return dataFactory.getOWLTypedLiteral(literal);
    }

    public static OWLTypedLiteral TypedLiteral(int literal) {
        return dataFactory.getOWLTypedLiteral(literal);
    }

    public static OWLTypedLiteral TypedLiteral(double literal) {
        return dataFactory.getOWLTypedLiteral(literal);
    }

    public static OWLTypedLiteral TypedLiteral(float literal) {
        return dataFactory.getOWLTypedLiteral(literal);
    }


    public static OWLOntology Ontology(OWLOntologyManager man, OWLAxiom ... axioms) throws OWLOntologyCreationException {
        return man.createOntology(CollectionFactory.createSet(axioms));
    }
}
