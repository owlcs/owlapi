package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.vocab.OWLFacet;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owl.vocab.XSDVocabulary;

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


    private Map<URI, OWLClass> classesByURI;

    private Map<URI, OWLObjectProperty> objectPropertiesByURI;

    private Map<URI, OWLDataProperty> dataPropertiesByURI;

    private Map<URI, OWLDatatype> datatypesByURI;

    private Map<URI, OWLNamedIndividual> individualsByURI;

    private Map<URI, OWLAnnotationProperty> annotationPropertiesByURI;


    public OWLDataFactoryImpl() {
        classesByURI = new HashMap<URI, OWLClass>();
        objectPropertiesByURI = new HashMap<URI, OWLObjectProperty>();
        dataPropertiesByURI = new HashMap<URI, OWLDataProperty>();
        datatypesByURI = new HashMap<URI, OWLDatatype>();
        individualsByURI = new HashMap<URI, OWLNamedIndividual>();
        annotationPropertiesByURI = new HashMap<URI, OWLAnnotationProperty>();
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
        return new IRIImpl(uri);
    }


    public OWLClass getOWLClass(URI uri) {
        OWLClass cls = classesByURI.get(uri);
        if (cls == null) {
            cls = new OWLClassImpl(this, getIRI(uri));
            classesByURI.put(uri, cls);
        }
        return cls;
    }


    public OWLClass getOWLClass(String curi,
                                NamespaceManager namespaceManager) {
        return getOWLClass(namespaceManager.getURI(curi));
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


    public OWLDatatype getIntegerDatatype() {
        return getOWLDatatype(XSDVocabulary.INTEGER.getURI());
    }


    public OWLDatatype getFloatDatatype() {
        return getOWLDatatype(XSDVocabulary.FLOAT.getURI());
    }


    public OWLDatatype getDoubleDatatype() {
        return getOWLDatatype(XSDVocabulary.DOUBLE.getURI());
    }


    public OWLDatatype getBooleanDatatype() {
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


    public OWLDataProperty getOWLDataProperty(URI uri) {
        OWLDataProperty prop = dataPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLDataPropertyImpl(this, getIRI(uri));
            dataPropertiesByURI.put(uri, prop);
        }
        return prop;
    }


    public OWLNamedIndividual getOWLNamedIndividual(URI uri) {
        OWLNamedIndividual ind = individualsByURI.get(uri);
        if (ind == null) {
            ind = new OWLNamedIndividualImpl(this, getIRI(uri));
            individualsByURI.put(uri, ind);
        }
        return ind;
    }


    public OWLDataProperty getOWLDataProperty(String curi,
                                           NamespaceManager namespaceManager) {
        return getOWLDataProperty(namespaceManager.getURI(curi));
    }


    public OWLNamedIndividual getOWLNamedIndividual(String curi,
                                       NamespaceManager namespaceManager) {
        return getOWLNamedIndividual(namespaceManager.getURI(curi));
    }


    public OWLObjectProperty getOWLObjectProperty(String curi,
                                               NamespaceManager namespaceManager) {
        return getOWLObjectProperty(namespaceManager.getURI(curi));
    }


    public OWLAnonymousIndividual getAnonymousIndividual(String id) {
        return new OWLAnonymousIndividualImpl(this, new NodeIDImpl(id));
    }


    public OWLDatatype getOWLDatatype(URI uri) {
        OWLDatatype dt = datatypesByURI.get(uri);
        if (dt == null) {
            dt = new OWLDatatypeImpl(this, getIRI(uri));
            datatypesByURI.put(uri, dt);
        }
        return dt;
    }


    public OWLTypedLiteral getTypedLiteral(String literal,
                                           OWLDatatype datatype) {
        return new OWLTypedLiteralImpl(this, literal, datatype);
    }


    public OWLTypedLiteral getTypedLiteral(int value) {
        return new OWLTypedLiteralImpl(this, Integer.toString(value), getOWLDatatype(XSDVocabulary.INTEGER.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(double value) {
        return new OWLTypedLiteralImpl(this, Double.toString(value), getOWLDatatype(XSDVocabulary.DOUBLE.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(boolean value) {
        return new OWLTypedLiteralImpl(this, Boolean.toString(value), getOWLDatatype(XSDVocabulary.BOOLEAN.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(float value) {
        return new OWLTypedLiteralImpl(this, Float.toString(value), getOWLDatatype(XSDVocabulary.FLOAT.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(String value) {
        return new OWLTypedLiteralImpl(this, value, getOWLDatatype(XSDVocabulary.STRING.getURI()));
    }


    public OWLRDFTextLiteral getRDFTextLiteral(String literal,
                                               String lang) {
        return new OWLRDFTextLiteralImpl(this, literal, lang);
    }


    public OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values) {
        return new OWLDataOneOfImpl(this, values);
    }


    public OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }


    public OWLDataComplementOf getDataComplementOf(OWLDataRange dataRange) {
        return new OWLDataComplementOfImpl(this, dataRange);
    }


    public OWLDataIntersectionOf getDataIntersectionOf(OWLDataRange... dataRanges) {
        return getDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataIntersectionOf getDataIntersectionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataIntersectionOfImpl(this, dataRanges);
    }


    public OWLDataUnionOf getDataUnionOf(OWLDataRange... dataRanges) {
        return getDataUnionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataUnionOf getDataUnionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataUnionOfImpl(this, dataRanges);
    }


    public OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype datatype,
                                                         Set<OWLFacetRestriction> facets) {
        return new OWLDatatypeRestrictionImpl(this, datatype, facets);
    }


    public OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype datatype,
                                                         OWLFacet facet,
                                                         OWLLiteral typedConstant) {
        return new OWLDatatypeRestrictionImpl(this, datatype, Collections.singleton(getFacetRestriction(facet, typedConstant)));
    }


    public OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype dataRange,
                                                         OWLFacetRestriction... facetRestrictions) {
        return getDatatypeRestriction(dataRange, CollectionFactory.createSet(facetRestrictions));
    }


    public OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                                   int facetValue) {
        return getFacetRestriction(facet, getTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                                   double facetValue) {
        return getFacetRestriction(facet, getTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                                   float facetValue) {
        return getFacetRestriction(facet, getTypedLiteral(facetValue));
    }


    public OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                                   OWLLiteral facetValue) {
        return new OWLFacetRestrictionImpl(this, facet, facetValue);
    }


    public OWLObjectIntersectionOf getObjectIntersectionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectIntersectionOfImpl(this, operands);
    }


    public OWLObjectIntersectionOf getObjectIntersectionOf(OWLClassExpression... operands) {
        return getObjectIntersectionOf(CollectionFactory.createSet(operands));
    }


    public OWLDataAllValuesFrom getDataAllValuesFrom(OWLDataPropertyExpression property,
                                                     OWLDataRange dataRange) {
        return new OWLDataAllValuesFromImpl(this, property, dataRange);
    }


    public OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                           int cardinality) {
        return new OWLDataExactCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                           int cardinality,
                                                           OWLDataRange dataRange) {
        return new OWLDataExactCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                       int cardinality) {
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                       int cardinality,
                                                       OWLDataRange dataRange) {
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                       int cardinality) {
        return new OWLDataMinCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                       int cardinality,
                                                       OWLDataRange dataRange) {
        return new OWLDataMinCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataSomeValuesFrom getDataSomeValuesFrom(OWLDataPropertyExpression property,
                                                       OWLDataRange dataRange) {
        return new OWLDataSomeValuesFromImpl(this, property, dataRange);
    }


    public OWLDataHasValue getDataHasValue(OWLDataPropertyExpression property,
                                           OWLLiteral value) {
        return new OWLDataHasValueImpl(this, property, value);
    }


    public OWLObjectComplementOf getObjectComplementOf(OWLClassExpression operand) {
        return new OWLObjectComplementOfImpl(this, operand);
    }


    public OWLObjectAllValuesFrom getObjectAllValuesFrom(OWLObjectPropertyExpression property,
                                                         OWLClassExpression classExpression) {
        return new OWLObjectAllValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectOneOf getObjectOneOf(Set<OWLIndividual> values) {
        return new OWLObjectOneOfImpl(this, values);
    }


    public OWLObjectOneOf getObjectOneOf(OWLIndividual... individuals) {
        return getObjectOneOf(CollectionFactory.createSet(individuals));
    }


    public OWLObjectExactCardinality getObjectExactCardinality(OWLObjectPropertyExpression property,
                                                               int cardinality) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectExactCardinality getObjectExactCardinality(OWLObjectPropertyExpression property,
                                                               int cardinality,
                                                               OWLClassExpression classExpression) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMinCardinality getObjectMinCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMinCardinality getObjectMinCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality,
                                                           OWLClassExpression classExpression) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMaxCardinality getObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMaxCardinality getObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality,
                                                           OWLClassExpression classExpression) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectHasSelf getObjectHasSelf(OWLObjectPropertyExpression property) {
        return new OWLObjectHasSelfImpl(this, property);
    }


    public OWLObjectSomeValuesFrom getObjectSomeValuesFrom(OWLObjectPropertyExpression property,
                                                           OWLClassExpression classExpression) {
        return new OWLObjectSomeValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectHasValue getObjectHasValue(OWLObjectPropertyExpression property,
                                               OWLIndividual individual) {
        return new OWLObjectHasValueImpl(this, property, individual);
    }


    public OWLObjectUnionOf getObjectUnionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectUnionOfImpl(this, operands);
    }


    public OWLObjectUnionOf getObjectUnionOf(OWLClassExpression... operands) {
        return getObjectUnionOf(CollectionFactory.createSet(operands));
    }


    public OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(OWLObjectPropertyExpression propertyExpression,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLAsymmetricObjectPropertyAxiomImpl(this, propertyExpression, annotations);
    }


    public OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(OWLObjectPropertyExpression propertyExpression) {
        return getAsymmetricObjectProperty(propertyExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property,
                                                            OWLClassExpression domain,
                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyDomainAxiomImpl(this, property, domain, annotations);
    }


    public OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property,
                                                            OWLClassExpression domain) {
        return getDataPropertyDomain(property, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery,
                                                          OWLDataRange owlDataRange,
                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyRangeAxiomImpl(this, propery, owlDataRange, annotations);
    }


    public OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery,
                                                          OWLDataRange owlDataRange) {
        return getDataPropertyRange(propery, owlDataRange, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty,
                                                          OWLDataPropertyExpression superProperty,
                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLSubDataPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty,
                                                          OWLDataPropertyExpression superProperty) {
        return getSubDataPropertyOf(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
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


    public OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals,
                                                                Set<? extends OWLAnnotation> annotations) {
        return new OWLDifferentIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLDifferentIndividualsAxiom getDifferentIndividuals(OWLIndividual... individuals) {
        return getDifferentIndividuals(CollectionFactory.createSet(individuals));
    }


    public OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals) {
        return getDifferentIndividuals(individuals, EMPTY_ANNOTATIONS_SET);
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


    public OWLDisjointDataPropertiesAxiom getDisjointDataProperties(Set<? extends OWLDataPropertyExpression> properties,
                                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLDisjointDataPropertiesAxiom getDisjointDataProperties(Set<? extends OWLDataPropertyExpression> properties) {
        return getDisjointDataProperties(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointDataPropertiesAxiom getDisjointDataProperties(OWLDataPropertyExpression... properties) {
        return getDisjointDataProperties(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(OWLObjectPropertyExpression... properties) {
        return getDisjointObjectProperties(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(Set<? extends OWLObjectPropertyExpression> properties) {
        return getDisjointObjectProperties(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(Set<? extends OWLObjectPropertyExpression> properties,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                                  Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentClassesImpl(this, classExpressions, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClasses(OWLClassExpression clsA,
                                                             OWLClassExpression clsB) {
        return getOWLEquivalentClasses(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClasses(OWLClassExpression clsA,
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


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(Set<? extends OWLDataPropertyExpression> properties,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(Set<? extends OWLDataPropertyExpression> properties) {
        return getEquivalentDataProperties(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression propertyA,
                                                                        OWLDataPropertyExpression propertyB) {
        return getEquivalentDataProperties(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression propertyA,
                                                                        OWLDataPropertyExpression propertyB,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return getEquivalentDataProperties(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression... properties) {
        return getEquivalentDataProperties(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression... properties) {
        return getEquivalentObjectProperties(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(Set<? extends OWLObjectPropertyExpression> properties) {
        return getEquivalentObjectProperties(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression propertyA,
                                                                            OWLObjectPropertyExpression propertyB) {
        return getEquivalentObjectProperties(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression propertyA,
                                                                            OWLObjectPropertyExpression propertyB,
                                                                            Set<? extends OWLAnnotation> annotations) {
        return getEquivalentObjectProperties(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property,
                                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalDataPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property) {
        return getFunctionalDataProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property) {
        return getFunctionalObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLImportsDeclaration getImportsDeclaration(OWLOntology subject,
                                                       URI importedOntologyURI) {
        URI cleanedImportedOntologyURI = importedOntologyURI;
        if (importedOntologyURI.getFragment() != null && importedOntologyURI.getFragment().length() == 0) {
            cleanedImportedOntologyURI = URI.create(importedOntologyURI.toString().substring(0, importedOntologyURI.toString().length() - 1));
        }
        return new OWLImportsDeclarationImpl(this, subject, cleanedImportedOntologyURI);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  OWLLiteral object,
                                                                  Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  OWLLiteral object) {
        return getDataPropertyAssertion(subject, property, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  int value) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  double value) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  float value) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  boolean value) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  String value) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
                                                                                  OWLDataPropertyExpression property,
                                                                                  OWLLiteral object) {
        return getNegativeDataPropertyAssertion(subject, property, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
                                                                                  OWLDataPropertyExpression property,
                                                                                  OWLLiteral object,
                                                                                  Set<? extends OWLAnnotation> annotations) {
        return new OWLNegativeDataPropertyAssertionImplAxiom(this, subject, property, object, annotations);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                                      OWLObjectPropertyExpression property,
                                                                                      OWLIndividual object) {
        return getNegativeObjectPropertyAssertion(subject, property, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                                      OWLObjectPropertyExpression property,
                                                                                      OWLIndividual object,
                                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                                      OWLObjectPropertyExpression property,
                                                                      OWLIndividual object) {
        return getObjectPropertyAssertion(individual, property, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual,
                                                    OWLClassExpression classExpression) {
        return getClassAssertion(individual, classExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual,
                                                    OWLClassExpression classExpression,
                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLClassAssertionImpl(this, individual, classExpression, annotations);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(OWLObjectPropertyExpression property) {
        return getInverseFunctionalObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(OWLObjectPropertyExpression property,
                                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                                          Set<? extends OWLAnnotation> annotations) {
        return new OWLIrreflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property) {
        return getReflexiveObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(OWLObjectPropertyExpression property) {
        return getIrreflexiveObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                                OWLClassExpression classExpression,
                                                                Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyDomainAxiomImpl(this, property, classExpression, annotations);
    }


    public OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                                OWLClassExpression classExpression) {
        return getObjectPropertyDomain(property, classExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                              OWLClassExpression range,
                                                              Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyRangeAxiomImpl(this, property, range, annotations);
    }


    public OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                              OWLClassExpression range) {
        return getObjectPropertyRange(property, range, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                              OWLObjectPropertyExpression superProperty,
                                                              Set<? extends OWLAnnotation> annotations) {
        return new OWLSubObjectPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                              OWLObjectPropertyExpression superProperty) {
        return getSubObjectPropertyOf(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLReflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSameIndividualsAxiom getSameIndividuals(Set<? extends OWLIndividual> individuals,
                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLSameIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLSameIndividualsAxiom getSameIndividuals(OWLIndividual... individuals) {
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.addAll(Arrays.asList(individuals));
        return getSameIndividuals(inds);
    }


    public OWLSameIndividualsAxiom getSameIndividuals(Set<? extends OWLIndividual> individuals) {
        return getSameIndividuals(individuals, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass,
                                                    OWLClassExpression superClass,
                                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLSubClassOfAxiomImpl(this, subClass, superClass, annotations);
    }


    public OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass,
                                                    OWLClassExpression superClass) {
        return getSubClassOf(subClass, superClass, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLSymmetricObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property) {
        return getSymmetricObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property,
                                                                        Set<? extends OWLAnnotation> annotations) {
        return new OWLTransitiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property) {
        return getTransitiveObjectProperty(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property) {
        return new OWLObjectPropertyInverseImpl(this, property);
    }


    public OWLInverseObjectPropertiesAxiom getInverseObjectProperties(OWLObjectPropertyExpression forwardProperty,
                                                                      OWLObjectPropertyExpression inverseProperty,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseObjectPropertiesAxiomImpl(this, forwardProperty, inverseProperty, annotations);
    }


    public OWLInverseObjectPropertiesAxiom getInverseObjectProperties(OWLObjectPropertyExpression forwardProperty,
                                                                      OWLObjectPropertyExpression inverseProperty) {
        return getInverseObjectProperties(forwardProperty, inverseProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubPropertyChainOfAxiom getSubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain,
                                                            OWLObjectPropertyExpression superProperty,
                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLSubPropertyChainAxiomImpl(this, chain, superProperty, annotations);
    }


    public OWLSubPropertyChainOfAxiom getSubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain,
                                                            OWLObjectPropertyExpression superProperty) {
        return getSubPropertyChainOf(chain, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                                    Set<? extends OWLPropertyExpression> properties,
                                    Set<? extends OWLAnnotation> annotations) {
        return new OWLHasKeyAxiomImpl(this, ce, properties, annotations);
    }


    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                                    Set<? extends OWLPropertyExpression> properties) {
        return getHasKey(ce, properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                                    OWLPropertyExpression... properties) {
        return getHasKey(ce, CollectionFactory.createSet(properties));
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


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(Set<? extends OWLObjectPropertyExpression> properties,
                                                                            Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                                      OWLObjectPropertyExpression property,
                                                                      OWLIndividual object,
                                                                      Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyAssertionAxiomImpl(this, individual, property, object, annotations);
    }


    public OWLSubAnnotationPropertyOf getSubAnnotationPropertyOf(OWLAnnotationProperty sub,
                                                                 OWLAnnotationProperty sup) {
        throw new OWLRuntimeException("TODO");
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


    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getAnnotation(OWLAnnotationProperty property,
                                       OWLAnnotationValue value) {
        return getAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getAnnotation(OWLAnnotationProperty property,
                                       OWLAnnotationValue value,
                                       Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationImpl(this, property, value, annotations);
    }


    public OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                              OWLAnnotation annotation) {
        return getAnnotationAssertion(subject, annotation.getProperty(), annotation.getValue());
    }


    public OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                              OWLAnnotation annotation,
                                                              Set<? extends OWLAnnotation> annotations) {
        return getAnnotationAssertion(subject, annotation.getProperty(), annotation.getValue(), annotations);
    }


    public OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                              OWLAnnotationProperty property,
                                                              OWLAnnotationValue value) {
        return getAnnotationAssertion(subject, property, value, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                              OWLAnnotationProperty property,
                                                              OWLAnnotationValue value,
                                                              Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationAssertionAxiomImpl(this, subject, property, value, annotations);
    }


    public OWLAnnotationPropertyDomain getAnnotationPropertyDomain(OWLAnnotationProperty prop,
                                                                   IRI domain) {
        return new OWLAnnotationPropertyDomainImpl(this, prop, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationPropertyRange getAnnotationPropertyRange(OWLAnnotationProperty prop,
                                                                 IRI range) {
        return new OWLAnnotationPropertyRangeAxiomImpl(this, prop, range, EMPTY_ANNOTATIONS_SET);
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

}
