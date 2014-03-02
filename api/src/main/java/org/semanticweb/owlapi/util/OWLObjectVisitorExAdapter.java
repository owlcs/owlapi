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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
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
 * Provides a default implementation of {@code OWLObjectVisitorEx}. Only the
 * methods that need specific client implementation need be overridden. The
 * adapter can be set up to return a default value.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 * @param <O>
 *        visitor return type
 */
public class OWLObjectVisitorExAdapter<O> implements OWLObjectVisitorEx<O> {

    private O defaultReturnValue = null;

    /**
     * Gets the default return value for this visitor. By default, the default
     * is {@code null}, but a fixed value (independent of the specified
     * {@code OWLObject} {@code object}) can be specified in the constructor
     * {@link org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#OWLObjectVisitorExAdapter(Object)}
     * 
     * @param object
     *        The object that was visited.
     * @return The default return value
     */
    protected O doDefault(@SuppressWarnings("unused") OWLObject object) {
        return defaultReturnValue;
    }

    /** default constructor */
    public OWLObjectVisitorExAdapter() {
        this(null);
    }

    /**
     * @param defaultReturnValue
     *        default return value
     */
    public OWLObjectVisitorExAdapter(O defaultReturnValue) {
        this.defaultReturnValue = defaultReturnValue;
    }

    @Override
    public O visit(OWLAnnotationAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLClassAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDeclarationAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDisjointClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLDisjointUnionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLEquivalentClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLHasKeyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSameIndividualAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSubClassOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSubDataPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(SWRLRule rule) {
        return doDefault(rule);
    }

    @Override
    public O visit(OWLClass desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataAllValuesFrom desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataExactCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataMaxCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataMinCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataSomeValuesFrom desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataHasValue desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectAllValuesFrom desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectComplementOf desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectExactCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectHasSelf desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectHasValue desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectIntersectionOf desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectMaxCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectMinCardinality desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectOneOf desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectSomeValuesFrom desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLObjectUnionOf desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDataComplementOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataIntersectionOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataOneOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDatatype node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDatatypeRestriction node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataUnionOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLFacetRestriction node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataProperty property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLObjectProperty property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLObjectInverseOf property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLNamedIndividual individual) {
        return doDefault(individual);
    }

    @Override
    public O visit(OWLAnnotationProperty property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLAnnotation annotation) {
        return doDefault(annotation);
    }

    @Override
    public O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(OWLAnonymousIndividual individual) {
        return doDefault(individual);
    }

    @Override
    public O visit(IRI iri) {
        return doDefault(iri);
    }

    @Override
    public O visit(OWLLiteral literal) {
        return doDefault(literal);
    }

    @Override
    public O visit(SWRLLiteralArgument node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLVariable node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLIndividualArgument node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLBuiltInAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLClassAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLDataRangeAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLDataPropertyAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLDifferentIndividualsAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLObjectPropertyAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(SWRLSameIndividualAtom node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLOntology ontology) {
        return doDefault(ontology);
    }

    @Override
    public O visit(OWLDatatypeDefinitionAxiom axiom) {
        return doDefault(axiom);
    }
}
