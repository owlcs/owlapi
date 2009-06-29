package uk.ac.manchester.cs.owl;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.net.URI;
import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataFactoryImpl implements OWLDataFactory {

    private static OWLDataFactory instance = new OWLDataFactoryImpl();

    private Map<URI, OWLClass> classesByURI;

    private Map<URI, OWLObjectProperty> objectPropertiesByURI;

    private Map<URI, OWLDataProperty> dataPropertiesByURI;

    private Map<URI, OWLDatatype> datatypesByURI;

    private Map<URI, OWLNamedIndividual> individualsByURI;

    private Map<URI, OWLAnnotationProperty> annotationPropertiesByURI;


    public OWLDataFactoryImpl() {
        classesByURI = new WeakHashMap<URI, OWLClass>();
        objectPropertiesByURI = new HashMap<URI, OWLObjectProperty>();
        dataPropertiesByURI = new HashMap<URI, OWLDataProperty>();
        datatypesByURI = new HashMap<URI, OWLDatatype>();
        individualsByURI = new HashMap<URI, OWLNamedIndividual>();
        annotationPropertiesByURI = new HashMap<URI, OWLAnnotationProperty>();
    }

    public static OWLDataFactory getInstance() {
        return instance;
    }


    public void purge() {
        classesByURI.clear();
        objectPropertiesByURI.clear();
        dataPropertiesByURI.clear();
        datatypesByURI.clear();
        individualsByURI.clear();
    }


    public IRI getIRI(URI uri) {
        if (uri == null) {
            return null;
        }
        return IRI.create(uri);
    }


    public OWLClass getOWLClass(URI uri) {
        OWLClass cls = classesByURI.get(uri);
        if (cls == null) {
            cls = new OWLClassImpl(this, getIRI(uri));
            classesByURI.put(uri, cls);
        }
        return cls;
    }

    public OWLClass getOWLClass(IRI iri) {
        return getOWLClass(iri.toURI());
    }

    public OWLClass getOWLClass(String curi,
                                PrefixManager prefixManager) {
        return getOWLClass(prefixManager.getIRI(curi));
    }


    public OWLClass getOWLThing() {
        return getOWLClass(OWLRDFVocabulary.OWL_THING.getURI());
    }


    public OWLClass getOWLNothing() {
        return getOWLClass(OWLRDFVocabulary.OWL_NOTHING.getURI());
    }


    public OWLDataProperty getOWLBottomDataProperty() {
        return getOWLDataProperty(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getURI());
    }


    public OWLObjectProperty getOWLBottomObjectProperty() {
        return getOWLObjectProperty(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getURI());
    }


    public OWLDataProperty getOWLTopDataProperty() {
        return getOWLDataProperty(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getURI());
    }


    public OWLObjectProperty getOWLTopObjectProperty() {
        return getOWLObjectProperty(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getURI());
    }


    public OWLDatatype getTopDatatype() {
        return getOWLDatatype(OWLRDFVocabulary.RDFS_LITERAL.getURI());
    }


    public OWLDatatype getIntegerOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.INTEGER.getURI());
    }


    public OWLDatatype getFloatOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.FLOAT.getURI());
    }


    public OWLDatatype getDoubleOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.DOUBLE.getURI());
    }


    public OWLDatatype getBooleanOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.BOOLEAN.getURI());
    }


    public OWLObjectProperty getOWLObjectProperty(URI uri) {
        OWLObjectProperty prop = objectPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLObjectPropertyImpl(this, getIRI(uri));
            objectPropertiesByURI.put(uri, prop);
        }
        return prop;
    }

    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return getOWLObjectProperty(iri.toURI());
    }

    public OWLDataProperty getOWLDataProperty(URI uri) {
        OWLDataProperty prop = dataPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLDataPropertyImpl(this, getIRI(uri));
            dataPropertiesByURI.put(uri, prop);
        }
        return prop;
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return getOWLDataProperty(iri.toURI());
    }

    public OWLNamedIndividual getOWLNamedIndividual(URI uri) {
        OWLNamedIndividual ind = individualsByURI.get(uri);
        if (ind == null) {
            ind = new OWLNamedIndividualImpl(this, getIRI(uri));
            individualsByURI.put(uri, ind);
        }
        return ind;
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return getOWLNamedIndividual(iri.toURI());
    }

    public OWLDataProperty getOWLDataProperty(String curi,
                                           PrefixManager prefixManager) {
        return getOWLDataProperty(prefixManager.getIRI(curi));
    }


    public OWLNamedIndividual getOWLNamedIndividual(String curi,
                                       PrefixManager prefixManager) {
        return getOWLNamedIndividual(prefixManager.getIRI(curi));
    }


    public OWLObjectProperty getOWLObjectProperty(String curi,
                                               PrefixManager prefixManager) {
        return getOWLObjectProperty(prefixManager.getIRI(curi));
    }


    public OWLAnonymousIndividual getOWLAnonymousIndividual(String id) {
        return new OWLAnonymousIndividualImpl(this, new NodeIDImpl(id));
    }

    /**
     * Gets an anonymous individual.  The node ID for the individual will be generated automatically
     * @return The anonymous individual
     */
    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(this, new NodeIDImpl());
    }

    public OWLDatatype getOWLDatatype(URI uri) {
        OWLDatatype dt = datatypesByURI.get(uri);
        if (dt == null) {
            dt = new OWLDatatypeImpl(this, getIRI(uri));
            datatypesByURI.put(uri, dt);
        }
        return dt;
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
        return getOWLDatatype(iri.toURI());
    }

    public OWLTypedLiteral getOWLTypedLiteral(String literal,
                                           OWLDatatype datatype) {
        return new OWLTypedLiteralImpl(this, literal, datatype);
    }


    public OWLTypedLiteral getOWLTypedLiteral(int value) {
        return new OWLTypedLiteralImpl(this, Integer.toString(value), getOWLDatatype(XSDVocabulary.INTEGER.getURI()));
    }


    public OWLTypedLiteral getOWLTypedLiteral(double value) {
        return new OWLTypedLiteralImpl(this, Double.toString(value), getOWLDatatype(XSDVocabulary.DOUBLE.getURI()));
    }


    public OWLTypedLiteral getOWLTypedLiteral(boolean value) {
        return new OWLTypedLiteralImpl(this, Boolean.toString(value), getOWLDatatype(XSDVocabulary.BOOLEAN.getURI()));
    }


    public OWLTypedLiteral getOWLTypedLiteral(float value) {
        return new OWLTypedLiteralImpl(this, Float.toString(value), getOWLDatatype(XSDVocabulary.FLOAT.getURI()));
    }


    public OWLTypedLiteral getOWLTypedLiteral(String value) {
        return new OWLTypedLiteralImpl(this, value, getOWLDatatype(XSDVocabulary.STRING.getURI()));
    }


    public OWLStringLiteral getOWLStringLiteral(String literal,
                                               String lang) {
        return new OWLStringLiteralImpl(this, literal, lang);
    }

    /**
     * Gets a string literal without a language tag.
     * @param literal The string literal
     * @return The string literal for the specfied string
     */
    public OWLStringLiteral getOWLStringLiteral(String literal) {
        return new OWLStringLiteralImpl(this, literal, null);
    }

    public OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values) {
        return new OWLDataOneOfImpl(this, values);
    }


    public OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }


    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        return new OWLDataComplementOfImpl(this, dataRange);
    }


    public OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges) {
        return getOWLDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataIntersectionOfImpl(this, dataRanges);
    }


    public OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges) {
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataUnionOfImpl(this, dataRanges);
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype,
                                                         Set<OWLFacetRestriction> facets) {
        return new OWLDatatypeRestrictionImpl(this, datatype, facets);
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype,
                                                         OWLFacet facet,
                                                         OWLLiteral typedConstant) {
        return new OWLDatatypeRestrictionImpl(this, datatype, Collections.singleton(getOWLFacetRestriction(facet, typedConstant)));
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
                                                         OWLFacetRestriction... facetRestrictions) {
        return getOWLDatatypeRestriction(dataRange, CollectionFactory.createSet(facetRestrictions));
    }


    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                                   int facetValue) {
        return getOWLFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                                   double facetValue) {
        return getOWLFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                                   float facetValue) {
        return getOWLFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                                   OWLLiteral facetValue) {
        return new OWLFacetRestrictionImpl(this, facet, facetValue);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectIntersectionOfImpl(this, operands);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands) {
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }


    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property,
                                                     OWLDataRange dataRange) {
        return new OWLDataAllValuesFromImpl(this, property, dataRange);
    }


    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataExactCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        return new OWLDataExactCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataMinCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        return new OWLDataMinCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property,
                                                       OWLDataRange dataRange) {
        return new OWLDataSomeValuesFromImpl(this, property, dataRange);
    }


    public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property,
                                           OWLLiteral value) {
        return new OWLDataHasValueImpl(this, property, value);
    }


    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        return new OWLObjectComplementOfImpl(this, operand);
    }


    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property,
                                                         OWLClassExpression classExpression) {
        return new OWLObjectAllValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values) {
        return new OWLObjectOneOfImpl(this, values);
    }


    public OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }


    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property) {
        return new OWLObjectHasSelfImpl(this, property);
    }


    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property,
                                                           OWLClassExpression classExpression) {
        return new OWLObjectSomeValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property,
                                               OWLIndividual individual) {
        return new OWLObjectHasValueImpl(this, property, individual);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectUnionOfImpl(this, operands);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }


    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLAsymmetricObjectPropertyAxiomImpl(this, propertyExpression, annotations);
    }


    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
                                                            OWLClassExpression domain,
                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyDomainAxiomImpl(this, property, domain, annotations);
    }


    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
                                                            OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                          OWLDataRange owlDataRange,
                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyRangeAxiomImpl(this, propery, owlDataRange, annotations);
    }


    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                          OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(propery, owlDataRange, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
                                                          OWLDataPropertyExpression superProperty,
                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLSubDataPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
                                                          OWLDataPropertyExpression superProperty) {
        return getOWLSubDataPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets a declaration for an entity
     * @param owlEntity The declared entity.
     * @return The declaration axiom for the specified entity.
     * @throws NullPointerException if owlEntity is <code>null</code>
     */

    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        if (owlEntity == null) {
            throw new NullPointerException("owlEntity");
        }
        return getOWLDeclarationAxiom(owlEntity, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
                                                      Set<? extends OWLAnnotation> annotations) {
        if (owlEntity == null) {
            throw new NullPointerException("owlEntity");
        }
        if (annotations == null) {
            throw new NullPointerException("annotations");
        }
        return new OWLDeclarationAxiomImpl(this, owlEntity, annotations);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals,
                                                                Set<? extends OWLAnnotation> annotations) {
        return new OWLDifferentIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals) {
        return getOWLDifferentIndividualsAxiom(CollectionFactory.createSet(individuals));
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointClassesAxiomImpl(this, classExpressions, annotations);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLDisjointClassesAxiom(clses);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties,
                                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                                  Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentClassesAxiomImpl(this, classExpressions, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
                                                             OWLClassExpression clsB) {
        return getOWLEquivalentClassesAxiom(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
                                                             OWLClassExpression clsB,
                                                             Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentClassesAxiom(CollectionFactory.createSet(clsA, clsB), annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLEquivalentClassesAxiom(clses);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA,
                                                                        OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA,
                                                                        OWLDataPropertyExpression propertyB,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA,
                                                                            OWLObjectPropertyExpression propertyB) {
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA,
                                                                            OWLObjectPropertyExpression propertyB,
                                                                            Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property,
                                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalDataPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLImportsDeclaration getOWLImportsDeclaration(URI importedOntologyURI) {
        URI cleanedImportedOntologyURI = importedOntologyURI;
        if (importedOntologyURI.getFragment() != null && importedOntologyURI.getFragment().length() == 0) {
            cleanedImportedOntologyURI = URI.create(importedOntologyURI.toString().substring(0, importedOntologyURI.toString().length() - 1));
        }
        return new OWLImportsDeclarationImpl(this, getIRI(cleanedImportedOntologyURI));
    }

    public OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI) {
        return getOWLImportsDeclaration(importedOntologyIRI.toURI());
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLDataPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, int value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, double value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, float value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, boolean value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, String value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLNegativeDataPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations) {
        return new OWLNegativeDataPropertyAssertionImplAxiom(this, subject, property, object, annotations);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, individual, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual, Set<? extends OWLAnnotation> annotations) {
        return new OWLClassAssertionImpl(this, individual, classExpression, annotations);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLIrreflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                OWLClassExpression classExpression,
                                                                Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyDomainAxiomImpl(this, property, classExpression, annotations);
    }


    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                              OWLClassExpression range,
                                                              Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyRangeAxiomImpl(this, property, range, annotations);
    }


    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                              OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
                                                              OWLObjectPropertyExpression superProperty,
                                                              Set<? extends OWLAnnotation> annotations) {
        return new OWLSubObjectPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
                                                              OWLObjectPropertyExpression superProperty) {
        return getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLReflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals,
                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLSameIndividualAxiomImpl(this, individuals, annotations);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individuals) {
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.addAll(Arrays.asList(individuals));
        return getOWLSameIndividualAxiom(inds);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
                                                    OWLClassExpression superClass,
                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLSubClassOfAxiomImpl(this, subClass, superClass, annotations);
    }


    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
                                                    OWLClassExpression superClass) {
        return getOWLSubClassOfAxiom(subClass, superClass, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLSymmetricObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLTransitiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property) {
        return new OWLObjectInverseOfImpl(this, property);
    }


    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty,
                                                                      OWLObjectPropertyExpression inverseProperty,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseObjectPropertiesAxiomImpl(this, forwardProperty, inverseProperty, annotations);
    }


    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty,
                                                                      OWLObjectPropertyExpression inverseProperty) {
        return getOWLInverseObjectPropertiesAxiom(forwardProperty, inverseProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain,
                                                            OWLObjectPropertyExpression superProperty,
                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLSubPropertyChainAxiomImpl(this, chain, superProperty, annotations);
    }


    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain,
                                                            OWLObjectPropertyExpression superProperty) {
        return getOWLSubPropertyChainOfAxiom(chain, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                    Set<? extends OWLPropertyExpression> properties,
                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLHasKeyAxiomImpl(this, ce, properties, annotations);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                    Set<? extends OWLPropertyExpression> properties) {
        return getOWLHasKeyAxiom(ce, properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                    OWLPropertyExpression... properties) {
        return getOWLHasKeyAxiom(ce, CollectionFactory.createSet(properties));
    }


    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
                                                  Set<? extends OWLClassExpression> classExpressions,
                                                  Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointUnionAxiomImpl(this, owlClass, classExpressions, annotations);
    }


    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
                                                  Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointUnionAxiom(owlClass, classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties,
                                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyAssertionAxiomImpl(this, individual, property, object, annotations);
    }


    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub,
                                                                 OWLAnnotationProperty sup) {
        return getOWLSubAnnotationPropertyOfAxiom(sub, sup, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubAnnotationPropertyOfAxiomImpl(this, sub, sup, annotations);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Annotations


    public OWLAnnotationProperty getOWLAnnotationProperty(URI uri) {
        OWLAnnotationProperty prop = annotationPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLAnnotationPropertyImpl(this, getIRI(uri));
            annotationPropertiesByURI.put(uri, prop);
        }
        return prop;
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return getOWLAnnotationProperty(iri.toURI());
    }

    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
                                       OWLAnnotationValue value) {
        return getOWLAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
                                       OWLAnnotationValue value,
                                       Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationImpl(this, property, value, annotations);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                              OWLAnnotation annotation) {
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue());
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                              OWLAnnotation annotation,
                                                              Set<? extends OWLAnnotation> annotations) {
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotations);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {

        return new OWLAnnotationAssertionAxiomImpl(this, subject, property, value, annotations);
    }


    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop,
                                                                   IRI domain, Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyDomainAxiomImpl(this, prop, domain, annotations);
    }


    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop,
                                                                   IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop,
                                                                 IRI range, Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyRangeAxiomImpl(this, prop, range, annotations);
    }


    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop,
                                                                 IRI range) {
        return getOWLAnnotationPropertyRangeAxiom(prop, range, EMPTY_ANNOTATIONS_SET);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets a SWRL rule which is named with a URI
     * @param uri The rule URI (the "name" of the rule)
     * @param antecendent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */

    public SWRLRule getSWRLRule(URI uri,
                                Set<? extends SWRLAtom> antecendent,
                                Set<? extends SWRLAtom> consequent) {

        return new SWRLRuleImpl(this, uri, antecendent, consequent, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets a SWRL rule which is not named with a URI - i.e. that is anonymous.
     * @param uri The anonymous id
     * @param antededent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */

    public SWRLRule getSWRLRule(URI uri,
                                boolean anonymous,
                                Set<? extends SWRLAtom> antededent,
                                Set<? extends SWRLAtom> consequent) {
        return new SWRLRuleImpl(this, anonymous, uri, antededent, consequent, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets a SWRL rule which is anonymous - i.e. isn't named with a URI
     * @param antecendent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */

    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> antecendent,
                                Set<? extends SWRLAtom> consequent) {
        return new SWRLRuleImpl(this, antecendent, consequent);
    }


    /**
     * Gets a SWRL class atom, i.e.  C(x) where C is a class expression and
     * x is either an individual id or an i-variable
     * @param desc The class expression
     * @param arg The argument (x)
     */

    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression desc,
                                          SWRLAtomIObject arg) {
        return new SWRLClassAtomImpl(this, desc, arg);
    }


    /**
     * Gets a SWRL data range atom, i.e.  D(x) where D is an OWL data range and
     * x is either a constant or a d-variable
     * @param rng The class expression
     * @param arg The argument (x)
     */

    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange rng,
                                                  SWRLAtomDObject arg) {
        return new SWRLDataRangeAtomImpl(this, rng, arg);
    }


    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either an individual id or
     * an i-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */

    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
                                                            SWRLAtomIObject arg0,
                                                            SWRLAtomIObject arg1) {
        return new SWRLObjectPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either a constant or
     * a d-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */

    public SWRLDataValuedPropertyAtom getSWRLDataValuedPropertyAtom(OWLDataPropertyExpression property,
                                                                    SWRLAtomIObject arg0,
                                                                    SWRLAtomDObject arg1) {
        return new SWRLDataValuedPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Creates a SWRL Built-In atom.
     * @param builtIn The SWRL builtIn (see SWRL W3 member submission)
     * @param args A non-empty set of SWRL D-Objects
     */

    public SWRLBuiltInAtom getSWRLBuiltInAtom(SWRLBuiltInsVocabulary builtIn,
                                              List<SWRLAtomDObject> args) {
        return new SWRLBuiltInAtomImpl(this, builtIn, args);
    }


    /**
     * Gets a SWRL i-variable.  This is used in rule atoms where a SWRL
     * I object can be used.
     * @param var The id (URI) of the variable
     */

    public SWRLAtomIVariable getSWRLAtomIVariable(URI var) {
        return new SWRLAtomIVariableImpl(this, var);
    }


    /**
     * Gets a SWRL d-variable.  This is used in rule atoms where a SWRL
     * D object can be used.
     * @param var The id (URI) of the variable
     */

    public SWRLAtomDVariable getSWRLAtomDVariable(URI var) {
        return new SWRLAtomDVariableImpl(this, var);
    }


    /**
     * Gets a SWRL individual object.
     * @param individual The individual that is the object argument
     */

    public SWRLAtomIndividualObject getSWRLAtomIndividualObject(OWLIndividual individual) {
        return new SWRLAtomIndividualObjectImpl(this, individual);
    }


    /**
     * Gets a SWRL constant object.
     * @param literal The constant that is the object argument
     */

    public SWRLAtomConstantObject getSWRLAtomConstantObject(OWLLiteral literal) {
        return new SWRLAtomConstantObjectImpl(this, literal);
    }


    public SWRLDifferentFromAtom getSWRLDifferentFromAtom(SWRLAtomIObject arg0,
                                                          SWRLAtomIObject arg1) {
        return new SWRLDifferentFromAtomImpl(this, arg0, arg1);
    }


    public SWRLSameAsAtom getSWRLSameAsAtom(SWRLAtomIObject arg0,
                                            SWRLAtomIObject arg1) {
        return new SWRLSameAsAtomImpl(this, arg0, arg1);
    }


    private static Set<OWLAnnotation> EMPTY_ANNOTATIONS_SET = Collections.emptySet();


    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
                                                          OWLDataRange dataRange) {
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
                                                          OWLDataRange dataRange,
                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLDatatypeDefinitionAxiomImpl(this, datatype, dataRange, annotations);
    }
}
