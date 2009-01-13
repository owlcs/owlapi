package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary;
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

    private Map<URI, OWLIndividual> individualsByURI;

    public OWLDataFactoryImpl() {
        classesByURI = new HashMap<URI, OWLClass>();
        objectPropertiesByURI = new HashMap<URI, OWLObjectProperty>();
        dataPropertiesByURI = new HashMap<URI, OWLDataProperty>();
        datatypesByURI = new HashMap<URI, OWLDatatype>();
        individualsByURI = new HashMap<URI, OWLIndividual>();
    }

    public void purge() {
        classesByURI.clear();
        objectPropertiesByURI.clear();
        dataPropertiesByURI.clear();
        datatypesByURI.clear();
        individualsByURI.clear();
    }


    public OWLClass getOWLClass(URI uri) {
        OWLClass cls = classesByURI.get(uri);
        if (cls == null) {
            cls = new OWLClassImpl(this, uri);
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


    public OWLDatatype getTopDataType() {
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
            prop = new OWLObjectPropertyImpl(this, uri);
            objectPropertiesByURI.put(uri, prop);
        }
        return prop;
    }


    public OWLDataProperty getOWLDataProperty(URI uri) {
        OWLDataProperty prop = dataPropertiesByURI.get(uri);
        if (prop == null) {
            prop = new OWLDataPropertyImpl(this, uri);
            dataPropertiesByURI.put(uri, prop);
        }
        return prop;
    }


    public OWLIndividual getOWLIndividual(URI uri) {
        OWLIndividual ind = individualsByURI.get(uri);
        if (ind == null) {
            ind = new OWLIndividualImpl(this, uri, false);
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

    public OWLIndividual getOWLAnonymousIndividual(URI anonId) {
        return new OWLIndividualImpl(this, anonId, true);
    }


    public OWLDatatype getOWLDatatype(URI uri) {
        OWLDatatype dt = datatypesByURI.get(uri);
        if (dt == null) {
            dt = new OWLDatatypeImpl(this, uri);
            datatypesByURI.put(uri, dt);
        }
        return dt;
    }


    public OWLTypedLiteral getOWLTypedLiteral(String literal, OWLDatatype datatype) {
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


    public OWLRDFTextLiteral getOWLUntypedConstant(String literal) {
        return new OWLRDFTextLiteralImpl(this, literal, null);
    }


    public OWLRDFTextLiteral getOWLRDFTextLiteral(String literal, String lang) {
        return new OWLRDFTextLiteralImpl(this, literal, lang);
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


    public OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange,
                                                              Set<OWLDataRangeFacetRestriction> facets) {
        return new OWLDataRangeRestrictionImpl(this, dataRange, facets);
    }


    public OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange,
                                                              OWLRestrictedDataRangeFacetVocabulary facet,
                                                              OWLTypedLiteral typedConstant) {
        return new OWLDataRangeRestrictionImpl(this,
                dataRange,
                Collections.singleton(getOWLDataRangeFacetRestriction(facet,
                        typedConstant)));
    }


    public OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange, OWLDataRangeFacetRestriction... facetRestrictions) {
        return getOWLDataRangeRestriction(dataRange, CollectionFactory.createSet(facetRestrictions));
    }


    public OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet, int facetValue) {
        return getOWLDataRangeFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                        double facetValue) {
        return getOWLDataRangeFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                        float facetValue) {
        return getOWLDataRangeFacetRestriction(facet, getOWLTypedLiteral(facetValue));
    }


    public OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                        OWLTypedLiteral facetValue) {
        return new OWLDataRangeFacetRestrictionImpl(this, facet, facetValue);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectIntersectionOfImpl(this, operands);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands) {
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }


    public OWLDataAllRestriction getOWLDataAllRestriction(OWLDataPropertyExpression property, OWLDataRange dataRange) {
        return new OWLDataAllRestrictionImpl(this, property, dataRange);
    }


    public OWLDataExactCardinalityRestriction getOWLDataExactCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                    int cardinality) {
        return new OWLDataExactCardinalityRestrictionImpl(this, property, cardinality, getTopDataType());
    }


    public OWLDataExactCardinalityRestriction getOWLDataExactCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                    int cardinality,
                                                                                    OWLDataRange dataRange) {
        return new OWLDataExactCardinalityRestrictionImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMaxCardinalityRestriction getOWLDataMaxCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                int cardinality) {
        return new OWLDataMaxCardinalityRestrictionImpl(this, property, cardinality, getTopDataType());
    }


    public OWLDataMaxCardinalityRestriction getOWLDataMaxCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                int cardinality,
                                                                                OWLDataRange dataRange) {
        return new OWLDataMaxCardinalityRestrictionImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMinCardinalityRestriction getOWLDataMinCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                int cardinality) {
        return new OWLDataMinCardinalityRestrictionImpl(this, property, cardinality, getTopDataType());
    }


    public OWLDataMinCardinalityRestriction getOWLDataMinCardinalityRestriction(OWLDataPropertyExpression property,
                                                                                int cardinality,
                                                                                OWLDataRange dataRange) {
        return new OWLDataMinCardinalityRestrictionImpl(this, property, cardinality, dataRange);
    }


    public OWLDataSomeRestriction getOWLDataSomeRestriction(OWLDataPropertyExpression property,
                                                            OWLDataRange dataRange) {
        return new OWLDataSomeRestrictionImpl(this, property, dataRange);
    }


    public OWLDataValueRestriction getOWLDataValueRestriction(OWLDataPropertyExpression property, OWLLiteral value) {
        return new OWLDataValueRestrictionImpl(this, property, value);
    }


    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        return new OWLObjectComplementOfImpl(this, operand);
    }


    public OWLObjectAllRestriction getOWLObjectAllRestriction(OWLObjectPropertyExpression property,
                                                              OWLClassExpression classExpression) {
        return new OWLObjectAllRestrictionImpl(this, property, classExpression);
    }


    public OWLObjectOneOf getOWLObjectOneOf(Set<OWLIndividual> values) {
        return new OWLObjectOneOfImpl(this, values);
    }


    public OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }


    public OWLObjectExactCardinalityRestriction getOWLObjectExactCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectExactCardinalityRestrictionImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectExactCardinalityRestriction getOWLObjectExactCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
        return new OWLObjectExactCardinalityRestrictionImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMinCardinalityRestriction getOWLObjectMinCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectMinCardinalityRestrictionImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMinCardinalityRestriction getOWLObjectMinCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
        return new OWLObjectMinCardinalityRestrictionImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMaxCardinalityRestriction getOWLObjectMaxCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality) {
        return new OWLObjectMaxCardinalityRestrictionImpl(this, property, cardinality, getOWLThing());
    }


    public OWLObjectMaxCardinalityRestriction getOWLObjectMaxCardinalityRestriction(
            OWLObjectPropertyExpression property, int cardinality, OWLClassExpression classExpression) {
        return new OWLObjectMaxCardinalityRestrictionImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectSelfRestriction getOWLObjectSelfRestriction(OWLObjectPropertyExpression property) {
        return new OWLObjectSelfRestrictionImpl(this, property);
    }


    public OWLObjectSomeValuesFrom getOWLObjectSomeRestriction(OWLObjectPropertyExpression property,
                                                               OWLClassExpression classExpression) {
        return new OWLObjectSomeValuesFromImpl(this, property, classExpression);
//        OWLObjectSomeValuesFrom r = (OWLObjectSomeValuesFrom) descriptionCache.get(d);
//        if(r == null) {
//            r = d;
//            descriptionCache.put(d, d);
//        }
//        return r;
    }


    public OWLObjectValueRestriction getOWLObjectValueRestriction(OWLObjectPropertyExpression property,
                                                                  OWLIndividual individual) {
        return new OWLObjectValueRestrictionImpl(this, property, individual);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectUnionOfImpl(this, operands);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }


    public OWLAntiSymmetricObjectPropertyAxiom getOWLAntiSymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        return new OWLAntiSymmetricObjectPropertyAxiomImpl(this, property);
    }


    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
                                                                    OWLClassExpression domain) {
        return new OWLDataPropertyDomainAxiomImpl(this, property, domain);
    }


    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                                  OWLDataRange owlDataRange) {
        return new OWLDataPropertyRangeAxiomImpl(this, propery, owlDataRange);
    }


    public OWLDataSubPropertyAxiom getOWLSubDataPropertyAxiom(OWLDataPropertyExpression subProperty,
                                                              OWLDataPropertyExpression superProperty) {
        return new OWLDataSubPropertyAxiomImpl(this, subProperty, superProperty);
    }


    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        return new OWLDeclarationAxiomImpl(this, owlEntity);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<OWLIndividual> individuals) {
        return new OWLDifferentIndividualsAxiomImpl(this, individuals);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals) {
        return getOWLDifferentIndividualsAxiom(CollectionFactory.createSet(individuals));
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> descriptions) {
        return new OWLDisjointClassesAxiomImpl(this, descriptions);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression clsA, OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.add(clsA);
        for (OWLClassExpression desc : classExpressions) {
            clses.add(desc);
        }
        return getOWLDisjointClassesAxiom(clses);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties) {
        return new OWLDisjointDataPropertiesAxiomImpl(this, properties);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties) {
        return new OWLDisjointObjectPropertiesAxiomImpl(this, properties);
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
                                                          Set<? extends OWLClassExpression> descriptions) {
        return new OWLDisjointUnionAxiomImpl(this, owlClass, descriptions);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> descriptions) {
        return new OWLEquivalentClassesAxiomImpl(this, descriptions);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB) {
        return getOWLEquivalentClassesAxiom(CollectionFactory.createSet(clsA, clsB));
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB) {
        return getOWLDisjointClassesAxiom(CollectionFactory.createSet(clsA, clsB));
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties) {
        return new OWLEquivalentDataPropertiesAxiomImpl(this, properties);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties) {
        return new OWLEquivalentObjectPropertiesAxiomImpl(this, properties);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        return new OWLFunctionalDataPropertyAxiomImpl(this, property);
    }


    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return new OWLFunctionalObjectPropertyAxiomImpl(this, property);
    }


    public OWLImportsDeclaration getOWLImportsDeclarationAxiom(OWLOntology subject, URI importedOntologyURI) {
        URI cleanedImportedOntologyURI = importedOntologyURI;
        if (importedOntologyURI.getFragment() != null && importedOntologyURI.getFragment().length() == 0) {
            cleanedImportedOntologyURI = URI.create(importedOntologyURI.toString().substring(0, importedOntologyURI.toString().length() - 1));
        }
        return new OWLImportsDeclarationImpl(this, subject, cleanedImportedOntologyURI);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property,
                                                                          OWLLiteral object) {
        return new OWLDataPropertyAssertionAxiomImpl(this, subject, property, object);
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property, int value) {
        return getOWLDataPropertyAssertionAxiom(subject, property, getOWLTypedLiteral(value));
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property, double value) {
        return getOWLDataPropertyAssertionAxiom(subject, property, getOWLTypedLiteral(value));
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property, float value) {
        return getOWLDataPropertyAssertionAxiom(subject, property, getOWLTypedLiteral(value));
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property, boolean value) {
        return getOWLDataPropertyAssertionAxiom(subject, property, getOWLTypedLiteral(value));
    }


    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                          OWLDataPropertyExpression property, String value) {
        return getOWLDataPropertyAssertionAxiom(subject, property, getOWLTypedLiteral(value));
    }


    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                                          OWLDataPropertyExpression property,
                                                                                          OWLLiteral object) {
        return new OWLNegativeDataPropertyAssertionAxiomImpl(this, subject, property, object);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLIndividual subject,
                                                                                              OWLObjectPropertyExpression property,
                                                                                              OWLIndividual object) {
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(this, subject, property, object);
    }


    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual individual,
                                                                              OWLObjectPropertyExpression property,
                                                                              OWLIndividual object) {
        return new OWLObjectPropertyAssertionAxiomImpl(this, individual, property, object);
    }


    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLIndividual individual, OWLClassExpression classExpression) {
        return new OWLClassAssertionAxiomImpl(this, individual, classExpression);
    }

    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(this, property);
    }


    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        return new OWLIrreflexiveObjectPropertyAxiomImpl(this, property);
    }


    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                        OWLClassExpression classExpression) {
        return new OWLObjectPropertyDomainAxiomImpl(this, property, classExpression);
    }


    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                                      OWLClassExpression range) {
        return new OWLObjectPropertyRangeAxiomImpl(this, property, range);
    }


    public OWLObjectSubPropertyAxiom getOWLSubObjectPropertyAxiom(OWLObjectPropertyExpression subProperty,
                                                                  OWLObjectPropertyExpression superProperty) {
        return new OWLObjectSubPropertyAxiomImpl(this, subProperty, superProperty);
    }


    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return new OWLReflexiveObjectPropertyAxiomImpl(this, property);
    }


    public OWLSameIndividualsAxiom getOWLSameIndividualsAxiom(Set<OWLIndividual> individuals) {
        return new OWLSameIndividualsAxiomImpl(this, individuals);
    }


    public OWLSubClassAxiom getOWLSubClassAxiom(OWLClassExpression subClass, OWLClassExpression superClass) {
        return new OWLSubClassAxiomImpl(this, subClass, superClass);
    }

    public OWLSubClassAxiom getOWLSubClassAxiom(String subClass, String superClass, NamespaceManager namespaceManager) {
        return getOWLSubClassAxiom(getOWLClass(subClass, namespaceManager), getOWLClass(superClass, namespaceManager));
    }

    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return new OWLSymmetricObjectPropertyAxiomImpl(this, property);
    }


    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return new OWLTransitiveObjectPropertyAxiomImpl(this, property);
    }


    public OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property) {
        return new OWLObjectPropertyInverseImpl(this, property);
    }


    public OWLDeprecatedClassAxiom getOWLDeprecatedClassAxiom(OWLClass owlClass) {
        return null;
    }


    public OWLDeprecatedObjectPropertyAxiom getOWLDeprecatedObjectPropertyAxiom(OWLObjectProperty property) {
        return null;
    }


    public OWLDeprecatedDataPropertyAxiom getOWLDeprecatedDataPropertyAxiom(OWLDataProperty property) {
        return null;
    }


    public OWLObjectPropertyChainSubPropertyAxiom getOWLObjectPropertyChainSubPropertyAxiom(
            List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {
        return new OWLObjectPropertyChainSubPropertyAxiomImpl(this, chain, superProperty);
    }


    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty) {
        return new OWLInverseObjectPropertiesAxiomImpl(this, forwardProperty, inverseProperty);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Annotations


    public OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, OWLAnnotation annotation) {
        return new OWLEntityAnnotationAxiomImpl(this, entity, annotation);
    }


    public OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, URI annotationURI, OWLLiteral value) {
        return getOWLEntityAnnotationAxiom(entity, getOWLConstantAnnotation(annotationURI, value));
    }


    public OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, URI annotationURI, OWLIndividual value) {
        return getOWLEntityAnnotationAxiom(entity, getOWLObjectAnnotation(annotationURI, value));
    }


    public OWLAxiomAnnotationAxiom getOWLAxiomAnnotationAxiom(OWLAxiom axiom, OWLAnnotation annotation) {
        return new OWLAxiomAnnotationAxiomImpl(this, axiom, annotation);
    }


    public OWLCommentAnnotation getCommentAnnotation(String comment) {
        return new OWLCommentAnnotationImpl(this, getOWLUntypedConstant(comment));
    }


    public OWLCommentAnnotation getCommentAnnotation(String comment, String langauge) {
        return new OWLCommentAnnotationImpl(this, getOWLRDFTextLiteral(comment, langauge));
    }


    public OWLLabelAnnotation getOWLLabelAnnotation(String label) {
        return new OWLLabelAnnotationImpl(this, getOWLUntypedConstant(label));
    }


    public OWLLabelAnnotation getOWLLabelAnnotation(String label, String language) {
        return new OWLLabelAnnotationImpl(this, getOWLRDFTextLiteral(label, language));
    }


    public OWLConstantAnnotation getOWLConstantAnnotation(URI annotationURI, OWLLiteral literal) {
        if (annotationURI.equals(OWLRDFVocabulary.RDFS_LABEL.getURI())) {
            return new OWLLabelAnnotationImpl(this, literal);
        } else if (annotationURI.equals(OWLRDFVocabulary.RDFS_COMMENT.getURI())) {
            return new OWLCommentAnnotationImpl(this, literal);
        } else {
            return new OWLConstantAnnotationImpl(this, annotationURI, literal);
        }
    }


    public OWLObjectAnnotation getOWLObjectAnnotation(URI annotationURI, OWLIndividual individual) {
        return new OWLObjectAnnotationImpl(this, annotationURI, individual);
    }


    public OWLOntologyAnnotationAxiom getOWLOntologyAnnotationAxiom(OWLOntology subject, OWLAnnotation annotation) {
        return new OWLOntologyAnnotationAxiomImpl(this, subject, annotation);
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
