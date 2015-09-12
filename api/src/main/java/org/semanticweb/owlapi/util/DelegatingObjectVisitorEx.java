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

import org.semanticweb.owlapi.model.*;

/**
 * A delegating visitor. This utility class can be used to override visiting a
 * particular type of object.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 * @param <O>
 *        the returned type
 */
public class DelegatingObjectVisitorEx<O> implements OWLObjectVisitorEx<O> {

    private final OWLObjectVisitorEx<O> delegate;

    /**
     * Constructs a visitor where the specified delegate will be used to visit
     * all objects unless one of the visit methods in this visitor is overriden.
     * 
     * @param delegate
     *        The delegate.
     */
    public DelegatingObjectVisitorEx(OWLObjectVisitorEx<O> delegate) {
        this.delegate = delegate;
    }

    @Override
    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLClassAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSubDataPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDeclarationAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDisjointClassesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDisjointUnionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLAnnotationAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLEquivalentClassesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSameIndividualAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSubClassOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(SWRLRule rule) {
        return delegate.visit(rule);
    }

    @Override
    public O visit(OWLClass ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataAllValuesFrom ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataExactCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataMaxCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataMinCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataSomeValuesFrom ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataHasValue ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectAllValuesFrom ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectComplementOf ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectExactCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectIntersectionOf ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectMaxCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectMinCardinality ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectOneOf ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectHasSelf ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectSomeValuesFrom ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectUnionOf ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLObjectHasValue ce) {
        return delegate.visit(ce);
    }

    @Override
    public O visit(OWLDataComplementOf node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLDataOneOf node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLFacetRestriction node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLDatatypeRestriction node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLDatatype node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLLiteral node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLDataProperty property) {
        return delegate.visit(property);
    }

    @Override
    public O visit(OWLObjectProperty property) {
        return delegate.visit(property);
    }

    @Override
    public O visit(OWLObjectInverseOf property) {
        return delegate.visit(property);
    }

    @Override
    public O visit(SWRLLiteralArgument node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLVariable node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLIndividualArgument node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLBuiltInAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLClassAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLDataRangeAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLDataPropertyAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLDifferentIndividualsAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLObjectPropertyAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(SWRLSameIndividualAtom node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLOntology ontology) {
        return delegate.visit(ontology);
    }

    @Override
    public O visit(OWLAnnotation node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLAnnotationProperty property) {
        return delegate.visit(property);
    }

    @Override
    public O visit(OWLHasKeyAxiom axiom) {
        return delegate.visit(axiom);
    }

    @Override
    public O visit(OWLDataIntersectionOf node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLDataUnionOf node) {
        return delegate.visit(node);
    }

    @Override
    public O visit(OWLNamedIndividual individual) {
        return delegate.visit(individual);
    }

    @Override
    public O visit(OWLAnonymousIndividual individual) {
        return delegate.visit(individual);
    }

    @Override
    public O visit(IRI iri) {
        return delegate.visit(iri);
    }

    @Override
    public O visit(OWLDatatypeDefinitionAxiom axiom) {
        return delegate.visit(axiom);
    }
}
