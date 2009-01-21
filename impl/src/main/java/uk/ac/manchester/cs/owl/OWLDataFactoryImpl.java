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

    public OWLClass getOWLClass(String curi, NamespaceManager namespaceManager) {
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
        return getDatatype(OWLRDFVocabulary.RDFS_LITERAL.getURI());
    }


    public OWLDatatype getIntegerDatatype() {
        return getDatatype(XSDVocabulary.INTEGER.getURI());
    }


    public OWLDatatype getFloatDatatype() {
        return getDatatype(XSDVocabulary.FLOAT.getURI());
    }


    public OWLDatatype getDoubleDatatype() {
        return getDatatype(XSDVocabulary.DOUBLE.getURI());
    }


    public OWLDatatype getBooleanDatatype() {
        return getDatatype(XSDVocabulary.BOOLEAN.getURI());
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


    public OWLNamedIndividual getOWLIndividual(URI uri) {
        OWLNamedIndividual ind = individualsByURI.get(uri);
        if (ind == null) {
            ind = new OWLNamedIndividualImpl(this, getIRI(uri));
            individualsByURI.put(uri, ind);
        }
        return ind;
    }

    public OWLDataProperty getOWLDataProperty(String curi, NamespaceManager namespaceManager) {
        return getOWLDataProperty(namespaceManager.getURI(curi));
    }

    public OWLIndividual getOWLIndividual(String curi, NamespaceManager namespaceManager) {
        return getOWLIndividual(namespaceManager.getURI(curi));
    }

    public OWLObjectProperty getOWLObjectProperty(String curi, NamespaceManager namespaceManager) {
        return getOWLObjectProperty(namespaceManager.getURI(curi));
    }

    public OWLAnonymousIndividual getOWLAnonymousIndividual(String id) {
        return new OWLAnonymousIndividualImpl(this, new NodeIDImpl(id));
    }


    public OWLDatatype getDatatype(URI uri) {
        OWLDatatype dt = datatypesByURI.get(uri);
        if (dt == null) {
            dt = new OWLDatatypeImpl(this, getIRI(uri));
            datatypesByURI.put(uri, dt);
        }
        return dt;
    }


    public OWLTypedLiteral getTypedLiteral(String literal, OWLDatatype datatype) {
        return new OWLTypedLiteralImpl(this, literal, datatype);
    }


    public OWLTypedLiteral getTypedLiteral(int value) {
        return new OWLTypedLiteralImpl(this, Integer.toString(value), getDatatype(XSDVocabulary.INTEGER.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(double value) {
        return new OWLTypedLiteralImpl(this, Double.toString(value), getDatatype(XSDVocabulary.DOUBLE.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(boolean value) {
        return new OWLTypedLiteralImpl(this, Boolean.toString(value), getDatatype(XSDVocabulary.BOOLEAN.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(float value) {
        return new OWLTypedLiteralImpl(this, Float.toString(value), getDatatype(XSDVocabulary.FLOAT.getURI()));
    }


    public OWLTypedLiteral getTypedLiteral(String value) {
        return new OWLTypedLiteralImpl(this, value, getDatatype(XSDVocabulary.STRING.getURI()));
    }


    public OWLRDFTextLiteral getOWLUntypedConstant(String literal) {
        return new OWLRDFTextLiteralImpl(this, literal, null);
    }


    public OWLRDFTextLiteral getRDFTextLiteral(String literal, String lang) {
        return new OWLRDFTextLiteralImpl(this, literal, lang);
    }


    public OWLDataOneOf getDataOneOf(Set<? extends OWLLiteral> values) {
        return new OWLDataOneOfImpl(this, values);
    }


    public OWLDataOneOf getDataOneOf(OWLLiteral... values) {
        return getDataOneOf(CollectionFactory.createSet(values));
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
        return new OWLDatatypeRestrictionImpl(this,
                datatype,
                Collections.singleton(getFacetRestriction(facet,
                        typedConstant)));
    }


    public OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype dataRange, OWLFacetRestriction... facetRestrictions) {
        return getDatatypeRestriction(dataRange, CollectionFactory.createSet(facetRestrictions));
    }


    public OWLFacetRestriction getFacetRestriction(OWLFacet facet, int facetValue) {
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


    public OWLDataAllValuesFrom getDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange) {
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


    public OWLDataValueRestriction getDataHasValue(OWLDataPropertyExpression property, OWLLiteral value) {
        return new OWLDataValueRestrictionImpl(this, property, value);
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


    public OWLObjectExactCardinality getObjectExactCardinality(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectExactCardinality getObjectExactCardinality(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMinCardinality getObjectMinCardinality(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMinCardinality getObjectMinCardinality(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMaxCardinality getObjectMaxCardinality(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMaxCardinality getObjectMaxCardinality(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
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


    public OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(
            OWLObjectPropertyExpression property,
            OWLAnnotation... annotations) {
        return new OWLAsymmetricObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property,
                                                            OWLClassExpression domain,
                                                            OWLAnnotation... annotations) {
        return new OWLDataPropertyDomainAxiomImpl(this, property, domain, annotations);
    }


    public OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery,
                                                          OWLDataRange owlDataRange,
                                                          OWLAnnotation... annotations) {
        return new OWLDataPropertyRangeAxiomImpl(this, propery, owlDataRange, annotations);
    }


    public OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty,
                                                          OWLDataPropertyExpression superProperty, OWLAnnotation... annotations) {
        return new OWLSubDataPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLDeclaration getDeclaration(OWLEntity owlEntity, OWLAnnotation... annotations) {
        return new OWLDeclarationImpl(this, owlEntity, annotations);
    }


    public OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals, OWLAnnotation... annotations) {
        return new OWLDifferentIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLDifferentIndividualsAxiom getDifferentIndividuals(OWLIndividual... individuals) {
        return getDifferentIndividuals(CollectionFactory.createSet(individuals));
    }


    public OWLDisjointClassesAxiom getDisjointClasses(Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations) {
        return new OWLDisjointClassesAxiomImpl(this, descriptions, annotations);
    }

    public OWLDisjointClassesAxiom getDisjointClasses(OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        for (OWLClassExpression desc : classExpressions) {
            clses.add(desc);
        }
        return getDisjointClasses(clses);
    }


    public OWLDisjointDataPropertiesAxiom getDisjointDataProperties(
            Set<? extends OWLDataPropertyExpression> properties, OWLAnnotation... annotations) {
        return new OWLDisjointDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLDisjointDataPropertiesAxiom getDisjointDataProperties(OWLDataPropertyExpression... properties) {
        return getDisjointDataProperties(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(
            Set<? extends OWLObjectPropertyExpression> properties, OWLAnnotation... annotations) {
        return new OWLDisjointObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(OWLObjectPropertyExpression... properties) {
        return getDisjointObjectProperties(CollectionFactory.createSet(properties));
    }


    public OWLDisjointUnionAxiom getDisjointUnion(OWLClass owlClass,
                                                  Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations) {
        return new OWLDisjointUnionImpl(this, owlClass, descriptions, annotations);
    }


    public OWLEquivalentClassesAxiom getEquivalentClasses(Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations) {
        return new OWLEquivalentClassesImpl(this, descriptions, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB, OWLAnnotation... annotations) {
        return getEquivalentClasses(CollectionFactory.createSet(clsA, clsB), annotations);
    }


    public OWLDisjointClassesAxiom getDisjointClasses(OWLClassExpression clsA, OWLClassExpression clsB, OWLAnnotation... annotations) {
        return getDisjointClasses(CollectionFactory.createSet(clsA, clsB), annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(
            Set<? extends OWLDataPropertyExpression> properties, OWLAnnotation... annotations) {
        return new OWLEquivalentDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression... properties) {
        return getEquivalentDataProperties(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(
            Set<? extends OWLObjectPropertyExpression> properties, OWLAnnotation... annotations) {
        return new OWLEquivalentObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression... properties) {
        return getEquivalentObjectProperties(CollectionFactory.createSet(properties));
    }


    public OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLFunctionalDataPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLImportsDeclaration getImportsDeclaration(OWLOntology subject, URI importedOntologyURI) {
        URI cleanedImportedOntologyURI = importedOntologyURI;
        if (importedOntologyURI.getFragment() != null && importedOntologyURI.getFragment().length() == 0) {
            cleanedImportedOntologyURI = URI.create(importedOntologyURI.toString().substring(0, importedOntologyURI.toString().length() - 1));
        }
        return new OWLImportsDeclarationImpl(this, subject, cleanedImportedOntologyURI);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property,
                                                                  OWLLiteral object, OWLAnnotation... annotations) {
        return new OWLDataPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property, int value, OWLAnnotation... annotations) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property, double value, OWLAnnotation... annotations) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property, float value, OWLAnnotation... annotations) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property, boolean value, OWLAnnotation... annotations) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), annotations);
    }


    public OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                                  OWLDataPropertyExpression property, String value, OWLAnnotation... annotations) {
        return getDataPropertyAssertion(subject, property, getTypedLiteral(value), annotations);
    }


    public OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
                                                                                  OWLDataPropertyExpression property,
                                                                                  OWLLiteral object, OWLAnnotation... annotations) {
        return new OWLNegativeDataPropertyAssertionImplAxiom(this, subject, property, object, annotations);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                                      OWLObjectPropertyExpression property,
                                                                                      OWLIndividual object, OWLAnnotation... annotations) {
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                                      OWLObjectPropertyExpression property,
                                                                      OWLIndividual object, OWLAnnotation... annotations) {
        return new OWLObjectPropertyAssertionAxiomImpl(this, individual, property, object, annotations);
    }


    public OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual, OWLClassExpression classExpression, OWLAnnotation... annotations) {
        return new OWLClassAssertionImpl(this, individual, classExpression, annotations);
    }

    public OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(
            OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(
            OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLIrreflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                                OWLClassExpression classExpression, OWLAnnotation... annotations) {
        return new OWLObjectPropertyDomainAxiomImpl(this, property, classExpression, annotations);
    }


    public OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                              OWLClassExpression range, OWLAnnotation... annotations) {
        return new OWLObjectPropertyRangeAxiomImpl(this, property, range, annotations);
    }


    public OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                              OWLObjectPropertyExpression superProperty, OWLAnnotation... annotations) {
        return new OWLSubObjectPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLReflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSameIndividualsAxiom getSameIndividuals(Set<OWLIndividual> individuals, OWLAnnotation... annotations) {
        return new OWLSameIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass, OWLClassExpression superClass, OWLAnnotation... annotations) {
        return new OWLSubClassOfAxiomImpl(this, subClass, superClass, annotations);
    }

    public OWLSubClassOfAxiom getSubClassOf(String subClass, String superClass, NamespaceManager namespaceManager, OWLAnnotation... annotations) {
        return getSubClassOf(getOWLClass(subClass, namespaceManager), getOWLClass(superClass, namespaceManager), annotations);
    }

    public OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLSymmetricObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property, OWLAnnotation... annotations) {
        return new OWLTransitiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property) {
        return new OWLObjectPropertyInverseImpl(this, property);
    }


    public OWLComplextSubPropertyAxiom getObjectPropertyChainSubProperty(
            List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty, OWLAnnotation... annotations) {
        return new OWLComplexSubPropertyAxiomImpl(this, chain, superProperty, annotations);
    }


    public OWLInverseObjectPropertiesAxiom getInverseObjectProperties(
            OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty, OWLAnnotation... annotations) {
        return new OWLInverseObjectPropertiesAxiomImpl(this, forwardProperty, inverseProperty, annotations);
    }

    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce, Set<? extends OWLObjectPropertyExpression> objectProperties, Set<? extends OWLDataPropertyExpression> dataProperties, OWLAnnotation... annotations) {
        return new OWLHasKeyAxiomImpl(this, ce, objectProperties, dataProperties, annotations);
    }

    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce, OWLDataPropertyExpression... properties) {
        return getHasKey(ce, new HashSet<OWLObjectPropertyExpression>(0), CollectionFactory.createSet(properties));
    }

    public OWLHasKeyAxiom getHasKey(OWLClassExpression ce, OWLObjectPropertyExpression... properties) {
        return getHasKey(ce, CollectionFactory.createSet(properties), new HashSet<OWLDataProperty>(0));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Annotations


    public OWLAnnotation getAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value, OWLAnnotation... annotations) {
        return new OWLAnnotationImpl(this, property, value, annotations);
    }

    public OWLAnnotation getAnnotation(URI property, OWLAnnotationValue value, OWLAnnotation... annotations) {
        return new OWLAnnotationImpl(this, getAnnotationProperty(property), value, annotations);
    }

    public OWLAnnotation getAnnotation(OWLAnnotationProperty property, String literal, String lang, OWLAnnotation... annotations) {
        if (lang == null) {
            throw new OWLRuntimeException("Lang must not be null");
        }
        return getAnnotation(property, getRDFTextLiteral(literal, lang), annotations);
    }

    public OWLAnnotation getAnnotation(OWLAnnotationProperty property, URI uri, OWLAnnotation... annotations) {
        return new OWLAnnotationImpl(this, property, new IRIImpl(uri), annotations);
    }

    public OWLAnnotation getAnnotation(URI property, OWLLiteral literal, OWLAnnotation... annotations) {
        return getAnnotation(getAnnotationProperty(property), literal, annotations);
    }

    public OWLAnnotation getAnnotation(URI property, OWLRDFTextLiteral literal, OWLAnnotation... annotations) {
        return getAnnotation(getAnnotationProperty(property), literal, annotations);
    }

    public OWLAnnotation getAnnotation(URI property, String literal, String lang, OWLAnnotation... annotations) {
        return getAnnotation(getAnnotationProperty(property), literal, lang, annotations);
    }

    public OWLAnnotation getAnnotation(URI property, URI uri, OWLAnnotation... annotations) {
        return getAnnotation(getAnnotationProperty(property), uri, annotations);
    }

    public OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotation annotation) {
        return new OWLAnnotationAssertionAxiomImpl(this, subject, annotation);
    }

    public OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotationProperty property, OWLLiteral literal) {
        return getAnnotationAssertion(subject, getAnnotation(property, literal));
    }

    public OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotationProperty property, String literal, String lang) {
        return getAnnotationAssertion(subject, getAnnotation(property, literal, lang));
    }

    public OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, URI propertyURI, OWLLiteral literal) {
        return getAnnotationAssertion(subject, getAnnotation(propertyURI, literal));
    }

    public OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, URI propertyURI, String literal, String lang) {
        return getAnnotationAssertion(subject, getAnnotation(propertyURI, literal, lang));
    }

    public OWLAnnotationProperty getAnnotationProperty(URI uri) {
        OWLAnnotationProperty prop = annotationPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLAnnotationPropertyImpl(this, getIRI(uri));
            annotationPropertiesByURI.put(uri, prop);
        }
        return prop;
    }

    public OWLAnnotationPropertyDomain getAnnotationPropertyDomain(OWLAnnotationProperty prop, IRI domain) {
        return new OWLAnnotationPropertyDomainImpl(this, prop, domain);
    }

    public OWLAnnotationPropertyRange getAnnotationPropertyRange(OWLAnnotationProperty prop, IRI range) {
        return null;
    }

    public OWLSubAnnotationPropertyOf getSubAnnotationPropertyOf(OWLAnnotationProperty sub, OWLAnnotationProperty sup) {
        return null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets a SWRL rule which is named with a URI
     *
     * @param uri         The rule URI (the "name" of the rule)
     * @param antecendent The atoms that make up the antecedent
     * @param consequent  The atoms that make up the consequent
     */
    public SWRLRule getSWRLRule(URI uri, Set<? extends SWRLAtom> antecendent, Set<? extends SWRLAtom> consequent) {

        return new SWRLRuleImpl(this, uri, antecendent, consequent);
    }


    /**
     * Gets a SWRL rule which is not named with a URI - i.e. that is anonymous.
     *
     * @param uri        The anonymous id
     * @param antededent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */
    public SWRLRule getSWRLRule(URI uri, boolean anonymous, Set<? extends SWRLAtom> antededent, Set<? extends SWRLAtom> consequent) {
        return new SWRLRuleImpl(this, anonymous, uri, antededent, consequent);
    }


    /**
     * Gets a SWRL rule which is anonymous - i.e. isn't named with a URI
     *
     * @param antecendent The atoms that make up the antecedent
     * @param consequent  The atoms that make up the consequent
     */
    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> antecendent, Set<? extends SWRLAtom> consequent) {
        return new SWRLRuleImpl(this, antecendent, consequent);
    }


    /**
     * Gets a SWRL class atom, i.e.  C(x) where C is a class description and
     * x is either an individual id or an i-variable
     *
     * @param desc The class description
     * @param arg  The argument (x)
     */
    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression desc, SWRLAtomIObject arg) {
        return new SWRLClassAtomImpl(this, desc, arg);
    }


    /**
     * Gets a SWRL data range atom, i.e.  D(x) where D is an OWL data range and
     * x is either a constant or a d-variable
     *
     * @param rng The class description
     * @param arg The argument (x)
     */
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange rng, SWRLAtomDObject arg) {
        return new SWRLDataRangeAtomImpl(this, rng, arg);
    }


    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either an individual id or
     * an i-variable.
     *
     * @param property The property (P)
     * @param arg0     The first argument (x)
     * @param arg1     The second argument (y)
     */
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLAtomIObject arg0,
                                                            SWRLAtomIObject arg1) {
        return new SWRLObjectPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either a constant or
     * a d-variable.
     *
     * @param property The property (P)
     * @param arg0     The first argument (x)
     * @param arg1     The second argument (y)
     */
    public SWRLDataValuedPropertyAtom getSWRLDataValuedPropertyAtom(OWLDataPropertyExpression property,
                                                                    SWRLAtomIObject arg0, SWRLAtomDObject arg1) {
        return new SWRLDataValuedPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Creates a SWRL Built-In atom.
     *
     * @param builtIn The SWRL builtIn (see SWRL W3 member submission)
     * @param args    A non-empty set of SWRL D-Objects
     */
    public SWRLBuiltInAtom getSWRLBuiltInAtom(SWRLBuiltInsVocabulary builtIn, List<SWRLAtomDObject> args) {
        return new SWRLBuiltInAtomImpl(this, builtIn, args);
    }


    /**
     * Gets a SWRL i-variable.  This is used in rule atoms where a SWRL
     * I object can be used.
     *
     * @param var The id (URI) of the variable
     */
    public SWRLAtomIVariable getSWRLAtomIVariable(URI var) {
        return new SWRLAtomIVariableImpl(this, var);
    }


    /**
     * Gets a SWRL d-variable.  This is used in rule atoms where a SWRL
     * D object can be used.
     *
     * @param var The id (URI) of the variable
     */
    public SWRLAtomDVariable getSWRLAtomDVariable(URI var) {
        return new SWRLAtomDVariableImpl(this, var);
    }


    /**
     * Gets a SWRL individual object.
     *
     * @param individual The individual that is the object argument
     */
    public SWRLAtomIndividualObject getSWRLAtomIndividualObject(OWLIndividual individual) {
        return new SWRLAtomIndividualObjectImpl(this, individual);
    }


    /**
     * Gets a SWRL constant object.
     *
     * @param literal The constant that is the object argument
     */
    public SWRLAtomConstantObject getSWRLAtomConstantObject(OWLLiteral literal) {
        return new SWRLAtomConstantObjectImpl(this, literal);
    }


    public SWRLDifferentFromAtom getSWRLDifferentFromAtom(SWRLAtomIObject arg0, SWRLAtomIObject arg1) {
        return new SWRLDifferentFromAtomImpl(this, arg0, arg1);
    }


    public SWRLSameAsAtom getSWRLSameAsAtom(SWRLAtomIObject arg0, SWRLAtomIObject arg1) {
        return new SWRLSameAsAtomImpl(this, arg0, arg1);
    }
}
