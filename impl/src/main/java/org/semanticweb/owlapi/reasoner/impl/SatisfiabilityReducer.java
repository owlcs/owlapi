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
package org.semanticweb.owlapi.reasoner.impl;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class SatisfiabilityReducer implements
        OWLAxiomVisitorEx<OWLClassExpression> {

    private final OWLDataFactory df;

    /**
     * @param dataFactory
     *        data factory to use
     */
    public SatisfiabilityReducer(@Nonnull OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSubClassOfAxiom axiom) {
        return df.getOWLObjectIntersectionOf(axiom.getSubClass(),
                df.getOWLObjectComplementOf(axiom.getSuperClass()));
    }

    @Override
    public OWLClassExpression visit(
            @Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression
            visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDeclarationAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLClassAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(
            @Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom.asOWLSubClassOfAxiom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLHasKeyAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull SWRLRule rule) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return null;
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return null;
    }
}
