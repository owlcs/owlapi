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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
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
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Class to remove structure sharing from OWL objects (Axioms or expressions)
 *
 * @author Ignazio Palmisano
 * @since 4.2.8
 */
public class OWLObjectDesharer implements OWLObjectVisitorEx<OWLObject> {

    private final OWLDataFactory df;

    /**
     * Creates an object duplicator that duplicates objects using the specified
     * data factory and uri replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     */
    public OWLObjectDesharer(OWLOntologyManager m) {
        df = m.getOWLDataFactory();
    }

    /**
     * @param object the object to duplicate
     * @param <O> return type
     * @return the duplicate
     */
    public <O extends OWLObject> O deshareObject(O object) {
        checkNotNull(object, "object cannot be null");
        return get(object);
    }

    private List<OWLAnnotation> anns(HasAnnotations axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        return asList(axiom.annotations().map(this::get));
    }

    @Override
    public OWLAsymmetricObjectPropertyAxiom visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return df.getOWLAsymmetricObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLClassAssertionAxiom visit(OWLClassAssertionAxiom axiom) {
        return df.getOWLClassAssertionAxiom(get(axiom.getClassExpression()),
            get(axiom.getIndividual()), anns(axiom));
    }

    @Override
    public OWLDataPropertyAssertionAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
        return df.getOWLDataPropertyAssertionAxiom(get(axiom.getProperty()),
            get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLDataPropertyDomainAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return df.getOWLDataPropertyDomainAxiom(get(axiom.getProperty()), get(axiom.getDomain()),
            anns(axiom));
    }

    @Override
    public OWLDataPropertyRangeAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        return df.getOWLDataPropertyRangeAxiom(get(axiom.getProperty()), get(axiom.getRange()),
            anns(axiom));
    }

    @Override
    public OWLSubDataPropertyOfAxiom visit(OWLSubDataPropertyOfAxiom axiom) {
        return df.getOWLSubDataPropertyOfAxiom(get(axiom.getSubProperty()),
            get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLDeclarationAxiom visit(OWLDeclarationAxiom axiom) {
        return df.getOWLDeclarationAxiom(get(axiom.getEntity()), anns(axiom));
    }

    @Override
    public OWLDifferentIndividualsAxiom visit(OWLDifferentIndividualsAxiom axiom) {
        return df.getOWLDifferentIndividualsAxiom(list(axiom.individuals()), anns(axiom));
    }

    @Override
    public OWLDisjointClassesAxiom visit(OWLDisjointClassesAxiom axiom) {
        return df.getOWLDisjointClassesAxiom(list(axiom.classExpressions()), anns(axiom));
    }

    @Override
    public OWLDisjointDataPropertiesAxiom visit(OWLDisjointDataPropertiesAxiom axiom) {
        return df.getOWLDisjointDataPropertiesAxiom(list(axiom.properties()), anns(axiom));
    }

    @Override
    public OWLDisjointObjectPropertiesAxiom visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return df.getOWLDisjointObjectPropertiesAxiom(list(axiom.properties()), anns(axiom));
    }

    @Override
    public OWLDisjointUnionAxiom visit(OWLDisjointUnionAxiom axiom) {
        return df.getOWLDisjointUnionAxiom(get(axiom.getOWLClass()), list(axiom.classExpressions()),
            anns(axiom));
    }

    @Override
    public OWLAnnotationAssertionAxiom visit(OWLAnnotationAssertionAxiom axiom) {
        return df.getOWLAnnotationAssertionAxiom(get(axiom.getProperty()), get(axiom.getSubject()),
            get(axiom.getValue()), anns(axiom));
    }

    @Override
    public OWLEquivalentClassesAxiom visit(OWLEquivalentClassesAxiom axiom) {
        return df.getOWLEquivalentClassesAxiom(list(axiom.classExpressions()), anns(axiom));
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return df.getOWLEquivalentDataPropertiesAxiom(list(axiom.properties()), anns(axiom));
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return df.getOWLEquivalentObjectPropertiesAxiom(list(axiom.properties()), anns(axiom));
    }

    @Override
    public OWLFunctionalDataPropertyAxiom visit(OWLFunctionalDataPropertyAxiom axiom) {
        return df.getOWLFunctionalDataPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLFunctionalObjectPropertyAxiom visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return df.getOWLFunctionalObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLInverseFunctionalObjectPropertyAxiom visit(
        OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return df.getOWLInverseFunctionalObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLInverseObjectPropertiesAxiom visit(OWLInverseObjectPropertiesAxiom axiom) {
        return df.getOWLInverseObjectPropertiesAxiom(get(axiom.getFirstProperty()),
            get(axiom.getSecondProperty()), anns(axiom));
    }

    @Override
    public OWLIrreflexiveObjectPropertyAxiom visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return df.getOWLIrreflexiveObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLNegativeDataPropertyAssertionAxiom visit(
        OWLNegativeDataPropertyAssertionAxiom axiom) {
        return df.getOWLNegativeDataPropertyAssertionAxiom(get(axiom.getProperty()),
            get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLNegativeObjectPropertyAssertionAxiom visit(
        OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return df.getOWLNegativeObjectPropertyAssertionAxiom(get(axiom.getProperty()),
            get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLObjectPropertyAssertionAxiom visit(OWLObjectPropertyAssertionAxiom axiom) {
        return df.getOWLObjectPropertyAssertionAxiom(get(axiom.getProperty()),
            get(axiom.getSubject()), get(axiom.getObject()), anns(axiom));
    }

    @Override
    public OWLSubPropertyChainOfAxiom visit(OWLSubPropertyChainOfAxiom axiom) {
        List<OWLObjectPropertyExpression> chain =
            asList(axiom.getPropertyChain().stream().map(this::get));
        return df.getOWLSubPropertyChainOfAxiom(chain, get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLObjectPropertyDomainAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return df.getOWLObjectPropertyDomainAxiom(get(axiom.getProperty()), get(axiom.getDomain()),
            anns(axiom));
    }

    @Override
    public OWLObjectPropertyRangeAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        return df.getOWLObjectPropertyRangeAxiom(get(axiom.getProperty()), get(axiom.getRange()),
            anns(axiom));
    }

    @Override
    public OWLSubObjectPropertyOfAxiom visit(OWLSubObjectPropertyOfAxiom axiom) {
        return df.getOWLSubObjectPropertyOfAxiom(get(axiom.getSubProperty()),
            get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLReflexiveObjectPropertyAxiom visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return df.getOWLReflexiveObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLSameIndividualAxiom visit(OWLSameIndividualAxiom axiom) {
        return df.getOWLSameIndividualAxiom(list(axiom.individuals()), anns(axiom));
    }

    @Override
    public OWLSubClassOfAxiom visit(OWLSubClassOfAxiom axiom) {
        return df.getOWLSubClassOfAxiom(get(axiom.getSubClass()), get(axiom.getSuperClass()),
            anns(axiom));
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return df.getOWLSymmetricObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLTransitiveObjectPropertyAxiom visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return df.getOWLTransitiveObjectPropertyAxiom(get(axiom.getProperty()), anns(axiom));
    }

    @Override
    public OWLClass visit(OWLClass ce) {
        return ce;
    }

    @Override
    public OWLDataAllValuesFrom visit(OWLDataAllValuesFrom ce) {
        return df.getOWLDataAllValuesFrom(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataExactCardinality visit(OWLDataExactCardinality ce) {
        return df.getOWLDataExactCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLDataMaxCardinality visit(OWLDataMaxCardinality ce) {
        return df.getOWLDataMaxCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLDataMinCardinality visit(OWLDataMinCardinality ce) {
        return df.getOWLDataMinCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLDataSomeValuesFrom visit(OWLDataSomeValuesFrom ce) {
        return df.getOWLDataSomeValuesFrom(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataHasValue visit(OWLDataHasValue ce) {
        return df.getOWLDataHasValue(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectAllValuesFrom visit(OWLObjectAllValuesFrom ce) {
        return df.getOWLObjectAllValuesFrom(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectComplementOf visit(OWLObjectComplementOf ce) {
        return df.getOWLObjectComplementOf(get(ce.getOperand()));
    }

    @Override
    public OWLObjectExactCardinality visit(OWLObjectExactCardinality ce) {
        return df.getOWLObjectExactCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLObjectIntersectionOf visit(OWLObjectIntersectionOf ce) {
        return df.getOWLObjectIntersectionOf(list(ce.operands()));
    }

    @Override
    public OWLObjectMaxCardinality visit(OWLObjectMaxCardinality ce) {
        return df.getOWLObjectMaxCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLObjectMinCardinality visit(OWLObjectMinCardinality ce) {
        return df.getOWLObjectMinCardinality(ce.getCardinality(), get(ce.getProperty()),
            get(ce.getFiller()));
    }

    @Override
    public OWLObjectOneOf visit(OWLObjectOneOf ce) {
        return df.getOWLObjectOneOf(list(ce.individuals()));
    }

    @Override
    public OWLObjectHasSelf visit(OWLObjectHasSelf ce) {
        return df.getOWLObjectHasSelf(get(ce.getProperty()));
    }

    @Override
    public OWLObjectSomeValuesFrom visit(OWLObjectSomeValuesFrom ce) {
        return df.getOWLObjectSomeValuesFrom(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLObjectUnionOf visit(OWLObjectUnionOf ce) {
        return df.getOWLObjectUnionOf(list(ce.operands()));
    }

    @Override
    public OWLObjectHasValue visit(OWLObjectHasValue ce) {
        return df.getOWLObjectHasValue(get(ce.getProperty()), get(ce.getFiller()));
    }

    @Override
    public OWLDataComplementOf visit(OWLDataComplementOf node) {
        return df.getOWLDataComplementOf(get(node.getDataRange()));
    }

    @Override
    public OWLDataOneOf visit(OWLDataOneOf node) {
        return df.getOWLDataOneOf(list(node.values()));
    }

    @Override
    public OWLDatatype visit(OWLDatatype node) {
        return node;
    }

    @Override
    public OWLDatatypeRestriction visit(OWLDatatypeRestriction node) {
        return df.getOWLDatatypeRestriction(get(node.getDatatype()),
            list(node.facetRestrictions()));
    }

    @Override
    public OWLFacetRestriction visit(OWLFacetRestriction node) {
        return df.getOWLFacetRestriction(node.getFacet(), get(node.getFacetValue()));
    }

    @Override
    public OWLLiteral visit(OWLLiteral node) {
        return node;
    }

    @Override
    public OWLDataProperty visit(OWLDataProperty property) {
        return property;
    }

    @Override
    public OWLObjectProperty visit(OWLObjectProperty property) {
        return property;
    }

    @Override
    public OWLObjectInverseOf visit(OWLObjectInverseOf property) {
        OWLObjectPropertyExpression inverse = property.getInverse();
        if (inverse.isAnonymous()) {
            return df.getOWLObjectInverseOf(get(property.getNamedProperty()));
        }
        return df.getOWLObjectInverseOf(get(inverse.asOWLObjectProperty()));
    }

    @Override
    public OWLNamedIndividual visit(OWLNamedIndividual individual) {
        return individual;
    }

    @Override
    public OWLOntology visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        return ontology;
    }

    @Override
    public SWRLRule visit(SWRLRule rule) {
        return df.getSWRLRule(list(rule.body()), list(rule.head()), anns(rule));
    }

    @Override
    public SWRLClassAtom visit(SWRLClassAtom node) {
        return df.getSWRLClassAtom(get(node.getPredicate()), get(node.getArgument()));
    }

    @Override
    public SWRLDataRangeAtom visit(SWRLDataRangeAtom node) {
        return df.getSWRLDataRangeAtom(get(node.getPredicate()), get(node.getArgument()));
    }

    @Override
    public SWRLObjectPropertyAtom visit(SWRLObjectPropertyAtom node) {
        return df.getSWRLObjectPropertyAtom(get(node.getPredicate()), get(node.getFirstArgument()),
            get(node.getSecondArgument()));
    }

    @Override
    public SWRLDataPropertyAtom visit(SWRLDataPropertyAtom node) {
        return df.getSWRLDataPropertyAtom(get(node.getPredicate()), get(node.getFirstArgument()),
            get(node.getSecondArgument()));
    }

    @Override
    public SWRLBuiltInAtom visit(SWRLBuiltInAtom node) {
        return df.getSWRLBuiltInAtom(get(node.getPredicate()), list(node.arguments()));
    }

    @Override
    public SWRLDifferentIndividualsAtom visit(SWRLDifferentIndividualsAtom node) {
        return df.getSWRLDifferentIndividualsAtom(get(node.getFirstArgument()),
            get(node.getSecondArgument()));
    }

    @Override
    public SWRLSameIndividualAtom visit(SWRLSameIndividualAtom node) {
        return df.getSWRLSameIndividualAtom(get(node.getFirstArgument()),
            get(node.getSecondArgument()));
    }

    @Override
    public SWRLVariable visit(SWRLVariable node) {
        return df.getSWRLVariable(get(node.getIRI()));
    }

    @Override
    public SWRLIndividualArgument visit(SWRLIndividualArgument node) {
        return df.getSWRLIndividualArgument(get(node.getIndividual()));
    }

    @Override
    public SWRLLiteralArgument visit(SWRLLiteralArgument node) {
        return df.getSWRLLiteralArgument(get(node.getLiteral()));
    }

    @Override
    public OWLHasKeyAxiom visit(OWLHasKeyAxiom axiom) {
        return df.getOWLHasKeyAxiom(get(axiom.getClassExpression()),
            list(axiom.propertyExpressions()), anns(axiom));
    }

    @Override
    public OWLDataIntersectionOf visit(OWLDataIntersectionOf node) {
        return df.getOWLDataIntersectionOf(list(node.operands()));
    }

    @Override
    public OWLDataUnionOf visit(OWLDataUnionOf node) {
        return df.getOWLDataUnionOf(list(node.operands()));
    }

    @Override
    public OWLAnnotationProperty visit(OWLAnnotationProperty property) {
        return property;
    }

    @Override
    public OWLAnnotationPropertyDomainAxiom visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return df.getOWLAnnotationPropertyDomainAxiom(get(axiom.getProperty()),
            get(axiom.getDomain()), anns(axiom));
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return df.getOWLAnnotationPropertyRangeAxiom(get(axiom.getProperty()),
            get(axiom.getRange()), anns(axiom));
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return df.getOWLSubAnnotationPropertyOfAxiom(get(axiom.getSubProperty()),
            get(axiom.getSuperProperty()), anns(axiom));
    }

    @Override
    public OWLAnnotation visit(OWLAnnotation node) {
        return df.getOWLAnnotation(get(node.getProperty()), get(node.getValue()), anns(node));
    }

    @Override
    public OWLAnonymousIndividual visit(OWLAnonymousIndividual individual) {
        return individual;
    }

    @Override
    public IRI visit(IRI iri) {
        return iri;
    }

    @Override
    public OWLDatatypeDefinitionAxiom visit(OWLDatatypeDefinitionAxiom axiom) {
        return df.getOWLDatatypeDefinitionAxiom(get(axiom.getDatatype()), get(axiom.getDataRange()),
            anns(axiom));
    }

    /**
     * A utility function that duplicates a set of objects.
     *
     * @param objects The set of object to be duplicated
     * @return The set of duplicated objects
     */
    protected <O extends OWLObject> List<O> list(Stream<O> objects) {
        return asList(objects.map(this::get));
    }

    @SuppressWarnings("unchecked")
    protected <O extends OWLObject> O get(O o) {
        return (O) o.accept(this);
    }
}
